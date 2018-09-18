
// 获取滚动条的滚动距离，解决浏览器不兼容问题，返回一个对象
function getScrollOffset() {
	if(window.pageXOffset) { //IE9以上兼容
		return {
			x : window.pageXOffset,
			y : window.pageYOffset
		}
	}else {
		return {//IE9以下
			x : document.body.scrollLeft + document.documentElement.scrollLeft,
			y : document.body.scrollTop + document.documentElement.scrollTop
		}
	}
}

//获取视口的尺寸
function getViewportOffset() {
	if(0 && window.innerWidth) {//IE9以上
		return {
			w : window.innerWidth,
			h : window.innerHeight
		}
	}
	else {
			if(document.compatMode === "BackCompat") {//怪异模式下
				return {
					w : document.body.clientWidth,
					h : document.body.clientHeight
				}
			}else {//标准模式下
				return {
					w : document.documentElement.clientWidth,
					h : document.documentElement.clientHeight
				}
			}
		}
}

//获取元素坐标（到文档距离）
function getElementPosition(target) {
	if(target.offsetParent == document.body) {
		return {
			w : target.offsetTop,
			h : target.offsetLeft
		}
	}else {
		return {
			w : target.offsetTop + parseInt(window.getComputedStyle(target.offsetParent).borderWidth) + getElementPosition(target.offsetParent).w,
			h : target.offsetLeft + parseInt(window.getComputedStyle(target.offsetParent).borderWidth) + getElementPosition(target.offsetParent).h
		}
	}
}

//查看元素的计算属性
function getStyle(elem, prop) {
	if(window.getComputedStyle) {
		return window.getComputedStyle(elem, null)[prop];
	}else {
		return elem.currentStyle[prop];
	}
}

//兼容性绑定事件处理程序
function addEvent(elem, type, handle) {
	if(elem.addEventListener) {//W3C标准方法
		elem.addEventListener(type, handle, false);
	}else if(elem.attachEvent) {//IE独有
		elem.attachEvent("on" + type, function() {
			handle.call(elem);
		});
	}else{//兼容性最好的，都可以用的
		elem["on" + type] = handle;
	}
}

//兼容性取消绑定事件
function removeEvent(elem, type, handle) {
	if(elem.removeEventListener) {//W3C标准方法
		elem.removeEventListener(type, handle, false);
	}else if(elem.detachEvent) {//IE独有
		elem.detachEvent("on" + type, function() {
			handle.call(elem);
		});
	}else{//兼容性最好的，都可以用的
		elem["on" + type] = null;
	}
}

//取消冒泡事件
function stopBubble(event) {
	if(event.stopPropagation) {//W3C标准方法
		event.stopPropagation();
	}else{//IE9以下和谷歌
		event.cancelBubble = true;
	}
}

//阻止默认事件
function cancelHandler(event) {
	if(event.preventDefault) {//W3C标准方法
		event.preventDefault();
	}else{//兼容IE
		event.returnValue = false;
	}
}

//拖拽元素
//元素行间样式要有定位，left和top值为0
function drag (elem) {
	var disX,
		dixY;
	var handle = function(e){//按下鼠标响应
		var event = e || window.event;//获取事件对象
		disX = event.pageX - parseInt(getStyle(elem, "left"));//获取事件发生时鼠标位置
		disY = event.pageY - parseInt(getStyle(elem, "top"));
		var mouseMove = function(e) {//鼠标移动事件处理函数
			var event = e || window.event;
			elem.style.left = event.pageX - disX + "px";
			elem.style.top = event.pageY - disY + "px";
		}
		var mouseUp = function(e) {//松开鼠标事件处理函数
			var event = e || window.event;
			removeEvent(document, "mousemove", mouseMove);//取消绑定事件
			removeEvent(document, "mouseup", mouseUp);//取消绑定事件
		}
		addEvent(document, "mousemove", mouseMove);
		addEvent(document, "mouseup", mouseUp);
		stopBubble(event);//阻止冒泡事件
		cancelHandler(event);//阻止默认事件
	}
	addEvent(elem, "mousedown", handle);
}