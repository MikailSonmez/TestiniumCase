package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Random;

public class ProductPage {
    WebDriver driver;

    @FindBy(className = "media-image__image media__wrapper--media")
    List<WebElement> productList;

    @FindBy(className = "product-detail-info__header-name")
    WebElement productName;

    @FindBy(className = "money-amount__main")
    WebElement productPrice;

    @FindBy(xpath = "//button[@aria-label='Ekle DOKULU POLO T-SHIRT']")
    WebElement addToCartButton;

    @FindBy(xpath = "//div[normalize-space()='S (US S)']")
    WebElement selectSmallSizeButton;

    @FindBy(xpath = "//span[contains(text(),'Alışveriş sepetini gör')]")
    WebElement lookAtCart;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void selectRandomProduct() {
        int size = productList.size();
        Random random = new Random();
        productList.get(random.nextInt(size)).click();
    }

    public String getProductName() {
        return productName.getText();
    }

    public String getProductPrice() {
        return productPrice.getText();
    }

    public void addToCart() {
        addToCartButton.click();
        selectSmallSizeButton.click();
        lookAtCart.click();
    }
}
