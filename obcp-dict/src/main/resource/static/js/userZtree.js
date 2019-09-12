var zTreeObj;
var rMenu = $("#rMenu");
/**
 * 初始化
 */
$(document).ready(function () {
    var time = new Date().getTime();
    var setting = {
        async:{
            enable: true,
            url:"/v1/dict/treeList",
            otherParam:{d:time},
            dataType:"json",
            dataFilter: filter
        },
        edit:{
            enable: true,
            showRemoveBtn:false,
            showRenameBtn:false,
            renameTitle:"重命名",
            drag:{
                isMove: true
            }
        },
        callback:{
            beforeDrop:beforeDrop,
            onDrop:onDrop,
            onRightClick:onRightClick,
            onClick:function (event, treeId, treeNode) {
                console.log(treeNode);
                node = treeNode;
                node_id = treeNode.code;
                zTreeObj.expandNode(node,!node.open,false,true);
                if (tableRender){
                    node_id = treeNode.id
                    tableRender.reload({where:{parent:node_id}, page: {
                            curr: 1
                        }})
                }
            }
        }
    };

    zTreeObj = $.fn.zTree.init($("#resourceTree"), setting);
});

/**
 *  右键菜单
 * @param event
 * @param treeId
 * @param treeNode
 */
function onRightClick(event, treeId, treeNode) {
    node = treeNode;
    node_id = treeNode.id;
    if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
        zTreeObj.cancelSelectedNode();
        showRMenu("root", event.clientX, event.clientY);
    } else if (treeNode && !treeNode.noR) {
        zTreeObj.selectNode(treeNode);
        showRMenu("node", event.clientX, event.clientY);
    }
}

/**
 * 右键菜单
 * @param type
 * @param x
 * @param y
 */
function showRMenu(type, x, y) {
    $("#rMenu").show();
    if (type=="root") {
        $("#m_del").hide();
        $("#m_check").hide();
        $("#m_unCheck").hide();
    } else {
        $("#m_del").show();
        $("#m_check").show();
        $("#m_unCheck").show();
    }

    y += document.body.scrollTop;
    x += document.body.scrollLeft;
    rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});

    $("body").bind("mousedown", onBodyMouseDown);
}

/**
 * 隐藏菜单
 */
function hideRMenu() {
    if (rMenu) rMenu.css({"visibility": "hidden"});
    $("body").unbind("mousedown", onBodyMouseDown);
}

/**
 * 鼠标事件
 * @param event
 */
function onBodyMouseDown(event){
    if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
        rMenu.css({"visibility" : "hidden"});
    }
}

/**
 *  右键添加
 */
function addTreeNode() {
    openLayer("添加资源",2,'/dictAdd.html?parent='+node_id,reflushByNodeId);
}

/**
 * 右键编辑资源
 */
function editTreeNode() {
    openLayer("编辑资源",2,'/dictAdd.html?code='+node_id,reflushByNodeId);
}

/**
 *  打开弹窗
 * @param title
 * @param type
 * @param url
 * @param cb
 */
function openLayer(title,type,url,cb) {
    layer.open({
        type: type,
        title: title,
        maxmin: true,
        resize: true,
        area: [ '60%', '70%'],
        content: url,
        zIndex: layer.zIndex, //重点1,
        end:function () {
            if (typeof cb == "function"){
                cb();
            }
        }
    });
}
/**
 *  拖拽前置
 * @param treeId
 * @param treeNodes
 * @param targetNode
 * @param moveType
 * @returns {boolean}
 */
function beforeDrop(treeId, treeNodes, targetNode, moveType) {
    return targetNode ? targetNode.drop !== false : true;
}

/**
 *  拖拽
 * @param e
 * @param treeId
 * @param treeNodesArray
 * @param targetNode
 */
function onDrop(e,treeId,treeNodesArray,targetNode,moveType) {
    console.log(treeNodesArray);
    if (treeNodesArray&&treeNodesArray.length > 0){
        var dropNodeIdArray = [];
        for (var i =0;i<treeNodesArray.length;i++){
            console.log(treeNodesArray[i].name);
            dropNodeIdArray.push(treeNodesArray[i].id);
        }
        console.log(treeNodesArray[0].name + "------------" + targetNode.name + "--------" + moveType);
        updateNodeParent(dropNodeIdArray,targetNode,moveType);
    }
}

/**
 *  更新资源数据
 * @param dropNodeIdArray
 * @param targetId
 */
function updateNodeParent(dropNodeIdArray,targetNode,moveType) {
    var targetId;
    if (targetNode){
        targetId = targetNode.id;
    }
    if (dropNodeIdArray.length > 0){
            console.log("target = "+targetNode.name);

       $.post("/v1/dict/updatDict",{dropNodeIds:dropNodeIdArray,parent:targetId,moveType:moveType},function (response) {
            if (!response.success){
                layer.msg(response.message || "修改失败",{icon:5},function () {
                   reflushByNodeId()
                });
            }
        })
    }
}

/**
 *  更新数据
 * @param nodeId
 */
function reflushByNodeId() {
    hideRMenu();//隐藏右键菜单
    if (zTreeObj){
        $("#resourceTree").empty();
         zTreeObj.reAsyncChildNodes(null,"reflush",false,function () {
             if (node){
                 zTreeObj.expandNode(node,true,false,true);
             }
         });
    }

    tableRender.reload();
}

/**
 * 数据预处理
 * @param treeId
 * @param parentNode
 * @param responseData
 * @returns {{name: string, children: *, open: boolean}}
 */
function filter(treeId, parentNode, responseData){
    var menus = {name:"目录",children:responseData,open:true, noR:false};
    return menus;
};