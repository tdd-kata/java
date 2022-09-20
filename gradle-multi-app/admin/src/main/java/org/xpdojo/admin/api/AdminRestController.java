package org.xpdojo.admin.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xpdojo.aggregation.application.CountOptionsService;
import org.xpdojo.aggregation.dto.Option;
import org.xpdojo.search.SearchVehicleService;
import org.xpdojo.search.ProductDto;

import java.util.List;

@RestController
public class AdminRestController {

    private final SearchVehicleService searchVehicleService;
    private final CountOptionsService countOptionsService;

    public AdminRestController(
            SearchVehicleService searchVehicleService,
            CountOptionsService countOptionsService
    ) {
        this.searchVehicleService = searchVehicleService;
        this.countOptionsService = countOptionsService;
    }

    @GetMapping("/")
    public String root() {
        return "admin";
    }

    @GetMapping("/products")
    public List<ProductDto> listProducts() {
        return searchVehicleService.listProducts();
    }

    @GetMapping("/options")
    public List<Option> listOptions() {
        return countOptionsService.listOptions();
    }

}
