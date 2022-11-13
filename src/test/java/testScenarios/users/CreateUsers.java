package testScenarios.users;

import baseSettings.BaseSettings;
import io.restassured.path.json.JsonPath;
import net.minidev.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.UserUtils;

public class CreateUsers extends BaseSettings {

    @Test
    public void userShouldBeCreatedWithAllRequiredFieldsAsFemaleAndActive() {
        String uniqueUserTag = UserUtils.generateUniqueUserTag();
        int userId = UserUtils.createUserAndReturnUserId(uniqueUserTag, "female", uniqueUserTag + "@gmail.com", "active");
        JsonPath createdUserInfo = UserUtils.getUserById(userId);

        Assert.assertEquals(createdUserInfo.get("data.name"), uniqueUserTag);
        Assert.assertEquals(createdUserInfo.get("data.email"), uniqueUserTag + "@gmail.com");
        Assert.assertEquals(createdUserInfo.get("data.gender"), "female");
        Assert.assertEquals(createdUserInfo.get("data.status"), "active");
    }

    @Test
    public void userShouldBeCreatedWithAllRequiredFieldsAsMaleAndActive() {
        String uniqueUserTag = UserUtils.generateUniqueUserTag();
        int userId = UserUtils.createUserAndReturnUserId(uniqueUserTag, "male", uniqueUserTag + "@gmail.com", "active");
        JsonPath createdUserInfo = UserUtils.getUserById(userId);

        Assert.assertEquals(createdUserInfo.get("data.name"), uniqueUserTag);
        Assert.assertEquals(createdUserInfo.get("data.email"), uniqueUserTag + "@gmail.com");
        Assert.assertEquals(createdUserInfo.get("data.gender"), "male");
        Assert.assertEquals(createdUserInfo.get("data.status"), "active");
    }

    @Test
    public void userShouldBeCreatedWithAllRequiredFieldsAsMaleAndInactive() {
        String uniqueUserTag = UserUtils.generateUniqueUserTag();
        int userId = UserUtils.createUserAndReturnUserId(uniqueUserTag, "male", uniqueUserTag + "@gmail.com", "inactive");
        JsonPath createdUserInfo = UserUtils.getUserById(userId);

        Assert.assertEquals(createdUserInfo.get("data.name"), uniqueUserTag);
        Assert.assertEquals(createdUserInfo.get("data.email"), uniqueUserTag + "@gmail.com");
        Assert.assertEquals(createdUserInfo.get("data.gender"), "male");
        Assert.assertEquals(createdUserInfo.get("data.status"), "inactive");
    }

    @Test
    public void userShouldNotBeCreatedWithoutNameField() {
        String uniqueUserTag = UserUtils.generateUniqueUserTag();
        JSONObject userInfo = new JSONObject();
        userInfo.put("gender", "female");
        userInfo.put("email", uniqueUserTag + "@gmail.com");
        userInfo.put("status", "active");

        Assert.assertEquals(UserUtils.createUserAndReturnStatusCode(userInfo), 422);
    }

    @Test
    public void userShouldNotBeCreatedWithEmptyNameField() {
        String uniqueUserTag = UserUtils.generateUniqueUserTag();
        JSONObject userInfo = new JSONObject();
        userInfo.put("name", "");
        userInfo.put("gender", "female");
        userInfo.put("email", uniqueUserTag + "@gmail.com");
        userInfo.put("status", "active");

        Assert.assertEquals(UserUtils.createUserAndReturnStatusCode(userInfo), 422);
    }

    @Test
    public void userShouldNotBeCreatedWithoutGenderField() {
        String uniqueUserTag = UserUtils.generateUniqueUserTag();
        JSONObject userInfo = new JSONObject();
        userInfo.put("name", uniqueUserTag);
        userInfo.put("email", uniqueUserTag + "@gmail.com");
        userInfo.put("status", "active");

        Assert.assertEquals(UserUtils.createUserAndReturnStatusCode(userInfo), 422);
    }

    @Test
    public void userShouldNotBeCreatedWithoutEmailField() {
        String uniqueUserTag = UserUtils.generateUniqueUserTag();
        JSONObject userInfo = new JSONObject();
        userInfo.put("name", uniqueUserTag);
        userInfo.put("gender", "female");
        userInfo.put("status", "active");

        Assert.assertEquals(UserUtils.createUserAndReturnStatusCode(userInfo), 422);
    }

    @Test
    public void userShouldNotBeCreatedWithoutStatusField() {
        String uniqueUserTag = UserUtils.generateUniqueUserTag();
        JSONObject userInfo = new JSONObject();
        userInfo.put("name", uniqueUserTag);
        userInfo.put("email", uniqueUserTag + "@gmail.com");
        userInfo.put("gender", "female");

        Assert.assertEquals(UserUtils.createUserAndReturnStatusCode(userInfo), 422);
    }

    @Test
    public void userShouldNotBeCreatedWithExistingEmail() {
        String uniqueUserTag = UserUtils.generateUniqueUserTag();
        UserUtils.createUserAndReturnUserId(uniqueUserTag, "female", uniqueUserTag + "@gmail.com", "active");
        JSONObject userInfo = new JSONObject();
        userInfo.put("name", "duplicated");
        userInfo.put("gender", "male");
        userInfo.put("email", uniqueUserTag + "@gmail.com");
        userInfo.put("status", "active");

        Assert.assertEquals(UserUtils.createUserAndReturnStatusCode(userInfo), 422);
    }

    //NOTE: email format tests can be populated. ex: user@, user@gmail, user @gmail.com etc.
    @Test
    public void userShouldNotBeCreatedWithWrongEmailFormat() {
        String uniqueUserTag = UserUtils.generateUniqueUserTag();
        JSONObject userInfo = new JSONObject();
        userInfo.put("name", "wrongemail");
        userInfo.put("gender", "male");
        userInfo.put("email", uniqueUserTag + "gmail.com");
        userInfo.put("status", "active");

        Assert.assertEquals(UserUtils.createUserAndReturnStatusCode(userInfo), 422);
    }

    @Test
    public void userShouldNotBeCreatedWithWrongGenderFormat() {
        String uniqueUserTag = UserUtils.generateUniqueUserTag();
        JSONObject userInfo = new JSONObject();
        userInfo.put("name", "wrongGender");
        userInfo.put("gender", "wrongGender");
        userInfo.put("email", uniqueUserTag + "@gmail.com");
        userInfo.put("status", "active");

        Assert.assertEquals(UserUtils.createUserAndReturnStatusCode(userInfo), 422);
    }

    @Test
    public void userShouldNotBeCreatedWithWrongStatusFormat() {
        String uniqueUserTag = UserUtils.generateUniqueUserTag();
        JSONObject userInfo = new JSONObject();
        userInfo.put("name", "wrongStatus");
        userInfo.put("gender", "male");
        userInfo.put("email", uniqueUserTag + "@gmail.com");
        userInfo.put("status", "wrongStatus");

        Assert.assertEquals(UserUtils.createUserAndReturnStatusCode(userInfo), 422);
    }
}
