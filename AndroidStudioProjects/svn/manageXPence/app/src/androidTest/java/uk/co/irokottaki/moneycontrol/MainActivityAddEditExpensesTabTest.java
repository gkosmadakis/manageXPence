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
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.schibsted.spain.barista.interaction.BaristaSleepInteractions.sleep;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityAddEditExpensesTabTest {
    private String currentDay;
    private String currentMonth;
    private int currentYear;

    @Rule
    public ActivityTestRule<SplashScreen> mActivityTestRule = new ActivityTestRule<>(SplashScreen
            .class);

    @Before
    public void setUp(){

        final Calendar calendar = Calendar.getInstance();
        int currentDayInt = calendar.get(Calendar.DAY_OF_MONTH);
        int currentMonthInt = calendar.get(Calendar.MONTH);
        currentYear = calendar.get(Calendar.YEAR);
        currentMonthInt++;
        if (currentMonthInt < 10){
            currentMonth = String.format("%02d", currentMonthInt);
        }
        else {
            currentMonth = String.valueOf(currentMonthInt);
        }

        if (currentDayInt < 10){
            currentDay = String.format("%02d", currentDayInt);
        }
        else {
            currentDay = String.valueOf(currentDayInt);
        }
    }

    @Test
    public void mainActivityAddExpensesTabTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource
        // /index.html

        sleep(3000);

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.addExpensesInfoBtn),
                        childAtPosition(
                                allOf(withId(R.id.addExpenses),
                                        childAtPosition(
                                                withId(android.R.id.tabcontent),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatButton11 = onView(
                allOf(withId(android.R.id.button1), withText("Close"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton11.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.saveButton), withText("Save To File"),
                        childAtPosition(
                                allOf(withId(R.id.addExpenses),
                                        childAtPosition(
                                                withId(android.R.id.tabcontent),
                                                0)),
                                6),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton2.perform(scrollTo(),click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.expenseText),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                3)),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("105"), closeSoftKeyboard());

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.descriptionCombo),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout2),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                4)),
                                1),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(2);
        appCompatCheckedTextView.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.dateButton), withText("Select Date"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout3),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                5)),
                                0),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton4.perform(scrollTo(),click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.saveButton), withText("Save To File"),
                        childAtPosition(
                                allOf(withId(R.id.addExpenses),
                                        childAtPosition(
                                                withId(android.R.id.tabcontent),
                                                0)),
                                6),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.incomeField),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout4),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                9)),
                                1),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("10"), closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource
        // /index.html
        sleep(1000);

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.incomeField), withText("10"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout4),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                9)),
                                1),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("100"));

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.incomeField), withText("100"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout4),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                9)),
                                1),
                        isDisplayed()));
        appCompatEditText6.perform(closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource
        // /index.html
        sleep(1000);

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.incomeField), withText("100"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout4),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                9)),
                                1),
                        isDisplayed()));
        appCompatEditText7.perform(replaceText("1000"));

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.incomeField), withText("1000"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout4),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                9)),
                                1),
                        isDisplayed()));
        appCompatEditText8.perform(closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource
        // /index.html
        sleep(1000);

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.incomeField), withText("1000"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout4),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                9)),
                                1),
                        isDisplayed()));
        appCompatEditText9.perform(pressImeActionButton());

        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.editButton), withText("Edit"),
                        childAtPosition(
                                allOf(withId(R.id.addExpenses),
                                        childAtPosition(
                                                withId(android.R.id.tabcontent),
                                                0)),
                                14),
                        isDisplayed()));
        appCompatButton10.perform(scrollTo());
        appCompatButton10.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.searchByDescText),
                        childAtPosition(
                                allOf(withId(R.id.editView),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                2)));
        appCompatEditText2.perform(replaceText("bvc"), closeSoftKeyboard());

        ViewInteraction appCompatButton25 = onView(
                allOf(withId(R.id.searchButton), withText("Search"),
                        childAtPosition(
                                allOf(withId(R.id.editView),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                4)));
        appCompatButton25.perform(click());

        ViewInteraction appCompatButton27 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton27.perform(click());

        ViewInteraction appCompatEditText16 = onView(
                allOf(withId(R.id.searchByAmountText),
                        childAtPosition(
                                allOf(withId(R.id.editView),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                3)));
        appCompatEditText16.perform(replaceText("12"), closeSoftKeyboard());

        ViewInteraction appCompatButton28 = onView(
                allOf(withId(R.id.searchButton), withText("Search"),
                        childAtPosition(
                                allOf(withId(R.id.editView),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                4)));
        appCompatButton28.perform(click());

        ViewInteraction appCompatButton29 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton29.perform(scrollTo(), click());

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.searchByDescText),
                        childAtPosition(
                                allOf(withId(R.id.editView),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                2)));
        appCompatEditText10.perform(replaceText("Shopping"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.searchByAmountText),
                        childAtPosition(
                                allOf(withId(R.id.editView),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("105"), closeSoftKeyboard());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.searchButton), withText("Search"),
                        childAtPosition(
                                allOf(withId(R.id.editView),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.edit_btn), withText("Edit"),
                        childAtPosition(
                                withParent(withId(R.id.listView)),
                                1),
                        isDisplayed()));
        appCompatButton7.perform(click());

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.editField), withText("105 Shopping "+currentDay+"/"+currentMonth+"/"+currentYear),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText11.perform(replaceText("100 Shopping "+currentDay+"/"+currentMonth+"/"+currentYear));

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.editField), withText("100 Shopping "+currentDay+"/"+currentMonth+"/"+currentYear),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText12.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(android.R.id.button1), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton8.perform(scrollTo(), click());

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton9.perform(scrollTo(), click());

        ViewInteraction appCompatEditText13 = onView(
                allOf(withId(R.id.searchByAmountText), withText("105"),
                        childAtPosition(
                                allOf(withId(R.id.editView),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatEditText13.perform(replaceText("100"));

        ViewInteraction appCompatEditText14 = onView(
                allOf(withId(R.id.searchByDescText), withText("Shopping"),
                        childAtPosition(
                                allOf(withId(R.id.editView),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatEditText14.perform(replaceText(""));

        ViewInteraction appCompatEditText15 = onView(
                allOf(withId(R.id.searchByAmountText), withText("100"),
                        childAtPosition(
                                allOf(withId(R.id.editView),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatEditText15.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton12 = onView(
                allOf(withId(R.id.searchButton), withText("Search"),
                        childAtPosition(
                                allOf(withId(R.id.editView),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatButton12.perform(click());

        ViewInteraction appCompatButton13 = onView(
                allOf(withId(R.id.delete_btn), withText("Delete"),
                        childAtPosition(
                                withParent(withId(R.id.listView)),
                                2),
                        isDisplayed()));
        appCompatButton13.perform(click());

        ViewInteraction appCompatButton14 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton14.perform(scrollTo(), click());

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

        sleep(1000);

        ViewInteraction appCompatButton18 = onView(
                allOf(withId(R.id.editButton), withText("Edit"),
                        childAtPosition(
                                allOf(withId(R.id.addExpenses),
                                        childAtPosition(
                                                withId(android.R.id.tabcontent),
                                                0)),
                                14),
                        isDisplayed()));
        appCompatButton18.perform(click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.infoEditButton),
                        childAtPosition(
                                allOf(withId(R.id.editView),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                1)));
        appCompatImageButton3.perform(click());

        ViewInteraction appCompatButton15 = onView(
                allOf(withId(android.R.id.button1), withText("Close"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton15.perform(scrollTo(), click());

        ViewInteraction appCompatButton26 = onView(
                allOf(withId(R.id.searchButton), withText("Search"),
                        childAtPosition(
                                allOf(withId(R.id.editView),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                4)));
        appCompatButton26.perform(click());

        ViewInteraction appCompatButton16 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton16.perform(scrollTo(), click());

        pressBack();

        ViewInteraction appCompatButton30 = onView(
                allOf(withId(R.id.circleButton), withText("Income Circle"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout4),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                9)),
                                2),
                        isDisplayed()));
        appCompatButton30.perform(click());

        ViewInteraction appCompatButton31 = onView(
                allOf(withId(android.R.id.button1), withText("SET"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton31.perform(scrollTo(), click());

        ViewInteraction appCompatButton32 = onView(
                allOf(withId(R.id.circleButton), withText("Income Circle"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout4),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                9)),
                                2),
                        isDisplayed()));
        appCompatButton32.perform(click());

        ViewInteraction appCompatButton33 = onView(
                allOf(withId(android.R.id.button3), withText("Reset"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                0)));
        appCompatButton33.perform(scrollTo(), click());

        ViewInteraction appCompatCheckBox = onView(
                allOf(withId(R.id.repeatCheckBox), withText("Repeat"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                3)),
                                2),
                        isDisplayed()));
        appCompatCheckBox.perform(click());

        ViewInteraction appCompatButton17 = onView(
                allOf(withId(R.id.endDateButton), withText("End Date"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                3)),
                                3),
                        isDisplayed()));
        appCompatButton17.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withClassName(is("android.support.v7.widget.AppCompatImageButton")), withContentDescription("Next month"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.DayPickerView")),
                                        childAtPosition(
                                                withClassName(is("com.android.internal.widget.DialogViewAnimator")),
                                                0)),
                                2)));
        appCompatImageButton.perform(scrollTo(), click());

        ViewInteraction appCompatButton19 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton19.perform(scrollTo(), click());

        ViewInteraction appCompatCheckBox2 = onView(
                allOf(withId(R.id.repeatCheckBox), withText("Repeat"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                3)),
                                2),
                        isDisplayed()));
        appCompatCheckBox2.perform(click());
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
