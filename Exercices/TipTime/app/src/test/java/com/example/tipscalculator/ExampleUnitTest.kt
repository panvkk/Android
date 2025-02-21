package com.example.tipscalculator

import org.junit.Test

import org.junit.Assert.*
import java.text.NumberFormat

class TipCalculatorTest {
    @Test
    fun calculateTip_20PercentNoRoundUp() {
        val amount = 10.00
        val tipPercent = 20.00
        val expectTip = NumberFormat.getCurrencyInstance().format(2)
        val actualTip = calculateTip(amount = amount, tipPercent =  tipPercent, roundUp = false)
        assertEquals(expectTip, actualTip)
    }
}