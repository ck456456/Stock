<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ page language="java" contentType="text/html; charset=UTF-8"
	    pageEncoding="UTF-8"%>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" >    
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<link rel="Stylesheet" type="text/css" href="css/TKC_styles.css"/>
	<link rel="Stylesheet" type="text/css" href="js/flexigrid/flexigrid.pack.css"/>
	<link rel="Stylesheet" type="text/css" href="css/index.css"/>
	<link rel="stylesheet" type="text/css" href="js/JSCal2-1.9/css/jscal2.css" />
	<link rel="stylesheet" type="text/css" href="js/JSCal2-1.9/css/border-radius.css" />
	<link rel="stylesheet" type="text/css" href="js/JSCal2-1.9/css/steel/steel.css" />
	<link rel="stylesheet" type="text/css" href="js/jquery.alerts-1.1/jquery.alerts.css" />
	<style>  
	</style>  
	<title>股票資料統計</title>
</head>
<body>
	<ul class="tabs">
		<li><a href="#tab1" id='LL01'>每日收盤行情上傳</a></li>
		<li><a href="#tab2" id='LL02'>統計分析</a></li>
	</ul>
	<div class="tab_container" style="height : 95%;">
		<div id="tab1" class="tab_content">
			<table style="width:100%; font-size: 12px; border: gray 1px solid;">
				<tr>
					<td rowspan=3>
						<input type="button" id="stock_f1" value="選 檔 匯 入"><br>
					</td>
					<td>副檔名為 "XLS"表上市 , 檔名範例 : A05_AWSTIU-<b>20130104</b>-C.xls , 上市資料來自臺灣證券交易所 Email</td> 
				</tr>
				<tr>
					<td>副檔名為 "CVS"表上櫃 , 檔名範例 : RSTA3104_<b>1020104</b>.CSV		 , 上櫃資料請到<a href="http://www.otc.org.tw/ch/stock/aftertrading/DAILY_CLOSE_quotes/stk_quote.php#" target="_blank">櫃檯買賣中心下載</a><br></td>
				</tr>
				<tr>
					<td>上市、上櫃股名代碼表 , 檔名範例 : StockTable.xls	 , 資料請到<a href="http://www.emega.com.tw/js/StockTable.htm"  target="_blank">兆豐證券下載</a> , 下載後請用 Excel 重存一次再匯入<br></td>
				</tr>
			</table> 
			<table id="stock_f1_grid"></table>
		</div>
		<div id="tab2" class="tab_content" style="height : 90%;border: 1px solid #fff;">
			<table id = "STKtype" style="width:100%; font-size: 12px; border: gray 1px solid;">
				<tr>
					<td><fieldset id = "STKtype00"><legend>上市類別</legend></fieldset><td>
				</tr>
				<tr>
					<td><fieldset id = "STKtype01"><legend>上櫃類別</legend></fieldset><td>
				</tr>
				<tr>
					<td><button style="width:110px;" id="B_SET"> 存 入  </button><td>
				</tr>
			</table> 
			<table style="width:100%; font-size: 12px;">
				<tr>
					<td>
						交易日期 : <input style="width:80px;" type="text" name="stk02001" id="stk02001"/><button id="B_001">..</button>
						<button style="width:110px;" id="B_TYP"> 類 別 條 件 </button>
						<button style="width:110px;float:right;" id="B_Run"> 開 始 執 行 </button>
        				<div style="clear:both;"></div>
					</td>				
				</tr>
			</table> 
			<ul class="tabs">
				<li><a href="#ta21" id='LL21'>股票代號一覽表</a></li>
				<li><a href="#ta22" id='LL22'>連續5天漲幅前50名(上市)</a></li>
				<li><a href="#ta23" id='LL23'>連續5天跌幅前50名(上市)</a></li>
				<li><a href="#ta24" id='LL24'>連續5天成交值前50名(上市)</a></li>
				<li><a href="#ta25" id='LL25'>連續5天熱門股前50名(上市)</a></li>
				<li><a href="#ta26" id='LL26'>連續5天漲幅前50名(上櫃)</a></li>
				<li><a href="#ta27" id='LL27'>連續5天跌幅前50名(上櫃)</a></li>
				<li><a href="#ta28" id='LL28'>連續5天成交值前50名(上櫃)</a></li>
				<li><a href="#ta29" id='LL29'>連續5天熱門股前50名(上櫃)</a></li>
			</ul>
			<div class="tab_container" style="height : 95%;border: 0px solid #fff;">
				<div id="ta21" class="tab_content">
					<table id="stock_21_grid"></table></div>
				<div id="ta22" class="tab_content">
					<table id="stock_22_grid"></table></div>
				<div id="ta23" class="tab_content">
					<table id="stock_23_grid"></table></div>
				<div id="ta24" class="tab_content">
					<table id="stock_24_grid"></table></div>
				<div id="ta25" class="tab_content">
					<table id="stock_25_grid"></table></div>
				<div id="ta26" class="tab_content">
					<table id="stock_26_grid"></table></div>
				<div id="ta27" class="tab_content">
					<table id="stock_27_grid"></table></div>
				<div id="ta28" class="tab_content">
					<table id="stock_28_grid"></table></div>
				<div id="ta29" class="tab_content">
					<table id="stock_29_grid"></table></div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="js/jquery/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.ui.datepicker.js"></script>
	<script type="text/javascript" src="js/jquery.alerts-1.1/jquery.alerts.js"></script>
	<script type="text/javascript" src="js/messages.js"></script>
	<script type="text/javascript" src="js/flexigrid/flexigrid.js"></script>
	<script type="text/javascript" src="js/upclick/upclick.js"></script>
	<script type="text/javascript" src="js/JSCal2-1.9/js/jscal2.js"></script>
	<script type="text/javascript" src="js/JSCal2-1.9/js/lang/b5.js"></script>
	<script type="text/javascript" charset="utf-8">
	// padding-right:100px;
	var stock_f1 = document.getElementById('stock_f1');
	var Para = '&pf=0&pdt=20130105';
	var Tstk02001 = "";
	var Tstatus = "";
	UPLTable = {
			dataType: 'json',
			colModel : [   
		 				{display: 'ID'		, name : 'id'			, hide:true, sortable : true, align: 'center'},
		 			 	{display: '證券代號'	, name : 'stk01001'	    , width : 60, sortable : true, align: 'left'},   
		 			 	{display: '證券名稱'	, name : 'stk01002'	    , width : 60, sortable : true, align: 'left'},   
		 			 	{display: '漲跌'		, name : 'stk02002'		, width : 50, sortable : true, align: 'right'},   
		 			 	{display: '開盤價'	, name : 'stk02003'		, width : 80, sortable : true, align: 'right'},   
		 			 	{display: '收盤價'	, name : 'stk02004'	    , width : 60, sortable : true, align: 'right'},   
		 			 	{display: '最高價'	, name : 'stk02005'	    , width : 60, sortable : true, align: 'right'},   
		 			 	{display: '最低價'	, name : 'stk02006'	    , width : 60, sortable : true, align: 'right'},   
		 			 	{display: '均價'		, name : 'stk02007'	    , width : 60, sortable : true, align: 'right'},   
		 			 	{display: '成交股數'	, name : 'stk02008'	    , width : 100, sortable : true, align: 'right'},   
		 			 	{display: '成交金額'	, name : 'stk02009'	    , width : 100, sortable : true, align: 'right'},   
		 			 	{display: '成交筆數'	, name : 'stk02010'		, width : 100, sortable : true, align: 'right'}],
			errormsg:	msg_fg_errormsg,
			pagestat:	msg_fg_pagestat, 
			procmsg:	msg_fg_procmsg, 
			nomsg:		msg_fg_nomsg,
			usepager : false,
			useRp : false,
			showTableToggleBtn : false,
			showToggleBtn:true,
			singleSelect:true, 
			nowrap:false,
			resizable:true,
			width :  $('#L01').width(),
			height : $(window).height()-170,//$(window).height()-$('#L01').height()-150
			onChangeSort : function(){},
			onSuccess : function(){alert("匯入成功");}
			};
	TSETable = {
			dataType: 'json',
			colModel : [{display: 'No'		, name : 'id'		, width : 40, sortable : true, align: 'right'},   
		 			 	{display: '第一天'	, name : 'TSE1'	    , width :160, sortable : true, align: 'left'},   
		 			 	{display: '第二天'	, name : 'TSE2'	    , width :160, sortable : true, align: 'left'},   
		 			 	{display: '第三天'	, name : 'TSE3'	    , width :160, sortable : true, align: 'left'},   
		 			 	{display: '第四天'	, name : 'TSE4'	    , width :160, sortable : true, align: 'left'},   
		 			 	{display: '第五天'	, name : 'TSE5'	    , width :160, sortable : true, align: 'left'}],   
			errormsg:	msg_fg_errormsg,
			pagestat:	msg_fg_pagestat, 
			procmsg:	msg_fg_procmsg, 
			nomsg:		msg_fg_nomsg,
			usepager : false,
			useRp : false,
			showTableToggleBtn : false,
			showToggleBtn:true,
			singleSelect:true, 
			nowrap:false,
			resizable:true,
			width :  $('#L01').width(),
			height : $(window).height()-180,//$(window).height()-$('#L01').height()-150
			onChangeSort : function(){},
			onSuccess : function(){}
			};
	OTCTable = {
			dataType: 'json',
			colModel : [{display: 'No'		, name : 'id'		, width : 40, sortable : true, align: 'right'},   
		 			 	{display: '第一天'	, name : 'OTC1'	    , width :160, sortable : true, align: 'left'},   
		 			 	{display: '第二天'	, name : 'OTC2'	    , width :160, sortable : true, align: 'left'},   
		 			 	{display: '第三天'	, name : 'OTC3'	    , width :160, sortable : true, align: 'left'},   
		 			 	{display: '第四天'	, name : 'OTC4'	    , width :160, sortable : true, align: 'left'},   
		 			 	{display: '第五天'	, name : 'OTC5'	    , width :160, sortable : true, align: 'left'}],   
			errormsg:	msg_fg_errormsg,
			pagestat:	msg_fg_pagestat, 
			procmsg:	msg_fg_procmsg, 
			nomsg:		msg_fg_nomsg,
			usepager : false,
			useRp : false,
			showTableToggleBtn : false,
			showToggleBtn:true,
			singleSelect:true, 
			nowrap:false,
			resizable:true,
			width :  $('#L01').width(),
			height : $(window).height()-180,//$(window).height()-$('#L01').height()-150
			onChangeSort : function(){},
			onSuccess : function(){}
			};
	STKTable = {
			url: 'json/Json01.action?action=UploadSTK',
			dataType: 'json',
			colModel : [{display: 'No'			, name : 'id'		, width : 40, sortable : true, align: 'right'},   
		 			 	{display: '證券代號'		, name : 'stk01001'	, width :160, sortable : true, align: 'left'},   
		 			 	{display: '證券名稱'		, name : 'stk01002'	, width :160, sortable : true, align: 'left'},   
		 			 	{display: '上市/上櫃'	, name : 'stk01003'	, width :160, sortable : true, align: 'left'},   
		 			 	{display: '類別'			, name : 'stk01004'	, width :160, sortable : true, align: 'left'}],   
			errormsg:	msg_fg_errormsg,
			pagestat:	msg_fg_pagestat, 
			procmsg:	msg_fg_procmsg, 
			nomsg:		msg_fg_nomsg,
			usepager : false,
			useRp : false,
			showTableToggleBtn : false,
			showToggleBtn:true,
			singleSelect:true, 
			nowrap:false,
			resizable:true,
			width :  $('#L01').width(),
			height : $(window).height()-180,//$(window).height()-$('#L01').height()-150
			onChangeSort : function(){},
			onSuccess : function(){}
			};
	$("#B_TYP").click(function(){
		if ($('#STKtype').is(':visible')) 
			{$('#STKtype').hide();}
		else{$('#STKtype').show();}
	});
	$("#B_SET").click(function (){
		array = [];
		$('[type=checkbox]','#STKtype00').each( function() {
			array.push( {stk01003 : 0,
						 stk01004 : $(this).val(),
						 stk01005 : $(this).is(':checked')
						});
		});
		$('[type=checkbox]','#STKtype01').each( function() {
			array.push( {stk01003 : 1,
				 		 stk01004 : $(this).val(),
				 		 stk01005 : $(this).is(':checked')
						});
		});
		$.ajax({
			type:'POST',
			dataType: 'json',
			url: 'json/Json01.action', 
			data:{action: 'TYPESAVE', ty00: JSON.stringify(array)},
			success:function(data,status){
				alert("ok ...");
			}});
	});
	$("#B_Run").click(Report);
	// $("#stk02001").blur(Report);
	$('#stk02001').val($.datepicker.formatDate("yy/mm/dd",new Date()));
	
	function setcolModel(data){
		$('th[abbr="TSE1"] >div').html(data["TSEcol"][0]);
		$('th[abbr="TSE2"] >div').html(data["TSEcol"][1]);
		$('th[abbr="TSE3"] >div').html(data["TSEcol"][2]);
		$('th[abbr="TSE4"] >div').html(data["TSEcol"][3]);
		$('th[abbr="TSE5"] >div').html(data["TSEcol"][4]);
		$('th[abbr="OTC1"] >div').html(data["OTCcol"][0]);
		$('th[abbr="OTC2"] >div').html(data["OTCcol"][1]);
		$('th[abbr="OTC3"] >div').html(data["OTCcol"][2]);
		$('th[abbr="OTC4"] >div').html(data["OTCcol"][3]);
 		$('th[abbr="OTC5"] >div').html(data["OTCcol"][4]);
	}
	function Report(){
		Tstk02001 = $('#stk02001').val();
		type00 = "";
		type01 = "";
		$('[type=checkbox]:checked','#STKtype00').each( function() {
			type00 = type00+"'"+$(this).val()+"',";
		});
		$('[type=checkbox]:checked','#STKtype01').each( function() {
			type01 = type01+"'"+$(this).val()+"',";
		});
		if (0!=type00.length) type00 = "("+type00.substr(0,type00.length-1)+")";  
		if (0!=type01.length) type01 = "("+type01.substr(0,type01.length-1)+")";
		$.ajax({
			type:'POST',
			dataType: 'json',
			url: 'json/Json01.action', 
			data:{action: 'report', pdt: Tstk02001,ty00 : type00,ty01 : type01},
			success:function(data,status){
				setcolModel(data);
				$('#stock_22_grid').flexAddData(data["stock_22"]); // 連續5天漲幅前50名(上市)
				$('#stock_23_grid').flexAddData(data["stock_23"]); // 連續5天跌幅前50名(上市)
				$('#stock_24_grid').flexAddData(data["stock_24"]); // 連續5天成交值前50名(上市)
				$('#stock_25_grid').flexAddData(data["stock_25"]); // 連續5天熱門股前50名(上市)
				$('#stock_26_grid').flexAddData(data["stock_26"]); // 連續5天漲幅前50名(上櫃)
				$('#stock_27_grid').flexAddData(data["stock_27"]); // 連續5天跌幅前50名(上櫃)
				$('#stock_28_grid').flexAddData(data["stock_28"]); // 連續5天成交值前50名(上櫃)
				$('#stock_29_grid').flexAddData(data["stock_29"]); // 連續5天熱門股前50名(上櫃)
				alert("ok ...");
			},
			error  : function(XMLHttpRequest, textStatus, errorThrown){
	            jError(XMLHttpRequest.responseText); 
			}
		});			
	}	 
	function setini(){
		$.ajax({
			type:'POST',
			dataType: 'json',
			url: 'json/Json01.action', 
			data:{action: 'setini'},
			success:function(data,status){
				for(var key in data["Type0"]) {
					$('#STKtype00').append(data["Type0"][key]);
				}	
				for(var key in data["Type1"]) {
					$('#STKtype01').append(data["Type1"][key]);
				}
				$('#STKtype').hide();	
			},
			error  :function(XMLHttpRequest, textStatus, errorThrown){
	            jError(XMLHttpRequest.responseText); 
				}
		});			
	}
	$(function(){
		setini();
		$defaultLi = $('ul.tabs li').eq(0).addClass('active');
		$($defaultLi.find('a').attr('href')).siblings().hide();
		$defaultLi = $('ul.tabs li').eq(2).addClass('active');
		$($defaultLi.find('a').attr('href')).siblings().hide();
		$('ul.tabs li').click(function() {
			var $this = $(this),
				_clickTab = $this.find('a').attr('href');
			$this.addClass('active').siblings('.active').removeClass('active');
			$(_clickTab).stop(false, true).fadeIn().siblings().hide();
			return false;
		}).find('a').focus(function(){
			this.blur();
		});
	});
	Calendar.setup({
		inputField : "stk02001",
		trigger    : "B_001",
		onSelect   : function() { this.hide();},
		showTime   : 12,
		dateFormat : "%Y/%m/%d"
	});
	$('#stock_f1_grid').flexigrid(UPLTable);
	$('#stock_21_grid').flexigrid(STKTable);	
	$('#stock_22_grid').flexigrid(TSETable);
	$('#stock_23_grid').flexigrid(TSETable);
	$('#stock_24_grid').flexigrid(TSETable);
	$('#stock_25_grid').flexigrid(TSETable);
	$('#stock_26_grid').flexigrid(OTCTable);
	$('#stock_27_grid').flexigrid(OTCTable);
	$('#stock_28_grid').flexigrid(OTCTable);
	$('#stock_29_grid').flexigrid(OTCTable);
	upclick({
		      element: stock_f1,
		      action: 'json/Json01.action',
              action_params: {'action':'Upload'},
			  dataname: 'fileData',
			  autoSubmit: true,
		      onstart:
			        function(filename)
			        {
		    	  	  var t = filename.split("\\");
		    	  	  t = t[t.length-1];
		    	  	  ext = t.split(".");
		    	  	  t = ext[0].toUpperCase();
		    	  	  ext = ext[ext.length-1].toUpperCase();
		    	  	  if ("STOCKTABLE"==t){
		    	  		dt = "";
		    	  	  	Para = ""; 
		    	  		return; 
		    	  	  }
		    	  	  if ("CSV"==ext){ 
			    	  	  t = t.split("_");
			    	  	  t = t[t.length-1];
			    	  	  dt = (parseInt(t.substr(0,3))+1911)+t.substr(-4);
			    	  	  Para = '&pf=1&pdt='+dt; 
		    	  	  }	  
		    	  	  if ("CSV"!=ext){
			    	  	  dt = t.split("-")[1];
			    	  	  Para = '&pf=0&pdt='+dt; 
		    	  	  }	   
			        },
		      oncomplete:
		        function(response_data) 
		        {
			      	if ((""==response_data)||("<pre></pre>"==response_data)) {
				      	if (dt=="") {
					      	$('#LL02').click();
					      	$('#LL03').click();
							$('#stock_21_grid')
							.flexOptions({url: 'json/Json01.action?action=UploadSTK'})
							.flexReload();
				      	}
				      	else {  	
							$('#stock_f1_grid')
							.flexOptions({url: 'json/Json01.action?action=Uploadbox'+Para})
							.flexReload();
				      	}	
					} 
			      	else{
						alert("匯入失敗");
					}    
		        }
		     });
	</script>
</body>
</html>