<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${applicationScope['lang']}" scope="session"/>
<fmt:setBundle basename="messages"/>
<html lang="en">
    <head>
        <link rel="stylesheet" href="buyForm.css">
        <meta charset="utf-8">
        <title></title>
    </head>
    <body>
        <div class="login-box">
            <h2>
                <fmt:message key="label.documents"/>
            </h2>
            <form action="document?id=${param.id}&count=${param.count}" method="post" enctype="multipart/form-data">
                <c:forEach var="i" begin="1" end="${param.count}" varStatus="status">
                    <div class="user">
                        <input type="file" name="document${i}" accept="image/*" required="" >
                        <label>
                            <fmt:message key="label.file"/>
                        </label>
                    </div>
                </c:forEach>
                <input type="submit" name="" value=<fmt:message key="label.submitButton"/>>
            </form>
        </div>
    </body>
</html>
