package com.signifyd.challenge;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;


public class SolutionTest {

    CustomerHistory customerHistory;

    @Before
    public void setup() {
        customerHistory = new CustomerHistory("dude@test.com");
    }

    @Test
    public void testNoHistory() {
        assertEquals("NO_HISTORY", customerHistory.getStatus());
    }

    @Test
    public void testFraudHistory() {
        customerHistory.enterFraudReport(new Date());
        assertEquals("FRAUD_HISTORY:1", customerHistory.getStatus());
    }

    @Test
    public void testThreeFraudHistory() {
        customerHistory.enterFraudReport(new Date());
        customerHistory.enterFraudReport(new Date());
        customerHistory.enterFraudReport(new Date());
        assertEquals("FRAUD_HISTORY:3", customerHistory.getStatus());
    }

    @Test
    public void testUnconfirmedHistory() {
        customerHistory.enterPurchase(new Date());
        assertEquals("UNCONFIRMED_HISTORY:1", customerHistory.getStatus());
    }

    @Test
    public void testThreeUnconfirmedHistory() {
        customerHistory.enterPurchase(new Date());
        customerHistory.enterPurchase(new Date());
        customerHistory.enterPurchase(new Date());
        assertEquals("UNCONFIRMED_HISTORY:3", customerHistory.getStatus());
    }

    @Test
    public void testGoodHistory() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -4);
        Date twoMonthsAgo = cal.getTime();
        customerHistory.enterPurchase(twoMonthsAgo);
        assertEquals("GOOD_HISTORY:1", customerHistory.getStatus());
    }

    @Test
    public void testThreeGoodHistory() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -4);
        Date fourMonthsAgo = cal.getTime();
        customerHistory.enterPurchase(fourMonthsAgo);
        customerHistory.enterPurchase(fourMonthsAgo);
        customerHistory.enterPurchase(fourMonthsAgo);
        assertEquals("GOOD_HISTORY:3", customerHistory.getStatus());
    }

    @Test
    public void testTwoGoodHistoryMixed() {
        customerHistory.enterPurchase(new Date());
        customerHistory.enterPurchase(new Date());

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -4);
        Date fourMonthsAgo = cal.getTime();
        customerHistory.enterPurchase(fourMonthsAgo);
        customerHistory.enterPurchase(fourMonthsAgo);
        assertEquals("GOOD_HISTORY:2", customerHistory.getStatus());
    }
}
