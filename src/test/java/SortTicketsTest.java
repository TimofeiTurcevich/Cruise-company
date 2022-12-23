import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.StationDAO;
import com.my.DB.DAO.TicketsDAO;
import com.my.DB.ShipDB;
import com.my.DB.StationDB;
import com.my.DB.TicketsDB;
import com.my.classes.Voyage;
import com.my.servlets.SetSaleServlet;
import com.my.servlets.SortTicketsServlet;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class SortTicketsTest {
    private final List<Voyage> voyages = new ArrayList<>();

    @Test
    void sortTest() throws SQLException, ServletException, IOException {
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        ShipDAO shipDAO = mock(ShipDB.class);
        SortTicketsServlet sortTicketsServlet = new SortTicketsServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        sortTicketsServlet.path = "";
        sortTicketsServlet.ticketsDAO = ticketsDAO;
        sortTicketsServlet.shipDAO = shipDAO;
        sortTicketsServlet.stationDAO = stationDAO;

        when(req.getSession()).thenReturn(session);
        when(req.getParameter("priceMin")).thenReturn("500");
        when(req.getParameter("priceMax")).thenReturn("700");
        when(req.getParameter("startStationId")).thenReturn("1");
        when(req.getParameter("stationId")).thenReturn("3");
        when(req.getParameter("isFinal")).thenReturn("on");
        when(req.getParameter("startDate")).thenReturn("2022-10-26");
        when(req.getParameter("endDate")).thenReturn("2022-10-30");
        when(req.getParameter("sortBy")).thenReturn("standard_price");
        when(req.getParameter("voyageDuration")).thenReturn("4");
        when(ticketsDAO.getSort(
                "500","700","1","3","on","2022-10-26","2022-10-30","standard_price","4",stationDAO,shipDAO,""
        )).thenReturn(voyages);

        sortTicketsServlet.doPost(req,resp);

        verify(session).setAttribute("tickets",voyages);
        verify(resp).sendRedirect("tickets?page=1");
    }

    @Test
    void sqlException() throws SQLException, ServletException, IOException {
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        ShipDAO shipDAO = mock(ShipDB.class);
        SortTicketsServlet sortTicketsServlet = new SortTicketsServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        sortTicketsServlet.path = "";
        sortTicketsServlet.ticketsDAO = ticketsDAO;
        sortTicketsServlet.shipDAO = shipDAO;
        sortTicketsServlet.stationDAO = stationDAO;

        when(req.getSession()).thenReturn(session);
        when(req.getParameter("priceMin")).thenReturn("500");
        when(req.getParameter("priceMax")).thenReturn("700");
        when(req.getParameter("startStationId")).thenReturn("1");
        when(req.getParameter("stationId")).thenReturn("3");
        when(req.getParameter("isFinal")).thenReturn("on");
        when(req.getParameter("startDate")).thenReturn("2022-10-26");
        when(req.getParameter("endDate")).thenReturn("2022-10-30");
        when(req.getParameter("sortBy")).thenReturn("standard_price");
        when(req.getParameter("voyageDuration")).thenReturn("4");
        when(ticketsDAO.getSort(
                "500","700","1","3","on","2022-10-26","2022-10-30","standard_price","4",stationDAO,shipDAO,""
        )).thenThrow(new SQLException());

        sortTicketsServlet.doPost(req,resp);

        verify(resp).sendError(500,"Something went wrong. Try again later");
    }
}
