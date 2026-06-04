package jp.co.metateam.library.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

/**
 * 貸出管理 Entity
 */
@Getter
@Setter
@Entity
@Table(name = "rental_manage")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 在庫 */
    @Column(name = "stock_id")
    private String stockId;

    /** 利用者 */

    @Column(name = "employee_id")
    private String employeeId;

    // ステータス
    @Column(name = "status")
    private Integer status;

    @Column(name = "expected_rental_on")
    private LocalDate lendDate;

    @Column(name = "expected_return_on")
    private LocalDate returnDate;

    @Column(name = "rentaled_at")
    private LocalDateTime rentaledAt;

    @Column(name = "returned_at")
    private LocalDateTime returnedAt;

    @Column(name = "canceled_at")
    private LocalDateTime canceledAt;
}