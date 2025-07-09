package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.AccountPage;
import utils.ExtentTestManager;

public class AccountTest extends BaseTest {

    private AccountPage accountPage;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        accountPage = new AccountPage(driver); // ✅ ThreadLocal-safe driver
    }

    @Test
    public void viewCheckingAccountSummary() throws InterruptedException {
    	
        ExtentTestManager.getTest().info("Viewing CHECKING account summary");

        accountPage.navigateToAccountsOverview();
        
        accountPage.selectAccountByType("CHECKING");

        ExtentTestManager.getTest().info("Validating balance is displayed");
        Assert.assertTrue(accountPage.isBalanceDisplayed(), "Balance should be visible");

        ExtentTestManager.getTest().info("Validating transactions table or message is displayed");
        Assert.assertTrue(accountPage.isRecentTransactionsDisplayed(), "Transactions or message should be visible");
    }

    @Test
    public void viewSavingsAccountSummary() throws InterruptedException {
    	
        ExtentTestManager.getTest().info("Viewing SAVINGS account summary");
        
        accountPage.navigateToAccountsOverview();
        
        accountPage.selectAccountByType("SAVINGS");

        ExtentTestManager.getTest().info("Validating balance is displayed");
        Assert.assertTrue(accountPage.isBalanceDisplayed(), "Balance should be visible");

        ExtentTestManager.getTest().info("Validating transactions table or message is displayed");
        Assert.assertTrue(accountPage.isRecentTransactionsDisplayed(), "Transactions or message should be visible");
    }
}
