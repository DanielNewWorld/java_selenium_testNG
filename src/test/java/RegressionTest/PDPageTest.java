package RegressionTest;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v104.network.Network;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.Listeners;
import pageObjects.*;
import resource.base;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Listeners(RegressionTest.Listeners.class)
public class PDPageTest extends base{
    ProductDPage pDpPage;
    ProductListingPage plpPage;
    MiniCart miniCart;
    MyAccountPages myAccountPages;
    PageElements pageElements;
    HomePage homePage;
    Actions actions;
    JavascriptExecutor jseProdTest;

    @BeforeTest
    public void beforeTest() throws IOException{
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
        jseProdTest = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        DevTools devTools = ((ChromeDriver)driver).getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(java.util.Optional.empty(), java.util.Optional.empty(), Optional.empty()));
        devTools.send(Network.setBlockedURLs(List.of("https://tag.wknd.ai/4949/i.js", "https://tag.wknd.ai/4951/i.js")));
        driver.get(currentURL);
        Thread.sleep(2000);
    }
    // Verify Submitting SWAG in PDP and adding product to Cart
    @Test(priority = 1, retryAnalyzer = Retry.class)
    public void enterSwagFromPDP_AndAddProductToCart() throws InterruptedException {
        homePage.plpIndexSelection(0, 3);// Selecting PLP by index
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOf(pageElements.getProductPricesPLP().get(0)));
        plpPage.selectProductFromPLP(0); // Selecting a Product without a SWAG
        pDpPage.addProductToCart_SWAG_InPDP(properties.getProperty("swagZipcode"));
        System.out.println("Enter SWAG in PDP and Add to cart = "+pageElements.getPDPdeliveryDetails().getText());// Checking PDP SWAG was Entered ok
        Assert.assertTrue(pageElements.getPDPdeliveryDetails().getText().contains(properties.getProperty("swagZipcode")));
        wait.until(ExpectedConditions.visibilityOf(pageElements.getUpdateSuccessMsgOnMiniCart()));
        System.out.println(pageElements.getUpdateSuccessMsgOnMiniCart().getText());//PDP success message item added
        Assert.assertEquals("Item added successfully", pageElements.getUpdateSuccessMsgOnMiniCart().getText());
    }
    // Verify Edit Date From PDP page
    @Test(priority = 2, retryAnalyzer = Retry.class)
    public void editDateFromPDP() throws InterruptedException {// edit Date From PDP
        homePage.submitSwag(properties.getProperty("swagZipcode"), 0); // Submit SWAG starts 0 = nextDay
        plpPage.selectProductFromPLP(0); // Select from PLP
        Thread.sleep(1000);
        pDpPage.editDateFromPDP( 2); // Updating Date from PDP starts from 0 - default so nextDat=1
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        String updatedDate2 = pageElements.getPDPdeliveryDetails().getText();
        Assert.assertNotEquals(pDpPage.currentDate1, updatedDate2);
        System.out.println("Test case first date = " + pDpPage.currentDate1 + " Updated date = " +updatedDate2);
    }
    // Verify Edit Zip code From PDP page
    @Test(priority = 3, retryAnalyzer = Retry.class)
    public void editZipCodeFromPDP() throws InterruptedException {
        homePage.submitSwag(properties.getProperty("swagZipcode"), 0); // Submit SWAG
        Thread.sleep(1000);
        plpPage.selectProductFromPLP(0); // Select Product PLP
        Thread.sleep(1000);
        String currentZipcode1 = pageElements.getPDPdeliveryDetails().getText(); // get Current submitted zipcode and date
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        pDpPage.editZipcodeFromPDP(properties.getProperty("swagZipcode"), properties.getProperty("updatedZipcode")); // Update zipcode PDP
        String updatedZipcode2 = pageElements.getPDPdeliveryDetails().getText(); // get Updated submitted zipcode and date
        System.out.println("Test case = " + currentZipcode1 + " VS updated = "+ updatedZipcode2);
        Assert.assertNotEquals(currentZipcode1, updatedZipcode2);
    }
    // Verify Add Product to Cart + Cart item Count
    @Test(priority = 4, retryAnalyzer = Retry.class)
    public void addProductToCartAfterSwag() throws InterruptedException {
        pDpPage = new ProductDPage(driver);
        homePage.submitSwag(properties.getProperty("swagZipcode"), 0);
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOf(pageElements.getProductPricesPLP().get(0)));
        plpPage.selectProductFromPLP(0);
        Thread.sleep(1000);
        pDpPage.addProductToCartAfterSWAG();
        wait.until(ExpectedConditions.visibilityOf(pageElements.getCloseMiniCart_x()));
        pageElements.getCloseMiniCart_x().sendKeys(Keys.ENTER);
        System.out.println("Test case = 1 "+ pDpPage.productAddedToCartText);
        wait.until(ExpectedConditions.visibilityOf(pageElements.getCartItemCount()));
        int cartItemCount = Integer.parseInt(pageElements.getCartItemCount().getText());
        System.out.println(pageElements.getCartItemCount().getText());
        Assert.assertTrue(cartItemCount > 0); // 1 product added to cart
    }
    // Verify Recommendation model
    @Test(priority = 5, retryAnalyzer = Retry.class)
    public void verifyRecommendationModel() throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOf(pageElements.getZipCode()));
        pDpPage.triggerRecommendationModel("96732", "FE84B", 0);
        Assert.assertTrue(pageElements.getRecommendationModel().isDisplayed());
        System.out.println("Test case = Recommendation model Test");
    }
    // Verify User can Select a Product from Recommendation model
    @Test(priority = 6, retryAnalyzer = Retry.class)
    public void verifySelectingRecommendationModelProduct() throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOf(pageElements.getZipCode()));
        pDpPage.triggerRecommendationModel("99697", "Red Roses", 0);
        pDpPage.selectRecommendationProduct(0);
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOf(pageElements.getProductTitlePDP()));
        System.out.println(pageElements.getProductTitlePDP().getText());
        Assert.assertTrue(pageElements.getProductTitlePDP().isDisplayed());
        Assert.assertNotEquals(pageElements.getProductTitlePDP().getText(), "");
        System.out.println("Test case = Selecting a Product from Recommendation model Test");
    }
    // Verify Click View Details of Product from Recommendation model
    @Test(priority = 7, retryAnalyzer = Retry.class)
    public void verifyViewDetailsRecommendationCTA() throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOf(pageElements.getZipCode()));
        pDpPage.triggerRecommendationModel("99501", "Mixed Roses", 0);
        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(pageElements.getViewDetailsRecommendation().get(0)));//wait
        pageElements.getViewDetailsRecommendation().get(0).click(); // Click View Details
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOf(pageElements.getProductTitlePDP()));
        System.out.println(pageElements.getProductTitlePDP().getText());
        Assert.assertTrue(pageElements.getProductTitlePDP().isDisplayed());
        Assert.assertNotEquals(pageElements.getProductTitlePDP().getText(), "");
        System.out.println("Test case = Selecting a Product from Recommendation model Test");
    }

    @AfterMethod
    public void afterTestCase() throws InterruptedException {
        Thread.sleep(1000);
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
