package org.xpdojo.search;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SearchVehicleService {

    private final ProductRepository productRepository;

    @Autowired
    public SearchVehicleService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> listProducts() {
        List<ProductDto> productDtos = Arrays.asList(
                new ProductDto(1, "git", BigInteger.valueOf(499_499)),
                new ProductDto(2, "elastic", BigInteger.valueOf(123_456_789)),
                new ProductDto(3, "java", BigInteger.valueOf(55_555_499))
        );
        for (ProductDto productDto : productDtos) {
            log.info("product >>> {}", productDto);
        }
        return productDtos;
    }

    public List<ProductDto> listProductsFromDb() {
        return productRepository.findAll()
                .stream()
                .map(productEntity -> new ProductDto(productEntity.getId(), productEntity.getName(), productEntity.getPrice()))
                .collect(Collectors.toList());
    }

    public ProductDto getProductById(Integer id) {
        return productRepository.findById(id)
                .map(productEntity -> new ProductDto(productEntity.getId(), productEntity.getName(), productEntity.getPrice()))
                .orElse(null);
    }

    public ProductDto createProduct(ProductDto productDto) {
        ProductEntity productEntity = ProductEntity.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .build();
        productRepository.save(productEntity);
        return productDto;
    }

    public ProductDto updateProduct(ProductDto productDto) {
        ProductEntity productEntity = ProductEntity.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .price(productDto.getPrice())
                .build();
        productRepository.save(productEntity);
        return productDto;
    }

    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

}
