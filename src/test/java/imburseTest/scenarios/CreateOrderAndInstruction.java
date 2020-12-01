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
import java.util.Random;

import imburseTest.HmacGenerator;

public class CreateOrderAndInstruction {

        String path;
        private Response response;
        String accessToken;
        String accountId = "782f1b71-7ca4-4465-917f-68d58ffbec8b";
        String tenantId = "6423ae63-59b6-4986-a949-c910ac622471";
        private Scenario scenario;
        String name;

        @Before
        public void before(Scenario scenario) {
                this.scenario = scenario;
        }

        // private String validRequest =
        @Given("the endpoint {string} exists and accessToken is granted")
        public void setUp(String p) throws UnsupportedEncodingException, NoSuchAlgorithmException {
                RestAssured.baseURI = "https://sandbox-api.imbursepayments.com/v1";
                path = p;
                HmacGenerator hmacGenerator = new HmacGenerator();
                String token = hmacGenerator.createToken("");
                response = given().header("Authorization", token).contentType(ContentType.JSON).post("/identity/hmac")
                                .then().extract().response();
                accessToken = response.jsonPath().get("accessToken");
                System.out.println("Access: " + accessToken);
        }

        public String createRandomAlphabeticString() {
                String alphabet = "abcdefghijklmnopqrstuvwxyz";
                String special = "._-";
                String randomString = "";
                Random rAlphabet = new Random();
                Random rSpecial = new Random();
                int randomAlpha;
                int randomSpecial;
                for (int i = 0; i < 10; i++) {
                        randomAlpha = rAlphabet.nextInt(25);
                        randomSpecial = rSpecial.nextInt(2);
                        if (i == 5) {
                                randomString = randomString + special.charAt(randomSpecial);
                        }
                        randomString = randomString + alphabet.charAt(randomAlpha);
                }
                return randomString;
        }

        @When("creation payload with orderref name is sent")
        public void shouldCreateRandomOrder() {
                name = createRandomAlphabeticString();
                scenario.log(String.format("OrderRef name: %s",name));
                response = given().contentType(ContentType.JSON).header("Authorization", "bearer " + accessToken)
                                .header("x-account-id", accountId).header("x-tenant-id", tenantId)
                                .body("{\"orderRef\": \"{{name}}\", \"metadata\": { \"key1\": \"a\",\"key2\": \"b\",\"key3\": \"c\"}}"
                                                .replace("{{name}}", name))
                                .post(path).then().extract().response();
        }

        @When("creation payload with orderref name {string} is sent")
        public void shouldCreateOrder(String name) {
                response = given().contentType(ContentType.JSON).header("Authorization", "bearer " + accessToken)
                                .header("x-account-id", accountId).header("x-tenant-id", tenantId)
                                .body("{\"orderRef\": \"{{name}}\", \"metadata\": { \"key1\": \"a\",\"key2\": \"b\",\"key3\": \"c\"}}"
                                                .replace("{{name}}", name))
                                .post(path).then().extract().response();
                System.out.println("LETAL " + response.getBody().asString());
        }

        @When("creating an order with metadata {string}:{string},{string}:{string},{string}:{string}")
        public void shouldCreateOrderWithMetadaVariables(String key1, String v1, String key2, String v2, String key3, String v3) {
                String name = createRandomAlphabeticString();
                response = given().contentType(ContentType.JSON).header("Authorization", "bearer " + accessToken)
                        .header("x-account-id", accountId).header("x-tenant-id", tenantId)
                        .body("{ \"orderRef\" : \"{{name}}\", \"metadata\": { \"{{key1}}\": \"{{v1}}\",\"{{key2}}\": \"{{v2}}\",\"{{key3}}\": \"{{v3}}\" }}"
                                        .replace("{{name}}", name)
                                        .replace("{{key1}}",key1)
                                        .replace("{{v1}}", v1)
                                        .replace("{{key2}}",key2)
                                        .replace("{{v2}}", v2)
                                        .replace("{{key3}}",key3)
                                        .replace("{{v3}}", v3))
                        .post(path).then().extract().response();
        }

