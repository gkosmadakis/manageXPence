package uk.co.irokottaki.moneycontrol;


import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.SeekBar;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
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
public class MainActivityManageExpensesTabTest {
    private String currentDay;
    private String currentMonth;
    private int currentYear;
    private String twentyDaysBeforeNow;
    private String monthTwentyDaysBeforeNow;

    @Rule
    public ActivityTestRule<SplashScreen> mActivityTestRule = new ActivityTestRule<>(SplashScreen
            .class);

    @Before
    public void setUp(){

        final Calendar calendar = Calendar.getInstance();
        Date currentDate = new Date();
        int currentDayInt = calendar.get(Calendar.DAY_OF_MONTH);
        int currentMonthInt = calendar.get(Calendar.MONTH);
        currentYear = calendar.get(Calendar.YEAR);
        currentMonthInt++;

        if (currentDayInt < 10){
            currentDay = String.format("%02d", currentDayInt);
        }
        else {
            currentDay = String.valueOf(currentDayInt);
        }

        if (currentMonthInt < 10){
            currentMonth = String.format("%02d", currentMonthInt);
        }
        else {
            currentMonth = String.valueOf(currentMonthInt);
        }
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, -20);
        int twentyDaysBeforeNowInt = calendar.get(Calendar.DAY_OF_MONTH);
        int monthTwentyDaysBeforeNowInt = calendar.get(Calendar.MONTH);
        monthTwentyDaysBeforeNowInt++;

        if (twentyDaysBeforeNowInt < 10){
            twentyDaysBeforeNow = String.format("%02d", twentyDaysBeforeNowInt);
        }
        else {
            twentyDaysBeforeNow = String.valueOf(twentyDaysBeforeNowInt);
        }

