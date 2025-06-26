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
        String message = secondResponse.jsonPath().getString("message");
        assertEquals("User already exists", message);
    }

    @Test
    @DisplayName("Создание пользователя без email")
    @Description("Проверяем, что если не указать email, возвращается ошибка 403")
    public void createUserWithoutEmailTest() {
        testUser = new User(null, "password", "Name");
        Response response = userClient.register(testUser);
        assertEquals(SC_FORBIDDEN, response.getStatusCode());
        String message = response.jsonPath().getString("message");
        assertEquals("Email, password and name are required fields", message);
    }

    @Test
    @DisplayName("Создание пользователя без имени")
    @Description("Проверяем, что если не указано имя, возвращается ошибка 403")
    public void createUserWithoutNameTest() {
        testUser = new User("email@mail.com", "password", null);
        Response response = userClient.register(testUser);
        assertEquals(SC_FORBIDDEN, response.getStatusCode());
        String message = response.jsonPath().getString("message");
        assertEquals("Email, password and name are required fields", message);
    }

    @Test
    @DisplayName("Создание пользователя без пароля")
    @Description("Проверяем, что если не указан пароль, возвращается ошибка 403")
    public void createUserWithoutPasswordTest() {
        testUser = new User("email@mail.com", null, "Name");
        Response response = userClient.register(testUser);
        assertEquals(SC_FORBIDDEN, response.getStatusCode());
        String message = response.jsonPath().getString("message");
        assertEquals("Email, password and name are required fields", message);
    }
}