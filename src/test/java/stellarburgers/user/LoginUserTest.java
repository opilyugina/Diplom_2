package stellarburgers.user;

import stellarburgers.base.BaseTest;
import org.junit.Test;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import stellarburgers.model.Credentials;
import io.restassured.response.Response;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

@DisplayName("Логин пользователя")
public class LoginUserTest extends BaseTest {

    @Test
    @DisplayName("Логин с валидными данными")
    @Description("Проверяем, что пользователь может войти с правильными email и паролем")
    public void loginWithValidCredentialsTest() {
        Credentials creds = new Credentials(user.getEmail(), user.getPassword());
        Response response = userClient.login(creds);
        assertEquals(SC_OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Логин с невалидными данными")
    @Description("Проверяем, что если указать несуществующий email и/или пароль, возвращается ошибка 401")
    public void loginWithInvalidCredentialsTest() {
        Credentials creds = new Credentials("wrong@mail.com", "wrongpass");
        Response response = userClient.login(creds);
        assertEquals(SC_UNAUTHORIZED, response.getStatusCode());
    }
}