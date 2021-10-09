package servlets;

import com.google.gson.Gson;
import model.File;
import org.hibernate.HibernateException;
import service.FileService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FileServlet extends HttpServlet {

    private FileService fileService;
    private File file;

    @Override
    public void init() {
        fileService = new FileService();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter writer = resp.getWriter();
        Gson gson = new Gson();

        req.setCharacterEncoding("UTF-8");
        final long id = Long.parseLong(req.getParameter("id"));

        if (id == 0) {

            try {

                List<File> fileList = fileService.getAll();
                resp.setContentType("text/HTML; charset=UTF-8");

                for (File file : fileList) {
                    writer.println(gson.toJson(file));
                }

            } catch (HibernateException e) {

                e.printStackTrace();
                resp.setContentType("text/HTML; charset=UTF-8");
                writer.println(gson.toJson("Произошла ошибка!"));

            }

        } else {

            try {

                file = fileService.getById(id);
                resp.setContentType("text/HTML; charset=UTF-8");
                writer.println(gson.toJson(file.toString()));

            } catch (HibernateException e) {

                e.printStackTrace();
                resp.setContentType("text/HTML; charset=UTF-8");
                writer.println(gson.toJson("Не удалось найти \"File\" с таким ID"));

            }
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter writer = resp.getWriter();
        Gson gson = new Gson();

        req.setCharacterEncoding("UTF-8");

        final long eventId = Long.parseLong(req.getParameter("event_id"));
        final String fileName = req.getParameter("file_name");
        final String filePath = req.getParameter("file_path");

        if ((eventId != 0 & fileName != null) & filePath != null) {

            try {

                file = fileService.create(eventId,filePath, fileName);
                resp.setContentType("text/HTML; charset=UTF-8");
                writer.println(gson.toJson(file.toString()));

            } catch (HibernateException e) {

                e.printStackTrace();
                resp.setContentType("text/HTML; charset=UTF-8");
                writer.println(gson.toJson("Не удалось создать \"File\" "));

            }
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter writer = resp.getWriter();
        Gson gson = new Gson();

        req.setCharacterEncoding("UTF-8");

        final long id = Long.parseLong(req.getParameter("id"));
        final long eventId = Long.parseLong(req.getParameter("event_id"));
        final String fileName = req.getParameter("file_name");
        final String filePath = req.getParameter("file_path");

        if ((id != 0 & eventId != 0) & (fileName != null & filePath != null)) {

            try {

                file = fileService.update(id, eventId, filePath, fileName);
                resp.setContentType("text/HTML; charset=UTF-8");
                writer.println(gson.toJson(file.toString()));

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

        if(id != 0) {

            try {

                fileService.deleteById(id);
                resp.setContentType("text/HTML; charset=UTF-8");
                writer.println(gson.toJson("Данные удалены"));

            } catch (HibernateException e) {

                e.printStackTrace();
                resp.setContentType("text/HTML; charset=UTF-8");
                writer.println(gson.toJson("Не удалось удалить данные"));
            }
        }
    }
}
