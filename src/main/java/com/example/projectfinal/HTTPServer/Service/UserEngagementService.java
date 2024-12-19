package com.example.projectfinal.HTTPServer.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserEngagementService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getTopUserEngagement(int reputationLimit, int n) {
        String sql = "WITH user_participation AS (" +
                "    SELECT q.owner_user_id AS user_id, qt.tag_id " +
                "    FROM questions q " +
                "    JOIN question_tags qt ON q.question_id = qt.question_id " +
                "    JOIN users u ON q.owner_user_id = u.user_id " +
                "    WHERE u.reputation > ? " +
                "    UNION ALL " +
                "    SELECT a.user_id, qt.tag_id " +
                "    FROM answers a " +
                "    JOIN question_tags qt ON a.question_id = qt.question_id " +
                "    JOIN users u ON a.user_id = u.user_id " +
                "    WHERE u.reputation > ? " +
                ") " +
                "SELECT t.name AS topic, COUNT(*) AS participation_count " +
                "FROM user_participation up " +
                "JOIN tags t ON up.tag_id = t.tag_id " +
                "GROUP BY t.name " +
                "ORDER BY participation_count DESC " +
                "LIMIT ?";
        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, reputationLimit, reputationLimit, n);

        return results.stream()
                .sorted((a, b) -> ((Long) b.get("participation_count")).compareTo((Long) a.get("participation_count")))
                .limit(n)
                .collect(Collectors.toList());
    }
}