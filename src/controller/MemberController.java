package controller;

import java.util.List;
import java.util.Scanner;

import dto.Member;
import util.Util;

public class MemberController extends Controller {
	private List<Member> members;
	private Scanner sc;
	private String command;
	private String actionMethodName;
	
	public MemberController(List<Member> members, Scanner sc) {
		this.members = members;
		this.sc = sc;
	}
	
	public void doAction(String actionMethodName, String command) {
		this.command = command;
		this.actionMethodName = actionMethodName;
		
		switch (actionMethodName) {
		case "join":
			doJoin();
			break;
		}
	}
	
	int lastMemberId = 0;
	
	// 회원가입
	public void doJoin() {
		int id = lastMemberId + 1;
		
		String regDate = Util.getNowDateTimeStr();
		String loginId = null;
		
		while (true) {
			System.out.print("아이디 : ");
			loginId = sc.nextLine();
			
			// 아이디 중복 체크
			if (isJoinableLoginId(loginId) == false) {
				System.out.println("이미 사용 중인 아이디입니다.");
				continue;
			}
			
			break;	// true일 경우 빠져나오기
		}
		
		String loginPw = null;
		String loginPwConfirm = null;
		
		while (true) {
			// 비밀번호 확인
			System.out.print("비밀번호 : ");
			loginPw = sc.nextLine();
			
			System.out.print("비밀번호 확인 : ");
			loginPwConfirm = sc.nextLine();
			
			if (loginPw.equals(loginPwConfirm) == false) {
				System.out.println("비밀번호를 확인해 주세요.");
				continue;
			}
			
			break;	// 일치하면 빠져나오기
		}
		
		System.out.print("이름 : ");
		String name = sc.nextLine();
		
		Member member = new Member(id, regDate, regDate, loginId, loginPw, name);
		members.add(member);

		System.out.printf("%d번 회원이 가입되었습니다.\n", id);
		lastMemberId++;
		
	}
	
	// 아이디 중복 체크
	private boolean isJoinableLoginId(String loginId) {
		int index = getMemberIndexByloginId(loginId);

		if (index == -1) {	// 찾아봤는데 없던데? 해당 아이디 사용 가능
			return true;
		}
		return false;
	}
	
	private int getMemberIndexByloginId(String loginId) {
		int i = 0;
		
		for (Member member : members) {	// 순회
			if (member.loginId.equals(loginId)) {	// 지금 입력한 아이디랑 똑같은 아이디 있나?
				return i;	// 있으면(중복이면) i값 반환 (i > -1)
			}
			i++;
		}
		return -1;	// 없으면 -1 반환
	}
		
}
