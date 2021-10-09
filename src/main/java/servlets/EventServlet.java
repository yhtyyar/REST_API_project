package servlets;

import com.google.gson.Gson;
import model.Event;
import org.hibernate.HibernateException;
import service.EventService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class EventServlet extends HttpServlet {

    private EventService eventService;
    private Event event;

    @Override
    public void init() {
        eventService = new EventService();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter writer = resp.getWriter();
        Gson gson = new Gson();

        req.setCharacterEncoding("UTF-8");
        final long id = Long.parseLong(req.getParameter("id"));

        if (id == 0) {
            // если пусто то нужно возвращать все данные (get_all)

            try {

                List<Event> eventList = eventService.getAll();
                resp.setContentType("text/HTML; charset=UTF-8");

                for (Event event : eventList) {
                    writer.println(gson.toJson(event.toString()));
                }

            } catch (HibernateException e) {

                e.printStackTrace();
                resp.setContentType("text/HTML; charset=UTF-8");
                writer.println(gson.toJson("Произошла ошибка!"));

            }

        } else {

            try {
                event = eventService.getById(id);
                resp.setContentType("text/HTML; charset=UTF-8");
                writer.println(gson.toJson(event.toString()));

            } catch (HibernateException e) {

                e.printStackTrace();
                resp.setContentType("text/HTML; charset=UTF-8");
                writer.println(gson.toJson("Не найдено \"Event\" с таким ID"));
            }
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter writer = resp.getWriter();
        Gson gson = new Gson();

        req.setCharacterEncoding("UTF-8");
        final String eventName = req.getParameter("event_name");


        if (eventName != null) {

            try {
                event = eventService.create(eventName);
                resp.setContentType("text/HTML; charset=UTF-8");
                writer.println(gson.toJson(event.toString()));

            } catch (HibernateException e) {

                e.printStackTrace();
                resp.setContentType("text/HTML; charset=UTF-8");
                writer.println(gson.toJson("Не удалось создать \"Event\" "));

            }
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter writer = resp.getWriter();
        Gson gson = new Gson();

        req.setCharacterEncoding("UTF-8");

        final long id = Long.parseLong(req.getParameter("id"));
        final long userId = Long.parseLong(req.getParameter("user_id"));
        final String eventName = req.getParameter("event_name");

        if ((id != 0 & userId != 0) & eventName != null) {

            try {

                event = eventService.update(id, userId, eventName);
                resp.setContentType("text/HTML; charset=UTF-8");
                writer.println(gson.toJson(event.toString()));

            } catch (HibernateException e) {

                e.printStackTrace();
                resp.setContentType("text/HTML; charset=UTF-8");
                writer.println(gson.toJson("Не удалось изменить данные"));

            }
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter writer = resp.getWriter();
        Gson gson = new Gson();

        req.setCharacterEncoding("UTF-8");

        final long id = Long.parseLong(req.getParameter("id"));


        if (id != 0) {

            try {

                eventService.deleteById(id);
                resp.setContentType("text/HTML; charset=UTF-8");
                writer.println(gson.toJson("Данные удалены"));

            } catch (HibernateException e) {

                e.printStackTrace();
                resp.setContentType("text/HTML; charset=UTF-8");
                writer.println(gson.toJson("Произошла ошибка, данные не удалены"));

            }
        }

    }
}
