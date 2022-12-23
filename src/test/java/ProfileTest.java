
import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.StationDAO;
import com.my.DB.DAO.TicketsDAO;
import com.my.DB.ShipDB;
import com.my.DB.StationDB;
import com.my.DB.TicketsDB;
import com.my.classes.User;
import com.my.servlets.CruiseServlet;
import com.my.servlets.ProfileServlet;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class ProfileTest {
    private User user;

    @Test
    void noUserTest() throws ServletException, IOException {

        ProfileServlet profileServlet = new ProfileServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        when(session.getAttribute("user")).thenReturn(user);
        when(req.getSession()).thenReturn(session);

        profileServlet.doGet(req,resp);

        verify(resp).sendRedirect("login.jsp");
    }

    @Test
    void clientUserTest() throws ServletException, IOException {
        user = new User.Builder()
                .setRole("client")
                .build();

        ProfileServlet profileServlet = new ProfileServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        when(session.getAttribute("user")).thenReturn(user);
        when(req.getSession()).thenReturn(session);

        profileServlet.doGet(req,resp);

        verify(resp).sendRedirect("user/profile.jsp");
    }

    @Test
    void adminUserTest() throws ServletException, IOException {
        user = new User.Builder()
                .setRole("admin")
                .build();

        ProfileServlet profileServlet = new ProfileServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        when(session.getAttribute("user")).thenReturn(user);
        when(req.getSession()).thenReturn(session);

        profileServlet.doGet(req,resp);

        verify(resp).sendRedirect("admin/profile.jsp");
    }
}
