/**
 *  正则匹配路径参数
 */
var getUrlParam = function (name) {  
	var r = Request[name];
   if (r != null && r != 'undefined') {
       return r;  //返回参数值 
   } else {
       return ""; 
   }
}

//截取url中的参数
function GetRequest() {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
        }
    }
    return theRequest;
}

var Request = new Object();
Request = GetRequest();
