

public class Request {
	private Room[][] room;

	public Request() { // 방 배열을 가진 생성자
		this.room = new Room[4][20]; // [층수][호수] 입력시 확장가능
		for (int i = 0; i < room.length; i++) {
			for (int j = 0; j < room[i].length; j++) {
				room[i][j] = new Room("빈방", j + 1); // 모든 방을 빈 방으로 초기화
			}
		}
	}

	public Room[][] getRoom() {
		return room;
	}

	public void setRoom(Room[][] room) {
		this.room = room;
	}

	public void roomReservation(int input, String name, String phoneNumber, String roomType) { // 예약의 가능여부를 선택하는 메소드
		int roomFloor = input / 100; // 층수
		int roomNumber = input % 100; // 방번호
		Room r = this.room[roomFloor - 2][roomNumber - 1];
		if (r.getRoomType().equals(roomType)) {
			if (r.getRoomState().equals("빈방")) { // 빈방이면서, 입력한 방호수가 해당 등급과 일치할 경우, 방을 예약 하는 메소드
				System.out.printf("%d%02d호에 성공적으로 예약되었습니다.\n", roomFloor, roomNumber);
				r.setRoomState("예약");
				r.setGuest(new Guest(name, phoneNumber));
			} else { // 방 등급은 일치하지만, 빈방이 아닐경우 설명 출력
				System.out.println("빈 방이 아닙니다. 올바른 정보를 입력해주세요.");
			}
		} else { // 입력한 방 번호와 등급이 안맞을경우
			System.out.println("해당 등급의 방이 아닙니다.");
		}
	}

	public void roomReservationCancel(int input, String name, String phoneNumber) { // 예약을 취소하는 메소드
		int roomFloor = input / 100; // 층수
		int roomNumber = input % 100; // 방번호
		Room r = this.room[roomFloor - 2][roomNumber - 1];
		if (r.getGuest() != null) {
			boolean tg = r.getGuest().getName().equals(name) && r.getGuest().getPhoneNumber().equals(phoneNumber)
					&& r.getRoomState().equals("예약");
			if (tg == true) { // 예약중이고, 고객의 정보가 일치하면 예약 취소 성공
				r.setRoomState("빈방");
				r.setGuest(null);
				System.out.printf("%d%02d호의 예약이 성공적으로 취소되었습니다.\n", roomFloor, roomNumber);
			} else { // 예약 취소 실패 (정보 불일치)
				System.out.println("예약 정보가 일치하지 않습니다. 올바른 정보를 입력해주세요.");
			}
		}
	}

	public void checkIn(int input, String name, String phoneNumber) { // 체크인
		int count = 0;
		int roomFloor = input / 100; // 층수
		int roomNumber = input % 100; // 방번호
		Room r = this.room[roomFloor - 2][roomNumber - 1];
		if (r.getGuest() != null && r.getRoomState().equals("예약")) {
			boolean tg = r.getGuest().getName().equals(name) && r.getGuest().getPhoneNumber().equals(phoneNumber);

			if (tg == true) { // 체크인 성공
				System.out.printf("%d%02d호가 성공적으로 체크인 되었습니다.\n", roomFloor, roomNumber);
				r.setRoomState("투숙");
				count++;
			} else {
				System.out.println("해당 고객의 정보로 예약된 방이 없습니다.");
			}
		} else {
			System.out.println("예약중인 방이 아닙니다.");
		}
	}

	public void walkIn(int input, String name, String phoneNumber, String roomType) {
		int roomFloor = input / 100; // 층수
		int roomNumber = input % 100; // 방번호
		Room r = this.room[roomFloor - 2][roomNumber - 1];
		if (r.getRoomType().equals(roomType)) {
			if (r.getRoomState().equals("빈방")) { // 조건 만족시, 방을 예약 하는 메소드
				System.out.printf("%d%02d호에 성공적으로 예약되었습니다.\n", roomFloor, roomNumber);
				r.setRoomState("투숙");
				r.setGuest(new Guest(name, phoneNumber));
			} else { // 조건 불만족시, 설명 출력
				System.out.println("빈 방이 아닙니다. 올바른 정보를 입력해주세요.");
			}
		} else { // 입력한 방 번호와 등급이 안맞을경우
			System.out.println("해당 등급의 방이 아닙니다.");
		}
	}

