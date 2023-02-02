package pageObjects;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.time.Duration;
import java.util.List;

public class MiniCart{
    WebDriver driver;
    JavascriptExecutor jseMiniCart;
    WebDriverWait wait;
    public String firstAddedAddonName, secondUpdatedAddonName;
    public MiniCart(WebDriver driver){ this.driver = driver; wait = new WebDriverWait(driver, Duration.ofSeconds(30)); }

    // This method will Add Addon to the Cart
    public void addAddonFromMiniCart(int addonSelectionIndex) throws InterruptedException { // Adding 1st Add-on from mini cart
        PageElements pageElementsMiniCart1 = new PageElements(driver);
        jseMiniCart = (JavascriptExecutor) driver;
        Actions actions1 = new Actions(driver);
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsMiniCart1.getCartIcon()));
        pageElementsMiniCart1.getCartIcon().sendKeys(Keys.ENTER); // Clicking to Cart page
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsMiniCart1.getAddAddonFromMiniCartCTA()));
        pageElementsMiniCart1.getAddAddonFromMiniCartCTA().sendKeys(Keys.ENTER);// Click to Add Addon from cart
        if (pageElementsMiniCart1.getAddonSelections().size() > 0) {
            List<WebElement> addonNames = driver.findElements(By.xpath("//div[@class='font-weight-medium']"));
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='font-weight-medium']")));
            try {
                Thread.sleep(3000);
                wait.until(ExpectedConditions.visibilityOf(addonNames.get(addonSelectionIndex)));
                pageElementsMiniCart1.getAddonSelections().get(addonSelectionIndex).click(); // Select Addon button
                Thread.sleep(1000);
                firstAddedAddonName = addonNames.get(addonSelectionIndex).getText(); // get 1st Selected addon name to compare
            } catch (Exception e) {
                jseMiniCart.executeScript("arguments[0].click();", pageElementsMiniCart1.getAddonSelections().get(addonSelectionIndex));
            }
            wait.until(ExpectedConditions.elementToBeClickable(pageElementsMiniCart1.getUpdateItemCTA()));
            actions1.scrollToElement(pageElementsMiniCart1.getUpdateItemCTA()).build().perform();
            pageElementsMiniCart1.getUpdateItemCTA().sendKeys(Keys.ENTER); // click Update after addon cta
            wait.until(ExpectedConditions.visibilityOf(pageElementsMiniCart1.getAlertPopupMessage()));// wait for Success message
        }else {
            System.out.println("There no addons for this Product");
        }
        pageElementsMiniCart1.getCloseMiniCart_x().sendKeys(Keys.ENTER); // close mini cart
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsMiniCart1.getCartIcon()));
        pageElementsMiniCart1.getCartIcon().sendKeys(Keys.ENTER); // open Mini Cart
        Thread.sleep(1000);// bellow is the code how to scroll in mini cart
        WebElement addonNameInMini = driver.findElement(By.xpath("//div[@id='addon-details']"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='addon-details']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addonNameInMini);
        Thread.sleep(2000);
    }
    // This method will Edit Addon by Unselecting current one and Selecting different one
    public void editAddOnFromMiniCart(int firstAddonIndex, int secondAddonIndex) throws InterruptedException {
        PageElements pageElementsMiniCart2 = new PageElements(driver);
        jseMiniCart = (JavascriptExecutor) driver;
        addAddonFromMiniCart(firstAddonIndex);
        Thread.sleep(1000);
        String oldAddonName = firstAddedAddonName;
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsMiniCart2.getCartIcon()));
        pageElementsMiniCart2.getCartIcon().sendKeys(Keys.ENTER); // Clicking back to Cart page
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsMiniCart2.getEditAddOn()));
        pageElementsMiniCart2.getEditAddOn().sendKeys(Keys.ENTER); // Edit Add-On
        Thread.sleep(2000);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(pageElementsMiniCart2.getAddonSelections().get(firstAddonIndex)));
            pageElementsMiniCart2.getAddonSelections().get(firstAddonIndex).click(); // Unselecting current Addon
        }catch (Exception e){
            jseMiniCart.executeScript("arguments[0].click();", pageElementsMiniCart2.getAddonSelections().get(firstAddonIndex));
        }
        Thread.sleep(1000);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(pageElementsMiniCart2.getAddonSelections().get(secondAddonIndex)));
            pageElementsMiniCart2.getAddonSelections().get(secondAddonIndex).click(); // Selecting a different Addon
        }catch (Exception e) {
            jseMiniCart.executeScript("arguments[0].click();", pageElementsMiniCart2.getAddonSelections().get(secondAddonIndex));
        }
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsMiniCart2.getUpdateItemCTA()));
        pageElementsMiniCart2.getUpdateItemCTA().sendKeys(Keys.ENTER); // click Update after addon cta
        wait.until(ExpectedConditions.visibilityOf(pageElementsMiniCart2.getAlertPopupMessage())); // wait to see success message
        if (pageElementsMiniCart2.getAddonNameText().getText().equals(oldAddonName)){
        Thread.sleep(2000);
        }
        secondUpdatedAddonName = pageElementsMiniCart2.getAddonNameText().getText();
        Assert.assertNotEquals(secondUpdatedAddonName, "");
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsMiniCart2.getCheckoutCTA()));
    }
    // Remove Add-on From Mini Cart
    public void removeAddOnFromMiniCart() {
        PageElements pageElementsMiniCart3 = new PageElements(driver);
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsMiniCart3.getCartIcon()));
        pageElementsMiniCart3.getCartIcon().sendKeys(Keys.ENTER); // Clicking back to Cart page
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsMiniCart3.getRemoveAddon()));
        System.out.println(pageElementsMiniCart3.getAddonNameText().getText()+ " removed Addon");
        pageElementsMiniCart3.getRemoveAddon().sendKeys(Keys.RETURN);
        wait.until(ExpectedConditions.visibilityOf(pageElementsMiniCart3.getAlertPopupMessage()));
        Assert.assertTrue(pageElementsMiniCart3.getAlertPopupMessage().getText().contains("removed"));
    }
    // This method will verify Removing Product From Mini Cart
    public void removeProductFromMiniCart() {
        PageElements pageElementsMiniCart4 = new PageElements(driver);
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsMiniCart4.getCartIcon()));
        pageElementsMiniCart4.getCartIcon().sendKeys(Keys.ENTER); // Clicking back to Cart page
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsMiniCart4.getRemoveProduct()));
        pageElementsMiniCart4.getRemoveProduct().sendKeys(Keys.RETURN);
        wait.until(ExpectedConditions.visibilityOf(pageElementsMiniCart4.getAlertPopupMessage()));
        Assert.assertTrue(pageElementsMiniCart4.getAlertPopupMessage().getText().contains("removed"));
    }
    // This method will verify Edit Product From Mini Cart
    public void editProductVariationFromMiniCart(int productVariationIndex){
        PageElements pageElementsMiniCart4 = new PageElements(driver);
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsMiniCart4.getCartIcon()));
        pageElementsMiniCart4.getCartIcon().sendKeys(Keys.ENTER); // Clicking back to Cart page
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsMiniCart4.getEditMainItem()));
        pageElementsMiniCart4.getEditMainItem().sendKeys(Keys.ENTER); // click Edit in cart page
        if(pageElementsMiniCart4.getProductOptionsPDP().size() > 0){
            if(!pageElementsMiniCart4.getProductOptionsPDP().get(productVariationIndex).isSelected() &&
                    pageElementsMiniCart4.getProductOptionsPDP().get(productVariationIndex).isDisplayed()){
                pageElementsMiniCart4.getProductOptionsPDP().get(productVariationIndex).click();
            }else{
                System.out.println("The Variation is already Selected so choosing the Next one");
                pageElementsMiniCart4.getProductOptionsPDP().get(productVariationIndex+1).click();
            }
        }else {
            System.out.println("There are no optional Variations");
        }
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsMiniCart4.getUpdateItemFromMiniCartCTA()));
        pageElementsMiniCart4.getUpdateItemFromMiniCartCTA().sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOf(pageElementsMiniCart4.getUpdateSuccessMsgOnMiniCart()));
    }
    // THis method will verify Updating Date From Mini Cart
    public void editDateFromMiniCart(int selectUpdatedDate) throws InterruptedException {
        PageElements pageElementsMiniCart5 = new PageElements(driver);
        jseMiniCart = (JavascriptExecutor) driver;
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsMiniCart5.getCartIcon()));
        pageElementsMiniCart5.getCartIcon().sendKeys(Keys.ENTER); // Clicking back to Cart page
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsMiniCart5.getEditDeliveryInfoInMiniCart()));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", pageElementsMiniCart5.getEditDeliveryInfoInMiniCart()); //move to Edit
        pageElementsMiniCart5.getEditDeliveryInfoInMiniCart().sendKeys(Keys.ENTER); // Click Edit Delivery Info
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        try {
            Thread.sleep(1000);
            wait.until(ExpectedConditions.attributeToBeNotEmpty(pageElementsMiniCart5.getZipCode(),"value"));// make sure there is Zipcode
            pageElementsMiniCart5.getContinueCTAinPDP().click(); // Click Continue on Zipcode
        } catch (Exception e) {
            jseMiniCart.executeScript("arguments[0].click();", pageElementsMiniCart5.getContinueCTAinPDP());
        }
        if (pageElementsMiniCart5.getAvailableDatesFromPDP().size() >= 1) {
            wait.until(ExpectedConditions.elementToBeClickable(pageElementsMiniCart5.getAvailableDatesFromPDP().get(selectUpdatedDate)));
            Thread.sleep(1000);
            pageElementsMiniCart5.getAvailableDatesFromPDP().get(selectUpdatedDate).sendKeys(Keys.ENTER); // Selecting new Date by index of availability
        } else {
            System.out.println("Date is available only for 1 more day, not more");
            pageElementsMiniCart5.getAvailableDatesFromPDP().get(selectUpdatedDate+1).click(); // if that date is not available pick the next date
        }
        Thread.sleep(1000);
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsMiniCart5.getContinueCTAinPDP())); //wait for calendar
        pageElementsMiniCart5.getContinueCTAinPDP().click(); // Click Update CTA on calendar
        Thread.sleep(3000);
        pageElementsMiniCart5.getCloseMiniCart_x().sendKeys(Keys.ENTER); // close mini cart
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOf(pageElementsMiniCart5.getPDPdeliveryDetails()));
    }
    // This method will Update Zipcode From Mini Cart by using 2 Strings Current zip and Update zip
    public void editZipCodeFromMiniCart(String currentZipcodeMiniCart, String updatedZipcodeMiniCart) throws InterruptedException {
        PageElements pageElementsMiniCart5 = new PageElements(driver);
        jseMiniCart = (JavascriptExecutor) driver;
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsMiniCart5.getCartIcon()));
        pageElementsMiniCart5.getCartIcon().sendKeys(Keys.ENTER); // Clicking back to Cart page
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsMiniCart5.getEditDeliveryInfoInMiniCart()));
        pageElementsMiniCart5.getEditDeliveryInfoInMiniCart().sendKeys(Keys.ENTER); // Click Edit Delivery Info
        wait.until(ExpectedConditions.textToBePresentInElementValue(pageElementsMiniCart5.getZipCode(), currentZipcodeMiniCart));
        pageElementsMiniCart5.getZipCode().clear(); // Clear the old Zipcode
        wait.until(ExpectedConditions.textToBePresentInElementValue(pageElementsMiniCart5.getZipCode(),""));
        pageElementsMiniCart5.getZipCode().sendKeys(updatedZipcodeMiniCart); // paste the New Zipcode
        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsMiniCart5.getContinueCTAinPDP()));
        pageElementsMiniCart5.getContinueCTAinPDP().sendKeys(Keys.ENTER); // Click Continue on Zipcode
        Thread.sleep(3000);
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsMiniCart5.getContinueCTAinPDP()));
        pageElementsMiniCart5.getContinueCTAinPDP().sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOf(pageElementsMiniCart5.getCloseMiniCart_x()));
        pageElementsMiniCart5.getCloseMiniCart_x().sendKeys(Keys.ENTER); // close mini cart
        Thread.sleep(3000);
        wait.until(ExpectedConditions.visibilityOf(pageElementsMiniCart5.getPDPdeliveryDetails()));
    }

}
