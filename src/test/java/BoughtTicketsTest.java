import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.StationDAO;
import com.my.DB.DAO.TicketsDAO;
import com.my.DB.ShipDB;
import com.my.DB.StationDB;
import com.my.DB.TicketsDB;
import com.my.classes.BoughtTickets;
import com.my.classes.User;
import com.my.servlets.AcceptServlet;
import com.my.servlets.BoughtTicketsServlet;
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

public class BoughtTicketsTest {
    private final List<BoughtTickets> boughtTickets = new ArrayList<>();
    private final User user = new User();

    @Test
    void someBoughtTest() throws SQLException, ServletException, IOException {
        BoughtTickets boughtTicket = new BoughtTickets();
        boughtTickets.add(boughtTicket);


        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        BoughtTicketsServlet boughtTicketsServlet = new BoughtTicketsServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher reqDisp = mock(RequestDispatcher.class);

        boughtTicketsServlet.ticketsDAO = ticketsDAO;
        boughtTicketsServlet.shipDAO = shipDAO;
        boughtTicketsServlet.stationDAO = stationDAO;
        boughtTicketsServlet.path = "";

        when(ticketsDAO.getBoughtTickets(user.getId(),shipDAO,stationDAO,"")).thenReturn(boughtTickets);
        when(req.getSession()).thenReturn(session);
        when(req.getSession().getAttribute("user")).thenReturn(user);
        when(req.getRequestDispatcher("boughtTickets.jsp?page=1")).thenReturn(reqDisp);

        boughtTicketsServlet.doGet(req,resp);

        verify(session).setAttribute("boughtTickets",boughtTickets);
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
        BoughtTicketsServlet boughtTicketsServlet = new BoughtTicketsServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher reqDisp = mock(RequestDispatcher.class);

        boughtTicketsServlet.ticketsDAO = ticketsDAO;
        boughtTicketsServlet.shipDAO = shipDAO;
        boughtTicketsServlet.stationDAO = stationDAO;
        boughtTicketsServlet.path = "";

        when(ticketsDAO.getBoughtTickets(user.getId(),shipDAO,stationDAO,"")).thenReturn(boughtTickets);
        when(req.getSession()).thenReturn(session);
        when(req.getParameter("page")).thenReturn("2");
        when(req.getSession().getAttribute("user")).thenReturn(user);
        when(req.getRequestDispatcher("boughtTickets.jsp")).thenReturn(reqDisp);

        boughtTicketsServlet.doGet(req,resp);

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
        BoughtTicketsServlet boughtTicketsServlet = new BoughtTicketsServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher reqDisp = mock(RequestDispatcher.class);

        boughtTicketsServlet.ticketsDAO = ticketsDAO;
        boughtTicketsServlet.shipDAO = shipDAO;
        boughtTicketsServlet.stationDAO = stationDAO;
        boughtTicketsServlet.path = "";

        when(ticketsDAO.getBoughtTickets(user.getId(),shipDAO,stationDAO,"")).thenReturn(boughtTickets);
        when(req.getSession()).thenReturn(session);
        when(req.getParameter("page")).thenReturn("2");
        when(req.getSession().getAttribute("user")).thenReturn(user);

        boughtTicketsServlet.doGet(req,resp);

        verify(resp).sendError(404,"This page("+req.getParameter("page")+") is not existing");
    }

    @Test
    void sqlError() throws SQLException, ServletException, IOException {
        BoughtTickets boughtTicket = new BoughtTickets();
        boughtTickets.add(boughtTicket);


        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        BoughtTicketsServlet boughtTicketsServlet = new BoughtTicketsServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        boughtTicketsServlet.ticketsDAO = ticketsDAO;
        boughtTicketsServlet.shipDAO = shipDAO;
        boughtTicketsServlet.stationDAO = stationDAO;
        boughtTicketsServlet.path = "";

        when(ticketsDAO.getBoughtTickets(user.getId(),shipDAO,stationDAO,"")).thenThrow(new SQLException());
        when(req.getSession()).thenReturn(session);
        when(req.getSession().getAttribute("user")).thenReturn(user);

        boughtTicketsServlet.doGet(req,resp);

        verify(resp).sendError(500,"Something went wrong. Try again later");
    }
}
