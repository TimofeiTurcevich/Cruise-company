import com.my.DB.DAO.TicketsDAO;
import com.my.DB.DAO.UserDAO;
import com.my.DB.TicketsDB;
import com.my.DB.UserDB;
import com.my.servlets.ChangePassServlet;
import com.my.servlets.DeclineDocumentServlet;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class DeclineDocumentTest {
    @Test
    void declineTest() throws ServletException, IOException {
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        DeclineDocumentServlet declineDocumentServlet = new DeclineDocumentServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        declineDocumentServlet.ticketsDAO = ticketsDAO;

        when(req.getParameter("id")).thenReturn("1");

        declineDocumentServlet.doPost(req,resp);

        verify(resp).sendRedirect("needToAccept");
    }

    @Test
    void idNull() throws ServletException, IOException {
        DeclineDocumentServlet declineDocumentServlet = new DeclineDocumentServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        when(req.getParameter("id")).thenReturn(null);

        declineDocumentServlet.doPost(req,resp);

        verify(resp).sendError(404,"Such link is not existing. Check if you enter all parameters right");
    }

    @Test
    void sqlException() throws SQLException, ServletException, IOException {
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        DeclineDocumentServlet declineDocumentServlet = new DeclineDocumentServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        declineDocumentServlet.ticketsDAO = ticketsDAO;

        when(req.getParameter("id")).thenReturn("1");
        doThrow(new SQLException()).when(ticketsDAO).setBoughtStatus(1,5);

        declineDocumentServlet.doPost(req,resp);

        verify(resp).sendError(500,"Something went wrong. Try again later");
    }
}
