package com.SimpleShop.controller;

import javax.validation.Valid;
import java.util.List;
import com.SimpleShop.database.Database;
import com.SimpleShop.util.DatabaseException;
import com.SimpleShop.domain.model.ShoppingCart;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShoppingCartController {

  private final Database database;

  public ShoppingCartController(Database database) {
    this.database = database;
  }

  @GetMapping("/carts")
  public List<ShoppingCart> getShoppingCarts() {
    return database.getShoppingCarts();
  }

  @GetMapping("/cart/{id}")
  public ShoppingCart getShoppingCartById(@PathVariable Long id) throws DatabaseException {
    return database.getShoppingCartById(id);
  }

  @DeleteMapping("/cart/{id}")
  public ShoppingCart deleteShoppingCartById(@PathVariable Long id) throws DatabaseException {
    return database.removeShoppingCartById(id);
  }

  @PostMapping("/carts")
  public ShoppingCart saveShoppingCart(@Valid @RequestBody ShoppingCart shoppingCart) {
    return database.saveShoppingCart(shoppingCart);
  }

}
