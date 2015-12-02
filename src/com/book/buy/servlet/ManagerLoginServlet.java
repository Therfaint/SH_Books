package com.book.buy.servlet;

import com.book.buy.dao.ManagerDao;
import com.book.buy.factory.ManagerDaoImpFactory;
import com.book.buy.vo.ManagerVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Created by violet on 2015/11/4.
 * 用于管理员登陆时
 * arguement:username、password
 */
@WebServlet(name = "ManagerLoginServlet", urlPatterns = "/managerlogin")
public class ManagerLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        String username = request.getParameter("username");
        String pwd = request.getParameter("pwd");

        ManagerDao mdao = ManagerDaoImpFactory.getManagerDaoImp();
        PrintWriter out = response.getWriter();

        ManagerVo mvo = new ManagerVo();
        String href = "";

        try {
            mvo = mdao.getPwdByName(username);
            if (null == mvo) {
                //提示不是管理员
                href = "/SH_Books/pages/managerPage/login/loginManager.jsp";
                out.print("<script language='javascript'>alert('该用户不是管理员');"
                        + "window.location.href='" + href + "';</script>");
            } else {
                if (pwd.equals(mvo.getPassword())) {
                    request.getSession().setAttribute("username", username);
                    request.getSession().setAttribute("userid", mvo.getId());

                    mdao.close();

                    response.sendRedirect("pages/managerPage/login/loginSucc.jsp");
                } else {
                    href = "";
                    out.print("<script language='javascript'>alert('该用户不存在！请先注册！！！');"
                            + "window.location.href='" + href + "';</script>");
                    mdao.close();
                    response.sendRedirect("pages/managerPage/login/loginFailure.jsp");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
