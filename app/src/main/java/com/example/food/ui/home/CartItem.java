package com.example.food.ui.home;

public class CartItem {
    String CartName,CartPrice,CartImgUrl,CartQuantity;

    public CartItem(String cartName, String cartPrice, String cartImgUrl, String cartQuantity) {
        CartName = cartName;
        CartPrice = cartPrice;
        CartImgUrl = cartImgUrl;
        CartQuantity = cartQuantity;
    }

    public String getCartName() {
        return CartName;
    }

    public String getCartPrice() {
        return CartPrice;
    }

    public String getCartImgUrl() {
        return CartImgUrl;
    }

    public String getCartQuantity() {
        return CartQuantity;
    }
}
