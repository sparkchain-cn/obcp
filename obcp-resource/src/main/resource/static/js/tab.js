var element,$;
layui.config({
	base : "js/"
}).use(['form','element','layer','jquery'],function(){
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : parent.layer;		
		$ = layui.jquery;
		element = layui.element;

})


//创建新选项卡
function createTab(title,url,id,parentTab){
	var filter = "bodyTab";
	var exist = $("#top_tabs ul");
	var menu=[];
	parent.element.tabDelete(filter, id);//删除当前ID的选项卡
	parent.element.tabAdd(filter, {
        title : title,
        content :"<iframe src='"+url+"' data-id='"+id+"' data-pid='"+parentTab+"'></frame>",
        id : id
    })
	parent.element.render("tab", filter);//渲染
	//当前窗口内容
	/*var curmenu = {
		"icon" : $(this).find("i.layui-icon").attr("data-icon"),
		"title" : title,
		"href" : url,
		"layId" : id
	}
	
	menu.push(curmenu);
	window.sessionStorage.setItem("menu",JSON.stringify(menu)); //打开的窗口
	window.sessionStorage.setItem("curmenu",JSON.stringify(curmenu));  //当前的窗口*/
	parent.element.tabChange(filter, id);//切换
	
}

