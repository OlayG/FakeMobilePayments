package com.imobile3.groovypayments.data.enums

sealed class CashStatus {
    data class Paid(val change: String = "") : CashStatus()
    data class Partial(val amountLeft: String) : CashStatus()
    object NoAction : CashStatus()
}