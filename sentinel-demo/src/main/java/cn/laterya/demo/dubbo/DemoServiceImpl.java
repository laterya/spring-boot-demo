package cn.laterya.demo.dubbo;

public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return "Hello " + name + ", response from provider.";
    }
}