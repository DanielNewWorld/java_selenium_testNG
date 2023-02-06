package RegressionTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import pageObjects.*;
import resource.base;
import java.io.IOException;
import java.time.Duration;

import org.testng.annotations.Listeners;

@Listeners(RegressionTest.Listeners.class)
public class PLPPageTest extends base {
    ProductDPage pDpPage;
    ProductListingPage plpPage;
    MiniCart miniCart;
    MyAccountPages myAccountPages;
    PageElements pageElements;
    HomePage homePage;
    Actions actions;
    JavascriptExecutor jseProdTest;

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
        jseProdTest = (JavascriptExecutor) driver;
        driver.get(currentURL);
        Thread.sleep(2000);
    }
    
    //Verifying Product Prices Are Displayed In PLP BEFORE SWAG - assert ok
    @Test(priority = 1, retryAnalyzer = Retry.class)
    public void verifyProductPricesAreDisplayedOnPLP() throws InterruptedException {
        homePage.plpIndexSelection(0, 1);
        System.out.println("Verify Product Prices are displayed Before SWAG in PLP = "+driver.getTitle());
        Thread.sleep(1000);
        plpPage.closeForceSWAG();
        wait.until(ExpectedConditions.visibilityOf(pageElements.getProductPricesPLP().get(0)));
        System.out.println("Product Prices are displayed BEFORE SWAG in PLP = "+driver.getTitle());
        plpPage.verifyProductPricesInPLP(); // method will verify product Prices
    }

    //Verify Product Prices are displayed AFTER SWAG - assert ok
    @Test(priority = 2, retryAnalyzer = Retry.class)
    public void verifyProductPricesDisplayed_AFTER_SWAG() throws InterruptedException {
        homePage.plpIndexSelection(0, 1);
        Thread.sleep(7000);
        wait.until(ExpectedConditions.visibilityOf(pageElements.getProductPricesPLP().get(0)));
        System.out.println("Product Prices are displayed AFTER SWAG in PLP = "+driver.getTitle());
        homePage.submitSwag("90006", 3);
        plpPage.verifyProductPricesInPLP(); // method will verify product Prices
    }

    //Verifying SWAG With Valid Input In PLP - assert ok
    @Test(priority = 3, retryAnalyzer = Retry.class)
    public void verifySwagInPLP() throws InterruptedException {
        homePage.plpIndexSelection(0, 1);
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOf(pageElements.getProductPricesPLP().get(0)));
        System.out.println("Submit SWAG In PLP = "+driver.getTitle());
        homePage.submitSwag(properties.getProperty("swagZipcode"), 3);
        System.out.println(pageElements.getSwagResultTxt().getText());
        Assert.assertTrue(pageElements.getSwagResultTxt().getText().contains(properties.getProperty("swagZipcode")));
    }

    //Verify User can UPDATE/Change the ZIPCODE to another valid zipcode in PLP SWAG - assert ok
    @Test(priority = 4, retryAnalyzer = Retry.class)
    public void updateSWAG_ToTaxFreeZipcode_AfterSWAG() throws InterruptedException {
        homePage.plpIndexSelection(0, 1);// Selecting PLP index
        Thread.sleep(7000);
        wait.until(ExpectedConditions.visibilityOf(pageElements.getProductPricesPLP().get(0)));
        System.out.println("Update Zipcode in PLP SWAG = "+driver.getTitle()); //getting title
        homePage.submitSwag(properties.getProperty("swagZipcode"), 1);//with 90006
        String firstSWAG = pageElements.getSwagResultTxt().getText();
        homePage.updateZipCodeInSWAG(properties.getProperty("taxFreeZipcode"), 2); // Updating Zipcode 97070
        wait.until(ExpectedConditions.visibilityOf(pageElements.getSwagResultTxt()));
        System.out.println("Test case = " + firstSWAG + " UpdatedZipcode = "+ pageElements.getSwagResultTxt().getText());
        Assert.assertNotEquals(firstSWAG, pageElements.getSwagResultTxt().getText());
    }

    //Verify Updating ZIPCODE to INVALID ZIPCODE after SWAG in PLP - assert ok
    @Test(priority = 5, retryAnalyzer = Retry.class)
    public void updateTo_InvalidZipcode_AfterSWAG() throws InterruptedException { // FAIL IN PROD
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        homePage.plpIndexSelection(0, 1); // Selecting PLP index
        Thread.sleep(7000);
        wait.until(ExpectedConditions.visibilityOf(pageElements.getProductPricesPLP().get(0)));
        homePage.submitSwag(properties.getProperty("swagZipcode"), 1); // Swag submission
        homePage.verifyInvalidZipcodeErrorMessage("*****"); // verifying error message when SWAG submitted with different characters
        Assert.assertTrue(pageElements.getInvalidInputErrorInSWAG().isDisplayed());
        System.out.println("Test case Updating SWAG to Invalid Zipcode in PLP After SWAG: "+driver.getTitle());
    }

    //Verify Updating DATE in PLP SWAG - assert ok
    @Test(priority = 6, retryAnalyzer = Retry.class)
    public void update_DateInPLPAfterSWAG() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        homePage.plpIndexSelection(0, 1); // Selecting PLP index
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOf(pageElements.getProductPricesPLP().get(0)));
        homePage.updateDateInCalendarAfterSWAG(properties.getProperty("swagZipcode"), 2);// This date index will Update date after SWAG already submitted
        System.out.println("Test case = Updating date in Home page after SWAG - " + homePage.swagResultsOnDateUpdateText+ " Updated date = "+ pageElements.getSwagResultTxt().getText());
        Assert.assertNotEquals(homePage.swagResultsOnDateUpdateText, pageElements.getSwagResultTxt().getText()); // Comparing the Updated date with 1st one
    }

    //Select a Product From PLP Before SWAG - assert ok
    @Test(priority = 7, retryAnalyzer = Retry.class)
    public void verifySelect_Product_BeforeSWAG() throws InterruptedException {
        homePage.plpIndexSelection(0, 1);// Selecting PLP by index
        Thread.sleep(5000);
        wait.until(ExpectedConditions.visibilityOf(pageElements.getProductPricesPLP().get(0)));
        System.out.println("Verify Selecting a Product Before SWAG in PLP: "+driver.getTitle());
        plpPage.closeForceSWAG();
        plpPage.selectProductFromPLP(1); // Selecting a Product without a SWAG
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));// little wait to load
        wait.until(ExpectedConditions.visibilityOf(pageElements.getProductTitlePDP()));
        Assert.assertTrue(pageElements.getProductTitlePDP().isDisplayed());
    }

    //Select a Product From PLP After SWAG - assert ok
    @Test(priority = 8, retryAnalyzer = Retry.class)
    public void verifySelect_Product_AfterSWAG() throws InterruptedException {
        homePage.plpIndexSelection(0, 1);// Selecting PLP by index
        Thread.sleep(7000);
        wait.until(ExpectedConditions.visibilityOf(pageElements.getProductPricesPLP().get(0)));
        homePage.submitSwag(properties.getProperty("swagZipcode"), 1);// submit SWAG in PLP
        Thread.sleep(2000);
        plpPage.selectProductFromPLP(0); // Selecting a Product right after SWAG
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));// little wait to load
        wait.until(ExpectedConditions.visibilityOf(pageElements.getProductTitlePDP()));
        Assert.assertTrue(pageElements.getProductTitlePDP().isDisplayed());// Check Product name is there
        Assert.assertTrue(pageElements.getPDPdeliveryDetails().isDisplayed());// and delivery info
    }

    //Test Check Availability of all products in PLP after SWAG - assert ok 18/01/23
    @Test(priority = 9, retryAnalyzer = Retry.class)
    public void allProductAvailabilityCheckAfterSWAG() throws InterruptedException {
        homePage.plpIndexSelection(0, 1);// Selecting PLP by index
        Thread.sleep(7000);
        wait.until(ExpectedConditions.visibilityOf(pageElements.getProductPricesPLP().get(0)));
        homePage.submitSwag("90006", 1); // don't include this one later
        Thread.sleep(2000);
        plpPage.checkAllAvailableProductsInPLP(); // loop
        System.out.println("Test case = Checking 60 Products availability in PLP after SWAG");
    }

    //Verify and check Pagination by Index in PLP - assert ok
    @Test(priority = 10, retryAnalyzer = Retry.class)
    public void VerifyPaginationInPLP() throws InterruptedException {
        Thread.sleep(1000);
        homePage.submitSwag(properties.getProperty("swagZipcode"), 2);
        Thread.sleep(2000);
        plpPage.checkAllPagination();
        System.out.println("Test case = Test case Checking All Products availability in PLP after SWAG + Pagination");
    }

    //Verify Force SWAG - assert ok
    @Test(priority = 11, retryAnalyzer = Retry.class)
    public void verifyForceSwagPopup() throws InterruptedException {
        plpPage.verifyForceSWAGinPLP(0, 1, properties.getProperty("swagZipcode"));
        System.out.println("Test case = Test case Checking All Products availability in PLP after SWAG + Pagination");
    }

    //Verify Filters using Price - assert ok
    @Test(priority = 12, retryAnalyzer = Retry.class)
    public void verifyFilterPriceBeforeSWAG() throws InterruptedException{
        homePage.plpIndexSelection(0, 1);// Selecting PLP by index
        Thread.sleep(5000);
        plpPage.verifyFilter(0, 2);
        Thread.sleep(2000);
    }

    //Verify Filters using Delivery Type - assert ok
    @Test(priority = 13, retryAnalyzer = Retry.class)
    public void verifyFilterDeliveryBeforeSWAG() throws InterruptedException{
        homePage.plpIndexSelection(0, 1);// Selecting PLP by index
        Thread.sleep(5000);
        plpPage.verifyFilter(1, 1);
        Thread.sleep(2000);
    }

    //Verify Filters using Occasion - assert ok
    @Test(priority = 14, retryAnalyzer = Retry.class)
    public void verifyFilterOccasionBeforeSWAG() throws InterruptedException{
        homePage.plpIndexSelection(0, 1);// Selecting PLP by index
        Thread.sleep(5000);
        plpPage.verifyFilter(2, 2);
        Thread.sleep(2000);
    }

    //Verify Filters using Flower Type - assert ok
    @Test(priority = 15, retryAnalyzer = Retry.class)
    public void verifyFilterFlowerBeforeSWAG() throws InterruptedException{
        homePage.plpIndexSelection(0, 1);// Selecting PLP by index
        Thread.sleep(5000);
        plpPage.verifyFilter(3, 2);
        Thread.sleep(2000);
    }

    //Verify Filters using Color - assert ok
    @Test(priority = 16, retryAnalyzer = Retry.class)
    public void verifyFilterColorBeforeSWAG() throws InterruptedException{
        homePage.plpIndexSelection(0, 1);// Selecting PLP by index
        Thread.sleep(5000);
        plpPage.verifyFilter(4, 2);
        Thread.sleep(2000);
    }

    //Verify Filters using Gift Type - assert ok
    @Test(priority = 17, retryAnalyzer = Retry.class)
    public void verifyFilterGiftTypeBeforeSWAG() throws InterruptedException{
        homePage.plpIndexSelection(0, 1);// Selecting PLP by index
        Thread.sleep(5000);
        plpPage.verifyFilter(5, 0);
        Thread.sleep(2000);
    }

    //Verify Reset Filters - assert ok
    @Test(priority = 18, retryAnalyzer = Retry.class)
    public void verifyFilterResetBeforeSWAG() throws InterruptedException{
        homePage.plpIndexSelection(0, 1);// Selecting PLP by index
        Thread.sleep(5000);
        plpPage.verifyFilter(4, 2);
        Thread.sleep(2000);
        plpPage.verifyFilterReset();
        Thread.sleep(2000);
    }

    //Verify Filters using Price after SWAG - assert ok
    @Test(priority = 19, retryAnalyzer = Retry.class)
    public void verifyFilterPriceAfterSWAG() throws InterruptedException{
        homePage.submitSwag(properties.getProperty("swagZipcode"), 2);
        Thread.sleep(5000);
        plpPage.verifyFilter(0, 2);
        Thread.sleep(2000);
    }

    //Verify Filters using Delivery Type after SWAG - assert ok
    @Test(priority = 20, retryAnalyzer = Retry.class)
    public void verifyFilterDeliveryAfterSWAG() throws InterruptedException{
        homePage.submitSwag(properties.getProperty("swagZipcode"), 2);
        Thread.sleep(5000);
        plpPage.verifyFilter(1, 1);
        Thread.sleep(2000);
    }

    //Verify Filters using Occasion after SWAG - assert ok
    @Test(priority = 21, retryAnalyzer = Retry.class)
    public void verifyFilterOccasionAfterSWAG() throws InterruptedException{
        homePage.submitSwag(properties.getProperty("swagZipcode"), 2);
        Thread.sleep(5000);
        plpPage.verifyFilter(2, 2);
        Thread.sleep(2000);
    }

    //Verify Filters using Flower Type after SWAG - assert ok
    @Test(priority = 22, retryAnalyzer = Retry.class)
    public void verifyFilterFlowerAfterSWAG() throws InterruptedException{
        homePage.submitSwag(properties.getProperty("swagZipcode"), 2);
        Thread.sleep(5000);
        plpPage.verifyFilter(3, 2);
        Thread.sleep(2000);
    }

    //Verify Filters using Color after SWAG - assert ok
    @Test(priority = 23, retryAnalyzer = Retry.class)
    public void verifyFilterColorAfterSWAG() throws InterruptedException{
        homePage.submitSwag(properties.getProperty("swagZipcode"), 2);
        Thread.sleep(5000);
        plpPage.verifyFilter(4, 2);
        Thread.sleep(2000);
    }

    //Verify Filters using Gift Type after SWAG - assert ok
    @Test(priority = 24, retryAnalyzer = Retry.class)
    public void verifyFilterGiftTypeAfterSWAG() throws InterruptedException{
        homePage.submitSwag(properties.getProperty("swagZipcode"), 2);
        Thread.sleep(5000);
        plpPage.verifyFilter(5, 1);
        Thread.sleep(2000);
    }

    //Verify Reset Filters after SWAG - assert ok
    @Test(priority = 25, retryAnalyzer = Retry.class)
    public void verifyFilterResetAfterSWAG() throws InterruptedException{
        homePage.submitSwag(properties.getProperty("swagZipcode"), 2);
        Thread.sleep(5000);
        plpPage.verifyFilter(4, 2);
        Thread.sleep(5000);
        plpPage.verifyFilterReset();
        Thread.sleep(2000);
    }

    //Verify Filters using PRICE update SWAG - assert ok
    @Test(priority = 26, retryAnalyzer = Retry.class)
    public void verifyFilterPriceUpdateSWAG() throws InterruptedException{
        homePage.submitSwag(properties.getProperty("swagZipcode"), 1);
        pageElements.getZipCode().clear();
        Thread.sleep(2000);
        homePage.submitSwag(properties.getProperty("updatedZipcode"), 2);
        Thread.sleep(2000);
        plpPage.verifyFilter(0, 1);
        Thread.sleep(2000);
    }

    //Verify Filters using Delivery Type update SWAG - assert ok
    @Test(priority = 27, retryAnalyzer = Retry.class)
    public void verifyFilterDeliveryUpdateSWAG() throws InterruptedException{
        homePage.submitSwag(properties.getProperty("swagZipcode"), 1);
        pageElements.getZipCode().clear();
        Thread.sleep(2000);
        homePage.submitSwag(properties.getProperty("updatedZipcode"), 2);
        Thread.sleep(2000);
        plpPage.verifyFilter(1, 1);
        Thread.sleep(2000);
    }

    //Verify Filters using Occasion update SWAG - assert ok
    @Test(priority = 28, retryAnalyzer = Retry.class)
    public void verifyFilterOccasionUpdateSWAG() throws InterruptedException{
        homePage.submitSwag(properties.getProperty("swagZipcode"), 1);
        pageElements.getZipCode().clear();
        Thread.sleep(2000);
        homePage.submitSwag(properties.getProperty("updatedZipcode"), 2);
        Thread.sleep(2000);
        plpPage.verifyFilter(2, 2);
        Thread.sleep(2000);
    }

    //Verify Filters using Flower Type update SWAG - assert ok
    @Test(priority = 29, retryAnalyzer = Retry.class)
    public void verifyFilterFlowerUpdateSWAG() throws InterruptedException{
        homePage.submitSwag(properties.getProperty("swagZipcode"), 1);
        pageElements.getZipCode().clear();
        Thread.sleep(2000);
        homePage.submitSwag(properties.getProperty("updatedZipcode"), 2);
        Thread.sleep(2000);
        plpPage.verifyFilter(3, 2);
        Thread.sleep(2000);
    }

    //Verify Filters using Color update SWAG - assert ok
    @Test(priority = 30, retryAnalyzer = Retry.class)
    public void verifyFilterColorUpdateSWAG() throws InterruptedException{
        homePage.submitSwag(properties.getProperty("swagZipcode"), 1);
        pageElements.getZipCode().clear();
        Thread.sleep(2000);
        homePage.submitSwag(properties.getProperty("updatedZipcode"), 2);
        Thread.sleep(2000);
        plpPage.verifyFilter(4, 2);
        Thread.sleep(2000);
    }

    //Verify Filters using Gift Type update SWAG - assert ok
    @Test(priority = 31, retryAnalyzer = Retry.class)
    public void verifyFilterGiftTypeUpdateSWAG() throws InterruptedException{
        homePage.submitSwag(properties.getProperty("swagZipcode"), 1);
        pageElements.getZipCode().clear();
        Thread.sleep(2000);
        homePage.submitSwag(properties.getProperty("updatedZipcode"), 2);
        Thread.sleep(2000);
        plpPage.verifyFilter(5, 1);
        Thread.sleep(2000);
    }

    //Verify Reset Filters after SWAG update - assert ok
    @Test(priority = 32, retryAnalyzer = Retry.class)
    public void verifyFilterResetUpdateSWAG() throws InterruptedException{
        homePage.submitSwag(properties.getProperty("swagZipcode"), 1);
        pageElements.getZipCode().clear();
        Thread.sleep(2000);
        homePage.submitSwag(properties.getProperty("updatedZipcode"), 2);
        Thread.sleep(2000);
        plpPage.verifyFilter(0, 2);
        Thread.sleep(2000);
        plpPage.verifyFilterReset();
        Thread.sleep(2000);
    }

    //Verify Sort By High to Low function - assert ok
    @Test(priority = 33, retryAnalyzer = Retry.class)
    public void verifySortHighToLow() throws InterruptedException{
        homePage.submitSwag(properties.getProperty("swagZipcode"), 1);
        Thread.sleep(5000);
        plpPage.verifySort(1);
        Thread.sleep(2000);
    }

    //Verify Sort By Low to High function - assert ok
    @Test(priority = 34, retryAnalyzer = Retry.class)
    public void verifySortLowToHigh() throws InterruptedException{
        homePage.submitSwag(properties.getProperty("swagZipcode"), 1);
        Thread.sleep(5000);
        plpPage.verifySort(2);
        Thread.sleep(2000);
    }

    //Verify Sort By setting back Featured - assert ok
    @Test(priority = 35, retryAnalyzer = Retry.class)
    public void verifySortFeatured() throws InterruptedException{
        homePage.submitSwag(properties.getProperty("swagZipcode"), 1);
        Thread.sleep(5000);
        plpPage.verifySort(2);
        Thread.sleep(2000);
        plpPage.verifySort(3);
        Thread.sleep(2000);
    }

    //Verify Submitting SWAG in Force SWAG - assert ok
    @Test(priority = 36, retryAnalyzer = Retry.class)
    public void verifyForceSWAG() throws InterruptedException {
        Actions actions = new Actions(driver);
        homePage.plpIndexSelection(0, 1);
        System.out.println("Verify Submitting SWAG in Force SWAG");
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOf(pageElements.getProductPricesPLP().get(0)));
        actions.sendKeys(Keys.PAGE_DOWN).build().perform();
        plpPage.verifyForceSWAG(properties.getProperty("swagZipcode"));
        Thread.sleep(5000);
        wait.until(ExpectedConditions.visibilityOf(pageElements.getProductPricesPLP().get(0)));
        wait.until(ExpectedConditions.visibilityOf(pageElements.getSwagResultTxt()));
        System.out.println(pageElements.getSwagResultTxt().getText());
        Assert.assertTrue(pageElements.getSwagResultTxt().getText().contains(properties.getProperty("swagZipcode")));
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
