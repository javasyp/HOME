import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		System.out.println("== 프로그램 시작 ==");
		
		Scanner sc = new Scanner(System.in);
		
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
				System.out.println("게시글이 없습니다");
			}
			
			else if (command.equals("article write")) {
				int id = lastArticleId + 1;
				
				System.out.print("제목 : ");
				String title = sc.nextLine();
				
				System.out.print("내용 : ");
				String body = sc.nextLine();

//				System.out.println(title + " / " + body);

				System.out.printf("%d번 글이 생성되었습니다.\n", id);
				lastArticleId++;

			} else {
				System.out.println("존재하지 않는 명령어입니다");
			}
		}
		
		System.out.println("== 프로그램 끝 ==");
		
		sc.close();
	}
}