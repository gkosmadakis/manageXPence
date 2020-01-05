package uk.co.irokottaki.moneycontrol.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import uk.co.irokottaki.moneycontrol.R;
import uk.co.irokottaki.moneycontrol.activity.AnnualChartActivity;
import uk.co.irokottaki.moneycontrol.activity.AnnualSavingsActivity;
import uk.co.irokottaki.moneycontrol.activity.CalculateAnnualExpensesActivity;
import uk.co.irokottaki.moneycontrol.activity.ChartActivity;
import uk.co.irokottaki.moneycontrol.activity.HorizontalBarChartActivity;
import uk.co.irokottaki.moneycontrol.activity.ReportActivity;
import uk.co.irokottaki.moneycontrol.model.AnyYear;
import uk.co.irokottaki.moneycontrol.model.YearToSet;

import static uk.co.irokottaki.moneycontrol.utils.Constants.AMOUNT;
import static uk.co.irokottaki.moneycontrol.utils.Constants.ANNUAL_EXPENSES;
import static uk.co.irokottaki.moneycontrol.utils.Constants.APRIL;
import static uk.co.irokottaki.moneycontrol.utils.Constants.AUGUST;
import static uk.co.irokottaki.moneycontrol.utils.Constants.DATE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.DECEMBER;
import static uk.co.irokottaki.moneycontrol.utils.Constants.DESCRIPTION;
import static uk.co.irokottaki.moneycontrol.utils.Constants.EIGHT;
import static uk.co.irokottaki.moneycontrol.utils.Constants.ELEVEN;
import static uk.co.irokottaki.moneycontrol.utils.Constants.EXPENSE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.EXPENSES_FILE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.EXPENSES_FOR;
import static uk.co.irokottaki.moneycontrol.utils.Constants.FEBRUARY;
import static uk.co.irokottaki.moneycontrol.utils.Constants.FIVE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.FOUR;
import static uk.co.irokottaki.moneycontrol.utils.Constants.INCOME;
import static uk.co.irokottaki.moneycontrol.utils.Constants.JANUARY;
import static uk.co.irokottaki.moneycontrol.utils.Constants.JULY;
import static uk.co.irokottaki.moneycontrol.utils.Constants.JUNE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.MARCH;
import static uk.co.irokottaki.moneycontrol.utils.Constants.MAY;
import static uk.co.irokottaki.moneycontrol.utils.Constants.NINE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.NOVEMBER;
import static uk.co.irokottaki.moneycontrol.utils.Constants.OCTOBER;
import static uk.co.irokottaki.moneycontrol.utils.Constants.OK;
import static uk.co.irokottaki.moneycontrol.utils.Constants.ONE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.PREFERENCES;
import static uk.co.irokottaki.moneycontrol.utils.Constants.SEPTEMBER;
import static uk.co.irokottaki.moneycontrol.utils.Constants.SEVEN;
import static uk.co.irokottaki.moneycontrol.utils.Constants.SIX;
import static uk.co.irokottaki.moneycontrol.utils.Constants.TEN;
import static uk.co.irokottaki.moneycontrol.utils.Constants.THREE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.TWELVE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.TWO;
import static uk.co.irokottaki.moneycontrol.utils.Constants.YEAR;

public class ChartsUtil {

    private YearToSet yearToSet;
    private AnyYear objectYear;
    private Context context;
    /*This map has all the data needed from the file. The first  key String on the map is the year, the second map contains the months as key,
    * and another map which contains a set with the descriptions and an List with the expense amounts. An example could be
    * 2018=> 11=> Key: Supermarket-> 25,20,11
				  Key: Shopping-> 10,20
				  Key: House Rent-> 265
	  20=> 05=> Key: Travel-> 250,200
	              Key: Mortgage-> 400
	              Key: Shopping-> 35, 55,100 */
    private HashMap<String, TreeMap<String, LinkedHashMap<String, List<Float>>>> yearsMappedToMonthsWithAmountsMap;
    private TreeMap<Integer, Map<Integer, String>> yearsMappedToMonthsWithFileLines;
    private StringBuilder allLinesInFile;
    private HashMap<String, AnyYear> yearsMappedToObjectYearsMap;

    public ChartsUtil(Context context) {

        this.context = context;
    }

