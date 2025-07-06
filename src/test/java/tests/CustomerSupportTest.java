package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.CustomerSupportPage;

public class CustomerSupportTest extends BaseTest {

    private CustomerSupportPage supportPage;

    @BeforeClass
    public void setUpPages() {
        supportPage = new CustomerSupportPage(driver);
    }

    @Test
    public void testSubmitFormWithValidDetails() {
    	supportPage.navigateToCustomerSupport();
        supportPage.submitSupportForm("John Doe", "john@example.com", "Loan Issue", "I need help with my loan status.");
        Assert.assertTrue(supportPage.isSuccessMessageDisplayed(), "Success message should be shown.");
    }

    @Test
    public void testSubmitFormWithEmptyFields() {
    	supportPage.navigateToCustomerSupport();
        supportPage.submitSupportForm("", "", "", "");
        Assert.assertTrue(supportPage.isValidationErrorDisplayed(), "Validation message should be displayed.");
    }

    @Test
    public void testVerifySuccessMessage() {
    	supportPage.navigateToCustomerSupport();
        supportPage.submitSupportForm("Jane", "jane@example.com", "Password Reset", "I forgot my password.");
        Assert.assertTrue(supportPage.isSuccessMessageDisplayed(), "Confirmation should be displayed.");
    }
}
