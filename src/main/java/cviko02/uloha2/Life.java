package cviko02.uloha2;


import java.util.List;

public class Life implements Runnable {

	private List<Man> men;
	
	public Life(List<Man> men) {
		this.men = men;
	}

	@Override
	public void run() {
		for (Man man : men) {
			synchronized(man) {
			man.setAge("starý");
			System.out.println("život: bijem "+man.getId()+". chlapa");
			man.setCharm("škaredý");
			}
		}
	}

}