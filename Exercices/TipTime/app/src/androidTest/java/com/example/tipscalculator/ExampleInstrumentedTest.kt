package com.example.tipscalculator


import java.text.NumberFormat
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tipscalculator.ui.theme.TipsCalculatorTheme
import org.junit.Test
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.onNodeWithText
import org.junit.runner.RunWith
import org.junit.Assert.*

class TipUiTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun calculate_20_percent_tip() {
        composeTestRule.setContent {
            TipsCalculatorTheme {
                TipTimeLayout()
            }
        }
        composeTestRule.onNodeWithText("Bill Amount")
            .performTextInput("10")
        composeTestRule.onNodeWithText("Tip Percentage").performTextInput("20")
        val expectedTip = NumberFormat.getCurrencyInstance().format(2)
        composeTestRule.onNodeWithText("Tip Amount: $expectedTip").assertExists(
            "No node with this text was found."
        )
    }
}