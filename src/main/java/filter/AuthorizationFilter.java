package filter;

import beans.LoginBean;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"/secured/*"})
public class AuthorizationFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationFilter.class);

    public static final String LOGIN_PAGE = "/login.xhtml";

    public AuthorizationFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.debug("loginFilter initialized");

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        try {

            HttpServletRequest reqt = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            HttpSession ses = reqt.getSession(false);
            LoginBean loginbean = (LoginBean) reqt.getSession().getAttribute("loginBean");
            

            if (loginbean != null) {
                if (loginbean.isLoggedIn()) {
                    LOGGER.debug("user is logged in");
                    // user is logged in, continue request
                    chain.doFilter(reqt, resp);
                } else {
                    LOGGER.debug("user is not logged in");
                    // user is not logged in, redirect to login page
                    resp.sendRedirect(reqt.getContextPath() + LOGIN_PAGE);
                }
            } else {
                LOGGER.debug("userManager not found");
                // user is not logged in, redirect to login page
                resp.sendRedirect(reqt.getContextPath() + LOGIN_PAGE);
            }
        } catch (IOException e) {
            LOGGER.error("error en AuthorizationFilter " + e.getMessage());
        }
    }

    @Override
    public void destroy() {

    }
}
