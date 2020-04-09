package com.SimpleShop.database;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import com.SimpleShop.domain.model.Client;
import com.SimpleShop.domain.model.Item;
import com.SimpleShop.domain.model.ShoppingCart;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class DatabaseTest {

  abstract Database getDatabase();

  @Test
  void shouldSaveShoppingCart() {
    Database database = getDatabase();

    ShoppingCart shoppingCart = ShoppingCart.builder()
        .client(Client.builder()
            .name("Jan")
            .address("Polska")
            .build())
        .products(Collections.singletonList(
            Item.builder()
                .name("piwo")
                .description("dobre")
                .price(new BigDecimal("10.3"))
                .quantity(10)
                .build()))
        .build();

    ShoppingCart savedShoppingCart = database.saveShoppingCart(shoppingCart);
    assertNotNull(savedShoppingCart);
    assertEquals(shoppingCart, savedShoppingCart);
    assertEquals(1, database.getShoppingCarts().size());
  }

  @Test
  void shouldGetShoppingCartById() throws DatabaseException {
    Database database = getDatabase();

    ShoppingCart shoppingCart = database.saveShoppingCart(ShoppingCart.builder()
        .client(Client.builder()
            .name("Jan")
            .address("Polska")
            .build())
        .products(Collections.singletonList(
            Item.builder()
                .name("piwo")
                .description("dobre")
                .price(new BigDecimal("10.3"))
                .quantity(10)
                .build()))
        .build());

    assertEquals(1, database.getShoppingCarts().size());
    assertEquals(shoppingCart, database.getShoppingCartById(shoppingCart.getId()));
  }

  @Test
  void shouldGetShoppingCarts() {
    Database database = getDatabase();

    ShoppingCart shoppingCart1 = ShoppingCart.builder()
        .client(Client.builder()
            .name("Jan")
            .address("Polska")
            .build())
        .products(Collections.singletonList(
            Item.builder()
                .name("piwo")
                .description("dobre")
                .price(new BigDecimal("10.3"))
                .quantity(10)
                .build()))
        .build();

    ShoppingCart shoppingCart2 = ShoppingCart.builder()
        .client(Client.builder()
            .name("Kazimierz")
            .address("USA")
            .build())
        .products(Collections.singletonList(
            Item.builder()
                .name("Cola")
                .description("Pyszna")
                .price(new BigDecimal("2.0"))
                .quantity(10)
                .build()))
        .build();

    database.saveShoppingCart(shoppingCart1);
    database.saveShoppingCart(shoppingCart2);

    assertEquals(2, database.getShoppingCarts().size());
    assertEquals(Arrays.asList(shoppingCart1, shoppingCart2), database.getShoppingCarts());
  }

  @Test
  void shouldRemoveShoppingCartById() throws DatabaseException {
    Database database = getDatabase();

    ShoppingCart shoppingCart = ShoppingCart.builder()
        .client(Client.builder()
            .name("Jan")
            .address("Polska")
            .build())
        .products(Collections.singletonList(
            Item.builder()
                .name("piwo")
                .description("dobre")
                .price(new BigDecimal("10.3"))
                .quantity(10)
                .build()))
        .build();

    ShoppingCart savedShoppingCart = database.saveShoppingCart(shoppingCart);
    database.removeShoppingCartById(savedShoppingCart.getId());
    assertNotNull(savedShoppingCart);
    assertEquals(0, database.getShoppingCarts().size());
  }

}