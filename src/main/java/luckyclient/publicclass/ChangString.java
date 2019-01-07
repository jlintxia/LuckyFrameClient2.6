package luckyclient.publicclass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

/**
 *
 *
 **/
public class ChangString {

    public static String changparams(String str, Map<String, String> variable, String changname) {
        try {
            if (null == str) {
                return null;
            }
            str = str.replace("&quot;", "\"");
            str = str.replace("&#39;", "\'");
            //@@用来注释@的引用作用
            int varcount = counter(str, "@") - counter(str, "@@") * 2;

            //如果存在传参，进行处理
            if (varcount > 0) {
                luckyclient.publicclass.LogUtil.APP.info("在" + changname + "【" + str + "】中找到" + varcount + "个可替换参数");
                int changcount = 0;
                //从参数列表中查找匹配变量
                for (Map.Entry<String, String> entry : variable.entrySet()) {
                    if (str.contains("@" + entry.getKey())) {
                        if (str.contains("@@" + entry.getKey())) {
                            str = str.replace("@@" + entry.getKey(), "////CHANG////");
                        }
                        //用来替换字符串中带了\"或是\'会导致\消失的问题
                        //entry.setValue(entry.getValue().replaceAll("\\\\\"", "\\&quot;"));
                        //entry.setValue(entry.getValue().replaceAll("\\\\\'", "\\\\&#39;"));
                        int viewcount = counter(str, "@" + entry.getKey());
                        str = str.replace("@" + entry.getKey(), entry.getValue());
                        luckyclient.publicclass.LogUtil.APP.info("将" + changname + "引用变量【@" + entry.getKey() + "】替换成值【" + entry.getValue() + "】");
                        str = str.replace("////CHANG////", "@@" + entry.getKey());
                        changcount = changcount + viewcount;
                    }
                }

                if (varcount != changcount) {
                    luckyclient.publicclass.LogUtil.APP.error(changname + "有引用变量未在参数列中找到，请检查！处理结果【" + str + "】");
                }
            }
            str = str.replace("@@", "@");
            //用来恢复字符串中带了\"或是\'会导致\消失的问题
            //str = str.replaceAll("\\&quot;", "\\\\\"");
            //str = str.replaceAll("\\&#39;", "\\\\\'");
            
            //输入框随机数
            if (str.contains("#sjs#")) {
        		  SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        	  	  String a=df.format(new Date());// new Date()为获取当前系统时间
        	  	  String b =str.replace("#sjs#", a);
        	  	  str= b;
        	}
            if (str.contains("#sjs8#")) {
      		  SimpleDateFormat df = new SimpleDateFormat("MMddHHmm");//设置日期格式
      	  	  String a=df.format(new Date());// new Date()为获取当前系统时间
      	  	  String b =str.replace("#sjs8#", a);
      	  	  str= b;
      	}
            
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static int counter(String str1, String str2) {
        int total = 0;
        for (String tmp = str1; tmp != null && tmp.length() >= str2.length(); ) {
            if (tmp.indexOf(str2) == 0) {
                total++;
                tmp = tmp.substring(str2.length());
            } else {
                tmp = tmp.substring(1);
            }
        }
        return total;
    }

    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public static void main(String[] args) {

    }

}
