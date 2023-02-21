package pageObjects;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.List;

public class HomePage {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor jseHomePage;
    public String currentMonthAndDateNameInCalendar, swagResultsOnDateUpdateText, selectedCarousel;
    public HomePage(WebDriver driver){ this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // 2. Submit SWAG with valid zip and date - ok
    public void submitSwag(String zipCode1, int datePickerIndex) throws InterruptedException{
        PageElements pageElementsHome1 = new PageElements(driver);
        Actions actions = new Actions(driver);
        jseHomePage = (JavascriptExecutor) driver;
        wait.until(ExpectedConditions.visibilityOf(pageElementsHome1.getZipCode()));
        pageElementsHome1.getZipCode().clear();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        pageElementsHome1.getZipCode().sendKeys(zipCode1);// Enter Zip code
        wait.until(ExpectedConditions.textToBePresentInElementValue(pageElementsHome1.getZipCode(), zipCode1));
        actions.sendKeys(Keys.TAB).build().perform(); // Click TAB
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsHome1.getSwagCalendarIcon()));
        pageElementsHome1.getSwagCalendarIcon().sendKeys(Keys.ENTER); // Click to Calendar

        if (datePickerIndex == 0) {
            pageElementsHome1.getDefaultDate().sendKeys(Keys.ENTER);
        }
        if (pageElementsHome1.getAvailableDatesFromSWAG().size() < datePickerIndex && pageElementsHome1.getDefaultDate().isDisplayed()){
             pageElementsHome1.getNextMonthBtn().click();
         }
         if (pageElementsHome1.getAvailableDatesFromSWAG().size() >= datePickerIndex) {
             wait.until(ExpectedConditions.elementToBeClickable(pageElementsHome1.getAvailableDatesFromSWAG().get(datePickerIndex-1)));
             try {
                 pageElementsHome1.getAvailableDatesFromSWAG().get(datePickerIndex-1).click(); // Select a date
             } catch (Exception e) {
                 jseHomePage.executeScript("arguments[0].click();",
                         pageElementsHome1.getAvailableDatesFromSWAG().get(datePickerIndex-1));
             }
         }
        Thread.sleep(2000);
         wait.until(ExpectedConditions.elementToBeClickable(pageElementsHome1.getShopNowCTA()));
         try {
             pageElementsHome1.getShopNowCTA().click();
         } catch (Exception e) {
             jseHomePage.executeScript("arguments[0].click();",pageElementsHome1.getShopNowCTA());
         }
        Thread.sleep(4000);
        wait.until(ExpectedConditions.presenceOfElementLocated(pageElementsHome1.swagResultsText));
    }

    // Verify User submitting SWAG with Valid Zip and without having to select Date - ок
    public void submitSwagWithoutEnteringDate(String zipCode2) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        PageElements pageElementsHome2 = new PageElements(driver);
        pageElementsHome2.getZipCode().clear();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsHome2.getShopNowCTA()));
        pageElementsHome2.getZipCode().sendKeys(zipCode2);// Enter Zip code
        wait.until(ExpectedConditions.textToBePresentInElementValue(pageElementsHome2.getZipCode(), zipCode2));
        pageElementsHome2.getShopNowCTA().click();
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsHome2.getSwagCalendarIcon()));
        pageElementsHome2.getShopNowCTA().click();
        wait.until(ExpectedConditions.presenceOfElementLocated(pageElementsHome2.swagResultsText));
        swagResultsOnDateUpdateText = pageElementsHome2.getSwagResultTxt().getText();
    }

    // Verify user is able to submit SWAG by using TAB and ENTER in HOME PAGE - ok
    public void submitSwagByUsingTABandENTER(String zipCode) throws InterruptedException {
        PageElements pageElements2a = new PageElements(driver);
        Actions actionsHomePage2a = new Actions(driver);
        wait.until(ExpectedConditions.visibilityOf(pageElements2a.getZipCode()));
        wait.until(ExpectedConditions.elementToBeClickable(pageElements2a.getZipCode()));
        pageElements2a.getZipCode().clear();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        pageElements2a.getZipCode().click();
        actionsHomePage2a.sendKeys(zipCode).perform();
        wait.until(ExpectedConditions.attributeToBeNotEmpty(pageElements2a.getZipCode(), "value"));
        actionsHomePage2a.sendKeys(Keys.TAB).build().perform(); // Click TAB
        Thread.sleep(4000);
        actionsHomePage2a.sendKeys(Keys.ENTER).build().perform();// Click ENTER
        wait.until(ExpectedConditions.presenceOfElementLocated(pageElements2a.swagResultsText));
    }

    // Verify user clicking into Calendar icon displays Calendar with available dates - ок
    public void availableDatesOnCalendarIconClick(String zipCode3) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        PageElements pageElementsHome3 = new PageElements(driver);
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsHome3.getShopNowCTA()));
        pageElementsHome3.getZipCode().clear();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        pageElementsHome3.getZipCode().sendKeys(zipCode3);// Enter Zip code
        wait.until(ExpectedConditions.textToBePresentInElementValue(pageElementsHome3.getZipCode(), zipCode3));
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.TAB).build().perform(); // Click TAB
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30)); // Wait
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsHome3.getSwagCalendarIcon()));
        pageElementsHome3.getSwagCalendarIcon().sendKeys(Keys.ENTER); // Click to Calendar
    }

    // Verify Open Calendar should default current Month and current Date - ok
    public void verifyCalendarIsWithCurrentMonthAndDate(String zipCode4){
        PageElements pageElementsHome4 = new PageElements(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        pageElementsHome4.getZipCode().clear(); // clear Zipcode field
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        availableDatesOnCalendarIconClick(zipCode4); // Enter zipcode and click on calendar
         wait.until(ExpectedConditions.elementToBeClickable(pageElementsHome4.getDefaultDate()));
         try {
             pageElementsHome4.getAvailableDatesFromSWAG().get(0).click(); // Clicking on default date
         } catch (Exception e) {
             jseHomePage.executeScript("arguments[0].click();",pageElementsHome4.getDefaultDate());
         }
         wait.until(ExpectedConditions.attributeToBeNotEmpty(pageElementsHome4.getDateFieldInSWAG(), "value"));// wait for field not to be empty
         currentMonthAndDateNameInCalendar = pageElementsHome4.getDateFieldInSWAG().getAttribute("value");
    }

    // Verify User should be able to Enter Date manually in Date field - ок
    public void verifyUserIsAbleToEnterDateManuallyInSWAG(String zipcode5, String dateInput){
        PageElements pageElementsHome5 = new PageElements(driver);
        Actions actionsHomePage5 = new Actions(driver);
        wait.until(ExpectedConditions.visibilityOf(pageElementsHome5.getZipCode()));
        pageElementsHome5.getZipCode().clear(); // clear zipcode field
        pageElementsHome5.getZipCode().sendKeys(zipcode5); // Enter Zipcode
        wait.until(ExpectedConditions.attributeToBeNotEmpty(pageElementsHome5.getZipCode(), "value"));
        actionsHomePage5.sendKeys(Keys.TAB).build().perform(); // Click TAB
        wait.until(ExpectedConditions.attributeToBeNotEmpty(pageElementsHome5.getDateFieldInSWAG(), "value"));
        pageElementsHome5.getDateFieldInSWAG().sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));// Control A and Clear the date from default date
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        pageElementsHome5.getDateFieldInSWAG().sendKeys(dateInput); // Enter date
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsHome5.getShopNowCTA()));
        pageElementsHome5.getShopNowCTA().click(); // click Shop Now CTA
        wait.until(ExpectedConditions.attributeToBeNotEmpty(pageElementsHome5.getDateFieldInSWAG(), "value"));
    }

    // Verify User should be able to Update Zip code in Zip code field after SWAG
    public void updateZipCodeInSWAG(String updatedZipCode, int datePickerIndex) throws InterruptedException{
        PageElements pageElementsHome6 = new PageElements(driver);
        Actions actionsHomePage6 = new Actions(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        pageElementsHome6.getZipCode().clear();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        submitSwag(updatedZipCode, datePickerIndex);
        wait.until(ExpectedConditions.visibilityOf(pageElementsHome6.getSwagResultTxt()));
        wait.until(ExpectedConditions.textToBePresentInElementValue(pageElementsHome6.getZipCode(), updatedZipCode));
        actionsHomePage6.sendKeys(Keys.TAB).build().perform(); // Click TAB
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsHome6.getSwagCalendarIcon()));
        pageElementsHome6.getShopNowCTA().click();
    }

    // Update Date in Calendar after SWAG - ок
    public void updateDateInCalendarAfterSWAG(String zipCode7, int updateDatePickerIndex) throws InterruptedException{
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        submitSwagWithoutEnteringDate(zipCode7); //Submitting SWAG with Default Date 1st
        Thread.sleep(4000);
        submitSwag(zipCode7, updateDatePickerIndex);
    }

    // Verify User entering INVALID ZIPCODE should display error message in zip code field - ok
    public void verifyInvalidZipcodeErrorMessage(String inValidZip){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        PageElements pageElementsHome8 = new PageElements(driver);
        wait.until(ExpectedConditions.visibilityOf(pageElementsHome8.getZipCode()));
        pageElementsHome8.getZipCode().clear(); // Clear the old zipcode
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        wait.until(ExpectedConditions.textToBePresentInElementValue(pageElementsHome8.getZipCode(), ""));
        pageElementsHome8.getZipCode().sendKeys(inValidZip);// Paste new zipcode
        wait.until(ExpectedConditions.textToBePresentInElementValue(pageElementsHome8.getZipCode(), inValidZip));
        //pageElementsHome8.getShopNowCTA().click(); // Click Shop Now
        wait.until(ExpectedConditions.visibilityOf(pageElementsHome8.getInvalidInputErrorInSWAG()));
    }

    // This method will Verify Search Result by submitting String search input - ok
    public void searchForInput(String searchInput) throws InterruptedException {
        PageElements pageElementsHome9 = new PageElements(driver);
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsHome9.getSearchFromNav())); // wait to Click Search from Menu item
        pageElementsHome9.getSearchFromNav().click();
        wait.until(ExpectedConditions.visibilityOf(pageElementsHome9.getSearchBox()));
        pageElementsHome9.getSearchBox().sendKeys(searchInput); // Input word to search box
        wait.until(ExpectedConditions.textToBePresentInElementValue(pageElementsHome9.getSearchBox(), searchInput));
        Thread.sleep(2000);
        pageElementsHome9.getSearchBox().sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.presenceOfElementLocated(pageElementsHome9.searchResultsText));
    }

    // This method will Select PLP from Top menu item and index of plp dropdown - ok
    public void plpIndexSelection(int menuListIndex, int plpMenuIndex) throws InterruptedException {
        PageElements pageElementsHome10 = new PageElements(driver);
        Actions actionsHomePage10 = new Actions(driver);
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsHome10.getTopMenuItems().get(menuListIndex)));
        pageElementsHome10.getTopMenuItems().get(menuListIndex).click();
        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsHome10.getPLP_index().get(plpMenuIndex)));
        actionsHomePage10.moveToElement(pageElementsHome10.getPLP_index().get(plpMenuIndex)).click().build().perform();// Sub menu Roses
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsHome10.getShopNowCTA()));
    }

    // This method will check all the Banner CTAs in Home pag - ok
    public void verifyCTAs_from_banners() throws IOException {
        PageElements pageElementsHome11 = new PageElements(driver);
        String url;
        HttpURLConnection huc;
        int respCode;
        for (WebElement webElement : pageElementsHome11.getBannerCTAs()) {
            url = webElement.getAttribute("href");
            huc = (HttpURLConnection) (new URL(url).openConnection());
            huc.setRequestMethod("HEAD");
            huc.connect();
            respCode = huc.getResponseCode();
            System.out.println(respCode);
            if (respCode >= 400) {
                System.out.println(url + " is a broken link");
            } else {
                System.out.println(url + " is a valid link");
            }
            Assert.assertTrue(respCode < 400);
        }
    }

    // This method will Select a Product From Carousel in Home page by using Index of products - ok
    public void selectProductFromCarousel(int indexOfCarouselProduct){
        PageElements pageElementsHome12 = new PageElements(driver);
        Actions actionsHomePage12 = new Actions(driver);
        wait.until(ExpectedConditions.visibilityOfAllElements(pageElementsHome12.getAvailableProductList()));//wait
        if(pageElementsHome12.getAvailableProductList().size() > 0) {// check if there is Products From Carousel
            actionsHomePage12.moveToElement(pageElementsHome12.getAvailableProductList().get(indexOfCarouselProduct)).build().perform(); // move to the product
            wait.until(ExpectedConditions.elementToBeClickable(pageElementsHome12.getAvailableProductList().get(indexOfCarouselProduct)));
            selectedCarousel = pageElementsHome12.getAvailableProductList().get(indexOfCarouselProduct).getText(); // get the product Title to compare
            //pageElementsHome12.getAvailableProductList().get(indexOfCarouselProduct).click();// Click on a product
            pageElementsHome12.getAvailableProductList().get(indexOfCarouselProduct).sendKeys(Keys.ENTER);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));// little wait to load
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsHome12.getAddToBagCTA()));
    }

    // Verify the user can Select a Product from the Category - ок
    public void checkHomeCategoryLinks() throws InterruptedException {
        PageElements pageElementsHome13 = new PageElements(driver);
        Actions actionsHomePage13 = new Actions(driver);
        String goBackToHomePage = driver.getCurrentUrl();
        if (pageElementsHome13.getHomeCategoryLinks().size() > 0) {
            for (int i = 0; i < pageElementsHome13.getHomeCategoryLinks().size(); i++) { // loop
                wait.until(ExpectedConditions.visibilityOfAllElements(pageElementsHome13.getHomeCategoryLinks()));
                actionsHomePage13.moveToElement(pageElementsHome13.getHomeCategoryLinks().get(i)).build().perform();//move to each category link
                wait.until(ExpectedConditions.elementToBeClickable(pageElementsHome13.getHomeCategoryLinks().get(i)));
                try {
                    System.out.println(i + ": " + pageElementsHome13.getHomeCategoryLinks().get(i).getText() + " - ok");
                    pageElementsHome13.getHomeCategoryLinks().get(i).sendKeys(Keys.ENTER);//click to each category link
                } catch (Exception e) {
                    System.out.println(i + ": " + pageElementsHome13.getHomeCategoryLinks().get(i).getText() + " - jse - ok");
                    jseHomePage.executeScript("arguments[0].click();",pageElementsHome13.getHomeCategoryLinks().get(i));
                }
                wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(goBackToHomePage)));
                Thread.sleep(3000);
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

                Assert.assertNotEquals(goBackToHomePage, driver.getCurrentUrl());
                Assert.assertNotEquals(driver.getTitle(), " ");

                System.out.println("getCurrentUrl: " + driver.getCurrentUrl());
                System.out.println("getTitle:" + driver.getTitle());

                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
                driver.get(goBackToHomePage); // Navigate Back to Home
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            }
        }
    }

    // This method will Verify the Header Section of the site - ok
    public void verifyActiveHeaderSection(int indexMenuItem) throws InterruptedException{
        PageElements pageElementsHome14 = new PageElements(driver);
        WebElement activeHeader = driver.findElement(By.xpath("//div[contains(@class, 'menu_panel')]"));
        String goBackToHomePage = driver.getCurrentUrl();

        int count = activeHeader.findElements(By.tagName("a")).size();
        System.out.println(count); // all links in Active Header

        for (int i = 0; i < count; i++) {
            wait.until(ExpectedConditions.visibilityOfAllElements(pageElementsHome14.getTopMenuItems().get(indexMenuItem)));
            wait.until(ExpectedConditions.elementToBeClickable(pageElementsHome14.getTopMenuItems().get(indexMenuItem)));
            pageElementsHome14.getTopMenuItems().get(indexMenuItem).click();

            String clickOnLinkTab = Keys.chord(Keys.CONTROL, Keys.ENTER);
            activeHeader = driver.findElement(By.xpath("//div[contains(@class, 'menu_panel')]"));
            wait.until(ExpectedConditions.elementToBeClickable(activeHeader.findElements(By.tagName("a")).get(i)));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            activeHeader.findElements(By.tagName("a")).get(i).sendKeys(clickOnLinkTab);// Click on Header Menu Links
            wait.until(ExpectedConditions.elementToBeClickable(pageElementsHome14.getTopMenuItems().get(indexMenuItem)));//wait after click

            Thread.sleep(2000);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

            Assert.assertNotEquals(goBackToHomePage, driver.getCurrentUrl());
            Assert.assertNotEquals(driver.getTitle(), " ");

            System.out.println(i + " - getCurrentUrl: " + driver.getCurrentUrl());
            System.out.println(i + " - getTitle:" + driver.getTitle());

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        }
    }

    // This method will Verify the Footer Section of the site - ok
    public void verifyTheFooterSection() throws InterruptedException{
        PageElements pageElementsHome15 = new PageElements(driver);

        WebElement activeHeader = driver.findElement(pageElementsHome15.footerSection);
        String goBackToHomePage = driver.getCurrentUrl();

        int count = activeHeader.findElements(By.tagName("a")).size();
        System.out.println(count); // all links in Active Header

        count = 40;
        for (int i = 0; i < count; i++) {
            Thread.sleep(1000);
            wait.until(ExpectedConditions.visibilityOfAllElements(pageElementsHome15.getFooterSection()));
            String clickOnLinkTab = Keys.chord(Keys.CONTROL, Keys.ENTER);
            activeHeader = driver.findElement(pageElementsHome15.footerSection);
            wait.until(ExpectedConditions.elementToBeClickable(activeHeader.findElements(By.tagName("a")).get(i)));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            activeHeader.findElements(By.tagName("a")).get(i).sendKeys(clickOnLinkTab);// Click on Header Menu Links
            wait.until(ExpectedConditions.elementToBeClickable(pageElementsHome15.getFooterSection()));//wait after click

            Thread.sleep(2000);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

            Assert.assertNotEquals(driver.getTitle(), " ");

            System.out.println(i + " - getCurrentUrl: " + driver.getCurrentUrl());
            System.out.println(i + " - getTitle:" + driver.getTitle());

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            driver.get(goBackToHomePage); // Navigate Back to Home
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        }
    }

    // Verify no broken links - ok
    public void verifyNoBrokenLinks(String homePageURL){
        String url;
        HttpURLConnection huc;
        int respCode;
        List<WebElement> links = driver.findElements(By.tagName("a"));
        System.out.println(links.size());
        for (WebElement link : links) {
            url = link.getAttribute("href");
            if (url == null || url.isEmpty()) {
                System.out.println("URL is either not configured for anchor tag or it is empty");
                continue;
            }
            if (!url.startsWith(homePageURL)) {
                System.out.println("URL belongs to another domain, skipping it.");
                continue;
            }
            try {
                huc = (HttpURLConnection) (new URL(url).openConnection());
                huc.setRequestMethod("HEAD");
                huc.connect();
                respCode = huc.getResponseCode();
                if (respCode >= 400) {
                    System.out.println(url + " is a BROKEN link");
                } else {
                    System.out.println(url + " is a VALID link");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Verify Search by Popular links - ok
    public void searchPopularLinks(int searchLinks) throws InterruptedException {
        PageElements pageElementsHome9 = new PageElements(driver);
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsHome9.getSearchFromNav())); // wait to Click Search from Menu item
        pageElementsHome9.getSearchFromNav().click();
        Thread.sleep(3000);
        wait.until(ExpectedConditions.elementToBeClickable(pageElementsHome9.getSearchPopularLinks().get(searchLinks)));
        pageElementsHome9.getSearchPopularLinks().get(searchLinks).click();
    }
}