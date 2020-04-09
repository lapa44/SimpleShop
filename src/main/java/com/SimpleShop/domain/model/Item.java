package com.SimpleShop.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Tolerate;

@Getter
@ToString
@Entity
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @NotNull
  private String name;
  @NotNull
  private String description;
  @NotNull
  private BigDecimal price;
  @NotNull
  private int quantity;

  @Tolerate
  public Item() {}

  @Builder
  public Item(String name, String description, BigDecimal price, int quantity) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.quantity = quantity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Item item = (Item) o;
    return quantity == item.quantity
        && Objects.equals(id, item.id)
        && Objects.equals(name, item.name)
        && Objects.equals(description, item.description)
        && price.compareTo(item.price) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, price, quantity);
  }
}