package cn.obcp.user.VO;

import com.google.common.collect.Lists;
import cn.obcp.user.domain.TResources;
import cn.obcp.user.enmu.ResourceStateEnmu;
import cn.obcp.user.enmu.ResourceType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *  zTree树形节点结构
 */
public class ZTreeNode extends BaseTreeNode<TResources,ZTreeNode>{


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
    public ZTreeNode addChild(List<TResources> list, ZTreeNode parent) {
                Iterator<TResources> iterator = list.iterator();
        while(iterator.hasNext()){
            TResources resources = iterator.next();
            if( String.valueOf(resources.getParentid()).equals(parent.getId())) {//父级id等资源父级id
                List<ZTreeNode> childrens = parent.getChildren();
                boolean state = resources.getState().equals(ResourceStateEnmu.DISABLED.getStatus());
                List<ZTreeNode> children = new ArrayList<>();
                if (childrens == null) {
                    childrens = Lists.newArrayList();
                }
                childrens.add(new ZTreeNode().create(String.valueOf( resources.getId()),resources.getName(),
                        String.valueOf(resources.getParentid()), resources.isChecked(), state,children));
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
    public List<ZTreeNode> bulid(List<TResources> list) {
        List<ZTreeNode> baseNode = new ArrayList<ZTreeNode>(); //父节点
        list.forEach( r -> {
            if(r.getResTypeCode().equalsIgnoreCase(ResourceType.PAGEFOLDER.getType())) {
                boolean state = r.getState().equals(ResourceStateEnmu.DISABLED.getStatus());
                if (r.getParentid() == null) {
//                	System.out.println(String.format("rid:%s , name:%s , isChecked:%s ", String.valueOf(r.getId()), r.getName(), r.isChecked()));
                    baseNode.add(new ZTreeNode().create(String.valueOf(r.getId()), r.getName(),
                            String.valueOf(r.getParentid()), r.isChecked(), state, new ArrayList<>()));
                }
            }
        });  //得到全部根节点

        baseNode.forEach(node ->{
            ZTreeNode current = addChild(list, node);
        });


        return baseNode;
    }
}
