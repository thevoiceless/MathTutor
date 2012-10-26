package csci422.lwm.mathtutor;

import java.util.Random;

import android.util.Log;

public class MathProblemGenerator {	
	
	private static final int[] VALUE_LIMIT = {10, 20, 100};
	private static final int EASY = 0;
	//private static final int MEDIUM = 1;
	//private static final int HARD = 2;
	
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
		problemType = random.nextInt(1);
		if (problemType == ADDITION) {
			generateAddition();
		} else if (problemType == SUBTRACTION) {
			generateSubtraction();
		} else {
			Log.wtf(MathTutor.DEBUG_TAG, "Problem Tag is invalid");
		}
	}
	
	private void generateAddition() {
		value1 = random.nextInt(VALUE_LIMIT[difficulty]);
		int answer = random.nextInt(VALUE_LIMIT[difficulty]);
		value2 = Math.abs(value1-answer);
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
	
}
