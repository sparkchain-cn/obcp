package cn.obcp.base.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.obcp.base.UuidCreator;
import cn.obcp.base.service.BaseService;

public abstract class BaseController<T, PK> {
	public final Logger logger = LoggerFactory.getLogger(getClass());

	protected BaseService<T, PK> entityService;

	@Autowired
	protected UuidCreator uuidCreator;

	public abstract void setEntityService(BaseService<T, PK> entityService);

}
