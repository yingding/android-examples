package com.example.compose.rally

import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import com.example.compose.rally.ui.components.RallyTopAppBar
import com.example.compose.rally.ui.theme.RallyTheme
import org.junit.Rule
import org.junit.Test

class TopApBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun rallyTopAppBarTest() {
        val allScreens = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTheme {
                RallyTopAppBar(
                    allScreens = allScreens,
                    onTabSelected = {},
                    currentScreen = RallyScreen.Accounts
                )
            }
        }
        // current Thread to sleep
        // Thread.sleep(5000)

        /*print the compose semantics tree to the Logcat,
         * You will need to select No Filters in Logcat, since the application is run as
         * com.example.compose.rally, but not com.example.compose.rally.test
         *
         * Examing unmerged tree of all nodes
         * https://developer.android.com/codelabs/jetpack-compose-testing#4
         */
        composeTestRule.onRoot(useUnmergedTree = true).printToLog("currentLabelExists")

//        composeTestRule
//            .onNodeWithContentDescription(RallyScreen.Accounts.name)
//            .assertIsSelected()

        composeTestRule
            .onNode(
                hasText(RallyScreen.Accounts.name.uppercase()) and
                        hasParent(
                            hasContentDescription(RallyScreen.Accounts.name)
                        ),
                useUnmergedTree = true
            )
            .assertExists()
        // testing whether there is a node with text="ACCOUNTS"
        // and its parent has contentDescription "Accounts", to see if such a node exist in the semantics tree
    }
}