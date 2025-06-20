package stellarburgers.user;

import stellarburgers.base.BaseTest;
import org.junit.Test;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import stellarburgers.client.UserClient;
import stellarburgers.model.User;
import io.restassured.response.Response;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

@DisplayName("Изменение данных пользователя")
public class UpdateUserTest extends BaseTest {
    private final UserClient userClient = new UserClient();

    @Test
    @DisplayName("Изменение данных пользователя с авторизацией")
    @Description("Проверяем, что авторизованный пользователь может изменить любые свои данные")
    public void updateUserWithAuthTest() {
        User updatedUser = new User("new@mail.com", "newpass", "NewName");
        Response response = userClient.updateUser(accessToken, updatedUser);
        assertEquals(SC_OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Изменение данных пользователя без авторизации")
    @Description("Проверяем, что неавторизованный пользователь не может изменить данные и получает ошибку 401")
    public void updateUserWithoutAuthTest() {
        User updatedUser = new User("new@mail.com", "newpass", "NewName");
        Response response = userClient.updateUser("", updatedUser);
        assertEquals(SC_UNAUTHORIZED, response.getStatusCode());
    }
}