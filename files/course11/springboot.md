# 一、springboot简介
## 简介：
	1.简化spring开发的一个框架
	2.整个spring技术栈的一个大整合(spring全家桶)
	3.javaEE开发的一站式解决方案

## 优点：
	1.快速创建独立运行的spring项目以及与主流框架继承
	2.使用嵌入式servlet容器，应用无需打成war包
	3.starters自动依赖与版本控制
	4.大量的自动配置，简化开发，也可以修改默认值
	5.无需xml，无代码生成，开箱即用
	6.准生产环境的运行时应用监控等


# 二、springboot快速开始

## 1.创建maven项目  2.3.0

### a. pom.xml 添加

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.3.0.RELEASE</version>
</parent>
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```

### b.创建 启动器 org.bhd.StraterApp.java

```java
@SpringBootApplication
public class StraterApp {

    public static void main(String[] args) {
        SpringApplication.run(StraterApp.class, args);
    }
}
```

### c.创建controller

```java
@Controller
public class HelloController {

    @ResponseBody
    @RequestMapping("/sayHello")
    public String sayHello(){
        return "hello,springboot!";
    }
}
```

---


```xml
<!-- pom.xml 中 这个插件，可以将应用打包成一个可执行的jar包；-->
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```


# 三、使用Spring Initializer快速创建Spring Boot项目


IDE都支持使用Spring的项目创建向导快速创建一个Spring Boot项目；

选择我们需要的模块；向导会联网创建Spring Boot项目；

默认生成的Spring Boot项目；

- 主程序已经生成好了，我们只需要我们自己的逻辑
- resources文件夹中目录结构
  - static：保存所有的静态资源； js css  images；
  - templates：保存所有的模板页面；（Spring Boot默认jar包使用嵌入式的Tomcat，默认不支持JSP页面）；
    可以使用模板引擎（freemarker、thymeleaf）；
  - application.properties：Spring Boot应用的配置文件；
    可以修改一些默认设置；



# 四、访问静态资源

**  以下两种方式都可以访问静态资源 **

## 1. SpringBoot 从 classpath/static 的目录
	注意目录名称必须是 src/main/resources/static
## 2. ServletContext 根目录下
	在 src/main/webapp 目录名称必须要 webapp


# 五、Spring Boot视图层技术

## 5.1 整合jsp

### 5.1.1 pom.xml添加
```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.3.0.RELEASE</version>
</parent>

<dependencies>
    <!-- springBoot 的启动器 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <!-- jstl -->
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>jstl</artifactId>
    </dependency>
    <!-- jasper -->
    <dependency>
        <groupId>org.apache.tomcat.embed</groupId>
        <artifactId>tomcat-embed-jasper</artifactId>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

### 5.1.2 在resources中创建 springBoot 的全局配置文件，application.properties

```properties
spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp
```

### 5.1.3 创建 controller ，jsp, 启动器 



## 5.2 整合freemarker

###　5.2.1 修改pom.xml

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.3.0.RELEASE</version>
</parent>

<dependencies>
    <!-- springBoot 的启动器 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <!-- freemarker 启动器的坐标 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-freemarker</artifactId>
    </dependency>
</dependencies>
```
### 5.2.2 编写视图
#### 注意：springBoot 要求模板形式的视图层技术的文件必须要放到 src/main/resources/templates目录下 创建 hello.ftl 

```html
<html>
    <head>
        <title>展示用户数据</title>
        <meta charset="utf-9"></meta>
    </head>
    <body>
        <table border="1" align="center" width="50%">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Age</th>
            </tr>
            <#list list as user >
                <tr>
                    <td>${user.userid}</td>
                    <td>${user.username}</td>
                    <td>${user.userage}</td>
                </tr>
            </#list>
        </table>
    </body>
</html>
```
### 5.2.3 controller ，启动器不变

```java
@Controller
public class UserController {

    @RequestMapping("/sayHello")
    public String sayHello(ModelMap model){

        Map<String,String> user = new HashMap<>();
        user.put("userid","9527");
        user.put("username","xiaoli");
        user.put("userage","22");

        model.put("user",user);

        return "hello";
    }
}
```


## 5.3 整合 Thymeleaf
### 5.3.0 application.properties(可以不添加，不是必须)
```properties
spring.mvc.view.prefix=/templates/
spring.mvc.view.suffix=.html
```
### 5.3.1 pom.xml
```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.3.0.RELEASE</version>
</parent>
		
