<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>闽太消防 - 数据中心 - 用户登录</title>
    <%@ include file="static-header.jsp" %>
</head>
<body>
<div class="container-fluid">
    <!-- 导航 start -->
    <%@ include file="static-nav.jsp" %>
    <!-- 导航 end -->
    <!-- 布局 start -->
    <h1>请输入用户密码登录</h1>
    <hr>

    <div id="J_message" style="display: none">
        <div class="alert alert-warning alert-dismissable">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
            <p id="J_message_content">${messge}</p>
        </div>
    </div>

    <form action="/login" method="post">
        <input type="hidden" name="callback" value="${callback}"/>
        <div class="form-group">
            <label for="J_userName">用户名</label>
            <input type="text" class="form-control" id="J_userName" name="userName" placeholder="请输入登录用户名">
        </div>
        <div class="form-group">
            <label for="J_password">密码</label>
            <input type="password" class="form-control" id="J_password" name="password" placeholder="请输入登录密码">
        </div>
        <button type="submit" id="J_submit" class="btn btn-info">登录</button>
    </form>
    <!-- 布局 end -->

    <script>
        $(function () {
            $("#J_submit").click(function () {
                var userName = $("#J_userName").val();
                if (userName == "") {
                    $("#J_message_content").text("请输入登录用户名！");
                    $("#J_message").show();
                    return false;
                }

                var password = $("#J_password").val();
                if (password == "") {
                    $("#J_message_content").text("请输入登录密码！");
                    $("#J_message").show();
                    return false;
                }
            });

            var message = $("#J_message_content").text();
            if (message != "") {
                $("#J_message").show();
            }
        });
    </script>
    <!-- 页脚 start -->
    <%@ include file="static-button.jsp" %>
    <!-- 页脚 end -->
</div>
</body>
<%@ include file="static-footer.jsp" %>
</html>
