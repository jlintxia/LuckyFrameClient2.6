package rmi.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

import rmi.model.RunBatchCaseEntity;
import rmi.model.RunCaseEntity;
import rmi.model.RunTaskEntity;

/**
 * =================================================================
 * ����һ�������Ƶ�������������������κ�δ�������ǰ���¶Գ����������޸ĺ�������ҵ��;��Ҳ������Գ�������޸ĺ����κ���ʽ�κ�Ŀ�ĵ��ٷ�����
 * Ϊ���������ߵ��Ͷ��ɹ���LuckyFrame�ؼ���Ȩ��Ϣ�Ͻ��۸�
 * ���κ����ʻ�ӭ��ϵ�������ۡ� QQ:1573584944  seagull1985
 * =================================================================
 * ��ΪԶ�̶�����õĽӿڣ�����̳�Remote��
 * @author�� seagull
 * @date 2017��12��1�� ����9:29:40
 * 
 */
public interface RunService extends Remote {
	/**
	 * ��������
	 * @param task
	 * @param loadpath
	 * @return
	 * @throws RemoteException
	 */
    public String runtask(RunTaskEntity task,String loadpath) throws RemoteException;
    /**
     * ���е�������
     * @param onecase
     * @param loadpath
     * @return
     * @throws RemoteException
     */
    public String runcase(RunCaseEntity onecase,String loadpath) throws RemoteException;
    /**
     * ������������
     * @param batchcase
     * @param loadpath
     * @return
     * @throws RemoteException
     */
    public String runbatchcase(RunBatchCaseEntity batchcase,String loadpath) throws RemoteException;
    /**
     * ȡ�ͻ�����־
     * @param storeName
     * @return
     * @throws RemoteException
     */
    public String getlogdetail(String storeName) throws RemoteException;
    /**
     * ȡ�ͻ��˴���ͼƬ
     * @param imgName
     * @return
     * @throws RemoteException
     */
    public byte[] getlogimg(String imgName) throws RemoteException;
    /**
     * �ϴ�JAR�����ͻ���
     * @param fileContent
     * @param name
     * @param loadpath
     * @return
     * @throws RemoteException
     */
    public String uploadjar(byte[] fileContent,String name,String loadpath) throws RemoteException;
    /**
     * WEB��Զ�̵�������
     * @param sign
     * @param executor
     * @param loadpath
     * @return
     * @throws RemoteException
     */
    public String webdebugcase(String sign,String executor,String loadpath) throws RemoteException;
    /**
     * ���ͻ�������
     * @return
     * @throws RemoteException
     */
    public String getClientStatus() throws RemoteException;
}
