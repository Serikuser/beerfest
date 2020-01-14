package by.siarhei.beerfest.command;

import by.siarhei.beerfest.entity.RoleType;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.session.SessionRequestContent;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface ActionCommand {
    String execute(SessionRequestContent content) throws IOException, ServletException;

}
