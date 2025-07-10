package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.RequestLoanPage;
import utils.ExtentTestManager;

public class RequestLoanTest extends BaseTest {

    private RequestLoanPage requestLoanPage;

    @BeforeClass
    public void setUp() {
        requestLoanPage = new RequestLoanPage(driver);
    }

    @Test
    public void testValidLoanRequest() {
        ExtentTestManager.getTest().info("Submitting valid loan request: $5000 with $1000 down payment to account 13344");
        requestLoanPage.navigateToRequestLoan();
        requestLoanPage.requestLoan("5000", "1000", "13344");

        ExtentTestManager.getTest().info("Checking loan approval status and confirmation message");
        Assert.assertTrue(requestLoanPage.isLoanApproved(), "Loan should be approved");
        Assert.assertTrue(requestLoanPage.isLoanApprovedMsg(), "Confirmation message should be displayed");
    }

    @Test
    public void testMissingLoanDetails() {
        ExtentTestManager.getTest().info("Submitting loan request with missing fields");
        requestLoanPage.navigateToRequestLoan();
        requestLoanPage.requestLoan("", "", "");

        ExtentTestManager.getTest().info("Verifying error message for missing data");
        Assert.assertTrue(requestLoanPage.isValidationErrorShown(), "Validation error should be shown for empty inputs");
    }

    @Test
    public void testLoanExceedingLimit() {
        ExtentTestManager.getTest().info("Submitting high loan request: $1000000 with $500 down payment");
        requestLoanPage.navigateToRequestLoan();
        requestLoanPage.requestLoan("1000000", "500", "13344");

        ExtentTestManager.getTest().info("Checking loan rejection and proper rejection message");
        Assert.assertTrue(requestLoanPage.isLoanRejected(), "Loan should be rejected");
        Assert.assertTrue(requestLoanPage.isLoanRejectedMsg(), "Rejection message should be displayed");
    }
}
