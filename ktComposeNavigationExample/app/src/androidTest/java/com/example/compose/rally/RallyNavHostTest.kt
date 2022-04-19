package com.example.compose.rally

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RallyNavHostTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: NavHostController

    /**
     * this function is called to setupRallyNavHost for all tests
     */
    @Before
    fun setupRallyNavHost() {
        composeTestRule.setContent {
            navController = rememberNavController()
            RallyNavHost(navController = navController)
        }
    }

    @Test
    fun rallyNavHost() {
        composeTestRule
            .onNodeWithContentDescription("Overview Screen")
            .assertIsDisplayed()
    }

    @Test
    fun rallyNavHost_navigateToAllAccounts_viaUI() {
        composeTestRule
            .onNodeWithContentDescription("All Accounts") // In RallyTab Modifier.clearAndSetSemantics { contentDescription = text }
            .performClick()
        composeTestRule
            .onNodeWithContentDescription("Accounts Screen")
            .assertIsDisplayed()
    }

    /**
     * https://developer.android.com/codelabs/jetpack-compose-navigation#6
     */
    @Test
    fun rallyNavHost_navigateToBills_viaUI() {
        // When click on "All Bills"
        composeTestRule.onNodeWithContentDescription("All Bills").apply {
            performScrollTo()
            performClick()
        }
        // Then the route is "Bills"
        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(route, "Bills")
    }


    @Test
    fun rallyNavHost_navigateToAllAccounts_callingNavigate() {
        // https://developer.android.com/codelabs/jetpack-compose-navigation#6
        runBlocking {
            withContext(Dispatchers.Main) {
                /* navController.navigate need to be made on the UI thread,
                 * thus we can wrap the call with coroutine with Main Dispatcher
                 * and runBlocking is used to wait for the coroutine executed
                 */
                navController.navigate(RallyScreen.Accounts.name)
            }
        }
        // Testing after the coroutine run with runBlocking
        composeTestRule
            .onNodeWithContentDescription("Accounts Screen")
            .assertIsDisplayed()
    }

}