package com.app.thuvienlichsu.crawl.linkers;

import com.app.thuvienlichsu.base.DiTichModel;
import com.app.thuvienlichsu.base.LeHoiModel;
import com.app.thuvienlichsu.base.Model;
import com.app.thuvienlichsu.crawl.crawlers.DiTichCrawler;
import com.app.thuvienlichsu.crawl.crawlers.LeHoiCrawler;
import com.app.thuvienlichsu.util.Config;
import com.google.gson.reflect.TypeToken;

import java.util.*;

public class DiTichToLeHoi
{
    public Map<String, Set<String>> generateHashmap()
    {
        Map<String, Set<String>> hashMap = new HashMap<>();
        LeHoiCrawler leHoiCrawler = new LeHoiCrawler();
        List<LeHoiModel> leHoiList = leHoiCrawler
                .loader(Config.TEMP_LE_HOI_FILENAME, new TypeToken<List<LeHoiModel>>() {});

        for (LeHoiModel leHoi : leHoiList)
        {
            String locationCode = leHoi.getDiaDanhCode();

            if (!hashMap.containsKey(locationCode))
            {
                hashMap.put(locationCode, new HashSet<>());
            }
            hashMap.get(locationCode).add(leHoi.getCode());
        }


        return hashMap;
    }
    public Map<String, Set<String>> LinkDiTichToLeHoi()
    {
        Map<String, Set<String>> hashMap = generateHashmap();
//        System.out.println(hashMap);
        DiTichCrawler diTichCrawler = new DiTichCrawler();
        List<DiTichModel> diTichList = diTichCrawler
                .loader(Config.TEMP_DI_TICH_FILENAME, new TypeToken<List<DiTichModel>>() {});
        for (DiTichModel diTich : diTichList)
        {
            hashMap.forEach((key, value) -> {
                if (key.contains(diTich.getCode()) || diTich.getCode().contains(key))
                {
                    diTich.setCacLeHoiLienQuan(value);
//                    System.out.println(diTich.getCode() + ": " + value);

                }
            });
        }

        List<Model> models = new ArrayList<>();
        models.addAll(diTichList);
        diTichCrawler.writeJson(Config.TEMP_DI_TICH_FILENAME, models);

        return hashMap;
    }

    public static void main(String[] args)
    {
        DiTichToLeHoi diTichToLeHoi = new DiTichToLeHoi();
        diTichToLeHoi.LinkDiTichToLeHoi();
    }
}