        if (monthTwentyDaysBeforeNowInt < 10){
            monthTwentyDaysBeforeNow = String.format("%02d", monthTwentyDaysBeforeNowInt);
        }
        else {
            monthTwentyDaysBeforeNow = String.valueOf(monthTwentyDaysBeforeNowInt);
        }
    }

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.READ_EXTERNAL_STORAGE",
                    "android.permission.WRITE_EXTERNAL_STORAGE");

    @Test
    public void mainActivityManageExpensesTabTest() {

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
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.expenseText),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                2)),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("200"), closeSoftKeyboard());

        //pressBack();

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
                .atPosition(5);
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
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
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

        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(android.R.id.tabs),
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0)),
                        1),
                        isDisplayed()));
        linearLayout.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.reportButton), withText("Report"),
                        childAtPosition(
                                allOf(withId(R.id.ManageExpenses),
                                        childAtPosition(
                                                withId(android.R.id.tabcontent),
                                                1)),
                                6),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.monthSpinner),
                        childAtPosition(
                                allOf(withId(R.id.reportView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                6),
                        isDisplayed()));
        appCompatSpinner2.perform(click());

        DataInteraction appCompatCheckedTextView2 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(13);
        appCompatCheckedTextView2.perform(click());

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

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource
        // /index.html
        sleep(3000);

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.exportButton), withText("Export"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout8),
                                        childAtPosition(
                                                withId(R.id.ManageExpenses),
                                                12)),
                                0),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.title), withText("Export TXT"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.view.menu" +
                                                ".ListMenuItemView")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource
        // /index.html
        sleep(3000);

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.exportButton), withText("Export"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout8),
                                        childAtPosition(
                                                withId(R.id.ManageExpenses),
                                                12)),
                                0),
                        isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(R.id.title), withText("Export PDF"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.view.menu.ListMenuItemView")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView2.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.infoDateRange),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout6),
                                        childAtPosition(
                                                withId(R.id.ManageExpenses),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(android.R.id.button1), withText("Close"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton7.perform(scrollTo(),click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.backUpInfoButton),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout14),
                                        childAtPosition(
                                                withId(R.id.ManageExpenses),
                                                10)),
                                1),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(android.R.id.button1), withText("Close"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton8.perform(click());

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.budgetButton), withText("Budget Control"),
                        childAtPosition(
                                allOf(withId(R.id.ManageExpenses),
                                        childAtPosition(
                                                withId(android.R.id.tabcontent),
                                                1)),
                                9),
                        isDisplayed()));
        appCompatButton9.perform(click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.infoBudgetButton),
                        childAtPosition(
                                allOf(withId(R.id.budgetView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                5),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction appCompatButton1 = onView(
                allOf(withId(android.R.id.button1), withText("Close"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton1.perform(scrollTo(), click());

        onView(withId(R.id.budgetBar)).perform(setProgress(1000));

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

        ViewInteraction appCompatButton18 = onView(
                allOf(withId(R.id.budgetButton), withText("Budget Control"),
                        childAtPosition(
                                allOf(withId(R.id.ManageExpenses),
                                        childAtPosition(
                                                withId(android.R.id.tabcontent),
                                                1)),
                                9),
                        isDisplayed()));
        appCompatButton18.perform(click());

        onView(withId(R.id.budgetBar)).perform(setProgress(1200));

        pressBack();

        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.importButton), withText("Import"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout8),
                                        childAtPosition(
                                                withId(R.id.ManageExpenses),
                                                12)),
                                1),
                        isDisplayed()));
        appCompatButton10.perform(click());


        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.dateFromTo),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout5),
                                        childAtPosition(
                                                withId(R.id.ManageExpenses),
                                                3)),
                                1),
                        isDisplayed()));
        String dateRange = twentyDaysBeforeNow+"/"+monthTwentyDaysBeforeNow+"/"+currentYear+"-"+currentDay+"/"+currentMonth+"/"+currentYear;
        appCompatEditText3.perform(replaceText(dateRange), closeSoftKeyboard());

        ViewInteraction appCompatButton11 = onView(
                allOf(withId(R.id.addExpensesButton), withText("Sum expenses"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout5),
                                        childAtPosition(
                                                withId(R.id.ManageExpenses),
                                                3)),
                                0),
                        isDisplayed()));
        appCompatButton11.perform(click());

        ViewInteraction appCompatButton12 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton12.perform(scrollTo(), click());

        sleep(1000);

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.dateFromTo),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout5),
                                        childAtPosition(
                                                withId(R.id.ManageExpenses),
                                                3)),
                                1),
                        isDisplayed()));
        appCompatEditText7.perform(replaceText(""), closeSoftKeyboard());

        sleep(1000);

        ViewInteraction linearLayout2 = onView(
                allOf(childAtPosition(
                        allOf(withId(android.R.id.tabs),
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0)),
                        1),
                        isDisplayed()));
        linearLayout2.perform(click());

        ViewInteraction appCompatButton13 = onView(
                allOf(withId(R.id.addExpensesButton), withText("Sum expenses"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout5),
                                        childAtPosition(
                                                withId(R.id.ManageExpenses),
                                                3)),
                                0),
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

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.dateFromTo),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout5),
                                        childAtPosition(
                                                withId(R.id.ManageExpenses),
                                                3)),
                                1),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("09"), closeSoftKeyboard());

        ViewInteraction appCompatButton15 = onView(
                allOf(withId(R.id.addExpensesButton), withText("Sum expenses"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout5),
                                        childAtPosition(
                                                withId(R.id.ManageExpenses),
                                                3)),
                                0),
                        isDisplayed()));
        appCompatButton15.perform(click());

        ViewInteraction appCompatButton16 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton16.perform(scrollTo(), click());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.dateFromTo), withText("09"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout5),
                                        childAtPosition(
                                                withId(R.id.ManageExpenses),
                                                3)),
                                1),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText(dateRange));

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.dateFromTo), withText(dateRange),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout5),
                                        childAtPosition(
                                                withId(R.id.ManageExpenses),
                                                3)),
                                1),
                        isDisplayed()));
        appCompatEditText6.perform(closeSoftKeyboard());

        ViewInteraction appCompatSpinner3 = onView(
                allOf(withId(R.id.addExpensesByDescSpinner),
                        childAtPosition(
                                allOf(withId(R.id.ManageExpenses),
                                        childAtPosition(
                                                withId(android.R.id.tabcontent),
                                                1)),
                                2),
                        isDisplayed()));
        appCompatSpinner3.perform(click());

        DataInteraction appCompatCheckedTextView3 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(5);
        appCompatCheckedTextView3.perform(click());

        ViewInteraction appCompatButton17 = onView(
                allOf(withId(R.id.addExpensesButton), withText("Sum expenses"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout5),
                                        childAtPosition(
                                                withId(R.id.ManageExpenses),
                                                3)),
                                0),
                        isDisplayed()));
        appCompatButton17.perform(click());
    }

    public static ViewAction setProgress(final int progress) {
        return new ViewAction() {
            @Override
            public void perform(UiController uiController, View view) {
                //((ImageView) view).setSelectedProgress(progress);
                 ((SeekBar) view).setProgress(progress);
            }

            @Override
            public String getDescription() {
                return "Set a progress";
            }

            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isDisplayed();
            }
        };
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
