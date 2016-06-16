<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="application/vnd.ms-excel; charset=ISO-8859-1">

<title>Insert title here</title>

</head>
<body>
<div id="tableExcel">  
<table id="test" width="100%" border="1" cellspacing="0" cellpadding="0"> 
      <tr> 
          <td colspan="5" align="center">WEB页面导出为EXCEL文档的方法</td> 
      </tr> 
      <tr> 
          <td>列标题1</td> 
          <td>列标题2</td> 
          <td>列标题3</td> 
          <td>列标题4</td> 
          <td>列标题5</td> 
      </tr> 
      <tr> 
          <td>aaa</td> 
          <td>bbb</td> 
          <td>ccc</td> 
          <td>ddd</td> 
          <td>eee</td> 
      </tr> 
      <tr> 
          <td>AAA</td> 
          <td>BBB</td> 
          <td>CCC</td> 
          <td>DDD</td> 
          <td>EEE</td> 
      </tr> 
      <tr> 
          <td>FFF</td> 
          <td>GGG</td> 
          <td>HHH</td> 
          <td>III</td> 
          <td>JJJ</td> 
      </tr> 
      <tr> 
        <td>aaa</td> 
        <td>bbb</td> 
        <td>ccc</td> 
        <td>ddd</td> 
        <td>eee</td> 
      </tr> 
      <tr> 
        <td>AAA</td> 
        <td>BBB</td> 
        <td>CCC</td> 
        <td>DDD</td> 
        <td>EEE</td> 
      </tr> 
      <tr> 
        <td colspan="5">FFFGGGHHHIIIJJJ</td> 
      </tr> 
     
      <tr> 
        <td>aaa</td> 
        <td>bbb</td> 
        <td>ccc</td> 
        <td>ddd</td> 
        <td>eee</td> 
      </tr> 
      <tr> 
        <td>AAA</td> 
        <td>BBB</td> 
        <td rowspan="4">CCCHHHcccccc</td> 
        <td>DDD</td> 
        <td>EEE</td> 
      </tr> 
      <tr> 
        <td>FFF</td> 
        <td>GGG</td> 
        <td>III</td> 
        <td>JJJ</td> 
      </tr> 
     
      <tr> 
        <td>aaa</td> 
        <td>bbb</td> 
        <td>ddd</td> 
        <td>eee</td> 
      </tr> 
      <tr> 
        <td>aaa</td> 
        <td>bbb</td> 
        <td>ddd</td> 
        <td>eee</td> 
      </tr> 
      <tr> 
        <td>AAA</td> 
        <td>BBB</td> 
        <td>CCC</td> 
        <td>DDD</td> 
        <td>EEE</td> 
      </tr> 
      <tr> 
        <td>FFF</td> 
        <td>GGG</td> 
        <td>HHH</td> 
        <td>III</td> 
        <td>JJJ</td> 
      </tr> 
</table> 
</div> 
<input type="button" value="另存为 Excel" onclick="saveCode(test)">
<input type="button" value="save as Excel2" onclick="exportExecl(test)">
<input type="button" value="save as Excel3" onclick="showLeftTime()">
</body>
<script type="text/javascript"> 

function saveCode(obj) { 
	  var tableExcel = document.getElementById('test');
	  if(tableExcel == null){
			alert('没有数据可以导出');
			return
	  }
/*  	  var now=new Date();
	  var year = now.getFullYear().toString();
	  alert(year);
	  var month = (now.getMonth()+1).toString();
	  alert(month);
	  var date = now.getDate().toString();
	  alert(date);
	  var hours = now.getHours().toString();
	  var fileName = year + month + date;
	  alert(fileName);  */
	  
	  
      var strHTML = tableExcel.outerHTML;	  
	  var winname = window.open('', '_blank', 'top=10000'); 
      winname.document.open('text/html', 'replace'); 
      winname.document.writeln(strHTML); 
      winname.document.execCommand('saveas','','CPS实时订单查询'+'.xls'); 
      winname.close(); 
} 

function exportExecl(tableId) {
	var table=document.getElementById(tableId);
		var oXL = new ActiveXObject("Excel.Application");
	var oWB = oXL.Workbooks.Add();
	var oSheet = oWB.ActiveSheet; 
	var sel=document.body.createTextRange();
	sel.moveToElementText(table);
	sel.select();
	sel.execCommand("Copy");
	oSheet.Paste();
	oXL.Visible = true;
}

function AutomateExcel(){
    var elTable = document.getElementById("test"); //要导出的table id。
    var oRangeRef = document.body.createTextRange(); 
    oRangeRef.moveToElementText(elTable); 
    oRangeRef.execCommand("Copy");
    var appExcel = new ActiveXObject("Excel.Application");
    appExcel.Workbooks.Add().Worksheets.Item(1).Paste(); 
    appExcel.Visible = true; 
    appExcel = null;
}

</script> 
<script language="javascript" type="text/javascript">
//获得当前时间,刻度为一千分一秒
var initializationTime=(new Date()).getTime();
function showLeftTime()
{
var now=new Date();
var year=now.getYear();
var month=now.getMonth();
var day=now.getDate();
var hours=now.getHours();
var minutes=now.getMinutes();
var seconds=now.getSeconds();
//document.all.show.innerHTML=""+year+"年"+month+"月"+day+"日 "+hours+":"+minutes+":"+seconds+"";
alert(year);
//一秒刷新一次显示时间
var timeID=setTimeout(showLeftTime,1000);
}
</script>

</html>