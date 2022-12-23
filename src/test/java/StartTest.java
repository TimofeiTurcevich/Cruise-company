import com.my.DB.DAO.UserDAO;
import com.my.DB.UserDB;
import com.my.classes.User;
import com.my.servlets.StartServlet;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class StartTest {

    private final User user = new User();

    @Test
    void startUserTest() throws IOException, ServletException {
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        when(req.getSession()).thenReturn(session);
        when(req.getSession().getAttribute("user")).thenReturn(user);

        StartServlet startServlet = new StartServlet();

        startServlet.doGet(req,resp);

        verify(resp).sendRedirect("tickets");
    }

    @Test
    void startNoUserTest() throws ServletException, IOException {
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        when(req.getSession()).thenReturn(session);
        when(req.getSession().getAttribute("user")).thenReturn(null);

        StartServlet startServlet = new StartServlet();

        startServlet.doGet(req,resp);

        verify(resp).sendRedirect("login.jsp");
    }

}
