package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.AccountPage;

public class AccountTest extends BaseTest {

    AccountPage accountPage;
    
    @BeforeClass
    public void setUp() {
        // Example assuming you already have 'driver' from BaseTest
        accountPage = new AccountPage(driver);  // ✅ Initialize it here
    }

    @Test
    public void viewCheckingAccountSummary() throws InterruptedException {
        accountPage.selectAccountByType("CHECKING");
        Assert.assertTrue(accountPage.isBalanceDisplayed());
        Assert.assertTrue(accountPage.isRecentTransactionsDisplayed());
    }

    @Test
    public void viewSavingsAccountSummary() throws InterruptedException {
    	driver.navigate().back();
        accountPage.selectAccountByType("SAVINGS");
        Assert.assertTrue(accountPage.isBalanceDisplayed());
        Assert.assertTrue(accountPage.isRecentTransactionsDisplayed());
    }
}
