package com.shopme.setting;

import com.shopme.common.entity.Setting;
import com.shopme.common.entity.SettingCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SettingRepository extends CrudRepository<Setting, String> {
    public List<Setting> findByCategory(SettingCategory category);

    @Query("SELECT s FROM Setting c WHERE c.category = ?1 OR c.category = ?2")
    public List<Setting> findBYTwoCategories(SettingCategory catOne, SettingCategory CatTwo);
}
