<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!--页头-->
<div class="page-header" style="margin-top: -20px;">
    <h3>轮播图管理</h3>
</div>
<%--jqgrid--%>
<script>
    $(function () {

        $('#tt').jqGrid({
            url:'${ff}/star/banner/findAll',
            datatype:'json',
            //引入bootstrap的UI样式
            styleUI:'Bootstrap',
            colNames:['ID','名称','封面','描述','创建日期','状态'],
            editurl:'${ff}/star/banner/edit',
            colModel:[
                {name:'id',hidden:true,editable:false},
                {name:'name',editable:true},
                {
                    name: 'cover',
                    editable: true,
                    edittype: "file",
                    formatter: function (value, options, row) {
                        return "<img style='width: 100px;height: 60px;' src='${pageContext.request.contextPath}/image/banner/"+row.cover+"'>";
                    }
                },
                {name:'description',editable:true},
                {name:'createDate',formatter:'date',
                    formatoptions:{newformat:'Y-m-d'}
                },
                {
                    name:'status',
                    editable:true,
                    edittype: 'select',
                    editoptions:{value:"正常:正常;冻结:冻结"}

                }
            ],

            caption:'展示所有轮播图',
            pager:'#pager',
            rowList:[3,5,10,20,30],
            rowNum:3,// 指定默认每页展示的条数，值必须来自rowList中的一个
            height:300,
            viewrecords:true,// 是否显示总记录条数
            autowidth:true,
            sortname : 'id',
        }).navGrid('#pager',{'add':true,'edit':true,'del':true,'search':false},
            {
                //控制修改
                closeAfterEdit:true,
                beforeShowForm:function (fmt) {
                    fmt.find("#cover").attr("disabled",true);}
            },
            {
                //控制添加操作
                closeAfterAdd:true, //修改后关闭框
                afterSubmit:function (response) {
                    var status = response.responseJSON.status;
                    var ids = response.responseJSON.id;
                    if(status){
                        $.ajaxFileUpload( {
                            url : "${pageContext.request.contextPath}/banner/upload",//用于文件上传的服务器端请求地址
                            fileElementId : "cover",    //文件上传空间的id属性  <input type="file" id="file" name="file" />
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


