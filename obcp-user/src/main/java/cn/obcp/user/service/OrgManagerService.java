package cn.obcp.user.service;

import cn.obcp.base.Page;
import cn.obcp.base.RetData;
import cn.obcp.base.service.BaseService;
import cn.obcp.user.domain.TOrgManager;

import java.util.List;

public interface OrgManagerService extends BaseService<TOrgManager, Long> {

     Page<TOrgManager> findByOrgManagerPage(Page<TOrgManager> page);

    boolean addManager(List<String> userIds, Long orgId);
}
