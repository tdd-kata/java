package org.xpdojo.search.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xpdojo.search.dto.Product;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class SearchVehicleService {

    public List<Product> listProducts() {
        List<Product> products = Arrays.asList(
                new Product("1", "git", BigInteger.valueOf(499_499)),
                new Product("2", "elastic", BigInteger.valueOf(123_456_789)),
                new Product("3", "java", BigInteger.valueOf(55_555_499))
        );
        for (Product product : products) {
            log.info("product >>> {}", product);
        }
        return products;
    }

}
