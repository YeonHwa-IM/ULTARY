<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, member.model.vo.*, post.model.vo.*"%>
<%
	Member loginUser = (Member)session.getAttribute("loginUser");
	Media proImg = (Media)session.getAttribute("proImg");
	ArrayList<Post> pList = (ArrayList<Post>)request.getAttribute("pList");
	ArrayList<Media> mList = (ArrayList<Media>)request.getAttribute("mList");
	ArrayList<PostComment> pcList = (ArrayList<PostComment>)request.getAttribute("pcList");
	ArrayList<CAns> cList = (ArrayList<CAns>)request.getAttribute("cList");
	ArrayList<Media> proList = (ArrayList<Media>)request.getAttribute("proList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내울타리 타임라인</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/myUltary/ultaryMain.css">
	<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
</head>
<body>
<script>
	$(function(){
		ansbull = true;
		ansempty = true;
		cNumber = ""; // 댓글 번호
	});
</script>
	<%@ include file="chat.jsp" %>
	<div id="all">
		<div id="all-wrap">
	<%@ include file="myUltaryheader.jsp" %>
	<%@ include file="navaside.jsp" %>
	
		<div id="topBtn">
			<a href="#all"><img src="<%= request.getContextPath() %>/image/top버튼.png" style="width:80%;height:80%"></a>
		</div>

			<section>
				<div class="sectiondiv">
				<% if(pList.isEmpty()){ %>
				<h1>조회할 게시물이 없음</h1>
				<% } else{ %>
					<% for(int i = 0; i < pList.size(); i++) { 
						Post p = pList.get(i); 
						String category = "";
						switch(p.getCategorynum()){
						case 2 : category = "펫일상"; break;
						case 3 : category = "펫지식"; break;
						case 4 : category = "펫리뷰"; break;
						case 5 : category = "펫분양"; break;
						}%>
					<div class="content1">
						<div class="writer">
							<div class="sectionimg">
								<% if(proImg != null){ %>
								<img class="sectionpic" src="<%= request.getContextPath() %>/uploadFiles/<%= proImg.getWebName() %>">
								<% } else{ %>
								<img class="sectionpic" src="<%= request.getContextPath() %>/image/프로필.png">
								<% } %>
								<div class="sectionNick"><%= p.getMemberid() %></div>
							</div>
							<div class="contentTitle"><%= p.getPostTitle() %></div>
							<div id="cDelete<%= i %>" class="cDelete">삭제</div>
							<div id="cModify<%= i %>" class="cDelete">수정</div>
							<div class="writerR">게시일 | <%= p.getPostDate() %></div>
						</div>
						
						<% ArrayList<Media> imgList = new ArrayList<Media>();
							for(int j=0;j<mList.size();j++) { 
								Media m = mList.get(j); %>
								<% if(m.getPostNum() == p.getPostNum()) {
									imgList.add(m); 
								}
							}
									if(!imgList.isEmpty()) {%>
						<div class="media">
							<div class="slide" id="slide<%= i %>">
						      <div id="back<%= i %>" class="slideBtn"><img src="<%= request.getContextPath() %>/image/왼쪽 화살표.png" alt="" width="50"></div>
						      <ul>
						      <% for(int z=0;z<imgList.size();z++){
						      	Media real = imgList.get(z); %>
						        <li><img src="<%= request.getContextPath() %>/uploadFiles/<%= real.getWebName() %>" alt="" width="550" height="350"></li>
						      <% } %>
						      </ul>
						      <div id="next<%= i %>" class="slideBtn"><img src="<%= request.getContextPath() %>/image/오른쪽 화살표.png" alt="" width="50"></div>
						    </div>
						</div>
								<% } %>
						<div class="content">
							<p><%= p.getPostContent() %></p>
						</div>
						<div class="explain">
							<div id="petlife"><%= category %></div>
							<div class="expansionbox">카테고리</div>
							<div id="likeBtn<%= i %>" class="expansionbox"><%= p.getPostLike() %> like</div>
						</div>
						<div id="comment<%= i %>"class="comment">
							<div class="comment0">댓글</div>
							<hr>
							
							<% if(!pcList.isEmpty()){ %>
								<% for(int x=0;x<pcList.size();x++){ 
									PostComment pc = pcList.get(x);%>
								
									<% if(p.getPostNum() == pc.getPostNum()) { %>
							<div class="comment1">
								<div class="comment2-1">
									<% if(!proList.isEmpty()){ %>
										<% for(int o=0;o<=proList.size();o++) { 
											if(o==proList.size()){ %>
									<img class="comment2-1img" src="<%= request.getContextPath() %>/image/프로필.png">
											<% break;
											}
											Media cProimg = proList.get(o);
											if(cProimg.getMemberId().equals(pc.getMemberid())){ %>
									<img class="comment2-1img" src="<%= request.getContextPath() %>/uploadFiles/<%= cProimg.getWebName() %>">		   
											<% break;
											} %>
										<% } %>
									<% } else{ %>
									<img class="comment2-1img" src="<%= request.getContextPath() %>/image/프로필.png">
									<% } %>
								</div>
								<div class="comment2-3"><%= pc.getMemberid() %></div>
								<div class="comment2-2">
									<%= pc.getcContent() %>
								</div>
								<div id="comment2-4<%= x %>" class="comment2-4"><%= pc.getcLike() %> like</div>
								<div id="comment2-5<%= x %>" class="comment2-5">답글달기</div>
								<% if(pc.getMemberid().equals(loginUser.getNickname())){ %>
								<div id="comment2-6<%= x %>" class="comment2-6">삭제</div>
								<% } %>
<script type="text/javascript">
   /*  댓글좋아요수! */
   
	$(function(){
		var cmlikeBtn = "#comment2-4"+<%= x %>;
		var cNum = <%= pc.getcNum() %>;
		
		$(cmlikeBtn).click(function(){
			$.ajax({
				url: 'commentlike.up',
				data: {cNum:cNum},
				success: function(data){
					$(cmlikeBtn).text(data+" like");
				}
			});
		});
		
	/* 댓글에 답글달기 버튼나오게하기 */
		var ansBtn = "#comment2-5"+<%= x %>;
		
		$(ansBtn).click(function(){
			var ansdiv = "#commentansdiv"+<%= i %>;
			var anstext = "#commentanstext"+<%= i %>;
			var ansget = "----- <%= pc.getMemberid() %> 님에게 답글달기 -----";
			
			ansbull = false;
			$(ansdiv).show();
			$(anstext).text(ansget);
			cNumber = "<%= pc.getcNum() %>";
		});
				
		/* --------답글버튼 없애기 ------------ */
		
		var ansdiv = "#commentansdiv"+<%= i %>;
		
		$('.commentansclose').click(function(){
			$(ansdiv).hide();
			ansbull = true;
			cNumber = "";
		});
		
		/* ---------댓글 삭제하기 -------------- */
		
		var cmDeleteBtn = "#comment2-6"+<%= x %>;
		
		$(cmDeleteBtn).click(function(){
			var result = confirm("댓글을 삭제하시겠습니까?");
			var cNum = <%= pc.getcNum() %>;
			
			if(result){
				alert("삭제하였습니다.");
				$.ajax({
					url: 'deleteComment.tl',
					data: {cNum:cNum},
					success: function(data){
						location.reload();
					}
				});
			} else{
				alert("삭제 취소");
			}
		});
	});
</script>
							<% if(!cList.isEmpty()){ %>
								<% for(int y=0;y<cList.size();y++){
										CAns ca = cList.get(y);%>
									<% if(pc.getcNum() == ca.getcNum()){ %>
								<div class="commentans">
									<div class="commentans2-0">ㄴ</div>
									<div class="commentans2-1">
									<% if(!proList.isEmpty()){ %>
										<% for(int o=0;o<=proList.size();o++) {
											if(o==proList.size()){ %>
										<img class="commentans2-1img" src="<%= request.getContextPath() %>/image/프로필.png">
											<% break; 
											}
											Media caProimg = proList.get(o);
											if(caProimg.getMemberId().equals(ca.getMemberid())){ %>
										<img class="commentans2-1img" src="<%= request.getContextPath() %>/uploadFiles/<%= caProimg.getWebName() %>">
											<% break;
											   } %>
										<% } %>
									<% } else{ %>
										<img class="commentans2-1img" src="<%= request.getContextPath() %>/image/프로필.png">
									<% } %>
									</div>
									<div class="commentans2-3"><%= ca.getMemberid() %></div>
									<div class="commentans2-2">
										<%= ca.getAnsContent() %>
									</div>
									<% if(ca.getMemberid().equals(loginUser.getNickname())){ %>
									<div id="commentans2-4<%= y %>"class="commentans2-4">삭제</div>
									<% } %>
								</div>
<script>
	$(function(){
	/* ---------답글 삭제하기 -------------- */
		
		var cmDeleteBtn = "#commentans2-4"+<%= y %>;
		
		$(cmDeleteBtn).click(function(){
			var result = confirm("답글을 삭제하시겠습니까?");
			var ansNum = <%= ca.getAnsNum() %>;
			
			if(result){
				alert("삭제하였습니다.");
				$.ajax({
					url: 'deleteCAns.tl',
					data: {ansNum:ansNum},
					success: function(data){
						location.reload();
					}
				});
			} else{
				alert("삭제 취소");
			}
		});
		
	});
</script>
									<% } %>
								<% } %>
							<% } %>
							</div>
									<% } %>
								<% } %>
							<% } %>
							
						</div>
						<div id="commentansdiv<%= i %>" class=commentansdiv>
							<span id="commentanstext<%= i %>"></span>
							<button class="commentansclose">x</button>
						</div>
						<div class="commenttextdiv">
								<div class="writercomment">
									<select id="commentrange<%= i %>" class="commentrange">
										<option value="1">전체공개</option>
										<option value="2">작성자만</option>
									</select>
								</div>
								<input id="commenttext<%= i %>" class="commenttext" type="text" placeholder="내용을 입력해주세요">
								<button id="commentsubmit<%= i %>"class="commentsubmit">댓글달기</button>
						</div>
					</div>
<script type="text/javascript">

	$(function(){
	/* ----------게시글 수정하기---------------------- */
		var modifyBtn = "#cModify"+<%= i %>;
		
		$(modifyBtn).click(function(){
			var result = confirm("게시글 수정하시겠습니까?");
			
			if(result){
				location.href = "<%= request.getContextPath() %>/postview.tl?pNum=<%= p.getPostNum() %>";
			} else{
				alert("삭제 취소");
			}
		});
		
	/* ---------- 게시글 삭제하기 ------------ */
		var deleteBtn = "#cDelete"+<%= i %>;
		
		$(deleteBtn).click(function(){
			var result = confirm("게시글을 삭제하시겠습니까?");
			var pNum = <%= p.getPostNum() %>;
			
			if(result){
				alert("삭제하였습니다.");
				$.ajax({
					url: 'deletePost.tl',
					data: {pNum:pNum},
					success: function(data){
						location.reload();
					}
				});
			} else{
				alert("삭제 취소");
			}
			
		});
		
	/* ------------댓글 입력하기 ------------- */
		var commentsubmit = "#commentsubmit"+<%= i %>;
		var commenttextch = "#commenttext"+<%= i %>;
		
			$(commenttextch).keydown(function(key){
				var $commenttextcm = $(commenttextch).val();
				if($commenttextcm != ""){
					ansempty = true;
				} else{
					ansempty = false;
				}
				if(key.keyCode == 13){
					var commenttext = "#commenttext"+<%= i %>;
					var writer = '<%= loginUser.getMemberId() %>';
					var content = $(commenttext).val();
					var pNum = <%= p.getPostNum() %>;
					var $range = "#commentrange"+<%= i %>;
					var range = $($range).val();
					var pNic = '<%= p.getMemberid() %>';
					
					if(ansempty && ansbull){
						$.ajax({
							url: 'insertComment.tl',
							data: {writer:writer, pNum:pNum, content:content, range:range, pNic:pNic},
							success: function(data){
								location.reload();
							}
						});
					} else if(ansempty && !ansbull){
						$.ajax({
							url: 'insertCAns.tl',
							data: {pNum:pNum, cNumber:cNumber, writer:writer, content:content},
							success: function(data){
								location.reload();
							}
						});
					}
				}
			});
			$(commentsubmit).click(function(){
				var $commenttextcm = $(commenttextch).val();
				if($commenttextcm != ""){
					ansempty = true;
				} else{
					ansempty = false;
				}
				var commenttext = "#commenttext"+<%= i %>;
				var writer = '<%= loginUser.getMemberId() %>';
				var content = $(commenttext).val();
				var pNum = <%= p.getPostNum() %>;
				var $range = "#commentrange"+<%= i %>;
				var range = $($range).val();
				var pNic = '<%= p.getMemberid() %>';
				
				if(ansempty && ansbull){
					$.ajax({
						url: 'insertComment.tl',
						data: {writer:writer, pNum:pNum, content:content, range:range, pNic:pNic},
						success: function(data){
							location.reload();
						}
					});
				} else if(ansempty && !ansbull){
					$.ajax({
						url: 'insertCAns.tl',
						data: {pNum:pNum, cNumber:cNumber, writer:writer, content:content},
						success: function(data){
							location.reload();
						}
					});
				}
			});
	});
	
	/* ------------좋아요 올리기 ---------------- */
    
    $(document).ready(function(){
    	var likeBtn = "#likeBtn"+<%= i %>;
    	var postNum = <%= p.getPostNum() %>;
    	$(likeBtn).click(function(){
    		$.ajax({
    			url: 'postlike.up',
    			data: {postNum:postNum},
    			success: function(data){
    				$(likeBtn).text(data+" like");
    			}
    		});
    	});
    	
	/* -------------이미지 슬라이드-------------------------- */

      var imgs;
      var img_count;
      var img_position = 1;
      var slide = "#slide"+<%= i %>+" ul";
      var backbtn = "#back"+<%= i %>;
      var nextbtn = "#next"+<%= i %>;

      imgs = $(slide);
      $back = $(backbtn);
      $next = $(nextbtn);
      img_count = imgs.children().length;

      //버튼을 클릭했을 때 함수 실행
      $back.click(function () {
        back();
      });
      $next.click(function () {
        next();
      });

      function back() {
        if(1<img_position){
          imgs.animate({
            left:'+=550px'
          });
          img_position--;
        }
      }
      function next() {
        if(img_count>img_position){
          imgs.animate({
            left:'-=550px'
          });
          img_position++;
        }
      }
    });
</script>

					<% } %>
				<% } %>
				</div>
			</section>
			
<script type="text/javascript">
	$(function(){
		$('.slideBtn').hover(function(){
			$('.slideBtn').css("opacity", "1");
		}, function(){
			$('.slideBtn').css("opacity", "0");
		});
		
		/* ----------------무한스크롤------------ */
		stop = true;
		if(1500 > $('.sectiondiv').height()){
			stop = false;
		}
		$(window).scroll(function(){
			if(stop &&$(window).scrollTop() == $(document).height() - $(window).height() && ($('section').css("height") < $('.sectiondiv').css("height"))){
				$('section').css("height", "+=500px");
			}
		});
	});
</script>

		</div>
		<footer>ULTARY</footer>
	</div>
</body>
</html>