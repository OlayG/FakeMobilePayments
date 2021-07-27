package com.imobile3.groovypayments.data.utils

fun String.getAmountFromFormattedAmount() = filter { it.isDigit() }.toLongOrNull() ?: 0L