<dependencies>
    <!-- springBoot 的启动器 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <!-- springBoot 的启动器 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
</dependencies>
```
### 5.3.2 创建存放视图的目录
目录位置：src/main/resources/templates
templates：该目录是安全的。意味着该目录下的内容是不允许外界直接访问的。

```html
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Thymeleaf入门</title>
</head>
<body>
    <span th:text="Hello"></span><hr/>
    <span th:text="${user.userid}"></span><hr/>
    <span th:text="${user.username}"></span><hr/>
    <span th:text="${user.userage}"></span><hr/>
</body>
</html>
```

### 5.3.3 Controller

```java
@Controller
public class UserController {

    @RequestMapping("/sayHello")
    public String sayHello(ModelMap model){

        Map<String,String> user = new HashMap<>();
        user.put("userid","9527");
        user.put("username","xiaoli");
        user.put("userage","22");

        model.put("user",user);

        return "hello";
    }
}
```

### 5.3.4 解决异常
##### 在1.5.10中有异常，在2.3.0中没有这个异常
```	html
<properties>
    <thymeleaf.version>3.0.2.RELEASE</thymeleaf.version>
    <thymeleaf-layout-dialect.version>2.0.4</thymeleaf-layout-dialect.version>
</properties>
```


# 六 Thymeleaf 语法详解

## 6.1 变量输出与字符串操作
### 6.1.1 th:text
```html
th:text 在页面中输出值
```
### 6.1.2 th:value
```html
th:value 可以将一个值放入到 input 标签的 value 中
```
### 6.1.3 判断字符串是否为空
### Thymeleaf 内置对象
#### 注意语法：
1，调用内置对象一定要用#
2，大部分的内置对象都以 s 结尾 strings、numbers、dates
`${#strings.isEmpty(key)}`
判断字符串是否为空，如果为空返回 true，否则返回 false
`${#strings.contains(msg,'T')}`
判断字符串是否包含指定的子串，如果包含返回 true，否则返回 false
`${#strings.startsWith(msg,'a')}`
判断当前字符串是否以子串开头，如果是返回 true，否则返回 false
`${#strings.endsWith(msg,'a')}`
判断当前字符串是否以子串结尾，如果是返回 true，否则返回 false
`${#strings.length(msg)}`
返回字符串的长度
`${#strings.indexOf(msg,'h')}`
查找子串的位置，并返回该子串的下标，如果没找到则返回-1
`${#strings.substring(msg,13)}`
`${#strings.substring(msg,13,15)}`
截取子串，用户与 jdk String 类下 SubString 方法相同
`${#strings.toUpperCase(msg)}`
`${#strings.toLowerCase(msg)}`
字符串转大小写。

## 6.2 日期格式化处理
`${#dates.format(key)}`
格式化日期，默认的以浏览器默认语言为格式化标准
`${#dates.format(key,'yyy/MM/dd')}`
按照自定义的格式做日期转换

```
${#dates.year(key)}
${#dates.month(key)}
${#dates.day(key)}
year：取年
Month：取月
Day：取日
```
## 6.3 条件判断
### 6.3.1th:if
```html
<span th:if="${sex} == '男'">
	性别：男
</span>
<span th:if="${sex} == '女'">
	性别：女
</span>
```
### 6.3.2th:switch
```html
<div th:switch="${id}">
    <span th:case="1">ID 为 1</span>
    <span th:case="2">ID 为 2</span>
    <span th:case="3">ID 为 3</span>
</div>
```
## 6.4 迭代遍历
### 6.4.1th:each
```java
@RequestMapping("/show3")
public String showInfo3(Model model){
    List<Users> list = new ArrayList<>();
    list.add(new Users(1,"张三",20));
    list.add(new Users(2,"李四",22));
    list.add(new Users(3,"王五",24));
    model.addAttribute("list", list);
    return "index3";
}
```
```html
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Age</th>
    </tr>
    <tr th:each="u : ${list}">
        <td th:text="${u.userid}"></td>
        <td th:text="${u.username}"></td>
        <td th:text="${u.userage}"></td>
    </tr>
</table>
```
### 6.4.2ht:each 状态变量
```java
@RequestMapping("/show3")
public String showInfo3(Model model){
    List<Users> list = new ArrayList<>();
    list.add(new Users(1,"张三",20));
    list.add(new Users(2,"李四",22));
    list.add(new Users(3,"王五",24));
    model.addAttribute("list", list);
    return "index3";
}
```
```html
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Age</th>
        <th>Index</th>
        <th>Count</th>
        <th>Size</th>
        <th>Even</th>
        <th>Odd</th>
        <th>First</th>
        <th>lase</th>
    </tr>
    <tr th:each="u,var : ${list}">
        <td th:text="${u.userid}"></td>
        <td th:text="${u.username}"></td>
        <td th:text="${u.userage}"></td>
        <td th:text="${var.index}"></td>
        <td th:text="${var.count}"></td>
        <td th:text="${var.size}"></td>
        <td th:text="${var.even}"></td>
        <td th:text="${var.odd}"></td>
        <td th:text="${var.first}"></td>
        <td th:text="${var.last}"></td>
    </tr>
</table>
```
### 状态变量属性
1.index:当前迭代器的索引 从 0 开始
2.count:当前迭代对象的计数 从 1 开始
3.size:被迭代对象的长度
4.even/odd:布尔值，当前循环是否是偶数/奇数 从 0 开始
5.first:布尔值，当前循环的是否是第一条，如果是返回 true 否则返回 false
6.last:布尔值，当前循环的是否是最后一条，如果是则返回 true 否则返回 false

### 6.4.3th:each 迭代 Map
```java
@RequestMapping("/show4")
public String showInfo4(Model model){
    Map<String, Users> map = new HashMap<>();
    map.put("u1", new Users(1,"张三",20));
    map.put("u2", new Users(2,"李四",22));
    map.put("u3", new Users(3,"王五",24));
    model.addAttribute("map", map);
    return "index4";
}
```
```html
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Age</th>
        </tr>
        <tr th:each="maps : ${map}">
        <td th:text="${maps}"></td>
    </tr>
</table>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Age</th>
    </tr>
    <tr th:each="maps : ${map}">
        <td th:each="entry:${maps}" th:text="${entry.value.userid}" ></td>
        <td th:each="entry:${maps}" th:text="${entry.value.username}"></td>
        <td th:each="entry:${maps}" th:text="${entry.value.userage}"></td>
    </tr>
</table>
```

## 6.5 域对象操作
### 6.5.1HttpServletRequest
```java
request.setAttribute("req", "HttpServletRequest");
Request:<span th:text="${#httpServletRequest.getAttribute('req')}"></span><br/>
```
### 6.5.2HttpSession
```java
request.getSession().setAttribute("sess", "HttpSession");
Session:<span th:text="${session.sess}"></span><br/>
```
### 6.5.3ServletContext
```java
request.getSession().getServletContext().setAttribute("app","Application");
Application:<span th:text="${application.app}"></span>
```
## 6.6 URL 表达式
```html
th:href
th:src
```
### 6.6.1url 表达式语法
```
基本语法：@{}
```
### 6.6.2URL 类型
#### 6.6.2.1 绝对路径
```html
<a th:href="@{http://www.baidu.com}">绝对路径</a><br/>
```
#### 6.6.2.2 相对路径
1)相对于当前项目的根
相对于项目的上下文的相对路径
`<a th:href="@{/show}">相对路径</a>`
2) 相对于服务器路径的根
`<a th:href="@{~/project2/resourcename}">相对于服务器的根</a>`

