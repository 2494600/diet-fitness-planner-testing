package apitests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class FitnessApiTests extends ApiBaseTest {

    private static String dynamicUser;
    private static String userToken;
    private static String adminToken;
    private static int createdMealId;



    @Test(priority = 1, description = "TC_API_01: Register user with valid data")
    public void testRegisterUserWithValidData() {
        dynamicUser = "api_user_" + System.currentTimeMillis() / 1000;

        String registerBody = "{\n" +
                "  \"name\": \"API Test User\",\n" +
                "  \"phone\": \"9876543210\",\n" +
                "  \"username\": \"" + dynamicUser + "\",\n" +
                "  \"password\": \"Pass@123\",\n" +
                "  \"gender\": \"MALE\",\n" +
                "  \"age\": 25,\n" +
                "  \"heightCm\": 175,\n" +
                "  \"weightKg\": 70,\n" +
                "  \"dietPreference\": \"VEGETARIAN\",\n" +
                "  \"fitnessLevel\": \"BEGINNER\",\n" +
                "  \"fitnessGoal\": \"OVERALL_HEALTH\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(registerBody)
                .when()
                .post("/api/auth/register")
                .then()
                .statusCode(200)
                .body("username", equalTo(dynamicUser))
                .body("message", notNullValue());
    }

    @Test(priority = 2, description = "TC_API_02: Register user with missing fields")
    public void testRegisterUserWithMissingFields() {
        String invalidRegisterBody = "{\n" +
                "  \"username\": \"incomplete_user\",\n" +
                "  \"password\": \"Pass@123\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(invalidRegisterBody)
                .when()
                .post("/api/auth/register")
                .then()
                .statusCode(400)
                .body("errors", notNullValue());
    }



    @Test(priority = 3, description = "TC_API_03: Login with valid credentials")
    public void testLoginWithValidCredentials() {
        String loginBody = "{\n" +
                "  \"username\": \"admin\",\n" + // Authenticating as admin to catch token first
                "  \"password\": \"admin123\"\n" +
                "}";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(loginBody)
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue())
                .extract().response();

        adminToken = response.jsonPath().getString("token");

        // Log in with the dynamically registered test user to capture user token context parameters
        String userLoginBody = "{\n" +
                "  \"username\": \"" + dynamicUser + "\",\n" +
                "  \"password\": \"Pass@123\"\n" +
                "}";

        userToken = given()
                .contentType(ContentType.JSON)
                .body(userLoginBody)
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(200)
                .extract().jsonPath().getString("token");
    }

    @Test(priority = 4, description = "TC_API_04: Login with invalid credentials")
    public void testLoginWithInvalidCredentials() {
        String wrongLoginBody = "{\n" +
                "  \"username\": \"admin\",\n" +
                "  \"password\": \"wrongPassword123\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(wrongLoginBody)
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(401);
    }



    @Test(priority = 5, description = "TC_API_05: Get dashboard data")
    public void testGetDashboardData() {
        given()
                .header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/user/dashboard")
                .then()
                .statusCode(200)
                .body("bmi", notNullValue())
                .body("dailyWaterIntake", notNullValue());
    }

    @Test(priority = 6, description = "TC_API_06: Complete task API")
    public void testCompleteTaskApi() {
        String taskPayload = "{\n" +
                "  \"taskId\": \"task_01\",\n" +
                "  \"taskType\": \"BREAKFAST\"\n" +
                "}";

        given()
                .header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(taskPayload)
                .when()
                .post("/api/user/complete-task")
                .then()
                .statusCode(200)
                .body("progressUpdated", equalTo(true));
    }

    @Test(priority = 7, description = "TC_API_07: Get user history")
    public void testGetUserHistory() {
        given()
                .header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/user/history")
                .then()
                .statusCode(200)
                .body("historyLogs", org.hamcrest.Matchers.notNullValue());
    }

    // --- Section 4: Protected Administrative Panel Operations (TS_04) ---

    @Test(priority = 8, description = "SM_TC_08: Verify admin can fetch general users list data mapping")
    public void testAdminGetUsersList() {
        given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/admin/users")
                .then()
                .statusCode(200)
                .body("usersList", hasSize(greaterThan(0)));
    }

    @Test(priority = 9, description = "SM_TC_09: Admin create meal plan")
    public void testAdminCreateMealPlan() {
        String mealBody = "{\n" +
                "  \"mealName\": \"Automation Test Quinoa Bowl\",\n" +
                "  \"calories\": 420,\n" +
                "  \"proteinGrams\": 15,\n" +
                "  \"carbGrams\": 60,\n" +
                "  \"mealType\": \"LUNCH\"\n" +
                "}";

        Response response = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(mealBody)
                .when()
                .post("/api/admin/meals")
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .extract().response();

        createdMealId = response.jsonPath().getInt("id");
    }

    @Test(priority = 10, description = "SM_TC_10: Admin delete meal plan")
    public void testAdminDeleteMealPlan() {
        given()
                .header("Authorization", "Bearer " + adminToken)
                .pathParam("id", createdMealId)
                .when()
                .delete("/api/admin/meals/{id}")
                .then()
                .statusCode(200)
                .body("deleted", equalTo(true));
    }
}