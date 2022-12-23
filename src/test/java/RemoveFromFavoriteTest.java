import com.my.DB.DAO.TicketsDAO;
import com.my.DB.TicketsDB;
import com.my.classes.User;
import com.my.servlets.PaymentServlet;
import com.my.servlets.RemoveFavoriteServlet;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class RemoveFromFavoriteTest {
    private final User user = new User.Builder()
            .setId(1)
            .build();
    @Test
    void removeTest() throws ServletException, IOException {
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        RemoveFavoriteServlet removeFavoriteServlet = new RemoveFavoriteServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        removeFavoriteServlet.ticketsDAO = ticketsDAO;

        when(req.getParameter("id")).thenReturn("1");
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);

        removeFavoriteServlet.doGet(req,resp);

        verify(resp).sendRedirect("cruise?id=1");
    }

    @Test
    void idNull() throws ServletException, IOException {
        RemoveFavoriteServlet removeFavoriteServlet = new RemoveFavoriteServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        when(req.getParameter("id")).thenReturn(null);

        removeFavoriteServlet.doGet(req,resp);

        verify(resp).sendError(404,"Such link is not existing. Check if you enter all parameters right");
    }

    @Test
    void sqlException() throws SQLException, ServletException, IOException {
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        RemoveFavoriteServlet removeFavoriteServlet = new RemoveFavoriteServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        removeFavoriteServlet.ticketsDAO = ticketsDAO;

        when(req.getParameter("id")).thenReturn("1");
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        doThrow(new SQLException()).when(ticketsDAO).deleteFromFavorite(1,1);

        removeFavoriteServlet.doGet(req,resp);

        verify(resp).sendError(500,"Something went wrong. Try again later");
    }
}
