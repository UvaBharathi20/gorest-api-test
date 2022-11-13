package testScenarios.users;

import baseSettings.BaseSettings;
import io.restassured.path.json.JsonPath;
import net.minidev.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.UserUtils;

public class UpdateUsers extends BaseSettings {

    @Test
    public void userNameShouldBeUpdated() {
        String uniqueUserTag = UserUtils.generateUniqueUserTag();
        int userId = UserUtils.createUserAndReturnUserId(uniqueUserTag, "female", uniqueUserTag + "@gmail.com", "active");
        String updateUniqueUserTag = UserUtils.generateUniqueUserTag();
        UserUtils.updateUser(userId, updateUniqueUserTag, "female", uniqueUserTag + "@gmail.com", "active");
        JsonPath createdUserInfo = UserUtils.getUserById(userId);

        Assert.assertEquals(createdUserInfo.get("data.name"), updateUniqueUserTag);
        Assert.assertEquals(createdUserInfo.get("data.email"), uniqueUserTag + "@gmail.com");
        Assert.assertEquals(createdUserInfo.get("data.gender"), "female");
        Assert.assertEquals(createdUserInfo.get("data.status"), "active");
    }

    @Test
    public void userGenderShouldBeUpdated() {
        String uniqueUserTag = UserUtils.generateUniqueUserTag();
        int userId = UserUtils.createUserAndReturnUserId(uniqueUserTag, "female", uniqueUserTag + "@gmail.com", "active");
        UserUtils.updateUser(userId, uniqueUserTag, "male", uniqueUserTag + "@gmail.com", "active");
        JsonPath createdUserInfo = UserUtils.getUserById(userId);

        Assert.assertEquals(createdUserInfo.get("data.name"), uniqueUserTag);
        Assert.assertEquals(createdUserInfo.get("data.email"), uniqueUserTag + "@gmail.com");
        Assert.assertEquals(createdUserInfo.get("data.gender"), "male");
        Assert.assertEquals(createdUserInfo.get("data.status"), "active");
    }

    @Test
    public void userEmailShouldBeUpdated() {
        String uniqueUserTag = UserUtils.generateUniqueUserTag();
        int userId = UserUtils.createUserAndReturnUserId(uniqueUserTag, "female", uniqueUserTag + "@gmail.com", "active");
        String updateUniqueUserTag = UserUtils.generateUniqueUserTag();
        UserUtils.updateUser(userId, uniqueUserTag, "female", updateUniqueUserTag + "@gmail.com", "active");
        JsonPath createdUserInfo = UserUtils.getUserById(userId);

        Assert.assertEquals(createdUserInfo.get("data.name"), uniqueUserTag);
        Assert.assertEquals(createdUserInfo.get("data.email"), updateUniqueUserTag + "@gmail.com");
        Assert.assertEquals(createdUserInfo.get("data.gender"), "female");
        Assert.assertEquals(createdUserInfo.get("data.status"), "active");
    }

    @Test
    public void userStatusShouldBeUpdated() {
        String uniqueUserTag = UserUtils.generateUniqueUserTag();
        int userId = UserUtils.createUserAndReturnUserId(uniqueUserTag, "female", uniqueUserTag + "@gmail.com", "active");
        UserUtils.updateUser(userId, uniqueUserTag, "female", uniqueUserTag + "@gmail.com", "inactive");
        JsonPath createdUserInfo = UserUtils.getUserById(userId);

        Assert.assertEquals(createdUserInfo.get("data.name"), uniqueUserTag);
        Assert.assertEquals(createdUserInfo.get("data.email"), uniqueUserTag + "@gmail.com");
        Assert.assertEquals(createdUserInfo.get("data.gender"), "female");
        Assert.assertEquals(createdUserInfo.get("data.status"), "inactive");
    }

    @Test
    public void userShouldBeUpdatedWithoutUserName() {
        String uniqueUserTag = UserUtils.generateUniqueUserTag();
        int userId = UserUtils.createUserAndReturnUserId(uniqueUserTag, "female", uniqueUserTag + "@gmail.com", "active");
        JSONObject userInfo = new JSONObject();
        userInfo.put("gender", "male");
        userInfo.put("email", uniqueUserTag + "@gmail.com");
        userInfo.put("status", "inactive");

        Assert.assertEquals(UserUtils.updateUserAndReturnStatusCode(userId, userInfo), 200);
        JsonPath createdUserInfo = UserUtils.getUserById(userId);

        Assert.assertEquals(createdUserInfo.get("data.name"), uniqueUserTag);
        Assert.assertEquals(createdUserInfo.get("data.email"), uniqueUserTag + "@gmail.com");
        Assert.assertEquals(createdUserInfo.get("data.gender"), "male");
        Assert.assertEquals(createdUserInfo.get("data.status"), "inactive");
    }

    @Test
    public void userShouldNotBeUpdatedWithEmptyUserName() {
        String uniqueUserTag = UserUtils.generateUniqueUserTag();
        int userId = UserUtils.createUserAndReturnUserId(uniqueUserTag, "female", uniqueUserTag + "@gmail.com", "active");
        JSONObject userInfo = new JSONObject();
        userInfo.put("name", "");
        userInfo.put("gender", "female");
        userInfo.put("email", uniqueUserTag + "@gmail.com");
        userInfo.put("status", "active");

        Assert.assertEquals(UserUtils.updateUserAndReturnStatusCode(userId, userInfo), 422);
    }

