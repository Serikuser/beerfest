package by.siarhei.beerfest.command.impl;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.command.Router;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.servlet.SessionRequestContent;

/**
 * Realization of {@code ActionCommand} interface.
 * Command is processing logout logic.
 *
 */
public class LogoutCommand implements ActionCommand {

    private static final String JSP_INDEX = "path.page.index";

    /**
     * Call method defines logout logic by setting invalidate session flag on true in {@code SessionRequestContent}
     * redirecting to {@code index.jsp}
     *
     * @param content object that contain request, response and session information.
     * @return {@code Router} with redirecting routing type.
     */
    @Override
    public Router execute(SessionRequestContent content){
        String uri = ConfigurationManager.getProperty(JSP_INDEX);
        content.invalidateSession();
        Router router = new Router(uri);
        router.setRedirect();
        return router;
    }
}
