package com.SimpleShop.database;

import java.util.List;
import com.SimpleShop.domain.model.ShoppingCart;
import com.SimpleShop.util.DatabaseException;

public interface Database {

  ShoppingCart saveShoppingCart(ShoppingCart shoppingCart);
  ShoppingCart getShoppingCartById(Long id) throws DatabaseException;
  List<ShoppingCart> getShoppingCarts();
  ShoppingCart removeShoppingCartById(Long id) throws DatabaseException;

}
