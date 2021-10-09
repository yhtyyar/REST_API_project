package servlets;

import com.google.gson.Gson;
import model.User;
import org.hibernate.HibernateException;
import service.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class UserServlet extends HttpServlet {

    private UserService userService;
    private User user;


    @Override
    public void init() {
        userService = new UserService();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter writer = resp.getWriter();
        Gson gson = new Gson();

        req.setCharacterEncoding("UTF-8");
        final long id = Long.parseLong(req.getParameter("id"));

        if (id == 0) {

            try {

                List<User> userList = userService.getAll();
                resp.setContentType("text/HTML; charset=UTF-8");

                for (User user : userList) {
                    writer.println(gson.toJson(user.toString()));
                }

            } catch (HibernateException e) {

                e.printStackTrace();
                resp.setContentType("text/HTML; charset=UTF-8");
                writer.println(gson.toJson("Произошла ошибка!"));
            }

        } else {

            try {

                user = userService.getById(id);
                resp.setContentType("text/HTML; charset=UTF-8");
                writer.println(gson.toJson(user.toString()));

            } catch (HibernateException e) {

                e.printStackTrace();
                resp.setContentType("text/HTML; charset=UTF-8");
                writer.println(gson.toJson("Не было найдена \"User\" с таким ID"));

            }
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter writer = resp.getWriter();
        Gson gson = new Gson();

        req.setCharacterEncoding("UTF-8");
        final String userName = req.getParameter("user_name");

        if (userName != null) {

            try {

                user = userService.create(userName);
                resp.setContentType("text/HTML; charset=UTF-8");
                writer.println(gson.toJson(user.toString()));

            } catch (HibernateException e) {

                e.printStackTrace();
                resp.setContentType("text/HTML; charset=UTF-8");
                writer.println(gson.toJson("Не удалось создать \"User\" "));

            }
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter writer = resp.getWriter();
        Gson gson = new Gson();

        req.setCharacterEncoding("UTF-8");
        final long id = Long.parseLong(req.getParameter("id"));
        final String userName = req.getParameter("user_name");

        if (id != 0 & userName != null) {

            try {

                user = userService.update(id,userName);
                resp.setContentType("text/HTML; charset=UTF-8");
                writer.println(gson.toJson(user.toString()));

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

                userService.deleteById(id);
                resp.setContentType("text/HTML; charset=UTF-8");
                writer.println(gson.toJson("Данные удалены!"));

            } catch (HibernateException e) {

                e.printStackTrace();
                resp.setContentType("text/HTML; charset=UTF-8");
                writer.println(gson.toJson("Не удалось удалить данные"));
            }
        }
    }
}
