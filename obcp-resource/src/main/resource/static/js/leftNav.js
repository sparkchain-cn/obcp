function navBar(strData) {
	var data;
	if(typeof(strData) == "string") {
		var data = JSON.parse(strData); //解析出来的是字符串需要转换一下
	} else {
		data = strData;
	}
	var params = buildParams();
	var ulHtml = '<ul class="layui-nav layui-nav-tree">';
	for(var i = 0; i < data.length; i++) {
		if(data[i].spread) {
			ulHtml += '<li class="layui-nav-item layui-nav-itemed">';
		} else {
			ulHtml += '<li class="layui-nav-item">';
		}
		if(data[i].children != undefined && data[i].children.length > 0) {
			ulHtml += '<a>';
			if(data[i].icon != undefined && data[i].icon != '') {
				if(data[i].icon.indexOf("icon-") != -1) {
					ulHtml += '<i class="iconfont ' + data[i].icon + '" data-icon="' + data[i].icon + '"></i>';
				} else {
					ulHtml += '<i class="layui-icon" data-icon="' + data[i].icon + '">' + data[i].icon + '</i>';
				}
			}
			ulHtml += '<cite>' + data[i].title + '</cite>';
			ulHtml += '<span class="layui-nav-more"></span>';
			ulHtml += '</a>';
			ulHtml += '<dl class="layui-nav-child">';
			for(var j = 0; j < data[i].children.length; j++) {
				if(data[i].children[j].target == "_blank") {
					ulHtml += '<dd><a data-url="' + data[i].children[j].href + params + '" target="' + data[i].children[j].target + '">';
					if(data[i].children[j].children != undefined && data[i].children[j].children.length > 0) {
						console.log(111)
					}
				} else {
					ulHtml += '<dd><a data-url="' + data[i].children[j].href + params + '">';
					if(data[i].children[j].children != undefined && data[i].children[j].children.length > 0) {
						ulHtml += '<span class="layui-nav-more"></span>';
						ulHtml += '<cite>' + data[i].children[j].title + '</cite></a>';

						ulHtml += '</a>';

						ulHtml += '<ol class="layui-nav-child">';

						for(var k = 0; k < data[i].children[j].children.length; k++) {
							ulHtml += '<li><a data-url="' + data[i].children[j].children[k].href + params +'" target="' + data[i].children[j].children[k].target + '">';

							if(data[i].children[j].children[k].icon.indexOf("icon-") != -1) {
								ulHtml += '<i class="iconfont ' + data[i].children[j].children[k].icon + '" data-icon="' + data[i].children[j].children[k].icon + '"></i>';
							} else {
								ulHtml += '<i class="layui-icon" data-icon="' + data[i].children[j].children[k].icon + '">' + data[i].children[j].children[k].icon + '</i>';
							}
							ulHtml += '<cite>' + data[i].children[j].children[k].title + '</cite></a>';
							ulHtml += '</a>';
							ulHtml += "</li>";
						}

						ulHtml += "</ol>";

						ulHtml += '</dd>';

					} else {
						if(data[i].children[j].icon != undefined && data[i].children[j].icon != '') {
							if(data[i].children[j].icon.indexOf("icon-") != -1) {
								ulHtml += '<i class="iconfont ' + data[i].children[j].icon + '" data-icon="' + data[i].children[j].icon + '"></i>';
							} else {
								ulHtml += '<i class="layui-icon" data-icon="' + data[i].children[j].icon + '">' + data[i].children[j].icon + '</i>';
							}
						}
						ulHtml += '<cite>' + data[i].children[j].title + '</cite></a></dd>';
					}
				}

			}
			ulHtml += "</dl>";
		} else {
			if(data[i].target == "_blank") {
				ulHtml += '<a data-url="' + data[i].href + params +'" target="' + data[i].target + '">';
			} else {
				ulHtml += '<a data-url="' + data[i].href + params + '">';
			}
			if(data[i].icon != undefined && data[i].icon != '') {
				if(data[i].icon.indexOf("icon-") != -1) {
					ulHtml += '<i class="iconfont ' + data[i].icon + '" data-icon="' + data[i].icon + '"></i>';
				} else {
					ulHtml += '<i class="layui-icon" data-icon="' + data[i].icon + '">' + data[i].icon + '</i>';
				}
			}
			ulHtml += '<cite>' + data[i].title + '</cite></a>';
		}
		ulHtml += '</li>';
	}
	ulHtml += '</ul>';
	return ulHtml;
}

function buildParams(){
	var pathname = window.location.pathname;
	var index = pathname.indexOf("/app/");
	if(index == 0){
		return "?appkey="+document.getElementById("a_c_t_r_k_val").value;
	}
	return "";
}