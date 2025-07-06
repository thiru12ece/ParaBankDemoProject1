package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.ConfigReader;

public class LoginTest extends BaseTest {

	String baseUrl = ConfigReader.getProperty("baseUrl");
    LoginPage loginPage;

    @Test
    public void loginWithValidCredentials() {
        driver.get(baseUrl);
        loginPage = new LoginPage(driver);
        loginPage.enterUsername("john");
        loginPage.enterPassword("demo");
        loginPage.clickLogin();
        Assert.assertTrue(driver.getTitle().contains("ParaBank | Accounts Overview"));
    }

    @Test
    public void loginWithInvalidPassword() {
        driver.get(baseUrl);
        loginPage = new LoginPage(driver);
        loginPage.enterUsername("john");
        loginPage.enterPassword("wrongpass");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.getErrorMessage().contains("could not be verified"));
    }

    @Test
    public void loginWithUnregisteredUser() {
        driver.get(baseUrl);
        loginPage = new LoginPage(driver);
        loginPage.enterUsername("nonexistentuser");
        loginPage.enterPassword("somepass");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.getErrorMessage().contains("The username and password could not be verified."));
    }

    @Test
    public void loginWithEmptyFields() {
        driver.get(baseUrl);
        loginPage = new LoginPage(driver);
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.getErrorMessage().contains("Please enter a username and password."));
    }

    @Test
    public void loginWithInvalidEmailFormat() {
        driver.get(baseUrl);
        loginPage = new LoginPage(driver);
        loginPage.enterUsername("invalid-email");
        loginPage.enterPassword("demo");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.getErrorMessage().contains("could not be verified"));
    }

    @Test(enabled = false)
    public void verifyPasswordFieldIsMasked() {
        driver.get(baseUrl);
        loginPage = new LoginPage(driver);
        Assert.assertEquals(loginPage.getPasswordFieldType(), "password");
    }

    @Test(enabled = false)
    public void rememberMeFunctionality() {
        driver.get(baseUrl);
        loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isUsernameFieldDisplayed());
    }

    @Test(enabled = false)
    public void sessionTimeoutTest() throws InterruptedException {
        driver.get(baseUrl);
        loginPage = new LoginPage(driver);
        loginPage.enterUsername("john");
        loginPage.enterPassword("demo");
        loginPage.clickLogin();
        Thread.sleep(600000); // Simulate 10 min inactivity
        driver.navigate().refresh();
        Assert.assertTrue(driver.getTitle().contains("ParaBank")); // Should redirect to login
    }
}