<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!--页头-->
<div class="page-header" style="margin-top: -20px;">
    <h3>明星管理</h3>
</div>
<%--jqgrid--%>
<script>
    $(function () {

        $('#tt').jqGrid({
            url : '${pageContext.request.contextPath}/star/findAll',
            datatype : "json",
            height : 300,
            editurl:'${pageContext.request.contextPath}/star/edit',
            colNames : [ '编号', '艺名', '真名', '照片', '性别','生日'],
            colModel : [
                {name : 'id',hidden:true,editable:false},
                {name : 'username',editable:true},
                {name : 'realname',editable:true},
                {name : 'photo',formatter:function (value,option,rows) {
                        return "<img style='width:100px;height:70px' src='${pageContext.request.contextPath}/image/star/"+rows.photo+"'>";
                    },editable:true,edittype: "file"},
                {name : 'sex',editable:true,edittype: 'select', editoptions:{value:"男:男;女:女"}},
                {
                    name: 'bir', formatter: 'date',
                    formatoptions: {newformat: 'Y-m-d'},editable:true,edittype:'date'
                }
            ],
            rowNum : 3,
            rowList : [ 3,5,10,15 ],
            pager : '#pager',
            viewrecords : true,
            subGrid : true,
            caption : "全明星列表",
            styleUI:"Bootstrap",
            autowidth:true,
            subGridRowExpanded : function(subgrid_id, id) {
                var subgrid_table_id, pager_id;
                subgrid_table_id = subgrid_id + "_t";
                pager_id = "p_" + subgrid_table_id;
                $("#" + subgrid_id).html(
                    "<table id='" + subgrid_table_id  +"' class='scroll'></table>" +
                    "<div id='" + pager_id + "' class='scroll'></div>");
                $("#" + subgrid_table_id).jqGrid(
                    {
                        url : "${pageContext.request.contextPath}/user/findByStarId?starId=" + id,
                        datatype : "json",
                        colNames : [ '编号', '用户名', '昵称', '头像','电话', '性别','地址','签名' ],
                        colModel : [
                            {name : "id"},
                            {name : "username"},
                            {name : "nickname"},
                            {name : "photo"},
                            {name : "phone"},
                            {name : "sex"},
                            {name : "address",formatter:function (value, option, rows) {
                                    return rows.province+"-"+rows.city;
                                }},
                            {name : "sign"}
                        ],
                        styleUI:"Bootstrap",
                        rowNum : 2,
                        pager : pager_id,
                        autowidth:true,
                        height : '100%'
                    });
                jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                    "#" + pager_id, {
                        edit : false,
                        add : false,
                        del : false,
                        search:false
                    });
            },
        }).jqGrid('navGrid', '#pager', {
            add : true,
            edit : false,
            del : false,
            search:false
        },{},
            {

            closeAfterAdd:true,
            afterSubmit:function (response) {
                var status = response.responseJSON.status;
                var ids = response.responseJSON.id;
                if(status){
                    $.ajaxFileUpload( {
                        url : "${pageContext.request.contextPath}/star/upload",
                        fileElementId : "photo",
                        data:{id:ids},
                        type:"post",
                        success : function(response) {
                            $("#tt").trigger("reloadGrid");
                        }
                    });
                }
                return "true";
            }
        });
    });

</script>


<!--创建表格-->
<%--<h1>展示所有轮播图</h1>--%>
<table id="tt"></table>
<div id="pager"></div>


