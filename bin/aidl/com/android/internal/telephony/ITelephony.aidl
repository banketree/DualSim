/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.internal.telephony;

import android.os.Bundle;
import java.util.List;
import android.telephony.NeighboringCellInfo;
import android.telephony.CellInfo;

/**
 * 电话交互接口
 * {@hide}
 */
interface ITelephony {

    /**
     * 拨打电话（不显示界面）
     */
    void dial(String number);

    /**
     * 呼叫指定的号码
     */
    void call(String callingPackage, String number);

    /**
     * 切换 3G和LTE
     */
    void toggleLTE(boolean on);

    /**
     * 如果目前正在进行的呼叫，显示呼叫屏幕
     */
    boolean showCallScreen();

    /**
     * 指定DTMF拨号盘 是否 应该是最初时可见InCallScreen 出现。 
     */
    boolean showCallScreenWithDialpad(boolean showDialpad);

    /**
     * 结束通话
     *
     * @return whether it hung up
     */
    boolean endCall();

    /**
     * 回答当前振铃呼叫
     */
    void answerRingingCall();

    /**
     * 关闭铃声，如果来电正在响铃
     */
    void silenceRinger();

    /**
     * 是否手机正处于摘机
     */
    boolean isOffhook();

    /**
     * 是否正在响铃
     * @return true if the phone state is RINGING.
     */
    boolean isRinging();

    /**
     * 是否空闲
     * @return true if the phone state is IDLE.
     */
    boolean isIdle();

    /**
     * 请检查是否收音机打开与否
     * @return returns true if the radio is on.
     */
    boolean isRadioOn();

    /**
     * 检查是否启用SIM卡PIN码锁
     * @return true if the SIM pin lock is enabled.
     */
    boolean isSimPinEnabled();

    /**
     * 取消未接来电通知
     */
    void cancelMissedCallsNotification();

    /**
     * 提供一个引脚来解锁SIM卡
     * @param pin The pin to check.
     * @return whether the operation was a success.
     */
    boolean supplyPin(String pin);

    /**
     * 提供一个引脚来解锁SIM卡
     * @param pin The pin to check.
     * @return Phone.PIN_RESULT_SUCCESS on success. Otherwise error code
     */
    int supplyPinReportResult(String pin);

    /**
     * 供应PUK来解锁SIM卡和SIM卡设置引脚新的引脚
     * @param puk The puk to check
     *        pin The pin to check.
     * @return Phone.PIN_RESULT_SUCCESS on success. Otherwise error code
     */
    int supplyPukReportResult(String puk, String pin);

    /**
     * Gets the number of attempts remaining for PIN1/PUK1 unlock.
     */
    int getIccPin1RetryCount();

    /**
     * 供应PUK来解锁SIM卡和SIM卡设置引脚新的引脚
     * @param puk The puk to check.
     *        pin The new pin to be set in SIM
     * @return whether the operation was a success.
     */
    boolean supplyPuk(String puk, String pin);

    /**
     * 处理的PIN MMI命令（PIN/PIN2/PUK/PUK2）
     *
     * @param dialString the MMI command to be executed.
     * @return true if MMI command is executed.
     */
    boolean handlePinMmi(String dialString);

    /**
     *打开或关闭无线电
     */
    void toggleRadioOnOff();

    /**
     * 把收音机开或关
     */
    boolean setRadio(boolean turnOn);

    /**
     * 把收音机打开或关闭无条件
     */
    boolean setRadioPower(boolean turnOn);

    /**
     * 请求状态更新位置信息服务
     */
    void updateServiceLocation();

    /**
     * 使位置更新通知
     */
    void enableLocationUpdates();

    /**
     * 禁用位置更新通知
     */
    void disableLocationUpdates();

    /**
     *使一个特定的APN型
     */
    int enableApnType(String type);

    /**
     *禁用一个特定的APN型。
     */
    int disableApnType(String type);

    /**
     * 允许移动数据连接
     */
    boolean enableDataConnectivity();

    /**
     * 不允许移动数据连接
     */
    boolean disableDataConnectivity();

    /**
     * 报告数据是否连接是可能的
     */
    boolean isDataConnectivityPossible();

    Bundle getCellLocation();

    /**
     * 返回邻近小区信息的装置
     */
    List<NeighboringCellInfo> getNeighboringCellInfo(String callingPkg);

     int getCallState();//返回呼叫状态
     int getDataActivity();
     int getDataState();

    /**
     * 返回当前主动电话类型.
     * Returns TelephonyManager.PHONE_TYPE_CDMA if RILConstants.CDMA_PHONE
     * and TelephonyManager.PHONE_TYPE_GSM if RILConstants.GSM_PHONE
     */
    int getActivePhoneType();

    /**
     * Returns the CDMA ERI icon index to display
     */
    int getCdmaEriIconIndex();

    /**
     * Returns the CDMA ERI icon mode,
     * 0 - ON
     * 1 - FLASHING
     */
    int getCdmaEriIconMode();

    /**
     * Returns the CDMA ERI text,
     */
    String getCdmaEriText();

    /**
     * Returns true if OTA service provisioning needs to run.
     * Only relevant on some technologies, others will always
     * return false.
     */
    boolean needsOtaServiceProvisioning();

    /**
      * Returns the unread count of voicemails
      */
    int getVoiceMessageCount();

    /**
      * Returns the network type for data transmission
      */
    int getNetworkType();

    /**
      * Returns the network type for data transmission
      */
    int getDataNetworkType();

    /**
      * Returns the network type for voice
      */
    int getVoiceNetworkType();

    /**
     * Return true if an ICC card is present
     */
    boolean hasIccCard();

    /**
     * Return if the current radio is LTE on CDMA. This
     * is a tri-state return value as for a period of time
     * the mode may be unknown.
     *
     * @return {@link Phone#LTE_ON_CDMA_UNKNOWN}, {@link Phone#LTE_ON_CDMA_FALSE}
     * or {@link PHone#LTE_ON_CDMA_TRUE}
     */
    int getLteOnCdmaMode();

    /**
     * Returns the all observed cell information of the device.
     */
    List<CellInfo> getAllCellInfo();

    /**
     * Sets minimum time in milli-seconds between onCellInfoChanged
     */
    void setCellInfoListRate(int rateInMillis);

    int getLteOnGsmMode();
}

