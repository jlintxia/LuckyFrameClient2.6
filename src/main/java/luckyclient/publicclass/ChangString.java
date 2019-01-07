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
            //@@����ע��@����������
            int varcount = counter(str, "@") - counter(str, "@@") * 2;

            //������ڴ��Σ����д���
            if (varcount > 0) {
                luckyclient.publicclass.LogUtil.APP.info("��" + changname + "��" + str + "�����ҵ�" + varcount + "�����滻����");
                int changcount = 0;
                //�Ӳ����б��в���ƥ�����
                for (Map.Entry<String, String> entry : variable.entrySet()) {
                    if (str.contains("@" + entry.getKey())) {
                        if (str.contains("@@" + entry.getKey())) {
                            str = str.replace("@@" + entry.getKey(), "////CHANG////");
                        }
                        //�����滻�ַ����д���\"����\'�ᵼ��\��ʧ������
                        //entry.setValue(entry.getValue().replaceAll("\\\\\"", "\\&quot;"));
                        //entry.setValue(entry.getValue().replaceAll("\\\\\'", "\\\\&#39;"));
                        int viewcount = counter(str, "@" + entry.getKey());
                        str = str.replace("@" + entry.getKey(), entry.getValue());
                        luckyclient.publicclass.LogUtil.APP.info("��" + changname + "���ñ�����@" + entry.getKey() + "���滻��ֵ��" + entry.getValue() + "��");
                        str = str.replace("////CHANG////", "@@" + entry.getKey());
                        changcount = changcount + viewcount;
                    }
                }

                if (varcount != changcount) {
                    luckyclient.publicclass.LogUtil.APP.error(changname + "�����ñ���δ�ڲ��������ҵ������飡��������" + str + "��");
                }
            }
            str = str.replace("@@", "@");
            //�����ָ��ַ����д���\"����\'�ᵼ��\��ʧ������
            //str = str.replaceAll("\\&quot;", "\\\\\"");
            //str = str.replaceAll("\\&#39;", "\\\\\'");
            
            //����������
            if (str.contains("#sjs#")) {
        		  SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//�������ڸ�ʽ
        	  	  String a=df.format(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
        	  	  String b =str.replace("#sjs#", a);
        	  	  str= b;
        	}
            if (str.contains("#sjs8#")) {
      		  SimpleDateFormat df = new SimpleDateFormat("MMddHHmm");//�������ڸ�ʽ
      	  	  String a=df.format(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
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
