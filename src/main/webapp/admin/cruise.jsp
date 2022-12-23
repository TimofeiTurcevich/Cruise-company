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
        <div class="new-cruise-bar">
            <div class="cruise">
                <img src="../Ship_Pictures/ship_${voyage.getShip().getId()}/${voyage.getShip().getPicture().getName()}" alt=""
                     width="800" height="400" class="cruise-photo"/>
                <div class="info">
                    <c:forEach var="staff" items="${voyage.getShip().getStaffs()}" varStatus="status">
                        <div class="cruise-info">
                            <hr>
                            <fmt:message key="label.staffName"/>
                            : ${staff.getName()}<br/>
                            <fmt:message key="label.staffPosition"/>
                            : ${staff.getPosition()}
                        </div>
                    </c:forEach>
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
                        <div class="buy">
                            <a href="delete?id=${voyage.getId()}">
                                <fmt:message key="label.deleteCruise"/>
                            </a>
                        </div>
                        <div class="buy">
                            <a href="editGeneral?id=${voyage.getId()}">
                                <fmt:message key="label.editGeneral"/>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="bottom">
                    <div class="buy-button">
                        <div class="buy">
                            <a href="deleteStation.jsp?id=${voyage.getId()}">
                                <fmt:message key="label.deleteStation"/>
                            </a>
                        </div>
                        <div class="buy">
                            <a href="addStation?id=${voyage.getId()}">
                                <fmt:message key="label.addStation"/>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="bottom">
                    <div class="buy-button">
                        <div class="buy">
                            <c:if test="${(voyage.isSale()==null) or (voyage.isSale()==false)}">
                                <a href="setSalePrice.jsp?id=${voyage.getId()}">
                                    <fmt:message key="label.setSalePrice"/>
                                </a>
                            </c:if>
                            <c:if test="${voyage.isSale()==true}">
                                <a href="removeSale?id=${voyage.getId()}">
                                    <fmt:message key="label.removeSale"/>
                                </a>
                            </c:if>
                        </div>
                        <div class="buy">
                            <a href="editStation.jsp?id=${voyage.getId()}">
                                <fmt:message key="label.editStation"/>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="button-bar">
            <div class="cruise">
                <div class="info">
                    <div class="cruise-info">
                        <hr>
                        <fmt:message key="label.shipName"/>
                        : ${voyage.getShip().getName()}
                    </div>
                    <div class="cruise-info">
                        <hr>
                        <fmt:message key="label.stationNumbers"/>
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
                    <div class="cruise-info">
                        <hr>
                        <fmt:message key="label.standardPrice"/>
                        : ${voyage.getStandardPrice()}
                    </div>
                    <div class="cruise-info">
                        <hr>
                        <fmt:message key="label.luxPrice"/>
                        : ${voyage.getLuxPrice()}
                    </div>
                    <c:if test="${voyage.isSale()}">
                        <div class="cruise-info">
                            <hr>
                            <fmt:message key="label.salePriceStandard"/>
                            : ${voyage.getSaleStandardPrice()}
                        </div>
                        <div class="cruise-info">
                            <hr>
                            <fmt:message key="label.salePriceLux"/>
                            : ${voyage.getSaleLuxPrice()}
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
        </div>
        <div class="lang-bar">
            <div class="check">
                <a href="changeLang?lang=en&page=${requestScope['javax.servlet.forward.request_uri']}?id=${voyage.getId()}">
                    <div class="option">
                        en
                    </div>
                </a>
            </div>
            <div class="check">
                <a href="changeLang?lang=uk&page=${requestScope['javax.servlet.forward.request_uri']}?id=${voyage.getId()}">
                    <div class="option">
                        uk
                    </div>
                </a>
            </div>
            <div class="check">
                <a href="changeLang?lang=ru&page=${requestScope['javax.servlet.forward.request_uri']}?id=${voyage.getId()}">
                    <div class="option">
                        ru
                    </div>
                </a>
            </div>
        </div>
    </body>
</html>
