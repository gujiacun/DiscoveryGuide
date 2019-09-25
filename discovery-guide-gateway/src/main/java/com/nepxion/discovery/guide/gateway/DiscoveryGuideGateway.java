package com.nepxion.discovery.guide.gateway;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.nepxion.discovery.guide.gateway.impl.MyDiscoveryEnabledStrategy;
import com.nepxion.discovery.guide.gateway.impl.MyGatewayStrategyTracer;
import com.nepxion.discovery.plugin.strategy.adapter.DiscoveryEnabledStrategy;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.gateway.filter.DefaultGatewayStrategyClearFilter;
import com.nepxion.discovery.plugin.strategy.gateway.filter.GatewayStrategyClearFilter;
import com.nepxion.discovery.plugin.strategy.gateway.tracer.GatewayStrategyTracer;

@SpringBootApplication
@EnableDiscoveryClient
public class DiscoveryGuideGateway {
    public static void main(String[] args) {
        System.setProperty("nepxion.banner.shown.ansi.mode", "true");

        new SpringApplicationBuilder(DiscoveryGuideGateway.class).run(args);
    }

    @Bean
    public DiscoveryEnabledStrategy discoveryEnabledStrategy() {
        return new MyDiscoveryEnabledStrategy();
    }

    /*@Bean
    public GatewayStrategyRouteFilter gatewayStrategyRouteFilter() {
        return new MyGatewayStrategyRouteFilter();
    }*/

    // 用以清除每次调用后GatewayStrategyContext的上下文对象，必须放在业务侧来初始化，抽象到框架层则无效
    @Bean
    public GatewayStrategyClearFilter gatewayStrategyContextClearFilter() {
        return new DefaultGatewayStrategyClearFilter();
    }

    @Bean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACE_ENABLED, matchIfMissing = false)
    public GatewayStrategyTracer gatewayStrategyTracer() {
        return new MyGatewayStrategyTracer();
    }
}