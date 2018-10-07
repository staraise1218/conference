var List={
    pageSize:10, //一页有几个
    total:0, //一共多少数据


    //获取我的预定
    getList:function(pageNum){
        pageNum=pageNum?pageNum:1
        var postData={
            pageSize:List.pageSize,
            pageNum:pageNum,
        }
        console.log(postData)
        Global.openLoading()
        $.ajax({
            type:"POST",
            url: Global.host + "/order/selectSimpleListByMember",
            data:postData,
            success: function (res) {
                console.log(res)
                Global.closeLoading()
                List.total=res.count
                var arr=res.list
                if(arr.length!==0){
                    var tbody=document.getElementById("tbody")
                    tbody.innerHTML="" //清空表格
                    arr.forEach(function(obj){
                        console.log(obj.otakeorder)
                        var tr=document.createElement("tr")
                        var startTime=Global.dateToFormat(new Date(obj.ocbegintime))  
                        var endTime=Global.dateToFormat(new Date(obj.ocendtime))       
                        // console.log(startTime,endTime)
                        var dockingtime=Global.dateToFormat(new Date(obj.ocdockingtime)) //对接时间
                        //审核状态
                        var stateStr=""
                        switch(Number(obj.opassstate)){
                            case 1:
                                stateStr="未通过"
                                break
                            case 2:
                                stateStr="通过"
                                break
                            case 3:
                                stateStr="待审批"
                                break
                            default:
                                stateStr="待审批"
                                break
                        }
                        // otakeorder 服务状态(1.未接单 2.服务中3.服务完成  默认为1 )
                        tr.innerHTML=`
                            <td>${obj.rname}</td>
                            <td>${startTime.substr(0,10)} <br>${startTime.substr(startTime.length-5,5)}--${endTime.substr(endTime.length-5,5)}</td>
                            <td>${stateStr}</td>
                            <td>${obj.uname}</td>
                            <td>${(obj.price&&obj.price!==null)?obj.price:0}</td>
                            <td>${dockingtime.substr(0,10)} <br>${dockingtime.substr(dockingtime.length-5,5)}</td>
                            <td class="list_table_btn">
                                <div class="al" style="display: inline-block;">
                                    <button class="lookBtn">查看</button>
                                    <button class="cancelBtn${Number(obj.opassstate)==3?'':' disabled_btn'}" data-toggle="modal" data-target="#myReject">取消</button>
                                    <br>
                                    <button class="commentBtn${Number(obj.otakeorder)==3?'':' disabled_btn'}" data-toggle="modal" data-target="#myReview">评价</button>
                                    <button class="exportBtn">导出</button>
                                </div>
                            </td>
                        `
                        tr.setAttribute("data-ocid",obj.ocid)
                        tr.setAttribute("data-id",obj.id)
                        tbody.appendChild(tr)
                    })

                    //生成分页
                    List.buildPage(List.total,pageNum)
                }
            }
        })
    },
    //生成分页
    buildPage:function(totalCount,currentPage){
        var totalPage=Math.ceil(totalCount/List.pageSize)
        // totalPage=2 //测试用
        // console.log(totalPage)
        $("#pagination3").pagination({
            currentPage: currentPage,
            totalPage: totalPage,
            isShow: true,
            count: 7, 
            homePageText: "首页",
            endPageText: "尾页",
            prevPageText: "上一页",
            nextPageText: "下一页",
            callback: function(page) {
                console.log(page)
                List.getList(page)
            }
        });
    },
    //取消订单
    cancelBook:function(ocid){
        //取消原因
        var message=document.getElementById("cancelMsg").value.trim()
        if(message==""){
            alert("请填写取消原因")
            return
        }
        var postData={
            ocid:ocid,
            message:message,
        }
        console.log(postData)
        $.ajax({
            type:"POST",
            url: Global.host + "/order/memberUpdateOrder",
            data:postData,
            success: function (res) {
                console.log(res)
                $('#myReject').modal('hide')
                alert("删除成功")
            }
        })
    },
    //评价订单
    reviewBook:function(ocid){
        var postData={
            ocid:ocid, //订单内容id
            cmcontent:document.getElementById("commentVal").value.trim()==""?null:document.getElementById("commentVal").value.trim(), //评论内容
            cmgrade:$(".starNumText").html(), //分数
            cmtime:Global.dateToFormat(new Date()), //时间?
            rid:"", //房间id 这里rid没返给我
            uname:"", //用户名?
        }
        console.log(postData)
        // return
        $.ajax({
            type:"POST",
            url: Global.host + "/comment/insertOneComment",
            data:postData,
            success: function (res) {
                console.log(res)
                $('#myReview').modal('hide')
                alert("评论成功")
            }
        })
    },
    eventsBind:function(){
        //点击我要预定
        // document.getElementById("gotoIndex").onclick=function(){
        //     window.location.href="index.html"
        // }
        //事件代理，点击每一条的按钮
        //查看
        $("#tbody").delegate(".lookBtn","click",function(){
            var ocid=$(this).closest("tr").attr("data-ocid")
            console.log(ocid)
            // return
            window.location.href="index_detail_look.html?ocid="+ocid
        })
        //取消
        $("#tbody").delegate(".cancelBtn","click",function(){
            if($(this).hasClass("disabled_btn")){
                return false
            }
            var ocid=$(this).closest("tr").attr("data-ocid")
            document.getElementById("myReject").setAttribute("data-ocid",ocid)
        })
        //评价
        $("#tbody").delegate(".commentBtn","click",function(){
            if($(this).hasClass("disabled_btn")){
                return false
            }
            var ocid=$(this).closest("tr").attr("data-ocid")
            document.getElementById("myReview").setAttribute("data-ocid",ocid)
        })
        //导出
        $("#tbody").delegate(".exportBtn","click",function(){
            let tr=$(this).closest("tr")[0]
            html2canvas(tr).then(function(canvas) {
                Global.canvasToLocalImg(canvas)
            });
        })
        //模态框 取消按钮
        document.getElementById("cancelBtn").onclick=function(){
            var ocid=Number(document.getElementById("myReject").getAttribute("data-ocid"))
            List.cancelBook(ocid)
        }
        //模态框 评价按钮
        document.getElementById("commentBtn").onclick=function(){
            var ocid=Number(document.getElementById("myReview").getAttribute("data-ocid"))
            List.reviewBook(ocid)
        }
    },  
    init:function(){
        Global.rightTabCut()
        Global.getMessageByLing()
        //获取数据
        List.getList()
        //事件绑定
        List.eventsBind()
    }
}
$(function () {
    List.init()
});