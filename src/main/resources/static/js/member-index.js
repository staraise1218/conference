var Index={
    rid:"", //当前会议室
    rname:"", //当前会议室名称

    //tab日期 和 时间段数组
    tabTitleArr:[], //表头
    timePeriodArr:["07:00","07:30","08:00","08:30","09:00","09:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30","18:00","18:30","19:00"],




    //初始化datetimepicker
    initDatetimepicker:function(){
        var nowDate=Global.dateToFormat(new Date()).substr(0,10)
        document.getElementById("datetime").value=nowDate
        $("#datetime").datetimepicker({
            format: 'yyyy-mm-dd',
            language: 'zh-CN',//显示中文
            minView:"month",
            autoclose:true
        }).on("hide",function(e){
            //关闭选择时间面板触发
            var date=e.currentTarget.value
            // console.log(date)
            //获取这一周的选定时间段
            Index.getRoomTime()
        });
    },
    //绑定鼠标选择时间事件
    selectTime:function(){
        var contentTab=document.getElementsByClassName("date_content")[0]
        var isMouseDown=false
        var startPosition={} //初始鼠标点击坐标
        
        contentTab.getElementsByTagName("tbody")[0].onmousedown=function(event){
            event.stopPropagation()
            isMouseDown=true	
            //清空之前选择的$td
         	$(contentTab).find("td").removeClass("mSelected")

            startPosition.X=event.clientX
            startPosition.Y=event.clientY
         
            var $td=$(event.target)
            if($td.html()==""){ //内容为空
                tdOperate($td)
            }
        }

        contentTab.onmouseup=function(event){
            event.stopPropagation()
            isMouseDown=false
        }
        
        contentTab.onmousemove=function(event){
            event.stopPropagation()
            if(isMouseDown){
                var $td=$(event.target)

                var position=$td.offset()
                var width=$td.outerWidth() //border-box
                var height=$td.outerHeight()
                //td左上角的X Y
                var tdLeftX=position.left
                // var tdLeftY=position.top
                //td右下角的X Y
                var tdRightX=position.left+width
                // var tdRightY=position.top+height
                if($td.html()==""&&(tdLeftX<startPosition.X&&tdRightX>startPosition.X)){ //内容为空；与开始点击的是同一列
                    tdOperate($td)
                }
            }
        }
        //操作
        function tdOperate($ele){ //当前滑过的$td
            if(!$ele.hasClass("mSelected")&&!$ele.hasClass("alreadySelected")){
                $ele.addClass("mSelected")
            }
            if($ele.hasClass("alreadySelected")){
                isMouseDown=false
            }
        }
    },
    //生成tabTitleArr 更新表头
    refreshTabTitleArr:function(beginDate){ //输入周日"2018-08-14"作为第一天
        var beginStamp=new Date(beginDate).getTime()
        Index.tabTitleArr=[]
        var weekArr=[]
        for(var i=0;i<7;i++){
            var stamp=beginStamp+i*86400000
            var date=Global.dateToFormat(new Date(stamp)).substr(5,5)
            Index.tabTitleArr.push(date)

            //星期几
            var weekDay=Global.getWeekDay(new Date(stamp))
            weekArr.push(weekDay)
        }
        console.log(weekArr)
        //更新tab的日期
        Index.tabTitleArr.forEach(function(value,index){
            var valueStr=value.replace(/-/g,"月")+"日"

            var span=document.querySelectorAll(".tab-date")[index]

            span.innerHTML=valueStr
            span.setAttribute("data-date",new Date().getFullYear()+"-"+value)
        })
        //更新tab的星期几
        console.log("更新星期几")
        $(".tab-week").each(function(index,el){
            this.innerHTML=weekArr[index]
        })
    },
    //绘制放入时间段 (一个时间段)
    drawTime:function(t1,t2){ //从后台得到的时间 date类型
        var datetime1=Global.dateToFormat(t1) //YYYY-MM-DD hh:mm:ss
        var datetime2=Global.dateToFormat(t2)

        var getDate=datetime1.substr(5,5)
        // console.log(getDate)
        var time1=datetime1.substr(11,5)
        var time2=datetime2.substr(11,5)
        // console.log(time1,time2)
        var dateIndex=Index.tabTitleArr.indexOf(getDate)+1
        // console.log(dateIndex)
        var time1Index=Index.timePeriodArr.indexOf(time1)
        var time2Index=Index.timePeriodArr.indexOf(time2)
        //放入时间段
        for(var i=0;i<time2Index-time1Index+1;i++){
            var $td=$(".date_content tr").eq(time1Index+1+i).find("td").eq(dateIndex)
            $td.addClass("alreadySelected")
        }
    },
    //获取一周的选定时间段
    getRoomTime:function(){
        //获取参数
        if($("li.myLeft_tabCon_act").length==0){
            return
        }
        var rid=Number($("li.myLeft_tabCon_act").attr("data-rid"))
        var inputDate=document.getElementById("datetime").value
        //获取参数 end

        //清空mSelected
        $(".date_content").find("td").removeClass("mSelected")

        // var postData={
        //     rid:5,
        //     dateAssign:"2018-09-14",
        // }
        var postData={
            rid:rid,
            dateAssign:inputDate,
        }
        console.log(postData)
        Global.openLoading()
        $.ajax({
            type: "POST",
            url: Global.host + "/user/showRoomByAssignWeek",
            data:postData,
            success: function (res) {
                console.log(res)
                Global.closeLoading()
                var arr=res
                //清空alreadySelected
                $(".date_content").find("td").removeClass("alreadySelected")
                //更新表头
                Index.refreshTabTitleArr(inputDate)
                // 绘制表格
                if(arr.length>0){
                    arr.forEach(function(obj){
                        var t1=new Date(obj.ocbegintime)
                        var t2=new Date(obj.ocendtime)
                        //绘制
                        Index.drawTime(t1,t2) //传入date类型
                    })
                }
                //同步表格标题
                Index.sameTableTitle()
            }
        })
    },
    /*获取普通用户的待办事项*/
    getUnfinishedMessage:function(){
    	$.ajax({
    		url: Global.host + "/order/selectMessageByMember",
    		success: function(res){
                console.log(res)
    			//开始set
    			var oneObject = document.getElementById("messageList");
    			res.forEach(function(obj){
            		var li = document.createElement("li")
                    li.innerHTML = obj.mtcontent

                    li.setAttribute("data-ocid",obj.ocid)
                    li.setAttribute("data-mtid",obj.mtid)

                    // //待办事项提示弹窗
                    // var $div=$('<div class="todo">'+
                    //                 '<div class="todo_top todo_btn"><span>会议室名</span><span class="todo_top_fr">发过来的的时间</span></div>'+
                    //                 // '<div class="todo_main">您有一场会议要参加</div>'+
                    //                 // '<div class="todo_btn">'+
                    //                 //     '<button class="btn1">确认</button>'+
                    //                 //     '<button>取消</button>'+
                    //                 // '</div>'+
                    //             '</div>')
                    // $(li).append($div)

        			oneObject.appendChild(li)
    			})
    		}
    	})
    },
    getMessage:function(){
    	$.ajax({
    		url: Global.host + "/order/selectMessage",
    		success: function(res){
    			//开始set
    			var oneObject = document.getElementById("messageList");
    			res.forEach(function(obj){
            		var li = document.createElement("li")
        			li.innerHTML = obj.mtcontent
        			oneObject.appendChild(li)
    			})
    		}
    	})
    },
    //获取常用会议室
    getRoomList:function(){
        $.ajax({
            url: Global.host + "/room/selectOften",
            success: function (res) {
                // console.log(res)
                var arr=res
                if(arr.length>0){
                    var ul=document.getElementById("one")
                    arr.forEach(function(item,index){
                        var li=document.createElement("li")
                        li.setAttribute("data-rid",item.rid)
                        li.style.cursor="pointer"
                        li.innerHTML=item.rname
                        if(index==0){
                            li.classList.add("myLeft_tabCon_act")
                            console.log(item)
                            //默认的会议室 常用会议室 第一个
                            Index.rid=item.rid
                            Index.rname=item.rname
                        }
                        ul.appendChild(li)
                    })
                }
                //同步表格标题
                Index.sameTableTitle()

                //获取这一周的选定时间段
                Index.getRoomTime()
            }
        });
    },
    //表格标题和常用会议室名称同步
    sameTableTitle:function(){
        var li=document.querySelector(".myLeft_tabCon_act")
        var caption=document.querySelector(".table_caption")
        caption.innerHTML=li.innerHTML
    },
    //获取当前选择的info
    getSelectedInfo:function(){
        var selectedTd=document.querySelectorAll(".mSelected")
        if(selectedTd.length<=1){
            alert("请选择至少30分钟时间段")
            return false
        }
        var firstTd=selectedTd[0]
        var lastTd=selectedTd[selectedTd.length-1]
        var $firstPare=$(firstTd).closest("tr")
        var $lastPare=$(lastTd).closest("tr")
        //日期
        var index=$firstPare.find("td").index($(firstTd))
        // console.log(index)
        var date=document.querySelectorAll(".date_content th")[index].getElementsByClassName("tab-date")[0].getAttribute("data-date")
        // console.log(date)
        //时间段
        var startTime=$firstPare.find("td").eq(0).html()
        var endTime=$lastPare.find("td").eq(0).html()

        return{
            date:date,
            startTime:startTime,
            endTime:endTime,

            rid:Index.rid, //会议室id
            rname:Index.rname,
        }
    },
    //跳转到 预订页面
    gotoBook:function(){
        var infoObj=Index.getSelectedInfo()
        if(infoObj){
            //判断该会议室是否锁定
            Index.checkLockRoom(function(){
                localStorage.setItem("infoObj",JSON.stringify(infoObj))
                window.location.href="index_detail.html"
            })

            // localStorage.setItem("infoObj",JSON.stringify(infoObj))
            // window.location.href="index_detail.html"
        }
    },
    checkLockRoom:function(callback){
        $.ajax({
            type:"POST",
            url: Global.host + "/user/checkLockRoom",
            data:{
                rid:Index.rid,
            },
            success: function (res) {
                console.log(res)
                // -1：不可进入预订界面
                // 0：可以预定
                if(res==0){
                    callback()
                }else{
                    alert("该会议室正在被编辑，请稍后操作")
                }
            }
        })
    },
    //筛选会议室
    searchRoom:function(){
        //获取参数
        let value1=document.getElementById("searchname").value.trim()
        let value2=document.getElementById("searchbuild").value.trim()
        let value3=document.getElementById("searchfloor").value.trim()
        //验证
        if(value1==""&&value2==""&&value3==""){
            alert("请输入至少一个查询条件")
            return
        }

        let rname=value1==""?null:value1
        let rbudingnubmer=value2==""?null:Number(value2)
        let rfloor=value3==""?null:Number(value3)

        var postData={
            pageSize:200,
            page:1,

            rname:rname,
            rbudingnubmer:rbudingnubmer,
            rfloor:rfloor
        }
        console.log(postData)
        $.ajax({
            type:"POST",
            url: Global.host + "/room/selectRoomByClientInsert",
            data:postData,
            success: function (res) {
                console.log(res)
                //显示搜索结果
                if(res.list.length>0){
                    let arr=res.list
                    $("#showResultsWrap").show()
                    let ul=document.getElementById("searchResults")
                    ul.innerHTML=""
                    arr.forEach(function(obj){
                        let li=document.createElement("li")
                        li.innerHTML=obj.rname
                        li.setAttribute("data-rid",obj.rid)
                        li.setAttribute("data-rintroduce",obj.rintroduce)
                        if(obj.rpic&&obj.rpic!==""){
                            let src=obj.rpic.split(",")[0]
                            li.setAttribute("data-imgsrc",src)
                        }
                        ul.appendChild(li)
                    })
                }else{
                    alert("没有您查询的数据")
                }
            }
        })
    },
    // //展示待办事项详情
    // showUpcoming:function(li){
    //     var $div=$('<div class="todo">'+
    //                     '<div class="todo_top todo_btn"><span>会议室名</span><span class="todo_top_fr">发过来的的时间</span></div>'+
    //                     '<div class="todo_main">您有一场会议要参加</div>'+
    //                     '<div class="todo_btn">'+
    //                         '<button class="btn1">确认</button>'+
    //                         '<button>取消</button>'+
    //                     '</div>'+
    //                 '</div>')
    //     $(li).append($div)
    // },
    fromUpcoming:function(el){
        var mtid=Number($(el).attr("data-mtid"))
        var ocid=Number($(el).attr("data-ocid"))
        switch(mtid){
            //普通用户
            case 2: //确认对接时间
                window.location.href="../member/index_detail_look_docking.html?ocid="+ocid
                break
            case 3: //待参会
                window.location.href="../member/index_detail_look.html?ocid="+ocid
                break
            //审核部门
            case 4: //待审核
                window.location.href="../approval/approval_detail.html?ocid="+ocid
                break
            //服务部门
            case 5: //会议准备事项
                window.location.href="../member/index_detail_look.html?ocid="+ocid
                break
            default:
                break
        }
    },
    changeWeek:function(type){ //1上一周 2下一周
        var datetime=document.getElementById("datetime")

        var oldDate=datetime.value
        var date=new Date(oldDate)
        var newdate
        if(Number(type)==1){
            newdate=new Date(date.getTime()-7*86400000)
        }else if(Number(type)==2){
            newdate=new Date(date.getTime()+7*86400000)
        }
        var newDate=Global.dateToFormat(newdate).substr(0,10)
        console.log(oldDate)
        console.log(newDate)

        datetime.value=newDate
    },
    eventsBind:function(){
        //选择时间段
        Index.selectTime()
        //上一周 下一周
        document.getElementById("preWeekBtn").onclick=function(event){
            event.stopPropagation()
            Index.changeWeek(1)
            Index.getRoomTime()
        }
        document.getElementById("nextWeekBtn").onclick=function(event){
            event.stopPropagation()
            Index.changeWeek(2)
            Index.getRoomTime()
        }
        //上一周 下一周 end
        //选择会议室
        Global.eventProxy(document.getElementById("one"),"li","click",function(ele){
            $(ele).addClass("myLeft_tabCon_act").siblings().removeClass("myLeft_tabCon_act")
            var rid=ele.getAttribute("data-rid")
            Index.rid=rid
            Index.rname=ele.innerHTML
            Index.getRoomTime()
        })
        //点击 我要预订
        document.getElementById("gotoBook").onclick=function () {
            Index.gotoBook()
        }
        //点击筛选的搜索
        document.getElementById("searchBtn").onclick=function(){
            Index.searchRoom()
        }
        //点击 搜索结果
        Global.eventProxy(document.getElementById("searchResults"),"li","click",function(ele){
            var rid=ele.getAttribute("data-rid")
            // console.log(rid)
            Index.rid=rid
            Index.rname=ele.innerHTML
            Index.getRoomTime()
        })
        //点击待办事项li
        $("#messageList").delegate("li","click",function(){
            // event.stopPropagation()
            Index.fromUpcoming(this)
        })
        //点击body清空表格
        $("body").click(function(event){
            console.log(event.target)
            if($(event.target).closest("tbody").length==0){
                $("td").removeClass("mSelected")
            }
        })
    },
    init:function(){
        Global.rightTabCut()
        Global.getMessageByLing()

        //初始化datetimepicker
        Index.initDatetimepicker()
        //获取常用会议室
        Index.getRoomList()
        //获取待办事项列表
        Index.getUnfinishedMessage();
        //事件绑定
        Index.eventsBind()
    }
}
$(function () {
    Index.init()
});