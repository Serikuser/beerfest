package by.siarhei.beerfest.command;

import by.siarhei.beerfest.servlet.SessionRequestContent;
@FunctionalInterface
public interface ActionCommand {
    Page execute(SessionRequestContent content);

}
