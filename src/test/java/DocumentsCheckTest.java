import com.my.servlets.DocumentCheckServlet;
import com.my.servlets.TicketsServlet;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class DocumentsCheckTest {

    @Test
    void documentsTest() throws ServletException, IOException {
        DocumentCheckServlet documentCheckServlet = new DocumentCheckServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher reqDisp = mock(RequestDispatcher.class);

        documentCheckServlet.path = "";

        when(req.getParameter("id")).thenReturn("2");
        when(req.getSession()).thenReturn(session);
        when(req.getRequestDispatcher("documentCheck.jsp")).thenReturn(reqDisp);


        documentCheckServlet.doGet(req,resp);

        verify(session).setAttribute("documents",null);
    }

    @Test
    void noDocumentsTest() throws IOException, ServletException {
        DocumentCheckServlet documentCheckServlet = new DocumentCheckServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher reqDisp = mock(RequestDispatcher.class);

        documentCheckServlet.path = "";

        when(req.getParameter("id")).thenReturn("");

        documentCheckServlet.doGet(req,resp);

        verify(resp).sendError(404,"Such link is not existing. Check if you enter all parameters right");
    }
}
