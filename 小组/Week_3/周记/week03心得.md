# week03

## 一周总结

- 学习了ajax实现与前端后台的交互，以及前后端分离
- 使用jquery编写前端页面

## 存在问题

- 后台的Servlet 显得有点冗杂
- 前端代码部分重合，后端代码耦合性高

## 下周规划

- 整理规范代码
- 学习MVC三层架构思想
- 手撕MVC





## Http状态码的知识

2XX 成功
200 ok（请求成功）
204 no content （请求成功，但是没有结果返回）
206 partial content （客户端请求一部分资源，服务端成功响应，返回一范围资源）

---



3XX 重定向
301 move permanently （永久性重定向）
302 found （临时性重定向）
303 see other （示由于请求对应的资源存在着另一个 URI，应使用 GET
方法定向获取请求的资源）
304 not modified （表示在客户端采用带条件的访问某资源时，服务端找到了资源，但是这个请求的条件不符合。跟重定向无关）
307 temporary redirect （跟302一个意思）

---



4XX 客户端错误
400 bad request （请求报文存在语法错误）
401 unauthorized （需要认证（第一次返回）或者认证失败（第二次返回））
403 forbidden （请求被服务器拒绝了）
404 not found （服务器上无法找到请求的资源）

---



5XX 服务器错误
500 internal server error （服务端执行请求时发生了错误）
503 service unavailable （服务器正在超负载或者停机维护，无法处理请求）
–参考自《图解HTTP》