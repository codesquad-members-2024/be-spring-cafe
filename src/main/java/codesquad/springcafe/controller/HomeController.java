package codesquad.springcafe.controller;

import codesquad.springcafe.DB.H2Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final H2Database h2Database;

    @Autowired
    public HomeController(H2Database h2Database) {
        this.h2Database = h2Database;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("articles", h2Database.getAllArticles());
        return "index";
    }
}