package codesquad.springcafe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {
    private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @GetMapping(value = {"/not-found"})
    public String showErrorPage() {
        return "error/not_found";
    }
}
