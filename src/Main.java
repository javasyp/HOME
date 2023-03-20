import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		System.out.println("== 프로그램 시작 ==");
		
		Scanner sc = new Scanner(System.in);
		
		List<Article> articles = new ArrayList<>();
		
		int lastArticleId = 0;
		
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
			
			// 목록
			if (command.equals("article list")) {
				if (articles.size() == 0) {
					System.out.println("게시글이 없습니다.");					
				} else {
					System.out.println("번호 //  제목   //  조회  ");
					for (int i = articles.size() - 1; i >= 0; i--) {
						Article article = articles.get(i);
						System.out.printf("  %d  //  %s  //  %d  \n", article.id, article.title, article.hit);
					}
				}
			
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
			
			// 세부사항
			} else if (command.startsWith("article detail")) {
				String[] cmdDiv = command.split(" ");
				
				if (cmdDiv.length < 3) {
					System.out.println("명령어를 확인해 주세요.");
					continue;
				}
				
				int id = Integer.parseInt(cmdDiv[2]);
				
				Article foundArticle = null;

				for (int i = 0; i < articles.size(); i++) {
					Article article = articles.get(i);
					
					if (article.id == id) {
						foundArticle = article;
						break;
					}
				}
				
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
				
				Article foundArticle = null;

				for (int i = 0; i < articles.size(); i++) {
					Article article = articles.get(i);
					
					if (article.id == id) {
						foundArticle = article;
						break;
					}
				}
				
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
				
			// 삭제
			} else if (command.startsWith("article delete")) {
				String[] cmdDiv = command.split(" ");
				
				if (cmdDiv.length < 3) {
					System.out.println("명령어를 확인해 주세요.");
					continue;
				}
				
				int id = Integer.parseInt(cmdDiv[2]);
				
				int foundIndex = -1;

				for (int i = 0; i < articles.size(); i++) {
					Article article = articles.get(i);
					
					if (article.id == id) {
						foundIndex = i;
						break;
					}
				}
				
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
}

class Article {
	int id;
	String regDate;
	String updateDate;
	String title;
	String body;
	int hit;
	
	Article(int id, String regDate, String updateDate, String title, String body) {
		this.id = id;
		this.regDate = regDate;
		this.updateDate = updateDate;
		this.title = title;
		this.body = body;
		this.hit = 0;
	}
}