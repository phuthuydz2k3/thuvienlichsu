package com.app.thuvienlichsu.crawl.crawlers;

import com.app.thuvienlichsu.base.Model;
import com.app.thuvienlichsu.base.NhanVatModel;
import com.app.thuvienlichsu.util.Config;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;


public class NhanVatCrawler extends SCrawler implements ICrawler {

    public static List<List<String>> crawlTable(Element table)  {
        List<List<String>> infobox = new ArrayList<>();
        Elements rows = table.select("tr");
        for (Element row : rows) {
            String label = row.select("th[scope=row], th[colspan=2]").text();
            List<String> info = new ArrayList<>();
            if (label.isEmpty()) {
                Elements case2td = row.select("td");
                if (case2td.size() == 2) {
                    for (Element td : case2td) {
                        info.add(td.text());
                    }
                    infobox.add(info);
                }  
                continue; 
            } 

            info.add(label);   // Set info[0] as label

            //  Add information to info[1..]
            Elements tabElements = row.select("table");
            if (tabElements.size() > 0) {
                Elements liElements = row.select("td li");
                if (liElements.size() > 0) {
                    // if there are li elements, get their text and concatenate with line breaks
                    for (Element li : liElements) {
                        info.add(li.text());
                    }
                } else {
                    Elements tdElements = tabElements.select("tr td");
                    if (tdElements.size() > 0) info.add(tdElements.text());
                }
            } else {
                Elements brElements = row.select("td br");
                if (brElements.size() > 0) {
                    row = row.select("td").get(row.select("td").size()-1);
                    String tdContent = row.select("td").html();
                    String lines[] = tdContent.split("<br>");
                    for (String line : lines) {
                        info.add(Jsoup.parse(line).text());
                    } 
                } else {
                    Element tdElement = row.selectFirst("td");
                    if (tdElement != null) info.add(tdElement.text());
                }
            }

            List<String> refinedInfo = new ArrayList<>();
            if (info.size() == 1) refinedInfo.addAll(info);
            else {
                String combined = String.join(" \n", info.subList(1, info.size()));
                refinedInfo.add(info.get(0));
                refinedInfo.add(combined);
            }
            infobox.add(refinedInfo);
        }     
        return infobox;
    }

