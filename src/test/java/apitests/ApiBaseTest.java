package apitests;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class ApiBaseTest {
    // Shared backend API base host path
    protected final String BASE_URI = "http://localhost:1010";

    @BeforeClass
    public void setupApiConfig() {
        RestAssured.baseURI = BASE_URI;
    }
}