package tw.com.prodisc.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;

import javax.mail.internet.MimeUtility;
import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.MILLISECOND;
import net.sf.json.JSONObject;

import com.sun.security.auth.module.NTSystem;

public class KC_Pub {
	public static int kc001=0; // 判斷是否為 Wed 程式
	/**
	 * 判斷字串是否為空值
	 * @param str : 要判斷的字串 
	 * @return true 空值 , false 不是空值
	 */
	static public String kc_FormatNum(String pattern, double value ) {
		    DecimalFormat myFormatter = new DecimalFormat(pattern);
		    return myFormatter.format(value);
	 }	

	public static long kc_Str2Long(String str,long def){
		long ret = 0;
		try {
			 ret = Long.parseLong(str);
		}
		catch(NumberFormatException nfe) {  
			return def;
		}
		return ret;
	}
	public static float kc_Str2Float(String str,float def){
		float ret = 0;
		try {
			 ret = Float.parseFloat(str);
		}
		catch(NumberFormatException nfe) {  
			return def;
		}
		return ret;
	}
	public static double kc_Str2Double(String str,double def){
		if (!isNumeric(str)) return def;
		return Double.parseDouble(str); 
	}
	public static int kc_Str2Int(String str,int def){
		if (!isNumeric(str)) return def;
		return Integer.parseInt(str); 
	}
	public static boolean isNull(String str) {
		return str == null || str.trim().length() == 0;
	}

	/**
	 * 判斷字串是否為 "true" (不分大小寫)
	 * @param s : 要判斷的字串 
	 * @return true "true"  
	 */
	public static boolean isBoolean(String s) {
	    return ((s != null) && (s.equalsIgnoreCase("true")||s.equalsIgnoreCase("on")));
	}
	
	/**
	 * 判斷字串是否為 數字 
	 * @param str : 要判斷的字串 
	 * @return true 為 數字  
	 */
	public static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
	
	/**
	 * 暫停一段時間
	 * @param millis : 毫秒
	 */
	public static void kc_sleep(long millis) {
		try { Thread.sleep ( millis ) ; 
		} catch (InterruptedException ie)
		{}
	}
	
	/**
	 * 取得現在時間
	 * @return 時間
	 */
	public final static Date kc_now() {
		Calendar cal = Calendar.getInstance();
        return cal.getTime();
	}
	public final static Date kc_setdate(int y,int m, int d){
		Calendar c = Calendar.getInstance();  
	    c.set(Calendar.YEAR, y);  			
	    c.set(Calendar.MONTH,m-1);  
	    c.set(Calendar.DATE, d);  
        c.set(Calendar.HOUR_OF_DAY, 0 );
        c.set(Calendar.MINUTE, 0 );
        c.set(Calendar.SECOND, 0 );
        c.set(Calendar.MILLISECOND, 0 );
        return c.getTime();
	}
	/**
	 * 取的今天日期並去掉"時分秒"
	 * @return 日期
	 */
	public final static Date kc_today() {
		Calendar cal = Calendar.getInstance();
        cal.set( HOUR_OF_DAY, 0 );
        cal.set( MINUTE, 0 );
        cal.set( SECOND, 0 );
        cal.set( MILLISECOND, 0 );
        return cal.getTime();
	}
	
