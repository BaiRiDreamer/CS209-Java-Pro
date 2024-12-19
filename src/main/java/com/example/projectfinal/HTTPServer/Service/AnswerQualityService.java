package com.example.projectfinal.HTTPServer.Service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnswerQualityService {
    @Autowired JdbcTemplate jdbcTemplate;

    // 统计所有Question的accept的answer中，是第一个回答answer的比例
    public double getFirstAnswerAcceptedRatio() {
        String sql = "SELECT COUNT(*) AS total, " +
                "SUM(CASE WHEN a.answer_id = q.accepted_answer_id AND a.creation_date = first_answer.creation_date THEN 1 ELSE 0 END) AS first_accepted " +
                "FROM questions q " +
                "JOIN answers a ON q.question_id = a.question_id " +
                "JOIN (SELECT question_id, MIN(creation_date) AS creation_date FROM answers GROUP BY question_id) first_answer " +
                "ON q.question_id = first_answer.question_id";
        Map<String, Object> result = jdbcTemplate.queryForMap(sql);
        int total = ((Number) result.get("total")).intValue();
        int firstAccepted = ((Number) result.get("first_accepted")).intValue();
        return total == 0 ? 0 : (double) firstAccepted / total;
    }


    // 统计所有有被accept的question中，被接受为accept的比例
    public double getAcceptedAnswerRatio() {
        String sql = "SELECT COUNT(a.answer_id) AS total_answers, " +
                "COUNT(CASE WHEN a.is_accepted = TRUE THEN 1 END) AS accepted_answers " +
                "FROM answers a " +
                "JOIN questions q ON a.question_id = q.question_id " +
                "WHERE a.is_accepted = TRUE OR a.answer_id IS NOT NULL";
        Map<String, Object> result = jdbcTemplate.queryForMap(sql);
        int total = ((Number) result.get("total_answers")).intValue();
        int accepted = ((Number) result.get("accepted_answers")).intValue();
        return total == 0 ? 0 : (double) accepted / total;
    }

    // 统计所有高质量回答（vote多）中，是由高质量用户（reputation）回答的比例
    public double getHighQualityAnswerByHighReputationUserRatio(int voteThreshold, int reputationThreshold) {
        String sql = "SELECT COUNT(*) AS total, " +
                "SUM(CASE WHEN u.reputation >= ? THEN 1 ELSE 0 END) AS high_reputation " +
                "FROM answers a " +
                "JOIN users u ON a.user_id = u.user_id " +
                "WHERE a.score >= ?";
        Map<String, Object> result = jdbcTemplate.queryForMap(sql, reputationThreshold, voteThreshold);
        int total = ((Number) result.get("total")).intValue();
        int highReputation = ((Number) result.get("high_reputation")).intValue();
        return total == 0 ? 0 : (double) highReputation / total;
    }


    // 统计所有高质量回答（vote多）中，各个字数区间的比例
    public Map<String, Double> getHighQualityAnswerLengthDistribution(int voteThreshold) {
        String sql = "SELECT LENGTH(a.body) AS length " +
                "FROM answers a " +
                "WHERE a.score >= ?";
        List<Integer> lengths = jdbcTemplate.queryForList(sql, Integer.class, voteThreshold);

        Map<String, Long> lengthDistribution = lengths.stream()
                .collect(Collectors.groupingBy(length -> {
                    if (length <= 100) return "0-100";
                    else if (length <= 200) return "101-200";
                    else if (length <= 500) return "201-500";
                    else if (length <= 1000) return "501-1000";
                    else if (length <= 2000) return "1001-2000";
                    else if (length <= 5000) return "2001-5000";
                    else return "5001+";
                }, Collectors.counting()));

        long total = lengths.size();

        return lengthDistribution.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> (double) e.getValue() / total));
    }

}
