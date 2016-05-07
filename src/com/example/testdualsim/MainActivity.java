package com.example.testdualsim;

import java.lang.reflect.Method;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		printTelephonyManagerMethodNamesForThisDevice(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public static void printTelephonyManagerMethodNamesForThisDevice(
			Context context) {

		TelephonyManager telephony = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		Class<?> telephonyClass;
		try {
			telephonyClass = Class.forName(telephony.getClass().getName());
			Method[] methods = telephonyClass.getMethods();
			for (int idx = 0; idx < methods.length; idx++) {

				Log.i("",
						"\n" + methods[idx] + " declared by "
								+ methods[idx].getDeclaringClass());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
