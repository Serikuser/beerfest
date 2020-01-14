package by.siarhei.beerfest.servlet;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.connection.ConnectionPool;
import by.siarhei.beerfest.factory.ArticleFactory;
import by.siarhei.beerfest.factory.UserFactory;
import by.siarhei.beerfest.service.CommandProvider;
import by.siarhei.beerfest.session.SessionRequestContent;

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

    @Override
    public void init() {
        ArticleFactory.getInstance();
        UserFactory.getInstance();
        ConnectionPool.INSTANCE.init();
    }

    @Override
    public void destroy() {
        ConnectionPool.INSTANCE.destroyPool();
    }

    private void processRequest(HttpServletRequest request
            , HttpServletResponse response) throws ServletException, IOException {
        String page;
        ActionCommand command = CommandProvider.defineCommand(request);
        SessionRequestContent content = new SessionRequestContent(request);
        page = command.execute(content);
        content.insert(request);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}
