<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge" >    
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<button style="width:110px;" id="B_javacsv"> javacsvTest </button>
	<button style="width:110px;" id="B_opencsv"> opencsvTest </button>
	<script type="text/javascript" src="js/jquery/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="js/upclick/upclick.js"></script>
	<script type="text/javascript" charset="utf-8">
	var stock_f1 = document.getElementById('B_opencsv');
	var stock_f2 = document.getElementById('B_javacsv');
	upclick({
	      element: stock_f2,
	      action: 'json/Json01.action',
      action_params: {'action':'javacsv'},
		  dataname: 'fileData',
		  autoSubmit: true,
	      onstart:
	        function(filename)
	        {
	        },
	      oncomplete:
	        function(response_data) 
	        {
		      	if ((""==response_data)||("<pre></pre>"==response_data)) {
			      	alert("ok....");
		      	}  	
	        }
	     });
	upclick({
	      element: stock_f1,
	      action: 'json/Json01.action',
        action_params: {'action':'opencsv'},
		  dataname: 'fileData',
		  autoSubmit: true,
	      onstart:
	        function(filename)
	        {
	        },
	      oncomplete:
	        function(response_data) 
	        {
		      	if ((""==response_data)||("<pre></pre>"==response_data)) {
			      	alert("ok....");
		      	}  	
	        }
	     });
	</script>
</body>
</html>