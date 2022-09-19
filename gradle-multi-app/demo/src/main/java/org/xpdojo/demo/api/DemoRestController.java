package org.xpdojo.demo.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xpdojo.aggregate.application.CountOptionsService;
import org.xpdojo.aggregate.dto.Option;
import org.xpdojo.search.application.SearchVehicleService;
import org.xpdojo.search.dto.Product;

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

    @GetMapping("/products")
    public List<Product> listProducts() {
        return searchVehicleService.listProducts();
    }

    @GetMapping("/options")
    public List<Option> listOptions() {
        return countOptionsService.listOptions();
    }

}
