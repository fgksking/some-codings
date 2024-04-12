# JQuery 学习

## 第一章理解架构

JQuer 是一个JavaScript库

### 库与框架的区别

1. **库（Library）**：
   - 库是一组已经编写好的代码，开发人员可以直接调用库中的函数或方法来完成特定的任务。
   - 开发人员在使用库时拥有更多的控制权，可以选择性地调用库中的函数，也可以根据需要自由组合库中的功能。
   - 库通常是面向对象或函数式编程的，提供了一系列的API供开发人员使用。
   - 例子：Java中的Apache Commons库，提供了各种常用功能的实现，如字符串操作、文件操作等。
2. **框架（Framework）**：
   - 框架是一种开发环境，是在特定领域下的一种半成品软件，提供了一整套解决方案和规范，开发人员需要按照框架规定的方式来编写代码。
   - 开发人员在使用框架时，需要按照框架的规范来编写代码，通常会遵循框架提供的设计模式和约定。
   - 框架通常提供了一整套的功能和结构，开发人员只需要按照框架的要求来填充具体的业务逻辑。
   - 例子：Spring框架，提供了依赖注入、面向切面编程等功能，开发人员可以使用Spring来构建企业级应用。

### jQuery对象与dom对象的区别

**jQuery是一个类数组对象，而DOM对象就是一个单独的DOM元素。**

#### **如何把jQuery对象转成DOM对象？**

**利用数组下标的方式读取到jQuery中的DOM对象**

HTML代码

```
<div>元素一</div>
<div>元素二</div>
<div>元素三</div>
```

JavaScript代码

```javascript
var $div = $('div') //jQuery对象
var div = $div[0] //转化成DOM对象
div.style.color = 'red' //操作dom对象的属性
```

用jQuery找到所有的div元素（3个），因为jQuery对象也是一个数组结构，可以通过数组下标索引找到第一个div元素，通过返回的div对象，调用它的style属性修改第一个div元素的颜色。这里需要注意的一点是**，数组的索引是从0开始的，也就是第一个元素下标是0**



**通过jQuery自带的get()方法**

jQuery对象自身提供一个.get() 方法允许我们直接访问jQuery对象中相关的DOM节点，get方法中提供一个元素的索引：

```javascript
var $div = $('div') //jQuery对象
var div = $div.get(0) //通过get方法，转化成DOM对象
div.style.color = 'red' //操作dom对象的属性
```



#### DOM转JQuery

通过$(dom)方法将普通的dom对象加工成jQuery对象之后，我们就可以调用jQuery的方法了

HTML代码

```
<div>元素一</div>
<div>元素二</div>
<div>元素三</div>
```

JavaScript代码

```javascript
var div = document.getElementsByTagName('div'); //dom对象
var $div = $(div); //jQuery对象
var $first = $div.first(); //找到第一个div元素
$first.css('color', 'red'); //给第一个元素设置颜色
```

通过getElementsByTagName获取到所有div节点的元素，结果是一个dom合集对象，不过这个对象是一个数组合集(3个div元素)。通过$(div)方法转化成jQuery对象，通过调用jQuery对象中的first与css方法查找第一个元素并且改变其颜色。

### 立即表达式与工厂模式

###  无冲突处理机制

## 核心机制

### 理解上下文this

this是JavaScript中的关键字，指的是当前的上下文对象，简单的说就是方法/属性的所有者

下面例子中，imooc是一个对象，拥有name属性与getName方法,在getName中this指向了所属的对象imooc

```
var imooc = {
    name:"慕课网",
    getName:function(){
        //this,就是imooc对象
        return this.name;
    }
}
imooc.getName(); //慕课网
```

当然在JavaScript中this是动态的，也就是说这个上下文对象都是可以被动态改变的(可以通过call,apply等方法)，具体的大家可以查阅相关资料

同样的在DOM中this就是指向了这个html元素对象，因为this就是DOM元素本身的一个引用

假如给页面一个P元素绑定一个事件:

```javascript
p.addEventListener('click',function(){
    //this === p
    //以下两者的修改都是等价的
    this.style.color = "red";
    p.style.color = "red";
},false);
```

通过addEventListener绑定的事件回调中，this指向的是当前的dom对象，所以再次修改这样对象的样式，只需要通过this获取到引用即可

```
 this.style.color = "red"
```

但是这样的操作其实还是很不方便的，这里面就要涉及一大堆的样式兼容，如果通过jQuery处理就会简单多了，我们只需要把this加工成jQuery对象

换成jQuery的做法：

```javascript
$('p').click(function(){
    //把p元素转化成jQuery的对象
    var $this= $(this) 
    $this.css('color','red')
})
```

通过把$()方法传入当前的元素对象的引用this，把这个this加工成jQuery对象，我们就可以用jQuery提供的快捷方法直接处理样式了

**总体：**

```javascript
this，表示当前的上下文对象是一个html对象，可以调用html对象所拥有的属性和方法。
$(this),代表的上下文对象是一个jquery的上下文对象，可以调用jQuery的方法和属性值。
```

### 架构设计

## 回调模型

