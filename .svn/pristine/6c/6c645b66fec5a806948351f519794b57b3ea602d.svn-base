$(function(){
	Global.getMessageByLing()

	//全局变量
	var bgtime="";
	var edtime="";
	var pageSize=10 //一页几个数据
	
	

	//时间选择器
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
		}
	}, function(start, end, label) {
		beginTimeStore = start;
		endTimeStore = end;
		console.log(this.startDate.format(this.locale.format));
		console.log(this.endDate.format(this.locale.format));
		bgtime=this.startDate.format(this.locale.format)
		edtime = this.endDate.format(this.locale.format)
		if(!this.startDate){
			this.element.val('');
		}else{
			this.element.val(this.startDate.format(this.locale.format) + this.locale.separator + this.endDate.format(this.locale.format));
		}
	});
	/*请求room*/
	var selRoom = document.getElementById("rname")
	$.ajax({
		type:"post",
		url:Global.host + "/room/selectRoomByHcode",
		success:function(res){
			//将值放入option中
			$.each(res,function(){
				selRoom.add(new Option(this.rname,this.rid))
			})
		}
	})
	/*请求预订人*/
	var selPeo = document.getElementById("pname")
	$.ajax({
		type:"post",
		url:Global.host + "/order/selectMemberNmeByHcode",
		success:function(res){
			//将值放入option中
			$.each(res,function(){
				selPeo.add(new Option(this,this))
			})
		}
	})

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
		$("#otakeorder").val(option.otakeorder)
	}
	getDataList();
	//初始化end

	//绑定事件
	document.getElementById("searchBtn").onclick=function(){
		getDataList();
	}
	//完成按钮
	$("#serviceList").delegate(".completeBtn","click",function(){
	    var oid = $(this).closest("tr").attr("data-oid")
	    $.ajax({
	    	type:"POST",
            url: Global.host + "/order/updateServiceComplete",
            data:{"oid":oid,"otakeorder":3},
            success: function (res) {
            	console.log(res)
            	if (res == 1) {
					alert("操作成功")
				}
            }
	    })
		//window.history.back(-1)
	})
	//点击查看按钮
	$("#serviceList").delegate(".seeBtn","click",function(){
		var ocid=$(this).closest("tr").attr("data-ocid")
		var otakeorder=$(this).closest("tr").attr("data-otakeorder") //1未接单
		console.log(otakeorder)
		var url=""
		if(Number(otakeorder)==1){
			url="service_detail.html?ocid="+ocid
		}else{
			url="../member/index_detail_look.html?ocid="+ocid
		}
        window.location.href=url
	})



	//获取参数
	function getParams(){
		var name=$("#pname").val()
		var otakeorder = $("#otakeorder").val()
		var rid=$("#rname").val()

		var option={
			name: name==""?null:name,
            otakeorder: otakeorder==""?null:Number(otakeorder),
            rid: rid==""?null:Number(rid),
		}
		return option
	}
	//获取数据
	function getDataList(page=1){
		//获取参数
		var option=getParams()

		var postData={
			beginOtime: bgtime==""?null:bgtime,
            endOtime: edtime==""?null:edtime,
			pageSize:pageSize,
			page:page,

            name: option.name,
            otakeorder: option.otakeorder,
            rid: option.rid,
		}
		
		console.log(postData)
		Global.openLoading()
        $.ajax({
            type:"POST",
            url: Global.host + "/order/selectSimpleListByFuWu",
            data:postData,
            success: function (res) {
				console.log(res)
				Global.closeLoading()
    	 		var totalCount=res.count
            	var arr=res.list
            	var serviceList=document.getElementById("serviceList")
            	var stateStr = '';
    	 		
            	//添加数据之前清空原有的
            	serviceList.innerHTML=""
            	arr.forEach(function(obj){
					console.log(obj)
            		if (obj.otakeorder == 1) {
            			stateStr = '未接单';
    				}else if(obj.otakeorder == 2){
            			stateStr = '服务中';
    				}else{
    					stateStr = '已完成';
    				}
					var otime = Global.dateToFormat(new Date(obj.otime))
					var ocdockingtime=Global.dateToFormat(new Date(obj.ocdockingtime))
            		var tr=document.createElement("tr")
            		tr.innerHTML=`
            			<td>${obj.rname}</td>
						<td>${otime.substr(0,10)} <br>${otime.substr(otime.length-5,5)}</td>
						<td>${ocdockingtime.substr(0,10)} <br>${ocdockingtime.substr(ocdockingtime.length-5,5)}</td>
						<td class="list_table_s">${stateStr}</td>
						<td class="list_table_s">${obj.name}</td>
						<td>${obj.tel}</td>
						<td>
							<ul class="list_table_star">
							</ul>
						</td>
						<td class="list_table_btn">
							<button class="seeBtn">查看</button>
							<button class="completeBtn">完成</button>
							<button>打印</button>
						</td>
					`
					//添加星评
					if(obj.cmgrade){
						let stars=obj.cmgrade
						let ultemp=$(tr).find(".list_table_star")[0]
						for(let i=0;i<5;i++){
							let litemp=document.createElement("li")
							//白星
							litemp.innerHTML='<li><span class="iconfont icon-xingxing"></span></li>'
							if(i+1<=stars){
								//黄星
								litemp.getElementsByTagName("span")[0].classList.add("icon_pre")
							}
							ultemp.appendChild(litemp)
						}
					}else{
						$(tr).find(".list_table_star").text("暂无评价")
					}
            		tr.setAttribute("data-oid",obj.oid)	
					tr.setAttribute("data-ocid",obj.ocid)	
					tr.setAttribute("data-otakeorder",obj.otakeorder)
        			serviceList.appendChild(tr)
            	})
            	
            	//重新生成分页
            	buildPage(totalCount,page)
            }
        })
	}
	//生成分页
	function buildPage(totalCount,currentPage){
        var totalPage=Math.ceil(totalCount/pageSize)
        // totalPage=2 //测试用
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
                getDataList(page)
            }
        });
    }
})


