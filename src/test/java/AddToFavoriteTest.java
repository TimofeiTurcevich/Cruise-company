import com.my.DB.DAO.TicketsDAO;
import com.my.DB.TicketsDB;
import com.my.classes.User;
import com.my.servlets.AddToFavoriteServlet;
import com.my.servlets.TicketsServlet;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class AddToFavoriteTest {

    @Test
    void addTest() throws SQLException, ServletException, IOException {
        User user = new User.Builder()
                .setId(1)
                .build();
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        AddToFavoriteServlet addToFavoriteServlet = new AddToFavoriteServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        addToFavoriteServlet.ticketsDAO = ticketsDAO;

        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(req.getParameter("id")).thenReturn("1");

        addToFavoriteServlet.doGet(req,resp);

        verify(resp).sendRedirect("cruise?id=1");
    }

    @Test
    void noId() throws IOException, ServletException {
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        AddToFavoriteServlet addToFavoriteServlet = new AddToFavoriteServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        addToFavoriteServlet.ticketsDAO = ticketsDAO;

        when(req.getParameter("id")).thenReturn(null);

        addToFavoriteServlet.doGet(req,resp);

        verify(resp).sendError(404,"Such link is not existing. Check if you enter all parameters right");
    }

    @Test
    void sqlError() throws ServletException, IOException, SQLException {
        User user = new User.Builder()
                .setId(1)
                .build();
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        AddToFavoriteServlet addToFavoriteServlet = new AddToFavoriteServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        addToFavoriteServlet.ticketsDAO = ticketsDAO;

        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(req.getParameter("id")).thenReturn("1");
        doThrow(new SQLException()).when(ticketsDAO).insertToFavorite(1,1);

        addToFavoriteServlet.doGet(req,resp);

        verify(resp).sendError(500,"Something went wrong. Try again later");
    }
}
