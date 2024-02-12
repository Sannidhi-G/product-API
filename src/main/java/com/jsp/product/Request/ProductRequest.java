package com.jsp.product.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
	 private String name;
     private String description;
     private double price;
     private int quantityAvailable;

     // Getters and setters

     @Override
     public String toString() {
         return "ProductRequest{" +
                 "name='" + name + '\'' +
                 ", description='" + description + '\'' +
                 ", price=" + price +
                 ", quantityAvailable=" + quantityAvailable +
                 '}';
     }

}