    @Test
    public void userShouldBeUpdatedWithoutGender() {
        String uniqueUserTag = UserUtils.generateUniqueUserTag();
        int userId = UserUtils.createUserAndReturnUserId(uniqueUserTag, "female", uniqueUserTag + "@gmail.com", "active");
        JSONObject userInfo = new JSONObject();
        userInfo.put("name", uniqueUserTag);
        userInfo.put("email", uniqueUserTag + "@gmail.com");
        userInfo.put("status", "inactive");

        Assert.assertEquals(UserUtils.updateUserAndReturnStatusCode(userId, userInfo), 200);
        JsonPath createdUserInfo = UserUtils.getUserById(userId);

        Assert.assertEquals(createdUserInfo.get("data.name"), uniqueUserTag);
        Assert.assertEquals(createdUserInfo.get("data.email"), uniqueUserTag + "@gmail.com");
        Assert.assertEquals(createdUserInfo.get("data.gender"), "female");
        Assert.assertEquals(createdUserInfo.get("data.status"), "inactive");
    }

    @Test
    public void userShouldBeUpdatedWithoutEmail() {
        String uniqueUserTag = UserUtils.generateUniqueUserTag();
        int userId = UserUtils.createUserAndReturnUserId(uniqueUserTag, "female", uniqueUserTag + "@gmail.com", "active");
        JSONObject userInfo = new JSONObject();
        userInfo.put("name", uniqueUserTag);
        userInfo.put("gender", "male");
        userInfo.put("status", "inactive");

        Assert.assertEquals(UserUtils.updateUserAndReturnStatusCode(userId, userInfo), 200);
        JsonPath createdUserInfo = UserUtils.getUserById(userId);

        Assert.assertEquals(createdUserInfo.get("data.name"), uniqueUserTag);
        Assert.assertEquals(createdUserInfo.get("data.email"), uniqueUserTag + "@gmail.com");
        Assert.assertEquals(createdUserInfo.get("data.gender"), "male");
        Assert.assertEquals(createdUserInfo.get("data.status"), "inactive");
    }

    @Test
    public void userShouldBeUpdatedWithoutStatus() {
        String uniqueUserTag = UserUtils.generateUniqueUserTag();
        int userId = UserUtils.createUserAndReturnUserId(uniqueUserTag, "female", uniqueUserTag + "@gmail.com", "active");
        JSONObject userInfo = new JSONObject();
        userInfo.put("name", uniqueUserTag);
        userInfo.put("gender", "male");
        userInfo.put("email", uniqueUserTag + "@gmail.com");

        Assert.assertEquals(UserUtils.updateUserAndReturnStatusCode(userId, userInfo), 200);
        JsonPath createdUserInfo = UserUtils.getUserById(userId);

        Assert.assertEquals(createdUserInfo.get("data.name"), uniqueUserTag);
        Assert.assertEquals(createdUserInfo.get("data.email"), uniqueUserTag + "@gmail.com");
        Assert.assertEquals(createdUserInfo.get("data.gender"), "male");
        Assert.assertEquals(createdUserInfo.get("data.status"), "active");
    }

    @Test
    public void userShouldNotBeUpdatedWithExistingEmail() {
        String uniqueUserTag = UserUtils.generateUniqueUserTag();
        UserUtils.createUserAndReturnUserId(uniqueUserTag, "female", uniqueUserTag + "@gmail.com", "active");
        String secondUniqueUserTag = UserUtils.generateUniqueUserTag();
        int secondUserId = UserUtils.createUserAndReturnUserId(secondUniqueUserTag, "female", secondUniqueUserTag + "@gmail.com", "active");
        JSONObject userInfo = new JSONObject();
        userInfo.put("email", uniqueUserTag + "@gmail.com");

        Assert.assertEquals(UserUtils.updateUserAndReturnStatusCode(secondUserId, userInfo), 422);
    }

    //NOTE: email format tests can be populated. ex: user@, user@gmail, user @gmail.com etc.
    @Test
    public void userShouldNotBeUpdatedWithWrongEmailFormat() {
        String uniqueUserTag = UserUtils.generateUniqueUserTag();
        int userId = UserUtils.createUserAndReturnUserId(uniqueUserTag, "female", uniqueUserTag + "@gmail.com", "active");
        JSONObject userInfo = new JSONObject();
        userInfo.put("email", uniqueUserTag + "gmail.com");

        Assert.assertEquals(UserUtils.updateUserAndReturnStatusCode(userId, userInfo), 422);
    }

    @Test
    public void userShouldNotBeCreatedWithWrongGenderFormat() {
        String uniqueUserTag = UserUtils.generateUniqueUserTag();
        int userId = UserUtils.createUserAndReturnUserId(uniqueUserTag, "female", uniqueUserTag + "@gmail.com", "active");
        JSONObject userInfo = new JSONObject();
        userInfo.put("gender", "wrongGender");

        Assert.assertEquals(UserUtils.updateUserAndReturnStatusCode(userId, userInfo), 422);
    }

    @Test
    public void userShouldNotBeCreatedWithWrongStatusFormat() {
        String uniqueUserTag = UserUtils.generateUniqueUserTag();
        int userId = UserUtils.createUserAndReturnUserId(uniqueUserTag, "female", uniqueUserTag + "@gmail.com", "active");
        JSONObject userInfo = new JSONObject();
        userInfo.put("status", "wrongStatus");

        Assert.assertEquals(UserUtils.updateUserAndReturnStatusCode(userId, userInfo), 422);
    }
}
