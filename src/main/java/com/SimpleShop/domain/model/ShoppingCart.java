package com.SimpleShop.domain.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Tolerate;

@Getter
@ToString
@Entity
public class ShoppingCart {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true)
  private Long id;
  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @NotNull
  private Client client;
  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn
  @NotNull
  private List<Item> products;
  private BigDecimal totalPrice;
  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Delivery delivery;
  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private GiftCard giftCard;

  public ShoppingCart(Client client, List<Item> products, Delivery delivery, GiftCard giftCard) {
    this.client = client;
    this.products = products;
    this.delivery = delivery;
    this.giftCard = giftCard;
    this.totalPrice = products.stream()
        .map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
        .reduce(BigDecimal.ZERO, BigDecimal::add)
        .subtract(delivery.getPrice()).subtract(giftCard.getValue());
  }

  @Builder
  public ShoppingCart(Client client, List<Item> products) {
    this.client = client;
    this.products = products;
    this.totalPrice = products.stream()
        .map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  @Tolerate
  public ShoppingCart() {
    products = new ArrayList<>();
    totalPrice = new BigDecimal(BigInteger.ZERO);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ShoppingCart that = (ShoppingCart) o;
    return Objects.equals(id, that.id)
        && Objects.equals(client, that.client)
        && products.size() == that.products.size()
        && products.containsAll(that.products)
        && totalPrice.compareTo(that.totalPrice) == 0
        && Objects.equals(delivery, that.delivery)
        && Objects.equals(giftCard, that.giftCard);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, client, products, totalPrice);
  }

  public void addToCart(Item item) {
    this.products.add(item);
    this.totalPrice = this.totalPrice.add(item.getPrice().multiply(new BigDecimal(item.getQuantity())));
  }

  public void addDelivery(Delivery delivery) {
    this.delivery = delivery;
    this.totalPrice = this.totalPrice.add(delivery.getPrice());
  }

  public void addGiftCard(GiftCard giftCard) {
    this.giftCard = giftCard;
    this.totalPrice = this.totalPrice.subtract(giftCard.getValue());
  }

}
