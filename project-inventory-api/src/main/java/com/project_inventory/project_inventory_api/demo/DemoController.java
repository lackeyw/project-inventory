package com.project_inventory.project_inventory_api.demo;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DemoController {

    Logger logger = Logger.getLogger(DemoController.class.getName());

    private final DemoService demoService;
    
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/demo")
    public List<Demo> demoEndpoint() {
        logger.info("Received GET request for /demo endpoint");
        return demoService.getDemoMessage();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/demo")
    public String demoPostEndpoint(@RequestBody String request) {
        logger.info("Received POST request for /demo endpoint with body: " + request);

       demoService.processDemoRequest(request);
       return "Processed Successfully";
    }
}
