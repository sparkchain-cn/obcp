package cn.obcp.user.service.impl;

import com.google.common.collect.Lists;
import cn.obcp.base.Page;
import cn.obcp.base.RetData;
import cn.obcp.base.mapping.BaseMapper;
import cn.obcp.base.service.BaseServiceImpl;
import cn.obcp.user.domain.TOrgManager;
import cn.obcp.user.domain.TUserExtend;
import cn.obcp.user.enmu.StatusEnmu;
import cn.obcp.user.mapper.OrgManagerMapper;
import cn.obcp.user.service.OrgManagerService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service("scorgManagerService")
public class OrgManagerServicecImpl extends BaseServiceImpl<TOrgManager, Long> implements OrgManagerService {

    OrgManagerServicecImpl() {
        super();
        this.objClz = TOrgManager.class;
    }

    @Autowired
    private OrgManagerMapper orgManagerMapper;

    @Override
    @Autowired
    public void setBaseMapper(@Qualifier("orgManagerMapper") BaseMapper<TOrgManager> baseMapper) {
        this.baseMapper = baseMapper;

    }

    public Page<TOrgManager> findByOrgManagerPage(Page<TOrgManager> page){
       Integer count = orgManagerMapper.findCountByOrgManagerPage(page);
       List<TOrgManager> list = orgManagerMapper.findByOrgManagerPage(page);
       page.setTotal(count);
       page.setResult(list);
       return page;
    }

    public boolean addManager(List<String> userIds, Long orgId){
        List<TOrgManager> orgManagers = Lists.newArrayList();
        Date date = Calendar.getInstance().getTime();
        for(String uid:userIds){
            TOrgManager orgManager = new TOrgManager();
            orgManager.setId(uuidCreator.id());
            orgManager.setUserId(uid);
            orgManager.setOrgId(orgId.toString());
            orgManager.setStatus(StatusEnmu.NORMAL.getType());//正常
            orgManager.setCreatetime(date);
            orgManager.setUpdatetime(date);
            orgManagers.add(orgManager);
        }
        return orgManagerMapper.addManager(orgManagers,orgId);

    }
}
