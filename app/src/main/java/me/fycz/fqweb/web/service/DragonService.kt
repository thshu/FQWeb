package me.fycz.fqweb.web.service

import me.fycz.fqweb.constant.Config
import me.fycz.fqweb.utils.FiledNameUtils
import me.fycz.fqweb.utils.GlobalApp
import me.fycz.fqweb.utils.callMethod
import me.fycz.fqweb.utils.callStaticMethod
import me.fycz.fqweb.utils.findClass
import me.fycz.fqweb.utils.findField
import me.fycz.fqweb.utils.getObjectField
import me.fycz.fqweb.utils.log
import me.fycz.fqweb.utils.new
import me.fycz.fqweb.utils.setBooleanField
import me.fycz.fqweb.utils.setFloatField
import me.fycz.fqweb.utils.setIntField
import me.fycz.fqweb.utils.setLongField
import me.fycz.fqweb.utils.setObjectField
import me.fycz.fqweb.utils.setShortField

/**
 * @author fengyue
 * @date 2023/5/30 10:54
 * @description
 */
object DragonService {

    // 延迟初始化DragonService的ClassLoader
    private val dragonClassLoader: ClassLoader by lazy {
        GlobalApp.getClassloader()
    }

    // 根据关键字和页码搜索书籍
    fun search(keyword: String, page: Int = 1): Any {
        // 根据包名和类名获取GetSearchPageRequest类
        val GetSearchPageRequest =
            "${Config.rpcModelPackage}.GetSearchPageRequest".findClass(dragonClassLoader)
        // 实例化GetSearchPageRequest类
        val getSearchPageRequest = GetSearchPageRequest.newInstance()
        // 设置GetSearchPageRequest类的属性
        getSearchPageRequest.setIntField("bookshelfSearchPlan", 4)
        getSearchPageRequest.setIntField("bookstoreTab", 2)
        getSearchPageRequest.setObjectField("clickedContent", "page_search_button")
        getSearchPageRequest.setObjectField("query", keyword)
        getSearchPageRequest.setObjectField(
            "searchSource",
            "${Config.rpcModelPackage}.SearchSource"
                .findClass(dragonClassLoader)
                .callStaticMethod("findByValue", arrayOf(Int::class.java), 1)
        )
        getSearchPageRequest.setObjectField("searchSourceId", "clks###")
        getSearchPageRequest.setObjectField("tabName", "store")
        getSearchPageRequest.setObjectField(
            "tabType", "${Config.rpcModelPackage}.SearchTabType"
                .findClass(dragonClassLoader)
                .callStaticMethod("findByValue", arrayOf(Int::class.java), 1)
        )
        getSearchPageRequest.setShortField("userIsLogin", 1)
        setField(getSearchPageRequest, "offset", (page - 1) * 10)
        setField(getSearchPageRequest, "passback", (page - 1) * 10)
        // 调用a类的b方法，传入GetSearchPageRequest对象
        return callFunction(
            clzName = "${Config.rpcApiPackage}.a",
            obj = getSearchPageRequest,
            funcName = "b"
        )
    }

    // 根据书籍ID获取书籍信息
    fun getInfo(bookId: String): Any {
        // 根据包名和类名获取BookDetailRequest类
        val BookDetailRequest =
            "${Config.rpcModelPackage}.BookDetailRequest".findClass(dragonClassLoader)
        // 实例化BookDetailRequest类
        val bookDetailRequest = BookDetailRequest.newInstance()
        // 设置BookDetailRequest类的属性
        bookDetailRequest.setLongField("bookId", bookId.toLong())
        // 调用a类的a方法，传入BookDetailRequest对象
        return callFunction(
            clzName = "${Config.rpcApiPackage}.a",
            obj = bookDetailRequest
        )
    }

    // 根据书籍ID获取书籍目录
    fun getCatalog(bookId: String): Any {
        // 根据包名和类名获取GetDirectoryForItemIdRequest类
        val GetDirectoryForItemIdRequest =
            "${Config.rpcModelPackage}.GetDirectoryForItemIdRequest".findClass(dragonClassLoader)
        // 实例化GetDirectoryForItemIdRequest类
        val getDirectoryForItemIdRequest = GetDirectoryForItemIdRequest.newInstance()
        // 设置GetDirectoryForItemIdRequest类的属性
        getDirectoryForItemIdRequest.setObjectField("bookId", bookId.toLong())
        // 调用a类的a方法，传入GetDirectoryForItemIdRequest对象
        return callFunction(
            clzName = "${Config.rpcApiPackage}.a",
            obj = getDirectoryForItemIdRequest
        )
    }

