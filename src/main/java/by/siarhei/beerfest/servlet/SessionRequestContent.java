package by.siarhei.beerfest.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class SessionRequestContent {
    private static final int FIRST_PARAMETER_INDEX = 0;
    private Map<String, Object> requestAttributes;
    private Map<String, String[]> requestParameters;
    private Map<String, Object> sessionAttributes;
    private boolean invalidate = false;

    public SessionRequestContent(HttpServletRequest request) {
        requestAttributes = new HashMap<>();
        requestParameters = new HashMap<>();
        sessionAttributes = new HashMap<>();
        extractValues(request);
    }


    private void extractValues(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Iterator<String> sessionAttributesIterator = session.getAttributeNames().asIterator();
        while (sessionAttributesIterator.hasNext()) {
            String name = sessionAttributesIterator.next();
            sessionAttributes.put(name, session.getAttribute(name));
        }

        Iterator<String> requestAttributesIterator = request.getAttributeNames().asIterator();
        while (requestAttributesIterator.hasNext()) {
            String name = requestAttributesIterator.next();
            requestAttributes.put(name, request.getAttribute(name));
        }

        requestParameters = request.getParameterMap();
    }

    public Object getSessionAttribute(String name) {
        return sessionAttributes.get(name);
    }

    public void setAttribute(String name, Object value) {
        requestAttributes.put(name, value);
    }

    public String getParameter(String name) {
        String[] strings = requestParameters.get(name);
        if (strings != null) {
            return strings[FIRST_PARAMETER_INDEX];
        } else {
            return null;
        }
    }

    public String[] getParametersValue(String name) {
        return requestParameters.get(name);
    }

    public void setSessionAttribute(String name, Object value) {
        sessionAttributes.put(name, value);
    }

    public Object getAttribute(String attribute) {
        return requestAttributes.get(attribute);
    }

    public void insert(HttpServletRequest request) {
        requestAttributes.forEach(request::setAttribute);
        HttpSession session = request.getSession();
        sessionAttributes.forEach(session::setAttribute);
        if (invalidate) {
            request.getSession().invalidate();
        }
    }

    public void invalidateSession() {
        this.invalidate = true;
    }
}
