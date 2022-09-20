package org.xpdojo.demo.api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xpdojo.aggregation.application.CountOptionsService;
import org.xpdojo.aggregation.dto.Option;
import org.xpdojo.search.ProductDto;
import org.xpdojo.search.SearchVehicleService;

import java.math.BigInteger;
import java.util.List;

@RestController
public class DemoRestController {

    private final SearchVehicleService searchVehicleService;
    private final CountOptionsService countOptionsService;

    public DemoRestController(
            SearchVehicleService searchVehicleService,
            CountOptionsService countOptionsService
    ) {
        this.searchVehicleService = searchVehicleService;
        this.countOptionsService = countOptionsService;
    }

    @GetMapping("/")
    public String root() {
        return "demo";
    }

    @GetMapping("/test-products")
    public List<ProductDto> listTestProducts() {
        return searchVehicleService.listProducts();
    }

    @PostMapping("/products")
    public ProductDto createProduct() {
        return searchVehicleService.createProduct(ProductDto.builder()
                .name("git")
                .price(BigInteger.valueOf(499_499))
                .build());
    }

    @GetMapping("/products")
    public List<ProductDto> listProducts() {
        return searchVehicleService.listProductsFromDb();
    }

    @GetMapping("/products/{id}")
    public ProductDto getProductById(@PathVariable Integer id) {
        return searchVehicleService.getProductById(id);
    }

    @PutMapping("/products/{id}")
    public ProductDto updateProduct(@PathVariable Integer id) {
        ProductDto updatedProduct = ProductDto.builder()
                .id(id)
                .name("hub")
                .price(BigInteger.valueOf(499_555))
                .build();
        return searchVehicleService.updateProduct(updatedProduct);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        searchVehicleService.deleteProduct(id);
    }

    @GetMapping("/options")
    public List<Option> listOptions() {
        return countOptionsService.listOptions();
    }

}
