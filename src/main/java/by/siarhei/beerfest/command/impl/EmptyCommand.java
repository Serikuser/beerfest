package by.siarhei.beerfest.command.impl;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.servlet.SessionRequestContent;

import javax.servlet.ServletException;
import java.io.IOException;

public class EmptyCommand implements ActionCommand {
    private static final String JSP_MAIN = "path.page.main";

    @Override
    public String execute(SessionRequestContent content) {
        return ConfigurationManager.getProperty(JSP_MAIN);
    }
}
