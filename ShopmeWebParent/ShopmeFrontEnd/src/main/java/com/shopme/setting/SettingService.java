package com.shopme.setting;

import com.shopme.common.entity.Setting;
import com.shopme.common.entity.SettingCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingService {
    @Autowired
    private SettingRepository repo;

    public List<Setting> getGeneralSetting() {
        return repo.findBYTwoCategories(SettingCategory.GENERAL, SettingCategory.CURRENCY);
    }

    public Email
}
