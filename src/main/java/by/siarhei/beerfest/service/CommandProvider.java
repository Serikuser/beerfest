package by.siarhei.beerfest.service;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.command.CommandType;
import by.siarhei.beerfest.command.impl.EmptyCommand;
import by.siarhei.beerfest.manager.MessageManager;

import javax.servlet.http.HttpServletRequest;

public class CommandProvider {

    private static final String PARAMETER_COMMAND = "command";
    private static final String ATTRIBUTE_ERROR_MESSAGE = "errorMessage";
    private static final String ERROR_MESSAGE = "ru.message.signup.error.joke";

    private CommandProvider() {
    }

    public static ActionCommand defineCommand(HttpServletRequest request) {
        String action = request.getParameter(PARAMETER_COMMAND);
        ActionCommand current = new EmptyCommand();
        // FIXME: 15.01.2020 remove it to filter chain
        if (action == null || action.isEmpty()) {
            return current;
        }
        try {
            CommandType currentType = CommandType.valueOf(action.toUpperCase());
            current = currentType.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            request.setAttribute(ATTRIBUTE_ERROR_MESSAGE, MessageManager.getProperty(ERROR_MESSAGE));
        }
        return current;
    }
}
