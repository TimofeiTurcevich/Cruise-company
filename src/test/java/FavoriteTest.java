import com.my.DB.DAO.ShipDAO;
import com.my.DB.DAO.StationDAO;
import com.my.DB.DAO.TicketsDAO;
import com.my.DB.ShipDB;
import com.my.DB.StationDB;
import com.my.DB.TicketsDB;
import com.my.classes.User;
import com.my.classes.Voyage;
import com.my.servlets.FavoriteServlet;
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

public class FavoriteTest {
    private final List<Voyage> voyages = new ArrayList<>();
    private final User user = new User();

    @Test
    void someBoughtTest() throws SQLException, ServletException, IOException {
        Voyage voyage =new Voyage();
        voyages.add(voyage);


        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        FavoriteServlet favoriteServlet = new FavoriteServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher reqDisp = mock(RequestDispatcher.class);

        favoriteServlet.ticketsDAO = ticketsDAO;
        favoriteServlet.shipDAO = shipDAO;
        favoriteServlet.stationDAO = stationDAO;
        favoriteServlet.path = "";

        when(ticketsDAO.getFavoriteVoyages(user.getId(),shipDAO,stationDAO,"")).thenReturn(voyages);
        when(req.getSession()).thenReturn(session);
        when(req.getSession().getAttribute("user")).thenReturn(user);
        when(req.getRequestDispatcher("favorites.jsp?page=1")).thenReturn(reqDisp);

        favoriteServlet.doGet(req,resp);

        verify(session).setAttribute("favoriteTickets",voyages);
        verify(reqDisp).forward(req,resp);
    }

    @Test
    void secondPageTest() throws SQLException, ServletException, IOException {
        Voyage voyage =new Voyage();
        voyages.add(voyage);
        voyages.add(voyage);
        voyages.add(voyage);
        voyages.add(voyage);


        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        FavoriteServlet favoriteServlet = new FavoriteServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher reqDisp = mock(RequestDispatcher.class);

        favoriteServlet.ticketsDAO = ticketsDAO;
        favoriteServlet.shipDAO = shipDAO;
        favoriteServlet.stationDAO = stationDAO;
        favoriteServlet.path = "";

        when(ticketsDAO.getFavoriteVoyages(user.getId(),shipDAO,stationDAO,"")).thenReturn(voyages);
        when(req.getSession()).thenReturn(session);
        when(req.getParameter("page")).thenReturn("2");
        when(req.getSession().getAttribute("user")).thenReturn(user);
        when(req.getRequestDispatcher("favorites.jsp")).thenReturn(reqDisp);

        favoriteServlet.doGet(req,resp);

        verify(reqDisp).forward(req,resp);
    }

    @Test
    void invalidPageTest() throws SQLException, ServletException, IOException {
        Voyage voyage =new Voyage();
        voyages.add(voyage);
        voyages.add(voyage);


        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        FavoriteServlet favoriteServlet = new FavoriteServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        favoriteServlet.ticketsDAO = ticketsDAO;
        favoriteServlet.shipDAO = shipDAO;
        favoriteServlet.stationDAO = stationDAO;
        favoriteServlet.path = "";

        when(ticketsDAO.getFavoriteVoyages(user.getId(),shipDAO,stationDAO,"")).thenReturn(voyages);
        when(req.getSession()).thenReturn(session);
        when(req.getParameter("page")).thenReturn("2");
        when(req.getSession().getAttribute("user")).thenReturn(user);

        favoriteServlet.doGet(req,resp);

        verify(resp).sendError(404,"This page("+req.getParameter("page")+") is not existing");
    }

    @Test
    void sqlException() throws IOException, SQLException, ServletException {
        ShipDAO shipDAO = mock(ShipDB.class);
        StationDAO stationDAO = mock(StationDB.class);
        TicketsDAO ticketsDAO = mock(TicketsDB.class);
        FavoriteServlet favoriteServlet = new FavoriteServlet();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpServletRequest req =  mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        favoriteServlet.ticketsDAO = ticketsDAO;
        favoriteServlet.shipDAO = shipDAO;
        favoriteServlet.stationDAO = stationDAO;
        favoriteServlet.path = "";

        when(ticketsDAO.getFavoriteVoyages(user.getId(),shipDAO,stationDAO,"")).thenThrow(new SQLException());
        when(req.getSession()).thenReturn(session);
        when(req.getSession().getAttribute("user")).thenReturn(user);

        favoriteServlet.doGet(req,resp);

        verify(resp).sendError(500,"Something went wrong. Try again later");
    }
}
