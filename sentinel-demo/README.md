# sentinel-demo

## 快速开始

参考官方文档：https://sentinelguard.io/zh-cn/docs/quick-start.html

### 启动控制台

src/main/resources目录下的 sentinel-dashboard-1.8.9.jar，使用指令启动

```shell
 java -Dserver.port=8080 -Dcsp.sentinel.dashboard.server=localhost:8080 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard-1.8.9.jar
```

启动成功后，访问 http://localhost:8080
账号密码都是 sentinel

### 启动服务

```java
public class App {
    public static void main(String[] args) {
        // 配置规则.
        initFlowRules();

        while (true) {
            // 1.5.0 版本开始可以直接利用 try-with-resources 特性
            try (Entry entry = SphU.entry("HelloWorld")) {
                // 被保护的逻辑
                System.out.println("hello world");
            } catch (BlockException ex) {
                // 处理被流控的逻辑
                System.out.println("blocked!");
            }
        }
    }

    private static void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("HelloWorld");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 20.
        rule.setCount(20);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }
}

```

编辑JVM参数，添加`-Dcsp.sentinel.dashboard.server=127.0.0.1:8080`，连接到控制台。
查看控制台：
![img.png](pictures/quick-start.png)