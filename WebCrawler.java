package assignments;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebCrawler {
    
    private final int MAX_PAGES_TO_SEARCH = 100;
    private Set<URL> visitedPages;
    private Queue<URL> pagesToVisit;
    private ExecutorService executor;
    
    public WebCrawler() {
        visitedPages = new HashSet<URL>();
        pagesToVisit = new LinkedList<URL>();
        executor = Executors.newFixedThreadPool(4);
    }
    
    public void search(String url, String searchWord) {
        URL startingUrl;
        try {
            startingUrl = new URL(url);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        
        pagesToVisit.add(startingUrl);
        while (visitedPages.size() < MAX_PAGES_TO_SEARCH && !pagesToVisit.isEmpty()) {
            URL currentUrl = pagesToVisit.poll();
            executor.execute(new PageSearchTask(currentUrl, searchWord));
            visitedPages.add(currentUrl);
            System.out.println("Visited page " + currentUrl);
            findLinks(currentUrl);
        }
        
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private void findLinks(URL url) {
        try {
            Document doc = Jsoup.connect(url.toString()).get();
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                String linkUrl = link.absUrl("href");
                URL newUrl = new URL(linkUrl);
                if (!visitedPages.contains(newUrl)) {
                    pagesToVisit.add(newUrl);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    static class PageSearchTask implements Runnable {
        private URL url;
        private String searchWord;
        
        public PageSearchTask(URL url, String searchWord) {
            this.url = url;
            this.searchWord = searchWord;
        }
        
        public void run() {
            try {
                Document doc = Jsoup.connect(url.toString()).get();
                String text = doc.body().text();
                if (text.toLowerCase().contains(searchWord.toLowerCase())) {
                    System.out.println(searchWord + " found on page " + url);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) {
        WebCrawler crawler = new WebCrawler();
        crawler.search("http://www.example.com", "example");
    }
}
