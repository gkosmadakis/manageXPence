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

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
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
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.schibsted.spain.barista.interaction.BaristaSleepInteractions.sleep;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityChartExpensesTabTest {

    @Rule
    public ActivityTestRule<SplashScreen> mActivityTestRule = new ActivityTestRule<>(SplashScreen.class);

    @Test
    public void test() {

        sleep(3000);

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.expenseText),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                3)),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("50"), closeSoftKeyboard());

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
                .atPosition(3);
        appCompatCheckedTextView.perform(click());

        ViewInteraction appCompatEditTextFirst = onView(
                allOf(withId(R.id.dateText),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout3),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                5)),
                                1),
                        isDisplayed()));
        appCompatEditTextFirst.perform(replaceText("01/01/2019"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.saveButton), withText("Save To File"),
                        childAtPosition(
                                allOf(withId(R.id.addExpenses),
                                        childAtPosition(
                                                withId(android.R.id.tabcontent),
                                                0)),
                                6),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.dateText),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout3),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                5)),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("01/02/2019"), closeSoftKeyboard());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.saveButton), withText("Save To File"),
                        childAtPosition(
                                allOf(withId(R.id.addExpenses),
                                        childAtPosition(
                                                withId(android.R.id.tabcontent),
                                                0)),
                                6),
                        isDisplayed()));
        appCompatButton3.perform(click());


        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.dateText),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout3),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                5)),
                                1),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("01/03/2019"), closeSoftKeyboard());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.saveButton), withText("Save To File"),
                        childAtPosition(
                                allOf(withId(R.id.addExpenses),
                                        childAtPosition(
                                                withId(android.R.id.tabcontent),
                                                0)),
                                6),
                        isDisplayed()));
        appCompatButton6.perform(click());


        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.dateText),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout3),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                5)),
                                1),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("01/04/2019"), closeSoftKeyboard());

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.saveButton), withText("Save To File"),
                        childAtPosition(
                                allOf(withId(R.id.addExpenses),
                                        childAtPosition(
                                                withId(android.R.id.tabcontent),
                                                0)),
                                6),
                        isDisplayed()));
        appCompatButton9.perform(click());


        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.dateText),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout3),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                5)),
                                1),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("01/05/2019"), closeSoftKeyboard());

        ViewInteraction appCompatButton12 = onView(
                allOf(withId(R.id.saveButton), withText("Save To File"),
                        childAtPosition(
                                allOf(withId(R.id.addExpenses),
                                        childAtPosition(
                                                withId(android.R.id.tabcontent),
                                                0)),
                                6),
                        isDisplayed()));
        appCompatButton12.perform(click());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.dateText),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout3),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                5)),
                                1),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("01/06/2019"), closeSoftKeyboard());

        ViewInteraction appCompatButton15 = onView(
                allOf(withId(R.id.saveButton), withText("Save To File"),
                        childAtPosition(
                                allOf(withId(R.id.addExpenses),
                                        childAtPosition(
                                                withId(android.R.id.tabcontent),
                                                0)),
                                6),
                        isDisplayed()));
        appCompatButton15.perform(click());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.dateText),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout3),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                5)),
                                1),
                        isDisplayed()));
        appCompatEditText7.perform(replaceText("01/07/2019"), closeSoftKeyboard());

        ViewInteraction appCompatButton18 = onView(
                allOf(withId(R.id.saveButton), withText("Save To File"),
                        childAtPosition(
                                allOf(withId(R.id.addExpenses),
                                        childAtPosition(
                                                withId(android.R.id.tabcontent),
                                                0)),
                                6),
                        isDisplayed()));
        appCompatButton18.perform(click());


        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.dateText),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout3),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                5)),
                                1),
                        isDisplayed()));
        appCompatEditText8.perform(replaceText("01/08/2019"), closeSoftKeyboard());

        ViewInteraction appCompatButton21 = onView(
                allOf(withId(R.id.saveButton), withText("Save To File"),
                        childAtPosition(
                                allOf(withId(R.id.addExpenses),
                                        childAtPosition(
                                                withId(android.R.id.tabcontent),
                                                0)),
                                6),
                        isDisplayed()));
        appCompatButton21.perform(click());


        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.dateText),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout3),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                5)),
                                1),
                        isDisplayed()));
        appCompatEditText9.perform(replaceText("01/09/2019"), closeSoftKeyboard());

        ViewInteraction appCompatButton24 = onView(
                allOf(withId(R.id.saveButton), withText("Save To File"),
                        childAtPosition(
                                allOf(withId(R.id.addExpenses),
                                        childAtPosition(
                                                withId(android.R.id.tabcontent),
                                                0)),
                                6),
                        isDisplayed()));
        appCompatButton24.perform(click());


        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.dateText),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout3),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                5)),
                                1),
                        isDisplayed()));
        appCompatEditText10.perform(replaceText("01/10/2019"), closeSoftKeyboard());

        ViewInteraction appCompatButton27 = onView(
                allOf(withId(R.id.saveButton), withText("Save To File"),
                        childAtPosition(
                                allOf(withId(R.id.addExpenses),
                                        childAtPosition(
                                                withId(android.R.id.tabcontent),
                                                0)),
                                6),
                        isDisplayed()));
        appCompatButton27.perform(click());


        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.dateText),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout3),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                5)),
                                1),
                        isDisplayed()));
        appCompatEditText11.perform(replaceText("01/11/2019"), closeSoftKeyboard());

        ViewInteraction appCompatButton30 = onView(
                allOf(withId(R.id.saveButton), withText("Save To File"),
                        childAtPosition(
                                allOf(withId(R.id.addExpenses),
                                        childAtPosition(
                                                withId(android.R.id.tabcontent),
                                                0)),
                                6),
                        isDisplayed()));
        appCompatButton30.perform(click());


        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.dateText),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout3),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                5)),
                                1),
                        isDisplayed()));
        appCompatEditText12.perform(replaceText("01/12/2019"), closeSoftKeyboard());

        ViewInteraction appCompatButton33 = onView(
                allOf(withId(R.id.saveButton), withText("Save To File"),
                        childAtPosition(
                                allOf(withId(R.id.addExpenses),
                                        childAtPosition(
                                                withId(android.R.id.tabcontent),
                                                0)),
                                6),
                        isDisplayed()));
        appCompatButton33.perform(click());

        ViewInteraction appCompatEditText14 = onView(
                allOf(withId(R.id.incomeField),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout4),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                9)),
                                1),
                        isDisplayed()));
        appCompatEditText14.perform(replaceText("10"), closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource
        // /index.html
        sleep(1000);

        ViewInteraction appCompatEditText15 = onView(
                allOf(withId(R.id.incomeField), withText("10"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout4),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                9)),
                                1),
                        isDisplayed()));
        appCompatEditText15.perform(replaceText("100"));

        ViewInteraction appCompatEditText16 = onView(
                allOf(withId(R.id.incomeField), withText("100"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout4),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                9)),
                                1),
                        isDisplayed()));
        appCompatEditText16.perform(closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource
        // /index.html
        sleep(1000);

        ViewInteraction appCompatEditText17 = onView(
                allOf(withId(R.id.incomeField), withText("100"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout4),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                9)),
                                1),
                        isDisplayed()));
        appCompatEditText17.perform(replaceText("1000"));

        ViewInteraction appCompatEditText18 = onView(
                allOf(withId(R.id.incomeField), withText("1000"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout4),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                9)),
                                1),
                        isDisplayed()));
        appCompatEditText18.perform(closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource
        // /index.html
        sleep(1000);

        ViewInteraction appCompatEditText19 = onView(
                allOf(withId(R.id.incomeField), withText("1000"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout4),
                                        childAtPosition(
                                                withId(R.id.addExpenses),
                                                9)),
                                1),
                        isDisplayed()));
        appCompatEditText19.perform(pressImeActionButton());

        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(android.R.id.tabs),
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0)),
                        2),
                        isDisplayed()));
        linearLayout.perform(click());

        ViewInteraction appCompatButton34 = onView(
                allOf(withId(R.id.chartButton), withText("Pie Chart"),
                        childAtPosition(
                                allOf(withId(R.id.ChartExpenses),
                                        childAtPosition(
                                                withId(android.R.id.tabcontent),
                                                2)),
                                2),
                        isDisplayed()));
        appCompatButton34.perform(click());

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        ViewInteraction appCompatTextView3 = onView(
                allOf(withId(R.id.title), withText("Settings"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.view.menu.ListMenuItemView")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView3.perform(click());

        ViewInteraction appCompatImageButton12 = onView(
                allOf(withId(R.id.arrowRight),
                        childAtPosition(
                                allOf(withId(R.id.chartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatImageButton12.perform(click());

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

        ViewInteraction appCompatImageButton13 = onView(
                allOf(withId(R.id.arrowRight),
                        childAtPosition(
                                allOf(withId(R.id.chartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatImageButton13.perform(click());

        ViewInteraction appCompatImageButton14 = onView(
                allOf(withId(R.id.arrowRight),
                        childAtPosition(
                                allOf(withId(R.id.chartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatImageButton14.perform(click());

        ViewInteraction appCompatImageButton15 = onView(
                allOf(withId(R.id.arrowRight),
                        childAtPosition(
                                allOf(withId(R.id.chartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatImageButton15.perform(click());

        ViewInteraction appCompatImageButton16 = onView(
                allOf(withId(R.id.arrowRight),
                        childAtPosition(
                                allOf(withId(R.id.chartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatImageButton16.perform(click());

        ViewInteraction appCompatImageButton17 = onView(
                allOf(withId(R.id.arrowRight),
                        childAtPosition(
                                allOf(withId(R.id.chartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatImageButton17.perform(click());

        ViewInteraction appCompatImageButton18 = onView(
                allOf(withId(R.id.arrowRight),
                        childAtPosition(
                                allOf(withId(R.id.chartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatImageButton18.perform(click());

        ViewInteraction appCompatImageButton19 = onView(
                allOf(withId(R.id.arrowRight),
                        childAtPosition(
                                allOf(withId(R.id.chartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatImageButton19.perform(click());

        ViewInteraction appCompatImageButton20 = onView(
                allOf(withId(R.id.arrowRight),
                        childAtPosition(
                                allOf(withId(R.id.chartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatImageButton20.perform(click());

        ViewInteraction appCompatImageButton21 = onView(
                allOf(withId(R.id.arrowRight),
                        childAtPosition(
                                allOf(withId(R.id.chartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatImageButton21.perform(click());

        ViewInteraction appCompatImageButton22 = onView(
                allOf(withId(R.id.arrowRight),
                        childAtPosition(
                                allOf(withId(R.id.chartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatImageButton22.perform(click());

        ViewInteraction appCompatImageButton23 = onView(
                allOf(withId(R.id.arrowRight),
                        childAtPosition(
                                allOf(withId(R.id.chartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatImageButton23.perform(click());

        ViewInteraction appCompatImageButton24 = onView(
                allOf(withId(R.id.arrowLeft),
                        childAtPosition(
                                allOf(withId(R.id.chartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatImageButton24.perform(click());

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

        ViewInteraction appCompatButton35 = onView(
                allOf(withId(R.id.barChartbutton), withText("Bar Chart"),
                        childAtPosition(
                                allOf(withId(R.id.ChartExpenses),
                                        childAtPosition(
                                                withId(android.R.id.tabcontent),
                                                2)),
                                5),
                        isDisplayed()));
        appCompatButton35.perform(click());

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        ViewInteraction appCompatTextView4 = onView(
                allOf(withId(R.id.title), withText("Settings"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.view.menu.ListMenuItemView")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView4.perform(click());

        ViewInteraction appCompatImageButton25 = onView(
                allOf(withId(R.id.arrowRight),
                        childAtPosition(
                                allOf(withId(R.id.barChartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatImageButton25.perform(click());

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

        ViewInteraction appCompatImageButton26 = onView(
                allOf(withId(R.id.arrowRight),
                        childAtPosition(
                                allOf(withId(R.id.barChartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatImageButton26.perform(click());

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

        ViewInteraction appCompatImageButton27 = onView(
                allOf(withId(R.id.arrowRight),
                        childAtPosition(
                                allOf(withId(R.id.barChartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatImageButton27.perform(click());

        ViewInteraction switch_4 = onView(
                allOf(withId(R.id.switchButton), withText("% or £"),
                        childAtPosition(
                                allOf(withId(R.id.barChartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                5),
                        isDisplayed()));
        switch_4.perform(click());

        ViewInteraction appCompatImageButton28 = onView(
                allOf(withId(R.id.arrowRight),
                        childAtPosition(
                                allOf(withId(R.id.barChartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatImageButton28.perform(click());

        ViewInteraction switch_5 = onView(
                allOf(withId(R.id.switchButton), withText("% or £"),
                        childAtPosition(
                                allOf(withId(R.id.barChartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                5),
                        isDisplayed()));
        switch_5.perform(click());

        ViewInteraction appCompatImageButton29 = onView(
                allOf(withId(R.id.arrowRight),
                        childAtPosition(
                                allOf(withId(R.id.barChartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatImageButton29.perform(click());

        ViewInteraction switch_6 = onView(
                allOf(withId(R.id.switchButton), withText("% or £"),
                        childAtPosition(
                                allOf(withId(R.id.barChartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                5),
                        isDisplayed()));
        switch_6.perform(click());

        ViewInteraction appCompatImageButton30 = onView(
                allOf(withId(R.id.arrowRight),
                        childAtPosition(
                                allOf(withId(R.id.barChartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatImageButton30.perform(click());

        ViewInteraction switch_7 = onView(
                allOf(withId(R.id.switchButton), withText("% or £"),
                        childAtPosition(
                                allOf(withId(R.id.barChartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                5),
                        isDisplayed()));
        switch_7.perform(click());

        ViewInteraction appCompatImageButton31 = onView(
                allOf(withId(R.id.arrowRight),
                        childAtPosition(
                                allOf(withId(R.id.barChartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatImageButton31.perform(click());

        ViewInteraction switch_8 = onView(
                allOf(withId(R.id.switchButton), withText("% or £"),
                        childAtPosition(
                                allOf(withId(R.id.barChartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                5),
                        isDisplayed()));
        switch_8.perform(click());

        ViewInteraction appCompatImageButton32 = onView(
                allOf(withId(R.id.arrowRight),
                        childAtPosition(
                                allOf(withId(R.id.barChartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatImageButton32.perform(click());

        ViewInteraction switch_9 = onView(
                allOf(withId(R.id.switchButton), withText("% or £"),
                        childAtPosition(
                                allOf(withId(R.id.barChartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                5),
                        isDisplayed()));
        switch_9.perform(click());

        ViewInteraction appCompatImageButton33 = onView(
                allOf(withId(R.id.arrowRight),
                        childAtPosition(
                                allOf(withId(R.id.barChartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatImageButton33.perform(click());

        ViewInteraction switch_10 = onView(
                allOf(withId(R.id.switchButton), withText("% or £"),
                        childAtPosition(
                                allOf(withId(R.id.barChartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                5),
                        isDisplayed()));
        switch_10.perform(click());

        ViewInteraction appCompatImageButton34 = onView(
                allOf(withId(R.id.arrowRight),
                        childAtPosition(
                                allOf(withId(R.id.barChartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatImageButton34.perform(click());

        ViewInteraction switch_11 = onView(
                allOf(withId(R.id.switchButton), withText("% or £"),
                        childAtPosition(
                                allOf(withId(R.id.barChartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                5),
                        isDisplayed()));
        switch_11.perform(click());

        ViewInteraction appCompatImageButton35 = onView(
                allOf(withId(R.id.arrowRight),
                        childAtPosition(
                                allOf(withId(R.id.barChartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatImageButton35.perform(click());

        ViewInteraction appCompatImageButton36 = onView(
                allOf(withId(R.id.arrowLeft),
                        childAtPosition(
                                allOf(withId(R.id.barChartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatImageButton36.perform(click());

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

        ViewInteraction appCompatButton36 = onView(
                allOf(withId(R.id.annualChartButton), withText("Annual Chart"),
                        childAtPosition(
                                allOf(withId(R.id.ChartExpenses),
                                        childAtPosition(
                                                withId(android.R.id.tabcontent),
                                                2)),
                                8),
                        isDisplayed()));
        appCompatButton36.perform(click());

        ViewInteraction appCompatImageButton37 = onView(
                allOf(withId(R.id.leftYearButton), withContentDescription("Left Year Button"),
                        childAtPosition(
                                allOf(withId(R.id.annualChartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton37.perform(click());

        ViewInteraction appCompatImageButton38 = onView(
                allOf(withId(R.id.rigthYearButton), withContentDescription("Right Year Button"),
                        childAtPosition(
                                allOf(withId(R.id.annualChartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatImageButton38.perform(click());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.circleTextview), withText("Set Income Circle"),
                        childAtPosition(
                                allOf(withId(R.id.annualChartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatButton37 = onView(
                allOf(withId(android.R.id.button1), withText("SET"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton37.perform(scrollTo(), click());

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

        ViewInteraction appCompatButton40 = onView(
                allOf(withId(R.id.annualChartButton), withText("Annual Chart"),
                        childAtPosition(
                                allOf(withId(R.id.ChartExpenses),
                                        childAtPosition(
                                                withId(android.R.id.tabcontent),
                                                2)),
                                8),
                        isDisplayed()));
        appCompatButton40.perform(click());

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        ViewInteraction appCompatTextView5 = onView(
                allOf(withId(R.id.title), withText("Settings"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.view.menu.ListMenuItemView")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView5.perform(click());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(R.id.circleTextview), withText("Set Income Circle"),
                        childAtPosition(
                                allOf(withId(R.id.annualChartView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatTextView2.perform(click());

        ViewInteraction appCompatButton38 = onView(
                allOf(withId(android.R.id.button3), withText("Reset"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                0)));
        appCompatButton38.perform(scrollTo(), click());

        pressBack();

        ViewInteraction appCompatButton39 = onView(
                allOf(withId(R.id.annualSavingsButton), withText("Savings Chart"),
                        childAtPosition(
                                allOf(withId(R.id.ChartExpenses),
                                        childAtPosition(
                                                withId(android.R.id.tabcontent),
                                                2)),
                                11),
                        isDisplayed()));
        appCompatButton39.perform(click());

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        ViewInteraction appCompatTextView6 = onView(
                allOf(withId(R.id.title), withText("Settings"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.view.menu.ListMenuItemView")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView6.perform(click());

        ViewInteraction appCompatImageButton39 = onView(
                allOf(withId(R.id.leftYearButton), withContentDescription("Left Year Button"),
                        childAtPosition(
                                allOf(withId(R.id.annualSavingsView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatImageButton39.perform(click());

        ViewInteraction appCompatImageButton40 = onView(
                allOf(withId(R.id.rigthYearButton), withContentDescription("Right Year Button"),
                        childAtPosition(
                                allOf(withId(R.id.annualSavingsView),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatImageButton40.perform(click());

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
