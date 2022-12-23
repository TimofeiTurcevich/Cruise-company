import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.StationDAO;
import com.my.DB.DAO.TicketsDAO;
import com.my.DB.ShipDB;
import com.my.DB.StationDB;
import com.my.DB.TicketsDB;
import com.my.classes.Ship;
import com.my.servlets.ChangeGeneralDBServlet;
import com.my.servlets.ChangeGeneralServlet;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class ChangeGeneralTest {
    private final List<Ship> ships = new ArrayList<>();

    @Test
    void changeTest() throws SQLException, ServletException, IOException {
        ShipDAO shipDAO = mock(ShipDB.class);
        ChangeGeneralServlet changeGeneralServlet = new ChangeGeneralServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

        changeGeneralServlet.shipDAO = shipDAO;
        changeGeneralServlet.path = "";

        when(req.getSession()).thenReturn(session);
        when(req.getRequestDispatcher("editGeneral.jsp")).thenReturn(requestDispatcher);
        when(shipDAO.getShips("")).thenReturn(ships);

        changeGeneralServlet.doGet(req,resp);

        verify(session).setAttribute("ships",ships);
        verify(requestDispatcher).forward(req,resp);
    }

    @Test
    void sqlException() throws ServletException, IOException, SQLException {
        ShipDAO shipDAO = mock(ShipDB.class);
        ChangeGeneralServlet changeGeneralServlet = new ChangeGeneralServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        changeGeneralServlet.shipDAO = shipDAO;
        changeGeneralServlet.path = "";

        when(req.getSession()).thenReturn(session);
        when(shipDAO.getShips("")).thenThrow(new SQLException());

        changeGeneralServlet.doGet(req,resp);

        verify(resp).sendError(500,"Something went wrong. Try again later");
    }
}
