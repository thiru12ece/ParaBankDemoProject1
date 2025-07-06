package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.RequestLoanPage;

public class RequestLoanTest extends BaseTest {


    private RequestLoanPage requestLoanPage;

    @BeforeClass
    public void setUpPages() {
        requestLoanPage = new RequestLoanPage(driver);
    }

    @Test
    public void testValidLoanRequest() {
    	requestLoanPage.navigateToRequestLoan();
        requestLoanPage.requestLoan("5000", "1000", "13344");
        Assert.assertTrue(requestLoanPage.isLoanApproved(), "Approved");
        Assert.assertTrue(requestLoanPage.isLoanApprovedMsg(), "Congratulations, your loan has been approved.");
    }

    @Test
    public void testMissingLoanDetails() {
    	requestLoanPage.navigateToRequestLoan();
        requestLoanPage.requestLoan("", "", "");
        Assert.assertTrue(requestLoanPage.isValidationErrorShown(), "An internal error has occurred and has been logged.");
    }

    @Test
    public void testLoanExceedingLimit() {
    	requestLoanPage.navigateToRequestLoan();
        requestLoanPage.requestLoan("1000000", "500", "13344");
        Assert.assertTrue(requestLoanPage.isLoanRejected(), "Denied");
        Assert.assertTrue(requestLoanPage.isLoanRejectedMsg(), "We cannot grant a loan in that amount with your available funds.");
    }

}
