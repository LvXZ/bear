$(document).ready(function(){
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
    $('.logOut').mouseout(function(){
        $('.products.dynamic').mouseout(function(){
            $(this).children('.product').css("backgroundColor", "#4f4e4e");
        });


        $('.logOut').mouseover(function(){
            $(this).children('.sonMenu').show();
        });
        $(this).children('.sonMenu').hide();
    });

    $('.like').click(function(){
        $('.like-main').siblings().hide();
        $('.like-main').show();
    });

    $('.order').click(function(){
        $('.order-main').siblings().hide();
        $('.order-main').show();
    });

    // 点击关闭商品详情box
    $('#signoffTwo').click(function(){
        $('.likeDetBox').fadeOut();
        $(document).unbind('mousewheel');
    });


    // 商品详情界面加减按钮响应

    $('.like-good-counter-min').click(function(){
        var temp = Number($('.like-good-counter-num').text());
        if(temp > 0) {
            console.log('-');
            temp --;
            $('.like-good-counter-num').text(""+ temp);
        }

    });
    $('.like-good-counter-plus').click(function(){
        var temp = Number($('.like-good-counter-num').text());
        var store = Number($('.like-inventory-num').text());
        if(temp < store) {
            temp ++;
            $('.like-good-counter-num').text(""+ temp);
        }
    });


    //加载我的收藏
    showCollections();

    //测试我的收藏
    showOrders();

    // 个人信息、收藏和订单切换
    var tar = $('.info-menu li'),
        box = $('.main-box'),
        len = tar.length;
    for(var i = 0; i < len; i ++) {
        (function (i){
            tar[i].onclick=function(){
                $(this).siblings().attr('id','');
                $(this).attr('id','active');
                $(box).children().css('display','none');
                $(box).children('ul:eq('+ i +')').css('display','block');
            }
        }(i));
    }

    //跳转界面加载main-box子项
    var list = $.Request("list");
    if(Number(list) != 0) {
        $(box).children().css('display','none');
        $(box).children('ul:eq('+ list +')').css('display','block');
        tar[0].attr('id','');
        tar[list].attr('id','active');
    }

    var warningBox = $('<div class="warning-box">你好</div>');
    $('body').append(warningBox);
});



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
    $('.likeDetBox').show();

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

            $(".like-product-name").html(response.data.goodName);
            $(".like-product-desc").html(response.data.introduction);
            $(".like-inventory-num").html(response.data.invenory);
            $(".like-good-counter-num").html(0);
            $(".like-good-price").html("￥"+response.data.price);
            $("#shop-id").val(response.data.shopId);
            $(".like-sales-num").html(response.data.sales);
            $(".iconfont-3").attr("id",response.data.goodId);



            if(response.data.image.length > 10){
                var newImage = response.data.image.replace(/\\/g,"/");
                var path = 'http://192.168.35.112:8081/download/o2o/images' + newImage;
                $(".like-product-img").attr("src",path);
            }else{
                var array = ["blue","green","orange","purple","yellow"];
                var i = Math.floor(Math.random()*5); //可均衡获取0到4的随机整数。
                $(".like-product-img").attr("src","img/"+ array[i] +"_bear.png");
                $(".like-product-img").attr("th:src","@{img/"+ array[i] +"_bear.png}");
            }
        },
        error: function (){
            alert("当前网络不稳定......");
        }
    });
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
            // 验证支付密码
            var payPwd = prompt("请输入支付密码","");
            var jsonValue = JSON.parse(Cookies.get('UserObjJson'));

            var objJson1 = {
                "telephone":jsonValue.telephone,
                "password":payPwd
            };

            var URL1 = "/user/purchase";
            $.ajax({
                type:"POST",
                url: URL1,
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: JSON.stringify(objJson1),
                async:true,
                success: function (response) {
                    if(response.code == 1){

                        var totalPrice = parseFloat(price.substring(1))*parseInt(purchaseNumber) + "";
                        var objJson2 = {
                            "goodsId":goodsId,
                            "purchaseNumber":purchaseNumber,
                            "shopId":shopId,
                            "totalPrice":totalPrice
                        };

                        var URL2 = "/order/make_order";
                        $.ajax({
                            type:"POST",
                            url: URL2,
                            contentType: "application/json;charset=utf-8",
                            dataType: "json",
                            data: JSON.stringify(objJson2),
                            async:true,
                            success: function (response) {

                                    showWarningBox(response.msg);
                            },
                            error: function (){
                                showWarningBox("当前网络不稳定......");
                            }
                        });


                    }else{
                        showWarningBox(response.msg);
                    }

                },
                error: function (){
                    showWarningBox("当前网络不稳定......");
                }
            });

        }

    }

}
/************************订单处理****************************/

