package com.eotu.dualsim.common;

import android.os.Bundle;
import android.os.IInterface;
import java.util.List;

interface ITelephony {
	void answerRingingCall();
	
	void call(String paramString);

	void cancelMissedCallsNotification();

	void dial(String paramString);

	int disableApnType(String paramString);

	boolean disableDataConnectivity();

	void disableLocationUpdates();

	int enableApnType(String paramString);

	boolean enableDataConnectivity();

	void enableLocationUpdates();

	boolean endCall();

	int getActivePhoneType();

	int getCallState();

	int getCdmaEriIconIndex();

	int getCdmaEriIconMode();

	String getCdmaEriText();

	boolean getCdmaNeedsProvisioning();

	Bundle getCellLocation();

	int getDataActivity();

	int getDataState();

	int getNetworkType();

	int getVoiceMessageCount();

	boolean handlePinMmi(String paramString);

	boolean hasIccCard();

	boolean isDataConnectivityPossible();

	boolean isIdle();

	boolean isOffhook();

	boolean isRadioOn();

	boolean isRinging();

	boolean isSimPinEnabled();

	boolean setRadio(boolean paramBoolean);

	boolean showCallScreen();

	boolean showCallScreenWithDialpad(boolean paramBoolean);

	void silenceRinger();

	boolean supplyPin(String paramString);

	void toggleRadioOnOff();

	void updateServiceLocation();
}