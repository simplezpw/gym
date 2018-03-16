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
	<!-- jsp文件头和头部 -->
	<%@ include file="../admin/top.jsp"%> 
	</head> 
<body>
		
<div class="container-fluid" id="main-container">



<div id="page-content" class="clearfix">
						
  <div class="row-fluid">


	<div class="row-fluid">
	
			<!-- 检索  -->
			<form action="syssver/versionlist.action" method="post" name="userForm" id="userForm">
			<table>
				<tr>
				<td>
					<select name="apptype" style="width:120px;">
						<option <c:if test="${pd.apptype == '-1' }">selected</c:if> value="-1">请选择类型</option>
						<option <c:if test="${pd.apptype == '0' }">selected</c:if> value="0">平板端</option>
						<option <c:if test="${pd.apptype == '1' }">selected</c:if> value="1">安卓端</option>
						<option <c:if test="${pd.apptype == '2' }">selected</c:if> value="2">IOS端</option>
					</select>
				</td>
				<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();" title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
				</tr>
			</table>
			<!-- 检索  -->
		
		
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<!-- <th class="center">
						<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
						</th> -->
						<th>序号</th>
						<th>版本号</th>
						<th>发布时间</th>
						<th>app</th>
						<th>备注</th>
						<th>类型</th>
						<th class="center">操作</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty varList}">
						<c:if test="${QX.cha == 1 }">
						<c:forEach items="${varList}" var="user" varStatus="vs">
									
							<tr>
								<%-- <td class='center' style="width: 30px;">
									<input type="hidden" value="${user.id }" class="privid"/>
									<label><input type='checkbox' /><span class="lbl"></span></label>
								</td> --%>
								<td class='center' style="width: 30px;">${vs.index+1}</td>
								<td>
									${user.version }
								</td>
								<td>
									<fmt:formatDate value="${user.logtime}" pattern="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td>
									<a href="${user.location}">点击下载</a>
								</td>
								<td>
									${user.remark }
								</td>
								<td>
									<c:if test="${user.apptype eq 0}">平板端</c:if>
									<c:if test="${user.apptype eq 1}">安卓端</c:if>
									<c:if test="${user.apptype eq 2}">IOS端</c:if>
								</td>
								<td style="width: 60px;">
									<c:choose>
										<c:when test="${user.is_new eq 0}">
											<div><span>最新版</span></div>
										</c:when>
										<c:otherwise>
											<div class='hidden-phone visible-desktop btn-group'>
												<span class="btn btn-success btn-mini" onclick="updateversion('${user.id}', '${user.apptype}')" title="设置为新版">设置为新版本</span>
											</div>
										</c:otherwise>
									</c:choose>
									<div class='hidden-phone visible-desktop btn-group'>
										<span class="btn btn-success btn-mini" onclick="editUser('${user.id}')" title="编辑版本"><i class="icon-edit icon-2x icon-only"></i></span>
									</div>
								</td>
							</tr>
						
						</c:forEach>
						</c:if>
						
						<c:if test="${QX.cha == 0 }">
							<tr>
								<td colspan="10" class="center">您无权查看</td>
							</tr>
						</c:if>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="10" class="center">没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
					
				
				</tbody>
			</table>
			
		<div class="page-header position-relative">
		<table style="width:100%;">
			<tr>
<!-- 				<td style="vertical-align:top;"> -->
<!-- 					<a title="批量删除" class="btn btn-small btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" ><i class='icon-trash'></i></a> -->
<!-- 				</td> -->
				<td style="vertical-align:top;">
					<a title="新增广告" class="btn btn-small btn-success" onclick="add();" ><i class='icon-pencil'>新增</i></a>
				</td>
				<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
			</tr>
		</table>
		</div>
		</form>
	</div>
 
 
 
 
	<!-- PAGE CONTENT ENDS HERE -->
  </div><!--/row-->
	
