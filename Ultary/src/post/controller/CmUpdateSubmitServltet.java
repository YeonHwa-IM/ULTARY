package post.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;

import common.MyFileRenamePolicy;
import member.model.vo.Media;
import member.model.vo.Member;
import post.model.service.PostService;
import post.model.vo.Post;

/**
 * Servlet implementation class CmUpdateSubmitServltet
 */
@WebServlet("/cmUpdate.po")
public class CmUpdateSubmitServltet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CmUpdateSubmitServltet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int pNum = Integer.parseInt(request.getParameter("pNum"));
		String title = request.getParameter("title");
		int range = Integer.parseInt(request.getParameter("postRange"));
		String content = request.getParameter("postContent");
		int categorynum = Integer.parseInt(request.getParameter("categorynum"));
		
		Post p = new Post(pNum, title, content, range, categorynum);
		
		int result = new PostService().updatePost(p);
		
		String page = "";
		if(result > 0) {
			page="/cmdetail.po?pno=" + pNum;
		} else {
			page="views/common/errorPage.jsp";
			request.setAttribute("msg", "게시글 수정에 실패");
		}
	
		RequestDispatcher view = request.getRequestDispatcher(page);
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
