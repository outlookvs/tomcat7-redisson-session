package com.mahendra.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet({"/index.html"})
public class SessionTestServlet extends HttpServlet {
    public SessionTestServlet() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html>");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\" />");
        out.println("<title>HTTP Session test</title>");
        out.println("<link type='text/css' rel='stylesheet' href='main.css' />");
        out.println("</head><body>");
        HttpSession session = request.getSession(true);
        Date created = new Date(session.getCreationTime());
        Date accessed = new Date(session.getLastAccessedTime());
        this.newLine("<h3>Server Instance: " + request.getLocalName() + " IP: " + request.getLocalAddr() + "</h3>", out);
        this.newLine("ID " + session.getId(), out);
        this.newLine("Created: " + created, out);
        this.newLine("Last Accessed: " + accessed, out);
        out.println("<hr/>");
        out.println("<h4>Try adding few attributes</h4>");
        out.println("<hr/>");
        out.println("<form method=get>");
        this.newLine("Enter <b>Name</b> for attribute: <input name='dataName' />", out);
        this.newLine("Enter <b>Value</b> for attribute: <input name='dataValue' />", out);
        this.newLine("<input type=submit value='Submit'/>", out);
        out.println("</form>");
        out.println("<hr/>");
        String dataName = request.getParameter("dataName");
        if (dataName != null && dataName.length() > 0) {
            String dataValue = request.getParameter("dataValue");
            session.setAttribute(dataName, dataValue);
        }

        Enumeration<String> e = session.getAttributeNames();

        while(e.hasMoreElements()) {
            String name = (String)e.nextElement();
            String value = session.getAttribute(name).toString();
            this.newLine(name + " = " + value, out);
        }

        out.println("<br/><br/>This application is provided for testing purpose only, please delete it before deploying your application.");
        out.println("</body></html>");
    }

    void newLine(String message, PrintWriter out) {
        out.println(message + "<br/>");
    }
}
