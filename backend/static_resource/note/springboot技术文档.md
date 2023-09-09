# 通用配置

## 1. pom依赖配置

```
<dependencies>
    <!--springboot依赖-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
        <version>3.0.6</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <version>3.0.6</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
        <version>3.0.6</version>
    </dependency>
    <!--mybatis-plus依赖-->
    <dependency>
        <groupId>com.baomidou</groupId>
        <artifactId>mybatis-plus-boot-starter</artifactId>
        <version>3.5.3.1</version>
    </dependency>
    <!--@data注解依赖-->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.26</version>
    </dependency>
    <!--mysql数据库依赖-->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.32</version>
    </dependency>
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>druid-spring-boot-starter</artifactId>
        <version>1.2.15</version>
    </dependency>
</dependencies>
```

## 2. application.yml

> 记得修改数据库名称！

```
server:
  port: 8080
spring:
  application:
    name: reggie_take_out
  datasource:
    type: com.zaxxer.hikari.HikariDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/web_course_work?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowPublicKeyRetrieval=true
    username: root
    password: root
mybatis-plus:
  configuration:
    map-underscore-to-camel-case: true
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  global-config:
    db-config:
      id-type: ASSIGN_ID
```

## 3. 通用结果返回类

```
import lombok.Data;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用返回结果类
 * @param <T>
 */
@Data
public class R<T> {

    private Integer code; //编码：1成功，0和其它数字为失败

    private String msg; //错误信息

    private T data; //数据

    private Map map = new HashMap(); //动态数据

    public static <T> R<T> success(T object) {
        R<T> r = new R<T>();
        r.data = object;
        r.code = 1;
        return r;
    }

    public static <T> R<T> error(String msg) {
        R r = new R();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }
}
```

## 4. 跨域问题

> 最直接的方式是在controller类上加注解
> 
> @CrossOrigin

另一个方法就是添加配置类

```
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  //请求的服务路径
                //申请请求的地址
                .allowedOrigins("http://localhost:8081")
                //是否允许携带cookie
                .allowCredentials(true)
                //请求方式
                .allowedMethods("GET","POST","DELETE","PUT","OPTIONS");
    }
}
```

## 5. 配置静态映射

```
package com.ocean.reggie.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Slf4j
@Configuration
public class WebMvcConfig aextends WebMvcConfigurationSupport {
    /**
     * 设置静态资源映射
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("静态资源映射启动");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }
}
```

## 6. ~启动类注解

@ServletComponentScan

```
在SpringBootApplication上使用@ServletComponentScan注解后，
Servlet（控制器）、Filter（过滤器）、Listener（监听器）可以直接通过@WebServlet、@WebFilter、@WebListener注解自动注册到Spring容器中，无需其他代码。
```

# mybatis-plus

## 1. 使用

> mapper类上的@Mapper
> 
> serviceImpl上的@Service

1. service接口
   ```
   public interface FoodService extends IService<Food>
   ```
2. mapper接口
   ```
   @Mapper
   public interface FoodMapper extends BaseMapper<Food> {
   }
   ```
3. serviceImpl类
   ```
   @Service
   public class FoodServiceImpl extends ServiceImpl<FoodMapper, Food> implements FoodService 
   ```

## 2. crud接口

### 2.1 save

```
// 插入一条记录（选择字段，策略插入）
boolean save(T entity);
// 插入（批量）
boolean saveBatch(Collection<T> entityList);
// 插入（批量）
boolean saveBatch(Collection<T> entityList, int batchSize);
```

参数说明：

|类型|参数名|描述|
|:--:|:--:|:--:|
|T|entity|实体对象|
|Collection<T>|entityList|实体对象集合|
|int|batchSize|插入批次数量|

### 2.2 remove

```
// 根据 entity 条件，删除记录
boolean remove(Wrapper<T> queryWrapper);
// 根据 ID 删除
boolean removeById(Serializable id);
// 根据 columnMap 条件，删除记录
boolean removeByMap(Map<String, Object> columnMap);
// 删除（根据ID 批量删除）
boolean removeByIds(Collection<? extends Serializable> idList);
```

参数说明：

|类型|参数名|描述|
|:--:|:--:|:--:|
|Wrapper<T>|queryWrapper|实体包装类 QueryWrapper|
|Serializable|id|主键 ID|
|Map<String, Object>|columnMap|表字段 map 对象|
|Collection<? extends Serializable>|idList|主键 ID 列表|

### 2.3 update

```
// 根据 UpdateWrapper 条件，更新记录 需要设置sqlset
boolean update(Wrapper<T> updateWrapper);
// 根据 whereWrapper 条件，更新记录
boolean update(T updateEntity, Wrapper<T> whereWrapper);
// 根据 ID 选择修改
boolean updateById(T entity);
// 根据ID 批量更新
boolean updateBatchById(Collection<T> entityList);
// 根据ID 批量更新
boolean updateBatchById(Collection<T> entityList, int batchSize);
```

参数说明：

|类型|参数名|描述|
|:--:|:--:|:--:|
|Wrapper<T>|updateWrapper|实体对象封装操作类 UpdateWrapper|
|T|entity|实体对象|
|Collection<T>|entityList|实体对象集合|
|int|batchSize|更新批次数量|

### 2.4 get

```
// 根据 ID 查询
T getById(Serializable id);

// 根据 Wrapper，查询一条记录。结果集，如果是多个会抛出异常，随机取一条加上限制条件 wrapper.last("LIMIT 1")
T getOne(Wrapper<T> queryWrapper);

// 根据 Wrapper，查询一条记录
T getOne(Wrapper<T> queryWrapper, boolean throwEx);

// 根据 Wrapper，查询一条记录
Map<String, Object> getMap(Wrapper<T> queryWrapper);

// 根据 Wrapper，查询一条记录
<V> V getObj(Wrapper<T> queryWrapper, Function<? super Object, V> mapper);

```

参数说明：

