import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.StationDAO;
import com.my.DB.DAO.TicketsDAO;
import com.my.DB.ShipDB;
import com.my.DB.StationDB;
import com.my.DB.TicketsDB;
import com.my.classes.Ship;
import com.my.classes.Voyage;
import com.my.servlets.NewCruiseCreateServlet;
import com.my.servlets.SortTicketsServlet;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class NewCruiseCreateTest {
    private final Voyage voyage = new Voyage();
    private final Ship ship = new Ship();

    @Test
    void createTest() throws SQLException, ServletException, IOException {
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        ShipDAO shipDAO = mock(ShipDB.class);
        NewCruiseCreateServlet newCruiseCreateServlet = new NewCruiseCreateServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        newCruiseCreateServlet.path = "";
        newCruiseCreateServlet.shipDAO = shipDAO;
        newCruiseCreateServlet.stationDAO = stationDAO;
        newCruiseCreateServlet.ticketsDAO = ticketsDAO;
        newCruiseCreateServlet.voyage = voyage;

        when(req.getParameter("departureDate1")).thenReturn("2022-10-26 00:00");
        when(req.getParameter("stationId1")).thenReturn("1");
        when(req.getParameter("stationNumbers")).thenReturn("3");
        when(req.getParameter("arrivalDate2")).thenReturn("2022-10-27 00:00");
        when(req.getParameter("departureDate2")).thenReturn("2022-10-28 00:00");
        when(req.getParameter("stationId2")).thenReturn("2");
        when(req.getParameter("arrivalDate3")).thenReturn("2022-10-28 00:00");
        when(req.getParameter("stationId3")).thenReturn("3");
        when(req.getParameter("shipId")).thenReturn("1");
        when(req.getParameter("standardPrice")).thenReturn("320");
        when(req.getParameter("luxPrice")).thenReturn("550");
        when(shipDAO.getShip(1,"")).thenReturn(ship);
        when(ticketsDAO.insertVoyage(voyage,stationDAO)).thenReturn(1L);

        newCruiseCreateServlet.doPost(req,resp);

        verify(resp).sendRedirect("cruise?id=1");
    }

    @Test
    void sqlException() throws SQLException, ServletException, IOException {
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        ShipDAO shipDAO = mock(ShipDB.class);
        NewCruiseCreateServlet newCruiseCreateServlet = new NewCruiseCreateServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        newCruiseCreateServlet.path = "";
        newCruiseCreateServlet.shipDAO = shipDAO;
        newCruiseCreateServlet.stationDAO = stationDAO;
        newCruiseCreateServlet.ticketsDAO = ticketsDAO;
        newCruiseCreateServlet.voyage = voyage;

        when(req.getParameter("departureDate1")).thenReturn("2022-10-26 00:00");
        when(req.getParameter("stationId1")).thenReturn("1");
        when(req.getParameter("stationNumbers")).thenReturn("3");
        when(req.getParameter("arrivalDate2")).thenReturn("2022-10-27 00:00");
        when(req.getParameter("departureDate2")).thenReturn("2022-10-28 00:00");
        when(req.getParameter("stationId2")).thenReturn("2");
        when(req.getParameter("arrivalDate3")).thenReturn("2022-10-28 00:00");
        when(req.getParameter("stationId3")).thenReturn("3");
        when(req.getParameter("shipId")).thenReturn("1");
        when(req.getParameter("standardPrice")).thenReturn("320");
        when(req.getParameter("luxPrice")).thenReturn("550");
        when(shipDAO.getShip(1,"")).thenReturn(ship);
        when(ticketsDAO.insertVoyage(voyage,stationDAO)).thenThrow(new SQLException());

        newCruiseCreateServlet.doPost(req,resp);

        verify(resp).sendError(500,"Something went wrong. Check if ship dont have voyage on this time or try again later");
    }
}
