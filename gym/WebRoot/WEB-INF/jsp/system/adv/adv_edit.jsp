<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<base href="<%=basePath%>">
		<meta charset="utf-8" />
		<title></title>
		<meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="static/css/bootstrap.min.css" rel="stylesheet" />
		<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="static/css/font-awesome.min.css" />
		<script src="static/js/uploadPreview.min.js" type="text/javascript"></script>
		<!-- 下拉框 -->
		<link rel="stylesheet" href="static/css/chosen.css" />
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<!--提示框-->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		
<script type="text/javascript">
	$(top.changeui());
	$(document).ready(function(){
		if($("#user_id").val()!=""){
			$("#loginname").attr("readonly","readonly");
			$("#loginname").css("color","gray");
		}
	});
	
	//保存
	function save(){
		var upimg = document.getElementById("pic_desc").value;
		if(upimg == null || upimg == ''){
			$("#pic_desc").tips({
				side:3,
	            msg:'请填写广告描述',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#pic_desc").focus();
			return false;
		}
		
		var edittype = $("#eidttype").val();
		if(edittype == 'add'){
			var upimg = document.getElementById("up_img").value;
			if(upimg == null || upimg == ''){
				$("#imgdiv").tips({
					side:3,
		            msg:'请选择广告图',
		            bg:'#AE81FF',
		            time:2
		        });
				
				$("#imgdiv").focus();
				return false;
			}
		}
		
		if($("#pic_href").val() == ''){
			$("#pic_href").tips({
				side:3,
	            msg:'请选择跳转链接',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#pic_href").focus();
			return false;
		}
		$("#userForm").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="syssver/${msg }.action?edittype=${eidttype}" enctype="multipart/form-data" name="userForm" id="userForm" method="post">
		<input type="hidden" name="id" id="id" value="${adv.id }"/>
		<input type="hidden" value="${eidttype}" name="eidttype" id="eidttype"/>
		<div id="zhongxin">
		<table>
			<tr>
				<tr>
				<td>
					广告描述:
				</td>
				<td>
					<textarea type="text" name="pic_desc" id="pic_desc">${adv.pic_desc }</textarea>
				</td>
			</tr>
			<tr>
				<td>
					广告图
				</td>
				<td>
					<div id="imgdiv" class="edit_img" >
						<img id="imgShow" src="${adv.pic_path }" style="width: 400px;">
					</div>
					<a href="javascript:void(0)" class="file uploading_img">上传头像
	                    <input type="file" id="up_img" name="pic_path" />
	                </a>
				</td>
			</tr>
			<tr>
				<td>
					跳转链接:
				</td>
				<td>
					<input type="text" value="${adv.pic_href }" name="pic_href" id="href"/>
				</td>
			</tr>
			<tr>
				<td>
					类型:
				</td>
				<td>
					<select name="pic_type">
						<option <c:if test="${adv.pic_type == 0 }">selected</c:if> value="0">手机端</option>
						<option <c:if test="${adv.pic_type == 1 }">selected</c:if> value="1">pad端</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					种类:
				</td>
				<td>
					<select name="pic_kind">
						<option <c:if test="${adv.pic_kind == 0 }">selected</c:if> value="0">启动页广告</option>
						<option <c:if test="${adv.pic_kind == 1 }">selected</c:if> value="1">侧边栏广告</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					状态:
				</td>
				<td>
					<select name="pic_statu">
						<option <c:if test="${adv.pic_statu == 0 }">selected</c:if> value="0">启用</option>
						<option <c:if test="${adv.pic_statu == 1 }">selected</c:if> value="1">不启用</option>
					</select>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;">
					<a class="btn btn-mini btn-primary" onclick="save();">确定</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
		</table>
		</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
		
	</form>
	
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		
		<script type="text/javascript">
		
		$(function() {
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			//日期框
			$('.date-picker').datepicker();
			
		});
		
		window.onload = function () {
		new uploadPreview({ UpBtn: "up_img", DivShow: "imgdiv", ImgShow: "imgShow" });
		}
		
		function taketheoper(type){
			$("#operval").val(type);
			if(type == 1 || type == 0){
				$("#title").html("举报通知消息");
			}else{
				$("#title").html("举报成功消息");
			}
		}
		
		</script>
	
</body>
</html>