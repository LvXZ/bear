var num = "0";


function indexUser(){

    var account = $('#account').val(),
        password = $('#password').val();
    var objJson = {
        "account":account,
        "password":password
    };

    var URL = "/user/index";
    AJAXFunction(objJson,URL);
}


function AJAXFunction(objJson,URL) {

    $.ajax({
        type:"POST",
        url: URL,
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        data: JSON.stringify(objJson),
        async:true,
        success: function (response) {
            alert(response.data.name)
        },
        error: function (){
            alert("当前网络不稳定......");
        }
    });
}
$(document).ready(function(){
    $(".loginMark").click(function(){
        $('.cover').fadeIn();
        $('.log-box').fadeIn();
        $(document).bind('mousewheel', function(event, delta) { return false; });
    });
    $(".regMark").click(function(){
        $('.cover').fadeIn();
        $('.reg-box').fadeIn();
        $(document).bind('mousewheel', function(event, delta) { return false; });
    });
    $('.cover').click(function() {
        $('.cover').fadeOut();
        $('.reg-box').fadeOut();
        $('.log-box').fadeOut();
        $(document).unbind('mousewheel');
    }); 
    $('.regEntr').click(function(){
        $('.log-box').fadeOut();
        $('.reg-box').fadeIn();
    }); 
    $('.logEntr').click(function(){
        $('.reg-box').fadeOut();
        $('.log-box').fadeIn();
    });
    $('.signoff').click(function(){
        $('.cover').fadeOut();
        $('.reg-box').fadeOut();
        $('.log-box').fadeOut();
        $(document).unbind('mousewheel');
    });
    $('.logOut').mouseover(function(){
        $(this).children('.sonMenu').show();
    });
    $('.logOut').mouseout(function(){
        $(this).children('.sonMenu').hide();
    });

    $('#signoffTwo').click(function(){
        $('.detail-box').fadeOut();
        $(document).unbind('mousewheel');
    });

    // 产品二三级菜单
    $('.products').mouseover(function(){
        $(this).children('.goodsType').show();
    });
    $('.products').mouseout(function(){
        $(this).children('.goodsType').hide();
    });
    $('.products.dynamic').mouseover(function(){
        $(this).children('.product').css("backgroundColor", "#474646");
    });
    $('.products.dynamic').mouseout(function(){
        $(this).children('.product').css("backgroundColor", "#4f4e4e");
    });

    // 商品详情界面加减按钮响应

    $('.good-counter-min').click(function(){
        var temp = Number($('.good-counter-num').text());
        console.log('-' + " : " + typeof(temp));
        if(temp > 0) {
            console.log('-');
            temp --;
            $('.good-counter-num').text(""+ temp);
        }

    });
    $('.good-counter-plus').click(function(){
        var temp = Number($('.good-counter-num').text());
        var store = Number($('.inventory-num').text());
        console.log('+' + " : " + typeof(temp));
        if(temp < store) {
            temp ++;
            $('.good-counter-num').text(""+ temp);
        }
    });

    var tar = document.getElementById("dynamicNav"),//获取动态的菜单栏
        possY = getViewportOffset().h;//获取屏幕高度

    function windowAddMouseWheel() {
        var scrollFunc = function (e) {
            e = e || window.event;
            if (e.wheelDelta || e.detail) {  //判断浏览器IE，谷歌滑轮事件
                if(getScrollOffset().y + 200 >= possY){
                    tar.style.display = 'block';
                }else {
                    tar.style.display = 'none';
                };
    
                //屏幕高度            getViewportOffset().h
                //网页高度，用JQ获得   $(document).height() 
                //滚动条滚动距离       getScrollOffset().y

                // 下滑网页，动态生成新商品信息
                if($(document).height() - getViewportOffset().h == getScrollOffset().y) {
                    if(num != "-1"){
                        num = parseInt(num) + 1 + "";
                        indexIndex(num);//处理获取
                    }else{
                        alert("主儿...已经下拉到最下面了");
                    }
                }
            }
        };
        //给页面绑定滑轮滚动事件
        if (document.addEventListener) {
            document.addEventListener('DOMMouseScroll', scrollFunc, false);
        }
    //滚动滑轮触发scrollFunc方法
        window.onmousewheel = document.onmousewheel = scrollFunc;
    }
    windowAddMouseWheel();

    // 展示商品，动态生成 
});


