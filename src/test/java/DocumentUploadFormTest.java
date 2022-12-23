import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.StationDAO;
import com.my.DB.DAO.TicketsDAO;
import com.my.DB.ShipDB;
import com.my.DB.StationDB;
import com.my.DB.TicketsDB;
import com.my.classes.BoughtTickets;
import com.my.servlets.DocumentUploadServlet;
import com.my.servlets.TicketsServlet;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class DocumentUploadFormTest {
    private BoughtTickets boughtTickets;

    @Test
    void formTest() throws SQLException, ServletException, IOException {
        boughtTickets = new BoughtTickets.Builder()
                .setId(1)
                .setTicketsCount(2)
                .build();

        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        DocumentUploadServlet documentUploadServlet = new DocumentUploadServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        RequestDispatcher reqDisp = mock(RequestDispatcher.class);

        documentUploadServlet.ticketsDAO = ticketsDAO;
        documentUploadServlet.shipDAO = shipDAO;
        documentUploadServlet.stationDAO = stationDAO;
        documentUploadServlet.path = "";

        when(req.getParameter("id")).thenReturn("1");
        when(ticketsDAO.getBoughtTicket(1,shipDAO,stationDAO,"")).thenReturn(boughtTickets);
        when(req.getRequestDispatcher("buyForm.jsp?count=2")).thenReturn(reqDisp);

        documentUploadServlet.doGet(req,resp);

        verify(reqDisp).forward(req,resp);
    }

    @Test
    void noTicketTest() throws SQLException, ServletException, IOException {
        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        DocumentUploadServlet documentUploadServlet = new DocumentUploadServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        RequestDispatcher reqDisp = mock(RequestDispatcher.class);

        documentUploadServlet.ticketsDAO = ticketsDAO;
        documentUploadServlet.shipDAO = shipDAO;
        documentUploadServlet.stationDAO = stationDAO;
        documentUploadServlet.path = "";

        when(req.getParameter("id")).thenReturn("1");
        when(ticketsDAO.getBoughtTicket(1,shipDAO,stationDAO,"")).thenReturn(boughtTickets);

        documentUploadServlet.doGet(req,resp);

        verify(resp).sendError(500,"Something went wrong. Try again later");
    }

    @Test
    void noIdTest() throws SQLException, ServletException, IOException {
        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        DocumentUploadServlet documentUploadServlet = new DocumentUploadServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        RequestDispatcher reqDisp = mock(RequestDispatcher.class);

        documentUploadServlet.ticketsDAO = ticketsDAO;
        documentUploadServlet.shipDAO = shipDAO;
        documentUploadServlet.stationDAO = stationDAO;
        documentUploadServlet.path = "";

        when(req.getParameter("id")).thenReturn("");

        documentUploadServlet.doGet(req,resp);

        verify(resp).sendError(404,"Such link is not existing. Check if you enter all parameters right");
    }
}
