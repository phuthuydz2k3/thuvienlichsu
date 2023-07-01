package com.app.thuvienlichsu.crawl.crawlers;


import com.app.thuvienlichsu.base.DiTichModel;
import com.app.thuvienlichsu.base.Model;
import com.app.thuvienlichsu.util.Config;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.app.thuvienlichsu.util.UrlDecode.getCodeFromUrl;


public class DiTichCrawler extends SCrawler implements ICrawler {
    @Override
    public List<Model> crawlPages() {
        String baseUrl = Config.DI_TICH_WEBPAGE;
        // List
        List<Model> destinationList = new ArrayList<>();
        Document doc;
        try {
            doc =  Jsoup
                    .connect(baseUrl)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36")
                    .get();
        } catch (IOException e ){
            throw new RuntimeException(e);
        }

        Elements nextElements = doc.select("a.btn.btn-sm.btn-secondary.next");
        String completeUrl = "";

        // Looking for the "Next"
        int id = 0;
        while (baseUrl.compareTo(completeUrl) != 0 && (!nextElements.isEmpty())) {
            // Getting the "Next"
            Element nextElement = nextElements.first();
            // Extracting the relative URL of the next page
            String relativeUrl = nextElement.attr("href");
            completeUrl = "https://nguoikesu.com" + relativeUrl;
            // Debugger
//            System.out.println(completeUrl);
            int maxRetries = 3;
            int retryCount = 0;
            boolean success = false;

            while (!success && retryCount < maxRetries) {
                try {
                    doc = Jsoup
                            .connect(completeUrl)
                            .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36")
                            .get();

                    success = true; // Request succeeded
                } catch (IOException e) {
                    System.out.println("Request failed. Retrying...");
                    retryCount++;

                    // Wait for a short duration before retrying
                    try {
                        Thread.sleep(1000); // Adjust the delay as needed
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            if (!success) {
                throw new RuntimeException("Exceeded maximum retries. Request failed.");
            }


            // Collect data
            // Get title
            String title = null;
            Element titleElement = doc.selectFirst("h2");
            if (titleElement != null) {
                title = titleElement.text();
            }

            List<String> texts = new ArrayList<>();

            Elements desElements = doc.select("div.com-content-article__body > p");
            for(Element element : desElements) {
                String text = element.text();
                text = text.replaceAll("\"", "'");
                texts.add(text);
            }

            // Relative person
            Set<String> nhanVatLienQuan = new HashSet<>();
            Elements refElements = desElements.select("a[href*=/nhan-vat/]");
            for (Element refElement : refElements) {
                String name = refElement.attr("href");
                nhanVatLienQuan.add(getCodeFromUrl(name));
            }

            Model destination =  new DiTichModel(title, texts, getCodeFromUrl(completeUrl), nhanVatLienQuan);
            destination.setId(++id);
            destinationList.add(destination);
            nextElements = doc.select("a.btn.btn-sm.btn-secondary.next");
        }

        return destinationList;
    }

    public void createDiTichJson()
    {
        List<Model> locationList = crawlPages();
        writeJson(Config.TEMP_DI_TICH_FILENAME, locationList);
    }

    // Testing
    public static void main(String[] args) {
        DiTichCrawler test = new DiTichCrawler();
        List<Model> locationList = test.crawlPages();
        test.writeJson(Config.TEMP_DI_TICH_FILENAME, locationList);
//        test.writeHTML(Config.DI_TICH_HTML, locationList);

//        DiTichCrawler test = new DiTichCrawler();
        List<DiTichModel> myList = test.loader(Config.TEMP_DI_TICH_FILENAME,  new TypeToken<List<DiTichModel>>() {});
//        List<Model> newList = new ArrayList<>();
//        newList.addAll(myList);
//        test.writeHTML(Config.DI_TICH_HTML, newList);
    }


}
