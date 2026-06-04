package jp.co.metateam.library.model;

/**
 * 貸出ステータス
 */
public enum RentalStatus {

    /** 貸出待ち */
    WAITING,

    /** 貸出中 */
    RENTING,

    /** 返却済み */
    RETURNED,

    /** キャンセル */
    CANCEL
}
