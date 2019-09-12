package cn.obcp.user.VO;

import com.google.common.collect.Lists;
import cn.obcp.user.domain.TDepartment;
import cn.obcp.user.enmu.ResourceStateEnmu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OrgTreeNode extends BaseTreeNode<TDepartment,OrgTreeNode> {

    private  boolean open = true;


    public OrgTreeNode(){

    }

    public OrgTreeNode create(String id, String name, String pid, boolean checked, boolean state, List<OrgTreeNode> children) {
        OrgTreeNode zTreeNode = new OrgTreeNode();
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
    public OrgTreeNode addChild(List<TDepartment> list, OrgTreeNode parent) {
        Iterator<TDepartment> iterator = list.iterator();
        while(iterator.hasNext()){
            TDepartment resources = iterator.next();
            if( String.valueOf(resources.getPid()).equals(parent.getId())) {//父级id等资源父级id
                List<OrgTreeNode> childrens = parent.getChildren();
                boolean state = resources.getStatus().equals(ResourceStateEnmu.DISABLED.getStatus());
                List<OrgTreeNode> children = new ArrayList<>();
                if (childrens == null) {
                    childrens = Lists.newArrayList();
                }
                childrens.add(new OrgTreeNode().create(String.valueOf( resources.getId()),resources.getName(),
                        String.valueOf(resources.getPid()), false, state,children));
                parent.setChildren(childrens);
            }
        }

        if(parent.getChildren() != null && parent.getChildren().size() > 0) {
            Iterator<OrgTreeNode> treeitr =  parent.getChildren().iterator();
            while(treeitr.hasNext()) {
                OrgTreeNode node = treeitr.next();
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
    public List<OrgTreeNode> bulid(List<TDepartment> list) {
        List<OrgTreeNode> baseNode = new ArrayList<OrgTreeNode>(); //父节点
        list.forEach( r -> {
               // boolean state = r.getStatus().equals(ResourceStateEnmu.DISABLED.getStatus());
                if (r.getPid() == null) {
                    baseNode.add(new OrgTreeNode().create(String.valueOf(r.getId()), r.getName(),
                            String.valueOf(r.getPid()), false, false, new ArrayList<>()));
                }

        });  //得到全部根节点

        baseNode.forEach(node ->{
            OrgTreeNode current = addChild(list, node);
        });


        return baseNode;
    }


    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
