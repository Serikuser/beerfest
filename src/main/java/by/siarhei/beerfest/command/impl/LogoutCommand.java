package by.siarhei.beerfest.command.impl;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.servlet.SessionRequestContent;

import javax.servlet.ServletException;
import java.io.IOException;

public class LogoutCommand implements ActionCommand {

    private static final String JSP_MAIN = "path.page.index";

    @Override
    public String execute(SessionRequestContent content){
        String page = ConfigurationManager.getProperty(JSP_MAIN);
        content.invalidateSession();
        return page;
    }
}
