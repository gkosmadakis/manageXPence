package uk.co.irokottaki.moneycontrol;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

import uk.co.irokottaki.moneycontrol.activity.MainActivity;
import uk.co.irokottaki.moneycontrol.model.AnyYear;
import uk.co.irokottaki.moneycontrol.model.YearToSet;
import uk.co.irokottaki.moneycontrol.utils.MainActivityUtil;
import uk.co.irokottaki.moneycontrol.utils.NothingSelectedSpinnerAdapter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.co.irokottaki.moneycontrol.utils.Constants.COUNCIL_TAX;
import static uk.co.irokottaki.moneycontrol.utils.Constants.ENTERTAINMENT;
import static uk.co.irokottaki.moneycontrol.utils.Constants.HOUSE_BILLS;
import static uk.co.irokottaki.moneycontrol.utils.Constants.HOUSE_RENT;
import static uk.co.irokottaki.moneycontrol.utils.Constants.MORTGAGE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.SHOPPING;
import static uk.co.irokottaki.moneycontrol.utils.Constants.SUPERMARKET;
import static uk.co.irokottaki.moneycontrol.utils.Constants.TRAVEL;

@RunWith(MockitoJUnitRunner.class)
public class MainActivityTests  {
    private static final double DELTA = 1e-15;

    @Test
    public void testProcessBalance(){

        final SharedPreferences sharedPrefs = mock(SharedPreferences.class);
        final Context context = mock(Context.class);
        when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
        final SharedPreferences.Editor editor = mock(SharedPreferences.Editor.class);
        when(sharedPrefs.edit()).thenReturn(editor);
        final EditText incomeField = mock(EditText.class);
        final Activity mMockMainActivity = mock(MainActivity.class);

        MainActivityUtil mainUtil = new MainActivityUtil(context);
        when(mMockMainActivity.findViewById(R.id.incomeField)).thenReturn(incomeField);
        when(incomeField.getText()).thenReturn(new MockEditable("1000"));

        HashMap<String, AnyYear> yearsMappedToObjectYearsMap = new HashMap<>();
        AnyYear objectYear = setUpObjectYear();
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        yearsMappedToObjectYearsMap.put(String.valueOf(year), objectYear);

        boolean isPaymentCircleSet = false;
        assertEquals(120, mainUtil.processBalance(incomeField,yearsMappedToObjectYearsMap,isPaymentCircleSet, "January", 2019),DELTA);
        assertEquals(120, mainUtil.processBalance(incomeField,yearsMappedToObjectYearsMap,isPaymentCircleSet, "February", 2019),DELTA);
        assertEquals(120, mainUtil.processBalance(incomeField,yearsMappedToObjectYearsMap,isPaymentCircleSet, "March", 2019),DELTA);
        assertEquals(120, mainUtil.processBalance(incomeField,yearsMappedToObjectYearsMap,isPaymentCircleSet, "April", 2019),DELTA);
        assertEquals(120, mainUtil.processBalance(incomeField,yearsMappedToObjectYearsMap,isPaymentCircleSet, "May", 2019),DELTA);
        assertEquals(120, mainUtil.processBalance(incomeField,yearsMappedToObjectYearsMap,isPaymentCircleSet, "June", 2019),DELTA);
        assertEquals(120, mainUtil.processBalance(incomeField,yearsMappedToObjectYearsMap,isPaymentCircleSet, "July", 2019),DELTA);
        assertEquals(120, mainUtil.processBalance(incomeField,yearsMappedToObjectYearsMap,isPaymentCircleSet, "August", 2019),DELTA);
        assertEquals(120, mainUtil.processBalance(incomeField,yearsMappedToObjectYearsMap,isPaymentCircleSet, "September", 2019),DELTA);
        assertEquals(120, mainUtil.processBalance(incomeField,yearsMappedToObjectYearsMap,isPaymentCircleSet, "October", 2019),DELTA);
        assertEquals(120, mainUtil.processBalance(incomeField,yearsMappedToObjectYearsMap,isPaymentCircleSet, "November", 2019),DELTA);
        assertEquals(120, mainUtil.processBalance(incomeField,yearsMappedToObjectYearsMap,isPaymentCircleSet, "December", 2019),DELTA);

        isPaymentCircleSet = true;
        assertEquals(120, mainUtil.processBalance(incomeField,yearsMappedToObjectYearsMap,isPaymentCircleSet, "January", 2019),DELTA);
        assertEquals(120, mainUtil.processBalance(incomeField,yearsMappedToObjectYearsMap,isPaymentCircleSet, "February", 2019),DELTA);
        assertEquals(120, mainUtil.processBalance(incomeField,yearsMappedToObjectYearsMap,isPaymentCircleSet, "March", 2019),DELTA);
        assertEquals(120, mainUtil.processBalance(incomeField,yearsMappedToObjectYearsMap,isPaymentCircleSet, "April", 2019),DELTA);
        assertEquals(120, mainUtil.processBalance(incomeField,yearsMappedToObjectYearsMap,isPaymentCircleSet, "May", 2019),DELTA);
        assertEquals(120, mainUtil.processBalance(incomeField,yearsMappedToObjectYearsMap,isPaymentCircleSet, "June", 2019),DELTA);
        assertEquals(120, mainUtil.processBalance(incomeField,yearsMappedToObjectYearsMap,isPaymentCircleSet, "July", 2019),DELTA);
        assertEquals(120, mainUtil.processBalance(incomeField,yearsMappedToObjectYearsMap,isPaymentCircleSet, "August", 2019),DELTA);
        assertEquals(120, mainUtil.processBalance(incomeField,yearsMappedToObjectYearsMap,isPaymentCircleSet, "September", 2019),DELTA);
        assertEquals(120, mainUtil.processBalance(incomeField,yearsMappedToObjectYearsMap,isPaymentCircleSet, "October", 2019),DELTA);
        assertEquals(120, mainUtil.processBalance(incomeField,yearsMappedToObjectYearsMap,isPaymentCircleSet, "November", 2019),DELTA);
        assertEquals(120, mainUtil.processBalance(incomeField,yearsMappedToObjectYearsMap,isPaymentCircleSet, "December", 2019),DELTA);

    }

