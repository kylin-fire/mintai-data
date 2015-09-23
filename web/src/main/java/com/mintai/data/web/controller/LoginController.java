package com.mintai.data.web.controller;

import com.mintai.data.biz.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 文件描述：Hello控制器
 *
 * @author leiteng
 *         Date 2015/9/22.
 */
@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String loginGet(@RequestParam(name = "callback") String callback, Model model) {
        return "login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String loginPost(@RequestParam(name = "userName", required = true) String userName, @RequestParam(name = "password", required = true) String password, @RequestParam(name = "callback") String callback, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {

        boolean login = loginService.login(userName, password);

        if (login) {

            request.getSession().setAttribute("login", Boolean.TRUE);

            if (StringUtils.isEmpty(callback) || "/".equals(callback)) {
                callback = "/index";
            }

            response.sendRedirect(callback);

            return callback;
        }

        model.addAttribute("message", "用户密码不匹配");

        return "login";
    }
}
