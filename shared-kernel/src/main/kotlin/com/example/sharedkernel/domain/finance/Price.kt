package com.example.sharedkernel.domain.finance

import com.example.sharedkernel.domain.base.ValueObject

data class Price(val amount: Int, val currency: Currency) : ValueObject
