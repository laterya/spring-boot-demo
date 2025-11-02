package cn.laterya.demo.dubbo;

import org.apache.dubbo.config.bootstrap.builders.ReferenceBuilder;

public class ConsumerApplication {
    public static void main(String[] args) {
        DemoService demoService =
                (DemoService) ReferenceBuilder.newBuilder()
                        .interfaceClass(DemoService.class)
                        .url("tri://localhost:50051")
                        .build()
                        .get();


        while (true) {
            try {
                String message = demoService.sayHello("dubbo");
                System.out.println(message);
            } catch (Exception e) {

            }
        }

    }
}