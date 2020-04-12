package com.SimpleShop.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Tolerate;

@Getter
@ToString
@Entity
public class Delivery {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private BigDecimal price;
  private String name;
  private Duration estimatedShippingTime;

  @Builder
  public Delivery(BigDecimal price, String name, Duration estimatedShippingTime) {
    this.price = price;
    this.name = name;
    this.estimatedShippingTime = estimatedShippingTime;
  }

  @Tolerate
  public Delivery() {}

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Delivery delivery = (Delivery) o;
    return Objects.equals(id, delivery.id) &&
        price.compareTo(delivery.price) == 0 &&
        Objects.equals(name, delivery.name) &&
        Objects.equals(estimatedShippingTime, delivery.estimatedShippingTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, price, name, estimatedShippingTime);
  }
}
