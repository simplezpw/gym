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

<!-- jsp文件头和头部 -->
<%@ include file="top.jsp"%>

</head>
<body>

	<div class="container-fluid" id="main-container">


		<div id="page-content" class="clearfix">

			<div class="page-header position-relative">
				<h1>
					数据统计 <small><i class="icon-double-angle-right"></i> </small>
				</h1>
			</div>
			<table class="table table-striped table-bordered table-hover">
				<tr>
					<td></td>
					<td>今日</td>
					<td>本周</td>
					<td>当月</td>
					<td>总计</td>
				</tr>
				<tr>
					<td>用户注册量</td>
					<td>${day }</td>
					<td>${week }</td>
					<td>${month }</td>
					<td>${total }</td>
				</tr>
			</table>
			
			<table>
				<tr>
					<td><select style="width:130px;" size="1" id="yearSelect" name="yearSelect"
						onchange="selYear('yearSelect',this.value)"></select></td>
					<td><select style="width:130px;" id="monthSelect">
							<option value="0">全部</option>
							<c:forEach begin="1" end="12" step="1" var="a">
								<option value="${a}">${a}</option>
							</c:forEach>
					</select></td>
				</tr>
			</table>
			<!--/page-header-->

			<div class="row-fluid">
				<div id="scansum" style="min-width: 700px; height: 400px"></div>
			</div>
			<!--/row-->
		</div>
		<!-- #main-content -->
	</div>
	<!--/.fluid-container#main-container-->
	<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse"> <i
		class="icon-double-angle-up icon-only"></i>
	</a>
	<!-- basic scripts -->
	<script src="static/1.9.1/jquery.min.js"></script>
	<script type="text/javascript"
		src="http://cdn.hcharts.cn/highcharts/highcharts.js"></script>
	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
	</script>

	<script src="static/js/bootstrap.min.js"></script>
	<!-- page specific plugin scripts -->

	<!--[if lt IE 9]>
		<script type="text/javascript" src="static/js/excanvas.min.js"></script>
		<![endif]-->
	<script type="text/javascript"
		src="static/js/jquery-ui-1.10.2.custom.min.js"></script>
	<script type="text/javascript"
		src="static/js/jquery.ui.touch-punch.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.slimscroll.min.js"></script>
	<script type="text/javascript"
		src="static/js/jquery.easy-pie-chart.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.sparkline.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.flot.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.flot.pie.min.js"></script>
	<script type="text/javascript"
		src="static/js/jquery.flot.resize.min.js"></script>
	<!-- ace scripts -->
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<!-- inline scripts related to this page -->
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<script type="text/javascript"
		src="static/js/bootstrap-datepicker.min.js"></script>
	<script type="text/javascript" src="static/js/bootbox.min.js"></script>

	<script type="text/javascript">
		$('.date-picker').datepicker();
		$(top.changeui());
		
		var scategories ;
		var sdata ;
		$(function() {
			$.ajax({
				type : 'POST',
				url : '<%=basePath%>/indexCountList.action',
				data : {
					'year' : new Date().getFullYear(),
					'month' : '0'
				},
				dataType : 'json',
				success : function(ret) {
					scategories = eval(ret.scansum);
					sdata = eval(ret.scantimes);
					scansumchart.xAxis[0].setCategories(sdata);
					scansumchart.series[0].setData(scategories);
				}
			});
			
			$("#yearSelect, #monthSelect").change(function(){
				$.ajax({
					type : 'POST',
					url : '<%=basePath%>/indexCountList.action',
					data : {
						'year' : $("#yearSelect").val(),
						'month' : $("#monthSelect").val()
					},
					dataType : 'json',
					success : function(ret) {
						scategories = eval(ret.scansum);
						sdata = eval(ret.scantimes);
						scansumchart.xAxis[0].setCategories(sdata);
						scansumchart.series[0].setData(scategories);
					}
				});
			});

			var scansumchart = new Highcharts.Chart({
				chart : {
					type : 'spline',
					renderTo : 'scansum'
				},
				title : {
					text : '用户统计'
				},
				xAxis : {
					categories : scategories
				},
				yAxis : {
					title : {
						text : '注册人数'
					},
					labels : {
						formatter : function() {
							return this.value
						}
					}
				},
				tooltip : {
					crosshairs : true,
					shared : true
				},
				plotOptions : {
					spline : {
						marker : {
							radius : 4,
							lineColor : '#666666',
							lineWidth : 1
						}
					}
				},
				series : [ {
					name : '注册时间',
					marker : {
						symbol : 'square'
					},
					data : sdata
				}]
			});
		})
		selYear('yearSelect',new Date().getFullYear());
		var Lastyear;
		function selYear(obj, Cyear) {
			var len = 16; //select长度
			var selObj = document.getElementById(obj);
			var selIndex = parseInt(len / 2) - 1;
			var newOpt;
			var LY = Cyear - Lastyear;
			for (i = 0; i < len; i++) {
				if (selObj.options.length != len) {
					newOpt = document.createElement("OPTION");
					newOpt.text = Cyear - selIndex + i;
					newOpt.value = Cyear - selIndex + i;
					selObj.options.add(newOpt, i);
					if (selIndex == i) {
						Lastyear = newOpt.value;
					}
				} else {
					selObj.options[i].text = parseInt(selObj.options[i].text) + LY;
					selObj.options[i].value = parseInt(selObj.options[i].value) + LY;
					if (selIndex == i) {
						Lastyear = selObj.options[i].value;
					}
				}
			}
			selObj.selectedIndex = selIndex;
		}
	</script>
</body>
</html>
