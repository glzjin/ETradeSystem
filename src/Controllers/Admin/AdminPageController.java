package Controllers.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminPageController {
    @RequestMapping("/admin")
    public String index(Model model) {
        return "admin/index";
    }
}
