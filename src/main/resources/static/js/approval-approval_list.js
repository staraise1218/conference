var List={
    pageSize:10, //一页有几个
    total:0, //一共多少数据

    //预定时间
    begintime:"",
    endtime:"",

    //获取参数
    getParams(){
        let option={
            rid:$("#rnameSelect").val()==""?null:Number($("#rnameSelect").val()),
            begintime:List.begintime==""?null:List.begintime,
            endtime:List.endtime==""?null:List.endtime,
            sid:$("#sernameSelect").val()==""?null:Number($("#sernameSelect").val()),
            opassstate:$("#stateSelect").val()==""?null:Number($("#stateSelect").val())                      
        }
        console.log(option)
        return option
    },
    //获取列表
    getList:function(pageNum=1){                          //参数1：页码，参数2：查询参数option

        var option=List.getParams()

        var postData={
            pageSize:List.pageSize,
            rid:option.rid, //会议室id
            beginOtime:option.begintime,
            endOtime:option.endtime,
            sid:option.sid, //人员id
            opassstate:option.opassstate, //订单审批状态
            page:pageNum
        }

        console.log(postData)
        Global.openLoading()
        $.ajax({
            type:"POST",
            url: Global.host + "/order/selectApprovalScreen",
            data:postData,
            success: function (res) {
                Global.closeLoading()
                console.log(res)
                List.total=res.coune
                var arr=res.list
                if(arr.length!==0){
                    var tbody=document.getElementById("tbody")
                    tbody.innerHTML="" //清空表格
                    arr.forEach(function(obj){
                        // console.log(obj)
                        var tr=document.createElement("tr")
                        //预定时间
                        var time=Global.dateToFormat(new Date(obj.otime))  
                        //对接时间
                        var dockingtime="不对接"
                        if(obj.ocdockingtime&&obj.ocdockingtime!==""){
                            dockingtime=Global.dateToFormat(new Date(obj.ocdockingtime))  
                        }
                        // var dockingtime=Global.dateToFormat(new Date(obj.ocdockingtime)) //对接时间
                        //审核状态
                        var stateStr=""
                        switch(obj.opassstate){
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
                        tr.innerHTML=`
                            <td>${obj.rname}</td>
                            <td>${time}</td>
                            <td>${dockingtime}</td>
                            <td class="list_table_s">${stateStr}</td>
                            <td class="list_table_s">${obj.username}</td>
                            <td>${obj.tel}</td>
                            <td class="list_table_s">${obj.sernameList?obj.sernameList:''}</td>
                            <td>
                                <ul class="list_table_star">
                                </ul>
                            </td>
                            <td class="list_table_btn" style="width: auto;">
                                <button class="lookBtn">查看</button>
                            </td>
                        `
                        tr.setAttribute("data-ocid",obj.ocid)
                        tbody.appendChild(tr)
                        //添加星评
                        if(obj.cmgrade){
                            var stars=obj.cmgrade
                            // console.log(stars)
                            var ul=tr.querySelector(".list_table_star")
                            // console.log(ul)
                            for(var i=0;i<5;i++){
                                var li=document.createElement("li")
                                //白星
                                li.innerHTML=`
                                    <li><span class="iconfont icon-xingxing"></span></li>
                                `
                                if(i+1<=stars){
                                    //黄星
                                    li.getElementsByTagName("span")[0].classList.add("icon_pre")
                                }
                                ul.appendChild(li)
                            }
                        }
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
        console.log(totalPage)
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
    //初始下拉框
    initSelect:function(){
        //该部门下的会议室select
        $.ajax({
            url: Global.host + "/room/selectRoomByHcodeAndDeptCode",
            success: function (res) {
                console.log(res)
                let arr=res
                if(arr.length>0){
                    var rnameSelect=document.getElementById("rnameSelect")
                    arr.forEach(function(obj){
                        let option=document.createElement("option")
                        option.innerHTML=obj.rname
                        option.setAttribute("value",obj.rid)
                        rnameSelect.appendChild(option)
                    })
                }
            }
        })
        //预定时间
        var beginTimeStore = '';
        var endTimeStore = '';
        $('#config-demo').daterangepicker({
            "timePicker": true,
            "timePicker24Hour": false,
            "linkedCalendars": false,
            "autoUpdateInput": false,
            "locale": {
                format: 'YYYY-MM-DD',
                separator: ' ~ ',
                applyLabel: "确认",
                cancelLabel: "取消",
                resetLabel: "重置",
                daysOfWeek: ['日', '一', '二', '三', '四', '五', '六'],
                monthNames: ['一月', '二月', '三月', '四月', '五月', '六月','七月', '八月', '九月', '十月', '十一月', '十二月'],
            },
        }, function(start, end, label) {
            console.log(start, end, label)
            beginTimeStore = start;
            endTimeStore = end;
            console.log(this.startDate.format(this.locale.format));
            console.log(this.endDate.format(this.locale.format));

            //点击确定 得到起始时间
            List.begintime=this.startDate.format(this.locale.format)
            List.endtime=this.endDate.format(this.locale.format)

            if(!this.startDate){
                this.element.val('');
            }else{
                this.element.val(this.startDate.format(this.locale.format) + this.locale.separator + this.endDate.format(this.locale.format));
            }
        });
        //服务人员select
        $.ajax({
            url: Global.host + "/servicepeo/selectList",
            success: function (res) {
                console.log(res)
                let arr=res
                if(arr.length>0){
                    let sernameSelect=document.getElementById("sernameSelect")
                    arr.forEach(function(obj){
                        let option=document.createElement("option")
                        option.innerHTML=obj.sername
                        option.setAttribute("value",obj.serid)
                        sernameSelect.appendChild(option)
                    })
                }
            }
        })
    },
    eventsBind:function(){
        //事件代理，点击每一条的按钮
        //查看
        $("#tbody").delegate(".lookBtn","click",function(){
            var ocid=$(this).closest("tr").attr("data-ocid")
            // console.log(ocid)
            // return
            window.location.href="approval_detail.html?ocid="+ocid
        })
        //点击查询确认
        document.getElementById("searchBtn").onclick=function(){
            event.stopPropagation()
            List.getList()
        }
    },  
    init:function(){
        Global.getMessageByLing()
        //生成select
        List.initSelect()
        //先获取数据
        var url=window.location.href
        
        if(url.indexOf("?")>-1){
            var str=url.split("?")[1]
            var keyArr=str.split("&")
            console.log(keyArr)
            var option={}
            keyArr.forEach(function(value){
                var arrtemp=value.split("=")
                option[arrtemp[0]]=arrtemp[1]
            })
            
            //修改审核状态的select
            $("#stateSelect").val(option.opassstate)
        }
        List.getList()
        //先获取数据 end
        //事件绑定
        List.eventsBind()
    }
}
$(function () {
    List.init()
});