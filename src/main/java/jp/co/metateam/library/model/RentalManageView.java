package jp.co.metateam.library.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RentalManageView {

    private Long id;
    private Stock stock;
    private Account account;
    private Integer status;
    private LocalDate lendDate;
    private LocalDate returnDate;
    private LocalDateTime rentaledAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDate getLendDate() {
        return lendDate;
    }

    public void setLendDate(LocalDate lendDate) {
        this.lendDate = lendDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public LocalDateTime getRentaledAt() {
        return rentaledAt;
    }

    public void setRentaledAt(LocalDateTime rentaledAt) {
        this.rentaledAt = rentaledAt;
    }
}