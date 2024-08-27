package me.fycz.fqweb.web


// 定义一个ReturnData类，用于返回数据
class ReturnData {

    // 是否成功，默认为false
    var isSuccess: Boolean = false
        private set

    // 错误信息，默认为"未知错误,请联系开发者!"
    var errorMsg: String = "未知错误,请联系开发者!"
        private set

    // 返回的数据，默认为null
    var data: Any? = null
        private set

    // 设置错误信息，并将isSuccess设为false
    fun setErrorMsg(errorMsg: String): ReturnData {
        this.isSuccess = false
        this.errorMsg = errorMsg
        return this
    }

    // 设置数据，并将isSuccess设为true，errorMsg设为""
    fun setData(data: Any): ReturnData {
        this.isSuccess = true
        this.errorMsg = ""
        this.data = data
        return this
    }
}