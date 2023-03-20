import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	static List<Article> articles = new ArrayList<>();
	static List<Member> members = new ArrayList<>();

	public static void main(String[] args) {
		System.out.println("== 프로그램 시작 ==");
		
		makeTestData();
		
		Scanner sc = new Scanner(System.in);
		
		int lastArticleId = 3;
		int lastMemberId = 0;
		
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
				
				System.out.print("비밀번호 : ");
				String loginPw = sc.nextLine();
				
				System.out.print("이름 : ");
				String name = sc.nextLine();
				
				Member member = new Member(id, regDate, regDate, loginId, loginPw, name);
				members.add(member);

				System.out.printf("%d번 회원이 가입되었습니다.\n", id);
				lastMemberId++;
				
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
	
	// 아이디 중복 체크
	private static boolean isJoinableLoginId(String loginId) {
		int index = getMemberIndexByloginId(loginId);

		if (index == -1) {	// 찾아봤는데 없던데? 해당 아이디 사용 가능
			return true;
		}
		return false;
	}
	
	private static int getMemberIndexByloginId(String loginId) {
		int i = 0;
		
		for (Member member : members) {	// 순회
			if (member.loginId.equals(loginId)) {	// 지금 입력한 아이디랑 똑같은 아이디 있나?
				return i;	// 있으면(중복이면) i값 반환 (i > -1)
			}
			i++;
		}
		return -1;	// 없으면 -1 반환
	}

	
	// 게시글 인덱스 찾기 (삭제 기능)
	private static int getArticleIndexById(int id) {
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
	private static Article getArticleById(int id) {
//		for (Article article : articles) {
//			if (article.id == id) {
//				return article;
//			}
//		}
//		return null;
		
		int index = getArticleIndexById(id);

		if (index != -1) {
			return articles.get(index);
		}
		return null;
	}

	private static void makeTestData() {
		System.out.println("테스트를 위한 데이터를 생성합니다.");
		articles.add(new Article(1, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), "제목1", "내용1", 11));
		articles.add(new Article(2, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), "제목2", "내용2", 22));
		articles.add(new Article(3, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), "제목3", "내용3", 33));
	}
}

class Member {
	int id;
	String regDate;
	String updateDate;
	String loginId;
	String loginPw;
	String name;
	
	Member(int id, String regDate, String updateDate, String loginId, String loginPw, String name) {
		this.id = id;
		this.regDate = regDate;
		this.updateDate = updateDate;
		this.loginId = loginId;
		this.loginPw = loginPw;
		this.name = name;
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
		this(id, regDate, updateDate, title, body, 0);
	}
	
	Article(int id, String regDate, String updateDate, String title, String body, int hit) {
		this.id = id;
		this.regDate = regDate;
		this.updateDate = updateDate;
		this.title = title;
		this.body = body;
		this.hit = hit;
	}
}