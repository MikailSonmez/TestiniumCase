package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.Logger;
import utils.LogHelper;

import java.time.Duration;

public class LoginPage {
    WebDriver driver;
    Logger log = LogHelper.getLogger(LoginPage.class);

    @FindBy(id = "zds-:r5:")
    WebElement emailInput;

    @FindBy(id = "zds-:r8:")
    WebElement passwordInput;

    @FindBy(css = "button[role='button']")
    WebElement loginButton;

    @FindBy(xpath = "//a[contains(text(),'GİRİŞ YAP')]")
    WebElement headerLoginIn;

    @FindBy(xpath = "//button[contains(text(),'GİRİŞ YAP')]")
    WebElement pageLoginIn;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void login(String email, String password) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        headerLoginIn.click();
        pageLoginIn.click();

        wait.until(ExpectedConditions.visibilityOf(emailInput));
        emailInput.click();
        emailInput.sendKeys(email);
        log.info("Attempting login with email: {}", email);
        passwordInput.click();
        passwordInput.sendKeys(password);
        log.debug("Password field is set.");
        Thread.sleep(5000);
        loginButton.click();

    }
}
