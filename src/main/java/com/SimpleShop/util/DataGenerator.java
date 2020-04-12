package com.SimpleShop.util;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collections;
import com.SimpleShop.domain.model.Client;
import com.SimpleShop.domain.model.Delivery;
import com.SimpleShop.domain.model.GiftCard;
import com.SimpleShop.domain.model.Item;
import com.SimpleShop.domain.model.ShoppingCart;
import com.github.javafaker.Faker;

public class DataGenerator {

  private static Faker FAKER = new Faker();
  private static String[] GIFT_CARD_NAMES = {"Birthday Card", "10 Year Anniversary Card"};

  public static ShoppingCart.ShoppingCartBuilder randomShoppingCart() {
    return ShoppingCart.builder()
        .client(randomClient().build())
        .products(Collections.singletonList(randomItem().build()));
  }

  public static Item.ItemBuilder randomItem() {
    return Item.builder()
        .name(FAKER.commerce().productName())
        .description(FAKER.commerce().material())
        .price(new BigDecimal(FAKER.number().numberBetween(1L, 100L)))
        .quantity(FAKER.number().randomDigitNotZero());
  }

  public static Client.ClientBuilder randomClient() {
    return Client.builder()
        .name(FAKER.name().fullName())
        .address(FAKER.address().fullAddress());
  }

  public static Delivery.DeliveryBuilder randomDelivery() {
    return Delivery.builder()
        .name(FAKER.company().name())
        .price(new BigDecimal(FAKER.number().numberBetween(5L, 20L)))
        .estimatedShippingTime(Duration.ofDays(FAKER.number().numberBetween(1L, 3L)));
  }

  public static GiftCard.GiftCardBuilder randomGiftCard() {
    return GiftCard.builder()
        .name(FAKER.options().option(GIFT_CARD_NAMES))
        .value(new BigDecimal(FAKER.number().numberBetween(5L, 100L)));
  }
}
