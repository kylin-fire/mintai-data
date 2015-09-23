package com.mintai.data.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ÎÄ¼þÃèÊö£ºHello¿ØÖÆÆ÷
 *
 * @author leiteng
 *         Date 2015/9/22.
 */
@Controller
public class HelloController {
    @RequestMapping("/hello")
    public String hello(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "hello";
  }
}
