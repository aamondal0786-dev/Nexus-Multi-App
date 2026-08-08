package com.example

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.compose.ui.test.onRoot

@RunWith(AndroidJUnit4::class)
class NexusAppTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testNexusApp() {
        composeTestRule.setContent {
            NexusApp()
        }
        composeTestRule.onRoot().assertExists()
    }
}
