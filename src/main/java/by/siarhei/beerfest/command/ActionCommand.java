package by.siarhei.beerfest.command;

import by.siarhei.beerfest.servlet.SessionRequestContent;
@FunctionalInterface
public interface ActionCommand {
    Router execute(SessionRequestContent content);

}
