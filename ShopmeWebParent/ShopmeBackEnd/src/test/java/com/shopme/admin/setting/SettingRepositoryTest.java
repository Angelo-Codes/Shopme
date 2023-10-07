package com.shopme.admin.setting;

import com.shopme.common.entity.Setting;
import com.shopme.common.entity.SettingCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class SettingRepositoryTest {

    @Autowired
    private SettingRepository repo;

    @Test
    public void testCreateGeneralSetting() {
        Setting siteName = new Setting("SITE_NAME", "Shopme", SettingCategory.GENERAL);
        Setting siteLogo = new Setting("SITE_LOGO", "Shopme.png", SettingCategory.GENERAL);
        Setting copyright = new Setting("COPYRIGHT", "Copyright (C) 2021 Shopme Ltd.", SettingCategory.GENERAL);

        repo.saveAll(List.of(siteName, siteLogo, copyright));

        Iterable<Setting> iterable = repo.findAll();
        assertThat(iterable).size().isGreaterThan(0);
    }
    @Test
    public void testCreateCurrencySettings() {
        Setting currencyId = new Setting("CURRENCY_ID", "1", SettingCategory.CURRENCY);
        Setting symbol = new Setting("CURRENCY_SYMBOL", "$", SettingCategory.CURRENCY);
        Setting symbolPosition = new Setting("CURRENCY_SYMBOL_POSITION", "before", SettingCategory.CURRENCY);
        Setting decimalPointType = new Setting("DECIMAL_POINT_TYPE", "POINT", SettingCategory.CURRENCY);
        Setting decimalDigits = new Setting("DECIMAL_DIGITS", "2", SettingCategory.CURRENCY);
        Setting thousandsPointType = new Setting("THOUSANDS_POINT_TYPE", "COMMA", SettingCategory.CURRENCY);

        repo.saveAll(List.of(currencyId, symbol, symbolPosition, decimalPointType,
                decimalDigits, thousandsPointType));

    }

    @Test
    public void testCreateMailSettings() {
        Setting currencyId = new Setting("MAIL_PORT", "123", SettingCategory.MAIL_SERVER);
        Setting symbol = new Setting("MAIL_HOST", "smtp.gmail.com", SettingCategory.MAIL_SERVER);
        Setting symbolPosition = new Setting("MAIL_USERNAME", "username", SettingCategory.MAIL_SERVER);
        Setting decimalPointType = new Setting("MAIL_PASSWORD", "password", SettingCategory.MAIL_SERVER);
        Setting decimalDigits = new Setting("MAIL_FROM", "shopme@gmail.com", SettingCategory.MAIL_SERVER);
        Setting thousandsPointType = new Setting("SMTP_AUTH", "true", SettingCategory.MAIL_SERVER);
        Setting thousandsPoint = new Setting("SMTP_SECURED", "true", SettingCategory.MAIL_SERVER);
        Setting thousandsPointTyp = new Setting("MAIL_SENDER_NAME", "Shopme Team", SettingCategory.MAIL_SERVER);
        Setting thousndsPoint = new Setting("SMTP_SECURED", "true", SettingCategory.MAIL_SERVER);
        Setting thosandsPoint = new Setting("CUSTOMER_VERIFY_SUBJECT", "Email subject", SettingCategory.MAIL_TEMPLATES);
        repo.saveAll(List.of(currencyId, symbol, symbolPosition, decimalPointType,
                decimalDigits, thousandsPointType, thousandsPoint, thousandsPointTyp, thousndsPoint, thosandsPoint));

    }

    @Test
    public void testListSettingsByCategory() {
        List<Setting> settings = repo.findByCategory(SettingCategory.GENERAL);
        settings.forEach(System.out::println);
    }

}
