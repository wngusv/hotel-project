
public class Room {

	private String roomState; // 현재 방의 상태(0 = 빈방, 1 = 예약, 2 = 투숙, 3 = ?...)
	private String roomType; // 방등급 (싱글, 더블, 디럭스, 스위트)
	private Guest guest; // 방의 손님
	private int price; // 방의 가격(등급별로 다름)

	public Room(String roomState, int input) {
		this.roomState = roomState;
		this.guest = null;
		if (input % 20 == 0) { // 방 끝자리에 따라 등급 결정
			this.roomType = "스위트";
		} else if (input % 8 == 0 && input % 20 != 0) {
			this.roomType = "디럭스";
		} else if (input % 2 == 0 && input % 8 != 0 && input % 20 != 0) {
			this.roomType = "더블";
		} else {
			this.roomType = "싱글";
		}
		if (this.roomType.equals("스위트")) { // 등급 별 객실 금액 설정
			this.price = 500000;
		} else if (this.roomType.equals("디럭스")) {
			this.price = 250000;
		} else if (this.roomType.equals("더블")) {
			this.price = 130000;
		} else if (this.roomType.equals("싱글")) {
			this.price = 70000;
		} else {
			this.price = 0;
		}
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getRoomState() {
		return roomState;
	}

	public void setRoomState(String roomState) {
		this.roomState = roomState;
	}

	public Guest getGuest() {
		return guest;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}

}
