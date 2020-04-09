package com.SimpleShop.database;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import com.SimpleShop.domain.model.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateDatabase implements Database {
  @Autowired
  private ShoppingCartRepository repository;

  public HibernateDatabase(ShoppingCartRepository repository) {
    this.repository = repository;
  }

  @Override
  public ShoppingCart saveShoppingCart(ShoppingCart shoppingCart) {
    return repository.save(shoppingCart);
  }

  @Override
  public ShoppingCart getShoppingCartById(Long id) throws DatabaseException {
    return repository.findById(id)
        .orElseThrow(() -> new DatabaseException(
            new NoSuchElementException("Couldn't find invoice with given id: " + id)));
  }

  @Override
  public List<ShoppingCart> getShoppingCarts() {
    List<ShoppingCart> cartList = new ArrayList<>();
    repository.findAll()
        .forEach(cartList::add);
    return cartList;
  }

  @Override
  public ShoppingCart removeShoppingCartById(Long id) throws DatabaseException {
    ShoppingCart cart = repository.findById(id)
        .orElseThrow(() -> new DatabaseException(
            new NoSuchElementException("Couldn't find invoice with given id: " + id)));
    repository.deleteById(id);
    return cart;
  }
}
