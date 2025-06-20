package stellarburgers.user;

import stellarburgers.base.BaseTest;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import stellarburgers.model.User;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
@DisplayName("Создание пользователя")
public class CreateUserTest extends BaseTest {

    private User testUser;

    @Test
    @DisplayName("Успешное создание уникального пользователя")
    @Description("Проверяем, что новый пользователь успешно создаётся")
    public void createUniqueUserTest() {
        testUser = generateRandomUser();
        Response response = userClient.register(testUser);
        assertEquals(SC_OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Создание пользователя с дублирующей почтой")
    @Description("Проверяем, что при попытке создать пользователя с существующей почтой возвращается ошибка 403")
    public void createDuplicateUserTest() {
        testUser = generateRandomUser();
        Response firstResponse = userClient.register(testUser);
        assertEquals(SC_OK, firstResponse.getStatusCode());

        Response secondResponse = userClient.register(testUser);
        assertEquals(SC_FORBIDDEN, secondResponse.getStatusCode());
    }

    @Test
    @DisplayName("Создание пользователя без обязательного поля")
    @Description("Проверяем, что если не указать обязательное поле (email), возвращается ошибка 400")
    public void createUserWithoutRequiredFieldTest() {
        testUser = new User(null, "password", "Name");
        Response response = userClient.register(testUser);
        assertEquals(SC_BAD_REQUEST, response.getStatusCode());
    }
}