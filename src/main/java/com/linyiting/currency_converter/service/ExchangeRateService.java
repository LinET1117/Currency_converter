package com.linyiting.currency_converter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linyiting.currency_converter.dao.ExchangeRateDao;
import com.linyiting.currency_converter.model.ExchangeRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExchangeRateService {

    private final ExchangeRateDao exchangeRateDao;

    @Autowired
    public ExchangeRateService(ExchangeRateDao exchangeRateDao) {
        this.exchangeRateDao = exchangeRateDao;
    }

    public ExchangeRate createExchangeRate(ExchangeRate exchangeRate) {
        return exchangeRateDao.save(exchangeRate);
    }

    public List<ExchangeRate> findByDate(String date) {
        return exchangeRateDao.findByDate(date);
    }

    public void updateByDate(String date, String usdToRmb, String rmbToNtd, String usdToNtd) {
        exchangeRateDao.updateByDate(date, usdToRmb, rmbToNtd, usdToNtd);
    }

    public void deleteByDate(String date) {
        exchangeRateDao.deleteByDate(date);
    }

    public List<ExchangeRate> getAllExchangeRates() {
        return exchangeRateDao.findAll();
    }

    @Autowired
    private RestTemplate restTemplate;

    public List<ExchangeRate> getAndSaveExchangeRates() throws JsonProcessingException {
        String url = "https://openapi.taifex.com.tw/v1/DailyForeignExchangeRates";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<ExchangeRate> apiResponses = mapper.readValue(
                response.getBody(),
                new TypeReference<List<ExchangeRate>>() {}
        );

        List<ExchangeRate> exchangeRates = new ArrayList<>();
        for (ExchangeRate apiResponse : apiResponses) {
            ExchangeRate exchangeRate = new ExchangeRate();
            exchangeRate.setDate(apiResponse.getDate());
            exchangeRate.setUsdToNtd(apiResponse.getUsdToNtd());
            exchangeRate.setRmbToNtd(apiResponse.getRmbToNtd());
            exchangeRate.setUsdToRmb(apiResponse.getUsdToRmb());
            exchangeRateDao.save(exchangeRate);
            exchangeRates.add(exchangeRate);
        }

        return exchangeRates;
    }
}
