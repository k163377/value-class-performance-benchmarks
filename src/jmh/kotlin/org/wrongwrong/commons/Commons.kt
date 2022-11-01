package org.wrongwrong.commons

data class Data(val i: Int)
@JvmInline value class Value(val i: Int)

data class DataWrapper(val data: Data)
data class ValueWrapper(val value: Value)
