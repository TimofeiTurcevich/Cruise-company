<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${applicationScope['lang']}" scope="session"/>
<fmt:setBundle basename="messages"/>
<html lang="en">
    <head>
        <link rel="stylesheet" href="cruise.css">
        <meta charset="utf-8">
        <script src="cruise.js" charset="utf-8"></script>
        <title></title>
    </head>
    <body>
        <div class="menu-bar">
            <div class="check">
                <a href="../index">
                    <div class="option">
                        <fmt:message key="label.home"/>
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
                <a href="../tickets">
                    <div class="option">
                        <fmt:message key="label.voyages"/>
                    </div>
                </a>
            </div>
            <div class="check">
                <a href="favorites">
                    <div class="option">
                        <fmt:message key="label.favorites"/>
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
        <div class="new-cruise-bar">
            <div class="cruise">
                <img src="../Ship_Pictures/ship_${voyage.getShip().getId()}/${voyage.getShip().getPicture().getName()}" alt=""
                     width="800" height="400" class="cruise-photo"/>
                <div class="info">
                    <div class="cruise-info">
                        <hr>
                        <fmt:message key="label.shipName"/>
                        : ${voyage.getShip().getName()}
                    </div>
                    <div class="cruise-info">
                        <hr>
                        <fmt:message key="label.stationsNumber"/>
                        : ${voyage.getVoyageStations().size()}
                    </div>
                    <div class="cruise-info">
                        <hr>
                        <fmt:message key="label.departureDate"/>
                        : ${voyage.getVoyageStations().get(0).getDepartureDate().toString().replaceAll(':00.0','')}
                    </div>
                    <div class="cruise-info">
                        <hr>
                        <fmt:message key="label.arrivalDate"/>
                        :
                        ${voyage.getVoyageStations().get(voyage.getVoyageStations().size()-1).getArrivalDate().toString().replaceAll(':00.0','')}
                    </div>
                    <c:if test="${voyage.isSale()}">
                        <div class="cruise-info">
                            <hr>
                            <fmt:message key="label.standardPrice"/>
                            : ${voyage.getStandardPrice()}$
                        </div>
                        <div class="cruise-info">
                            <hr>
                            <fmt:message key="label.luxPrice"/>
                            : ${voyage.getLuxPrice()}$
                        </div>
                    </c:if>
                    <div class="cruise-info">
                        <hr>
                        <fmt:message key="label.standardCount"/>
                        : ${voyage.getShip().getStandardRooms()}
                    </div>
                    <div class="cruise-info">
                        <hr>
                        <fmt:message key="label.luxCount"/>
                        : ${voyage.getShip().getLuxRooms()}
                    </div>
                    <div class="cruise-info">
                        <hr>
                        <fmt:message key="label.boughtStandard"/>
                        : ${voyage.getBoughtStandard()}
                    </div>
                    <div class="cruise-info">
                        <hr>
                        <fmt:message key="label.boughtLux"/>
                        : ${voyage.getBoughtLux()}
                    </div>
                </div>
            </div>
            <hr>
            <div class="border">
                <div class="line">
                    <div class="bubble">
                        <div class="text-try">
                            <fmt:message key="label.station"/>
                        </div>
                    </div>
                    <div class="bubble">
                        <div class="text-try">

                            <fmt:message key="label.country"/>
                        </div>
                    </div>
                    <div class="bubble">
                        <div class="text-try">
                            <fmt:message key="label.city"/>
                        </div>
                    </div>
                    <div class="bubble">
                        <div class="text-try">
                            <fmt:message key="label.arrivalDate"/>
                        </div>
                    </div>
                    <div class="bubble">
                        <div class="text-try">
                            <fmt:message key="label.departureDate"/>
                        </div>
                    </div>
                </div>
                <c:forEach var="station" items="${voyage.getVoyageStations()}" varStatus="status">
                    <div class="line">
                        <div class="bubble">
                            <div class="text-try">
                                ${status.count}
                            </div>
                        </div>
                        <div class="bubble">
                            <div class="text-try">
                                ${station.getStation().getCountry()}
                            </div>
                        </div>
                        <div class="bubble">
                            <div class="text-try">
                                ${station.getStation().getCity()}
                            </div>
                        </div>
                        <div class="bubble">
                            <div class="text-try">
                                <c:if test="${station.getArrivalDate()==null}">
                                    ----
                                </c:if>
                                <c:if test="${station.getArrivalDate()!=null}">
                                    ${station.getArrivalDate().toString().replaceAll(':00.0','')}
                                </c:if>

                            </div>
                        </div>
                        <div class="bubble">
                            <div class="text-try">
                                <c:if test="${station.getDepartureDate()==null}">
                                    ----
                                </c:if>
                                <c:if test="${station.getDepartureDate()!=null}">
                                    ${station.getDepartureDate().toString().replaceAll(':00.0','')}
                                </c:if>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <hr>
            <div class="bottom-line">
                <div class="bottom">
                    <div class="buy-button">
                        <div class="text">
                            <fmt:message key="label.standard"/>
                        </div>
                        <div class="text" id="standardPrice">
                            <c:if test="${voyage.isSale()}">
                                ${voyage.getSaleStandardPrice()}$
                            </c:if>
                            <c:if test="${!voyage.isSale()}">
                                ${voyage.getStandardPrice()}$
                            </c:if>
                        </div>
                        <c:if test="${voyage.getBoughtStandard()!=voyage.getShip().getStandardRooms() and voyage.isAvailable()}">
                            <div class="buy">
                                <form action="buy?id=${param.id}&type=standard" method="post">
                                    <input type="number" id="standardChild" style="display:none" name="child" value="0">
                                    <input type="number" id="standardAdult" style="display:none" name="adult" value="1">
                                    <input type="number" id="standardNights" style="display:none" name="nights "
                                           value="${voyage.getVoyageDuration()}">
                                    <input type="submit" name="" value=<fmt:message key="label.buy"/>>
                                </form>
                            </div>
                        </c:if>
                    </div>
                </div>
                <div class="count">
                    <div class="try">
                        <div class="label">
                            <fmt:message key="label.adult"/>
                        </div>
                        <div class="label">
                            <fmt:message key="label.children"/>
                        </div>
                        <div class="label">
                            <fmt:message key="label.nights"/>
                        </div>
                    </div>
                    <div class="try">

                        <input type="number" id="adult" name="adult" value="1" min="1" max="4" onchange="adultChange()">
                        <input type="number" id="child" name="child" value="0" min="0" max="3" onchange="childChange()">
                        <input type="number" id="night" name="nights" value="${voyage.getVoyageDuration()}" readonly>
                    </div>
                </div>
                <div class="bottom">
                    <div class="buy-button">
                        <div class="text">
                            <fmt:message key="label.lux"/>
                        </div>
                        <div class="text" id="luxPrice">
                            <c:if test="${voyage.isSale()}">
                                ${voyage.getSaleLuxPrice()}$
                            </c:if>
                            <c:if test="${!voyage.isSale()}">
                                ${voyage.getLuxPrice()}$
                            </c:if>
                        </div>
                        <c:if test="${voyage.getBoughtLux()!=voyage.getShip().getLuxRooms() and voyage.isAvailable()}">
                            <div class="buy">
                                <form action="buy?id=${param.id}&type=lux" method="post">
                                    <input type="number" id="luxChild" style="display:none" name="child" value="0">
                                    <input type="number" id="luxAdult" style="display:none" name="adult" value="1">
                                    <input type="number" id="luxNights" style="display:none" name="nights "
                                           value="${voyage.getVoyageDuration()}">
                                    <input type="submit" name="" value=<fmt:message key="label.buy"/>>
                                </form>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>

        <div class="lang-bar">
            <div class="check">

                <div class="option">
                    <a href="changeLang?lang=en&page=${requestScope['javax.servlet.forward.request_uri']}?id=${voyage.getId()}">en</a>
                </div>
            </div>
            <div class="check">

                <div class="option">
                    <a href="changeLang?lang=uk&page=${requestScope['javax.servlet.forward.request_uri']}?id=${voyage.getId()}">uk</a>
                </div>
            </div>
            <div class="check">

                <div class="option">
                    <a href="changeLang?lang=ru&page=${requestScope['javax.servlet.forward.request_uri']}?id=${voyage.getId()}">ru</a>
                </div>
            </div>
        </div>

        <div class="button-bar">
            <c:if test="${favorite}">
                <a href="removeFavorite?id=${voyage.getId()}" class="option"><span><fmt:message
                        key="label.removeFavorite"/></span></a>
            </c:if>
            <c:if test="${!favorite}">
                <a href="addToFavorite?id=${voyage.getId()}" class="option"><span><fmt:message key="label.addFavorite"/></span></a>
            </c:if>
        </div>
    </body>
</html>
