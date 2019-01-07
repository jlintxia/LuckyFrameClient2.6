package luckyclient.caserun.exwebdriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;

import luckyclient.caserun.exappium.AppTestControl;
import luckyclient.planapi.api.GetServerAPI;
import luckyclient.planapi.entity.PublicCaseParams;

/**
 * =================================================================
 * =================================================================
 * 
 * @author： seagull
 * @date 2017年12月1日 上午9:29:40
 * 
 */
public class BaseWebDrive {

	/**
	 * @param args
	 * @throws IOException
	 * @throws IOException
	 */
	public static Boolean webScreenShot(WebDriver driver,String imgname) {
		Boolean result = false;
		String relativelyPath = System.getProperty("user.dir");

		// 对远程系统进行截图
		driver = new Augmenter().augment(driver);
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(relativelyPath + "\\log\\ScreenShot\\" + imgname + ".png"));
		} catch (IOException e) {
			luckyclient.publicclass.LogUtil.APP.error("截图操作失败，抛出异常请查看日志...", e);
			e.printStackTrace();
		}
		luckyclient.publicclass.LogUtil.APP
				.info("已对当前界面进行截图操作，请前往服务器上查看...【" + relativelyPath + "\\log\\ScreenShot\\web端报错图\\" + imgname + ".png】");
		return result;
		
	
	}
	//自己添加网页截图
	public static Boolean mywebScreenShot(WebDriver driver,String imgname) {
		Boolean result = false;
		String relativelyPath = System.getProperty("user.dir");

		// 对远程系统进行截图
		driver = new Augmenter().augment(driver);
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		
		  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	  	  String a=df.format(new Date());// new Date()为获取当前系统时间
	  	  String str2 = a.replaceAll(" ", "");
	  	  String str3 = str2.replaceAll("-", ""); 
	  	  String str4 = str3.replaceAll(":", ""); 
	  	  System.out.println(str4);
		
		
		try {
			FileUtils.copyFile(scrFile, new File(relativelyPath + "\\log\\ScreenShot\\web端流程图\\"+ imgname + ".png"));
		} catch (IOException e) {
			luckyclient.publicclass.LogUtil.APP.error("截图操作失败，抛出异常请查看日志...", e);
			e.printStackTrace();
		}
		luckyclient.publicclass.LogUtil.APP
				.info("已对当前界面进行截图操作，请前往服务器上查看...【" + relativelyPath + "\\log\\ScreenShot\\" + imgname + ".png】");
		return result;
		
	
	}
}
