var Global=(function(){
    var host="http://localhost:8089"
    
    //date转格式
    function dateToFormat(date) { //date类型
        Y = date.getFullYear() + '-';
        M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
        D = (date.getDate() < 10 ? "0" + date.getDate() : date.getDate()) + ' ';
        h = (date.getHours() < 10 ? "0" + date.getHours() : date.getHours()) + ':';
        m = (date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes());
        // s = date.getSeconds();
        return Y + M + D + h + m;
    }
    //获取是星期几
    function getWeekDay(date){ //date类型
        var weekDay = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
        return weekDay[date.getDay()]
    }
    //hasClass
    function hasClass(ele,classStr){
        let arr=Array.prototype.slice.apply(ele.classList)
        return arr.indexOf(classStr)>-1
    }
    //原生事件代理
    function eventProxy(parent,selectStr,eventStr,func){
        parent.addEventListener(eventStr,function(){
            let target=event.target
            let flag=false
            if (selectStr.indexOf("#")>-1) { //id选择器        
                flag=(target.id===selectStr.slice(1))
            }else if(selectStr.indexOf(".")>-1){ //class选择器
                flag=(hasClass(target,selectStr.slice(1)))
            }else{ //默认tag选择器
                flag=(target.tagName.toLowerCase() === selectStr.toLowerCase())
            }
            if(flag){
                func(target)
            }
        })
    }
    //js保存canvas到本地图片
    function canvasToLocalImg(canvas){
        //图片导出为 png 格式
        var type = 'png';
        var imgData = canvas.toDataURL(type);

        /**
         * 获取mimeType
         * @param  {String} type the old mime-type
         * @return the new mime-type
        */
        var _fixType = function(type) {
        type = type.toLowerCase().replace(/jpg/i, 'jpeg');
        var r = type.match(/png|jpeg|bmp|gif/)[0];
        return 'image/' + r;
        }; 

        // 加工image data，替换mime type
        imgData = imgData.replace(_fixType(type),'image/octet-stream');

        /**
         * 在本地进行文件保存
         * @param  {String} data     要保存到本地的图片数据
         * @param  {String} filename 文件名
         */
        var saveFile = function(data, filename){
            var save_link = document.createElementNS('http://www.w3.org/1999/xhtml', 'a');
            save_link.href = data;
            save_link.download = filename;

            var event = document.createEvent('MouseEvents');
            event.initMouseEvent('click', true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
            save_link.dispatchEvent(event);
        };

        // 下载后的文件名
        var filename = '订单'+new Date().getTime()+'.'+type
        
        // download
        saveFile(imgData,filename);
    }
    //根据权限更新上面tabcut选项
    function rightTabCut(){
        if(localStorage.getItem("userRight")){
            let right=Number(localStorage.getItem("userRight"))
            //1.普通用户 2.主管部门 3.服务部门 4.没有权限的 5.管理员 6.用户不存在
            //更新dom
            switch(right){
                case 1:
                    break
                case 2:
                    $("#approTab").show()
                    break
                case 3:
                    $("#serviceTab").show()
                    break
                case 4:
                    break
                case 5:
                    break
            }
        }
    }
    //新增图片样式resize
    function resizeHeadpic(imgEle){
        console.log(imgEle.width,imgEle.height)
        if(imgEle.width>imgEle.height){
            // imgEle.style.width="auto"
            // imgEle.style.height="100%"
            imgEle.style.width="100%"
            imgEle.style.height="auto"
        }else{
            // imgEle.style.width="100%"
            // imgEle.style.height="auto"
            imgEle.style.width="auto"
            imgEle.style.height="100%"
        }
    }
    function openLoading(){
        if($(".mLoading").length==0){
            var $div=$('<div class="mLoading" style="position:fixed;top:0;bottom:0;left:0;right:0;background-color: rgba(0,0,0,0.2);z-index:999;"><img src="../images/loading.gif" style="position:absolute;top:50%;left:50%;transform:translate(-50%,-50%);"></div>')
            $("body").append($div)
        }
    }
    function closeLoading(){
        if($(".mLoading").length>0){
            $(".mLoading").remove()
        }
    }
    //查询铃铛（消息）
    function getMessageByLing(){
        //点击铃铛图标显示
        $('.header_icon').click(function (event) {
            event.stopPropagation()
            $('.xiaoxi').toggle();
        }) 
        $("body").click(function(event){
            if($(event.target).closest("#xiaoxi").length==0){
                $('.xiaoxi').hide();
            }
        })
        
        if(localStorage.getItem("userRight")){
            var url=""
            var right=Number(localStorage.getItem("userRight"))
            console.log(right)
            //1.普通用户 2.主管部门 3.服务部门 4.没有权限的 5.管理员 6.用户不存在
            //更新dom
            switch(right){
                case 1: //普通用户
                    url=host+"/order/selectInformMessageByMember"
                    break
                case 2: //主管部门
                    url=host+"/order/selectInformMessageByApproval"
                    break
                case 3: //服务部门
                    url=host+"/order/selectInformMessageByService"
                    break
                case 4: 
                    url=host+"/order/selectInformMessageByMember"
                    break
                case 5:
                    url=host+"/order/selectInformMessageByMember"
                    break
                default:
                    break
            }
            console.log(url)
            $.ajax({
                url: url,
                success: function(res){
                    console.log(res)
                    var arr=res
                    if(arr.length==0){
                        $(".dian").hide()
                        return
                    }
                    //开始set
                    var oneObject = document.getElementById("xiaoxi");
                    res.forEach(function(obj){
                        var mcdate = Global.dateToFormat(new Date(obj.mcdate));
                        var li = document.createElement("li")
                        li.innerHTML =  '<div class="xiaoxi_content">'+obj.mtcontent+'</div>'+
                                        '<div class="xiaoxi_name clearfix">'+
                                            '<span class="xiaoxi_date">'+mcdate+'</span>'+
                                        '</div>';
                        li.setAttribute("data-mtid",obj.mtid)
                        li.setAttribute("data-ocid",obj.ocid)
                        oneObject.appendChild(li)
                    })
                }
            })

            //绑定铃铛消息事件
            $("#xiaoxi").delegate("li","click",function(){
                event.stopPropagation()
                // var mtid=Number($(this).attr("data-mtid"))
                var ocid=Number($(this).attr("data-ocid"))
                // switch(mtid){
                //     //普通用户
                //     case 6: //订单被修改
                //         window.location.href="../member/index_detail_look_docking.html?ocid="+ocid
                //         break
                //     case 7: //订单被通过
                //         window.location.href="../member/index_detail_look.html?ocid="+ocid
                //         break
                //     case 8: //订单未通过
                //         window.location.href="../approval/approval_detail.html?ocid="+ocid
                //         break
                //     //审核部门
                //     case 1: //取消预订
                //         break
                //     //服务部门
                //     default:
                //         break
                // }
                window.location.href="../member/index_detail_look.html?ocid="+ocid
            })
        }
    }
    //获取服务人员信息by id(md5码)
    function getSerPerInfo(hospitalCode,unitCode,staffId,callback){
        var postData={
            type:"2", //院外2 院内1

            hospitalCode:hospitalCode, //医院编码
            unitCode:unitCode, //主管部门编码
            staffId:staffId, //职工id
        }
        console.log(postData)
        $.ajax({
            url: "http://218.247.12.42:8196/outsourcedController/getStaffInfoByType",
            data:postData,
            success: function (res) {
                console.log(res)
                var obj={
                    name:res.data.name,
                    mobile:res.data.mobile
                }
                callback(obj)
            }
        })
    }
    //---------------------------------------------
    return{
        host:host,

        dateToFormat:dateToFormat, //参数：date
        eventProxy:eventProxy,
        canvasToLocalImg:canvasToLocalImg,
        rightTabCut:rightTabCut,
        resizeHeadpic:resizeHeadpic,
        openLoading:openLoading,
        closeLoading:closeLoading,
        getMessageByLing:getMessageByLing,
        getSerPerInfo:getSerPerInfo,
        getWeekDay:getWeekDay,
    }
})()