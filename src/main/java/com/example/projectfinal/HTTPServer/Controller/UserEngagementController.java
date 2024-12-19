package com.example.projectfinal.HTTPServer.Controller;

import com.example.projectfinal.HTTPServer.Service.UserEngagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/UserEngagement")
public class UserEngagementController {

    @Autowired
    private UserEngagementService userEngagementService;

    @GetMapping("/TopUserEngagement")
    public List<Map<String, Object>> getTopUserEngagement(@RequestParam int reputationLimit, @RequestParam int n) {
        return userEngagementService.getTopUserEngagement(reputationLimit, n);
    }
}