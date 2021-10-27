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

    private final static String ENCODING_UTF_8 = "UTF-8";

    private final EventService eventService = new EventService();
    private final Gson gson = new Gson();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter writer = resp.getWriter();

        req.setCharacterEncoding(ENCODING_UTF_8);

        final long id = Long.parseLong(req.getParameter("id"));

        if (id == 0) {

            try {
                List<Event> eventList = eventService.getAll();
                resp.setContentType("text/HTML; charset=UTF-8");

                for (Event events : eventList) {
                    writer.write(gson.toJson(events.toString()));
                }
            } catch (HibernateException e) {
                e.printStackTrace();
                resp.setContentType("text/HTML; charset=UTF-8");
                writer.write(gson.toJson("Произошла ошибка!"));
            }

        } else {

            try {
                Event event = eventService.getById(id);
                resp.setContentType("text/HTML; charset=UTF-8");
                writer.write(gson.toJson(event.toString()));
            } catch (HibernateException e) {
                e.printStackTrace();
                resp.setContentType("text/HTML; charset=UTF-8");
                writer.write(gson.toJson("Не найдено \"Event\" с таким ID"));
            }
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter writer = resp.getWriter();

        req.setCharacterEncoding(ENCODING_UTF_8);

        final String eventName = req.getParameter("event_name");

        if (eventName == null) {
            resp.setContentType("text/HTML; charset=UTF-8");
            writer.write(gson.toJson("Не удалось создать \"Event\" "));
            return;
        }

        try {
            Event event = eventService.create(eventName);
            resp.setContentType("text/HTML; charset=UTF-8");
            writer.write(gson.toJson(event.toString()));
        } catch (HibernateException e) {
            e.printStackTrace();
            resp.setContentType("text/HTML; charset=UTF-8");
            writer.write(gson.toJson("Не удалось создать \"Event\" "));
        }

    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter writer = resp.getWriter();

        req.setCharacterEncoding(ENCODING_UTF_8);

        final long id = Long.parseLong(req.getParameter("id"));
        final long userId = Long.parseLong(req.getParameter("user_id"));
        final String eventName = req.getParameter("event_name");

        if (id == 0 || userId == 0 || eventName == null) {
            resp.setContentType("text/HTML; charset=UTF-8");
            writer.write(gson.toJson("Не удалось изменить данные"));
            return;
        }

        try {
            Event event = eventService.update(id, userId, eventName);
            resp.setContentType("text/HTML; charset=UTF-8");
            writer.write(gson.toJson(event.toString()));
        } catch (HibernateException e) {
            e.printStackTrace();
            resp.setContentType("text/HTML; charset=UTF-8");
            writer.write(gson.toJson("Не удалось изменить данные"));
        }

    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter writer = resp.getWriter();

        req.setCharacterEncoding(ENCODING_UTF_8);

        final long id = Long.parseLong(req.getParameter("id"));

        if (id == 0) {
            resp.setContentType("text/HTML; charset=UTF-8");
            writer.write(gson.toJson("Произошла ошибка, данные не удалены"));
            return;
        }

        try {
            eventService.deleteById(id);
            resp.setContentType("text/HTML; charset=UTF-8");
            writer.write(gson.toJson("Данные удалены"));
        } catch (HibernateException e) {
            e.printStackTrace();
            resp.setContentType("text/HTML; charset=UTF-8");
            writer.write(gson.toJson("Произошла ошибка, данные не удалены"));
        }

    }
}
