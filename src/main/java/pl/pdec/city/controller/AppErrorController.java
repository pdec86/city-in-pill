package pl.pdec.city.controller;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class AppErrorController implements ErrorController {

    /**
     * Error Attributes in the Application
     */
    private final ErrorAttributes errorAttributes;

    private final static String ERROR_PATH = "/error";

    /**
     * Controller for the Error Controller
     */
    public AppErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    /**
     * Supports the HTML Error View
     */
    @RequestMapping(value = ERROR_PATH, produces = "text/html")
    public String errorHtml(HttpServletRequest request, WebRequest webRequest, Model model) {
        getErrorAttributes(request, webRequest).forEach(model::addAttribute);

        return "errors/error";
    }

    /**
     * Supports other formats like JSON, XML
     */
    @RequestMapping(value = ERROR_PATH)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request, WebRequest webRequest) {
        Map<String, Object> body = getErrorAttributes(request, webRequest);
        HttpStatus status = getStatus(request);

        return new ResponseEntity<>(body, status);
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request, WebRequest webRequest) {
        Map<String, Object> map = this.errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults().including(ErrorAttributeOptions.Include.STACK_TRACE));
        map.put("err_status", request.getAttribute("javax.servlet.error.status_code"));
        map.put("err_reason", request.getAttribute("javax.servlet.error.message"));

        return map;
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
        if (statusCode != null) {
            try {
                return HttpStatus.valueOf(statusCode);
            } catch (Exception ex) {
            }
        }

        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
