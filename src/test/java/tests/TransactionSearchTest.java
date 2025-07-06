package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.TransactionSearchPage;

public class TransactionSearchTest extends BaseTest {

    private TransactionSearchPage transactionPage;

    @BeforeClass
    public void setup() {
        transactionPage = new TransactionSearchPage(driver);
    }

    @Test
    public void testSearchByTransactionId() {

        transactionPage.selectAccount("13344");
        transactionPage.searchByTransactionId("12345");

        Assert.assertTrue(transactionPage.isResultFound() || transactionPage.isNoResultMessageShown());
    }

    @Test
    public void testSearchByDate() {

        transactionPage.selectAccount("13344");
        transactionPage.searchByDate("07-04-2025");

        Assert.assertTrue(transactionPage.isResultFound() || transactionPage.isNoResultMessageShown());
    }

    @Test
    public void testSearchByDateRange() {

        transactionPage.selectAccount("13344");
        transactionPage.searchByDateRange("07-01-2025", "07-05-2025");

        Assert.assertTrue(transactionPage.isResultFound() || transactionPage.isNoResultMessageShown());
    }

    @Test
    public void testSearchByAmount() {

        transactionPage.selectAccount("13344");
        transactionPage.searchByAmount("100");

        Assert.assertTrue(transactionPage.isResultFound() || transactionPage.isNoResultMessageShown());
    }
}
