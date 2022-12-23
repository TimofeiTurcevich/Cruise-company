import com.my.DB.DAO.UserDAO;
import com.my.DB.UserDB;
import com.my.classes.User;
import com.my.servlets.ChangeLanguageServlet;
import com.my.servlets.ChangePassServlet;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class ChangePassTest {
    private final User user = new User.Builder()
            .setId(1)
            .build();

    @Test
    void changePassTest() throws ServletException, IOException {
        UserDAO userDAO = mock(UserDB.class);
        ChangePassServlet changePassServlet = new ChangePassServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        changePassServlet.userDAO = userDAO;
        
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(req.getParameter("oldPass")).thenReturn("1");
        when(req.getParameter("newPass")).thenReturn("2");

        changePassServlet.doPost(req,resp);

        verify(resp).sendRedirect("../profile");
    }

    @Test
    void sqlException() throws ServletException, IOException, SQLException {
        UserDAO userDAO = mock(UserDB.class);
        ChangePassServlet changePassServlet = new ChangePassServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        changePassServlet.userDAO = userDAO;

        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(req.getParameter("oldPass")).thenReturn("1");
        when(req.getParameter("newPass")).thenReturn("2");
        doThrow(new SQLException()).when(userDAO).changePass("1","2",1);

        changePassServlet.doPost(req,resp);

        verify(resp).sendError(500,"Something went wrong. Try again later");
    }
}
