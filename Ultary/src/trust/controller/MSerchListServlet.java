package trust.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;
import member.model.vo.Media;
import member.model.vo.Member;
import post.model.service.PostService;
import trust.model.service.MatchingService;
import trust.model.vo.PageInfo;

/**
 * Servlet implementation class SerchBoard
 */
@WebServlet("/SerchMember.sc")
public class MSerchListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MSerchListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MatchingService service = new MatchingService();
		String loginId = ((Member)request.getSession().getAttribute("loginUser")).getMemberId();
		
		String h_area1 = request.getParameter("h_area1");
		String h_area ="";
		String h_area2 = request.getParameter("h_area2");
		String h_area3 = request.getParameter("h_area3");
		
		switch(h_area1) {
		case "1" :h_area ="서울특별시"; break;
		case "2" :h_area ="부산광역시"; break;
		case "3" :h_area ="대구광역시"; break;
		case "4" :h_area ="인천광역시"; break;
		case "5" :h_area ="광주광역시"; break;
		case "6" :h_area ="대전광역시"; break;
		case "7" :h_area ="울산광역시"; break;
		case "8" :h_area ="강원도"; break;
		case "9" :h_area ="경기도"; break;
		case "10" :h_area ="경상남도"; break;
		case "11" :h_area ="경상북도"; break;
		case "12" :h_area ="전라남도"; break;
		case "13" :h_area ="전라북도"; break;
		case "14" :h_area ="제주특별자치도"; break;
		case "15" :h_area ="충청남도"; break;
		case "16" :h_area ="충청북도"; break;
		}
		
		String address = "%"+h_area+" "+h_area2+" "+h_area3;
		int check01 = Integer.parseInt(request.getParameter("check01"));
		String[] check1 =	request.getParameterValues("check1");
		System.out.println(check1.length);
		String serch1 = "";
		if(check1.length > 1) {
			serch1 = String.join(", ",check1);
			System.out.println(serch1);
		} else {
			serch1 = check1[0];
		}

	
		String[] check2 = request.getParameterValues("check2");
		String serch2 = "";
		if(check2.length > 1) {
			serch2 = String.join(",",check2);
		} else {
			serch2 = check2[0];
		} 
		Member member = new Member();
		member.setMemberId(loginId);
		member.setAddress(address);
		member.setTrustfield(serch1);
		member.setTrustmeans(check01);
		
		String pet = new String(serch2);
		
		
		
		int listCount = service.getListCount(member,pet,loginId);
		int currentPage;	// 현재 페이지
		int pageLimit = 10;		// 한페이지의 표시된 페이징 수
		int maxPage;		// 전체 페이지 중 마지막 페이지
		int startPage;		// 페이징된 페이지 중 시작 페이지
		int endPage;		// 페이징된 페이지 중 마지막 페이지
		int MemberLimit = 10;		// 한 페이지에 보일 게시글 수
		currentPage =1;
		
		if(request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		maxPage = (int)((double)listCount/pageLimit+0.9);
		
		startPage = (((int)((double)currentPage/pageLimit+0.9))-1)*pageLimit +1;
		
		endPage = pageLimit + startPage-1;
		if(maxPage < endPage) {
			endPage= maxPage;
		}
		PageInfo pi = new PageInfo(currentPage,listCount,pageLimit,maxPage,startPage,endPage,MemberLimit);
		
		ArrayList<Member> list = service.serchMember(currentPage,MemberLimit,member,pet);
		
		
		String page= null;
		if(list != null) {
			
			ArrayList<Member> markList = new MemberService().selectMarkMember(loginId);
			ArrayList<Media> proImg = new PostService().selectAllproimg();
			
			page="views/trustMatch/matching02.jsp";
			request.setAttribute("list", list);
			request.setAttribute("pi", pi);
			request.setAttribute("check1", check1);
			request.setAttribute("mark", markList);
			request.setAttribute("proImg", proImg);
			
		} else {
			page= "views/common/errorPage.jsp";
			request.setAttribute("msg","게시판 조회에 실패하였습니다");
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
