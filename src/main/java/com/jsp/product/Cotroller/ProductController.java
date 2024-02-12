package com.jsp.product.Cotroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.product.Request.ProductRequest;
import com.jsp.product.entity.Product;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	private final Map<Long, Product> productMap = new HashMap<>();
    private long productIdCounter = 1;
    
    @PostMapping
    @Cacheable("products")
    public String createProduct(@RequestBody ProductRequest productRequest) {
        long productId = productIdCounter++;
        Product product = new Product(productId, productRequest.getName(), productRequest.getDescription(),
                productRequest.getPrice(), productRequest.getQuantityAvailable());
        productMap.put(productId, product);
        return "Product created successfully. Product details: " + product;
    }

    @GetMapping("/{productId}")
    @Cacheable(value="products",key="#productId")
    public Object readProduct(@PathVariable long productId) {
        Product product = productMap.get(productId);
        if (product != null) {
            return product;
        } else {
            return "Product not found for ID: " + productId;
        }
    }

    @PutMapping("/{productId}")
    @Cacheable("products")
    public String updateProduct(@PathVariable long productId, @RequestBody ProductRequest productRequest) {
        if (productMap.containsKey(productId)) {
            Product updatedProduct = new Product(productId, productRequest.getName(), productRequest.getDescription(),
                    productRequest.getPrice(), productRequest.getQuantityAvailable());
            productMap.put(productId, updatedProduct);
            return "Product updated successfully. Updated product details: " + updatedProduct;
        } else {
            return "Product not found for ID: " + productId;
        }
    }

    @DeleteMapping("/{productId}")
    @CacheEvict(value = "products" , key = "#productId")
    public String deleteProduct(@PathVariable long productId) {
        if (productMap.containsKey(productId)) {
            productMap.remove(productId);
            return "Product deleted successfully.";
        } else {
            return "Product not found for ID: " + productId;
        }
    }

    @PostMapping("/{productId}/apply-discount")
    public Object applyDiscount(@PathVariable long productId, @RequestParam double discountPercentage) {
        return applyModification(productId, discountPercentage, "Discount");
    }

    @PostMapping("/{productId}/apply-tax")
    public Object applyTax(@PathVariable long productId, @RequestParam double taxRate) {
        return applyModification(productId, taxRate, "Tax");
    }

    private Object applyModification(long productId, double modifierValue, String modifierType) {
        Product product = productMap.get(productId);
        if (product != null) {
            double modifiedPrice;

            if ("Discount".equals(modifierType)) {
                modifiedPrice = product.getPrice() - (product.getPrice() * modifierValue / 100);
            } else if ("Tax".equals(modifierType)) {
                modifiedPrice = product.getPrice() + (product.getPrice() * modifierValue / 100);
            } else {
                return "Invalid modifier type. Use 'Discount' or 'Tax'.";
            }

            product.setPrice(modifiedPrice);
            return "Modification applied successfully. Updated product details: " + product;
        } else {
            return "Product not found for ID: " + productId;
        }
    }
}
