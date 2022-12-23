import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.StationDAO;
import com.my.DB.DAO.TicketsDAO;
import com.my.DB.ShipDB;
import com.my.DB.StationDB;
import com.my.DB.TicketsDB;
import com.my.classes.Voyage;
import com.my.classes.VoyageStation;
import com.my.servlets.DeleteCruiseServlet;
import com.my.servlets.DeleteStationServlet;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class DeleteStationTest {
    private final VoyageStation voyageStation = new VoyageStation();
    private final Voyage voyage = new Voyage.Builder()
            .setId(1)
            .setVoyageStations(new ArrayList<>())
            .build();

    @Test
    void deleteTest() throws SQLException, ServletException, IOException {
        voyage.getVoyageStations().add(voyageStation);

        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        DeleteStationServlet deleteStationServlet = new DeleteStationServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        deleteStationServlet.stationDAO = stationDAO;
        deleteStationServlet.path = "";
        deleteStationServlet.shipDAO = shipDAO;
        deleteStationServlet.ticketsDAO = ticketsDAO;

        when(req.getParameter("id")).thenReturn("1");
        when(req.getParameter("stationPosition")).thenReturn("1");
        when(ticketsDAO.getVoyage(1,shipDAO,stationDAO,"")).thenReturn(voyage);

        deleteStationServlet.doPost(req,resp);

        verify(resp).sendRedirect("cruise?id=1");
    }

    @Test
    void idNull() throws ServletException, IOException {
        DeleteStationServlet deleteStationServlet = new DeleteStationServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        when(req.getParameter("id")).thenReturn(null);

        deleteStationServlet.doPost(req,resp);

        verify(resp).sendError(404,"Such link is not existing. Check if you enter all parameters right");
    }

    @Test
    void voyageNull() throws SQLException, ServletException, IOException {
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        DeleteStationServlet deleteStationServlet = new DeleteStationServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        deleteStationServlet.stationDAO = stationDAO;
        deleteStationServlet.path = "";
        deleteStationServlet.shipDAO = shipDAO;
        deleteStationServlet.ticketsDAO = ticketsDAO;

        when(req.getParameter("id")).thenReturn("1");
        when(req.getParameter("stationPosition")).thenReturn("1");
        when(ticketsDAO.getVoyage(1,shipDAO,stationDAO,"")).thenReturn(null);

        deleteStationServlet.doPost(req,resp);

        verify(resp).sendError(500,"Something went wrong. Try again later");
    }
}
