package com.app.thuvienlichsu.crawl.linkers;

import com.app.thuvienlichsu.base.DiTichModel;
import com.app.thuvienlichsu.base.LeHoiModel;
import com.app.thuvienlichsu.base.Model;
import com.app.thuvienlichsu.crawl.crawlers.DiTichCrawler;
import com.app.thuvienlichsu.crawl.crawlers.LeHoiCrawler;
import com.app.thuvienlichsu.util.Config;
import com.google.gson.reflect.TypeToken;

import java.util.*;

public class LeHoiToDiTich
{
    public Map<String, Set<String>> generateHashMap()
    {
        Map<String, Set<String>> hashMap = new HashMap<>();
        DiTichCrawler diTichCrawler = new DiTichCrawler();
        List<DiTichModel> diTichList = diTichCrawler.loader(Config.TEMP_DI_TICH_FILENAME,  new TypeToken<List<DiTichModel>>() {});

        for (DiTichModel diTich : diTichList)
        {
            if (diTich.getCacLeHoiLienQuan() != null)
            {
                for (String leHoi : diTich.getCacLeHoiLienQuan())
                {
                    if (!hashMap.containsKey(leHoi))
                    {
                        hashMap.put(leHoi, new HashSet<>());
                    }
                    hashMap.get(leHoi).add(diTich.getCode());
                }
            }
        }

        return hashMap;
    }

    public void LinkLeHoiToDiTich()
    {
        Map<String, Set<String>> hashMap = generateHashMap();
//        System.out.println(hashMap);

        LeHoiCrawler leHoiCrawler = new LeHoiCrawler();
        List<LeHoiModel> leHoiList = leHoiCrawler.loader(Config.TEMP_LE_HOI_FILENAME, new TypeToken<List<LeHoiModel>>() {});

        for (LeHoiModel leHoi : leHoiList)
        {
//            if (hashMap.containsKey(leHoi.getCode()))
//            {
//                leHoi.setDiTichLienQuan(hashMap.get(leHoi.getCode()));
//                System.out.println(leHoi.getCode());
//                System.out.println(hashMap.get(leHoi.getCode()));
//            }
            hashMap.forEach((key, value) -> {
                if (key.contains(leHoi.getCode()) || leHoi.getCode().contains(key))
                {
                    leHoi.setDiTichLienQuan(value);
//                    System.out.println(key + value);

                }
            });
        }

        List<Model> models = new ArrayList<>();
        models.addAll(leHoiList);
        leHoiCrawler.writeJson(Config.TEMP_LE_HOI_FILENAME, models);
    }

    public static void main(String[] args)
    {
        LeHoiToDiTich leHoiToDiTich = new LeHoiToDiTich();
        leHoiToDiTich.LinkLeHoiToDiTich();
    }
}
