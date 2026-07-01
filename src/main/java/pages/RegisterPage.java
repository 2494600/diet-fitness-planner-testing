package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends BasePage {
    private final By nameField = By.name("name");
    private final By phoneField = By.name("phone");
    private final By usernameField = By.name("username");
    private final By passwordField = By.name("password");
    private final By ageField = By.name("age");
    private final By heightField = By.name("heightCm");
    private final By weightField = By.name("weightKg");

    private final By maleButton = By.xpath("//button[contains(@class,'gender-btn')][contains(translate(., 'MALE', 'male'), 'male')]");
    private final By femaleButton = By.xpath("//button[contains(@class,'gender-btn')][contains(translate(., 'FEMALE', 'female'), 'female')]");
    private final By vegetarianButton = By.xpath("//button[contains(@class,'gender-btn')][contains(translate(., 'VEGETARIAN', 'vegetarian'), 'vegetarian')]");
    private final By nonVegButton = By.xpath("//button[contains(@class,'gender-btn')][contains(translate(., 'NON-VEG', 'non-veg'), 'non-veg')]");

    private final By beginnerButton = By.xpath("//button[contains(@class,'level-btn')][contains(translate(., 'BEGINNER', 'beginner'), 'beginner')]");
    private final By intermediateButton = By.xpath("//button[contains(@class,'level-btn')][contains(translate(., 'INTERMEDIATE', 'intermediate'), 'intermediate')]");
    private final By advancedButton = By.xpath("//button[contains(@class,'level-btn')][contains(translate(., 'ADVANCED', 'advanced'), 'advanced')]");

    private final By overallHealthGoalCard = By.xpath("//label[contains(@class,'goal-card')][contains(translate(., 'HEALTH', 'health'), 'health')]");
    private final By buildMuscleGoalCard = By.xpath("//label[contains(@class,'goal-card')][contains(translate(., 'MUSCLE', 'muscle'), 'muscle')]");
    private final By loseWeightGoalCard = By.xpath("//label[contains(@class,'goal-card')][contains(translate(., 'WEIGHT', 'weight'), 'weight')]");
    private final By medicalControlGoalCard = By.xpath("//label[contains(@class,'goal-card')][contains(translate(., 'DIABETIC', 'diabetic'), 'diabetic')]");

    private final By submitAccountButton = By.className("btn-submit");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public void fillRegistrationForm(String name, String phone, String username, String password,
                                     String gender, String age, String height, String weight,
                                     String diet, String level, String goal) {
        type(nameField, name);
        type(phoneField, phone);
        type(usernameField, username);
        type(passwordField, password);
        type(ageField, age);
        type(heightField, height);
        type(weightField, weight);

        if (gender.equalsIgnoreCase("MALE")) { click(maleButton); } else { click(femaleButton); }
        if (diet.equalsIgnoreCase("VEG") || diet.equalsIgnoreCase("VEGETARIAN")) { click(vegetarianButton); } else { click(nonVegButton); }

        switch (level.toUpperCase()) {
            case "BEGINNER" -> click(beginnerButton);
            case "INTERMEDIATE" -> click(intermediateButton);
            case "ADVANCED" -> click(advancedButton);
        }

        switch (goal.toUpperCase()) {
            case "OVERALL_HEALTH", "OVERALL HEALTH" -> click(overallHealthGoalCard);
            case "BUILD_MUSCLE", "BUILD MUSCLE" -> click(buildMuscleGoalCard);
            case "LOSE_WEIGHT", "LOSE WEIGHT" -> click(loseWeightGoalCard);
            case "DIABETIC_CONTROL", "DIABETIC/BP CONTROL" -> click(medicalControlGoalCard);
        }
    }

    public void clickRegister() { click(submitAccountButton); }
}