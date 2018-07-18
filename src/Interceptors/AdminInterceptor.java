package Interceptors;

import Models.UsersEntity;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //从 Session  里获取当前登录的用户
        UsersEntity user = (UsersEntity) httpServletRequest.getSession().getAttribute("user");

        //判断当前用户的角色是否为非管理员
        if(user.getUserRoleType() != UsersEntity.USER_ROLE_TYPE_ADMIN) {
            //让他返回到正确的路径
            httpServletResponse.sendRedirect("/");
            return false;
        }

        //已登录
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
