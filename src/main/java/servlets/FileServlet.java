package servlets;

import com.google.gson.Gson;
import model.File;
import org.hibernate.HibernateException;
import service.FileService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("api/v1/files")
public class FileServlet extends HttpServlet {

    private final static String ENCODING_UTF_8 = "UTF-8";

    private final FileService fileService = new FileService();
    private final Gson gson = new Gson();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter writer = resp.getWriter();
        File file;

        req.setCharacterEncoding(ENCODING_UTF_8);

        final long id = Long.parseLong(req.getParameter("id"));

        if (id == 0) {

            try {
                List<File> fileList = fileService.getAll();
                resp.setContentType("text/HTML; charset=UTF-8");

                for (File files : fileList) {
                    writer.write(gson.toJson(files.toString()));
                }
            } catch (HibernateException e) {
                e.printStackTrace();
                resp.setContentType("text/HTML; charset=UTF-8");
                writer.write(gson.toJson("Произошла ошибка!"));
            }

        } else {

            try {
                file = fileService.getById(id);
                resp.setContentType("text/HTML; charset=UTF-8");
                writer.write(gson.toJson(file.toString()));
            } catch (HibernateException e) {
                e.printStackTrace();
                resp.setContentType("text/HTML; charset=UTF-8");
                writer.write(gson.toJson("Не удалось найти \"File\" с таким ID"));
            }
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter writer = resp.getWriter();
        File file;

        req.setCharacterEncoding(ENCODING_UTF_8);

        final long eventId = Long.parseLong(req.getParameter("event_id"));
        final String fileName = req.getParameter("file_name");
        final String filePath = req.getParameter("file_path");

        if (eventId == 0 || fileName == null || filePath == null) {
            resp.setContentType("text/HTML; charset=UTF-8");
            writer.write(gson.toJson("Не удалось создать \"File\" "));
            return;
        }

        try {
            file = fileService.create(eventId, filePath, fileName);
            resp.setContentType("text/HTML; charset=UTF-8");
            writer.write(gson.toJson(file.toString()));
        } catch (HibernateException e) {
            e.printStackTrace();
            resp.setContentType("text/HTML; charset=UTF-8");
            writer.write(gson.toJson("Не удалось создать \"File\" "));
        }

    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter writer = resp.getWriter();
        File file;

        req.setCharacterEncoding(ENCODING_UTF_8);

        final long id = Long.parseLong(req.getParameter("id"));
        final long eventId = Long.parseLong(req.getParameter("event_id"));
        final String fileName = req.getParameter("file_name");
        final String filePath = req.getParameter("file_path");

        if (id == 0 || eventId == 0 || fileName == null || filePath == null) {
            resp.setContentType("text/HTML; charset=UTF-8");
            writer.write(gson.toJson("Не удалось изменить данные"));
            return;
        }

        try {
            file = fileService.update(id, eventId, filePath, fileName);
            resp.setContentType("text/HTML; charset=UTF-8");
            writer.write(gson.toJson(file.toString()));
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
            fileService.deleteById(id);
            resp.setContentType("text/HTML; charset=UTF-8");
            writer.write(gson.toJson("Данные удалены"));
        } catch (HibernateException e) {
            e.printStackTrace();
            resp.setContentType("text/HTML; charset=UTF-8");
            writer.write(gson.toJson("Не удалось удалить данные"));
        }
    }

}
