package jp.co.metateam.library.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 貸出ステータス表示用DTO（HTML互換）
 */
@Getter
@Setter
public class RentalStatusView {

    private int value;
    private String text;

    public RentalStatusView(int value, String text) {
        this.value = value;
        this.text = text;
    }
}
