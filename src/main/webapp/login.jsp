<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${applicationScope['lang']}" scope="session"/>
<fmt:setBundle basename="messages"/>
<html lang="en">
    <head>
        <link rel="stylesheet" href="login.css">
        <meta charset="utf-8">
        <script src="login.js" charset="utf-8"></script>
        <title></title>
    </head>
    <body>
        <div class="login-box">
            <h2>
                <fmt:message key="label.login"/>
                </a></h2>
            <div class="error">
                ${requestScope['javax.servlet.error.message']}
            </div>
            <form name="myForm" action="login" method="post" onsubmit="return validateForm()">
                <div class="user">
                    <input type="text" name="email" required="">
                    <label>Email</label>
                </div>
                <div class="user">
                    <input type="password" name="pass" required="">
                    <label>
                        <fmt:message key="label.pass"/>
                    </label>
                </div>
                <input type="submit" name="" value=<fmt:message key="label.submitButton"/>>
            </form>
            <div class="register">
                <fmt:message key="label.dontHaveAccount"/>
                <a href="register.jsp">
                    <fmt:message key="label.signUp"/>
                </a>
            </div>
        </div>
    </body>
</html>
