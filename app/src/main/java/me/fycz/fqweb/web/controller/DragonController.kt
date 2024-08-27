package me.fycz.fqweb.web.controller

import me.fycz.fqweb.utils.getObjectField
import me.fycz.fqweb.web.ReturnData
import me.fycz.fqweb.web.service.DragonService


/**
 * @author fengyue
 * @date 2023/5/29 18:04
 * @description
 */
object DragonController {

    /**
     * 搜索
     * @param parameters 参数
     * @return 返回数据
     */
    fun search(parameters: Map<String, List<String>>): ReturnData {
        // 获取参数query的值
        val keyword = parameters["query"]?.firstOrNull()
        // 获取参数page的值，如果没有则默认为1
        val page = parameters["page"]?.firstOrNull()?.toInt() ?: 1
        val returnData = ReturnData()
        // 如果参数query为空，则返回错误信息
        if (keyword.isNullOrEmpty()) {
            return returnData.setErrorMsg("参数query不能为空")
        }
        // 调用DragonService的search方法，获取搜索结果，并设置到返回数据中
        returnData.setData(DragonService.search(keyword, page))
        return returnData
    }

    /**
     * 获取书籍信息
     * @param parameters 参数
     * @return 返回数据
     */
    fun info(parameters: Map<String, MutableList<String>>): ReturnData {
        // 获取参数book_id的值
        val bookId = parameters["book_id"]?.firstOrNull()
        val returnData = ReturnData()
        // 如果参数book_id为空，则返回错误信息
        if (bookId.isNullOrEmpty()) {
            return returnData.setErrorMsg("参数book_id不能为空")
        }
        // 调用DragonService的getInfo方法，获取书籍信息，并设置到返回数据中
        returnData.setData(DragonService.getInfo(bookId))
        return returnData
    }

    /**
     * 获取书籍目录
     * @param parameters 参数
     * @return 返回数据
     */
    fun catalog(parameters: Map<String, MutableList<String>>): ReturnData {
        // 获取参数book_id的值
        val bookId = parameters["book_id"]?.firstOrNull()
        val returnData = ReturnData()
        // 如果参数book_id为空，则返回错误信息
        if (bookId.isNullOrEmpty()) {
            return returnData.setErrorMsg("参数book_id不能为空")
        }
        // 调用DragonService的getCatalog方法，获取书籍目录，并设置到返回数据中
        returnData.setData(DragonService.getCatalog(bookId))
        return returnData
    }

    /**
     * 获取书籍内容
     * @param parameters 参数
     * @return 返回数据
     */
    fun content(parameters: Map<String, MutableList<String>>): ReturnData {
        // 获取参数item_id的值
        val itemId = parameters["item_id"]?.firstOrNull()
        val returnData = ReturnData()
        // 如果参数item_id为空，则返回错误信息
        if (itemId.isNullOrEmpty()) {
            return returnData.setErrorMsg("参数item_id不能为空")
        }
        // 调用DragonService的getContent方法，获取书籍内容
        val content = DragonService.getContent(itemId)
        // 尝试解码书籍内容
        kotlin.runCatching {
            DragonService.decodeContent(content.getObjectField("data") as Any)
        }
        // 设置返回数据
        returnData.setData(content)
        return returnData
    }

    /**
     * 获取书城信息
     * @param parameters 参数
     * @return 返回数据
     */
    fun bookMall(parameters: Map<String, MutableList<String>>): ReturnData {
        val returnData = ReturnData()
        // 调用DragonService的bookMall方法，获取书城信息，并设置到返回数据中
        returnData.setData(DragonService.bookMall(parameters))
        return returnData
    }

    /**
     * 获取新书分类
     * @param parameters 参数
     * @return 返回数据
     */
    fun newCategory(parameters: Map<String, MutableList<String>>): ReturnData {
        val returnData = ReturnData()
        // 调用DragonService的newCategory方法，获取新书分类，并设置到返回数据中
        returnData.setData(DragonService.newCategory(parameters))
        return returnData
    }

}