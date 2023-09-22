# GateWay

## 依赖

1. 依赖导入

   > 要排除spring-web-starter！

   ```
   <dependency>
       <groupId>com.alibaba.cloud</groupId>
       <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
   </dependency>
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-gateway</artifactId>
   </dependency>
   ```

2. lp访问要加入下面依赖
   ```
   <!--<dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-loadbalancer</artifactId>
   </dependency>-->
   ```

<br/>

## yml配置

```
server:
  port:
    8002
spring:
  application:
    name: gateway-learn
  cloud:
    nacos:
      server-addr: localhost:8848
    gateway:
      discovery:
        locator:
          enabled: false # 开启从注册中心动态创建路由的功能，利用微服务名进行路由
      routes:
        - id: payment_routh1
          uri: http://localhost:8001
          predicates:
            - Path=/test1/** # 路径相匹配的进行路由
          filters:
            - StripPrefix=1 # 去掉前缀
```

<br/>

## 常用路由工厂

```
predicates:
  - Path=/test1/** # 路径相匹配的进行路由
  - After=2023-01-01T00:00:00+08:00[Asia/Shanghai] # 时间相匹配的进行路由, 2023年以后的时间
  - Before=2024-01-01T00:00:00+08:00[Asia/Shanghai] # 时间相匹配的进行路由, 2024年以前的时间
  - Between=2023-01-01T00:00:00+08:00[Asia/Shanghai], 2024-01-01T00:00:00+08:00[Asia/Shanghai] # 时间相匹配的进行路由, 2023年到2024年之间的时间
  - Method=GET,Post # 请求方式相匹配的进行路由
```

<br/>

## 局部过滤器

```
routes:
  - id: payment_routh1
    uri: http://localhost:8001
    predicates:
      - Path=/test1/** # 路径相匹配的进行路由
      - After=2023-01-01T00:00:00+08:00[Asia/Shanghai] # 时间相匹配的进行路由, 2023年以后的时间
      - Before=2024-01-01T00:00:00+08:00[Asia/Shanghai] # 时间相匹配的进行路由, 2024年以前的时间
      - Between=2023-01-01T00:00:00+08:00[Asia/Shanghai], 2024-01-01T00:00:00+08:00[Asia/Shanghai] # 时间相匹配的进行路由, 2023年到2024年之间的时间
      - Method=GET,Post # 请求方式相匹配的进行路由
    filters:
      - StripPrefix=1 # 去掉前缀
```

## 默认过滤器

```
default-filters: # 默认过滤器
  - StripPrefix=1 # 去掉前缀
```

## 全局过滤器

```
package com.ocean.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Order(-1) // 过滤器的优先级，数字越小，优先级越高
public class AuthorizeFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取请求参数
        String token = exchange.getRequest().getQueryParams().getFirst("token");
        if (token == null || token.isEmpty()) {
            // 如果token为空，则设置状态码为401，返回
            exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        // 否则，放行
        return chain.filter(exchange);
    }
}
```

# nacos调用其他模块的服务

## feign调用

1. 依赖
   > 关于feign的导包： 1. nacos中去掉ribbon包； 2. 导入openfeign和loadbalancer包
    ```
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        <exclusions>
            <exclusion>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
    </dependency>
    <!--feign依赖-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-loadbalancer</artifactId>
    </dependency>
    ```
2. 启动类
   > 加上@EnableFeignClients // 开启Feign
3. service接口
   > 1. @FeignClient(value = "service-user-8001"), value为服务名
   > 2. @GetMapping("/user/getUserByCode"), 要加上路径
   > 3. @RequestParam("code") String code, 要加上@RequestParam注解！！！
    ```java
    package com.ocean.service;
    
    import com.ocean.commonPackage.common.R;
    import com.ocean.commonPackage.entity.user.User;
    import org.springframework.cloud.openfeign.FeignClient;
    import org.springframework.stereotype.Service;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RequestParam;
    
    @Service
    @FeignClient(value = "service-user-8001")
    public interface UserService {
        @GetMapping("/user/getUserByCode")
        R<User> getUserByCode(@RequestParam("code") String code);
    }
    
    ```
4. 使用
   依赖注入service接口后直接调用即可