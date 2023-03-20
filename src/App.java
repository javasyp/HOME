import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controller.MemberController;
import dto.Article;
import dto.Member;
import util.Util;

public class App {
	
	List<Article> articles;
	List<Member> members;
	
	public App() {	// 생성자 호출 (의도적으로 실행하자마자 리스트 생성)
		articles = new ArrayList<>();
		members = new ArrayList<>();
	}
	
	public void start() {
		System.out.println("== 프로그램 시작 ==");
		
		makeTestData();
		
		Scanner sc = new Scanner(System.in);
		
		int lastArticleId = 3;

		// 멤버 컨트롤러 생성
		// 매개변수와 인자 활용하여 members와 sc 넘기기 - 멤버 컨트롤러에서 사용하기 위함
		MemberController memberController = new MemberController(members, sc);
		
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
				int id = lastArticleId + 1;
				
				String regDate = Util.getNowDateTimeStr();
				
				System.out.print("제목 : ");
				String title = sc.nextLine();
				
				System.out.print("내용 : ");
				String body = sc.nextLine();
				
				Article article = new Article(id, regDate, regDate, title, body);
				articles.add(article);
	
				System.out.printf("%d번 글이 생성되었습니다.\n", id);
				lastArticleId++;
			
			// 목록
			} else if (command.startsWith("article list")) {
				if (articles.size() == 0) {
					System.out.println("게시글이 없습니다.");
					continue;
				}
				
				// 검색 기능
				String searchKeyword = command.substring("article list".length()).trim();	// article list 다음 부분의 길이
				
				List<Article> forPrintArticles = articles;
				
				if (searchKeyword.length() > 0) {	// 검색어가 있다!
					System.out.println("검색어 : " + searchKeyword);
					
					forPrintArticles = new ArrayList<>();	// 검색어를 포함하는 요소들만 골라서 새로운 리스트에 담기
					
					for (Article article : articles) {
						if (article.title.contains(searchKeyword)) {	// article의 제목이 searchKeyword를 포함하고 있으면
							forPrintArticles.add(article);
						}
					}
					
					if (forPrintArticles.size() == 0) {
						System.out.println("검색 결과가 없습니다.");
						continue;
					}
				}
				
				// 원본(검색어가 없는 경우) 출력 또는 검색어가 있는 경우 출력
				System.out.println("번호 //  제목   //  조회  ");
				for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
					Article article = forPrintArticles.get(i);
					System.out.printf("  %d  //  %s  //  %d  \n", article.id, article.title, article.hit);
				}
				
			// 세부사항
			} else if (command.startsWith("article detail")) {
				String[] cmdDiv = command.split(" ");
				
				if (cmdDiv.length < 3) {
					System.out.println("명령어를 확인해 주세요.");
					continue;
				}
				
				int id = Integer.parseInt(cmdDiv[2]);
				
				Article foundArticle = getArticleById(id);
				
				if (foundArticle == null) {					
					System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
					continue;
				}
				
				foundArticle.hit++;
				
				System.out.printf("번호 : %d\n", foundArticle.id);
				System.out.printf("작성날짜 : %s\n", foundArticle.regDate);
				System.out.printf("수정날짜 : %s\n", foundArticle.updateDate);
				System.out.printf("제목 : %s\n", foundArticle.title);
				System.out.printf("내용 : %s\n", foundArticle.body);
				System.out.printf("조회 : %s\n", foundArticle.hit);
			
			// 수정
			}  else if (command.startsWith("article modify")) {
				String[] cmdDiv = command.split(" ");
				
				if (cmdDiv.length < 3) {
					System.out.println("명령어를 확인해 주세요.");
					continue;
				}
				
				int id = Integer.parseInt(cmdDiv[2]);
				
				Article foundArticle = getArticleById(id);
				
				if (foundArticle == null) {					
					System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
					continue;
				}
				
				String updateDate = Util.getNowDateTimeStr();
				
				System.out.print("새 제목 : ");
				String newTitle = sc.nextLine();
				
				System.out.print("새 내용 : ");
				String newBody = sc.nextLine();
				
				foundArticle.title = newTitle;
				foundArticle.body = newBody;
				foundArticle.updateDate = updateDate;
				
				System.out.printf("%d번 글을 수정했습니다.\n", id);
				
			// 삭제
			} else if (command.startsWith("article delete")) {
				String[] cmdDiv = command.split(" ");
				
				if (cmdDiv.length < 3) {
					System.out.println("명령어를 확인해 주세요.");
					continue;
				}
				
				int id = Integer.parseInt(cmdDiv[2]);
				
				int foundIndex = getArticleIndexById(id);
				
				if (foundIndex == -1) {					
					System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
					continue;
				} 
				
				articles.remove(foundIndex);
				System.out.printf("%d번 글을 삭제했습니다.\n", id);
				
			} else {
				System.out.println("존재하지 않는 명령어입니다.");
			}
		}
		
		System.out.println("== 프로그램 끝 ==");
		
		sc.close();
		
	}

	// 게시글 인덱스 찾기 (삭제 기능)
	private int getArticleIndexById(int id) {
		int i = 0;
		
		for (Article article : articles) {
			if (article.id == id) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	// 게시글 번호 찾기 (세부사항 및 수정 기능)
	private Article getArticleById(int id) {
//			for (Article article : articles) {
//				if (article.id == id) {
//					return article;
//				}
//			}
//			return null;
		
		int index = getArticleIndexById(id);

		if (index != -1) {
			return articles.get(index);
		}
		return null;
	}

	private void makeTestData() {
		System.out.println("테스트를 위한 데이터를 생성합니다.");
		articles.add(new Article(1, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), "제목1", "내용1", 11));
		articles.add(new Article(2, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), "제목2", "내용2", 22));
		articles.add(new Article(3, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), "제목3", "내용3", 33));
	}
}