import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.StationDAO;
import com.my.DB.DAO.TicketsDAO;
import com.my.DB.DAO.UserDAO;
import com.my.DB.ShipDB;
import com.my.DB.StationDB;
import com.my.DB.TicketsDB;
import com.my.DB.UserDB;
import com.my.classes.User;
import com.my.servlets.RegisterServlet;
import com.my.servlets.SalesServlet;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Random;
import java.util.stream.IntStream;

import static org.mockito.Mockito.*;

public class RegisterTest {

    private User user = new User();

    @Test
    void registerTest() throws SQLException, ServletException, IOException {
        user = new User.Builder()
                .setEmail("timofeiturcevich@gmail.com")
                .setBirthDate(Date.valueOf("2022-03-02"))
                .setFirstName("test")
                .setLastName("test")
                .setPhone("+380 44 000 00 00")
                .setCode("213")
                .build();

        UserDAO userDAO = mock(UserDB.class);
        RegisterServlet registerServlet = new RegisterServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        Random random = mock(Random.class);
        IntStream intStream = mock(IntStream.class);
        StringBuilder stringBuilder = new StringBuilder("213");

        registerServlet.userDAO = userDAO;
        registerServlet.random = random;

        when(req.getParameter("email")).thenReturn(user.getEmail());
        when(req.getParameter("date")).thenReturn(user.getBirthDate().toString());
        when(req.getParameter("firstName")).thenReturn(user.getFirstName());
        when(req.getParameter("lastName")).thenReturn(user.getLastName());
        when(req.getParameter("phone")).thenReturn(user.getPhone());
        when(req.getParameter("pass")).thenReturn("test");
        when(random.ints(48,123)).thenReturn(intStream);
        when(intStream.filter(i->(i<=57 || i>=65)&&(i<=90||i>=97))).thenReturn(intStream);
        when(intStream.limit(5)).thenReturn(intStream);
        when(intStream.collect(StringBuilder::new,StringBuilder::appendCodePoint, StringBuilder::append)).thenReturn(stringBuilder);
        when(req.getSession()).thenReturn(session);
        when(userDAO.insertUser(user.getEmail(),"test",user.getBirthDate(),user.getFirstName(),user.getLastName(), user.getPhone(),"")).thenReturn(user);

        registerServlet.doPost(req,resp);

        verify(resp).sendRedirect("verify.jsp?id="+user.getId());
    }

    @Test
    void registerFailedTest() throws SQLException, ServletException, IOException {

        UserDAO userDAO = mock(UserDB.class);
        RegisterServlet registerServlet = new RegisterServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        Random random = mock(Random.class);
        IntStream intStream = mock(IntStream.class);
        StringBuilder stringBuilder = new StringBuilder("213");
        HttpSession session = mock(HttpSession.class);

        registerServlet.userDAO = userDAO;
        registerServlet.random = random;

        when(req.getParameter("email")).thenReturn(user.getEmail());
        when(req.getParameter("date")).thenReturn("2022-03-02");
        when(req.getParameter("firstName")).thenReturn(user.getFirstName());
        when(req.getParameter("lastName")).thenReturn(user.getLastName());
        when(req.getParameter("phone")).thenReturn(user.getPhone());
        when(req.getParameter("pass")).thenReturn("test");
        when(random.ints(48,123)).thenReturn(intStream);
        when(intStream.filter(i->(i<=57 || i>=65)&&(i<=90||i>=97))).thenReturn(intStream);
        when(intStream.limit(5)).thenReturn(intStream);
        when(intStream.collect(StringBuilder::new,StringBuilder::appendCodePoint, StringBuilder::append)).thenReturn(stringBuilder);
        when(userDAO.insertUser(user.getEmail(),"test",Date.valueOf("2022-03-02"),user.getFirstName(),user.getLastName(), user.getPhone(),"")).thenThrow(new SQLException());
        when(req.getSession()).thenReturn(session);

        registerServlet.doPost(req,resp);

        verify(session).setAttribute("errorRegister",true);
        verify(resp).sendRedirect("register.jsp");
    }

    @Test
    void userNull() throws SQLException, ServletException, IOException {
        UserDAO userDAO = mock(UserDB.class);
        RegisterServlet registerServlet = new RegisterServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        Random random = mock(Random.class);
        IntStream intStream = mock(IntStream.class);
        StringBuilder stringBuilder = new StringBuilder("213");
        HttpSession session = mock(HttpSession.class);

        registerServlet.userDAO = userDAO;
        registerServlet.random = random;

        when(req.getParameter("email")).thenReturn(user.getEmail());
        when(req.getParameter("date")).thenReturn("2022-03-02");
        when(req.getParameter("firstName")).thenReturn(user.getFirstName());
        when(req.getParameter("lastName")).thenReturn(user.getLastName());
        when(req.getParameter("phone")).thenReturn(user.getPhone());
        when(req.getParameter("pass")).thenReturn("test");
        when(random.ints(48,123)).thenReturn(intStream);
        when(intStream.filter(i->(i<=57 || i>=65)&&(i<=90||i>=97))).thenReturn(intStream);
        when(intStream.limit(5)).thenReturn(intStream);
        when(intStream.collect(StringBuilder::new,StringBuilder::appendCodePoint, StringBuilder::append)).thenReturn(stringBuilder);
        when(userDAO.insertUser(user.getEmail(),"test",Date.valueOf("2022-03-02"),user.getFirstName(),user.getLastName(), user.getPhone(),"")).thenReturn(null);
        when(req.getSession()).thenReturn(session);

        registerServlet.doPost(req,resp);

        verify(session).setAttribute("errorRegister",false);
        verify(resp).sendRedirect("register.jsp");
    }
}
