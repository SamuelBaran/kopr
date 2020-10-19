package cviko09.uloha1;

// kontrola gramatiky cez SERVICE

import java.util.List;

import cviko09.uloha1.SpellCheckerService;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class MainApp extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
        BorderPane rootPane = new BorderPane();
        RedGreenPane redGreenPane = new RedGreenPane();  
        TextArea textArea = new TextArea();
        rootPane.setCenter(textArea);
        rootPane.setBottom(redGreenPane);
        rootPane.setPrefSize(640, 480);
        Scene scene = new Scene(rootPane);

        primaryStage.setScene(scene);
		primaryStage.setTitle("KOPR editor");
		primaryStage.show();
		
		SpellCheckerService service = new SpellCheckerService(textArea.textProperty());
		// nabindujem .. model red panelu je listenerom service valueProp
		redGreenPane.greenStateProperty().bind(service.valueProperty());
		service.start();
		
// 		listener kt reaguje ked sa meni proiperty
//		textArea.textProperty().addListener(new ChangeListener<String>() {
//
//			@Override
//			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//				SpellChecker spellChecker = new SpellChecker();
//				final List<SpellChecker.SpellcheckBoundary> kontrola = spellChecker.check(newValue);
//				boolean isOK = kontrola.isEmpty();
//				System.out.println(newValue + " kontrola ok: " + isOK);	
//				redGreenPane.setGreenState(isOK);
//			}
//		});
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	
}