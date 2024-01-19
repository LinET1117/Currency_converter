package com.linyiting.currency_converter.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.linyiting.currency_converter.model.ExchangeRate;
import com.linyiting.currency_converter.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/exchange_rates")
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    @Autowired
    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    // 1.   實作查詢政府資料 API,將美金換台幣、人民幣換台幣、美金換人民幣三種
    //      匯率資訊顯示於畫面並存於 DB,Table 自訂
    @GetMapping("/DailyForeignExchangeRates")
    public String updateExchangeRates(Model model) throws JsonProcessingException {
        List<ExchangeRate> exchangeRates = exchangeRateService.getAndSaveExchangeRates();
        model.addAttribute("exchangeRates", exchangeRates);
        return "DailyForeignExchangeRates";  // 返回到顯示匯率的Thymeleaf視圖
    }

    // 2.   實作匯率資料表新增 API,規格自訂
    //      處理創建匯率記錄的表單提交 (Create -> POST: create)
    @PostMapping("/create")
    public String createExchangeRate(@ModelAttribute("exchangeRate") ExchangeRate exchangeRate) {
        exchangeRateService.createExchangeRate(exchangeRate);
        return "redirect:/exchange_rates";
    }

    // 3.   實作匯率資料表更新 API,規格自訂
    //      處理更新匯率記錄的表單提交 (UPDATE -> POST: update)
    @PostMapping("/update")
    public String updateExchangeRate(@ModelAttribute("exchangeRate") ExchangeRate exchangeRate) {
        exchangeRateService.updateByDate(
                exchangeRate.getDate(),
                exchangeRate.getUsdToNtd(),
                exchangeRate.getRmbToNtd(),
                exchangeRate.getUsdToRmb()
        );
        return "redirect:/exchange_rates";
    }

    // 4.   實作匯率資料表查詢 API,規格自訂
    //      顯示所有匯率記錄的列表 (Read -> GET: read)
    @GetMapping
    public String listExchangeRates(Model model) {
        List<ExchangeRate> exchangeRates = exchangeRateService.getAllExchangeRates();
        model.addAttribute("exchangeRates", exchangeRates);
        return "exchange_rates_list";
    }

    //5.    實作匯率資料表刪除 API,規格自訂
    //      刪除匯率記錄 (DELETE -> DEL: delete)
    @DeleteMapping("/delete")
    public String deleteExchangeRate(@RequestParam("date") String date) {
        exchangeRateService.deleteByDate(date);
        return "redirect:/exchange_rates";
    }

    // 顯示創建匯率記錄的表單(GET: create)
    @GetMapping("/create")
    public String showCreateExchangeRateForm(Model model) {
        model.addAttribute("exchangeRate", new ExchangeRate());
        return "create_exchange_rate";
    }

    // 顯示更新匯率記錄的表單
    @GetMapping("/update")
    public String showUpdateExchangeRateForm(@RequestParam("date") String date, Model model) {
        List<ExchangeRate> exchangeRates = exchangeRateService.findByDate(date);
        if (exchangeRates.isEmpty()) {
            // 處理找不到匯率記錄的情況
            return "redirect:/exchange_rates";
        }
        model.addAttribute("exchangeRate", exchangeRates.get(0));
        return "update_exchange_rate";
    }
}
