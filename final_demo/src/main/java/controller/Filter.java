package controller;

import com.mysql.cj.Session;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@WebFilter("/*")
public class Filter implements javax.servlet.Filter {
   /* Map<String, Long> map = new ConcurrentHashMap<>();*/
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String[] urls ={"/user/login.html","/register.html","/userServlet","/forget.html","/newcom.html","/comServlet?method=getAllCom","/com.html"
        ,"/comServlet"};
        HttpServletRequest request =(HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        String role = (String)request.getSession().getAttribute("role");
        String url = request.getRequestURI().toString();
        String ip = request.getRemoteAddr();
        //得到id
        String id = session.getId();
        //ip地址
      /*  if(map.containsKey(id)){
            map.put(id,(System.currentTimeMillis()));
        }//已经存了session的情况
        else {
            Long l = map.get(id);
            if((System.currentTimeMillis()-l)/1000>2){
                //大于两秒
                response.getWriter().write("访问太频繁了");
                return;
            }
        }*/
        System.out.println(url);
        for (String s : urls) {
            if(url.contains(s)){
                filterChain.doFilter(request,response);
                return;
            }
        }
        String username =(String) request.getSession().getAttribute("username");
      //  System.out.println("拦截器登录");
         System.out.println(username);
        if(username!=null){
            System.out.println("成功进入");
            //后台管理员资源
            if(url.contains("/admin")){
                //管理员通道
                if("admin".equals(role)) {
                    filterChain.doFilter(request, response);
                }else{
                    response.sendRedirect("/com.html");

                }
                return;
            }
            //不是后台管理员资源
            filterChain.doFilter(request,response);
        }else{
            System.out.println("资源被拦截了");
            response.sendRedirect("/com/com.html");
            //request.getRequestDispatcher("login.jsp?error=5").forward(request,response);
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
