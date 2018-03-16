<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<meta charset="utf-8" />
	<title>${pd.SYSNAME}</title>
	<meta name="description" content="overview & stats" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<!-- basic styles -->
	<link href="static/css/bootstrap.min.css" rel="stylesheet" />
	<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
	<link rel="stylesheet" href="static/css/font-awesome.min.css" />
	<!-- page specific plugin styles -->
	<!-- 下拉框-->
	<link rel="stylesheet" href="static/css/chosen.css" />
	<!-- ace styles -->
	<link rel="stylesheet" href="static/css/ace.min.css" />
	<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
	<link rel="stylesheet" href="static/css/ace-skins.min.css" />
	<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
	<link rel="stylesheet" href="static/css/datepicker.css" /><!-- 日期框 -->
	<!--引入弹窗组件start-->
	<script type="text/javascript" src="plugins/attention/zDialog/zDrag.js"></script>
	<script type="text/javascript" src="plugins/attention/zDialog/zDialog.js"></script>
	<!--引入弹窗组件end-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
</head>
<body>

	<div class="container-fluid" id="main-container">
		

			<div id="page-content" class="clearfix">
				<table>
				<tr>
					<td>
						<span class="input-icon">
							<td><input class="span10 date-picker" name="datatime" id="stime"  value="${stime}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期" title="查询时间"/></td>
						</span>
					</td>
					<td>
						<select name="datatype" id="datatype">
							<option <c:if test="${datatype eq '1'}">selected</c:if> value="1">心率</option>
							<option <c:if test="${datatype eq '2'}">selected</c:if> value="2">睡眠</option>
							<option <c:if test="${datatype eq '3'}">selected</c:if> value="3">步数</option>
						</select>
					</td>
					<td style="vertical-align:top;"><button class="btn btn-mini btn-light" id="search"  title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
				</tr>
			</table>
				<!--/page-header-->
				<table class="table table-striped table-bordered table-hover" id="dtab">
					
				</table>
				
				<div id="weekdata" style="min-width:700px;height:400px"></div>
				<br/>
				<hr/>
				<br/>
				<div id="monthdata" style="min-width:700px;height:400px"></div>
				<br/>
				<hr/>
				<br/>
				<div id="yeardata" style="min-width:700px;height:400px"></div>

		</div>
		<!-- #main-content -->
	</div>
	<!--/.fluid-container#main-container-->
	<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse"> <i
		class="icon-double-angle-up icon-only"></i>
	</a>
	<!-- basic scripts -->
	<!-- <script src="static/1.9.1/jquery.min.js"></script>
	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
	</script> -->

	<script src="static/js/bootstrap.min.js"></script>
	<!-- page specific plugin scripts -->

	<!--[if lt IE 9]>
		<script type="text/javascript" src="static/js/excanvas.min.js"></script>
		<![endif]-->
	<script type="text/javascript" src="static/js/jquery-ui-1.10.2.custom.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.ui.touch-punch.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.slimscroll.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.easy-pie-chart.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.sparkline.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.flot.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.flot.pie.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.flot.resize.min.js"></script>
	<script type="text/javascript" src="http://cdn.hcharts.cn/highcharts/highcharts.js"></script> 
	<!-- ace scripts -->
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script>
	<script type="text/javascript" src="static/js/bootbox.min.js"></script>
	
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<!-- inline scripts related to this page -->


	<script type="text/javascript">
		$('.date-picker').datepicker();
		$(top.changeui());
		var wdata ;
		var mdata ;
		var ydata ;
		var datatype = 1;
		var title = '心率(次/分)';
		$(function() {
			$("#search").click(function(){
				$.ajax({
					type : 'POST',
					url : '<%=basePath%>mbracelet/datas.action',
					data : {'datatime':$("#stime").val(), 'datatype' : $("#datatype").val()},
					dataType: 'json',
					success : function(ret){
						if(ret == null || ret == '') return false;
						buildtabler(ret);
						wdata  = eval(ret.wy);
						mdata  = eval(ret.my);
						ydata  = eval(ret.yy);
						weekdata.xAxis[0].setCategories(['周一', '周二', '周三', '周四', '周五', '周六', '周日']);
						weekdata.series[0].setData(wdata);
						monthdata.xAxis[0].setCategories(['第一周', '第二周', '第三周', '第四周']);
						monthdata.series[0].setData(mdata);
						yeardata.xAxis[0].setCategories(['一月', '二月', '三月', '四月', 
				                      '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']);
						yeardata.series[0].setData(ydata);
						weekdata.yAxis[0].setTitle({text:title});
						monthdata.yAxis[0].setTitle({text:title});
						yeardata.yAxis[0].setTitle({text:title});
					}
				});
			
			});
			
			$.ajax({
				type : 'POST',
				url : '<%=basePath%>mbracelet/datas.action',
				data : null,
				sync : false,
				dataType: 'json',
				success : function(ret){
					if(ret == null || ret == '') return false;
					buildtabler(ret);
					console.log(title);
					wdata  = eval(ret.wy);
					mdata  = eval(ret.my);
					ydata  = eval(ret.yy);
					weekdata.xAxis[0].setCategories(['周一', '周二', '周三', '周四', '周五', '周六', '周日']);
					weekdata.series[0].setData(wdata);
					monthdata.xAxis[0].setCategories(['第一周', '第二周', '第三周', '第四周']);
					monthdata.series[0].setData(mdata);
					yeardata.xAxis[0].setCategories(['一月', '二月', '三月', '四月', 
			                      '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']);
					yeardata.series[0].setData(ydata);
					weekdata.yAxis[0].setTitle({text:title});
					monthdata.yAxis[0].setTitle({text:title});
					yeardata.yAxis[0].setTitle({text:title});
				}
			});
				var weekdata =	new Highcharts.Chart({
				        chart: {
				            type: 'spline',
				            renderTo: 'weekdata' 
				        },
				        title: {
				            text: '周测量数据'
				        },
				        xAxis: {
				            categories: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
				        },
				        yAxis: {
				            title: {
				                text: title
				            },
				            labels: {
				                formatter: function() {
				                    return this.value; 
				                }
				            }
				        },
				        tooltip: {
				            crosshairs: true,
				            shared: true
				        },
				        plotOptions: {
				            spline: {
				                marker: {
				                    radius: 4,
				                    lineColor: '#666666',
				                    lineWidth: 1
				                }
				            }
				        },
				        series: [{
				            name: '周-天',
				            marker: {
				                symbol: 'square'
				            },
				            data: wdata
				        }]
				    });
					
				var monthdata = new Highcharts.Chart({
				        chart: {
				            type: 'spline',
				            	renderTo: 'monthdata' 
				        },
				        title: {
				            text: '月测量数据'
				        },
				        xAxis: {
				            categories: ['第一周', '第二周', '第三周', '第四周']
				        },
				        yAxis: {
				            title: {
				                text: title
				            },
				            labels: {
				                formatter: function() {
				                    return this.value 
				                }
				            }
				        },
				        tooltip: {
				            crosshairs: true,
				            shared: true
				        },
				        plotOptions: {
				            spline: {
				                marker: {
				                    radius: 4,
				                    lineColor: '#666666',
				                    lineWidth: 1
				                }
				            }
				        },
				        series: [{
				            name: '月-周',
				            marker: {
				                symbol: 'square'
				            },
				            data: mdata
				        }]
				    });
				
				var yeardata = new Highcharts.Chart({
			        chart: {
			            type: 'spline',
			            	renderTo: 'yeardata' 
			        },
			        title: {
			            text: '年测量数据'
			        },
			        xAxis: {
			            categories: ['一月', '二月', '三月', '四月', 
				                      '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']
			        },
			        yAxis: {
			            title: {
			                text: title
			            },
			            labels: {
			                formatter: function() {
			                    return this.value 
			                }
			            }
			        },
			        tooltip: {
			            crosshairs: true,
			            shared: true
			        },
			        plotOptions: {
			            spline: {
			                marker: {
			                    radius: 4,
			                    lineColor: '#666666',
			                    lineWidth: 1
			                }
			            }
			        },
			        series: [{
			            name: '年-月',
			            marker: {
			                symbol: 'square'
			            },
			            data: ydata
			        }]
			    });
				
			
		});
		
		function buildtabler(ret){
			$("#dtab").empty();
			var datatype = ret.datatype;
			var bid = ret.braceletid;
			var datatime = ret.datatime;
			var html = '<tr><td>手环序列号</td>';
			if(datatype == 1){
				title = '心率(次/分)';
				html += '<td>最高心率</td><td>最低心率</td><td>平均心率</td><td>记录时间</td></tr>';
				var hr = ret.maxhr == null ? '-' : ret.maxhr;
				var lr = ret.minhr == null ? '-' : ret.minhr;
				var ar = ret.avghr == null ? '-' : ret.avghr;
				html += '<tr><td>'+bid+'</td><td>'+hr+'</td><td>'+lr+'</td><td>'+ar+'</td><td>'+datatime+'</td></tr>';
			}else if(datatype == 2){
				title = '睡眠(小时)';
				html += '<td>深睡时长</td><td>浅睡时长</td><td>总睡时长</td>' + 
					'<td>起床时间</td><td>入睡时间</td><td>记录时间</td></tr>';
					console.log(ret.avgds);
					console.log(ret.avgds / 3600);
					console.log(parseInt((ret.avgds) / 3600));
				var hr = ret.avgds == null ? '-' : (parseInt((ret.avgds) / 3600) + '时' + parseInt(ret.avgds % 3600 / 60) + '分') ;
				var lr = ret.avgls == null ? '-' : (parseInt(ret.avgls / 3600) + '时' + parseInt(ret.avgls % 3600 / 60) + '分');
				var ar = ret.avgts == null ? '-' : (parseInt(ret.avgts / 3600) + '时' + parseInt(ret.avgts % 3600 / 60) + '分');
				var gt = ret.getuptime == null ? '-' : ret.getuptime;
				var is = ret.insleeptime == null ? '-' : ret.insleeptime;
				html += '<tr><td>'+bid+'</td><td>'+hr+'</td><td>'+lr+'</td><td>'+ar+'</td><td>'+gt+'</td><td>'+is+'</td><td>'+datatime+'</td></tr>';
			}else if(datatype == 3){
				title = '总步数';
				html += '<td>总步数</td><td>卡路里(焦)</td><td>里程(米)</td><td>记录时间</td></tr>';
				var hr = ret.sumstep == null ? '-' : ret.sumstep;
				var distance = ret.distance == null ? '-' : ret.distance;
				var calorie = ret.calorie == null ? '-' : ret.calorie;
				html += '<tr><td>'+bid+'</td><td>'+hr+'</td><td>'+calorie+'</td><td>'+distance+'</td><td>'+datatime+'</td></tr>';
			}
			$("#dtab").append(html);
		}
	</script>
</body>
</html>
