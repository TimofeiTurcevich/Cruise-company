import com.my.servlets.DocumentUploadServlet;
import com.my.servlets.QuitServlet;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class QuitTest {
    @Test
    void quitTest() throws ServletException, IOException {
        QuitServlet quitServlet = new QuitServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        when(req.getSession()).thenReturn(session);

        quitServlet.doGet(req,resp);

        verify(resp).sendRedirect("../index");
    }
}
