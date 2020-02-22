package by.siarhei.beerfest.command;

import by.siarhei.beerfest.servlet.SessionRequestContent;

/**
 * Functional interface realisation of Command pattern.
 * Every command must has a corresponding element in enum {@code CommandType}
 */
@FunctionalInterface
public interface ActionCommand {
    /**
     * Sets a common interface for specific command classes.
     *
     * @param content object that contain request, response and session information.
     * @return {@code Router} which contains path to resources and router type.
     */
    Router execute(SessionRequestContent content);
}
