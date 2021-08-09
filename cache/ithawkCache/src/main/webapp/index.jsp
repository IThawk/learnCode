<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>员工列表</title>
    <%
        pageContext.setAttribute("APP_PATH", request.getContextPath());
    %>
    <script type="text/javascript" src="${APP_PATH }/static/js/jquery-2.0.3.min.js"></script>
    <link href="${APP_PATH }/static/bootstrap-3.3.4-dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="${APP_PATH }/static/bootstrap-3.3.4-dist/js/bootstrap.min.js"></script>
</head>
<body>

<!-- Modal 对话框   员工添加-->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">员工添加</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="empName_add_input" class="col-sm-2 control-label">员工姓名</label>
                        <div class="col-sm-10">
                            <input type="text" name="empName" class="form-control" id="empName_add_input"
                                   placeholder="empName">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email_add_input" class="col-sm-2 control-label">邮箱</label>
                        <div class="col-sm-10">
                            <input type="text" name="email" class="form-control" id="email_add_input"
                                   placeholder="email@163.com">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email_add_input" class="col-sm-2 control-label">性别</label>
                        <div class="col-sm-10">
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender1_add_input" value="M" checked="checked"> 男
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender2_add_input" value="F"> 女
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email_add_input" class="col-sm-2 control-label">部门名称 </label>
                        <div class="col-sm-4">
                            <select class="form-control" name="dId" id="dept_add_select">

                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="save_emps_btn">保存</button>
            </div>
        </div>
    </div>
</div>
<%--员工修改--%>
<!-- Modal 对话框-->
<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">员工修改</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="empName_add_input" class="col-sm-2 control-label">empName</label>
                        <div class="col-sm-10">
                            <p class="form-control-static" id="empName_update_static"></p>
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email_add_input" class="col-sm-2 control-label">邮箱</label>
                        <div class="col-sm-10">
                            <input type="text" name="email" class="form-control" id="email_update_input"
                                   placeholder="email@163.com">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email_add_input" class="col-sm-2 control-label">性别</label>
                        <div class="col-sm-10">
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender1_update_input" value="M" checked="checked">
                                男
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="gender2_update_input" value="F"> 女
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="email_add_input" class="col-sm-2 control-label">部门名称 </label>
                        <div class="col-sm-4">
                            <select class="form-control" name="dId" id="dept_update_select">

                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="emps_update_btn">更新</button>
            </div>
        </div>
    </div>
</div>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <h1>员工管理</h1>
        </div>
    </div>

    <div class="row">
        <div class="col-md-4 col-md-offset-8">
            <button type="button" class="btn-primary" id="add_emp_btn">新增</button>
            <button type="button" class="btn-danger" id="emp_delete_all_btn">删除</button>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <table class="table table-hover" id="emps_table">
                <thead>
                <tr>
                    <th>
                        <input type="checkbox" id="checkAll"/>

                    </th>
                    <th>序号</th>
                    <th>员工姓名</th>
                    <th>性别</th>
                    <th>邮箱</th>
                    <th>部门名称</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
        </div>

    </div>
    <div class="row">

        <div class="col-md-6" id="page_info_area">

        </div>
        <div class="col-md-6" id="page_nav_area">


        </div>

    </div>
