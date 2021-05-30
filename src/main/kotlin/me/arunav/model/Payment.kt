package me.arunav.model

import java.time.LocalDate
import java.util.*

data class Payment(
    val paymentId: Int,
    val cardNumber: String,
    val routingCode: String,
    val bankAccountId: String,
    val paymentDate: LocalDate,
    val remitStatus: Int,
    val returnDate: LocalDate? = null
)
