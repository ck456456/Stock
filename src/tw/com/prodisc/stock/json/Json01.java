package tw.com.prodisc.stock.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


import tw.com.prodisc.stock.bean.BSTKF01;
import tw.com.prodisc.stock.bean.BSTKF02;
import tw.com.prodisc.stock.service.IsSTKF01;
import tw.com.prodisc.stock.service.IsSTKF02;
import tw.com.prodisc.util.KC_Pub;

import au.com.bytecode.opencsv.CSVReader;

import com.csvreader.CsvReader;
import com.opensymphony.xwork2.ActionSupport;

public class Json01 extends ActionSupport{

	private static final long serialVersionUID = 1L;
	private JSONArray  TSEcol; // 上市五天 
	private JSONArray  OTCcol; // 上櫃五天
	private String[][] buf = new  String[50][5];
	
	private String action;
	private String pf;
	private String pdt;
	private String ty00;
	private String ty01;
	
	
	private Date tstk02001;  // 交易日期
	
	private String fileDataFileName;
	private String fileDataContentType;
	private File   fileData;
	@SuppressWarnings("rawtypes")
	private IsSTKF01 s_STKF01;
	@SuppressWarnings("rawtypes")
	private IsSTKF02 s_STKF02;
	

	@SuppressWarnings("rawtypes")
	public IsSTKF02 getS_STKF02() {
		return s_STKF02;
	}
	@SuppressWarnings("rawtypes")
	public void setS_STKF02(IsSTKF02 s_STKF02) {
		this.s_STKF02 = s_STKF02;
	}
	public String getTy00() {
		return ty00;
	}
	public void setTy00(String ty00) {
		this.ty00 = ty00;
	}
	public String getTy01() {
		return ty01;
	}
	public void setTy01(String ty01) {
		this.ty01 = ty01;
	}
	public String getPf() {
		return pf;
	}
	public void setPf(String pf) {
		this.pf = pf;
	}
	public String getPdt() {
		return pdt;
	}
	public void setPdt(String pdt) {
		this.pdt = pdt;
	}
	public String getFileDataFileName() {
		return fileDataFileName;
	}
	public void setFileDataFileName(String fileDataFileName) {
		this.fileDataFileName = fileDataFileName;
	}
	public String getFileDataContentType() {
		return fileDataContentType;
	}
	public void setFileDataContentType(String fileDataContentType) {
		this.fileDataContentType = fileDataContentType;
	}
	public File getFileData() {
		return fileData;
	}
	public void setFileData(File fileData) {
		this.fileData = fileData;
	}
	@SuppressWarnings("rawtypes")
	public IsSTKF01 getS_STKF01() {
		return s_STKF01;
	}
	@SuppressWarnings("rawtypes")
	public void setS_STKF01(IsSTKF01 s_STKF01) {
		this.s_STKF01 = s_STKF01;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		if (action == null) action = "";
		this.action = action.toUpperCase();
	}
	
