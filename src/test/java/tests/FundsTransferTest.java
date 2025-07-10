package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.FundsTransferPage;
import utils.ExtentTestManager;

public class FundsTransferTest extends BaseTest {

    private FundsTransferPage fundsTransferPage;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        fundsTransferPage = new FundsTransferPage(driver);
    }

    @Test
    public void testValidFundTransfer() {
    	
        ExtentTestManager.getTest().info("Starting valid fund transfer from 13344 to 13345");
        
        fundsTransferPage.navigateToTransferFunds();

        fundsTransferPage.transferFunds("13344", "13345", "100");

        ExtentTestManager.getTest().info("Validating success message");
        Assert.assertTrue(fundsTransferPage.isTransferSuccessful(), "✅ Transfer should be successful");
    }

    @Test
    public void testInvalidFundTransfer() {
        ExtentTestManager.getTest().info("Starting invalid fund transfer with non-numeric amount");
        fundsTransferPage.navigateToTransferFunds();

        fundsTransferPage.transferFunds("13344", "13345", "testFund");

        ExtentTestManager.getTest().info("Validating failure message");
        Assert.assertTrue(fundsTransferPage.isTransferFailed(), "❌ Transfer should fail due to invalid amount");
    }
}
