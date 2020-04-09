package com.SimpleShop.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class HibernateDatabaseTest extends DatabaseTest{

  @Autowired
  private ShoppingCartRepository repository;

  @Override
  Database getDatabase() {
    return new HibernateDatabase(repository);
  }

  @BeforeEach
  void clearDatabase() {
    repository.deleteAll();
  }
}