


/***************************Coolies处理*****************************/


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


    }



}

/***************************商家注册*****************************/
function getShopImage(){
    $("#reg-shop-file").click();
    showImage("reg-shop-file","show-shop-img-1");
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


function shopRegister() {


    var shopFile = document.getElementById('reg-shop-file');
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

    var registerUrl = '/shop/register';

    //创建json字符串
    var objJson = {};

    //从cookies获取拥有者
    var jsonValue = JSON.parse(Cookies.get('UserObjJson'));
    objJson.ownerId = jsonValue.telephone;


    objJson.shopName = $('#reg-shop-name').val();
    objJson.category = $('#reg-shop-category').val();
    objJson.idCard = $('#reg-shop-card').val();
    objJson.bankAccount = $('#reg-shop-bank').val();
    objJson.logistics = $('#reg-shop-logistics').val();
    objJson.introduction = $('#reg-shop-introduction').val();


    var thumbnail = $('#reg-shop-file')[0].files[0];//图片

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
                window.open("/shoper", "_self");

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