    @Test
    public void testCheckBudgetWarning(){

        final SharedPreferences sharedPrefs = mock(SharedPreferences.class);
        final Context context = mock(Context.class);
        when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
        when(sharedPrefs.getInt(String.valueOf(anyInt()),anyInt())).thenReturn(1000);

        AnyYear objectYear = setUpObjectYear();

        final Calendar calendar = Calendar.getInstance();//this gets the current month
        int year = calendar.get(Calendar.YEAR);

        HashMap<String, AnyYear> yearsMappedToObjectYearsMap = new HashMap<>();
        yearsMappedToObjectYearsMap.put(String.valueOf(year), objectYear);

        MainActivityUtil mainUtil = new MainActivityUtil(context);
        assertEquals(0.88, mainUtil.checkBudgetWarning(yearsMappedToObjectYearsMap, "January", 2019), DELTA);

        assertEquals(0.88, mainUtil.checkBudgetWarning(yearsMappedToObjectYearsMap, "February", 2019), DELTA);

        assertEquals(0.88, mainUtil.checkBudgetWarning(yearsMappedToObjectYearsMap, "March", 2019), DELTA);

        assertEquals(0.88, mainUtil.checkBudgetWarning(yearsMappedToObjectYearsMap, "April", 2019), DELTA);

        assertEquals(0.88, mainUtil.checkBudgetWarning(yearsMappedToObjectYearsMap, "May", 2019), DELTA);

        assertEquals(0.88, mainUtil.checkBudgetWarning(yearsMappedToObjectYearsMap, "June", 2019), DELTA);

        assertEquals(0.88, mainUtil.checkBudgetWarning(yearsMappedToObjectYearsMap, "July", 2019), DELTA);

        assertEquals(0.88, mainUtil.checkBudgetWarning(yearsMappedToObjectYearsMap, "August", 2019), DELTA);

        assertEquals(0.88, mainUtil.checkBudgetWarning(yearsMappedToObjectYearsMap, "September", 2019), DELTA);

        assertEquals(0.88, mainUtil.checkBudgetWarning(yearsMappedToObjectYearsMap, "October", 2019), DELTA);

        assertEquals(0.88, mainUtil.checkBudgetWarning(yearsMappedToObjectYearsMap, "November", 2019), DELTA);

        assertEquals(0.88, mainUtil.checkBudgetWarning(yearsMappedToObjectYearsMap, "December", 2019), DELTA);
    }

