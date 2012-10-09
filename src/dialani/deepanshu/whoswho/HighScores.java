package dialani.deepanshu.whoswho;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HighScores extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.highscores_page);
        Long h[]=new Long[3];
        String d[]=new String[3];
        SharedPreferences datepref = this.getSharedPreferences("dates", 0);
        SharedPreferences highscores = this.getSharedPreferences("highscores", 0);
		for(int i=0;i<3;i++)
		{
			h[i]=highscores.getLong("highscore"+i,0);
			d[i]=datepref.getString("date"+i,"0");
		}
		bubblesort(h,d);
		LinearLayout f=(LinearLayout)findViewById(R.id.f);
		LinearLayout s=(LinearLayout)findViewById(R.id.s);
		LinearLayout t=(LinearLayout)findViewById(R.id.t);
		TextView fdate=(TextView)findViewById(R.id.firstdate);
        TextView sdate=(TextView)findViewById(R.id.seconddate);
        TextView tdate=(TextView)findViewById(R.id.thirddate);
	   	 TextView fscore=(TextView)findViewById(R.id.firstscore);
         TextView sscore=(TextView)findViewById(R.id.secondscore);
         TextView tscore=(TextView)findViewById(R.id.thirdscore);
         if(d[2].equals("0"))
        	 f.setVisibility(View.GONE);
         else
         {
        	 fscore.setText(""+h[2]);
        	 fdate.setText(d[2]);
         }
         if(d[1].equals("0"))
        	 s.setVisibility(View.GONE);
         else
         {
        	 sscore.setText(""+h[1]);
        	 sdate.setText(d[1]);
         }
         if(d[0].equals("0"))
        	 t.setVisibility(View.GONE);
         else
         {
        	 tscore.setText(""+h[0]);
        	 tdate.setText(d[0]);
         }
         
         

	}
	public void bubblesort(Long[] h, String[] d)
	{
		int i,j;
		String q;
		Long t;
		for(i=0;i<3;i++)
		{
			for(j=0;j<2;j++)
			{
				if(h[j]>h[j+1])
				{
					t=h[j];
					h[j]=h[j+1];
					h[j+1]=t;
					q=d[j];
					d[j]=d[j+1];
					d[j+1]=q;
				}
			}
		}
	}
    @Override
    public void onBackPressed() 
    {
    			Intent intent=new Intent(this,MainPage.class);
				finish();
				startActivity(intent);
    }
}
