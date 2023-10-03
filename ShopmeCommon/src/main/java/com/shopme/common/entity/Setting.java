package com.shopme.common.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "settings")
public class Setting {

    @Id
    @Column(name = "`key`", nullable = false, length = 128)
    private Integer key;

    @Column(nullable = false, length = 1024)
    private String value;

    @Enumerated(EnumType.STRING)
    @Column(length = 45, nullable = false)
    private SettingCategory category;

    public Setting() {

    }

    public Setting(Integer key, String value, SettingCategory category) {
        this.key = key;
        this.value = value;
        this.category = category;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SettingCategory getCategory() {
        return category;
    }

    public void setCategory(SettingCategory category) {
        this.category = category;
    }
}
