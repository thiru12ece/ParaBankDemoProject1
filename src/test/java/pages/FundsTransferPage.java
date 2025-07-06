package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FundsTransferPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By transferFundsLink = By.xpath("//a[contains(text(),'Transfer Funds')]");
    private By fromAccountDropdown = By.xpath("//select[@id='fromAccountId']");
    private By toAccountDropdown = By.xpath("//select[@id='toAccountId']");
    private By amountInput = By.xpath("//input[@id='amount']");
    private By transferButton = By.xpath("//input[@value='Transfer']");
    private By successMessage = By.xpath("//h1[contains(text(),'Transfer Complete')]");
    private By errorMessage = By.xpath("//*[contains(text(),'An internal error has occurred and has been logged.')]");

    public FundsTransferPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public void navigateToTransferFunds() {
        wait.until(ExpectedConditions.elementToBeClickable(transferFundsLink)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(fromAccountDropdown));
    }

    public void transferFunds(String fromAccount, String toAccount, String amount) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(fromAccountDropdown));
        driver.findElement(fromAccountDropdown).sendKeys(fromAccount);
        driver.findElement(toAccountDropdown).sendKeys(toAccount);
        driver.findElement(amountInput).clear();
        driver.findElement(amountInput).sendKeys(amount);
        driver.findElement(transferButton).click();
    }

    public boolean isTransferSuccessful() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).isDisplayed();
    }

    public boolean isTransferFailed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).isDisplayed();
    }
}
