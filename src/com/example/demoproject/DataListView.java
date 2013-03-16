package com.example.demoproject;


import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class DataListView extends ListActivity {
	static final ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
	SimpleAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customer_list);
		
		//For list view
		adapter = new SimpleAdapter(this,list,R.layout.list_customer,
				new String[] {"NAME","ADDRESS", "PHONENO"},
				new int[] {android.R.id.text1}
				);
//===========================================================================================================
//Getting data from first activity
//===========================================================================================================
		Bundle extras = getIntent().getExtras();
		if (extras == null) 
		{
			return;
		}
		String savedData = extras.getString("jsonData");
//===========================================================================================================
//END Getting data from first activity
//===========================================================================================================
		if (savedData != null) 
		{
			try
			{
				String jesonData = savedData;
				try 
				{
					JSONArray jsonArry = new JSONArray(jesonData);
					list.clear();
					
					for (int i = 0; i < jsonArry.length(); i++) 
					{
						JSONObject jsonObject = jsonArry.getJSONObject(i);

						//adding to listview
						HashMap<String,String> temp = new HashMap<String,String>();
						temp.put("NAME",jsonObject.getString("name"));
						list.add(temp);
					}
					setListAdapter(adapter);
				} 
				catch (Exception e) 
				{
					//Toast.makeText( getApplicationContext(),"Error: "+e,Toast.LENGTH_SHORT).show();
				}

			}
			catch (Throwable e) {
				Log.d("Error on saving", e+"");
			}
		} 
	}
}
