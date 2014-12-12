package generator;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * Library of methods used for random text generation
 */
public class GeneratorLibrary
{
	public static void main (String[] args) {
		String text = "";
		try {
			text = fileToString(new File("/Users/elijah/Development/workspace/PS4/sawyer.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(generateText(text, 7, 500));
	}
	
	/**
	 * Returns the contents of a plain text file as a string. Throws an
	 * IOException if the file can't be read or if any other problem is
	 * encountered.
	 */
	public static String fileToString (File file) throws IOException
	{
		byte[] encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
		return new String(encoded, StandardCharsets.UTF_8);
	}

	/**
	 * Randomly generates and returns a string using the algorithm sketched in
	 * P4. The parameters are the source text, the generation level, and the
	 * length of the string to be generated.
	 * 
	 * If level >= the length of the text, or if level is negative, throws an
	 * IllegalArgumentException.
	 */
	public static String generateText (String text, int level, int length)
	{
		// Make sure parameters are valid
		if (text.length() <= level || level < 0)
		{
			throw new IllegalArgumentException();
		}
		
		// This random number generator is used throughout as a source
		// of randomness
		Random rand = new Random();
		
		// Pick the initial random seed
		String seed = pickRandomSeed(text, level, rand);
		
		// Compose the string
		String result = "";		
		while (result.length() < length)
		{
			// Find out how many times the seed occurs inside the text
			int count = countTargetOccurrences(text, seed);
			
			// If there are no occurrences, pick a new seed
			if (count == 0)
			{
				seed = pickRandomSeed(text, level, rand);
			}
			
			// Otherwise, advance the text generation by one character
			else
			{
				int n = rand.nextInt(count);
				char c = getCharAfterNthOccurrence(text, n + 1, seed);
				seed = (seed + c).substring(1);
				result += c;
			}
		}
		
		// When the result is long enough, return it
		return result;
	}

	/**
	 * Returns a substring of text that contains length characters, beginning at
	 * an index chosen randomly using rand. If there are not length characters
	 * in text, throws an IllegalArgumentException.
	 */
	public static String pickRandomSeed (String text, int length, Random rand)
	{
		int index = rand.nextInt(text.length() - (length - 1));
		return text.substring(index, index + length);
	}

	/**
	 * Let n be the length of text.
	 * 
	 * If the length of target is >= n, throws an IllegalArgumentException
	 * 
	 * Otherwise, if the length of target is 0, returns n
	 * 
	 * Otherwise, returns the number of times that target appears in the first
	 * n-1 characters of text
	 */
	public static int countTargetOccurrences (String text, String target)
	{
		int n = text.length();
		int targetCount = 0;
		int start = 0;
		int targetIndex = 0;
//		if the target length is the same or bigger then the text length throw an IllegalArgumentException
		if (target.length() >= n) {
			throw new IllegalArgumentException();
//		else if the target length is 0, return the length of the text
		} else if (target.length() == 0){
			return n;
//		else run a loop that counts the number of target occurrences.
		} else {
			while (targetIndex >= 0) {
				targetIndex = text.indexOf(target, start);
				if (targetIndex >= 0) {
					start = targetIndex + 1;
					if (targetIndex + target.length() < n) {
						targetCount++;
					}
				}		
			}
			return targetCount;
		}
	}

	/**
	 * If n is not positive, throws an IllegalArgumentException.
	 * 
	 * Otherwise, returns the character that follows the nth occurrence of the
	 * target inside the text.
	 * 
	 * If the target does not occur n times within the text, or if the nth
	 * occurrence is not followed by a character, throws NoSuchElementException.
	 */
	public static char getCharAfterNthOccurrence (String text, int n, String target)
	{
//		if n is less then 1 throw an IllegalArgumentException
		if (n < 1){
			throw new IllegalArgumentException();
//		else check to make sure that there are enough occurences of n
//		in the text and if not make sure it throws the correct type
//		of exception
		} else {
			try {
				if (countTargetOccurrences(text, target) < n) {
					throw new NoSuchElementException();
				}
			} catch (IllegalArgumentException e) {
				throw new NoSuchElementException();
			}
		}
		
//		Now finally if you've somehow avoided all those exceptions
//		run a loop and get that damn char after nth occurrence
//		programming is fun but hard man
		int start = 0;
		int targetIndex = 0;
		int count = 1;
		char nextChar = 0;
		while (count <= n) {
			targetIndex = text.indexOf(target, start);
			if (targetIndex >= 0) {
				start = targetIndex + 1;
				if (targetIndex + target.length() >= text.length()) {
					throw new NoSuchElementException();
				}
				nextChar = text.charAt(targetIndex + target.length());
			}
			count++;
		}
		return nextChar;
	}
}
