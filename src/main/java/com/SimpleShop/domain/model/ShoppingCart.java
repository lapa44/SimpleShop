package com.SimpleShop.domain.model;

import javax.persistence.CascadeType;
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

@Getter
@ToString
@Entity
public class ShoppingCart {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @NotNull
  private Client client;
  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn
  @NotNull
  private List<Item> products;
  private BigDecimal totalPrice;

  @Builder
  public ShoppingCart(Client client, List<Item> products) {
    this.client = client;
    this.products = products;
    this.totalPrice = products.stream()
        .map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

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
        && totalPrice.compareTo(that.totalPrice) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, client, products, totalPrice);
  }

  public ShoppingCart addToCart(Item item) {
    products.add(item);
    totalPrice = totalPrice.add(item.getPrice().multiply(new BigDecimal(item.getQuantity())));
    return this;
  }
}
