package pageObjects;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import resource.base;
import java.time.Duration;
import java.util.List;

public class ProductListingPage extends base {
    WebDriver driver;
    JavascriptExecutor jsePLP;
    WebDriverWait wait;
    public ProductListingPage(WebDriver driver) {
        this.driver = driver; wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }
    public void selectProductFromPLP(int productIndex) {
        PageElements pageElementsPLP1 = new PageElements(driver);
        Actions actions = new Actions(driver);
        jsePLP = (JavascriptExecutor) driver;
        if (pageElementsPLP1.getSwagResultTxt().isDisplayed() && pageElementsPLP1.getAvailableProductList().size() > 0) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(pageElementsPLP1.getAvailableProductList().get(productIndex)));
                actions.moveToElement(pageElementsPLP1.getAvailableProductList().get(productIndex)).build().perform();
                pageElementsPLP1.getAvailableProductList().get(productIndex).click();
            } catch (Exception e) {
                jsePLP.executeScript("arguments[0].click();", pageElementsPLP1.getAvailableProductList().get(productIndex));
            }
        } else {
            pageElementsPLP1.getAvailableProductList().get(0).click();
            System.out.println("No Products are available or only 1");
        }
    }

    public void checkAllAvailableProductsInPLP() throws InterruptedException {
        PageElements pageElementsHome13 = new PageElements(driver);
        Actions actionsHomePage13 = new Actions(driver);
        String goBackToHomePage = driver.getCurrentUrl();
        if (pageElementsHome13.getAvailableProductList().size() > 0) {
            for (int i = 0; i < pageElementsHome13.getAvailableProductList().size(); i++) { // loop
                wait.until(ExpectedConditions.visibilityOfAllElements(pageElementsHome13.getAvailableProductList()));
                actionsHomePage13.moveToElement(pageElementsHome13.getAvailableProductList().get(i)).build().perform();//move to each category link
                wait.until(ExpectedConditions.elementToBeClickable(pageElementsHome13.getAvailableProductList().get(i)));
                try {
                    System.out.println(i + ": " + pageElementsHome13.getAvailableProductList().get(i).getText() + " - ok");
                    pageElementsHome13.getAvailableProductList().get(i).sendKeys(Keys.ENTER);//click to each category link
                } catch (Exception e) {
                    System.out.println(i + ": " + pageElementsHome13.getAvailableProductList().get(i).getText() + " - jse - ok");
                    jsePLP.executeScript("arguments[0].click();",pageElementsHome13.getAvailableProductList().get(i));
                }
                wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(goBackToHomePage)));
                Thread.sleep(7000);
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

                Assert.assertNotEquals(goBackToHomePage, driver.getCurrentUrl());
                Assert.assertNotEquals(driver.getTitle(), " ");

                System.out.println("getCurrentUrl: " + driver.getCurrentUrl());
                System.out.println("getTitle:" + driver.getTitle());

                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
                driver.get(goBackToHomePage); // Navigate Back to Home
                Thread.sleep(7000);
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            }
        }
    }

    // This method will verify Pagination of PLP on the bottom of the Page - ok
    public void checkAllPagination() throws InterruptedException{
        PageElements pageElements3 = new PageElements(driver);
        Actions actions = new Actions(driver);
        jsePLP = (JavascriptExecutor) driver;
        if (pageElements3.getPagination().size() > 0) {
            for (int i = 0; i < pageElements3.getPagination().size(); i++) {
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
                actions.moveToElement(pageElements3.getPagination().get(i)).build().perform();
                wait.until(ExpectedConditions.elementToBeClickable(pageElements3.getPagination().get(i)));
                try {
                    pageElements3.getPagination().get(i).sendKeys(Keys.ENTER);
                } catch (Exception e){
                    jsePLP.executeScript("arguments[0].click();", pageElements3.getPagination().get(i));
                }
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
                System.out.println(i + 1 + " number of paginationByIndex");
            }
            Thread.sleep(7000);
        }
    }

    // This method will verify Product Prices are displayed in PLP - ok
    public void verifyProductPricesInPLP() throws InterruptedException {
        PageElements pageElements4 = new PageElements(driver);
        Actions actions4 = new Actions(driver);
        Thread.sleep(3000);
        wait.until(ExpectedConditions.visibilityOfAllElements(pageElements4.getAvailableProductList()));
        if (pageElements4.getProductPricesPLP().size() > 0) {
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(@class,'a5fCG')]"))); //ok
            for (WebElement listOfPrices : pageElements4.getProductPricesPLP()) {
                int clearPrices;
                if (listOfPrices.getText().contains("-")) {
                    String[] splitPrices = listOfPrices.getText().split("-");
                    actions4.moveToElement(listOfPrices).build().perform();
                    wait.until(ExpectedConditions.visibilityOf(listOfPrices));
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class,'a5fCG')]")));
                    clearPrices = Integer.parseInt(splitPrices[0].replaceAll("[^0-9]", ""));
                } else {
                    actions4.moveToElement(listOfPrices).build().perform();
                    wait.until(ExpectedConditions.visibilityOf(listOfPrices));
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class,'a5fCG')]")));
                    clearPrices = Integer.parseInt(listOfPrices.getText().replaceAll("[^0-9]", ""));
                }
                System.out.print(clearPrices + "\n");
                Assert.assertTrue(clearPrices > 0);
            }
        } else {
            System.out.println(pageElements4.getSwagResultTxt().getText() + " = No products are available");
        }
    }
    
    // THis method will verify FORCE SWAG in PLP
    public void verifyForceSWAGinPLP(int navMenuIndex, int plpIndex, String forceSwagZipcode) throws InterruptedException {
        PageElements pageElements5 = new PageElements(driver);
        HomePage homePage5 = new HomePage(driver);
        jsePLP = (JavascriptExecutor) driver;
        Actions actions5 = new Actions(driver);
        homePage5.plpIndexSelection(navMenuIndex, plpIndex);
        Thread.sleep(2000);
        jsePLP.executeScript("window.scrollBy(0,300)");
        if (pageElements5.getForceSwagModel().isDisplayed() ) {
            wait.until(ExpectedConditions.visibilityOf(pageElements5.getForceSwagZipcode())).sendKeys(forceSwagZipcode);
        }else {
            Thread.sleep(2000);
            wait.until(ExpectedConditions.visibilityOf(pageElements5.getForceSwagZipcode())).sendKeys(forceSwagZipcode);
        }
        wait.until(ExpectedConditions.textToBePresentInElementValue(pageElements5.getForceSwagZipcode(), forceSwagZipcode));
        actions5.sendKeys(Keys.TAB).build().perform(); // Click TAB
        List<WebElement> forceSwagDateField = driver.findElements(pageElements5.swagDateField);
        wait.until(ExpectedConditions.attributeToBeNotEmpty(forceSwagDateField.get(1), "value"));
        actions5.sendKeys(Keys.ENTER).build().perform(); // Click TAB
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOf(pageElements5.getSwagResultTxt()));
    }

    //This will close Force SWAG - ok
    public void closeForceSWAG() throws InterruptedException {
        PageElements pageElements6 = new PageElements(driver);
        jsePLP = (JavascriptExecutor) driver;
        wait.until(ExpectedConditions.visibilityOf(pageElements6.getZipCode()));
        jsePLP.executeScript("window.scrollBy(0,300)");
        wait.until(ExpectedConditions.presenceOfElementLocated(pageElements6.forceSwagModel));
        Actions actions6 = new Actions(driver);
        actions6.sendKeys(Keys.ESCAPE).build().perform();
        Thread.sleep(1000);
    }

    //5) Verify Filters using Price - ok
    public void verifyFilter(int index, int subIndex) throws InterruptedException{
        PageElements pageElementsPLP = new PageElements(driver);

        wait.until(ExpectedConditions.elementToBeClickable(pageElementsPLP.getFilter()));
        pageElementsPLP.getFilter().sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.elementToBeClickable(pageElementsPLP.getFilterMenuItem().get(index)));
        pageElementsPLP.getFilterMenuItem().get(index).sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.elementToBeClickable(pageElementsPLP.getFilterMenuSubItem().get(subIndex)));
        pageElementsPLP.getFilterMenuSubItem().get(subIndex).click();

        Thread.sleep(5000);
        wait.until(ExpectedConditions.visibilityOfAllElements(pageElementsPLP.getAvailableProductList()));
        if (pageElementsPLP.getAvailableProductList().size() > 0) {
            for (int i = 0; i < pageElementsPLP.getAvailableProductList().size(); i++) { // loop
                try {
                    System.out.println(i + ": " + pageElementsPLP.getAvailableProductList().get(i).getText());
                    System.out.println(pageElementsPLP.getProductPricesPLP().get(i).getText() + "\n");
                } catch (Exception e) {
                    System.out.println(i + ": " + pageElementsPLP.getAvailableProductList().get(i).getText() + " - jse");
                    System.out.println(pageElementsPLP.getProductPricesPLP().get(i).getText() + "\n");
                }
            }
        }
    }

    //5) Verify Sort - ok
    public void verifySort(int index) throws InterruptedException{
        PageElements pageElementsPLP = new PageElements(driver);

        wait.until(ExpectedConditions.elementToBeClickable(pageElementsPLP.getSort()));
        pageElementsPLP.getSort().sendKeys(Keys.ENTER);

        if (index == 1) {
            System.out.println("Sort by Price: High to Low\n");
            wait.until(ExpectedConditions.elementToBeClickable(pageElementsPLP.getSortMenuHiLo()));
            pageElementsPLP.getSortMenuHiLo().click();
        }
        if (index == 2) {
            System.out.println("Sort by Price: Low to High\n");
            wait.until(ExpectedConditions.elementToBeClickable(pageElementsPLP.getSortMenuLoHi()));
            pageElementsPLP.getSortMenuLoHi().click();
        }

        if (index == 3) {
            System.out.println("Sort by Featured\n");
            wait.until(ExpectedConditions.elementToBeClickable(pageElementsPLP.getSortMenuFeatured()));
            pageElementsPLP.getSortMenuFeatured().click();
        }

        Thread.sleep(3000);
        wait.until(ExpectedConditions.visibilityOfAllElements(pageElementsPLP.getAvailableProductList()));
        if (pageElementsPLP.getAvailableProductList().size() > 0) {
            for (int i = 0; i < pageElementsPLP.getAvailableProductList().size(); i++) { // loop
                try {
                    System.out.println(i + ": " + pageElementsPLP.getAvailableProductList().get(i).getText());
                    System.out.println(pageElementsPLP.getProductPricesPLP().get(i).getText() + "\n");
                } catch (Exception e) {
                    System.out.println(i + ": " + pageElementsPLP.getAvailableProductList().get(i).getText() + " - jse");
                    System.out.println(pageElementsPLP.getProductPricesPLP().get(i).getText() + "\n");
                }
            }
        }
    }

    //Filter Reset - ok
    public void verifyFilterReset () throws InterruptedException{
        PageElements pageElementsPLP = new PageElements(driver);
        pageElementsPLP.getFilterReset().click();
        System.out.println("\nFilter Reset");

        Thread.sleep(5000);
        wait.until(ExpectedConditions.visibilityOfAllElements(pageElementsPLP.getAvailableProductList()));
        if (pageElementsPLP.getAvailableProductList().size() > 0) {
            for (int i = 0; i < pageElementsPLP.getAvailableProductList().size(); i++) { // loop
                try {
                    System.out.println(i + ": " + pageElementsPLP.getAvailableProductList().get(i).getText());
                    System.out.println(pageElementsPLP.getProductPricesPLP().get(i).getText() + "\n");
                } catch (Exception e) {
                    System.out.println(i + ": " + pageElementsPLP.getAvailableProductList().get(i).getText() + " - jse");
                    System.out.println(pageElementsPLP.getProductPricesPLP().get(i).getText() + "\n");
                }
            }
        }
    }

    //This will SWAG in Force SWAG - ok
    public void verifyForceSWAG(String swagZipcode) throws InterruptedException {
        PageElements pageElements6 = new PageElements(driver);
        Actions actions = new Actions(driver);
        jsePLP = (JavascriptExecutor) driver;
        wait.until(ExpectedConditions.visibilityOf(pageElements6.getZipCode()));
        jsePLP.executeScript("window.scrollBy(0,300)");
        wait.until(ExpectedConditions.presenceOfElementLocated(pageElements6.forceSwagModel));
        wait.until(ExpectedConditions.visibilityOf(pageElements6.getForceSwagZipcode()));
        pageElements6.getForceSwagZipcode().sendKeys(swagZipcode);// Enter Zip code
        wait.until(ExpectedConditions.textToBePresentInElementValue(pageElements6.getForceSwagZipcode(), swagZipcode));
        actions.sendKeys(Keys.TAB).build().perform(); // Click TAB
        Thread.sleep(5000);
        wait.until(ExpectedConditions.elementToBeClickable(pageElements6.getForceSwagShopNow()));
        pageElements6.getForceSwagShopNow().sendKeys(Keys.ENTER);
    }
}