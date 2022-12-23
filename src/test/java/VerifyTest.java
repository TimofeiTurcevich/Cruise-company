import com.my.DB.DAO.UserDAO;
import com.my.DB.UserDB;
import com.my.classes.User;
import com.my.servlets.VerifyServlet;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class VerifyTest {
    private User user = new User.Builder()
            .setId(1)
            .setCode("someCode")
            .build();

    @Test
    void verifyTest() throws SQLException, ServletException, IOException {
        UserDAO userDAO = mock(UserDB.class);
        VerifyServlet verifyServlet = new VerifyServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        verifyServlet.userDAO = userDAO;

        when(req.getParameter("id")).thenReturn("1");
        when(userDAO.getUserToVerify(1)).thenReturn(user);
        when(req.getParameter("code")).thenReturn("someCode");
        when(req.getSession()).thenReturn(session);

        verifyServlet.doPost(req,resp);
        verify(session).setAttribute("user",user);
        verify(resp).sendRedirect("index");
    }

    @Test
    void invalidCode() throws SQLException, ServletException, IOException {
        UserDAO userDAO = mock(UserDB.class);
        VerifyServlet verifyServlet = new VerifyServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        verifyServlet.userDAO = userDAO;

        when(req.getParameter("id")).thenReturn("1");
        when(userDAO.getUserToVerify(1)).thenReturn(user);
        when(req.getParameter("code")).thenReturn("someCod");

        verifyServlet.doPost(req,resp);
        verify(resp).sendRedirect("verify.jsp?id=1");
    }

    @Test
    void sqlException() throws SQLException, ServletException, IOException {
        UserDAO userDAO = mock(UserDB.class);
        VerifyServlet verifyServlet = new VerifyServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        verifyServlet.userDAO = userDAO;

        when(req.getParameter("id")).thenReturn("1");
        when(userDAO.getUserToVerify(1)).thenThrow(new SQLException());

        verifyServlet.doPost(req,resp);
        verify(resp).sendError(500,"Something went wrong. Try again later");
    }

    @Test
    void nullParameter() throws ServletException, IOException {
        UserDAO userDAO = mock(UserDB.class);
        VerifyServlet verifyServlet = new VerifyServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        verifyServlet.userDAO = userDAO;

        when(req.getParameter("id")).thenReturn(null);

        verifyServlet.doPost(req,resp);
        verify(resp).sendError(404,"Such link is not existing. Check if you enter all parameters right");
    }
}
