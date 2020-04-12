package com.SimpleShop.database;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import com.SimpleShop.domain.model.Client;
import com.SimpleShop.domain.model.Delivery;
import com.SimpleShop.domain.model.GiftCard;
import com.SimpleShop.domain.model.Item;
import com.SimpleShop.domain.model.ShoppingCart;
import com.SimpleShop.util.DataGenerator;
import com.SimpleShop.util.DatabaseException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class DatabaseTest {

  abstract Database getDatabase();

  @Test
  void shouldSaveShoppingCart() {
    Database database = getDatabase();

    ShoppingCart shoppingCart = DataGenerator.randomShoppingCart().build();

    ShoppingCart savedShoppingCart = database.saveShoppingCart(shoppingCart);

    assertNotNull(savedShoppingCart);
    assertEquals(shoppingCart, savedShoppingCart);
    assertEquals(1, database.getShoppingCarts().size());
  }

  @Test
  void shouldGetShoppingCartById() throws DatabaseException {
    Database database = getDatabase();

    ShoppingCart shoppingCart = database.saveShoppingCart(DataGenerator.randomShoppingCart()
        .build());

    assertEquals(1, database.getShoppingCarts().size());
    assertEquals(shoppingCart, database.getShoppingCartById(shoppingCart.getId()));
  }

  @Test
  void shouldGetShoppingCarts() {
    Database database = getDatabase();

    ShoppingCart shoppingCart1 = DataGenerator.randomShoppingCart().build();

    ShoppingCart shoppingCart2 = DataGenerator.randomShoppingCart().build();

    database.saveShoppingCart(shoppingCart1);
    database.saveShoppingCart(shoppingCart2);

    assertEquals(2, database.getShoppingCarts().size());
    assertEquals(Arrays.asList(shoppingCart1, shoppingCart2), database.getShoppingCarts());
  }

  @Test
  void shouldRemoveShoppingCartById() throws DatabaseException {
    Database database = getDatabase();

    ShoppingCart shoppingCart = DataGenerator.randomShoppingCart().build();

    ShoppingCart savedShoppingCart = database.saveShoppingCart(shoppingCart);
    database.removeShoppingCartById(savedShoppingCart.getId());
    assertNotNull(savedShoppingCart);
    assertEquals(0, database.getShoppingCarts().size());
  }

  @Test
  void shouldAddShoppingCartWithDelivery() throws DatabaseException {
    Database database = getDatabase();

    ShoppingCart shoppingCart = ShoppingCart.builder()
        .client(Client.builder()
            .name("John")
            .address("England")
            .build())
        .products(Collections.singletonList(
            Item.builder()
                .name("Beer")
                .description("Tasty")
                .price(new BigDecimal("10.3"))
                .quantity(10)
                .build()))
        .build();
    shoppingCart.addDelivery(Delivery.builder()
        .name("Courier")
        .price(BigDecimal.TEN)
        .estimatedShippingTime(Duration.ofDays(1))
        .build());

    ShoppingCart savedShoppingCart = database.saveShoppingCart(shoppingCart);
    assertEquals(1, database.getShoppingCarts().size());
    assertNotNull(savedShoppingCart);
    assertEquals(0, new BigDecimal("113.0")
        .compareTo(database.getShoppingCartById(savedShoppingCart.getId()).getTotalPrice()));
  }

  @Test
  void shouldAddGiftCardToShoppingCart() throws DatabaseException {
    Database database = getDatabase();

    ShoppingCart savedShoppingCart = database.saveShoppingCart(
        ShoppingCart.builder()
            .client(Client.builder()
                .name("John")
                .address("England")
                .build())
            .products(Collections.singletonList(
                Item.builder()
                    .name("Beer")
                    .description("Tasty")
                    .price(new BigDecimal("10.3"))
                    .quantity(10)
                    .build()))
            .build());

    savedShoppingCart.addGiftCard(GiftCard.builder()
        .name("Birthday Card")
        .value(new BigDecimal("100"))
        .build());

    database.saveShoppingCart(savedShoppingCart);
    assertEquals(1, database.getShoppingCarts().size());
    assertNotNull(savedShoppingCart);
    assertEquals(0, new BigDecimal("3.0")
        .compareTo(database.getShoppingCartById(savedShoppingCart.getId()).getTotalPrice()));
  }

  @Test
  void shouldAddDeliveryAndGiftCardToShoppingCart() throws DatabaseException {
    Database database = getDatabase();

    ShoppingCart savedShoppingCart = database.saveShoppingCart(
        ShoppingCart.builder()
            .client(Client.builder()
                .name("John")
                .address("Poland")
                .build())
            .products(Collections.singletonList(
                Item.builder()
                    .name("Beer")
                    .description("Good")
                    .price(new BigDecimal("10.3"))
                    .quantity(10)
                    .build()))
            .build());

    savedShoppingCart.addDelivery(Delivery.builder()
        .name("Courier")
        .price(BigDecimal.TEN)
        .estimatedShippingTime(Duration.ofDays(1))
        .build());
    savedShoppingCart.addGiftCard(GiftCard.builder()
        .name("Birthday Card")
        .value(new BigDecimal("100"))
        .build());
    database.saveShoppingCart(savedShoppingCart);

    assertEquals(1, database.getShoppingCarts().size());
    assertNotNull(savedShoppingCart);
    assertEquals(0, new BigDecimal("13.0")
        .compareTo(database.getShoppingCartById(savedShoppingCart.getId()).getTotalPrice()));
  }
}
