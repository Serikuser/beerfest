package by.siarhei.beerfest.command;

import by.siarhei.beerfest.servlet.SessionRequestContent;

import javax.servlet.ServletException;
import java.io.IOException;

public interface ActionCommand {
    String execute(SessionRequestContent content);

}
