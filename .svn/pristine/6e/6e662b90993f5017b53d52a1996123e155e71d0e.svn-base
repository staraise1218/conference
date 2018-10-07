var Detail={
    infoObj:{},


    getSelectDate:function(){
        if(localStorage.getItem("infoObj")&&localStorage.getItem("infoObj")!==null&&localStorage.getItem("infoObj")!=="null"){
            var infoObjStr=localStorage.getItem("infoObj")
            var infoObj=JSON.parse(infoObjStr)
            console.log(infoObj)
            Detail.infoObj=infoObj

            //更新页面dom
            Detail.updateDom()
        }
    },
    updateDom:function(){
        //会议室名称
        document.getElementById("rname").value=Detail.infoObj.rname
        //会议时间
        document.getElementById("meetingtime").value=Detail.infoObj.date+"  "+Detail.infoObj.startTime+" -- "+Detail.infoObj.endTime
        //开始日期
        document.getElementById("startDate").value=Detail.infoObj.date
        //会议预定联系人 会议使用联系人
        if(localStorage.getItem("mUserInfo")&&localStorage.getItem("mUserInfo")!==null&&localStorage.getItem("mUserInfo")!=="null"){
            var mUserInfo=JSON.parse(localStorage.getItem("mUserInfo"))
            console.log(mUserInfo)
            document.getElementById("ocreservename").value=mUserInfo.name?mUserInfo.name:""
            document.getElementById("ocreservephone").value=mUserInfo.phone?mUserInfo.phone:""

            document.getElementById("ocusename").value=mUserInfo.name?mUserInfo.name:""
            document.getElementById("ocusephone").value=mUserInfo.phone?mUserInfo.phone:""
        }
    },
    //获取台型
    getTaixing:function(){
        $.ajax({
            url: Global.host + "/cplatform/selectList",
            success: function (res) {
                console.log(res)
                var arr=res
                var taixing=document.getElementById("taixingSelect")
                arr.forEach(function(obj){
                    var option=document.createElement("option")
                    option.innerHTML=obj.cname
                    option.setAttribute("value",Number(obj.cid))
                    option.setAttribute("data-src",obj.cpic)
                    taixing.appendChild(option)
                })
            }
        })
    },
    initDatetimepicker:function(){
        // var nowDate=Global.dateToFormat(new Date()).substr(0,10)
        // document.getElementById("datetime").value=nowDate
        $("#ocdockingtime").datetimepicker({
            format: 'yyyy-mm-dd hh:ii',
            language: 'zh-CN',//显示中文
            autoclose:true
        }).on("hide",function(e){
            // //关闭选择时间面板触发
            // var date=e.currentTarget.value
            // Index.weekNum=0
            // console.log(date)
            // //获取这一周的选定时间段
            // Index.getRoomTime(Index.rid,Index.weekNum)
        });
    },
    validForm:function(){
        //会议主题必填
        if(document.getElementById("ocsourcename").value.trim()===""){
            // alert("请输入会议主题")
            $("#submitOpen").text("请输入会议主题");
            $("#mySubmit").modal("show")
            document.getElementById("confirm").onclick=function(){
                $("#mySubmit").modal("hide")
            }
            return false
        }
        //参会人数
        var value1=document.getElementById("ocnum").value
        if(value1.trim()===""){
            // alert("请输入参会人数")
            $("#submitOpen").text("请输入参会人数");
            $("#mySubmit").modal("show")
            document.getElementById("confirm").onclick=function(){
                $("#mySubmit").modal("hide")
            }
            return false
        }
        var reg1=/^[0-9]*$/
        if(!reg1.test(value1)){
            // alert("参会人数请填写数字")
            $("#submitOpen").text("参会人数请填写数字");
            $("#mySubmit").modal("show")
            document.getElementById("confirm").onclick=function(){
                $("#mySubmit").modal("hide")
            }
            return false
        }
        //会议预定联系人	
        var value2=document.getElementById("ocreservename").value
        if(value2.trim()===""){
            // alert("请填写会议预定联系人")
            $("#submitOpen").text("请填写会议预定联系人");
            $("#mySubmit").modal("show")
            document.getElementById("confirm").onclick=function(){
                $("#mySubmit").modal("hide")
            }
            return false
        }
        var reg2=/^[\u4e00-\u9fa5]{2,7}$/
        if(!reg2.test(value2)){
            // alert("请输入正确格式的姓名")
            $("#submitOpen").text("请输入正确格式的姓名");
            $("#mySubmit").modal("show")
            document.getElementById("confirm").onclick=function(){
                $("#mySubmit").modal("hide")
            }
            return false
        }
        //会议预定联系人 联系方式
        var value3=document.getElementById("ocreservephone").value
        if(value3.trim()===""){
            // alert("请输入会议预定联系人的联系方式")
            $("#submitOpen").text("请输入会议预定联系人的联系方式");
            $("#mySubmit").modal("show")
            document.getElementById("confirm").onclick=function(){
                $("#mySubmit").modal("hide")
            }
            return false
        }
        //会议使用联系人
        var value4=document.getElementById("ocusename").value
        if(value4.trim()===""){
            // alert("请输入会议使用联系人")
            $("#submitOpen").text("请输入会议使用联系人");
            $("#mySubmit").modal("show")
            document.getElementById("confirm").onclick=function(){
                $("#mySubmit").modal("hide")
            }
            return false
        }
        //会议使用联系人 联系方式
        var value5=document.getElementById("ocusephone").value
        if(value5.trim()===""){
            // alert("请输入会议使用联系人的联系方式")
            $("#submitOpen").text("请输入会议使用联系人的联系方式");
            $("#mySubmit").modal("show")
            document.getElementById("confirm").onclick=function(){
                $("#mySubmit").modal("hide")
            }
            return false
        }
        return true
    },  
    submit:function(){
        //验证form
        if(!Detail.validForm()){
            return
        }
        var postData={
            rid:Number(Detail.infoObj.rid), //会议室id
            ocnum:Number(document.getElementById("ocnum").value), //参会人数
            stringocbegintime:Detail.infoObj.date+" "+Detail.infoObj.startTime+":00", //开始时间 
            stringocendtime:Detail.infoObj.date+" "+Detail.infoObj.endTime+":00", //结束时间
            ocsourcename:document.getElementById("ocsourcename").value, //物资联系人? 改成会议主题
            ocusename:document.getElementById("ocusename").value, //会议使用联系人
            ocusephone:document.getElementById("ocusephone").value, //会议使用联系人电话
            ocreservename:document.getElementById("ocreservename").value, //会议预定联系人
            ocreservephone:document.getElementById("ocreservephone").value, //会议预定联系人电话
            ocdockingstate:$("#ocdockingstate").is(':checked')?1:2, //是否与服务人员对接（1.是 2.否）
            stringocdockingtime:document.getElementById("ocdockingtime").value+":00", //对接时间 
            ocmaterialsname:document.getElementById("ocmaterialsname").value.trim()==""?null:document.getElementById("ocmaterialsname").value.trim(), //会议物资联系人
            ocmaterialsphone:document.getElementById("ocmaterialsphone").value.trim()==""?null:document.getElementById("ocmaterialsphone").value.trim(), //会议物资联系人电话
            ocbigcarnum:document.getElementById("ocbigcarnum").value.trim()==""?null:Number(document.getElementById("ocbigcarnum").value.trim()), //大车位数目
            ocsmallnum:document.getElementById("ocsmallnum").value.trim()==""?null:Number(document.getElementById("ocsmallnum").value.trim()), //小车位数目
            ocshowplatform:document.getElementById("ocshowplatform").value.trim()==""?null:Number(document.getElementById("ocshowplatform").value.trim()), //展台个数
            ocshowshelf:document.getElementById("ocshowshelf").value.trim()==""?null:Number(document.getElementById("ocshowshelf").value.trim()), //展架个数
            ocpower:document.getElementById("ocpower").value.trim()==""?null:document.getElementById("ocpower").value.trim(), //功率
            ocremark:document.getElementById("ocremark").value.trim()==""?null:document.getElementById("ocremark").value.trim(), //备注
            cid:document.getElementById("taixingSelect").value.trim()==""?null:Number(document.getElementById("taixingSelect").value.trim()), //台型
            
            //页面里没有的：
            // ocarrangement:"", //会议布置情况
            // ocagenda:"", //议程
            // ocprintnum:"", //会议议程打印份数
            // ocplatform:"", //主席台是否摆放（1.是 2.否）
            // ocpeoname:"", //参会人名
            // serid:"", //服务人员id

            // ocaccessory:"", //附件
                
           
            otype:$("#isRepeat").is(':checked')?2:1, //是否重复预定（1.不重复 2.重复）
            unit:Number(document.getElementById("unit").value), //重复的单位（1.天 2.周 3.月）
            num:Number(document.getElementById("num").value), //重复次数
            //物品参数？

            // ocaccessoryfile:"",
            ocaccessoryfile:null,
        }
        console.log(postData)
        $.ajax({
            type: "POST",
            url: Global.host + "/user/reserve",
            data:postData,
            success: function (res) {
                console.log(res)
                //判断状态？
                //-3：用户的预定时间在当前时间之前 -2：会议室被删除 -1:该会议室正在被编辑 0:预定冲突 1：预定成功 
                switch(Number(res)){
                    case 1:
                        //alert("预定成功");
                        $("#submitOpen").text("预定成功");
                        // $("#confirm").click(function(){window.location.href="index_list.html"})
                        $("#mySubmit").modal("show")
                        document.getElementById("confirm").onclick=function(){
                            $("#mySubmit").modal("hide")
                            window.location.href="index_list.html"
                        }
                        break
                    case 0:
                        // alert("预定冲突")
                        $("#submitOpen").text("预定冲突")
                        $("#mySubmit").modal("show")
                        document.getElementById("confirm").onclick=function(){
                            $("#mySubmit").modal("hide")
                        }
                        break
                    case -1:
                        // alert("该会议室正在被编辑")
                        $("#submitOpen").text("该会议室正在被编辑")
                        $("#mySubmit").modal("show")
                        document.getElementById("confirm").onclick=function(){
                            $("#mySubmit").modal("hide")
                        }
                        break
                    case -2:
                        // alert("该会议室已被删除")
                        $("#submitOpen").text("该会议室已被删除")
                        $("#mySubmit").modal("show")
                        document.getElementById("confirm").onclick=function(){
                            $("#mySubmit").modal("hide")
                        }
                        break
                    case -3:
                        // alert("不能预定已过去的时间")
                        $("#submitOpen").text("不能预定已过去的时间")
                        $("#mySubmit").modal("show")
                        document.getElementById("confirm").onclick=function(){
                            $("#mySubmit").modal("hide")
                        }
                        break
                }
            }
        })
    },
    //重复时，更改开始日期和结束日期
    startEndDate:function(){
        var type=Number(document.getElementById("unit").value) //类型
        var num=Number(document.getElementById("num").value.trim()) //重复次数
        console.log(num)
        if(num!==0){
            //开始时间固定的
            var startTime=Detail.infoObj.date+" "+Detail.infoObj.startTime
            console.log(startTime)
            var startstamp=new Date(startTime).getTime()
            //更改结束日期
            var endstamp
            switch (type){
                case 1: //每天
                    endstamp=startstamp+(num-1)*86400000
                    break;
                case 2: //每周
                    endstamp=startstamp+(num-1)*7*86400000
                    break;
                case 3: //每月
                    endstamp=startstamp+(num-1)*30*86400000
                    break;
                default:
                    break;
            }
            var startDate=Global.dateToFormat(new Date(endstamp))
            document.getElementById("endDate").value=startDate.substr(0,10)
        }
    },
    checkLockRoom:function(callback){
        $.ajax({
            type:"POST",
            url: Global.host + "/user/checkLockRoom",
            data:{
                rid:Detail.infoObj.rid,
            },
            success: function (res) {
                console.log(res)
                // -1：不可进入预订界面
                // 0：可以预定
                callback(res)
            }
        })
    },
    eventsBind:function(){
        //点击是否重复
        $(":radio[name='order']").click(function(){
            console.log(this.value)
            if(this.value==2){ //重复
                $(".repeatTr").show()
            }else{
                $(".repeatTr").hide()
            }
        })
        //更改结束日期
        $("#unit").change(function(){
            Detail.startEndDate()
        })
        $("#num").blur(function(){
            Detail.startEndDate()
        })
        //更改结束日期 end
        //点击提交
        document.getElementById("submitBtn").onclick=function(){
            console.log(55)
            Detail.submit()
        }
        //点击取消
        document.getElementById("backBtn").onclick=function(){
            window.history.back(-1)
        }
        //离开页面前 解锁正在编辑的会议室
        window.onbeforeunload= function(event) {
            //解锁该会议室
            $.ajax({
                type:"POST",
                url: Global.host + "/user/releaseRoom",
                data:{
                    rid:Detail.infoObj.rid,
                },
                success: function (res) {
                    console.log(res)
                }
            })

            localStorage.setItem("test","biubiubiu")

            return "确定离开此页面吗？";
        }
    }, 
    init:function(){
        Global.rightTabCut()
        Global.getMessageByLing()
        //获取页面参数
        Detail.getSelectDate()
        // //检查当前会议室是否锁定
        // Detail.checkLockRoom(function(res){
        //     console.log(res)
        //     if(!res==0){ //不可以预定
        //         alert("该会议室正在编辑，请稍后操作")
        //         window.history.back(-1)
        //     }
        // })
        //获取台型
        Detail.getTaixing()
        //init datetimepicker
        Detail.initDatetimepicker()
        //事件绑定
        Detail.eventsBind()
    }
}
$(function () {
    Detail.init()
});