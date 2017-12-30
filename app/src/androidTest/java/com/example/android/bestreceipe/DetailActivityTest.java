package com.example.android.bestreceipe;

/**
 * Created by sunilkumar on 29/12/17.
 */

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class DetailActivityTest {

    @Rule
    public ActivityTestRule<DetailActivity> detailActivityActivityTestRule = new ActivityTestRule<>(DetailActivity.class);

    @Test
    public void checkViewVisibility() {
        onView(withId(R.id.tv_step_instruction)).check(matches(isDisplayed()));
        onView(withId(R.id.iv_empty_video)).check(matches(isDisplayed()));
        onView(withContentDescription("Navigate up")).perform(click());
    }
}