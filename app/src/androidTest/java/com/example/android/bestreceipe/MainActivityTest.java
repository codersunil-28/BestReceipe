package com.example.android.bestreceipe;

/**
 * Created by sunilkumar on 29/12/17.
 */

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    //Test to open the navigation drawer and then clicking on a menu item.
    @Test
    public void clickImage_OpensNavDrawer() {
        onView(withId(R.id.bake_img_logo)).perform(click());
        onView(withId(R.id.drawer_layout)).check(matches(isDisplayed()));
        onView(withText(R.string.nutella_pie)).perform(click());
    }

    //Test to open the navigation drawer,click on a menu item and check visibility and scrolls of the respective elements(CardView and 2 RecyclerViews) and clicks.
    @Test
    public void checkRecyclerViewVisibilityAndClicks() {
        onView(withId(R.id.bake_img_logo)).perform(click());
        onView(withId(R.id.drawer_layout)).check(matches(isDisplayed()));
        onView(withText(R.string.nutella_pie)).perform(click());
        onView(withId(R.id.cv_recipe_list)).check(matches(isDisplayed()));
        onView(withId(R.id.cv_recipe_list)).perform(swipeUp());
        onView(withId(R.id.recipe_detail_ingredients_recycler_view)).check(matches(isDisplayed()));
        onView(withId(R.id.recipe_detail_ingredients_recycler_view)).perform(swipeUp());
        onView(withId(R.id.recipe_detail_steps_recycler_view)).check(matches(isDisplayed()));
        onView(withId(R.id.recipe_detail_steps_recycler_view)).perform(swipeUp());
        onView(ViewMatchers.withId(R.id.recipe_detail_steps_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
    }
}