    private AnyYear setUpObjectYear (){

        YearToSet yearToSet = new YearToSet(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        AnyYear objectYear = new AnyYear(yearToSet);

        Float totalExpensesForMonth = 880f;
        LinkedHashSet set = new LinkedHashSet();
        set.add("House Bills");
        set.add("SuperMarket");
        set.add("Shopping");
        ArrayList<Float> amounts = new ArrayList();
        amounts.add(300f);
        amounts.add(400f);
        amounts.add(180f);
        String fileLine = "300 House Bills 20/01/2019 \n 400 SuperMarket 21/01/2019 \n 180 Shopping 22/01/2019";
        objectYear.setAmountAndDescJan(totalExpensesForMonth, set, amounts, fileLine);
        objectYear.setAmountAndDescFeb(totalExpensesForMonth, set, amounts, fileLine);
        objectYear.setAmountAndDescMar(totalExpensesForMonth, set, amounts, fileLine);
        objectYear.setAmountAndDescApr(totalExpensesForMonth, set, amounts, fileLine);
        objectYear.setAmountAndDescMay(totalExpensesForMonth, set, amounts, fileLine);
        objectYear.setAmountAndDescJun(totalExpensesForMonth, set, amounts, fileLine);
        objectYear.setAmountAndDescJul(totalExpensesForMonth, set, amounts, fileLine);
        objectYear.setAmountAndDescAug(totalExpensesForMonth, set, amounts, fileLine);
        objectYear.setAmountAndDescSep(totalExpensesForMonth, set, amounts, fileLine);
        objectYear.setAmountAndDescOct(totalExpensesForMonth, set, amounts, fileLine);
        objectYear.setAmountAndDescNov(totalExpensesForMonth, set, amounts, fileLine);
        objectYear.setAmountAndDescDec(totalExpensesForMonth, set, amounts, fileLine);

        return objectYear;
    }

    @Test
    public void testHasPermissions() {
        Context context = mock(Context.class);
        ContextCompat.checkSelfPermission(context, "android.permission.WRITE_EXTERNAL_STORAGE677");

        when(context.checkPermission(eq("android.permission.WRITE_EXTERNAL_STORAGE677"),anyInt(),anyInt())).thenReturn(
                PackageManager.PERMISSION_DENIED);

        try {
            setFinalStatic(Build.VERSION.class.getField("SDK_INT"), 25);
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean hasPermissions = MainActivity.hasPermissions(context, "android.permission.WRITE_EXTERNAL_STORAGE677");
        assertFalse(hasPermissions);

        ContextCompat.checkSelfPermission(context, "android.permission.WRITE_EXTERNAL_STORAGE");

        when(context.checkPermission(eq("android.permission.WRITE_EXTERNAL_STORAGE"),anyInt(),anyInt())).thenReturn(
                PackageManager.PERMISSION_GRANTED);

        hasPermissions = MainActivity.hasPermissions(context, "android.permission.WRITE_EXTERNAL_STORAGE");
        assertTrue(hasPermissions);

    }

    static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue);
    }

    @Test
    public void testTokenExists(){

        final SharedPreferences sharedPrefs = mock(SharedPreferences.class);
        final Context context = mock(Context.class);
        when(context.getSharedPreferences(anyString(),anyInt())).thenReturn(sharedPrefs);
        when(sharedPrefs.getString(anyString(),anyString())).thenReturn("com.example.valdio.dropboxintegration");
        MainActivityUtil mainActivityUtil = new MainActivityUtil(context);

        boolean tokenExists = mainActivityUtil.tokenExists();
        assertTrue(tokenExists);

    }

    @Test
    public void testRetrieveAccessToken(){
        /*First scenario*/
        final SharedPreferences sharedPrefs = mock(SharedPreferences.class);
        final Context context = mock(Context.class);
        when(context.getSharedPreferences(anyString(),anyInt())).thenReturn(sharedPrefs);
        when(sharedPrefs.getString(anyString(),anyString())).thenReturn("com.example.valdio.dropboxintegration");
        MainActivityUtil mainActivityUtil = new MainActivityUtil(context);

        String token = mainActivityUtil.retrieveAccessToken();
        assertEquals("com.example.valdio.dropboxintegration", token);

        /*Second scenario*/
        when(sharedPrefs.getString(anyString(),anyString())).thenReturn(null);
        token = mainActivityUtil.retrieveAccessToken();
        assertNull(token);

    }

