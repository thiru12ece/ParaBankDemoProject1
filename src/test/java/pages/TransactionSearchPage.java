package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TransactionSearchPage {

    private WebDriver driver;
    private WebDriverWait wait;
  
    private By transactionsLink = By.xpath("//a[contains(text(),'Find Transactions')]");
    private By accountDropdown = By.xpath("//select[@id='accountId']");
    private By transactionIdInput = By.xpath("//input[@id='transactionId']");
    private By transactionDateInput = By.xpath("//input[@id='transactionDate']");
    
    private By fromDateInput = By.xpath("//div//input[@id='fromDate']");
    private By toDateInput = By.xpath("//div//input[@id='toDate']");
    private By amountInput = By.xpath("//input[@id='amount']");

    private By findtransactionByIdBtn = By.xpath("//button[@id='findById']");
    private By findtransactionByDateBtn = By.xpath("//button[@id='findByDate']");
    private By findtransactionByDateRangeBtn = By.xpath("//button[@id='findByDateRange']");
    private By findtransactionByAmountBtn = By.xpath("//button[@id='findByAmount']");
    private By resultsTable = By.xpath("//table[@id='transactionTable']");
    private By noResultsMsg = By.xpath("//*[contains(text(),'No transactions found')]");

    public TransactionSearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public void navigateToTransactionSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(transactionsLink)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(transactionsLink));
    }


    public void selectAccount(String accountNumber) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(accountDropdown));
        driver.findElement(accountDropdown).sendKeys(accountNumber);
    }

    public void searchByTransactionId(String txnId) {
        driver.findElement(transactionIdInput).clear();
        driver.findElement(transactionIdInput).sendKeys(txnId);
        driver.findElement(findtransactionByIdBtn).click();
    }

    public void searchByDate(String date) {
        driver.findElement(transactionDateInput).clear();
        driver.findElement(transactionDateInput).sendKeys(date);
        driver.findElement(findtransactionByDateBtn).click();
    }

    public void searchByDateRange(String from, String to) {
        driver.findElement(fromDateInput).clear();
        driver.findElement(fromDateInput).sendKeys(from);
        driver.findElement(toDateInput).clear();
        driver.findElement(toDateInput).sendKeys(to);
        driver.findElement(findtransactionByDateRangeBtn).click();
    }

    public void searchByAmount(String amount) {
        driver.findElement(amountInput).clear();
        driver.findElement(amountInput).sendKeys(amount);
        driver.findElement(findtransactionByAmountBtn).click();
    }

    public boolean isResultFound() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(resultsTable)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isNoResultMessageShown() {
        try {
            return driver.findElement(noResultsMsg).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
