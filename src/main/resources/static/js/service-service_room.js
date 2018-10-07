var Room={
    rid:"", //这个会议室的rid
    roomInfo:{},
    

    //获取rid
    getRid:function(){
        let rid=Number(window.location.href.split("?rid=")[1])
        console.log(rid)
        Room.rid=rid
        //获取会议室信息
        Room.getRoomInfo()
    },
    getRoomInfo:function(){
        let postData={
            rid:Room.rid
        }
        console.log(postData)
        $.ajax({
            type:"POST",
            url: Global.host + "/room/selectOneRoomByRid",
            data:postData,
            success: function (res) {
                console.log(res)
                if(res){
                    Room.roomInfo=res
                    Room.updateDom()
                }
            }
        })
    },
    updateDom:function(){
        console.log("dom")
        let info=Room.roomInfo
        //会议室名称
        document.getElementById("rname-title").innerHTML=info.rname
        //地址
        document.getElementById("address").innerHTML=info.rlocation
        //会议室
        document.getElementById("rname").innerHTML=info.rname
        //预定间隔
        document.getElementById("rinterval").innerHTML=info.rinterval
        //所属部门
        document.getElementById("rdepartment").innerHTML=info.rdepartment
        //详情介绍
        document.getElementById("rintroduce").innerHTML=info.rintroduce
        //图片
        if(info.rpic&&info.rpic!==""){
            let srcArr=info.rpic.split(",")
            console.log(srcArr)
            let rimages=document.getElementById("rimages")
            srcArr.forEach(function(src){
                if(src&&src.trim()!==""){
                    let img=document.createElement("img")
                    img.setAttribute("src",src)
                    // img.style.width="400px"
                    // img.style.height="auto"
                    // img.style.border="1px solid red" //测试用
                    rimages.appendChild(img)
                }
            })
        }
    },
    //筛选rid
    filterRid:function(){
         //验证
        //会议室名称
        let value1=$("#rnameInput").val().trim()
        if(value1==""){
            alert("请输入会议室名称")
            return
        }
        //会议室楼号
        let value2=$("#rbuildingInput").val().trim()
        if(value2==""){
            alert("请输入会议室楼号")
            return
        }
        let reg1=/^[0-9]*$/
        if(!reg1.test(value2)){
            alert("楼号请输入数字")
            return
        } 
        //会议室楼层
        let value3=$("#rfloorInput").val().trim()
        if(value3==""){
            alert("请输入会议室楼层")
            return
        }
        if(!reg1.test(value3)){
            alert("楼层请输入数字")
            return
        } 
        //验证 end
        var postData={
            pageSize:10,
            page:1,

            rname:value1,
            rbudingnubmer:Number(value2),
            rfloor:Number(value3),
        }
        // postData={
        //     rname:"会议室1",
        //     rbudingnubmer:1,
        //     rfloor:2,
        // }
        console.log(postData)
        $.ajax({
            type:"POST",
            url: Global.host + "/room/selectRoomByClientInsert",
            data:postData,
            success: function (res) {
                console.log(res)
                let arr=res.list
                if(arr.length>0){
                    //更新右侧会议室信息
                    Room.roomInfo=arr[0]
                    Room.updateDom()
                }else{
                    alert("没有您查询的会议室")
                }
            }
        })
    },
    eventsBind:function(){
        //点击搜索确认按钮
        document.getElementById("searchBtn").onclick=function(){
            Room.filterRid()
        }
    }, 
    init:function(){
        Global.getMessageByLing()
        //获取rid
        Room.getRid()
        //事件绑定
        Room.eventsBind()
    }
}
$(function () {
    Room.init()
});