// for(var i = 0; i < 5; i ++) {
//     var templeft = $('<div class = "item"><div class="son"><img class = "good"/><span class="goodname">CHARLES＆KEITH 小方包 CK2-70780504 2018春简约金属装饰单肩包</span><span class="price">￥500</span><span class="input-group-addon"><i class="iconfont">&#xe648;</i></span></div></div>');
//     var tempright = $('<div class = "item"><div class="son"><img class = "good"/><span class="goodname">hello this is for test</span><span class="price">￥520</span></div></div>');
//     $('#col-left').append(templeft);
//     $('#col-right').append(tempright);
// }
// $('.item').click(function(){
//     $('.detail-box').show();
// });
function moreGoods(data) {
    if(data.length < 1){
        num = "-1";
    }else{
        for(var i = 0; i < 5; i ++) {
            var templeft = $('<div class = "item"><div class="son"><img onclick="productBox('+data[i].goodId+')" class ="good" ' +
            'src ="http://192.168.35.112:8081/download/o2o/images'+ data[i].image.replace(/\\/g,"/") +'"/>' +
            '<span class="goodname" onclick="productBox('+data[i].goodId+')">'+ data[i].goodName +'</span><span class="price">￥'+ data[i].price +'</span>' +
            '<span class="input-group-addon"><i class="iconfont" data-flag="0" id="'+data[i].goodId +'" onclick="collect(this)">&#xe658;</i></span></div></div>');
            var tempright = $('<div class = "item"><div class="son"><img onclick="productBox('+data[i+5].goodId+')" class ="good" ' +
            'src ="http://192.168.35.112:8081/download/o2o/images'+ data[i+5].image.replace(/\\/g,"/") +'"/>' +
            '<span class="goodname" onclick="productBox('+data[i+5].goodId+')">'+ data[i+5].goodName +'</span><span class="price">￥'+ data[i+5].price +'</span>' +
            '<span class="input-group-addon"><i class="iconfont" data-flag="0" id="'+data[i+5].goodId +'" onclick="collect(this)">&#xe658;</i></span></div></div>');
            $('#col-left').append(templeft);
            $('#col-right').append(tempright);
        }
        /*&#xe64b;*/

    }
}

function productBox(goodsId) {
    console.log(goodsId);
    goodsId = goodsId+"";

    var objJson = {
        "goodsId":goodsId
    };
    var URL = "/goods/find_goods";
    ShowGoods_Function(objJson,URL);

    // 展示商品详情界面,禁用滚动条
    $(document).bind('mousewheel', function(event, delta) {return false;});
    $('.detail-box').show();

}



/**
 * login登录界面控制
 */

function loginUser(){

    var telephone = $('#login-phone').val(),
        password = $('#login-password').val();
    var objJson = {
        "telephone":telephone,
        "password":password
    };

    var URL = "/user/login";
    Login_AJAXFunction(objJson,URL);


}

function Login_AJAXFunction(objJson,URL) {

    $.ajax({
        type:"POST",
        url: URL,
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        data: JSON.stringify(objJson),
        async:true,
        success: function (response) {

            if(response.code==1){
                //alert(response.msg);
                console.log(response);
                //自我刷新
                window.location.reload();
            }else{
                alert(response.msg);
            }
            

        },
        error: function (){
            alert("访问失败，请检查网络、路径......");
        }
    });
}

/**
 * user 退出
 */