|类型|参数名|描述|
|:--:|:--:|:--:|
|Serializable|id|主键 ID|
|Wrapper<T>|queryWrapper|实体对象封装操作类 QueryWrapper|
|boolean|throwEx|有多个 result 是否抛出异常|
|T|entity|实体对象|
|Function<? super Object, V>|mapper|转换函数|

### 2.5 list

```
// 查询所有
List<T> list();

// 查询列表
List<T> list(Wrapper<T> queryWrapper);

// 查询（根据ID 批量查询）
Collection<T> listByIds(Collection<? extends Serializable> idList);

// 查询（根据 columnMap 条件）
Collection<T> listByMap(Map<String, Object> columnMap);

// 查询所有列表
List<Map<String, Object>> listMaps();

// 查询列表
List<Map<String, Object>> listMaps(Wrapper<T> queryWrapper);

// 查询全部记录
List<Object> listObjs();

// 查询全部记录
<V> List<V> listObjs(Function<? super Object, V> mapper);

// 根据 Wrapper 条件，查询全部记录
List<Object> listObjs(Wrapper<T> queryWrapper);

// 根据 Wrapper 条件，查询全部记录
<V> List<V> listObjs(Wrapper<T> queryWrapper, Function<? super Object, V> mapper);

```

参数说明：

|类型|参数名|描述|
|:--:|:--:|:--:|
|Wrapper<T>|queryWrapper|实体对象封装操作类 QueryWrapper|
|Collection<? extends Serializable>|idList|主键 ID 列表|
|Map<String, Object>|columnMap|表字段 map 对象|
|Function<? super Object, V>|mapper|转换函数|

## 3. 构造器

基本用法

> ”name“为字段名，”ocean“为要查找的参数
> 
> 即查找字段名name等于ocean的数据

```
QueryWrapper<Employee> wrapper = new QueryWrapper<>();
wrapper.eq("name","ocean");
```

基本方法：

1. eq---等于
   ```
   eq("name", "老王")`--->`name = '老王'
   ```
2. ne---不等于
   ```
   ne("name", "老王")`--->`name <> '老王'
   ```
3. gt---大于
   ```
   gt("age", 18)`--->`age > 18
   ```
4. ge---大于等于
5. lt---小于
6. le---小于等于
7. between---and
   ```
   between("age", 18, 30)`--->`age between 18 and 30
   ```
8. notBetween
   ```
   notBetween("age", 18, 30)`--->`age not between 18 and 30
   ```
9. like---模糊查询
   ```
   例: like("name", "王")--->name like '%王%'
   ```
10. notLike
    ```
    notLike("name", "王")`--->`name not like '%王%'
    ```
11. likeLeft
    ```
    likeLeft("name", "王")`--->`name like '%王'
    ```
12. likeRight
    ```
    likeRight("name", "王")`--->`name like '王%'
    ```
13. isNull
    ```
    isNull("name")`--->`name is null
    ```
14. isNotNull
15. in
    ```
    in("age",{1,2,3})`--->`age in (1,2,3)
    ```
16. notIn
17. insql
    ```
    inSql("id", "select id from table where id < 3")`--->`id in (select id from table where id < 3)
    ```
18. notInSql
19. groupBy
    ```
    groupBy("id", "name")`--->`group by id,name
    ```
20. orderByAsc
    ```
    orderByAsc("id", "name")`--->`order by id ASC,name ASC
    ```
21. having
    ```
    having("sum(age) > 10")`--->`having sum(age) > 10
    ```
    ```
    having("sum(age) > {0}", 11)`--->`having sum(age) > 11
    ```
22. or
    > 主动调用`or`表示紧接着下一个**方法**不是用`and`连接!(不调用`or`则默认为使用`and`连接)
    ```
    eq("id",1).or().eq("name","老王")`--->`id = 1 or name = '老王'
    ```

## 4. 分页查询

1. 在config下添加分页查询插件
   ```
   import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
   import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   
   /**
    * 配置mp的分页插件
    */
   @Configuration
   public class MybatisPlusConfig {
       @Bean
       public MybatisPlusInterceptor mybatisPlusInterceptor() {
           MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
           mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
           return mybatisPlusInterceptor;
       }
   }
   ```
2. 后端服务
   1. 首先创建pageInfo对象
      > page是查询的第几页，pagesize是每一页的数据数量
      > 
      > page索引从1开始！
      ```
      Page<Food> pageInfo = new Page<>(page, pageSize);
      ```
   2. 条件构造器
      
      举例：
      ```
      QueryWrapper wrapper = new QueryWrapper();
      wrapper.orderByAsc("fid");
      wrapper.like("fname", input);
      ```
   3. 进行查询
      
      查询的数据会封装进pageInfo
      
      pageInfo的records属性保存了查询的结果
      ```
      page(pageInfo,wrapper);
      ```
   4. 举例：
      ```
      @Override
      public R<Page> page(int page, int pageSize, String input) {
          // 创建pageInfo对象
          // Page中的records属性保存了查询的结果
          Page<Food> pageInfo = new Page<>(page, pageSize);
          // 条件构造器
          QueryWrapper wrapper = new QueryWrapper();
          wrapper.orderByAsc("fid");
          wrapper.like("fname", input);
          // 进行分页查询
          page(pageInfo,wrapper);
          matchFoodAndFtype(pageInfo.getRecords(),ftypeService.selectAll().getData());
          addPicturePrefix(pageInfo.getRecords());
          for (Food record : pageInfo.getRecords()) {
              record.setFdesc(record.getFdesc().replace("<br>", ""));
          }
          return R.success(pageInfo);
      }
      ```
   5. 前端elementui组件
      1. 组件
         > :total    是数据的总数
         > 
         > :page-size="3"    是每一页的数据个数，total/pagesize可以算出总页数
         ```
         <div class="block" style="margin-left: 25%;margin-top: 50px">
             <el-pagination
                 layout="prev, pager, next"
                 :total=this.dataCount
                 :page-size="3"
                 @current-change="handleCurrentChange">
             </el-pagination>
         </div>
         ```
      2. 获取数据总数
         
         建议挂载到mounted上
         ```
         getDataCount() {
             this.$axios({
                 url: this.$store.state.url+'/food/count',
                 method: 'get'
             }).then(
                 res => {
                     this.dataCount = res.data.data;
                 },
                 err => {
                     console.log(err.message);
                 }
             )
         },
         ```
      3. 向后端请求数据
         > page，pagesize同上，input用于模糊查找，与后端构造器有关
         > 
         > res.data.data.records获取数据
         ```
         getPageData() {
             let data = {page: this.page, pageSize: this.pageSize, input: this.input}
             this.$axios({
                 url: this.$store.state.url+"/food/page",
                 method: 'get',
                 params: data
             }).then(
                 res => {
                     this.tableData = res.data.data.records;
                 }
             )
         },
         ```
      4. 按钮事件-切换页数
         ```
         handleCurrentChange(val) {
             this.page = val;
             this.getPageData();
         },
         ```

