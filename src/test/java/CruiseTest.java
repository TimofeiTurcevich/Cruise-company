import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.StationDAO;
import com.my.DB.DAO.TicketsDAO;
import com.my.DB.ShipDB;
import com.my.DB.StationDB;
import com.my.DB.TicketsDB;
import com.my.DB.UserDB;
import com.my.classes.User;
import com.my.classes.Voyage;
import com.my.servlets.CruiseServlet;
import com.my.servlets.SalesServlet;
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

public class CruiseTest {
    private User user;
    private final List<Voyage> favorite = new ArrayList<>();
    private final Voyage voyage = new Voyage.Builder()
            .setId(1)
            .build();

    @Test
    void cruiseNotInFavoriteTest() throws SQLException, ServletException, IOException {
        user = new User.Builder()
                .setId(1)
                .setRole("client")
                .build();

        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        CruiseServlet cruiseServlet = new CruiseServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher reqDisp = mock(RequestDispatcher.class);

        cruiseServlet.path = "";
        cruiseServlet.ticketsDAO = ticketsDAO;
        cruiseServlet.shipDAO = shipDAO;
        cruiseServlet.stationDAO = stationDAO;

        when(req.getParameter("id")).thenReturn("1");
        when(session.getAttribute("user")).thenReturn(user);
        when(ticketsDAO.getVoyage(Long.parseLong(req.getParameter("id")),shipDAO,stationDAO,"")).thenReturn(voyage);
        when(ticketsDAO.getFavoriteVoyages(user.getId(),shipDAO,stationDAO,"")).thenReturn(favorite);
        when(req.getRequestDispatcher("cruise.jsp")).thenReturn(reqDisp);
        when(req.getSession()).thenReturn(session);

        cruiseServlet.doGet(req,resp);

        verify(session).setAttribute("voyage",voyage);
        verify(session).setAttribute("favorite",false);
        verify(reqDisp).forward(req,resp);
    }

    @Test
    void cruiseInFavoriteTest() throws SQLException, ServletException, IOException {
        favorite.add(voyage);
        user = new User.Builder()
                .setId(1)
                .setRole("client")
                .build();

        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        CruiseServlet cruiseServlet = new CruiseServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher reqDisp = mock(RequestDispatcher.class);

        cruiseServlet.path = "";
        cruiseServlet.ticketsDAO = ticketsDAO;
        cruiseServlet.shipDAO = shipDAO;
        cruiseServlet.stationDAO = stationDAO;

        when(req.getParameter("id")).thenReturn("1");
        when(session.getAttribute("user")).thenReturn(user);
        when(ticketsDAO.getVoyage(Long.parseLong(req.getParameter("id")),shipDAO,stationDAO,"")).thenReturn(voyage);
        when(ticketsDAO.getFavoriteVoyages(user.getId(),shipDAO,stationDAO,"")).thenReturn(favorite);
        when(req.getRequestDispatcher("cruise.jsp")).thenReturn(reqDisp);
        when(req.getSession()).thenReturn(session);

        cruiseServlet.doGet(req,resp);

        verify(session).setAttribute("voyage",voyage);
        verify(session).setAttribute("favorite",true);
        verify(reqDisp).forward(req,resp);
    }

    @Test
    void cruiseAdminTest() throws SQLException, ServletException, IOException {
        favorite.add(voyage);
        user = new User.Builder()
                .setId(1)
                .setRole("admin")
                .build();

        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        CruiseServlet cruiseServlet = new CruiseServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher reqDisp = mock(RequestDispatcher.class);

        cruiseServlet.path = "";
        cruiseServlet.ticketsDAO = ticketsDAO;
        cruiseServlet.shipDAO = shipDAO;
        cruiseServlet.stationDAO = stationDAO;

        when(req.getParameter("id")).thenReturn("1");
        when(session.getAttribute("user")).thenReturn(user);
        when(ticketsDAO.getVoyage(Long.parseLong(req.getParameter("id")),shipDAO,stationDAO,"")).thenReturn(voyage);
        when(req.getRequestDispatcher("cruise.jsp")).thenReturn(reqDisp);
        when(req.getSession()).thenReturn(session);

        cruiseServlet.doGet(req,resp);

        verify(session).setAttribute("voyage",voyage);
        verify(reqDisp).forward(req,resp);
    }

    @Test
    void idNull() throws ServletException, IOException {
        CruiseServlet cruiseServlet = new CruiseServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        when(req.getParameter("id")).thenReturn(null);

        cruiseServlet.doGet(req,resp);

        verify(resp).sendError(404,"Such link is not existing. Check if you enter all parameters right");
    }

    @Test
    void voyageNull() throws SQLException, ServletException, IOException {
        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        CruiseServlet cruiseServlet = new CruiseServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        cruiseServlet.path = "";
        cruiseServlet.ticketsDAO = ticketsDAO;
        cruiseServlet.shipDAO = shipDAO;
        cruiseServlet.stationDAO = stationDAO;

        when(req.getParameter("id")).thenReturn("1");
        when(session.getAttribute("user")).thenReturn(user);
        when(ticketsDAO.getVoyage(Long.parseLong(req.getParameter("id")),shipDAO,stationDAO,"")).thenReturn(null);
        when(req.getSession()).thenReturn(session);

        cruiseServlet.doGet(req,resp);

        verify(resp).sendError(500,"Something went wrong. Try again later");
    }
}
