package net.press.pa.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Description: 
 *
 * @author <a href="ralph.hodgson@pa.press.net">Ralph Hodgson</a>
 *
 */
public class PasswordStrengthIndicator
{
	private static final Log log = LogFactory.getLog(PasswordStrengthIndicator.class);
	
	// Rules variables
	private static int PASSWORD_MIN_LENGTH = 3;
	private static int PASSWORD_MAX_LENGTH = 10;
	private static int PASSWORD_STRENGTH = 20;
	
	private static boolean PASSWORD_MIXED_CASE = false;
	private static boolean PASSWORD_NUMERIC = true;
	private static boolean PASSWORD_SPECIAL = false;
	
	private static final int MIXED_IMPLIED = 6;
	
	public static boolean isValidPassword(String passwd) 
	{
		if (passwd == null)
			return false;
		
		int lengthScore = applyLengthScore(passwd); 
		int letterScore = applyLetterScore(passwd);
		int numberScore = applyNumberScore(passwd);
		int specialScore = applySpecialScore(passwd);
		
		int comboScore = applyComboScore(passwd, letterScore, numberScore, specialScore);
		
		int total = lengthScore + letterScore + numberScore + specialScore + comboScore;
		
		String verdict;
		
		if (total < 16) {
			verdict = "very weak";
		} else if (total > 15 && total < 25) {
			verdict = "weak";
		} else if (total > 24 && total < 35) {
			verdict = "mediocre";
		} else if (total > 34 && total < 45) {
			verdict = "strong";
		} else {
			verdict = "very strong";
		}
		log.debug(verdict + " - " + total);

		// Does it meet the password policy?
		if (passwd.length() < PASSWORD_MIN_LENGTH || passwd.length() > PASSWORD_MAX_LENGTH)
			return false;
		if (numberScore == 0 && PASSWORD_NUMERIC)
			return false;
		if (letterScore != MIXED_IMPLIED && PASSWORD_MIXED_CASE)
			return false;
		if (specialScore == 0 && PASSWORD_SPECIAL)
			return false;
		if (total < PASSWORD_STRENGTH)
			return false;
		
		return true;
	}
	
	private static int applyLengthScore(String passwd)
	{
		if (passwd.length() < 5) // length 4 or less
		{
    		log.debug("3 points for length (" + passwd.length() + ")");;
    		return 3;
    	} 
		
		if (passwd.length() > 4 && passwd.length() < 8) // length between 5 and 7
    	{
    		log.debug("6 points for length (" + passwd.length() + ")");
    		return 6;
    	} 
		
		if (passwd.length() > 7 && passwd.length() < 16) // length between 8 and 15
    	{
    		log.debug("12 points for length (" + passwd.length() + ")");
    		return 12;
    	} 

    	if (passwd.length() > 15) // length 16 or more
   		{
    		log.debug("18 point for length (" + passwd.length() + ")");
    		return 18;
   		}
    	
    	// Can't calculate length?
    	return 0;
	}
	
	private static int applyLetterScore(String passwd)
	{
		int letterScore = 0;
		
		// LETTERS 
		Matcher mL = Pattern.compile(".??[a-z]").matcher(passwd);
		
		if ( mL.find() ) // [verified] at least one lower case letter
		{
			letterScore = 1;
			log.debug("1 point for a lower case character\n");
		}
		
		Matcher mU = Pattern.compile(".??[A-Z]").matcher(passwd);
		
		if ( mU.find() ) // [verified] at least one upper case letter
		{
			letterScore += 5;
			log.debug("5 point for an upper case character");
		}
		return letterScore;
	}
	
	private static int applyNumberScore(String passwd)
	{
		int numbers = 0;
		
		// NUMBERS
		Matcher m = Pattern.compile(".??[0-9]").matcher(passwd);
		
		while ( m.find() ) // [verified] at least one number
		{
			numbers += 1;
		}
		
		if (numbers == 1) 
		{
			log.debug("5 points for 1 number.");
			return 5;
		}	
			
		if (numbers == 2) 
		{
			log.debug("7 points for 2 numbers.");
			return 7;
		}
		
		if (numbers > 2) 
		{
			log.debug("10 points for at least 3 numbers.");
			return 10;
		}
		return 0;
	}
	
	private static int applySpecialScore(String passwd)
	{
		int specials = 0;
		// SPECIAL CHAR
		Matcher m = Pattern.compile(".??[:,!,@,#,$,%,^,&,*,?,_,~]").matcher(passwd);
		
		while (m.find()) // [verified] at least one special character
			{
			specials += 1;
		}
		if (specials == 1) 
		{
			log.debug("5 points for 1 special character.");
			return 5;
		}
		
		if (specials > 1) 
		{
			log.debug("10 points for at least 2 special characters.");
			return 10;
		}
		return 0;
	}
	
	private static int applyComboScore(String passwd, int letterScore, int numberScore, int specialScore)
	{
		int comboScore = 0;
		
		// COMBOS
		
		// [verified] both upper and lower case
		if (letterScore == MIXED_IMPLIED) 
		{
			comboScore += 2;
			log.debug("2 combo points for upper and lower letters.");
		}
		
		// [verified] both letters and numbers
		if (letterScore > 0 && numberScore > 0) 
		{
			comboScore += 2;
			log.debug("2 combo points for letters and numbers.");
		}
		
		// [verified] letters, numbers, and special characters
		if (letterScore > 0 && numberScore > 0 && specialScore > 0) 
		{
			comboScore += 2;
			log.debug("2 combo points for letters, numbers and special chars");
		}
		
		// [verified] upper, lower, numbers, and special characters
		if (letterScore == MIXED_IMPLIED && numberScore > 0 && specialScore > 0)
		{
			comboScore += 2;
			log.debug("2 combo points for upper and lower case letters, numbers and special chars");
		}
		return comboScore;
	}

	public static void main(String[] args) 
	{
		if (args.length != 1) 
		{
			log.info("Unbundled usage: java PasswordStrengthIndicator <password>");
			System.exit(1);
		}
		
		if (PasswordStrengthIndicator.isValidPassword(args[0]))
			log.info("Password meets or exceeds defined security rules.");
		else
			log.info("Password fails to meet minimum security requirements.");
		
	}
}
