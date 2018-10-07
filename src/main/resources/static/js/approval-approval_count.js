var Count={
    begintime:"", //服务统计时间段
    endtime:"", //服务统计时间段

    chart:"",

    initDaterangepicker:function(){
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
            Count.begintime=this.startDate.format(this.locale.format)
            Count.endtime=this.endDate.format(this.locale.format)

            if(!this.startDate){
                this.element.val('');
            }else{
                this.element.val(this.startDate.format(this.locale.format) + this.locale.separator + this.endDate.format(this.locale.format));
            }
        });
    },
    //初始化一个图表
    initChart:function(){
        Count.chart = echarts.init(document.getElementById('mainPie'))
    },
    //设置图表
    setChart:function(mData){ //参数data：数组，满意 一般 不满意
        // 指定图表的配置项和数据
        let option = {
            title : {
                show:false,
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: ['满意','一般','不满意']
            },
            series : [
                {
                    name: '用户评价',
                    type: 'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data:[
                        {value:0, name:'满意',itemStyle:{color:"#c53f3c"}},
                        {value:0, name:'一般',itemStyle:{color:"#97cab2"}},
                        {value:0, name:'不满意',itemStyle:{color:"#3a4e5d"}}
                    ],
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };

        mData.forEach(function(val,index){
            option.series[0].data[index].value=Number(val)
        })

        // 使用刚指定的配置项和数据显示图表。
        Count.chart.setOption(option);
    },
    //获取某个会议室的服务统计
    getServiceCount:function({
        rid,
        beginTime=null,
        endTime=null,
    }={}){
        var postData={
            rid:rid, 
            beginTime:beginTime,
            endTime:endTime
        }
        console.log(postData)
        $.ajax({
            type:"POST",
            url: Global.host + "/comment/selectServiceStatistic",
            data:postData,
            success: function (res) {
                console.log(res)
                //update右侧dom
                //平均星评
                if(res.avg){
                    let avg=res.avg
                    let ul=document.getElementById("avgStars")
                    for(let i=0;i<5;i++){
                        let li=document.createElement("li")
                        //白星
                        li.innerHTML=`
                            <li><span class="iconfont icon-xingxing"></span></li>
                        `
                        if(i+1<=avg){
                            //黄星
                            li.getElementsByTagName("span")[0].classList.add("icon_pre")
                        }
                        ul.appendChild(li)
                    }
                    document.getElementById("avgStarsText").innerHTML=avg
                    document.getElementById("avgStarsTextWrap").style.display="inline"
                    document.getElementById("avgStarsTextWrapDiv").style.display="block"
                }
                //显示图表
                Count.setChart([res.sumManyi,res.sumYiban,res.sumBuman])
                //评论列表
                if(res.commentList.length>0){
                    let commentList=res.commentList
                    let ul=document.getElementById("commentList")
                    ul.style.display="block"
                    commentList.forEach(function(obj){
                        let li=document.createElement("li")
                        let cmtime=Global.dateToFormat(new Date(obj.cmtime)).substr(0,10)
                        li.innerHTML=`
                            <div class="review_u_top">
                                <ul class="list_table_star" id="commentList">
                                </ul>&nbsp;&nbsp;
                                <span>${obj.cmgrade} 分</span>
                                <span class="fr">${cmtime}</span>
                            </div>
                            <p class="review_u_word">${obj.cmcontent}</p>
                            <div class="review_u_top">${obj.uname}</div>
                        `
                        li.className="border_b"
                        //添加星评
                        if(obj.cmgrade){
                            let stars=obj.cmgrade
                            let ultemp=li.querySelector(".list_table_star")
                            for(let i=0;i<5;i++){
                                let litemp=document.createElement("li")
                                //白星
                                litemp.innerHTML=`
                                    <li><span class="iconfont icon-xingxing"></span></li>
                                `
                                if(i+1<=stars){
                                    //黄星
                                    litemp.getElementsByTagName("span")[0].classList.add("icon_pre")
                                }
                                ultemp.appendChild(litemp)
                            }
                        }
                        ul.appendChild(li)
                    })
                }
            }
        })
    },
    //获取会议室
    getRid:function(){
        //验证
        //会议室名称
        let value1=$("#rname").val().trim()
        if(value1==""){
            alert("请输入会议室名称")
            return
        }
        //会议室楼号
        let value2=$("#rbuilding").val().trim()
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
        let value3=$("#rfloor").val().trim()
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
        //     pageSize:10,
        //     page:1,

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
                console.log(res) //？？？？？？？？空？？
                if(res&&res.list&&res.list.length>0){
                    let room=res.list[0]
                    //更新右侧会议室名称
                    document.getElementById("rTitle").innerHTML=room.rname
                    //获取右侧的服务统计信息
                    Count.getServiceCount({
                        rid:room.rid,
                        beginTime:Count.begintime==""?null:Count.begintime,
                        endTime:Count.endtime==""?null:Count.endtime,
                    })
                }else{
                    alert("没有您查询的会议室")
                }
            }
        })
    },
    eventsBind:function(){
        //点击搜索确认按钮
        document.getElementById("searchBtn").onclick=function(){
            Count.getRid()
        }
    },
    init:function(){
        Global.getMessageByLing()
        Count.initDaterangepicker()
        //初始化图表
        Count.initChart()

        Count.eventsBind()
    }
}
$(function(){
    Count.init()
})