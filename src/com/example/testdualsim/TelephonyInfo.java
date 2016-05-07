package com.example.testdualsim;

import java.lang.reflect.Method;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

public class TelephonyInfo {
	private static final String FIELD_getDeviceIdGemini = "getDeviceIdGemini";
	private static final String FIELD_getDeviceId = "getDeviceId";
	private static final String FIELD_getSimStateGemini = "getSimStateGemini";
	private static final String FIELD_getSimState = "getSimState";
	private static final String FIELD_isMultiSimEnabled = "isMultiSimEnabled";
	private static final String FIELD_SCH_I959 = "SCH-I959";
	private static final String FIELD_GT_I9502 = "GT-I9502";
	private static final String FIELD_SCH_N719 = "SCH-N719";
	private static final String FIELD_GT_N7102 = "GT-N7102";
	private static final String FIELD_phone2 = "phone2";
	private static final String FIELD_samsung = "samsung";
	private static final String FIELD_huawei = "huawei";
	private static final String FIELD_getITelephony = "getITelephony";
	private static final String FIELD_getSubscriberId = "getSubscriberId";
	private static final String FIELD_SIMCARD = "SIMCARD";
	private static final String FIELD_subscription = "subscription";

	private static TelephonyInfo mThis;
	private Context mContext;
	private String mImeiSIM1;
	private String mImeiSIM2;
	private boolean mIsSIM1Ready = false;
	private boolean mIsSIM2Ready = false;
	private boolean mIsMultiSimEnabled = false;

	public TelephonyInfo() {
	}

	public static TelephonyInfo getInstance() {
		if (mThis == null) {
			mThis = new TelephonyInfo();
		}

		return mThis;
	}

	public void initConfig(Context context) {
		mContext = context;
		mIsMultiSimEnabled = getMultiSimEnabled();

		TelephonyManager telephonyManager = ((TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE));

		mImeiSIM1 = telephonyManager.getDeviceId();
		mImeiSIM2 = null;

		try {
			mImeiSIM1 = getDeviceIdBySlot(FIELD_getDeviceIdGemini, 0);
			mImeiSIM2 = getDeviceIdBySlot(FIELD_getDeviceIdGemini, 1);
		} catch (GeminiMethodNotFoundException e) {
			try {
				mImeiSIM1 = getDeviceIdBySlot(FIELD_getDeviceId, 0);
				mImeiSIM2 = getDeviceIdBySlot(FIELD_getDeviceId, 1);
			} catch (GeminiMethodNotFoundException e1) {
				// Call here for next manufacturer's predicted method name
				// if you wish
			}
		}

		mIsSIM1Ready = telephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY;
		mIsSIM2Ready = false;

		try {
			mIsSIM1Ready = getSIMStateBySlot(FIELD_getSimStateGemini, 0);
			mIsSIM2Ready = getSIMStateBySlot(FIELD_getSimStateGemini, 1);
		} catch (GeminiMethodNotFoundException e) {
			try {
				mIsSIM1Ready = getSIMStateBySlot(FIELD_getSimState, 0);
				mIsSIM2Ready = getSIMStateBySlot(FIELD_getSimState, 1);
			} catch (GeminiMethodNotFoundException e1) {
			}
		}
	}

	public void releaseData() {
		mThis = null;
	}

	public String getImeiSIM1() {
		return mImeiSIM1;
	}

	/*
	 * public static void setImeiSIM1(String imeiSIM1) { TelephonyInfo.imeiSIM1
	 * = imeiSIM1; }
	 */

	public String getImeiSIM2() {
		return mImeiSIM2;
	}

	/*
	 * public static void setImeiSIM2(String imeiSIM2) { TelephonyInfo.imeiSIM2
	 * = imeiSIM2; }
	 */

	public boolean isSIM1Ready() {
		return mIsSIM1Ready;
	}

	/*
	 * public static void setSIM1Ready(boolean isSIM1Ready) {
	 * TelephonyInfo.isSIM1Ready = isSIM1Ready; }
	 */

	public boolean isSIM2Ready() {
		return mIsSIM2Ready;
	}

	/*
	 * public static void setSIM2Ready(boolean isSIM2Ready) {
	 * TelephonyInfo.isSIM2Ready = isSIM2Ready; }
	 */

	public boolean isMultiSimEnabled() {
		return mIsMultiSimEnabled;
	}

