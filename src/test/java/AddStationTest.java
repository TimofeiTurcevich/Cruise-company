import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.StationDAO;
import com.my.DB.DAO.TicketsDAO;
import com.my.DB.ShipDB;
import com.my.DB.StationDB;
import com.my.DB.TicketsDB;
import com.my.classes.Station;
import com.my.servlets.AcceptDocumentServlet;
import com.my.servlets.AddStationDBServlet;
import com.my.servlets.AddStationServlet;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class AddStationTest {
    private final List<Station> stations = new ArrayList<>();

    @Test
    void addTest() throws SQLException, ServletException, IOException {
        StationDAO stationDAO = mock(StationDB.class);
        AddStationServlet addStationServlet = new AddStationServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

        when(req.getSession()).thenReturn(session);
        when(stationDAO.getStations()).thenReturn(stations);
        when(req.getRequestDispatcher("addStation.jsp")).thenReturn(requestDispatcher);

        addStationServlet.stationDAO = stationDAO;

        addStationServlet.doGet(req,resp);

        verify(session).setAttribute("stations",stations);
        verify(requestDispatcher).forward(req,resp);
    }

    @Test
    void sqlException() throws SQLException, ServletException, IOException {
        StationDAO stationDAO = mock(StationDB.class);
        AddStationServlet addStationServlet = new AddStationServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        when(req.getSession()).thenReturn(session);
        when(stationDAO.getStations()).thenThrow(new SQLException());

        addStationServlet.stationDAO = stationDAO;

        addStationServlet.doGet(req,resp);

        verify(resp).sendError(500,"Something went wrong. Try again later");
    }
}
