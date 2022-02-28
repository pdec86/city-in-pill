package pl.pdec.city.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class HomeController {

    @RequestMapping(value = {"/ag", "/ag/", "/ag/{ignored:(?:(?!index\\.html|.*js|.*css|.*ico).*)}/**"}, produces = "text/html")
    public Object angularApp(@PathVariable Optional<String> ignored) {
        //String restOfTheUrl = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        return "forward:/ag/index.html";
    }
}
