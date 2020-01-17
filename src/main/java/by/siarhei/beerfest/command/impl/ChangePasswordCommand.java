package by.siarhei.beerfest.command.impl;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.entity.RoleType;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.manager.MessageManager;
import by.siarhei.beerfest.service.impl.AccountService;
import by.siarhei.beerfest.servlet.SessionRequestContent;

public class ChangePasswordCommand implements ActionCommand {
    private static final String JSP_MAIN = "path.page.main";
    private static final String PARAMETER_NEW_PASSWORD = "newPassword";
    private static final String ATTRIBUTE_USER_ROLE = "userRole";
    private static final String ATTRIBUTE_USER_LOGIN = "userLogin";
    private static final String ATTRIBUTE_USER_EMAIL = "userEmail";
    private static final String ATTRIBUTE_MESSAGE = "errorMessage";
    private static final String SUCCESS_MESSAGE = "ru.message.change.password.success";
    private static final String ERROR_MESSAGE = "ru.message.change.password.error";

    @Override
    public String execute(SessionRequestContent content){
        String page = ConfigurationManager.getProperty(JSP_MAIN);
        AccountService service = new AccountService();
        if (content.getSessionAttribute(ATTRIBUTE_USER_ROLE) != RoleType.UNAUTHORIZED) {
            String newPassword = content.getParameter(PARAMETER_NEW_PASSWORD);
            String login = content.getSessionAttribute(ATTRIBUTE_USER_LOGIN).toString();
            String eMail = content.getSessionAttribute(ATTRIBUTE_USER_EMAIL).toString();
            RoleType roleType = (RoleType) content.getSessionAttribute(ATTRIBUTE_USER_ROLE);
            page = ConfigurationManager.getProperty(roleType.getPage());
            if (service.changeUserPassword(login, eMail, newPassword)) {
                content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(SUCCESS_MESSAGE));
            } else {
                content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(ERROR_MESSAGE));
            }
        }
        return page;
    }
}
