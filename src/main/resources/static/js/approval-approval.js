var Appro={
    num:6,

    getListCount:function(){
        $.ajax({
            url: Global.host + "/order/selectStateList",
            success: function (res) {
                console.log(res)
                //未审批订单总数
                document.getElementById("threeCount").innerHTML=res.three
                //已通过订单总数
                document.getElementById("twoCount").innerHTML=res.two
                //未通过订单总数
                document.getElementById("oneCount").innerHTML=res.one
            }
        })
    },
    getSomeList:function(){
        var postData={
            num:Appro.num //前几条
        }
        console.log(postData)
        $.ajax({
            url: Global.host + "/order/selectListByCheckAndDeft",
            data:postData,
            success: function (res) {
                console.log(res)
                var list1=res.list1 //待审批
                var list2=res.list2 //已通过
                var list3=res.list3 //未通过

                var arr=[]

                arr[0]=list3[0]?list3[0]:"无数据" //第一行
                arr[1]=list2[0]?list2[0]:"无数据"
                arr[2]=list1[0]?list1[0]:"无数据"

                arr[3]=list3[1]?list3[1]:null
                arr[4]=list2[1]?list2[1]:null
                arr[5]=list1[1]?list1[1]:null
                console.log(arr)
                //update list
                Appro.updateList(arr)
            }
        })
    },    
    /*获取普通用户的待办事项*/
    getUnfinishedMessage:function(){
    	$.ajax({
    		url: Global.host + "/order/selectMessageByApproval",
    		success: function(res){
                console.log(res)
    			//开始set
    			var oneObject = document.getElementById("messageList");
    			res.forEach(function(obj){
            		var li = document.createElement("li")
                    li.innerHTML = obj.mtcontent
                    li.setAttribute("data-mtid",obj.mtid)
                    li.setAttribute("data-ocid",obj.ocid)
        			oneObject.appendChild(li)
    			})
    		}
    	})
    },
    updateList:function(arr){
        if(arr.length!==0){
            var pareDiv=document.getElementById("itemWrap")
            arr.forEach(function(obj){
                console.log(obj)
                var div=document.createElement("div")
                div.className="state-contentc"
                if(obj==null){
                    div.innerHTML=''
                    div.style.background="none"
                    div.style.boxShadow="none"
                }else if(obj=="无数据"){
                    div.innerHTML='<img style="display:block;margin:0 auto" src="../images/暂无相关记录.png"><p style="text-align:center;">暂无相关记录</p>'
                }else{
                    var begintime=Global.dateToFormat(new Date(obj.ocbegintime))
                    var endtime=Global.dateToFormat(new Date(obj.ocendtime))
                    var date=begintime.substr(0,10)
                    console.log(begintime,endtime)
                    var classStr=""
                    var textStr=""
                    if(Number(obj.opassstate)==3){ //3
                        classStr="approval_btn"
                        textStr="待审批"
                    }else if(Number(obj.opassstate)==2){ //2
                        classStr="adopt_btn"
                        textStr="已通过"
                    }else{ //1
                        classStr="refuse_btn"
                        textStr="未通过"
                    }
                    div.innerHTML=`
                        <h1>${obj.rname}</h1>
                        <p>申请时间：${date} ${begintime.substr(begintime.length-5,5)}-${endtime.substr(endtime.length-5,5)}</p>
                        <p>申请人:${obj.name}</p>
                        <p>联系电话:${obj.tel}</p>
                        <button class="lookBtn">查看</button>
                        <div class="div1 ${classStr}">${textStr}</div>
                    `
                    div.setAttribute("data-ocid",obj.ocid)
                    div.setAttribute("data-opassstate",obj.opassstate)
                }
                pareDiv.appendChild(div)
            })
        }
    },
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
    eventsBind:function(){
        //点击查看
        $("#itemWrap").delegate(".lookBtn","click",function(){
            var ocid=$(this).closest(".state-contentc").attr("data-ocid")
            console.log(ocid)
            var opassstate=$(this).closest(".state-contentc").attr("data-opassstate")
            var url=""
            switch (Number(opassstate)) {
                case 3: //待审批 审批页面
                    url="../approval/approval_detail.html?ocid="+ocid
                    break;
                case 2: //已通过 查看页面
                    url="../member/index_detail_look.html?ocid="+ocid
                    break;
                default: //查看页面
                    url="../member/index_detail_look.html?ocid="+ocid
                    break;
            }
            window.location.href=url
        })
        //点击待办事项
        $("#messageList").delegate("li","click",function(){
            Appro.fromUpcoming(this)
        })
        //点击数量
        $("ul.content_menu li a").click(function(event){
            var opassstate=$(this).attr("data-opassstate")
            console.log(opassstate)
            window.location.href="../approval/approval_list.html?opassstate="+opassstate
            return false
        })
    }, 
    init:function(){
        Global.getMessageByLing()
        Appro.getUnfinishedMessage()
        //获取各类状态订单数量
        Appro.getListCount()
        //获取前6条订单
        Appro.getSomeList()
        //事件绑定
        Appro.eventsBind()
    }
}
$(function () {
    Appro.init()
});