	/**
	 * 日期的加減
	 * @param D01   原日期
	 * @param i01  要加的天數
	 * @return 傳回結果"日期"
	 */
	public final static Date kc_addDate(Date D01,int i01) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(D01);
		cal.add(Calendar.DATE,i01);
		return cal.getTime(); 
	}
	
	/**
	 * 時間轉字串
	 * @param fm  如 : "yyyy-MM-dd HH:mm:ss" ;  "yyyy-MM-dd hh:mm:ss"
	 * @param D01  待轉時間
	 * @return 傳回結果"String"
	 */
	public final static String kc_Date2Str(String fm,Date D01) {
		if (D01 == null) return "";
		SimpleDateFormat S01 = new SimpleDateFormat(fm);
		String Ret = S01.format(D01);
		return Ret;
	}
	
	/**
	 * 字串轉時間    Date dt=kc_Str2Date("yyyyMMdd","20120101")
	 * @param fm  時間格式
	 * @param S01  待轉字串
	 * @return 傳回結果"Date"
	 */
	public static Date kc_Str2Date(String fm,String S01) {
		// if (S01.equals("")) return null;
		if (isNull(S01)) return null;
		DateFormat sdf = new SimpleDateFormat(fm);
		try {
			return sdf.parse(S01);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Copy 檔案
	 * ex. kc_copyFile(new File("c:\\file.tmp"),new File("d:\\file.tmp"));
	 * @param in  原始檔案
	 * @param out 目標檔案
	 */
	public static void kc_copyFile(File in, File out){
		try {
			FileInputStream fis = new FileInputStream(in);
		    FileOutputStream fos = new FileOutputStream(out);
	        byte[] buf = new byte[1024];
	        int i = 0;
	        while ((i = fis.read(buf)) != -1) {
	            fos.write(buf, 0, i);
	        }
	        if (fis != null) fis.close();
	        if (fos != null) fos.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	  }
	/**
	 * 檔案下載
	 * @param s1 : server 實際路徑
	 * @param response
	 * @param request
	 * @return true 表成功 , flase 表失敗 
	 */
	public static boolean kc_filedown(String s1,HttpServletResponse response,HttpServletRequest 	 request){
		try {
			File file = new File(s1);
			FileInputStream 	  fis = new FileInputStream(file);
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        byte[] buf = new byte[1024];
	        for (int readNum; (readNum = fis.read(buf)) != -1;) {
	            bos.write(buf, 0, readNum); //no doubt here is 0
	        }
			String nn = file.toString();
		    int sep = nn.lastIndexOf('\\');
			ServletContext      context  = ServletActionContext.getServletContext();
			String              mimetype = context.getMimeType( s1 );
			response.setContentType( (mimetype != null) ? mimetype : "application/octet-stream" );
			response.setContentLength( (int)file.length() );
			String user_agent = request.getHeader("user-agent");
			boolean isInternetExplorer = (user_agent.indexOf("MSIE") > -1);
			if (isInternetExplorer) {
			    response.setHeader("Content-disposition", "attachment; filename=\"" + URLEncoder.encode(nn.substring(sep + 1), "utf-8") + "\"");
			} else {
			    response.setHeader("Content-disposition", "attachment; filename=\"" + MimeUtility.encodeWord(nn.substring(sep + 1)) + "\"");
			}			
			DataOutput output = new DataOutputStream( response.getOutputStream() );
			for( int i = 0; i < bos.toByteArray().length; i++ ) { 
				output.writeByte( bos.toByteArray()[i] );
			}
	        if (fis != null) fis.close();
	        if (bos != null) bos.close();
	        
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * 以 pdf 格式送出
	 * @param bytes    要送的資料
	 * @param response 
	 * @return true 表成功 , flase 表失敗 
	 */
	public static boolean kc_PDFSnd(byte[] bytes,HttpServletResponse  response) {
		try {
			response.setContentType( "application/pdf" );
			DataOutput output = new DataOutputStream( response.getOutputStream() );
			response.setContentLength(bytes.length);
			for( int i = 0; i < bytes.length; i++ ) { 
				output.writeByte( bytes[i] );
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 以 json 格式送出
	 * @param JSON    要送的資料
	 * @param response 
	 * @return true 表成功 , flase 表失敗 
	 */
	public static boolean kc_JSONSnd(String JSON,HttpServletResponse  response) {
		try {
			response.setHeader("Content-type", "text/javascript;charset=UTF-8");
			response.setHeader("Pragma","No-cache");   
			response.setHeader("Cache-Control","no-cache");   
			response.setDateHeader("Expires",   0);
			response.getWriter().write(JSON);
			response.getWriter().flush();   
			response.getWriter().close();			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 以 excel 格式送出
	 * @param bytes    要送的資料
	 * @param response 
	 * @return true 表成功 , flase 表失敗 
	 */
	public static boolean kc_ExcelSnd(byte[] bytes,HttpServletResponse  response) {
		try {
			response.setContentType( "application/vnd.ms-excel" );
			DataOutput output = new DataOutputStream( response.getOutputStream() );
			response.setContentLength(bytes.length);
			for( int i = 0; i < bytes.length; i++ ) { 
				output.writeByte( bytes[i] );
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 判斷 AD 帳號是否合法 
	 * @param id   帳號
	 * @param pwd  密碼
	 * @return "success" : 認証成功 , "no success" : 認証失敗 , "error" : 認証出錯    
	 */
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	public static String kc_ldapisok(String id, String pwd) {
		    String root = "DC=prodisc,DC=com,DC=tw"; //root
		    String strDomain = "PRODISC.COM.TW";
		    String userID = id;
		    String password = pwd;
		    Hashtable env = new Hashtable();
		    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		    env.put(Context.PROVIDER_URL, "ldap://172.16.88.10:389");
		    env.put(Context.SECURITY_PRINCIPAL, userID + "@" + strDomain);
		    env.put(Context.SECURITY_CREDENTIALS, password);
		    env.put(Context.SECURITY_AUTHENTICATION, "SIMPLE");
		    DirContext ctx = null;
		    try {
		      ctx = new InitialDirContext(env);
		      //System.out.println("認証成功");
		      return "success";
		    } catch (javax.naming.AuthenticationException e) {
		      //System.out.println("認証失敗");
		      return "no success";
		    } catch (Exception e) {
		      //System.out.println("認証出錯：");
		      e.printStackTrace();
		      return "error";
		    }
	  }
	
	/**
	 * 取的 AD UserName
	 * @return 傳回結果
	 */
	public static String kc_GetUserName() {
		return new NTSystem().getName();
	  }
	
	@SuppressWarnings("rawtypes")
	private static void kc_setval(Object ff,String methodStr,Class c,Object age){
		// System.out.println(methodStr+":"+c.getName());
    	Method method;
		try {
			method = ff.getClass().getMethod(methodStr, new Class[]{c});
			method.invoke(ff, new Object[]{age});
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		// method.invoke(ff, (age == null) ? null : new Object[]{age});
	}
	
	@SuppressWarnings("rawtypes")
	public static void kc_json2obj(Object ff,String fn,JSONObject obj){
        Method[] methods = ff.getClass().getDeclaredMethods();
        for (Method method : methods) {
        	String n = method.getName();
        	String r = method.getReturnType().getName();
        	Class pt = null;
        	
        	Class[] pts = method.getParameterTypes();
            for (Class c : pts) pt = c;
        	
        	if (!n.substring(0,3).toLowerCase().equals("set")) continue;
        	if (!r.equals("void")) continue;
        	if (pts.length != 1) continue;
        	
    		Iterator i1 = obj.keys();
    		while (i1.hasNext()) {
	            String key = i1.next().toString();
	            if (!key.equals(fn)) continue;
	            JSONObject o1 = (JSONObject) obj.get(key);
	    		Iterator i2 = o1.keys();
	    		// System.out.println(pt.getName());
	    		while (i2.hasNext()) {
		            String k1 = i2.next().toString();
	            	String kk = o1.getString(k1);
	            	
		            if (n.substring(3).toLowerCase().equals(k1.toLowerCase())){
		            	if (pt.getName().equals("java.lang.String")) 	kc_setval(ff,n,pt,kk);
		            	if (pt.getName().equals("int")) 				kc_setval(ff,n,pt,isNumeric(kk)?o1.getInt(k1):0);
		            	if (pt.getName().equals("java.lang.Integer"))	kc_setval(ff,n,pt,isNumeric(kk)?o1.getInt(k1):0);
		            	if (pt.getName().equals("boolean"))				
		            		 kc_setval(ff,n,pt,isBoolean(kk));
		            	if (pt.getName().equals("java.util.Date"))		kc_setval(ff,n,pt,kc_Str2Date("yyyy/MM/dd",o1.getString(k1)));
		            	if (pt.getName().equals("float"))				kc_setval(ff,n,pt,isNumeric(kk)?(float)o1.getDouble(k1):0);
		            	if (pt.getName().equals("double"))				kc_setval(ff,n,pt,isNumeric(kk)?o1.getDouble(k1):0);
		            	if (pt.getName().equals("long"))				kc_setval(ff,n,pt,isNumeric(kk)?o1.getLong(k1):0);
		            	if (pt.getName().equals("byte"))				kc_setval(ff,n,pt,isNumeric(kk)?(byte)o1.getInt(k1):0);
		            	if (pt.getName().equals("short"))				kc_setval(ff,n,pt,isNumeric(kk)?(short)o1.getInt(k1):0);
		            	if (pt.getName().equals("char"))				kc_setval(ff,n,pt,isNumeric(kk)?(char)o1.getInt(k1):0);
		            	if (pt.getName().equals("java.math.BigDecimal"))kc_setval(ff,n,pt,isNumeric(kk)?new BigDecimal(o1.getString(k1)):new BigDecimal("0"));
		            	if (pt.getName().equals("java.math.BigInteger"))kc_setval(ff,n,pt,isNumeric(kk)?new BigInteger(o1.getString(k1)):new BigInteger("0"));
		            }
	    		}
    	    }
    		/*
            System.out.println("Method name        = " + n);
            System.out.println("Method return type = " + r);
            System.out.println("Param type         = " + pt);
            System.out.println("----------------------------------------");
            */
        }
	}
	
	@SuppressWarnings("rawtypes")
	private static Object kc_getval(Object ff,String methodStr,Class c){
		// System.out.println(methodStr+":"+c.getName());
		Object obj = null;
    	Method method;
		try {
			method = ff.getClass().getMethod(methodStr, new Class[]{});
			// String r = method.getReturnType().getName();
			obj = method.invoke(ff, new Object[]{});
			// System.out.println(obj);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
    	return obj;
		// method.invoke(ff, (age == null) ? null : new Object[]{age});
	}
	@SuppressWarnings("rawtypes")
	public static JSONObject kc_obj2json(Object ff,String fn){
		JSONObject obj01=new JSONObject();
		JSONObject obj02=new JSONObject();
		
        Method[] methods = ff.getClass().getDeclaredMethods();
    	// System.out.println(ff.toString());
        for (Method method : methods) {
        	String n = method.getName();
        	String r = method.getReturnType().getName();
        	Class pt = null;
        	
        	Class[] pts = method.getParameterTypes();
            for (Class c : pts) pt = c;
        	
        	if (r.equals("void")) continue;
        	if (pts.length != 0) continue;
        	if (n.substring(0,3).toLowerCase().equals("get")){
    			if (r.equals("java.util.Date")) {
    				Date dt = (Date) kc_getval(ff,n,pt);
    				if (dt != null) 
    					obj02.put(n.substring(3).toLowerCase(),"Date(" + dt.getTime() + "+0200)");
    				else
    					obj02.put(n.substring(3).toLowerCase(),"");
    			}			
    			else	
        		    obj02.put(n.substring(3).toLowerCase(),kc_getval(ff,n,pt));
        	}
        	if (n.substring(0,2).toLowerCase().equals("is")) 
        		obj02.put(n.substring(2).toLowerCase(),kc_getval(ff,n,pt));
        		// !n.substring(0,2).toLowerCase().equals("is")) continue;
        	    // obj02.put(n.substring(3).toLowerCase(),kc_getval(ff,n,pt));
        	// System.out.println(n+":"+kc_getval(ff,n,pt));
        }
        obj01.put(fn , obj02);
        return obj01;
	}
	
	public static void main(String[] args) {
		System.out.println( "kc_Date2Str(yyyy/MM/dd hh:mm:ss,new Date ()): "+kc_Date2Str("yyyy/MM/dd hh:mm:ss",new Date ()));
		System.out.println( "kc_Str2Date(yyyy.MM.dd,kc_Str2Date(yyyyMMdd,20120310)): "+kc_Date2Str("yyyy.MM.dd",kc_Str2Date("yyyyMMdd","20120310")));
		System.out.println(kc_ldapisok("86407","1zzzzzzzzz"));
		System.out.println(kc_ldapisok("svcSQL","SQL2KDumbOracleBetter"));
		System.out.println(kc_ldapisok("t_chierp","t_chiERP"));
		System.out.println(kc_GetUserName());
		System.out.println(isNumeric("0.01"));
		System.out.println(isNumeric(""));
	}

}
