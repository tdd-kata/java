package org.xpdojo.search;

import lombok.Builder;

import java.math.BigInteger;

public class ProductDto {

    private int id;
    private String name;
    private BigInteger price;

    @Builder
    public ProductDto(int id, String name, BigInteger price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigInteger getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
