package org.xpdojo.search.dto;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {

    @Test
    void test_product() {
        Product actual = new Product("1", "k8s", BigInteger.valueOf(111L));
        String expected = "k8s";
        assertEquals(expected, actual.getName());
    }
}
