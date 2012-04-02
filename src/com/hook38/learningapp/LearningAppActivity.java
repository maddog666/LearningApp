package com.hook38.learningapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LearningAppActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        this.setFonts();
        this.initiate();
           
    }
    
    private void initiate() {
    	final LinearLayout basketballLayout = (LinearLayout)findViewById(R.id.basketballLayout);
        final LinearLayout baseballLayout = (LinearLayout)findViewById(R.id.baseballLayout);
        final LinearLayout tennisLayout = (LinearLayout)findViewById(R.id.tennisLayout);
        final LinearLayout soccerLayout = (LinearLayout)findViewById(R.id.soccerLayout);
        final LinearLayout golfLayout = (LinearLayout)findViewById(R.id.golfLayout);
        final LinearLayout othersLayout = (LinearLayout)findViewById(R.id.othersLayout);
        
        basketballLayout.setOnClickListener(this);
        baseballLayout.setOnClickListener(this);
        soccerLayout.setOnClickListener(this);
        tennisLayout.setOnClickListener(this);
        golfLayout.setOnClickListener(this);
        othersLayout.setOnClickListener(this);
     
        
    }
    
    private void setFonts() {
    	
    	Typeface malgun = Typeface.createFromAsset(getAssets(), "fonts/malgun.ttf");      
        Typeface malgunBold = Typeface.createFromAsset(getAssets(), "fonts/malgunbd.ttf");
        
        TextView mainTitle = (TextView)findViewById(R.id.TitleMain);
        mainTitle.setTypeface(malgunBold);
        
        TextView basketballTitle = (TextView)findViewById(R.id.TitleBasketball);         
        
        TextView baseballTitle = (TextView)findViewById(R.id.TitleBaseball);
        TextView tennisTitle = (TextView)findViewById(R.id.TitleTennis);
        TextView soccerTitle = (TextView)findViewById(R.id.TitleSoccer);
        TextView othersTitle = (TextView)findViewById(R.id.TitleOthers);
        TextView golfTitle = (TextView)findViewById(R.id.TitleGolf);
        
        basketballTitle.setTypeface(malgun);
        baseballTitle.setTypeface(malgun);
        tennisTitle.setTypeface(malgun);
        soccerTitle.setTypeface(malgun);
        golfTitle.setTypeface(malgun);
        othersTitle.setTypeface(malgun);       
         
    }
    
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.baseballLayout:
			Intent baseballIntent = new Intent(this, GamingActivity.class);
			baseballIntent.putExtra("itemType", "baseball");
			baseballIntent.putExtra("itemNumber", 1);
			startActivity(baseballIntent);
			break;
		case R.id.basketballLayout:
			Intent basketballIntent = new Intent(this, GamingActivity.class);
			basketballIntent.putExtra("itemType", "basketball");
			basketballIntent.putExtra("itemNumber", 1);
			startActivity(basketballIntent);
			break;			
		case R.id.tennisLayout:
			Intent tennisIntent = new Intent(this, GamingActivity.class);
			tennisIntent.putExtra("itemType", "tennis");
			tennisIntent.putExtra("itemNumber", 1);
			startActivity(tennisIntent);
			break;
		case R.id.soccerLayout:
			Intent soccerIntent = new Intent(this, GamingActivity.class);
			soccerIntent.putExtra("itemType", "soccer");
			soccerIntent.putExtra("itemNumber", 1);
			startActivity(soccerIntent);
			break;
		case R.id.golfLayout:
			Intent golfIntent = new Intent(this, GamingActivity.class);
			golfIntent.putExtra("itemType", "golf");
			golfIntent.putExtra("itemNumber", 1);
			startActivity(golfIntent);
			break;
		case R.id.othersLayout:
		
			break;
		
		}
	}
	
}