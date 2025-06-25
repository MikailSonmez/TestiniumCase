package stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import utils.ApiUtils;
import utils.RandomHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TrelloSteps {

    private String boardId;
    private List<String> cardIds = new ArrayList<>();

    @Given("the Trello API key and token are configured")
    public void apiKeyAndTokenConfigured() {

    }

    @When("a new board is created via Trello API")
    public void createBoard() {
        Response response = ApiUtils.getRequestSpec()
                .queryParam("name", "TestBoard")
                .post("/boards");

        response.then().statusCode(200);
        boardId = response.path("id");
        assertThat(boardId, notNullValue());
    }

    @When("two cards are added to the board")
    public void addTwoCards() {
        for (int i = 1; i <= 2; i++) {
            Response response = ApiUtils.getRequestSpec()
                    .queryParam("name", "Card " + i)
                    .queryParam("idList", getDefaultListId(boardId))
                    .post("/cards");

            response.then().statusCode(200);
            String cardId = response.path("id");
            cardIds.add(cardId);
        }
        assertThat(cardIds.size(), is(2));
    }

    @When("one of the cards is randomly selected and updated")
    public void updateRandomCard() {
        String cardId = RandomHelper.getRandomElement(cardIds);
        Response response = ApiUtils.getRequestSpec()
                .queryParam("name", "Updated Card Name")
                .put("/cards/" + cardId);

        response.then().statusCode(200);
        String updatedName = response.path("name");
        assertThat(updatedName, equalTo("Updated Card Name"));
    }

    @Then("all created cards are deleted")
    public void deleteAllCards() {
        for (String cardId : cardIds) {
            Response response = ApiUtils.getRequestSpec()
                    .delete("/cards/" + cardId);

            response.then().statusCode(200);
        }
        cardIds.clear();
    }

    @Then("the board is deleted")
    public void deleteBoard() {
        Response response = ApiUtils.getRequestSpec()
                .delete("/boards/" + boardId);

        response.then().statusCode(200);
        boardId = null;
    }


    private String getDefaultListId(String boardId) {
        Response response = ApiUtils.getRequestSpec()
                .get("/boards/" + boardId + "/lists");

        response.then().statusCode(200);

        List<Map<String, Object>> lists = response.jsonPath().getList("");
        if (lists.isEmpty()) {
            throw new RuntimeException("No lists found on board");
        }
        return (String) lists.get(0).get("id");
    }
}
