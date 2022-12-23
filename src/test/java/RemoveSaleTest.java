import com.my.DB.DAO.TicketsDAO;
import com.my.DB.TicketsDB;
import com.my.servlets.RemoveSaleServlet;
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

public class RemoveSaleTest {

    @Test
    void removeTest() throws SQLException, ServletException, IOException {
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        RemoveSaleServlet removeSaleServlet = new RemoveSaleServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        when(req.getParameter("id")).thenReturn("1");

        removeSaleServlet.ticketsDAO = ticketsDAO;

        removeSaleServlet.doGet(req,resp);

        verify(resp).sendRedirect("cruise?id=1");
    }

    @Test
    void idNull() throws ServletException, IOException {
        RemoveSaleServlet removeSaleServlet = new RemoveSaleServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        when(req.getParameter("id")).thenReturn(null);

        removeSaleServlet.doGet(req,resp);

        verify(resp).sendError(404,"Such link is not existing. Check if you enter all parameters right");
    }

    @Test
    void sqlException() throws SQLException, ServletException, IOException {
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        RemoveSaleServlet removeSaleServlet = new RemoveSaleServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        when(req.getParameter("id")).thenReturn("1");
        doThrow(new SQLException()).when(ticketsDAO).setStandardVoyage(1);

        removeSaleServlet.ticketsDAO = ticketsDAO;

        removeSaleServlet.doGet(req,resp);

        verify(resp).sendError(500,"Something went wrong. Try again later");
    }
}
