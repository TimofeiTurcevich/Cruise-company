import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.StationDAO;
import com.my.DB.DAO.TicketsDAO;
import com.my.DB.ShipDB;
import com.my.DB.StationDB;
import com.my.DB.TicketsDB;
import com.my.classes.Ship;
import com.my.classes.Station;
import com.my.servlets.HomeServlet;
import com.my.servlets.NewCruiseServlet;
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

public class NewCruiseTest {
    private final List<Station> stations = new ArrayList<>();
    private final List<Ship> ships = new ArrayList<>();

    @Test
    void newCruiseTest() throws SQLException, ServletException, IOException {
        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        NewCruiseServlet newCruiseServlet = new NewCruiseServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

        newCruiseServlet.path = "";
        newCruiseServlet.shipDAO = shipDAO;
        newCruiseServlet.stationDAO = stationDAO;

        when(req.getSession()).thenReturn(session);
        when(shipDAO.getShips("")).thenReturn(ships);
        when(stationDAO.getStations()).thenReturn(stations);
        when(req.getRequestDispatcher("addNewCruiseGeneral.jsp")).thenReturn(requestDispatcher);

        newCruiseServlet.doGet(req,resp);

        verify(session).setAttribute("ships",ships);
        verify(session).setAttribute("stations",stations);
        verify(requestDispatcher).forward(req,resp);
    }

    @Test
    void sqlException() throws SQLException, ServletException, IOException {
        ShipDAO shipDAO = mock(ShipDB.class);
        NewCruiseServlet newCruiseServlet = new NewCruiseServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

        newCruiseServlet.path = "";
        newCruiseServlet.shipDAO = shipDAO;

        when(req.getSession()).thenReturn(session);
        when(shipDAO.getShips("")).thenThrow(new SQLException());

        newCruiseServlet.doGet(req,resp);

        verify(resp).sendError(500,"Something went wrong. Try again later");
    }
}