//我的收藏测试用，谁删谁铁废物。你爸爸我就删，狗儿子
function showOrders() {


    var URL = "/order/get_user_order";

    $.ajax({
        type:"POST",
        url: URL,
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        data: "",
        async:true,
        success: function (response) {

            console.log(response);

            if(response.code == 1){


                if(response.data.length < 1){
                    num = "-1";
                }else{
                    for(var i = 0; i < response.data.length; i ++) {
                        var temp = $('<li class="like-detail">' +
                        '<span class="orders-goods-name">'+response.data[i].goodsObject.goodName+'</span>' +
                        '<span class="orders-deal">处理状态:</span>' +
                        '<span class="orders-id">订单ID:</span>' +
                        '<span class="orders-goods-num">购买数量:</span>' +
                        '<span class="orders-goods-price">购买总价:</span>' +
                        '<span class="orders-goods-time">下单时间:</span>' +
                        '<span class="orders-deal-1">'+showDeal(response.data[i].deal)+'</span>' +
                        '<span class="orders-goods-id-1">'+response.data[i].orderId+'</span>' +
                        '<span class="orders-goods-num-1">'+countNum(response.data[i].totalPrice,response.data[i].goodsObject.price)+'</span>' +
                        '<span class="orders-goods-price-1">'+response.data[i].totalPrice+'</span>' +
                        '<span class="orders-goods-time-1">'+response.data[i].deal+'</span>' +
                        '<button class="get-order order-btn" id="'+ response.data[i].orderId +'" data-deal="'+ response.data[i].deal +'" onclick="ensureOrder(this)">确定收货</button>' +
                        '<button class="cancel-order order-btn" id="'+ response.data[i].orderId +'" data-deal="'+ response.data[i].deal +'" onclick="cancelOrder(this)">取消订单</button>' +
                        '<img src="http://192.168.35.112:8081/download/o2o/images'+ response.data[i].goodsObject.image.replace(/\\/g,"/") +'" class="orders-img"></li>');

                        $('#order-main').append(temp);
                    }

                }


            }else{
                alert(response.msg);
            }


        },
        error: function (){
            alert("当前网络不稳定......");
        }
    });


}
function ensureOrder(obj) {
    if(parseInt(obj.getAttribute('data-deal'))>=0){

        showWarningBox("收货成功");

        // 后台

        var objJson = {
            "orderId":obj.id,
            "deal": 3
        };

        var URL = "/order/update_order";


        $.ajax({
            type:"POST",
            url: URL,
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            data: JSON.stringify(objJson),
            async:true,
            success: function (response) {
                console.log(response.msg);

                if(response.code == 1){
                    $(obj).parent().children('.orders-deal-1').text(showDeal(3));

                    $(obj).parent().children('get-order order-btn').setAttribute("data-deal","3");
                    $(obj).parent().children('cancel-order order-btn').setAttribute("data-deal","3");
                }
            },
            error: function (){
                alert("当前网络不稳定......");
            }
        });
    }else{
        alert("订单已经取消");
    }



}

