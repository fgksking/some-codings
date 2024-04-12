# Js

## Vue

### 介绍

Vue (读音 /vjuː/，类似于 **view**) 是一套用于构建用户界面的**渐进式框架**。与其它大型框架不同的是，Vue 被设计为可以自底向上逐层应用。Vue 的核心库只关注视图层，不仅易于上手，还便于与第三方库或既有项目整合。另一方面，当与[现代化的工具链](https://v2.cn.vuejs.org/v2/guide/single-file-components.html)以及各种[支持类库](https://github.com/vuejs/awesome-vue#libraries--plugins)结合使用时，Vue 也完全能够为复杂的单页应用提供驱动。

### 如何判断Vue的data数据是否为空

data数据为数组时

![image-20240330193421153](C:\Users\fhkin\AppData\Roaming\Typora\typora-user-images\image-20240330193421153.png)

也可以用`Object.keys()`方法：可以通过`Object.keys(this.$data).length === 0`来判断Vue实例的`data`对象是否为空。如果`Object.keys(this.$data)`返回的数组长度为0，则表示`data`对象为空。

##  JS中$含义及用法

## axios

### 介绍

Axios 是一个基于 promise 的 HTTP 库，可以用在浏览器和 node.js 中。

#### 特性

- 从浏览器中创建 **XMLHttpRequests**
- 从 node.js 创建 http 请求
- 支持 **Promise** API
- 拦截请求和响应
- 转换请求数据和响应数据
- 取消请求
- 自动转换 JSON 数据
- 客户端支持防御 **XSRF**

### 解决axiso异步执行

因为刷新页面axios异步执行获取数据，导致新页面获得的数据被覆盖，用Vue渲染从后台获取的数据

解决办法：

1.

- 在 Vue 实例中定义一个方法 `onSubmit`，该方法用于处理表单提交逻辑。

- 在表单中使用 `v-on:submit.prevent="onSubmit"` 来阻止表单的默认提交行为，并调用 `onSubmit` 方法处理提交逻辑。

- 在 `onSubmit` 方法中使用 Vue Resource、Axios 或 Fetch 等工具向后台发送请求，获取新数据。

- 在请求成功后，更新 Vue 实例中的数据，从而更新页面内容，而不刷新整个页面。

例子点菜系统的页面展示所有菜品，如要把菜品排序，从后台获取新数据

```html

    var vm =new Vue({
        el:"#app",
        data(){
            return{
                dishes:[],  //菜品
                selected:[],  //加入购物车的
                table:[],     //所有餐桌号
                selected_table:1
            }
        },
       mounted(){
            //mounted钩子函数刷新页面自动执行
            //页面加载，发送异步请求,查询数据
            this.allDish();
            this.show_table_id();
        },
        methods:{
            allDish(){
                axios({
                    method: "get",
                    url: "http://localhost:8080/dishesServlet"
                }).then( (resp) =>{
                    this.dishes = resp.data;

                })
            },
            show_table_id(){
              axios({
                  method: "get",
                  url:"http://localhost:8080/table"
              }).then((resp)=>{
                  this.table=resp.data;
                })
            },


            async onSubmit_price(){
                const response = await axios.get("http://localhost:8080/dishesServlet?data=Price");
                this.dishes = response.data;

               /* axios.post('http://localhost:8080/dishesServlet?data=Price',{dishes:this.dishes}).then(
                    response=>{
                      this.dishes = response.data;
                    })*/

            },
            async onSubmit_type() {
                const response = await axios.get("http://localhost:8080/dishesServlet?data=Type");
                this.dishes = response.data;
            },
            submitData(){


                //有数据的话
                if(this.selected!==undefined&&this.selected.length>0){
                    //提交给服务器
                    axios.post('http://localhost:8080/submitServlet',this.selected).then(
                        response=>{
                            console.log("提交成功");
                            alert("提交成功");
                            //跳转值购物车
                            location.href="pay.html";
                        }
                    ).catch(error=>{ console.log("数据提交失败")});
                }else {
                    alert("请选择菜品");
                }
            }



            }
```



### Get请求乱码问题

tomcat8.0以上版本已经用utf-8编码了，8.0以下才有这个问题

```
byte [] bytes = s.getBytes("ISO-8859-1");
 s= new String(bytes, "utf-8");
```

**Post请求**

request.setCharacterEncoding("UTF-8");

## 请求体

请求体中的数据通常只能被读取一次。这是因为在Servlet容器中，当请求到达时，请求体中的数据会被解析并读取到内存中，以便Servlet可以访问和处理这些数据。一旦数据被读取，就无法再次直接从请求体中读取，因为它已经被消耗（consumed）了

问题：

Filter过滤器调用 servletRequest.getParameter方法，对数据做一些帅选处理时，导致重定向时request，response请求体丢失了一部分数据

### 请求体的定位

在网络通信中，请求体（Request Body）是HTTP请求中包含的数据部分，通常用于向服务器发送数据。HTTP请求由请求行、请求头部和请求体三部分组成，其中请求体是可选的，用于传递客户端向服务器提交的数据。

请求体通常用于以下情况：

1. **向服务器提交表单数据**：当用户在网页上填写表单并点击提交按钮时，表单中的数据会被包含在请求体中发送到服务器，服务器可以根据这些数据进行相应的处理。
2. **上传文件**：如果用户需要上传文件，文件的内容会被包含在请求体中，以便服务器接收并保存这些文件。
3. **发送JSON数据**：在使用RESTful API进行通信时，客户端通常会将JSON格式的数据放在请求体中发送给服务器，服务器端进行解析和处理。
4. **其他数据传输需求**：除了上述情况外，请求体还可以用于传输其他类型的数据，比如XML数据、二进制数据等。

请求体的格式取决于请求的Content-Type头部字段。常见的请求体格式包括：

- **application/x-www-form-urlencoded**：表单数据以键值对的形式编码在请求体中，如`name=John&age=30`。
- **multipart/form-data**：用于上传文件或表单数据，数据以多部分形式编码在请求体中。
- **application/json**：请求体中包含JSON格式的数据。
- 其他自定义的数据格式。

在Servlet编程中，可以通过HttpServletRequest对象的getReader()或getInputStream()方法来读取请求体中的数据。一旦请求体数据被读取，通常就无法再次直接从请求体中读取，除非采取类似HttpServletRequestWrapper的方式进行多次读取。