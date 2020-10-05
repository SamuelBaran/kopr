package cviko02.uloha2;


public class Man {

	private int id;
	private String age;
	private String charm;
	
	public Man(int id) {
		this.id = id;
		age = "mladý";
		charm = "pekný";
	}

	public int getId() {
		return id;
	}
	
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getCharm() {
		return charm;
	}

	public void setCharm(String charm) {
		this.charm = charm;
	}
	
	@Override
	public String toString() {
		return age + " a " + charm;
	}
}