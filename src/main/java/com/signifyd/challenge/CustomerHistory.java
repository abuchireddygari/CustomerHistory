package com.signifyd.challenge;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CustomerHistory {
    String customerAccount;

    ArrayList<Date> purchaseHistory = new ArrayList<Date>();
    ArrayList<Date> fraudHistory = new ArrayList<Date>();

    public CustomerHistory (String customerAccount) {
        this.customerAccount = customerAccount;
    }

    public String getStatus() {
        if(purchaseHistory.isEmpty() && fraudHistory.isEmpty()) {
            return "NO_HISTORY";
        } else if(!fraudHistory.isEmpty()){
            return "FRAUD_HISTORY:" + fraudHistory.size();
        } else {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, -90);
            Date ninetyDaysAgo = cal.getTime();
            int confirmed = 0;
            int unconfirmed = 0;
            for(Date purchaseDate: purchaseHistory) {
                if (purchaseDate.after(ninetyDaysAgo)) {
                    unconfirmed++;
                } else {
                    confirmed++;
                }
            }
            if(confirmed == 0) {
                return "UNCONFIRMED_HISTORY:" + unconfirmed;
            } else {
                return "GOOD_HISTORY:" + confirmed;
            }

        }
    }

    public void enterPurchase(Date date) {
        purchaseHistory.add(date);
    }

    public void enterFraudReport(Date date) {
        fraudHistory.add(date);
    }

    public String getCustomerAccount() {
        return customerAccount;
    }


}
