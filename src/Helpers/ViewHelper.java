package Helpers;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.SmartView;
import org.springframework.web.servlet.View;

public class ViewHelper {
    /**
     * 判断是否为跳转路径
     * @param mv
     * @return
     */
    public static boolean isRedirectView(ModelAndView mv) {
        if(mv != null) {
            String viewName = mv.getViewName();
            if (viewName.startsWith("redirect:/")) {
                return true;
            }
            View view = mv.getView();
            return (view != null && view instanceof SmartView && ((SmartView) view).isRedirectView());
        }

        return false;
    }
}
