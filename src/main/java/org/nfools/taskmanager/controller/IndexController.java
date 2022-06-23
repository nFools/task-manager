package org.nfools.taskmanager.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/")
@Controller
public class IndexController {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${server.servlet.ui-path}")
    private String uiPath;

    @RequestMapping("/*")
    public void any(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.sendRedirect(contextPath + uiPath + "/");
    }
}
