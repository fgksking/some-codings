package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {

    @Override
    public void service(ServletRequest req, ServletResponse res) throws IOException {

        Logger logger = LoggerFactory.getLogger(getClass());
        String methodName = req.getParameter("method");
        req.setCharacterEncoding("UTF-8");
        res.setContentType("text/html;charset=UTF-8");
        if(methodName==null){
            logger.debug("传递正确的参数");
            throw new RuntimeException("请传递method的正确参数");
        }

        try {
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);

            logger.info("调用了"+methodName);
            method.invoke(this,req,res);


        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }



    }
}
