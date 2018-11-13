package uk.co.irokottaki.moneycontrol;


import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.schibsted.spain.barista.interaction.BaristaSleepInteractions.sleep;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityChartExpensesTabTest {

    @Rule
    public ActivityTestRule<SplashScreen> mActivityTestRule = new ActivityTestRule<>(SplashScreen
            .class);

    @Test
    public void mainActivityChartExpensesTabTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource
        // /index.html
        sleep(3000);

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.expenseText),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                2)),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("150"), closeSoftKeyboard());

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.descriptionCombo),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout2),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                3)),
                                1),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(8);
        appCompatCheckedTextView.perform(scrollTo());
        appCompatCheckedTextView.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.dateButton), withText("Select Date"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout3),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                4)),
                                0),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                allOf(withClassName(is("com.android.internal.widget" +
                                                ".ButtonBarLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                3),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.saveButton), withText("Save To File"),
                        childAtPosition(
                                allOf(withId(R.id.addExpenses),
                                        childAtPosition(
                                                withId(android.R.id.tabcontent),
                                                0)),
                                5),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.expenseText), withText("150"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                2)),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("87"));

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.expenseText), withText("87"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                2)),
                                1),
                        isDisplayed()));
        appCompatEditText3.perform(closeSoftKeyboard());

        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.descriptionCombo),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout2),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                3)),
                                1),
                        isDisplayed()));
        appCompatSpinner2.perform(click());

        DataInteraction appCompatCheckedTextView2 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(3);
        appCompatCheckedTextView2.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.saveButton), withText("Save To File"),
                        childAtPosition(
                                allOf(withId(R.id.addExpenses),
                                        childAtPosition(
                                                withId(android.R.id.tabcontent),
                                                0)),
                                5),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(android.R.id.tabs),
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0)),
                        2),
                        isDisplayed()));
        linearLayout.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.chartButton), withText("Pie Chart"),
                        childAtPosition(
                                allOf(withId(R.id.ChartExpenses),
                                        childAtPosition(
                                                withId(android.R.id.tabcontent),
                                                2)),
                                2),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction switch_ = onView(
                allOf(withId(R.id.toggleButton), withText("% or £"),
                        childAtPosition(
                                allOf(withId(R.id.chartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                5),
                        isDisplayed()));
        switch_.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.arrowLeft),
                        childAtPosition(
                                allOf(withId(R.id.chartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.arrowRight),
                        childAtPosition(
                                allOf(withId(R.id.chartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.arrowRight),
                        childAtPosition(
                                allOf(withId(R.id.chartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction imageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()));
        imageButton.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.barChartbutton), withText("Bar Chart"),
                        childAtPosition(
                                allOf(withId(R.id.ChartExpenses),
                                        childAtPosition(
                                                withId(android.R.id.tabcontent),
                                                2)),
                                5),
                        isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction switch_2 = onView(
                allOf(withId(R.id.switchButton), withText("% or £"),
                        childAtPosition(
                                allOf(withId(R.id.barChartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                5),
                        isDisplayed()));
        switch_2.perform(click());

        ViewInteraction appCompatImageButton4 = onView(
                allOf(withId(R.id.arrowLeft),
                        childAtPosition(
                                allOf(withId(R.id.barChartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatImageButton4.perform(click());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                allOf(withClassName(is("com.android.internal.widget.ButtonBarLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                3)),
                                3),
                        isDisplayed()));
        appCompatButton7.perform(click());

        ViewInteraction appCompatImageButton5 = onView(
                allOf(withId(R.id.arrowRight),
                        childAtPosition(
                                allOf(withId(R.id.barChartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatImageButton5.perform(click());

        ViewInteraction switch_3 = onView(
                allOf(withId(R.id.switchButton), withText("% or £"),
                        childAtPosition(
                                allOf(withId(R.id.barChartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                5),
                        isDisplayed()));
        switch_3.perform(click());

        ViewInteraction imageButton2 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()));
        imageButton2.perform(click());

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.annualChartButton), withText("Annual Chart"),
                        childAtPosition(
                                allOf(withId(R.id.ChartExpenses),
                                        childAtPosition(
                                                withId(android.R.id.tabcontent),
                                                2)),
                                8),
                        isDisplayed()));
        appCompatButton8.perform(click());

        ViewInteraction appCompatImageButton6 = onView(
                allOf(withId(R.id.leftYearButton), withContentDescription("Left Year Button"),
                        childAtPosition(
                                allOf(withId(R.id.annualChartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton6.perform(click());

        ViewInteraction appCompatImageButton7 = onView(
                allOf(withId(R.id.leftYearButton), withContentDescription("Left Year Button"),
                        childAtPosition(
                                allOf(withId(R.id.annualChartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton7.perform(click());

        ViewInteraction appCompatImageButton8 = onView(
                allOf(withId(R.id.leftYearButton), withContentDescription("Left Year Button"),
                        childAtPosition(
                                allOf(withId(R.id.annualChartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton8.perform(click());

        ViewInteraction imageButton3 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()));
        imageButton3.perform(click());

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.annualSavingsButton), withText("Savings Chart"),
                        childAtPosition(
                                allOf(withId(R.id.ChartExpenses),
                                        childAtPosition(
                                                withId(android.R.id.tabcontent),
                                                2)),
                                11),
                        isDisplayed()));
        appCompatButton9.perform(click());

        ViewInteraction appCompatImageButton9 = onView(
                allOf(withId(R.id.leftYearButton), withContentDescription("Left Year Button"),
                        childAtPosition(
                                allOf(withId(R.id.annualSavingsView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatImageButton9.perform(click());

        ViewInteraction imageButton4 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()));
        imageButton4.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
