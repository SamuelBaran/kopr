package cviko02.uloha3;


import java.util.List;

public class Life implements Runnable {

	private List<Man> men;
	
	public Life(List<Man> men) {
		this.men = men;
	}

	@Override
	public void run() {
		for (int i = 0; i < men.size(); i++) {
			Man man = men.get(i);
			System.out.println("život: bijem "+man.getId()+". chlapa");
			men.set(i, new Man(man.getId(), "starý","škaredý"));
		}
	}

}