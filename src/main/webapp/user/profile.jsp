<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${applicationScope['lang']}" scope="session"/>
<fmt:setBundle basename="messages"/>
<html lang="en">
    <head>
        <link rel="stylesheet" href="profile.css">
        <meta charset="utf-8">
        <title></title>
    </head>
    <body>
        <div class="menu-bar">
            <div class="check">
                <a href="../index">
                    <div class="option">
                        <fmt:message key="label.home"/>
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
                <a href="../tickets">
                    <div class="option">
                        <fmt:message key="label.voyages"/>
                    </div>
                </a>
            </div>
            <div class="check">
                <a href="favorites">
                    <div class="option">
                        <fmt:message key="label.favorites"/>
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
        <div class="profile-options">

            <div class="test">
                <div class="selected">
                    <a href="../profile">
                        <fmt:message key="label.general"/>
                    </a>
                </div>
            </div>
            <div class="test">
                <div class="profile-option">
                    <a href="boughtTickets">
                        <fmt:message key="label.boughtTickets"/>
                    </a>
                </div>
            </div>
            <div class="test">
                <div class="profile-option">
                    <a href="profileChangePass.jsp">
                        <fmt:message key="label.changePass"/>
                    </a>
                </div>
            </div>
            <div class="test">
                <div class="profile-option">
                    <a href="profileChangeGeneral.jsp">
                        <fmt:message key="label.changeGeneral"/>
                    </a>
                </div>
            </div>
            <div class="test">
                <div class="profile-option">
                    <a href="quit">
                        <fmt:message key="label.quit"/>
                    </a>
                </div>
            </div>
        </div>
        <div class="profile-details">
            <div class="text">
                <div class="hTest">
                    General information
                </div>
                <div class="row">


                    <div class="column">
                        <div class="title">
                            Email
                        </div>
                        <div class="info">
                            ${user.getEmail()}
                        </div>
                        <div class="title">
                            <fmt:message key="label.firstName"/>
                        </div>
                        <div class="info">
                            ${user.getFirstName()}
                        </div>
                        <div class="title">
                            <fmt:message key="label.birthdate"/>
                        </div>
                        <div class="info">
                            ${user.getBirthDate()}
                        </div>
                    </div>
                    <div class="column">
                        <div class="title">
                            <fmt:message key="label.phone"/>
                        </div>
                        <div class="info">
                            ${user.getPhone()}
                        </div>
                        <div class="title">
                            <fmt:message key="label.lastName"/>
                        </div>
                        <div class="info">
                            ${user.getLastName()}
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="button-bar">
            <div class="check">
                <a href="changeLang?lang=en&page=${pageContext.request.getRequestURI()}">
                    <div class="option">
                        en
                    </div>
                </a>
            </div>
            <div class="check">
                <a href="changeLang?lang=uk&page=${pageContext.request.getRequestURI()}">
                    <div class="option">
                        uk
                    </div>
                </a>
            </div>
            <div class="check">
                <a href="changeLang?lang=ru&page=${pageContext.request.getRequestURI()}">
                    <div class="option">
                        ru
                    </div>
                </a>
            </div>
        </div>
    </body>
</html>
