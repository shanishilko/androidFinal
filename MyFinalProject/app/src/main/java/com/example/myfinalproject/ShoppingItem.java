package com.example.myfinalproject;

public class ShoppingItem {
    String Name;
    int Quantity;

    public ShoppingItem(String name, int quantity) {
        this.Name = name;
        this.Quantity = quantity;
    }

    public int getQuantity() {
        return Quantity;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public void setQuantity(int quantity) {
        this.Quantity = quantity;
    }
}
