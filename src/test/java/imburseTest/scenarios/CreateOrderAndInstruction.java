package imburseTest.scenarios;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


import org.junit.jupiter.api.Assertions;


import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import imburseTest.HmacGenerator;
public class CreateOrderAndInstruction {

    String path;
    String orderName;
    private Response response;
    String accessToken;
    String accountId = "782f1b71-7ca4-4465-917f-68d58ffbec8b";
    String tenantId = "6423ae63-59b6-4986-a949-c910ac622471";
    private Scenario scenario;


    @Before
    public void before(Scenario scenario){
        this.scenario = scenario;
    }

    // private String validRequest =
    @Given("the endpoint {string} exists and accessToken is granted")
    public void setUp(String p) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        RestAssured.baseURI = "https://sandbox-api.imbursepayments.com/v1";
        path = p;
        System.out.println(RestAssured.baseURI + path);
        HmacGenerator hmacGenerator = new HmacGenerator();
        String token = hmacGenerator.createToken("");
        response = given().header("Authorization", token).contentType(ContentType.JSON).post("/identity/hmac").then()
                .extract().response();
        accessToken = response.jsonPath().get("accessToken");
        System.out.println("Access: " + accessToken);
    }

    @When("creation payload with orderref name {string} is sent")
    public void shouldCreateOrder(String name) {
        response = given().contentType(ContentType.JSON).header("Authorization", "bearer " + accessToken)
                .header("x-account-id", accountId).header("x-tenant-id", tenantId)
                .body("{ \"orderRef\" : \"{{name}}\", \"metadata\": \"{\" \"key1\": \"a\",\"key2\": \"b\",\"key3\": \"c\", }".replace("{{name}}", name)).post(path).then().extract()
                .response();
    }

    @When("trying to create an order with orderRef name {} and metada {string}:{string}, {string}:{string}, {string}:{string}")
    public void shouldCreateOrderWithMetadaVariables(String name,String key1, String v1, String key2, String v2, String key3, String v3) {
        response = given().contentType(ContentType.JSON).header("Authorization", "bearer " + accessToken)
                .header("x-account-id", accountId).header("x-tenant-id", tenantId)
                .body("{ \"orderRef\" : \"{{name}}\", \"metadata\": \"{\" \"{{key1}}\": \"{{v1}}\",\"{{key2}}\": \"{{v2}}\",\"{{key3}}\": \"{{v3}}\", }".replace("{{name}}", name)).post(path).then().extract()
                .response();
    }

    @Then("response status should be {int}")
    public void checkResponseStatus(int code) {
        if(!response.body().asString().isEmpty()){
            scenario.log(response.jsonPath().getString("errors.message"));

        }
        Assertions.assertEquals(code, response.getStatusCode());
    }


    @When("trying to create instruction {string} for {string}")
    public void createInstruction(String instructionName, String order) throws Exception {
        path = String.format("/order-management/%s/instruction", order);
        System.out.println(RestAssured.baseURI + path);
        String direction = "DEBIT";
        String custRef = "customerRef";
        String amount = "100.01";
        String currency = "EUR";
        String country = "IE";
        String settleBy = "2020-10-21";
        String schemeId = "654EB81FF7F07F7CF5A1EE3FF6972E90";
        String body = ("{\"instructionRef\": \"{{instructionRef}}\", \"customerRef\":\"{{custRef}}\", \"direction\":\"{{direction}}\", \"amount\":\"{{amount}}\", \"currency\":\"{{currency}}\", \"country\":\"{{country}}\", \"settledByDate\":\"{{settleBy}}\", \"schemeId\":\"{{schemeId}}\", \"metadata\":{\"key1\":\"a\",\"key2\":\"b\",\"key3\":\"c\"}}")
                .replace("{{instructionRef}}", instructionName).replace("{{custRef}}", custRef)
                .replace("{{direction}}", direction).replace("{{amount}}", amount).replace("{{currency}}", currency)
                .replace("{{country}}", country).replace("{{settleBy}}", settleBy).replace("{{schemeId}}", schemeId);
        response = given().contentType(ContentType.JSON).header("Authorization", "bearer " + accessToken)
                .header("x-account-id", accountId).header("x-tenant-id", tenantId).body(body).post(path).then()
                .extract().response();
    }

    @When("trying to create instruction {string} for {string} with invalid amount {string}")
    public void createInstructionInvalidAmount(String instructionName, String order, String amount) throws Exception {
        path = String.format("/order-management/%s/instruction", order);
        System.out.println(RestAssured.baseURI + path);
        String direction = "DEBIT";
        String custRef = "customerRef";
        // String amount = "100..01";
        String currency = "EUR";
        String country = "IE";
        String settleBy = "2020-10-21";
        String schemeId = "654EB81FF7F07F7CF5A1EE3FF6972E90";
        String body = ("{\"instructionRef\": \"{{instructionRef}}\", \"customerRef\":\"{{custRef}}\", \"direction\":\"{{direction}}\", \"amount\":\"{{amount}}\", \"currency\":\"{{currency}}\", \"country\":\"{{country}}\", \"settledByDate\":\"{{settleBy}}\", \"schemeId\":\"{{schemeId}}\", \"metadata\":{\"key1\":\"a\",\"key2\":\"b\",\"key3\":\"c\"}}")
                .replace("{{instructionRef}}", instructionName).replace("{{custRef}}", custRef)
                .replace("{{direction}}", direction).replace("{{amount}}", amount).replace("{{currency}}", currency)
                .replace("{{country}}", country).replace("{{settleBy}}", settleBy).replace("{{schemeId}}", schemeId);
        response = given().contentType(ContentType.JSON).header("Authorization", "bearer " + accessToken)
                .header("x-account-id", accountId).header("x-tenant-id", tenantId).body(body).post(path).then()
                .extract().response();
    }

    @When("trying to create instruction {string} for {string} with invalid currency {string}")
    public void createInstructionInvalidCurrency(String instructionName, String order, String currency) throws Exception {
        path = String.format("/order-management/%s/instruction", order);
        System.out.println(RestAssured.baseURI + path);
        String direction = "DEBIT";
        String custRef = "customerRef";
        String amount = "100";
        // String currency = "EUR";
        String country = "IE";
        String settleBy = "2020-10-21";
        String schemeId = "654EB81FF7F07F7CF5A1EE3FF6972E90";
        String body = ("{\"instructionRef\": \"{{instructionRef}}\", \"customerRef\":\"{{custRef}}\", \"direction\":\"{{direction}}\", \"amount\":\"{{amount}}\", \"currency\":\"{{currency}}\", \"country\":\"{{country}}\", \"settledByDate\":\"{{settleBy}}\", \"schemeId\":\"{{schemeId}}\", \"metadata\":{\"key1\":\"a\",\"key2\":\"b\",\"key3\":\"c\"}}")
                .replace("{{instructionRef}}", instructionName).replace("{{custRef}}", custRef)
                .replace("{{direction}}", direction).replace("{{amount}}", amount).replace("{{currency}}", currency)
                .replace("{{country}}", country).replace("{{settleBy}}", settleBy).replace("{{schemeId}}", schemeId);
        response = given().contentType(ContentType.JSON).header("Authorization", "bearer " + accessToken)
                .header("x-account-id", accountId).header("x-tenant-id", tenantId).body(body).post(path).then()
                .extract().response();
    }
    @When("trying to create instruction {string} for {string} with invalid country code {string}")
    public void createInstructionInvalidCountry(String instructionName, String order, String country) throws Exception {
        path = String.format("/order-management/%s/instruction", order);
        System.out.println(RestAssured.baseURI + path);
        String direction = "DEBIT";
        String custRef = "customerRef";
        String amount = "100";
        String currency = "EUR";
        // String country = "IE";
        String settleBy = "2020-10-21";
        String schemeId = "654EB81FF7F07F7CF5A1EE3FF6972E90";
        String body = ("{\"instructionRef\": \"{{instructionRef}}\", \"customerRef\":\"{{custRef}}\", \"direction\":\"{{direction}}\", \"amount\":\"{{amount}}\", \"currency\":\"{{currency}}\", \"country\":\"{{country}}\", \"settledByDate\":\"{{settleBy}}\", \"schemeId\":\"{{schemeId}}\", \"metadata\":{\"key1\":\"a\",\"key2\":\"b\",\"key3\":\"c\"}}")
                .replace("{{instructionRef}}", instructionName).replace("{{custRef}}", custRef)
                .replace("{{direction}}", direction).replace("{{amount}}", amount).replace("{{currency}}", currency)
                .replace("{{country}}", country).replace("{{settleBy}}", settleBy).replace("{{schemeId}}", schemeId);
        response = given().contentType(ContentType.JSON).header("Authorization", "bearer " + accessToken)
                .header("x-account-id", accountId).header("x-tenant-id", tenantId).body(body).post(path).then()
                .extract().response();
    }

    @When("trying to create instruction {string} for {string} with invalid direction {string}")
    public void createInstructionInvalidDirection(String instructionName, String order, String direction) throws Exception {
        path = String.format("/order-management/%s/instruction", order);
        System.out.println(RestAssured.baseURI + path);
        // String direction = "DEBIT";
        String custRef = "customerRef";
        String amount = "100";
        String currency = "EUR";
        String country = "IE";
        String settleBy = "2020-10-21";
        String schemeId = "654EB81FF7F07F7CF5A1EE3FF6972E90";
        String body = ("{\"instructionRef\": \"{{instructionRef}}\", \"customerRef\":\"{{custRef}}\", \"direction\":\"{{direction}}\", \"amount\":\"{{amount}}\", \"currency\":\"{{currency}}\", \"country\":\"{{country}}\", \"settledByDate\":\"{{settleBy}}\", \"schemeId\":\"{{schemeId}}\", \"metadata\":{\"key1\":\"a\",\"key2\":\"b\",\"key3\":\"c\"}}")
                .replace("{{instructionRef}}", instructionName).replace("{{custRef}}", custRef)
                .replace("{{direction}}", direction).replace("{{amount}}", amount).replace("{{currency}}", currency)
                .replace("{{country}}", country).replace("{{settleBy}}", settleBy).replace("{{schemeId}}", schemeId);
        response = given().contentType(ContentType.JSON).header("Authorization", "bearer " + accessToken)
                .header("x-account-id", accountId).header("x-tenant-id", tenantId).body(body).post(path).then()
                .extract().response();
    }

    @When("trying to create and instruction {string} for {string} with invalid schemeId {string}")
    public void createInstructionInvalidSchemeId(String instructionName, String order, String schemeId) throws Exception {
        path = String.format("/order-management/%s/instruction", order);
        System.out.println(RestAssured.baseURI + path);
        String direction = "DEBIT";
        String custRef = "customerRef";
        String amount = "100";
        String currency = "EUR";
        String country = "IE";
        String settleBy = "2020-10-21";
        // String schemeId = "654EB81FF7F07F7CF5A1EE3FF6972E90";
        String body = ("{\"instructionRef\": \"{{instructionRef}}\", \"customerRef\":\"{{custRef}}\", \"direction\":\"{{direction}}\", \"amount\":\"{{amount}}\", \"currency\":\"{{currency}}\", \"country\":\"{{country}}\", \"settledByDate\":\"{{settleBy}}\", \"schemeId\":\"{{schemeId}}\", \"metadata\":{\"key1\":\"a\",\"key2\":\"b\",\"key3\":\"c\"}}")
                .replace("{{instructionRef}}", instructionName).replace("{{custRef}}", custRef)
                .replace("{{direction}}", direction).replace("{{amount}}", amount).replace("{{currency}}", currency)
                .replace("{{country}}", country).replace("{{settleBy}}", settleBy).replace("{{schemeId}}", schemeId);
        response = given().contentType(ContentType.JSON).header("Authorization", "bearer " + accessToken)
                .header("x-account-id", accountId).header("x-tenant-id", tenantId).body(body).post(path).then()
                .extract().response();
    }
}
