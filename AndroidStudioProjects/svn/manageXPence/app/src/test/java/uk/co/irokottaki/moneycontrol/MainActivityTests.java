package uk.co.irokottaki.moneycontrol;

import org.junit.Test;

import uk.co.irokottaki.moneycontrol.activity.MainActivity;
import uk.co.irokottaki.moneycontrol.model.YearToSet;

import static org.junit.Assert.assertEquals;


public class MainActivityTests {
    private static final double DELTA = 1e-15;

    @Test
    public void testResetExpenseOfCurrentMonth(){
        MainActivity activity = new MainActivity();
        YearToSet yearToSet = new YearToSet(0.0f, 0.0f, 0.0f,0.0f, 0.0f, 0.0f,0.0f ,0.0f, 0.0f ,0.0f ,0.0f ,0.0f );

        activity.resetExpenseOfCurrentMonth(3, yearToSet);
        assertEquals(0.0, (double)yearToSet.getAmountMar(), DELTA);

        activity.resetExpenseOfCurrentMonth(4, yearToSet);
        assertEquals(0.0, (double)yearToSet.getAmountApr(), DELTA);

        activity.resetExpenseOfCurrentMonth(5, yearToSet);
        assertEquals(0.0, (double)yearToSet.getAmountMay(), DELTA);

        activity.resetExpenseOfCurrentMonth(6, yearToSet);
        assertEquals(0.0, (double)yearToSet.getAmountJun(), DELTA);

        activity.resetExpenseOfCurrentMonth(7, yearToSet);
        assertEquals(0.0, (double)yearToSet.getAmountJul(), DELTA);

        activity.resetExpenseOfCurrentMonth(8, yearToSet);
        assertEquals(0.0, (double)yearToSet.getAmountAug(), DELTA);

        activity.resetExpenseOfCurrentMonth(9, yearToSet);
        assertEquals(0.0, (double)yearToSet.getAmountSep(), DELTA);

        activity.resetExpenseOfCurrentMonth(10, yearToSet);
        assertEquals(0.0, (double)yearToSet.getAmountOct(), DELTA);

        activity.resetExpenseOfCurrentMonth(11, yearToSet);
        assertEquals(0.0, (double)yearToSet.getAmountNov(), DELTA);

        activity.resetExpenseOfCurrentMonth(12, yearToSet);
        assertEquals(0.0, (double)yearToSet.getAmountDec(), DELTA);
    }

}
