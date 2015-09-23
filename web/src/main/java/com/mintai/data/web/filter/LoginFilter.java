package com.mintai.data.web.filter;

import com.google.common.collect.Sets;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

/**
 * 文件描述：登录拦截器
 *
 * @author leiteng
 *         Date 2015/9/23.
 */
public class LoginFilter implements Filter {
    /** 排除的url集合 */
    private Set<String> excludes = Collections.emptySet();

    public void init(FilterConfig filterConfig) throws ServletException {
        String excludeUrls = filterConfig.getInitParameter("excludes");

        if (StringUtils.isEmpty(excludeUrls)) {
            return;
        }

        excludes = Sets.newHashSet(excludeUrls.split(","));
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        boolean needLogin = needLogin(request);

        if (needLogin) {
            response.sendRedirect("/login?callback=" + request.getRequestURI());
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean needLogin(HttpServletRequest request) {
        String uri = request.getRequestURI();
        // 无须登录uri
        if (!CollectionUtils.isEmpty(excludes)) {
            for (String each : excludes) {
                if (uri.matches(each)) {
                    return false;
                }
            }
        }

        HttpSession session = request.getSession();
        // 已登录
        if (Boolean.TRUE.equals(session.getAttribute("login"))) {
            return false;
        }

        return true;
    }

    public void destroy() {

    }
}