function userExist() {
    if(Cookies.get('UserObjJson')){
        Cookies.remove('UserObjJson', { path: '' });
        alert("已退出");
        window.location.reload();

    }
}

/**
 * register注册界面控制
 */

function Register_uploadImage(){
    $("#reg-user-file").click();
}


function Register_check(){

    //分割上传文件字符串  userfile的value为上传文件的名称 类型为字符串
    var userfile = document.getElementById('reg-user-file');
    var fileNameArr = userfile.value.toLowerCase().split('.');
    //或者document.getElementById("userfile")    也可以获得该dom元素
    //文件名后缀
    var suffix = fileNameArr[fileNameArr.length-1];
    //如果后缀为空
    if(suffix != ""){

        if(suffix=='gif'||suffix=='jpg'||suffix=='bmp'||suffix=='png'||suffix=='jpeg'){
            var imgSize = userfile.files[0].size;
            //alert("图片大小："+imgSize+"B");
            if(imgSize>1024*1024){
                alert("注册失败，图片大于1M");
                return false;
            }
        }else{
            alert("请选择格式为*.jpg、*.gif、*.bmp、*.png、*.jpeg 的图片");
            return false;
        }
    }

    var registerUrl = '/user/register';

    //创建json字符串
    var objJson = {};
    objJson.name = $('#reg-username').val();
    objJson.password = $('#reg-password').val();
    objJson.telephone = $('#reg-telephone').val();
    objJson.email = $('#reg-email').val();


    var thumbnail = $('#reg-user-file')[0].files[0];//图片

    var formData = new FormData();//创建传输对象
    formData.append('objImg', thumbnail);
    formData.append('objJson', JSON.stringify(objJson));
    console.log(formData.get("objJson"));


    $.ajax({
        url : registerUrl,
        type : 'POST',
        data : formData,
        processData: false,       //必不可缺
        contentType: false,       //必不可缺
        cache : false,
        success : function(response) {
            if (response.code == 1) {
                alert(response.msg);

                //var openURL = "/login";
                //window.open(openURL, "_self");

            } else {
                alert(response.msg);
                //$('#captcha_img').click();
            }
        },
        error: function (){
            alert("访问失败，请检查网络、路径......");
        }
    });

}

/**
 * 判断cookies
 */

function checkCookies(){
    if(!Cookies.get('UserObjJson')){
        // alert("您还未登陆！请登陆");
        //window.open("/login",'_self');

        // var array = ["blue","green","orange","purple","yellow"];
        //     var i = Math.floor(Math.random()*5); //可均衡获取0到4的随机整数。
        //     $(".AfterUserImg").attr("src","../../img/"+ array[i] +"_bear.png");

        $('.log-tag').css("display","block");
        $('.navbar-right').css("display","none");//切换导航显示

    }else{
        var jsonValue = JSON.parse(Cookies.get('UserObjJson'));
        //alert(jsonValue.image);
        console.log(jsonValue);

        if(jsonValue.image.length > 10){
            var newImage = jsonValue.image.replace(/\\/g,"/");
            var path = 'http://192.168.35.112:8081/download/o2o/images' + newImage;
            $(".AfterUserImg").attr("src",path);
        }else{
            var array = ["blue","green","orange","purple","yellow"];
            var i = Math.floor(Math.random()*5); //可均衡获取0到4的随机整数。
            $(".AfterUserImg").attr("src","img/"+ array[i] +"_bear.png");
            $(".AfterUserImg").attr("th:src","@{img/"+ array[i] +"_bear.png}");
        }
        
        $('.log-tag').css("display","none");
        $('.navbar-right').css("display","block");//切换导航显示
    }
    indexIndex(num);
 }


 function indexIndex(number) {
     var priority = "6",
         startIndex = number,
         flag = "1";
     var objJson = {
         "priority": priority,
         "startIndex": startIndex,
         "flag": flag
     };

     var URL = "/goods/search_priority";
     Index_Function(objJson,URL);

 }


