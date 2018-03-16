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
		<script type="text/javascript" src="static/js/plupload.full.min.js"></script>
		<script type="text/javascript" src="static/js/qiniu.js"></script>
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
	            msg:'请填写版本号',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#pic_desc").focus();
			return false;
		}
		
		var edittype = $("#eidttype").val();
		if(edittype == 'add'){
			var upimg = document.getElementById("location").value;
			if(upimg == null || upimg == ''){
				$("#location").tips({
					side:3,
		            msg:'请选择app包',
		            bg:'#AE81FF',
		            time:2
		        });
				
				$("#location").focus();
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
					版本号:
				</td>
				<td>
					<input type="text" name="version" value="${adv.version}" id="pic_desc"/>
				</td>
			</tr>
			<tr id="upapp">
				<td>
					app:
				</td>
				<td>
					<input type="file" id="up_img" value="${adv.location}"/>
					<span id="process" style="display:none;"></span>
					<input name="location" type="hidden" id="location"/>
					<%-- <div id="imgdiv" class="edit_img" >
						<img id="imgShow" src="${adv.pic_path }" style="width: 400px;">
					</div>
					<a href="javascript:void(0)" class="file uploading_img">上传文件
	                    <input type="file" id="up_img" name="pic_path" />
	                </a> --%>
				</td>
			</tr>
			<tr>
				<td>
					备注:
				</td>
				<td>
					<textarea type="text" rows="5" cols="4" name="remark">${adv.remark }</textarea>
				</td>
			</tr>
			<tr>
				<td>
					类型:
				</td>
				<td>
					<select name="apptype">
						<option <c:if test="${adv.apptype == 0 }">selected</c:if> value="0">平板端</option>
						<option <c:if test="${adv.pic_type == 1 }">selected</c:if> value="1">安卓端</option>
						<option <c:if test="${adv.apptype == 2 }">selected</c:if> value="2">IOS端</option>
					</select>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="2">
					<a class="btn btn-mini btn-primary" id="submit" onclick="save();">确定</a>
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
// 			$('.date-picker').datepicker();
			
			var uploader = Qiniu.uploader({
			    runtimes: 'html5,flash,html4',    //上传模式,依次退化
			    browse_button: 'up_img',       //上传选择的点选按钮，**必需**
			    uptoken_url: '<%=basePath%>/syssver/getToken.action',
			        //Ajax请求upToken的Url，**强烈建议设置**（服务端提供）
			    // uptoken : '<Your upload token>',
			        //若未指定uptoken_url,则必须指定 uptoken ,uptoken由其他程序生成
			    unique_names: false,
			        // 默认 false，key为文件名。若开启该选项，SDK会为每个文件自动生成key（文件名）
			    save_key: false,
			        // 默认 false。若在服务端生成uptoken的上传策略中指定了 `sava_key`，则开启，SDK在前端将不对key进行任何处理
			    domain: '7xpizi.com2.z0.glb.qiniucdn.com',
			        //bucket 域名，下载资源时用到，**必需**
			    container: 'upapp',           //上传区域DOM ID，默认是browser_button的父元素，
			    max_file_size: '1000M',           //最大文件体积限制
			    //flash_swf_url: 'js/plupload/Moxie.swf',  //引入flash,相对路径
			    max_retries: 3,                   //上传失败最大重试次数
			    dragdrop: false,                   //开启可拖曳上传
			    drop_element: 'container',        //拖曳上传区域元素的ID，拖曳文件或文件夹后可触发上传
			    chunk_size: '4mb',                //分块上传时，每片的体积
			    auto_start: true,                 //选择文件后自动上传，若关闭需要自己绑定事件触发上传
			    init: {
			        'FilesAdded': function(up, files) {
			            plupload.each(files, function(file) {
			                // 文件添加进队列后,处理相关的事情
			            	/* var fileType=file.type.substr(file.type.lastIndexOf("/")+1).toLowerCase();//获得文件后缀名
						    if(fileType != 'apk' && fileType != 'png' && fileType != 'jpg' && fileType != 'jpeg'){
						    	$("#tp").tips({
									side:3,
						            msg:'请上传图片格式的文件',
						            bg:'#AE81FF',
						            time:3
						        });
						    	$("#tp").val('');
						    	document.getElementById("tp").files[0] = '请选择图片';
						    } */
			            });
			        },
			        'BeforeUpload': function(up, file) {
			               // 每个文件上传前,处理相关的事情
			        },
			        'UploadProgress': function(up, file) {
			               // 每个文件上传时,处理相关的事情
			               $("#process").css('display', 'block');
			               $("#process").css('color', 'red');
			               $("#process").html("文件已上传" + file.percent + "%");
			               $("#submit").addClass('disabled');
			        },
			        'FileUploaded': function(up, file, info) {
			        	// 每个文件上传成功后,处理相关的事情
			        	$("#submit").removeClass('disabled');
			        	var domain = up.getOption('domain');
		                var res = $.parseJSON(info);
		                var sourceLink = domain + "/" + res.key; 
		                if(sourceLink.indexOf("http") < 0){
		                	sourceLink = "http://" + sourceLink;
		                }
		                console.log(sourceLink);
		                console.log(file);
		                $("#location").val(sourceLink);
			        },
			        'Error': function(up, err, errTip) {
			               //上传出错时,处理相关的事情
			               alert("文件上传失败!");
			        },
			        'UploadComplete': function() {
			               //队列文件处理完毕后,处理相关的事情
			        	   /* $("#submit").removeClass('disabled');
			        	   var domain = up.getOption('domain');
			               var res = $.parseJSON(info);
			               var sourceLink = domain + "/" + res.key; //获取上传成功后的文件的Url
// 			               $("#cover").val(sourceLink);
			               $("#uptime").val(new Date()); */
			        },
			        'Key': function(up, file) {
			            // 若想在前端对每个文件的key进行个性化处理，可以配置该函数
			            // 该配置必须要在 unique_names: false , save_key: false 时才生效
			        	var key = "";
			            // do something with key here
			            key = "app_" + new Date().getTime();
			            return key;
			        }
			    }
			});
			
		});
		
		
		
		/* window.onload = function () {
		new uploadPreview({ UpBtn: "up_img", DivShow: "imgdiv", ImgShow: "imgShow" });
		} */
		
		</script>
	
</body>
</html>