function cancelOrder(obj) {

    if(parseInt(obj.getAttribute('data-deal')) <= 1){
        // 网页上删除改订单
        showWarningBox("取消订单成功，等待商家确认");

        // 数据库删除订单

        var objJson = {
            "orderId":obj.id,
            "deal":-1
        };

        var URL = "/order/update_order";


        $.ajax({
            type:"POST",
            url: URL,
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            data: JSON.stringify(objJson),
            async:true,
            success: function (response) {
                console.log(response.msg);
                if(response.code == 1){
                    $(obj).parent().children('.orders-deal-1').text(showDeal(-1));

                    $(obj).parent().children('get-order order-btn').setAttribute("data-deal","-1");
                    $(obj).parent().children('cancel-order order-btn').setAttribute("data-deal","-1");
                }
            },
            error: function (){
                alert("当前网络不稳定......");
            }
        });
    }else{
        alert("订单已经发送，无法取消");
    }





}
function showWarningBox(tar) {
    $('.warning-box').hide();
    $('.warning-box').text(tar).fadeIn();
    setTimeout(function(){
        $('.warning-box').fadeOut();
    },2000);
}

function countNum(totalPrice,price){
    return parseFloat(totalPrice)/parseFloat(price);
}

function showDeal(deal) {

    var i = parseInt(deal);
    switch(i){
        case -2:{return "订单已经退货";}break;
        case -1:{return "等待商家确认退货";}break;
        case 0:{return "订单等待商家发货";}break;
        case 1:{return "订单货物已经发送";}break;
        case 2:{return "订单货物到达目的地";}break;
        case 3:{return "订单货物已经签收";}break;
        case 4:{return "订单全部完成";}break;
        default:{
            return "订单出现异常，请等待系统处理";
        }
    }
}


/************************图片显示处理****************************/
function Register_uploadImage(){
    $("#reg-user-file").click();
    showImage();
}


function showImage(){
    var inputJ = $("#reg-user-file");

    inputJ.change(function(e){
        var file     = e.target.files[0],//拿到原始对象
            thisType = file.type,//获取到表面的名称，可判断文件类型
            thisSize = file.size,//文件的大小
            reader   = new FileReader();

        //readAsDataURL(file),读取文件，将文件以数据URL的形式保存在result的属性中
        reader.readAsDataURL(file);

        //文件加载成功以后，渲染到页面
        reader.onload = function(e) {
            $("#user-image").attr("src", e.target.result);
        }
    });
}

/************************收藏处理****************************/


function showCollections() {

    var URL = "/goods/user_collect_goods";

    $.ajax({
        type:"POST",
        url: URL,
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        data: "",
        async:true,
        success: function (response) {

            console.log(response);

            if(response.code == 1){


                if(response.data.length < 1){
                    num = "-1";
                }else{
                    for(var i = 0; i < response.data.length; i ++) {
                        var temp = $('<li class="like-detail" ><span class="product-name pointer" onclick="productBox('+response.data[i].goodId+')">'+response.data[i].goodName+'</span>' +
                            '<span class="inventory">库存:</span>' +
                            '<span class="unlike" id="'+ response.data[i].goodId +'" onclick="unlikeGood(this)">×</span>' +
                            '<span class="inventory-num">'+response.data[i].sales+'</span>' +
                            '<span class="good-price">￥'+response.data[i].price+'</span>' +
                            '<img src="http://192.168.35.112:8081/download/o2o/images'+ response.data[i].image.replace(/\\/g,"/") +'" class="product-img pointer" onclick="productBox('+response.data[i].goodId+')"></li>');
                        $('#like-main').append(temp);
                    }

                }


            }else{
                alert(response.msg);
            }


        },
        error: function (){
            alert("当前网络不稳定......");
        }
    });

}
//收藏商品详情界面
function likeDetBox() {

}


// 取消收藏
function unlikeGood(obj) {
    // 网页上删除改商品
    showWarningBox("取消收藏");
    $(obj).parent().remove();

    // 数据库删除改商品

    var objJson = {
        "goodsId":obj.id
    };

    var URL = "/goods/un_collect_goods";


    $.ajax({
        type:"POST",
        url: URL,
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        data: JSON.stringify(objJson),
        async:true,
        success: function (response) {
            console.log(response.msg)
        },
        error: function (){
            alert("当前网络不稳定......");
        }
    });

    
}

