

public class Guest {
	private int totalSpent; // 고객의 총쓴돈
	private String name; // 이름
	private String phoneNumber; // 전화번호
	private String membership; // 총 쓴돈에 비례한 멤버십
	private String lostItem; // 고객의 분실물

	public Guest(String name, String phoneNumber) { // 신규 고객 생성용 메소드
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.totalSpent = 0;
		this.membership = "일반";
		this.lostItem = null;
	}

	public int getTotalSpent() {
		return totalSpent;
	}

	public void setTotalSpent(int totalSpent) {
		this.totalSpent = totalSpent;
	}

	public String getLostItem() {
		return lostItem;
	}

	public void setLostItem(String lostItem) {
		this.lostItem = lostItem;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getMembership() {
		return membership;
	}

	public void setMembership() { // 멤버십 갱신
		if (this.totalSpent >= 1000000) {
			this.membership = "VIP";
		} else if (totalSpent >= 500000) {
			this.membership = "골드";
		} else if (totalSpent >= 250000) {
			this.membership = "실버";
		} else {
			this.membership = "일반";
		}
	}
	public void guestInfo() {
		System.out.printf("%s등급 %s고객님, 연락처 %s , 누적 사용 금액 : %d\n",this.membership,this.name,this.phoneNumber,this.totalSpent);
	}
}
