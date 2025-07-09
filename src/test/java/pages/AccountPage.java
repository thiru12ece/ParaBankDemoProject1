package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class AccountPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private final By accountsOverviewLink = By.xpath("//a[contains(text(),'Accounts Overview')]");
    private final By accountLinks = By.cssSelector("#accountTable a");
    private final By balance = By.xpath("//td[@id='availableBalance']");
    private final By transactionsTable = By.xpath("//table[@id='transactionTable']");
    private final By transactionHeaders = By.xpath("//table[@id='transactionTable']//th[contains(text(),'Transaction')]");
    private final By accountTypeLocator = By.xpath("//*[contains(text(),'Account Type')]/following-sibling::td");
    private final By transactionsLink = By.linkText("Find Transactions");
    private final By noTransactionsMessage = By.xpath("//*[contains(text(), 'No transactions found')]");

    public AccountPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public void navigateToAccountsOverview() {
        wait.until(ExpectedConditions.elementToBeClickable(accountsOverviewLink)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(accountLinks));
    }


    public void selectAccountByType(String expectedType) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(accountLinks));
        List<WebElement> accounts = driver.findElements(accountLinks);
        boolean found = false;

        for (int i = 0; i < accounts.size(); i++) {
            accounts = driver.findElements(accountLinks);  // Refresh in each iteration
            WebElement account = accounts.get(i);

            wait.until(ExpectedConditions.elementToBeClickable(account)).click();
            Thread.sleep(2000);
            wait.until(ExpectedConditions.visibilityOfElementLocated(accountTypeLocator));

            String actualType = driver.findElement(accountTypeLocator).getText().trim();
            if (actualType.equalsIgnoreCase(expectedType)) {
                found = true;
                break;
            } else {
                driver.navigate().back();
                wait.until(ExpectedConditions.visibilityOfElementLocated(accountLinks));
            }
        }

        if (!found) {
            throw new NoSuchElementException("No account found with type: " + expectedType);
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(balance));
    }

    public boolean isBalanceDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(balance)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isRecentTransactionsDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(transactionsTable)).isDisplayed();
        } catch (Exception e) {
            try {
                return wait.until(ExpectedConditions.visibilityOfElementLocated(noTransactionsMessage)).isDisplayed();
            } catch (Exception ex) {
                return false;
            }
        }
    }

    public void navigateToTransactions() {
        wait.until(ExpectedConditions.elementToBeClickable(transactionsLink)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(transactionsTable));
    }

    public boolean validateTransactionTableHeaders() {
        List<WebElement> headers = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(transactionHeaders));
        return headers.stream().anyMatch(h -> h.getText().contains("Date")) &&
               headers.stream().anyMatch(h -> h.getText().contains("Amount")) &&
               headers.stream().anyMatch(h -> h.getText().contains("Type"));
    }

    public void filterTransactionsByDate(String from, String to) {
        By fromDate = By.id("fromDate");
        By toDate = By.id("toDate");
        By findButton = By.cssSelector("input[value='Find Transactions']");

        WebElement fromField = wait.until(ExpectedConditions.visibilityOfElementLocated(fromDate));
        fromField.clear();
        fromField.sendKeys(from);

        WebElement toField = wait.until(ExpectedConditions.visibilityOfElementLocated(toDate));
        toField.clear();
        toField.sendKeys(to);

        wait.until(ExpectedConditions.elementToBeClickable(findButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(transactionsTable));
    }

    public boolean areFilteredResultsDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(transactionsTable)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void sortBy(String column) {
        List<WebElement> headers = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(transactionHeaders));
        for (WebElement header : headers) {
            if (header.getText().equalsIgnoreCase(column)) {
                wait.until(ExpectedConditions.elementToBeClickable(header)).click();
                break;
            }
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(transactionsTable));
    }

    public boolean isSorted(String column) {
        // Placeholder: logic to validate sorting should go here
        return true;
    }
}
