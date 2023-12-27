

import java.util.Scanner;

public class HotelReservationProgram {

	public static int numberChange(String roomNumberStr) { // 입력한 스트링을 숫자로 변환해줌
		if (roomNumberStr.length() == 3) { // 3자리일 경우 조건 만족.
			return (roomNumberStr.charAt(0) - '0') * 100 + (roomNumberStr.charAt(1) - '0') * 10
					+ (roomNumberStr.charAt(2) - '0');
		} else { // 3자릿수가 아니라면 999리턴해서 오류발생시킴
			return 999;
		}
	}

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Request request = new Request();
		Employee employee = new Employee();
		GuestManagement management = new GuestManagement();

		boolean logIn = true; // 로그인 화면과 업무 화면을 전환하는 boolean값.

		while (true) { // 직원 로그인과 업무 화면의 분리

			while (logIn) { // 로그인 성공시, 직원의 접근 권한 인식
				System.out.println("<호텔 운영 시스템>\n");
				System.out.println("직원코드를 입력하세요."); // 매니저 = 1004 , 프론트직원 = 1234, 청소부 7777.
				String accessCode = scan.nextLine();
				employee.accessCode(accessCode);
				if (employee.getRole() == null) { // 코드 잘못 입력시, 재입력을 받음.
					System.out.println("일치하는 직원 코드가 없습니다. 다시 입력해주세요.");
				} else if (employee.getRole().equals("청소 직원")) { // 청소직원일 경우
					System.out.println("성공적으로 로그인 되었습니다.");
					logIn = false;
				} else { // 관리자나 데스크직원인 경우
					System.out.println("성공적으로 로그인 되었습니다.");
					logIn = false;
				}
			}

			while (logIn == false) { // 업무화면 인터페이스
				request.roomInfo(); // 방 전체 정보 출력
				String role = employee.getRole();
				if (role.equals("청소 직원")) { // 청소 직원의 업무
					System.out.print("1. 청소관련 업무\n9. 로그아웃\n");
				} else if (role.equals("데스크 직원")) { // 데스크 직원의 업무
					System.out.print("2. 예약관련 업무\n3. 체크인/워크인\n4. 체크아웃\n9. 로그아웃\n");
				} else { // 매니저의 업무
					System.out.print(
							"1. 청소관련 업무 \n2. 예약관련 업무\n3. 체크인/워크인\n4. 체크아웃\n5. 고객정보 관리\n9. 로그아웃\n9999. 프로그램 종료\n");
				}
				String employeeRequest = scan.nextLine();
				String input = "";
				if (employeeRequest.equals("1") && (role.equals("청소 직원") || role.equals("매니저"))) { // 청소관련 메뉴
					System.out.println("1. 청소가 필요한 방 목록\n2. 청소완료 처리\n3. 한 층 일괄 청소 완료 처리");
					input = scan.nextLine();
					if (input.equals("1")) { // 청소가 필요한 방 목록을 출력
						request.makeUpRoom();
					} else if (input.equals("2")) { // 청소 완료시, 객실 번호 입력하여 빈방으로 변경
						while (true) { // 방 청소를 원하는 만큼
							if (request.roomTypeCheck("청소 필요") == false) { // 청소 필요인 방이 있는지 검사
								System.out.println("청소가 필요한 방이 없습니다.");
								break;
							}
							request.makeUpRoom();
							System.out.println("청소 완료한 방 번호를 입력해주세요.\n청소완료 처리 메뉴 나가기 : 999\n");
							input = scan.nextLine();
							int roomNumber = numberChange(input);
							if (roomNumber == 999) { // 탈출코드(예약취소를 그만하려고 할때)
								break;
							}
							if (roomNumber / 100 >= 2 && roomNumber / 100 <= 5 && roomNumber % 100 >= 1
									&& roomNumber % 100 <= 20) {
								System.out.println("발견된 분실물이 있습니까? 1. 예, 2. 아니오");
								String agree = scan.nextLine();
								if (agree.equals("1")) {
									System.out.println("분실물을 입력해주세요.");
									String lostItem = scan.nextLine();
									Room r = request.returnRoom(roomNumber);
									management.guestLostItem(r, lostItem);
									request.cleanComplete(roomNumber);
								} else if (agree.equals("2")) {
									request.cleanComplete(roomNumber);
								} else {
									System.out.println("잘못된 입력입니다. 올바른 번호를 입력해주세요.");
								}
							} else { // 방번호를 201~520 으로 입력하지 않은 경우
								System.out.println("잘못된 입력입니다. 올바른 번호를 입력해주세요.");
								break;
							}
						}
					} else if (input.equals("3")) {
						request.makeUpRoom();
						while (true) {
							// 청소 필요한 방 없으면 종료
							if (request.roomTypeCheck("청소 필요") == false) { // 청소 필요인 방이 있는지 검사
								break;
							}
							System.out.println("청소 완료한 층을 입력해주세요.\n청소완료 처리 메뉴 나가기 : 999\n");
							input = scan.nextLine();

							if (input.equals("999")) { // 탈출코드(예약취소를 그만하려고 할때)
								break;
							}

							if (input.equals("2") || input.equals("3") || input.equals("4") || input.equals("5")) {
								while (true) {
									System.out.println("발견된 분실물이 있습니까? 1. 예, 2. 아니오");
									String agree = scan.nextLine();
									if (agree.equals("1")) {
										System.out.println("분실물을 발견한 방 번호를 입력해주세요.");
										String find = scan.nextLine();
										int roomNumber = numberChange(find);
										if (roomNumber / 100 >= 2 && roomNumber / 100 <= 5 && roomNumber % 100 >= 1
												&& roomNumber % 100 <= 20) {
											System.out.println("분실물을 입력해주세요.");
											String lostItem = scan.nextLine();
											Room r = request.returnRoom(roomNumber);
											management.guestLostItem(r, lostItem);
										} else { // 방번호를 201~520 으로 입력하지 않은 경우
											System.out.println("잘못된 입력입니다. 올바른 번호를 입력해주세요.");
										}
									} else if (agree.equals("2")) {
										break;
									}
								}
								request.floorCleanComplete(input);
							} else { // 층수를 2~5로 입력하지 않은 경우
								System.out.println("잘못된 입력입니다. 올바른 층수를 입력해주세요.");
								break;
							}
						}
					} else { // 1,2,3번이 아닌 번호를 입력한경우
						System.out.println("잘못된 입력입니다. 올바른 번호를 입력해주세요.");
					}
				}

				else if (employeeRequest.equals("2") && (role.equals("데스크 직원") || role.equals("매니저"))) { // 예약관련 메뉴
					System.out.println("1. 예약 등록\n2. 예약 취소");
					input = scan.nextLine();
					String roomType = "";
					if (input.equals("1")) { // 예약 등록 절차
						System.out.println("방 타입을 입력해주세요. (싱글, 더블, 디럭스, 스위트)");
						roomType = scan.nextLine();
						if (roomType.equals("싱글") || roomType.equals("더블") || roomType.equals("디럭스")
								|| roomType.equals("스위트")) {
							request.emptyRoom(roomType);
							System.out.println("예약할 방 번호를 입력해주세요.");
							String roomNumberStr = scan.nextLine();
							int roomNumber = numberChange(roomNumberStr);
							if (roomNumber / 100 >= 2 && roomNumber / 100 <= 5 && roomNumber % 100 >= 1
									&& roomNumber % 100 <= 20) {
								System.out.println("예약자 성함을 입력해주세요.");
								String name = scan.nextLine();
								System.out.println("예약자 전화번호를 입력해주세요.");
								String phoneNumber = scan.nextLine();

								if (management.guestExist(name, phoneNumber) == false) { // 신규고객일시 등록
									management.guestRegist(new Guest(name, phoneNumber));
								}

								request.roomReservation(roomNumber, name, phoneNumber, roomType);
							} else {
								System.out.println("잘못된 입력입니다. 올바른 번호를 입력해주세요.");
							}
						} else { // 방번호를 201~520 으로 입력하지 않은 경우
							System.out.println("잘못된 입력입니다. 올바른 등급을 입력해주세요.");
						}
					}

					else if (input.equals("2")) { // 예약 취소 절차
						System.out.println("예약자 성함을 입력해주세요.");
						String name = scan.nextLine();
						System.out.println("예약자 전화번호를 입력해주세요.");
						String phoneNumber = scan.nextLine();
						// 예약자가 있는지?
						while (true) { // 예약취소를 원하는만큼 진행
							request.reservationInfo(name, phoneNumber);

							if (request.guestRoomCheck(name, phoneNumber, "예약") == false) {
								break;
							}

							System.out.println("예약을 취소할 방 번호를 입력해주세요.\n예약 취소 메뉴 나가기 : 999");
							String roomNumberStr = scan.nextLine();
							int roomNumber = numberChange(roomNumberStr);
							if (roomNumber == 999) { // 탈출코드(예약취소를 그만하려고 할때)
								break;
							}
							if (roomNumber / 100 >= 2 && roomNumber / 100 <= 5 && roomNumber % 100 >= 1
									&& roomNumber % 100 <= 20) {

								request.roomReservationCancel(roomNumber, name, phoneNumber);
							} else {
								System.out.println("잘못된 입력입니다. 올바른 번호를 입력해주세요.");
							}
						}
					} else { // 메뉴 선택에서 1,2번이 아닌 번호를 입력했을 경우
						System.out.println("잘못된 입력입니다. 올바른 번호를 입력해주세요.");
					}
				}

				else if (employeeRequest.equals("3") && (role.equals("데스크 직원") || role.equals("매니저"))) { // 체크인/워크인
																											// 관련메뉴
					System.out.println("1. 체크인\n2. 워크인");
					input = scan.nextLine();

					if (input.equals("1")) { // 예약되어있는 객실을 원하는 만큼 체크인하는 절차
						while (true) {
							System.out.println("체크인할 방 번호를 입력해주세요.\n체크인 메뉴 나가기 : 999");
							input = scan.nextLine();
							int roomNumber = numberChange(input);
							if (roomNumber == 999) { // 반복문 종료
								break;
							}
							if (roomNumber / 100 >= 2 && roomNumber / 100 <= 5 && roomNumber % 100 >= 1
									&& roomNumber % 100 <= 20) {
								if (request.roomCheck(roomNumber).equals("예약") == false) {
									System.out.println("예약중인 방이 아닙니다. 올바른 정보를 입력해주세요.");
									break;
								}
								System.out.println("예약자 성함을 입력해주세요.");
								String name = scan.nextLine();
								System.out.println("예약자 전화번호를 입력해주세요.");
								String phoneNumber = scan.nextLine();
								request.checkIn(roomNumber, name, phoneNumber);
							} else { // 방번호를 201~520 으로 입력하지 않은 경우
								System.out.println("잘못된 입력입니다. 올바른 번호를 입력해주세요.");
							}
						}
					} else if (input.equals("2")) { // 예약하지 않은 방을 워크인하는 절차
						System.out.println("방 타입을 입력해주세요. (싱글, 더블, 디럭스, 스위트)");
						String roomType = scan.nextLine();
						if (roomType.equals("싱글") || roomType.equals("더블") || roomType.equals("디럭스")
								|| roomType.equals("스위트")) {
							request.emptyRoom(roomType);
							while (true) {
								System.out.println("워크인 할 방 번호를 입력해주세요.");
								String roomNumberStr = scan.nextLine();
								int roomNumber = numberChange(roomNumberStr);
								if (roomNumber / 100 >= 2 && roomNumber / 100 <= 5 && roomNumber % 100 >= 1
										&& roomNumber % 100 <= 20) {
									if (request.roomCheck(roomNumber).equals("빈방")) { // 입력한 호수가 빈 방인지 검사
										System.out.println("고객님 성함을 입력해주세요.");
										String name = scan.nextLine();
										System.out.println("고객님 전화번호를 입력해주세요.");
										String phoneNumber = scan.nextLine();

										if (management.guestExist(name, phoneNumber) == false) { // 신규고객일시 등록
											management.guestRegist(new Guest(name, phoneNumber));
										}

										request.walkIn(roomNumber, name, phoneNumber, roomType);
										break;
									} else { // 빈방아니면 출력
										System.out.println("해당 객실은 빈 방이 아닙니다. 올바른 방 번호를 입력해주세요.");
									}

								} else { // 방번호를 201~520 으로 입력하지 않은 경우
									System.out.println("잘못된 입력입니다. 올바른 번호를 입력해주세요.");
									break;
								}
							}
						} else { // 등급 잘못쳤을때
							System.out.println("잘못된 입력입니다. 올바른 등급을 입력해주세요.");
						}
					} else { // 1,2번이 아닌 번호를 선택했을 경우
						System.out.println("잘못된 입력입니다. 올바른 번호를 입력해주세요.");
					}
				}

				else if (employeeRequest.equals("4") && (role.equals("데스크 직원") || role.equals("매니저"))) { // 체크아웃 관련 메뉴
					boolean checkOutEnd = false;
					while (checkOutEnd == false) {

						System.out.println("고객의 성함을 입력해주세요.");
						String name = scan.nextLine();
						System.out.println("고객의 전화번호를 입력해주세요.");
						String phoneNumber = scan.nextLine();
						if (management.guestExist(name, phoneNumber)) {
							while (true) { // 체크아웃을 원하는만큼 진행
								request.occupiedRoom(name, phoneNumber);
								if (request.guestRoomCheck(name, phoneNumber, "투숙") == false) { // 해당 고객이 투숙중인 방이 없을 경우
									checkOutEnd = true;
									break;
								}
								System.out.println("체크아웃 할 방 번호를 입력해주세요.\n체크아웃 메뉴 나가기 : 999");
								String roomNumberStr = scan.nextLine();
								int roomNumber = numberChange(roomNumberStr);
								if (roomNumber == 999) { // 탈출코드(체크아웃을 그만하려고 할때)
									checkOutEnd = true;
									break;
								}
								if (roomNumber / 100 >= 2 && roomNumber / 100 <= 5 && roomNumber % 100 >= 1
										&& roomNumber % 100 <= 20) {
									Room r = request.returnRoom(roomNumber);
									management.guestTotalSpent(r);

									request.checkOut(roomNumber, name, phoneNumber);
								} else { // 방번호 이상하게쳤을때
									System.out.println("잘못된 입력입니다. 올바른 번호를 입력해주세요.");
								}
							}
						} else { // 해당 고객이 고객 배열에 존재하지 않는 경우
							System.out.println("올바른 고객 정보를 입력해 주세요.");
						}
					}
				} else if (employeeRequest.equals("5") && role.equals("매니저")) { // 고객 정보관리 관련메뉴
					System.out.println("1. 고객 정보 확인\n2.고객 정보 삭제");
					input = scan.nextLine();

					if (input.equals("1")) { // 고객 배열 전체 고객 출력
						management.allGuestInfo();
					} else if (input.equals("2")) { // 고객 정보 삭제처리
						System.out.println("고객의 성함을 입력해주세요.");
						String name = scan.nextLine();
						System.out.println("고객의 전화번호를 입력해주세요.");
						String phoneNumber = scan.nextLine();
						if (management.guestExist(name, phoneNumber)) { // 고객이 고객 배열에 존재하는지 체크
							boolean disposableGuest = request.disposableGuest(name, phoneNumber);
							if (disposableGuest) { // 고객의 정보로 된 방이 단 하나도 없을 경우
								System.out.println("고객의 정보를 삭제했습니다.");
								management.guestDelete(name, phoneNumber);
							} else { // 고객의 정보로 된 방이 존재해서 삭제하면 안되는 경우
								System.out.println("해당 고객의 정보를 삭제할 수 없습니다.");
							}
						} else { // 고객이 고객배열에 없는 경우
							System.out.println("올바른 고객 정보를 입력해 주세요.");
						}
					} else { // 1,2번이 아닌 메뉴를 골랐을 경우
						System.out.println("잘못된 입력입니다. 올바른 번호를 입력해주세요.");
					}

				} else if (employeeRequest.equals("9")) { // 로그아웃
					System.out.println("성공적으로 로그아웃 되었습니다.");
					logIn = true;
				} else if (employeeRequest.equals("9999") && role.equals("매니저")) { // 프로그램 강제 종료
					System.exit(0);
				} else {
					System.out.println("잘못된 입력입니다. 올바른 번호를 입력해주세요.");
				}
			}
		}
	}
}