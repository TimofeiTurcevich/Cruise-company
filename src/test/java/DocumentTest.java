import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.StationDAO;
import com.my.DB.DAO.TicketsDAO;
import com.my.DB.ShipDB;
import com.my.DB.StationDB;
import com.my.DB.TicketsDB;
import com.my.servlets.DeleteStationServlet;
import com.my.servlets.DocumentServlet;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class DocumentTest {

    @Test
    void documentTest() throws ServletException, IOException {
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        DocumentServlet documentServlet = new DocumentServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        ServletContext servletContext = mock(ServletContext.class);
        Part part = mock(Part.class);
        InputStream inputStream = mock(InputStream.class);

        documentServlet.ticketsDAO = ticketsDAO;

        when(req.getParameter("id")).thenReturn("1");
        when(req.getServletContext()).thenReturn(servletContext);
        when(servletContext.getRealPath("/")).thenReturn("");
        when(req.getParameter("count")).thenReturn("1");
        when(req.getPart("document1")).thenReturn(part);
        when(part.getInputStream()).thenReturn(inputStream);

        documentServlet.doPost(req,resp);

        verify(resp).sendRedirect("boughtTickets");
    }

    @Test
    void idNull() throws ServletException, IOException {
        DocumentServlet documentServlet = new DocumentServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        ServletContext servletContext = mock(ServletContext.class);
        Part part = mock(Part.class);
        InputStream inputStream = mock(InputStream.class);


        when(req.getParameter("id")).thenReturn(null);

        documentServlet.doPost(req,resp);

        verify(resp).sendError(404,"Such link is not existing. Check if you enter all parameters right");
    }

    @Test
    void sqlException() throws ServletException, IOException, SQLException {
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        DocumentServlet documentServlet = new DocumentServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        documentServlet.ticketsDAO = ticketsDAO;

        when(req.getParameter("id")).thenReturn("1");
        doThrow(new SQLException()).when(ticketsDAO).setBoughtStatus(1,2);

        documentServlet.doPost(req,resp);

        verify(resp).sendError(500,"Something went wrong. Try again later");
    }

    @Test
    void noDir() throws ServletException, IOException {
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        DocumentServlet documentServlet = new DocumentServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        ServletContext servletContext = mock(ServletContext.class);
        Part part = mock(Part.class);
        InputStream inputStream = mock(InputStream.class);

        documentServlet.ticketsDAO = ticketsDAO;

        when(req.getParameter("id")).thenReturn("1");
        when(req.getServletContext()).thenReturn(servletContext);
        when(servletContext.getRealPath("/")).thenReturn("some");
        when(req.getParameter("count")).thenReturn("1");
        when(req.getPart("document1")).thenReturn(part);
        when(part.getInputStream()).thenReturn(inputStream);

        documentServlet.doPost(req,resp);

        verify(resp).sendRedirect("boughtTickets");
    }
}
