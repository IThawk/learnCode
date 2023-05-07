<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>员工列表</title>
    <%
        pageContext.setAttribute("APP_PATH", request.getContextPath());
    %>
    <script type="text/javascript" src="${APP_PATH}/static/js/jquery-2.0.3.min.js"></script>
    <link href="${APP_PATH}static/bootstrap-3.3.4-dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="${APP_PATH}static/bootstrap-3.3.4-dist/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <h1>gupao-ssm</h1>
        </div>
    </div>

    <div class="row">
        <div class="col-md-4 col-md-offset-8">
            <button type="button" class="btn-primary">新增</button>
            <button type="button" class="btn-danger">删除</button>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <table class="table table-hover">
                <tr>
                    <th>#</th>
                    <th>empName</th>
                    <th>gender</th>
                    <th>email</th>
                    <th>deptName</th>
                    <th>操作</th>
                </tr>
                <c:forEach items="${pageInfo.list}" var="emp">
                    <tr>
                        <th>${emp.empId}</th>
                        <th>${emp.empName}</th>
                        <th>${emp.gender=="M"?"男":"女"}</th>
                        <th>${emp.email}</th>
                        <th>${emp.department.deptName}</th>
                        <th>
                            <button type="button" class="btn btn-primary btn-sm"><span
                                    class="glyphicon glyphicon-pencil"
                                    aria-hidden="true"></span>编辑
                            </button>
                            <button type="button" class="btn btn-danger btn-sm"><span class="glyphicon glyphicon-trash"
                                                                                      aria-hidden="true">删除
                            </button>
                        </th>
                    </tr>
                </c:forEach>
            </table>
        </div>

    </div>
    <div class="row">
        <%-- 分页信息--%>
        <div class="col-md-6">
            当前${pageInfo.pageNum}页，总共有${pageInfo.pages}页，总共有${pageInfo.total}条记录
        </div>
        <div class="col-md-6">
            <nav aria-label="Page navigation">
                <ul class="pagination">
                    <li>
                        <a href="${APP_PATH}/emps?pn=1">
                            首页
                        </a>
                    </li>
                    <c:if test="${pageInfo.hasPreviousPage}">
                        <li>
                            <a href="${APP_PATH}/emps?pn=${pageInfo.pageNum-1}" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                    </c:if>
                    <c:forEach items="${pageInfo.navigatepageNums}" var="page_Number">
                        <c:if test="${page_Number==pageInfo.pageNum}">
                            <li class="active"><a href="#">${page_Number}</a></li>
                        </c:if>
                        <c:if test="${page_Numbe!=pageInfo.pageNum}">
                            <li><a href="${APP_PATH}/emps?pn=${page_Number}">${page_Number}</a></li>
                        </c:if>
                    </c:forEach>

                    <c:if test="${pageInfo.hasNextPage}">
                        <li>
                            <a href="${APP_PATH}/emps?pn=${pageInfo.pageNum+1}" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </c:if>
                    <li>
                        <a href="${APP_PATH}/emps?pn=${pageInfo.pages}">
                            末页
                        </a>
                    </li>
                </ul>
            </nav>

        </div>

    </div>
</div>

</body>
</html>
