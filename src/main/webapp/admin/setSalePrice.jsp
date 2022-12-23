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
        <title></title>
    </head>
    <body>
        <div class="login-box">
            <h2>
                <fmt:message key="label.setSalePrice"/>
            </h2>
            <form action="setSale?id=${param.id}" method="post">

                <div class="user">
                    <input type="number" min="1" name="saleStandardPrice" required="">
                    <label>
                        <fmt:message key="label.standardPriceSet"/>
                    </label>
                </div>
                <div class="user">
                    <input type="number" min="1" name="saleLuxPrice" required="">
                    <label>
                        <fmt:message key="label.luxPriceSet"/>
                    </label>
                </div>
                <input type="submit" name="" value=<fmt:message key="label.submitButton"/>>
            </form>
        </div>
    </body>
</html>
