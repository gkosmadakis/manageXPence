package uk.co.irokottaki.moneycontrol.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MultiMap extends HashMap<String, List<String>> {

    private static final long serialVersionUID = 1L;

    public void put(String key, String value) {
        List<String> current = get(key);
        if (current == null) {
            current = new ArrayList<>();
            super.put(key, current);
        }
        current.add(value);
    }
}
