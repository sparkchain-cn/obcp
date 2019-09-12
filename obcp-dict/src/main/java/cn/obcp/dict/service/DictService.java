package cn.obcp.dict.service;
// ##remain#import#

import java.util.List;

import cn.obcp.base.service.BaseService;
import cn.obcp.dict.domain.TDict;

/**
 * <pre>
 * 字典的业务接口
 * 该代码通过mgicode代码生成器生成
 * </pre>
 * 
 */

public interface DictService extends BaseService<TDict, String> {

    Integer getCount();

    String saveDict(TDict tDict);

    String updateDictStatus(String code);

    String deleteDict(String code);

    String getDictName(String code);

    TDict getParentById(String code);

    TDict getDictById(String code);
    
    List<TDict> getDictAll();
    
	// ##remain#method#

	// 根据属性找到唯一的方法

}