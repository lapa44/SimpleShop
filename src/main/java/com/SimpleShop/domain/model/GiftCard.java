package com.SimpleShop.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Tolerate;

@Getter
@ToString
@Entity
public class GiftCard {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(unique = true)
  private Long id;
  private String name;
  private BigDecimal value;

  @Builder
  public GiftCard(String name, BigDecimal value) {
    this.name = name;
    this.value = value;
  }

  @Tolerate
  public GiftCard() {}

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GiftCard giftCard = (GiftCard) o;
    return Objects.equals(id, giftCard.id) &&
        Objects.equals(name, giftCard.name) &&
        value.compareTo(giftCard.value) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, value);
  }
}
