package servlets;

import com.google.gson.Gson;
import model.User;
import org.hibernate.HibernateException;
import service.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("api/v1/users")
public class UserServlet extends HttpServlet {

    private final static String ENCODING_UTF_8 = "UTF-8";

    private final UserService userService = new UserService();
    private final Gson gson = new Gson();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter writer = resp.getWriter();

        req.setCharacterEncoding(ENCODING_UTF_8);

        final long id = Long.parseLong(req.getParameter("id"));

        if (id == 0) {

            try {

                List<User> userList = userService.getAll();
                resp.setContentType("text/HTML; charset=UTF-8");

                for (User users : userList) {
                    writer.write(gson.toJson(users.toString()));
                }
            } catch (HibernateException e) {
                e.printStackTrace();
                resp.setContentType("text/HTML; charset=UTF-8");
                writer.write(gson.toJson("Произошла ошибка!"));
            }

        } else {

            try {
                User user = userService.getById(id);
                resp.setContentType("text/HTML; charset=UTF-8");
                writer.write(gson.toJson(user.toString()));
            } catch (HibernateException e) {
                e.printStackTrace();
                resp.setContentType("text/HTML; charset=UTF-8");
                writer.write(gson.toJson("Не было найдена \"User\" с таким ID"));
            }
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter writer = resp.getWriter();

        req.setCharacterEncoding(ENCODING_UTF_8);

        final String userName = req.getParameter("user_name");

        if (userName == null) {
            resp.setContentType("text/HTML; charset=UTF-8");
            writer.write(gson.toJson("Не удалось создать \"User\" "));
            return;
        }

        try {
            User user = userService.create(userName);
            resp.setContentType("text/HTML; charset=UTF-8");
            writer.write(gson.toJson(user.toString()));
        } catch (HibernateException e) {
            e.printStackTrace();
            resp.setContentType("text/HTML; charset=UTF-8");
            writer.write(gson.toJson("Не удалось создать \"User\" "));
        }

    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter writer = resp.getWriter();

        req.setCharacterEncoding(ENCODING_UTF_8);

        final long id = Long.parseLong(req.getParameter("id"));
        final String userName = req.getParameter("user_name");

        if (id == 0 || userName == null) {
            resp.setContentType("text/HTML; charset=UTF-8");
            writer.write(gson.toJson("Не удалось изменить данные"));
            return;
        }

        try {
            User user = userService.update(id, userName);
            resp.setContentType("text/HTML; charset=UTF-8");
            writer.write(gson.toJson(user.toString()));
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
            writer.write(gson.toJson("Не удалось удалить данные"));
            return;
        }

        try {
            userService.deleteById(id);
            resp.setContentType("text/HTML; charset=UTF-8");
            writer.write(gson.toJson("Данные удалены!"));
        } catch (HibernateException e) {
            e.printStackTrace();
            resp.setContentType("text/HTML; charset=UTF-8");
            writer.write(gson.toJson("Не удалось удалить данные"));
        }
    }

}
