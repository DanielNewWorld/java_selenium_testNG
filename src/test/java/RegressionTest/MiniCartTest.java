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
public class MiniCartTest extends base {
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

    // Verify Edit Variation of Product from MINI CART
    @Test(priority = 1, retryAnalyzer = Retry.class)
    public void editVariationProductFromMiniCart() throws InterruptedException {
        homePage.submitSwag(properties.getProperty("swagZipcode"), 0);
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOf(pageElements.getProductPricesPLP().get(0)));
        plpPage.selectProductFromPLP(0);
        pDpPage.addProductToCartAfterSWAG();
        pageElements.getCloseMiniCart_x().sendKeys(Keys.ENTER);
        Thread.sleep(2000);
        miniCart.editProductVariationFromMiniCart(0);
        Assert.assertEquals(pageElements.getUpdateSuccessMsgOnMiniCart().getText(), "Your item has been updated");
        System.out.println("Test case = " + pageElements.getUpdateSuccessMsgOnMiniCart().getText());
    }
    // Verify ADD Ad-on From MINI CART
    @Test(priority = 2, retryAnalyzer = Retry.class)
    public void addAddonFromMiniCart() throws InterruptedException {
        homePage.submitSwag(properties.getProperty("swagZipcode"), 0); // Submitting SWAG
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOf(pageElements.getProductPricesPLP().get(0)));
        plpPage.selectProductFromPLP(0); // Selecting item From PLP
        pDpPage.addProductToCartAfterSWAG(); // Adding Product to Cart
        miniCart.addAddonFromMiniCart(0); // 1st Selecting Addon From mini Cart
        Assert.assertEquals(pageElements.getAddonNameText().getText(), miniCart.firstAddedAddonName); // current addon in cart name check with 1st added
        System.out.println("Test case Product addon added = "+ pageElements.getAddonNameText().getText()+" "+ miniCart.firstAddedAddonName);
    }
    // Verify Edit Ad-on From MINI CART
    @Test(priority = 3, retryAnalyzer = Retry.class)
    public void editAddonFromMiniCart() throws InterruptedException {// Edit Addon from cart
        homePage.submitSwag(properties.getProperty("swagZipcode"), 0); // Submitting SWAG
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOf(pageElements.getProductPricesPLP().get(0)));
        plpPage.selectProductFromPLP(1); // Selecting item From PLP
        pDpPage.addProductToCartAfterSWAG(); // Adding Product to Cart
        Thread.sleep(2000);
        miniCart.editAddOnFromMiniCart(0, 1); // Unselect 0 and 1 by index Select different addon
        Assert.assertNotEquals(miniCart.firstAddedAddonName, miniCart.secondUpdatedAddonName);
        System.out.println("Test case 1st added Addon = "+ miniCart.firstAddedAddonName+", Updated to = "+ miniCart.secondUpdatedAddonName);
    }
    // Verify Remove Ad-on From MINI CART
    @Test(priority = 4, retryAnalyzer = Retry.class)
    public void removeAddonFromMiniCart() throws InterruptedException {
        homePage.submitSwag(properties.getProperty("swagZipcode"), 0); // Submit SWAG
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOf(pageElements.getProductPricesPLP().get(0)));
        plpPage.selectProductFromPLP(0); // Select a product
        pDpPage.addProductToCartAfterSWAG(); // Add Product to Cart
        Thread.sleep(1000);
        miniCart.addAddonFromMiniCart(0); // Add Addon to Mini Cart
        miniCart.removeAddOnFromMiniCart(); // Remove Addon
        Thread.sleep(2000);
        Assert.assertEquals(pageElements.getAlertPopupMessage().getText(), "The item has been removed.");
        System.out.println("Test case = " + pageElements.getAlertPopupMessage().getText());
    }
    // Verify Remove Product From MINI CART
    @Test(priority = 5, retryAnalyzer = Retry.class)
    public void removeProductFromMiniCart() throws InterruptedException {
        homePage.submitSwag(properties.getProperty("swagZipcode"), 0); // submit SWAG with 90006
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOf(pageElements.getProductPricesPLP().get(0)));
        plpPage.selectProductFromPLP(0); // Select a Product
        pDpPage.addProductToCartAfterSWAG(); // Add Product to Cart
        miniCart.removeProductFromMiniCart(); // Remove Product from Cart
        Assert.assertEquals(pageElements.getEmptyCartMessage().getText(), "Your Cart is Empty");
        System.out.println("Test case = " + pageElements.getAlertPopupMessage().getText() + " " + pageElements.getEmptyCartMessage().getText());
    }
    // Verify Edit Date From MINI CART
    @Test(priority = 6, retryAnalyzer = Retry.class)
    public void editDateFromMiniCart() throws InterruptedException {
        homePage.submitSwag(properties.getProperty("swagZipcode"), 0); // Submit SWAG
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOf(pageElements.getProductPricesPLP().get(0)));
        plpPage.selectProductFromPLP(0); // Select Product by index number
        wait.until(ExpectedConditions.elementToBeClickable(pageElements.getAddToBagCTA()));
        String currentDate1 = pageElements.getPDPdeliveryDetails().getText(); //Checking the 1st date
        pDpPage.addProductToCartAfterSWAG(); // Add Product to Cart
        Thread.sleep(1000);
        miniCart.editDateFromMiniCart(2); // Selecting date by available index
        System.out.println("Test case 1st date " + currentDate1 + " Updated date = "+ pageElements.getPDPdeliveryDetails().getText());
        Assert.assertNotEquals(currentDate1, pageElements.getPDPdeliveryDetails().getText());
    }
    // Verify Edit Zip code From MINI CART
    @Test(priority = 7, retryAnalyzer = Retry.class)
    public void editZipcodeFromMiniCart() throws InterruptedException {
        homePage.submitSwag(properties.getProperty("swagZipcode"), 0); // Submit SWAG enter zip and available dates index
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOf(pageElements.getProductPricesPLP().get(0)));
        plpPage.selectProductFromPLP(0); // Select Product by Index from PLP
        pDpPage.addProductToCartAfterSWAG(); // Add Product To Cart
        wait.until(ExpectedConditions.visibilityOf(pageElements.getPDPdeliveryDetails()));
        String currentZipCodeInPDP = pageElements.getPDPdeliveryDetails().getText(); // get PDP/Cart Delivery Info
        miniCart.editZipCodeFromMiniCart(properties.getProperty("swagZipcode"), properties.getProperty("updatedZipcode")); // Update Zipcode
        System.out.println("Test case 2nd Current Zipcode = "+currentZipCodeInPDP+ " updated date = "+ pageElements.getPDPdeliveryDetails().getText());
        Assert.assertNotEquals(currentZipCodeInPDP, pageElements.getPDPdeliveryDetails().getText());
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