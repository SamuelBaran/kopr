package cviko09.uloha1;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Pane;

public class RedGreenPane extends Pane {

	BooleanProperty greenState = new SimpleBooleanProperty();
	
	public RedGreenPane() {
		setPrefSize(640, 20);
		greenState.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue)
					setStyle("-fx-background-color: green;");
				else
					setStyle("-fx-background-color: red;");
			}
		});
		setGreenState(true);
	}

	public BooleanProperty greenStateProperty() {
		return greenState;
	}
	
	public boolean getGreenState() {
		return greenState.get();
	}

	public void setGreenState(boolean isGreen) {
		greenState.set(isGreen);
	}
}
