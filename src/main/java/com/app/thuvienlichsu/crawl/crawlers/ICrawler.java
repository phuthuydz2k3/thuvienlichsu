package com.app.thuvienlichsu.crawl.crawlers;

import com.app.thuvienlichsu.base.Model;

import java.util.List;


public interface ICrawler {
    public List<Model> crawlPages();
//    public void writeJson(String fileName, List<Model> models);
    public void writeJson(String fileName, List<? extends Model> models);
}
