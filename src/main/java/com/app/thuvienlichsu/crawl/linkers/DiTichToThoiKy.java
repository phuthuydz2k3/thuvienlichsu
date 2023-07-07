
package com.app.thuvienlichsu.crawl.linkers;

import com.app.thuvienlichsu.base.DiTichModel;
import com.app.thuvienlichsu.base.Model;
import com.app.thuvienlichsu.base.ThoiKyModel;
import com.app.thuvienlichsu.crawl.crawlers.DiTichCrawler;
import com.app.thuvienlichsu.crawl.crawlers.ThoiKyCrawler;
import com.app.thuvienlichsu.util.Config;
import com.google.gson.reflect.TypeToken;

import java.util.*;

public class DiTichToThoiKy
{
    private Map<String, List<String>> generateHashMap()
    {
        Map<String, List<String>> hashMap = new HashMap<>();
        ThoiKyCrawler thoiKyCrawler = new ThoiKyCrawler();
        List<ThoiKyModel> thoiKyList = thoiKyCrawler.loader(Config.TEMP_THOI_KY_FILENAME,  new TypeToken<List<ThoiKyModel>>() {});

        for (ThoiKyModel thoiKy : thoiKyList)
        {
            for (String diaDanh : thoiKy.getCacDiTichLienQuan())
            {
                if (!hashMap.containsKey(diaDanh))
                {
                    hashMap.put(diaDanh, new ArrayList<>());
                }
                hashMap.get(diaDanh).add(thoiKy.getCode());
            }
        }

//        System.out.println(hashMap);
        return hashMap;
    }

    public int diaDanhToThoiKy()
    {
        Map<String, List<String>> diaDanhToThoiKy = generateHashMap();
        DiTichCrawler diaDanhCrawler = new DiTichCrawler();
        int size = 0;
        List<DiTichModel> diaDanhList = diaDanhCrawler.loader(Config.TEMP_DI_TICH_FILENAME, new TypeToken<List<DiTichModel>>() {});

        for (DiTichModel diaDanh : diaDanhList)
        {
            diaDanh.setCacThoiKyLienQuan(new HashSet<>());
            if (diaDanhToThoiKy.containsKey(diaDanh.getCode()))
            {
                Set<String> set = new HashSet<>(diaDanhToThoiKy.get(diaDanh.getCode()));
                diaDanh.setCacThoiKyLienQuan(set);
                size += set.size();
            }
        }

        List<Model> models = new ArrayList<>();
        models.addAll(diaDanhList);
        diaDanhCrawler.writeJson(Config.TEMP_DI_TICH_FILENAME, models);

        return size;
    }

    public static void main(String[] args)
    {
        DiTichToThoiKy diTichToThoiKy = new DiTichToThoiKy();
        diTichToThoiKy.diaDanhToThoiKy();
    }
}
