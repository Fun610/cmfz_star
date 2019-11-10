<%@ page import="java.util.Date" %>
<%@ page import="java.util.Formatter" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ff" value="${pageContext.request.contextPath}"></c:set>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>后台管理系统</title>
    <%--引入bootstrap的样式--%>
    <link rel="stylesheet" href="../statics/boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="../statics/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <script src="../statics/boot/js/jquery-3.3.1.min.js"></script>
    <script src="../statics/boot/js/bootstrap.min.js"></script>

    <%--引入jqgrid--%>
    <script src="../statics/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="../statics/jqgrid/js/trirand/jquery.jqGrid.min.js"></script>


    <%--引入文件上传插件--%>
    <script src="../statics/jqgrid/js/ajaxfileupload.js"></script>

    <%--引入kindeditor插件--%>

    <script charset="utf-8" src="../kindeditor/kindeditor-all-min.js"></script>
    <script charset="utf-8" src="../kindeditor/lang/zh-CN.js"></script>


    <script src="../echarts/echarts.min.js"></script>


</head>
<body>
<!--导航条-->
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <!--导航标题-->
        <div class="navbar-header">
            <a class="navbar-brand" href="#">持明法洲后台管理系统 <small>v1.0</small></a>
        </div>

        <!--导航条内容-->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">欢迎:<font color="aqua"> <shiro:principal/> </font></a></li>
                <li><a href="${ff}/admin/logout">退出登录 <span class="glyphicon glyphicon-log-out"></span> </a></li>
            </ul>
        </div>
    </div>

</nav>

<div class="container-fluid " style="padding-left: 0px">
    <div class="row">
        <div class="col-sm-2">
            <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                <shiro:hasRole name="admin">
                <div class="panel panel-default">
                    <div class="panel-heading text-center" role="tab" id="headingOne">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                轮播图管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body text-center">

                               <a href="javascript:$('#centerLayout').load('${ff}/back/banner/lists_s.jsp')" class="btn btn-default">所有轮播图</a>

                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading text-center" role="tab" id="headingTwo">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                专辑管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                        <div class="panel-body text-center">
                            <a href="javascript:$('#centerLayout').load('${ff}/back/album/album.jsp')" class="btn btn-default">专辑列表</a>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading text-center" role="tab" id="headingThree">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                                文章管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                        <div class="panel-body text-center">
                            <a href="javascript:$('#centerLayout').load('${ff}/back/article/article.jsp')" class="btn btn-default">文章列表</a>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading text-center" role="tab" id="headingFour">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
                                用户管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFour" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                        <div class="panel-body text-center">
                            <ul class="list-group">
                                <a href="javascript:$('#centerLayout').load('${ff}/back/user/user.jsp')" class="btn btn-default list-group-item">用户列表</a>
                                <a href="javascript:$('#centerLayout').load('${ff}/back/user/userTrend.jsp')" class="btn btn-default list-group-item">用户注册趋势表</a>
                            </ul>
                        </div>



                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading text-center" role="tab" id="headingFive">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFive" aria-expanded="false" aria-controls="collapseFive">
                                明星管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFive" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                        <div class="panel-body text-center">
                            <a href="javascript:$('#centerLayout').load('${ff}/back/star/star.jsp')" class="btn btn-default">全明星</a>
                        </div>
                    </div>
                </div>
                </shiro:hasRole>
                <shiro:hasRole name="super">
                    <div class="panel panel-default">
                        <div class="panel-heading text-center" role="tab" id="headingSix">
                            <h4 class="panel-title">
                                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseSix" aria-expanded="false" aria-controls="collapseSix">
                                    管理员管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseSix" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                            <div class="panel-body text-center">
                                <a href="javascript:$('#centerLayout').load('#')" class="btn btn-default">管理员列表</a>
                            </div>
                        </div>
                    </div>
                </shiro:hasRole>
            </div>
        </div>

        <!--中心布局-->
        <div class="col-sm-10" id="centerLayout">
            <!--巨幕-->
            <div class="jumbotron">
                <font style="padding-left: 100px">欢迎来到持明法州后台管理系统！</font>
            </div>
            <div>
                <img src="${ff}/back/8.jpg" style="width: 1110px;height:400px ">
            </div>


        </div>
    </div>
</div>
<%--<ol class="breadcrumb" style="height: 50px">
    <li class="text-center">持明法州后台管理系统@百知教育</li>
</ol>--%>
<nav class="navbar navbar-default navbar-fixed-bottom" style="height: 50px">
    <div class="container text-center" style="line-height: 50px">
        持明法州后台管理系统@百知教育
        <%
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String date2= format.format(date);
            out.print(date2);
        %>
    </div>
</nav>













</body>
</html>