## 5. 公共字段自动填充

### 1. 实体类添加注解

```
@TableField(fill = FieldFill.INSERT)
private LocalDateTime createTime;

@TableField(fill = FieldFill.INSERT_UPDATE)
private LocalDateTime updateTime;

@TableField(fill = FieldFill.INSERT)
private Long createUser;

@TableField(fill = FieldFill.INSERT_UPDATE)
private Long updateUser;
```

### 2. 添加配置类

```
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * 自定义元数据对象处理器
 */
@Configuration
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("createUser",BaseContext.getCurrentId());
        metaObject.setValue("updateUser",BaseContext.getCurrentId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser",BaseContext.getCurrentId());
    }
}
```

### 3. 关于线程的运用

问题：配置类无法获得请求中的session对象，故无法实现对updateUser等内容的自动填充

解决方法：利用线程

原理：在一次请求中，拦截器、响应操作等过程均在一个线程中完成。所以，可以通过ThreadLocal对象进行保存数据，然后进行传递。

#### 3.1 封装一个工具类

```
/**
 * 基于ThreadLocal封装工具类
 * 用于保存和获取当前登录用户id
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }
}
```

#### 3.2 在拦截器中添加数据

```
//4. 如果不成立,获取登录状态,判断是否已经登录
// 获取session,如果获取到数据,说明已经登录了
if (request.getSession().getAttribute("employee") != null) {
    System.out.println(request.getSession().getAttribute("employee"));

    ***
    Long empId = (Long) request.getSession().getAttribute("employee");
    BaseContext.setCurrentId(empId);
    ***

    filterChain.doFilter(request,response);
    return;
}
```

#### 3.3 读取

在2的配置类中进行读取

```
metaObject.setValue("updateUser",BaseContext.getCurrentId());
```

# 全局异常处理类

示例

```
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        // 处理重复异常
        // Duplicate entry 'ocean' for key 'idx_username'
        if (ex.getMessage().contains("Duplicate entry")) {
            String[] split = ex.getMessage().split(" ");
            String msg = split[2] + "已存在";
            return R.error(msg);
        }
        return R.error("未知错误");
    }
}
```

# 前后端数据的传输

## 1. get请求

> 当请求为get请求时，使用@PathVariable或者@RequestParam获取参数值，获取路径参数。@PathVariable一般用于获取获取*url/{id}*这种形式的参数；@RequestParam获取查询参数。即*url?name=*这种形式

1. @GetMapping 组合注解，是 @RequestMapping(method = RequestMethod.GET) 的缩写
2. get请求一般通过url传参，如：
http://localhost:4001/api/unit?code=111
后端要获取code参数，可以使用**@RequestParam**注解
   ```
   @RestController
   public class HelloController {
       @RequestMapping(value="/hello",method= RequestMethod.GET)
       public String sayHello(@RequestParam Integer id){
           return "id:"+id;
       }
   }
   ```
3. http://localhost:4001/api/unit/1
   
   后端使用@PathVariable可以接收路径参数1。
   ```
   @RestController
   public class HelloController {
       @RequestMapping(value="/hello/{id}/{name}",method= RequestMethod.GET)
       public String sayHello(@PathVariable("id") Integer id,@PathVariable("name") String name){
           return "id:"+id+" name:"+name;
       }
   }
   ```
4. 当然，也可以通过request获取

## 2. Post请求

1. 后端接收json数据应用@RequestBody或者@requestparam
   > 使用@RequestBody注解接收参数的时候，从名称上来看也就是说要读取的数据在请求体里，前台的Content-Type必须要改为application/json，所以要发post请求，因为Ajax使用的POST，并且发送的是JSON对象。
   ```
   {
       "code": "123",
       "name": "ocean"
   }
   ```
   ```
   //map接收
   @PostMapping(path = "/demo1")
   public void demo1(@RequestBody Map<String, String> person) {
       System.out.println(person.get("name"));
   }
    
   //或者是实体对象接收
   @PostMapping(path = "/demo1")
   public void demo1(@RequestBody Person person) {
       System.out.println(person.toString());
   }
   ```

# 过滤器

> 使用springboot时，在启动类上加上注解
> 
> @ServletComponentScan

## 1. 原理

```
当我们使用过滤器时，过滤器会对游览器的请求进行过滤，过滤器可以动态的分为3个部分，
1.放行之前的代码
2.放行
3.放行后的代码，这3个部分分别会发挥不同作用。

第一部分代码会对游览器请求进行第一次过滤，然后继续执行
第二部分代码就是将游览器请求放行，如果还有过滤器，那么就继续交给下一个过滤器
第三部分代码就是对返回的Web资源再次进行过滤处理
```

## 2. 创建filter

> 继承Filter类

```
import javax.servlet.*;
import java.io.IOException;
 
public class MyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
 
    }
}
```

## 3. 使用filter

