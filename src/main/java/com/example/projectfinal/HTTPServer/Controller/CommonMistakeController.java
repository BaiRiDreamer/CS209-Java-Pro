package com.example.projectfinal.HTTPServer.Controller;

import com.example.projectfinal.HTTPServer.Service.CommonMistakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/CommonMistake")
public class CommonMistakeController {
    @Autowired
    private CommonMistakeService CommonMistakeService;

    @GetMapping("/CommonMistake")
    public List<Map<String, Object>> getCommonMistakes(@RequestParam int n) {
        List<Map<String, Object>> commonMistakes = CommonMistakeService.getCommonMistakes(n);
        return commonMistakes;
    }
}
