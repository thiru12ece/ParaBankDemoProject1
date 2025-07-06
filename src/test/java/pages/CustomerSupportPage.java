package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CustomerSupportPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators – update based on your app’s actual HTML
    private By customerSupportLink = By.xpath("//a[contains(text(),'Contact Us')]");
    private By nameInput = By.xpath("//input[@id='name']");
    private By emailInput = By.xpath("//input[@id='email']");
    private By phoneInput = By.xpath("//input[@id='phone']");
    private By messageInput = By.xpath("//textarea[@id='message']");
    private By sendButton = By.xpath("//input[@value='Send to Customer Care']");
    private By successMessage = By.xpath("//*[contains(text(), 'Thank you') or contains(text(), 'Message sent')]");
    private By validationMessage = By.xpath("//*[contains(text(), 'Name is required') or contains(text(),'please enter')]");

    public CustomerSupportPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public void navigateToCustomerSupport() {
        wait.until(ExpectedConditions.elementToBeClickable(customerSupportLink)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput));
    }

    public void submitSupportForm(String name, String email, String phone, String message) {
        driver.findElement(nameInput).clear();
        driver.findElement(nameInput).sendKeys(name);

        driver.findElement(emailInput).clear();
        driver.findElement(emailInput).sendKeys(email);

        driver.findElement(phoneInput).clear();
        driver.findElement(phoneInput).sendKeys(phone);

        driver.findElement(messageInput).clear();
        driver.findElement(messageInput).sendKeys(message);

        driver.findElement(sendButton).click();
    }

    public boolean isSuccessMessageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).isDisplayed();
    }

    public boolean isValidationErrorDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(validationMessage)).isDisplayed();
    }
}