```
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
 
@WebFilter("/*")
public class MyFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("对request进行过滤");
        //下面这行代码就是放行
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("对response进行过滤");
    }
}
```

配置拦截路径-注解方式

@WebFilter：参数如下

```
filterName：该filter的名字
initParams：初始化参数
displayName：filter显示名称
servletNames：指定对哪些servlet进行过滤
asyncSupported：是否支持异步模式
urlPatterns：指定拦截路径
value：指定拦截路径
```

> @WebFilter("/*")表示拦截所有请求

## 4. 生命周期

Filter有3个阶段，分别是初始化，拦截和过滤，销毁。

1. 初始化阶段：当服务器启动时，我们的服务器(Tomcat)就会读取配置文件，扫描注解，然后来创建我们的Filter。
2. 拦截和过滤阶段：只要请求资源的路径和拦截的路径相同，那么过滤器就会对请求进行过滤，这个阶段在服务器运行过程中会一直循环。
3. 销毁阶段：当服务器(Tomcat)关闭时，服务器创建的Filter也会随之销毁。

## 5. ~FilterConfig

> 我们在init方法中使用FilterConfig来读取配置的数据库的信息，然后输出。

方法：

```
getFilterName()：获取filter的名称
getServletContext()：获取ServletContext
getInitparamter(String var1)：获取配置的初始参数的值
getInitParamterNames()：获取配置的所有参数名称
```

实例：

```
import javax.servlet.*;
import java.io.IOException;
import java.util.Enumeration;
 
public class MyFilterConfig implements Filter {
 
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("-----------获取全部key:value------------");
        //得到所有配置参数的名字
        Enumeration<String> names = filterConfig.getInitParameterNames();
        while (names.hasMoreElements()) {
            //得到每一个名字
            String name = names.nextElement();
            System.out.println(name+" = "+filterConfig.getInitParameter(name));
        }
        System.out.println("-----------end.....------------");
    }
 
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    }
 
    @Override
    public void destroy() {
    }
}
```

```
xml配置：
    <filter>
        <filter-name>myFilterConfig</filter-name>
        <filter-class>com.clucky.filter.MyFilterConfig</filter-class>
        <init-param>
            <param-name>driver</param-name>
            <param-value>com.mysql.jdbc.Driver</param-value>
        </init-param>
        <init-param>
            <param-name>url</param-name>
            <param-value>jdbc:mysql://localhost:3306/equip_employ_manage?serverTimezone=GMT</param-value>
        </init-param>
        <init-param>
            <param-name>username</param-name>
            <param-value>root</param-value>
        </init-param>
        <init-param>
            <param-name>password</param-name>
            <param-value>root</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>myFilterConfig</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
```

## 6. ~FilterChain

> ServletResquest和ServletResponse可以直接强转成HttpServletRequest和HttpServletResponse，然后使用相应的方法。

放行

filterChain.doFilter(request,response);

## 7. ~项目实例

```
package com.ocean.reggie.filter;

import com.alibaba.fastjson2.JSON;
import com.ocean.reggie.common.R;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;

/**
 * 检查用户是否已经完成了登录
 */
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    // 路径匹配器,支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    // 定义不需要处理的请求路径
    private final String[] urls = new String[] {
            "/employee/login",
            "/employee/logout",
            "/backend/**",
            "/front/**"
    };

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //1. 获取本次请求的URI
        String requestURI = request.getRequestURI();

        //2. 判断本次请求是否需要处理
        boolean check = check(requestURI);

        //3. 如果不需要处理,直接放行
        if (check) {
            filterChain.doFilter(request,response);
            return;
        }

        //4. 如果不成立,获取登录状态,判断是否已经登录
        // 获取session,如果获取到数据,说明已经登录了
        if (request.getSession().getAttribute("employee") != null) {
            filterChain.doFilter(request,response);
            return;
        }

        //5. 如果未登录,通过输出流向客户端页面响应数据
        //ps:返回数据的格式等等由前端规定!
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }

    public boolean check(String uri) {
        for (String url : urls) {
            if (PATH_MATCHER.match(url, uri)) {
                return true;
            }
        }
        return false;
    }
}
```

# thymeleaf

1. 导入依赖
   ```
   <!--thymeleaf依赖-->
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-thymeleaf</artifactId>
       <version>3.1.0-SNAPSHOT</version>
   </dependency>
   ```
2. yml配置
   ```
   spring:
       thymeleaf:
         # 是否开启缓存，开发时设置为false
         cache: false
         # 文件编码
         encoding: UTF-8
         # 模板文件位置
         prefix: classpath:/templates/
         # 模板文件后缀
         suffix: .html
   ```
3. controller示例
   ```
   @Controller
   @RequestMapping("thymeleaf")
   public class BookController {
   
       @GetMapping("/books")
       public String books(Model model) {
           List<Book> books = new ArrayList<>();
           Book book1 = new Book(1, "三国演义", "罗贯中");
           Book book2 = new Book(2, "红楼梦", "曹雪芹");
           books.add(book1);
           books.add(book2);
           model.addAttribute("books", books);
           System.out.println("-----------------------------");
           return "book";
       }
   }
   ```
4. html示例
   ```
   <!DOCTYPE html>
   <html lang="en" xmlns:th="http://www.thymeleaf.org">
   <head>
       <meta charset="UTF-8">
       <title>Title</title>
   </head>
   <body>
       <table border="1">
           <tr>
               <td>图书编号</td>
               <td>图书名称</td>
               <td>图书作者</td>
           </tr>
           <tr th:each="book:${books}">
               <td th:text="${book.id}"></td>
               <td th:text="${book.name}"></td>
               <td th:text="${book.author}"></td>
           </tr>
       </table>
   </body>
   </html>
   ```

# freemarker

## 简单使用

1. 导入依赖
   ```
   <!--freemarker依赖-->
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-freemarker</artifactId>
       <version>2.7.2</version>
   </dependency>
   ```
