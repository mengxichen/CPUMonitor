package com.mengxi.demo.sysdashboard.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mengxi.demo.sysdashboard.accessor.SystemInfoAccessor;

public class SystemInfoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SystemInfoAccessor accessor = SystemInfoAccessor.getInstance();

        // E.g. includeCpu=1&includeMem=1&interval=1
        String includeCpuParam = request.getParameter("includeCpu");
        String includeMemParam = request.getParameter("includeMem");
        String intervalParam = request.getParameter("interval");

        boolean includeCpu = "1".equals(includeCpuParam);
        boolean includeMem = "1".equals(includeMemParam);
        int interval = 1; // Default is 1 second
        try {
            interval = Integer.parseInt(intervalParam);
        } catch (Exception e) {
            // No-op
        }

        String resultJson = accessor.getSystemInfoJson(includeCpu, includeMem, interval);
        response.setContentType("application/json");
        response.getWriter().write(resultJson);
    }

}
