<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${applicationScope['lang']}" scope="session"/>
<fmt:setBundle basename="messages"/>
<html lang="en">
    <head>
        <link rel="stylesheet" href="register.css">
        <meta charset="utf-8">
        <script src="register.js" charset="utf-8"></script>
        <title></title>
    </head>
    <body>
        <div class="login-box">
            <h2>
                <fmt:message key="label.register"/>
                </a></h2>
            <div class="error">
                ${requestScope['javax.servlet.error.message']}
            </div>
            <form name="myForm" action="register" method="post" onsubmit="return validateForm()">
                <div class="user">
                    <input type="text" name="email" required="">
                    <label>Email</label>
                </div>
                <div class="user">
                    <input type="text" name="firstName" required="">
                    <label>
                        <fmt:message key="label.firstName"/>
                    </label>
                </div>
                <div class="user">
                    <input type="text" name="lastName" required="">
                    <label>
                        <fmt:message key="label.lastName"/>
                    </label>
                </div>
                <div class="user">
                    <input type="text" name="phone" required="">
                    <label>
                        <fmt:message key="label.phone"/>
                    </label>
                </div>
                <div class="user">
                    <input type="date" name="date" required="">
                    <label>
                        <fmt:message key="label.birthdate"/>
                    </label>
                </div>
                <div class="user">
                    <input type="password" name="pass" required="">
                    <label>
                        <fmt:message key="label.pass"/>
                    </label>
                </div>
                <div class="user">
                    <input type="password" name="" required="">
                    <label>
                        <fmt:message key="label.confirm"/>
                    </label>
                </div>
                <input type="submit" name="" value=<fmt:message key="label.submitButton"/>>
            </form>
        </div>
    </body>
</html>
