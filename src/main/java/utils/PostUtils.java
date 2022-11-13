package utils;

import baseSettings.BaseSettings;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import net.minidev.json.JSONObject;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import utils.enums.MediaType;

public final class PostUtils extends BaseSettings {

    public static JsonPath getAllPosts() {
        return RestAssured
                .given()
                .header("Authorization", getAccessToken())
                .contentType(MediaType.JSON)
                .get(getPostsPath())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .time(Matchers.lessThan(getResponseTimeLimit()))
                .extract().jsonPath();
    }

    public static JsonPath getAllPostsByUserId(int userId) {
        return RestAssured
                .given()
                .header("Authorization", getAccessToken())
                .contentType(MediaType.JSON)
                .get(getUsersPath() + "/" + userId + "/posts")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .time(Matchers.lessThan(getResponseTimeLimit()))
                .extract().jsonPath();
    }

    public static int createPostAndReturnPostId(int userId, String title, String body) {
        JSONObject postInfo = new JSONObject();
        postInfo.put("user_id", userId);
        postInfo.put("title", title);
        postInfo.put("body", body);
        return RestAssured
                .given()
                .header("Authorization", getAccessToken())
                .body(postInfo.toJSONString())
                .contentType(MediaType.JSON)
                .post(getPostsPath())
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .and()
                .time(Matchers.lessThan(getResponseTimeLimit()))
                .extract().jsonPath().get("data.id");
    }

    public static int createPostAndReturnStatusCode(JSONObject postInfo) {
        return RestAssured
                .given()
                .header("Authorization", getAccessToken())
                .body(postInfo.toJSONString())
                .contentType(MediaType.JSON)
                .post(getPostsPath())
                .then()
                .time(Matchers.lessThan(getResponseTimeLimit()))
                .extract().statusCode();
    }

}
