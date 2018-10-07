var Detail={
    ocid:"",
    infoArr:[], //数组

    oid:"",
    

    //ocid oid
    getOcid:function(){
        var url=window.location.href
        var ocid=url.split("?ocid=")[1]
        Detail.ocid=Number(ocid)
        Detail.getInfo()

        if(url.indexOf("service_detail")==-1){ //不是服务的订单详情页才get
            Detail.getSerPerson()
        }
    },
    getInfo:function(){
        var postData={
            ocid:Detail.ocid
        }
        console.log(postData)
        $.ajax({
            type: "POST",
            url: Global.host + "/order/selectOneInfo",
            data:postData,
            success: function (res) {
                console.log(res)
                Detail.infoArr=res
                //更新dom
                Detail.updateDom()

                //保存oid //基本信息
                if(res[0].oid){
                    Detail.oid=Number(res[0].oid)  
                }
            }
        })
    },
    getSerPerson:function(){
        var postData={
            ocid:Detail.ocid
        }
        console.log(postData)
        Global.openLoading()
        $.ajax({
            type: "POST",
            url: Global.host + "/servicepeo/selectServicepeoByOcid",
            data:postData,
            success: function (res) {
                console.log(res)
                if(res&&res.length>1){
                    $("#serPerTable").show()
                    var serPerArr=res.slice(0,res.length-1)
                    var option=res[res.length-1]
                    console.log(serPerArr)
                    console.log(option)
                    var serPerTbody=document.getElementById("serPerTbody")
                    serPerArr.forEach(function(value,index){
                        Global.getSerPerInfo(option.hcode,option.unitCode,value,function(obj){
                            if(index==serPerArr.length-1){
                                Global.closeLoading()
                            }
                            var tr=document.createElement("tr")
                            tr.innerHTML='<td class="detail_input">'+obj.name+'</td><td class="detail_input">'+obj.mobile+'</td>';
                            serPerTbody.appendChild(tr)
                        })
                    })
                }else{
                    Global.closeLoading()
                }
            }
        })
    },
    updateDom:function(){
        var info=Detail.infoArr[0]
        var inforname=Detail.infoArr[4]
        //会议室主题
        document.getElementById("ocsourcename").value=info.ocsourcename
        //会议室名称? 没返给我rname
        document.getElementById("rname").value = inforname
        //会议时间
        var begintime=Global.dateToFormat(new Date(info.ocbegintime))
        var endtime=Global.dateToFormat(new Date(info.ocendtime))
        document.getElementById("meetingtime").value=begintime.substr(0,10)+"  "+begintime.substr(begintime.length-5,5)+"--"+endtime.substr(endtime.length-5,5)
        //参会人数
        document.getElementById("ocnum").value=info.ocnum
        //是否重复??????

        //会议预定联系人
        document.getElementById("ocreservename").value=info.ocreservename
        //会议预定联系人电话
        document.getElementById("ocreservephone").value=info.ocreservephone
        //会议使用联系人
        document.getElementById("ocusename").value=info.ocusename
        //会议使用联系人电话
        document.getElementById("ocusephone").value=info.ocusephone
        //台型？没返给我？

        //是否与服务人员对接
        if(info.ocdockingstate){
            document.getElementById("ocdockingstate"+info.ocdockingstate).checked=true
        }
        if(info.ocdockingtime&&info.ocdockingtime!==""){
            document.getElementById("ocdockingtime").value=Global.dateToFormat(new Date(info.ocdockingtime))
        }
        //会议物资联系人
        if(info.ocmaterialsname&&info.ocmaterialsname!==""){
            document.getElementById("ocmaterialsname").value=info.ocmaterialsname
        }
        //会议物资联系人电话
        if(info.ocmaterialsphone&&info.ocmaterialsphone!==""){
            document.getElementById("ocmaterialsphone").value=info.ocmaterialsphone
        }
        //大车位数量
        if(info.ocbigcarnum&&info.ocbigcarnum!==""){
            document.getElementById("ocbigcarnum").value=info.ocbigcarnum
        }
        //小车位数量
        if(info.ocsmallnum&&info.ocsmallnum!==""){
            document.getElementById("ocsmallnum").value=info.ocsmallnum
        }
        //展台
        if(info.ocshowplatform&&info.ocshowplatform!==""){
            document.getElementById("ocshowplatform").value=info.ocshowplatform
        }
        //展架
        if(info.ocshowshelf&&info.ocshowshelf!==""){
            document.getElementById("ocshowshelf").value=info.ocshowshelf
        }
        //用电功率
        if(info.ocpower&&info.ocpower!==""){
            document.getElementById("ocpower").value=info.ocpower
        }
        //附件?????

        //备注
        if(info.ocremark&&info.ocremark!==""){
            document.getElementById("ocremark").value=info.ocremark
        }
        //基础物品
        if(Detail.infoArr[1]&&Detail.infoArr[1].length!==0){
            var baseGoods=Detail.infoArr[1]
            var baseGoodsTbody=document.getElementById("baseGoods")
            console.log(baseGoodsTbody)
            baseGoods.forEach(function(obj){
                var tr=document.createElement("tr")
                tr.innerHTML=`
                    <td class="detail_input"><input type="text" name="" value=${obj.boname} readonly="readonly"></td>
                    <td class="detail_input"><input type="text" name="" value=${obj.bonum} readonly="readonly"></td>
                `
                baseGoodsTbody.appendChild(tr)
            })
        }
        //增值物品
        if(Detail.infoArr[2]&&Detail.infoArr[2]!==0){
            var addGoods=Detail.infoArr[2]
            var addGoodsTbody=document.getElementById("addGoods")
            addGoods.forEach(function(obj){
                var tr=document.createElement("tr")
                var sum=Math.round(obj.aoprice*Number(obj.aonum)*100)/100
                console.log(sum)
                tr.innerHTML=`
                    <td class="detail_input"><input type="text" name="" value=${obj.aoname} readonly="readonly"></td>
                    <td class="detail_input"><input type="text" name="" value=${obj.aoprice} readonly="readonly"></td>
                    <td class="detail_input"><input type="text" name="" value=${obj.aonum} readonly="readonly"></td>
                    <td class="detail_input"><input type="text" name="" value=${sum} readonly="readonly"></td>
                `
                addGoodsTbody.appendChild(tr)
            })
        }
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
                    endstamp=startstamp+num*86400000
                    break;
                case 2: //每周
                    endstamp=startstamp+num*7*86400000
                    break;
                case 3: //每月
                    endstamp=startstamp+num*30*86400000
                    break;
                default:
                    break;
            }
            var startDate=Global.dateToFormat(new Date(endstamp))
            document.getElementById("endDate").value=startDate.substr(0,10)
        }
    },
    eventsBind:function(){
        //点击返回
        document.getElementById("back").onclick=function(){
            window.history.back(-1)
        }
    }, 
    init:function(){
        Global.rightTabCut()
        Global.getMessageByLing()
        //获取页面参数 获取ocid
        Detail.getOcid()
        //事件绑定
        Detail.eventsBind()
    }
}
$(function () {
    Detail.init()
});