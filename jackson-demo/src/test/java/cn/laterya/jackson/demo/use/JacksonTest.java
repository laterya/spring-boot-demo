package cn.laterya.jackson.demo.use;

import cn.laterya.jackson.demo.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author Pan Yu
 * @Description
 * @Date 创建于 2025/11/9 22:23
 */
public class JacksonTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        // 一次性配置（线程安全的前提）
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MAPPER.registerModule(new JavaTimeModule());
        MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    public void test() throws JsonProcessingException {
        // 1. 对象 → JSON 字符串
        User user = new User();
        user.setName("张三");
        user.setAge(25);
        user.setPassword("secret"); // 会被 @JsonIgnore 忽略
        user.setCreateTime(LocalDateTime.now());

        String json = MAPPER.writeValueAsString(user);
        System.out.println("序列化结果: " + json);
        // 输出: {"user_name":"张三","age":25,"createTime":"2025-11-09 23:15:30"}

        // 2. JSON 字符串 → 对象
        User parsedUser = MAPPER.readValue(json, User.class);
        System.out.println("反序列化结果: " + parsedUser);

        // 3. 处理 List<User>
        List<User> users = Arrays.asList(
                new User() {{
                    setName("李四");
                    setAge(30);
                    setCreateTime(LocalDateTime.now());
                }},
                new User() {{
                    setName("王五");
                    setAge(28);
                    setCreateTime(LocalDateTime.now());
                }}
        );
        String jsonArray = MAPPER.writeValueAsString(users);
        System.out.println("List 序列化: " + jsonArray);

        // 反序列化 List（必须用 TypeReference）
        List<User> parsedUsers = MAPPER.readValue(jsonArray, new TypeReference<List<User>>() {
        });
        System.out.println("List 反序列化数量: " + parsedUsers.size());

        // 4. 使用 JsonNode 动态解析
        JsonNode rootNode = MAPPER.readTree(json);
        String userName = rootNode.get("user_name").asText();
        int userAge = rootNode.get("age").asInt();
        System.out.println("JsonNode 解析: " + userName + ", " + userAge);

        // 5. JsonNode → POJO
        User fromNode = MAPPER.treeToValue(rootNode, User.class);
        System.out.println("JsonNode 转对象: " + fromNode);
    }
}
