<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!--页头-->
<div>
<ul class="nav nav-tabs">
    <li role="presentation" class="active"><a href="#">所有文章</a></li>
    <li role="presentation" ><a href="#" onclick="openModal('add','')">添加文章</a></li>

</ul>
</div>


<%--jqgrid--%>
<script>

    KindEditor.create('#editor_id',{
        width : '517px',
        //点击图片空间按钮时发送的请求
        fileManagerJson:"${pageContext.request.contextPath}/article/browse",
        //展示图片空间按钮
        allowFileManager:true,
        //上传图片所对应的方法
        uploadJson:"${pageContext.request.contextPath}/article/upload",
        //上传图片是图片的形参名称
        filePostName:"articleImg",
        afterBlur:function () {
            this.sync();
        }

    });






    $(function () {

        $('#tt').jqGrid({
            url : '${pageContext.request.contextPath}/article/findAll',
            datatype : "json",
            height : 300,
            editurl:'${pageContext.request.contextPath}/album/edit',
            colNames : [ '编号', '标题', '作者', '简介','内容','创建时间','操作'],
            colModel : [
                {name : 'id',hidden:true,editable:false},
                {name : 'title',editable:true},
                {name : 'author',editable:false},
                {name : 'abstracts',editable:false},

                {name : 'content',editable:true,hidden: true},
                {
                    name: 'createDate', formatter: 'date',
                    formatoptions: {newformat: 'Y-m-d'},editable:true,edittype:'date'
                },
                {name:'dsf',formatter: function (value, option, rows) {
                        return "<button class='btn btn-warning' type='button' onclick=\"openModal('edit','"+ rows.id+"')\">修改</button>"+
                            "&nbsp;&nbsp;&nbsp;&nbsp;<a class='btn btn-danger' onclick=\"del('"+rows.id+"')\">删除</a>";
                    },align:'center'}
            ],
            rowNum : 3,
            rowList : [ 3,5,10,15 ],
            pager : '#pager',
            viewrecords : true,
            caption : "专辑列表",
            styleUI:"Bootstrap",
            autowidth:true,
        }).jqGrid('navGrid', '#pager', {
            add : false,
            edit : false,
            del : false,
            search:false
        });
    });
    
    function openModal(oper,id) {
        if("add"==oper){
            $("#article-id").val("");
            $("#article-title").val("");
            $("#article-author").val("");
            $("#article-abstracts").val("");
            KindEditor.html("#editor_id","");
        }
        if ("edit"==oper){
            var article = $("#tt").jqGrid("getRowData",id);
            $("#article-id").val(article.id);
            $("#article-title").val(article.title);
            $("#article-author").val(article.author);
            $("#article-abstracts").val(article.abstracts);
            KindEditor.html("#editor_id",article.content);
        }
        $("#article-modal").modal("show");
    }
    function del(id) {
        $.ajax({
            url:"${pageContext.request.contextPath}/article/delete",
            datatype:"json",
            type:"post",
            data:{id:id},
            success:function () {
               $("#tt"). trigger("reloadGrid");
            }
        });
    }
    
    
    
    function save() {
        var id = $("#article-id").val();
        var url = "";
        if (id){
            url="${pageContext.request.contextPath}/article/update";
        }else{
            url="${pageContext.request.contextPath}/article/save"
        }
        $.ajax({
            url:url,
            datatype:"json",
            type:"post",
            data:$("#article-form").serialize(),
            success:function () {
                $("#tt").trigger("reloadGrid");
            }
        });
        $("#article-modal").modal("hide");

    }

</script>


<!--创建表格-->
<%--<h1>展示所有轮播图</h1>--%>
<table id="tt"></table>
<div id="pager"></div>

<div class="modal fade" id="article-modal"  role="dialog" >
    <div class="modal-dialog" role="document" style="width: 680px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="exampleModalLabel">文章操作</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="article-form">
                    <input type="hidden" name="id" id="article-id">
                    <div class="form-group">
                        <label for="article-title" class="col-sm-2 control-label" >文章标题:</label>
                        <div class="col-sm-10">
                            <input type="text" name="title" class="form-control" id="article-title" placeholder="请输入文章标题">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="article-author" class="col-sm-2 control-label">文章作者:</label>
                        <div class="col-sm-10">
                            <input type="text" name="author" class="form-control" id="article-author" placeholder="请输入文章作者">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="article-abstracts" class="col-sm-2 control-label">文章简介:</label>
                        <div class="col-sm-10">
                            <input type="text" name="abstracts" class="form-control" id="article-abstracts" placeholder="请输入文章简介">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-10">
                            <textarea id="editor_id" name="content" style="min-width: 90%">
                            &lt;strong&gt;HTML内容&lt;/strong&gt;
                            </textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="save()">保存</button>
            </div>
        </div>
    </div>
</div>


