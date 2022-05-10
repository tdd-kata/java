package com.markruler.batch.reader;

import com.markruler.batch.model.Product;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import java.math.BigDecimal;

public class ProductCsvFieldSetMapper implements FieldSetMapper<Product> {

    @Override
    public Product mapFieldSet(FieldSet fieldSet) {
        // 순서가 같아야 한다.
        // return new Product(id, name, description, price, unit);
        return Product.builder()
                .id(Long.valueOf(fieldSet.readString("id")))
                .name(fieldSet.readString("name"))
                .description(fieldSet.readString("description"))
                .price(new BigDecimal(fieldSet.readString("price")))
                .unit(Integer.valueOf(fieldSet.readString("unit")))
                .build();
    }
}
