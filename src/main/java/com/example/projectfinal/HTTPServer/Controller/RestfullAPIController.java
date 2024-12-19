package com.example.projectfinal.HTTPServer.Controller;

import com.example.projectfinal.HTTPServer.Service.RestfullAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restfullAPI")
public class RestfullAPIController {

    @Autowired
    private RestfullAPIService restfullAPIService;

    @GetMapping("/bugFrequency")
    public List<Map<String, Object>> getBugFrequency(@RequestParam String error, @RequestParam int n) {
        return restfullAPIService.getBugFrequency(error, n);
    }
}