package cn.laterya.sentinel.demo.dubbo;

import com.alibaba.csp.sentinel.init.InitExecutor;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.bootstrap.builders.ServiceBuilder;

import java.util.Collections;

public class ProviderApplication {

    private static final String RES_KEY = "cn.laterya.demo.dubbo.DemoService:sayHello(java.lang.String)";
    private static final String INTERFACE_RES_KEY = "cn.laterya.demo.dubbo.DemoService";

    public static void main(String[] args) {
        InitExecutor.doInit();

        initFlowRule();

        DubboBootstrap.getInstance()
                .protocol(new ProtocolConfig(CommonConstants.TRIPLE, 50051))
                .service(ServiceBuilder.newBuilder().interfaceClass(DemoService.class).ref(new DemoServiceImpl()).build())
                .start()
                .await();
    }

    private static void initFlowRule() {
        FlowRule flowRule = new FlowRule();
        flowRule.setResource(RES_KEY);
        flowRule.setCount(10);
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setLimitApp("default");
        FlowRuleManager.loadRules(Collections.singletonList(flowRule));
    }
}