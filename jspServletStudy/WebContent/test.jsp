<%@ page contentType="text/html" %>
<%@ page import="org.apache.logging.log4j.*" %>
<%@ page import="org.apache.logging.log4j.web.*" %>
<%!
    private final Logger logger = LogManager.getRootLogger();
%>
<%
    logger.trace("Entering application.");
    logger.info("Input Message : {}", "Hello Log4j2");
    logger.trace("Exiting application.");
%>

