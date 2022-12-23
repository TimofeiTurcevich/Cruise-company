import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.StationDAO;
import com.my.DB.DAO.TicketsDAO;
import com.my.DB.ShipDB;
import com.my.DB.StationDB;
import com.my.DB.TicketsDB;
import com.my.classes.BoughtTickets;
import com.my.classes.Voyage;
import com.my.servlets.AcceptServlet;
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
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class NeedToAcceptTest {
    private final List<BoughtTickets> boughtTickets = new ArrayList<>();

    @Test
    void someBoughtTest() throws SQLException, ServletException, IOException {
        BoughtTickets boughtTicket = new BoughtTickets();
        boughtTickets.add(boughtTicket);


        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        AcceptServlet acceptServlet = new AcceptServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher reqDisp = mock(RequestDispatcher.class);

        acceptServlet.ticketsDAO = ticketsDAO;
        acceptServlet.shipDAO = shipDAO;
        acceptServlet.stationDAO = stationDAO;
        acceptServlet.path = "";

        when(ticketsDAO.getAcceptTickets(shipDAO,stationDAO,"")).thenReturn(boughtTickets);
        when(req.getSession()).thenReturn(session);
        when(req.getRequestDispatcher("needToAccept.jsp?page=1")).thenReturn(reqDisp);

        acceptServlet.doGet(req,resp);

        verify(session).setAttribute("acceptTickets",boughtTickets);
        verify(reqDisp).forward(req,resp);
    }

    @Test
    void secondPageTest() throws SQLException, ServletException, IOException {
        BoughtTickets boughtTicket = new BoughtTickets();
        boughtTickets.add(boughtTicket);
        boughtTickets.add(boughtTicket);
        boughtTickets.add(boughtTicket);
        boughtTickets.add(boughtTicket);


        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        AcceptServlet acceptServlet = new AcceptServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher reqDisp = mock(RequestDispatcher.class);

        acceptServlet.ticketsDAO = ticketsDAO;
        acceptServlet.shipDAO = shipDAO;
        acceptServlet.stationDAO = stationDAO;
        acceptServlet.path = "";

        when(ticketsDAO.getAcceptTickets(shipDAO,stationDAO,"")).thenReturn(boughtTickets);
        when(req.getSession()).thenReturn(session);
        when(req.getParameter("page")).thenReturn("2");
        when(req.getRequestDispatcher("needToAccept.jsp")).thenReturn(reqDisp);

        acceptServlet.doGet(req,resp);

        verify(reqDisp).forward(req,resp);
    }

    @Test
    void invalidPageTest() throws SQLException, ServletException, IOException {
        BoughtTickets boughtTicket = new BoughtTickets();
        boughtTickets.add(boughtTicket);
        boughtTickets.add(boughtTicket);


        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        AcceptServlet acceptServlet = new AcceptServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        acceptServlet.ticketsDAO = ticketsDAO;
        acceptServlet.shipDAO = shipDAO;
        acceptServlet.stationDAO = stationDAO;
        acceptServlet.path = "";

        when(ticketsDAO.getAcceptTickets(shipDAO,stationDAO,"")).thenReturn(boughtTickets);
        when(req.getSession()).thenReturn(session);
        when(req.getParameter("page")).thenReturn("2");

        acceptServlet.doGet(req,resp);

        verify(resp).sendError(404,"This page("+req.getParameter("page")+") is not existing");
    }

    @Test
    void sqlException() throws SQLException, ServletException, IOException {
        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        AcceptServlet acceptServlet = new AcceptServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        acceptServlet.ticketsDAO = ticketsDAO;
        acceptServlet.shipDAO = shipDAO;
        acceptServlet.stationDAO = stationDAO;
        acceptServlet.path = "";

        when(ticketsDAO.getAcceptTickets(shipDAO,stationDAO,"")).thenThrow(new SQLException());

        acceptServlet.doGet(req,resp);

        verify(resp).sendError(500,"Something went wrong. Try again later");
    }
}
