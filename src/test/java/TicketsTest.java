import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.StationDAO;
import com.my.DB.DAO.TicketsDAO;
import com.my.DB.DAO.UserDAO;
import com.my.DB.ShipDB;
import com.my.DB.StationDB;
import com.my.DB.TicketsDB;
import com.my.DB.UserDB;
import com.my.classes.User;
import com.my.classes.Voyage;
import com.my.servlets.LoginServlet;
import com.my.servlets.TicketsServlet;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class TicketsTest {
    private final List<Voyage> voyages = new ArrayList<>();
    @Test
    void someTicketsTest() throws SQLException, ServletException, IOException {
        Voyage voyage = new Voyage();
        voyages.add(voyage);
        voyages.add(voyage);


        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        TicketsServlet ticketsServlet = new TicketsServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher reqDisp = mock(RequestDispatcher.class);

        ticketsServlet.ticketsDAO = ticketsDAO;
        ticketsServlet.shipDAO = shipDAO;
        ticketsServlet.stationDAO = stationDAO;
        ticketsServlet.path = "";

        when(stationDAO.getStations()).thenReturn(new ArrayList<>());
        when(ticketsDAO.getAllTickets("",shipDAO,stationDAO,"")).thenReturn(voyages);
        when(req.getSession()).thenReturn(session);
        when(req.getRequestDispatcher("tickets.jsp?page=1")).thenReturn(reqDisp);

        ticketsServlet.doGet(req,resp);

        verify(session).setAttribute("stations",new ArrayList<>());
        verify(session).setAttribute("tickets", voyages);
        verify(reqDisp).forward(req,resp);
    }

    @Test
    void secondPageTest() throws SQLException, ServletException, IOException {
        Voyage voyage = new Voyage();
        voyages.add(voyage);
        voyages.add(voyage);
        voyages.add(voyage);
        voyages.add(voyage);


        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        TicketsServlet ticketsServlet = new TicketsServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher reqDisp = mock(RequestDispatcher.class);

        ticketsServlet.ticketsDAO = ticketsDAO;
        ticketsServlet.shipDAO = shipDAO;
        ticketsServlet.stationDAO = stationDAO;
        ticketsServlet.path = "";

        when(stationDAO.getStations()).thenReturn(new ArrayList<>());
        when(ticketsDAO.getAllTickets("",shipDAO,stationDAO,"")).thenReturn(voyages);
        when(req.getSession()).thenReturn(session);
        when(req.getParameter("page")).thenReturn("2");
        when(req.getRequestDispatcher("tickets.jsp")).thenReturn(reqDisp);

        ticketsServlet.doGet(req,resp);

        verify(session).setAttribute("stations",new ArrayList<>());
        verify(reqDisp).forward(req,resp);
    }

    @Test
    void invalidPageTest() throws SQLException, ServletException, IOException {
        Voyage voyage = new Voyage();
        voyages.add(voyage);
        voyages.add(voyage);
        voyages.add(voyage);


        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        TicketsServlet ticketsServlet = new TicketsServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        ticketsServlet.ticketsDAO = ticketsDAO;
        ticketsServlet.shipDAO = shipDAO;
        ticketsServlet.stationDAO = stationDAO;
        ticketsServlet.path = "";

        when(stationDAO.getStations()).thenReturn(new ArrayList<>());
        when(ticketsDAO.getAllTickets("",shipDAO,stationDAO,"")).thenReturn(voyages);
        when(req.getSession()).thenReturn(session);
        when(req.getParameter("page")).thenReturn("2");

        ticketsServlet.doGet(req,resp);

        verify(session).setAttribute("stations",new ArrayList<>());
        verify(resp).sendError(404,"This page(" + req.getParameter("page") + ") is not existing");
    }

    @Test
    void sqlException() throws SQLException, ServletException, IOException {
        StationDAO stationDAO = mock(StationDB.class);
        TicketsServlet ticketsServlet = new TicketsServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        ticketsServlet.stationDAO = stationDAO;

        when(stationDAO.getStations()).thenThrow(new SQLException());
        when(req.getSession()).thenReturn(session);

        ticketsServlet.doGet(req,resp);

        verify(resp).sendError(500,"Something went wrong. Try again later");
    }
}
