package com.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Test2Servlet extends BaseServlet{

    public void check400(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);

    }
    public void check500(HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_GATEWAY);
    }

    //以此类推

}
