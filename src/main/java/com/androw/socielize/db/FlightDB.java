package com.androw.socielize.db;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Androw on 12/08/2014.
 */
public class FlightDB {
    private List<String> iatas;

    public FlightDB() {
        String data = "AA, AB, AC, AD, AE, AF, AG, AH, AI, DB";
        this.iatas = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(data, ",");
        while (st.hasMoreTokens()) {
            this.iatas.add(st.nextToken().trim());
        }
    }

    public List<String> getIATAList(String query) {
        String iata = null;
        query = query.toLowerCase();
        List<String> matched = new ArrayList<String>();
        for (int i = 0; i < this.iatas.size(); i++) {
            iata = this.iatas.get(i).toLowerCase();
            if (iata.startsWith(query)) matched.add(this.iatas.get(i));
        }
        return matched;
    }
}