    // 根据书籍ID获取书籍内容
    fun getContent(itemId: String): Any {
        // 根据包名和类名获取FullRequest类
        val FullRequest =
            "${Config.rpcModelPackage}.FullRequest".findClass(dragonClassLoader)
        // 实例化FullRequest类
        val fullRequest = FullRequest.newInstance()
        // 设置FullRequest类的属性
        fullRequest.setObjectField("itemId", itemId)
        // 调用Config.readerFullRequestClz类的a方法，传入FullRequest对象
        return callFunction(
            clzName = Config.readerFullRequestClz,
            obj = fullRequest
        )
    }

    // 解码书籍内容
    fun decodeContent(itemContent: Any): Any {
        // 根据包名和类名获取a类
        return "com.dragon.read.reader.bookend.a.a".findClass(dragonClassLoader)
            // 实例化a类
            .new(null).callMethod("a", itemContent)!!.callMethod("blockingFirst")!!
    }

    // 获取书城信息
    fun bookMall(parameters: Map<String, MutableList<String>>): Any {
        // 根据包名和类名获取GetBookMallCellChangeRequest类
        val GetBookMallCellChangeRequest =
            "${Config.rpcModelPackage}.GetBookMallCellChangeRequest".findClass(dragonClassLoader)
        // 实例化GetBookMallCellChangeRequest类
        val getBookMallCellChangeRequest = GetBookMallCellChangeRequest.newInstance()
        // 设置GetBookMallCellChangeRequest类的属性
        parameters.forEach { (key, value) ->
            setField(getBookMallCellChangeRequest, key, value.first())
        }
        // 调用a类的a方法，传入GetBookMallCellChangeRequest对象
        return callFunction(
            clzName = "${Config.rpcApiPackage}.a",
            obj = getBookMallCellChangeRequest
        )
    }

    // 获取新书分类信息
    fun newCategory(parameters: Map<String, MutableList<String>>): Any {
        // 根据包名和类名获取GetNewCategoryLandingPageRequest类
        val GetNewCategoryLandingPageRequest =
            "${Config.rpcModelPackage}.GetNewCategoryLandingPageRequest".findClass(dragonClassLoader)
        // 实例化GetNewCategoryLandingPageRequest类
        val getNewCategoryLandingPageRequest = GetNewCategoryLandingPageRequest.newInstance()
        // 设置GetNewCategoryLandingPageRequest类的属性
        parameters.forEach { (key, value) ->
            setField(getNewCategoryLandingPageRequest, key, value.first())
        }
        // 调用a类的a方法，传入GetNewCategoryLandingPageRequest对象
        return callFunction(
            clzName = "${Config.rpcApiPackage}.a",
            obj = getNewCategoryLandingPageRequest
        )
    }

    // 调用指定类的方法
    private fun callFunction(clzName: String, funcName: String = "a", obj: Any): Any {
        return try {
            // 根据类名获取类
            clzName.findClass(dragonClassLoader)
                // 调用类的静态方法，传入obj对象
                .callStaticMethod(
                    funcName,
                    obj
                )!!.callMethod("blockingFirst")!!
        } catch (e: Throwable) {
            // 打印异常堆栈信息
            e.stackTraceToString()
        }
    }

    // 设置对象的属性
    private fun setField(obj: Any, name: String, value: Any) {
        try {
            // 将下划线命名转换为驼峰命名
            val fieldName = FiledNameUtils.underlineToCamel(name)
            val fieldValueStr = value.toString()
            // 根据属性类型设置属性值
            when (val field = obj.getObjectField(fieldName)) {
                is Short -> obj.setShortField(fieldName, fieldValueStr.toShort())
                is Int -> obj.setIntField(fieldName, fieldValueStr.toInt())
                is Long -> obj.setLongField(fieldName, fieldValueStr.toLong())
                is Float -> obj.setFloatField(fieldName, fieldValueStr.toFloat())
                is Boolean -> obj.setBooleanField(fieldName, fieldValueStr.toBoolean())
                else -> {
                    val fieldClz = field?.javaClass ?: obj.findField(fieldName)?.type!!
                    if (fieldClz.isEnum) {
                        // 如果属性是枚举类型，根据枚举值设置属性值
                        obj.setObjectField(
                            fieldName,
                            fieldClz.callStaticMethod("findByValue", fieldValueStr.toInt())
                        )
                    } else {
                        // 如果属性是String类型，直接设置属性值
                        obj.setObjectField(fieldName, fieldValueStr)
                    }
                }
            }
        } catch (e: Throwable) {
            // 打印异常堆栈信息
            log("Set field $name=$value error：\n${e.stackTraceToString()}")
        }
    }
}