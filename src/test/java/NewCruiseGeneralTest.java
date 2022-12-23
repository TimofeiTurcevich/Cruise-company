import com.my.servlets.NewCruiseGeneralServlet;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class NewCruiseGeneralTest {
    @Test
    void newCruiseTest() throws ServletException, IOException {
        NewCruiseGeneralServlet newCruiseGeneralServlet = new NewCruiseGeneralServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

        when(req.getRequestDispatcher("newCruiseStations.jsp")).thenReturn(requestDispatcher);

        newCruiseGeneralServlet.doPost(req,resp);

        verify(requestDispatcher).forward(req,resp);
    }
}
