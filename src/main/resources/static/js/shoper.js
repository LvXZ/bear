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
    $('.products.dynamic').mouseout(function(){
        $(this).children('.product').css("backgroundColor", "#4f4e4e");
    });


    $('.logOut').mouseover(function(){
        $(this).children('.sonMenu').show();
    });
    $('.logOut').mouseout(function(){
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

});



/***************************商品管理*****************************/
function showShopGoods() {
    var objJson = {
        "shopId":$("#mod-shop-id").val()
    };


    var URL = "/goods/find_shop_goods";

    $.ajax({
        type:"POST",
        url: URL,
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        data: JSON.stringify(objJson),
        async:true,
        success: function (response) {

            console.log(response);

            if(response.code == 1){


                if(response.data.length < 1){
                    num = "-1";
                }else{
                    for(var i = 0; i < response.data.length; i ++) {
                        var temp = $('<li class="like-detail"><span class="product-name">'+response.data[i].goodName+'</span>' +
                            '<span class="inventory">库存:</span>' +
                            '<span class="inventory-num">'+response.data[i].sales+'</span>' +
                            '<span class="good-price">￥'+response.data[i].price+'</span>' +
                            '<img src="http://192.168.35.112:8081/download/o2o/images'+ response.data[i].image.replace(/\\/g,"/") +'" class="product-img"  onclick="OpenDetail()"></li>');
                        $('#info-main').append(temp);
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





/***************************订单管理*****************************/
function showShopOrders() {


    var URL = "/order/get_shop_order";

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
                            '<span class="orders-id">'+response.data[i].orderId+'</span>' +
                            '<span class="orders-deal">处理状态:</span>' +
                            '<span class="orders-goods-name">商品名称:</span>' +
                            '<span class="orders-goods-num">购买数量:</span>' +
                            '<span class="orders-goods-price">购买总价:</span>' +
                            '<span class="orders-goods-time">下单时间:</span>' +
                            '<span class="orders-deal-1">'+showDeal(response.data[i].deal)+'</span>' +
                            '<span class="orders-goods-name-1">'+response.data[i].goodsObject.goodName+'</span>' +
                            '<span class="orders-goods-num-1">'+countNum(response.data[i].totalPrice,response.data[i].goodsObject.price)+'</span>' +
                            '<span class="orders-goods-price-1">'+response.data[i].totalPrice+'</span>' +
                            '<span class="orders-goods-time-1">'+response.data[i].deal+'</span>' +
                            '<img src="http://192.168.35.112:8081/download/o2o/images'+ response.data[i].goodsObject.image.replace(/\\/g,"/") +'" class="orders-img"></li>');


                        $('#address-main').append(temp);
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
function countNum(totalPrice,price){
    return parseFloat(totalPrice)/parseFloat(price);
}

function showDeal(deal) {

    var i = parseInt(deal);
    switch(i){
        case -1:{return "订单已经退货";}break;
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


/***************************Coolies处理*****************************/


function checkCookies(){
    if(!Cookies.get('UserObjJson')){

        location.href="/index";

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



        //店铺信息
        findShop();

    }



}

/***************************商品上架*****************************/
function addGoods() {


    var goodsFile = document.getElementById('reg-goods-file');
    var fileNameArr = goodsFile.value.toLowerCase().split('.');

    var suffix = fileNameArr[fileNameArr.length-1];
    //如果后缀为空
    if(suffix != ""){

        if(suffix=='gif'||suffix=='jpg'||suffix=='bmp'||suffix=='png'||suffix=='jpeg'){
            var imgSize = goodsFile.files[0].size;
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

    var registerUrl = '/goods/add';

    //创建json字符串
    var objJson = {};

    //从绑定中获取
    objJson.shopId = $('#mod-shop-id').val();

    objJson.goodName = $('#reg-goods-name').val();
    objJson.price = $('#reg-goods-price').val();
    objJson.sales = $('#reg-goods-sales').val();
    objJson.category = $('#reg-goods-category').val();
    objJson.introduction = $('#reg-goods-introduction').val();
    objJson.invenory = $('#reg-goods-invenory').val();


    var thumbnail = $('#reg-goods-file')[0].files[0];//图片

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
                alert('提交失败！');
                //$('#captcha_img').click();
            }
        },
        error: function (){
            alert("访问失败，请检查网络、路径......");
        }
    });

}


/***************商品、店铺图片显示到网页****************/
function getGoodsImage(){
    $("#reg-goods-file").click();
    showImage("reg-goods-file","show-goods-img-1");

}
function getShopImage(){
    $("#mod-shop-file").click();
    showImage("mod-shop-file","show-shop-img-2");
}



function showImage(file_addr,show_addr){
    var inputJ = $('#'+ file_addr);

    inputJ.change(function(e){
        var file     = e.target.files[0],//拿到原始对象
            thisType = file.type,//获取到表面的名称，可判断文件类型
            thisSize = file.size,//文件的大小
            reader   = new FileReader();

        //readAsDataURL(file),读取文件，将文件以数据URL的形式保存在result的属性中
        reader.readAsDataURL(file);

        //文件加载成功以后，渲染到页面
        reader.onload = function(e) {
            $('#'+ show_addr).attr("src", e.target.result);
        }
    });

}

/***************店铺获取信息，店铺修改信息****************/
function findShop(){
    var jsonValue = JSON.parse(Cookies.get('UserObjJson'));
    var inputUserId = jsonValue.telephone;
    var objJson = {
        "ownerId":inputUserId
    };

    var URL = "/shop/get_shop_2";

    Find_AJAXFunction(objJson,URL);
}

function Find_AJAXFunction(objJson,URL) {

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

                showShopInfo(response.data);

            }else{
                //alert(response.msg);
                location.href="/addshop"
            }


        },
        error: function (){
            alert("访问失败，请检查网络、路径......");
        }
    });
}

function showShopInfo(data) {

    /*$("img").one("error", function(){
        var array = ["blue","green","orange","purple","yellow"];
        var i = Math.floor(Math.random()*5); //可均衡获取0到4的随机整数。
        $(this).attr("src","../static/img/"+ array[i] +"_bear.png");
    });*/

    $("#mod-shop-id").val(data.shopId);
    $("#mod-owner-id").val(data.ownerId);
    $("#mod-shop-name").val(data.shopName);
    $("#mod-shop-priority").val(data.priority);
    $("#mod-shop-category").val(data.category);
    $("#mod-shop-introduction").val(data.introduction);
    $("#mod-shop-card").val(data.idCard);
    $("#mod-shop-bank").val(data.bankAccount);
    $("#mod-shop-logistics").val(data.logistics);


    if(data.image.length > 0){
        var newImage = data.image.replace(/\\/g,"/")
        var path = 'http://192.168.35.112:8081/download/o2o/images' + newImage;
        $("#show-shop-img-2").attr("src",path);
    }else{
        var array = ["blue","green","orange","purple","yellow"];
        var i = Math.floor(Math.random()*5); //可均衡获取0到4的随机整数。
        $("#show-shop-img-2").attr("src","../static/img/"+ array[i] +"_bear.png");
    }

    //商品管理
    showShopGoods();
    //订单管理
    showShopOrders();
}

function shopModifyInfo() {

    var registerUrl = '/shop/modify_shop_info';

    //创建json字符串
    var objJson = {};
    objJson.shopId = $('#mod-shop-id').val();
    objJson.ownerId = $('#mod-owner-id').val();
    objJson.shopName = $('#mod-shop-name').val();
    objJson.category = $('#mod-shop-category').val();
    objJson.introduction = $('#mod-shop-introduction').val();
    objJson.priority = $("#mod-shop-priority").val();
    objJson.logistics = $("#mod-shop-logistics").val();
    objJson.bankAccount = $("#mod-shop-bank").val();
    objJson.idCard = $("#mod-shop-card").val();

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

function shopModifyImage() {


    var shopFile = document.getElementById('mod-shop-file');
    var fileNameArr = shopFile.value.toLowerCase().split('.');

    var suffix = fileNameArr[fileNameArr.length-1];
    //如果后缀为空
    if(suffix != ""){

        if(suffix=='gif'||suffix=='jpg'||suffix=='bmp'||suffix=='png'||suffix=='jpeg'){
            var imgSize = shopFile.files[0].size;
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

    var registerUrl = '/shop/modify_shop_image';

    //创建json字符串
    var objJson = {};
    objJson.shopId = $('#mod-shop-id').val();

    var thumbnail = $('#mod-shop-file')[0].files[0];//图片

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


function OpenDetail() {
	$(".detail-box").css("display","block");


}

function CloseDetail() {

	$(".detail-box").css("display","none");

}