2. controller方法
   ```
   @GetMapping("/book")
   public String books(HttpServletRequest request) {
       request.setAttribute("flag", true);
       request.setAttribute("createDate", new Date());
       request.setAttribute("age",18); // 数值型
       return "books";
   }
   ```
3. ftl文件
   ```
   ${flag?c}<br>
   ${flag?string}<br>
   ${flag?string("yes", "no")}<br>
   ```

## 常用变量类型

### 1. Boolean类型

```
${flag?c}<br>                            //true
${flag?string}<br>                        //true        
${flag?string("yes", "no")}<br>            //yes
```

### 2. 日期类型

```
${createDate?date} <br>                                            2023年5月9日
${createDate?time} <br>                                            下午9:58:30
${createDate?datetime} <br>                                        2023年5月9日 下午9:58:30
${createDate?string("yyyy年MM月dd日 HH:mm:ss")} <br>              2023年05月09日 21:58:30
```

### 3. 数值类型

```
request.setAttribute("age",18); // 数值型
request.setAttribute("salary",10000); // 数值型
request.setAttribute("avg",0.545); // 浮点型
```

```
${age} <br>        //18
${salary} <br>    //10,000
<#-- 将数值转换成字符串输出 -->
${salary?c} <br>    //10000
<#-- 将数值转换成货币类型的字符串输出 -->
${salary?string.currency} <br> //￥10,000.00
<#-- 将数值转换成百分比类型的字符串输出 -->
${avg?string.percent} <br>        //55%
<#-- 将浮点型数值保留指定小数位输出 （##表示保留两位小数） -->
${avg?string["0.##"]} <br>        //0.55
```

### 4. 字符串类型

直接输出即可

> 字符串的处理交给Java代码，何必给freemarker呢

### 5. sequence类型

```
List<Book> books = new ArrayList<>();
books.add(new Book(1, "三国演义", "罗贯中"));
books.add(new Book(2, "红楼梦", "曹雪芹"));
request.setAttribute("books", books);
```

```
<#list books as book>
    ${book.id}&nbsp;
    ${book.name}&nbsp;
    ${book.author}<br>
</#list>
```

### 6. hash类型

```
HashMap<String, String> map = new HashMap<>();
map.put("first", "1");
map.put("second", "2");
request.setAttribute("map", map);
```

```
<#list map?keys as key>
    ${key} -- ${map[key]}
    <br>
</#list>
```

## 常用命令

1. if
   ```
   <#if condition>
           ...
       <#elseif condition2>
           ...
       <#elseif condition3>
           ...
       <#else>
           ...
   </#if>
   ```
2. list
   ```
   格式1：
   <#list sequence as item>
   </#list>
   格式2：
   <#list sequence as item>
       <#else>
       当没有选项时，执行else指令
   </#list>
   ```

# 对JSON数据处理

## 1. 常规处理

1. @ResponseBody：在类名上添加
2. @RestController：相当于对整个controller控制类，添加了@ResponseBody

## 2. fastjson使用

1. Java转json
   ```
   JSON.toJSONString(对象等)
   ```
2. json转Java

> json字符串转简单java对象

```
/* json字符串转简单java对象
* 字符串：{"password":"123456","username":"dmego"}
*/
String jsonStr1 = "{'password':'123456','username':'ggf'}";
// 调用parseObject()
User user = JSON.parseObject(jsonStr1, User.class);
```

> json字符串转List<Object>对象

```
/*
* json字符串转List<Object>对象
* 字符串：[{"password":"123123","username":"zhangsan"},
*        {"password":"321321","username":"lisi"}]
*/
String jsonStr2 = "[{'password':'123123','username':'zhangsan'},{'password':'321321','username':'lisi'}]";
// 调用parseArray()将字符串转为集合
List<User> users = JSON.parseArray(jsonStr2, User.class);
```

3. 对json串的操作
   ```
   /**
   * 对json串的操作
   */
   @Test
   public void operateJson() {
       // 读取本地json文本
       String jsonStr = FileUtil.readUtf8String(new File("caipu.json"));
       // 创建json对象
       JSONObject jsonObj = JSONObject.parseObject(jsonStr);
       // 操作json内容
       // 获取响应码resultcode
       System.out.println(jsonObj.get("resultcode"));
       // 获取响应信息reason
       System.out.println(jsonObj.getString("reason"));
       // 获取data
       JSONObject resJsonObj = (JSONObject)jsonObj.get("result");
       System.out.println(resJsonObj.getString("data"));
   }
   ```
   
   结果
   ```
   200
   Success
   [{"albums":["http://juheimg.oss-cn-hangzhou.aliyuncs.com/cookbook/t/7/6269_........
   ```

> 注意！！！！
> 
> 如果数据库的主码使用19位的雪花算法，传给前端会出错！！！
> 
> 因为前端只能接收16位！！！
> 
> 因此要用字符串进行传递，所以需要自定义json传递规则

## 3. 自定义json传递方式

​    详见mvc配置类！

# MVC配置类

## 简介

> **WebMvcConfigurerAdapter
WebMvcConfigurer
WebMvcConfigurationSupport
WebMvcAutoConfiguration**

1. WebMvcConfigurer 为接口
2. WebMvcConfigurerAdapter 是 WebMvcConfigurer 的实现类大部分为空方法，已弃用。
3. WebMvcConfigurationSupport 是mvc的基本实现并包含了WebMvcConfigurer接口中的方法
4. WebMvcAutoConfiguration 是mvc的自动装在类并部分包含了WebMvcConfigurer接口中的方法

> ps：如果在springboot项目中没有使用到以上类，那么会自动启用WebMvcAutoConfiguration类做自动加载；

继承WebMvcConfigurationSupport

```
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

}
```

## 配置静态资源映射

```
/**
 * 设置静态资源映射
 * @param registry
 */
@Override
protected void addResourceHandlers(ResourceHandlerRegistry registry) {
    log.info("静态资源映射启动");
    registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
    registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
}
```