    @TargetApi(19)
    public Map<String,AnyYear> readTheFile() {

        Float amount;
        String desc;
        String date;

        objectYear = new AnyYear(yearToSet);
        yearToSet = new YearToSet(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        yearsMappedToMonthsWithAmountsMap = new HashMap<>();

        try (Scanner in = new Scanner(context.openFileInput(EXPENSES_FILE));){

            int lineIndex = 0;//this is to count the lines

            yearsMappedToMonthsWithFileLines = new TreeMap<>();

            while (in.hasNextLine()) {

                String line = in.nextLine();
                if (++lineIndex > 2 && !line.equals("")) {
                    int index = line.lastIndexOf(' ');
                    amount = Float.valueOf(line.substring(0, line.indexOf(' ')));
                    desc = line.substring(line.indexOf(' '), index).trim();
                    date = line.substring(index, line.length());
                    String extractMonthFromDate = date.substring(date.indexOf('/') + 1, date
                            .lastIndexOf('/'));
                    if (extractMonthFromDate.startsWith("0")) {
                        extractMonthFromDate = extractMonthFromDate.replace("0", "");
                    }
                    String extractYearFromDate = date.substring(date.lastIndexOf('/') + 1, date
                            .length());

                    populateMonthsWithAmountsMap(amount, desc, extractMonthFromDate, extractYearFromDate);

                    populateMonthsWithFileLinesMap(line, extractMonthFromDate, extractYearFromDate);

                }
            }
        } catch (FileNotFoundException e) {
            Log.e("File not found ", e.getMessage());
        }

        /*Call the method to iterate the map*/
        yearsMappedToObjectYearsMap = new HashMap<>();
        yearsMappedToObjectYearsMap = iterateMainMap();

        return yearsMappedToObjectYearsMap;
    }

    private void populateMonthsWithFileLinesMap(String line, String extractMonthFromDate, String extractYearFromDate) {

        TreeMap tempFileLinesMap;
        StringBuilder fileLines;/* Similarly for the map to store years with months and fileLines that are used in ReportActivity */
        if (yearsMappedToMonthsWithFileLines.containsKey(Integer.parseInt(extractYearFromDate))) {

                tempFileLinesMap = (TreeMap) yearsMappedToMonthsWithFileLines.get(Integer.parseInt(extractYearFromDate));
                if (tempFileLinesMap.get(Integer.parseInt(extractMonthFromDate)) != null) {
                    StringBuilder linesFound = new StringBuilder((String)tempFileLinesMap.get(Integer.parseInt(extractMonthFromDate)));
                    linesFound.append(line + "\n");
                    fileLines = linesFound;
                }
                else {
                    fileLines = new StringBuilder();
                    fileLines.append(line + "\n");
                }
        }
        else {
            tempFileLinesMap = new TreeMap<String, String>();
            fileLines = new StringBuilder();
            fileLines.append(line + "\n");
        }
        tempFileLinesMap.put(Integer.parseInt(extractMonthFromDate), fileLines.toString());
        yearsMappedToMonthsWithFileLines.put(Integer.parseInt(extractYearFromDate), tempFileLinesMap);
    }

    private void populateMonthsWithAmountsMap(Float amount, String desc, String extractMonthFromDate, String extractYearFromDate) {

        TreeMap tempFirstMap;
        LinkedHashMap tempSecondMap;
        List tempList;
        Set<String> descriptionsSet;
        /*Here I store the data to the map, key is the year, value is another map, the tempMap with key as the month
         * and value an List with the amounts of this month*/
        if (yearsMappedToMonthsWithAmountsMap.containsKey(extractYearFromDate)) {
            /*if the key = year is in the map then get the tempFirstMap*/
            tempFirstMap = yearsMappedToMonthsWithAmountsMap.get(extractYearFromDate);

            /*if the key = month and the
            tempSecondMap has the current description then get the tempList create a new set and call the method to add amounts that are
            with the same description. if the tempSecondMap does not have the description then create a new tempList*/
            if (tempFirstMap.containsKey(Integer.parseInt(extractMonthFromDate))) {
                tempSecondMap = (LinkedHashMap) tempFirstMap.get(Integer.parseInt(extractMonthFromDate));

                if(tempSecondMap.get(desc) != null) {
                    tempList = (List) tempSecondMap.get(desc);//tempList size is always 1
                    descriptionsSet = new LinkedHashSet<>();
                    descriptionsSet.add(desc);//descriptionsSet size is always 1
                    addAmountsWithDuplicates(descriptionsSet,desc, String.valueOf(amount), tempList);
                }
                else {
                    tempList = new ArrayList();
                    tempList.add(amount);
                }
            }
            /* if the key = month is not in the map at all then get the tempFirstMap, create a new tempSecondMap, a new set and a new list.
             * Add the amount to the list, the desc in the set */
            else {
                tempFirstMap = yearsMappedToMonthsWithAmountsMap.get(extractYearFromDate);
                tempSecondMap = new LinkedHashMap();
                descriptionsSet = new LinkedHashSet<>();
                tempList = new ArrayList();

                tempList.add(amount);
                descriptionsSet.add(desc);
            }
        }
        /*if the key = year is not in the map then create new tempFirstMap, a new tempSecondMap, a new set and a new tempList
        add the amount in the tempList the description in the set */
        else {
            tempFirstMap = new TreeMap();
            tempSecondMap = new LinkedHashMap();
            descriptionsSet = new LinkedHashSet<>();
            tempList = new ArrayList();

            tempList.add(amount);
            descriptionsSet.add(desc);
        }

        /* Store fields in the maps and then in the main map */
        tempSecondMap.put(desc, tempList);
        tempFirstMap.put(Integer.parseInt(extractMonthFromDate), tempSecondMap);
        yearsMappedToMonthsWithAmountsMap.put(extractYearFromDate, tempFirstMap);
    }

    private static void addAmountsWithDuplicates(Set descriptions, String desc, String
            amount, List<Float> arrayAmount) {
        Float amountWithDuplicate;
        if (descriptions.contains(desc)) {
            int i = 0;
            for (Iterator s = descriptions.iterator(); s.hasNext(); i++) {
                String descFound = (String) s.next();
                if (desc.equals(descFound)) {
                    amountWithDuplicate = arrayAmount.get(i) + Float.valueOf(amount);//add up the
                    // amounts if there are duplicates
                    arrayAmount.set(i, amountWithDuplicate);
                    break;
                }
            }
        } else {
            descriptions.add(desc);
            arrayAmount.add(Float.valueOf(amount));
        }
    }

    private String iterateFileLinesMap(String monthRequested, String yearRequested){
        StringBuilder line = new StringBuilder();
        allLinesInFile = new StringBuilder();
        for (Map.Entry<Integer, Map<Integer, String>> yearEntry : yearsMappedToMonthsWithFileLines.entrySet()) {
            int year = yearEntry.getKey();
            for ( Map.Entry<Integer, String> monthEntry : yearEntry.getValue().entrySet()) {
                int month = monthEntry.getKey();

                allLinesInFile.append(monthEntry.getValue());
                if (monthRequested.equals(String.valueOf(month)) && yearRequested.equals(String.valueOf(year))) {
                    line.append(monthEntry.getValue());
                    break;
                    }
                }
            }
            return line.toString();
        }

    private HashMap iterateMainMap() {

        for (Map.Entry<String, TreeMap<String, LinkedHashMap<String, List<Float>>>> yearEntry : yearsMappedToMonthsWithAmountsMap.entrySet()) {
            String year = yearEntry.getKey();
            Log.e("Year from file is ", year);
            objectYear = new AnyYear(yearToSet);

            yearToSet = new YearToSet(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);

            for (Map.Entry<String, LinkedHashMap<String, List<Float>>> monthEntry : yearEntry.getValue().entrySet()) {
                String month = String.valueOf(monthEntry.getKey());
                Log.e("Month from file is ", month);

                LinkedHashMap<String, List<Float>> secondMap = monthEntry.getValue();
                Set descriptionsSet = new LinkedHashSet(secondMap.keySet());

                Log.e("Description in file ", descriptionsSet.toString());
                List amounts = new ArrayList();
                for (List<Float> listFloats : secondMap.values()) {
                    for(Float amountFloat : listFloats) {
                        amounts.add(amountFloat);
                    }
                }
                processAmountsForEveryMonth(month, amounts, objectYear, descriptionsSet, iterateFileLinesMap(month, year));

            }
            /*add the year read from the map and the object year which has all the fields set that are needed */
            yearsMappedToObjectYearsMap.put(year,objectYear);
        }
        return yearsMappedToObjectYearsMap;
    }

    private void processAmountsForEveryMonth(String month, List<Float> amounts, AnyYear obj, Set set, String fileLine){

        if (month.equals(ONE)) {
            Float totalExpensesForMonth = sumExpensesForMonth(amounts);
            obj.setAmountAndDescJan(totalExpensesForMonth, set, amounts, fileLine);
        }
        if (month.equals(TWO)) {
            Float totalExpensesForMonth = sumExpensesForMonth(amounts);
            obj.setAmountAndDescFeb(totalExpensesForMonth, set, amounts, fileLine);
        }
        if (month.equals(THREE)) {
            Float totalExpensesForMonth = sumExpensesForMonth(amounts);
            obj.setAmountAndDescMar(totalExpensesForMonth, set, amounts, fileLine);
        }
        if (month.equals(FOUR)) {
            Float totalExpensesForMonth = sumExpensesForMonth(amounts);
            obj.setAmountAndDescApr(totalExpensesForMonth, set, amounts, fileLine);
        }
        if (month.equals(FIVE)) {
            Float totalExpensesForMonth = sumExpensesForMonth(amounts);
            obj.setAmountAndDescMay(totalExpensesForMonth, set, amounts, fileLine);
        }
        if (month.equals(SIX)) {
            Float totalExpensesForMonth = sumExpensesForMonth(amounts);
            obj.setAmountAndDescJun(totalExpensesForMonth, set, amounts, fileLine);
        }
        if (month.equals(SEVEN)) {
            Float totalExpensesForMonth = sumExpensesForMonth(amounts);
            obj.setAmountAndDescJul(totalExpensesForMonth, set, amounts, fileLine);
        }
        if (month.equals(EIGHT)) {
            Float totalExpensesForMonth = sumExpensesForMonth(amounts);
            obj.setAmountAndDescAug(totalExpensesForMonth, set, amounts, fileLine);
        }
        if (month.equals(NINE)) {
            Float totalExpensesForMonth = sumExpensesForMonth(amounts);
            obj.setAmountAndDescSep(totalExpensesForMonth, set, amounts, fileLine);
        }
        if (month.equals(TEN)) {
            Float totalExpensesForMonth = sumExpensesForMonth(amounts);
            obj.setAmountAndDescOct(totalExpensesForMonth, set, amounts, fileLine);
        }
        if (month.equals(ELEVEN)) {
            Float totalExpensesForMonth = sumExpensesForMonth(amounts);
            obj.setAmountAndDescNov(totalExpensesForMonth, set, amounts, fileLine);
        }
        if (month.equals(TWELVE)) {
            Float totalExpensesForMonth = sumExpensesForMonth(amounts);
            obj.setAmountAndDescDec(totalExpensesForMonth, set, amounts, fileLine);
        }
        obj.setAllLinesInFile(allLinesInFile.toString());
    }

    private Float sumExpensesForMonth(List<Float> amounts) {
        Float totalExpensesForMonth = 0.0f;
        for (int i = 0; i < amounts.size(); i++) {

            totalExpensesForMonth += amounts.get(i);
        }
        return totalExpensesForMonth;
    }

    public AnyYear getObjectYear() {
        return objectYear;
    }

    public void setData(Float jan, Float feb, Float mar, Float apr, Float may, Float jun, Float jul,
                        Float aug, Float sep, Float oct, Float nov, Float dec, LineChart mChart) {

        String [] monthData = new String[]{JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER,
                OCTOBER, NOVEMBER, DECEMBER};
        List<String> xVals = new ArrayList<>();
        for (String aMonthData : monthData) {
            xVals.add((aMonthData).substring(0, 3));
        }

        List<Entry> yVals = new ArrayList<>();

        List<Float> addAlltheMonths = new ArrayList<>();
        addAlltheMonths.add(jan);
        addAlltheMonths.add(feb);
        addAlltheMonths.add(mar);
        addAlltheMonths.add(apr);
        addAlltheMonths.add(may);
        addAlltheMonths.add(jun);
        addAlltheMonths.add(jul);
        addAlltheMonths.add(aug);
        addAlltheMonths.add(sep);
        addAlltheMonths.add(oct);
        addAlltheMonths.add(nov);
        addAlltheMonths.add(dec);

        for (int i = 0; i < monthData.length; i++) {
            float val = addAlltheMonths.get(i);
            yVals.add(new Entry(val, i));
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, ANNUAL_EXPENSES);

        // set the line to be drawn like this "- - - - - -"
        set1.enableDashedLine(10f, 5f, 0f);
        set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.BLUE);
        set1.setCircleColor(Color.BLUE);
        set1.setLineWidth(1f);
        set1.setCircleSize(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setFillAlpha(65);
        set1.setFillColor(Color.BLACK);

        List<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // set data
        mChart.setData(data);
        //update chart
        mChart.invalidate();
    }

    public void setXYAxisForChart(LineChart mChart) {
        XAxis xAxis = mChart.getXAxis();
        xAxis.setSpaceBetweenLabels(1);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines

        leftAxis.setAxisMaxValue(3000f);
        leftAxis.setAxisMinValue(-50f);
        leftAxis.setStartAtZero(false);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);
    }

