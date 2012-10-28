package csci422.lwm.mathtutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import android.util.Log;

public class MathProblemGenerator {	
	
	private static final int[] VALUE_LIMIT = {10, 20, 100};
	public static final int EASY = 0;
	public static final int MEDIUM = 1;
	public static final int HARD = 2;
	
	private static final int ADDITION = 0;
	private static final int SUBTRACTION = 1;
	
	private Integer value1;
	private Integer value2;
	private int difficulty;
	private int problemType;
	private static Random random = new Random();
	
	public MathProblemGenerator() {
		this.difficulty = EASY;
		this.generateProblem();
	}
	
	public void generateProblem() {
		problemType = random.nextInt(2);
		if (problemType == ADDITION) {
			generateAddition();
		} else if (problemType == SUBTRACTION) {
			generateSubtraction();
		} else {
			Log.wtf(MathTutor.DEBUG_TAG, "Problem Tag is invalid");
		}
	}
	
	private void generateAddition() {
		value1 = 99;
		value2 = 99;
		while(value1 + value2 > VALUE_LIMIT[difficulty]) {
			value1 = random.nextInt(VALUE_LIMIT[difficulty]);
			value2 = random.nextInt(VALUE_LIMIT[difficulty]);
		}
	}
	
	private void generateSubtraction() {
		value1 = random.nextInt(VALUE_LIMIT[difficulty]);
		value2 = random.nextInt(VALUE_LIMIT[difficulty]);
	}
	
	public String getQuestion() {
		if (problemType == ADDITION) {
			return value1 + " + " + value2 + " = ?";
		} else if (problemType == SUBTRACTION) {
			if (value1 >= value2) {
				return value1 + " - " + value2 + " = ?";
			} else {
				return value2 + " - " + value1 + " = ?";
			}
		} else {
			Log.wtf(MathTutor.DEBUG_TAG, "Problem Tag is invalid");
			return "";
		}
	}
	
	public Integer getAnswer() {
		if (problemType == ADDITION) {
			return value1 + value2;
		} else if (problemType == SUBTRACTION) {
			return Math.abs(value1 - value2);	
		} else {
			Log.wtf(MathTutor.DEBUG_TAG, "Problem Tag is invalid");
			return Integer.valueOf(-1);
		}
	}
	
	public ArrayList<Integer> getAnswerChoices() {
		ArrayList<Integer> answers = new ArrayList<Integer>();
		answers.add(getAnswer());
		while (answers.size() < 4) {
			int answer = random.nextInt(VALUE_LIMIT[difficulty]);
			if (!answers.contains(answer)) {
				answers.add(answer);
			}
		}
		Collections.shuffle(answers);
		return answers;
	}
	
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
		generateProblem();
	}
	
	public int getDifficulty() {
		return difficulty;
	}
	
}
