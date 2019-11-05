<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!--页头-->
<div class="page-header" style="margin-top: -20px;">
    <h3>专辑管理</h3>
</div>
<%--jqgrid--%>
<script>
    $(function () {

        $('#tt').jqGrid({
            url : '${pageContext.request.contextPath}/album/findAll',
            datatype : "json",
            height : 300,
            editurl:'${pageContext.request.contextPath}/album/edit',
            colNames : [ '编号', '标题', '封面', '章节数', '评分','作者','简介','发布时间'],
            colModel : [
                {name : 'id',hidden:true,editable:false},
                {name : 'title',editable:true},
                {name : 'cover',editable:true,formatter: function (value, option, rows) {
                        return "<img style='width:100px;height:70px' src='${pageContext.request.contextPath}/image/album/"+rows.cover+"'>";
                    },edittype: 'file'},
                {name : 'count',editable:false,edittype:'number'},
                {name : 'score',editable:false,edittype:'number'},
                {name : 'author',editable:true,edittype:'select',editoptions:{
                    dataUrl:"${pageContext.request.contextPath}/star/find"
                    },formatter:function (value, option,rows) {
                        return rows.star.username;
                    }},
                {name : 'brief',editable:true,edittype:'textarea'},
                {
                    name: 'createDate', formatter: 'date',
                    formatoptions: {newformat: 'Y-m-d'},editable:true,edittype:'date'
                }
            ],
            rowNum : 3,
            rowList : [ 3,5,10,15 ],
            pager : '#pager',
            viewrecords : true,
            subGrid : true,
            caption : "专辑列表",
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
                        url : "${pageContext.request.contextPath}/chapter/findByAlbumId?albumId=" + id,
                        datatype : "json",
                        colNames : [ '编号', '音乐名称', '大小', '时长','歌手', '发布时间','在线播放' ],
                        colModel : [
                            {name : "id",editable:false,hidden:true},
                            {name : "name",editable:true,edittype:'file'},

                            {name : "size",editable:false},
                            {name : "duration",editable:false},
                            {name : "singer",editable:true},
                            {name : "createDate", formatter: 'date',
                                formatoptions: {newformat: 'Y-m-d'},editable:false,edittype:'date'
                            },
                            {name : "ss",width:300,formatter:function (value, option, rows) {
                                    return "<audio controls>\n" +
                                        "  <source src='${pageContext.request.contextPath}/image/chapter/"+rows.name+"'type='audio/mpeg' >\n" +

                                        "</audio>"
                                }}

                        ],
                        styleUI:"Bootstrap",
                        rowNum : 2,
                        pager : pager_id,
                        autowidth:true,
                        height : '100%',
                        editurl: "${pageContext.request.contextPath}/chapter/edit"
                    });
                jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                    "#" + pager_id, {
                        edit : false,
                        add : true,
                        del : false,
                        search:false
                    },{},{
                        closeAfterAdd:true,
                        afterSubmit:function (response) {
                            var status = response.responseJSON.status;
                            var ids = response.responseJSON.id;
                            if(status){
                                $.ajaxFileUpload( {
                                    url : "${pageContext.request.contextPath}/chapter/upload",
                                    fileElementId : "name",
                                    data:{id:ids,albumId:id},
                                    type:"post",
                                    success : function(response) {
                                        $("#tt").trigger("reloadGrid");
                                    }
                                });
                            }
                            return "true";
                        }
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
                        url : "${pageContext.request.contextPath}/album/upload",
                        fileElementId : "cover",
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


