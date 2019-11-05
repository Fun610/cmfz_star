<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!--页头-->
<div>
    <ul class="nav nav-tabs">
        <li role="presentation" class="active"><a href="#">所有用户</a></li>
        <li role="presentation" ><a href="${pageContext.request.contextPath}/user/export" >导出用户</a></li>
    </ul>
</div>
<%--jqgrid--%>
<script>
    $(function () {

        $('#tt').jqGrid({
            url : '${pageContext.request.contextPath}/user/findAll',
            datatype : "json",
            height : 300,
            editurl:'${pageContext.request.contextPath}/album/edit',
            colNames : [ '编号', '用户名', '联系方式', '头像', '昵称','地址','个性签名 ','性别','创建日期'],
            colModel : [
                {name : 'id',hidden:true,editable:false},
                {name : 'username',editable:true},
                {name : 'phone',editable:true},
                {name : 'photo',editable:false,},
                {name : 'nickname',editable:false,},
                {name : 'address',editable:false,formatter:function (value, option, rows) {
                        return rows.province+"-"+rows.city;
                    }},
                {name : 'sign',editable:false,},
                {name : 'sex',editable:false,},
                {
                    name: 'createDate', formatter: 'date',
                    formatoptions: {newformat: 'Y-m-d'},editable:true,edittype:'date'
                }
            ],
            rowNum : 3,
            rowList : [ 3,5,10,15 ],
            pager : '#pager',
            viewrecords : true,
            caption : "用户列表",
            styleUI:"Bootstrap",
            autowidth:true,

        }).jqGrid('navGrid', '#pager', {
            add : false,
            edit : false,
            del : false,
            search:false
        });
    });




</script>


<!--创建表格-->
<%--<h1>展示所有轮播图</h1>--%>
<table id="tt"></table>
<div id="pager"></div>


