package Interceptors;

import Helpers.UserHelper;
import Models.UsersEntity;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        Boolean is_in_guest_page = (httpServletRequest.getRequestURI().indexOf("/login") == 0 || //放行登录
                                    httpServletRequest.getRequestURI().indexOf("/register") == 0 || //放行注册
                                    httpServletRequest.getRequestURI().indexOf("/assets") == 0 || //放行资源
                                    httpServletRequest.getRequestURI().indexOf("/error") == 0 //放行错误页面
                            );
        Boolean is_in_asset_page = (httpServletRequest.getRequestURI().indexOf("/assets") == 0 || //放行资源
                                    httpServletRequest.getRequestURI().indexOf("/error") == 0 //放行错误页面
                            );
        //从 Session  里获取当前登录的用户
        UsersEntity user = (UsersEntity) httpServletRequest.getSession().getAttribute("user");
        if(user == null && !is_in_guest_page && !is_in_asset_page) {
            //未登录
            httpServletResponse.sendRedirect("/login");
            return false;
        }

        //已登录的还访问登录界面就是搞事儿了
        if(user != null && is_in_guest_page && !is_in_asset_page) {
            //未登录
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
