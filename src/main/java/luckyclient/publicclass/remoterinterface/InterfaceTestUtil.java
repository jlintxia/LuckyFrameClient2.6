package luckyclient.publicclass.remoterinterface;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

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
public class InterfaceTestUtil {
	private static Logger log = LoggerFactory
			.getLogger(InterfaceTestUtil.class);
	private static final ObjectMapper MAP = new ObjectMapper();
	private static final int READ_TIME_OUT = 30 * 1000;
	private static final Map<String, Class<?>> BASE_TYPE = new HashMap<String, Class<?>>();
	static {
		BASE_TYPE.put("long", long.class);
		BASE_TYPE.put("double", double.class);
		BASE_TYPE.put("float", float.class);
		BASE_TYPE.put("bool", boolean.class);
		BASE_TYPE.put("char", char.class);
		BASE_TYPE.put("byte", byte.class);
		BASE_TYPE.put("void", void.class);
		BASE_TYPE.put("short", short.class);
	}

/*	public static Map<String, Object> doTest(InterfaceObject object)
			throws Exception {
		// ��ȡ����������б����������ͣ�����ֵ������ת��InterfaceParamObject����
		InterfaceParamObject[] paramContent = MAP.readValue(object.getParams(),
				InterfaceParamObject[].class);
		Object result = hessian(object.getRemoteUrl(),
				object.getInterfaceClass(), object.getInterfaceMethod(),
				paramContent);

		if (result != null) {
			log.info("���ؽ��������{},����ԭʼ�����{}", result.getClass(),
					result.toString());
			return objectToMap(result);
		}
		log.info("���ؽ��������null");
		return null;

	}

	public static Map<String, Object> objectToMap(Object obj) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>(0);
		String objClass = obj.getClass().getName();
		if (isBaseTypeForArray(objClass)) {
			map.put(objClass, obj.toString());
		} else {
			// Field[] allField = obj.getClass().getDeclaredFields();
			Field[] allField = getAllFields(obj);
			for (Field field : allField) {
				field.setAccessible(true);
				map.put(field.getName(), field.get(obj));
			}

		}
		for (Object key : map.keySet()) {
			System.out.println("KEY:" + key + ", VALUE:" + map.get(key));
		}
		return map;
	}

	private static Field[] getAllFields(Object object) {
		Class<?> clazz = object.getClass();
		List<Field> fieldList = new ArrayList<>();
		while (clazz != null) {
			fieldList.addAll(
					new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
			clazz = clazz.getSuperclass();
		}
		Field[] fields = new Field[fieldList.size()];
		fieldList.toArray(fields);
		return fields;
	}

	private static boolean isBaseTypeForArray(String objClassName) {

		boolean isBase = false;
		if (objClassName != null) {
			isBase = BASE_TYPE.get(objClassName) != null ? true : false;
		}
		return isBase;
	}

	public static String genJsonStr(Object object)
			throws JsonProcessingException {
		return MAP.writeValueAsString(object);
	}

	private static Object hessian(String remoteUrl, String className,
			String methodName, InterfaceParamObject[] paramContent)
			throws Exception {
		HessianProxyFactory factory = new HessianProxyFactory();
		factory.setReadTimeout(READ_TIME_OUT);
		Object interfaceObj = null;
		Class<?> interfaceClassName = null;

		try {
			interfaceClassName = Class.forName(className);
			interfaceObj = factory.create(interfaceClassName, remoteUrl);
			log.info("����MyHessianProxyFactory���ص�hessian�������Ϊ��{}",
					interfaceObj.getClass());
		} catch (Exception ex) {
			log.info("�����ڷ����ȡ�ӿڵ�hesian�������ʱ�������⡣�쳣��Ϣ��{}", ex.getMessage());
		}
		Object[] allRequstParas = new Object[paramContent.length];
		for (int i = 0; i < paramContent.length; i++) {
			InterfaceParamObject paramObject = paramContent[i];
			Class<?> paramClass = null;

			try {
				String paramClassName = paramObject.getClassname();

				paramClass = getBaseTypeClassByName(paramClassName);
				if (paramClass == null) {
					paramClass = Class.forName(paramObject.getClassname());
				}
			} catch (Exception ex) {
				log.info("�����ڷ����ȡ�ӿڵĲ���������ʱ�������⡣�쳣��Ϣ{}", ex.getMessage());
			}
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(
					DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			Object requestParamObject = null;
			if (paramObject.getValue() != null
					&& paramObject.getValue().trim().length() > 0) {
				try {

					requestParamObject = objectMapper
							.readValue(paramObject.getValue(), paramClass);
				} catch (Exception ex) {
					log.info("����Json�ַ�������ת����%s����ʱ�����쳣������Ԥ��ֵΪ%s",
							requestParamObject.getClass(),
							paramObject.getValue());
				}
			} else {
				requestParamObject = paramClass.getInterfaces();
			}
			allRequstParas[i] = requestParamObject;
		}

		if (interfaceObj != null && allRequstParas.length > 0) {
			return getMethod(methodName, interfaceObj, allRequstParas);
		}
		log.info("û��������������ؿն���");
		return null;
	}

	private static Object getMethod(String methodName, Object o,
			Object[] allRequstParas) {
		int totalRequestParam = allRequstParas.length;

		Class<?>[] allRequstParasClass = null;
		// ����
		if (allRequstParas != null) {
			int len = allRequstParas.length;
			allRequstParasClass = new Class[len];
			for (int i = 0; i < len; ++i) {
				allRequstParasClass[i] = allRequstParas[i].getClass();
			}
		}
		Object result = new Object[totalRequestParam];
		Method method = null;
		try {
			// ���ݷ������Լ����������б��ȡ��������
			method = o.getClass().getDeclaredMethod(methodName,
					allRequstParasClass);
		} catch (NoSuchMethodException ex) {
			log.error(String.format("��ȡ�����������Ҳ�����������������Ϊ%s,�쳣��Ϣ:%s",
					o.getClass(), ex.getMessage()));
		} catch (SecurityException ex) {
			log.error(String.format("��ȡ����������������ȫ�쳣����������Ϊ%s,�쳣��Ϣ:%s",
					o.getClass(), ex.getMessage()));
		}
		try {
			result = method.invoke(o, allRequstParas);
		} catch (Exception ex) {
			log.error(String.format("���÷�������ִ�е��÷����쳣����������Ϊ%s,�쳣��Ϣ:%s",
					o.getClass(), ex.getMessage()));
		}
		return result;
	}

	*//**
	 * 1��ת��������������Ϊ��װ����<br>
	 * 2����<br>
	 * 
	 * @param className
	 * @return
	 * @see
	 *//*
	private static Class<?> getBaseTypeClassByName(String className) {
		return BASE_TYPE.get(className);
	}

	private static Boolean isBaseType(String className) {
		Boolean istrue = false;
		for (String key : BASE_TYPE.keySet()) {
			istrue = className.equals(key) ? true : false;
		}
		return istrue;
	}*/
}