/************************Cookies处理****************************/

function checkCookies(){
    if(!Cookies.get('UserObjJson')){

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


        getUserInfo(jsonValue.telephone);
        
    }
 }

/************************用户获取个人信息、修改个人信息处理****************************/
function getUserInfo(telephone){


    var objJson = {
        "telephone":telephone
    };

    var URL = "/user/get_info";



    $.ajax({
        type:"POST",
        url: URL,
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        data: JSON.stringify(objJson),
        async:true,
        success: function (response) {

            if(response.code==1){
                console.log(response);

                $('#user-id').val(response.data.telephone);
                $('#user-name').val(response.data.name);
                $('#user-email').val(response.data.email);
                $('#user-address').val(response.data.deliveryaddress);
                if(response.data.sex=="男"){
                    $('#user-man').attr('checked','true');
                }else{
                    $('#user-woman').attr('checked','true');
                }


                if(response.data.image.length > 10){
                    var newImage = response.data.image.replace(/\\/g,"/");
                    var path = 'http://192.168.35.112:8081/download/o2o/images' + newImage;
                    $("#user-image").attr("src",path);
                }else{
                    var array = ["blue","green","orange","purple","yellow"];
                    var i = Math.floor(Math.random()*5); //可均衡获取0到4的随机整数。
                    $("#user-image").attr("src","img/"+ array[i] +"_bear.png");
                    $("#user-image").attr("th:src","@{img/"+ array[i] +"_bear.png}");
                }


            }else{
                alert(response.msg);
            }


        },
        error: function (){
            alert("访问失败，请检查网络、路径......");
        }
    });

}


function userModifyInfo(){

    var registerUrl = '/user/update_user_info';

    //创建json字符串
    var objJson = {};
    objJson.telephone = $('#user-id').val();
    objJson.name = $('#user-name').val();
    objJson.email = $('#user-email').val();

    if($("input[name='sex']:checked").val() == "1"){
        objJson.sex = "男";
    }else {
        objJson.sex = "女";
    }
    objJson.deliveryaddress = $('#user-address').val();

    var formData = new FormData();//创建传输对象
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


function userModifyImage(){

    var userFile = document.getElementById('reg-user-file');
    var fileNameArr = userFile.value.toLowerCase().split('.');

    var suffix = fileNameArr[fileNameArr.length-1];
    //如果后缀为空
    if(suffix != ""){

        if(suffix=='gif'||suffix=='jpg'||suffix=='bmp'||suffix=='png'||suffix=='jpeg'){
            var imgSize = userFile.files[0].size;
            //alert("图片大小："+imgSize+"B");
            if(imgSize>1024*1024*3){
                alert("注册失败，图片大于3M");
                return false;
            }
        }else{
            alert("请选择格式为*.jpg、*.gif、*.bmp、*.png、*.jpeg 的图片");
            return false;
        }
    }

    var registerUrl = '/user/update_user_image';

    //创建json字符串
    var objJson = {};
    objJson.telephone = $('#user-id').val();

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





//Request定义
(function ($) {
    $.extend({
        Request: function (m) {
        var sValue = location.search.match(new RegExp("[\?\&]" + m + "=([^\&]*)(\&?)", "i"));
        return sValue ? sValue[1] : sValue;
    },
    UrlUpdateParams: function (url, name, value) {
        var r = url;
        if (r != null && r != 'undefined' && r != "") {
            value = encodeURIComponent(value);
            var reg = new RegExp("(^|)" + name + "=([^&]*)(|$)");
            var tmp = name + "=" + value;
            if (url.match(reg) != null) {
                r = url.replace(eval(reg), tmp);
            }
            else {
                if (url.match("[\?]")) {
                    r = url + "&" + tmp;
                } else {
                    r = url + "?" + tmp;
                }
            }
        }
        return r;
    }
 
    });
})(jQuery);


