package com.jsp.product.entity;

import java.util.List;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Builder
@NoArgsConstructor
@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long productId;
	private String name;
	private String description;
	private double price;
	private int quantityAvaible;
	
	public Product(long productId, String name, String description, double price, int quantityAvaible) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantityAvaible = quantityAvaible;
    }
	
	@Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantityAvailable=" + quantityAvaible +
                '}';
    }

}
