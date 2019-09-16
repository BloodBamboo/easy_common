package cn.com.edu.hnzikao.kotlin.base

object ParameterField {
    val CLASS: String = "CLASS"
    val BUNDLE: String = "BUNDLE"
    //当viewModel共享数据时live可能会接收多个事件，通过确定接收类来区别
    val RECEIVER_CLASS: String = "RECEIVER_CLASS"
    val MESSAGE: String = "MESSAGE"
}