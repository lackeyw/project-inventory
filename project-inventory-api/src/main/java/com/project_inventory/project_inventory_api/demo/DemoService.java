package com.project_inventory.project_inventory_api.demo;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DemoService {
    private final DemoRepository demoRepository;

    public List<Demo> getDemoMessage() {
        return demoRepository.findAll();
    }

    public void processDemoRequest(String request) {
        Demo demo = new Demo();
        demo.setMessage(request);
        demoRepository.save(demo);
    }
    
}