	public void checkOut(int input, String name, String phoneNumber) {
		int roomFloor = input / 100; // 층수
		int roomNumber = input % 100; // 방번호
		Room r = this.room[roomFloor - 2][roomNumber - 1];
		if (r.getGuest() != null && r.getRoomState().equals("투숙")) {
			boolean tg = r.getGuest().getName().equals(name) && r.getGuest().getPhoneNumber().equals(phoneNumber);
			if (tg == true) { // 체크아웃 성공
				r.setRoomState("청소 필요");
				System.out.printf("%d%02d호가 성공적으로 체크아웃 되었습니다.\n", roomFloor, roomNumber);
			} else { // 체크아웃 실패 (정보 불일치)
				System.out.println("고객의 정보가 일치하지 않습니다. 올바른 정보를 입력해주세요."); // 해당 정보가 일치하지 않습니다.(고객 성함,전화번호 or 현재 방의 상태)
			}
		} else {
			System.out.println("체크아웃을 진행할 수 있는 방이 아닙니다.");
		}
	}

	public boolean disposableGuest(String name, String phoneNumber) {
		for (int i = 0; i < room.length; i++) { // 층수 전체
			for (int j = 0; j < room[i].length; j++) { // 호수 전체
				Guest g = room[i][j].getGuest();
				if (g != null) {
					if (g.getName().equals(name) && g.getPhoneNumber().equals(phoneNumber)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public void cleanComplete(int input) { // 특정 방 청소하는 메소드
		int roomFloor = input / 100; // 층수
		int roomNumber = input % 100; // 방번호
		Room r = this.room[roomFloor - 2][roomNumber - 1];
		if (r.getRoomState().equals("청소 필요")) {
			r.setRoomState("빈방");
			r.setGuest(null);
			System.out.printf("%d%02d호는 예약 가능한 방으로 변경되었습니다.\n", roomFloor, roomNumber);
		} else {
			System.out.println("청소 완료 처리를 할 수 없는 방 입니다.");
		}
	}

	public void floorCleanComplete(String input) { // 여기서 입력받는 String은 반드시 한자리 정수인 String이다. ex)2,3,4,5 // 특정 층 청소 메소드
		char c = input.charAt(0); // 문자열을 문자로 바꾸는 작업
		int floor = c - '0'; // 문자를 정수로 변환
		for (int i = 0; i < room[floor - 2].length; i++) { // 해당 층의 청소필요 방을 빈방으로 변환하는 작업
			if (room[floor - 2][i].getRoomState().equals("청소 필요")) {
				room[floor - 2][i].setRoomState("빈방");
				room[floor - 2][i].setGuest(null);
			}
		}
		System.out.printf("%s층 전체의 청소가 필요한 방을 예약 가능한 방으로 변경했습니다.\n", input);
	}

	public String roomCheck(int input) { // 특정 호수의 방이 빈방인지를 검사하는 메소드
		int roomFloor = input / 100; // 층수
		int roomNumber = input % 100; // 방번호
		Room r = this.room[roomFloor - 2][roomNumber - 1];
		return r.getRoomState();
	}

	public void roomInfo() { // 방 전체정보 출력
		System.out.println("\t\t\t\t\t\t    <방 상태>\n\t\t\t\t\t● 투숙\t○ 빈방\t□ 예약\t■ 청소 필요\n");
		for (int i = 0; i < room.length; i++) { // 층수
			for (int j = 0; j < room[i].length; j++) { // 호수
				System.out.printf("%d%02d", i + 2, j + 1);
				if (room[i][j].getRoomState().equals("투숙")) {
					System.out.print("● "); // 투숙중
				} else if (room[i][j].getRoomState().equals("빈방")) {
					System.out.print("○ "); // 빈방
				} else if (room[i][j].getRoomState().equals("예약")) {
					System.out.print("□ "); // 예약
				} else {
					System.out.print("■ "); // 청소필요
				}
			}
			System.out.print("\n");
		}
	}

	public void reservationInfo(String name, String phoneNumber) { // 특정 고객의 예약된 방 확인 메소드
		int count = 0;
		System.out.printf("%s 고객님이 예약하신 방 목록 : ", name);
		for (int i = 0; i < room.length; i++) {
			for (int j = 0; j < room[i].length; j++) {
				Room r = this.room[i][j];
				if (r.getGuest() != null) {
					boolean tg = r.getGuest().getName().equals(name)
							&& r.getGuest().getPhoneNumber().equals(phoneNumber);

					if (tg == true && r.getRoomState().equals("예약")) { // 예약 정보 출력
						System.out.printf("%d%02d ", i + 2, j + 1);
						count++;
					}
				}
			}
		}
		if (count == 0) {
			System.out.println("해당 고객의 정보로 예약된 방이 없습니다.");
		}
		System.out.print("\n");
	}

	public void emptyRoom(String roomType) { // 빈방만 보기
		System.out.printf("%s등급 빈 방 정보\n", roomType);
		int count = 0;
		for (int i = 0; i < room.length; i++) { // 층수
			count = 0;
			for (int j = 0; j < room[i].length; j++) { // 호수
				Room r = this.room[i][j];
				if (r.getRoomState().equals("빈방") && r.getRoomType().equals(roomType)) {
					System.out.printf("%d%02d ", i + 2, j + 1);
					count++;
				}
			}
			if (count >= 1) {
				System.out.print("\n");
			}
		}
	}

	public void makeUpRoom() { // 청소필요한 방만 보기
		int count = 0;
		for (int i = 0; i < room.length; i++) {
			for (int j = 0; j < room[i].length; j++) {
				if (room[i][j].getRoomState().equals("청소 필요")) {
					count++;
				}
			}
		}
		if (count >= 1) {
			System.out.println("청소가 필요한 방 목록\n");
			count = 0;
			for (int i = 0; i < room.length; i++) { // 층수
				count = 0;
				for (int j = 0; j < room[i].length; j++) { // 호수
					Room r = this.room[i][j];
					if (r.getRoomState().equals("청소 필요")) {
						System.out.printf("%d%02d ", i + 2, j + 1);
						count++;
					}
				}
				if (count >= 1) {
					System.out.print("\n");
				}
			}
		} else {
			System.out.println("청소가 필요한 방이 없습니다.");
		}
	}

	public void occupiedRoom(String name, String phoneNumber) { // 특정 고객의 투숙중인 방만 보기
		int count = 0;
		for (int i = 0; i < room.length; i++) { // 투숙중인 방 개수 체크
			for (int j = 0; j < room[i].length; j++) {
				Room r = this.room[i][j];
				if (r.getGuest() != null) {
					boolean tg = r.getGuest().getName().equals(name)
							&& r.getGuest().getPhoneNumber().equals(phoneNumber);

					if (tg == true && r.getRoomState().equals("투숙")) { // 투숙중인 방 개수 카운팅
						count++;
					}
				}
			}
		}
		if (count == 0) { // 없으면 없다고 출력해줌
			System.out.println("해당 고객의 정보로 투숙중인 방이 없습니다.");
		} else { // 있다면 몇호인지 출력
			System.out.printf("%s 고객님이 투숙중인 방 목록 : ", name);
			for (int i = 0; i < room.length; i++) {
				for (int j = 0; j < room[i].length; j++) {
					Room r = this.room[i][j];
					if (r.getGuest() != null) {
						boolean tg = r.getGuest().getName().equals(name)
								&& r.getGuest().getPhoneNumber().equals(phoneNumber);

						if (tg == true && r.getRoomState().equals("투숙")) { // 투숙 정보 출력
							System.out.printf("%d%02d ", i + 2, j + 1);
							count++;
						}
					}
				}
			}
			System.out.print("\n");
		}
	}

	public boolean roomTypeCheck(String roomType) { // 입력값인 방이 있는지 없는지 검사
		boolean re = false;
		for (int i = 0; i < room.length; i++) {
			for (int j = 0; j < room[i].length; j++) {
				Room r = room[i][j];
				if (r.getRoomState().equals(roomType)) {
					re = true;
					break;
				}
			}
		}
		return re;
	}

	public boolean guestRoomCheck(String name, String phoneNumber, String roomType) { // 특정 고객의 입력값인 방이 있는지 검사
		int count = 0;
		for (int i = 0; i < room.length; i++) {
			for (int j = 0; j < room[i].length; j++) {
				Room r = this.room[i][j];
				if (r.getGuest() != null) {
					boolean tg = r.getGuest().getName().equals(name)
							&& r.getGuest().getPhoneNumber().equals(phoneNumber);
					if (tg == true && r.getRoomState().equals(roomType)) {
						count++;
					}
				}
			}
		}
		if (count == 0) { // 입력값인 방이 없다면, false를 리턴
			return false;
		} else { // 입력값인 방이 있으면 true를 리턴
			return true;
		}
	}

	public Room returnRoom(int input) {
		int roomFloor = input / 100; // 층수
		int roomNumber = input % 100; // 방번호
		return this.room[roomFloor - 2][roomNumber - 1];
	}
}
