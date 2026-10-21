package com.mrmagnusneo.catlin

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WaterTrackerTest {
    @get:Rule val rule = createComposeRule()

    @Test fun addsWater() {
        rule.setContent { WaterTracker() }
        rule.onNodeWithText("+250 мл").performClick()
        rule.onNodeWithText("Выпито: 250 мл").assertIsDisplayed()
    }

    @Test fun finishesDayAndResetsWater() {
        rule.setContent { WaterTracker() }
        rule.onNodeWithText("+250 мл").performClick()
        rule.onNodeWithText("Завершить день").performClick()
        rule.onNodeWithText("Выпито: 0 мл").assertIsDisplayed()
        rule.onNodeWithText("Дней подряд: 0").assertIsDisplayed()
    }

    @Test fun countsSuccessfulDays() {
        rule.setContent { WaterTracker() }
        repeat(6) { rule.onNodeWithText("+250 мл").performClick() }
        rule.onNodeWithText("Завершить день").performClick()
        rule.onNodeWithText("Дней подряд: 1").assertIsDisplayed()
    }
}
