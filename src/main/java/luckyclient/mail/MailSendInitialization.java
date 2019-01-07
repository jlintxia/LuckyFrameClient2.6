package luckyclient.mail;

import java.util.Properties;

import luckyclient.dblog.LogOperation;

/**
 * =================================================================
 * ����һ�������Ƶ�������������������κ�δ�������ǰ���¶Գ����������޸ĺ�������ҵ��;��Ҳ������Գ�������޸ĺ����κ���ʽ�κ�Ŀ�ĵ��ٷ�����
 * Ϊ���������ߵ��Ͷ��ɹ���LuckyFrame�ؼ���Ȩ��Ϣ�Ͻ��۸�
 * ���κ����ʻ�ӭ��ϵ�������ۡ� QQ:1573584944  seagull1985
 * =================================================================
 *
 * @author�� seagull
 * @date 2018��3��1��
 */
public class MailSendInitialization {

    public static void sendMailInitialization(String subject, String content, String taskid) {
        String[] addresses = LogOperation.getEmailAddress(taskid);
        Properties properties = luckyclient.publicclass.SysConfig.getConfiguration();
        if (addresses != null) {
            luckyclient.publicclass.LogUtil.APP.info("׼�������Խ�������ʼ�֪ͨ�����Եȡ�������");
            //�������Ҫ�������ʼ�
            MailSenderInfo mailInfo = new MailSenderInfo();
            //�������Ҫ�������ʼ�
            SimpleMailSender sms = new SimpleMailSender();
            mailInfo.setMailServerHost(properties.getProperty("mail.smtp.ip"));
            mailInfo.setMailServerPort(properties.getProperty("mail.smtp.port"));
            mailInfo.setSslenable(properties.getProperty("mail.smtp.ssl.enable").equals("true"));
            mailInfo.setValidate(true);
            mailInfo.setUserName(properties.getProperty("mail.smtp.username"));
            //������������
            mailInfo.setPassword(properties.getProperty("mail.smtp.password"));
            mailInfo.setFromAddress(properties.getProperty("mail.smtp.username"));
            //����
            mailInfo.setSubject(subject);
            //����
            mailInfo.setContent(content);
            mailInfo.setToAddresses(addresses);
            //sms.sendHtmlMail(mailInfo);

            StringBuilder stringBuilder = new StringBuilder();
            for (String address : addresses) {
                stringBuilder.append(address).append(";");
            }
            String addressesmail = stringBuilder.toString();
            if (sms.sendHtmlMail(mailInfo)) {
                luckyclient.publicclass.LogUtil.APP.info("��" + addressesmail + "�Ĳ��Խ��֪ͨ�ʼ�������ɣ�");
            } else {
                luckyclient.publicclass.LogUtil.APP.error("��" + addressesmail + "�Ĳ��Խ��֪ͨ�ʼ�����ʧ�ܣ�");
            }
        } else {
            luckyclient.publicclass.LogUtil.APP.info("��ǰ������Ҫ�����ʼ�֪ͨ��");
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
    }

}
