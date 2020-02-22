package by.siarhei.beerfest.command;

import javax.servlet.http.HttpServletRequest;

/**
 * Utility class for define command.
 */

public class CommandProvider {

    private static final String PARAMETER_COMMAND = "command";

    private CommandProvider() {
    }

    /**
     * Defines a command by its string representation that gets from request.
     * But there must be a corresponding element in enum {@code CommandType}
     *
     * @param request object that contain request.
     * @return {@code ActionCommand} object
     */
    public static ActionCommand defineCommand(HttpServletRequest request) {
        String action = request.getParameter(PARAMETER_COMMAND);
        CommandType currentType = CommandType.valueOf(action.toUpperCase());
        return currentType.getCurrentCommand();
    }
}