### 6.6.3 在 url 中实现参数传递
`<a th:href="@{/show(id=1,name=zhagnsan)}">相对路径-传参</a>`

### 6.6.4 在 url 中通过 restful 风格进行参数传递
`<a th:href="@{/path/{id}/show(id=1,name=zhagnsan)}"> 相 对 路 径 - 传 参 -restful</a>`


# 七、SpringBoot集成JDBCTemplate

## application.porperties

```properties
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/dept
spring.datasource.username=root
spring.datasource.password=root
```

## pom.xml

```xml
<dependencies>
        <!-- springBoot 的启动器 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- springBoot 的启动器 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>

        <!-- mysql 数据库驱动 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>

    </dependencies>
```


## service

```java
@Service
public class EmpService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<Emp> list() {
        RowMapper<Emp> rowMapper = new BeanPropertyRowMapper<Emp>(Emp.class);
        List<Emp> emps = jdbcTemplate.query("select * from emp", rowMapper);//最后一个参数为id值
        return emps;
    }

    public void addEmp(Emp emp) {
        jdbcTemplate.update("insert into emp values(?,?,?,?)",null,"wf","fdsa","234234.1");
    }
}
```

# 八、SpringBoot集成JPA

## pom.xml
```xml
<dependencies>
        <!-- springBoot 的启动器 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- springBoot 的启动器 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
		<!-- jpa -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <!-- mysql 数据库驱动 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>

    </dependencies>
```

