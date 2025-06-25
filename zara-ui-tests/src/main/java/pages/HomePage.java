package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.Keys;

public class HomePage {
    WebDriver driver;

    @FindBy(css = "input.search-products-input")
    WebElement searchBox;

    @FindBy(css = "button.menu-button")
    WebElement menuButton;

    @FindBy(xpath = "//a[text()='Men']")
    WebElement menLink;

    @FindBy(xpath = "//a[text()='See All']")
    WebElement seeAllLink;

    @FindBy(id = "onetrust-accept-btn-handler")
    WebElement acceptCookiesButton;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void search(String keyword) {
        searchBox.clear();
        searchBox.sendKeys(keyword);
        searchBox.sendKeys(Keys.ENTER);
    }

    public void clickMenuAndSelectCategory() {
        menuButton.click();
        menLink.click();
        seeAllLink.click();
    }
    public void pressEnterOnSearchBox() {
        searchBox.sendKeys(Keys.ENTER);
    }
    public void clearSearchBox() {
        searchBox.clear();
    }

    public void acceptCookies() {
        if (acceptCookiesButton.isDisplayed()) {
            acceptCookiesButton.click();
        }
    }
}
