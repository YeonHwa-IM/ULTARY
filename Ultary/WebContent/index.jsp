<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ULTARY</title>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
</head>
<body>
<script>
   $(function(){ 
      location.href = "<%= request.getContextPath() %>/main.ini";
   });
</script>
</body>
</html>