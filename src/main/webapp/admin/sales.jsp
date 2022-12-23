<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${applicationScope['lang']}" scope="session"/>
<fmt:setBundle basename="messages"/>
<html lang="en">
    <head>
        <link rel="stylesheet" href="sales.css">
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
            <c:if test="${salesTickets.size()>0}">
                <c:forEach var="i" begin="${(param.page-1)*3}"
                           end="${salesTickets.size()<(param.page)*3?salesTickets.size()-1:(param.page-1)*3+2}"
                           varStatus="status1">
                    <div class="cruise">

                        <img src="../Ship_Pictures/ship_${salesTickets.get(i).getShip().getId()}/${salesTickets.get(i).getShip().getPicture().getName()}"
                             alt="" class="cruise-photo"/>
                        <div class="vertical-line">
                            <hr>
                        </div>
                        <div class="test">

                            <div class="text">
                                <c:forEach var="station" items="${salesTickets.get(i).getVoyageStations()}" varStatus="status">
                                    <fmt:message key="label.station"/>
                                    : ${status.count}
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
                                <c:out value="${salesTickets.get(i).getShip().getName()}"/>
                                <br/><br/>
                                <fmt:message key="label.departureDate"/>
                                :
                                <c:out value="${salesTickets.get(i).getVoyageStations().get(0).getDepartureDate().toString().replaceAll(':00.0','')}"/>
                                <br/><br/>
                                <fmt:message key="label.arrivalDate"/>
                                :
                                <c:out value="${salesTickets.get(i).getVoyageStations().get(salesTickets.get(i).getVoyageStations().size()-1).getArrivalDate().toString().replaceAll(':00.0','')}"/>
                                <br/>
                            </div>
                        </div>
                        <div class="vertical-line">
                            <hr>
                        </div>
                        <div class="more-info">
                            <c:if test="${salesTickets.get(i).isSale()}">
                                <div class="sale-price">
                                    ${salesTickets.get(i).getStandardPrice()}$ - ${salesTickets.get(i).getLuxPrice()}$
                                </div>
                                <div class="price">
                                    ${salesTickets.get(i).getSaleStandardPrice()}$ - ${salesTickets.get(i).getSaleLuxPrice()}$
                                </div>
                            </c:if>
                            <c:if test="${(salesTickets.get(i).isSale()==null) or (!salesTickets.get(i).isSale())}">
                                <div class="price">
                                    ${salesTickets.get(i).getStandardPrice()}$ - ${salesTickets.get(i).getLuxPrice()}$
                                </div>
                            </c:if>
                            <a href="cruise?id=${salesTickets.get(i).getId()}">
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
            <c:if test="${salesTickets.size()<1}">
                <div class="no-info">
                    <fmt:message key="label.noResult"/>
                </div>
            </c:if>
        </div>
        <c:if test="${salesTickets.size()>0}">
            <div class="pagination">
                <fmt:parseNumber var="intValue" integerOnly="true"
                                 type="number" value="${salesTickets.size()/3+0.9}"/>
                <div class="some">
                    <div class="pagination:container">
                        <c:if test="${param.page!=1}">
                            <a href="sales?page=${param.page-1}">
                                <div class="pagination:number arrow">
                                    <svg width="18" height="18">
                                        <use xlink:href="#left"/>
                                    </svg>
                                </div>
                            </a>
                        </c:if>
                        <c:if test="${param.page>4}">
                            <a href="sales?page=1">
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
                                <a href="sales?page=${j}">
                                    <div class="pagination:number">
                                        ${j}
                                    </div>
                                </a>
                            </c:if>
                            <c:if test="${param.page==j}">
                                <a href="sales?page=${j}">
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
                            <a href="sales?page=${intValue}">
                                <div class="pagination:number">
                                    ${intValue}
                                </div>
                            </a>
                        </c:if>

                        <c:if test="${param.page!=intValue}">
                            <div class="pagination:number arrow">
                                <a href="sales?page=${param.page+1}">
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
