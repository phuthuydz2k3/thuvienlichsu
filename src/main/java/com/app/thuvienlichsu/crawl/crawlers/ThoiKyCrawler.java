package com.app.thuvienlichsu.crawl.crawlers;

import com.app.thuvienlichsu.base.Model;
import com.app.thuvienlichsu.base.ThoiKyModel;
import com.app.thuvienlichsu.util.Config;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



public class ThoiKyCrawler extends SCrawler implements ICrawler {
    public List<Model> crawlPages() {
        String baseUrl = Config.THOI_KY_WEBPAGE;
        String thoiKyUrl = "/dong-lich-su";
        List<Model> eraList = new ArrayList<>();
        Document doc;
        try {
            doc = Jsoup.connect(baseUrl+thoiKyUrl).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //  Lấy các elements trong sidebar
        Elements liElements = doc.selectFirst("ul.mod-articlescategories.categories-module.mod-list").select("li");

        int id = 0;
        for (Element liElement : liElements) {
            thoiKyUrl = liElement.selectFirst("a").attr("href");
            try {
                doc = Jsoup.connect(baseUrl+thoiKyUrl).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//            System.out.println(baseUrl+thoiKyUrl);  // Console log

            //  Get eraName
            String eraName = doc.selectFirst("h1").text();

            //  Get eraCode
            String eraCode = thoiKyUrl.substring(thoiKyUrl.lastIndexOf("/") + 1);

            //  Get description
            Elements dElements = doc.selectFirst("div.category-desc.clearfix").select("p");
            ArrayList<String> description = new ArrayList<>();
            for (Element dElement : dElements) {
                description.add(dElement.wholeText());
            }

            //  Get all links related to thoi ky
            ArrayList<String> links = new ArrayList<>();
            String nextPageUrl = "";
            while (nextPageUrl != null) {
                Elements h2Elements = doc.select("div.page-header h2");
                for (Element h2Element : h2Elements) {
                    links.add(h2Element.selectFirst("a").attr("href"));
                }
                Element nextPage = doc.selectFirst("a[aria-label=Đi tới tiếp tục trang].page-link");
                if (nextPage != null) nextPageUrl=nextPage.attr("href");
                else {
                    nextPageUrl = null;
                    continue;
                }
                try {
                    String sanitizedUrl = nextPageUrl.replace("|", "%7C");
                    doc = Jsoup.connect(baseUrl+sanitizedUrl).get();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            Set<String> historicalFiguresLinked = new HashSet<>();
            Set<String> historicalDestinationsLinked = new HashSet<>();
            //  Traverse all links to find relative figures
            for (String link : links) {
                ArrayList<String> hrefs = getRelatives(baseUrl+link);

                for (String href : hrefs) {
                    String code = href.substring(href.lastIndexOf("/") + 1);
                    if (href.contains("nhan-vat")) {
                        historicalFiguresLinked.add(code);
                    } else if (href.contains("dia-danh")) {
                        historicalDestinationsLinked.add(code);
                    }
                }
            }

            Model era = new ThoiKyModel(eraName, description, eraCode
                    , historicalFiguresLinked, historicalDestinationsLinked);
            era.setId(++id);
            eraList.add(era);
        }

        return eraList;
    }

    // Lay thong tin nhan vat tu trang gom nhieu trang con
    private ArrayList<String> getRelatives(String url) {
        ArrayList<String> hrefs = new ArrayList<>();
        Document doc = null;

        boolean isConnected = false;
        while (!isConnected) {
            try {
                doc = Jsoup.connect(url)
                        .timeout(2000000).get();
                isConnected = true; // Connection successful, exit the loop
            } catch (IOException e) {
                System.err.println("Failed to connect: " + e.getMessage());
                // Wait for a while before trying again
                try {
                    Thread.sleep(5000); // 5 seconds
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }

        //  Find in article body
        Element articleBody = doc.selectFirst("div.com-content-article__body");
        if (articleBody != null) {
            Elements aTags = doc.selectFirst("div.com-content-article__body").select("a");
            for (Element aTag : aTags) {
                String href = aTag.attr("href");
                if (href.isEmpty() || href.startsWith("#")) continue;
                hrefs.add(href);
            }
        }

        //  Find in article sidebar
        Element isNhanVat = doc.selectFirst("div.caption > h3 > a");
        if (isNhanVat != null)
            hrefs.add(isNhanVat.attr("href"));
        return hrefs;
    }

    public void createThoiKyJson()
    {
        List<Model> models = crawlPages();
        writeJson(Config.TEMP_THOI_KY_FILENAME, models);
    }
    // Testing
    public static void main(String[] args) {
        ThoiKyCrawler test = new ThoiKyCrawler();
        List<Model> models = test.crawlPages();
        test.writeJson(Config.TEMP_THOI_KY_FILENAME, models);
    }
}