## 自定义JSON数据格式

> 自定义的目的：
> 
> 如果数据库的主码使用19位的雪花算法，传给前端会出错！！！
> 
> 因为前端只能接收16位！！！
> 
> 因此要用字符串进行传递，所以需要自定义json传递规则

```
@Override
protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    //创建消息转换器对象
    MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
    //设置对象转换器，使用jackson将Java对象转为json
    messageConverter.setObjectMapper(new JacksonObjectMapper());
    //将上面的自定义转换器添加到转换器集合中,index = 0是为了优先使用我们的转换器
    converters.add(0,messageConverter);
}
```

```
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

/**
 * 对象映射器:基于jackson将Java对象转为json，或者将json转为Java对象
 * 将JSON解析为Java对象的过程称为 [从JSON反序列化Java对象]
 * 从Java对象生成JSON的过程称为 [序列化Java对象到JSON]
 */
public class JacksonObjectMapper extends ObjectMapper {

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    public JacksonObjectMapper() {
        super();
        //收到未知属性时不报异常
        this.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        //反序列化时，属性不存在的兼容处理
        this.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        SimpleModule simpleModule = new SimpleModule()
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)))
                .addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)))
                .addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)))
                .addSerializer(BigInteger.class, ToStringSerializer.instance)
                .addSerializer(Long.class, ToStringSerializer.instance)
                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)))
                .addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)))
                .addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
        //注册功能模块 例如，可以添加自定义序列化器和反序列化器
        this.registerModule(simpleModule);
    }
}
```

# 文件上传

> 此处将图片保存到本地文件

## 1. 配置文件

> 在配置文件中添加图片保存的绝对路径

```
website:
    localImagePath: D:/git/dailyFile/courseWork/JavaWeb/website_work/src/images
```

## 2. 添加映射路径

> 在WebMvcConfig中添加

```
registry.addResourceHandler("/image/**").addResourceLocations("file:D:/git/dailyFile/courseWork/JavaWeb/website_work/src/images/");
```

意思是，当访问http://localhost:8080/image/**时，返回绝对路径下的图片

## 3. controller类

```
@Value("${website.localImagePath}")    //D:/git/dailyFile/courseWork/JavaWeb/website_work/src/images
private String basePath;

@Value("${website.imagePath}")    //http://localhost:8080/image
private String imagePath;

@PostMapping("/food/upload")
public R<String> upload(@RequestPart("file") MultipartFile file) {
    String newPath = basePath + "/foodImages/";
    //获取原始文件名，以此获取文件后缀
    String originalFilename = file.getOriginalFilename();
    String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
    // 通过uuid生成随机文件名
    String fileName = UUID.randomUUID() + suffix;
    // 判断目标目录是否存在，若不存在就创建
    File dir = new File(newPath);
    if (!dir.exists()) {
        dir.mkdirs();
    }
    try {
        System.out.println(newPath + fileName);
        file.transferTo(new File(newPath + fileName));
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
    return R.success(imagePath + "/foodImages/" + fileName);
}
```

## 4. 前端axios异步提交

```
let formData = new FormData();
formData.append("file", this.$refs.file.files[0])
this.$axios({
    url: this.$store.state.url + '/common/food/upload',
    method: 'post',
    headers: {
    'Content-Type': 'multipart/form-data',
    },
    data: formData
}).then(
    res => {
        this.foodInfo.fpic = res.data.data;
    }
)
```

1. 提交文件，需要修改'Content-Type': 'multipart/form-data'
2. 文件通过formData上传
   > let formData = new FormData();
formData.append("file", this.$refs.file.files[0])
3. 注意！！！
   
   数据用data传输，不是params！！！
   > data: formData

# 邮件系统

> 推荐学习网站
> 
> https://www.cnblogs.com/tianmengwei/p/5058088.html#:~:text=MimeMessages

## 1. 导入依赖

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```

## 2. 代码使用

使用SimpleMailMessage

```
JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

javaMailSender.setHost("smtp.qq.com");  //设置发送方的邮箱格式
javaMailSender.setProtocol("smtp");     //设置协议
javaMailSender.setUsername("1252074183@qq.com");    //发送方账号
javaMailSender.setPassword("vennihzjcgaxjbji");     //发送方授权码
javaMailSender.setPort(587);                        //端口号
javaMailSender.setDefaultEncoding("UTF-8");         //编码

SimpleMailMessage message = new SimpleMailMessage();
message.setTo("20216928@stu.neu.edu.cn");           //收件方邮箱
message.setFrom("1252074183@qq.com");               //必须与发送发账号相同！！！
message.setSubject("测试！");                        //标题
message.setText("hello mail!!!");                   //正文内容

Properties properties = new Properties();           //配置
properties.put("mail.smtp.auth", "true");
properties.put("mail.smtp.timeout", "25000");

javaMailSender.setJavaMailProperties(properties);
javaMailSender.send(message);
```

## 3. 发送html格式

使用MimeMessage

```
JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

javaMailSender.setHost("smtp.qq.com");  //设置发送方的邮箱格式
javaMailSender.setProtocol("smtp");     //设置协议
javaMailSender.setUsername("1252074183@qq.com");    //发送方账号
javaMailSender.setPassword("vennihzjcgaxjbji");     //发送方授权码
javaMailSender.setPort(587);                        //端口号
javaMailSender.setDefaultEncoding("UTF-8");         //编码

MimeMessage message = javaMailSender.createMimeMessage();
MimeMessageHelper helper = new MimeMessageHelper(message, true);//通过helper配置

helper.setTo("20216928@stu.neu.edu.cn");           //收件方邮箱
helper.setFrom("1252074183@qq.com");               //必须与发送发账号相同！！！
helper.setSubject("测试！");                        //标题
helper.setText("<h1>hello mail!!!</h1>", true);//正文内容，true为发送html格式

