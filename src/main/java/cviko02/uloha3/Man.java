package cviko02.uloha3;


public class Man {

	private final int id;
	private final String age;
	private final String charm;
	
	public Man(int id) {
		this.id = id;
		age = "mladý";
		charm = "pekný";
	}

	public Man(int id, String age, String charm) {
		this.id = id;
		this.age = age;
		this.charm = charm;
	}

	public int getId() {
		return id;
	}
	
	public String getAge() {
		return age;
	}

	public String getCharm() {
		return charm;
	}

	@Override
	public String toString() {
		return age + " a " + charm;
	}
}