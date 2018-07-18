package Controllers.Buyer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BuyerPageController {
    @RequestMapping("/buyer")
    public String index(Model model) {
        return "buyer/index";
    }
}
