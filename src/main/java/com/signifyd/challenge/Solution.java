package com.signifyd.challenge;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {

    Map<String, CustomerHistory> customerList = new HashMap<String, CustomerHistory>();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    private void solve(List<CSVRecord> records) {
        for (CSVRecord record : records) {
            Date date = null;
            try {
                date = df.parse(record.get(0));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String customer = record.get(1);
            String status = record.get(2);

            if (status.equals("PURCHASE")) {
                CustomerHistory ch = customerList.get(customer);
                if(ch == null) { //New customer
                    ch = new CustomerHistory(customer);
                    customerList.put(customer,ch);
                }
                ch.enterPurchase(date);
                System.out.println(date + "," + customer + "," + ch.getStatus());
            } else if (status.equals("FRAUD_REPORT")) {
                CustomerHistory ch = customerList.get(customer);
                if(ch == null) { //New customer
                    ch = new CustomerHistory(customer);
                    customerList.put(customer,ch);
                }
                ch.enterFraudReport(date);
            } else {
                System.out.println("BAD REPORT: " + status);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Expecting a singular file argument.");
            return;
        }

        CSVParser parser = CSVParser.parse(new File(args[0]), StandardCharsets.UTF_8, CSVFormat.DEFAULT);
        List<CSVRecord> record = parser.getRecords();
        Solution solution = new Solution();
        solution.solve(record);

        // You can find the CSVRecord documentation at:
        // https://commons.apache.org/proper/commons-csv/apidocs/org/apache/commons/csv/CSVRecord.html

        // If you are using Java 8, you can parse the LocalDate from the record by:
        // java.time.LocalDate.parse(record.get(0))
    }
}
