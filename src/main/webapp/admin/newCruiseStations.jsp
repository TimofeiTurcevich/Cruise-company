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
                <fmt:message key="label.selectStations"/>
                </span></h2>
            <form name="myForm" action="createCruise" method="post" onsubmit="return validateForm()">
                <input type="text" style="display:none;" name="luxPrice" required="" value=${param.luxPrice}>
                <input type="text" style="display:none;" name="stationNumbers" required="" value=${param.stationNumbers}>
                <input type="text" style="display:none;" name="standardPrice" required="" value=${param.standardPrice}>
                <input type="text" style="display:none;" name="shipId" required="" value=${param.shipId}>
                <c:forEach var="i" begin="1" end="${param.stationNumbers}" varStatus="status">
                    <div class="station">
                        <div class="picklist">
                            <nav>
                                <input type="number" id="stationId${status.count}" style="display:none;"
                                       name="stationId${status.count}" value="0">
                                <label for="touch${status.count}"><span id="picked${status.count}"><fmt:message
                                        key="label.chooseStation"/></span></label>
                                <input type="checkbox" id="touch${status.count}">
                                <div class="test2">
                                    <ul class="slide">
                                        <li><a id="0" onclick="pick${status.count}(this,this.innerHTML)">
                                            <fmt:message key="label.chooseStation"/>
                                        </a></li>
                                        <c:forEach var="station" items="${stations}" varStatus="status1">
                                            <li><a id="${station.getId()}" onclick="pick${status.count}(this,this.innerHTML)">${station.getCountry()},
                                                ${station.getCity()}</a></li>
                                        </c:forEach>
                                    </ul>
                                </div>
                                <script type="text/javascript">
                                  function pick${status.count}(li,value){
                                    document.getElementById('stationId${status.count}').value = li.id;
                                    document.getElementById('picked${status.count}').innerHTML = value;
                                    document.getElementById('touch${status.count}').checked = false;
                                  }

                                </script>
                            </nav>

                        </div>
                        <c:if test="${status.count==1}">

                            <div class="user" style="display:none;">
                                <input type="datetime-local" name="arrivalDate${status.count}">
                                <label>
                                    <fmt:message key="label.arrivalDate"/>
                                </label>
                            </div>

                            <div class="user">
                                <input type="datetime-local" name="departureDate${status.count}" required="">
                                <label>
                                    <fmt:message key="label.departureDate"/>
                                </label>
                            </div>
                        </c:if>
                        <c:if test="${status.count!=1 && status.count!= param.stationNumbers}">

                            <div class="user">
                                <input type="datetime-local" name="arrivalDate${status.count}" required="">
                                <label>
                                    <fmt:message key="label.arrivalDate"/>
                                </label>
                            </div>

                            <div class="user">
                                <input type="datetime-local" name="departureDate${status.count}" required="">
                                <label>
                                    <fmt:message key="label.departureDate"/>
                                </label>
                            </div>
                        </c:if>

                        <c:if test="${status.count == param.stationNumbers}">

                            <div class="user">
                                <input type="datetime-local" name="arrivalDate${status.count}" required="">
                                <label>
                                    <fmt:message key="label.arrivalDate"/>
                                </label>
                            </div>

                            <div class="user" style="display:none;">
                                <input type="datetime-local" name="departureDate${status.count}">
                                <label>
                                    <fmt:message key="label.departureDate"/>
                                </label>
                            </div>
                        </c:if>
                    </div>
                </c:forEach>
                <input type="submit" name="" value=<fmt:message key="label.submitButton"/>>
            </form>
        </div>
    </body>
</html>
