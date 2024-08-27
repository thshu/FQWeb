package me.fycz.fqweb.web

import android.graphics.Bitmap
import de.robv.android.xposed.XposedHelpers
import fi.iki.elonen.NanoHTTPD
import me.fycz.fqweb.MainHook.Companion.moduleRes
import me.fycz.fqweb.utils.JsonUtils
import me.fycz.fqweb.web.controller.DragonController
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

/**
 * @author fengyue
 * @date 2023/5/29 17:58
 * @description
 */
class HttpServer(port: Int) : NanoHTTPD(port) {
    override fun serve(session: IHTTPSession): Response {
        var returnData: ReturnData? = null
        // 获取请求头中的content-type，并尝试将其转换为UTF-8编码
        val ct = ContentType(session.headers["content-type"]).tryUTF8()
        // 将转换后的content-type设置回请求头
        session.headers["content-type"] = ct.contentTypeHeader
        val uri = session.uri
        try {
            // 如果请求方法为GET
            if (session.method == Method.GET) {
                // 获取请求参数
                val parameters = session.parameters
                // 根据请求的URI，调用相应的控制器方法，获取返回数据
                returnData = when {
                    uri.endsWith("/search") -> DragonController.search(parameters)
                    uri.endsWith("/info") -> DragonController.info(parameters)
                    uri.endsWith("/catalog") -> DragonController.catalog(parameters)
                    uri.endsWith("/content") -> DragonController.content(parameters)
                    uri.endsWith("/reading/bookapi/bookmall/cell/change/v1/") -> DragonController.bookMall(parameters)
                    uri.endsWith("/reading/bookapi/new_category/landing/v/") -> DragonController.newCategory(parameters)
                    else -> null
                }
            }/* else if (session.method == Method.POST) {
                val parameters = session.parameters
                val files = HashMap<String, String>()
                session.parseBody(files)
                val postBody = files["postData"]
            }*/
            // 如果返回数据为空，则返回404页面
            if (returnData == null) {
                return newChunkedResponse(
                    Response.Status.NOT_FOUND,
                    MIME_HTML,
                    XposedHelpers.assetAsByteArray(moduleRes, "404.html").inputStream()
                )
            }
            // 如果返回数据为Bitmap类型，则将其转换为PNG格式的图片，并返回
            val response = if (returnData.data is Bitmap) {
                val outputStream = ByteArrayOutputStream()
                (returnData.data as Bitmap).compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                val byteArray = outputStream.toByteArray()
                outputStream.close()
                val inputStream = ByteArrayInputStream(byteArray)
                newFixedLengthResponse(
                    Response.Status.OK,
                    "image/png",
                    inputStream,
                    byteArray.size.toLong()
                )
            } else {
                // 否则，将返回数据转换为JSON格式，并返回
                newFixedLengthResponse(JsonUtils.toJson(returnData))
            }
            // 添加跨域请求头
            response.addHeader("Access-Control-Allow-Methods", "GET, POST")
            response.addHeader("Access-Control-Allow-Origin", session.headers["origin"])
            return response
        } catch (e: Exception) {
            // 如果发生异常，则返回异常堆栈信息
            return newFixedLengthResponse(e.stackTraceToString())
        }
    }
}