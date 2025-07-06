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

    private By accountLinks = By.cssSelector("#accountTable a");
    private By balance = By.xpath("//td[@id='availableBalance']");
    private By transactionsTable = By.xpath("//table[@id='transactionTable']");
    private By transactionHeaders = By.xpath("//table[@id='transactionTable']//th[contains(text(),'Transaction')]");
    private By accountTypeLocator = By.xpath("//*[contains(text(),'Account Type')]/following-sibling::td");
    private By transactionsLink = By.linkText("Find Transactions");
    private By noTransactionsMessage = By.xpath("//*[contains(text(), 'No transactions found')]");

    public AccountPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ✅ Updated method
    public void selectAccountByType(String expectedType) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(accountLinks));
        List<WebElement> accounts = driver.findElements(accountLinks);
        boolean found = false;

        for (int i = 0; i < accounts.size(); i++) {
            // Refresh the account list every time (DOM changes after navigating back)
            accounts = driver.findElements(accountLinks);
            WebElement account = accounts.get(i);

            // Click the account link
            wait.until(ExpectedConditions.elementToBeClickable(account)).click();
            Thread.sleep(5000);
            // Wait for the Account Type element to load (adjust this based on real HTML)
            wait.until(ExpectedConditions.visibilityOfElementLocated(accountTypeLocator));

            String actualType = driver.findElement(accountTypeLocator).getText().trim();
            System.out.println("Account Type: " + actualType);  // Debug log

            if (actualType.equalsIgnoreCase(expectedType)) {
                found = true;
                break;
            } else {
                // Navigate back to the accounts overview page
                driver.navigate().back();
                wait.until(ExpectedConditions.visibilityOfElementLocated(accountLinks));
            }
        }

        if (!found) {
            throw new NoSuchElementException("No account found with type: " + expectedType);
        }

        // Wait for balance section (optional)
        wait.until(ExpectedConditions.visibilityOfElementLocated(balance));
    }


    public boolean isBalanceDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(balance)).isDisplayed();
    }

    public boolean isRecentTransactionsDisplayed() {
        try {
            // Check if transactions table is visible
            return wait.until(ExpectedConditions.visibilityOfElementLocated(transactionsTable)).isDisplayed();
        } catch (Exception e) {
            // If table is not found, check for "No transactions found" message
            try {
                return !wait.until(ExpectedConditions.visibilityOfElementLocated(noTransactionsMessage)).isDisplayed();
            } catch (Exception ignored) {
                return false; // Neither table nor message was found
            }
        }
    }

    public void navigateToTransactions() {
        wait.until(ExpectedConditions.elementToBeClickable(transactionsLink)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(transactionsTable));
    }

}