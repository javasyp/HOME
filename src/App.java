import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controller.ArticleController;
import controller.MemberController;
import dto.Article;
import dto.Member;

public class App {
	
	List<Article> articles;
	List<Member> members;
	
	public App() {	// 생성자 호출 (의도적으로 실행하자마자 리스트 생성)
		articles = new ArrayList<>();
		members = new ArrayList<>();
	}
	
	public void start() {
		System.out.println("== 프로그램 시작 ==");
		
		Scanner sc = new Scanner(System.in);

		// 멤버 컨트롤러 생성
		// 매개변수와 인자 활용하여 members와 sc 넘기기 - 멤버 컨트롤러에서 사용하기 위함
		MemberController memberController = new MemberController(members, sc);
		
		// 아티클 컨트롤러 생성
		ArticleController articleController = new ArticleController(articles, sc);
		
		articleController.makeTestData();
		
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
			
			// 회원가입
			if (command.equals("member join")) {
				memberController.doJoin();
				
			// 작성
			} else if (command.equals("article write")) {
				articleController.doWrite();
			
			// 목록
			} else if (command.startsWith("article list")) {
				articleController.showList(command);
				
			// 세부사항
			} else if (command.startsWith("article detail")) {
				articleController.showDetail(command);
			
			// 수정
			}  else if (command.startsWith("article modify")) {
				articleController.doModify(command);
				
			// 삭제
			} else if (command.startsWith("article delete")) {
				articleController.doDelete(command);
				
			} else {
				System.out.println("존재하지 않는 명령어입니다.");
			}
		}
		
		System.out.println("== 프로그램 끝 ==");
		
		sc.close();
		
	}

}