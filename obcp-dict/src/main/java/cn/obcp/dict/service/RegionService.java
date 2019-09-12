package cn.obcp.dict.service;


import cn.obcp.base.service.BaseService;
import cn.obcp.dict.domain.TRegion;

/**
 * <pre>
 * 行政区划的业务接口
 * 该代码通过mgicode代码生成器生成
 * </pre>
 */

public interface RegionService extends BaseService<TRegion, Long> {

    int countAll();

    TRegion getParentById(Long id);

    TRegion getRegionById(Long id);
    // ##remain#method#

    // 根据属性找到唯一的方法

}