    @Test
    public void testFindTheDateFormat(){

        String dateToRequest = "20190130";
        assertEquals("yyyyMMdd", MainActivityUtil.findTheDateFormat(dateToRequest));

        dateToRequest = "30-01-2019";
        assertEquals("dd-MM-yyyy", MainActivityUtil.findTheDateFormat(dateToRequest));

        dateToRequest = "2019-01-30";
        assertEquals("yyyy-MM-dd", MainActivityUtil.findTheDateFormat(dateToRequest));

        dateToRequest = "01/30/2019";
        assertEquals("MM/dd/yyyy", MainActivityUtil.findTheDateFormat(dateToRequest));

        dateToRequest = "30/01/19";
        assertEquals("dd/MM/yy", MainActivityUtil.findTheDateFormat(dateToRequest));

        dateToRequest = "2019/01/30";
        assertEquals("yyyy/MM/dd", MainActivityUtil.findTheDateFormat(dateToRequest));

        dateToRequest = "30 Jan 2019";
        assertEquals("dd MMM yyyy", MainActivityUtil.findTheDateFormat(dateToRequest));

        dateToRequest = "30 January 2019";
        assertEquals("dd MMMM yyyy", MainActivityUtil.findTheDateFormat(dateToRequest));

        dateToRequest = "201901301200";
        assertEquals("yyyyMMddHHmm", MainActivityUtil.findTheDateFormat(dateToRequest));

        dateToRequest = "20190130 1200";
        assertEquals("yyyyMMdd HHmm", MainActivityUtil.findTheDateFormat(dateToRequest));

        dateToRequest = "30-01-2019 12:00";
        assertEquals("dd-MM-yyyy HH:mm", MainActivityUtil.findTheDateFormat(dateToRequest));

        dateToRequest = "2019-01-30 12:00";
        assertEquals("yyyy-MM-dd HH:mm", MainActivityUtil.findTheDateFormat(dateToRequest));

        dateToRequest = "30/01/2019 12:00";
        assertEquals("MM/dd/yyyy HH:mm", MainActivityUtil.findTheDateFormat(dateToRequest));

        dateToRequest = "2019/01/30 12:00";
        assertEquals("yyyy/MM/dd HH:mm", MainActivityUtil.findTheDateFormat(dateToRequest));

        dateToRequest = "30 Jan 2019 12:00";
        assertEquals("dd MMM yyyy HH:mm", MainActivityUtil.findTheDateFormat(dateToRequest));

        dateToRequest = "30 January 2019 12:00";
        assertEquals("dd MMMM yyyy HH:mm", MainActivityUtil.findTheDateFormat(dateToRequest));

        dateToRequest = "20190130120000";
        assertEquals("yyyyMMddHHmmss", MainActivityUtil.findTheDateFormat(dateToRequest));

        dateToRequest = "20190130 120000";
        assertEquals("yyyyMMdd HHmmss", MainActivityUtil.findTheDateFormat(dateToRequest));

        dateToRequest = "30-01-2019 12:00:00";
        assertEquals("dd-MM-yyyy HH:mm:ss", MainActivityUtil.findTheDateFormat(dateToRequest));

        dateToRequest = "2019-01-30 12:00:00";
        assertEquals("yyyy-MM-dd HH:mm:ss", MainActivityUtil.findTheDateFormat(dateToRequest));

        dateToRequest = "01/30/2019 12:00:00";
        assertEquals("MM/dd/yyyy HH:mm:ss", MainActivityUtil.findTheDateFormat(dateToRequest));

        dateToRequest = "2019/01/30 12:00:00";
        assertEquals("yyyy/MM/dd HH:mm:ss", MainActivityUtil.findTheDateFormat(dateToRequest));

        dateToRequest = "30 Jan 2019 12:00:00";
        assertEquals("dd MMM yyyy HH:mm:ss", MainActivityUtil.findTheDateFormat(dateToRequest));

        dateToRequest = "30 January 2019 12:00:00";
        assertEquals("dd MMMM yyyy HH:mm:ss", MainActivityUtil.findTheDateFormat(dateToRequest));
    }

    @Test
    public void testSetExpenseFieldsFromCameraExtractedText(){

        final Context context = mock(Context.class);
        final Activity mMockMainActivity = Mockito.mock(MainActivity.class);
        MainActivityUtil util = new MainActivityUtil(context);
        String testString = "Date: 06/02/2019 Tesco Amount 30";
        util.setExpenseFieldsFromCameraExtractedText(testString, mMockMainActivity);
        assertTrue(util.isExpenseFound());
        assertTrue(util.isDescFound());
        assertTrue(util.isDateFound());

        testString = "Date: 06/02/2019 Table Amount 30";
        util.setExpenseFieldsFromCameraExtractedText(testString, mMockMainActivity);
        assertTrue(util.isExpenseFound());
        assertTrue(util.isDescFound());
        assertTrue(util.isDateFound());

        testString = "Date: 06/02/2019 Jacket Amount 30";
        util.setExpenseFieldsFromCameraExtractedText(testString, mMockMainActivity);
        assertTrue(util.isExpenseFound());
        assertTrue(util.isDescFound());
        assertTrue(util.isDateFound());

        testString = "Date: 06/02/2019 Petrol Amount 30";
        util.setExpenseFieldsFromCameraExtractedText(testString, mMockMainActivity);
        assertTrue(util.isExpenseFound());
        assertTrue(util.isDescFound());
        assertTrue(util.isDateFound());
    }

