package com.hook38.learningapp.objects;

import java.util.ArrayList;

public class LearningPage {
	private String drawableExtension = ".png";
	private static final String NUMBER_FILENAME_PREFIX = "num";
	private static final String NUMBER_FILENAME_JOINER = "_";
	//The number of items
	private int itemNumber = 1;
	private String itemType="";
	
	public LearningPage(String type, int num) {
		this.setItemType(type);
		this.setItemNumber(num);
	}
	
	/**
	 * This method return a random integer number between a given range
	 * @param min The minimum number
	 * @param max The maximum number
	 * @return A random number between min and max
	 */
	private int randomNumber(int min, int max) {
		if (min>max) {
			int temp = max;
			min = max;
			max = temp;
		} else if(min == max) {
			return min;
		}
		return (int) (Math.random()*(max-min+1)) + min;
	}
	
	public String generateMainImageFileName() {
		//main image files have a format of type_num.png
		//for example, baseball_3.png
		return this.itemType+"_"+this.itemNumber;
	}
	
	public String generateNumberFileName(String numString, String minString, 
			String maxString) {
		int max = Integer.parseInt(maxString);
		int min = Integer.parseInt(minString);
		return this.generateNumberFileName(numString, min, max);
	}
	
	public String generateNumberSoundFileName() {
		//File names are in the format of sound_number_(num)(file extension)
		//for example sound_number_1.m4a
		return "sound_number_"+this.itemNumber;
	}
	
	public String generateItemSoundFileName() {
		//File names are in the format of sound_(item)(file extension)
		//for example sound_basketball.m4a
		return "sound_" + itemType;
	}
	
	private String generateNumberFileName(String num, int min, int max) {
		//File names are in the format of num(number)_(index)(file extension)
		//for example num1_3.png
		String fileName = NUMBER_FILENAME_PREFIX + num + NUMBER_FILENAME_JOINER 
				+ this.randomNumber(min, max);		
		return fileName;
	}
	
	public String getDrawableExtension() {
		return drawableExtension;
	}

	public void setDrawableExtension(String drawableExtension) {
		this.drawableExtension = drawableExtension;
	}

	public int getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(int itemNumber) {
		this.itemNumber = itemNumber;
	}
	
	/**
	 * This method take an int number and create a string arraylist
	 * which contain each digit at its index position. For example, 
	 * 123 will have 1 at index 0, 2 at index 1, and 3 at index 3.
	 * @param num Any integer number
	 * @return A Stng arraylist of the position index for num.
	 */
	public ArrayList<String> numberToStringtArray(int num) {
		ArrayList<String> array = new ArrayList<String>();
		String numString = Integer.toString(num);
		for(int i=0; i<numString.length(); i++) {
			char numChar = numString.charAt(i);
			array.add(String.valueOf(numChar));
		}		
		return array;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	
}
