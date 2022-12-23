import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.StationDAO;
import com.my.DB.DAO.TicketsDAO;
import com.my.DB.ShipDB;
import com.my.DB.StationDB;
import com.my.DB.TicketsDB;
import com.my.classes.User;
import com.my.classes.Voyage;
import com.my.servlets.EditStationServlet;
import com.my.servlets.HomeServlet;
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

public class HomeTest {
    private final List<Voyage> voyages = new ArrayList<>();
    @Test
    void adminTest() throws ServletException, IOException {
        User user = new User.Builder()
                .setId(1)
                .setRole("admin")
                .build();

        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        HomeServlet homeServlet = new HomeServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        homeServlet.path = "";
        homeServlet.shipDAO = shipDAO;
        homeServlet.stationDAO = stationDAO;
        homeServlet.ticketsDAO = ticketsDAO;

        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);

        homeServlet.doGet(req,resp);

        verify(resp).sendRedirect("admin/needToAccept");
    }

    @Test
    void clientTest() throws ServletException, IOException, SQLException {
        User user = new User.Builder()
                .setId(1)
                .setRole("client")
                .build();

        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        HomeServlet homeServlet = new HomeServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

        homeServlet.path = "";
        homeServlet.shipDAO = shipDAO;
        homeServlet.stationDAO = stationDAO;
        homeServlet.ticketsDAO = ticketsDAO;

        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(req.getRequestDispatcher("index.jsp")).thenReturn(requestDispatcher);
        when(ticketsDAO.getLatestVoyages(shipDAO,stationDAO,"")).thenReturn(voyages);

        homeServlet.doGet(req,resp);

        verify(session).setAttribute("latestTickets",voyages);
        verify(requestDispatcher).forward(req,resp);
    }

    @Test
    void sqlException() throws ServletException, IOException, SQLException {
        User user = new User.Builder()
                .setId(1)
                .setRole("client")
                .build();

        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        HomeServlet homeServlet = new HomeServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        homeServlet.path = "";
        homeServlet.shipDAO = shipDAO;
        homeServlet.stationDAO = stationDAO;
        homeServlet.ticketsDAO = ticketsDAO;

        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(ticketsDAO.getLatestVoyages(shipDAO,stationDAO,"")).thenThrow(new SQLException());

        homeServlet.doGet(req,resp);

        verify(resp).sendError(500,"Something went wrong. Try again later");
    }
}
