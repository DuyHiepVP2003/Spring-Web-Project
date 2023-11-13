package com.project.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class ShoppingCart {
    private List<CartItem> cartItems;
    public ShoppingCart(){
        this.cartItems = new ArrayList<>();
    }

    public void addCartItem(CartItem cartItem){
        for (CartItem item: cartItems){
            if (item.getProduct().getId().equals(cartItem.getProduct().getId())){
                item.setQuantity(item.getQuantity()+1);
                return;
            }
        }
        cartItems.add(cartItem);
    }

    public void removeCartItem(Long productId){
        for (CartItem item : cartItems) {
            if (item.getProduct().getId().equals(productId)) {
                if (item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1);
                } else {
                    cartItems.remove(item);
                }
                return;
            }
        }
    }

    public void removeCartButton(Long productId){
        for (CartItem cartItem: cartItems){
            if (cartItem.getProduct().getId().equals(productId)){
                cartItems.remove(cartItem);
                return;
            }
        }
    }
    public String calculateSubTotal(){
        Long total = 0l;
        for (CartItem cartItem:cartItems){
            Long price = Long.parseLong(cartItem.getProduct().getPrice());
            total += price*cartItem.getQuantity();
        }
        return total.toString();
    }

    public String calculateTotal(){
        double total = 0l;
        for (CartItem cartItem:cartItems) {
            Long price = Long.parseLong(cartItem.getProduct().getPrice());
            total += price * cartItem.getQuantity();
        }
        double TaxPercentage = 0.05;
        double discountAmount = total * TaxPercentage;

        total += discountAmount;
        total += 30000;
        return String.valueOf(total);
    }
}
