package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.CustomerSupportPage;
import utils.ExtentTestManager;

public class CustomerSupportTest extends BaseTest {

    private CustomerSupportPage supportPage;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        supportPage = new CustomerSupportPage(driver);
    }

    @Test
    public void testSubmitFormWithValidDetails() {
        ExtentTestManager.getTest().info("Submitting form with valid data");
        supportPage.navigateToCustomerSupport();
        supportPage.submitSupportForm("John Doe", "john@example.com", "Loan Issue", "I need help with my loan status.");

        Assert.assertTrue(
            supportPage.isSuccessMessageDisplayed(),
            "Expected success message after submitting valid support request."
        );
    }

    @Test
    public void testSubmitFormWithEmptyFields() {
        ExtentTestManager.getTest().info("Submitting form with empty fields");
        supportPage.navigateToCustomerSupport();
        supportPage.submitSupportForm("", "", "", "");

        Assert.assertTrue(
            supportPage.isValidationErrorDisplayed(),
            "Expected validation error for empty form submission."
        );
    }

    @Test
    public void testVerifySuccessMessage() {
        ExtentTestManager.getTest().info("Submitting form for password reset request");
        supportPage.navigateToCustomerSupport();
        supportPage.submitSupportForm("Jane", "jane@example.com", "Password Reset", "I forgot my password.");

        Assert.assertTrue(
            supportPage.isSuccessMessageDisplayed(),
            "Expected confirmation message after successful form submission."
        );
    }
}
