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
			 
			String command = sc.nextLine();
			
			if (command.length() == 0) {
				System.out.println("명령어를 입력해주세요");
				continue;
			}
			
			if (command.equals("exit")) {
				break;
			}
			
			if (command.equals("article list")) {
				if (articles.size() == 0) {
					System.out.println("게시글이 없습니다.");					
				} else {
					System.out.println("번호 //  제목");
					for (int i = articles.size() - 1; i >= 0; i--) {
						Article article = articles.get(i);
						System.out.printf("  %d  //  %s  \n", article.id, article.title);
					}
				}
				
			} else if (command.equals("article write")) {
				int id = lastArticleId + 1;
				
				System.out.print("제목 : ");
				String title = sc.nextLine();
				
				System.out.print("내용 : ");
				String body = sc.nextLine();
				
				Article article = new Article(id, title, body);
				articles.add(article);

				System.out.printf("%d번 글이 생성되었습니다.\n", id);
				lastArticleId++;

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
	String title;
	String body;
	
	Article(int id, String title, String body) {
		this.id = id;
		this.title = title;
		this.body = body;
	}
}