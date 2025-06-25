Feature: Zara Product Search and Cart Management

  Scenario: Search shorts and shirts and add to cart
    Given the user navigates to the Zara website
    And accepts cookies
    When the user logs in with valid credentials
    And clicks on the menu and selects "Men" -> "See All"
    When the user searches for the word read from Excel cell row 0 col 0
    And clears the search box
    And searches for the second word from Excel cell (row: 0, col: 1)
    And presses the "Enter" key
    And selects a random product from the results
    Then the product's information and price are saved to a text file
    And the product is added to the cart
    Then the price on the product page should match the price in the cart
    When the product quantity is increased to 2
    Then the quantity in the cart should be 2
    When the product is removed from the cart
    Then the cart should be empty