Properties properties = new Properties();           //配置
properties.put("mail.smtp.auth", "true");
properties.put("mail.smtp.timeout", "25000");
javaMailSender.setJavaMailProperties(properties);

javaMailSender.send(message);
```

# excel处理

## 写

### 1. 导入依赖

```
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>easyexcel</artifactId>
    <version>3.1.1</version>
</dependency>
```

### 2. 实体类

> @ExcelProperty("用户编号")    列名    还有个index属性，指定列的顺序
> 
> @ExcelIgnore    排除的列名

```
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @ExcelProperty("用户编号")
    private Integer userId;
    @ExcelProperty("姓名")
    private String userName;
    @ExcelProperty("性别")
    private String gender;
    @ExcelProperty("工资")
    private Double salary;
    @ExcelProperty("入职时间")
    private Date hireDate;

    // 排除的属性!
    @ExcelIgnore
    private String desc;
}
```

### 3. @ExcelProperty详解

属性：

1. value：列名，不写默认变量名
   
   如果写成一个数组
   ```
   @ExcelProperty(value = {"group1", "用户编号"}, index = 0)
   private Integer userId;
   @ExcelProperty(value = {"group1", "姓名"}, index = 1)
   private String userName;
   ```
   
   会实现分组效果
2. index：列顺序

### 4. 格式化

```
@NumberFormat(value = "###.#") // 数字格式化,保留1位小数
private Double salary;
@ExcelProperty(value = "入职时间", index = 2)
@DateTimeFormat(value = "yyyy年MM月dd日 HH时mm分ss秒") // 日期格式化
private Date hireDate;
```

### 5. 控制excel样式

```
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@HeadRowHeight(value = 30) // 头部行高
@ContentRowHeight(value = 25) // 内容行高
@ColumnWidth(value = 20) // 列宽
// 头背景设置成红色 IndexedColors.RED.getIndex()
@HeadStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 10)
// 头字体设置成20, 字体默认宋体
@HeadFontStyle(fontName = "宋体", fontHeightInPoints = 20)
// 内容的背景设置成绿色  IndexedColors.GREEN.getIndex()
@ContentStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 17)
// 内容字体设置成20, 字体默认宋体
@ContentFontStyle(fontName = "宋体", fontHeightInPoints = 20)
public class DemoStyleData {

