<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${applicationScope['lang']}" scope="session"/>
<fmt:setBundle basename="messages"/>
<html lang="en">
    <head>
        <link rel="stylesheet" href="documentCheck.css">
        <script src="documentsCheck.js" charset="utf-8"></script>
        <meta charset="utf-8">
        <title></title>
    </head>
    <body>
        <div class="menu-bar">
            <div class="check">
                <a href="needToAccept">
                    <div class="option">
                        <fmt:message key="label.acceptTickets"/>
                    </div>
                </a>
            </div>

            <div class="check">
                <a href="sales">
                    <div class="option">
                        <fmt:message key="label.sales"/>
                    </div>
                </a>
            </div>
            <div class="check">
                <a href="tickets">
                    <div class="option">
                        <fmt:message key="label.voyages"/>
                    </div>
                </a>
            </div>
            <div class="check">
                <a href="newCruiseGeneral">
                    <div class="option">
                        <fmt:message key="label.newVoyage"/>
                    </div>
                </a>
            </div>
            <div class="check">
                <a href="../profile">
                    <div class="option">
                        <fmt:message key="label.profile"/>
                    </div>
                </a>
            </div>
        </div>
        <img src="../Hand-drawn-cruise-ship-on-transparent-background-PNG.png" alt="ship" width="1900" height="1000"
             class="ship"/>
        <div class="new-cruise-bar">
            <c:forEach var="image" items="${documents}" varStatus="status">
                <c:if test="${status.count!=1}">
                    <img class="mySlides" src="../Documents/${image.getParentFile().getName()}/${image.getName()}"
                         style="display:none;">
                </c:if>
                <c:if test="${status.count==1}">
                    <img class="mySlides" src="../Documents/${image.getParentFile().getName()}/${image.getName()}">
                </c:if>
            </c:forEach>
        </div>
        <div class="left-bar">

            <button class="w3-button w3-display-left" onclick="plusDivs(-1)">&#10094;</button>
        </div>
        <div class="right-bar">

            <button class="w3-button w3-display-right" onclick="plusDivs(+1)">&#10095;</button>
        </div>
        <div class="start-bar">
            <div class="check">
                <a href="accept?id=${param.id}">
                    <div class="option">
                        <fmt:message key="label.accept"/>
                    </div>
                </a>
            </div>
            <div class="check">
                <a href="decline?id=${param.id}">
                    <div class="option">
                        <fmt:message key="label.decline"/>
                    </div>
                </a>
            </div>
        </div>
        <div class="button-bar">
            <div class="check">
                <a href="changeLang?lang=en&page=${requestScope['javax.servlet.forward.request_uri']}">
                    <div class="option">
                        en
                    </div>
                </a>
            </div>
            <div class="check">
                <a href="changeLang?lang=uk&page=${requestScope['javax.servlet.forward.request_uri']}">
                    <div class="option">
                        uk
                    </div>
                </a>
            </div>
            <div class="check">
                <a href="changeLang?lang=ru&page=${requestScope['javax.servlet.forward.request_uri']}">
                    <div class="option">
                        ru
                    </div>
                </a>
            </div>
        </div>

    </body>
</html>
