import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.StationDAO;
import com.my.DB.DAO.TicketsDAO;
import com.my.DB.ShipDB;
import com.my.DB.StationDB;
import com.my.DB.TicketsDB;
import com.my.classes.Ship;
import com.my.classes.Voyage;
import com.my.servlets.ChangeGeneralDBServlet;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class ChangeGeneralDBTest {

    @Test
    void changeTest() throws SQLException, ServletException, IOException {
        Ship ship  = new Ship.Builder()
                .setId(1)
                .build();
        Voyage voyage = new Voyage.Builder()
                .setId(1)
                .setStandardPrice(200)
                .setLuxPrice(400)
                .setShip(ship)
                .build();

        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        ChangeGeneralDBServlet changeGeneralServlet = new ChangeGeneralDBServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        changeGeneralServlet.path = "";
        changeGeneralServlet.shipDAO = shipDAO;
        changeGeneralServlet.stationDAO = stationDAO;
        changeGeneralServlet.ticketsDAO = ticketsDAO;

        when(ticketsDAO.getVoyage(voyage.getId(),shipDAO,stationDAO,"")).thenReturn(voyage);
        when(req.getParameter("standardPrice")).thenReturn("300");
        when(req.getParameter("luxPrice")).thenReturn("500");
        when(req.getParameter("shipId")).thenReturn("1");
        when(req.getParameter("id")).thenReturn("1");
        when(shipDAO.getShip(1,"")).thenReturn(ship);

        changeGeneralServlet.doPost(req,resp);

        verify(resp).sendRedirect("cruise?id=1");
    }

    @Test
    void errorTest() throws SQLException, ServletException, IOException {

        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        ChangeGeneralDBServlet changeGeneralServlet = new ChangeGeneralDBServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        changeGeneralServlet.path = "";
        changeGeneralServlet.shipDAO = shipDAO;
        changeGeneralServlet.stationDAO = stationDAO;
        changeGeneralServlet.ticketsDAO = ticketsDAO;

        when(ticketsDAO.getVoyage(1,shipDAO,stationDAO,"")).thenThrow(new SQLException());
        when(req.getParameter("id")).thenReturn("1");

        changeGeneralServlet.doPost(req,resp);

        verify(resp).sendError(500,"Something went wrong. Try again later");
    }

    @Test
    void idNull() throws ServletException, IOException {
        ChangeGeneralDBServlet changeGeneralServlet = new ChangeGeneralDBServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);


        when(req.getParameter("id")).thenReturn(null);

        changeGeneralServlet.doPost(req,resp);

        verify(resp).sendError(404,"Such link is not existing. Check if you enter all parameters right");
    }

    @Test
    void voyageNull() throws SQLException, ServletException, IOException {

        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        ChangeGeneralDBServlet changeGeneralServlet = new ChangeGeneralDBServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        changeGeneralServlet.path = "";
        changeGeneralServlet.shipDAO = shipDAO;
        changeGeneralServlet.stationDAO = stationDAO;
        changeGeneralServlet.ticketsDAO = ticketsDAO;

        when(ticketsDAO.getVoyage(1,shipDAO,stationDAO,"")).thenReturn(null);
        when(req.getParameter("id")).thenReturn("1");

        changeGeneralServlet.doPost(req,resp);

        verify(resp).sendError(500,"Something went wrong. Try again later");
    }
}
