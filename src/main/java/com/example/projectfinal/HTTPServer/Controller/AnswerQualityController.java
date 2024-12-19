package com.example.projectfinal.HTTPServer.Controller;

import com.example.projectfinal.HTTPServer.Service.AnswerQualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/AnswerQuality")
public class AnswerQualityController {

    @Autowired
    private AnswerQualityService answerQualityService;

    @GetMapping("/firstAnswerAcceptedRatio")
    public double getFirstAnswerAcceptedRatio() {
        return answerQualityService.getFirstAnswerAcceptedRatio();
    }

    @GetMapping("/acceptedAnswerRatio")
    public double getAcceptedAnswerRatio() {
        return answerQualityService.getAcceptedAnswerRatio();
    }

    @GetMapping("/highQualityAnswerByHighReputationUserRatio")
    public double getHighQualityAnswerByHighReputationUserRatio(
            @RequestParam int voteThreshold,
            @RequestParam int reputationThreshold) {
        return answerQualityService.getHighQualityAnswerByHighReputationUserRatio(voteThreshold, reputationThreshold);
    }

    @GetMapping("/highQualityAnswerLengthDistribution")
    public Map<String, Double> getHighQualityAnswerLengthDistribution(@RequestParam int voteThreshold) {
        return answerQualityService.getHighQualityAnswerLengthDistribution(voteThreshold);
    }
}