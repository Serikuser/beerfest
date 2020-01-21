package by.siarhei.beerfest.filter;

import by.siarhei.beerfest.command.CommandType;
import by.siarhei.beerfest.command.LocaleType;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.manager.MessageManager;
import by.siarhei.beerfest.service.LanguageService;
import by.siarhei.beerfest.service.impl.LanguageServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Stream;

@WebFilter(urlPatterns = {"/controller"}, servletNames = {"Controller"})
public class CommandFilter implements Filter {
    private static final String JSP_MAIN = "path.page.main";
    private static final String PARAMETER_COMMAND = "command";
    private static final String ATTRIBUTE_ERROR_MESSAGE = "errorMessage";
    private static final String ERROR_MESSAGE = "message.signup.error.joke";
    private LanguageService languageService = new LanguageServiceImpl();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        LocaleType localeType = languageService.defineLocale(request);
        String action = request.getParameter(PARAMETER_COMMAND);
        if (action == null || action.isBlank()) {
            String page = ConfigurationManager.getProperty(JSP_MAIN);
            request.setAttribute(ATTRIBUTE_ERROR_MESSAGE, MessageManager.getProperty(ERROR_MESSAGE, localeType));
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
            return;
        }
        if (Stream.of(CommandType.values()).noneMatch(commandType -> commandType.name().equalsIgnoreCase(action))) {
            String page = ConfigurationManager.getProperty(JSP_MAIN);
            request.setAttribute(ATTRIBUTE_ERROR_MESSAGE, MessageManager.getProperty(ERROR_MESSAGE, localeType));
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
