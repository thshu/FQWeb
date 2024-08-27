package me.fycz.fqweb.utils

import de.robv.android.xposed.XSharedPreferences
/**
 * @author fengyue
 * @date 2022/9/19 17:23
 */
object XSPUtils {
    // 声明一个XSharedPreferences对象，用于存储配置信息
    private var prefs = XSharedPreferences("me.fycz.FQWeb", "config")

    // 获取布尔值
    fun getBoolean(key: String, defValue: Boolean = false): Boolean {
        // 如果配置文件发生变化，重新加载配置文件
        if (prefs.hasFileChanged()) {
            prefs.reload()
        }
        // 返回指定键的布尔值，如果不存在则返回默认值
        return prefs.getBoolean(key, defValue)
    }

    // 获取整数
    fun getInt(key: String, defValue: Int = 0): Int {
        // 如果配置文件发生变化，重新加载配置文件
        if (prefs.hasFileChanged()) {
            prefs.reload()
        }
        // 返回指定键的整数，如果不存在则返回默认值
        return prefs.getInt(key, defValue)
    }

    // 获取浮点数
    fun getFloat(key: String, defValue: Float = 0F): Float {
        // 如果配置文件发生变化，重新加载配置文件
        if (prefs.hasFileChanged()) {
            prefs.reload()
        }
        // 返回指定键的浮点数，如果不存在则返回默认值
        return prefs.getFloat(key, defValue)
    }

    // 获取字符串
    fun getString(key: String, defValue: String = ""): String? {
        // 如果配置文件发生变化，重新加载配置文件
        if (prefs.hasFileChanged()) {
            prefs.reload()
        }
        // 返回指定键的字符串，如果不存在则返回默认值
        return prefs.getString(key, defValue)
    }

    // 获取所有配置信息
    fun getAll(): Map<String, *> {
        // 如果配置文件发生变化，重新加载配置文件
        if (prefs.hasFileChanged()) {
            prefs.reload()
        }
        // 返回所有配置信息
        return prefs.all
    }
}

// 判断是否启用某个功能
inline fun hasEnable(
    key: String,
    default: Boolean = false,
    noinline extraCondition: (() -> Boolean)? = null,
    crossinline block: () -> Unit
) {
    // 如果存在额外的条件，则执行额外的条件判断
    val conditionResult = if (extraCondition != null) extraCondition() else true
    // 如果启用了该功能且额外的条件成立，则执行相应的操作
    if (XSPUtils.getBoolean(key, default) && conditionResult) {
        block()
    }
}
