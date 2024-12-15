/*
 Java 主题（10 分）
     我们在本课程中涉及了各种主题，例如泛型、集合、I/O、lambda、多线程、socket 等。我们很想知道，在
     Stack Overflow 上最常被问到的前 N 个（N>1，您可以根据您的数据和用户界面设计选择合适的 N，下同）主
     题是什么。
 ==> 哪些有关java的topic被问的最多？ 可以直接统计1000个thread中的tag_name的数量，然后排序取前N个
*/

/*
 User Engagement (15分)
     分析并展示（高级用户）用户参与度最高的N个Java相关主题。
 ==> 首先对每个thread的非高级用户的活动进行去除，之后统计每个thread的高级用户的活动数量，然后排序取前N个
 */

/*
 Common Mistakes (15分)
     分析并展示Java开发者最常讨论的N个错误和异常。
 ==> 分析每个thread中的的question和answer的content，通过文本匹配找出最常见的错误和异常，然后排序取前N个
*/

/*
 Answer Quality (30分)
      分析影响高质量答案的因素，包括回答创建时间、回答者的声誉等
     1. 回答创建时间：统计每个thread中的answer的creation_date，
     2. 回答者的声誉：统计每个thread中的answer的score
     3. 问题的讨论热度：统计每个thread中的answer的count

  ==> 过滤出高质量的答案，然后对这些答案进行分析回答创建时间、回答者的声誉，问题的讨论热度等等情况
 */


 -------------------------------------------------------------
-------------------- 以下是数据库的表结构 -------------------------
 -------------------------------------------------------------

-- 创建 'users' 表，存储 Stack Overflow 用户的基本信息
CREATE TABLE users (
       user_id INT PRIMARY KEY,  -- 用户的唯一标识符，作为主键
       account_id INT NOT NULL,  -- 用户账户的 ID，不能为空
       reputation INT NOT NULL,  -- 用户的声誉积分，不能为空
       user_type VARCHAR(50) ,  -- 用户类型（如 'registered'），不能W为空
       display_name VARCHAR(255) ,  -- 用户的显示名称，不能为空
       profile_image TEXT ,  -- 用户头像的图片 URL，不能为空
       link TEXT   -- 用户的 Stack Overflow 主页链接，不能为空
);

-- 创建 'questions' 表，存储问题的基本信息
CREATE TABLE questions (
       question_id INT PRIMARY KEY,  -- 问题的唯一标识符，作为主键
       title VARCHAR(255) NOT NULL,  -- 问题的标题，不能为空
       content_license VARCHAR(50),  -- 问题的内容许可类型（如 'CC BY-SA 4.0'），不能为空
       link TEXT NOT NULL,  -- 问题的 Stack Overflow 链接，不能为空
       view_count INT NOT NULL,  -- 问题的浏览次数，不能为空
       answer_count INT NOT NULL,  -- 问题的答案数量，不能为空
       score INT NOT NULL,  -- 问题的得分，不能为空
       creation_date TIMESTAMPTZ NOT NULL,  -- 问题创建时间，包含时区信息
       last_activity_date TIMESTAMPTZ NOT NULL,  -- 问题最后活动时间，包含时区信息
       owner_user_id INT NOT NULL,  -- 问题所有者的用户 ID，不能为空，外键关联到 'users' 表
       is_answered BOOLEAN NOT NULL,  -- 问题是否已被回答，'true' 或 'false'
       accepted_answer_id INT,  -- 如果有采纳的答案，则记录采纳的答案 ID，允许为空
       FOREIGN KEY (owner_user_id) REFERENCES users(user_id)  -- 外键约束，关联 'users' 表
);

-- 创建 'answers' 表，存储答案的详细信息
CREATE TABLE answers (
         answer_id INT PRIMARY KEY,  -- 答案的唯一标识符，作为主键
         question_id INT NOT NULL,  -- 所属问题的 ID，不能为空，外键关联到 'questions' 表
         user_id INT NOT NULL,  -- 答案的作者的用户 ID，不能为空，外键关联到 'users' 表
         is_accepted BOOLEAN NOT NULL,  -- 该答案是否被采纳，'true' 或 'false'
         score INT NOT NULL,  -- 答案的得分，不能为空
         body TEXT NOT NULL,  -- 答案的正文内容，不能为空
         creation_date TIMESTAMPTZ NOT NULL,  -- 答案创建时间，包含时区信息
         last_activity_date TIMESTAMPTZ NOT NULL,  -- 答案最后活动时间，包含时区信息
         FOREIGN KEY (question_id) REFERENCES questions(question_id),  -- 外键约束，关联 'questions' 表
         FOREIGN KEY (user_id) REFERENCES users(user_id)  -- 外键约束，关联 'users' 表
);


-- 创建 'tags' 表，存储问题标签信息
CREATE TABLE tags (
      tag_id SERIAL PRIMARY KEY,  -- 标签的唯一标识符，自动生成，作为主键
      name VARCHAR(255) UNIQUE NOT NULL  -- 标签名称，不能为空，并且保证唯一性
);


-- 创建 'question_tags' 表，存储问题与标签的多对多关系
CREATE TABLE question_tags (
       question_id INT NOT NULL,  -- 问题的 ID，不能为空，外键关联到 'questions' 表
       tag_id INT NOT NULL,  -- 标签的 ID，不能为空，外键关联到 'tags' 表
       PRIMARY KEY (question_id, tag_id),  -- 多对多关系的主键，由问题 ID 和标签 ID 组成
       FOREIGN KEY (question_id) REFERENCES questions(question_id) ON DELETE CASCADE,  -- 外键约束，关联 'questions' 表，删除问题时同时删除关联
       FOREIGN KEY (tag_id) REFERENCES tags(tag_id) ON DELETE CASCADE  -- 外键约束，关联 'tags' 表，删除标签时同时删除关联
);


-- 为常用查询字段创建索引，以提高查询性能
CREATE INDEX idx_questions_owner_user_id ON questions(owner_user_id);  -- 为 'questions' 表的 'owner_user_id' 列创建索引
CREATE INDEX idx_answers_question_id ON answers(question_id);  -- 为 'answers' 表的 'question_id' 列创建索引
CREATE INDEX idx_answers_user_id ON answers(user_id);  -- 为 'answers' 表的 'user_id' 列创建索引
CREATE INDEX idx_question_tags_tag_id ON question_tags(tag_id);  -- 为 'question_tags' 表的 'tag_id' 列创建索引