    // 字符串的头背景设置成粉红 IndexedColors.PINK.getIndex()
    @HeadStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 14)
    // 字符串的头字体设置成20
    @HeadFontStyle(fontHeightInPoints = 30)
    // 字符串的内容背景设置成天蓝 IndexedColors.SKY_BLUE.getIndex()
    @ContentStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 40)
    // 字符串的内容字体设置成20,默认宋体
    @ContentFontStyle(fontName = "宋体", fontHeightInPoints = 20)
    @ExcelProperty(value = "字符串标题")
    private String string;
    @ExcelProperty(value = "日期标题")
    private Date date;
    @ExcelProperty(value = "数字标题")
    private Double doubleData;
    // lombok 会生成getter/setter方法
}
```

## 读

实体类与上面一样

```
List<User> users = new ArrayList<>();
EasyExcel.read(path, User.class, new AnalysisEventListener() {
    /*每读取一行就调用一次*/
    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {
        User user = (User) o;
        users.add(user);
    }
    /*全部读取完后调用*/
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println(users);
        for (User user : users) {
            System.out.println(user);
        }
    }
}).sheet().doRead();
```

若要读取全部sheet

```
.sheet().doRead()改成.doReadAll()
```

# 拦截器

1. 创建拦截器
   ```
   import jakarta.servlet.http.HttpServletRequest;
   import jakarta.servlet.http.HttpServletResponse;
   import org.springframework.web.servlet.HandlerInterceptor;
   import org.springframework.web.servlet.ModelAndView;
   
   public class MyInterceptor implements HandlerInterceptor {
       @Override
       public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
           System.out.println("pre");
           return HandlerInterceptor.super.preHandle(request, response, handler);
       }
   
       @Override
       public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
           System.out.println("after");
           HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
       }
   
       @Override
       public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
           System.out.println("complete");
           HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
       }
   }
   ```
2. 加入配置类
   ```
   @Override
       protected void addInterceptors(InterceptorRegistry registry) {
           registry.addInterceptor(new MyInterceptor())
                   .addPathPatterns("/**");
       }
   ```

# AOP

## 实例

1. 导入依赖
   ```
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-aop</artifactId>
       <version>3.0.6</version>
   </dependency>
   ```
2. 配置aop类
   ```
   import org.aspectj.lang.ProceedingJoinPoint;
   import org.aspectj.lang.annotation.Around;
   import org.aspectj.lang.annotation.Aspect;
   import org.aspectj.lang.annotation.Pointcut;
   import org.springframework.stereotype.Component;
   
   @Aspect
   @Component
   public class AopPrac {
       @Pointcut("execution(* com.ocean.aop.Controller.*(..))")
       public void aop1(){}
   
       @Around("aop1()")
       public void around(ProceedingJoinPoint joinPoint) {
           System.out.println("pre test1...");
           try {
               joinPoint.proceed();
           } catch (Throwable e) {
               throw new RuntimeException(e);
           }
           System.out.println("after test1...");
       }
   }
   ```

## 知识点

1. execution表达式
   - 修饰符-可选
   - 返回值类型-必选   *表示任意返回值
   - 方法名-必选   *表示任意方法
   - 参数-必选
     - ()代表没有参数
     - (..)代表匹配任意数量、任意类型参数
     - (java.lang.String)   匹配一个String类型参数
     - (java.lang.String)   匹配任意String类型参数
   - 异常-可选
2. **ProceedingJoinPoint**用法
   ```
   //拦截的实体类
   Object target = point.getTarget();
   
   //拦截的方法名称
   String methodName = point.getSignature().getName();
   
   //拦截的方法参数
   Object[] args = point.getArgs();
   
   //拦截的放参数类型
   Class[] parameterTypes = ((MethodSignature)point.getSignature()).getMethod().getParameterTypes();
   
   object = point.proceed(); //目标方法的执行
   ```

# Restful风格

1. 请求方式
   |请求方式|含义|
   |--|--|
   |GET（SELECT）|从服务器取出资源（一项或多项）|
   |POST（CREATE）|在服务器新建一个资源|
   |PUT（UPDATE）|在服务器更新资源（更新完整资源）|
   |PATCH（UPDATE）|在服务器更新资源， PATCH更新个别属性|
   |DELETE（DELETE）|从服务器删除资源|
2. 相关注解
   
   注解                               作用
@RestController         由 @Controller + @ResponseBody组成（返回 JSON 数据格式）
@PathVariable            URL 中的 {xxx} 占位符可以通过@PathVariable(“xxx“) 绑定到控制器处理方法的形参中
@RequestMapping    注解用于请求地址的解析，是最常用的一种注解
@GetMapping            查询请求
@PostMapping           添加请求
@PutMapping             更新请求
@DeleteMapping        删除请求
@RequestParam        将请求参数绑定到你控制器的方法参数上（是springmvc中接收普通参数的注解）
3. 举例
   - ```
     @RestController
     @RequestMapping("/users") // 下面的每个控制器方法的请求路径都有前缀 /users
     public class UserController
     {
      @GetMapping("/{id}")
      public String getById(@PathVariable Integer id)
         {
             return "getById";
         }
     }
     ```
   - ```
     @RestController
     @RequestMapping("/students")
     public class StudentController {
     ​
         // Mock data - replace with database queries later
         private static List<Student> students = new ArrayList<>(Arrays.asList(
                 new Student(1, "Alice", 20),
                 new Student(2, "Bob", 21),
                 new Student(3, "Charlie", 22)
         ));
     ​
         // GET /students - get all students
         @GetMapping("")
         public List<Student> getAllStudents() {
             return students;
         }
     ​
         // GET /students/{id} - get a student by id
         @GetMapping("/{id}")
         public Student getStudentById(@PathVariable int id) {
             for (Student s : students) {
                 if (s.getId() == id) {
                     return s;
                 }
             }
             return null;  // Return null if student not found
         }
     ​
         // POST /students - create a new student
         @PostMapping("")
         public ResponseEntity<String> createStudent(@RequestBody Student student) {
             students.add(student);
             return ResponseEntity.status(HttpStatus.CREATED).build();
         }
     ​
         // PUT /students/{id} - update an existing student
         @PutMapping("/{id}")
         public ResponseEntity<String> updateStudent(@PathVariable int id, @RequestBody Student updatedStudent) {
             for (int i = 0; i < students.size(); i++) {
                 if (students.get(i).getId() == id) {
                     students.set(i, updatedStudent);
                     return ResponseEntity.status(HttpStatus.OK).build();
                 }
             }
             return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Return 404 if student not found
         }
     ​
         // DELETE /students/{id} - delete a student by id
         @DeleteMapping("/{id}")
         public ResponseEntity<String> deleteStudentById(@PathVariable int id) {
             for (int i = 0; i < students.size(); i++) {
                 if (students.get(i).getId() == id) {
                     students.remove(i);
                     return ResponseEntity.status(HttpStatus.OK).build();
                 }
             }
             return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Return 404 if student not found
         }
     }
     ```

<br/>

# Slf4j

1. 创建自定义注解
   ```
   package com.ocean.log.anotation;
   
   import java.lang.annotation.ElementType;
   import java.lang.annotation.Retention;
   import java.lang.annotation.RetentionPolicy;
   import java.lang.annotation.Target;
   
   @Target(ElementType.METHOD)
   @Retention(RetentionPolicy.RUNTIME)
   public @interface MyLog {
       String value() default "";
   }
   ```

2. aop切片类
   ```
   package com.ocean.log;
   
   import com.ocean.log.anotation.MyLog;
   import lombok.extern.slf4j.Slf4j;
   import org.aspectj.lang.ProceedingJoinPoint;
   import org.aspectj.lang.annotation.Around;
   import org.aspectj.lang.annotation.Aspect;
   import org.aspectj.lang.annotation.Pointcut;
   import org.springframework.stereotype.Component;
   import java.lang.reflect.Method;
   import java.util.Arrays;
   
   @Component
   @Slf4j
   @Aspect
   public class MyLogAspect {
       @Pointcut("@annotation(com.ocean.log.anotation.MyLog)")
       public void logPointCut() {
       }
   
       @Around("logPointCut()")
       public Object around(ProceedingJoinPoint point) throws Throwable {
           Object result = null;
           try {
               result = point.proceed();
           } catch (Throwable throwable) {
               // 获取类路径
               String classPath = point.getThis().toString().split("@")[0];
               // 通过反射获取类
               Class<?> aClass = Class.forName(classPath);
               // 获取方法（注意，此方法要求方法名唯一！）
               for (Method method : aClass.getMethods()) {
                   if (method.getName().equals(point.getSignature().getName())) {
                       MyLog annotation = method.getAnnotation(MyLog.class);
                       log.error("出错方法功能: {}", annotation.value());
                   }
               }
               log.error("错误的方法：{}", point.getSignature());
               log.error("传入的参数: {}", Arrays.toString(point.getArgs()));
               log.error("错误原因: {}", throwable.getMessage());
           }
           return result;
       }
   }
   ```

3. 在控制类中使用
   ```
   package com.ocean.controller;
   
   import com.ocean.log.anotation.MyLog;
   import jakarta.websocket.server.PathParam;
   import org.springframework.web.bind.annotation.GetMapping;
   import org.springframework.web.bind.annotation.RestController;
   
   @RestController
   public class TestController {
       @MyLog("测试功能")
       @GetMapping("/test")
       public String test(@PathParam("name") String name, @PathParam("age") Integer age) {
           int[] a = new int[3];
           System.out.println(a[5]);
           return "test";
       }
   }
   ```