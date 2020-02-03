package by.siarhei.beerfest.command.impl;

import static by.siarhei.beerfest.command.Page.Router.*;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.command.Page;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.servlet.SessionRequestContent;

public class LogoutCommand implements ActionCommand {

    private static final String JSP_MAIN = "path.page.index";

    @Override
    public Page execute(SessionRequestContent content){
        String uri = ConfigurationManager.getProperty(JSP_MAIN);
        content.invalidateSession();
        return new Page(uri, REDIRECT);
    }
}
