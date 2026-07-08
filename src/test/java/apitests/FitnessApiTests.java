package apitests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class FitnessApiTests extends ApiBaseTest {

    private static String dynamicUser;
    private static String userToken;
    private static String adminToken;
    private static int createdMealId;

    @BeforeClass
    public void setupTokens() {

        Response adminResponse = given()
                .contentType(ContentType.JSON)
                .body("""
                {
                  "username":"admin",
                  "password":"admin123"
                }
            """)
                .post("/api/auth/login");

        adminToken = adminResponse.jsonPath().getString("token");

        dynamicUser = "api_user_" + System.currentTimeMillis();

        String registerBody = """
        {
          "username":"%s",
          "password":"Pass@123",
          "name":"Test User",
          "phone":"9876543210",
          "gender":"MALE",
          "age":25,
          "heightCm":175,
          "weightKg":70,
          "goal":"WEIGHT_LOSS",
          "dietPreference":"VEG",
          "workoutLevel":"BEGINNER"
        }
        """.formatted(dynamicUser);

        Response userResponse = given()
                .contentType(ContentType.JSON)
                .body(registerBody)
                .post("/api/auth/register");

        userToken = userResponse.jsonPath().getString("token");
    }


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
                "  \"dietPreference\": \"VEG\",\n" +
                "  \"workoutLevel\": \"BEGINNER\",\n" +
                "  \"goal\": \"OVERALL_HEALTH\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(registerBody)
                .when()
                .post("/api/auth/register")
                .then()
                .statusCode(200)
                .body("username", equalTo(dynamicUser))
                .body("token", notNullValue())
                .body("role", equalTo("USER"))
                .body("userId", notNullValue());

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
                .body("name", notNullValue())
                .body("goal", notNullValue())
                .body("workoutLevel", notNullValue());

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
                .post("/api/auth/login");

        response.prettyPrint();

        response.then().statusCode(200);

        adminToken = response.jsonPath().getString("token");

        dynamicUser = "api_user_" + System.currentTimeMillis();

        String registerBody = "{\n" +
                "  \"username\": \"" + dynamicUser + "\",\n" +
                "  \"password\": \"Pass@123\",\n" +
                "  \"name\": \"Test User\",\n" +
                "  \"phone\": \"9876543210\",\n" +
                "  \"gender\": \"MALE\",\n" +
                "  \"age\": 25,\n" +
                "  \"heightCm\": 175,\n" +
                "  \"weightKg\": 70,\n" +
                "  \"goal\": \"WEIGHT_LOSS\",\n" +
                "  \"dietPreference\": \"VEG\",\n" +
                "  \"workoutLevel\": \"BEGINNER\"\n" +
                "}";

        response = given()
                .contentType(ContentType.JSON)
                .body(registerBody)
                .when()
                .post("/api/auth/register");

        response.then().statusCode(200);

        userToken = response.jsonPath().getString("token");

        System.out.println("Dynamic User = " + dynamicUser);

        String userLoginBody = "{\n" +
                "  \"username\": \"" + dynamicUser + "\",\n" +
                "  \"password\": \"Pass@123\"\n" +
                "}";

        Response userResponse = given()
                .contentType(ContentType.JSON)
                .body(userLoginBody)
                .when()
                .post("/api/auth/login");

        userResponse.prettyPrint();

        userResponse.then()
                .statusCode(200);

        userToken = userResponse.jsonPath().getString("token");
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
                .body("profile.bmi", notNullValue())
                .body("wellnessTips.waterIntake", notNullValue());
    }


    @Test(priority = 6, description = "TC_API_06: Complete task API")
    public void testCompleteTaskApi() {

        String taskPayload = """
        {
          "taskId": 17,
          "taskType": "BREAKFAST"
        }
        """;

        given()
                .header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(taskPayload)
                .when()
                .post("/api/user/complete-task")
                .then()
                .statusCode(200)
                .body("date", notNullValue())
                .body("completedWorkoutIds", hasItem(17))
                .body("badge", equalTo("Beginner"));
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

        String mealBody = """
    {
      "foodItem": "Automation Test Quinoa Bowl",
      "calories": 420,
      "proteins": 15,
      "carbs": 60,
      "fats": 10,
      "mealType": "LUNCH",
      "dietPreference": "VEG",
      "goal": "WEIGHT_LOSS",
      "dayNumber": 1
    }
    """;

        Response response = given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .body(mealBody)
                .when()
                .post("/api/admin/meals")
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("foodItem", equalTo("Automation Test Quinoa Bowl"))
                .body("mealType", equalTo("LUNCH"))
                .body("calories", equalTo(420))
                .body("proteins", equalTo(15.0f))
                .body("carbs", equalTo(60.0f))
                .body("fats", equalTo(10.0f))
                .body("dietPreference", equalTo("VEG"))
                .body("goal", equalTo("WEIGHT_LOSS"))
                .body("dayNumber", equalTo(1))
                .extract()
                .response();

        createdMealId = response.jsonPath().getInt("id");

        System.out.println("Created Meal ID: " + createdMealId);
    }

    @Test(priority = 10, description = "SM_TC_10: Admin delete meal plan")
    public void testAdminDeleteMealPlan() {

        given()
                .header("Authorization", "Bearer " + adminToken)
                .pathParam("id", createdMealId)
                .when()
                .delete("/api/admin/meals/{id}")
                .then()
                .statusCode(200);

    }

}