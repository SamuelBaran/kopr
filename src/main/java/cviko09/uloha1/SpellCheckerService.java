package cviko09.uloha1;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import cviko09.uloha1.SpellChecker;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.css.PseudoClass;

public class SpellCheckerService extends Service<Boolean>{
	
	private StringProperty text; 

	public SpellCheckerService(StringProperty text) {
		this.text = text;
		
	}
	
	
	@Override
	protected Task<Boolean> createTask() {
		return new Task<Boolean>() {
			private String textValue;
			private SpellChecker spellChecker = new SpellChecker();
			
			@Override
			protected Boolean call() throws Exception {
				while(true) {
					CountDownLatch latch = new CountDownLatch(1);
					// chcem ziskat hodnotu a otestovat
					// potrebujem textArea resp aspon textProperty
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							textValue = text.getValue();
							latch.countDown();
						}
					});
					
					// cakam na uvolnenie
					latch.await();
					
					final List<SpellChecker.SpellcheckBoundary> kontrola = spellChecker.check(textValue);
					boolean isOK = kontrola.isEmpty();
					System.out.println(textValue + " kontrola ok: " + isOK);
					
					// 'poslem' vysledok
					updateValue(isOK);

				}
			}
			
		};
	}

}
