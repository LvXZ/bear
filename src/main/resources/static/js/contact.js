var date = new Date();
var jq = $.noConflict();
var id = jq("#id").val();
var aimid = jq("#toid").val();
var websocket;

var day = 0;
var datestr = date.toLocaleDateString();


var datajson = {
    "producerid": id,
    "subscriberid": aimid
};
jq(function() {

    jq.ajax({
        url: "/chat/get",
        type:"POST",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        data: JSON.stringify(datajson),
        async:true,
        success : function(result) {
            console.dir(result.data);
            for (var i = 0; i < result.data.length; i++) {
                console.log(result.data[i].producerId);
                console.log(id);
                var context;
                var date = new Date();
                if (result.data[i].producerId == id) {

                    context = '<div class="right"><div class="date">'
                        + date.toLocaleString()
                        + '</div><span></span><p class="message">'
                        + result.data[i].context
                        + '</p><img src="img/kefu.png" th:src="@{img/kefu.png}"></div>';
                } else {
                    context = '<div class="left"><img src="img/kefu.png" th:src="@{img/kefu.png}"><span></span><p class="message">'
                        + result.data[i].context
                        + '</p><div class="date">'
                        + date.toLocaleString()
                        + '</div></div>';
                }
                jq(".window").append(context);
            }
        }

    });
});
connectWebSocket(id, aimid);
jq(".send").click(
    function() {
        var text = jq("textarea").val();
        if (text == "") {
            alert("请输入内容");
        } else {
            var date = new Date();
            jq("textarea").val("");
            var right = '<div class="right"><div class="date">'
                + date.toLocaleString()
                + '</div><span></span><p class="message">' + text
                + '</p><img src="img/kefu.png" th:src="@{img/kefu.png}"></div>';
            jq(".window").append(right);
            sendMesage(text, aimid);
        }
    });

function connectWebSocket(selfid, aimid) {
    if ((typeof WebSocket) != 'undefined') {
        websocket = new WebSocket("ws://127.0.0.1:8080/websocket/" + selfid + "/" + aimid);
    } else {
        alert('当前浏览器 Not support websocket')
    }

    //连接发生错误的回调方法
    websocket.onerror = function() {
        showReceivedMsg("WebSocket连接发生错误");
    };

    //连接成功建立的回调方法
    websocket.onopen = function() {
        showReceivedMsg("WebSocket连接成功");
    }

    //接收到消息的回调方法
    websocket.onmessage = function(event) {
        var date = new Date();
        var message = event.data.substring(event.data.indexOf(':') + 1);
        var context = '<div class="left"><img src="img/kefu.png" th:src="@{img/kefu.png}"><span></span><p class="message">'
            + message
            + '</p><div class="date">'
            + date.toLocaleString() + '</div></div>';
        jq(".window").append(context);
    }

    //连接关闭的回调方法
    websocket.onclose = function() {
        showReceivedMsg("WebSocket连接关闭");
        websocket.close();
    }

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function() {
        closeWebSocket();
    }
}

function showReceivedMsg(msg) {
    console.log(msg);
}

function showindialog(msg) {

}

//发送信息
function sendMesage(msg, toid) {
    msg = "to" + toid + ":" + msg;
    websocket.send(msg);
}

window.onunload = function() {
    websocket.close();
}

jq(".seeHistory a").click(function(){
    seeHistory(day);
});

function seeHistory(day1){
    console.log(day1);

    var startTime,endTime;

    if(day == 0){
        startTime = datestr;
        endTime = date.toLocaleDateString()+" "+date.toLocaleTimeString().substr(2);
    }else{
        startTime = new Date(date.getTime()-day1*1000*60*60*24).toLocaleDateString();
        endTime = new Date(date.getTime()-(day1-1)*1000*60*60*24).toLocaleDateString();
    }

    var jsondate = {
        "producerid": id,
        "subscriberid": aimid,
        "startTime": startTime,
        "endTime": endTime
    };


    jq.ajax({
        url: "/chat/gethistory",
        type:"POST",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        data: JSON.stringify(jsondate),
        async:true,
        success : function(result) {
            console.log(result.data);
            for(var i = result.data.length-1;i>=0;i--){
                if(result.data[i].producerId == id){
                    var right = '<div class="right"><div class="date">'
                        + new Date(result.data[i].timestamp).toLocaleString()
                        + '</div><span></span><p class="message">' + result.data[i].context
                        + '</p><img src="img/kefu.png" th:src="@{img/kefu.png}"></div>';
                    jq("#history").after(right);
                }else{
                    var left = '<div class="left"><img src="img/kefu.png" th:src="@{img/kefu.png}"><span></span><p class="message">'
                        + result.data[i].context
                        + '</p><div class="date">'
                        + new Date(result.data[i].timestamp).toLocaleString() + '</div></div>';
                    jq("#history").after(left);
                }
            }
            day++;
        }
    });
}