    public void casesToShowExpensesForMonth(Map<String, AnyYear> yearsMappedToObjectYearsMap,int monthInt, int yearRequested, Activity activity) {

        AnyYear year = yearsMappedToObjectYearsMap.get(String.valueOf(yearRequested));

        if (year != null) {
            switch (monthInt) {

                case 1:
                    showExpensesForMonth(JANUARY, year.getYear().getDescriptionsForJan(),
                            year.getYear().getArrayOfamountJan(), activity, yearRequested, yearsMappedToObjectYearsMap);
                    break;
                case 2:
                    showExpensesForMonth(FEBRUARY, year.getYear().getDescriptionsForFeb(),
                            year.getYear().getArrayOfamountFeb(), activity, yearRequested, yearsMappedToObjectYearsMap);
                    break;
                case 3:
                    showExpensesForMonth(MARCH, year.getYear().getDescriptionsForMar(),
                            year.getYear().getArrayOfamountMar(), activity, yearRequested, yearsMappedToObjectYearsMap);
                    break;
                case 4:
                    showExpensesForMonth(APRIL, year.getYear().getDescriptionsForApr(),
                            year.getYear().getArrayOfamountApr(), activity, yearRequested, yearsMappedToObjectYearsMap);
                    break;
                case 5:
                    showExpensesForMonth(MAY, year.getYear().getDescriptionsForMay(),
                            year.getYear().getArrayOfamountMay(), activity, yearRequested, yearsMappedToObjectYearsMap);
                    break;
                case 6:
                    showExpensesForMonth(JUNE, year.getYear().getDescriptionsForJun(),
                            year.getYear().getArrayOfamountJun(), activity, yearRequested, yearsMappedToObjectYearsMap);
                    break;
                case 7:
                    showExpensesForMonth(JULY, year.getYear().getDescriptionsForJul(),
                            year.getYear().getArrayOfamountJul(), activity, yearRequested, yearsMappedToObjectYearsMap);
                    break;
                case 8:
                    showExpensesForMonth(AUGUST, year.getYear().getDescriptionsForAug(),
                            year.getYear().getArrayOfamountAug(), activity, yearRequested, yearsMappedToObjectYearsMap);
                    break;
                case 9:
                    showExpensesForMonth(SEPTEMBER, year.getYear().getDescriptionsForSep(),
                            year.getYear().getArrayOfamountSep(), activity, yearRequested, yearsMappedToObjectYearsMap);
                    break;
                case 10:
                    showExpensesForMonth(OCTOBER, year.getYear().getDescriptionsForOct(),
                            year.getYear().getArrayOfamountOct(), activity, yearRequested, yearsMappedToObjectYearsMap);
                    break;
                case 11:
                    showExpensesForMonth(NOVEMBER, year.getYear().getDescriptionsForNov(),
                            year.getYear().getArrayOfamountNov(), activity, yearRequested, yearsMappedToObjectYearsMap);
                    break;
                case 12:
                    showExpensesForMonth(DECEMBER, year.getYear().getDescriptionsForDec(),
                            year.getYear().getArrayOfamountDec(), activity, yearRequested, yearsMappedToObjectYearsMap);
                    break;
                 default:
                     Log.i("CaseToShowExpense Month", String.valueOf(monthInt));
                     break;
            }// end of switch
        }
    }

    private void showExpensesForMonth(String month, Set descriptions, List<Float>
            arrayAmount, Activity activity, int yearRequested, Map<String,AnyYear> yearsMappedToObjectYearsMap) {

        if (activity instanceof ChartActivity) {

            ((ChartActivity) activity).getMonthLabel().setText(month+" "+yearRequested);
            ((ChartActivity) activity).getDataSet().clear();
            ((ChartActivity) activity).getxVals().clear();
            int x = 0;
            for (Iterator s = descriptions.iterator(); s.hasNext(); x++) {
                String desc = (String) s.next();
                ((ChartActivity) activity).getyVals1().add(new Entry(arrayAmount.get(x), x));
                ((ChartActivity) activity).getxVals().add(desc);
            }
            ((ChartActivity) activity).setDataSet(new PieDataSet(((ChartActivity) activity).getyVals1(), EXPENSES_FOR + month));
        }
        else if (activity instanceof HorizontalBarChartActivity){
            ((HorizontalBarChartActivity) activity).getMonthLabel().setText(month+" "+yearRequested);
            ((HorizontalBarChartActivity) activity).getBarDataSet1().clear();
            ((HorizontalBarChartActivity) activity).getxAxis().clear();
            int z = 0;
            for (Iterator s = descriptions.iterator(); s.hasNext(); z++) {
                String desc = (String) s.next();
                ((HorizontalBarChartActivity) activity).getValueSet1().add(new BarEntry(arrayAmount.get(z), z));
                ((HorizontalBarChartActivity) activity).getxAxis().add(desc);
            }
        }

        modifyData(activity,yearsMappedToObjectYearsMap);
    }

