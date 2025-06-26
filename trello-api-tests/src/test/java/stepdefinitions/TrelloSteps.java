package stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import utils.ApiUtils;
import utils.RandomHelper;
import utils.LogHelper;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TrelloSteps {

    private String boardId;
    private List<String> cardIds = new ArrayList<>();
    Logger log = LogHelper.getLogger(TrelloSteps.class);

    @Given("the Trello API key and token are configured")
    public void apiKeyAndTokenConfigured() {
        log.info("Trello API key and token are assumed to be configured.");
    }

    @When("a new board is created via Trello API")
    public void createBoard() {
        log.info("Creating a new Trello board...");
        Response response = ApiUtils.getRequestSpec()
                .queryParam("name", "TestBoard")
                .post("/boards");

        response.then().statusCode(200);
        boardId = response.path("id");
        log.info("New board created with ID: " + boardId);
        assertThat(boardId, notNullValue());
    }

    @When("two cards are added to the board")
    public void addTwoCards() {
        log.info("Adding two cards to the board...");
        for (int i = 1; i <= 2; i++) {
            Response response = ApiUtils.getRequestSpec()
                    .queryParam("name", "Card " + i)
                    .queryParam("idList", getDefaultListId(boardId))
                    .post("/cards");

            response.then().statusCode(200);
            String cardId = response.path("id");
            cardIds.add(cardId);
            log.info("Card " + i + " added with ID: " + cardId);
        }
        assertThat(cardIds.size(), is(2));
        log.info("Total cards added: " + cardIds.size());
    }

    @When("one of the cards is randomly selected and updated")
    public void updateRandomCard() {
        String cardId = RandomHelper.getRandomElement(cardIds);
        log.info("Randomly selected card ID for update: " + cardId);

        Response response = ApiUtils.getRequestSpec()
                .queryParam("name", "Updated Card Name")
                .put("/cards/" + cardId);

        response.then().statusCode(200);
        String updatedName = response.path("name");
        log.info("Card updated successfully. New name: " + updatedName);
        assertThat(updatedName, equalTo("Updated Card Name"));
    }

    @Then("all created cards are deleted")
    public void deleteAllCards() {
        log.info("Deleting all created cards...");
        for (String cardId : cardIds) {
            Response response = ApiUtils.getRequestSpec()
                    .delete("/cards/" + cardId);

            response.then().statusCode(200);
            log.info("Deleted card ID: " + cardId);
        }
        cardIds.clear();
        log.info("All cards deleted and card list cleared.");
    }

    @Then("the board is deleted")
    public void deleteBoard() {
        log.info("Deleting the board with ID: " + boardId);
        Response response = ApiUtils.getRequestSpec()
                .delete("/boards/" + boardId);

        response.then().statusCode(200);
        log.info("Board deleted successfully.");
        boardId = null;
    }

    private String getDefaultListId(String boardId) {
        log.info("Retrieving default list ID for board: " + boardId);
        Response response = ApiUtils.getRequestSpec()
                .get("/boards/" + boardId + "/lists");

        response.then().statusCode(200);

        List<Map<String, Object>> lists = response.jsonPath().getList("");
        if (lists.isEmpty()) {
            log.error("No lists found on the board.");
            throw new RuntimeException("No lists found on board");
        }
        String listId = (String) lists.get(0).get("id");
        log.info("Default list ID retrieved: " + listId);
        return listId;
    }
}
