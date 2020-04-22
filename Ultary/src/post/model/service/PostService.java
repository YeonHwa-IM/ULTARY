package post.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import member.model.vo.Media;
import post.model.dao.PostDAO;
import post.model.vo.Post;

public class PostService {

	public int insertPost(Post p, ArrayList<Media> fileList) {
		Connection conn = getConnection();
		
		PostDAO dao = new PostDAO();
		
		int result1 = dao.insertPost(conn, p);
		if(!fileList.isEmpty()) {
			int result2 = dao.insertMedia(conn, fileList);
			
			if(result1 > 0 && result2 > 0) {
				commit(conn);
			} else {
				rollback(conn);
			}
		} else {
			if(result1 > 0) {
				commit(conn);
			} else {
				rollback(conn);
			}
		}
		close(conn);
		
		return result1;
	}

	public ArrayList selectTList(int i, String memberId) {
		
		Connection conn = getConnection();
		ArrayList list = null;
		PostDAO dao = new PostDAO();
		
		if(i == 1) {
			list = dao.selectPList(conn, memberId);
		} else {
			list = dao.selectMList(conn, memberId);
		}
		close(conn);
		
		return list;
	}

	public int insertProfile(Media m) {
		Connection conn = getConnection();
		
		int result2 = new PostDAO().deleteorigProfile(conn, m.getMemberId());
		int result1 = 0;
		
		if(result2 > 0) {
			result1 = new PostDAO().insertProfile(conn, m);
			if(result1 > 0) {
				commit(conn);
			} else {
				rollback(conn);
			}
		} else {
			rollback(conn);
		}
		return result1;
	}

	public Media selectProImg(String memberId) {
		Connection conn = getConnection();
		
		Media proImg = new PostDAO().selectProImg(conn, memberId);
		
		close(conn);
		
		return proImg;
	}

	public int postLike(int postNum, String memberId) {
		Connection conn = getConnection();
		
		ResultSet rset = new PostDAO().selectLikePost(conn, postNum, memberId);
		int postLike;
		
		int result1 = 0;
		int result2 = 0;
		
		if(rset != null) {
			result1 = new PostDAO().likeDown(conn, postNum);
			result2 = new PostDAO().deleteLikePost(conn, postNum, memberId);
			if(result1 > 0 && result2 > 0) {
				commit(conn);
			} else {
				rollback(conn);
			}
		} else {
			result1 = new PostDAO().likeUp(conn, postNum);
			result2 = new PostDAO().insertLikePost(conn, postNum, memberId);
			if(result1 > 0 && result2 > 0) {
				commit(conn);
			} else {
				rollback(conn);
			}
		}
		postLike = new PostDAO().selectPostLike(conn, postNum);
		
		close(conn);
		return postLike;
	}
	
	//커뮤니티 부분--------------- 
	//게시판 별 글 목록 조회
	public int getListCount(int categorynum) {
		Connection conn = getConnection();
		
		int result = new PostDAO().getListCount(conn,categorynum);
		
		close(conn);
		
		return result;
	}
	
	
	public ArrayList<Post> selectList(int currentPage, int boardLimit,int categorynum) {
		Connection conn = getConnection();
		
		ArrayList<Post> list = new PostDAO().selectList(conn,currentPage,boardLimit,categorynum);
		
		close(conn);
		
		return list;
	}

	public ArrayList<Post> selectAllList(int currentPage, int boardLimit) {
		// 공지사항을 제외한 모든 글 모아보기
		Connection conn = getConnection();
		ArrayList<Post> AllList = new PostDAO().selectAllList(conn,currentPage,boardLimit);
		
		close(conn);
		
		return AllList;
	}
	// 공지사항을 제외한 모든 글 개수
		public int getAllListCount() {
			Connection conn= getConnection();
			
			int result = new PostDAO().getAllListCount(conn);
			
			close(conn);
			
			return result;
		}
		
		// 게시글 상세보기
	   public Post selectPostDetail(int pno) {
		      Connection conn = getConnection();
		      PostDAO dao = new PostDAO();
		      Post post = null;
		      
		      //조회수 증가 
		      int result = dao.updatePostCount(conn,pno); 
		      
		      if(result > 0) {
		         post =dao.selectPostDetail(conn,pno);
		         if(post != null) {
		            commit(conn);
		         } else {
		            rollback(conn);
		         }
		      } else {
		         rollback(conn);
		      }
		      
		      return post;
		   }

	   	//게시글 상세보기에서 사진이 있을때
		   public ArrayList<Media> selectphoto(int pno) {
		      Connection conn = getConnection();
		      ArrayList<Media> list = new PostDAO().selectphoto(conn,pno);
		      
		      close(conn);
		      
		      return list;
		   }
		  /////// 추가한거 /////////
		// 검색 결과 게시물 총 개수

		public int getSearchListCount(String searchtext, int categorynum, String searchcon, int date) {
			Connection conn = getConnection();
			PostDAO dao = new PostDAO();
			int result = dao.getSearchListCount(conn,searchtext,categorynum,searchcon,date);
			
			close(conn);
			return result;
		}
		
		// 검색결과 게시판 목록
		public ArrayList<Post> selectSearchList(String searchtext, int categorynum, String searchcon, int date,
				int currentPage, int boardLimit) {
			Connection conn = getConnection();
			PostDAO dao = new PostDAO();
			ArrayList<Post> list = dao.selectSearchList(conn,searchtext,categorynum,searchcon,date,currentPage,boardLimit);
			
			close(conn);
			return list;
		}
		
		// 모아보기 게시판 사진 불러오기
		public ArrayList<Media> selectAllMList() {
			Connection conn = getConnection();
			ArrayList<Media> list = new PostDAO().selectAllMList(conn);
			
			return list;
		}
		
		// 게시판 업데이트하기
		public int updatePost(String title,String content,String postRange,int pno, ArrayList<Media> fileList,ArrayList<Media> originList) {
			Connection conn = getConnection();
			
			PostDAO dao = new PostDAO();
			
			int result1 = dao.updatePost(conn,title,content,postRange,pno); //게시물 내용 업데이트
			
			int result2 = 0;
						
			int count = dao.photostatus(conn, pno); // 게시물에 사진이 있는지 없는지 확인
			
			originList.addAll(fileList);
			if(!fileList.isEmpty()) {
				if(count != 0 ) {
					result2 = dao.deleteMedia(conn,pno);
				} 
				int result3 = dao.deleteinsertMedia(conn,originList,pno);
				
				if(result1 > 0 && result3 > 0) {
					commit(conn);
				} else {
					rollback(conn);
				}
			} else {
				if(result1 > 0) {
					commit(conn);
				} else {
					rollback(conn);
				}
			}
			close(conn);
			return result1;
		}	

}
