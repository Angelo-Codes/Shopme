package com.shopme.admin.setting;


import com.shopme.admin.FileUploadUtil;
import com.shopme.common.entity.Currency;
import com.shopme.common.entity.Setting;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class SettingController {
    @Autowired
    private SettingService service;

    @Autowired
    private CurrencyRepository repo;


    @GetMapping("/settings")
    public String listAll(Model model) {
        List<Setting> listSettings = service.listAllSettings();
        List<Currency> listCurrencies = repo.findAllByOrderByNameAsc();

        model.addAttribute("listCurrencies", listCurrencies);
        for (Setting setting : listSettings) {
            model.addAttribute(setting.getKey(), setting.getValue());
        }

        return "settings/settings";
    }

    @GetMapping("/settings/save_general")
    public String saveGeneralSetting(@RequestParam("fileImage")MultipartFile multipartFile,
                                     HttpServletRequest request, RedirectAttributes re) throws IOException{
        GenaralSettingBag settingBag = service.getGeneralSettings();

        saveSiteLogo(multipartFile, settingBag);
        saveCurrencySymbol(request, settingBag);
        updateSettingValuesFromForm(request, settingBag.list());

        re.addFlashAttribute("message", "General Setting has been saved.");
        return "redirect:/settings";
    }

    private void updateSettingValuesFromForm(HttpServletRequest request, List<Setting> listSetting) {

        for (Setting setting : listSetting) {
            String value = request.getParameter(setting.getKey());
            if (value != null) {
                setting.setValue(value);
            }
        }
        service.saveAll(listSetting);
    }

    private void saveCurrencySymbol(HttpServletRequest request, GenaralSettingBag settingBag) {

        Integer currencyId = Integer.parseInt(request.getParameter("CURRENCY_ID"));
        Optional<Currency> findByIdResult = repo.findById(currencyId);
        if (findByIdResult.isPresent()) {
            Currency currency = findByIdResult.get();
            settingBag.updateCurrencySymbol(currency.getSymbol());
        }

    }

    private void saveSiteLogo(MultipartFile multipartFile, GenaralSettingBag settingBag) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            String value = "/site-logo/" + fileName;
            settingBag.updateSiteLogo(value);

            String uploadDir = "../site-logo";
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }

    }
    @PostMapping("/settings/save_mail_server")
    public String saveMailServerSettings(HttpServletRequest request, RedirectAttributes re) {
        List<Setting> mailServerSettings = service.getMailServerSettings();
        updateSettingValuesFromForm(request, mailServerSettings);

        re.addFlashAttribute("message", "Mail Server Have been saved");

        return "redirect:/settings#mailServer";
    }

    @PostMapping("/settings/save_mail_templates")
    public String saveMailTemplateSettings(HttpServletRequest request, RedirectAttributes re) {
        List<Setting> mailTemplateSettings = service.getMailTemplateSettings();
        updateSettingValuesFromForm(request, mailTemplateSettings);

        re.addFlashAttribute("message", "Mail template settings have been saved");

        return "redirect:/settings#mailTemplates";
    }


}
