package by.siarhei.beerfest.command.impl;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.session.SessionRequestContent;

import javax.servlet.ServletException;
import java.io.IOException;

public class LogoutCommand implements ActionCommand {

    private static final String JSP_MAIN = "path.page.index";

    @Override
    public String execute(SessionRequestContent content) throws IOException, ServletException {
        String page = ConfigurationManager.getProperty(JSP_MAIN);
        content.invalidateSession();
        return page;
    }
}