    @Test
    public void testProcessExpenseRepeatingAmount() throws ParseException {
        final Context context = mock(Context.class);
        String testAmount = "50";
        String testDescription = "SuperMarket";

        final Activity mMockMainActivity = mock(MainActivity.class);
        final CheckBox repeatCheckBox = mock(CheckBox.class);
        when(mMockMainActivity.findViewById(R.id.repeatCheckBox)).thenReturn(repeatCheckBox);
        when(repeatCheckBox.isChecked()).thenReturn(true);
        ArrayList datesTheRepeatingExpenseOccurs = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString1 = "25/03/2019";
        String dateString2 = "25/04/2019";
        Date date1 = sdf.parse(dateString1);
        Date date2 = sdf.parse(dateString2);
        datesTheRepeatingExpenseOccurs.add(date1);
        datesTheRepeatingExpenseOccurs.add(date2);

        MainActivityUtil util = new MainActivityUtil(context);
        assertTrue(util.processExpenseRepeatingAmount(testAmount,testDescription,repeatCheckBox,datesTheRepeatingExpenseOccurs,mMockMainActivity));

    }

    @Test
    public void testPopulateDatesTheRepeatingExpenseOccursList(){

        final Context context = mock(Context.class);
        MainActivityUtil util = new MainActivityUtil(context);
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        /* the case when the repeating expense is ending the current year*/
        ArrayList datesTheRepeatingExpenseOccurs = (ArrayList) util.populateDatesTheRepeatingExpenseOccursList(currentYear, currentMonth+4, currentDay, currentYear, currentMonth);

        SimpleDateFormat formattter = new SimpleDateFormat("dd/MM/yyyy");

        calendar.set(currentYear, currentMonth+1, currentDay);
        Date candidateDate = calendar.getTime();

        assertTrue(formattter.format(datesTheRepeatingExpenseOccurs.get(0)).equals(formattter.format(candidateDate)));

        calendar.set(currentYear, currentMonth+2, currentDay);
        candidateDate = calendar.getTime();
        assertTrue(formattter.format(datesTheRepeatingExpenseOccurs.get(1)).equals(formattter.format(candidateDate)));

        calendar.set(currentYear, currentMonth+3, currentDay);
        candidateDate = calendar.getTime();
        assertTrue(formattter.format(datesTheRepeatingExpenseOccurs.get(2)).equals(formattter.format(candidateDate)));

        calendar.set(currentYear, currentMonth+4, currentDay);
        candidateDate = calendar.getTime();
        assertTrue(formattter.format(datesTheRepeatingExpenseOccurs.get(3)).equals(formattter.format(candidateDate)));

        /* the case when the repeating expense is ending the next year*/
        datesTheRepeatingExpenseOccurs = (ArrayList) util.populateDatesTheRepeatingExpenseOccursList(currentYear+1, currentMonth+4, currentDay, currentYear, currentMonth);

        assertTrue(datesTheRepeatingExpenseOccurs.size() == 16);

        calendar.set(currentYear+1, currentMonth+4, currentDay);
        assertTrue(formattter.format(datesTheRepeatingExpenseOccurs.get(15)).equals(formattter.format(calendar.getTime())));

        calendar.set(currentYear, currentMonth+1, currentDay);
        assertTrue(formattter.format(datesTheRepeatingExpenseOccurs.get(0)).equals(formattter.format(calendar.getTime())));

    }

    @Test
    public void testGetDaysBetweenDates() {
        final Context context = mock(Context.class);
        MainActivityUtil util = new MainActivityUtil(context);
        final Activity mMockMainActivity = mock(MainActivity.class);
        String testDatesFromTo = "01/02/2019-20/02/2019";

        List testReturnedList = util.getDaysBetweenDates(mMockMainActivity, testDatesFromTo);
        assertTrue(testReturnedList.size() == 20);

    }

}
