package org.xpdojo.file.servlet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ServletMultipartController {

    private final ServletMultipartService servletMultipartService;

    @GetMapping("/servlet")
    public String servlet() {
        return "servlet";
    }

    @PostMapping(path = "/servlet", consumes = "multipart/form-data")
    public String servlet(
            HttpServletRequest servletRequest
    ) throws IOException, ServletException {

        log.info("### servletRequest ###");
        log.info("{}", servletRequest.getContentType());
        // org.apache.catalina.core.ApplicationPart
        Collection<Part> parts = servletRequest.getParts();
        servletMultipartService.savePart(parts);
        return "redirect:/";
    }

}
