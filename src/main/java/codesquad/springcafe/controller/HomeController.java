package codesquad.springcafe.controller;

import codesquad.springcafe.DB.Database;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("articles", Database.getAllArticles());
        return "index";
    }
}