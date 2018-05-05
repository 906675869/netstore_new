package com.parent.xtgl.config.filter;


import org.jboss.logging.Logger;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 过滤器
 */
@WebFilter(filterName="timeFilter",urlPatterns= {"/*"})
@Order(FilterRegistrationBean.LOWEST_PRECEDENCE)
@Component
public class WebTimeFilter implements Filter {
    static Logger logger = Logger.getLogger(WebTimeFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        long start = System.currentTimeMillis();
        chain.doFilter(request, response);
//        String url = request.getRemoteAddr();
//        logger.error(url+" 耗时：" + (System.currentTimeMillis() - start));
//        log.warn("filter 耗时：" + (System.currentTimeMillis() - start));
//        System.out.println("filter 耗时：" + (System.currentTimeMillis() - start));
    }

    @Override
    public void destroy() {

    }
}
