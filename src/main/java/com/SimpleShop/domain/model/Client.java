package com.SimpleShop.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Tolerate;

@Getter
@EqualsAndHashCode
@ToString
@Entity
public class Client {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @NotNull
  private String name;
  @NotNull
  private String address;

  @Tolerate
  public Client() {}

  @Builder
  public Client(String name, String address) {
    this.name = name;
    this.address = address;
  }

}

