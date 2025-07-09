package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.TransactionSearchPage;
import utils.ExtentTestManager;

public class TransactionSearchTest extends BaseTest {

    private TransactionSearchPage transactionPage;

    @BeforeClass
    public void setup() {
        transactionPage = new TransactionSearchPage(driver);
    }

    @Test
    public void testSearchByTransactionId() {
        ExtentTestManager.getTest().info("Selecting account: 13344");
        transactionPage.selectAccount("13344");

        ExtentTestManager.getTest().info("Searching by transaction ID: 12345");
        transactionPage.searchByTransactionId("12345");

        Assert.assertTrue(
            transactionPage.isResultFound() || transactionPage.isNoResultMessageShown(),
            "Result should be found or 'No transactions found' message should be displayed"
        );
    }

    @Test
    public void testSearchByDate() {
        ExtentTestManager.getTest().info("Selecting account: 13344");
        transactionPage.selectAccount("13344");

        ExtentTestManager.getTest().info("Searching by date: 07-04-2025");
        transactionPage.searchByDate("07-04-2025");

        Assert.assertTrue(
            transactionPage.isResultFound() || transactionPage.isNoResultMessageShown(),
            "Result should be found or 'No transactions found' message should be displayed"
        );
    }

    @Test
    public void testSearchByDateRange() {
        ExtentTestManager.getTest().info("Selecting account: 13344");
        transactionPage.selectAccount("13344");

        ExtentTestManager.getTest().info("Searching by date range: 07-01-2025 to 07-05-2025");
        transactionPage.searchByDateRange("07-01-2025", "07-05-2025");

        Assert.assertTrue(
            transactionPage.isResultFound() || transactionPage.isNoResultMessageShown(),
            "Result should be found or 'No transactions found' message should be displayed"
        );
    }

    @Test
    public void testSearchByAmount() {
        ExtentTestManager.getTest().info("Selecting account: 13344");
        transactionPage.selectAccount("13344");

        ExtentTestManager.getTest().info("Searching by amount: 100");
        transactionPage.searchByAmount("100");

        Assert.assertTrue(
            transactionPage.isResultFound() || transactionPage.isNoResultMessageShown(),
            "Result should be found or 'No transactions found' message should be displayed"
        );
    }
}