    private void modifyData(Activity activity,Map<String,AnyYear>yearsMappedToObjectYearsMap) {

        if (activity instanceof ChartActivity) {
            addColors(activity);
            ((ChartActivity) activity).setData(new PieData(((ChartActivity) activity).getxVals(), ((ChartActivity) activity).getDataSet()));

            //this is called every time the user presses the arrows left or right
            if (((ChartActivity) activity).isStateSwitchButton()) {
                ((ChartActivity) activity).getmChart().setUsePercentValues(false);//if switch is ON it shows numbers not percentages
                ((ChartActivity) activity).getData().setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler
                            viewPortHandler) {
                        return Float.toString(v);
                    }
                });
            } else {

                ((ChartActivity) activity).getmChart().setUsePercentValues(true);//if switch is OFF it shows percentages
                ((ChartActivity) activity).getData().setValueFormatter(new PercentFormatter());
            }

            ((ChartActivity) activity).getData().setValueTextSize(12f);//this is the size of the percentages/numbers
            ((ChartActivity) activity).getData().setValueTextColor(Color.GRAY);
            ((ChartActivity) activity).getmChart().setDrawSliceText(false);//this removes the descriptions from every slice

            ((ChartActivity) activity).getmChart().setData(((ChartActivity) activity).getData());
            //undo all highlights
            ((ChartActivity) activity).getmChart().highlightValues(null);
            //update pie chart
            ((ChartActivity) activity).getmChart().invalidate();
        }

        else if (activity instanceof HorizontalBarChartActivity){
            if (((HorizontalBarChartActivity) activity).getValueSet1().isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                        .setTitle("No data to present")
                        .setMessage("There are no expenses to display on this month.");
                builder.setPositiveButton(OK,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert1 = builder.create();
                alert1.show();
                ((HorizontalBarChartActivity) activity).getChart().clear();
            } else {
                ((HorizontalBarChartActivity) activity).setBarDataSet1(new BarDataSet(((HorizontalBarChartActivity) activity).getValueSet1(), EXPENSE));
                ((HorizontalBarChartActivity) activity).getBarDataSet1().setColor(Color.rgb(0, 153, 204));

                ((HorizontalBarChartActivity) activity).getDataSets().clear();
                ((HorizontalBarChartActivity) activity).getDataSets().add(((HorizontalBarChartActivity) activity).getBarDataSet1());

                ((HorizontalBarChartActivity) activity).setData(new BarData(((HorizontalBarChartActivity) activity).getxAxis(), ((HorizontalBarChartActivity) activity).getDataSets()));

                if (((HorizontalBarChartActivity) activity).isStateSwitchButton()) {
                    calculatePercentagesAndModifyYAxis(yearsMappedToObjectYearsMap,((HorizontalBarChartActivity) activity).getMonthInt(), ((HorizontalBarChartActivity) activity).getYearInt(),
                            activity, ((HorizontalBarChartActivity) activity).getDataSets());

                    ((HorizontalBarChartActivity) activity).getData().setValueFormatter(new PercentFormatter());

                } else {
                    revertToNumbersAndModifyYAxis(yearsMappedToObjectYearsMap,((HorizontalBarChartActivity) activity).getMonthInt(), ((HorizontalBarChartActivity) activity).getYearInt(),
                            activity, ((HorizontalBarChartActivity) activity).getDataSets());

                    ((HorizontalBarChartActivity) activity).getData().setValueFormatter(new ValueFormatter() {
                        @Override
                        public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler
                                viewPortHandler) {
                            return Float.toString(v);
                        }
                    });
                }
                ((HorizontalBarChartActivity) activity).getChart().setData(((HorizontalBarChartActivity) activity).getData());
                ((HorizontalBarChartActivity) activity).getChart().setDescription("");
                ((HorizontalBarChartActivity) activity).getChart().animateXY(2000, 2000);
                ((HorizontalBarChartActivity) activity).getChart().invalidate();
            }
        }
    }

    private void addColors(Activity activity) {
        //add many colors
        List<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        ((ChartActivity) activity).getDataSet().setColors(colors);
    }

    public void calculatePercentagesAndModifyYAxis(Map<String,AnyYear> yearsMappedToObjectYearsMap, int monthInt, int yearRequested, Activity activity, List<BarDataSet> dataSets) {

        AnyYear year =  yearsMappedToObjectYearsMap.get(String.valueOf(yearRequested));

        switch (monthInt) {

            case 1:
                calculatePercentages(year.getYear().getArrayOfamountJan(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 2:
                calculatePercentages(year.getYear().getArrayOfamountFeb(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 3:
                calculatePercentages(year.getYear().getArrayOfamountMar(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 4:
                calculatePercentages(year.getYear().getArrayOfamountApr(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 5:
                calculatePercentages(year.getYear().getArrayOfamountMay(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 6:
                calculatePercentages(year.getYear().getArrayOfamountJun(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 7:
                calculatePercentages(year.getYear().getArrayOfamountJul(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 8:
                calculatePercentages(year.getYear().getArrayOfamountAug(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 9:
                calculatePercentages(year.getYear().getArrayOfamountSep(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 10:
                calculatePercentages(year.getYear().getArrayOfamountOct(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 11:
                calculatePercentages(year.getYear().getArrayOfamountNov(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 12:
                calculatePercentages(year.getYear().getArrayOfamountDec(), activity);
                modifyYAxis(activity, dataSets);
                break;
            default:
                Log.i("Calculate%&Modify Month", String.valueOf(monthInt));
                break;
        }
    }

    private void modifyYAxis(Activity activity, List<BarDataSet> dataSets) {
        ((HorizontalBarChartActivity) activity).setData(new BarData(((HorizontalBarChartActivity) activity).getxAxis(), dataSets));
        if (dataSets != null) {
            ((HorizontalBarChartActivity) activity).getChart().setData(((HorizontalBarChartActivity) activity).getData());
            ((HorizontalBarChartActivity) activity).getChart().animateXY(2000, 2000);
            ((HorizontalBarChartActivity) activity).getChart().invalidate();
        }
    }

    private void calculatePercentages(List<Float> arrayOfamount, Activity activity) {

        int total = 0;
        double percentage = 0;
        for (int i = 0; i < arrayOfamount.size(); i++) {
            total += Math.round(arrayOfamount.get(i));
        }
        for (int j = 0; j < arrayOfamount.size(); j++) {
            if (total > 0) {
                percentage = Math.round((arrayOfamount.get(j) * 100.0) / (double) total);
            }
            float percentageInFloat = (float) percentage;
            //i replace all the items on the valueSet1 with percentages
            ((HorizontalBarChartActivity) activity).getValueSet1().set(j, new BarEntry(percentageInFloat, j));
        }
    }

    public void revertToNumbersAndModifyYAxis(Map<String, AnyYear> yearsMappedToObjectYearsMap,int monthInt, int yearRequested, Activity activity, List<BarDataSet> dataSets) {

        AnyYear year =  yearsMappedToObjectYearsMap.get(String.valueOf(yearRequested));

        switch (monthInt) {

            case 1:
                revertPercentagesToNumbers(year.getYear().getArrayOfamountJan(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 2:
                revertPercentagesToNumbers(year.getYear().getArrayOfamountFeb(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 3:
                revertPercentagesToNumbers(year.getYear().getArrayOfamountMar(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 4:
                revertPercentagesToNumbers(year.getYear().getArrayOfamountApr(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 5:
                revertPercentagesToNumbers(year.getYear().getArrayOfamountMay(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 6:
                revertPercentagesToNumbers(year.getYear().getArrayOfamountJun(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 7:
                revertPercentagesToNumbers(year.getYear().getArrayOfamountJul(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 8:
                revertPercentagesToNumbers(year.getYear().getArrayOfamountAug(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 9:
                revertPercentagesToNumbers(year.getYear().getArrayOfamountSep(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 10:
                revertPercentagesToNumbers(year.getYear().getArrayOfamountOct(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 11:
                revertPercentagesToNumbers(year.getYear().getArrayOfamountNov(), activity);
                modifyYAxis(activity, dataSets);
                break;
            case 12:
                revertPercentagesToNumbers(year.getYear().getArrayOfamountDec(), activity);
                modifyYAxis(activity, dataSets);
                break;
                default:
                Log.i("revert2Num&Modify Month", String.valueOf(monthInt));
                    break;
        }
    }

    private void revertPercentagesToNumbers(List<Float> arrayOfamount, Activity activity) {

        for (int j = 0; j < arrayOfamount.size(); j++) {
            ((HorizontalBarChartActivity) activity).getValueSet1().set(j, new BarEntry(arrayOfamount.get(j), j));//i replace all the items
            // with actual numbers as they used to be
        }
    }

    public void calculateSelectedExpenses(Map<String,AnyYear>yearsMappedToObjectYearsMap,int yearRequested, Spinner expensesList, Activity activity) {

        String selectedExpense = expensesList.getSelectedItem().toString();

        AnyYear year =  yearsMappedToObjectYearsMap.get(String.valueOf(yearRequested));

            calculateExpensesByMonth(year.getYear().getDescriptionsForJan(), selectedExpense,
                    year.getYear().getArrayOfamountJan(), activity);
            calculateExpensesByMonth(year.getYear().getDescriptionsForFeb(), selectedExpense,
                    year.getYear().getArrayOfamountFeb(), activity);
            calculateExpensesByMonth(year.getYear().getDescriptionsForMar(), selectedExpense,
                    year.getYear().getArrayOfamountMar(), activity);
            calculateExpensesByMonth(year.getYear().getDescriptionsForApr(), selectedExpense,
                    year.getYear().getArrayOfamountApr(), activity);
            calculateExpensesByMonth(year.getYear().getDescriptionsForMay(), selectedExpense,
                    year.getYear().getArrayOfamountMay(), activity);
            calculateExpensesByMonth(year.getYear().getDescriptionsForJun(), selectedExpense,
                    year.getYear().getArrayOfamountJun(), activity);
            calculateExpensesByMonth(year.getYear().getDescriptionsForJul(), selectedExpense,
                    year.getYear().getArrayOfamountJul(), activity);
            calculateExpensesByMonth(year.getYear().getDescriptionsForAug(), selectedExpense,
                    year.getYear().getArrayOfamountAug(), activity);
            calculateExpensesByMonth(year.getYear().getDescriptionsForSep(), selectedExpense,
                    year.getYear().getArrayOfamountSep(), activity);
            calculateExpensesByMonth(year.getYear().getDescriptionsForOct(), selectedExpense,
                    year.getYear().getArrayOfamountOct(), activity);
            calculateExpensesByMonth(year.getYear().getDescriptionsForNov(), selectedExpense,
                    year.getYear().getArrayOfamountNov(), activity);
            calculateExpensesByMonth(year.getYear().getDescriptionsForDec(), selectedExpense,
                    year.getYear().getArrayOfamountDec(), activity);

    }

    private void calculateExpensesByMonth(Set descriptions, String selectedExpense,
                                          List<Float> arrayAmount, Activity activity) {

        Iterator itr = descriptions.iterator();
        int i = 0;
        while (itr.hasNext()) {
            String descFound = itr.next().toString();
            if (selectedExpense.equals(descFound)) {
                ((CalculateAnnualExpensesActivity)activity).setAnnualExpenseDouble(((CalculateAnnualExpensesActivity)activity).getAnnualExpenseDouble() + (double) arrayAmount.get(i));
            }
            i++;
        }
    }

    @TargetApi(19)
    @SuppressWarnings("StringConcatenationInLoop")
    public void readTheFileToRecalculateMonthExpensesDueToIncomeChangeCircle(int salaryDay,
                                                                             String currentMonth, YearToSet obj2018)
            throws ParseException {

        String amount = "";
        String date = "";

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("/data/data/uk.co.irokottaki" +
                ".moneycontrol/files/expenses.txt")));){


            String line = "";
            int lineIndex = 0;//this is to count the lines
            while ((line = br.readLine()) != null) {

                if (++lineIndex > 2 && !line.equals("") && !line.equals("r")) {

                    int index = line.lastIndexOf(' ');
                    amount = line.substring(0, line.indexOf(' '));
                    date = line.substring(index, line.length());
                    String extractDayFromDate = date.substring(0, date.indexOf('/'));
                    //convert String to int but first take the second character e.g. take 5 from 05
                    int extractDayFromDateInt = Integer.parseInt(extractDayFromDate.trim()
                            .replaceFirst("^0+(?!$)", ""));
                    String extractMonthFromDate = date.substring(date.indexOf('/') + 1, date
                            .lastIndexOf('/'));
                    if (extractMonthFromDate.startsWith("0")) {
                        extractMonthFromDate = extractMonthFromDate.replace("0", "");
                    }
                    String extractYearFromDate = date.substring(date.lastIndexOf('/') + 1, date
                            .length());

                    final Calendar calendar = Calendar.getInstance();
                    java.util.Date currentDate = new SimpleDateFormat("MMM", Locale.ENGLISH)
                            .parse(currentMonth);
                    calendar.setTime(currentDate);// here i convert the String month in an
                    // integer to be used on the switch-case
                    int monthInt = calendar.get(Calendar.MONTH) + 1;
                    int currentYear = Calendar.getInstance().get(Calendar.YEAR);

                    /*if the year read from the file is not the current year then jump up to the while statement and continue reading the file */
                    if (currentYear != Integer.parseInt(extractYearFromDate)) {
                        continue;
                    }

                    checkExpenseDateAgainstSalaryDay(salaryDay, obj2018, amount, extractDayFromDateInt, extractMonthFromDate, monthInt);
                }
            }

        } catch (FileNotFoundException e) {
            Log.e("File not found",e.getMessage());
        } catch (IOException e) {
            Log.e("IOException",e.getMessage());
        }
    }

    private void checkExpenseDateAgainstSalaryDay(int salaryDay, YearToSet obj2018, String amount, int extractDayFromDateInt, String extractMonthFromDate, int monthInt) {

        if (salaryDay <= extractDayFromDateInt && extractMonthFromDate.equals(String.valueOf(monthInt - 1))) {
                /*if the salary day set by the user is before the day of the expense
                made and the expense made in the
                previous month then the expense is added in the current month*/
            addAmountToCurrentOrNextMonth(Float.valueOf(amount), monthInt, obj2018);
        }
                /*else if the salary day set by the user is after the day of the
                expense made and the expense made
                on the current month then the expense amount is added in the current
                month*/
        else if (salaryDay > extractDayFromDateInt && extractMonthFromDate.equals(String.valueOf(monthInt))) {

            addAmountToCurrentOrNextMonth(Float.valueOf(amount), monthInt,obj2018);

        } else if (salaryDay <= extractDayFromDateInt && extractMonthFromDate.equals(String.valueOf(monthInt))) {
                /*if the day set by the user is before the day of the expense made
                and the expense made in the
                current month then the expense is added in the next month*/
            addAmountToCurrentOrNextMonth(Float.valueOf(amount), monthInt + 1,obj2018);

        } else if (salaryDay > extractDayFromDateInt && extractMonthFromDate.equals(String.valueOf(monthInt - 1))) {
                /*if the day set by the user is after the day of the expense made and
                 the expense made in the
                previous month then the expense is added in the previous month*/
            addAmountToCurrentOrNextMonth(Float.valueOf(amount), monthInt - 1,obj2018);

        }
    }

    public void addAmountToCurrentOrNextMonth(Float amount, int monthInt, YearToSet obj2018) {

        switch (monthInt) {

            case 1:
                obj2018.setAmountJan(obj2018.getAmountJan()+amount);
                break;
            case 2:
                obj2018.setAmountFeb(obj2018.getAmountFeb()+amount);
                break;
            case 3:
                obj2018.setAmountMar(obj2018.getAmountMar()+amount);
                break;
            case 4:
                obj2018.setAmountApr(obj2018.getAmountApr()+amount);
                break;
            case 5:
                obj2018.setAmountMay(obj2018.getAmountMay()+amount);
                break;
            case 6:
                obj2018.setAmountJun(obj2018.getAmountJun()+amount);
                break;
            case 7:
                obj2018.setAmountJul(obj2018.getAmountJul()+amount);
                break;
            case 8:
                obj2018.setAmountAug(obj2018.getAmountAug()+amount);
                break;
            case 9:
                obj2018.setAmountSep(obj2018.getAmountSep()+amount);
                break;
            case 10:
                obj2018.setAmountOct(obj2018.getAmountOct()+amount);
                break;
            case 11:
                obj2018.setAmountNov(obj2018.getAmountNov()+amount);
                break;
            case 12:
                obj2018.setAmountDec(obj2018.getAmountDec()+amount);
                break;
                default:
                    Log.i("+Amnt2CurrntOrNxt Month",String.valueOf(monthInt));
                    break;
        }

    }

    public void switchMonthsReport(Map<String,AnyYear> yearsMappedToObjectYearsMap, int getMonthSelection, int yearRequested, Activity activity) {

        AnyYear year = yearsMappedToObjectYearsMap.get(String.valueOf(yearRequested));

        if (year != null) {
            switch (getMonthSelection) {

                case 1:
                    ((ReportActivity) activity).getReportView().setText(formatReportArea(year.getYear().getFileLineJan()));
                    break;
                case 2:
                    ((ReportActivity) activity).getReportView().setText(formatReportArea(year.getYear().getFileLineFeb()));
                    break;
                case 3:
                    ((ReportActivity) activity).getReportView().setText(formatReportArea(year.getYear().getFileLineMar()));
                    break;
                case 4:
                    ((ReportActivity) activity).getReportView().setText(formatReportArea(year.getYear().getFileLineApr()));
                    break;
                case 5:
                    ((ReportActivity) activity).getReportView().setText(formatReportArea(year.getYear().getFileLineMay()));
                    break;
                case 6:
                    ((ReportActivity) activity).getReportView().setText(formatReportArea(year.getYear().getFileLineJun()));
                    break;
                case 7:
                    ((ReportActivity) activity).getReportView().setText(formatReportArea(year.getYear().getFileLineJul()));
                    break;
                case 8:
                    ((ReportActivity) activity).getReportView().setText(formatReportArea(year.getYear().getFileLineAug()));
                    break;
                case 9:
                    ((ReportActivity) activity).getReportView().setText(formatReportArea(year.getYear().getFileLineSep()));
                    break;
                case 10:
                    ((ReportActivity) activity).getReportView().setText(formatReportArea(year.getYear().getFileLineOct()));
                    break;
                case 11:
                    ((ReportActivity) activity).getReportView().setText(formatReportArea(year.getYear().getFileLineNov()));
                    break;
                case 12:
                    ((ReportActivity) activity).getReportView().setText(formatReportArea(year.getYear().getFileLineDec()));
                    break;

                default:
                    ((ReportActivity) activity).getReportView().setText(formatReportArea(year.getAllLinesInFile()));
                    break;
            }//end of switch
        }
     }


    public StringBuilder formatReportArea(String fileLine) {

        StringBuilder shortLine = new StringBuilder();
        String formatStr = "%-8s%-15s%-10s";

        String[] lines = fileLine.split("\n");

        String amount = AMOUNT;
        String shortDesc = DESCRIPTION;
        String date = DATE;
        shortLine.append(String.format(formatStr, amount, shortDesc, date)).trimToSize();
        shortLine.append("\n");
        shortLine.append("\n");

        for (int i = 0; i < lines.length; i++) {
            if (i >= 0 && !lines[i].equals("")) {
                amount = lines[i].substring(0, lines[i].indexOf(' '));
                shortDesc = lines[i].substring(lines[i].indexOf(' '), lines[i].lastIndexOf
                        (' ')).trim();
                date = lines[i].substring(lines[i].lastIndexOf(' '), lines[i].length())
                        .trim();
                shortLine.append(String.format(formatStr, amount, shortDesc, date)).trimToSize();
                shortLine.append("\n");
            }
        }
        return shortLine;
    }

    public void switchAmountsBetweenYears(Map<String,AnyYear> yearsMappedToObjectYearsMap, int yearRequested, TextView yearView, Activity activity) {

        AnyYear year = yearsMappedToObjectYearsMap.get(String.valueOf(yearRequested));

        yearView.setText(YEAR + yearRequested);

           if (year != null) {
               setData(year.getYear().getAmountJan(), year.getYear().getAmountFeb(), year.getYear().getAmountMar(), year.getYear().getAmountApr(),
                       year.getYear().getAmountMay(), year.getYear().getAmountJun(), year.getYear().getAmountJul(), year.getYear().getAmountAug(),
                       year.getYear().getAmountSep(), year.getYear().getAmountOct(), year.getYear().getAmountNov(), year.getYear().getAmountDec(),
                       ((AnnualChartActivity) activity).getAnnualChart());
           }
           else {
               setData(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, ((AnnualChartActivity) activity).getAnnualChart());
           }
    }

    public void setSavings(Map<String,AnyYear> yearsMappedToObjectYearsMap, int yearRequested, Activity activity) {

        AnyYear year = yearsMappedToObjectYearsMap.get(String.valueOf(yearRequested));

        //retrieve the incomes from Shared Preferences
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        String incomeValue = sp.getString(INCOME, "");

        if (!incomeValue.equals("")) {

            Float incomeForJan = Float.parseFloat(incomeValue);
            Float incomeForFeb = Float.parseFloat(incomeValue);
            Float incomeForMar = Float.parseFloat(incomeValue);
            Float incomeForApr = Float.parseFloat(incomeValue);
            Float incomeForMay = Float.parseFloat(incomeValue);
            Float incomeForJun = Float.parseFloat(incomeValue);
            Float incomeForJul = Float.parseFloat(incomeValue);
            Float incomeForAug = Float.parseFloat(incomeValue);
            Float incomeForSep = Float.parseFloat(incomeValue);
            Float incomeForOct = Float.parseFloat(incomeValue);
            Float incomeForNov = Float.parseFloat(incomeValue);
            Float incomeForDec = Float.parseFloat(incomeValue);

            if (year != null) {
                setData(incomeForJan - year.getYear().getAmountJan(), incomeForFeb - year.getYear().getAmountFeb(), incomeForMar -
                                year.getYear().getAmountMar(), incomeForApr - year.getYear().getAmountApr(),
                        incomeForMay - year.getYear().getAmountMay(), incomeForJun - year.getYear().getAmountJun(), incomeForJul -
                                year.getYear().getAmountJul(), incomeForAug - year.getYear().getAmountAug(),
                        incomeForSep - year.getYear().getAmountSep(), incomeForOct - year.getYear().getAmountOct(), incomeForNov -
                                year.getYear().getAmountNov(), incomeForDec - year.getYear().getAmountDec(), ((AnnualSavingsActivity) activity).getAnnualSavingsChart());

            } else {
                setData(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, ((AnnualSavingsActivity) activity).getAnnualSavingsChart());
            }
        }
    }


    public void populateYearSpinnerAndSetCurrentYear(Map<String,AnyYear> yearsMappedToObjectYearsMap, int yearRequested, Spinner yearList, Activity activity) {

        List<String> yearsFoundInFile = new ArrayList<>();

        for (String yearKey : yearsMappedToObjectYearsMap.keySet()) {

            yearsFoundInFile.add(yearKey);
        }

        Collections.sort(yearsFoundInFile);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, yearsFoundInFile);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearList.setAdapter(spinnerAdapter);

        yearList.setAdapter(new NothingSelectedSpinnerAdapter(spinnerAdapter, R.layout.spinnernothingselected, activity));

        int index = 1;
        for (String yearIndex : yearsFoundInFile) {

            if (yearIndex.equals(String.valueOf(yearRequested))) {

                break;
            }
            index++;
        }
        yearList.setSelection(index);
    }

    public void updateMapWithNewExpense(String amount, String descriptionText, String date, int currentYear, Map<String,AnyYear> yearsMappedToObjectYearsMap) {
        // get the anyYear object
        AnyYear anyYear = yearsMappedToObjectYearsMap.get(String.valueOf(currentYear));

        String monthTheExpenseWritten = date.substring(date.indexOf('/') + 1, date.lastIndexOf('/'));
        if (monthTheExpenseWritten.startsWith("0")) {
                monthTheExpenseWritten = monthTheExpenseWritten.replace("0", "");
        }
            String formatStr = "%-8s%-15s%-10s";
            String updatedAllLinesInFile = anyYear.getAllLinesInFile();
            updatedAllLinesInFile += String.format(formatStr, amount, descriptionText, date) + "\n";

            if (monthTheExpenseWritten.equals(ONE)) {

                Float updatedTotalExpensesForMonth = anyYear.getYear().getAmountJan() + Float.valueOf(amount);
                Set existingDescriptionsSet = anyYear.getYear().getDescriptionsForJan();
                List<Float> updatedArrayOfAmounts = anyYear.getYear().getArrayOfamountJan();
                //that means the expense added already exists in the set so it is a duplicate
                checkDuplicateDescriptions(amount, descriptionText, existingDescriptionsSet, updatedArrayOfAmounts);
                String updatedFileLine = anyYear.getYear().getFileLineJan();
                updatedFileLine += String.format(formatStr, amount, descriptionText, date) + "\n";
                // set all the fields updated
                anyYear.setAmountAndDescJan(updatedTotalExpensesForMonth, existingDescriptionsSet, updatedArrayOfAmounts, updatedFileLine);
            }
            if (monthTheExpenseWritten.equals(TWO)) {

                Float updatedTotalExpensesForMonth = anyYear.getYear().getAmountFeb() + Float.valueOf(amount);
                Set existingDescriptionsSet = anyYear.getYear().getDescriptionsForFeb();
                List<Float> updatedArrayOfAmounts = anyYear.getYear().getArrayOfamountFeb();
                //that means the expense added already exists in the set so it is a duplicate
                checkDuplicateDescriptions(amount, descriptionText, existingDescriptionsSet, updatedArrayOfAmounts);
                String updatedFileLine = anyYear.getYear().getFileLineFeb();
                updatedFileLine += String.format(formatStr, amount, descriptionText, date) + "\n";
                // set all the fields updated
                anyYear.setAmountAndDescFeb(updatedTotalExpensesForMonth, existingDescriptionsSet, updatedArrayOfAmounts, updatedFileLine);
            }
            if (monthTheExpenseWritten.equals(THREE)) {

                Float updatedTotalExpensesForMonth = anyYear.getYear().getAmountMar() + Float.valueOf(amount);
                Set existingDescriptionsSet = anyYear.getYear().getDescriptionsForMar();
                List<Float> updatedArrayOfAmounts = anyYear.getYear().getArrayOfamountMar();
                //that means the expense added already exists in the set so it is a duplicate
                checkDuplicateDescriptions(amount, descriptionText, existingDescriptionsSet, updatedArrayOfAmounts);
                String updatedFileLine = anyYear.getYear().getFileLineMar();
                updatedFileLine += String.format(formatStr, amount, descriptionText, date) + "\n";
                // set all the fields updated
                anyYear.setAmountAndDescMar(updatedTotalExpensesForMonth, existingDescriptionsSet, updatedArrayOfAmounts, updatedFileLine);
            }
            if (monthTheExpenseWritten.equals(FOUR)) {

                Float updatedTotalExpensesForMonth = anyYear.getYear().getAmountApr() + Float.valueOf(amount);
                Set existingDescriptionsSet = anyYear.getYear().getDescriptionsForApr();
                List<Float> updatedArrayOfAmounts = anyYear.getYear().getArrayOfamountApr();
                //that means the expense added already exists in the set so it is a duplicate
                checkDuplicateDescriptions(amount, descriptionText, existingDescriptionsSet, updatedArrayOfAmounts);
                String updatedFileLine = anyYear.getYear().getFileLineApr();
                updatedFileLine += String.format(formatStr, amount, descriptionText, date) + "\n";
                // set all the fields updated
                anyYear.setAmountAndDescApr(updatedTotalExpensesForMonth, existingDescriptionsSet, updatedArrayOfAmounts, updatedFileLine);
            }
            if (monthTheExpenseWritten.equals(FIVE)) {

                Float updatedTotalExpensesForMonth = anyYear.getYear().getAmountMay() + Float.valueOf(amount);
                Set existingDescriptionsSet = anyYear.getYear().getDescriptionsForMay();
                List<Float> updatedArrayOfAmounts = anyYear.getYear().getArrayOfamountMay();
                //that means the expense added already exists in the set so it is a duplicate
                checkDuplicateDescriptions(amount, descriptionText, existingDescriptionsSet, updatedArrayOfAmounts);
                String updatedFileLine = anyYear.getYear().getFileLineMay();
                updatedFileLine += String.format(formatStr, amount, descriptionText, date) + "\n";
                // set all the fields updated
                anyYear.setAmountAndDescMay(updatedTotalExpensesForMonth, existingDescriptionsSet, updatedArrayOfAmounts, updatedFileLine);
            }
            if (monthTheExpenseWritten.equals(SIX)) {

                Float updatedTotalExpensesForMonth = anyYear.getYear().getAmountJun() + Float.valueOf(amount);
                Set existingDescriptionsSet = anyYear.getYear().getDescriptionsForJun();
                List<Float> updatedArrayOfAmounts = anyYear.getYear().getArrayOfamountJun();
                //that means the expense added already exists in the set so it is a duplicate
                checkDuplicateDescriptions(amount, descriptionText, existingDescriptionsSet, updatedArrayOfAmounts);
                String updatedFileLine = anyYear.getYear().getFileLineJun();
                updatedFileLine += String.format(formatStr, amount, descriptionText, date) + "\n";
                // set all the fields updated
                anyYear.setAmountAndDescJun(updatedTotalExpensesForMonth, existingDescriptionsSet, updatedArrayOfAmounts, updatedFileLine);
            }
            if (monthTheExpenseWritten.equals(SEVEN)) {

                Float updatedTotalExpensesForMonth = anyYear.getYear().getAmountJul() + Float.valueOf(amount);
                Set existingDescriptionsSet = anyYear.getYear().getDescriptionsForJul();
                List<Float> updatedArrayOfAmounts = anyYear.getYear().getArrayOfamountJul();
                //that means the expense added already exists in the set so it is a duplicate
                checkDuplicateDescriptions(amount, descriptionText, existingDescriptionsSet, updatedArrayOfAmounts);
                String updatedFileLine = anyYear.getYear().getFileLineJul();
                updatedFileLine += String.format(formatStr, amount, descriptionText, date) + "\n";
                // set all the fields updated
                anyYear.setAmountAndDescJul(updatedTotalExpensesForMonth, existingDescriptionsSet, updatedArrayOfAmounts, updatedFileLine);
            }
            if (monthTheExpenseWritten.equals(EIGHT)) {

                Float updatedTotalExpensesForMonth = anyYear.getYear().getAmountAug() + Float.valueOf(amount);
                Set existingDescriptionsSet = anyYear.getYear().getDescriptionsForAug();
                List<Float> updatedArrayOfAmounts = anyYear.getYear().getArrayOfamountAug();
                //that means the expense added already exists in the set so it is a duplicate
                checkDuplicateDescriptions(amount, descriptionText, existingDescriptionsSet, updatedArrayOfAmounts);
                String updatedFileLine = anyYear.getYear().getFileLineAug();
                updatedFileLine += String.format(formatStr, amount, descriptionText, date) + "\n";
                // set all the fields updated
                anyYear.setAmountAndDescAug(updatedTotalExpensesForMonth, existingDescriptionsSet, updatedArrayOfAmounts, updatedFileLine);
            }
            if (monthTheExpenseWritten.equals(NINE)) {

                Float updatedTotalExpensesForMonth = anyYear.getYear().getAmountSep() + Float.valueOf(amount);
                Set existingDescriptionsSet = anyYear.getYear().getDescriptionsForSep();
                List<Float> updatedArrayOfAmounts = anyYear.getYear().getArrayOfamountSep();
                //that means the expense added already exists in the set so it is a duplicate
                checkDuplicateDescriptions(amount, descriptionText, existingDescriptionsSet, updatedArrayOfAmounts);
                String updatedFileLine = anyYear.getYear().getFileLineSep();
                updatedFileLine += String.format(formatStr, amount, descriptionText, date) + "\n";
                // set all the fields updated
                anyYear.setAmountAndDescSep(updatedTotalExpensesForMonth, existingDescriptionsSet, updatedArrayOfAmounts, updatedFileLine);
            }
            if (monthTheExpenseWritten.equals(TEN)) {

                Float updatedTotalExpensesForMonth = anyYear.getYear().getAmountOct() + Float.valueOf(amount);
                Set existingDescriptionsSet = anyYear.getYear().getDescriptionsForOct();
                List<Float> updatedArrayOfAmounts = anyYear.getYear().getArrayOfamountOct();
                //that means the expense added already exists in the set so it is a duplicate
                checkDuplicateDescriptions(amount, descriptionText, existingDescriptionsSet, updatedArrayOfAmounts);
                String updatedFileLine = anyYear.getYear().getFileLineOct();
                updatedFileLine += String.format(formatStr, amount, descriptionText, date) + "\n";
                // set all the fields updated
                anyYear.setAmountAndDescOct(updatedTotalExpensesForMonth, existingDescriptionsSet, updatedArrayOfAmounts, updatedFileLine);
            }
            if (monthTheExpenseWritten.equals(ELEVEN)) {

                Float updatedTotalExpensesForMonth = anyYear.getYear().getAmountNov() + Float.valueOf(amount);
                Set existingDescriptionsSet = anyYear.getYear().getDescriptionsForNov();
                List<Float> updatedArrayOfAmounts = anyYear.getYear().getArrayOfamountNov();
                //that means the expense added already exists in the set so it is a duplicate
                checkDuplicateDescriptions(amount, descriptionText, existingDescriptionsSet, updatedArrayOfAmounts);
                String updatedFileLine = anyYear.getYear().getFileLineNov();
                updatedFileLine += String.format(formatStr, amount, descriptionText, date) + "\n";
                // set all the fields updated
                anyYear.setAmountAndDescNov(updatedTotalExpensesForMonth, existingDescriptionsSet, updatedArrayOfAmounts, updatedFileLine);
            }
            if (monthTheExpenseWritten.equals(TWELVE)) {

                Float updatedTotalExpensesForMonth = anyYear.getYear().getAmountDec() + Float.valueOf(amount);
                Set existingDescriptionsSet = anyYear.getYear().getDescriptionsForDec();
                List<Float> updatedArrayOfAmounts = anyYear.getYear().getArrayOfamountDec();
                //that means the expense added already exists in the set so it is a duplicate
                checkDuplicateDescriptions(amount, descriptionText, existingDescriptionsSet, updatedArrayOfAmounts);
                String updatedFileLine = anyYear.getYear().getFileLineDec();
                updatedFileLine += String.format(formatStr, amount, descriptionText, date) + "\n";
                // set all the fields updated
                anyYear.setAmountAndDescDec(updatedTotalExpensesForMonth, existingDescriptionsSet, updatedArrayOfAmounts, updatedFileLine);
            }
            anyYear.setAllLinesInFile(updatedAllLinesInFile);

    }

    private void checkDuplicateDescriptions(String amount, String descriptionText, Set existingDescriptionsSet, List<Float> updatedArrayOfAmounts) {
        if (existingDescriptionsSet.contains(descriptionText)) {

            addAmountsWithDuplicates(existingDescriptionsSet, descriptionText, amount, updatedArrayOfAmounts);

        } else {
            updatedArrayOfAmounts.add(Float.valueOf(amount));
            existingDescriptionsSet.add(descriptionText);
        }
    }

    public void clickRightOrLeft( Activity activity, boolean isLeftClick) {

        final Calendar calendar = Calendar.getInstance();
        Intent intent = activity.getIntent();
        HashMap<String, AnyYear>  yearsMappedToObjectYearsMapLocal = (HashMap<String, AnyYear> ) intent.getSerializableExtra("yearsMappedToObjectYearsMap");

        if (activity instanceof HorizontalBarChartActivity) {
            processForHorizontalBarChartActivity(activity, isLeftClick, calendar, yearsMappedToObjectYearsMapLocal);
        }
        else {
            processForChartActivity(activity, isLeftClick, calendar, yearsMappedToObjectYearsMapLocal);
        }
    }

    private void processForChartActivity(Activity activity, boolean isLeftClick, Calendar calendar, HashMap<String, AnyYear> yearsMappedToObjectYearsMapLocal) {
        int monthInt = ((ChartActivity) activity).getMonthInt();
        if (isLeftClick) {

            ((ChartActivity) activity).setMonthInt(monthInt - 1);
        }
        else {

            ((ChartActivity) activity).setMonthInt(monthInt + 1);
        }
        int year = ((ChartActivity) activity).getYearInt();
        if (((ChartActivity) activity).getMonthInt() > 12) {
            ((ChartActivity) activity).setYear(year + 1);
            ((ChartActivity) activity).setMonthInt(1);
        }
        if (((ChartActivity) activity).getMonthInt() < 1) {
            ((ChartActivity) activity).setYear(year - 1);
            ((ChartActivity) activity).setMonthInt(12);
        }
        //the expenses file is empty so just set Data to start the activity*//*
        if (yearsMappedToObjectYearsMapLocal.isEmpty()) {
            String currentMonth = String.format(Locale.UK, "%tB", calendar);
            ((ChartActivity) activity).getMonthLabel().setText(currentMonth + " " + calendar.get(Calendar.YEAR));
        } else {
            casesToShowExpensesForMonth(yearsMappedToObjectYearsMapLocal, ((ChartActivity) activity).getMonthInt(), ((ChartActivity) activity).getYearInt(), activity);
        }
    }

    private void processForHorizontalBarChartActivity(Activity activity, boolean isLeftClick, Calendar calendar, HashMap<String, AnyYear> yearsMappedToObjectYearsMapLocal) {
        int monthInt = ((HorizontalBarChartActivity) activity).getMonthInt();
        if (isLeftClick) {

            ((HorizontalBarChartActivity) activity).setMonthInt(monthInt - 1);
        }
        else {
            ((HorizontalBarChartActivity) activity).setMonthInt(monthInt + 1);
        }
        int year = ((HorizontalBarChartActivity) activity).getYearInt();
        if (((HorizontalBarChartActivity) activity).getMonthInt() > 12) {
            ((HorizontalBarChartActivity) activity).setYear(year + 1);
            ((HorizontalBarChartActivity) activity).setMonthInt(1);
        }
        if (((HorizontalBarChartActivity) activity).getMonthInt() < 1) {
            ((HorizontalBarChartActivity) activity).setYear(year - 1);
            ((HorizontalBarChartActivity) activity).setMonthInt(12);
        }
        //the expenses file is empty so just set Data to start the activity*//*
        if (yearsMappedToObjectYearsMapLocal.isEmpty()) {
            String currentMonth = String.format(Locale.UK, "%tB", calendar);
            ((HorizontalBarChartActivity) activity).getMonthLabel().setText(currentMonth + " " + calendar.get(Calendar.YEAR));
        } else {
            casesToShowExpensesForMonth(yearsMappedToObjectYearsMapLocal, ((HorizontalBarChartActivity) activity).getMonthInt(), ((HorizontalBarChartActivity) activity).getYearInt(), activity);
        }
    }

    public void resetExpenseOfCurrentMonth(int currentMonth, YearToSet obj2018) {

        switch (currentMonth) {

            case 1:
                obj2018.setAmountJan(0f);
                break;
            case 2:
                obj2018.setAmountFeb(0f);
                break;
            case 3:
                obj2018.setAmountMar(0f);
                break;
            case 4:
                obj2018.setAmountApr(0f);
                break;
            case 5:
                obj2018.setAmountMay(0f);
                break;
            case 6:
                obj2018.setAmountJun(0f);
                break;
            case 7:
                obj2018.setAmountJul(0f);
                break;
            case 8:
                obj2018.setAmountAug(0f);
                break;
            case 9:
                obj2018.setAmountSep(0f);
                break;
            case 10:
                obj2018.setAmountOct(0f);
                break;
            case 11:
                obj2018.setAmountNov(0f);
                break;
            case 12:
                obj2018.setAmountDec(0f);
                break;
                default:
                    Log.i("resetXpnseCurrent Month", String.valueOf(currentMonth));
                    break;
        }
    }

    public void displayMonthToLabel(Activity activity) {

        final Calendar calendar = Calendar.getInstance();
        String currentMonth = String.format(Locale.UK, "%tB", calendar);

        if(activity instanceof ChartActivity) {
            ((ChartActivity) activity).setYear(calendar.get(Calendar.YEAR));// get the current year
            ((ChartActivity) activity).getMonthLabel().setText(currentMonth);
            String getCurrentMonthDisplayed = ((ChartActivity) activity).getMonthLabel().getText().toString();
            ((ChartActivity) activity).setMonthInt(0);

            try {
                java.util.Date date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse
                        (getCurrentMonthDisplayed);
                calendar.setTime(date);// here i convert the String month in an integer to be used on
                // the switch-case
                ((ChartActivity) activity).setMonthInt(calendar.get(Calendar.MONTH));
            } catch (ParseException e) {
                Log.e("ParseException", e.getMessage());
            }
        }
        else {
            ((HorizontalBarChartActivity) activity).setYear(calendar.get(Calendar.YEAR));// get the current year
            ((HorizontalBarChartActivity) activity).getMonthLabel().setText(currentMonth);
            String getCurrentMonthDisplayed = ((HorizontalBarChartActivity) activity).getMonthLabel().getText().toString();
            ((HorizontalBarChartActivity) activity).setMonthInt(0);

            try {
                java.util.Date date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse
                        (getCurrentMonthDisplayed);
                calendar.setTime(date);// here i convert the String month in an integer to be used on
                // the switch-case
                ((HorizontalBarChartActivity) activity).setMonthInt(calendar.get(Calendar.MONTH));
            } catch (ParseException e) {
                Log.e("ParseException", e.getMessage());
            }
        }
    }

}
