package jp.co.metateam.library.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 貸出登録画面用DTO（HTML互換）
 */
@Getter
@Setter

public class RentalManageDto {

    /** 社員番号 */
    @NotBlank(message = "社員番号は必須です")
    private String employeeId;

    /** 在庫番号 */
    @NotBlank(message = "在庫番号は必須です")
    private String stockId;

    /** 貸出予定日 */
    @NotNull(message = "貸出予定日は必須です")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate lendDate;

    /** 返却予定日 */
    @NotNull(message = "返却予定日は必須です")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDate;

    /** 貸出ステータス（数値） */

    @NotNull(message = "ステータスは必須です")
    private Integer status;

}