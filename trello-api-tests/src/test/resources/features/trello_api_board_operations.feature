Feature: Trello Board and Card API Operations

  Scenario: Create board, add cards, update and delete
    Given the Trello API key and token are configured
    When a new board is created via Trello API
    And two cards are added to the board
    And one of the cards is randomly selected and updated
    Then all created cards are deleted
    And the board is deleted
