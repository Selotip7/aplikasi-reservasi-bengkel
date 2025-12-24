package com.my.projekakhir.models

data class Booking(
    var serviceName: String? = "",
    var timestamp: Long? = System.currentTimeMillis()
)