## application.porperties
```properties
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/dept
spring.datasource.username=root
spring.datasource.password=root
```


## entity

```java
@Entity
public class Emp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="name")
    private String name;
    @Column(name="job")
    private String job;
    @Column(name="salary")
    private Double salary;

    public Emp() {
    }

    public Emp(Integer id, String name, String job, Double salary) {
        this.id = id;
        this.name = name;
        this.job = job;
        this.salary = salary;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", job='" + job + '\'' +
                ", salary=" + salary +
                '}';
    }
}
```


## service
```java
@Service
public class EmpService {

    @Autowired
    private EmpDao empDao;

    public List<Emp> list() {

        List<Emp> emps = empDao.findAll();//最后一个参数为id值
        return emps;
    }
}
```

## dao

```java
import com.xbky.entity.Emp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface EmpDao extends JpaRepository<Emp,Integer> {
}
```



# 九、SpringBoot集成Mybatis

## pom.xml
```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.3.0.RELEASE</version>
</parent>

<dependencies>
    <!-- springBoot 的启动器 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <!-- web 启动器 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
    <!-- Mybatis 启动器 -->
    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
        <version>1.1.1</version>
    </dependency>
    <!-- mysql 数据库驱动 -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>
    <!-- druid 数据库连接池 -->
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>druid</artifactId>
        <version>1.0.9</version>
    </dependency>
</dependencies>
```


## 添加 application.properties 全局配置文件
```properties
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/dept
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
mybatis.mapper-locations=classpath*:mapper/*.xml
mybatis.type-aliases-package=com.xbky.entity
```

## Mapper类

```java
@Mapper
public interface EmpMapper {

    public List<Emp> list();

}
```

## service类

```java
@Service
public class EmpService {

    @Autowired
    private EmpMapper empMapper;

    public List<Emp> list() {

        List<Emp> emps = empMapper.list();
        return emps;
    }
}
```

## 启动类

```java
@SpringBootApplication
//@MapperScan 用户扫描MyBatis的Mapper
@MapperScan("com.xbky.mapper") 
接口
public class App {
    public static void main(String[] args) {
    	SpringApplication.run(App.class, args);
    }
}
```

# 十、Mybatis集成分页插件

## 在 pox.xml 中添加PageHelper分页插件依赖

```xml
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper-spring-boot-starter</artifactId>
    <version>1.2.5</version>
</dependency>

```
## 配置application.yml

```properties
logging.level.com.xbky.mapper=DEBUG
pagehelper.helper-dialect=mysql
pagehelper.reasonable=true
pagehelper.support-methods-arguments=true
pagehelper.params=count=countSql
pagehelper.page-size-zero=true
```


## 在service层中使用PageHelper插件

## controller
```java
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/list")
    public PageInfo<User> list(Integer pageIndex, Integer pageSize){
        return userService.list(pageIndex,pageSize);
    }
}
```

## service
```java
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public PageInfo<User> list(Integer pageIndex, Integer pageSize){

        //使用分页插件,核心代码就这一行，页数、每页行数
        PageHelper.startPage(pageIndex, pageSize);
        //在 mapper.xml 中不要加 limit 分页，插件会自动拦截和添加 limit 分页
        List<User> users = userMapper.list();
        PageInfo<User> pages = new PageInfo<User>(users);

        return pages;
    }
}
```
## 结果：
```json
{
	"total": 13,
	"list": [{
		"id": 4,
		"name": "go",
		"sex": 1
	}, {
		"id": 5,
		"name": "李逵",
		"sex": 5
	}, {
		"id": 6,
		"name": "林冲123",
		"sex": 2
	}],
	"pageNum": 2,
	"pageSize": 3,
	"size": 3,
	"startRow": 4,
	"endRow": 6,
	"pages": 5,
	"prePage": 1,
	"nextPage": 3,
	"isFirstPage": false,
	"isLastPage": false,
	"hasPreviousPage": true,
	"hasNextPage": true,
	"navigatePages": 8,
	"navigatepageNums": [1, 2, 3, 4, 5],
	"navigateFirstPage": 1,
	"navigateLastPage": 5,
	"firstPage": 1,
	"lastPage": 5
}
```

