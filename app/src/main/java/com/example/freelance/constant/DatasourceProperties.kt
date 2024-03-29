package com.example.freelance.constant

object DatasourceProperties {
   const val TIMEOUT_READ: Long = 60000L
   const val TIMEOUT_CONNECT: Long = 60000L

    fun getDomain(): String {
        return "dog.ceo"
    }

    fun getUrl(): String {
        return "https://dog.ceo/"
    }
}