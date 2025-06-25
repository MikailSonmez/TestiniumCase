package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage {
    WebDriver driver;

    @FindBy(css = "span.cart-price")
    WebElement cartPrice;

    @FindBy(css = "input.quantity-input")
    WebElement quantityInput;

    @FindBy(css = "button.increase-qty")
    WebElement increaseQtyButton;

    @FindBy(css = "button.remove-item")
    WebElement removeItemButton;

    @FindBy(css = "div.empty-cart-message")
    WebElement emptyCartMessage;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getCartPrice() {
        return cartPrice.getText();
    }

    public void increaseQuantityTo(int quantity) {
        while (Integer.parseInt(quantityInput.getAttribute("value")) < quantity) {
            increaseQtyButton.click();
        }
    }

    public void removeItem() {
        removeItemButton.click();
    }

    public boolean isCartEmpty() {
        return emptyCartMessage.isDisplayed();
    }
}
