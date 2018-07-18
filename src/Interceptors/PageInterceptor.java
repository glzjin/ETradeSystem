package Interceptors;

import Helpers.ViewHelper;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 参数转换
 * page to currentPage
 */

public class PageInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if(!ViewHelper.isRedirectView(modelAndView) && modelAndView != null) {
            String page = httpServletRequest.getParameter("page");

            if(page != null) {
                modelAndView.getModelMap().put("currentPage", Integer.valueOf(page));
            } else {
                modelAndView.getModelMap().put("currentPage", 1);
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
