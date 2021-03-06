package trust.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.ArrayList;

import member.model.dao.MemberDAO;
import member.model.vo.Media;
import member.model.vo.Member;
import member.model.vo.Pet;
import trust.model.dao.MatchingDAO;
import trust.model.vo.TrustPost;
import trust.model.vo.TrustReview;


public class MatchingService {

	public int getListCount(Member member, String pet, String loginId) {
		Connection conn = getConnection();
		int result = new MatchingDAO().getListCount(conn,member,pet,loginId);
		close(conn);
		return result;
	}
	
	public ArrayList<Member> serchMember(int currentPage, int memberLimit, Member member, String pet) {
		Connection conn = getConnection();
		
		ArrayList<Member> m = new MatchingDAO().MemberList(conn,currentPage,member,memberLimit,pet);
		close(conn);
		return m;
	}

	public Member DetailView(String memberid) {
		Connection conn = getConnection();
		
		Member m = new MatchingDAO().DetailView(conn,memberid);
		if(m != null) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return m;
	}

	public Pet DetailPet(String memberId) {
		Connection conn =getConnection();
		
		Pet pet = new MatchingDAO().DetailPet(conn,memberId);
		
		if(pet != null) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return pet;
	}

	public Member MemberDetail(String memberid) {
		Connection conn = getConnection();
		
		Member m = new MatchingDAO().MemberDetail(conn,memberid);
		
		if(m != null) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		
		return m;
	}

	public int sendTustpost(TrustPost tp) {
		Connection conn = getConnection();
		
		int result = new MatchingDAO().sendTrustpost(conn,tp);
		
		if(result >0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public ArrayList<TrustPost> TpostBalshin(String loginUser) {
		Connection conn = getConnection();
		ArrayList<TrustPost> balshin = new MatchingDAO().MyBalshin(conn,loginUser);
		
		if(balshin != null) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return balshin;
	}

	public ArrayList<TrustPost> TpostSushin(String loginUser) {
		Connection conn = getConnection();
		ArrayList<TrustPost> susin = new MatchingDAO().MySusin(conn,loginUser);
		
		if(susin != null) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return susin;
	}

	public int changePosition(int position, int tpostNum) {
		Connection conn = getConnection();
		
		int result = new MatchingDAO().changePosition(conn,position,tpostNum);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public Member RwriteView(int tpostnum) {
		Connection conn = getConnection();
		
		TrustPost tp = new MatchingDAO().serchtp(conn,tpostnum);
		
		Member m = new MatchingDAO().MemberDetail(conn, tp.getSushin());
		
		if(m !=null) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return m;
	}

	public int review(TrustReview tr, String user, int tpnum) {
		Connection conn = getConnection();
		
		int result = new MatchingDAO().review(conn,tr);
		int result2 = 0;
		if(result > 0) {
			commit(conn);
			int trnum = new MatchingDAO().gettrnum(conn,user);
			result2= new MatchingDAO().insertTpnum(conn,tpnum,trnum);
			
			if(result2>0) {
				commit(conn);
			}else {
				rollback(conn);
			}
		}else {
			rollback(conn);
		}
		close(conn);
		return result2;
	}

	public ArrayList<TrustReview> trList(String loginUser) {
		Connection conn = getConnection();
		
		ArrayList<TrustReview> tr = new MatchingDAO().trList(conn,loginUser);
		
		close(conn);
		return tr;
	}

	public TrustReview trusReviewSerch(int trnum) {
		Connection conn = getConnection();
		
		TrustReview tr = new MatchingDAO().serchtr(conn,trnum);
		
		if(tr != null) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return tr;
	}

	public int updatetr(TrustReview tr) {
		Connection conn = getConnection();
		
		int result = new MatchingDAO().updatetr(conn,tr);
		
		if(result > 0 ) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public int deletetr(int trnum) {
		Connection conn = getConnection();
		
		int result = new MatchingDAO().deletetr(conn,trnum);
		
		if(result >0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public ArrayList<TrustReview> trReview(String memberid) {
		Connection conn = getConnection();
		
		ArrayList<TrustReview> trList = new MatchingDAO().trReview(conn,memberid);
		
		if(trList != null) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		return trList;
	}

	public TrustPost tpview(int tpnum) {
		Connection conn = getConnection();
		
		TrustPost tp = new MatchingDAO().tpview(conn,tpnum);
		
		if(tp !=null) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		return tp;
	}

	public int removetp(int tpnum) {
		Connection conn = getConnection();
		
		int result = new MatchingDAO().deletetp(conn,tpnum);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		return result;
	}

	public Pet SerchPet(int petnum) {
		Connection conn =getConnection();
		
		Pet pet = new MatchingDAO().SerchPet(conn,petnum);
	
		
		close(conn);
		return pet;
	}

	public Media serchpetImg(int petnum) {
		Connection conn = getConnection();
		
		Media m = new MatchingDAO().serchpetImg(conn,petnum);
		
		close(conn);
		return m;
	}

	public ArrayList<Media> selectAllImg() {
		Connection conn = getConnection();
		
		ArrayList<Media> allImg = new MatchingDAO().selectAllImg(conn);
		close(conn);
		return allImg;
	}





}
