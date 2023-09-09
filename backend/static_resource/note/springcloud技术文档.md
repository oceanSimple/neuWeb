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

1. pom
   > 注意：
   >
   > 1. 使用restTemplate调用服务，需要导入spring-cloud-loadbalancer覆盖已经停更的版本较低的ribbon
   > 2. 使用feign调用服务，需要导入spring-cloud-starter-openfeign
   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <project xmlns="http://maven.apache.org/POM/4.0.0"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
       <modelVersion>4.0.0</modelVersion>
       <parent>
           <groupId>com.ocean</groupId>
           <artifactId>nacos-learn</artifactId>
           <version>1.0-SNAPSHOT</version>
       </parent>
   
       <groupId>org.example</groupId>
       <artifactId>consumer-9001</artifactId>
   
       <properties>
           <maven.compiler.source>18</maven.compiler.source>
           <maven.compiler.target>18</maven.compiler.target>
           <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
       </properties>
   
       <dependencies>
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-web</artifactId>
           </dependency>
           <dependency>
               <groupId>com.alibaba.cloud</groupId>
               <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
           </dependency>
           <dependency>
               <groupId>org.example</groupId>
               <artifactId>common-package</artifactId>
               <version>1.0-SNAPSHOT</version>
           </dependency>
           <!--Ribbon停止维护，故导入此包进行覆盖！-->
           <dependency>
               <groupId>org.springframework.cloud</groupId>
               <artifactId>spring-cloud-loadbalancer</artifactId>
           </dependency>
           <!--feign依赖-->
           <dependency>
               <groupId>org.springframework.cloud</groupId>
               <artifactId>spring-cloud-starter-openfeign</artifactId>
           </dependency>
       </dependencies>
   </project>
   ```

2. yml
   ```
   server:
     port: 9001
   spring:
     application:
       name: consumer-9001
     cloud:
       nacos:
         discovery:
           # nacos的服务地址
           server-addr: 127.0.0.1:8848
   management:
     endpoints:
       web:
         exposure:
           ## yml文件中存在特殊字符，必须用单引号包含，否则启动报错
           include: '*'
   ```

3. 启动类
   ```java
   @SpringBootApplication
   @EnableDiscoveryClient
   @EnableFeignClients     // 开启Feign
   public class Consumer9001Application {
       public static void main(String[] args) {
           SpringApplication.run(Consumer9001Application.class, args);
       }
   }
   ```

4. restTemplate调用服务
    1. 导包！
    2. config
       ```java
       package com.ocean.config;
       
       import org.springframework.cloud.client.loadbalancer.LoadBalanced;
       import org.springframework.context.annotation.Bean;
       import org.springframework.context.annotation.Configuration;
       import org.springframework.web.client.RestTemplate;
       
       @Configuration
       public class AutoBeanConfig {
           // @LoadBalanced: 通过SpringCloud中的负载均衡器，从注册中心获取服务提供者的地址
           // 通过服务提供者的地址，再通过RestTemplate调用服务提供者的接口
            @Bean
            @LoadBalanced
            public RestTemplate restTemplate() {
                return new RestTemplate();
            }
       }
       ```
    3. controller
       ```
         @Autowired
         private RestTemplate restTemplate;
         
             @GetMapping("/consumer/student/get")
         public Student getStudent() {
             // 通过服务提供者的微服务名称，从注册中心获取服务提供者的地址
             // 通过服务提供者的地址，再通过RestTemplate调用服务提供者的接口
             String url = "http://provider-8001/student";
             Student result = restTemplate.getForObject(url, Student.class);
             return result;
         }
       ```

5. feign调用
    1. 导包！
    2. service
       ```java
       @Service
       @FeignClient(value = "provider-8001")    // 指定调用哪个服务
       public interface StudentService {
           @GetMapping("/student")
           Student getStudent();
       }
       ```
    3. controller
       ```
           @Autowired
           private StudentService studentService;
           
           @GetMapping("/student")
           public Student getStudent2() {
               return studentService.getStudent();
           }
       ```

<br/>
