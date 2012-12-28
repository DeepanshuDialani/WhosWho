package dialani.deepanshu.whoswho;
 
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.Toast;
 
public class Options extends PreferenceActivity 
{
	
	
        @Override
        protected void onCreate(Bundle savedInstanceState) 
        {
                super.onCreate(savedInstanceState);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                       WindowManager.LayoutParams.FLAG_FULLSCREEN);
                addPreferencesFromResource(R.layout.options);
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                String selectedLevel = sp.getString("levels","1");
                Log.i("level="+selectedLevel,"level="+selectedLevel);
               
                
        }
        

       @Override
        public void onBackPressed() 
        {
        			Intent intent=new Intent(this,MainPage.class);
    				finish();
    				startActivity(intent);
        }
        
      
}