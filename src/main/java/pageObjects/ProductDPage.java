package pageObjects;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.time.Duration;

public class ProductDPage {
    WebDriver driver;
    PageElements pageElementsPDP;
    WebDriverWait wait;
    JavascriptExecutor jsePDP;
    public String productAddedToCartText, currentDate1, productName;

    public ProductDPage(WebDriver driver) { this.driver = driver; wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void selectStandardFromPDP() {
        pageElementsPDP = new PageElements(driver);
        if (pageElementsPDP.getProductOptionsPDP().size() > 0) {
            wait.until(ExpectedConditions.elementToBeClickable(pageElementsPDP.getProductOptionsPDP().get(0)));
            pageElementsPDP.getProductOptionsPDP().get(0).click();
            System.out.println("You have selected Standard from product selection");
        } else {
            selectPremiumFromPDP();
            System.out.println("Standard option is unavailable, Premium is selected!");
        }
    }

    public void selectPremiumFromPDP() { // THis will select Premium when you edit Item
        pageElementsPDP = new PageElements(driver);
        if (pageElementsPDP.getProductOptionsPDP().size() >= 2) {
            wait.until(ExpectedConditions.elementToBeClickable(pageElementsPDP.getProductOptionsPDP().get(2)));
            pageElementsPDP.getProductOptionsPDP().get(2).click();
            System.out.println("You have selected Premium from product selection");
        } else {
            selectStandardFromPDP();
            System.out.println("Premium option is unavailable, Standard is selected!");
        }
    }
    // This Method will Update the Date from PDP by using Date index
    public void editDateFromPDP(int newDate) throws InterruptedException {
        pageElementsPDP = new PageElements(driver);
        jsePDP = (JavascriptExecutor) driver;
        wait.until(ExpectedConditions.visibilityOf(pageElementsPDP.getPDPdeliveryDetails()));
        currentDate1 = pageElementsPDP.getPDPdeliveryDetails().getText(); // Get current Date in PDP to compare after Update
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsPDP.getEditFromPDP()));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        try {
            pageElementsPDP.getEditFromPDP().click(); // Click on Edit Delivery Info in PDP
        } catch (Exception e) {
            jsePDP.executeScript("arguments[0].click();", pageElementsPDP.getEditFromPDP());
        }
        try {
            Thread.sleep(1000);
            wait.until(ExpectedConditions.attributeToBeNotEmpty(pageElementsPDP.getZipCode(),"value"));// make sure there is Zipcode
            pageElementsPDP.getContinueCTAinPDP().click(); // Click Continue on Zipcode
        } catch (Exception e) {
            jsePDP.executeScript("arguments[0].click();", pageElementsPDP.getContinueCTAinPDP());
        }
        if (pageElementsPDP.getAvailableDatesFromPDP().size() >= 1) {
            wait.until(ExpectedConditions.elementToBeClickable(pageElementsPDP.getAvailableDatesFromPDP().get(newDate)));
            Thread.sleep(1000);
            pageElementsPDP.getAvailableDatesFromPDP().get(newDate).sendKeys(Keys.ENTER); // Selecting new Date by index of availability
        } else {
            System.out.println("Date is available only for 1 more day, not more");
            pageElementsPDP.getAvailableDatesFromPDP().get(newDate+1).click(); // if that date is not available pick the next date
        }
        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsPDP.getContinueCTAinPDP())); //wait for calendar
        pageElementsPDP.getContinueCTAinPDP().click(); // Click Update CTA on calendar
        wait.until(ExpectedConditions.visibilityOf(pageElementsPDP.getPDPdeliveryDetails()));
    }
    // This method will Edit Zipcode from PDP page
    public void editZipcodeFromPDP(String currentZipcode, String newZipcode) {
        pageElementsPDP = new PageElements(driver);
        jsePDP = (JavascriptExecutor) driver;
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsPDP.getEditFromPDP()));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        try {
            pageElementsPDP.getEditFromPDP().click(); // Click on Edit Delivery Info in PDP
        } catch (Exception e) {
            jsePDP.executeScript("arguments[0].click();", pageElementsPDP.getEditFromPDP());
        }
        wait.until(ExpectedConditions.textToBePresentInElementValue(pageElementsPDP.getZipCode(), currentZipcode));
        pageElementsPDP.getZipCode().clear(); // Clear the current zipCode
        wait.until(ExpectedConditions.textToBePresentInElementValue(pageElementsPDP.getZipCode(),""));// wait till empty
        pageElementsPDP.getZipCode().sendKeys(newZipcode);// Enter new Zipcode
        wait.until(ExpectedConditions.textToBePresentInElementValue(pageElementsPDP.getZipCode(), newZipcode));
        pageElementsPDP.getContinueCTAinPDP().click();// Clicking Continue CTA on Zipcode
        if (pageElementsPDP.getContinueCTAinPDP().isDisplayed() && pageElementsPDP.getDefaultDate().isSelected()) {
            wait.until(ExpectedConditions.elementToBeClickable(pageElementsPDP.getContinueCTAinPDP()));
            pageElementsPDP.getContinueCTAinPDP().click(); // Clicking Continue in Calendar
        } else {
            wait.until(ExpectedConditions.elementToBeClickable(pageElementsPDP.getContinueCTAinPDP()));
            pageElementsPDP.getContinueCTAinPDP().sendKeys(Keys.ENTER);
        }
        wait.until(ExpectedConditions.visibilityOf(pageElementsPDP.getPDPdeliveryDetails())); // wait for new delivery Info
    }
    // Add Product to Cart
    public void addProductToCartAfterSWAG() throws InterruptedException {
        pageElementsPDP = new PageElements(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsPDP.getAddToBagCTA())); // wait
        productName = pageElementsPDP.getProductTitlePDP().getText();
        pageElementsPDP.getAddToBagCTA().sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsPDP.getCheckoutCTA()));
        Thread.sleep(1000);
        productAddedToCartText = pageElementsPDP.getItemAddedMessage().getText();
        Assert.assertEquals(pageElementsPDP.getItemAddedMessage().getText(), "Item added successfully");
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsPDP.getCheckoutCTA())); // wait
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsPDP.getCloseMiniCart_x()));
        pageElementsPDP.getCloseMiniCart_x().sendKeys(Keys.ENTER); // close mini cart
    }
    // This Method will Submit SWAG in PDP and Adds product to cart
    public void addProductToCart_SWAG_InPDP(String zipCodePdpSWAG){
        pageElementsPDP = new PageElements(driver);
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsPDP.getAddToBagCTA())); // wait
        pageElementsPDP.getAddToBagCTA().sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.textToBePresentInElementValue(pageElementsPDP.getZipCode(), ""));
        pageElementsPDP.getZipCode().clear(); // Clear the current zipCode
        pageElementsPDP.getZipCode().sendKeys(zipCodePdpSWAG);// Enter new Zipcode
        wait.until(ExpectedConditions.textToBePresentInElementValue(pageElementsPDP.getZipCode(), zipCodePdpSWAG));
        pageElementsPDP.getContinueCTAinPDP().click();// Clicking Continue CTA on Zipcode
        if (pageElementsPDP.getContinueCTAinPDP().isDisplayed() && pageElementsPDP.getDefaultDate().isSelected()) {
            wait.until(ExpectedConditions.elementToBeClickable(pageElementsPDP.getContinueCTAinPDP()));
            pageElementsPDP.getContinueCTAinPDP().click(); // Clicking Continue in Calendar
        } else {
            wait.until(ExpectedConditions.elementToBeClickable(pageElementsPDP.getContinueCTAinPDP()));
            pageElementsPDP.getContinueCTAinPDP().sendKeys(Keys.ENTER);
        }
        wait.until(ExpectedConditions.visibilityOf(pageElementsPDP.getPDPdeliveryDetails())); // wait for new delivery Info
    }
    // THis method will trigger Recommendation model
    public void triggerRecommendationModel(String invalidZipcode, String searchInput,int productIndex) throws InterruptedException {
        pageElementsPDP = new PageElements(driver);
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsPDP.getSearchFromNav())); // wait to Click Search from Menu item
        pageElementsPDP.getSearchFromNav().click();
        wait.until(ExpectedConditions.visibilityOf(pageElementsPDP.getSearchBox()));
        pageElementsPDP.getSearchBox().sendKeys(searchInput); // Input word to search box
        wait.until(ExpectedConditions.textToBePresentInElementValue(pageElementsPDP.getSearchBox(), searchInput));
        Thread.sleep(1000);
        try {
            pageElementsPDP.getSearchIcon().click(); // Click search icon
        } catch (Exception e) {
            jsePDP.executeScript("arguments[0].click();", pageElementsPDP.getSearchIcon());
        }
        wait.until(ExpectedConditions.visibilityOf(pageElementsPDP.getSearchResults())); // wait for results
        System.out.println(pageElementsPDP.getSearchResults().getText());// get text what you searched for
        if (pageElementsPDP.getSwagResultTxt().isDisplayed() && pageElementsPDP.getAvailableProductList().size() > 0) {
            try { // Selecting a Product without a SWAG
                wait.until(ExpectedConditions.elementToBeClickable(pageElementsPDP.getAvailableProductList().get(productIndex)));
                pageElementsPDP.getAvailableProductList().get(productIndex).click();
            } catch (Exception e) {
                jsePDP.executeScript("arguments[0].click();", pageElementsPDP.getAvailableProductList().get(productIndex));
            }
        }
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsPDP.getAddToBagCTA())); // wait
        pageElementsPDP.getAddToBagCTA().sendKeys(Keys.ENTER); // Click "Add to Bag" CTA
        wait.until(ExpectedConditions.textToBePresentInElementValue(pageElementsPDP.getZipCode(), ""));
        pageElementsPDP.getZipCode().sendKeys(invalidZipcode);// Enter new Zipcode
        Thread.sleep(1000);
        try {
            wait.until(ExpectedConditions.textToBePresentInElementValue(pageElementsPDP.getZipCode(), invalidZipcode));
            pageElementsPDP.getContinueCTAinPDP().click();// Clicking Continue CTA on Zipcode
        } catch (Exception e) {
            jsePDP.executeScript("arguments[0].click();", pageElementsPDP.getContinueCTAinPDP());
        }
        Thread.sleep(2000);
        if (pageElementsPDP.getRecommendationModel().isDisplayed()){
            System.out.println("Recommendation model is displayed");
        }else if(pageElementsPDP.getZipcodeErrorPDP().isDisplayed()){
            System.out.println("Recommendation is not displayed But There is Error message in zipcode");
        }else{
            wait.until(ExpectedConditions.visibilityOf(pageElementsPDP.getRecommendationModel()));
            System.out.println("Needs some wait time");
        }
    }
    // This method will Select a Product in Recommendation Model
    public void selectRecommendationProduct(int indexOfProduct) throws InterruptedException {
        pageElementsPDP = new PageElements(driver);
        wait.until(ExpectedConditions.visibilityOf(pageElementsPDP.getRecommendationModel()));
        if (pageElementsPDP.getRecommendationProducts().size() >= indexOfProduct){
            pageElementsPDP.getRecommendationProducts().get(indexOfProduct).click();
        }else{
            System.out.println("The products are less then the index you picked");
        }
        Thread.sleep(1000);
    }
}