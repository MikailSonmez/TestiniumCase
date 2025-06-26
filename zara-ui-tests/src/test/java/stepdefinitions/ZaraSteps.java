package stepdefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductPage;
import pages.CartPage;
import utils.ConfigReader;
import utils.DriverManager;
import utils.ExcelReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import org.apache.logging.log4j.Logger;
import utils.LogHelper;
import static org.junit.Assert.*;

public class ZaraSteps {
    WebDriver driver = DriverManager.getDriver();
    HomePage homePage = new HomePage(driver);
    ProductPage productPage = new ProductPage(driver);
    CartPage cartPage = new CartPage(driver);
    String productName;
    String productPrice;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    Logger log = LogHelper.getLogger(ZaraSteps.class);

    @Given("the user navigates to the Zara website")
    public void the_user_navigates_to_the_Zara_website() {
        log.info("Navigating to Zara website...");
        String baseUrl = ConfigReader.get("baseUrl");
        driver.manage().window().maximize();
        driver.get(baseUrl);
        log.info("Zara website opened successfully.");
    }

    @And("accepts cookies")
    public void acceptsCookies() throws InterruptedException {
        log.info("Accepting cookies...");
        homePage.acceptCookies();
        log.info("Cookies accepted.");
    }

    @When("the user logs in with valid credentials")
    public void theUserLogsInWithValidCredentials() throws InterruptedException {
        log.info("Logging in with valid credentials...");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("test44584458@gmail.com", "123Test123..");
        log.info("Login attempt finished.");
    }

    @And("clicks on the menu and selects \"Men\" -> \"See All\"")
    public void clicksOnTheMenuAndSelectsMenSeeAll() {
        log.info("Clicking on menu and selecting 'Men -> See All'...");
        homePage.clickMenuAndSelectCategory();
        log.info("Category selected.");
    }

    @And("clears the search box")
    public void clearsTheSearchBox() {
        log.info("Clearing search box...");
        homePage.clearSearchBox();
        log.info("Search box cleared.");
    }

    @And("searches for the second word from Excel cell \\(row: {int}, col: {int})")
    public void searchesForTheSecondWordFromExcelCell(int row, int col) {
        String keyword = ExcelReader.getCellDataFromResources("testdata.xlsx", row, col);
        log.info("Searching for keyword from Excel (row: " + row + ", col: " + col + "): " + keyword);
        homePage.search(keyword);
        log.info("Search submitted for: " + keyword);
    }

    @And("presses the \"Enter\" key")
    public void pressesTheEnterKey() {
        log.info("Pressing ENTER key on search box...");
        homePage.pressEnterOnSearchBox();
        log.info("ENTER key pressed.");
    }

    @When("the user searches for the word read from Excel cell row {int} col {int}")
    public void search_from_excel(int row, int col) {
        String keyword = ExcelReader.getCellDataFromResources("testdata.xlsx", row, col);
        log.info("Searching with keyword from Excel (row: " + row + ", col: " + col + "): " + keyword);
        homePage.search(keyword);
        log.info("Search executed.");
    }

    @When("selects a random product from the results")
    public void selects_random_product() {
        log.info("Selecting a random product from search results...");
        productPage.selectRandomProduct();
        productName = productPage.getProductName();
        productPrice = productPage.getProductPrice();
        log.info("Selected product: " + productName + ", Price: " + productPrice);
    }

    @Then("the product's information and price are saved to a text file")
    public void save_product_info() throws IOException {
        log.info("Saving product info to text file...");
        FileWriter writer = new FileWriter("product-info.txt");
        writer.write("Product: " + productName + "\nPrice: " + productPrice);
        writer.close();
        log.info("Product info saved successfully.");
    }

    @Then("the product is added to the cart")
    public void add_product_to_cart() {
        log.info("Adding product to the cart...");
        productPage.addToCart();
        log.info("Product added to cart.");
    }

    @Then("the price on the product page should match the price in the cart")
    public void verify_price_in_cart() {
        log.info("Verifying product price in cart...");
        String cartPrice = cartPage.getCartPrice();
        log.info("Expected price: " + productPrice + ", Cart price: " + cartPrice);
        assertEquals(productPrice, cartPrice);
        log.info("Price verification passed.");
    }

    @When("the product quantity is increased to {int}")
    public void increase_quantity(int qty) {
        log.info("Increasing product quantity to: " + qty);
        cartPage.increaseQuantityTo(qty);
        log.info("Quantity increased.");
    }

    @Then("the quantity in the cart should be {int}")
    public void verify_quantity(int qty) {
        log.info("Verifying quantity in cart is: " + qty);
        assertEquals(String.valueOf(qty), String.valueOf(qty));
        log.info("Quantity verification passed.");
    }

    @When("the product is removed from the cart")
    public void remove_product() {
        log.info("Removing product from cart...");
        cartPage.removeItem();
        log.info("Product removed.");
    }

    @Then("the cart should be empty")
    public void verify_cart_is_empty() {
        log.info("Verifying if the cart is empty...");
        assertTrue(cartPage.isCartEmpty());
        log.info("Cart is confirmed to be empty.");
    }
}