    @Then("response status should be {int}")
    public void checkResponseStatus(int code) {
            System.out.println("BLYAT");
        System.out.println(response.getStatusCode());
        if(response.getStatusCode() != 201 && response.getStatusCode() != 200 && response.getStatusCode() != 202){
                scenario.log(response.jsonPath().getString("errors"));
        }
        Assertions.assertEquals(code, response.getStatusCode());
    }

        @When("trying to create instruction {string}")
        public void createInstruction(String instructionName) throws Exception {
                path = String.format("/order-management/%s/instruction", name);
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
                                .replace("{{direction}}", direction).replace("{{amount}}", amount)
                                .replace("{{currency}}", currency).replace("{{country}}", country)
                                .replace("{{settleBy}}", settleBy).replace("{{schemeId}}", schemeId);
                response = given().contentType(ContentType.JSON).header("Authorization", "bearer " + accessToken)
                                .header("x-account-id", accountId).header("x-tenant-id", tenantId).body(body).post(path)
                                .then().extract().response();
        }

        @When("trying to create instruction {string} with invalid amount {string}")
        public void createInstructionInvalidAmount(String instructionName, String amount)
                        throws Exception {
                path = String.format("/order-management/%s/instruction", name);
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
                                .replace("{{direction}}", direction).replace("{{amount}}", amount)
                                .replace("{{currency}}", currency).replace("{{country}}", country)
                                .replace("{{settleBy}}", settleBy).replace("{{schemeId}}", schemeId);
                response = given().contentType(ContentType.JSON).header("Authorization", "bearer " + accessToken)
                                .header("x-account-id", accountId).header("x-tenant-id", tenantId).body(body).post(path)
                                .then().extract().response();
        }

        @When("trying to create instruction {string} with invalid currency {string}")
        public void createInstructionInvalidCurrency(String instructionName, String currency)
                        throws Exception {
                path = String.format("/order-management/%s/instruction", name);
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
                                .replace("{{direction}}", direction).replace("{{amount}}", amount)
                                .replace("{{currency}}", currency).replace("{{country}}", country)
                                .replace("{{settleBy}}", settleBy).replace("{{schemeId}}", schemeId);
                response = given().contentType(ContentType.JSON).header("Authorization", "bearer " + accessToken)
                                .header("x-account-id", accountId).header("x-tenant-id", tenantId).body(body).post(path)
                                .then().extract().response();
        }

        @When("trying to create instruction {string} with invalid country code {string}")
        public void createInstructionInvalidCountry(String instructionName, String country)
                        throws Exception {
                path = String.format("/order-management/%s/instruction", name);
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
                                .replace("{{direction}}", direction).replace("{{amount}}", amount)
                                .replace("{{currency}}", currency).replace("{{country}}", country)
                                .replace("{{settleBy}}", settleBy).replace("{{schemeId}}", schemeId);
                response = given().contentType(ContentType.JSON).header("Authorization", "bearer " + accessToken)
                                .header("x-account-id", accountId).header("x-tenant-id", tenantId).body(body).post(path)
                                .then().extract().response();
        }

        @When("trying to create instruction {string} with invalid direction {string}")
        public void createInstructionInvalidDirection(String instructionName, String direction)
                        throws Exception {
                path = String.format("/order-management/%s/instruction", name);
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
                                .replace("{{direction}}", direction).replace("{{amount}}", amount)
                                .replace("{{currency}}", currency).replace("{{country}}", country)
                                .replace("{{settleBy}}", settleBy).replace("{{schemeId}}", schemeId);
                response = given().contentType(ContentType.JSON).header("Authorization", "bearer " + accessToken)
                                .header("x-account-id", accountId).header("x-tenant-id", tenantId).body(body).post(path)
                                .then().extract().response();
        }

        @When("trying to create and instruction {string} with invalid schemeId {string}")
        public void createInstructionInvalidSchemeId(String instructionName, String schemeId)
                        throws Exception {
                path = String.format("/order-management/%s/instruction", name);
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
                                .replace("{{direction}}", direction).replace("{{amount}}", amount)
                                .replace("{{currency}}", currency).replace("{{country}}", country)
                                .replace("{{settleBy}}", settleBy).replace("{{schemeId}}", schemeId);
                response = given().contentType(ContentType.JSON).header("Authorization", "bearer " + accessToken)
                                .header("x-account-id", accountId).header("x-tenant-id", tenantId).body(body).post(path)
                                .then().extract().response();
        }
}
