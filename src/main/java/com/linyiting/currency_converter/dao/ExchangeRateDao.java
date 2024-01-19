package com.linyiting.currency_converter.dao;

import com.linyiting.currency_converter.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ExchangeRateDao extends JpaRepository<ExchangeRate, Long> {

    // 新增方法，用於插入新的匯率記錄
    ExchangeRate save(ExchangeRate exchangeRate);

    // 查詢方法，根據日期查詢匯率
    List<ExchangeRate> findByDate(String date);

    // 更新方法，根據日期更新匯率
    @Modifying
    @Query("UPDATE ExchangeRate e SET e.usdToNtd = :usdToNtd, e.rmbToNtd = :rmbToNtd, e.usdToRmb = :usdToRmb WHERE e.date = :date")
    @Transactional
    void updateByDate(String date, String usdToNtd, String rmbToNtd, String usdToRmb);

    // 刪除方法，根據日期刪除匯率
    @Modifying
    @Query("DELETE FROM ExchangeRate e WHERE e.date = :date")
    @Transactional
    void deleteByDate(String date);
}