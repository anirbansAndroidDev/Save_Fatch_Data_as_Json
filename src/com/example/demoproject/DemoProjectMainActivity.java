package com.example.demoproject;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DemoProjectMainActivity extends Activity {

	EditText name;
	EditText add;
	EditText phon;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo_project_main);
	}

	//On click method
	public void saveData(View v) {
		name = (EditText)findViewById(R.id.editTextName);
		add  = (EditText)findViewById(R.id.editTextAdd);
		phon = (EditText)findViewById(R.id.editTextPhon);

		String valName = name.getText().toString();
		String valAdd  = add.getText().toString();
		String valPhon = phon.getText().toString();

		if(valName.equalsIgnoreCase("")  || valAdd.equalsIgnoreCase("") || valPhon.equalsIgnoreCase("") )
		{
			Toast.makeText( getApplicationContext(),"Please fill up all info.",Toast.LENGTH_LONG).show();
		}
		else
		{
			writeJSON(valName,valAdd,valPhon);
		}
	}



	public void writeJSON(String valName,String valAdd,String valPhon) {
		
		JSONArray Parentobject = null;
		JSONObject object = new JSONObject();
		
		try 
		{
			object.put("name", valName);
			object.put("add", valAdd);
			object.put("phone", valPhon);
			Parentobject = new JSONArray(Arrays.asList(object));
		} 
		catch (JSONException e) 
		{
			e.printStackTrace();
		}
		
		String savedData = Parentobject.toString();
		SaveAsJson(savedData);
	}

	private void SaveAsJson(String savedData) {
		try
		{
//===========================================================================================================
//opening a file and reading it after that storing it as a string
//===========================================================================================================
			File file = getBaseContext().getFileStreamPath("samplefile.json");
			//Checking file existence 
			if(file.exists())
			{
				FileInputStream fIn 	  = openFileInput("samplefile.json");
				StringBuffer storedString = new StringBuffer();
				DataInputStream dataIO    = new DataInputStream(fIn);
				String strLine 			  = null;
				if ((strLine = dataIO.readLine()) != null) 
				{
					storedString.append(strLine);
				}
				String readString = new String(storedString);
				
//===========================================================================================================
//END opening a file and reading it after that storing it as a string
//===========================================================================================================

				//Joining existing json file + newly created json file
				FileOutputStream fOut = openFileOutput("samplefile.json",MODE_WORLD_READABLE);
				OutputStreamWriter osw = new OutputStreamWriter(fOut); 
				String joinOldNew = readString.substring(0, readString.length()-1) +","+ savedData.replace("[", "");
				osw.write(joinOldNew);
				osw.flush();
				osw.close();

				
				ReadJsonFile(joinOldNew);
			}
			else
			{
//===========================================================================================================
// Write the string to the file
//===========================================================================================================
				FileOutputStream fOut = openFileOutput("samplefile.json",MODE_WORLD_READABLE);
				OutputStreamWriter osw = new OutputStreamWriter(fOut); 

				//here savedData is the string which will be  written into that file
				osw.write(savedData);
				osw.flush();
				osw.close();
//===========================================================================================================
// Write the string to the file
//===========================================================================================================

				ReadJsonFile(savedData);
			}
		}
		catch (Throwable t) {
			Log.d("Error on saving", t+"");
		}

	}

	private void ReadJsonFile(String sendData) {
		try
		{
			//sending that string into next intent
			Intent showList = new Intent(this, DataListView.class);
			showList.putExtra("jsonData", sendData);
			startActivity(showList);
		}
		catch (Throwable e) 
		{
			Log.d("Error on saving", e+"");
		}

	}

}
