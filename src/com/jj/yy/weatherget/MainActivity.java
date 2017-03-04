package com.jj.yy.weatherget;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	final String KEY_CITY_SET = "key_city_set";
	final String TAG = "Jason";
	final String baseUrl = "http://wthrcdn.etouch.cn/weather_mini?city=";
	ArrayAdapter<String> mArrayAdapter;
	Button mButtonSearch;
	String mCity = "上海";
	String[] mCitysArray;
	EditText mEditText;
	InputMethodManager mInputMethodManager;
	Object mObject = new Object();
	ProgressDialog mProgressDialog = null;
	RelativeLayout mRelativeLayout;
	RequestQueue mRequestQueue;
	SharedPreferences mSharedPreferences;
	Spinner mSpinner;
	boolean mStudy = false;
	TextView mTextViewForecast;
	TextView mTextViewToday;
	final String share_preference = "com.yy.weather";

	private InfoItem parseJson(JSONObject paramJSONObject) {
		InfoItem localInfoItem = new InfoItem();
		try {
			localInfoItem.setDesc(paramJSONObject.getString("desc"));
			localInfoItem.setStatus(paramJSONObject.getString("status"));
			JSONObject localJSONObject1 = paramJSONObject.getJSONObject("data");
			localInfoItem.setWendu(localJSONObject1.getString("wendu"));
			localInfoItem.setGanmao(localJSONObject1.getString("ganmao"));
			localInfoItem.setCity(localJSONObject1.getString("city"));
			JSONArray localJSONArray = localJSONObject1.getJSONArray("forecast");
			int i = localJSONArray.length();
			ArrayList localArrayList = new ArrayList();
			for (int j = 0;; j++) {
				if (j >= i) {
					localInfoItem.setForecastList(localArrayList);
					return localInfoItem;
				}
				JSONObject localJSONObject2 = localJSONArray.getJSONObject(j);
				Forecast localForecast = new Forecast();
				localForecast.setFengxiang(localJSONObject2.getString("fengxiang"));
				localForecast.setFengli(localJSONObject2.getString("fengli"));
				localForecast.setHigh(localJSONObject2.getString("high"));
				localForecast.setType(localJSONObject2.getString("type"));
				localForecast.setLow(localJSONObject2.getString("low"));
				localForecast.setDate(localJSONObject2.getString("date"));
				localArrayList.add(localForecast);
			}
		} catch (JSONException localJSONException) {
			localJSONException.printStackTrace();
		}
		return localInfoItem;
	}

	private void setBgByType(String paramString) {
		if (new GregorianCalendar().get(11) > 17) {
			if (paramString.contains("雨")) {
				this.mRelativeLayout.setBackgroundResource(R.drawable.bg_moderate_rain_night);
			} else if (paramString.contains("云")) {
				this.mRelativeLayout.setBackgroundResource(R.drawable.bg_na);
			} else if (paramString.contains("雾")) {
				this.mRelativeLayout.setBackgroundResource(R.drawable.bg_fog_night);
			} else if (paramString.contains("雪")) {
				this.mRelativeLayout.setBackgroundResource(R.drawable.bg_snow_night);
			} else if (paramString.contains("晴")) {
				this.mRelativeLayout.setBackgroundResource(R.drawable.bg_sunny_night);
			}
		} else {
			if (paramString.contains("雨")) {
				this.mRelativeLayout.setBackgroundResource(R.drawable.bg_moderate_rain_day);
			} else if (paramString.contains("云")) {
				this.mRelativeLayout.setBackgroundResource(R.drawable.bg_na);
			} else if (paramString.contains("雾")) {
				this.mRelativeLayout.setBackgroundResource(R.drawable.bg_fog_day);
			} else if (paramString.contains("雪")) {
				this.mRelativeLayout.setBackgroundResource(R.drawable.blur_bg_snow_day);
			} else if (paramString.contains("晴")) {
				this.mRelativeLayout.setBackgroundResource(R.drawable.bg_sunny_day);
			}
		}
	}

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(2130903040);
		this.mInputMethodManager = ((InputMethodManager) getSystemService("input_method"));
		this.mSharedPreferences = getSharedPreferences("share_preference", 0);
		this.mRelativeLayout = ((RelativeLayout) findViewById(2131361792));
		this.mTextViewToday = ((TextView) findViewById(2131361797));
		this.mTextViewForecast = ((TextView) findViewById(2131361798));
		this.mSpinner = ((Spinner) findViewById(2131361794));
		this.mEditText = ((EditText) findViewById(2131361795));
		this.mEditText.clearFocus();
		this.mButtonSearch = ((Button) findViewById(2131361796));
		this.mButtonSearch.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				String str = MainActivity.this.mEditText.getText().toString().trim();
				if (!"".equals(str))
					synchronized (MainActivity.this.mObject) {
						MainActivity.this.mStudy = true;
						MainActivity.this.requestCity(str);
						return;
					}
				Toast.makeText(MainActivity.this, 2131165189, 0).show();
			}
		});
		this.mCitysArray = study(this.mCity).split("#");
		this.mArrayAdapter = new ArrayAdapter(this, 2130903041, this.mCitysArray);
		this.mSpinner.setAdapter(this.mArrayAdapter);
		this.mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView,
					int paramAnonymousInt, long paramAnonymousLong) {
				synchronized (MainActivity.this.mObject) {
					Log.v("Jason", "setOnItemSelectedListener ");
					if (!MainActivity.this.mStudy)
						MainActivity.this.requestCity(
								MainActivity.this.mSpinner.getItemAtPosition(paramAnonymousInt).toString());
					return;
				}
			}

			public void onNothingSelected(AdapterView<?> paramAnonymousAdapterView) {
			}
		});
		this.mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		this.mRequestQueue.add(setup(this.mCity));
		this.mRequestQueue.start();
	}

	public boolean onCreateOptionsMenu(Menu paramMenu) {
		getMenuInflater().inflate(2131296256, paramMenu);
		return true;
	}

	protected void onResume() {
		super.onResume();
	}

	public void requestCity(String paramString) {
		this.mProgressDialog = ProgressDialog.show(this, "天气", "加载中...");
		Log.v("Jason", "setOnItemSelectedListener city = " + paramString);
		this.mRequestQueue.add(setup(paramString));
	}

	public JsonObjectRequest setup(String paramString) {

		try {
			paramString = URLEncoder.encode(paramString, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		Log.v("Jason", "baseUrl + cityString = " + "http://wthrcdn.etouch.cn/weather_mini?city=" + paramString);

		Response.Listener<JSONObject> listener = new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject arg0) {

				try {
					if (mProgressDialog != null)
						mProgressDialog.dismiss();

//					String str1 = new String(arg0.toString().getBytes("ISO-8859-1"), "utf-8");
					String str1 = arg0.toString();
					Log.d("Jason", "string = " + str1);
					JSONObject localJSONObject = new JSONObject(str1);
					if (!"1000".equals(localJSONObject.getString("status"))) {
						Toast.makeText(MainActivity.this, "show something", 0).show();
						return;
					}

					InfoItem infoItem = parseJson(localJSONObject);
					StringBuilder localStringBuilder1 = new StringBuilder();
					localStringBuilder1.append(infoItem.getCity() + "  ");
					localStringBuilder1.append(infoItem.getWendu() + " ℃\n");
					localStringBuilder1.append("感冒指数：" + infoItem.getGanmao() + "\n");

					mTextViewToday.setText(localStringBuilder1.toString());

					if (mStudy) {
						study(infoItem.getCity());
					}

					ArrayList<Forecast> localArrayList = infoItem.getForecastList();

					StringBuilder localStringBuilder2 = new StringBuilder();

					String todayWeatherType = "";

					for (int i = 0; i < localArrayList.size(); i++) {
						Forecast localForecast = (Forecast) localArrayList.get(i);
						localStringBuilder2.append(localForecast.getDate() + " ");
						localStringBuilder2.append(localForecast.getType() + " \n");
						localStringBuilder2.append("风向: " + localForecast.getFengxiang() + "  ");
						localStringBuilder2.append("风力: " + localForecast.getFengli() + "  \n");
						localStringBuilder2.append(localForecast.getLow() + " / " + localForecast.getHigh());
						localStringBuilder2.append("\n\n");
						if (!"".endsWith(todayWeatherType)) {
							todayWeatherType = localForecast.getType();
						}
					}

					mTextViewForecast.setText(localStringBuilder2);

					setBgByType(todayWeatherType);
					mInputMethodManager.hideSoftInputFromWindow(MainActivity.this.mEditText.getWindowToken(), 0);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		};

		Response.ErrorListener errorListener = new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError arg0) {
				Log.e("Jason", arg0.getMessage(), arg0);
			}
		};

		return new JsonObjectRequest("http://wthrcdn.etouch.cn/weather_mini?city=" + paramString, listener,
				errorListener);

	}

	public String study(String paramString) {

		String str1 = mSharedPreferences.getString("key_city_set", "");

		Log.v("Jason", "study city = " + paramString + " str1 = " + str1);

		StringBuilder localStringBuilder = new StringBuilder();
		String[] arrayOfString = null;
		if ("".equals(str1)) {
			arrayOfString = getResources().getStringArray(R.array.spinnerArray);
		}

		String str2 = localStringBuilder.toString();
		if (!str2.contains(paramString)) {
			str2 = new StringBuilder(String.valueOf(paramString)).append("#").toString();
			SharedPreferences.Editor localEditor = this.mSharedPreferences.edit();
			localEditor.putString("key_city_set", str2);
			localEditor.apply();
			this.mCitysArray = str2.split("#");
			this.mArrayAdapter = new ArrayAdapter(this, R.layout.layout_item, this.mCitysArray);
			this.mSpinner.setAdapter(this.mArrayAdapter);
			this.mSpinner.setSelection(-1 + this.mCitysArray.length);
			this.mTextViewForecast.postDelayed(new Runnable() {
				public void run() {
					MainActivity.this.mStudy = false;
				}
			}, 2000L);
		}

		Log.v("Jason", "study sb.toString() = " + str2);

		for (int j = 0; j < arrayOfString.length; j++) {
			localStringBuilder.append(arrayOfString[j] + "#");
		}

		localStringBuilder.append(str1);

		return localStringBuilder.toString();
	}
}