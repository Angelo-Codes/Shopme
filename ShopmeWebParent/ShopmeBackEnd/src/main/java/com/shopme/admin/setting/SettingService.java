package com.shopme.admin.setting;

import com.shopme.common.entity.Setting;
import com.shopme.common.entity.SettingCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SettingService {
    @Autowired
    private SettingRepository repo;

    public List<Setting> listAllSettings() {
        return  (List<Setting>) repo.findAll();
    }

    public GenaralSettingBag getGeneralSettings(){
        List<Setting> settings = new ArrayList<>();
        List<Setting> generalSetting = repo.findByCategory(SettingCategory.GENERAL);
        List<Setting> currencySetting = repo.findByCategory(SettingCategory.CURRENCY);
        settings.addAll(generalSetting);
        settings.addAll(currencySetting);

        return new GenaralSettingBag(settings);
    }

    public void saveAll(Iterable<Setting> settings) {
        repo.saveAll(settings);
    }

    public List<Setting> getMailServerSettings() {
        return repo.findByCategory(SettingCategory.MAIL_SERVER);
    }

    public List<Setting> getMailTemplateSettings() {
        return repo.findByCategory(SettingCategory.MAIL_TEMPLATES);
    }

}
