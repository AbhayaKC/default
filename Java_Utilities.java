package common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Java_Utilities {
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	
		/**
	 *  Method - To Create Date Object
	 *  @param:
	 *  @return - Returns the Date object.
	 */
	 
	public Date utilities_Date(){
		Date date=new Date();
		return date;
	}
		
	/**
	 *  Method - To format Date object to desired pattern which we pass as string argument. 
	 *  @param: dateFormatPattern String (Ex: yyyy/mm/dd , mm/dd/yy, etc.)
	 *  @return - String (Returns the the formatted date of type String) 
	 */
	
	public String utilities_formatDateToString(String dateFormatPattern){
		SimpleDateFormat sdf=new SimpleDateFormat(dateFormatPattern);
		String formattedDate=sdf.format(new Date());
		return formattedDate;
	}
	
	
	

	
	/**	 
	 * Method - To Return the date object by taking date of type String 
	 * @param stringFormattedDate (Date in the form of String)
	 * @param dateFormatPattern (Pattern of the "stringFormattedDate" parameter)
	 * stringFormattedDate and dateFormatPattern both should be in same format. Ex: dateFormatPattern="dd-M-yyyy hh:mm:ss" 
	 * and stringFormattedDate="31-01-2018 10:20:56"
	 * @return Date
	 * @throws ParseException
	 */
	public Date utilities_parseDateFromString(String stringFormattedDate,String dateFormatPattern) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat(dateFormatPattern);
		Date parsedDate=sdf.parse(stringFormattedDate);
		return parsedDate;
	}
	
	/**
	 * Method - To create Calendar object.
	 * @return Calendar
	 */
	
	public Calendar utilities_calender(){
		Calendar calendar = Calendar.getInstance();       
        return calendar;
	}
	
	/**
	 * Method -To generate random alpha numeric String
	 * @param randomStringLength
	 * @return String (Returns the random string of desired length.
	 */	
	public String utilities_randAlphaNumeric(int randomStringLength) {
		StringBuilder builder = new StringBuilder();
		while (randomStringLength-- != 0) {
			int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}
	
	/**
	 * Method - To generate random integers between range min and max.
	 * @param min
	 * @param max
	 * @return int
	 */
	
	public int utilities_randIntGen(int min,int max){
		Random rand=new Random();
		int randomInt = rand.nextInt((max - min) + 1) + min;

	    return randomInt;
	}
	
	public String generate_RandomNumber(int len){
		String Numbers ="1234567890";
        StringBuilder NUM = new StringBuilder();
        Random rnd = new Random();
        while (NUM.length() < len) { // length of the random numbers.
            int index = (int) (rnd.nextFloat() * Numbers.length());
            NUM.append(Numbers.charAt(index));
        }
        String RandomNumbers = NUM.toString();
        return RandomNumbers;
		
	}
	
	public String getRandomNumbers(){
		String Numbers ="1234567890";
        StringBuilder NUM = new StringBuilder();
        Random rnd = new Random();
        while (NUM.length() <= 9) { // length of the random numbers.
            int index = (int) (rnd.nextFloat() * Numbers.length());
            NUM.append(Numbers.charAt(index));
        }
        String RandomNumbers = NUM.toString();
        return RandomNumbers;
		
	}
	
	public String getRandomNames(){
	        String Characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	        StringBuilder character = new StringBuilder();
	        Random rnd = new Random();
	        while (character.length() < 6) { // length of the random string.
	            int index = (int) (rnd.nextFloat() * Characters.length());
	            character.append(Characters.charAt(index));
	        }
	        String RandomCharacters = character.toString();
	        return RandomCharacters;
	}
	
	/**
	 * Method - To generate random float values between range min and max.
	 * @param min
	 * @param max
	 * @return float
	 */
	public float utilities_randFloatGen(float min,float max){
	    float generatedFloat = min + new Random().nextFloat() * (max - min);
	    return generatedFloat;
	}
	
	/**
	 * Method- To split a string based on Regex. Regex can be a delimiter 
	 * @param s
	 * @param regEx
	 * @return String[] (String array)
	 */
	public String[] utilities_split(String s,String regEx){
		String[] tokens=s.split(regEx);
		return tokens;
	}
	
	/**
	 * Method - this function replaces the original character with replacement character
	 * @param s
	 * @param original
	 * @param replacement
	 * @return String
	 */
	public String utilities_replace(String s,char original, char replacement){
		String replacedStr=s.replace(original, replacement);
		return replacedStr;
	}
	
	/**
	 * Method - this function replaces the original string with replacement string
	 * @param s
	 * @param regex
	 * @param replacement
	 * @return
	 */
	public String utilities_replaceAll(String s,String regex, String replacement){
		String replacedStr=s.replaceAll(regex, replacement);
		return replacedStr;
	}
	
	/**
	 * Method - returns the month of the current date.
	 * @return int
	 */
	public int utilities_month(){
		Calendar now = Calendar.getInstance();
		int month=now.get(Calendar.MONTH)+1;		
		return month;
	}
	
	
	/**
	 * Method - Returns the year of current date.
	 * @return int
	 */
	public int utilities_year(){
		Calendar now = Calendar.getInstance();
		int year=now.get(Calendar.YEAR);		
		return year;
	}
	
	/**
	 * Method - Returns the day(Date) of the current Date.
	 * @return int
	 */
	public int utilities_day(){
		Calendar now = Calendar.getInstance();
		int month=now.get(Calendar.DATE);		
		return month;
	}
	
	
	/**
	 * Method -  Returns the day name of the current Date.
	 * @return String
	 */
	public String utilities_dayOfWeek(){
		String dayOfWeek = new SimpleDateFormat("EEEE").format(new Date());
		return dayOfWeek;
	}
	
	/**
	 * Method- Returns true if given date is todays or future date else returns false
	 * @throws ParseException 
	 */
	public boolean utilities_futureDate(String givenDate) throws ParseException{
		Date date=new SimpleDateFormat("MM/dd/YYYY").parse(givenDate);
		return date.after(new Date());
				
		//return true;
	}
//	public static void main(String[] args) throws ParseException {
//		Java_Utilities u=new Java_Utilities();
//		//String s="Lavanya";
//		//u.utilities_calender();
//		//System.out.println(u.utilities_Date());
//		//System.out.println(u.utilities_formatDateToString("yy/dd"));
//		//System.out.println(u.utilities_parseDateFromString("31-01-2018","dd-M-yyyy"));
//		//System.out.println(u.randomAlphaNumeric(4));	
//		//System.out.println(u.randomIntGenerator(2, 60));
//		//System.out.println(u.randomFloatGen(1F,5F));
//		//String a[]=u.split("Lavanya","a");
//		//System.out.println(u.utilities_replace("Lavanya", 'a', 'b'));
//		//System.out.println(u.utilities_replaceAll("Lavanya", "a", "\n"));
//		System.out.println(u.utilities_dayOfWeek());
//		//System.out.println(u.utilities_day());
//		
//	}
}