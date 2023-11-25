<!DOCTYPE html>
<html>
<head>
    <#import "common/common.macro.ftl" as netCommon>
    <@netCommon.commonStyle />
    <title>${I18n.admin_name}</title>
</head>
<body class="hold-transition skin-blue sidebar-mini <#if cookieMap?exists && cookieMap["xxljob_adminlte_settings"]?exists && "off" == cookieMap["xxljob_adminlte_settings"].value >sidebar-collapse</#if> ">
<div class="wrapper">
    <!-- header -->
    <@netCommon.commonHeader />
    <!-- left -->
    <@netCommon.commonLeft "tools" />

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>${I18n.job_tools}</h1>
        </section>

        <form class="form-horizontal form">
            <div class="form-group">
                <label for="firstname" class="col-sm-2 control-label">${I18n.job_tools_groovy_param}<font color="black">*</font></label>
                <div class="col-sm-10">
                    <textarea class="textarea form-control" name="executorParam"
                              placeholder="${I18n.system_please_input}${I18n.job_tools_groovy_param}"
                              style="height: 63px; line-height: 1.2;"></textarea>
                </div>
            </div>
            <div class="form-group">
                <label for="firstname" class="col-sm-2 control-label">${I18n.job_tools_groovy_input}<font color="black">*</font></label>
                <div class="col-sm-10">
                    <textarea class="textarea form-control" name="executor" minlength="1"
                              placeholder="${I18n.system_please_input}${I18n.job_tools_groovy_input}"
                              style="height: 63px; line-height: 1.2;"></textarea>
                </div>
            </div>

            <div class="form-group">
                <label for="firstname" class="col-sm-2 control-label">输出<font color="black">*</font></label>
                <div class="col-sm-10">
                    <textarea class="textarea form-control" name="executorRe" minlength="1"
                              placeholder="输入"
                              style="height: 63px; line-height: 1.2;"></textarea>
                </div>
            </div>
        </form>

        <div class="form-group">
            <div class="col-sm-offset-3 col-sm-6">
                <button class="btn btn-primary" onclick="ajax_get()">${I18n.system_run}</button>
            </div>
        </div>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <!-- footer -->
    <@netCommon.commonFooter />
</div>
<@netCommon.commonScript />
</body>
<script>
    function ajax_get() {
        // alert('aaaaaa')
        const xhr = new XMLHttpRequest()
        xhr.open('post', '/xxl-job-admin/tools/groovy')
        // 如果是post请求，原生ajax，需要设置一个请求头，告诉后端，我发送的数据是什么格式的
        xhr.setRequestHeader('content-type', 'application/json')
        xhr.addEventListener('load', function () {
            if (xhr.status == 200) {
                // console.log(xhr.response)
                document.getElementsByName("executorRe")[0].value = xhr.response;

            }
        })
        // post请求，在这里写参数
        // 如果是get请求，send可以为空，或者null

        var executorParam = document.getElementsByName("executorParam")[0].value;
        var executor = document.getElementsByName("executor")[0].value;
//         //通过FormData构造函数创建一个空对象
//         var formdata = new FormData();
//
// //可以通过append()方法来追加数据
//         formdata.append("executorParam", executorParam);
//         formdata.append("executor", executor);
// // //通过get方法对值进行读取
// //         console.log(formdata.get("name")); // 张三
// //
// // //通过set方法对值进行修改
// //         formdata.set("name","李四");
// //         console.log(formdata.get("name")); // 李四
//
//         console.log(executorParam)
//         alert(executorParam)
        var body = {
            "executorParam": executorParam,
            "executor": executor
        }
        xhr.send(JSON.stringify(body))
    }
</script>

</html>
