<html>

<head>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
</head>

<body>
<h2>Hello World!</h2>

<button onclick="GetRequest()">GET请求</button>
<button onclick="PostRequest()">POST请求</button>
<button onclick="sendMessage('DELETE',DeleteFunction)">DELECT请求</button>
<button onclick="sendMessage('TRACE',TraceFuction)">TRACE请求</button>
<button onclick="sendMessage('HEAD',HeadFuction)">HEAD请求</button>
<button onclick="PutRequest()">PUT请求</button>
<button onclick="sendMessage('OPTIONS',)">OPTIONS请求</button>
<button onclick="change()">跳转资源</button>
<button onclick="testrequest('resource')">跳转资源</button>
<button onclick="testrequest('notfound')">notfind不存在的网页</button>
<button onclick="testrequest('test')">错误的请求方式</button>


<script>
    function GetRequest() {
        fetch('http://localhost:8080/test',{
            method:'GET'
        }).then(response=>{
            if(response.ok){
                //此处的return是把response返回是数据return给data
                return response.json();
            }else{
                throw new Error('请求失败');
            }
        })
            .then(data=>{
                alert('GET请求成功，数据是'+JSON.stringify(data));
            }).catch(error=>{
                alert('GET请求失败');
        })
    }
//ajax
    function PostRequest() {
        var xttp =new XMLHttpRequest();
        xttp.onreadystatechange=function (){
            if(this.readyState==4&&this.status==200){
                alert(this.responseText);
            }
        };
        xttp.open("POST","http://localhost:8080/test",true);
        xttp.setRequestHeader("Content-type","application/json")
        xttp.send(JSON.stringify({key:'后端收到post请求了'}));
    }
function PutRequest() {
    var xm = new XMLHttpRequest();
    xm.onreadystatechange = function () {
        if (xm.readyState == 4) {
            if (xm.status === 200) {
                alert(xm.responseText);
            } else {
                alert("请求失败");
            }
        }
    };
    //put
    xm.open('PUT', 'http://localhost:8080/test', true);
    xm.setRequestHeader('Content-Type', 'application/json');
    xm.send(JSON.stringify({key: '后端收到put请求了'}));
}
//以下请求雷同，用回调函数统一实现

    function sendMessage(method,cFunction){
        var xtthp;
        xttp =new XMLHttpRequest();
        xttp.onreadystatechange=function () {
            if(this.readyState===4&&this.status===200){
                cFunction(this);
            }
        };
        xttp.open(method,"http://localhost:8080/test",true);

        xttp.send();

    }

    function DeleteFunction(xttp) {
        alert(xttp.responseText);
    }
    function HeadFuction(xttp) {
        var headers=xttp.getAllResponseHeaders();
        alert("请在控制台查看");
        console.log(headers);
    }

    function OptionsFuction(xttp) {
        var x=xttp
    }

 /*   xm.open('HEAD','http://localhost:8080/test',true);
    xm.send();*/
    //用fetch api      fetch不支持Trace请求方法
/*    function TraceRequest() {
        fetch("http://localhost:8080/test",{
            method:'TRACE',
            headers:{
                'Content-Type':'applicaction/json'
            }
        }).then(response=>{
            alert("Trace请求成功");
        })

    }*/
    function TraceFuction(xttp) {
        alert("Trace请求成功");
    }



    //以下是模仿后台发送的各种状态码
    function testrequest(path) {
        fetch("http://localhost:8080/"+path)
            .then(response=>{
                if(response.ok){
                    return response.text();
                }else {
                    alert("发生错误");
                    throw new Error('wrong status'+response.status);
                }
            }).then(data=>{
                alert(data);
        }).catch(err=>{
            console.error(err);
        })

    }

    function change() {
        fetch("http://localhost:8080/test?data=change",{
            method:'GET',

        }).then(response=>{
            alert("跳转成功");
        })
    }


</script>


</body>
</html>
