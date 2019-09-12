/**
 * TODO
 * 
 * 
 * lmf 创建于2018年11月29日
 */
package cn.obcp.user.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @author lmf
 * @param <EmbeddedServletContainerCustomizer>
 *
 */
@Component
public class ErrorPageConfig implements ErrorPageRegistrar {

	
	/* (non-Javadoc)
	 * @see org.springframework.boot.web.server.ErrorPageRegistry#addErrorPages(org.springframework.boot.web.server.ErrorPage[])
	 */
	@Override
	public void registerErrorPages(ErrorPageRegistry registry) {
		// TODO Auto-generated method stub
		ErrorPage[] errorPage = new ErrorPage[2];
		errorPage[0] = new ErrorPage(HttpStatus.NOT_FOUND,"/404");
		errorPage[1] = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,"500");
		registry.addErrorPages(errorPage);
	}
}
