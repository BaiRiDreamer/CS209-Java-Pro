package com.example.projectfinal.HTTPServer.Controller;

import com.example.projectfinal.HTTPServer.Service.JavaTopicAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/javaTopicAnalysis")
public class JavaTopicAnalysisController {

    @Autowired
    private JavaTopicAnalysisService javaTopicAnalysisService;

    @GetMapping("/TopTopics/{num}")
    public List<Map<String, Object>> getHotestTopics(@PathVariable Integer num) {
        int defaultNum = 10;
        if (num == null || num <= 0) {
            num = defaultNum;
        }

        List<Map<String, Object>> result = null;
        try {
            result = javaTopicAnalysisService.getTopJavaTopics(num);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return javaTopicAnalysisService.getTopJavaTopics(num);
    }

    // URL example: http://localhost:8080/javaTopicAnalysis/SpecificTopics/java,spring
    @GetMapping("/SpecificTopics/{topics}")
    public List<Map<String, Object>> getSpecificTopics(@PathVariable List<String> topics) {
        List<Map<String, Object>> result = null;
        try {
            result = javaTopicAnalysisService.getSpecificJavaTopics(topics);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}