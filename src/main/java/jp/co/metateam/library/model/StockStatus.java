package jp.co.metateam.library.model;

/**
 * 在庫ステータス
 */
public enum StockStatus {

    /** 利用可 */
    AVAILABLE,

    /** 貸出中 */
    RENTED,

    /** 貸出不可 */
    UNAVAILABLE
}