package uk.co.irokottaki.moneycontrol.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
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
import java.util.Calendar;
import java.util.HashMap;

import uk.co.irokottaki.moneycontrol.model.AnyYear;
import uk.co.irokottaki.moneycontrol.utils.ChartsUtil;
import uk.co.irokottaki.moneycontrol.R;
import uk.co.irokottaki.moneycontrol.utils.Utils;

import static uk.co.irokottaki.moneycontrol.utils.Constants.*;

public class AnnualSavingsActivity extends AppCompatActivity implements OnChartGestureListener, OnChartValueSelectedListener {

    private LineChart mChart;
    ImageButton leftYearButton;
    ImageButton rightYearButton;
    private int year;
    private TextView yearView;
    private ChartsUtil util;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annual_savings);
        setTitle(SAVINGS_CHART);

        //READ THE FILE AND GET THE AMOUNTS FOR EVERY MONTH/YEAR
        util = new ChartsUtil(this);
        Intent intent = getIntent();
        HashMap<String,AnyYear> yearsMappedToObjectYearsMap = (HashMap<String, AnyYear>) intent.getSerializableExtra(YEARS_MAPPED_TO_OBJECT_YEARS_MAP);


        RelativeLayout layout = (RelativeLayout) findViewById(R.id.annualSavingsView);

        //this is to change the background color of the activity when user changes it from settings
        Utils utils = new Utils(this);
        utils.setBackgroundAndAdjustLayout(layout, AnnualSavingsActivity.this);

        mChart = (LineChart) findViewById(R.id.annualSavingsLayout);
        mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);

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

        //Year Label
        yearView = (TextView) findViewById(R.id.yearlabelSavings);

        util.setXYAxisForChart(mChart);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);// get the current year
        yearView.setText(YEAR + year);

        //i am just setting the data for 2017 that will be displayed on initializing the activity
        // . Obviously it should be changed every year
        util.setSavings(yearsMappedToObjectYearsMap, year, AnnualSavingsActivity.this);

        //Listener to events on clicking the image arrows
        leftYearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                HashMap<String,AnyYear> yearsMappedToObjectYearsMap = (HashMap<String, AnyYear>) intent.getSerializableExtra(YEARS_MAPPED_TO_OBJECT_YEARS_MAP);

                year--;
                yearView.setText(YEAR + year);
                util.setSavings(yearsMappedToObjectYearsMap, year, AnnualSavingsActivity.this);
            }
        });

        rightYearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                HashMap<String,AnyYear> yearsMappedToObjectYearsMap = (HashMap<String, AnyYear>) intent.getSerializableExtra(YEARS_MAPPED_TO_OBJECT_YEARS_MAP);

                year++;
                yearView.setText(YEAR + year);
                util.setSavings(yearsMappedToObjectYearsMap, year, AnnualSavingsActivity.this);
            }
        });

        mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend
        l.setForm(Legend.LegendForm.LINE);

    }// end of onCreate

    public LineChart getmChart() {
        return mChart;
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
