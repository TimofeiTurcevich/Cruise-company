import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.StationDAO;
import com.my.DB.DAO.TicketsDAO;
import com.my.DB.ShipDB;
import com.my.DB.StationDB;
import com.my.DB.TicketsDB;
import com.my.classes.BoughtTickets;
import com.my.classes.Voyage;
import com.my.servlets.CancelServlet;
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

public class CancelTest {
    private BoughtTickets boughtTickets;
    private final Voyage voyage = new Voyage.Builder()
            .setId(1)
            .setBoughtLux(1)
            .setBoughtStandard(1)
            .build();

    @Test
    void cancelTest() throws SQLException, ServletException, IOException {
        boughtTickets = new BoughtTickets.Builder()
                .setId(1)
                .setStatus("Awaiting documents")
                .setVoyage(voyage)
                .build();

        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        CancelServlet cancelServlet = new CancelServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        cancelServlet.ticketsDAO = ticketsDAO;
        cancelServlet.path = "";
        cancelServlet.shipDAO = shipDAO;
        cancelServlet.stationDAO = stationDAO;

        when(ticketsDAO.getBoughtTicket(1,shipDAO,stationDAO,"")).thenReturn(boughtTickets);
        when(req.getParameter("id")).thenReturn("1");

        cancelServlet.doGet(req,resp);

        verify(resp).sendRedirect("boughtTickets");
    }

    @Test
    void acceptedTicketCancelTest() throws SQLException, ServletException, IOException {
        boughtTickets = new BoughtTickets.Builder()
                .setId(1)
                .setStatus("Accepted")
                .setVoyage(voyage)
                .setType("lux")
                .build();

        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        CancelServlet cancelServlet = new CancelServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        cancelServlet.ticketsDAO = ticketsDAO;
        cancelServlet.path = "";
        cancelServlet.shipDAO = shipDAO;
        cancelServlet.stationDAO = stationDAO;

        when(ticketsDAO.getBoughtTicket(1,shipDAO,stationDAO,"")).thenReturn(boughtTickets);
        when(req.getParameter("id")).thenReturn("1");

        cancelServlet.doGet(req,resp);

        verify(resp).sendRedirect("boughtTickets");
    }

    @Test
    void idNull() throws ServletException, IOException {
        CancelServlet cancelServlet = new CancelServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        when(req.getParameter("id")).thenReturn(null);

        cancelServlet.doGet(req,resp);

        verify(resp).sendError(404,"Such link is not existing. Check if you enter all parameters right");
    }

    @Test
    void ticketsNull() throws SQLException, ServletException, IOException {
        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        CancelServlet cancelServlet = new CancelServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        cancelServlet.ticketsDAO = ticketsDAO;
        cancelServlet.path = "";
        cancelServlet.shipDAO = shipDAO;
        cancelServlet.stationDAO = stationDAO;

        when(ticketsDAO.getBoughtTicket(1,shipDAO,stationDAO,"")).thenReturn(null);
        when(req.getParameter("id")).thenReturn("1");

        cancelServlet.doGet(req,resp);

        verify(resp).sendError(500,"Something went wrong. Try again later");
    }

    @Test
    void standardTicket() throws SQLException, ServletException, IOException {
        boughtTickets = new BoughtTickets.Builder()
                .setId(1)
                .setStatus("Accepted")
                .setVoyage(voyage)
                .setType("standard")
                .build();

        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        CancelServlet cancelServlet = new CancelServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        cancelServlet.ticketsDAO = ticketsDAO;
        cancelServlet.path = "";
        cancelServlet.shipDAO = shipDAO;
        cancelServlet.stationDAO = stationDAO;

        when(ticketsDAO.getBoughtTicket(1,shipDAO,stationDAO,"")).thenReturn(boughtTickets);
        when(req.getParameter("id")).thenReturn("1");

        cancelServlet.doGet(req,resp);

        verify(resp).sendRedirect("boughtTickets");
    }
}
