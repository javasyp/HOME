import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controller.ArticleController;
import controller.Controller;
import controller.MemberController;
import dto.Article;
import dto.Member;

public class App {
	
	public App() {
	}
	
	public void start() {
		System.out.println("== 프로그램 시작 ==");
		
		Scanner sc = new Scanner(System.in);

		// 멤버 컨트롤러 생성
		// 매개변수와 인자 활용하여 members와 sc 넘기기 - 멤버 컨트롤러에서 사용하기 위함
		MemberController memberController = new MemberController(sc);
		
		// 아티클 컨트롤러 생성
		ArticleController articleController = new ArticleController(sc);
		
		Controller controller;
		
		articleController.makeTestData();
		memberController.makeTestData();
		
		while (true) {
			System.out.print("명령어 > ");
			 
			String command = sc.nextLine().trim();
			
			if (command.length() == 0) {
				System.out.println("명령어를 입력해 주세요.");
				continue;
			}
			
			if (command.equals("exit")) {
				break;
			}
			
			String[] commandDiv = command.split(" ");	// article delete ! / member join
			
			String controllerName = commandDiv[0];	// article / member
			
			if (commandDiv.length == 1) {	// 단어가 하나만 들어왔을 때
				System.out.println("명령어를 확인해 주세요.");
				continue;
			}
			
			String actionMethodName = commandDiv[1];	// list, write, join ··· 등
			
			controller = null;
			
			// 컨트롤러 선택
			if (controllerName.equals("article")) {
				controller = articleController;
			} else if (controllerName.equals("member")) {
				controller = memberController;
			} else {
				System.out.println("존재하지 않는 명령어입니다.");
				continue;
			}
			
			// 실질적인 일 (어떤 건 인자가 있고 어떤 건 없어서 통일시키기 위함)
			controller.doAction(actionMethodName, command);
			
//			if (command.equals("member join")) {
//				memberController.doJoin();
//			} else if (command.equals("article write")) {
//				articleController.doWrite();
//			} else if (command.startsWith("article list")) {
//				articleController.showList(command);
//			} else if (command.startsWith("article detail")) {
//				articleController.showDetail(command);
//			} else if (command.startsWith("article modify")) {
//				articleController.doModify(command);
//			} else if (command.startsWith("article delete")) {
//				articleController.doDelete(command);
//			} else {
//				System.out.println("존재하지 않는 명령어입니다.");
//			}
		}
		
		System.out.println("== 프로그램 끝 ==");
		
		sc.close();
		
	}

}