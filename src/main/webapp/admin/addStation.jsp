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
                <fmt:message key="label.addStation"/>
            </h2>
            <form name="myForm" action="addStationDB?id=${param.id}" method="post" onsubmit="return validateForm()">
                <div class="picklist">
                    <nav>
                        <input type="number" id="stationId" style="display:none;" name="stationId" value="">
                        <label for="touch"><span id="picked"><fmt:message key="label.chooseStation"/></span></label>
                        <input type="checkbox" id="touch">
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
                      function pick(li,value){
                        document.getElementById('stationId').value = li.id;
                        document.getElementById('picked').innerHTML = value;
                        document.getElementById('touch').checked = false;
                      }

                        </script>
                    </nav>

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
                <div class="user">
                    <input type="number" name="position" required="" min="2" max="${voyage.getVoyageStations().size()-1}">
                    <label>
                        <fmt:message key="label.stationNumber"/>
                    </label>
                </div>
                <input type="submit" name="" value=<fmt:message key="label.submitButton"/>>
            </form>
        </div>
    </body>
</html>
