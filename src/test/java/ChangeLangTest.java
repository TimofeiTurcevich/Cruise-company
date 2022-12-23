import com.my.DB.DAO.StationDAO;
import com.my.DB.DAO.UserDAO;
import com.my.DB.StationDB;
import com.my.DB.UserDB;
import com.my.classes.User;
import com.my.servlets.AddStationServlet;
import com.my.servlets.ChangeLanguageServlet;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class ChangeLangTest {
    private final User user = new User.Builder()
            .setId(1)
            .build();
    @Test
    void langTest() throws ServletException, IOException {
        UserDAO userDAO = mock(UserDB.class);
        ChangeLanguageServlet changeLanguageServlet = new ChangeLanguageServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        ServletContext servletContext = mock(ServletContext.class);

        changeLanguageServlet.userDAO = userDAO;

        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(req.getServletContext()).thenReturn(servletContext);
        when(req.getParameter("lang")).thenReturn("en");
        when(req.getParameter("page")).thenReturn("page");

        changeLanguageServlet.doGet(req,resp);

        verify(servletContext).setAttribute("lang","en");
        verify(resp).sendRedirect(req.getParameter("page"));
    }

    @Test
    void sqlException() throws ServletException, IOException, SQLException {
        UserDAO userDAO = mock(UserDB.class);
        ChangeLanguageServlet changeLanguageServlet = new ChangeLanguageServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        ServletContext servletContext = mock(ServletContext.class);

        changeLanguageServlet.userDAO = userDAO;

        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(req.getServletContext()).thenReturn(servletContext);
        when(req.getParameter("lang")).thenReturn("en");
        when(req.getParameter("page")).thenReturn("page");
        doThrow(new SQLException()).when(userDAO).changeLang(1,"en");

        changeLanguageServlet.doGet(req,resp);

        verify(servletContext).setAttribute("lang","en");
        verify(resp).sendError(500,"Something went wrong. Try again later");
    }
}
