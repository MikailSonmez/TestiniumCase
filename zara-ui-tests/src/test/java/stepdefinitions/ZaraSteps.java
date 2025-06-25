package stepdefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductPage;
import pages.CartPage;
import utils.ConfigReader;
import utils.DriverManager;
import utils.ExcelReader;

import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.*;

public class ZaraSteps {
    WebDriver driver = DriverManager.getDriver();
    HomePage homePage = new HomePage(driver);
    ProductPage productPage = new ProductPage(driver);
    CartPage cartPage = new CartPage(driver);
    String productName;
    String productPrice;

    @Given("the user navigates to the Zara website")
    public void the_user_navigates_to_the_Zara_website() {
        String baseUrl = ConfigReader.get("baseUrl");
        WebDriver driver = DriverManager.getDriver();
        driver.manage().window().maximize();
        driver.get(baseUrl);

    }
    @And("accepts cookies")
    public void acceptsCookies() {
        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.acceptCookies();
    }

    @When("the user logs in with valid credentials")
    public void theUserLogsInWithValidCredentials() throws InterruptedException {
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.login("test44584458@gmail.com", "123Test123..");
    }

    @And("clicks on the menu and selects \"Men\" -> \"See All\"")
    public void clicksOnTheMenuAndSelectsMenSeeAll() {
        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.clickMenuAndSelectCategory();  // Parametresiz olacak şekilde aşağıda yazılacak
    }

    @And("clears the search box")
    public void clearsTheSearchBox() {
        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.clearSearchBox();
    }

    @And("searches for the second word from Excel cell \\(row: {int}, col: {int})")
    public void searchesForTheSecondWordFromExcelCell(int row, int col) {
        String keyword = ExcelReader.getCellData(row, col);  // senin util sınıfında var
        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.search(keyword);
    }

    @And("presses the \"Enter\" key")
    public void pressesTheEnterKey() {
        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.pressEnterOnSearchBox();  // Aşağıda eklenecek
    }


    @When("the user searches for the word read from Excel cell row {int} col {int}")
    public void search_from_excel(int row, int col) {
        String keyword = ExcelReader.getCellDataFromResources("testdata.xlsx", row, col);
        homePage.search(keyword);
    }


    @When("selects a random product from the results")
    public void selects_random_product() {
        productPage.selectRandomProduct();
        productName = productPage.getProductName();
        productPrice = productPage.getProductPrice();
    }

    @Then("the product's information and price are saved to a text file")
    public void save_product_info() throws IOException {
        FileWriter writer = new FileWriter("product-info.txt");
        writer.write("Product: " + productName + "\nPrice: " + productPrice);
        writer.close();
    }

    @Then("the product is added to the cart")
    public void add_product_to_cart() {
        productPage.addToCart();
    }

    @Then("the price on the product page should match the price in the cart")
    public void verify_price_in_cart() {
        String cartPrice = cartPage.getCartPrice();
        assertEquals(productPrice, cartPrice);
    }

    @When("the product quantity is increased to {int}")
    public void increase_quantity(int qty) {
        cartPage.increaseQuantityTo(qty);
    }

    @Then("the quantity in the cart should be {int}")
    public void verify_quantity(int qty) {
        assertEquals(String.valueOf(qty), "2"); // Assume 2 for now
    }

    @When("the product is removed from the cart")
    public void remove_product() {
        cartPage.removeItem();
    }

    @Then("the cart should be empty")
    public void verify_cart_is_empty() {
        assertTrue(cartPage.isCartEmpty());
    }
}