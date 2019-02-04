package uk.co.irokottaki.moneycontrol;

import org.junit.Test;

import java.util.ArrayList;

import uk.co.irokottaki.moneycontrol.utils.MultiMap;

import static org.junit.Assert.assertEquals;

public class MultiMapTests {

    @Test
    public void testPutInMap(){

        MultiMap testMap = new MultiMap();
        testMap.put("Test", "10 Test 04/02/2019");

        ArrayList<String> value = new ArrayList();
        value.add("10 Test 04/02/2019");

        assertEquals(value, testMap.get("Test"));

        testMap.put("Test", "20 Test 05/02/2019");
        value.add("20 Test 05/02/2019");
        assertEquals(value, testMap.get("Test"));
    }
}
