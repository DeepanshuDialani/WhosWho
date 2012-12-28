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
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
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
 
public class CatPref extends PreferenceActivity 
{
	
	
        @Override
        protected void onCreate(Bundle savedInstanceState) 
        {
                super.onCreate(savedInstanceState);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                       WindowManager.LayoutParams.FLAG_FULLSCREEN);
                addPreferencesFromResource(R.layout.catpref2);
                savePreferences();
                
                	
       }
       @Override
        public void onBackPressed() 
        {	
    				finish();		
        }
       
        private void savePreferences()
        {
        	final CheckBoxPreference academia = (CheckBoxPreference)findPreference("academia");
            final CheckBoxPreference business = (CheckBoxPreference) findPreference("business");
            final CheckBoxPreference entertainment = (CheckBoxPreference) findPreference("entertainment");
            final CheckBoxPreference politics = (CheckBoxPreference) findPreference("politics");
            final CheckBoxPreference sports = (CheckBoxPreference) findPreference("sports");
            
            SharedPreferences sp = this.getSharedPreferences("optionsPref",0);
            final SharedPreferences.Editor editor = sp.edit();
            
            academia.setOnPreferenceClickListener(new OnPreferenceClickListener() {
             	public boolean onPreferenceClick(Preference preference) {   
             	   if (!academia.isChecked()) 
             	   {
             		   editor.putInt("academia", 0);
             	   }
             	   else
             	   {
             		   editor.putInt("academia", 1);
             	   }
             	  editor.commit();
             	   return true; 
             	}});
            business.setOnPreferenceClickListener(new OnPreferenceClickListener() {
             	public boolean onPreferenceClick(Preference preference) {   
             	   if (!business.isChecked()) 
             	   {
             		   editor.putInt("business", 0);
             	   }
             	   else
             	   {
             		   editor.putInt("business", 1);
             	   }
             	  editor.commit();
             	   return true; 
             	}});
            entertainment.setOnPreferenceClickListener(new OnPreferenceClickListener() {
             	public boolean onPreferenceClick(Preference preference) {   
             	   if (!entertainment.isChecked()) 
             	   {
             		   editor.putInt("entertainment", 0);
             	   }
             	   else
             	   {
             		   editor.putInt("entertainment", 1);
             	   }
             	  editor.commit();
             	   return true; 
             	}});
            politics.setOnPreferenceClickListener(new OnPreferenceClickListener() {
             	public boolean onPreferenceClick(Preference preference) {   
             	   if (!politics.isChecked()) 
             	   {
             		   editor.putInt("politics", 0);
             	   }
             	   else
             	   {
             		   editor.putInt("politics", 1);
             	   }
             	  editor.commit();
             	   return true; 
             	}});
            sports.setOnPreferenceClickListener(new OnPreferenceClickListener() {
             	public boolean onPreferenceClick(Preference preference) {   
             	   if (!sports.isChecked()) 
             	   {
             		   editor.putInt("sports", 0);
             	   }
             	   else
             	   {
             		   editor.putInt("sports", 1);
             	   }
             	  editor.commit();
             	   return true; 
             	}});
            
        }
        
      
}