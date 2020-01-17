package by.siarhei.beerfest.command;

import by.siarhei.beerfest.servlet.SessionRequestContent;

public interface ActionCommand {
    String execute(SessionRequestContent content);

}
