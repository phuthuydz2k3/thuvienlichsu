package com.app.thuvienlichsu.crawl.linkers;

import com.app.thuvienlichsu.base.Model;
import com.app.thuvienlichsu.base.NhanVatModel;
import com.app.thuvienlichsu.base.ThoiKyModel;
import com.app.thuvienlichsu.crawl.crawlers.NhanVatCrawler;
import com.app.thuvienlichsu.crawl.crawlers.ThoiKyCrawler;
import com.app.thuvienlichsu.util.Config;
import com.google.gson.reflect.TypeToken;

import java.util.*;


public class NhanVatToThoiKy
{
    private Map<String, List<String>> generateHashMap()
    {
        Map<String, List<String>> hashMap = new HashMap<>();
        ThoiKyCrawler thoiKyCrawler = new ThoiKyCrawler();
        List<ThoiKyModel> thoiKyList = thoiKyCrawler.loader(Config.TEMP_THOI_KY_FILENAME,  new TypeToken<List<ThoiKyModel>>() {});

        for (ThoiKyModel thoiKy : thoiKyList)
        {
            for (String nhanVat : thoiKy.getCacNhanVatLienQuan())
            {
                if (!hashMap.containsKey(nhanVat))
                {
                    hashMap.put(nhanVat, new ArrayList<>());
                }
                hashMap.get(nhanVat).add(thoiKy.getCode());
            }
        }

        return hashMap;
    }

    public int linkNhanVatToThoiKy()
    {
        Map<String, List<String>> nhanVatToThoiKy = generateHashMap();
//        System.out.println(nhanVatToThoiKy);
        NhanVatCrawler nhanVatCrawler = new NhanVatCrawler();
        List<NhanVatModel> nhanVatList = nhanVatCrawler
                .loader(Config.TEMP_NHAN_VAT_FILENAME,  new TypeToken<List<NhanVatModel>>() {});
        int size = 0;

        for (NhanVatModel nhanVat : nhanVatList)
        {
            if (nhanVatToThoiKy.containsKey(nhanVat.getCode()))
            {
                Set<String> set = new HashSet<>(nhanVatToThoiKy.get(nhanVat.getCode()));
                nhanVat.setCacThoiKyLienQuan(set);
                size += set.size();
            }
        }

        List<Model> models = new ArrayList<>();
        models.addAll(nhanVatList);
        nhanVatCrawler.writeJson(Config.TEMP_NHAN_VAT_FILENAME, models);

        return size;
    }

    public static void main(String[] args)
    {
        NhanVatToThoiKy nhanVatToThoiKy = new NhanVatToThoiKy();
        nhanVatToThoiKy.linkNhanVatToThoiKy();
    }
}
