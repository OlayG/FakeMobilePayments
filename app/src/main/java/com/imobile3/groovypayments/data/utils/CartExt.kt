package com.imobile3.groovypayments.data.utils

import com.imobile3.groovypayments.data.model.Cart

val Cart.cartAmountLeft: Long
    get() = grandTotal.minus(payments.sumOf { it.approvedAmount })