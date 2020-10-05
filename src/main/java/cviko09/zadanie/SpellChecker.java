package cviko09.zadanie;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpellChecker {
	private List<String> incorrectWords = new ArrayList<String>();
	
	public SpellChecker() {
		incorrectWords.addAll(Arrays.asList(
				"vysí", 
				"rokou",
				"vládov", 
				"bycikel",
				"cytrus",
				"lehátko",
				"sluchátko",
				"naviac"
		));
	}
	
	public List<SpellcheckBoundary> check(CharSequence text) {
		List<SpellcheckBoundary> errors = new ArrayList<SpellcheckBoundary>();
		
		BreakIterator iterator = BreakIterator.getWordInstance();
		iterator.setText(text.toString());

		int start = iterator.first();
		int end = iterator.next();

		while (end != BreakIterator.DONE) {
			SpellcheckBoundary spellcheckBoundary = checkWord(text, start, end);
			if(spellcheckBoundary.isError()) {
				errors.add(spellcheckBoundary);
			}
			
			start = end;
			end = iterator.next();
		}
	
		//simulácia dlhého výpočtu
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return errors;
	}
	
	private SpellcheckBoundary checkWord(CharSequence text, int start, int end) {
		String word = "";
		if(end == -1) {
			word = text.toString().substring(start);
		} else {
			word = text.toString().substring(start, end);
		}
		if (!word.isEmpty() && Character.isLetterOrDigit(word.charAt(0))) {
			if(!spellCheck(word)) {
				return new SpellcheckBoundary(start, end);
			}
		}
		return SpellcheckBoundary.NO_ERROR;
	}
	
	private boolean spellCheck(String slovo) {
		return !incorrectWords.contains(slovo);
	}

	public static class SpellcheckBoundary {
		private boolean isError;
		
		private int start;
		
		private int end;

		public static SpellcheckBoundary NO_ERROR = new SpellcheckBoundary(-1, -1, false); 
		
		public SpellcheckBoundary(int start, int end) {
			this(start, end, true);
		}
		
		public SpellcheckBoundary(int start, int end, boolean isError) {
			super();
			this.start = start;
			this.end = end;
			this.isError = isError;
		}
		
		public int getStart() {
			return start;
		}
		
		public int getEnd() {
			return end;
		}
		
		public boolean isError() {
			return isError;
		}

		@Override
		public String toString() {
			return "SpellcheckBoundary [isError=" + isError + ", start="
					+ start + ", end=" + end + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + end;
			result = prime * result + (isError ? 1231 : 1237);
			result = prime * result + start;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			SpellcheckBoundary other = (SpellcheckBoundary) obj;
			if (end != other.end)
				return false;
			if (isError != other.isError)
				return false;
			if (start != other.start)
				return false;
			return true;
		}
		
		
		
	}
	
	public static void main(String[] args) {
		SpellChecker spellChecker = new SpellChecker();
		String text = "bycikel vysí a sluchátko naviac hučí už päť rokou";
		for (SpellcheckBoundary wc : spellChecker.check(text)) {			
			System.out.println(wc.start + " " + wc.end + " '" + text.substring(wc.start, wc.end) + "'");			
		}


	}
}
