package pageObjects;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
public class MyAccountPages {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor jseAccountPage;
    public MyAccountPages(WebDriver driver){ this.driver = driver; wait = new WebDriverWait(driver, Duration.ofSeconds(30)); }

    // Verify Sign In from Side panel
    public void verifySignInFromSidePanel(String email, String password) throws InterruptedException {
        PageElements pageElementsAccount1 = new PageElements(driver);
        Actions actions1 = new Actions(driver);
        jseAccountPage = (JavascriptExecutor) driver;
        Thread.sleep(2000);
        pageElementsAccount1.getFtdPlusAndSignIn().get(pageElementsAccount1.getFtdPlusAndSignIn().size() - 1).click();
        wait.until(ExpectedConditions.visibilityOf(pageElementsAccount1.getEmailSignIn()));

        pageElementsAccount1.getEmailSignIn().sendKeys(email);// Send Email in field
        wait.until(ExpectedConditions.textToBePresentInElementValue(pageElementsAccount1.getEmailSignIn(), email));

        pageElementsAccount1.getPasswordSignIn().sendKeys(password);// Send Password in field
        wait.until(ExpectedConditions.textToBePresentInElementValue(pageElementsAccount1.getPasswordSignIn(), password));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(pageElementsAccount1.getRecaptchaIframe()));
        Thread.sleep(4000);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(pageElementsAccount1.getRecaptchaUnCheckedBox()));
            pageElementsAccount1.getRecaptchaUnCheckedBox().click();
        } catch (Exception e) {
            jseAccountPage.executeScript("arguments[0].click();", pageElementsAccount1.getRecaptchaUnCheckedBox());
        }
        //Thread.sleep(5000);
        //wait.until(ExpectedConditions.visibilityOf(pageElementsAccount1.getRecaptchaCheckedBox()));
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsAccount1.getSignInCTA()));
        //pageElementsAccount1.getSignInCTA().sendKeys(Keys.ENTER);// Click Sign in button
        pageElementsAccount1.getSignInCTA().click();// Click Sign in button
        //actions1.sendKeys(Keys.TAB).build().perform();
        //actions1.sendKeys(Keys.ENTER).build().perform();
    }
}
