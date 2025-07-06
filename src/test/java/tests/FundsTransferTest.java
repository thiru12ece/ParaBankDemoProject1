package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.FundsTransferPage;

public class FundsTransferTest extends BaseTest {

    private FundsTransferPage fundsTransferPage;

    @BeforeClass
    public void setUpPages() {
        fundsTransferPage = new FundsTransferPage(driver);
    }

    @Test
    public void testValidFundTransfer() {
    	fundsTransferPage.navigateToTransferFunds();
        fundsTransferPage.transferFunds("13344", "13345", "100");
        Assert.assertTrue(fundsTransferPage.isTransferSuccessful(), "Transfer should be successful");
    }

    @Test
    public void testInvalidFundTransfer() {
    	fundsTransferPage.navigateToTransferFunds();
        // Invalid amount (like empty or negative)
        fundsTransferPage.transferFunds("13344", "13345", "testFund");
        Assert.assertTrue(fundsTransferPage.isTransferFailed(), "An internal error has occurred and has been logged.");
    }
}
