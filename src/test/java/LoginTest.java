import com.my.DB.DAO.UserDAO;
import com.my.DB.UserDB;
import com.my.classes.User;
import com.my.servlets.LoginServlet;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class LoginTest {
    private User user;
    @Test
    void adminLoginTest() throws SQLException, ServletException, IOException {
        user = new User.Builder()
                .setRole("admin")
                .setEmail("timofeiturcevich@gmail.com")
                .setLang("en")
                .build();

        UserDAO userDAO = mock(UserDB.class);
        LoginServlet loginServlet = new LoginServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        ServletContext servletContext = mock(ServletContext.class);

        when(req.getServletContext()).thenReturn(servletContext);
        when(req.getParameter("email")).thenReturn("timofeiturcevich@gmail.com");
        when(req.getParameter("pass")).thenReturn("adminPass123!");
        when(userDAO.getUser("timofeiturcevich@gmail.com","adminPass123!")).thenReturn(user);
        when(req.getSession()).thenReturn(session);

        loginServlet.userDAO = userDAO;
        loginServlet.doPost(req,resp);

        verify(session).setAttribute("user",user);
        verify(resp).sendRedirect("admin/needToAccept");
    }

    @Test
    void clientLoginTest() throws SQLException, ServletException, IOException {
        user = new User.Builder()
                .setRole("client")
                .setEmail("timofeiturcevich@gmail.com")
                .setLang("en")
                .build();

        UserDAO userDAO = mock(UserDB.class);
        LoginServlet loginServlet = new LoginServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        ServletContext servletContext = mock(ServletContext.class);

        when(req.getServletContext()).thenReturn(servletContext);
        when(req.getParameter("email")).thenReturn("testClient@gmail.com");
        when(req.getParameter("pass")).thenReturn("clientPass123!");
        when(userDAO.getUser("testClient@gmail.com","clientPass123!")).thenReturn(user);
        when(req.getSession()).thenReturn(session);

        loginServlet.userDAO = userDAO;
        loginServlet.doPost(req,resp);

        verify(session).setAttribute("user",user);
        verify(resp).sendRedirect("index");
    }

    @Test
    void noUserTest() throws SQLException, ServletException, IOException {
        UserDAO userDAO = mock(UserDB.class);
        LoginServlet loginServlet = new LoginServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        when(req.getParameter("email")).thenReturn("noUser@gmail.com");
        when(req.getParameter("pass")).thenReturn("noUserPass123!");
        when(userDAO.getUser("noUser@gmail.com","noUserPass123!")).thenReturn(null);
        when(req.getSession()).thenReturn(session);

        loginServlet.userDAO = userDAO;
        loginServlet.doPost(req,resp);

        verify(session).setAttribute("errorLogin",true);
        verify(resp).sendRedirect("login.jsp");
    }

    @Test
    void sqlException() throws SQLException, ServletException, IOException {
        UserDAO userDAO = mock(UserDB.class);
        LoginServlet loginServlet = new LoginServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        when(req.getParameter("email")).thenReturn("noUser@gmail.com");
        when(req.getParameter("pass")).thenReturn("noUserPass123!");
        when(userDAO.getUser("noUser@gmail.com","noUserPass123!")).thenThrow(new SQLException());
        when(req.getSession()).thenReturn(session);

        loginServlet.userDAO = userDAO;
        loginServlet.doPost(req,resp);

        verify(resp).sendError(500,"Something went wrong. Try again later");
    }
}
