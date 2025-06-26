package stellarburgers.order;

import stellarburgers.base.BaseTest;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import stellarburgers.client.OrderClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
@DisplayName("Получение заказов пользователя")
public class GetUserOrdersTest extends BaseTest {

    private final OrderClient orderClient = new OrderClient();

    @Test
    @DisplayName("Получение заказов пользователя с авторизацией")
    @Description("Проверяем, что авторизованный пользователь может получить свои заказы")
    public void getUserOrdersWithAuthTest() {
        Response response = orderClient.getAll(accessToken);
        assertEquals(SC_OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Получение заказов без авторизации")
    @Description("Проверяем, что получение заказов без токена авторизации возвращает 401")
    public void getUserOrdersWithoutAuthTest() {
        Response response = orderClient.getAll("");
        assertEquals(SC_UNAUTHORIZED, response.getStatusCode());
        String message = response.jsonPath().getString("message");
        assertEquals("You should be authorised", message);
    }
}