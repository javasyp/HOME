package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dto.Article;
import util.Util;

public class ArticleController extends Controller {
	private List<Article> articles;
	private Scanner sc;
	private String command;
	private String actionMethodName;
	
	int lastArticleId = 3;
	
	public ArticleController(Scanner sc) {
		this.articles = new ArrayList<>();	// 각각 컨트롤러 내부에 있는 A.L 사용
		this.sc = sc;
	}
	
	public void doAction(String actionMethodName, String command) {
		this.command = command;
		this.actionMethodName = actionMethodName;
		
		switch (actionMethodName) {
		case "write":
			doWrite();
			break;
		case "list":
			showList();
			break;
		case "detail":
			showDetail();
			break;
		case "modify":
			doModify();
			break;
		case "delete":
			doDelete();
			break;
		default:
			System.out.println("해당 기능은 사용할 수 없습니다.");
			break;
		}
	}
	
	// 작성
	private void doWrite() {
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
	}
	
	// 목록
	private void showList() {
		if (articles.size() == 0) {
			System.out.println("게시글이 없습니다.");
			return;
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
				return;
			}
		}
		
		// 원본(검색어가 없는 경우) 출력 또는 검색어가 있는 경우 출력
		System.out.println("번호 //  제목   //  조회  ");
		for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
			Article article = forPrintArticles.get(i);
			System.out.printf("  %d  //  %s  //  %d  \n", article.id, article.title, article.hit);
		}
			
	}
	
	// 세부사항
	private void showDetail() {
		String[] cmdDiv = command.split(" ");
		
		if (cmdDiv.length < 3) {
			System.out.println("명령어를 확인해 주세요.");
			return;
		}
		
		int id = Integer.parseInt(cmdDiv[2]);
		
		Article foundArticle = getArticleById(id);
		
		if (foundArticle == null) {					
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
			return;
		}
		
		foundArticle.hit++;
		
		System.out.printf("번호 : %d\n", foundArticle.id);
		System.out.printf("작성날짜 : %s\n", foundArticle.regDate);
		System.out.printf("수정날짜 : %s\n", foundArticle.updateDate);
		System.out.printf("제목 : %s\n", foundArticle.title);
		System.out.printf("내용 : %s\n", foundArticle.body);
		System.out.printf("조회 : %s\n", foundArticle.hit);
	}
	
	// 수정
	private void doModify() {
		String[] cmdDiv = command.split(" ");
		
		if (cmdDiv.length < 3) {
			System.out.println("명령어를 확인해 주세요.");
			return;
		}
		
		int id = Integer.parseInt(cmdDiv[2]);
		
		Article foundArticle = getArticleById(id);
		
		if (foundArticle == null) {					
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
			return;
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
	}
	
	// 삭제
	private void doDelete() {
		String[] cmdDiv = command.split(" ");
		
		if (cmdDiv.length < 3) {
			System.out.println("명령어를 확인해 주세요.");
			return;
		}
		
		int id = Integer.parseInt(cmdDiv[2]);
		
		int foundIndex = getArticleIndexById(id);
		
		if (foundIndex == -1) {					
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
			return;
		} 
		
		articles.remove(foundIndex);
		System.out.printf("%d번 글을 삭제했습니다.\n", id);
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

	public void makeTestData() {
		System.out.println("테스트를 위한 데이터를 생성합니다.");
		articles.add(new Article(1, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), "제목1", "내용1", 11));
		articles.add(new Article(2, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), "제목2", "내용2", 22));
		articles.add(new Article(3, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), "제목3", "내용3", 33));
	}
}
