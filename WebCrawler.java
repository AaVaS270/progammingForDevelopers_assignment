package assignments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler {
	private static final int NUM_THREADS = 10;
	private static final int MAX_PAGES = 100;
	private final Set<URL> visited;
	private final Queue<URL> queue;

	public WebCrawler(URL startUrl) {
	    visited = new HashSet<>();
	    queue = new LinkedList<>();
	    queue.add(startUrl);
	}

	public void start() {
	    ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
	    for (int i = 0; i < NUM_THREADS; i++) {
	        executor.execute(new CrawlerTask());
	    }
	    executor.shutdown();
	    while (!executor.isTerminated()) {
	    }
	}

	private class CrawlerTask implements Runnable {
	    @Override
	    public void run() {
	        while (!queue.isEmpty() && visited.size() < MAX_PAGES) {
	            URL url = queue.poll();
	            if (visited.contains(url)) {
	                continue;
	            }
	            visited.add(url);
	            try {
	                String pageContent = getPageContent(url);
	                System.out.println("Visited page " + url);
	                Set<URL> links = getLinks(pageContent);
	                for (URL link : links) {
	                    if (!visited.contains(link)) {
	                        queue.add(link);
	                    }
	                }
	            } catch (IOException e) {
	                System.err.println("Failed to crawl " + url + ": " + e.getMessage());
	            }
	        }
	    }

	    private String getPageContent(URL url) throws IOException {
	         URLConnection connection = url.openConnection();
	         BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	         StringBuilder builder = new StringBuilder();
	         String line;
	         while ((line = reader.readLine()) != null) {
	             builder.append(line);
	         }
	         reader.close();
	         return builder.toString();
	    }

	    private Set<URL> getLinks(String pageContent) throws IOException {
	         Pattern pattern = Pattern.compile("<a\\s+href=\"([^\"]+)\"\\s*>");
	         Matcher matcher = pattern.matcher(pageContent);
	         Set<URL> links = new HashSet<>();
	         while (matcher.find()) {
	             String link = matcher.group(1);
	             links.add(new URL(link));
	         }
	         return links;
	    }
	}

	public static void main(String[] args) throws Exception {
	    URL startUrl = new URL("https://www.example.com/");
	    WebCrawler crawler = new WebCrawler(startUrl);
	    crawler.start();
	}

}