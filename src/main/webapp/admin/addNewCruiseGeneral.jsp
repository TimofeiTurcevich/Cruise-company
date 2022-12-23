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
                <fmt:message key="label.newVoyage"/>
                </span></h2>
            <form action="createGeneral" method="post">
                <div class="picklist">
                    <nav>
                        <input id="shipId" type="number" name="shipId" style="display:none;" value="0">
                        <label for="touch"><span id="picked"><fmt:message key="label.chooseShip"/></span></label>
                        <input type="checkbox" id="touch">
                        <div class="test2">
                            <ul class="slide">
                                <li><a id="0" onclick="pick(this,this.innerHTML)">
                                    <fmt:message key="label.chooseShip"/>
                                </a></li>
                                <c:forEach var="ship" items="${ships}" varStatus="status">
                                    <li><a id="${ship.getId()}" onclick="pick(this,this.innerHTML)">${ship.getName()}</a></li>
                                </c:forEach>
                            </ul>
                        </div>
                        <script type="text/javascript">
                      function pick(li,value){
                        document.getElementById('shipId').value = li.id;
                        document.getElementById('picked').innerHTML = value;
                        document.getElementById('touch').checked = false;
                      }

                        </script>
                    </nav>

                </div>
                <div class="user">
                    <input type="number" min="1" name="standardPrice" required="">
                    <label>
                        <fmt:message key="label.standardPriceSet"/>
                    </label>
                </div>
                <div class="user">
                    <input type="number" min="1" name="luxPrice" required="">
                    <label>
                        <fmt:message key="label.luxPriceSet"/>
                    </label>
                </div>
                <div class="user">
                    <input type="number" min="3" max="10" name="stationNumbers" required="">
                    <label>
                        <fmt:message key="label.stationNumbers"/>
                    </label>
                </div>
                <input type="submit" name="" value=<fmt:message key="label.submitButton"/>>
            </form>
        </div>
    </body>
</html>
