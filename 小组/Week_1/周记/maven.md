# javaweb学习



## maven

### 新版idea的问题(用模板)

创建完maven的模板后再手动创建test包、java 、resource包

可以手动调节包的属性：

![image-20240315230610901](C:\Users\fhkin\AppData\Roaming\Typora\typora-user-images\image-20240315230610901.png)

---







## tomcat

Apache Tomcat 是一个免费的，开源的 Jakarta Servlet 和 JSP 技术实现。该软件可以使用 Windows 安装程序在 Windows 上轻松安装，具有用户友好的界面。使用 Apache Tomcat，用户可以在生产环境中测试他们的 Web 应用程序，然后再将它们部署到公众。

tomcat已经部署到本地，就不用在maven添加tomcat的依赖。

---







## Servlet

### 环境配置

maven引用依赖，jar包，建议4.0以上

-  注意： 4.0  以前的包是  javax.servlet:javax.servlet-api

~~~xml
<dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>javax.servlet-api</artifactId>
      <version>4.0.0</version>
      <scope>provided</scope>
    </dependency>
~~~

### servlet执行流程

htt ps://locasthost:8080/javaweb_maven_03/Hello

- javaweb_maven_03  指项目名称
- Hello 指对应实现servlet的方法/类

### servlet urlPattern配置

1. 精确匹配   @WebServlet("/para/123/...")
2. 目录匹配  @WebServlet("/para/*")
3. 扩展名匹配  @WebServlet("*.para")
4. 任意匹配  @WebServlet("/")
5. fikret

