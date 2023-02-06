package RegressionTest;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v104.network.Network;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.testng.annotations.Listeners;
import pageObjects.*;
import resource.base;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Listeners(RegressionTest.Listeners.class)
public class MyAccountPagesTest extends base {
    ProductDPage pDpPage;
    ProductListingPage plpPage;
    MiniCart miniCart;
    MyAccountPages myAccountPages;
    PageElements pageElements;
    HomePage homePage;
    Actions actions;
    DevTools devTools;

    @BeforeTest
    public void beforeTest() throws IOException {
        driver = initializeDriver();
    }
    @BeforeMethod
    public void beforeMethod() throws InterruptedException {
        pDpPage = new ProductDPage(driver);
        plpPage = new ProductListingPage(driver);
        pageElements = new PageElements(driver);
        homePage = new HomePage(driver);
        miniCart = new MiniCart(driver);
        actions = new Actions(driver);
        myAccountPages = new MyAccountPages(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        devTools = ((ChromeDriver)driver).getDevTools();

        //DevTools devTools = ((ChromeDriver)driver).getDevTools();
        //devTools.createSession();
        //devTools.send(Network.enable(java.util.Optional.empty(),
        //        java.util.Optional.empty(), Optional.empty()));
        //devTools.send(Network.setBlockedURLs(List.of("https://tag.wknd.ai/4949/i.js", "https://tag.wknd.ai/4951/i.js")));

        driver.get(currentURL);
        Thread.sleep(2000);
    }
    // Sign In ********************************** MY ACCOUNT Pages ****************************
    @Test(priority = 1) //retryAnalyzer = Retry.class
    public void verifyUserIsAbleToSignIn() throws InterruptedException {
        Thread.sleep(2000);
        MyAccountPages myAccountPages = new MyAccountPages(driver);
        myAccountPages.verifySignInFromSidePanel(properties.getProperty("email"), properties.getProperty("password"));
        System.out.println("Test case = Sign In into account" );
    }

    @AfterMethod
    public void afterTestCase() throws InterruptedException {
        Thread.sleep(2000);
        driver.manage().deleteAllCookies();
        Thread.sleep(2000);
        driver.navigate().refresh();
    }

    @AfterTest
    public void tearDown() throws InterruptedException {
        Thread.sleep(2000);
        driver.quit();
    }
}
