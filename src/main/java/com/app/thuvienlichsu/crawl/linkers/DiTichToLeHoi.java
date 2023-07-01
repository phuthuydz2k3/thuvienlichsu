package com.app.thuvienlichsu.crawl.linkers;

import com.app.thuvienlichsu.base.DiTichModel;
import com.app.thuvienlichsu.base.LeHoiModel;
import com.app.thuvienlichsu.base.Model;
import com.app.thuvienlichsu.crawl.crawlers.DiTichCrawler;
import com.app.thuvienlichsu.crawl.crawlers.LeHoiCrawler;
import com.app.thuvienlichsu.util.Config;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiTichToLeHoi
{
    public Map<String, List<String>> DiTichToLeHoi()
    {
        Map<String, List<String>> hashMap = new HashMap<>();
        LeHoiCrawler leHoiCrawler = new LeHoiCrawler();
        List<LeHoiModel> leHoiList = leHoiCrawler
                .loader(Config.TEMP_LE_HOI_FILENAME, new TypeToken<List<LeHoiModel>>() {});

        for (LeHoiModel leHoi : leHoiList)
        {
            String locationCode = leHoi.getDiaDanhCode();

            if (!hashMap.containsKey(locationCode))
            {
                hashMap.put(locationCode, new ArrayList<>());
            }
            hashMap.get(locationCode).add(leHoi.getCode());
        }


        return hashMap;
    }
    public void LinkDiTichToLeHoi()
    {
        Map<String, List<String>> hashMap = DiTichToLeHoi();
        System.out.println(hashMap);
        DiTichCrawler diTichCrawler = new DiTichCrawler();
        List<DiTichModel> diTichList = diTichCrawler
                .loader(Config.TEMP_DI_TICH_FILENAME, new TypeToken<List<DiTichModel>>() {});
        for (DiTichModel diTich : diTichList)
        {
            hashMap.forEach((key, value) -> {
                if (key.contains(diTich.getCode()) || diTich.getCode().contains(key))
                {
                    diTich.setCacLeHoiLienQuan(value);
                    System.out.println(diTich.getCode() + value);

                }
            });
        }

        List<Model> models = new ArrayList<>();
        models.addAll(diTichList);
        diTichCrawler.writeJson(Config.TEMP_DI_TICH_FILENAME, models);
    }

    public static void main(String[] args)
    {
        DiTichToLeHoi diTichToLeHoi = new DiTichToLeHoi();
        diTichToLeHoi.LinkDiTichToLeHoi();
    }
}
