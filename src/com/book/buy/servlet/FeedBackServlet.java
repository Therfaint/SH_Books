package com.book.buy.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.book.buy.dao.FeedBackDao;
import com.book.buy.dao.InformDao;
import com.book.buy.factory.FeedBackDaoImplFactory;
import com.book.buy.factory.InformDaoImplFactory;
import com.book.buy.utils.NewDate;
import com.book.buy.vo.FeedBackVo;
import com.book.buy.vo.InformVo;

/**
 * Servlet implementation class FeedBack
 */
@WebServlet("/FeedBack")
public class FeedBackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FeedBackServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(true);
		Integer userId=-1;
		String description=null;
		
		 try {
			userId=Integer.parseInt(request.getParameter("userId"));//用户名
			description=request.getParameter("description");//反馈信息
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			String href="./pages/managePage/controlCenter/controlCenter.jsp";
			  out.print("<script language='javascript'>alert('输入有误！');window.location.href='"
						+ href + "';</script>");
			  out.flush();
				out.close();
		}
		
		if(description==""){String href="./pages/managePage/controlCenter/controlCenter.jsp";out.print("<script language='javascript'>alert('输入有误！');window.location.href='"
				+ href + "';</script>");
		 out.flush();
			out.close();}
		  String date = NewDate.getDate(new Date());
		  
		 
	       FeedBackVo feedBackVo = new FeedBackVo(userId,description,date);
		 FeedBackDao FeedBackDaoImpl = FeedBackDaoImplFactory.getFeedBackDaoImpl();
		try {
		 FeedBackDaoImpl.addFeedBack(feedBackVo);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		FeedBackDaoImpl.close();
		String href = "./pages/managePage/controlCenter/controlCenter.jsp";
		out.print("<script language='javascript'>alert('反馈成功！');window.location.href='"
				+ href + "';</script>");
		
		  

		out.flush();
		out.close();
	  
	}
}