    public void crawlNguoiKeSu() {
        String baseUrl = "https://nguoikesu.com";
        String nhanVatUrl = "/nhan-vat/an-duong-vuong";
        Document doc = null;

        ArrayList<Model> nhanVatList = new ArrayList<>();

        while (nhanVatUrl != null) {
//            System.out.println(baseUrl + nhanVatUrl);
            boolean isConnected = false;
            while (!isConnected) {
                try {
                    doc = Jsoup.connect(baseUrl+nhanVatUrl)
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
            
            // Collecting data
            String name = doc.selectFirst("div.page-header > h2").text();
            NhanVatModel nhanVat = new NhanVatModel(name);

            int lastSlashIndex = nhanVatUrl.lastIndexOf("/");   // Get the text after last slash of url as nhanVatCode
            String nhanVatCode = nhanVatUrl.substring(lastSlashIndex + 1);
            nhanVat.setCode(nhanVatCode);

            // Get table
            
            Element table = doc.selectFirst("table.infobox"); // select the table element with class "infobox"
            if (table != null) {
                // Map<String, ArrayList<String>> infoMap = new HashMap<String, ArrayList<String>>();
                nhanVat.setThongTin(crawlTable(table));
            }
            

            //  Scrape description
            List<String> info = new ArrayList<>();
            Element firstPTag = doc.selectFirst("p");
            if (firstPTag != null)  {
                String text = firstPTag.wholeText();
                info.add(text);
                Element nextPTag = firstPTag.nextElementSibling();
                while (nextPTag != null && !nextPTag.tagName().equals("h2")) {
                    text = nextPTag.wholeText();
                    if (!text.isEmpty()) info.add(text);
                    nextPTag = nextPTag.nextElementSibling();
                }
                nhanVat.setMoTa(info);
            }

            //  Search for relative figures
            Set<String> nhanVatLienQuan = new HashSet<>();
            Set<String> diaDanhLienQuan = new HashSet<>();
            Set<String> thoiKyLienQuan = new HashSet<>();
            Elements aTags = doc.select("div.com-content-article__body a");
            for (Element aTag : aTags) {
                String href = aTag.attr("href");
                if (href.isEmpty() || href.startsWith("#")) continue;
                String code = href.substring(href.lastIndexOf("/") + 1);
                if (href.contains("nhan-vat")) {
                    nhanVatLienQuan.add(code);
                } else if (href.contains("dia-danh")) {
                    diaDanhLienQuan.add(code);
                } else if (href.contains("dong-lich-su")) {
                    thoiKyLienQuan.add(code);
                }
            }
            nhanVat.setCacNhanVatLienQuan(nhanVatLienQuan);
            nhanVat.setCacDiTichLienQuan(diaDanhLienQuan);
            nhanVat.setCacThoiKyLienQuan(thoiKyLienQuan);

            // Add scapred nhanvat to list
            nhanVatList.add(nhanVat);

            // visit next page
            Element nextPage = doc.selectFirst("a.btn.btn-sm.btn-secondary.next");
            if (nextPage != null) {
                nhanVatUrl = nextPage.attr("href");
            } else {
                nhanVatUrl = null;
            }
        }
        
        // Write to JSON file
        writeJson(Config.TEMP_NHAN_VAT_FILENAME, nhanVatList);
    }

    public void crawlWiki()  {
        //  Input from JSON back to Objects
        List<NhanVatModel> nhanVatList = loader(Config.TEMP_NHAN_VAT_FILENAME, new TypeToken<List<NhanVatModel>>() {});
        String baseUrl = "https://vi.wikipedia.org/wiki/";
        String nhanVatUrl = "";
        Document doc;

        for (NhanVatModel nhanVat : nhanVatList) {
//            System.out.println(baseUrl+nhanVatUrl);
            nhanVatUrl = nhanVat.getTenModel().replace(" ", "_");
            try {
                doc =  Jsoup.connect(baseUrl+nhanVatUrl).get();
            } catch (IOException e ){
                continue;
            }

            Element table = doc.selectFirst("div.mw-parser-output table.infobox");
            if (table != null) {
                List<List<String>> infoBox = crawlTable(table);
                infoBox = merge(nhanVat.getThongTin(), infoBox);
                nhanVat.setThongTin(infoBox);
            }
        }

        // Write to JSON file
        List<Model> modelList = new ArrayList<>(nhanVatList);
        writeJson(Config.TEMP_NHAN_VAT_FILENAME, nhanVatList);
    }

    public static boolean shouldMerge(List<String> info1, List<String> info2) {
//        StringBuilder stringBuilder = new StringBuilder();
//        for (int i = 1; i < info1.size(); i++) {
//            stringBuilder.append(info1.get(i));
//        }
//        String str1 = stringBuilder.toString();
//        stringBuilder = new StringBuilder();
//        for (int i = 1; i < info2.size(); i++) {
//            stringBuilder.append(info2.get(i));
//        }
//        String str2 = stringBuilder.toString();

        String str1 = info1.get(1);
        str1.replaceAll(" \n", "");
        String str2 = info2.get(1);
        str2.replaceAll(" \n", "");

        // Tạo một HashMap để lưu số lần xuất hiện của chữ cái và số trong str1
        Map<Character, Integer> occurrencesMap = new HashMap<>();

        // Đếm số lần xuất hiện của chữ cái và số trong str1
        for (char ch : str1.toCharArray()) {
            if (Character.isLetter(ch) || Character.isDigit(ch)) {
                occurrencesMap.put(ch, occurrencesMap.getOrDefault(ch, 0) + 1);
            }
        }

        int str1Count = 0;
        int str2Count = 0;

        // Đếm số lần xuất hiện của chữ cái và số trong str2
        for (char ch : str2.toCharArray()) {
            if (Character.isLetter(ch) || Character.isDigit(ch)) {
                occurrencesMap.put(ch, occurrencesMap.getOrDefault(ch, 0) - 1);
            }
        }

        // Tính tổng số lần xuất hiện của chữ cái và số còn lại trong str1
        for (int count : occurrencesMap.values()) {
            if (count > 0) str1Count += count;
            else str2Count -= count;
        }

        int totalDiff = str1Count + str2Count;
        float rate = (float) 0.3;
        if (Math.max(str1.length(),str2.length()) < 20) rate = (float) 0.8;
        if ((float)totalDiff > (float)(rate*str2.length())) return true;
        if ((float)totalDiff > (float)(rate*str1.length())) return true;
        return false;
    }

    public static List<List<String>> merge(List<List<String>> table1, List<List<String>> table2) {
        if (table1.isEmpty()) return table2;
        int ttcIndex1 = -1;
        int ttcIndex2 = -1;
        for (int i = 0; i < table1.size(); i++)  
            if (table1.get(i).get(0).contains("Thông tin")) {
                ttcIndex1 = i;
                break;
            } 
        for (int i = 0; i < table2.size(); i++)  
            if (table2.get(i).get(0).contains("Thông tin")) {
                ttcIndex2 = i;
                break;
            }
        if (ttcIndex2>ttcIndex1+1 && Math.min(ttcIndex1, ttcIndex2) > 0) {
            table1.subList(0, ttcIndex1-1).clear();
            table1.addAll(0, table2.subList(0, ttcIndex2-1));
            ttcIndex1 = ttcIndex2;
        }
        for (int i = ttcIndex1+1; i < table1.size(); i++) {
            if (table1.get(i).size() == 1 || table1.get(i).get(1).isEmpty()) continue;
            for (int j = ttcIndex2+1; j < table2.size(); j++) {
                if (table1.get(i).get(0).equalsIgnoreCase(table2.get(j).get(0))) {
                    if (table2.get(j).size() == 1 || table2.get(j).get(1).isEmpty()) break;
                    if (shouldMerge(table1.get(i), table2.get(j)) ){
                        int lastIndex1 = table1.get(i).size() - 1;
                        int lastIndex2 = table2.get(j).size() - 1;
                        table1.get(i).set(lastIndex1, table1.get(i).get(lastIndex1) + " \n(Theo nguoikesu.com)");
                        table2.get(j).set(lastIndex2, table2.get(j).get(lastIndex2) + " \n(Theo wikipedia.org)");
                        table2.get(j).remove(0);
//                        table1.get(i).add("(Theo nguoikesu.com)");
//                        table2.get(j).add("(Theo wikipedia.org)");
//                        table2.get(j).set(0, "</td> <td>");
                        table1.get(i).addAll(table2.get(j));
                    }
                    break;
                }
            }
        }
        return table1;     
    }

    public List<Model> crawlPages() {
        crawlNguoiKeSu();
        crawlWiki();
        List<NhanVatModel> nhanVatList = loader(Config.TEMP_NHAN_VAT_FILENAME, new TypeToken<List<NhanVatModel>>() {});
        List<Model> modelList = new ArrayList<>(nhanVatList);
        // Write to JSON file
        return modelList;
    }

    public void createNhanVatJson()
    {
        List<Model> figures = crawlPages();
        writeJson(Config.TEMP_NHAN_VAT_FILENAME, figures);
    }

    public static void main(String[] args) {
        NhanVatCrawler test = new NhanVatCrawler();
        List<Model> figures = test.crawlPages();
        test.writeJson(Config.TEMP_NHAN_VAT_FILENAME, figures);
    }
}