</div><!--/#page-content-->
</div><!--/.fluid-container#main-container-->
		
		<!-- 返回顶部  -->
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>
		
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		<script type="text/javascript">
		
		$(top.changeui());
		
		//检索
		function search(){
			top.jzts();
			$("#userForm").submit();
		}
		
		//去发送电子邮件页面
		function sendEmail(EMAIL){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="发送电子邮件";
			 diag.URL = '<%=basePath%>/head/goSendEmail.action?EMAIL='+EMAIL;
			 diag.Width = 600;
			 diag.Height = 470;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
		
		//去发送短信页面
		function sendSms(phone){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="发送短信";
			 diag.URL = '<%=basePath%>/head/goSendSms.action?PHONE='+phone+'&msg=appuser';
			 diag.Width = 600;
			 diag.Height = 265;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = 'syssver/goAddVersion.action';
			 diag.Width = 400;
			 diag.Height = 300;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location.reload()",100);
					 }else{
						 nextPage(${page.currentPage});
					 }
				}
				diag.close();
			 };
			 diag.show();
		}
		
		function operOrderNum(obj, id){
			var input = $(obj).parents('tr').find('input[name="ordernum"]');
			var i = $(obj).find('.icon-only');
			console.log(i.hasClass('icon-check'));
			if(i.hasClass('icon-check')){
				var val = input.val();
				if(isNaN(val) || val < 0)
					return false;
				i.addClass('icon-edit');
				i.removeClass('icon-check');
				$.get("<%=basePath%>syssver/saveadvorder.action?id="+ id +'&ordernum='+val, function(ret){
					location.reload();
				}, 'text');
			}else if(i.hasClass('icon-edit')){
				i.addClass('icon-check');
				i.removeClass('icon-edit');
				input.removeAttr("readonly");
			}
		}
		
		//修改
		function editUser(user_id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = 'syssver/goEditVersion.action?id='+user_id;
			 diag.Width = 400;
			 diag.Height = 300;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					nextPage(${page.currentPage});
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//删除
		function delUser(userId){
			bootbox.confirm("确定要删除该条广告吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "syssver/deleteAdv.action?id="+userId+"&tm="+new Date().getTime();
					$.get(url,function(data){
// 						if(data=="success"){
							nextPage(${page.currentPage});
// 						}
					});
				}
			});
		}
		
		//删除
		function updateversion(userId, apptype){
			bootbox.confirm("确定要设置为最新版吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "syssver/updatenewversion.action?id="+userId+"&apptype="+apptype+"&tm="+new Date().getTime();
					$.get(url,function(data){
// 						if(data=="success"){
							nextPage(${page.currentPage});
// 						}
					});
				}
			});
		}
		
		function updadv(id, msg){
			var param;
			if(msg == 'totop'){
				param = '&toTop=totop';
			}else{
				param = '&pic_statu='+ msg;
			}
			top.jzts();
			var url = "syssver/topAdv.action?id="+id + param +"&tm="+new Date().getTime();
			$.get(url,function(data){
				nextPage(${page.currentPage});
			});
		}
		
		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					var emstr = '';
					var phones = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++)
					{
						  if(document.getElementsByName('ids')[i].checked){
						  	if(str=='') str += document.getElementsByName('ids')[i].value;
						  	else str += ',' + document.getElementsByName('ids')[i].value;
						  	
						  	if(emstr=='') emstr += document.getElementsByName('ids')[i].id;
						  	else emstr += ';' + document.getElementsByName('ids')[i].id;
						  	
						  	if(phones=='') phones += document.getElementsByName('ids')[i].alt;
						  	else phones += ';' + document.getElementsByName('ids')[i].alt;
						  }
					}
					if(str==''){
						bootbox.dialog("您没有选择任何内容!", 
							[
							  {
								"label" : "关闭",
								"class" : "btn-small btn-success",
								"callback": function() {
									//Example.show("great success");
									}
								}
							 ]
						);
						
						$("#zcheckbox").tips({
							side:3,
				            msg:'点这里全选',
				            bg:'#AE81FF',
				            time:8
				        });
						
						return;
					}else{
						if(msg == '确定要删除选中的数据吗?'){
							top.jzts();
							$.ajax({
								type: "POST",
								url: 'user/deleteAllU.action?tm='+new Date().getTime(),
						    	data: {USER_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											nextPage(${page.currentPage});
									 });
								}
							});
						}
					}
				}
			});
		}
		
		</script>
		
		<script type="text/javascript">
		
		$(function() {
			
			//日期框
			$('.date-picker').datepicker();
			
			//下拉框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//复选框
			$('table th input:checkbox').on('click' , function(){
				var that = this;
				$(this).closest('table').find('tr > td:first-child input:checkbox')
				.each(function(){
					this.checked = that.checked;
					$(this).closest('tr').toggleClass('selected');
				});
					
			});
			
		});
		
		//导出excel
		function toExcel(){
			var USERNAME = $("#nav-search-input").val();
			var lastLoginStart = $("#lastLoginStart").val();
			var lastLoginEnd = $("#lastLoginEnd").val();
			var ROLE_ID = $("#role_id").val();
			window.location.href='<%=basePath%>/user/excel.action?USERNAME='+USERNAME+'&lastLoginStart='+lastLoginStart+'&lastLoginEnd='+lastLoginEnd+'&ROLE_ID='+ROLE_ID;
		}
		
		//打开上传excel页面
		function fromExcel(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="EXCEL 导入到数据库";
			 diag.URL = 'user/goUploadExcel.action';
			 diag.Width = 300;
			 diag.Height = 150;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
		
		</script>
		
	</body>
</html>

