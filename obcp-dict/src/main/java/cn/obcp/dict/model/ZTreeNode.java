package cn.obcp.dict.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

import cn.obcp.base.utils.StringUtils;
import cn.obcp.dict.domain.TDict;

/**
 *  zTree树形节点结构
 */
public class ZTreeNode extends BaseTreeNode<TDict,ZTreeNode>{


    public ZTreeNode(){

    }

    public ZTreeNode create(String id, String name, String pid, boolean checked, boolean state, List<ZTreeNode> children) {
        ZTreeNode zTreeNode = new ZTreeNode();
        zTreeNode.setId(id);
        zTreeNode.setName(name);
        zTreeNode.setpId(pid);
        zTreeNode.setChecked(checked);
        zTreeNode.setDisabled(state);
        zTreeNode.setDrag(false);
        zTreeNode.setChildren(children);
        return zTreeNode;
    }

    @Override
    public ZTreeNode addChild(List<TDict> list, ZTreeNode parent) {
                Iterator<TDict> iterator = list.iterator();
        while(iterator.hasNext()){
            TDict resources = iterator.next();
            if(parent.getId().equals(resources.getParent())) {//父级id等资源父级id
                List<ZTreeNode> childrens = parent.getChildren();
                boolean state = resources.getStatus().equals("1");
                List<ZTreeNode> children = new ArrayList<>();
                if (childrens == null) {
                    childrens = Lists.newArrayList();
                }
                childrens.add(new ZTreeNode().create(resources.getCode(),resources.getName(),resources.getParent(), false, state,children));
                parent.setChildren(childrens);
            }
        }
        if(parent.getChildren() != null && parent.getChildren().size() > 0) {
            Iterator<ZTreeNode> treeitr =  parent.getChildren().iterator();
            while(treeitr.hasNext()) {
                ZTreeNode node = treeitr.next();
                if (!parent.getId().equals(parent.getpId())){
                    addChild(list, node);
                }
            }

        }else {
            return parent;
        }
        return parent;
    }

    @Override
    public List<ZTreeNode> bulid(List<TDict> list) {
        List<ZTreeNode> baseNode = new ArrayList<ZTreeNode>(); //父节点
        list.forEach( r -> {
            if (StringUtils.isNullOrEmpty(r.getParent())) {
                baseNode.add(new ZTreeNode().create(r.getCode(), r.getName(), r.getParent(), false, r.getStatus() == 1, new ArrayList<>()));
            }
        });  //得到全部根节点

        baseNode.forEach(node ->{
            ZTreeNode current = addChild(list, node);
        });


        return baseNode;
    }
}
