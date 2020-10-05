package cviko02.zadanie;


import java.util.List;

public class Woman implements Runnable {

	private List<Man> men;
	
	public Woman(List<Man> men) {
		this.men = men;
	}

	@Override
	public void run() {
		for (Man man: men) {
			System.out.println("žena: hodnotím "+man.getId()+". chlapa: " + man.toString());
		}
	}

}