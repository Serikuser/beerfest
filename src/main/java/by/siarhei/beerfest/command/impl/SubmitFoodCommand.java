package by.siarhei.beerfest.command.impl;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.entity.RoleType;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.manager.MessageManager;
import by.siarhei.beerfest.service.impl.BarServiceImpl;
import by.siarhei.beerfest.servlet.SessionRequestContent;

public class SubmitFoodCommand implements ActionCommand {
    private static final String JSP_MAIN = "path.page.main";
    private static final String ATTRIBUTE_USER_ROLE = "userRole";
    private static final String ATTRIBUTE_MESSAGE = "foodSubmitMessage";
    private static final String PARAMETER_FOOD_NAME = "foodName";
    private static final String SUBMIT_FOOD_SUCCESS = "ru.message.submit.food.success";
    private static final String SUBMIT_FOOD_ERROR = "ru.message.submit.food.error";
    private static final String SIGNUP_ERROR_JOKE = "ru.message.signup.error.joke";

    @Override
    public String execute(SessionRequestContent content) {
        String page = ConfigurationManager.getProperty(JSP_MAIN);
        BarServiceImpl service = new BarServiceImpl();
        if (content.getSessionAttribute(ATTRIBUTE_USER_ROLE) == RoleType.ADMIN) {
            RoleType roleType = (RoleType) content.getSessionAttribute(ATTRIBUTE_USER_ROLE);
            page = ConfigurationManager.getProperty(roleType.getPage());
            String foodName = content.getParameter(PARAMETER_FOOD_NAME);
            if (service.submitFood(foodName)) {
                content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(SUBMIT_FOOD_SUCCESS));
            } else {
                content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(SUBMIT_FOOD_ERROR));
            }
        } else {
            content.setAttribute(ATTRIBUTE_MESSAGE, MessageManager.getProperty(SIGNUP_ERROR_JOKE));
        }
        return page;
    }
}
