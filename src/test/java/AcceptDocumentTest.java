import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.StationDAO;
import com.my.DB.DAO.TicketsDAO;
import com.my.DB.ShipDB;
import com.my.DB.StationDB;
import com.my.DB.TicketsDB;
import com.my.classes.BoughtTickets;
import com.my.classes.Ship;
import com.my.classes.Voyage;
import com.my.servlets.AcceptDocumentServlet;
import com.my.servlets.TicketsServlet;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class AcceptDocumentTest {
    private BoughtTickets boughtTickets;
    private Ship ship;
    private final Voyage voyage = new Voyage.Builder()
            .setBoughtStandard(0)
            .setBoughtLux(0)
            .setId(1)
            .build();

    @Test
    void acceptTest() throws SQLException, IOException, ServletException {
        ship = new Ship.Builder()
                .setId(1)
                .setLuxRooms(2)
                .setStandardRooms(2)
                .build();
        voyage.setShip(ship);
        boughtTickets = new BoughtTickets.Builder()
                .setId(1)
                .setType("lux")
                .setVoyage(voyage)
                .build();

        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        AcceptDocumentServlet acceptDocumentServlet = new AcceptDocumentServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        acceptDocumentServlet.ticketsDAO  = ticketsDAO;
        acceptDocumentServlet.path = "";
        acceptDocumentServlet.shipDAO = shipDAO;
        acceptDocumentServlet.stationDAO = stationDAO;

        when(req.getParameter("id")).thenReturn("1");
        when(ticketsDAO.getBoughtTicket(1,shipDAO,stationDAO,"")).thenReturn(boughtTickets);

        acceptDocumentServlet.doGet(req,resp);

        verify(resp).sendRedirect("needToAccept");
    }

    @Test
    void acceptStandard() throws SQLException, ServletException, IOException {
        ship = new Ship.Builder()
                .setId(1)
                .setLuxRooms(2)
                .setStandardRooms(2)
                .build();
        voyage.setShip(ship);
        boughtTickets = new BoughtTickets.Builder()
                .setId(1)
                .setType("standard")
                .setVoyage(voyage)
                .build();

        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        AcceptDocumentServlet acceptDocumentServlet = new AcceptDocumentServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        acceptDocumentServlet.ticketsDAO  = ticketsDAO;
        acceptDocumentServlet.path = "";
        acceptDocumentServlet.shipDAO = shipDAO;
        acceptDocumentServlet.stationDAO = stationDAO;

        when(req.getParameter("id")).thenReturn("1");
        when(ticketsDAO.getBoughtTicket(1,shipDAO,stationDAO,"")).thenReturn(boughtTickets);

        acceptDocumentServlet.doGet(req,resp);

        verify(resp).sendRedirect("needToAccept");
    }

    @Test
    void idNull() throws ServletException, IOException {
        AcceptDocumentServlet acceptDocumentServlet = new AcceptDocumentServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        when(req.getParameter("id")).thenReturn(null);

        acceptDocumentServlet.doGet(req,resp);

        verify(resp).sendError(404,"Such link is not existing. Check if you enter all parameters right");
    }

    @Test
    void allLuxBought() throws SQLException, ServletException, IOException {
        ship = new Ship.Builder()
                .setId(1)
                .setLuxRooms(1)
                .setStandardRooms(1)
                .build();
        voyage.setShip(ship);
        boughtTickets = new BoughtTickets.Builder()
                .setId(1)
                .setType("lux")
                .setVoyage(voyage)
                .build();

        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        AcceptDocumentServlet acceptDocumentServlet = new AcceptDocumentServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        acceptDocumentServlet.ticketsDAO  = ticketsDAO;
        acceptDocumentServlet.path = "";
        acceptDocumentServlet.shipDAO = shipDAO;
        acceptDocumentServlet.stationDAO = stationDAO;

        when(req.getParameter("id")).thenReturn("1");
        when(ticketsDAO.getBoughtTicket(1,shipDAO,stationDAO,"")).thenReturn(boughtTickets);

        acceptDocumentServlet.doGet(req,resp);

        verify(resp).sendRedirect("needToAccept");
    }

    @Test
    void allStandardBought() throws SQLException, ServletException, IOException {
        ship = new Ship.Builder()
                .setId(1)
                .setLuxRooms(1)
                .setStandardRooms(1)
                .build();
        voyage.setShip(ship);
        boughtTickets = new BoughtTickets.Builder()
                .setId(1)
                .setType("standard")
                .setVoyage(voyage)
                .build();

        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        AcceptDocumentServlet acceptDocumentServlet = new AcceptDocumentServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        acceptDocumentServlet.ticketsDAO  = ticketsDAO;
        acceptDocumentServlet.path = "";
        acceptDocumentServlet.shipDAO = shipDAO;
        acceptDocumentServlet.stationDAO = stationDAO;

        when(req.getParameter("id")).thenReturn("1");
        when(ticketsDAO.getBoughtTicket(1,shipDAO,stationDAO,"")).thenReturn(boughtTickets);

        acceptDocumentServlet.doGet(req,resp);

        verify(resp).sendRedirect("needToAccept");
    }

    @Test
    void sqlException() throws ServletException, IOException, SQLException {
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        AcceptDocumentServlet acceptDocumentServlet = new AcceptDocumentServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        acceptDocumentServlet.ticketsDAO  = ticketsDAO;

        when(req.getParameter("id")).thenReturn("1");
        doThrow(new SQLException()).when(ticketsDAO).setBoughtStatus(1,4);

        acceptDocumentServlet.doGet(req,resp);

        verify(resp).sendError(500,"Something went wrong. Try again later");
    }
}
