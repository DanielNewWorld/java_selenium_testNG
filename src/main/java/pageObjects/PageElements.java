package pageObjects;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class PageElements {
    WebDriver driver;
    public PageElements(WebDriver driver){ this.driver = driver; }

    // ******************* HOME Page ********************************
    By companyLogo = By.xpath("//div[contains(@class,'header_logo')]");
    By availableProductList = By.xpath("//span[contains(@aria-label,'Product Name') and contains(@class,'product-tile')]/a"); //ok
    By topMenuItems = By.xpath("//*[contains(@role, 'menuitem')]");  //ok
    By topMenuSubLinks = By.xpath("//*[contains(@class, 'menu_panelList')]/li/a"); // ("//*[contains(@class, 'menu_panelList')]/li");
    By shopNowButton = By.xpath("//div[contains(@class, 'site-wide-available-gifts_buttonWrapper') or contains(@class, 'site-wide-available-gifts-test')]/button"); //ok
    By productPrices = By.xpath("//div[contains(@class,'a5fCG')]"); //ok
    By zipCode = By.xpath("//input[contains(@placeholder,'Delivery Address') or contains(@placeholder,'Zipcode') or contains(@id,'zipCode') or contains(@id,'address')]"); //ok
    By swagCalendarIcon = By.xpath("//button[@aria-label='Choose Date' and @aria-disabled='false']"); //ok
    By swagDateField = By.xpath("//input[contains(@class,'date-picker_input')]"); //ok
    public By swagResultsText = By.xpath("//div[contains(@class,'center white-space-nowrap')]"); //ok
    By bannerCTA = By.xpath("//div[contains(@id,'clickable-comp-banner')] //a[contains(@class,'link_wrapper')]");
    By invalidDateErrorInSWAG = By.xpath("//p[@role='alert' and contains(@class,'font-weight-medium text-body')]");
    By searchFromNav = By.xpath("//span[contains(@aria-label, 'Search') and @role='button']"); //ok
    By categoryLinksFromHomePage = By.xpath("//div[contains(@class,'item-stacked_descriptionBorder')]/a"); //ok
    public By footerSection = By.xpath("//section[contains(@class,'component footer')]"); //ok

    // ********************************* PLP Page ***************************
    By forceSwagModel = By.xpath("//div[not(contains(@class, 'DeliveryDate')) and contains(@class, 'SwagModal')]");
    By forceSwagZipcode = By.xpath("//input[@id='zipCode-swag-modal']");
    By forceSwagShopNow = By.xpath("//form[contains(@class, 'inFormModal')]/div[contains(@class, 'site-wide-available-gifts_button')]/button");
    By mixedRoses = By.xpath("//span[contains(@aria-label, 'Mixed Roses') and contains(@class, 'product')]");
    By searchBox = By.xpath("//*[contains(@class, 'searchInput')]"); //ok
    By searchIcon = By.xpath("//div[contains(@class, 'searchIcon')]"); //ok
    public By searchResultsText = By.xpath("//*[contains(@class, 'search-results')]"); //ok
    By pagination = By.xpath("//li[contains(@class, 'products_paginationLinkItem')]"); //ok
    By filter = By.xpath("//button[contains(@class,'filters_icon')]"); //ok
    By filterMenuItem = By.xpath("//div[contains(@class,'rc-collapse-header')]"); //ok
    By filterMenuSubItem = By.xpath("//label[contains(@class,'checkbox_text')]"); //ok
    By filterReset = By.xpath("//button[contains(@type,'reset')]"); //ok
    By sort = By.xpath("//select[contains(@id,'sort')]"); //ok
    By sortMenuFeatured = By.xpath("//select[contains(@id,'sort')]/option[2]"); //ok
    By sortMenuHiLo = By.xpath("//select[contains(@id,'sort')]/option[3]"); //ok
    By sortMenuLoHi = By.xpath("//select[contains(@id,'sort')]/option[4]"); //ok

    // ********************************* PDP Page ***************************
    By nextMonthCalendarBtnPDP = By.xpath("//button[contains(@class,'text-align-center') and @aria-label='Next Month']"); //ok
    By editFromPDP = By.xpath("//span[text()='Edit']");
    //By availableDatesFromSWAG = By.xpath("//div[not(contains(@class,'date-picker-styles_disabled')) and contains(@class,'date-picker-styles') and @aria-selected='false']");
    By availableDatesFromSWAG = By.xpath("//div[not(contains(@arialabel,'is not an available delivery date for this zip code')) and contains(@class,'date-picker-styles') and contains(@id,'not-selected-')]"); //ok
    By availableDatesInPDP = By.xpath("//div[not(contains(@class,'disabled')) and contains(@class,'day-picker-styles') and contains(@class,'undefined')]");
    By whiteRoses = By.xpath("//span[contains(@aria-label, 'White Rose') and contains(@class, 'product')]");
    By productTitlePDP = By.xpath("//*[@id='product-title']"); //ok
    By addToBagCTA = By.xpath("//button[contains(@class,'font-cta py') and contains(@class,'button_btn-pdp')]"); // Add to bag // ok
    By itemAddedMessage = By.xpath("//span[text()='Item added successfully' or contains(text(), 'Item updated Successfully')]");
    By editMainItem = By.xpath("//button[@aria-label='Edit product']"); // "//button[@aria-label='Edit product']"
    By productOptionsPDP = By.xpath("//*[contains(@class, 'product-option-button_wrapper')]");
    By updateItemCTA = By.xpath("//button[contains(@class, 'button_btn-primary-light')]"); //Checkout
    By addonSelection = By.xpath("//input[contains(@class, 'checkbox_input')]");
    By editAddOn = By.xpath("//button[@aria-label='Edit Addon']");
    By removeAddon = By.xpath("//button[@aria-label='Remove addon']");
    By removeProduct = By.xpath("//button[@aria-label='Remove product']");
    By alertPopupMessage = By.xpath("//span[contains(@class,'alert_message')]");
    By addAddon = By.xpath("//div[@id='extra-special-button']/button");
    By addonNameText = By.xpath("//div[@id='addon-details']/div[1]");
    By updateDateAndContinue = By.xpath("//button[contains(@class,'button_btn-primary') and @type='submit']");
    By zipCodeErrorPDP = By.xpath("//p[@id='zipCode-deliver-to-error']");
    By recommendationModel = By.xpath("//div[contains(@class,'recommendations-modal')]");
    By recommendationModelProducts = By.xpath("//button[contains(@class,'recommendations-carousel') and contains(@aria-label,'products')]");
    By viewDetailsRecommendationCTA = By.xpath("//button[contains(@class,'recommendation-card') and contains(@class,'font-cta py-0 button')]");

    // ******************************* MINI CART *******************************
    By closeMiniCart = By.xpath("//div[contains(@class,'side-panel_wrapper') or @id='addon-panel-wrapper']/div/button");
    By cartIcon = By.xpath("//li[@class='p-0 mx-2 px-1 flex-center']/button");
    By emptyCartMessage = By.xpath("//*[@class='mb-4 text-big text-align-center']");
    By pdpDeliveryDetails = By.xpath("//*[@id='deliver-to-container' and contains(@class, 'text-body')]/div/div");
    By updateDeliveryDetailsMiniCart = By.xpath("//button[contains(@aria-label, 'update delivery details')]");
    //By defaultDate = By.xpath("//div[contains(@class,'day-picker') and @aria-selected='true' or contains(@class,'date-picker') and @aria-selected='true']");
    By defaultDate = By.xpath("//div[contains(@class,'date-picker') and contains(@id,'selected-day')]"); // ok
    By checkoutCTA = By.xpath("//button[contains(@class,'font-cta py') and contains(@class,'primary')]");
    By cartItemCount = By.xpath("//span[contains(@class,'cart-item-count')]");
    By upDateItemFromMiniCartCTA = By.xpath("//div[@id='pdp-cta-container']/button");
    By miniCartUpdateSuccessMsg = By.xpath("//div[contains(@class,'alert_success') or contains(@class,'addToCartHeader')]");

    //************************************** MANAGE ACCOUNT PAGES **********************************
    By ftdPlusAndSignInFromHeader = By.xpath("//span[contains(@aria-label, 'Sign in') and @role='button']"); //ok
    By emailSigIn = By.xpath("//input[@id = 'username']"); //ok
    By passwordSignIn = By.xpath("//input[@id = 'password']"); //ok
    By recaptchaIframe = By.xpath("//iframe[@title='reCAPTCHA']");
    By recaptchaUnCheckedBox = By.xpath("//span[contains(@class, 'recaptcha-checkbox') and @role='checkbox' and @aria-checked='false']"); //ok
    By recaptchaCheckedBox = By.xpath("//span[contains(@class, 'recaptcha-checkbox') and @role='checkbox' and @aria-checked='true']"); //ok
    By signInIntoAccountCTA = By.xpath("//button[contains(@class,'button_btn-primary') and @type='submit']"); //ok

    public List<WebElement> getViewDetailsRecommendation(){ return driver.findElements(viewDetailsRecommendationCTA); }
    public List<WebElement> getRecommendationProducts(){ return driver.findElements(recommendationModelProducts); }
    public WebElement getRecommendationModel(){ return driver.findElement(recommendationModel); }
    public WebElement getZipcodeErrorPDP(){ return driver.findElement(zipCodeErrorPDP); }
    public WebElement getRecaptchaCheckedBox(){ return driver.findElement(recaptchaCheckedBox);} //to verify its checked
    public WebElement getRecaptchaUnCheckedBox(){ return driver.findElement(recaptchaUnCheckedBox); }
    public WebElement getRecaptchaIframe(){ return driver.findElement(recaptchaIframe); }
    public WebElement getMixedRoses(){ return driver.findElements(mixedRoses).get(0); }
    public WebElement getWhiteRoses(){ return driver.findElement(whiteRoses); }
    public WebElement getSearchFromNav(){ return driver.findElement(searchFromNav); }
    public WebElement getMainLogo(){ return driver.findElement(companyLogo); }
    public WebElement getForceSwagModel(){ return driver.findElement(forceSwagModel); }
    public WebElement getForceSwagZipcode(){ return driver.findElement(forceSwagZipcode); }
    public WebElement getForceSwagShopNow(){ return driver.findElement(forceSwagShopNow); }
    public WebElement getFooterSection(){ return driver.findElement(footerSection); }
    public List<WebElement> getHomeCategoryLinks(){ return driver.findElements(categoryLinksFromHomePage); }
    public WebElement getEmailSignIn(){ return driver.findElement(emailSigIn); }
    public WebElement getPasswordSignIn(){ return driver.findElement(passwordSignIn);}
    public List<WebElement> getFtdPlusAndSignIn(){ return driver.findElements(ftdPlusAndSignInFromHeader); }
    public WebElement getInvalidInputErrorInSWAG(){ return driver.findElement(invalidDateErrorInSWAG); }
    public WebElement getUpdateSuccessMsgOnMiniCart(){ return driver.findElement(miniCartUpdateSuccessMsg); }
    public WebElement getUpdateItemFromMiniCartCTA(){return driver.findElement(upDateItemFromMiniCartCTA); }
    public List<WebElement> getBannerCTAs(){ return driver.findElements(bannerCTA); }
    public WebElement getCartItemCount(){ return driver.findElement(cartItemCount); }
    public WebElement getDateFieldInSWAG(){ return driver.findElement(swagDateField); }
    public WebElement getCheckoutCTA(){ return driver.findElement(checkoutCTA); }
    public WebElement getDefaultDate(){ return driver.findElement(defaultDate); }
    public WebElement getEditDeliveryInfoInMiniCart(){ return driver.findElement(updateDeliveryDetailsMiniCart); } //miniCartEditDateZip
    public List<WebElement> getPagination(){ return driver.findElements(pagination); }
    public List<WebElement> getAvailableDatesFromPDP(){return driver.findElements(availableDatesInPDP); }
    public WebElement getNextMonthBtn(){ return driver.findElement(nextMonthCalendarBtnPDP); }
    public WebElement getContinueCTAinPDP(){ return driver.findElement(updateDateAndContinue);}
    public WebElement getSearchResults(){ return driver.findElement(searchResultsText); }
    public WebElement getSearchIcon(){ return driver.findElement(searchIcon); }
    public WebElement getSearchBox(){ return driver.findElement(searchBox); }
    public WebElement getPDPdeliveryDetails(){return driver.findElement(pdpDeliveryDetails); }
    public WebElement getEditFromPDP(){return driver.findElement(editFromPDP); }
    public List<WebElement> getAvailableProductList(){ return driver.findElements(availableProductList); }
    public List<WebElement> getTopMenuItems(){ return driver.findElements(topMenuItems); }
    public List<WebElement> getPLP_index(){ return driver.findElements(topMenuSubLinks); } // (dropdown) SubLinks From Top Menu items starts from [1]
    public WebElement getZipCode(){ return driver.findElement(zipCode); }
    public List<WebElement> getAvailableDatesFromSWAG(){ return driver.findElements(availableDatesFromSWAG); }
    public WebElement getShopNowCTA(){ return driver.findElement(shopNowButton); }
    public List<WebElement> getProductPricesPLP(){ return driver.findElements(productPrices); }
    public WebElement getSwagCalendarIcon(){ return driver.findElement(swagCalendarIcon);}
    public WebElement getSwagResultTxt(){ return driver.findElement(swagResultsText); }
    public WebElement getAddToBagCTA(){return driver.findElement(addToBagCTA); }
    public WebElement getItemAddedMessage(){return driver.findElement(itemAddedMessage); }
    public WebElement getCloseMiniCart_x(){return driver.findElement(closeMiniCart); }
    public WebElement getCartIcon(){return driver.findElement(cartIcon); }
    public List<WebElement> getProductOptionsPDP(){return driver.findElements(productOptionsPDP); }
    public List<WebElement> getAllCheckoutCTAs(){return driver.findElements(updateItemCTA); }
    public WebElement getUpdateItemCTA(){return getAllCheckoutCTAs().get(1); } // checkout after addon added
    public WebElement getProductTitlePDP(){return driver.findElement(productTitlePDP); }
    public WebElement getAddAddonFromMiniCartCTA(){return driver.findElement(addAddon); }
    public List<WebElement> getAddonSelections(){return driver.findElements(addonSelection); }
    public WebElement getAlertPopupMessage(){return driver.findElement(alertPopupMessage); }
    public WebElement getEditMainItem(){ return driver.findElement(editMainItem); }
    public WebElement getEditAddOn(){return driver.findElement(editAddOn); }
    public WebElement getAddonNameText(){return driver.findElement(addonNameText); }
    public WebElement getRemoveAddon(){return driver.findElement(removeAddon); }
    public WebElement getRemoveProduct(){return driver.findElement(removeProduct); }
    public WebElement getEmptyCartMessage(){return driver.findElement(emptyCartMessage); }
    public WebElement getSignInCTA(){ return driver.findElement(signInIntoAccountCTA); }
    public WebElement getFilter() {return driver.findElement(filter);}
    public List<WebElement> getFilterMenuItem() {return driver.findElements(filterMenuItem);}
    public List<WebElement> getFilterMenuSubItem() {return driver.findElements(filterMenuSubItem);}
    public WebElement getFilterReset() {return driver.findElement(filterReset);}
    public WebElement getSort() {return driver.findElement(sort);}
    public WebElement getSortMenuFeatured() {return driver.findElement(sortMenuFeatured);}
    public WebElement getSortMenuHiLo() {return driver.findElement(sortMenuHiLo);}
    public WebElement getSortMenuLoHi() {return driver.findElement(sortMenuLoHi);}
}