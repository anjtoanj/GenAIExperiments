package automation.tests;

import org.testng.annotations.BeforeMethod;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.Assert;
import static io.restassured.RestAssured.given;
import org.testng.annotations.Test;

public class PetStoreAPITests {

    @BeforeMethod
    public void setup() {
        // Set the base URI for all tests
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test
    public void updatePet() {
        // Update an existing pet with PUT method
        String updatePetBody = """
            {
                "id": 1,
                "category": {
                    "id": 1,
                    "name": "Dogs"
                },
                "name": "Updated Dog",
                "photoUrls": ["string"],
                "tags": [
                    {
                        "id": 1,
                        "name": "string"
                    }
                ],
                "status": "available"
            }""";

        given()
            .contentType(ContentType.JSON)
            .body(updatePetBody)
            .when()
            .put("/pet")
            .then()
            .assertThat()
            .statusCode(200)
            .log()
            .all();

        // Verify the response contains updated name
        String response = given()
            .when()
            .get("/pet/1")
            .then()
            .extract()
            .response()
            .asString();

        System.out.println("Update Pet Response: " + response);
        Assert.assertTrue(response.contains("Updated Dog"));
    }

    @Test
    public void addNewPet() {
        // Add a new pet with POST method
        String newPetBody = """
            {
                "id": 999,
                "category": {
                    "id": 1,
                    "name": "Dogs"
                },
                "name": "New Dog",
                "photoUrls": ["string"],
                "tags": [
                    {
                        "id": 1,
                        "name": "string"
                    }
                ],
                "status": "available"
            }""";

        given()
            .contentType(ContentType.JSON)
            .body(newPetBody)
            .when()
            .post("/pet")
            .then()
            .assertThat()
            .statusCode(200)
            .log()
            .all();

        // Verify the newly added pet
        String response = given()
            .when()
            .get("/pet/999")
            .then()
            .extract()
            .response()
            .asString();

        System.out.println("Add Pet Response: " + response);
        Assert.assertTrue(response.contains("New Dog"));
    }

    @Test
    public void findPetsByStatus() {
        // Find pets by status with GET method
        given()
            .when()
            .param("status", "available")
            .get("/pet/findByStatus")
            .then()
            .assertThat()
            .statusCode(200)
            .log()
            .all();

        // Get and print the response
        String response = given()
            .when()
            .param("status", "available")
            .get("/pet/findByStatus")
            .then()
            .extract()
            .response()
            .asString();

        System.out.println("Find Pets By Status Response: " + response);
        Assert.assertTrue(response.contains("available"));
    }
}