	private void setF02(List<BSTKF02> f02_dat,BufferedReader reader){
		try {
			String text = null;
			while ((text = reader.readLine()) != null) {
				String[] Tmp =text.split("\"");
				text = "";
				for (int i=0;i<Tmp.length;i++){
					if ((i % 2)==1) Tmp[i] = Tmp[i].replace(",", "");
					text = text+Tmp[i];
				}
				String[] Strs =text.replace("\"", "").split(",");
				if (Strs.length<14) continue;
				if (!KC_Pub.isNumeric(Strs[0].substring(0,1))) continue;
				BSTKF01 f01 = s_STKF01.getNo(Strs[0]);
				if (null == f01) continue;
				BSTKF02 f02 = new BSTKF02();
				f02.setStk02001(tstk02001);
				f02.setStk02002(KC_Pub.kc_Str2Float (Strs[3],0)); // 漲跌
				f02.setStk02003(KC_Pub.kc_Str2Float (Strs[4],0)); // 開盤 價 
				f02.setStk02004(KC_Pub.kc_Str2Float (Strs[2],0)); // 收盤 價 
				f02.setStk02005(KC_Pub.kc_Str2Float (Strs[5],0)); // 最高 價 
				f02.setStk02006(KC_Pub.kc_Str2Float (Strs[6],0)); // 最低 價 
				f02.setStk02007(KC_Pub.kc_Str2Float (Strs[7],0)); // 均價 
				f02.setStk02008(KC_Pub.kc_Str2Long  (Strs[8],0)); // 成交股數 
				f02.setStk02009(KC_Pub.kc_Str2Double(Strs[9],0)); // 成交金額(元) 
				f02.setStk02010(KC_Pub.kc_Str2Long  (Strs[10],0)); // 成交筆數
				f02.setF01(f01);
				f02_dat.add(f02);
				/*
				   0   1   2	   3   4       5      6   7           8               9      10      11      12      13        14           15         16
				代號	名稱	收盤 	漲跌	開盤 	最高 	最低	均價 	成交股數  	成交金額(元)	成交筆數 	最後買價	最後賣價	發行股數 	次日參考價 	 次日漲停價 	次日跌停價 
				private float 		stk02002;		 // 漲跌	 
				private float 		stk02003;		 // 開盤 價	 
				private float 		stk02004;		 // 收盤 價
				private float 		stk02005;		 // 最高 價
				private float 		stk02006;		 // 最低 價
				private float 		stk02007;		 // 均價
				private long 		stk02008;		 // 成交股數
				private BigDecimal 	stk02009;		 // 成交金額(元)
				private long 		stk02010;		 // 成交筆數
				*/
			}
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
	private void setCSV(List<BSTKF02> f02s,List<BSTKF02> f02_dat) {
		try {
			setF02(f02_dat,new BufferedReader(new InputStreamReader(new FileInputStream(fileData), "BIG5")));
			chkf02s(f02s,f02_dat);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void setF02(List<BSTKF02> f02_dat,Sheet sheet){
		String chk = "";
		int sign = 1;
		float tAve=0;
		for (int i=0;i<sheet.getRows();i++){
			chk = sheet.getCell(0,i).getContents().trim();
			if (!KC_Pub.isNumeric(chk.substring(0,1))) continue;
			
			BSTKF01 f01 = s_STKF01.getNo(chk);
			if (null == f01) continue;
			sign = "-".equals(sheet.getCell(7, i).getContents())?-1:1;
			tAve = KC_Pub.kc_Str2Float (sheet.getCell(2, i).getContents().replace(",",""),0);
			tAve = (tAve == 0)?1:tAve;
			tAve = KC_Pub.kc_Str2Float (sheet.getCell(12, i).getContents().replace(",",""),0)/tAve;
			BSTKF02 f02 = new BSTKF02();
			f02.setStk02001(tstk02001);
			f02.setStk02002(KC_Pub.kc_Str2Float (sheet.getCell( 8, i).getContents().replace(",",""),0)*sign); // 漲跌
			f02.setStk02003(KC_Pub.kc_Str2Float (sheet.getCell( 3, i).getContents().replace(",",""),0)); // 開盤 價 
			f02.setStk02004(KC_Pub.kc_Str2Float (sheet.getCell( 6, i).getContents().replace(",",""),0)); // 收盤 價 
			f02.setStk02005(KC_Pub.kc_Str2Float (sheet.getCell( 4, i).getContents().replace(",",""),0)); // 最高 價 
			f02.setStk02006(KC_Pub.kc_Str2Float (sheet.getCell( 5, i).getContents().replace(",",""),0)); // 最低 價
			f02.setStk02007(tAve); // 均價 
			f02.setStk02008(KC_Pub.kc_Str2Long  (sheet.getCell( 2, i).getContents().replace(",",""),0)); // 成交股數 
			f02.setStk02009(KC_Pub.kc_Str2Double(sheet.getCell(12, i).getContents().replace(",",""),0)); // 成交金額(元) 
			f02.setStk02010(KC_Pub.kc_Str2Long  (sheet.getCell(11, i).getContents().replace(",",""),0)); // 成交筆數
			f02.setF01(f01);
			
			f02_dat.add(f02);
		}
	}
	private void setXLS(List<BSTKF02> f02s, List<BSTKF02> f02_dat){
		try {
			Workbook wb = Workbook.getWorkbook(fileData);
			setF02(f02_dat,wb.getSheet(0));
			chkf02s(f02s,f02_dat);
			wb.close();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	private void chkf02s(List<BSTKF02> f02s,List<BSTKF02> f02_dat){
		boolean b1,b2 = true;
		for(BSTKF02 d1 : f02_dat){
			b1 = true;
			b2 = true;
			for(BSTKF02 d2 : f02s){
				if (!d1.getF01().getStk01001().equals(d2.getF01().getStk01001())) continue;
				b1 = false;
				b2 = false;
				if ( d1.getStk02002()!=d2.getStk02002()) b2 = true;
				if ( d1.getStk02003()!=d2.getStk02003()) b2 = true;
				if ( d1.getStk02004()!=d2.getStk02004()) b2 = true;
				if ( d1.getStk02005()!=d2.getStk02005()) b2 = true;
				if ( d1.getStk02006()!=d2.getStk02006()) b2 = true;
				if ( d1.getStk02007()!=d2.getStk02007()) b2 = true;
				if ( d1.getStk02008()!=d2.getStk02008()) b2 = true;
				if ( d1.getStk02009()!=d2.getStk02009()) b2 = true;
				if ( d1.getStk02010()!=d2.getStk02010()) b2 = true;
				if ( b2) {
					d2.setStk02002(d1.getStk02002());
					d2.setStk02003(d1.getStk02003());
					d2.setStk02004(d1.getStk02004());
					d2.setStk02005(d1.getStk02005());
					d2.setStk02006(d1.getStk02006());
					d2.setStk02007(d1.getStk02007());
					d2.setStk02008(d1.getStk02008());
					d2.setStk02009(d1.getStk02009());
					d2.setStk02010(d1.getStk02010());
					s_STKF01.save(d2); 
				}
				break;
			}
			if (b1) s_STKF01.create(d1); 
		}
	}
	private Date getTstk02001(String ext){
		String fname = fileDataFileName.substring(0,fileDataFileName.lastIndexOf("."));
		fname = fname.substring(fileDataFileName.lastIndexOf("_")+1);
		if ( ext.equals("CSV")){
		    return KC_Pub.kc_setdate(
	    		 Integer.parseInt(fname.substring(0,3))+1911,
	    		 Integer.parseInt(fname.substring(3,5)),
	    		 Integer.parseInt(fname.substring(5,7)));
		}
		String[] Strs = fileDataFileName.split("-");
		return KC_Pub.kc_Str2Date("yyyyMMdd",Strs[1]);
	}
	@SuppressWarnings("unchecked")
	private void chkf01s(List<BSTKF01> f01s,List<BSTKF01> f01_dat){
		boolean b1,b2 = true;
		for(BSTKF01 d1 : f01_dat){
			b1 = true;
			b2 = true;
			for(BSTKF01 d2 : f01s){
				if (d1.getStk01001().equals("1101")){
					System.out.print(d1.getStk01002());
				}
				if (!d1.getStk01001().equals(d2.getStk01001())) continue;
				b1 = false;
				b2 = false;
				if (!d1.getStk01002().equals(d2.getStk01002())) b2 = true; 
				if ( d1.getStk01003()!=d2.getStk01003()) b2 = true;
				if (!d1.getStk01004().equals(d2.getStk01004())) b2 = true;
				if ( b2) {
					d2.setStk01002(d1.getStk01002());
					d2.setStk01003(d1.getStk01003());
					d2.setStk01004(d1.getStk01004());
					s_STKF01.save(d2); 
				}
				break;
			}
			if (b1){
				d1.setStk01005(s_STKF01.getByType(d1.getStk01004()));
				s_STKF01.create(d1);
			}	
		}
	}
	private void setF01(List<BSTKF01> f01_dat,Sheet sheet){
		String chk = "",s01="",s02="";
		int    flg = 0; // 0:上市 1:上櫃 
		for (int j=0;j<sheet.getColumns();j=j+2){
			for (int i=3;i<sheet.getRows();i++){
				chk = sheet.getCell(j,i).getContents().trim();
				s01 = sheet.getCell(j+1, i).getContents().trim();
				if (KC_Pub.isNull(chk)) continue;
				if (KC_Pub.isNull(s01)) continue;
				chk = chk.replace("�","");
				chk = chk.replace(" ","");
				s01 = s01.replace("＊","");
				s01 = s01.replace("＃","");
				s01 = s01.replace(" ","");
				s01 = s01.replace("�","");
				
				if ("上市".equals(chk)) {flg=0;s02=s01;continue;}
				if ("上櫃".equals(chk)) {flg=1;s02=s01;continue;}
				if (!KC_Pub.isNumeric(chk.substring(0,1))) continue;
				BSTKF01 f01 = new BSTKF01();
				f01.setStk01001(chk);
				f01.setStk01002(s01);
				f01.setStk01003(flg);
				f01.setStk01004(s02);
				f01_dat.add(f01);
			}
		}	
	}
	private void setSTK(List<BSTKF01> f01s, List<BSTKF01> f01_dat){
		try {
			Workbook wb = Workbook.getWorkbook(fileData);
			setF01(f01_dat,wb.getSheet(0));
			chkf01s(f01s,f01_dat);
			wb.close();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	private void uploadstk(){
		int rowid = 0;
		HttpServletResponse  response = ServletActionContext.getResponse();
		List<BSTKF01> f01s = s_STKF01.getAll();
		JSONObject obj=new JSONObject();
		JSONArray  arr = new JSONArray();
        for(int i=0;i<f01s.size();i++){
        	JSONObject obj01 = new JSONObject();   
        	JSONObject obj02 = new JSONObject();   
    		obj01.put("id"           , ""+(++rowid));
			obj02.put("id"           , ""+rowid);
		 	obj02.put("stk01001"     , f01s.get(i).getStk01001());
		 	obj02.put("stk01002"	 , f01s.get(i).getStk01002());
		 	obj02.put("stk01003"	 , (0==f01s.get(i).getStk01003())?"上市":"上櫃");
		 	obj02.put("stk01004"	 , f01s.get(i).getStk01004());
    		obj01.put("cell",obj02);
    		arr.add(obj01);
        }
		obj.put("rows" , arr);
		obj.put("total", rowid);
		obj.put("jqcmd", "");
		KC_Pub.kc_JSONSnd(obj.toString(),response);
	}
	@SuppressWarnings("unchecked")
	private void uploadbox(){
		int rowid = 0;
		HttpServletResponse  response = ServletActionContext.getResponse();
		tstk02001 = KC_Pub.kc_Str2Date("yyyyMMdd",pdt);
		List<BSTKF02> f02s = s_STKF02.getByDate(tstk02001,pf);
		JSONObject obj=new JSONObject();
		JSONArray  arr = new JSONArray();
        for(int i=0;i<f02s.size();i++){
        	JSONObject obj01 = new JSONObject();   
        	JSONObject obj02 = new JSONObject();   
    		obj01.put("id"           , ""+(++rowid));
			obj02.put("id"           , f02s.get(i).getId());			   
		 	obj02.put("stk01001"     , f02s.get(i).getF01().getStk01001());
		 	obj02.put("stk01002"	 , f02s.get(i).getF01().getStk01002());
		 	obj02.put("stk02002"	 , KC_Pub.kc_FormatNum("###,###.##",f02s.get(i).getStk02002()));
		 	obj02.put("stk02003"	 , KC_Pub.kc_FormatNum("###,###.##",f02s.get(i).getStk02003()));
		 	obj02.put("stk02004"	 , KC_Pub.kc_FormatNum("###,###.##",f02s.get(i).getStk02004()));
		 	obj02.put("stk02005"	 , KC_Pub.kc_FormatNum("###,###.##",f02s.get(i).getStk02005()));
		 	obj02.put("stk02006"	 , KC_Pub.kc_FormatNum("###,###.##",f02s.get(i).getStk02006()));
		 	obj02.put("stk02007"	 , KC_Pub.kc_FormatNum("###,###.##",f02s.get(i).getStk02007()));
		 	obj02.put("stk02008"	 , KC_Pub.kc_FormatNum("###,###",f02s.get(i).getStk02008()));
		 	obj02.put("stk02009"	 , KC_Pub.kc_FormatNum("###,###",f02s.get(i).getStk02009()));
		 	obj02.put("stk02010"	 , KC_Pub.kc_FormatNum("###,###",f02s.get(i).getStk02010()));
    		obj01.put("cell",obj02);
    		arr.add(obj01);
        }
		obj.put("rows" , arr);
		obj.put("total", rowid);
		obj.put("jqcmd", "");
		KC_Pub.kc_JSONSnd(obj.toString(),response);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private JSONArray get5day(String ff){
		tstk02001 = KC_Pub.kc_Str2Date("yyyy/MM/dd",pdt);
		List<BSTKF02> f02s = s_STKF02.getDateList(tstk02001,5,ff);
		JSONArray  arr = new JSONArray();
        for(int i=0;i<5;i++){
        	if (i<f02s.size()) 
        		arr.add(KC_Pub.kc_Date2Str("yyyy/MM/dd",(Date)((Map)f02s.get(i)).get("stk02001"))+"(第 "+(i+1)+" 天)");
        	else
        		arr.add("");
        }
		return arr;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private JSONObject getRise(String f1,String ty,String f2,String f3,JSONArray day){
		String s01;
		String s02 = "0".equals(f1) ? "TSE":"OTC";
		int iRow=0;
		boolean bb = false;
		
		for (int i = 0; i < buf.length; i++)  
			for (int j = 0; j < buf[i].length; j++)
				buf[i][j] = "";
		for (int i = 0; i < day.size(); i++){
			s01 = (String)day.get(i);
			if (KC_Pub.isNull(s01)) continue;
			s01 = s01.substring(0,10);
			List<Map> f02s = s_STKF02.getRise(KC_Pub.kc_Str2Date("yyyy/MM/dd", s01),f1,ty,f2,f3,50);
			iRow=0;
	        for(int j=0;j<50;j++){
	        	if (j >= f02s.size()) continue;
	        	s01 = "<a href=\"http://tw.stock.yahoo.com/q/q?s="+f02s.get(j).get("stk01001")+"\" target=\"_blank\">"+f02s.get(j).get("stk01001")+" "+f02s.get(j).get("stk01002")+"</a>";
	        	if (i>0) {
	        		bb = false;
	        		for (int k=0;k<50;k++)
	        			if (s01.equals(buf[k][i-1])) bb = true;
	        		if (!bb) continue;
	        	}
	        	buf[iRow][i] = s01;
	        	iRow++;
	        }
		}
		JSONObject obj=new JSONObject();
		JSONArray  arr = new JSONArray();
        for(int i=0;i<buf.length;i++){
        	JSONObject obj01 = new JSONObject();   
        	JSONObject obj02 = new JSONObject();   
    		obj01.put("id"       , ""+(i+1));
			obj02.put("id"       , ""+(i+1));			   
		 	obj02.put(s02+"1"    , buf[i][0]);
		 	obj02.put(s02+"2"	 , buf[i][1]);
		 	obj02.put(s02+"3"	 , buf[i][2]);
		 	obj02.put(s02+"4"	 , buf[i][3]);
		 	obj02.put(s02+"5"	 , buf[i][4]);
    		obj01.put("cell",obj02);
    		arr.add(obj01);
        }
		obj.put("rows" , arr);
		obj.put("total", buf.length);
		obj.put("jqcmd", "");
		// s02
		/*
		colModel : [{display: 'ID'		, name : 'id'		, hide:true, sortable : true, align: 'center'},   
	 			 	{display: '第一天'	, name : 'TSE1'	    , width :160, sortable : true, align: 'left'},   
	 			 	{display: '第二天'	, name : 'TSE2'	    , width :160, sortable : true, align: 'left'},   
	 			 	{display: '第三天'	, name : 'TSE3'	    , width :160, sortable : true, align: 'left'},   
	 			 	{display: '第四天'	, name : 'TSE4'	    , width :160, sortable : true, align: 'left'},   
	 			 	{display: '第五天'	, name : 'TSE5'	    , width :160, sortable : true, align: 'left'}],
	 	*/		 	   
		return obj;
	}
	private void report(){
		HttpServletResponse  response = ServletActionContext.getResponse();
		JSONObject obj=new JSONObject();
		obj.put("TSEcol"   , TSEcol = get5day("0")); // 上市五天 0:上市 1:上櫃 
		obj.put("OTCcol"   , OTCcol = get5day("1")); // 上櫃五天
		obj.put("stock_22" , getRise("0",ty00+" and stk02002 > 0 ","(stk02004/(stk02004-stk02002))","desc",TSEcol)); // 上市漲5天-50名
		obj.put("stock_23" , getRise("0",ty00+" and stk02002 < 0 ","(stk02004/(stk02004-stk02002))","asc" ,TSEcol)); // 上市跌5天-50名
		obj.put("stock_24" , getRise("0",ty00,"stk02009","desc" ,TSEcol)); // 上櫃成交值5天-50名(交易金額)
		obj.put("stock_25" , getRise("0",ty00,"stk02008","desc" ,TSEcol)); // 上櫃熱門股5天-50名(交易股數)
		
		obj.put("stock_26" , getRise("1",ty01+" and stk02002 > 0 ","(stk02004/(stk02004-stk02002))","desc",OTCcol)); // 上櫃漲5天-50名
		obj.put("stock_27" , getRise("1",ty01+" and stk02002 < 0 ","(stk02004/(stk02004-stk02002))","asc" ,OTCcol)); // 上櫃跌5天-50名
		obj.put("stock_28" , getRise("1",ty01,"stk02009","desc" ,OTCcol)); // 上櫃成交值5天-50名(交易金額)
		obj.put("stock_29" , getRise("1",ty01,"stk02008","desc" ,OTCcol)); // 上櫃熱門股5天-50名(交易股數)
		KC_Pub.kc_JSONSnd(obj.toString(),response);
	}
	@SuppressWarnings("unchecked")
	private void upload(){
		String fname = fileDataFileName.substring(0,fileDataFileName.lastIndexOf(".")).toUpperCase();
		List<BSTKF01> f01s = s_STKF01.getAll();
		List<BSTKF01> f01_dat = new ArrayList<BSTKF01>();
		if ("STOCKTABLE".equals(fname)) {
			setSTK(f01s,f01_dat);
			return;
		}
		String ext = fileDataFileName.substring(fileDataFileName.lastIndexOf(".")+1).toUpperCase();
		tstk02001 = getTstk02001(ext);
		List<BSTKF02> f02s = s_STKF02.getByDate(tstk02001);
		List<BSTKF02> f02_dat = new ArrayList<BSTKF02>();
		if ( ext.equals("CSV")) setCSV(f02s,f02_dat); //上櫃資料 轉 BSTKF02
		if (!ext.equals("CSV")) setXLS(f02s,f02_dat); //上市資料 轉 BSTKF01,BSTKF02
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setini(){
		HttpServletResponse  response = ServletActionContext.getResponse();
		String s01="",s02="";
		List<Map> f01s = s_STKF01.getByType();
		JSONObject obj=new JSONObject();
		JSONArray  ar0 = new JSONArray();
		JSONArray  ar1 = new JSONArray();
		 
        for(Map row: f01s){
        	s02 = ((boolean)row.get("stk01005")) ? "checked":"";
        	s01 = "<input type='checkbox' value='"+row.get("stk01004")+"' "+s02+">"+row.get("stk01004");
        	if (0 == (int)row.get("stk01003"))
        		ar0.add(s01);
        	else
        		ar1.add(s01);
        }
		obj.put("Type0"   , ar0);  
		obj.put("Type1"   , ar1); 
		KC_Pub.kc_JSONSnd(obj.toString(),response);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void typesave(){
		JSONArray  ar0 = new JSONArray();
		JSONArray  ar1 = (JSONArray)ar0.element(ty00).get(0);
		JSONObject obj = null;
		List<Map> f01s = s_STKF01.getByType();
		for (int i=0;i<f01s.size();i++){
			for (int j=0;j<ar1.size();j++){
				obj = ar1.getJSONObject(j);
				if ((int)f01s.get(i).get("stk01003") != (int)obj.get("stk01003")) continue;
				if (!((String)f01s.get(i).get("stk01004")).equals((String)obj.get("stk01004"))) continue;
				if ((boolean)f01s.get(i).get("stk01005")!=(boolean)obj.get("stk01005")){
					Session ss01 = s_STKF01.getDao().openSession();
					Transaction tx = ss01.beginTransaction(); 
					String hql = "UpDate stock.stkf01 Set stk01005 = "+obj.get("stk01005")+
							" Where stk01003 =  "+obj.get("stk01003")+
							" and stk01004 = '"+obj.get("stk01004")+"'";
					Query q =ss01.createSQLQuery(hql);
					q.executeUpdate();
					tx.commit(); 
					s_STKF01.getDao().closeSession(ss01);
				}
				break;
			}
		}	
	}
	private void opencsv(){
		CSVReader reader;
		try {
			reader = new CSVReader(new InputStreamReader(new FileInputStream(fileData), "BIG5"), ';');
		    String[] line;
		    while((line=reader.readNext())!=null){
		        StringBuilder stb=new StringBuilder(400);
		        for(int i=0;i<line.length;i++){
		        	stb.append(line[i]);
		            stb.append(";");
		        }
		        System.out.println( stb);
		    }		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void javacsv(){
		try {
			
			ServletContext pp = ServletActionContext.getServletContext();
			KC_Pub.kc_copyFile(fileData, new File(pp.getRealPath(fileDataFileName)));
			CsvReader csvfile = new CsvReader(pp.getRealPath(fileDataFileName),',',Charset.forName("BIG5"));
			csvfile.readHeaders();
			while (csvfile.readRecord())
				System.out.println(csvfile.get("ID") + ":" + csvfile.get("Name"));
			csvfile.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	public  String execute()throws Exception{
		if (action.equals("UPLOAD"))    {  upload();	}
		if (action.equals("UPLOADBOX")) {  uploadbox();	}
		if (action.equals("UPLOADSTK")) {  uploadstk();	}
		if (action.equals("REPORT")) 	{  report();	}
		if (action.equals("SETINI")) 	{  setini();	}
		if (action.equals("TYPESAVE")) 	{  typesave();	}
		if (action.equals("OPENCSV")) 	{  opencsv();	}
		if (action.equals("JAVACSV")) 	{  javacsv();	}
		return null;
	}
}
