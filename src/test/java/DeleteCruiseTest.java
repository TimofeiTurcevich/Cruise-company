import com.my.DB.DAO.StationDAO;
import com.my.DB.DAO.TicketsDAO;
import com.my.DB.StationDB;
import com.my.DB.TicketsDB;
import com.my.servlets.AddStationServlet;
import com.my.servlets.DeleteCruiseServlet;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class DeleteCruiseTest {

    @Test
    void deleteTest() throws ServletException, IOException {
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        DeleteCruiseServlet deleteCruiseServlet = new DeleteCruiseServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        deleteCruiseServlet.ticketsDAO = ticketsDAO;

        when(req.getParameter("id")).thenReturn("1");

        deleteCruiseServlet.doGet(req,resp);

        verify(resp).sendRedirect("needToAccept.jsp");
    }

    @Test
    void idNull() throws ServletException, IOException {
        DeleteCruiseServlet deleteCruiseServlet = new DeleteCruiseServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        when(req.getParameter("id")).thenReturn(null);

        deleteCruiseServlet.doGet(req,resp);

        verify(resp).sendError(404,"Such link is not existing. Check if you enter all parameters right");
    }

    @Test
    void sqlException() throws ServletException, IOException, SQLException {
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        DeleteCruiseServlet deleteCruiseServlet = new DeleteCruiseServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);

        deleteCruiseServlet.ticketsDAO = ticketsDAO;

        when(req.getParameter("id")).thenReturn("1");
        doThrow(new SQLException()).when(ticketsDAO).deleteVoyage(1);

        deleteCruiseServlet.doGet(req,resp);

        verify(resp).sendError(500,"Something went wrong. Try again later");
    }
}
