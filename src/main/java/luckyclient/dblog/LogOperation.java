package luckyclient.dblog;

import java.text.SimpleDateFormat;
import java.util.Date;

import luckyclient.publicclass.DBOperation;

/**
 * =================================================================
 * ����һ�������Ƶ�������������������κ�δ�������ǰ���¶Գ����������޸ĺ�������ҵ��;��Ҳ������Գ�������޸ĺ����κ���ʽ�κ�Ŀ�ĵ��ٷ�����
 * Ϊ���������ߵ��Ͷ��ɹ���LuckyFrame�ؼ���Ȩ��Ϣ�Ͻ��۸� ���κ����ʻ�ӭ��ϵ�������ۡ� QQ:1573584944 seagull1985
 * =================================================================
 * 
 * @ClassName: LogOperation
 * @Description: ��־д�����ݿ� @author�� seagull
 * @date 2015��4��15�� ����9:29:40
 * 
 */
public class LogOperation {
	public static DBOperation dbt = DbLink.dbLogLink();
	static int exetype = DbLink.exetype;

	/**
	 * ��������ִ��״̬ casestatus pass:0 fail:1 lock:2 unexecute:4
	 */
	public void addCaseDetail(String taskid, String caseno, String caseversion, String casename, Integer casestatus) {
		if (0 == exetype) {
			int taskidtoint = Integer.parseInt(taskid);
			casename = casename.replace("'", "''");
			// �������ڸ�ʽ
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sql = "Insert into test_casedetail(TASKID,CASENO,CASEVERSION,CASETIME,"
					+ "CASENAME,CASESTATUS) Values (" + taskidtoint + ",'" + caseno + "','" + caseversion + "',"
					+ "str_to_date('" + df.format(new Date()) + "','%Y-%m-%d %T'),'" + casename + "','" + casestatus
					+ "')";
			try {
				dbt.executeSql(sql);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * ��������ִ��״̬ casestatus pass:0 fail:1 lock:2 unexecute:4
	 */
	public void updateCaseDetail(String taskid, String caseno, Integer casestatus) {
		if (0 == exetype) {
			int taskidtoint = Integer.parseInt(taskid);
			// �������ڸ�ʽ
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sql = "update test_casedetail set casestatus = '" + casestatus + "',CASETIME = str_to_date('"
					+ df.format(new Date()) + "','%Y-%m-%d %T')" + " where taskid = " + taskidtoint + " and caseno = '"
					+ caseno + "'";
			try {
				dbt.executeSql(sql);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * ��������ִ����־
	 */
	public void caseLogDetail(String taskid, String caseno, String detail, String loggrade, String step,
			String imgname) {
		if (0 == exetype) {
			if (detail.indexOf("'") > -1) {
				detail = detail.replaceAll("'", "''");
			}
			int taskidtoint = Integer.parseInt(taskid);
			String sqlresult = null;
			try {
				sqlresult = dbt.executeQuery("select id from test_casedetail where taskid = " + taskidtoint
						+ " and caseno = '" + caseno + "' order by id desc");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if (!"".equals(sqlresult) && null != sqlresult) {
				// ȡ�������һ��������ΪCASEID
				int caseid = Integer.parseInt(sqlresult.substring(0, sqlresult.indexOf("%")));
				// �������ڸ�ʽ
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if (detail.length()>5000) {
					 luckyclient.publicclass.LogUtil.APP.info("��־��ϸ����5000�ַ����޷��������ݿ�洢��������־��ϸ��ӡ...");
					 luckyclient.publicclass.LogUtil.APP.info("������"+caseno+"����"+step+"������־����"+loggrade+",��־��ϸ��"+detail+"��...");
					 detail="��־��ϸ����5000�ַ��޷��������ݿ⣬����LOG4J��־�д�ӡ����ǰ���鿴...";
				}
				String sql = "Insert into test_logDetail(LOGTIME,TASKID,CASEID,DETAIL,LOGGRADE,STEP,IMGNAME)  "
						+ "Values (str_to_date('" + df.format(new Date()) + "','%Y-%m-%d %T')," + taskidtoint + ","
						+ caseid + ",'" + detail + "','" + loggrade + "','" + step + "','" + imgname + "')";
				try {
					String re = dbt.executeSql(sql);
					if (re.indexOf("�ɹ�") < 0) {
						throw new Exception("����������" + caseno + "����" + step + "��־�����ݿ��г����쳣������");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
	}

	/**
	 * ���±��������ִ��ͳ�����
	 */
	public static int[] updateTastdetail(String taskid, int casecount) {
		int[] taskcount = null;
		if (0 == exetype) {
			try {
				int id = Integer.parseInt(taskid);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				String casesucsql = dbt.executeQuery(
						"select count(*) from test_casedetail where taskid = " + id + " and casestatus = 0");
				String casefailsql = dbt.executeQuery(
						"select count(*) from test_casedetail where taskid = " + id + " and casestatus = 1");
				String caselocksql = dbt.executeQuery(
						"select count(*) from test_casedetail where taskid = " + id + " and casestatus = 2");
				String casenoexesql = dbt.executeQuery(
						"select count(*) from test_casedetail where taskid = " + id + " and casestatus = 4");

				int casesuc = Integer.parseInt(casesucsql.substring(0, casesucsql.indexOf("%")));
				int casefail = Integer.parseInt(casefailsql.substring(0, casefailsql.indexOf("%")));
				int caselock = Integer.parseInt(caselocksql.substring(0, caselocksql.indexOf("%")));
				int casenoexec = Integer.parseInt(casenoexesql.substring(0, casenoexesql.indexOf("%")));

				if (casecount == 0) {
					casecount = casesuc + casefail + caselock + casenoexec;
				}
				// ���ر�������ִ�����
				taskcount = new int[5];
				taskcount[0] = casecount;
				taskcount[1] = casesuc;
				taskcount[2] = casefail;
				taskcount[3] = caselock;
				taskcount[4] = casenoexec;

				String sql = "update test_TaskExcute set casetotal_count = " + casecount + ",casesucc_count = "
						+ casesuc + ",casefail_count = " + casefail + ",caselock_count = " + caselock
						+ ",casenoexec_count = " + casenoexec + ",finishtime =  str_to_date('" + df.format(new Date())
						+ "','%Y-%m-%d %T'), " + "taskStatus  = 2 where id = " + id;
				dbt.executeSql(sql);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return taskcount;
	}

	/**
	 * ���±��������ִ��״̬
	 */
	public static void updateTastStatus(String taskid, int casecount) {
		if (0 == exetype) {
			try {
				int id = Integer.parseInt(taskid);
				String sql = "update test_TaskExcute set casetotal_count= " + casecount + ",taskStatus  = 1 where id = "
						+ id;

				dbt.executeSql(sql);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * ���±�������ĵ�������ִ����־
	 */
	public static void updateCaseLogDetail(String caseno, String taskid, String detail, String loggrade, String step) {
		try {
			if (detail.indexOf("'") > -1) {
				detail = detail.replaceAll("'", "''");
			}
			int inttaskid = Integer.parseInt(taskid);
			String casesidsql;
			casesidsql = dbt.executeQuery(
					"select id from TEST_CASEDETAIL t where caseno = '" + caseno + "' and taskid = " + inttaskid);
			int casesid = Integer.parseInt(casesidsql.substring(0, casesidsql.indexOf("%")));

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sql = "Insert into test_logDetail(LOGTIME,TASKID,CASEID,DETAIL,LOGGRADE,STEP,IMGNAME)  "
					+ "Values (str_to_date('" + df.format(new Date()) + "','%Y-%m-%d %T')," + inttaskid + "," + casesid
					+ ",'" + detail + "','" + loggrade + "','" + step + "','')";

			String re = dbt.executeSql(sql);
			if (re.indexOf("�ɹ�") < 0) {
				throw new Exception("����������" + caseno + "����" + step + "��־�����ݿ��г����쳣������");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * ɾ����������ָ����������־��ϸ
	 */
	public static void deleteCaseLogDetail(String caseno, String taskid) {
		int inttaskid = Integer.parseInt(taskid);
		String casesidsql;
		try {
			casesidsql = dbt.executeQuery(
					"select id from TEST_CASEDETAIL t where caseno = '" + caseno + "' and taskid = " + inttaskid);
			int casesid = Integer.parseInt(casesidsql.substring(0, casesidsql.lastIndexOf("%")));
			// ɾ��ԭ������־
			dbt.executeSql("delete from TEST_LOGDETAIL where caseid = " + casesid + " and taskid = " + inttaskid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ɾ����������ָ����������ϸ
	 */
	public static void deleteCaseDetail(String caseno, String taskid) {
		int inttaskid = Integer.parseInt(taskid);
		try {
			// ɾ��ԭ��������
			dbt.executeSql("delete from TEST_CASEDETAIL where caseno = '" + caseno + "' and taskid = " + inttaskid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ȡ��ָ������ID�еĲ����ڳɹ�״̬��������д�Լ��汾��
	 */
	public String unSucCaseUpdate(String taskid) {
		int inttaskid = Integer.parseInt(taskid);
		String casesidsql = null;
		try {
			casesidsql = dbt.executeQuery("select caseno,caseversion from TEST_CASEDETAIL t where t.taskid = "
					+ inttaskid + " and t.casestatus <> 0");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return casesidsql;
	}

	/**
	 * ȡ��ָ������ID�������ĵ����Ƿ�Ҫ�����ʼ�״̬���ռ��˵�ַ isSendMail varchar(1) default(0); --0 ������ 1
	 * ���� eMailer varchar(100) ; --�ռ���
	 */

	public static String[] getEmailAddress(String taskid) {
		int inttaskid = Integer.parseInt(taskid);
		String casesidsql = null;
		String[] address = null;
		try {
			casesidsql = dbt.executeQuery(
					"select t.issendmail,t.emailer from TEST_JOBS t where id in (select jobid from TEST_TASKEXCUTE t where t.id = "
							+ inttaskid + ")");
			String status = casesidsql.substring(0, casesidsql.indexOf("%"));
			if ("1".equals(status)) {
				String temp = casesidsql.substring(casesidsql.indexOf("%") + 1, casesidsql.length() - 1);
				// ������һ��;
				if (temp.indexOf(";") > -1 && temp.substring(temp.length() - 1, temp.length()).indexOf(";") > -1) {
					temp = temp.substring(0, temp.length() - 1);
				}
				// �����ַ
				if (temp.indexOf("null") <= -1 && temp.indexOf(";") > -1) {
					address = temp.split(";", -1);
					// һ����ַ
				} else if (temp.indexOf("null") <= -1 && temp.indexOf(";") <= -1) {
					address = new String[1];
					address[0] = temp;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return address;
		}
		return address;
	}

	/**
	 * ȡ��ָ������ID�������ĵ����Ƿ�Ҫ�Զ������Լ���������Ŀ���� isBuilding varchar(1) default(0); --0
	 * ���Զ����� 1 �Զ����� BuildName varchar(100) ; --������Ŀ����
	 */
	public static String[] getBuildName(String taskid) {
		int inttaskid = Integer.parseInt(taskid);
		String casesidsql = null;
		String[] buildname = null;
		try {
			casesidsql = dbt.executeQuery(
					"select t.isbuilding,t.buildname from TEST_JOBS t where id in (select jobid from TEST_TASKEXCUTE t where t.id = "
							+ inttaskid + ")");
			if (null == casesidsql || "".equals(casesidsql)) {
				return buildname;
			}
			String status = casesidsql.substring(0, casesidsql.indexOf("%"));
			if ("1".equals(status)) {
				String temp = casesidsql.substring(casesidsql.indexOf("%") + 1, casesidsql.length() - 1);
				// ������һ��;
				if (temp.indexOf(";") > -1 && temp.substring(temp.length() - 1, temp.length()).indexOf(";") > -1) {
					temp = temp.substring(0, temp.length() - 1);
				}
				// �������
				if (temp.indexOf("null") <= -1 && temp.indexOf(";") > -1) {
					buildname = temp.split(";", -1);
					// һ������
				} else if (temp.indexOf("null") <= -1 && temp.indexOf(";") <= -1) {
					buildname = new String[1];
					buildname[0] = temp;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return buildname;
		}
		return buildname;
	}

	/**
	 * ȡ��ָ������ID�������ĵ����Ƿ�Ҫ�Զ�����TOMCAT isrestart varchar(1) default(0); --0 ���Զ����� 1
	 * �Զ����� restartcomm varchar(200) ; -- ��ʽ��������IP;�������û���;����������;ssh�˿�;Shell����;
	 * ����192.168.222.22;pospsettle;pospsettle;22;cd
	 * /home/pospsettle/tomcat-7.0-7080/bin&&./restart.sh;
	 */

	public static String[] getrestartcomm(String taskid) {
		int inttaskid = Integer.parseInt(taskid);
		String casesidsql = null;
		String[] command = null;
		try {
			casesidsql = dbt.executeQuery(
					"select t.isrestart,t.restartcomm from TEST_JOBS t where id in (select jobid from TEST_TASKEXCUTE t where t.id = "
							+ inttaskid + ")");
			if (null == casesidsql || "".equals(casesidsql)) {
				return command;
			}
			String status = casesidsql.substring(0, casesidsql.indexOf("%"));
			if ("1".equals(status)) {
				String temp = casesidsql.substring(casesidsql.indexOf("%") + 1, casesidsql.length() - 1);
				// ������һ��;
				if (temp.indexOf(";") > -1 && temp.substring(temp.length() - 1, temp.length()).indexOf(";") > -1) {
					temp = temp.substring(0, temp.length() - 1);
				}
				// �������
				if (temp.indexOf("null") <= -1 && temp.indexOf(";") > -1) {
					command = temp.split(";", -1);
					// һ������
				} else if (temp.indexOf("null") <= -1 && temp.indexOf(";") <= -1) {
					command = new String[1];
					command[0] = temp;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return command;
		}
		return command;

	}

	/**
	 * ��ȡ���Լƻ�����
	 */
	public static String getTestPlanName(String taskid) {
		int inttaskid = Integer.parseInt(taskid);
		String testplanname = "NULL";
		try {
			String sql = dbt.executeQuery(
					"select t.testlinkname from TEST_JOBS t where id in (select jobid from TEST_TASKEXCUTE t where t.id = "
							+ inttaskid + ")");
			testplanname = sql.substring(0, sql.lastIndexOf("%"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return testplanname;
		}
		return testplanname;
	}

	/**
	 * ��ȡ�������ʱ��
	 */
	public static String getTestTime(String taskid) {
		int inttaskid = Integer.parseInt(taskid);
		String desTime = "�������ʱ������";
		try {
			String sql = dbt.executeQuery(
					"select date_format(t.createtime,'%Y-%m-%d %T'),date_format(t.finishtime,'%Y-%m-%d %T') from TEST_TASKEXCUTE t where t.id= "
							+ inttaskid);
			String starttime = sql.substring(0, sql.indexOf("%"));
			String finishtime = sql.substring(sql.indexOf("%") + 1, sql.length() - 1);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date start = df.parse(starttime);
			Date finish = df.parse(finishtime);
			long l = finish.getTime() - start.getTime();
			long day = l / (24 * 60 * 60 * 1000);
			long hour = (l / (60 * 60 * 1000) - day * 24);
			long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
			long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
			desTime = "<font color='#2828FF'>" + hour + "</font>Сʱ<font color='#2828FF'>" + min
					+ "</font>��<font color='#2828FF'>" + s + "</font>��";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return desTime;
		}
		return desTime;
	}

	/**
	 * ��ѯwebִ�У����������
	 */
	public static int querydrivertype(String taskid) {
		int taskidtoint = Integer.parseInt(taskid);
		int drivertype = 0;
		try {
			String sqlresult = dbt.executeQuery(
					"select browsertype from test_jobs where id = (select jobid from TEST_TASKEXCUTE where id = "
							+ taskidtoint + ")");
			drivertype = Integer.parseInt(sqlresult.substring(0, sqlresult.lastIndexOf("%")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return drivertype;
		}
		return drivertype;
	}

	/**
	 * ��ѯ����������������־ִ��ʵ�ʽ��
	 */
	public static String getLogDetailTestResult(int taskid, String caseno, int casestatus) {
		String sqlresult = "";
		try {
			sqlresult = dbt.executeQuery(
					"select detail from test_logdetail where logid=(select MIN(logid) from test_logdetail "
							+ "where loggrade='error' and taskid=" + taskid
							+ " and caseid=(select id from test_casedetail where taskid=" + taskid + " and caseno='"
							+ caseno + "' and casestatus='" + casestatus + "'))");
			if (sqlresult.indexOf("���Խ����") <= 0 || sqlresult.indexOf("%") <= 0) {
				return sqlresult;
			}
			sqlresult = sqlresult.substring(sqlresult.indexOf("���Խ����") + 5, sqlresult.lastIndexOf("%"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return sqlresult;
		}
		return sqlresult;
	}

	/**
	 * �����������Ʋ�ѯ����ID
	 */
	public static int getTaskExcuteTaskid(String taskname) {
		String sqlresult = "";
		try {
			sqlresult = dbt.executeQuery("select id from test_taskexcute t where t.taskid='" + taskname + "'");
			sqlresult = sqlresult.substring(0, sqlresult.lastIndexOf("%"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Integer.parseInt(sqlresult);
		}
		return Integer.parseInt(sqlresult);
	}

	/**
	 * ��ѯ����������������־Ԥ�ڽ�� 2017-09-16
	 */
	public static String getLogDetailExpectResult(int taskid, String caseno, int casestatus) {
		String sqlresult = "";
		try {
			sqlresult = dbt.executeQuery(
					"select detail from test_logdetail where logid=(select MIN(logid) from test_logdetail "
							+ "where loggrade='error' and taskid=" + taskid
							+ " and caseid=(select id from test_casedetail where taskid=" + taskid + " and caseno='"
							+ caseno + "' and casestatus='" + casestatus + "'))");
			if (sqlresult.indexOf("Ԥ�ڽ����") <= 0 || sqlresult.indexOf("%") <= 0) {
				return sqlresult;
			}
			sqlresult = sqlresult.substring(sqlresult.indexOf("Ԥ�ڽ����") + 5, sqlresult.lastIndexOf("���Խ����") - 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return sqlresult;
		}
		return sqlresult;
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

	}

}
