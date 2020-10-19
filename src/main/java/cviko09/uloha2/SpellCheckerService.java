package cviko09.uloha2;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Phaser;

import cviko09.uloha2.SpellChecker;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.css.PseudoClass;

public class SpellCheckerService extends Service<Boolean>{
	
	private StringProperty text; 
	private Phaser needToCheckPhaser = new Phaser(1); // na kolko cakame kym skonci faza
	
	public SpellCheckerService(StringProperty text) {
		this.text = text;
		text.addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				int phase = needToCheckPhaser.arrive(); //  chcem ukoncit tuto fazu a zacina dalsia
				System.out.println("skonciola faza " + phase);
			}
		});
	}
	
	
	@Override
	protected Task<Boolean> createTask() {
		return new Task<Boolean>() {
			private String textValue;
			private SpellChecker spellChecker = new SpellChecker();
			
			@Override
			protected Boolean call() throws Exception {
				int unverifiedPhase = 0;
				while(true) {
					int actualPhase = needToCheckPhaser.getPhase();
					// v aktualnej faze sa nic nestlacilo
					if (unverifiedPhase == actualPhase) {
						needToCheckPhaser.awaitAdvance(unverifiedPhase++);
						System.out.println("Faza " + unverifiedPhase + " skoncila");
					} else {
						// unverifiedPhase < needToCheckPhaser.getPhase()
						// napr mam 5 .. bolo stlacenych 5 cisel a vo faze 5 nebolo stlacene nic
						unverifiedPhase = actualPhase;
					}
					
					CountDownLatch latch = new CountDownLatch(1);
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
