package luckyclient.caserun.exwebdriver;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * =================================================================
 * ����һ�������Ƶ�������������������κ�δ�������ǰ���¶Գ����������޸ĺ�������ҵ��;��Ҳ������Գ�������޸ĺ����κ���ʽ�κ�Ŀ�ĵ��ٷ�����
 * Ϊ���������ߵ��Ͷ��ɹ���LuckyFrame�ؼ���Ȩ��Ϣ�Ͻ��۸�
 * ���κ����ʻ�ӭ��ϵ�������ۡ� QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author�� seagull
 * @date 2017��12��1�� ����9:29:40
 * 
 */
public class WebDriverInitialization{
	/**
	 * @param args
	 * ��ʼ��WebDriver
	 * @throws IOException 
	 */
	public static WebDriver setWebDriverForTask(int drivertype) throws WebDriverException,IOException{
		// ����Ϊ��
		File directory = new File("");
/*		System.setProperty("webdriver.ie.driver",directory.getCanonicalPath()+"\\IEDriverServer.exe");
        DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
        ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
        WebDriver webDriver = new InternetExplorerDriver(ieCapabilities);*/
		WebDriver webDriver = null;

		if(drivertype==0){
			System.setProperty("webdriver.ie.driver",directory.getCanonicalPath()+"\\IEDriverServer.exe");
			webDriver = new InternetExplorerDriver();
		}else if(drivertype==1){
			System.setProperty("webdriver.gecko.driver",directory.getCanonicalPath()+"\\geckodriver.exe");
			webDriver = new FirefoxDriver();
		}else if(drivertype==2){
			System.setProperty("webdriver.chrome.driver",directory.getCanonicalPath()+"\\chromedriver.exe");
			ChromeOptions option = new ChromeOptions();
			option.addArguments("disable-infobars");
			webDriver = new ChromeDriver(option);
		}else if(drivertype==3){
			System.setProperty("webdriver.edge.driver",directory.getCanonicalPath()+"\\MicrosoftWebDriver.exe");
			webDriver = new EdgeDriver();
		}else{
			System.setProperty("webdriver.ie.driver",directory.getCanonicalPath()+"\\IEDriverServer.exe");
			webDriver = new InternetExplorerDriver();
		}
		
		webDriver.manage().window().maximize();
		//����ҳ��������ʱ��30��
		webDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		//����Ԫ�س������ʱ��30��  
		webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
        return webDriver;
	}

	/**
	 * ��ʼ��WebDriver
	 * @return
	 * @throws IOException
	 */
	public static WebDriver setWebDriverForLocal() throws IOException{
		File directory = new File("");
		System.setProperty("webdriver.ie.driver",directory.getCanonicalPath()+"\\IEDriverServer.exe");
		WebDriver webDriver = new InternetExplorerDriver();
		webDriver.manage().window().maximize();
		//����ҳ��������ʱ��30��
		webDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		//����Ԫ�س������ʱ��30��  
		webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);  
        return webDriver;
	}
	
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub

	}

}
