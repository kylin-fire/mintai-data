<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .navbar-inverse .navbar-brand {
        color: #ddd;
    }
</style>
<nav class="navbar navbar-default navbar-static-top navbar-inverse" role="navigation">
    <div class="navbar-inner">
        <div class="navbar-header">
            <a class="navbar-brand" href="/">闽太消防 - 数据中心</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">欢迎，您</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">帮助<span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="$!{helpLink}#">用户手册</a></li>
                        <li><a href="#">版权申明</a></li>
                        <li class="divider"></li>
                        <li><a href="#">关于我们</a></li>
                    </ul>
                </li>
                <li><a href="/logout">退出&nbsp; &nbsp;</a></li>
            </ul>
        </div>
    </div>
</nav>