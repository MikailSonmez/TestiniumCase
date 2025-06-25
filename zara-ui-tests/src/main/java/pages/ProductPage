package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Random;

public class ProductPage {
    WebDriver driver;

    @FindBy(css = "div.product-grid-product")
    List<WebElement> productList;

    @FindBy(css = "h1.product-name")
    WebElement productName;

    @FindBy(css = "span.price-current")
    WebElement productPrice;

    @FindBy(css = "button.add-to-cart")
    WebElement addToCartButton;

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
    }
}
