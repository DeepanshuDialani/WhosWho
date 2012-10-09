package dialani.deepanshu.whoswho;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;

import android.support.v4.app.NavUtils;



public class QuestionPage extends Activity 
{


	public final static String EXTRA_MESSAGE = "cdialani.deepanshu.whoswho.MESSAGE";
	public static int index;
	public static long scorevalue;
	public static boolean correctanswer=false;
	public long totalscore;
    public static int count_hints_click[]=new int[4];
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Random rnd=new Random();
		index=rnd.nextInt(5);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //scorevalue=100;
        Intent i=getIntent();
        long t=i.getLongExtra("totalscore",100);
        totalscore=t;
        //totalscore=totalscore+scorevalue;
        setContentView(R.layout.question_page); //loading the main.xml layout file
        Button btn_score=(Button)findViewById(R.id.score);
        btn_score.setText(""+totalscore);
        countdown();
       TextView title=(TextView)findViewById(R.id.title2);
       Typeface tf = Typeface.createFromAsset(getAssets(),"Lobster.otf");
        title.setTypeface(tf);
       //title.setVisibility(View.INVISIBLE);
        count_hints_click[0]=0;
        count_hints_click[1]=0;
        count_hints_click[2]=0;
        correctanswer=false;
        showInstructions();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);//prevents keyboard auto popping 
    }
    
    public CountDownTimer mCountDownTimer;
    public static long timetaken; 
    public void countdown()
    {
        //final Button pass=(Button)findViewById(R.id.pass);
    	//pass.setVisibility(View.GONE);
    	final int red=getResources().getColor(R.color.red);
        final Button mTextField=(Button)findViewById(R.id.countdown);
        mCountDownTimer=new CountDownTimer(121000,1000) {
        	public void onTick(long millisUntilFinished) 
        	{
        		mTextField.setText("02:" +"00");
                if((millisUntilFinished/1000)<60)
                {
	                	if(millisUntilFinished/1000<10)
	                	{
	                		mTextField.setTextColor(red);
	                		mTextField.setText("00:0"+millisUntilFinished / 1000);
	                	}
	                	else
	                		mTextField.setText("00:"+millisUntilFinished / 1000);
                }
                if((millisUntilFinished/1000)>=60 && (millisUntilFinished/1000)<120)
                	{
                		
                		if(((millisUntilFinished/1000)-60)<10)
                			mTextField.setText("01:0"+ ((millisUntilFinished/1000)-60));
                		else
                			mTextField.setText("01:" + ((millisUntilFinished/1000)-60));
                	}
                timetaken=millisUntilFinished/1000;
            }

            public void onFinish() 
            {
            	mTextField.setText("00:00");
            	//pass.setVisibility(View.VISIBLE);
            	//pass.setText("Pass");
            	onSkip();
            }
         }.start();
         if(correctanswer==true && mCountDownTimer != null)
         {	
         		mCountDownTimer.cancel();
         		mCountDownTimer = null;
         }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
    	menu.add(0,1,0,"Pass");
    	return true;
    }
    @Override 
    public boolean onOptionsItemSelected(MenuItem item)
    { 
    	if(item.getItemId()==1)
    	{
    		onSkip();
    		return true; 
    	}
    	else
    		return super.onOptionsItemSelected(item);
    } 
    @Override
    public void onBackPressed() 
    {
    	final Dialog dialog = new Dialog(context);
    	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    	dialog.setContentView(R.layout.exit_dialog);
        TextView answer = (TextView) dialog.findViewById(R.id.answer);
    	final Intent intent = new Intent(this, MainPage.class);
    	Button yes = (Button) dialog.findViewById(R.id.yes);
    	Button no = (Button) dialog.findViewById(R.id.no);
        yes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				isHighScore(totalscore);
				dialog.cancel();
				//intent.putExtra("totalscore",totalscore);
				finish();
				startActivity(intent);
			}
		});
        no.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				dialog.cancel();
			}
		});
        	updateScore3(0);
        	if (!isFinishing())
        	dialog.show();
    }
    private void isHighScore(Long totalscore)
    {
    	SharedPreferences highscores = this.getSharedPreferences("highscores", 0);
    	SharedPreferences dates = this.getSharedPreferences("dates", 0);
    	SharedPreferences.Editor editor = highscores.edit();
    	SharedPreferences.Editor ed = dates.edit();
    	
    	Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c.getTime());
        
    	long[] hs=new long[3];
    	for(int i=2;i>=0;i--)
    	{
    		hs[i]=highscores.getLong("highscore"+i,0);
    		if(totalscore>hs[i])
    		{
    			editor.putLong("highscore"+i, totalscore);
                editor.commit();
                ed.putString("date"+i,formattedDate);
                ed.commit();
                break;
    		}
    	}	
    }

    public static int unusedhints=0;
    public void onSubmitAnswer(View view) 
    {
    	EditText editText = (EditText) findViewById(R.id.answer);
        String message = editText.getText().toString();
        Resources res = getResources();
    	String[] answers= res.getStringArray(R.array.answers);
        Intent intent = new Intent(this, QuestionPage.class); //param2= activity to be started
        if(message.equalsIgnoreCase(answers[index]))
        {
        	correctanswer=true;
        	mCountDownTimer.cancel();
        	showScoreOnCorrect();
        	//Toast.makeText(MyFirstActivity.this,""+timetaken+correctanswer,Toast.LENGTH_LONG).show();
        }
        else
        {
        	Toast.makeText(QuestionPage.this,"Try Again!",Toast.LENGTH_LONG).show();
        }
    }
    public void q_onclick(View view) 
    {
    	int dg=getResources().getColor(R.color.dark_grey);
    	view.setBackgroundColor(dg);
    	Button clicked_button=(Button) view;
    	Resources res = getResources();
    	String[] gender = res.getStringArray(R.array.gender);
    	String[] profession = res.getStringArray(R.array.profession);
    	String[] nationality = res.getStringArray(R.array.nationality);
    	String[] living = res.getStringArray(R.array.living);
    	String[] age = res.getStringArray(R.array.age);
    	//Button button=new Button (this);
    	//button.setText("what?");
    	//View v=getLayout(R.layout.activity_my_first);
    	//R.layout.activity_my_first.addView(button); 
    	switch(view.getId())
    	{
    	case R.id.btn_gender:		if(clicked_button.getText().equals("Gender?"))
    								updateScore(5);
    								clicked_button.setText(gender[index]);
    				    			break;
    	case R.id.btn_profession:   if(clicked_button.getText().equals("Profession?"))
    								updateScore(5);
    								clicked_button.setText(profession[index]);
					    			break;
    	case R.id.btn_nationality:	if(clicked_button.getText().equals("Nationality?"))
    								updateScore(5);
    								clicked_button.setText(nationality[index]);
					    			break;
		case R.id.btn_living: 		if(clicked_button.getText().equals("Living or Dead?"))
									updateScore(5);
									clicked_button.setText(living[index]);
				    				break;
		case R.id.btn_age: 	        if(clicked_button.getText().equals("Age?"))
									updateScore(5);
									clicked_button.setText(age[index]);
									break;
    	}
    	
    }
    final Context context = this;
    public void hint_onclick(View view)
    {
    	final Dialog dialog = new Dialog(context);
    	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    	dialog.setContentView(R.layout.hint_dialog_custom);
        TextView hint = (TextView) dialog.findViewById(R.id.hint);
        TextView hint_title = (TextView) dialog.findViewById(R.id.hint_title);
        Drawable dg=getResources().getDrawable(R.drawable.hint_icon_bulb_clicked);
        ImageButton clicked_button=(ImageButton) view;
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Resources res = getResources();
    	String[] hint1 = res.getStringArray(R.array.hint1);
    	String[] hint2 = res.getStringArray(R.array.hint2);
    	String[] hint3 = res.getStringArray(R.array.hint3);
        dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				
			}
		});
        switch(view.getId()) 
    	{
    	case R.id.hint1:		count_hints_click[0]++;
    							hint_title.setText("Hint 1");
    							clicked_button.setImageDrawable(dg);
    							updateScore2(10,count_hints_click[0]);
    							hint.setText(hint1[index]);
    							break;
    	case R.id.hint2:   		count_hints_click[1]++;
    							hint_title.setText("Hint 2");
    							updateScore2(10,count_hints_click[1]);
    							clicked_button.setImageDrawable(dg);
    							hint.setText(hint2[index]);
    							break;
    	case R.id.hint3:		count_hints_click[2]++;	
    							hint_title.setText("Hint 3");
    							updateScore2(10,count_hints_click[2]);
    							clicked_button.setImageDrawable(dg);
    							hint.setText(hint3[index]);
    							break;
    	}
        if (!isFinishing())
        dialog.show();
        openAllQuestions();
        
    }
    public void openAllQuestions()
    {
    	View gender= findViewById(R.id.btn_gender);
    	View profession= findViewById(R.id.btn_profession);
    	View nationality= findViewById(R.id.btn_nationality);
    	View living= findViewById(R.id.btn_living);
    	View age= findViewById(R.id.btn_age);
    	q_onclick(gender);
    	q_onclick(profession);
    	q_onclick(nationality);
    	q_onclick(living);
    	q_onclick(age);
    }
    
    public void onSkip()
    {
    	final Dialog dialog = new Dialog(context);
    	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    	dialog.setContentView(R.layout.pass_dialog_custom);
        TextView answer = (TextView) dialog.findViewById(R.id.answer);
        Resources res = getResources();
        String[] answers= res.getStringArray(R.array.answers);
    	answer.setText(answers[index]);
    	final Intent intent = new Intent(this, QuestionPage.class);
    	Button dialogButton = (Button) dialog.findViewById(R.id.gotit);
        dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				intent.putExtra("totalscore",totalscore);
				dialog.cancel();
				finish();
				startActivity(intent);
			}
		});
        	updateScore3(0);
        	 if (!isFinishing())
        	dialog.show();
        
    }
    public void updateScore(int t)
    {
    	Button score=(Button)findViewById(R.id.score);
    	//showDeductedScore(t);
    	scorevalue=scorevalue-t;
    	totalscore=totalscore-t;
    	score.setText(""+totalscore);
    }
    public void updateScore2(int t, int count)
    {
    	if(count==1)
    	{	
    		Button score=(Button)findViewById(R.id.score);
    		//showDeductedScore(t);
    		scorevalue=scorevalue-t;
    		totalscore=totalscore-t;
    		score.setText(""+totalscore);
    	}
    }
   
    public void updateScore3(int t)
    {
    	Button score=(Button)findViewById(R.id.score);
    	scorevalue=scorevalue-t;
    	totalscore=totalscore-t;
    }
    public static long finalscore;
    public void showScoreOnCorrect()
    {
    	final Dialog dialog = new Dialog(context);
    	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    	dialog.setContentView(R.layout.score_dialog);
    	Button timer= (Button) dialog.findViewById(R.id.s_timer_score);
    	Button total= (Button) dialog.findViewById(R.id.s_total_score);
    	Button levelscore= (Button) dialog.findViewById(R.id.s_score);
    	TextView score_title = (TextView) dialog.findViewById(R.id.score_title);
    	score_title.setText("Awesome!");
    	timer.setText(""+timetaken);
    	levelscore.setText(""+totalscore);
    	finalscore=timetaken+totalscore;
    	total.setText(""+finalscore);
    	final Intent intent = new Intent(this, QuestionPage.class);
    	Button dialogButton = (Button) dialog.findViewById(R.id.okbutton);
        dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				intent.putExtra("totalscore",finalscore);
				dialog.cancel();
				finish();
				startActivity(intent);
				correctanswer=false;
			}
		});
        if (!isFinishing())
        	dialog.show();
    }
    public void showInstructions()
    {
    	Button btn_gender=(Button)findViewById(R.id.btn_gender);
    	Button btn_profession=(Button)findViewById(R.id.btn_profession);
    	Button btn_nationality=(Button)findViewById(R.id.btn_nationality);
    	Button btn_living=(Button)findViewById(R.id.btn_living);
    	Button btn_age=(Button)findViewById(R.id.btn_age);
    	
        btn_gender.setOnLongClickListener(new OnLongClickListener() { 
            @Override
            public boolean onLongClick(View v) {
            	Toast.makeText(QuestionPage.this,"Click to know the gender.\n-5 from score.",Toast.LENGTH_LONG).show();
                return true;
            }
        });
        btn_profession.setOnLongClickListener(new OnLongClickListener() { 
            @Override
            public boolean onLongClick(View v) {
            	Toast.makeText(QuestionPage.this,"Click to know the profession.\n-5 from score.",Toast.LENGTH_LONG).show();
                return true;
            }
        });
        btn_nationality.setOnLongClickListener(new OnLongClickListener() { 
            @Override
            public boolean onLongClick(View v) {
            	Toast.makeText(QuestionPage.this,"Click to know the nationality.\n-5 from score.",Toast.LENGTH_LONG).show();
                return true;
            }
        });
        btn_living.setOnLongClickListener(new OnLongClickListener() { 
            @Override
            public boolean onLongClick(View v) {
            	Toast.makeText(QuestionPage.this,"Click to know whether the person is living or dead. -5 from score.",Toast.LENGTH_LONG).show();
                return true;
            }
        });
        
        btn_age.setOnLongClickListener(new OnLongClickListener() { 
            @Override
            public boolean onLongClick(View v) {
            	Toast.makeText(QuestionPage.this,"Click to know the present age.\n-5 from score.",Toast.LENGTH_LONG).show();
                return true;
            }
        });
        Button btn_score=(Button)findViewById(R.id.score);
        btn_score.setOnLongClickListener(new OnLongClickListener() { 
            @Override
            public boolean onLongClick(View v) {
            	Toast.makeText(QuestionPage.this,"100 points at the start of a new level.\n-5 for using a question.\n-10 for using a hint.",Toast.LENGTH_LONG).show();
                return true;
            }
        });
        ImageButton btn_hint1=(ImageButton)findViewById(R.id.hint1);
        ImageButton btn_hint2=(ImageButton)findViewById(R.id.hint2);
        ImageButton btn_hint3=(ImageButton)findViewById(R.id.hint3);
        btn_hint1.setOnLongClickListener(new OnLongClickListener() { 
            @Override
            public boolean onLongClick(View v) {
            	Toast.makeText(QuestionPage.this,"Click to know the first hint.\nOpens up all questions.\n-10 from score.",Toast.LENGTH_LONG).show();
                return true;
            }
        });
        btn_hint2.setOnLongClickListener(new OnLongClickListener() { 
            @Override
            public boolean onLongClick(View v) {
            	Toast.makeText(QuestionPage.this,"Click to know the second hint.\nOpens up all questions.\n-10 from score.",Toast.LENGTH_LONG).show();
                return true;
            }
        });
        btn_hint3.setOnLongClickListener(new OnLongClickListener() { 
            @Override
            public boolean onLongClick(View v) {
            	Toast.makeText(QuestionPage.this,"Click to know the third hint.\nOpens up all questions.\n-10 from score.",Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }/*
    public void showDeductedScore(final int t)
    {
    	
    	CountDownTimer mCountDownTimer;
    	final int red=getResources().getColor(R.color.red);
        final TextView title=(TextView)findViewById(R.id.title2);
        mCountDownTimer=new CountDownTimer(1500,1000) {
        	public void onTick(long millisUntilFinished) 
        	{
        		title.setVisibility(View.VISIBLE);
        		title.setTextColor(red);
        		title.setText("-"+t);
            }

            public void onFinish() 
            {
            	title.setVisibility(View.INVISIBLE);
            }
         }.start();
    }*/
}