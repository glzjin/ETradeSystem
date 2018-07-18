package Controllers.Seller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SellerPageController {
    @RequestMapping("/seller")
    public String index(Model model) {
        return "seller/index";
    }

    @RequestMapping("/seller/pending")
    public String pending(Model model) {
        return "seller/pending";
    }
}
