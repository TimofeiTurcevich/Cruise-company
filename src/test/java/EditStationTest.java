import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.StationDAO;
import com.my.DB.DAO.TicketsDAO;
import com.my.DB.ShipDB;
import com.my.DB.StationDB;
import com.my.DB.TicketsDB;
import com.my.classes.Voyage;
import com.my.classes.VoyageStation;
import com.my.servlets.DocumentServlet;
import com.my.servlets.EditStationServlet;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class EditStationTest {
    private final VoyageStation voyageStation = new VoyageStation();
    private final Voyage voyage = new Voyage.Builder()
            .setId(1)
            .setVoyageStations(new ArrayList<>())
            .build();
    @Test
    void editTest() throws SQLException, ServletException, IOException {
        voyage.getVoyageStations().add(voyageStation);

        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        EditStationServlet editStationServlet = new EditStationServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        editStationServlet.stationDAO = stationDAO;
        editStationServlet.shipDAO = shipDAO;
        editStationServlet.path = "";
        editStationServlet.ticketsDAO = ticketsDAO;

        when(req.getParameter("id")).thenReturn("1");
        when(ticketsDAO.getVoyage(1,shipDAO,stationDAO,"")).thenReturn(voyage);
        when(req.getParameter("arrivalDate")).thenReturn("2022-10-26 00:00");
        when(req.getParameter("departureDate")).thenReturn("2022-10-26 00:00");
        when(req.getParameter("position")).thenReturn("1");

        editStationServlet.doPost(req,resp);

        verify(resp).sendRedirect("cruise?id=1");
    }

    @Test
    void idNull() throws ServletException, IOException {
        EditStationServlet editStationServlet = new EditStationServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        when(req.getParameter("id")).thenReturn(null);

        editStationServlet.doPost(req,resp);

        verify(resp).sendError(404,"Such link is not existing. Check if you enter all parameters right");
    }

    @Test
    void voyageNull() throws SQLException, ServletException, IOException {
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        EditStationServlet editStationServlet = new EditStationServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        editStationServlet.stationDAO = stationDAO;
        editStationServlet.shipDAO = shipDAO;
        editStationServlet.path = "";
        editStationServlet.ticketsDAO = ticketsDAO;

        when(req.getParameter("id")).thenReturn("1");
        when(ticketsDAO.getVoyage(1,shipDAO,stationDAO,"")).thenReturn(null);
        when(req.getParameter("arrivalDate")).thenReturn("2022-10-26 00:00");
        when(req.getParameter("departureDate")).thenReturn("2022-10-26 00:00");
        when(req.getParameter("position")).thenReturn("1");

        editStationServlet.doPost(req,resp);

        verify(resp).sendError(500,"Something went wrong. Try again later");
    }
}
