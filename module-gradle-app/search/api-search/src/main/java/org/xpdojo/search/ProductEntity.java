package org.xpdojo.search;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;

@Table(name = "product")
@Entity
@ToString
@Getter
@Setter
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigInteger price;

    public ProductEntity() {
    }

    @Builder
    public ProductEntity(int id, String name, BigInteger price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
