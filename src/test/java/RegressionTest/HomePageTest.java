package RegressionTest;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.Listeners;
import pageObjects.*;
import resource.base;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

@Listeners(RegressionTest.Listeners.class)
public class HomePageTest extends base {
    ProductDPage pDpPage;
    ProductListingPage plpPage;
    MiniCart miniCart;
    MyAccountPages myAccountPages;
    PageElements pageElements;
    HomePage homePage;
    Actions actions;
    JavascriptExecutor jseProdTest;
    Calendar calendarProd = Calendar.getInstance();
    Date today2 = calendarProd.getTime();
    int matchingString;

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
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        jseProdTest = (JavascriptExecutor) driver;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.get(currentURL);
        Thread.sleep(2000);
    }

    //*********************************************************** HOME PAGE ******************
    // Verifying SWAG With Valid ZIPCODE - assert ок
    @Test(priority = 1, retryAnalyzer = Retry.class)
    public void verifySwagWithValidInputInHomePage() throws InterruptedException {
        homePage.submitSwag(properties.getProperty("swagZipcode"), 2);
        System.out.println("Test case = " + pageElements.getSwagResultTxt().getText());
        Assert.assertTrue(pageElements.getSwagResultTxt().getText().contains("Results for"));
        Assert.assertTrue(pageElements.getSwagResultTxt().getText().contains(properties.getProperty("swagZipcode")));
    }

    // Verify user should be abel to submit SWAG with CANADA zipcode on Home page - assert ok
    @Test(priority = 2, retryAnalyzer = Retry.class)
    public void verifySubmittingSwagWithCanadianZipcode() throws InterruptedException{
        homePage.submitSwag(properties.getProperty("canadianZipcode"), 1);
        System.out.println("Test case = " + pageElements.getSwagResultTxt().getText());
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        Assert.assertTrue(pageElements.getSwagResultTxt().getText().contains("Results for"));
        Assert.assertTrue(pageElements.getSwagResultTxt().getText().contains(properties.getProperty("canadianZipcode")));
    }

    // Verify User should be able to Update Date in Calendar after SWAG - assert ок
    @Test(priority = 3, retryAnalyzer = Retry.class)
    public void verifyUserCanUpdateDateInCalendarAfterSwag() throws InterruptedException {
        homePage.updateDateInCalendarAfterSWAG(properties.getProperty("swagZipcode"), 1);// This date index will Update date after SWAG already submitted
        Thread.sleep(4000);
        System.out.println("Test case = Updating date in Home page after SWAG - " + homePage.swagResultsOnDateUpdateText+
                " Updated date = "+ pageElements.getSwagResultTxt().getText());
        Assert.assertNotEquals(homePage.swagResultsOnDateUpdateText,
                pageElements.getSwagResultTxt().getText()); // Comparing the Updated date with 1st one
    }
    
    // Verify User Entering valid zip code and clicking into date field - auto selects the date from HOME PAGE - assert ок
    @Test(priority = 4, retryAnalyzer = Retry.class)
    public void verifyAutoSelectedDateFromHomePageInSWAG(){
        homePage.submitSwagWithoutEnteringDate(properties.getProperty("swagZipcode"));
        wait.until(ExpectedConditions.visibilityOf(pageElements.getSwagResultTxt()));
        System.out.println("Test case = Checking Date Autos-selection in SWAG calendar - " + pageElements.getSwagResultTxt().getText());
        Assert.assertTrue(pageElements.getSwagResultTxt().getText().contains("Results for"));
        Assert.assertTrue(pageElements.getSwagResultTxt().getText().contains(properties.getProperty("swagZipcode")));
    }

    // Verify user clicking into Calendar icon displays Calendar with available dates in HOME PAGE - assert ок
    @Test(priority = 5, retryAnalyzer = Retry.class)
    public void clickingCalendarDisplaysAvailableDateFromHomePage(){
        homePage.availableDatesOnCalendarIconClick(properties.getProperty("swagZipcode"));
        wait.until(ExpectedConditions.visibilityOfAllElements(pageElements.getAvailableDatesFromSWAG()));
        System.out.println("Test case = This is how many available dates are in calendar - " +
                pageElements.getAvailableDatesFromSWAG().size());
        Assert.assertTrue(pageElements.getAvailableDatesFromSWAG().size() > 0);
    }

    // Verify Open Calendar should default current Month and current Date in HOME PAGE - assert ok
    @Test(priority = 6, retryAnalyzer = Retry.class)
    public void calendarWithCurrentMonthAndDateFromHomePage(){
        calendarProd.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendarProd.getTime();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String todayAsString = dateFormat.format(today2); // getting today's date from world calendar
        String tomorrowAsString = dateFormat.format(tomorrow); // getting tomorrow's date from worlds calendar
        homePage.verifyCalendarIsWithCurrentMonthAndDate(properties.getProperty("swagZipcode")); // submit SWAG without clicking shop now
        System.out.println("Test case = Getting current date Today or Tomorrow - "+todayAsString+" "
                +tomorrowAsString+" next available date = "+ homePage.currentMonthAndDateNameInCalendar);
         if (homePage.currentMonthAndDateNameInCalendar.equals(todayAsString)){
             Assert.assertEquals(homePage.currentMonthAndDateNameInCalendar, todayAsString);// check if today is the default date
         }else {
             Assert.assertEquals(homePage.currentMonthAndDateNameInCalendar, tomorrowAsString);// check if tomorrow is the default date
         }
    }

    // Verify User should be able to Enter Date manually in Date field in HOME PAGE - assert ok
    @Test(priority = 7, retryAnalyzer = Retry.class)
    public void userIsAbleToEnterValidDateManuallyInSWAG(){
        calendarProd.add(Calendar.DAY_OF_YEAR, 2);
        Date date2 = calendarProd.getTime();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String futureDate = dateFormat.format(date2); // getting future Date to enter 2 days ahead
        homePage.verifyUserIsAbleToEnterDateManuallyInSWAG(properties.getProperty("swagZipcode"), futureDate);// submit swag by manually entering the date
        wait.until(ExpectedConditions.textToBePresentInElementValue(pageElements.getDateFieldInSWAG(), futureDate));
        System.out.println("Test case = Entering Valid Date Manually - " +futureDate+" - "+ pageElements.getDateFieldInSWAG().getAttribute("value"));
        Assert.assertEquals(futureDate, pageElements.getDateFieldInSWAG().getAttribute("value"));
    }

    // Verify Updating date manually with invalid input in Date field after SWAG in HOME PAGE - assert ок
    @Test(priority = 8, retryAnalyzer = Retry.class)
    public void userEnteringInvalidDateManuallyInSWAG(){
        String invalidDate = "00/00/0000";
        homePage.verifyUserIsAbleToEnterDateManuallyInSWAG(properties.getProperty("swagZipcode"), invalidDate);
        wait.until(ExpectedConditions.visibilityOf(pageElements.getInvalidInputErrorInSWAG()));
        System.out.println("Test case = Entering Invalid Date Manually - " +
                pageElements.getInvalidInputErrorInSWAG().getText());
        Assert.assertTrue(pageElements.getInvalidInputErrorInSWAG().getText().contains("Enter a valid delivery date"));
    }

    //Verify user is able to submit SWAG by using TAB and ENTER in HOME PAGE - assert ok
    @Test(priority = 9, retryAnalyzer = Retry.class)
    public void submittingSwagWithTABAndENTERinHomePage() throws InterruptedException {
        homePage.submitSwagByUsingTABandENTER(properties.getProperty("swagZipcode"));
        System.out.println("Test case = Submitting SWAG with TAB/ENTER - " + pageElements.getSwagResultTxt().getText());
        Assert.assertTrue(pageElements.getSwagResultTxt().getText().contains("Results for"));
        Assert.assertTrue(pageElements.getSwagResultTxt().getText().contains(properties.getProperty("swagZipcode")));
    }

    // Verify User entering INVALID ZIPCODE should display error message in zip code field - assert ok
    @Test(priority = 10, retryAnalyzer = Retry.class)
    public void verifyInvalidZipcodeErrorMessage(){
        homePage.verifyInvalidZipcodeErrorMessage("*****"); // verifying error message when SWAG submitted with different characters
        System.out.println("Test case = Invalid zipcode Error - " + pageElements.getInvalidInputErrorInSWAG().getText());
        Assert.assertTrue(pageElements.getInvalidInputErrorInSWAG().getText().contains("Enter a valid"));
    }

    // Verify Product Prices are Displayed BEFORE SWAG from HOME PAGE - assert ok
    @Test(priority = 11, retryAnalyzer = Retry.class)
    public void verifyProductPricesAreDisplayedFromHomePage() throws InterruptedException {
        Thread.sleep(3000);
        if (pageElements.getProductPricesPLP().size() > 0) {
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(@class,'a5fCG')]")));
            for (WebElement listOfPrices : pageElements.getProductPricesPLP()) {
                int clearPrices;
                if (listOfPrices.getText().contains("-")) {
                    String[] splitPrices = listOfPrices.getText().split("-");
                    clearPrices = Integer.parseInt(splitPrices[0].replaceAll("[^0-9]", ""));
                } else {
                    clearPrices = Integer.parseInt(listOfPrices.getText().replaceAll("[^0-9]", ""));
                }
                System.out.println(clearPrices);
                Assert.assertTrue(clearPrices > 0);
            }
        } else {
            System.out.println(pageElements.getSwagResultTxt().getText() + " = No products are available");
        }
    }

    // Verify Product Prices are Displayed AFTER SWAG from HOME PAGE - assert ok
    @Test(priority = 12, retryAnalyzer = Retry.class)
    public void verifyProductPPricesAreDisplayedAfterSWAG() throws InterruptedException {
        homePage.submitSwag(properties.getProperty("swagZipcode"), 1);
        wait.until(ExpectedConditions.urlContains("zipcode="));
        plpPage.verifyProductPricesInPLP(); // method will verify product Prices
        System.out.println("\nTest case = Product prices are displayed in PLP after SWAG");// Assertion is in the method
    }

    // Verify Search Results with Valid Input in HOME PAGE - assert ok
    @Test(priority = 13, retryAnalyzer = Retry.class)
    public void verifySearchWithValidInput() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(pageElements.getMainLogo()));
        actions.moveToElement(pageElements.getMainLogo()).build().perform();
        pageElements.getMainLogo().click();
        homePage.searchForInput(properties.getProperty("validSearchInput"));
        Thread.sleep(2000);
        wait.until(ExpectedConditions.presenceOfElementLocated(pageElements.swagResultsText));
        System.out.println("Test case = valid input in search - "+ pageElements.getSearchResults().getText());
        Assert.assertTrue(pageElements.getSearchResults().getText().contains(properties.getProperty("validSearchInput")));
        Assert.assertTrue(pageElements.getSwagResultTxt().getText().contains("Results"));
    }

    // Verify SEARCH Function Displays Correct Products - assert ok
    @Test(priority = 14, retryAnalyzer = Retry.class)
    public void verifySearchFunctionDisplaysCorrectProducts() throws InterruptedException {
        homePage.searchForInput(properties.getProperty("validSearchInput"));
        wait.until(ExpectedConditions.presenceOfElementLocated(pageElements.swagResultsText));
        if (pageElements.getAvailableProductList().size() > 0){
            Thread.sleep(2000);
            wait.until(ExpectedConditions.visibilityOfAllElements(pageElements.getAvailableProductList()));
            for (int i = 0; i < pageElements.getAvailableProductList().size(); i++){
                actions.moveToElement(pageElements.getAvailableProductList().get(i)).build().perform();
                String searchInput = properties.getProperty("validSearchInput");
                System.out.println(pageElements.getAvailableProductList().get(i).getText());
                if (pageElements.getAvailableProductList().get(i).getText().contains(StringUtils.capitalize(searchInput))){
                    matchingString++;
                }
            }
            System.out.println("Test case = Find matching words in Search results = " + matchingString);
            Assert.assertTrue(matchingString>0);
        }
    }

    // Verify Search Results with invalid input From HOME PAGE - assert ok
    @Test(priority = 15, retryAnalyzer = Retry.class)
    public void verifySearchWithInvalidInput() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(pageElements.getMainLogo()));
        actions.moveToElement(pageElements.getMainLogo()).build().perform();
        pageElements.getMainLogo().click();
        homePage.searchForInput(properties.getProperty("invalidSearchInput"));
        Thread.sleep(1000);
        wait.until(ExpectedConditions.presenceOfElementLocated(pageElements.searchResultsText));
        System.out.println("Test case = Invalid input in search - "+ pageElements.getSearchResults().getText());
        Assert.assertTrue(pageElements.getSearchResults().getText().contains(properties.getProperty("invalidSearchInput")));
        Assert.assertTrue(pageElements.getSearchResults().getText().contains("No results found"));
    }

    // Verify Clicking on CTA from the banner navigates the user to the right page - assert ok
    @Test(priority = 16, retryAnalyzer = Retry.class)
    public void CTAs_from_banners() throws IOException {
        homePage.verifyCTAs_from_banners();
        System.out.println("Test case = With all Banner CTAs in Home page" );
    }

    // Verify the user can Select a Product from the Carousel in HOME PAGE - assert ok
    @Test(priority = 17, retryAnalyzer = Retry.class)
    public void verifyUserCanSelectProductFromCarousel(){
        homePage.selectProductFromCarousel(0); // Selecting Product From Carousel by Index
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        Assert.assertEquals(homePage.selectedCarousel, pageElements.getProductTitlePDP().getText());// compare the title
        System.out.println("Test case current ZipCode = "+ pageElements.getProductTitlePDP().getText());
    }

    //Verify the user can Select a Product from the Category - assert ok
    @Test(priority = 18, retryAnalyzer = Retry.class)
    public void VerifyHomePageCategory() throws InterruptedException {
        homePage.checkHomeCategoryLinks(); // Checking all the Category links in Home page
        System.out.println("Test case" ); // We are using the Assertion with in the method
    }

    //Verify top Navigation menu items - assert ok
    @Test(priority = 19)
    public void verifyTheHeaderSection() throws InterruptedException{
        homePage.verifyActiveHeaderSection(0);//1st one Assertion included
        System.out.println("Test case" );//Working
    }

    //Verify footer Navigation menu items - assert ok
    @Test(priority = 20, retryAnalyzer = Retry.class)
    public void verifyTheFooterSection() throws InterruptedException{
        homePage.verifyTheFooterSection();
        System.out.println("Test case " );
    }

    //Verify Broken links - assert ok
    @Test(priority = 21, retryAnalyzer = Retry.class)
    public void verifyBrokenLinks(){
        homePage.verifyNoBrokenLinks(currentURL);
    }

    //Verify User is able to Update Zip code after SWAG submited - assert ok
    @Test(priority = 22, retryAnalyzer = Retry.class)
    public void verifyUserCanUpdateZipCodeInCalendarAfterSwag() throws InterruptedException {
        homePage.submitSwagWithoutEnteringDate(properties.getProperty("swagZipcode"));
        homePage.submitSwagWithoutEnteringDate(properties.getProperty("updatedZipcode"));
        Thread.sleep(4000);
        System.out.println("Test case = Updating date in Home page after SWAG - " + homePage.swagResultsOnDateUpdateText+
                "\n Updated date = "+ pageElements.getSwagResultTxt().getText());
        Assert.assertNotEquals(homePage.swagResultsOnDateUpdateText,
                pageElements.getSwagResultTxt().getText()); // Comparing the Updated date with 1st one
    }

    //Verify submitting SWAG with Empty Zipcode field will trigger error message - assert ok
    @Test(priority = 23, retryAnalyzer = Retry.class)
    public void verifyEmptyZipcodeErrorMessage(){
        homePage.verifyInvalidZipcodeErrorMessage(" "); // verifying error message when SWAG submitted with different characters
        System.out.println("Test case = Invalid zipcode Error - " + pageElements.getInvalidInputErrorInSWAG().getText());
        Assert.assertTrue(pageElements.getInvalidInputErrorInSWAG().getText().contains("Enter a valid"));
    }

    //Verify the user is able to select a product the HomePage after SWAG - assert ok
    @Test(priority = 24, retryAnalyzer = Retry.class)
    public void verifySelectProductFromCarouselAfterSWAG() throws InterruptedException{
        homePage.submitSwagWithoutEnteringDate(properties.getProperty("swagZipcode"));
        Thread.sleep(4000);
        homePage.selectProductFromCarousel(0); // Selecting Product From Carousel by Index
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        Assert.assertEquals(homePage.selectedCarousel, pageElements.getProductTitlePDP().getText());// compare the title
        System.out.println("Test case current ZipCode = "+ pageElements.getProductTitlePDP().getText());
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
