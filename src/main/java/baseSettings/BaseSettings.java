package baseSettings;

import io.restassured.RestAssured;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.BeforeSuite;

import java.util.Properties;

public class BaseSettings {

    private static Properties properties;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(ITestContext context) {
        setTestProperties();
        setEnvironmentForTest();
    }

    protected void setTestProperties() {
        properties = new Properties();
        properties.put("baseURI", "https://gorest.co.in/public/");
        properties.put("usersPath", "v1/users");
        properties.put("postsPath", "v1/posts");
        properties.put("commentsPath", "v1/comments");
        properties.put("responseTimeLimit", "5000");
        properties.put("accessToken", "128badd84950d09c490d0fda125499a782d59b6ca80af1a162c8f4b7712f9822");
        System.getenv().forEach((key, value) -> properties.setProperty(key, value));
    }

    public static void setEnvironmentForTest() {
        RestAssured.reset();
        RestAssured.baseURI = properties.getProperty("baseURI");
    }

    public static String getAccessToken() {
        return "Bearer " + properties.getProperty("accessToken");
    }

    public static String getUsersPath() {
        return properties.getProperty("usersPath");
    }

    public static String getPostsPath() {
        return properties.getProperty("postsPath");
    }

    public static String getCommentsPath() {
        return properties.getProperty("commentsPath");
    }

    public static Long getResponseTimeLimit() {
        return Long.parseLong(properties.getProperty("responseTimeLimit"));
    }

}
