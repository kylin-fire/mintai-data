package com.mintai.data.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 文件描述：首页控制器
 *
 * @author leiteng
 *         Date 2015/9/23.
 */
@Controller
public class IndexController {
    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }
}
