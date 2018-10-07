$(function() {
	Global.getMessageByLing()
	eventsBind()
	/*将服务状态set进去*/
	$.ajax({
		type:"post",
		url:Global.host + "/order/selectStateListByService",
		success:function(res){
			var one = res.one;//1
			var two = res.two;//2
			var three = res.three;//3
			document.getElementById("oneObjCount").innerHTML=one
			document.getElementById("twoObjCount").innerHTML=two
			document.getElementById("threeObjCount").innerHTML=three
		}
	})
    /*获取普通用户的待办事项*/
	$.ajax({
		type:"post",
		url: Global.host + "/order/selectMessageByService",
		success: function(res){
			//开始set
			var oneObject = document.getElementById("messageList");
			res.forEach(function(obj){
				console.log(obj)
        		var li = document.createElement("li")
				li.innerHTML = obj.mtcontent
				li.setAttribute("data-mtid",obj.mtid)
				li.setAttribute("data-ocid",obj.ocid)
    			oneObject.appendChild(li)
			})
		}
	})
	/*将6个订单列表set进去*/
	$.ajax({
		type:"post",
		url:Global.host + "/order/selectSixListForService",
		success:function(res){
			console.log(res) //服务部门点击已完成按钮（otakeorder状态为3）或者接单按钮（otakeorder状态为2） 数据库默认为1（未接单）

			//1待接单 2服务中 3已完成

			console.log(res)
			var list1=res.list1 //待接单
			var list2=res.list2 //服务中
			var list3=res.list3 //已完成

			var arry=[]

			arry[0]=list1[0]?list1[0]:"无数据" //第一行
			arry[1]=list2[0]?list2[0]:"无数据"
			arry[2]=list3[0]?list3[0]:"无数据"

			arry[3]=list1[1]?list1[1]:null
			arry[4]=list2[1]?list2[1]:null
			arry[5]=list3[1]?list3[1]:null

			if(arry.length!==0){
				var listOfData = document.getElementById("listOfData")
				//添加数据之前清空原有的
				listOfData.innerHTML=""
				arry.forEach(function(obj) {
					var $div
					if(obj==null){
						div.innerHTML=''
						div.style.background="none"
						div.style.boxShadow="none"
					}else if(obj=="无数据"){
						div.innerHTML='<img style="display:block;margin:0 auto" src="../images/暂无相关记录.png"><p style="text-align:center;">暂无相关记录</p>'
					}else{
						var begintime = thansForTime(obj.ocbegintime);
						var endtime = thansForTime(obj.ocendtime);
	                    var date=begintime.substr(0,10)
						var classStr=""
						var textStr=""
						if(Number(obj.otakeorder) == 1){//未接单
							textStr = "待接单";
							classStr="div1 approval_btn"
						}else if(Number(obj.otakeorder) == 2){//服务中
							textStr = "服务中";
							classStr="div1 adopt_btn"
						}else{
							textStr = "已完成";
							classStr="div1 refuse_btn"
						}
						$div=$(`
							<div class="state-contentc">
								<h1>${obj.rname}</h1>
								<p>申请时间：${date} ${begintime.substr(begintime.length-5,5)}-${endtime.substr(endtime.length-5,5)}</p>
								<p>申请人:${obj.name}</p>
								<p>联系电话:${obj.tel}</p>
								<button class="lookBtn" onclick="lookContent()">查看</button>
								<div class="${classStr}">${textStr}</div>
								<input type="hidden" value="${obj.ocid}" class="hideocid" />
							</div>	
						`)
					}
					$(listOfData).append($div);
				})
			}
		}
	})
})
function eventsBind(){
	//点击待办事项
	$("#messageList").delegate("li","click",function(){
		fromUpcoming(this)
	})
	//点击数量去列表
	$("ul.content_menu li a").click(function(event){
		var otakeorder=$(this).attr("data-otakeorder")
		window.location.href="../service/service_list.html?otakeorder="+otakeorder
		return false
	})
}
function fromUpcoming(el){
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
}
function thansForTime(time) {
	var d = new Date(time);
	var times=d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate() + ' ' + d.getHours() + ':' + d.getMinutes() + ':' + d.getSeconds(); 
	return times;
}
/*事件监听*/
function lookContent(){
	var ocid = $(".hideocid").val();
    window.location.href="../member/index_detail_look.html?ocid="+ocid
}