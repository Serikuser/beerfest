package by.siarhei.beerfest.command.impl;


import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.entity.RoleType;
import by.siarhei.beerfest.entity.StatusType;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.manager.MessageManager;
import by.siarhei.beerfest.service.impl.AccountService;
import by.siarhei.beerfest.servlet.SessionRequestContent;

import javax.servlet.ServletException;
import java.io.IOException;

public class SignupCommand implements ActionCommand {

    public static final String PARAMETER_USERNAME = "username";
    public static final String PARAMETER_EMAIL = "email";
    public static final String PARAMETER_PASSWORD = "password";
    public static final String PARAMETER_ROLE = "role";
    private static final String JSP_MAIN = "path.page.main";
    private static final String ATTRIBUTE_MESSAGE = "errorMessage";
    private static final String SIGNUP_SUCCESS = "ru.message.signup.success";
    private static final String SIGNUP_ERROR = "ru.message.signup.error";
    private static final String SIGNUP_ERROR_JOKE = "ru.message.signup.error.joke";

    @Override
    public String execute(SessionRequestContent content){
        String page = ConfigurationManager.getProperty(JSP_MAIN);
        AccountService service = new AccountService();
        if (isEnterDataExist(content)) {
            String name = content.getParameter(PARAMETER_USERNAME);
            String eMail = content.getParameter(PARAMETER_EMAIL);
            String password = content.getParameter(PARAMETER_PASSWORD);
            RoleType role = RoleType.valueOf(content.getParameter(PARAMETER_ROLE).toUpperCase());
            if (service.signupUser(name, eMail, password, role, StatusType.ACTIVE)) {
                content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(SIGNUP_SUCCESS));
            } else {
                content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(SIGNUP_ERROR));
            }

        } else {
            content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(SIGNUP_ERROR_JOKE));
        }
        return page;
    }

    private boolean isEnterDataExist(SessionRequestContent content) {
        return content.getParameter(PARAMETER_USERNAME) != null
                && content.getParameter(PARAMETER_EMAIL) != null
                && content.getParameter(PARAMETER_PASSWORD) != null
                && content.getParameter(PARAMETER_ROLE) != null;
    }
}

