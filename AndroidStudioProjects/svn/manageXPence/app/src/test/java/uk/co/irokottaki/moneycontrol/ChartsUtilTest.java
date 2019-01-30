package uk.co.irokottaki.moneycontrol;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;

import uk.co.irokottaki.moneycontrol.activity.ReportActivity;
import uk.co.irokottaki.moneycontrol.model.AnyYear;
import uk.co.irokottaki.moneycontrol.model.YearToSet;
import uk.co.irokottaki.moneycontrol.utils.ChartsUtil;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class ChartsUtilTest {
    private static final double DELTA = 1e-15;
    private ChartsUtil chartsUtill;

    @Test
    public void testAddmountToCurrentOrNextMonth(){

        YearToSet yearToSet = new YearToSet(0.0f, 0.0f, 0.0f,0.0f, 0.0f, 0.0f,0.0f ,0.0f, 0.0f ,0.0f ,0.0f ,0.0f );
        final Context context = Mockito.mock(Context.class);
        chartsUtill = new ChartsUtil(context);

        chartsUtill.addAmountToCurrentOrNextMonth(100f, 1,yearToSet);
        assertEquals(100, yearToSet.getAmountJan(), DELTA);

        chartsUtill.addAmountToCurrentOrNextMonth(122f, 2,yearToSet);
        assertEquals(122, yearToSet.getAmountFeb(), DELTA);

        chartsUtill.addAmountToCurrentOrNextMonth(33f, 3,yearToSet);
        assertEquals(33, yearToSet.getAmountMar(), DELTA);

        chartsUtill.addAmountToCurrentOrNextMonth(33.5f, 4,yearToSet);
        assertEquals(33.5, yearToSet.getAmountApr(), DELTA);

        chartsUtill.addAmountToCurrentOrNextMonth(2f, 5,yearToSet);
        assertEquals(2, yearToSet.getAmountMay(), DELTA);

        chartsUtill.addAmountToCurrentOrNextMonth(55f, 6,yearToSet);
        assertEquals(55, yearToSet.getAmountJun(), DELTA);

        chartsUtill.addAmountToCurrentOrNextMonth(44f, 7,yearToSet);
        assertEquals(44, yearToSet.getAmountJul(), DELTA);

        chartsUtill.addAmountToCurrentOrNextMonth(333f, 8,yearToSet);
        assertEquals(333, yearToSet.getAmountAug(), DELTA);

        chartsUtill.addAmountToCurrentOrNextMonth(22f, 9,yearToSet);
        assertEquals(22, yearToSet.getAmountSep(), DELTA);

        chartsUtill.addAmountToCurrentOrNextMonth(50.5f, 10,yearToSet);
        assertEquals(50.5, yearToSet.getAmountOct(), DELTA);

        chartsUtill.addAmountToCurrentOrNextMonth(109f, 11,yearToSet);
        assertEquals(109, yearToSet.getAmountNov(), DELTA);

        chartsUtill.addAmountToCurrentOrNextMonth(500f, 12,yearToSet);
        assertEquals(500, yearToSet.getAmountDec(), DELTA);
    }

    @Test
    public void testSwitchMonthsReport(){

        HashMap<String, AnyYear> yearsMappedToObjectYearsMap = new HashMap<>();

        final Context context = Mockito.mock(Context.class);
        final Activity mMockReportActivity = Mockito.mock(ReportActivity.class);
        final TextView reportView = Mockito.mock(TextView.class);
        when(((ReportActivity) mMockReportActivity).getReportView()).thenReturn(reportView);
        chartsUtill = new ChartsUtil(context);

        YearToSet yearToSet = new YearToSet(0.0f, 0.0f, 0.0f,0.0f, 0.0f, 0.0f,0.0f ,0.0f, 0.0f ,0.0f ,0.0f ,0.0f );
        AnyYear objectYear = new AnyYear(yearToSet);
        objectYear.setAmountAndDescJan(0f, new LinkedHashSet(), new ArrayList<Float>(), "15      SuperMarket    01/01/2019");
        objectYear.setAmountAndDescFeb(0f, new LinkedHashSet(), new ArrayList<Float>(), "25      SuperMarket    01/02/2019");
        objectYear.setAmountAndDescMar(0f, new LinkedHashSet(), new ArrayList<Float>(), "20      SuperMarket    01/03/2019");
        objectYear.setAmountAndDescApr(0f, new LinkedHashSet(), new ArrayList<Float>(), "30      SuperMarket    01/04/2019");
        objectYear.setAmountAndDescMay(0f, new LinkedHashSet(), new ArrayList<Float>(), "40      SuperMarket    01/05/2019");
        objectYear.setAmountAndDescJun(0f, new LinkedHashSet(), new ArrayList<Float>(), "50      SuperMarket    01/06/2019");
        objectYear.setAmountAndDescJul(0f, new LinkedHashSet(), new ArrayList<Float>(), "60      SuperMarket    01/07/2019");
        objectYear.setAmountAndDescAug(0f, new LinkedHashSet(), new ArrayList<Float>(), "70      SuperMarket    01/08/2019");
        objectYear.setAmountAndDescSep(0f, new LinkedHashSet(), new ArrayList<Float>(), "80      SuperMarket    01/09/2019");
        objectYear.setAmountAndDescOct(0f, new LinkedHashSet(), new ArrayList<Float>(), "90      SuperMarket    01/10/2019");
        objectYear.setAmountAndDescNov(0f, new LinkedHashSet(), new ArrayList<Float>(), "100     SuperMarket    01/11/2019");
        objectYear.setAmountAndDescDec(0f, new LinkedHashSet(), new ArrayList<Float>(), "110     SuperMarket    01/12/2019");

        yearsMappedToObjectYearsMap.put("2019", objectYear);
        String formatStr = "%-8s%-15s%-10s";

        chartsUtill.switchMonthsReport(yearsMappedToObjectYearsMap, 1, 2019, mMockReportActivity);
        String expectedValue = String.format(formatStr,"Amount", "Description", "Date")+"\n"+"\n"+"15      SuperMarket    01/01/2019"+"\n";
        assertEquals(expectedValue, chartsUtill.formatReportArea("15      SuperMarket    01/01/2019").toString());

        chartsUtill.switchMonthsReport(yearsMappedToObjectYearsMap, 2, 2019, mMockReportActivity);
        expectedValue = String.format(formatStr,"Amount", "Description", "Date")+"\n"+"\n"+"25      SuperMarket    01/02/2019"+"\n";
        assertEquals(expectedValue, chartsUtill.formatReportArea("25      SuperMarket    01/02/2019").toString());

        chartsUtill.switchMonthsReport(yearsMappedToObjectYearsMap, 3, 2019, mMockReportActivity);
        expectedValue = String.format(formatStr,"Amount", "Description", "Date")+"\n"+"\n"+"20      SuperMarket    01/03/2019"+"\n";
        assertEquals(expectedValue, chartsUtill.formatReportArea("20      SuperMarket    01/03/2019").toString());

        chartsUtill.switchMonthsReport(yearsMappedToObjectYearsMap, 4, 2019, mMockReportActivity);
        expectedValue = String.format(formatStr,"Amount", "Description", "Date")+"\n"+"\n"+"30      SuperMarket    01/04/2019"+"\n";
        assertEquals(expectedValue, chartsUtill.formatReportArea("30      SuperMarket    01/04/2019").toString());

        chartsUtill.switchMonthsReport(yearsMappedToObjectYearsMap, 5, 2019, mMockReportActivity);
        expectedValue = String.format(formatStr,"Amount", "Description", "Date")+"\n"+"\n"+"40      SuperMarket    01/05/2019"+"\n";
        assertEquals(expectedValue, chartsUtill.formatReportArea("40      SuperMarket    01/05/2019").toString());

        chartsUtill.switchMonthsReport(yearsMappedToObjectYearsMap, 6, 2019, mMockReportActivity);
        expectedValue = String.format(formatStr,"Amount", "Description", "Date")+"\n"+"\n"+"50      SuperMarket    01/06/2019"+"\n";
        assertEquals(expectedValue, chartsUtill.formatReportArea("50      SuperMarket    01/06/2019").toString());

        chartsUtill.switchMonthsReport(yearsMappedToObjectYearsMap, 7, 2019, mMockReportActivity);
        expectedValue = String.format(formatStr,"Amount", "Description", "Date")+"\n"+"\n"+"60      SuperMarket    01/07/2019"+"\n";
        assertEquals(expectedValue, chartsUtill.formatReportArea("60      SuperMarket    01/07/2019").toString());

        chartsUtill.switchMonthsReport(yearsMappedToObjectYearsMap, 8, 2019, mMockReportActivity);
        expectedValue = String.format(formatStr,"Amount", "Description", "Date")+"\n"+"\n"+"70      SuperMarket    01/08/2019"+"\n";
        assertEquals(expectedValue, chartsUtill.formatReportArea("70      SuperMarket    01/08/2019").toString());

        chartsUtill.switchMonthsReport(yearsMappedToObjectYearsMap, 9, 2019, mMockReportActivity);
        expectedValue = String.format(formatStr,"Amount", "Description", "Date")+"\n"+"\n"+"80      SuperMarket    01/09/2019"+"\n";
        assertEquals(expectedValue, chartsUtill.formatReportArea("80      SuperMarket    01/09/2019").toString());

        chartsUtill.switchMonthsReport(yearsMappedToObjectYearsMap, 10, 2019, mMockReportActivity);
        expectedValue = String.format(formatStr,"Amount", "Description", "Date")+"\n"+"\n"+"90      SuperMarket    01/10/2019"+"\n";
        assertEquals(expectedValue, chartsUtill.formatReportArea("90      SuperMarket    01/10/2019").toString());

        chartsUtill.switchMonthsReport(yearsMappedToObjectYearsMap, 11, 2019, mMockReportActivity);
        expectedValue = String.format(formatStr,"Amount", "Description", "Date")+"\n"+"\n"+"100     SuperMarket    01/11/2019"+"\n";
        assertEquals(expectedValue, chartsUtill.formatReportArea("100      SuperMarket    01/11/2019").toString());

        chartsUtill.switchMonthsReport(yearsMappedToObjectYearsMap, 12, 2019, mMockReportActivity);
        expectedValue = String.format(formatStr,"Amount", "Description", "Date")+"\n"+"\n"+"110     SuperMarket    01/12/2019"+"\n";
        assertEquals(expectedValue, chartsUtill.formatReportArea("110      SuperMarket    01/12/2019").toString());

    }
}
