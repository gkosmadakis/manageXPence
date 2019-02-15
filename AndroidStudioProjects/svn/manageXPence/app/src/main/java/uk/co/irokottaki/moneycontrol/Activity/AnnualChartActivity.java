package uk.co.irokottaki.moneycontrol.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
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
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import uk.co.irokottaki.moneycontrol.model.AnyYear;
import uk.co.irokottaki.moneycontrol.model.YearToSet;
import uk.co.irokottaki.moneycontrol.utils.ChartsUtil;
import uk.co.irokottaki.moneycontrol.R;
import uk.co.irokottaki.moneycontrol.utils.Utils;

import static uk.co.irokottaki.moneycontrol.utils.Constants.*;


public class AnnualChartActivity extends AppCompatActivity implements OnChartGestureListener,
        OnChartValueSelectedListener {
    private LineChart annualChart;
    ImageButton leftYearButton;
    ImageButton rightYearButton;
    private int year;
    private int valueFromNumPicker1;
    private NumberPicker numberPicker1;
    private boolean isPaymentCircleSetAnnual;
    private ChartsUtil util;
    private HashMap<String, AnyYear> yearsMappedToObjectYearsMap;
    private YearToSet yearToSet = null;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annual_chart);
        setTitle(ANNUAL_CHART);

        //READ THE FILE AND GET THE AMOUNTS FOR EVERY MONTH/YEAR
        util = new ChartsUtil(this);

        Intent intent = getIntent();
        yearsMappedToObjectYearsMap = (HashMap<String, AnyYear>) intent.getSerializableExtra(YEARS_MAPPED_TO_OBJECT_YEARS_MAP);


        RelativeLayout layout = (RelativeLayout) findViewById(R.id.annualChartView);
        SharedPreferences sharedprefs = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        isPaymentCircleSetAnnual = sharedprefs.getBoolean(IS_PAYMENT_CIRCLE_ANNUAL, false);
        //this is to change the background color of the activity when user changes it from settings
        Utils utils = new Utils(this);
        utils.setBackgroundAndAdjustLayout(layout, AnnualChartActivity.this);

        annualChart = (LineChart) findViewById(R.id.annualChartLayout);
        annualChart.setOnChartGestureListener(this);
        annualChart.setOnChartValueSelectedListener(this);
        annualChart.setDrawGridBackground(false);

        // no description text
        annualChart.setDescription("");
        annualChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable touch gestures
        annualChart.setTouchEnabled(true);

        // enable scaling and dragging
        annualChart.setDragEnabled(true);
        annualChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        annualChart.setPinchZoom(true);

        // Arrow buttons
        leftYearButton = new ImageButton(this);
        leftYearButton = (ImageButton) findViewById(R.id.leftYearButton);

        rightYearButton = new ImageButton(this);
        rightYearButton = (ImageButton) findViewById(R.id.rigthYearButton);

        //Circle Button
        TextView circleTextview = (TextView) findViewById(R.id.circleTextview);
        if(Build.VERSION.SDK_INT >= 24) {
            circleTextview.setText(Html.fromHtml("<font color=#0000FF> <u> Set Income Circle", 26));
        }
        else {
            circleTextview.setText(Html.fromHtml("<font color=#0000FF> <u> Set Income Circle"));
        }

        //Year Label
        final TextView yearView = (TextView) findViewById(R.id.yearlabel);

        //Bottom Label for each expense in a year
        TextView specificexpenseForYear = (TextView) findViewById(R.id.specificExpensesOfYear);
        specificexpenseForYear.setMovementMethod(LinkMovementMethod.getInstance());
        specificexpenseForYear.setText(Html.fromHtml("<font color=#0000FF> <u>Hom much do I spend" +
                " on a year for each expense?" +
                "</u></font>"));

        util.setXYAxisForChart(annualChart);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);// get the current year
        yearView.setText(YEAR + year);

        // Get the object of AnyYear from yearsMappedToObjectYearsMap returned from MainActivity to access the months data expenses
        AnyYear currentYear = yearsMappedToObjectYearsMap.get(String.valueOf(year));
        if (currentYear != null) {
            yearToSet = currentYear.getYear();

        if (isPaymentCircleSetAnnual) {
            String currentMonth = String.format(Locale.UK, "%tB", calendar);
            int monthInt = calendar.get(Calendar.MONTH) + 1;

            try {
                util.resetExpenseOfCurrentMonth(monthInt - 1, yearToSet);
                util.resetExpenseOfCurrentMonth(monthInt, yearToSet);
                util.resetExpenseOfCurrentMonth(monthInt + 1, yearToSet);
                //get the int values from number pickers
                valueFromNumPicker1 = sharedprefs.getInt(VALUEFROMNUMPICKER1ANNUAL,
                        valueFromNumPicker1);

                util.readTheFileToRecalculateMonthExpensesDueToIncomeChangeCircle(valueFromNumPicker1,
                        currentMonth, yearToSet);
            } catch (ParseException e) {
                Log.e("Parse Exception ", e.getMessage());
            }
            util.setData(yearToSet.getAmountJan(), yearToSet.getAmountFeb(), yearToSet.getAmountMar(), yearToSet.getAmountApr(),
                    yearToSet.getAmountMay(), yearToSet.getAmountJun(), yearToSet.getAmountJul(), yearToSet.getAmountAug(),
                    yearToSet.getAmountSep(), yearToSet.getAmountOct(), yearToSet.getAmountNov(), yearToSet.getAmountDec(), annualChart);

        } else {
            util.setData(yearToSet.getAmountJan(), yearToSet.getAmountFeb(), yearToSet.getAmountMar(), yearToSet.getAmountApr(),
                    yearToSet.getAmountMay(), yearToSet.getAmountJun(), yearToSet.getAmountJul(), yearToSet.getAmountAug(),
                    yearToSet.getAmountSep(), yearToSet.getAmountOct(), yearToSet.getAmountNov(), yearToSet.getAmountDec(), annualChart);

        }
        //listener of the income circle button
        circleTextview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showNumberPickerDialogOnButtonClick(yearToSet);
            }
        });
    }
    /*the expenses file is empty so just set to 0 the data*/
    else {
            util.setData(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, annualChart);
        }

        //Listener to events on clicking the image arrows
        leftYearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                HashMap<String, AnyYear> yearsMappedToObjectYearsMapRetrieved = (HashMap<String, AnyYear> ) intent.getSerializableExtra(YEARS_MAPPED_TO_OBJECT_YEARS_MAP);

                year--;
                util.switchAmountsBetweenYears(yearsMappedToObjectYearsMapRetrieved,year, yearView, AnnualChartActivity.this);
            }
        });

        rightYearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                HashMap<String, AnyYear> yearsMappedToObjectYearsMapRetrieved = (HashMap<String, AnyYear> ) intent.getSerializableExtra(YEARS_MAPPED_TO_OBJECT_YEARS_MAP);

                year++;
                util.switchAmountsBetweenYears(yearsMappedToObjectYearsMapRetrieved,year, yearView, AnnualChartActivity.this);
            }
        });

        annualChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);

        // get the legend (only possible after setting data)
        Legend l = annualChart.getLegend();

        // modify the legend
        l.setForm(Legend.LegendForm.LINE);

        specificexpenseForYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), CalculateAnnualExpensesActivity.class);
                intent.putExtra("yearsMappedToObjectYearsMap", yearsMappedToObjectYearsMap);
                startActivity(intent);
            }
        });
    }// end of onCreate

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {

            yearsMappedToObjectYearsMap = (HashMap<String, AnyYear>) data.getSerializableExtra("yearsMappedToObjectYearsMap");

        }
    }

    protected void showNumberPickerDialogOnButtonClick(final YearToSet yearToSet) {

        LayoutInflater inflater = LayoutInflater.from(AnnualChartActivity.this);
        View npView = inflater.inflate(R.layout.number_picker_dialog_annual_layout, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AnnualChartActivity
                .this, R.style.Theme_AppCompat_Light_Dialog);
        //number pickers
        numberPicker1 = npView.findViewById(R.id.numberPicker1);
        numberPicker1.setMaxValue(31);
        numberPicker1.setMinValue(1);
        numberPicker1.setWrapSelectorWheel(true);

        if ((valueFromNumPicker1 != 0)) {
            numberPicker1.setValue(valueFromNumPicker1);
        }

        final Calendar calendar = Calendar.getInstance();//this gets the
        // current month
        final String currentMonth = String.format(Locale.UK, "%tB", calendar);
        final int monthInt = calendar.get(Calendar.MONTH) + 1;

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

                                try {
                                    /*Set to zero the expenses sums of the current and next month*/
                                    util.resetExpenseOfCurrentMonth(monthInt - 1,yearToSet);
                                    util.resetExpenseOfCurrentMonth(monthInt,yearToSet);
                                    util.resetExpenseOfCurrentMonth(monthInt + 1,yearToSet);
                                    util.readTheFileToRecalculateMonthExpensesDueToIncomeChangeCircle
                                            (valueFromNumPicker1, currentMonth,yearToSet);
                                    util.setData(yearToSet.getAmountJan(),yearToSet.getAmountFeb(),yearToSet.getAmountMar(),yearToSet.getAmountApr(),
                                            yearToSet.getAmountMay(),yearToSet.getAmountJun(), yearToSet.getAmountJul(),yearToSet.getAmountAug(),
                                            yearToSet.getAmountSep(),yearToSet.getAmountOct(), yearToSet.getAmountNov(),yearToSet.getAmountDec(), annualChart);

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
                                // Get the object years to access the months data expenses
                                AnyYear currentYear = yearsMappedToObjectYearsMap.get(String.valueOf(year));
                                final YearToSet yearToSetNew = currentYear.getYear();
                                util.setData(yearToSetNew.getAmountJan(),yearToSetNew.getAmountFeb(),yearToSetNew.getAmountMar(),yearToSetNew.getAmountApr(),
                                        yearToSetNew.getAmountMay(),yearToSetNew.getAmountJun(), yearToSetNew.getAmountJul(),yearToSetNew.getAmountAug(),
                                        yearToSetNew.getAmountSep(),yearToSetNew.getAmountOct(), yearToSetNew.getAmountNov(),yearToSetNew.getAmountDec(), annualChart);

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

    public LineChart getAnnualChart() {
        return annualChart;
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
        switch (item.getItemId()) { // This is added to avoid the call on the onCreate method of MainActivity because it will read again the file and iterate the map
            case android.R.id.home: finish();
                return true;
        }
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
