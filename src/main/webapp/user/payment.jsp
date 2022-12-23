<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${applicationScope['lang']}" scope="session"/>
<fmt:setBundle basename="messages"/>
<html lang="en">
    <head>
        <link rel="stylesheet" href="payment.css">
        <meta charset="utf-8">
        <script src="payment.js"></script>
        <title></title>
    </head>
    <body>
        <div class="login-box">
            <h2>
                <fmt:message key="label.payment"/>
            </h2>
            <form name="myForm" action="payment?id=${param.id}" method="post" onsubmit="return validateForm()">
                <div class="user">
                    <input type="text" id="card-number" name="" required="" onkeydown="keyCard(event)" readonly>
                    <label id="card-label">
                        <fmt:message key="label.cardNumber"/>
                    </label>
                </div>
                <div class="user">
                    <input type="text" id="card-date" name="" required="" onkeydown="keyDate(event)" readonly>
                    <label id="date-label">
                        <fmt:message key="label.dateMonth"/>
                    </label>
                </div>
                <div class="user">
                    <input type="password" id="card-date" name="" required="" maxlength="3">
                    <label id="date-label">CVV</label>
                </div>
                <input type="submit" name="" value=<fmt:message key="label.submitButton"/>>
            </form>
        </div>
    </body>
</html>
