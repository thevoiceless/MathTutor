package csci422.lwm.mathtutor;

import java.util.Random;

public class MathProblemGenerator {	
	
	private static final int VALUE_LIMIT = 10;
	
	private Integer value1;
	private Integer value2;
	private static Random random = new Random();
	
	public MathProblemGenerator() {
		this.generateProblem();
	}
	
	public void generateProblem() {
		value1 = random.nextInt(VALUE_LIMIT);
		value2 = random.nextInt(VALUE_LIMIT);
	}
	
	public String getQuestion() {
		return value1 + " + " + value2 + " = ?";
	}
	
	public Integer getAnswer() {
		return value1 + value2;
	}
	
}
