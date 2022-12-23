import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.TicketsDAO;
import com.my.DB.ShipDB;
import com.my.DB.TicketsDB;
import com.my.servlets.NewCruiseServlet;
import com.my.servlets.PaymentServlet;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class PaymentTest {

    @Test
    void payTest() throws ServletException, IOException {
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        PaymentServlet paymentServlet = new PaymentServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        paymentServlet.ticketsDAO = ticketsDAO;

        when(req.getParameter("id")).thenReturn("1");

        paymentServlet.doPost(req,resp);

        verify(resp).sendRedirect("boughtTickets");
    }

    @Test
    void idNull() throws ServletException, IOException {
        PaymentServlet paymentServlet = new PaymentServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        when(req.getParameter("id")).thenReturn(null);

        paymentServlet.doPost(req,resp);

        verify(resp).sendError(404,"Such link is not existing. Check if you enter all parameters right");
    }

    @Test
    void sqlException() throws ServletException, IOException, SQLException {
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        PaymentServlet paymentServlet = new PaymentServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        paymentServlet.ticketsDAO = ticketsDAO;

        when(req.getParameter("id")).thenReturn("1");
        doThrow(new SQLException()).when(ticketsDAO).setBoughtStatus(1,3);

        paymentServlet.doPost(req,resp);

        verify(resp).sendError(500,"Something went wrong. Try again later");
    }
}
