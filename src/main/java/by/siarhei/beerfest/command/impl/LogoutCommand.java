package by.siarhei.beerfest.command.impl;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.command.Router;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.servlet.SessionRequestContent;

public class LogoutCommand implements ActionCommand {

    private static final String JSP_INDEX = "path.page.index";

    @Override
    public Router execute(SessionRequestContent content){
        String uri = ConfigurationManager.getProperty(JSP_INDEX);
        content.invalidateSession();
        Router router = new Router(uri);
        router.setRedirect();
        return router;
    }
}
