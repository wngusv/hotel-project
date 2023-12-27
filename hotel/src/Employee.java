

public class Employee {
	private String role;

	public Employee() {
		this.role = null;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public void accessCode(String accessCode) {
		if (accessCode.equals("1004")) {
			this.role = "매니저";
		} else if (accessCode.equals("1234")) {
			this.role = "데스크 직원";
		} else if (accessCode.equals("7777")) {
			this.role = "청소 직원";
		} else {
			this.role = null;
		}
	}
}
