package com.hook38.learningapp;

import java.util.ArrayList;
import android.media.AudioManager;

import com.hook38.learningapp.objects.LearningPage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GamingActivity extends Activity implements OnClickListener, OnTouchListener {
	private LearningPage page;
	private int itemNumber;
	private String itemType;
	private static final String TAG = "debugg log";
	private static final int MIN_LEVEL = 1;
	private static final int MAX_LEVEL = 20;
	//sound variables
	private MediaPlayer player;
	private int numberSoundID;
	private int itemSoundID;
	
	//onTouch states and parameters
	static final int NONE = 0;
	static final int DRAG = 1;	
	int mode = NONE;
	PointF start = new PointF();
	PointF mid = new PointF();
	static final float MIN_SWIPE_DISTANCE = 50f; 
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.gaming);
        //retrieve parameters
        Intent i = this.getIntent();
	    itemNumber = i.getIntExtra("itemNumber", 0);
	    itemType = i.getStringExtra("itemType");
              
        this.initiate();
        
	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.d("onClick", String.valueOf(v.getId()));
		switch(v.getId()){
		case(R.id.previousPageButton):
			Log.d(TAG, "previous");
			this.previousPage();
			break;
		case(R.id.nextPageButton):
			this.nextPage();
			break;
		case(R.id.homeButton):
			this.goHome();
			break;
		case(R.id.TitleMiddleLayout):
			this.playSound(this.numberSoundID);
			break;
		
		}
	}
	
	private void goHome() {
		Intent homeIntent = new Intent(this, LearningAppActivity.class);
		startActivity(homeIntent);
	}
	
	
	private void initiate() {
		//initiate page managing object
		page = new LearningPage(itemType, itemNumber);
		this.insertTitleNumbers();	
		
		//initiate sounds
		
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		this.setItemSound();
		this.setNumberSound();
		
		final LinearLayout centerImageLinearLayout = 
				(LinearLayout)findViewById(R.id.TitleMiddleLayout);
		centerImageLinearLayout.setOnClickListener(this);
		final ImageView previousPageArrow = (ImageView)findViewById(R.id.previousPageButton);
		final ImageView nextPageArrow = (ImageView)findViewById(R.id.nextPageButton);
		final ImageView homeButton = (ImageView)findViewById(R.id.homeButton);		
		previousPageArrow.setOnClickListener(this);
		nextPageArrow.setOnClickListener(this);
		homeButton.setOnClickListener(this);		
		
		
		this.insertMainImage();		
		
		final LinearLayout mainImageLayout = (LinearLayout) findViewById(R.id.centerImageLayout);
		mainImageLayout.setOnTouchListener(this);		
	}

	private void playSound(int resId) {
		Log.d("Play sound", String.valueOf(resId));
		if(player != null){
			player.release();
		}
		if(resId != 0){
			player = MediaPlayer.create(this, resId);
			player.start();
		}
	}
	
	private void addMainImageFromFile(String fileName) {
		int id = getResources().getIdentifier(fileName, "drawable", getPackageName());
		//if set image view to the full page
		ImageView imageView = (ImageView) findViewById(R.id.fullPageImageView);
		//if set image view to the center area
		//ImageView imageView = (ImageView) findViewById(R.id.centerImageView);
		imageView.setImageResource(id);
		Log.d("addImage", String.valueOf(id));
	}
	
	private void setNumberSound() {
		this.numberSoundID = getResources().getIdentifier(page.generateNumberSoundFileName(), 
				"raw",getPackageName());		
	}
	
	private void setItemSound() {
		this.itemSoundID = getResources().getIdentifier(page.generateItemSoundFileName(), 
				"raw",getPackageName());		
	}
	
	private void addTitleNumberFromFile(String fileName) {		
		LinearLayout numberLayout = (LinearLayout)findViewById(R.id.TitleMiddleLayout);        
        int id = getResources().getIdentifier(fileName, "drawable", getPackageName());
        ImageView imageView = new ImageView(this);
        LinearLayout.LayoutParams vp = 
        		new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 
        				LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(vp);        
        imageView.setImageResource(id);        
        numberLayout.addView(imageView);        
	}
	
	private void insertTitleNumbers() {
		ArrayList<String> array = page.numberToStringtArray(itemNumber);		
		for(String numString:array) {			
			String fileName = page.generateNumberFileName(
					numString,
					this.getString(R.integer.minNumberIndex),
					this.getString(R.integer.maxNumberIndex));
			this.addTitleNumberFromFile(fileName);
		}
	}
	
	private void insertMainImage() {
		String fileName = page.generateMainImageFileName();		
		this.addMainImageFromFile(fileName);
	}
	
	private void previousPage() {
		if(itemNumber > GamingActivity.MIN_LEVEL) {
			Intent i = new Intent(this, GamingActivity.class);
			i.putExtra("itemNumber", (itemNumber - 1));
			i.putExtra("itemType", itemType);
			startActivity(i);
		}
	}
	
	private void nextPage() {
		if(itemNumber < GamingActivity.MAX_LEVEL){
			Intent i = new Intent(this, GamingActivity.class);
			i.putExtra("itemType", itemType);
			i.putExtra("itemNumber", (itemNumber + 1));
			startActivity(i);
		}else{
			goHome();
		}
	}

	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		//this.dumpEvent(event);
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			start.set(event.getX(0), event.getY(0));
			mode = DRAG;
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			if(mid.x == 0) {
				//finger up without swiping
				this.playSound(this.itemSoundID);
				break;
			}
			if(mode == DRAG) {
				if(spacing(start.x, mid.x) >= GamingActivity.MIN_SWIPE_DISTANCE) {					
					if(start.x > mid.x){
						Log.d("onTouch", "LEFT");
						this.nextPage();
					}else{
						Log.d("onTouch", "RIGHT");
						this.previousPage();
						
					}
				}
			}
			//initiate 
			start.set(0, 0);
			mid.set(0, 0);
			mode = NONE;
			break;
		case MotionEvent.ACTION_MOVE:
			if(mode == DRAG) {
				mid.set(event.getX(0), event.getY(0));
			}
			break;
		}
		return true;
	}
	
	private void dumpEvent(MotionEvent event) {
		String names[] = {"Down", "UP", "MOVE", "CENCEL", "OUTSIDE", "POINTER_DOWN", "POINTER_UP",
				"7?", "8?", "9?" };
		StringBuilder sb = new StringBuilder();
		int action = event.getAction();
		int actionCode = action & MotionEvent.ACTION_MASK;
		sb.append("event ACTION_").append(names[actionCode]);
		if(actionCode == MotionEvent.ACTION_POINTER_DOWN || 
				actionCode == MotionEvent.ACTION_POINTER_UP) {
			sb.append("(pid ").append(action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
			sb.append(")");
		}
		sb.append("[");
		for (int i = 0; i < event.getPointerCount(); i++) {
			sb.append("#" ).append(i);
			sb.append("(pid " ).append(event.getPointerId(i));
			sb.append(")=" ).append((int) event.getX(i));
			sb.append("," ).append((int) event.getY(i));
			if (i + 1 < event.getPointerCount())
				sb.append(";" );
		}
		sb.append("]" );
		Log.d("TouchEvent", sb.toString());
	
	}
	
	/**
	 * Calculate the distance of the x axis
	 * @param x1 The starting X axis
	 * @param x2 The ending X axis
	 * @return The distance between the two
	 */
	private float spacing(float x1, float x2) {
		return Math.max(x1, x2) - Math.min(x1, x2);
	}
}