function Index_Function(objJson,URL) {

    $.ajax({
        type:"POST",
        url: URL,
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        data: JSON.stringify(objJson),
        async:true,
        success: function (response) {
            console.log(response.data);
            moreGoods(response.data);
        },
        error: function (){
            alert("当前网络不稳定......");
        }
    });
}

function ShowGoods_Function(objJson,URL) {

    $.ajax({
        type:"POST",
        url: URL,
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        data: JSON.stringify(objJson),
        async:true,
        success: function (response) {
            console.log(response.data);
            //moreGoods(response.data);

            $(".product-name").html(response.data.goodName);
            $(".product-desc").html(response.data.introduction);
            $(".inventory-num").html(response.data.invenory);
            $(".good-counter-num").html(0);
            $(".good-price").html("￥"+response.data.price);
            $("#shop-id").val(response.data.shopId);
            $(".sales-num").html(response.data.sales);
            $(".iconfont-3").attr("id",response.data.goodId);



            if(response.data.image.length > 10){
                var newImage = response.data.image.replace(/\\/g,"/");
                var path = 'http://192.168.35.112:8081/download/o2o/images' + newImage;
                $(".product-img").attr("src",path);
            }else{
                var array = ["blue","green","orange","purple","yellow"];
                var i = Math.floor(Math.random()*5); //可均衡获取0到4的随机整数。
                $(".product-img").attr("src","img/"+ array[i] +"_bear.png");
                $(".product-img").attr("th:src","@{img/"+ array[i] +"_bear.png}");
            }
        },
        error: function (){
            alert("当前网络不稳定......");
        }
    });
}


//收藏
function collect(obj) {

    if(!Cookies.get('UserObjJson')){
        alert("您还未登录");
    }else{
        var flag = obj.getAttribute('data-flag');
        console.log(flag);

        if(flag == "0"){
            $(obj).html("&#xe64b;");
            obj.setAttribute('data-flag',"1");

            var URL = "/goods/collect_goods";
        }else{
            $(obj).html("&#xe658;");
            obj.setAttribute('data-flag',"0");

            var URL = "/goods/un_collect_goods";
        }

        var objJson = {
            "goodsId":obj.id
        };
        Collect_Function(objJson,URL);
    }



}


function Collect_Function(objJson,URL) {

    $.ajax({
        type:"POST",
        url: URL,
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        data: JSON.stringify(objJson),
        async:true,
        success: function (response) {


            alert(response.msg);
        },
        error: function (){
            alert("当前网络不稳定......");
        }
    });
}

//我的订单
function myOrders() {

    url = "/userinfo?list=2";//跳转+传参,跳转到订单
    window.location.href = url;

}

//我的收藏
function myCollect() {

    url = "/userinfo?list=3";//跳转+传参，跳转到收藏
    window.location.href = url;
}

//立即购买
function buyGoods(obj) {


    var goodsId = obj.id,
        purchaseNumber = $(".good-counter-num").html(),
        shopId = $("#shop-id").val(),
        price = $(".good-price").html();

    if(parseInt(purchaseNumber) < 1){
        alert("购买失败，您未选择数量");
    }else{

        if(!Cookies.get('UserObjJson')){
            alert("您还未登录");
        }else{
            var totalPrice = parseFloat(price.substring(1))*parseInt(purchaseNumber) + "";


            var objJson = {
                "goodsId":goodsId,
                "purchaseNumber":purchaseNumber,
                "shopId":shopId,
                "totalPrice":totalPrice
            };

            var URL = "/order/make_order";

            $.ajax({
                type:"POST",
                url: URL,
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: JSON.stringify(objJson),
                async:true,
                success: function (response) {


                    alert(response.msg);
                },
                error: function (){
                    alert("当前网络不稳定......");
                }
            });
        }


    }



}

//加入购物车
function addShoppingCar(obj) {
    console.log(obj.id);




}
