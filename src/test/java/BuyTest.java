import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.StationDAO;
import com.my.DB.DAO.TicketsDAO;
import com.my.DB.ShipDB;
import com.my.DB.StationDB;
import com.my.DB.TicketsDB;
import com.my.classes.User;
import com.my.servlets.AddStationDBServlet;
import com.my.servlets.BuyServlet;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class BuyTest {
    private final User user = new User.Builder()
            .setId(1)
            .build();

    @Test
    void buyTest() throws ServletException, IOException {
        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        BuyServlet buyServlet = new BuyServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        buyServlet.path = "";
        buyServlet.shipDAO = shipDAO;
        buyServlet.stationDAO = stationDAO;
        buyServlet.ticketsDAO = ticketsDAO;

        when(req.getParameter("id")).thenReturn("1");
        when(req.getParameter("type")).thenReturn("lux");
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(req.getParameter("child")).thenReturn("1");
        when(req.getParameter("adult")).thenReturn("1");

        buyServlet.doPost(req,resp);

        verify(resp).sendRedirect("boughtTickets");
    }

    @Test
    void idNull() throws ServletException, IOException {
        BuyServlet buyServlet = new BuyServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        when(req.getParameter("id")).thenReturn(null);

        buyServlet.doPost(req,resp);

        verify(resp).sendError(404,"Such link is not existing. Check if you enter all parameters right");
    }

    @Test
    void sqlException() throws ServletException, IOException, SQLException {
        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        BuyServlet buyServlet = new BuyServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        buyServlet.path = "";
        buyServlet.shipDAO = shipDAO;
        buyServlet.stationDAO = stationDAO;
        buyServlet.ticketsDAO = ticketsDAO;

        when(req.getParameter("id")).thenReturn("1");
        when(req.getParameter("type")).thenReturn("lux");
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(req.getParameter("child")).thenReturn("1");
        when(req.getParameter("adult")).thenReturn("1");
        doThrow(new SQLException()).when(ticketsDAO).insertBoughtTicket(1,1,"lux",1,1,shipDAO,stationDAO,"");

        buyServlet.doPost(req,resp);

        verify(resp).sendError(500,"Something went wrong. Try again later");
    }
}
