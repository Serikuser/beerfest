package by.siarhei.beerfest.service;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.command.CommandType;


import javax.servlet.http.HttpServletRequest;

public class CommandProvider {

    private static final String PARAMETER_COMMAND = "command";

    private CommandProvider() {
    }
    public static ActionCommand defineCommand(HttpServletRequest request) {
        String action = request.getParameter(PARAMETER_COMMAND);
        CommandType currentType = CommandType.valueOf(action.toUpperCase());
        return currentType.getCurrentCommand();
    }
}
