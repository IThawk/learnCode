<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="test/register" method="post">
    姓名:<input type="text" name="name"> <br>
    年龄:<input type="text" name="age"> <br>
    <input type="submit" value="注册"> <br>
</form>
<hr>
<form action="test/find" method="get">
    id:<input type="text" name="id"> <br>
    <input type="submit" value="查询"> <br>
</form>
<hr>
<a href="test/count">查询学生总数</a>

</body>
</html>