分页的同时，获取总数量，从而计算总页数

由于我使用了EasyUI，DataGrid控件的分页需要返回总行数，所以拿来展示比较经典

先定义一个 Page 变量，这个类在com.github.pagehelper.Page，然后依然是 PageHelper.startPage(page, rows)，然后进行正常的查询，在查询后pages.getTotal()就可以获取到总行数了，拿到总行数，就可以计算出总页数




# 十一、SpringBoot用AOP使用Log4j收集web请求日志

## pom.xml

```xml
<!--
springboot 1.3+支持login2
可以引入以下包使用login4j
-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
</dependency>

<!-- springBoot 的Aop -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>

<dependency>
    <groupId>commons-logging</groupId>
    <artifactId>commons-logging</artifactId>
    <version>1.2</version>
</dependency>

<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
```


## Aop 
```java
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;

@Aspect
@Component
public class LoggerAop {

    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(LoggerAop.class);
    // private Logger logger = Logger.getLogger(AopController.class.getName());

    @Pointcut("execution(public * com.xbky.controller..*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));


        Enumeration<String> parameterNames = request.getParameterNames();
        while(parameterNames.hasMoreElements()){
            String key = parameterNames.nextElement();
            String value = (String) request.getParameter(key);
            logger.info("Key:"+request.getRemoteAddr()+",Value:"+value);
        }

    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("RESPONSE : " + ret);
    }
}
```

## controller 略



# 十二、全局异常拦截处理

## 在/resources/templates中创建error.html
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
    <head>
        <meta charset="UTF-8">
        <title>Title</title>
    </head>
    <body>
        动态error错误页面
        <p th:text="${error}"></p>
        <p th:text="${status}"></p>
        <p th:text="${message}"></p>
    </body>
</html>
```

404.html
```html
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Title</title>
    </head>
    <body>
        静态404错误页面
    </body>
</html>
```


## 全局异常类

@ExceptionHandler 拦截了异常，我们可以通过该注解实现自定义异常处理。其中，@ExceptionHandler 配置的 value 指定需要拦截的异常类型，上面我配置了拦截Exception，
再根据不同异常类型返回不同的相应，最后添加判断，如果是Ajax请求，则返回json,如果是非ajax则返回view，这里是返回到error.html页面。

```java
/**
 * 异常处理器
 */
@RestControllerAdvice
public class BusinessExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        System.out.println("请求有参数才进来");
    }
    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     * @param model
     */
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("author", "嘟嘟MD");
    }
    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e,HttpServletRequest req){
        AjaxObject r = new AjaxObject();
        //业务异常
        if(e instanceof BusinessException){
            r.put("code", ((BusinessException) e).getCode());
            r.put("msg", ((BusinessException) e).getMsg());
        }else{//系统异常
            r.put("code","500");
            r.put("msg","未知异常，请联系管理员");
        }
        //使用HttpServletRequest中的header检测请求是否为ajax, 如果是ajax则返回json, 如果为非ajax则返回view(即ModelAndView)
        String contentTypeHeader = req.getHeader("Content-Type");
        String acceptHeader = req.getHeader("Accept");
        String xRequestedWith = req.getHeader("X-Requested-With");
        if ((contentTypeHeader != null && contentTypeHeader.contains("application/json"))
                || (acceptHeader != null && acceptHeader.contains("application/json"))
                || "XMLHttpRequest".equalsIgnoreCase(xRequestedWith)) {
            return r;
        } else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("msg", e.getMessage());
            modelAndView.addObject("url", req.getRequestURL());
            modelAndView.addObject("stackTrace", e.getStackTrace());
            modelAndView.setViewName("error");
            return modelAndView;
        }
    }
}
```

# 十三、SpringBoot热部署

pom.xml

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>

```
application.properties

```properties
debug=true
#设置开启热部署
spring.devtools.restart.enabled=true 
#页面不加载缓存，修改即时生效
spring.freemarker.cache=false 
```

开发工具idea中使用JRebel插件
1、点击File -> Settings -> Plugins,如下图：搜索JRebel安装

# 十四、SpringBoot打包发布

### 添加如下配置
```xml
<!-- pom.xml 中 这个插件，可以将应用打包成一个可执行的jar包；-->
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>

```