	public boolean isMultiSim() {
		return mIsSIM1Ready && mIsSIM2Ready;
	}

	public boolean hasSimByUsing() { // 有卡在使用？
		return mIsSIM1Ready || mIsSIM2Ready;
	}

	// 获取SIM卡的服务商
	public void getOperator() {
		TelephonyManager telephonyManager = (TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE);

		String ope = telephonyManager.getSimOperator();
		if (ope.equals("46000") || ope.equals("46002")) {
			Log.v("", "中国移动");
		} else if (ope.equals("46001")) {
			Log.v("", "中国联通");
		} else {
			Log.v("", "中国电信");
		}
	}

	// 获取双卡双待SIM卡的服务商
	public void getOperatorBySlot(int slotID) {
		Log.v("", "getOperatorBySlot:" + slotID);
		TelephonyManager telephonyManager = (TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE);
		try {
			Class<?> mLoadClass = Class
					.forName("android.telephony.TelephonyManager");

			Class<?>[] parameter = new Class[1];
			parameter[0] = int.class;
			Method getSimStateGemini = mLoadClass.getMethod(
					"getSimOperatorGemini", parameter);

			Object[] obParameter = new Object[1];
			obParameter[0] = slotID;
			Object ob_phone = getSimStateGemini.invoke(telephonyManager,
					obParameter);

			if (ob_phone != null) {
				String operator = ob_phone.toString();

				if (operator.equals("46000") || operator.equals("46002")) {
					Log.v("", "卡：" + slotID + "中国移动");
				} else if (operator.equals("46001")) {
					Log.v("", "卡：" + slotID + "中国联通");
				} else {
					Log.v("", "卡：" + slotID + "中国电信");
				}
			}

		} catch (Exception e) {
			Log.v("", "getOperatorBySlot:" + slotID + " Exception!");
			e.printStackTrace();
		}
	}

	private String getDeviceIdBySlot(String predictedMethodName, int slotID)
			throws GeminiMethodNotFoundException {

		String imei = null;

		TelephonyManager telephony = (TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE);

		try {

			Class<?> telephonyClass = Class.forName(telephony.getClass()
					.getName());

			Class<?>[] parameter = new Class[1];
			parameter[0] = int.class;
			Method getSimID = telephonyClass.getMethod(predictedMethodName,
					parameter);

			Object[] obParameter = new Object[1];
			obParameter[0] = slotID;
			Object ob_phone = getSimID.invoke(telephony, obParameter);

			if (ob_phone != null) {
				imei = ob_phone.toString();

			}
		} catch (Exception e) {
			throw new GeminiMethodNotFoundException(predictedMethodName);
		}

		return imei;
	}

	private boolean getSIMStateBySlot(String predictedMethodName, int slotID)
			throws GeminiMethodNotFoundException {

		boolean isReady = false;
		TelephonyManager telephony = (TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE);

		try {
			Class<?> telephonyClass = Class.forName(telephony.getClass()
					.getName());

			Class<?>[] parameter = new Class[1];
			parameter[0] = int.class;
			Method getSimStateGemini = telephonyClass.getMethod(
					predictedMethodName, parameter);

			Object[] obParameter = new Object[1];
			obParameter[0] = slotID;
			Object ob_phone = getSimStateGemini.invoke(telephony, obParameter);

			if (ob_phone != null) {
				int simState = Integer.parseInt(ob_phone.toString());
				if (simState == TelephonyManager.SIM_STATE_READY) {
					isReady = true;
				}
			}
		} catch (Exception e) {
			throw new GeminiMethodNotFoundException(predictedMethodName);
		}

		return isReady;
	}

	private boolean getMultiSimEnabled() {
		boolean isEnabled = false;

		TelephonyManager telephony = (TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE);

		try {
			Class<?> telephonyClass = Class.forName(telephony.getClass()
					.getName());

			Method getSimStateGemini = telephonyClass
					.getMethod(FIELD_isMultiSimEnabled);
			Object ob_phone = getSimStateGemini.invoke(telephony);
			if (ob_phone != null)
				isEnabled = (Boolean) ob_phone;
		} catch (Exception e) {
			isEnabled = false;
		}

		return isEnabled;
	}

	private static class GeminiMethodNotFoundException extends Exception {
		private static final long serialVersionUID = -996812356902545308L;

		public GeminiMethodNotFoundException(String info) {
			super(info);
		}
	}
}