package by.siarhei.beerfest.servlet;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.command.CommandProvider;
import by.siarhei.beerfest.command.Router;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
@MultipartConfig()

public class Controller extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request
            , HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request
            , HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request
            , HttpServletResponse response) throws ServletException, IOException {
        ActionCommand command = CommandProvider.defineCommand(request);
        SessionRequestContent content = new SessionRequestContent(request);
        Router page = command.execute(content);
        String uri = page.getUri();
        content.insert(request);
        Router.TransitionType transitionType = page.getTransitionType();
        switch (transitionType) {
            case FORWARD:
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(uri);
                dispatcher.forward(request, response);
                break;
            case REDIRECT:
                response.sendRedirect(request.getContextPath() + uri);
                break;
        }
    }
}
