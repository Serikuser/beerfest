package by.siarhei.beerfest.filter;

import by.siarhei.beerfest.entity.RoleType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/controller"}, servletNames = {"Controller"})
public class ServletSecurityFilter implements Filter {
    private static final String ATTRIBUTE_USER_ROLE = "userRole";
    private static final String URL_CONTROLLER = "/controller";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        RoleType roleType = (RoleType) session.getAttribute(ATTRIBUTE_USER_ROLE);
        if (roleType == null) {
            roleType = RoleType.UNAUTHORIZED;
            session.setAttribute(ATTRIBUTE_USER_ROLE, roleType);
            RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher(URL_CONTROLLER);
            dispatcher.forward(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
