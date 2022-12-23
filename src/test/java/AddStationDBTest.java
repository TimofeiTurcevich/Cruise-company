import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.StationDAO;
import com.my.DB.DAO.TicketsDAO;
import com.my.DB.ShipDB;
import com.my.DB.StationDB;
import com.my.DB.TicketsDB;
import com.my.classes.Voyage;
import com.my.servlets.AcceptDocumentServlet;
import com.my.servlets.AddStationDBServlet;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class AddStationDBTest {
    private final Voyage voyage = new Voyage.Builder()
            .setId(1)
            .setVoyageStations(new ArrayList<>())
            .build();

    @Test
    void addTest() throws SQLException, ServletException, IOException {
        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        AddStationDBServlet addStationDBServlet = new AddStationDBServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        addStationDBServlet.stationDAO = stationDAO;
        addStationDBServlet.ticketsDAO = ticketsDAO;
        addStationDBServlet.path = "";
        addStationDBServlet.shipDAO = shipDAO;

        when(req.getParameter("position")).thenReturn("1");
        when(req.getParameter("arrivalDate")).thenReturn("2022-10-26 00:00");
        when(req.getParameter("departureDate")).thenReturn("2022-10-26 00:00");
        when(req.getParameter("stationId")).thenReturn("1");
        when(req.getParameter("id")).thenReturn("1");
        when(ticketsDAO.getVoyage(1,shipDAO,stationDAO,"")).thenReturn(voyage);

        addStationDBServlet.doPost(req,resp);

        verify(resp).sendRedirect("cruise?id=1");
    }

    @Test
    void voyageNull() throws SQLException, ServletException, IOException {
        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        AddStationDBServlet addStationDBServlet = new AddStationDBServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        addStationDBServlet.stationDAO = stationDAO;
        addStationDBServlet.ticketsDAO = ticketsDAO;
        addStationDBServlet.path = "";
        addStationDBServlet.shipDAO = shipDAO;

        when(req.getParameter("position")).thenReturn("1");
        when(req.getParameter("arrivalDate")).thenReturn("2022-10-26 00:00");
        when(req.getParameter("departureDate")).thenReturn("2022-10-26 00:00");
        when(req.getParameter("stationId")).thenReturn("1");
        when(req.getParameter("id")).thenReturn("1");
        when(ticketsDAO.getVoyage(1,shipDAO,stationDAO,"")).thenReturn(null);

        addStationDBServlet.doPost(req,resp);

        verify(resp).sendError(500,"Something went wrong. Try again later");
    }
}
