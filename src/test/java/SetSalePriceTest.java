import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.StationDAO;
import com.my.DB.DAO.TicketsDAO;
import com.my.DB.ShipDB;
import com.my.DB.StationDB;
import com.my.DB.TicketsDB;
import com.my.classes.Voyage;
import com.my.servlets.RemoveFavoriteServlet;
import com.my.servlets.SetSaleServlet;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class SetSalePriceTest {
    private final Voyage voyage = new Voyage.Builder()
            .setSale(false)
            .setSaleLuxPrice(0)
            .setSaleStandardPrice(0)
            .build();

    @Test
    void setTest() throws SQLException, ServletException, IOException {
        voyage.setId(1);

        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        ShipDAO shipDAO = mock(ShipDB.class);
        SetSaleServlet setSaleServlet = new SetSaleServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        setSaleServlet.ticketsDAO = ticketsDAO;
        setSaleServlet.path = "";
        setSaleServlet.shipDAO = shipDAO;
        setSaleServlet.stationDAO = stationDAO;

        when(req.getParameter("id")).thenReturn("1");
        when(ticketsDAO.getVoyage(1,shipDAO,stationDAO,"")).thenReturn(voyage);
        when(req.getParameter("saleStandardPrice")).thenReturn("200");
        when(req.getParameter("saleLuxPrice")).thenReturn("400");

        setSaleServlet.doPost(req,resp);

        verify(resp).sendRedirect("cruise?id=1");
    }

    @Test
    void idNull() throws SQLException, ServletException, IOException {
        SetSaleServlet setSaleServlet = new SetSaleServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        when(req.getParameter("id")).thenReturn(null);

        setSaleServlet.doPost(req,resp);

        verify(resp).sendError(404,"Such link is not existing. Check if you enter all parameters right");
    }

    @Test
    void voyageNull() throws SQLException, ServletException, IOException {
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        ShipDAO shipDAO = mock(ShipDB.class);
        SetSaleServlet setSaleServlet = new SetSaleServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        setSaleServlet.ticketsDAO = ticketsDAO;
        setSaleServlet.path = "";
        setSaleServlet.shipDAO = shipDAO;
        setSaleServlet.stationDAO = stationDAO;

        when(req.getParameter("id")).thenReturn("1");
        when(ticketsDAO.getVoyage(1,shipDAO,stationDAO,"")).thenReturn(null);

        setSaleServlet.doPost(req,resp);

        verify(resp).sendError(500,"Something went wrong. Try again later");
    }
}
