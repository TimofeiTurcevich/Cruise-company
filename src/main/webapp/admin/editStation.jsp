<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${applicationScope['lang']}" scope="session"/>
<fmt:setBundle basename="messages"/>
<html lang="en">
    <head>
        <link rel="stylesheet" href="addNewCruiseGeneral.css">
        <meta charset="utf-8">
        <script src="newCruiseStations.js" charset="utf-8"></script>
        <title></title>
    </head>
    <body>
        <div class="login-box">
            <h2>
                <fmt:message key="label.editStation"/>
            </h2>
            <form name="myForm" action="editStation?id=${param.id}" method="post" onsubmit="return validateForm()">

                <div class="user">
                    <input type="number" min="2" max="${voyage.getVoyageStations().size()-1}" name="position" required="">
                    <label>
                        <fmt:message key="label.stationNumber"/>
                    </label>
                </div>
                <div class="user">
                    <input type="datetime-local" name="arrivalDate" required="">
                    <label>
                        <fmt:message key="label.arrivalDate"/>
                    </label>
                </div>
                <div class="user">
                    <input type="datetime-local" name="departureDate" required="">
                    <label>
                        <fmt:message key="label.departureDate"/>
                    </label>
                </div>
                <input type="submit" name="" value=<fmt:message key="label.submitButton"/>>
            </form>
        </div>
    </body>
</html>
