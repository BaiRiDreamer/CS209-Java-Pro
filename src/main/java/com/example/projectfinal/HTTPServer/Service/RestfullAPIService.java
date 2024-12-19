package com.example.projectfinal.HTTPServer.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class RestfullAPIService {
    @Autowired private JdbcTemplate jdbcTemplate;

    // 映射标准错误名称到其可能的变体和描述
    private static final Map<String, List<String>> errorPatternsMap = new HashMap<>();
    static {
        errorPatternsMap.put("NullPointerException", List.of("NullPointerException", "NullPointer", "NullError", "null pointer", "null reference"));
        errorPatternsMap.put("OutOfMemoryError", List.of("OutOfMemoryError", "OOM", "Out of memory", "memory overflow", "memory leak"));
        errorPatternsMap.put("ArrayIndexOutOfBoundsException", List.of("ArrayIndexOutOfBoundsException", "ArrayIndexOutOfBound", "IndexOutOfBounds", "Array index error"));
        errorPatternsMap.put("ClassCastException", List.of("ClassCastException", "Class Cast", "Type Casting Error", "invalid type cast"));
        errorPatternsMap.put("IllegalArgumentException", List.of("IllegalArgumentException", "Illegal Argument", "Invalid Argument", "wrong argument"));
        errorPatternsMap.put("IllegalStateException", List.of("IllegalStateException", "Illegal State", "Invalid State", "wrong state"));
        errorPatternsMap.put("ConcurrentModificationException", List.of("ConcurrentModificationException", "Concurrent Modification", "Collection modified concurrently"));
        errorPatternsMap.put("NoClassDefFoundError", List.of("NoClassDefFoundError", "Class Not Found", "Class Not Exist", "class not found"));
        errorPatternsMap.put("NoSuchMethodError", List.of("NoSuchMethodError", "Method Not Found", "Method Not Exist", "method not found"));
        errorPatternsMap.put("StackOverflowError", List.of("StackOverflowError", "Stack Overflow", "Recursion Too Deep", "recursion overflow"));
        errorPatternsMap.put("StringIndexOutOfBoundsException", List.of("StringIndexOutOfBoundsException", "StringIndexOutOfBound", "String index error"));
        errorPatternsMap.put("NumberFormatException", List.of("NumberFormatException", "Number Format", "Invalid Number", "wrong number format"));
        errorPatternsMap.put("ArithmeticException", List.of("ArithmeticException", "Arithmetic Error", "Math Error", "mathematical error"));
        errorPatternsMap.put("IndexOutOfBoundsException", List.of("IndexOutOfBoundsException", "IndexOutOfBound", "Index out of range", "index error"));
        errorPatternsMap.put("FileNotFoundException", List.of("FileNotFoundException", "File Not Found", "File Not Exist", "file not found"));
        errorPatternsMap.put("IOException", List.of("IOException", "Input/Output Error", "I/O Error", "input output error"));
        errorPatternsMap.put("SQLException", List.of("SQLException", "SQL Error", "Database Error", "database exception"));
        errorPatternsMap.put("NoSuchElementException", List.of("NoSuchElementException", "Element Not Found", "Element Not Exist", "element not found"));
        errorPatternsMap.put("UnsupportedOperationException", List.of("UnsupportedOperationException", "Unsupported Operation", "Operation Not Supported", "operation not supported"));
        errorPatternsMap.put("AssertionError", List.of("AssertionError", "Assertion Error", "Assertion Failed", "assertion error"));
        errorPatternsMap.put("Error", List.of("Error", "Runtime Error", "Runtime Exception", "runtime error"));
        errorPatternsMap.put("Exception", List.of("Exception", "Checked Exception", "Unchecked Exception", "checked exception", "unchecked exception"));
    }

    // 映射标准错误名称到预编译的正则表达式列表
    private static Map<String, List<Pattern>> compiledErrorPatterns;

    @PostConstruct
    private void init() {
        // 预编译所有正则表达式，忽略大小写
        compiledErrorPatterns = errorPatternsMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(patternStr -> Pattern.compile("\\b" + Pattern.quote(patternStr) + "\\b", Pattern.CASE_INSENSITIVE))
                                .collect(Collectors.toList())
                ));
    }

    public List<Map<String, Object>> getBugFrequency(@RequestParam String error, @RequestParam int n) {
        // 获取所有问题的标题和答案的正文
        String sql = "SELECT title AS content FROM questions UNION ALL SELECT body AS content FROM answers";
        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);

        // 匹配error可能的变体
        List<Pattern> patterns = compiledErrorPatterns.get(error);
        if (patterns == null) {
            // 如果error不在白名单中，则直接在所有问题中搜索error
            patterns = List.of(Pattern.compile("\\b" + Pattern.quote(error) + "\\b", Pattern.CASE_INSENSITIVE));
        }

        // 使用并行流提高处理性能
        List<Pattern> finalPatterns = patterns;
        Map<String, Long> errorCount = results.parallelStream()
                .map(map -> (String) map.get("content"))
                .filter(content -> finalPatterns.stream().anyMatch(pattern -> pattern.matcher(content).find()))
                .collect(Collectors.groupingBy(content -> error, Collectors.counting()));


        // 将统计结果按出现次数求和
        return errorCount.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .limit(n)
                .map(entry -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("error", entry.getKey());
                    result.put("count", entry.getValue());
                    return result;
                })
                .collect(Collectors.toList());
    }
}