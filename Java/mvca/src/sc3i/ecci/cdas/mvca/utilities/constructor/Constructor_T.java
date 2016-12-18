package sc3i.ecci.cdas.mvca.utilities.constructor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Constructor_T {

	public Constructor_T() {

	}

	public Object createInstance(String s) {
		Object obj = null;
		try {
			
			Class cls = Class.forName(s);
			Method mth = cls.getDeclaredMethod("getInstance",new Class[0]);
			obj = mth.invoke(null, new Object[0]);
			mth = cls.getDeclaredMethod("getMyName", new Class[0]);
			String str = (String)mth.invoke(obj, new Object[0]);
			System.out.println(str);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			obj = create(s);
		}
		return obj;
	}

	public Object create(String s) {
		Object obj = null;
		try {
			Class cls = Class.forName(s);
			obj = cls.newInstance();
			Method mth = cls.getDeclaredMethod("getMyName",new Class[0]);
			String str = (String)mth.invoke(obj, new Object[0]);
			System.out.println(str);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return obj;
	}

}
