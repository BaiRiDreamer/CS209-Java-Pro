package com.example.projectfinal.CrawlData.Service;

import com.example.projectfinal.CrawlData.Entity.*;
import com.example.projectfinal.CrawlData.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class StackOverflowCrawlerService {
    private static final Logger logger = LoggerFactory.getLogger(StackOverflowCrawlerService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private QuestionTagRepository questionTagRepository;

    private static final String API_URL = "https://api.stackexchange.com/2.3/";
    private static final String API_KEY = "rl_bNHWAfLVJh12Uyv9GV4JYxS5i"; // 请在此替换为您的实际 API Key
    private static ArrayList<Integer> questionIds = new ArrayList<>();

    public void crawlQuestionData() {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();

        /**
         *  Example of a request URL:
         *  https://api.stackexchange.com/2.3/questions?page=1&pagesize=20&order=desc&sort=hot&tagged=java&site=stackoverflow
         *  https://api.stackexchange.com/2.3/questions?page=1&pagesize=20&order=desc&sort=hot&tagged=java&site=stackoverflow
         **/

        logger.info("Crawling question data from Stack Overflow...");

        int page = 8;
        int pageSize = 100; // 每页获取的问题数量
        String keyword = "questions";
        String order = "desc";
        String sort = "votes";
        String tags = "java";
        String site = "stackoverflow";

        int totalQuestions = 0;
        int maxQuestions = 500; // 需要获取的问题总数
        int answerGetSpeed = 99; // 每次获取答案的问题数量

        while (totalQuestions < maxQuestions) {
            String url = API_URL + keyword + "?page=" + page + "&pagesize=" + pageSize + "&order=" + order + "&sort=" + sort + "&tagged=" + tags + "&site=" + site + "&key=" + API_KEY;

            logger.info("Requesting data from: " + url);

            try {
                String response = restTemplate.getForObject(url, String.class);
                JsonNode rootNode = objectMapper.readTree(response);
                JsonNode itemsNode = rootNode.get("items");

                // itemsNode.elements().hasNext() returns false if there are no more elements in the iterator
                if (itemsNode == null || !itemsNode.elements().hasNext()) {
                    break; // 如果没有更多数据，退出循环
                }

                // Iterator<JsonNode> items = itemsNode.elements(); ==> 将itemsNode转换为迭代器，以便遍历其中的元素
                Iterator<JsonNode> items = itemsNode.elements();

                while (items.hasNext()) {
                    JsonNode item = items.next();

                    // 解析并保存用户数据
                    JsonNode ownerNode = item.get("owner");
                    User owner = parseUser(ownerNode);

                    // 如果用户不存在，则跳过
                    if (owner == null) {
                        continue;
                    }

                    // 如果用户不存在，则保存用户数据
                    if (!userRepository.existsById(owner.getUserId())) {
                        // 保存用户数据
                        userRepository.save(owner);
                    }

                    // 解析并保存问题数据
                    Question question = parseQuestion(item, owner);
                    // 如果问题不存在，则保存问题数据
                    if (!questionRepository.existsById(question.getQuestionId())) {
                        // 保存问题数据
                        questionRepository.save(question);
                    }

                    // 解析并保存标签数据和问题标签关联
                    JsonNode tagsNode = item.get("tags");
                    if (tagsNode != null && tagsNode.isArray()) {
                        for (JsonNode tagNode : tagsNode) {
                            String tagName = tagNode.asText();
                            Tag tag = tagRepository.findByName(tagName);
                            // 如果标签不存在，则保存标签数据
                            if (tag == null) {
                                tag = new Tag();
                                tag.setName(tagName);
                                tagRepository.save(tag);
                            }

                            // 保存问题标签关联数据
                            QuestionTag questionTag = new QuestionTag();
                            questionTag.setQuestionId(question.getQuestionId());
                            questionTag.setTagId(tag.getTagId());
                            questionTagRepository.save(questionTag);
                        }
                    }

                    questionIds.add(question.getQuestionId());
                    // 获取对应问题的答案数据
                    // 注意这里不是直接调用，而是判断是否积累了足够多的问题后再调用
                    if (questionIds.size() >= answerGetSpeed) {
                        fetchAndSaveAnswers(questionIds);
                        questionIds.clear();
                    }

                    totalQuestions++;
                    if (totalQuestions >= maxQuestions) {
                        break;
                    }
                }

                // 清理剩余的问题 ID，以获取对应问题的答案数据，避免遗漏，最后一页的问题数量可能不足 50 个
                if (!questionIds.isEmpty())
                    fetchAndSaveAnswers(questionIds);
                questionIds.clear();

                // 检查是否需要退避
                if (rootNode.has("backoff")) {
                    int backoff = rootNode.get("backoff").asInt();
                    System.out.println("Backing off for " + backoff + " seconds...");
                    Thread.sleep(backoff * 1000L);
                }

                page++; // 翻页

                // 控制请求频率，避免触发限速
                Thread.sleep(3000L);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private User parseUser(JsonNode ownerNode) {
        User user = new User();

        // 首先判断是否存在用户 ID，如果不存在则返回空
        if (ownerNode == null || ownerNode.get("user_id") == null) {
            return null;
        }
        user.setUserId(ownerNode.get("user_id").asInt());
        user.setAccountId(ownerNode.get("account_id").asInt());
        user.setReputation(ownerNode.get("reputation").asInt());
        user.setUserType(ownerNode.get("user_type").asText());
        user.setDisplayName(ownerNode.get("display_name").asText());
        user.setProfileImage(ownerNode.get("profile_image").asText(null));
        user.setLink(ownerNode.get("link").asText());
        return user;
    }

    private Question parseQuestion(JsonNode item, User owner) {
        Question question = new Question();
        question.setQuestionId(item.get("question_id").asInt());
        question.setTitle(item.get("title") != null ? item.get("title").asText() : null);
        question.setContentLicense(item.get("content_license") != null ? item.get("content_license").asText() : null);
        question.setLink(item.get("link") != null ? item.get("link").asText() : null);
        question.setViewCount(item.get("view_count").asInt());
        question.setAnswerCount(item.get("answer_count").asInt());
        question.setScore(item.get("score").asInt());
        question.setCreationDate(toOffsetDateTime(item.get("creation_date").asLong()));
        question.setLastActivityDate(toOffsetDateTime(item.get("last_activity_date").asLong()));
        question.setOwnerUserId(owner.getUserId());
        question.setIsAnswered(item.get("is_answered").asBoolean());
        if (item.has("accepted_answer_id")) {
            question.setAcceptedAnswerId(item.get("accepted_answer_id").asInt());
        }
        return question;
    }

    private void fetchAndSaveAnswers(List<Integer> questionIds) {
        /**
         *  Example of a request URL:
         *  https://api.stackexchange.com/2.3/answers/11227902?page=1&pagesize=100&order=desc&sort=activity&site=stackoverflow&filter=!6WPIomorr1gmu
         **/
        int page = 1;
        int pageSize = 100; // 每页获取的问题数量
        String keyword = "questions";
        String order = "desc";
        String sort = "votes";
        String site = "stackoverflow";
        String filter = "!nNPvSNdWme"; // 过滤器，用于指定返回的字段
        boolean hasMore = true;

        String questionId = String.join(";", questionIds.stream().map(String::valueOf).toArray(String[]::new)); // 将问题 ID 列表转换为字符串, 例如 "11227902;11227903;11227904"

        String url = API_URL + keyword + "/" + questionId + "/answers?" + "page=" + page + "&pagesize=" + pageSize + "&order=" + order + "&sort=" + sort + "&site=" + site + "&filter=" + filter + "&key=" + API_KEY;

        logger.info("Requesting data from: " + url);

        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();

        while (hasMore) {
            JsonNode ownerNode = null;
            try {
                url = API_URL + keyword + "/" + questionId + "/answers?" + "page=" + page + "&pagesize=" + pageSize + "&order=" + order + "&sort=" + sort + "&site=" + site + "&filter=" + filter + "&key=" + API_KEY;
                String response = restTemplate.getForObject(url, String.class);
                JsonNode rootNode = objectMapper.readTree(response);
                JsonNode itemsNode = rootNode.get("items");

                // 更新HasMore
                hasMore = rootNode.get("has_more").asBoolean();

                if (itemsNode != null && itemsNode.isArray()) {
                    // 将itemsNode转换为迭代器，以便遍历其中的元素
                    Iterator<JsonNode> items = itemsNode.elements();

                    while (items.hasNext()) {
                        JsonNode item = items.next();

                        // 解析并保存回答者的用户数据
                        ownerNode = item.get("owner");
                        User answerUser = parseUser(ownerNode);

                        // 如果用户不存在，则跳过
                        if (answerUser == null) {
                            continue;
                        }

                        // 如果用户不存在，则保存用户数据
                        if (!userRepository.existsById(answerUser.getUserId())) {
                            userRepository.save(answerUser);
                        }

                        // 解析并保存答案数据
                        Answer answer = parseAnswer(item, answerUser);
                        // 判断答案是否已存在
                        if (!answerRepository.existsById(answer.getAnswerId())) {
                            // 保存答案数据
                            answerRepository.save(answer);
                        }
                    }
                }

                // 检查是否需要退避
                if (rootNode.has("backoff")) {
                    int backoff = rootNode.get("backoff").asInt();
                    System.out.println("Backing off for " + backoff + " seconds...");
                    Thread.sleep(backoff * 1000L);
                }

                // 控制请求频率
                Thread.sleep(5000L);

            } catch (Exception e) {
                // 输出节点信息
                logger.error("Failed to fetch answers from: " + url);
                if (ownerNode != null)
                    logger.error(ownerNode.toString());
                e.printStackTrace();
            }
            page++; // 翻页
        }
    }

    private Answer parseAnswer(JsonNode item, User user) {
        Answer answer = new Answer();
        answer.setAnswerId(item.get("answer_id").asInt());
        answer.setQuestionId(item.get("question_id").asInt());
        answer.setUserId(user.getUserId());
        answer.setIsAccepted(item.get("is_accepted").asBoolean());
        answer.setScore(item.get("score").asInt());
        answer.setBody(item.get("body").asText());
        answer.setCreationDate(toOffsetDateTime(item.get("creation_date").asLong()));
        answer.setLastActivityDate(toOffsetDateTime(item.get("last_activity_date").asLong()));
        return answer;
    }

    private OffsetDateTime toOffsetDateTime(long epochSeconds) {
        return OffsetDateTime.ofInstant(Instant.ofEpochSecond(epochSeconds), ZoneOffset.UTC);
    }
}