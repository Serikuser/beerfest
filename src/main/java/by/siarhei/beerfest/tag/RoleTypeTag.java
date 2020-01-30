package by.siarhei.beerfest.tag;

import by.siarhei.beerfest.entity.RoleType;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

@SuppressWarnings("serial")
public class RoleTypeTag extends TagSupport {
    private static final String COLOR_GREEN = "#35D031";
    private static final String COLOR_RED = "#D01C17";
    private static final String COLOR_BLACK = "#130502";
    private static final String COLOR_ORANGE = "#d07503c2";
    private RoleType role;

    public void setRole(RoleType role) {
        this.role = role;
    }

    @Override
    public int doStartTag() throws JspException {
        if (role != null) {
            try {
                String color;
                switch (role) {
                    case PARTICIPANT:
                        color = COLOR_GREEN;
                        break;
                    case GUEST:
                        color = COLOR_ORANGE;
                        break;
                    case ADMIN:
                        color = COLOR_RED;
                        break;
                    default:
                        color = COLOR_BLACK;
                        break;
                }
                pageContext.getOut().write("<div style=\"color: " + color + "\">" + role + "</div>");
            } catch (IOException e) {
                throw new JspException(e);
            }
        }
        return SKIP_BODY;
    }
}
