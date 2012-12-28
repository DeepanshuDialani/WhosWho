package dialani.deepanshu.whoswho;
import java.util.Arrays;
import java.util.Random;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainPage extends Activity 
{
	public static int total=0;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.congratulations);
        TextView title=(TextView)findViewById(R.id.title);
        Typeface tf = Typeface.createFromAsset(getAssets(),"Lobster.otf");
        title.setTypeface(tf);
        sortHighScores();
        
    	    /*SharedPreferences sharedPreferences = this.getSharedPreferences("optionsPref",0);
    	    Toast.makeText(MainPage.this, "academia-"+sharedPreferences.getInt("academia", 1), Toast.LENGTH_SHORT).show();
    	    Toast.makeText(MainPage.this, "business-"+sharedPreferences.getInt("business", 1), Toast.LENGTH_SHORT).show();
    	    Toast.makeText(MainPage.this, "entertainment-"+sharedPreferences.getInt("entertainment", 1), Toast.LENGTH_SHORT).show();
    	    Toast.makeText(MainPage.this, "politics-"+sharedPreferences.getInt("politics", 1), Toast.LENGTH_SHORT).show();
    	    Toast.makeText(MainPage.this, "sports-"+sharedPreferences.getInt("sports", 1), Toast.LENGTH_SHORT).show();
			*/
	}
	
	private long h[]=new long[3];
	private String d[]=new String[3];
	public void startGame(View view)
	{
		Intent intent = new Intent(this, QuestionPage.class);
		intent.putExtra("totalscore",total);
		finish();
		startActivity(intent);
	}
	final Context context = this;
	public void showAbout(View view)
	{
		final Dialog dialog = new Dialog(context);
    	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    	dialog.setContentView(R.layout.pass_dialog_custom);
    	TextView answer = (TextView) dialog.findViewById(R.id.answer);
    	TextView hint_title = (TextView) dialog.findViewById(R.id.hint_title);
    	Button okaybutton = (Button) dialog.findViewById(R.id.gotit);
    	okaybutton.setText("Okay");
    	hint_title.setText("Instructions");
    	answer.setText("Identify the famous personalities. Use as few questions and hints as possible to maximize your score. Long press a button to know more, while in the game.");
    	Button dialogButton = (Button) dialog.findViewById(R.id.gotit);
    	final Intent intent = new Intent(this, MainPage.class);
        dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.cancel();
				//finish();
				//startActivity(intent);
			}
		});
        if (!isFinishing())
        	dialog.show();
	}
	public void showHighScores(View view)
	{
		Intent intent = new Intent(this, HighScores.class);
		SharedPreferences highscores = this.getSharedPreferences("highscores", 0);
		//intent.putExtra("hs",arr);
		finish();
		startActivity(intent);
	}
	public void showOptions(View view)
	{
		Intent intent = new Intent(this, Options.class);
		finish();
		startActivity(intent);
	}
	public void sortHighScores()
	{
		SharedPreferences highscores = this.getSharedPreferences("highscores", 0);
		SharedPreferences datepref = this.getSharedPreferences("dates", 0);
		for(int i=0;i<3;i++)
		{
			h[i]=highscores.getLong("highscore"+i,0);
			d[i]=datepref.getString("date"+i,"0");
		}
    	SharedPreferences.Editor editor = highscores.edit();
    	SharedPreferences.Editor ed = datepref.edit();
    	bubblesort();
    	int i,j=2;
    	for(i=0;i<3;i++)
    	{
    		editor.putLong("highscore"+i, h[j]);
        	editor.commit();
        	ed.putString("date"+i,d[j]);
            ed.commit();
        	j--;
    	}
    	
	}
	public void bubblesort()
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
	
}