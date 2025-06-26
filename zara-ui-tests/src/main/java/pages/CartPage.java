package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage {
    WebDriver driver;

    @FindBy(xpath = "//div[@class='money-amount']")
    WebElement cartPrice;

    @FindBy(css = "input.quantity-input")
    WebElement quantityInput;

    @FindBy(css = "div[aria-label='Bir birim daha ekle - DOKULU POLO T-SHIRT'] svg")
    WebElement increaseQtyButton;

    @FindBy(css = "button[aria-label='Ürünü sil'] span")
    WebElement removeItemButton;

    @FindBy(css = "div[class='zds-empty-state__title'] span")
    WebElement emptyCartMessage;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getCartPrice() {
        return cartPrice.getText();
    }

    public void increaseQuantityTo(int quantity) {
        while (Integer.parseInt(quantityInput.getAttribute("2")) < quantity) {
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
