package com.linyiting.currency_converter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "currencyconverter")
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("Date")
    @Column(name = "date", length = 256)
    private String date;

    @JsonProperty("USD/NTD")
    @Column(name = "usd_to_ntd", length = 256)
    private String usdToNtd;

    @JsonProperty("RMB/NTD")
    @Column(name = "rmb_to_ntd", length = 256)
    private String rmbToNtd;

    @JsonProperty("USD/RMB")
    @Column(name = "usd_to_rmb", length = 256)
    private String usdToRmb;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsdToNtd() {
        return usdToNtd;
    }

    public void setUsdToNtd(String usdToNtd) {
        this.usdToNtd = usdToNtd;
    }

    public String getRmbToNtd() {
        return rmbToNtd;
    }

    public void setRmbToNtd(String rmbToNtd) {
        this.rmbToNtd = rmbToNtd;
    }

    public String getUsdToRmb() {
        return usdToRmb;
    }

    public void setUsdToRmb(String usdToRmb) {
        this.usdToRmb = usdToRmb;
    }

    // 構造函數
    public ExchangeRate() {}

}
