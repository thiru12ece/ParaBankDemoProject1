package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RequestLoanPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By requestLoanLink = By.xpath("//a[contains(text(),'Request Loan')]");
    private By loanAmountInput = By.xpath("//input[@id='amount']");
    private By downPaymentInput = By.xpath("//input[@id='downPayment']");
    private By fromAccountDropdown = By.xpath("//select[@id='fromAccountId']");
    private By applyNowButton = By.cssSelector("input[value='Apply Now']");
    private By loanStatus = By.xpath("//td[@id='loanStatus']");
    private By loanRequestDeniedMessage = By.xpath("//*[@id=\"loanRequestDenied\"]/p");
    private By loanRequestSuccessMessage = By.xpath("//p[contains(text(),'Congratulations')]");
    private By errorMessage = By.xpath("//p[contains(text(),'error') or contains(text(),'required') or contains(text(),'invalid')]");

    public RequestLoanPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public void navigateToRequestLoan() {
        wait.until(ExpectedConditions.elementToBeClickable(requestLoanLink)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(loanAmountInput));
    }

    public void requestLoan(String amount, String downPayment, String fromAccount) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(loanAmountInput)).clear();
        driver.findElement(loanAmountInput).sendKeys(amount);

        driver.findElement(downPaymentInput).clear();
        driver.findElement(downPaymentInput).sendKeys(downPayment);

        driver.findElement(fromAccountDropdown).sendKeys(fromAccount);
        driver.findElement(applyNowButton).click();
    }

    public boolean isLoanApproved() {
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(loanStatus, "Approved"));
    }
    
    public boolean isLoanApprovedMsg() {
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(loanRequestSuccessMessage, "Congratulations, your loan has been approved."));
    }

    public boolean isLoanRejected() {
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(loanStatus, "Denied"));
    }
    
    public boolean isLoanRejectedMsg() {
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(loanRequestDeniedMessage, "We cannot grant a loan in that amount with your available funds."));
    }
    
    public boolean isValidationErrorShown() {
        return driver.getPageSource().toLowerCase().contains("error") || driver.getPageSource().toLowerCase().contains("required");
    }
}
