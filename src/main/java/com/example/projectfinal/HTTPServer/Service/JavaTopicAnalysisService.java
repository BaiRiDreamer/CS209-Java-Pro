package com.example.projectfinal.HTTPServer.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JavaTopicAnalysisService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getTopJavaTopics(int n) {
        String sql = "SELECT t.name AS topic, COUNT(*) AS count " +
                "FROM question_tags qt " +
                "JOIN tags t ON qt.tag_id = t.tag_id " +
                "GROUP BY t.name";
        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);

        // 定义白名单
        List<String> whitelist = List.of("java", "spring", "hibernate", "maven", "junit", "jpa", "spring-boot", "spring-mvc", "spring-security", "spring-data-jpa",
                "stream", "lambda", "thread", "concurrency", "servlet", "jdbc", "jpa", "jackson", "json", "rest", "restful", "web", "http", "https", "tomcat", "jetty", "undertow",
                "netty", "nio", "io", "file", "orm", "arraylist", "annotation", "reflection", "class", "object", "oop", "design-pattern", "singleton", "factory", "abstract-factory", "builder", "prototype",
                "spring-bean", "spring-annotation", "spring-xml", "spring-javaconfig", "spring-aop", "spring-tx", "spring-jdbc", "spring-jpa", "spring-orm", "spring-mvc",
                "jar", "performance", "memory", "cpu", "disk", "network", "database", "mysql", "oracle", "sql", "nosql", "mongodb", "redis", "cassandra", "hbase", "hadoop");

        // 过滤掉不在白名单中的标签
        results = results.stream()
                .filter(m -> whitelist.contains(m.get("topic")))
                .collect(Collectors.toList());
        // 按 count 降序排序
        results.sort((m1, m2) -> ((Long) m2.get("count")).compareTo((Long) m1.get("count")));
        // 取前 n 个
        return results.subList(0, Math.min(n, results.size()));
    }
}