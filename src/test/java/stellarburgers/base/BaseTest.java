package stellarburgers.base;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import stellarburgers.model.Order;
import stellarburgers.client.UserClient;
import stellarburgers.model.Credentials;
import stellarburgers.model.User;
import stellarburgers.constants.Endpoints;

import java.util.Collections;

public class BaseTest {
    protected final UserClient userClient = new UserClient();
    protected String accessToken;
    protected User user;
    protected  static final Faker faker = new Faker();

    @BeforeClass
    public static void setUpClass() {
        RestAssured.baseURI = Endpoints.BASE_URL;
    }

    @Before
    @Step("Подготовка тестовых данных")
    public void setUp() {
        user = generateRandomUser();
        userClient.register(user);
        Credentials credentials = new Credentials(user.getEmail(), user.getPassword());
        Response loginResponse = userClient.login(credentials);
        accessToken = loginResponse.jsonPath().getString("accessToken");
    }

    @Step("Генерация случайного пользователя")
    protected User generateRandomUser() {
        Faker faker = new Faker();
        return new User(
                faker.internet().emailAddress(),
                faker.internet().password(8, 12),
                faker.name().firstName()
        );
    }

    @Step("Генерация тестового заказа")
    protected Order generateOrder() {
        String validIngredientId = "61c0c5a71d1f82001bdaaa6d";
        return new Order(Collections.singletonList(validIngredientId));
    }

    @After
    @Step("Очистка тестовых данных")
    public void tearDown() {
        if (accessToken != null) {
            userClient.delete(accessToken);
        }
    }
}