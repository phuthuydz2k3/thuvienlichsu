package com.app.thuvienlichsu.base;

import com.app.thuvienlichsu.util.Config;
import com.app.thuvienlichsu.util.StringUtility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoadData
{
    private final List<Model> nhanVat;
    private final List<Model> thoiKy;
    private final List<Model> suKien;
    private final List<Model> diTich;
    private final List<Model> leHoi;
    public List<Model> getNhanVat() {
        return nhanVat;
    }

    public List<Model> getThoiKy() {
        return thoiKy;
    }

    public List<Model> getSuKien() {
        return suKien;
    }

    public List<Model> getDiTich() {
        return diTich;
    }

    public List<Model> getLeHoi() {
        return leHoi;
    }
    public LoadData() {
        nhanVat = loadNhanVat();
        thoiKy = loadThoiKy();
        suKien = loadSuKien();
        diTich = loadDiTich();
        leHoi = loadLeHoi();
    }
    public List<Model> loadNhanVat()
    {
        List<NhanVatModel> myList = loader(Config.NHAN_VAT_FILENAME,  new TypeToken<List<NhanVatModel>>() {});
        List<Model> newList = new ArrayList<>(myList);
        for (Model model : newList) model.setHTML();
        Collections.sort(newList);
        return newList;
    }
    public List<Model> loadThoiKy()
    {
        List<ThoiKyModel> myList = loader(Config.THOI_KY_FILENAME,  new TypeToken<List<ThoiKyModel>>() {});
        List<Model> newList = new ArrayList<>(myList);
        for (Model model : newList) model.setHTML();
        Collections.sort(newList);
        return newList;
    }
    public List<Model> loadSuKien()
    {
        List<SuKienModel> myList = loader(Config.SU_KIEN_FILENAME,  new TypeToken<List<SuKienModel>>() {});
        List<Model> newList = new ArrayList<>(myList);
        for (Model model : newList) model.setHTML();
        Collections.sort(newList);
        return newList;
    }
    public List<Model> loadDiTich()
    {
        List<DiTichModel> myList = loader(Config.DI_TICH_FILENAME,  new TypeToken<List<DiTichModel>>() {});
        List<Model> newList = new ArrayList<>(myList);
        for (Model model : newList) model.setHTML();
        Collections.sort(newList);
        return newList;
    }

    public List<Model> loadLeHoi()
    {
        List<LeHoiModel> myList = loader(Config.LE_HOI_FILENAME,  new TypeToken<List<LeHoiModel>>() {});
        List<Model> newList = new ArrayList<>(myList);
        for (Model model : newList) model.setHTML();
        Collections.sort(newList);
        return newList;
    }
    public <T> List<T> loader(String filePath, TypeToken<List<T>> typeToken) {
        List<T> list = new ArrayList<>();
        try (FileReader reader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            // Read the JSON string from the file
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            String jsonString = jsonBuilder.toString();

            // Use Gson to deserialize the JSON string into an ArrayList of the specified type
            Gson gson = new GsonBuilder().create();
            list = gson.fromJson(jsonString, typeToken.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
    public int binaryCheck(int start, int end, String dT, ArrayList<Model> modelList) {
        if (end < start) {
            return -1;
        }
        int mid = start + (end - start) / 2;
        int compareNext = dT.compareTo(modelList.get(mid).getTenModel());
        if (mid == 0) {
            if (compareNext < 0) {
                return 0;
            } else if (compareNext > 0) {
                return binaryCheck(mid + 1, end, dT, modelList);
            } else {
                return -1;
            }
        } else {
            int comparePrevious = dT.compareTo(modelList.get(mid - 1).getTenModel());
            if (comparePrevious > 0 && compareNext < 0) {
                return mid;
            } else if (comparePrevious < 0) {
                return binaryCheck(start, mid - 1, dT, modelList);
            } else if (compareNext > 0) {
                if (mid == modelList.size() - 1) {
                    return modelList.size();
                }
                return binaryCheck(mid + 1, end, dT, modelList);
            } else {
                return -1;
            }
        }
    }
    public int binaryLookup(int start, int end, String dT, ArrayList<Model> temp) {
        if (end < start) {
            return -1;
        }
        int mid = start + (end - start) / 2;
        int compare = StringUtility.isContain(dT, temp.get(mid).getTenModel());
        if (compare == -1) {
            return binaryLookup(start, mid - 1, dT, temp);
        } else if (compare == 1) {
            return binaryLookup(mid + 1, end, dT, temp);
        } else {
            return mid;
        }
    }

    public static void main(String[] args) {
        LoadData ld = new LoadData();

        for (Model m : ld.getThoiKy()) {
            System.out.println(m);
        }
    }
    public static int binaryLookupByCode(int start, int end, String dT, ArrayList<Model> allModels) {
        if (end < start) {
            return -1;
        }
        int mid = start + (end - start) / 2;
        int compare = StringUtility.isContain(dT, allModels.get(mid).getCode());
        if (compare == -1) {
            return binaryLookupByCode(start, mid - 1, dT, allModels);
        } else if (compare == 1) {
            return binaryLookupByCode(mid + 1, end, dT, allModels);
        } else {
            return mid;
        }
    }

}
