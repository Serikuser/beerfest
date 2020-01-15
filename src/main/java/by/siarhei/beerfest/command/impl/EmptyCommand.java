package by.siarhei.beerfest.command.impl;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.session.SessionRequestContent;

import javax.servlet.ServletException;
import java.io.IOException;

public class EmptyCommand implements ActionCommand {
    private static final String JSP_MAIN = "path.page.main";

    @Override
    public String execute(SessionRequestContent content) throws IOException, ServletException {
        return ConfigurationManager.getProperty(JSP_MAIN);
    }
}
