package uk.co.irokottaki.moneycontrol.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import uk.co.irokottaki.moneycontrol.Model.AmountsFor2015;
import uk.co.irokottaki.moneycontrol.Model.AmountsFor2016;
import uk.co.irokottaki.moneycontrol.Model.AmountsFor2017;
import uk.co.irokottaki.moneycontrol.Model.AmountsFor2018;
import uk.co.irokottaki.moneycontrol.Utils.ChartsUtil;
import uk.co.irokottaki.moneycontrol.R;
import uk.co.irokottaki.moneycontrol.Utils.Utils;

import static uk.co.irokottaki.moneycontrol.Utils.Constants.*;


public class AnnualChartActivity extends AppCompatActivity implements OnChartGestureListener,
        OnChartValueSelectedListener {
    public LineChart mChart;
    ImageButton leftYearButton;
    ImageButton rightYearButton;
    private TextView specificexpenseForYear;
    private TextView circleTextview;
    public int year;
    private int valueFromNumPicker1;
    private NumberPicker numberPicker1;
    private boolean isPaymentCircleSetAnnual;
    private ChartsUtil util;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annual_chart);
        setTitle(ANNUAL_CHART);

        //READ THE FILE AND GET THE AMOUNTS FOR EVERY MONTH/YEAR
        util = new ChartsUtil(this);
        util.readTheFile();
        // Get the object years to acces the months data expenses
        final AmountsFor2018 obj2018 = util.getObjectYear().getYear2018();
        final AmountsFor2017 obj2017 = util.getObjectYear().getYear2017();
        final AmountsFor2016 obj2016 = util.getObjectYear().getYear2016();
        final AmountsFor2015 obj2015 = util.getObjectYear().getYear2015();

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.annualChartView);
        SharedPreferences sharedprefs = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        isPaymentCircleSetAnnual = sharedprefs.getBoolean(IS_PAYMENT_CIRCLE_ANNUAL, false);
        //this is to change the background color of the activity when user changes it from settings
        Utils utils = new Utils(this);
        utils.setBackgroundAndAdjustLayout(layout, AnnualChartActivity.this);

        mChart = (LineChart) findViewById(R.id.annualChartLayout);
        mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);

        // no description text
        mChart.setDescription("");
        mChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // Arrow buttons
        leftYearButton = new ImageButton(this);
        leftYearButton = (ImageButton) findViewById(R.id.leftYearButton);

        rightYearButton = new ImageButton(this);
        rightYearButton = (ImageButton) findViewById(R.id.rigthYearButton);

        //Circle Button
        circleTextview = new TextView(this);
        circleTextview = (TextView) findViewById(R.id.circleTextview);
        circleTextview.setText(Html.fromHtml("<font color=#0000FF> <u> Set Income Circle"));

        //Year Label
        final TextView yearView = (TextView) findViewById(R.id.yearlabel);

        //Bottom Label for each expense in a year
        specificexpenseForYear = (TextView) findViewById(R.id.specificExpensesOfYear);
        specificexpenseForYear.setMovementMethod(LinkMovementMethod.getInstance());
        specificexpenseForYear.setText(Html.fromHtml("<font color=#0000FF> <u>Hom much do I spend" +
                " on a year for each expense?" +
                "</u></font>"));

        Calendar calendar = util.setXYAxisForChart(yearView, mChart, year);
        year = calendar.get(Calendar.YEAR);// get the current year
        yearView.setText(YEAR + year);

        if (isPaymentCircleSetAnnual) {
            String currentMonth = String.format(Locale.UK, "%tB", calendar);
            int monthInt = calendar.get(Calendar.MONTH) + 1;

            try {
                resetExpenseOfCurrentMonth(monthInt - 1,obj2018);
                resetExpenseOfCurrentMonth(monthInt,obj2018);
                resetExpenseOfCurrentMonth(monthInt + 1,obj2018);
                //get the int values from number pickers
                valueFromNumPicker1 = sharedprefs.getInt(VALUEFROMNUMPICKER1ANNUAL,
                        valueFromNumPicker1);

                readTheFileToRecalculateMonthExpensesDueToIncomeChangeCircle(valueFromNumPicker1,
                        currentMonth,obj2018);
            } catch (ParseException e) {
                Log.e("Parse Exception " ,e.getMessage());
            }
            util.setData(obj2018.getAmountJan(),obj2018.getAmountFeb(),obj2018.getAmountMar(),obj2018.getAmountApr(),
                    obj2018.getAmountMay(),obj2018.getAmountJun(), obj2018.getAmountJul(),obj2018.getAmountAug(),
                    obj2018.getAmountSep(),obj2018.getAmountOct(), obj2018.getAmountNov(),obj2018.getAmountDec(), mChart);

        } else {

            //i am just setting the data for every year that will be displayed on initializing
            // the activity.
            if (year == 2018) {
                util.setData(obj2018.getAmountJan(),obj2018.getAmountFeb(),obj2018.getAmountMar(),obj2018.getAmountApr(),
                          obj2018.getAmountMay(),obj2018.getAmountJun(), obj2018.getAmountJul(),obj2018.getAmountAug(),
                          obj2018.getAmountSep(),obj2018.getAmountOct(), obj2018.getAmountNov(),obj2018.getAmountDec(), mChart);
            }
            if (year == 2017) {
                util.setData(obj2017.getAmountJan(),obj2017.getAmountFeb(),obj2017.getAmountMar(),obj2017.getAmountApr(),
                        obj2017.getAmountMay(),obj2017.getAmountJun(),obj2017.getAmountJul(),obj2017.getAmountAug(),
                        obj2017.getAmountSep(),obj2017.getAmountOct(),obj2017.getAmountNov(),obj2017.getAmountDec(), mChart);
            }

            if (year == 2016) {
                util.setData(obj2016.getAmountJan(),obj2016.getAmountFeb(),obj2016.getAmountMar(),obj2016.getAmountApr(),
                        obj2016.getAmountMay(),obj2016.getAmountJun(),obj2016.getAmountJul(),obj2016.getAmountAug(),
                        obj2016.getAmountSep(),obj2016.getAmountOct(),obj2016.getAmountNov(),obj2016.getAmountDec(), mChart);
            }

            if (year == 2015) {
                util.setData(obj2015.getAmountJan(),obj2015.getAmountFeb(),obj2015.getAmountMar(),obj2015.getAmountApr(),
                        obj2015.getAmountMay(),obj2015.getAmountJun(),obj2015.getAmountJul(),obj2015.getAmountAug(),
                        obj2015.getAmountSep(),obj2015.getAmountOct(),obj2015.getAmountNov(),obj2015.getAmountDec(), mChart);
            }
        }
        //listener of the income circle button
        circleTextview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showNumberPickerDialogOnButtonClick(obj2018);
            }
        });

        //Listener to events on clicking the image arrows
        leftYearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year--;
                switchAmountsBetweenYears(yearView, obj2015, obj2016, obj2017, obj2018);
            }
        });

        rightYearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year++;
                switchAmountsBetweenYears(yearView, obj2015, obj2016, obj2017, obj2018);
            }
        });

        mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend
        l.setForm(Legend.LegendForm.LINE);

        specificexpenseForYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(view.getContext(), CalculateAnnualExpensesActivity
                        .class);
                startActivity(intent1);
            }
        });
    }// end of onCreate

    private void switchAmountsBetweenYears(TextView yearView, AmountsFor2015 obj2015, AmountsFor2016 obj2016, AmountsFor2017 obj2017, AmountsFor2018 obj2018) {
        switch (year) {
            case 2018:
                yearView.setText(YEAR + year);
                util.setData(obj2018.getAmountJan(),obj2018.getAmountFeb(),obj2018.getAmountMar(),obj2018.getAmountApr(),
                        obj2018.getAmountMay(),obj2018.getAmountJun(), obj2018.getAmountJul(),obj2018.getAmountAug(),
                        obj2018.getAmountSep(),obj2018.getAmountOct(), obj2018.getAmountNov(),obj2018.getAmountDec(), mChart);
                break;
            case 2017:
                yearView.setText(YEAR + year);
                util.setData(obj2017.getAmountJan(),obj2017.getAmountFeb(),obj2017.getAmountMar(),obj2017.getAmountApr(),
                        obj2017.getAmountMay(),obj2017.getAmountJun(),obj2017.getAmountJul(),obj2017.getAmountAug(),
                        obj2017.getAmountSep(),obj2017.getAmountOct(),obj2017.getAmountNov(),obj2017.getAmountDec(), mChart);
                break;
            case 2016:
                yearView.setText(YEAR + year);
                util.setData(obj2016.getAmountJan(),obj2016.getAmountFeb(),obj2016.getAmountMar(),obj2016.getAmountApr(),
                        obj2016.getAmountMay(),obj2016.getAmountJun(),obj2016.getAmountJul(),obj2016.getAmountAug(),
                        obj2016.getAmountSep(),obj2016.getAmountOct(),obj2016.getAmountNov(),obj2016.getAmountDec(), mChart);
                break;
            case 2015:
                yearView.setText(YEAR + year);
                util.setData(obj2015.getAmountJan(),obj2015.getAmountFeb(),obj2015.getAmountMar(),obj2015.getAmountApr(),
                        obj2015.getAmountMay(),obj2015.getAmountJun(),obj2015.getAmountJul(),obj2015.getAmountAug(),
                        obj2015.getAmountSep(),obj2015.getAmountOct(),obj2015.getAmountNov(),obj2015.getAmountDec(), mChart);
                break;

        }
    }


    protected void showNumberPickerDialogOnButtonClick(final AmountsFor2018 obj2018) {

        LayoutInflater inflater = LayoutInflater.from(AnnualChartActivity.this);
        View npView = inflater.inflate(R.layout.number_picker_dialog_annual_layout, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AnnualChartActivity
                .this, AlertDialog.THEME_HOLO_LIGHT);
        //number pickers
        numberPicker1 = npView.findViewById(R.id.numberPicker1);
        numberPicker1.setMaxValue(31);
        numberPicker1.setMinValue(1);
        numberPicker1.setWrapSelectorWheel(true);

        //Labels before the number pickers
        TextView numberPickerLabel1 = new TextView(this);
        numberPickerLabel1 = npView.findViewById(R.id.numberPickerLabel1);

        if ((valueFromNumPicker1 != 0)) {
            numberPicker1.setValue(valueFromNumPicker1);
        }

        alertDialogBuilder.setCancelable(false)
                .setTitle("Set your payment circle")
                .setMessage("Set the day your salary comes in." + "\n" +
                        "It is applied only in expenses of the current month." + "\n" +
                        "Change back to calendar month with the Reset button.")
                .setView(npView)
                .setPositiveButton(SET,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                valueFromNumPicker1 = numberPicker1.getValue();

                                final Calendar calendar = Calendar.getInstance();//this gets the
                                // current month
                                String currentMonth = String.format(Locale.UK, "%tB", calendar);
                                int monthInt = calendar.get(Calendar.MONTH) + 1;

                                try {
                                    /*Set to zero the expenses sums of the current and next month*/
                                    resetExpenseOfCurrentMonth(monthInt - 1,obj2018);
                                    resetExpenseOfCurrentMonth(monthInt,obj2018);
                                    resetExpenseOfCurrentMonth(monthInt + 1,obj2018);
                                    readTheFileToRecalculateMonthExpensesDueToIncomeChangeCircle
                                            (valueFromNumPicker1, currentMonth,obj2018);
                                    util.setData(obj2018.getAmountJan(),obj2018.getAmountFeb(),obj2018.getAmountMar(),obj2018.getAmountApr(),
                                            obj2018.getAmountMay(),obj2018.getAmountJun(), obj2018.getAmountJul(),obj2018.getAmountAug(),
                                            obj2018.getAmountSep(),obj2018.getAmountOct(), obj2018.getAmountNov(),obj2018.getAmountDec(), mChart);

                                } catch (ParseException e) {
                                    Log.e("ParseException",e.getMessage());
                                }
                                // store in preferences the boolean to set the circle and the
                                // values from the number pickers.
                                SharedPreferences prefers = getSharedPreferences(PREFERENCES,
                                        Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefers.edit();
                                editor.putBoolean(IS_PAYMENT_CIRCLE_ANNUAL, true);
                                editor.putInt(VALUEFROMNUMPICKER1ANNUAL, valueFromNumPicker1);
                                editor.apply();

                            }
                        })
                .setNegativeButton(CANCEL,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        })
                .setNeutralButton(RESET,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                isPaymentCircleSetAnnual = false;
                                valueFromNumPicker1 = 0;

                                util.readTheFile();
                                AmountsFor2018 obj2018Updated = util.getObjectYear().getYear2018();
                                util.setData(obj2018Updated.getAmountJan(),obj2018Updated.getAmountFeb(),obj2018Updated.getAmountMar(),obj2018Updated.getAmountApr(),
                                        obj2018Updated.getAmountMay(),obj2018Updated.getAmountJun(), obj2018Updated.getAmountJul(),obj2018Updated.getAmountAug(),
                                        obj2018Updated.getAmountSep(),obj2018Updated.getAmountOct(), obj2018Updated.getAmountNov(),obj2018Updated.getAmountDec(), mChart);

                                // store in preferences the boolean to set the circle and the
                                // values from the number pickers.
                                SharedPreferences prefers = getSharedPreferences(PREFERENCES,
                                        Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefers.edit();
                                editor.putBoolean(IS_PAYMENT_CIRCLE_ANNUAL, false);
                                editor.putInt(VALUEFROMNUMPICKER1ANNUAL, valueFromNumPicker1);
                                editor.apply();
                                dialog.cancel();
                            }
                        });
        alertDialogBuilder.create();
        alertDialogBuilder.show();
    }

    @SuppressWarnings("StringConcatenationInLoop")
    public void readTheFileToRecalculateMonthExpensesDueToIncomeChangeCircle(int salaryDay,
                                                                             String currentMonth, AmountsFor2018 obj2018)
            throws ParseException {

        String fileLine = "";
        String amount = "";
        String date = "";

        try {
            InputStream inputStream = new FileInputStream("/data/data/uk.co.irokottaki" +
                    ".moneycontrol/files/expenses.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            int lineIndex = 0;//this is to count the lines
            while ((line = br.readLine()) != null) {

                if (lineIndex == 0) {
                    fileLine = line + "\n" + "\n";// need to catch the space and write only one
                    // space after the header.
                }

                if (++lineIndex > 2 && !line.equals("") && !line.equals("r")) {
                    fileLine += line + "\n";
                    int index = line.lastIndexOf(" ");
                    amount = line.substring(0, line.indexOf(" "));
                    date = line.substring(index, line.length());
                    String extractDayFromDate = date.substring(0, date.indexOf("/"));
                    //convert String to int but first take the second character e.g. take 5 from 05
                    int extractDayFromDateInt = Integer.parseInt(extractDayFromDate.trim()
                            .replaceFirst("^0+(?!$)", ""));
                    String extractMonthFromDate = date.substring(date.indexOf("/") + 1, date
                            .lastIndexOf("/"));
                    if (extractMonthFromDate.startsWith("0")) {
                        extractMonthFromDate = extractMonthFromDate.replace("0", "");
                    }
                    String extractYearFromDate = date.substring(date.lastIndexOf("/") + 1, date
                            .length());

                    final Calendar calendar = Calendar.getInstance();
                    java.util.Date currentDate = new SimpleDateFormat("MMM", Locale.ENGLISH)
                            .parse(currentMonth);
                    calendar.setTime(currentDate);// here i convert the String month in an
                    // integer to be used on the switch-case
                    int monthInt = calendar.get(Calendar.MONTH) + 1;
                    int currentYear = Calendar.getInstance().get(Calendar.YEAR);

                    if (salaryDay != 0 && currentMonth != null) {
                        if (salaryDay <= extractDayFromDateInt &&
                                extractMonthFromDate.equals(String.valueOf(monthInt - 1)) &&
                                currentYear == Integer.parseInt(extractYearFromDate)) {
                            /*if the salary day set by the user is before the day of the expense
                            made and the expense made in the
                            previous month then the expense is added in the current month*/
                            addAmountToCurrentOrNextMonth(Float.valueOf(amount), monthInt, obj2018);
                        }
                            /*else if the salary day set by the user is after the day of the
                            expense made and the expense made
                            on the current month then the expense amount is added in the current
                            month*/
                        else if (salaryDay > extractDayFromDateInt &&
                                extractMonthFromDate.equals(String.valueOf(monthInt)) &&
                                currentYear == Integer.parseInt(extractYearFromDate)) {

                            addAmountToCurrentOrNextMonth(Float.valueOf(amount), monthInt,obj2018);
                        } else if (salaryDay <= extractDayFromDateInt &&
                                extractMonthFromDate.equals(String.valueOf(monthInt)) &&
                                currentYear == Integer.parseInt(extractYearFromDate)) {
                            /*if the day set by the user is before the day of the expense made
                            and the expense made in the
                            current month then the expense is added in the next month*/
                            addAmountToCurrentOrNextMonth(Float.valueOf(amount), monthInt + 1,obj2018);
                        } else if (salaryDay > extractDayFromDateInt &&
                                extractMonthFromDate.equals(String.valueOf(monthInt - 1)) &&
                                currentYear == Integer.parseInt(extractYearFromDate)) {
                            /*if the day set by the user is after the day of the expense made and
                             the expense made in the
                            previous month then the expense is added in the previous month*/
                            addAmountToCurrentOrNextMonth(Float.valueOf(amount), monthInt - 1,obj2018);

                        }
                    }
                }
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            Log.e("File not found",e.getMessage());
        } catch (IOException e) {
            Log.e("IOException",e.getMessage());
        }
    }

    private void addAmountToCurrentOrNextMonth(Float amount, int monthInt, AmountsFor2018 obj2018) {

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
        }

    }

    private void resetExpenseOfCurrentMonth(int currentMonth, AmountsFor2018 obj2018) {

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
        }
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        final Bundle bundle = new Bundle();
        final Intent intent = new Intent(this, MainActivity.class);

        bundle.putInt(ANNUALCHART, 2);
        intent.putExtras(bundle);

        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_annual_chart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onChartGestureStart(MotionEvent motionEvent, ChartTouchListener.ChartGesture
            chartGesture) {
        /*Not used*/
    }

    @Override
    public void onChartGestureEnd(MotionEvent motionEvent, ChartTouchListener.ChartGesture
            chartGesture) {
        /*Not used*/
    }

    @Override
    public void onChartLongPressed(MotionEvent motionEvent) {
        /*Not used*/
    }

    @Override
    public void onChartDoubleTapped(MotionEvent motionEvent) {
        /*Not used*/
    }

    @Override
    public void onChartSingleTapped(MotionEvent motionEvent) {
        /*Not used*/
    }

    @Override
    public void onChartFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        /*Not used*/
    }

    @Override
    public void onChartScale(MotionEvent motionEvent, float v, float v1) {
        /*Not used*/
    }

    @Override
    public void onChartTranslate(MotionEvent motionEvent, float v, float v1) {
        /*Not used*/
    }

    @Override
    public void onValueSelected(Entry entry, int i, Highlight highlight) {
        /*Not used*/
    }

    @Override
    public void onNothingSelected() {
        /*Not used*/
    }
}
