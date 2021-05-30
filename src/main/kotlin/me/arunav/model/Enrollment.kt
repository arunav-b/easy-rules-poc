package me.arunav.model

data class Enrollment(
    val enrollmentId: Int,
    val cardNumber: String,
    val instrumentId: Int,
    val routingCode: String,
    val bankAccountId: String,
    val enrollmentStatus: Int
)
