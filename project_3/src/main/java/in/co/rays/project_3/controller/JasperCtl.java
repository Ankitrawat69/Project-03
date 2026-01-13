package in.co.rays.project_3.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.impl.SessionImpl;

import in.co.rays.project_3.dto.UserDTO;
import in.co.rays.project_3.util.HibDataSource;
import in.co.rays.project_3.util.JDBCDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@WebServlet(name = "JasperCtl", urlPatterns = { "/ctl/JasperCtl" })
public class JasperCtl extends BaseCtl {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            /* Load properties */
            ResourceBundle rb =
                ResourceBundle.getBundle("in.co.rays.project_3.bundle.system");

            /* ✅ Docker-safe jrxml loading */
            InputStream jrxmlStream =
                Thread.currentThread()
                      .getContextClassLoader()
                      .getResourceAsStream("reports/A4.jrxml");

            if (jrxmlStream == null) {
                throw new RuntimeException("A4.jrxml not found in classpath");
            }

            JasperReport jasperReport =
                JasperCompileManager.compileReport(jrxmlStream);

            /* Session check */
            HttpSession session = request.getSession(false);
            UserDTO dto = (UserDTO) session.getAttribute("user");

            if (dto == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        "User not logged in");
                return;
            }

            /* Parameters */
            Map<String, Object> map = new HashMap<>();
            map.put("ID", 1L);

            /* DB connection */
            java.sql.Connection conn = null;
            String database = rb.getString("DATABASE");

            if ("Hibernate".equalsIgnoreCase(database)) {
                conn = ((SessionImpl) HibDataSource.getSession()).connection();
            } else if ("JDBC".equalsIgnoreCase(database)) {
                conn = JDBCDataSource.getConnection();
            }

            if (conn == null) {
                throw new RuntimeException("Database connection is null");
            }

            /* Fill report */
            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, conn);

            /* Export PDF */
            byte[] pdf =
                JasperExportManager.exportReportToPdf(jasperPrint);

            /* Response */
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition",
                    "inline; filename=marksheet.pdf");

            ServletOutputStream out = response.getOutputStream();
            out.write(pdf);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServletException, IOException {
    }

    @Override
    protected String getView() {
        return null;
    }
}
