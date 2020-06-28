package com.example.food.ui.home;

public class Item {
    String ItemName,ItemPrice,ItemImgUrl;

    public Item(String itemName, String itemPrice, String itemImgUrl) {
        ItemName = itemName;
        ItemPrice = itemPrice;
        ItemImgUrl = itemImgUrl;
    }

    public String getItemName() {
        return ItemName;
    }

    public String getItemPrice() {
        return ItemPrice;
    }

    public String getItemImgUrl() {
        return ItemImgUrl;
    }
}
