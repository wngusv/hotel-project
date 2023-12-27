

import java.util.Arrays;

public class GuestManagement {
	private Guest[] guests;

	public GuestManagement() {
		this.guests = new Guest[20];
		for (int i = 0; i < guests.length; i++) {
			guests[i] = null;
		}
	}

	public Guest[] getGuests() {
		return guests;
	}

	public void setGuests(Guest[] guests) {
		this.guests = guests;
	}

	public boolean guestExist(String name, String phoneNumber) { // 일치하는 고객이 있는지 판별하는 메소드 (논리값 리턴)
		for (int i = 0; i < guests.length; i++) {
			if (this.guests[i] != null) {
				if (this.guests[i].getName().equals(name) && this.guests[i].getPhoneNumber().equals(phoneNumber)) {
					return true;
				}
			}
		}
		return false;
	}

	public void guestRegist(Guest guest) { // 고객 등록절차(신규 고객용)
		for (int i = 0; i < guests.length; i++) { // 앞에서부터 빈칸에다가 고객저장
			if (guests[i] == null) {
				guests[i] = guest;
				break;
			}
			if (guests[i] == guests[guests.length - 1] && guests[i] != null) { // 배열꽉차면 늘리고 저장
				Guest[] copy = Arrays.copyOf(guests, guests.length + 20);
				for (int j = guests.length; j < guests.length + 20; j++) {
					copy[j] = null;
				}
				guests = copy;
			}
		}
	}

	public void guestDelete(String name, String phoneNumber) { // 고객 정보 삭제및 정리
		for (int i = 0; i < guests.length; i++) { // 고객 정보 삭제
			if (this.guests[i].getName().equals(name) && this.guests[i].getPhoneNumber().equals(phoneNumber)) {
				guests[i] = null;
				break;
			}
		}
		for (int i = 0; i < guests.length - 1; i++) { // 중간에 빈칸이 있을경우, 배열 정리 기능
			if (this.guests[i] == null) {
				if (this.guests[i + 1] != null) {
					this.guests[i] = this.guests[i + 1];
					this.guests[i + 1] = null;
				} else {
					break;
				}
			}
		}
	}

	public void guestLostItem(Room room, String lostItem) { // 분실물을 고객에게 저장하기
		Guest guest = room.getGuest(); // 방의 손님을 특정
		for (int i = 0; i < guests.length; i++) {
			if (guests[i] != null && guest != null) {
				if (guests[i].getName().equals(guest.getName())
						&& guests[i].getPhoneNumber().equals(guest.getPhoneNumber())) { // 방의 손님과 손님 배열을 검사해서 특정 방의 손님을
																						// 확정짓기
					if (guests[i].getLostItem() == null) { // 분실물이 없을땐 대입
						guests[i].setLostItem(lostItem);
						break;
					} else { // 분실물이 이미 있을땐 덧셈
						guests[i].setLostItem(guest.getLostItem() + lostItem);
						break;
					}
				}
			}
		}
	}

	public void guestTotalSpent(Room room) { // 고객의 총 사용금액 갱신
		int price = room.getPrice(); // 방 가격 받아오기
		Guest guest = room.getGuest(); // 방의 손님을 특정
		for (int i = 0; i < guests.length; i++) { // 방의 금액을 총 사용금액에 대입한다.
			if (guests[i] != null && guest != null) {
				if (guests[i].getName().equals(guest.getName())
						&& guests[i].getPhoneNumber().equals(guest.getPhoneNumber())) { // 방의 손님과 손님 배열을 검사해서 특정 방의 손님을
																						// 확정짓기
					guests[i].setTotalSpent(guests[i].getTotalSpent() + price);
					break;
				}
			}
		}
	}

	public void allGuestInfo() { // 모든 고객배열의 고객 정보를 출력함.
		int count = 0;
		for (int i = 0; i < guests.length; i++) {
			if (guests[i] != null) {
				count++;
			}
		}
		if (count >= 1) {
			for (int i = 0; i < guests.length; i++) {
				if (guests[i] != null) {
					guests[i].setMembership();
					guests[i].guestInfo();
				}
			}
		} else {
			System.out.println("등록된 고객의 정보가 없습니다.");
		}
	}
}
