package com.example.bniqrcase

import android.graphics.drawable.Icon
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.bniqrcase.ui.theme.BNIOrange
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class QRTest {
    @get:Rule
    val composeTestRule = createComposeRule() // or createAndroidComposeRule<YourActivity>()

    @Test
    fun TestSaldoCorrectly() {

        composeTestRule.setContent { QRActivity() }

        var buttonClicked = false
        // Set the content of your Composable
        composeTestRule.setContent {
            FloatingActionButton(
                onClick = { buttonClicked = true },
                modifier = Modifier.testTag("MyFAB")
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.qr_scan),
                    contentDescription = "QR Scan",
                    tint = BNIOrange
                )
            }
        }

        // Click the FloatingActionButton
        composeTestRule.onNodeWithTag("MyFAB").performClick()

        // Verify that the button click triggered the expected behavior
        Assert.assertTrue(buttonClicked)
    }
}