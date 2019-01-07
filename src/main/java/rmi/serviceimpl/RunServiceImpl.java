package rmi.serviceimpl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import rmi.model.RunBatchCaseEntity;
import rmi.model.RunCaseEntity;
import rmi.model.RunTaskEntity;
import rmi.service.RunService;


/**
 * =================================================================
 * ����һ�������Ƶ�������������������κ�δ�������ǰ���¶Գ����������޸ĺ�������ҵ��;��Ҳ������Գ�������޸ĺ����κ���ʽ�κ�Ŀ�ĵ��ٷ�����
 * Ϊ���������ߵ��Ͷ��ɹ���LuckyFrame�ؼ���Ȩ��Ϣ�Ͻ��۸�
 * ���κ����ʻ�ӭ��ϵ�������ۡ� QQ:1573584944  seagull1985
 * =================================================================
 * ��ΪԶ�̶����ʵ���࣬��̳�UnicastRemoteObject
 * @author�� seagull
 * @date 2017��12��1�� ����9:29:40
 * 
 */
public class RunServiceImpl extends UnicastRemoteObject implements RunService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RunServiceImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String runtask(RunTaskEntity task,String loadpath) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("��������ģʽ���Գ���...������Ŀ��"+task.getProjectname()+"  ����ID��"+task.getTaskid());
		try{
			File file =new File(System.getProperty("user.dir")+loadpath); 	   
			if  (!file .isDirectory())      
			{       
				System.out.println("�ͻ��˲�������׮·�������ڣ����顾"+file.getPath()+"��");
				return "�ͻ��˲�������׮·�������ڣ����顾"+file.getPath()+"��";
			}
			Runtime run = Runtime.getRuntime();
			StringBuffer sb=new StringBuffer();
			sb.append(task.getTaskid()).append(" ");
			sb.append(loadpath);
			run.exec("cmd.exe /k start " + "task.cmd" +" "+ sb.toString(), null,new File(System.getProperty("user.dir")+"\\"));
			
		} catch (Exception e) {		
			e.printStackTrace();
			return "��������ģʽ���Գ����쳣������";
		} 
		return "��������ģʽ���Գ�������";
	}
	
	@Override
	public String runcase(RunCaseEntity onecase,String loadpath) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("����������ģʽ���Գ���...������Ŀ��"+onecase.getProjectname()+"  ����ID��"+onecase.getTaskid());
		System.out.println("����������ţ�"+onecase.getTestCaseExternalId()+"  �����汾��"+onecase.getVersion());
		try{
			File file =new File(System.getProperty("user.dir")+loadpath); 	   
			if  (!file .isDirectory())      
			{   
				System.out.println("�ͻ��˲�������׮·�������ڣ����顾"+file.getPath()+"��");
				return "�ͻ��˲�������׮·�������ڣ����顾"+file.getPath()+"��";
			}
			Runtime run = Runtime.getRuntime();
			StringBuffer sb=new StringBuffer();
			sb.append(onecase.getTaskid()).append(" ");
			sb.append(onecase.getTestCaseExternalId()).append(" ");
			sb.append(onecase.getVersion()).append(" ");
			sb.append(loadpath);
			run.exec("cmd.exe /k start " + "task_onecase.cmd" + " " +sb.toString(), null,new File(System.getProperty("user.dir")+"\\"));			
		} catch (Exception e) {		
			e.printStackTrace();
			return "����������ģʽ���Գ����쳣������";
		} 
		return "����������ģʽ���Գ�������";
	}
	
	@Override
	public String runbatchcase(RunBatchCaseEntity batchcase,String loadpath) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("������������ģʽ���Գ���...������Ŀ��"+batchcase.getProjectname()+"  ����ID��"+batchcase.getTaskid());
		System.out.println("��������������"+batchcase.getBatchcase());
		try{
			File file =new File(System.getProperty("user.dir")+loadpath); 	   
			if  (!file .isDirectory())      
			{    
				System.out.println("�ͻ��˲�������׮·�������ڣ����顾"+file.getPath()+"��");
				return "�ͻ��˲�������׮·�������ڣ����顾"+file.getPath()+"��";
			}
			Runtime run = Runtime.getRuntime();
			StringBuffer sb=new StringBuffer();
			sb.append(batchcase.getTaskid()).append(" ");
			sb.append(batchcase.getBatchcase()).append(" ");
			sb.append(loadpath);
			System.out.println(sb.toString());
			run.exec("cmd.exe /k start " + "task_batch.cmd" + " " +sb.toString(), null,new File(System.getProperty("user.dir")+"\\"));		
		} catch (Exception e) {		
			e.printStackTrace();
			return "������������ģʽ���Գ����쳣������";
		} 
		return "������������ģʽ���Գ�������";
	}
	
	@Override
	public String webdebugcase(String sign,String executor,String loadpath) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Web�˵���������"+sign+" �����ˣ�"+executor);
		try{
			File file =new File(System.getProperty("user.dir")+loadpath); 	   
			if  (!file .isDirectory())      
			{    
				System.out.println("�ͻ��˲�������׮·�������ڣ����顾"+file.getPath()+"��");
				return "�ͻ��˲�������׮·�������ڣ����顾"+file.getPath()+"��";
			}
			Runtime run = Runtime.getRuntime();
			StringBuffer sb=new StringBuffer();
			sb.append(sign).append(" ");
			sb.append(executor).append(" ");
			sb.append(loadpath);
			run.exec("cmd.exe /k start " + "web_debugcase.cmd" + " " +sb.toString(), null,new File(System.getProperty("user.dir")+"\\"));			
		} catch (Exception e) {		
			e.printStackTrace();
			return "����Web����ģʽ���Գ����쳣������";
		} 
		return "����Web����ģʽ���Գ�������";
	}
	
	/**
	 * ��ȡ�ͻ�����־
	 * 
	 * @param request
	 * @param response
	 * @param storeName
	 * @param contentType
	 * @param realName
	 * @throws Exception
	 */
	@Override
	public String getlogdetail(String storeName) throws RemoteException{
		BufferedReader bos = null;
		String ctxPath = System.getProperty("user.dir")+"\\log\\";
		String downLoadPath = ctxPath + storeName;

		String str = "";
		InputStreamReader isr=null;
		try {
			isr = new InputStreamReader(new FileInputStream(downLoadPath), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "��ȡ��־·����������ͻ�����־·���Ƿ����!downLoadPath: "+downLoadPath;
		}
		bos = new BufferedReader(isr);
		StringBuffer sb = new StringBuffer();
		try {
			while ((str = bos.readLine()) != null)
			{
				sb.append(str).append("\n");
			}
			bos.close();
			System.out.println("����˶�ȡ������־�ɹ�!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "�ͻ���תBufferedReaderʧ�ܣ�����ԭ��";
		}
		return sb.toString();
	}
	
	/**
	 * ��ȡ�ͻ��˽�ͼ
	 * 
	 * @param request
	 * @param response
	 * @param storeName
	 * @param contentType
	 * @param realName
	 * @throws Exception
	 */
	@Override
	public byte[] getlogimg(String imgName) throws RemoteException{
		String ctxPath = System.getProperty("user.dir")+"\\log\\ScreenShot\\";
		String downLoadPath = ctxPath+imgName;
        byte[] b = null;
        try {
            File file = new File(downLoadPath);
            b = new byte[(int) file.length()];
            BufferedInputStream is = new BufferedInputStream(new FileInputStream(file));
            is.read(b);
            is.close();
            System.out.println("����˻�ȡ����ͼƬ��"+downLoadPath);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return b;
	}
	
	/**
	 * �ϴ�JAR��
	 * 
	 * @param request
	 * @param response
	 * @param storeName
	 * @param contentType
	 * @param realName
	 * @throws Exception
	 */
	@Override
	public String uploadjar(byte[] fileContent,String name,String loadpath) throws RemoteException{
		String path = System.getProperty("user.dir")+loadpath;
		if  (!new File(path) .isDirectory())      
		{    
			System.out.println("�ͻ��˲�������׮·�������ڣ����顾"+path+"��");
			return "�ͻ��˲�������׮·�������ڣ����顾"+path+"��";
		}
		String pathName = path +"\\"+ name;
		File file = new File(pathName);
        try { 
            if (file.exists()){
            	file.deleteOnExit();
            }
            file.createNewFile();
            BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(file));
            os.write(fileContent);
            os.flush();
            os.close();
            System.out.println("������ϴ�JAR��("+name+")�����ؿͻ���libĿ¼�ɹ�!");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "�ͻ���δ�ҵ���ȷ·�����ļ����ϴ�ʧ�ܣ�";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "�ͻ���IOException";
        }
         return "�ϴ�"+name+"���ͻ��˳ɹ���";
	}
	
	@Override
	public String getClientStatus() throws RemoteException{
		return "success";
	}
	
	public static void main(String[] args) throws RemoteException {
	}
}
