<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${applicationScope['lang']}" scope="session"/>
<fmt:setBundle basename="messages"/>
<html lang="en">
    <head>
        <link rel="stylesheet" href="home.css">
        <meta charset="utf-8">
        <title></title>
    </head>
    <body>
        <div class="menu-bar">
            <div class="check">
                <a href="index">
                    <div class="option">
                        <fmt:message key="label.home"/>
                    </div>
                </a>
            </div>

            <div class="check">
                <a href="user/sales">
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
                <a href="user/favorites">
                    <div class="option">
                        <fmt:message key="label.favorites"/>
                    </div>
                </a>
            </div>
            <div class="check">
                <a href="profile">
                    <div class="option">
                        <fmt:message key="label.profile"/>
                    </div>
                </a>
            </div>
        </div>
        <img src="Hand-drawn-cruise-ship-on-transparent-background-PNG.png" alt="ship" width="1900" height="1000" class="ship"/>
        <div class="new-cruise-bar">
            <c:if test="${latestTickets.size()>0}">
                <c:forEach var="ticket" items="${latestTickets}" varStatus="status1">
                    <div class="cruise">

                        <img src="Ship_Pictures/ship_${ticket.getShip().getId()}/${ticket.getShip().getPicture().getName()}"
                             alt="" class="cruise-photo"/>
                        <div class="vertical-line">
                            <hr>
                        </div>
                        <div class="test">

                            <div class="text">
                                <c:forEach var="station" items="${ticket.getVoyageStations()}" varStatus="status">
                                    <fmt:message key="label.station"/>
                                    : ${status.count}:
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
                                <c:out value="${ticket.getShip().getName()}"/>
                                <br/><br/>
                                <fmt:message key="label.departureDate"/>
                                :
                                <c:out value="${ticket.getVoyageStations().get(0).getDepartureDate().toString().replaceAll(':00.0','')}"/>
                                <br/><br/>
                                <fmt:message key="label.arrivalDate"/>
                                :
                                <c:out value="${ticket.getVoyageStations().get(ticket.getVoyageStations().size()-1).getArrivalDate().toString().replaceAll(':00.0','')}"/>
                                <br/>
                            </div>
                        </div>
                        <div class="vertical-line">
                            <hr>
                        </div>
                        <div class="more-info">
                            <c:if test="${ticket.isSale()}">
                                <div class="sale-price">
                                    ${ticket.getStandardPrice()}$ - ${ticket.getLuxPrice()}$
                                </div>
                                <div class="price">
                                    ${ticket.getSaleStandardPrice()}$ - ${ticket.getSaleLuxPrice()}$
                                </div>
                            </c:if>
                            <c:if test="${(ticket.isSale()==null) or (!ticket.isSale())}">
                                <div class="price">
                                    ${ticket.getStandardPrice()}$ - ${ticket.getLuxPrice()}$
                                </div>
                            </c:if>
                            <a href="user/cruise?id=${ticket.getId()}">
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
            <c:if test="${latestTickets.size()<1}">
                <div class="no-info">
                    <fmt:message key="label.noResult"/>
                </div>
            </c:if>
        </div>


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

        <div class="start-bar">
            <a href="start" class="option">
                <fmt:message key="label.startVoyage"/>
            </a>
        </div>
    </body>
</html>
