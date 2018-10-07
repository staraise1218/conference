var Manage={
    pageSize:10,
    total:"", //总数据条数

    curPage:1, //当前页码
    

    //生成部门select
    departmentSelect:function(){
        $.ajax({
            url: Global.host + "/room/selectDeptNameLitsByHcode",
            success: function (res) {
                // console.log(res)
                // let arr=res
                // let select=document.getElementById("departmentSelect")
                // arr.forEach(function(obj){
                //     let option=document.createElement("option")
                //     option.innerHTML=obj.officeName
                //     option.setAttribute("value",obj.officeId)
                //     option.setAttribute("data-officeId",obj.officeId)
                //     select.appendChild(option)
                // })
                console.log(res)
                let arr=res
                let selects=document.getElementsByClassName("departmentSelect")
                for(let i=0;i<selects.length;i++){
                    let select=selects[i]
                    arr.forEach(function(obj){
                        let option=document.createElement("option")
                        option.innerHTML=obj.officeName
                        option.setAttribute("value",obj.officeId)
                        option.setAttribute("data-officeId",obj.officeId)
                        select.appendChild(option)
                    })
                }
            }
        })
    },
    //获取列表数据
    getList:function(pageNum=1){
        //获取参数
        var rname=document.getElementById("nameInput").value.trim()==""?null:document.getElementById("nameInput").value.trim()
        var rbudingnubmer=document.getElementById("buildInput").value.trim()==""?null:document.getElementById("buildInput").value.trim()
        var rfloor=document.getElementById("floorInput").value.trim()==""?null:document.getElementById("floorInput").value.trim()
        //获取参数
        var postData={
            pageSize:Manage.pageSize,

            page:pageNum,
            rname:rname,
            rbudingnubmer:rbudingnubmer,
            rfloor:rfloor
        }
        console.log(postData)
        Global.openLoading()
        $.ajax({
            type:"POST",
            url: Global.host + "/room/selectRoomByClientInsert",
            data:postData,
            success: function (res) {
                console.log(res)
                Global.closeLoading()
                Manage.total=res.count
                var arr=res.list
                if(arr.length!==0){
                    var tbody=document.getElementById("tbody")
                    tbody.innerHTML="" //清空表格
                    arr.forEach(function(obj){
                        console.log(obj)
                        let tr=document.createElement("tr")
                        tr.innerHTML=`
                            <td>${obj.rname}</td>
							<td>${obj.rdepartment?obj.rdepartment:""}</td>
							<td>${obj.rbudingnubmer}号楼</td>
							<td>${obj.rfloor}层</td>
							<td>${obj.rhousenumber}号</td>
							<td class="list_table_btn">
								<button class="lookBtn">查看</button>
								<button class="updBtn">编辑</button>
								<button class="cancelBtn">删除</button>
								<button>打印</button>
							</td>
                        `
                        tr.setAttribute("data-rid",obj.rid)
                        tbody.appendChild(tr)
                    })

                    //更新当前页码
                    Manage.curPage=pageNum
                    //生成分页
                    Manage.buildPage(Manage.total,pageNum)
                }
            }
        })
    },
    //生成分页
    buildPage:function(totalCount,currentPage){
        var totalPage=Math.ceil(totalCount/Manage.pageSize)
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
                Manage.getList(page)
            }
        });
    },
    //验证新增会议室
    validAddRoom:function(){
        //会议室名称
        let value1=document.getElementById("addname").value.trim()
        if(value1==""){
            alert("请输入会议室名称")
            return false
        }
        //所属部门
        //let valueDept=document.getElementById("departmentSelect").value.trim()
        
        //楼号
        let value2=document.getElementById("addbuilding").value.trim()
        if(value2==""){
            alert("请输入会议室所在楼号")
            return false
        }
        let reg1=/^[0-9]*$/
        if(!reg1.test(value2)){
            alert("会议室楼号请填写数字")
            return false
        }
        //层数
        let value3=document.getElementById("addfloor").value.trim()
        if(value3==""){
            alert("请输入会议室所在层数")
            return false
        }
        if(!reg1.test(value3)){
            alert("会议室层数请填写数字")
            return false
        }
        //门牌号
        let value4=document.getElementById("addhousenum").value.trim()
        if(value4==""){
            alert("请输入会议室门牌号")
            return false
        }
        //预定时间间隔
        let value5=document.getElementById("addinterval").value.trim()
        if(value5==""){
            alert("请输入预定时间间隔")
            return false
        }
        if(!reg1.test(value5)){
            alert("预定时间间隔请填写数字")
            return false
        }
    },
    addRoom:function(){
        Manage.validAddRoom()

        //上传图片
        // var files=[]
        // $("#myAdd .add_pic").find("input").each(function(){
        //     if(this.files.length>0){
        //         files.push(this.files[0])
        //     }
        // })
        // console.log(files)



        // var form=document.createElement("form")
        // form.setAttribute("id","formUpload")
        var formData=new FormData()
        formData.append("rname",document.getElementById("addname").value.trim())
        formData.append("rdepartment",document.getElementById("departmentSelect").value)
        formData.append("rbudingnubmer",Number(document.getElementById("addbuilding").value))
        formData.append("rfloor",Number(document.getElementById("addfloor").value))
        formData.append("rhousenumber",Number(document.getElementById("addhousenum").value))
        formData.append("rinterval",Number(document.getElementById("addinterval").value))
        formData.append("rintroduce",document.getElementById("addintroduce").value.trim()==""?null:document.getElementById("addintroduce").value.trim())

        // formData.append("files",document.getElementById("addname").value.trim())
        $("#myAdd .add_pic").find("input").each(function(){
            if(this.files.length>0){
                formData.append("files",this.files[0])
            }
        })
        // console.log(files)

        // let postData={
        //     rname:document.getElementById("addname").value.trim(), //会议室名称
        //     rdepartment:document.getElementById("departmentSelect").value, //所属部门
        //     rbudingnubmer:Number(document.getElementById("addbuilding").value), //楼号
        //     rfloor:Number(document.getElementById("addfloor").value), //楼层
        //     rhousenumber:Number(document.getElementById("addhousenum").value), //门牌号
        //     rinterval:Number(document.getElementById("addinterval").value), //间隔时间
        //     //rlocation:document.getElementById("addaddress").value.trim()==""?null:document.getElementById("addaddress").value.trim(), //位置
        //     rintroduce:document.getElementById("addintroduce").value.trim()==""?null:document.getElementById("addintroduce").value.trim(), //简介
        //     // rpic:files, 
        //     files:files, //图片files数组

        //     // hcode:"", //医院编码
        //     // rstate:"", //会议室状态(1.未锁定 2.锁定 3.已删除)
        //     // rusestate:"", //会议室使用状态 (1.使用中  2.已预订  3.未预定)
        // }
        // console.log(postData)
        $.ajax({
            type:"POST",
            url: Global.host + "/room/insertroom",
            data:formData,
            cache: false,//上传文件无需缓存
            processData: false,//用于对data参数进行序列化处理 这里必须false
            contentType: false, //必须
            traditional:true,
            success: function (res) {
                console.log(res)
                if(res=="新增成功！"){
                    $('#myAdd').modal('hide')
                    alert("操作成功")
                    //刷新下列表
                    Manage.getList()
                }else if(res=="新增失败！"){
                    aler("请求失败")
                }
            }   
        })
    },
    updateRoom:function(rid){
        //获取会议室信息
        Manage.getRoomInfo(rid,function(){
            $('#myEdit').modal('show')
        })
    },
    getRoomInfo:function(rid,callback){
        let postData={
            rid:Number(rid)
        }
        console.log(postData)
        $.ajax({
            type:"POST",
            url: Global.host + "/room/selectOneRoomByRid",
            data:postData,
            success: function (res) {
                console.log(res)
                var info=res
                //update dom
                //会议室名称
                document.getElementById("addname-edit").value=info.rname
                //主管部门
                if(info.rdepartment&&info.rdepartment!=""){
                    var value=""
                    $("#departmentSelect-edit option").each(function(){
                        if(this.innerHTML==info.rdepartment){
                            value=$(this).attr("data-officeId")
                        }
                    })
                    $("#departmentSelect-edit").val(value)
                }
                //楼号
                document.getElementById("addbuilding-edit").value=info.rbudingnubmer
                //楼层
                document.getElementById("addfloor-edit").value=info.rfloor
                //门牌号
                if(info.rhousenumber&&info.rhousenumber!==""){
                    document.getElementById("addhousenum-edit").value=info.rhousenumber
                }
                //预定时间间隔
                if(info.rinterval&&info.rinterval!==""){
                    document.getElementById("addinterval-edit").value=info.rinterval
                }
                //位置
                if(info.rlocation&&info.rlocation!==""){
                    document.getElementById("addaddress-edit").value=info.rlocation
                }
                //会议室介绍
                if(info.rintroduce&&info.rintroduce!==""){
                    document.getElementById("addintroduce-edit").value=info.rintroduce
                }
                //会议室图片？

                callback()
            }
        })
    },
    deleteRoom:function(rid){
        $.ajax({
            type: "POST",
            url: Global.host + "/room/deleteroom",
            data:{
                rid:Number(rid)
            },
            success: function (res) {
                console.log(res)
                if(res=="删除成功！"){
                    alert("删除成功")
                    //刷新一下list
                    Manage.getList(Manage.curPage)
                }else if(res=="删除失败！"){
                    alert("请求失败")
                }
            }
        })
    },
    addPic:function(){
        var $p=$('<p class="add_pic_img"><img src="" onload="Global.resizeHeadpic(this)"><i class="iconfont icon-cuowu"></i><input type="file" accept="image/*" style="display:none;"></p>')
        var $input=$p.find("input")
        $input.change(function(){
            var fileList=this.files
            if(fileList.length>0){
                var file=fileList[0]
                var reader= new FileReader()   
                reader.onload=function(e){
                    $p.find("img").attr("src",e.target.result)
                    $("#myAdd .add_pic").prepend($p)
                }
                reader.readAsDataURL(file)
            }
        })
        $input[0].click()
    },
    eventsBind:function(){
        //事件代理
        //点击查看
        Global.eventProxy(document.getElementById("tbody"),".lookBtn","click",function(ele){
            var rid=$(ele).closest("tr").attr("data-rid")
            console.log(rid)
            window.location.href="service_room.html?rid="+rid
        })
        //点击编辑
        $("#tbody").delegate(".updBtn","click",function(){
            var rid=$(this).closest("tr").attr("data-rid")
            Manage.updateRoom(rid)
        })
        //点击删除
        $("#tbody").delegate(".cancelBtn","click",function(){
            var rid=$(this).closest("tr").attr("data-rid")
            Manage.deleteRoom(rid)
        })
        //点击新增的确定
        document.getElementById("addRoomBtn").onclick=function(){
            Manage.addRoom()
        }
        //点击确认
        document.getElementById("searchBtn").onclick=function(){
            Manage.getList()
        }
        //点击上传图片加号
        $("#myAdd .add_pic_img_btn").click(function(){
            Manage.addPic()
        })
        //点击上传图片close
        $("#myAdd .add_pic").delegate(".icon-cuowu","click",function(){
            event.stopPropagation()
            $(this).parent().remove()
        })
    }, 
    init:function(){
        Global.getMessageByLing()
        //生成部门select
        Manage.departmentSelect()
        //获取列表
        Manage.getList()
        //事件绑定
        Manage.eventsBind()
    }
}
$(function () {
    Manage.init()
});