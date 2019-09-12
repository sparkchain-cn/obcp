package cn.obcp.user.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.obcp.base.RetData;
import cn.obcp.user.service.UserExtendService;

@Aspect
@Component
public class CheckIsAdmin {

    CheckIsAdmin(){
        System.out.println("CheckIsAdmin init ing +++++++++++++++++");
    }

    @Pointcut(value = "@annotation(cn.obcp.user.annotation.CheckAdmin)")
    public void annotationPointCut() {
    }

    @Autowired
    UserExtendService userExtendService;

    @Before("annotationPointCut()")
    public Object validAdmin(){
        if (!userExtendService.currIsAdmin()){
            return RetData.succuess();
        }else {
            return new Exception("非管理员角色不能访问");
        }
    }

}