</div>
<%--在页面加载完 发请求--%>
<script type="text/javascript">
    var totalRecord, currentPage;
    $(function () {
        $.ajax({
            url: "${APP_PATH}/emps",
            data: "pn=1",
            type: "GET",
            success: function (result) {
                //console.log(result);
                //1、解析并显示员工数据
                build_emps_table(result);
                //2、解析并显示分页数据
                build_page_info(result);
                //3、显示分页条
                build_page_nav(result);
            }

        })
    })
    function build_emps_table(result) {
        //清空之前的数据
        $("#emps_table tbody").empty();
        var emps = result.extend.pageInfo.list;
        $.each(emps, function (index, item) {
            // 回调函数，第一个参数代表索引  第二个参数代表 是 list里面的数据（每循环一次是一条）
            var checkBoxTd = $("<td><input type='checkbox' class='check_item'/></td>");
            var empIdTd = $("<td></td>").append(item.empId);
            var empNameTd = $("<td></td>").append(item.empName);
            var genderId = $("<td></td>").append(item.gender == 'M' ? "男" : "女");
            var emailTd = $("<td></td>").append(item.email);
            var deptNameTd = $("<td></td>").append(item.department.deptName);
            /*
             *  <button type="button" class="btn btn-primary btn-sm"><span
             class="glyphicon glyphicon-pencil"
             aria-hidden="true"></span>编辑
             </button>
             * */
            var editBtn = $("<button></button>").addClass("btn btn-primary btn-sm edit_btn")
                .append($("<span></span>").addClass("glyphicon glyphicon-pencil")).append("编辑");
            //为编辑按钮添加一个自定义属性，来表示当前员工id
            editBtn.attr("edit-id", item.empId);
            var deleteBtn = $("<button></button>").addClass("btn btn-danger btn-sm delete_btn")
                .append($("<span></span>").addClass("glyphicon glyphicon-trash")).append("删除");

            //为删除按钮添加当前删除员工的id
            deleteBtn.attr("del-id", item.empId);
            var btnTd = $("<td></td>").append(editBtn).append(" ").append(deleteBtn);

            //append方法执行完成后还是返回原来的元素
            $("<tr></tr>")
                .append(checkBoxTd)
                .append(empIdTd)
                .append(empNameTd)
                .append(genderId)
                .append(emailTd)
                .append(deptNameTd)
                .append(btnTd)
                .appendTo("#emps_table tbody");
        })
    }
    //显示分页信息
    function build_page_info(result) {
        //清空
        $("#page_info_area").empty();

        $("#page_info_area").append("当前" + result.extend.pageInfo.pageNum + "页，总共有" + result.extend.pageInfo.pages + " 页，总共有" + result.extend.pageInfo.total + " 条记录");
        totalRecord = result.extend.pageInfo.total;
        currentPage = result.extend.pageInfo.pageNum;
    }
    //显示分页条
    function build_page_nav(result) {
        //清空
        $("#page_nav_area").empty();
        var nav = $("<nav></nav>").addClass("Page navigation");
        var ul = $("<ul></ul>").addClass("pagination");
        var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href", "#"));

        var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;"));

        if (result.extend.pageInfo.hasPreviousPage == false) {
            firstPageLi.addClass("disabled");
            prePageLi.addClass("disabled");
        } else {
            firstPageLi.click(function () {
                to_page(1);
            })
            prePageLi.click(function () {
                to_page(result.extend.pageInfo.pageNum - 1);
            })
        }
        var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;"));

        var lastPageLi = $("<li></li>").append($("<a></a>").append("末页").attr("href", "#"));

        if (result.extend.pageInfo.hasNextPage == false) {
            nextPageLi.addClass("disabled");
            lastPageLi.addClass("disabled");
        } else {
            lastPageLi.click(function () {
                to_page(result.extend.pageInfo.pages);
            })
            nextPageLi.click(function () {
                to_page(result.extend.pageInfo.pageNum + 1);
            })
        }
        //页码号  "navigatepageNums":[1,2,3,4,5]
        ul.append(firstPageLi).append(prePageLi);
        $.each(result.extend.pageInfo.navigatepageNums, function (index, item) {
            var numLi = $("<li></li>").append($("<a></a>").append(item));
            if (result.extend.pageInfo.pageNum == item) {
                numLi.addClass("active");
            }
            numLi.click(function () {
                to_page(item);
            })
            ul.append(numLi);
        })

        ul.append(nextPageLi).append(lastPageLi);

        nav.append(ul);
        nav.appendTo("#page_nav_area");

    }
    //单击导航条 跳转到指定页码

    /* $(function () {
     to_page(1);
     });
     */
    function to_page(pn) {
        $.ajax({
            url: "${APP_PATH}/emps",
            data: "pn=" + pn,
            type: "GET",
            success: function (result) {
                //console.log(result);
                //1、解析并显示员工数据
                build_emps_table(result);
                //2、解析并显示分页数据
                build_page_info(result);
                //3、显示分页条
                build_page_nav(result);
            }

        })
    }
    //清空表单内容
    function reset_form(ele) {
        $(ele)[0].reset();
        $(ele).find("*").removeClass("has-error has-success");
        $(ele).find(".help-block").text("");
    }
    $("#add_emp_btn").click(function () {
        //表单重置(表单的数据和样式)
        // $("#myModal form")[0].reset();
        reset_form("#myModal form");
        //发送ajax请求，查出部门信息，显示在下拉列表中

        getDepts("#dept_add_select");
        //弹出对话框
        $("#myModal").modal({
            backdrop: "static"
        })
    })

    function getDepts(ele) {
        //清空信息
        $(ele).empty();
        $.ajax({
            url: "${APP_PATH}/depts",
            type: "GET",
            success: function (result) {
                //{"code":100,"msg":"处理成功！","extend":{"depts":[{"deptId":1,"deptName":"开发部"},{"deptId":2,"deptName":"测试部"}]}}
                console.log(result);
                //在下拉列表中打印下拉信息
                $.each(result.extend.depts, function () {  //this 当前正在遍历的对象
                    var optionEle = $("<option></option>").append(this.deptName).attr("value", this.deptId);
                    optionEle.appendTo(ele);
                })

            }

        })
    }

    function validata_add_form() {
        //1、拿到数据
        var empName = $("#empName_add_input").val();
        var regName = /(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})/;//a-zA-Z0-9   6-16位 允许汉子
        if (!regName.test(empName)) {
            // alert("用户名可以是2-5位中文或6-16位英文和数组的组合！");
            show_validate_msg("#empName_add_input", "error", "用户名必须是2-5位中文或6-16位英文和数字的组合！")
            return false;
        } else {
            show_validate_msg("#empName_add_input", "success", "")
        }
        var email = $("#email_add_input").val();
        var regEmail = /^(\w)+(\.\w+)*@(\w)+((\.\w{2,3}){1,3})$/;
        if (!regEmail.test(email)) {
            //alert("邮箱格式不正确！");
            show_validate_msg("#email_add_input", "error", "邮箱格式不正确！")
            return false;
        } else {
            show_validate_msg("#email_add_input", "success", "")
        }
        return true;
    }
    //显示校验结果提示信息
    function show_validate_msg(ele, status, msg) {
        //清楚当前元素状态
        $(ele).parent().removeClass("has-success has-error");
        $(ele).next("span").text("");
        if ("success" == status) {
            $(ele).parent().addClass("has-success");
            $(ele).next("span").text(msg);

        } else if ("error" == status) {
            $(ele).parent().addClass("has-error");
            $(ele).next("span").text(msg);  //在进行数据库校验是否存在相同用户名时 显示不出提示信息，但测试以跑通到这里

        }
    }


    $("#save_emps_btn").click(function () {
        //表单数据校验

        if (!validata_add_form()) {
            return false;
        }
        //保证用户名存在是 保存不可以点击
        if ($(this).attr("ajax-va") == "error") {

            // alert("用户名已存在！")
            return false;
        }
        insert_empInfo();
    })
    function insert_empInfo() {
        // alert("sdaasd ");  serialize() 将表单里内容序列化
        $.ajax({
            url: "${APP_PATH}/emp",
            type: "POST",
            data: $("#myModal form").serialize(),
            success: function (result) {
                /*//保证用户绕过前端校验时  后端也进行校验 保证数据安全
                 if (result.code == 100) {
                 //关闭对话框
                 $("#myModal").modal('hide');
                 //到表单的最后一页, 发送请求显示最后一页
                 to_page(totalRecord);
                 console.log(result);
                 } else {
                 //console.log(result);
                 //有哪个字段的错误信息就显示哪个字段
                 if (undefined != result.extend.errorfield.email) {
                 //显示邮箱错误信息
                 show_validate_msg("#email_add_input", "error", result.extend.errorfield.email)
                 }
                 if (undefined != result.extend.errorfield.empName) {
                 //显示名字错误信息
                 show_validate_msg("#empName_add_input", "error", result.extend.errorfield.empName)
                 }

                 }*/
                //关闭对话框
                $("#myModal").modal('hide');
                //到表单的最后一页, 发送请求显示最后一页
                to_page(totalRecord);
                console.log(result);


            }
        })

    }
    //校验用户名是否可用
    $("#empName_add_input").change(function () {
        //发送请求
        var empName = this.value;
        $.ajax({
            url: "${APP_PATH}/checkUser",
            data: "empName=" + empName,
            type: "POST",
            success: function (result) {
                //  alert(result.code);
                console.log(result);
                if (result.code == 100) {
                    show_validate_msg("#empName_add_input", "success", "用户名可用");
                    $("#save_emps_btn").attr("ajax-va", "success");
                } else {
                    show_validate_msg("#empName_add_input", "error", "用户名不可用");
                    $("#save_emps_btn").attr("ajax-va", "error");
                }
            }
        })

    });
    $(document).on("click", ".edit_btn", function () {
        //查出部门信息，并显示部门列表
        getDepts("#myModal2 select");
        //查出员工信息并显示
        getEmp($(this).attr("edit-id"));

//把员工的id传递给模态框的更新按钮

        $("#emps_update_btn").attr("edit-id", $(this).attr("edit-id"));
        $("#myModal2").modal({
            backdrop: "static"
        })
    })

    function getEmp(id) {
        $.ajax({
            url: "${APP_PATH}/emp/" + id,
            type: "GET",
            success: function (result) {
                //  console.log(result);
                var empData = result.extend.emp;
                $("#empName_update_static").text(empData.empName);
                $("#email_update_input").val(empData.email);
                $("#myModal2 input[name=gender]").val([empData.gender]);
                $("#myModal2 select").val([empData.dId]);
            }

        })
    }
    //点击链接，更新员工信息
    $("#emps_update_btn").click(function () {
        var email = $("#email_update_input").val();
        var regEmail = /^(\w)+(\.\w+)*@(\w)+((\.\w{2,3}){1,3})$/;
        if (!regEmail.test(email)) {
            //alert("邮箱格式不正确！");
            show_validate_msg("#email_update_input", "error", "邮箱格式不正确！")
            return false;
        } else {
            show_validate_msg("#email_update_input", "success", "")
        }
        //发送请求，保存数据 发送PUT请求 修改员工
        $.ajax({
            url: "${APP_PATH}/emp/" + $(this).attr("edit-id"),
            type: "POST",
            data: $("#myModal2 form").serialize() + "&_method=PUT",//转化为PUT请求
            success: function (result) {
                //关闭对话框，会到本页面
                $("#myModal2").modal('hide');
                to_page(currentPage);
            }

        })
    })
    //单个删除
    $(document).on("click", ".delete_btn", function () {
        //弹出是否删除对话框  //  $(this) 当前点击的删除按钮   ("td:eq(1)")找到第二个td
        var empName = $(this).parents("tr").find("td:eq(1)").text();
        var empId = $(this).attr("del-id");
        if (confirm("真的要删除  " + empName + "  吗？")) {
            //确认，发送ajax
            $.ajax({
                url: "${APP_PATH}/emp/" + empId,
                type: "DELETE",
                success: function (result) {
                   // alert(result.msg);
                    console.log(result.msg);
                    to_page(currentPage);//回到之前页面 刷新数据
                }

            })
        }

    })

    //完成全选，全不选
    $("#checkAll").click(function () {
        //attr获取checkedUndefinde;
        //向我们这些dom原生属性：attr获取自定义属性的值
        //prop修改和读取dom原生属性的值 选中是true 反之false
        $(".check_item").prop("checked", $(this).prop("checked"));

    })

    //check_item点满了也会全选中
    $(document).on("click", ".check_item", function () {
        //当前选中的元素是不是5个
        var flag = $(".check_item:checked").length == $(".check_item").length;
        $("#checkAll").prop("checked", flag);
    })

    //点击全部删除 批量删除
    $("#emp_delete_all_btn").click(function () {
        var empNames = "";
        var del_idstr = "";
        $.each($(".check_item:checked"), function () {
            empNames += $(this).parents("tr").find("td:eq(2)").text() + ",";
            //组装员工id字符串
            del_idstr += $(this).parents("tr").find("td:eq(1)").text() + "-";
        })
        empNames.substr(0, empNames.length - 1);
        del_idstr.substr(0, del_idstr.length - 1);
        if (confirm("真的要删除" + empNames + "吗？")) {
            //发送Ajax请求
            $.ajax({
                url: "${APP_PATH}/emp/" + del_idstr,
                type: "DELETE",
                success: function (result) {
                    //alert(result.msg);
                    console.log(result.msg);
                    to_page(currentPage);
                }

            })
        }

    })

</script>
</body>
</html>