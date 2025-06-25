package stellarburgers.order;

import stellarburgers.base.BaseTest;
import io.restassured.response.Response;
import org.junit.Test;
import stellarburgers.model.Order;
import stellarburgers.client.OrderClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;

import java.util.Collections;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

@DisplayName("Создание заказа")
public class CreateOrderTest extends BaseTest {
    private final OrderClient orderClient = new OrderClient();

    @Test
    @DisplayName("Создание заказа с авторизацией")
    @Description("Проверка успешного создания заказа авторизованным пользователем")
    public void createOrderWithAuthShouldBeSuccessfulTest() {
        Order order = generateOrder();
        Response response = orderClient.create(order, accessToken);
        assertEquals(SC_OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    @Description("Проверка создания заказа без токена авторизации")
    public void createOrderWithoutAuthShouldBeSuccessfulTest() {
        Order order = generateOrder();
        Response response = orderClient.create(order, "");
        assertEquals(SC_OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    @Description("Проверка ошибки при создании заказа без ингредиентов")
    public void createOrderWithoutIngredientsShouldFailTest() {
        Order order = new Order(Collections.emptyList());
        Response response = orderClient.create(order, accessToken);
        assertEquals(SC_BAD_REQUEST, response.getStatusCode());
        String message = response.jsonPath().getString("message");
        assertEquals("Ingredient ids must be provided", message);
    }

    @Test
    @DisplayName("Создание заказа с невалидным хешем ингредиента")
    @Description("Проверка ошибки при создании заказа с невалидным ингредиентом")
    public void createOrderWithInvalidIngredientHashShouldFailTest() {
        Order order = new Order(Collections.singletonList("invalid_hash"));
        Response response = orderClient.create(order, accessToken);
        assertEquals(SC_INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}