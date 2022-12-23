<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${applicationScope['lang']}" scope="session"/>
<fmt:setBundle basename="messages"/>
<html lang="en">
    <head>
        <link rel="stylesheet" href="tickets.css">
        <meta charset="utf-8">
        <title></title>
    </head>
    <body>
        <div class="menu-bar">
            <div class="check">
                <a href="needToAccept">
                    <div class="option">
                        <fmt:message key="label.acceptTickets"/>
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
                <a href="tickets">
                    <div class="option">
                        <fmt:message key="label.voyages"/>
                    </div>
                </a>
            </div>
            <div class="check">
                <a href="newCruiseGeneral">
                    <div class="option">
                        <fmt:message key="label.newVoyage"/>
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
        <div class="search">
            <div class="please">
                <div class="title">
                    <fmt:message key="label.minPrice"/>
                </div>
                <div class="range-box">
                    <div>
                        <span id="rangeValueMin">0</span>
                        <input class="range" type="range" name "" value="0" min="0" max="${maxPrice-1}"
                        onChange="rangeSlideMin(this.value)" onmousemove="rangeSlideMin(this.value)"></input>
                    </div>
                    <script type="text/javascript">
                          function rangeSlideMin(value) {
                              document.getElementById('rangeValueMin').innerHTML = value;
                              document.getElementById('priceMin').value = value;
                          }

                        </script>
                </div>
                <div class="title">
                    <fmt:message key="label.maxPrice"/>
                </div>
                <div class="range-box">
                    <div>
                        <span id="rangeValueMax">0</span>
                        <input class="range" type="range" name "" value="0" min="0" max="${maxPrice}"
                        onChange="rangeSlideMax(this.value)" onmousemove="rangeSlideMax(this.value)"></input>
                    </div>
                    <script type="text/javascript">
                          function rangeSlideMax(value) {
                              document.getElementById('rangeValueMax').innerHTML = value;
                              document.getElementById('priceMax').value = value;
                          }

                        </script>
                </div>

                <div class="picklist">
                    <nav>

                        <label for="touch"><span id="picked"><fmt:message key="label.startStation"/></span></label>
                        <input type="checkbox" id="touch" onchange="check1()">
                        <div class="test2">
                            <ul class="slide">
                                <li><a id="" onclick="pick(this,this.innerHTML)">
                                    <fmt:message key="label.startStation"/>
                                </a></li>
                                <c:forEach var="station" items="${stations}">
                                    <li><a id="${station.getId()}" onclick="pick(this,this.innerHTML)">${station.getCountry()},
                                        ${station.getCity()}</a></li>
                                </c:forEach>
                            </ul>
                        </div>
                        <script type="text/javascript">
                            function pick(li,value){
                              document.getElementById('picked').innerHTML = value;
                              document.getElementById('touch').checked = false;
                              document.getElementById('startStationId').value = li.id;
                            }

                            </script>
                    </nav>

                </div>
                <div class="picklist">
                    <nav>

                        <label for="touch2"><span id="picked2"><fmt:message key="label.selectStation"/></span></label>
                        <input type="checkbox" id="touch2" onchange="check2()">

                        <div class="test2">
                            <ul class="slide">
                                <li><a id="" onclick="pick2(this,this.innerHTML)">
                                    <fmt:message key="label.selectStation"/>
                                </a></li>
                                <c:forEach var="station" items="${stations}">
                                    <li><a id="${station.getId()}" onclick="pick2(this,this.innerHTML)">${station.getCountry()},
                                        ${station.getCity()}</a></li>
                                </c:forEach>
                            </ul>
                        </div>
                        <script type="text/javascript">
                          function pick2(li,value){
                            document.getElementById('picked2').innerHTML = value;
                            document.getElementById('touch2').checked = false;
                            document.getElementById('stationId').value = li.id;
                          }

                            </script>
                    </nav>

                    <label class="control">
                        <input type="checkbox" name="checkbox" onchange="setValue(this.checked)"/>
                        <fmt:message key="label.final"/>
                        <script type="text/javascript">
                          function setValue(value){
                            document.getElementById('final').checked = value;
                          }

                            </script>
                    </label>
                </div>
                <div class="date">
                    <div class="date-text">

                        <label for="startDate">
                            <fmt:message key="label.startDate"/>
                        </label>
                        <label for="endDate">
                            <fmt:message key="label.endDate"/>
                        </label>
                    </div>
                    <div class="calendar">
                        <input type="date" onchange="setStartDate(this.value)"/>
                        <input type="date" onchange="setEndDate(this.value)"/>
                        <script type="text/javascript">
                              function setStartDate(value){
                                document.getElementById('startDate').value = value;
                              }

                            </script>
                        <script type="text/javascript">
                              function setEndDate(value){
                                document.getElementById('endDate').value = value;
                              }

                            </script>
                    </div>
                </div>
                <div class="picklist">
                    <nav>

                        <label for="touch3"><span id="picked3"><fmt:message key="label.sort"/></span></label>
                        <input type="checkbox" id="touch3">

                        <div class="test2">
                            <ul class="slide">
                                <li><a id="" onclick="pick3(this,this.innerHTML)">
                                    <fmt:message key="label.sort"/>
                                </a></li>
                                <li><a id="lux_price" onclick="pick3(this,this.innerHTML)">
                                    <fmt:message key="label.luxPriceSort"/>
                                </a></a></li>
                                <li><a id="lux_price DESC" onclick="pick3(this,this.innerHTML)">
                                    <fmt:message key="label.luxPriceSortDesc"/>
                                </a></a></li>
                                <li><a id="standard_price" onclick="pick3(this,this.innerHTML)">
                                    <fmt:message key="label.standardPriceSort"/>
                                </a></a></li>
                                <li><a id="standard_price DESC" onclick="pick3(this,this.innerHTML)">
                                    <fmt:message key="label.standardPriceSortDesc"/>
                                </a></a></li>
                                <li><a id="voyage duration" onclick="pick3(this,this.innerHTML)">
                                    <fmt:message key="label.voyageDuration"/>
                                </a></a></li>
                                <li><a id="stations count" onclick="pick3(this,this.innerHTML)">
                                    <fmt:message key="label.stationCount"/>
                                </a></a></li>
                            </ul>
                        </div>
                        <script type="text/javascript">
                          function pick3(li,value){
                            document.getElementById('picked3').innerHTML = value;
                            document.getElementById('touch3').checked = false;
                            document.getElementById('sortBy').value = li.id;
                          }

                            </script>
                    </nav>
                </div>
                <div class="number">
                    <fmt:message key="label.duration"/>
                    <input id="voyageDuration" type="number" value="" min="0" onchange="setVoyageDuration(this.value)">

                    <script type="text/javascript">
                            function setVoyageDuration(value){
                              document.getElementById('voyageDurationForm').value = value;
                            }

                        </script>
                </div>
            </div>
        </div>
        <div class="start-bar">
            <div class="option">
                <form action="sort" method="post">
                    <input id="priceMin" type="number" name="priceMin" value="0" style="display:none;">
                    <input id="priceMax" type="number" name="priceMax" value="0" style="display:none;">
                    <input id="startStationId" type="number" name="startStationId" value="" style="display:none;">
                    <input id="stationId" type="number" name="stationId" value="" style="display:none;">
                    <input id="final" type="checkbox" name="isFinal" style="display:none;"/>
                    <input type="date" id="startDate" name="startDate" style="display:none;"/>
                    <input type="date" id="endDate" name="endDate" style="display:none;"/>
                    <input type="text" id="sortBy" name="sortBy" style="display:none;"/>
                    <input id="voyageDurationForm" name="voyageDuration" type="number" value="" min="0" style="display:none;">
                    <input type="submit" name="" value=<fmt:message key="label.submitButton"/>>
                </form>
            </div>
        </div>
        <div class="new-cruise-bar">
            <c:if test="${tickets.size()>0}">
                <c:forEach var="i" begin="${(param.page-1)*3}"
                           end="${tickets.size()<(param.page)*3?tickets.size()-1:(param.page-1)*3+2}" varStatus="status1">
                    <div class="cruise">

                        <img src="../Ship_Pictures/ship_${tickets.get(i).getShip().getId()}/${tickets.get(i).getShip().getPicture().getName()}"
                             alt="" class="cruise-photo"/>
                        <div class="vertical-line">
                            <hr>
                        </div>
                        <div class="test">

                            <div class="text">
                                <c:forEach var="station" items="${tickets.get(i).getVoyageStations()}" varStatus="status">
                                    <fmt:message key="label.station"/>
                                    :
                                    <c:out value="${station}"/>
                                    <br/><br/>
                                </c:forEach>
                            </div>
                        </div>
                        <div class="vertical-line">
                            <hr>
                        </div>
                        <div class="test">

                            <div class="text">
                                <fmt:message key="label.shipName"/>
                                :
                                <c:out value="${tickets.get(i).getShip().getName()}"/>
                                <br/><br/>
                                <fmt:message key="label.departureDate"/>
                                :
                                <c:out value="${tickets.get(i).getVoyageStations().get(0).getDepartureDate().toString().replaceAll(':00.0','')}"/>
                                <br/><br/>
                                <fmt:message key="label.arrivalDate"/>
                                :
                                <c:out value="${tickets.get(i).getVoyageStations().get(tickets.get(i).getVoyageStations().size()-1).getArrivalDate().toString().replaceAll(':00.0','')}"/>
                                <br/>
                            </div>
                        </div>
                        <div class="vertical-line">
                            <hr>
                        </div>
                        <div class="more-info">
                            <c:if test="${tickets.get(i).isSale()}">
                                <div class="sale-price">
                                    ${tickets.get(i).getStandardPrice()}$ - ${tickets.get(i).getLuxPrice()}$
                                </div>
                                <div class="price">
                                    ${tickets.get(i).getSaleStandardPrice()}$ - ${tickets.get(i).getSaleLuxPrice()}$
                                </div>
                            </c:if>
                            <c:if test="${(tickets.get(i).isSale()==null) or (!tickets.get(i).isSale())}">
                                <div class="price">
                                    ${tickets.get(i).getStandardPrice()}$ - ${tickets.get(i).getLuxPrice()}$
                                </div>
                            </c:if>
                            <a href="cruise?id=${tickets.get(i).getId()}">
                                <fmt:message key="label.moreInfo"/>
                            </a>
                        </div>
                    </div>

                    <c:if test="${status1.count != 3}">
                        <div class="line">
                            <hr>
                        </div>
                    </c:if>
                </c:forEach>
            </c:if>

            <c:if test="${tickets.size()<1}">
                <div class="no-info">
                    <fmt:message key="label.noResult"/>
                </div>
            </c:if>

        </div>
        <c:if test="${tickets.size()>0}">
            <div class="pagination">
                <fmt:parseNumber var="intValue" integerOnly="true"
                                 type="number" value="${tickets.size()/3+0.9}"/>
                <div class="some">
                    <div class="pagination:container">
                        <c:if test="${param.page!=1}">
                            <a href="tickets?page=${param.page-1}">
                                <div class="pagination:number arrow">
                                    <svg width="18" height="18">
                                        <use xlink:href="#left"/>
                                    </svg>
                                </div>
                            </a>
                        </c:if>
                        <c:if test="${param.page>4}">
                            <a href="tickets?page=1">
                                <div class="pagination:number">
                                    1
                                </div>
                            </a>
                            <div class="pagination:number">
                                ...
                            </div>
                        </c:if>
                        <c:forEach var="j" begin="${param.page>4?(param.page+2>=intValue?intValue-4:param.page-1):1}"
                                   end="${intValue>7?(param.page>4?(param.page+2>=intValue?intValue:param.page+1):5):intValue}"
                                   varStatus="status2">
                            <c:if test="${param.page!=j}">
                                <a href="tickets?page=${j}">
                                    <div class="pagination:number">
                                        ${j}
                                    </div>
                                </a>
                            </c:if>
                            <c:if test="${param.page==j}">
                                <a href="tickets?page=${j}">
                                    <div class="pagination:number pagination:active">
                                        ${j}
                                    </div>
                                </a>
                            </c:if>
                        </c:forEach>
                        <c:if test="${intValue>7 and param.page+2<intValue}">
                            <div class="pagination:number">
                                ...
                            </div>
                            <a href="tickets?page=${intValue}">
                                <div class="pagination:number">
                                    ${intValue}
                                </div>
                            </a>
                        </c:if>

                        <c:if test="${param.page!=intValue}">
                            <div class="pagination:number arrow">
                                <a href="tickets?page=${param.page+1}">
                                    <svg width="18" height="18">
                                        <use xlink:href="#right"/>
                                    </svg>
                            </div>
                            </a>
                        </c:if>
                    </div>
                </div>

                <svg class="hide">
                    <symbol id="left" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7"></path>
                    </symbol>
                    <symbol id="right" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"></path>
                    </symbol>
                </svg>
            </div>
        </c:if>
        <div class="button-bar">
            <div class="check">
                <a href="changeLang?lang=en&page=${requestScope['javax.servlet.forward.request_uri']}">
                    <div class="option">
                        en
                    </div>
                </a>
            </div>
            <div class="check">
                <a href="changeLang?lang=uk&page=${requestScope['javax.servlet.forward.request_uri']}">
                    <div class="option">
                        uk
                    </div>
                </a>
            </div>
            <div class="check">
                <a href="changeLang?lang=ru&page=${requestScope['javax.servlet.forward.request_uri']}">
                    <div class="option">
                        ru
                    </div>
                </a>
            </div>
        </div>
    </body>
</html>
