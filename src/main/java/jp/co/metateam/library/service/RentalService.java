package jp.co.metateam.library.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jp.co.metateam.library.model.Account;
import jp.co.metateam.library.model.Rental;
import jp.co.metateam.library.model.RentalManageDto;
import jp.co.metateam.library.model.RentalManageView;
import jp.co.metateam.library.model.Stock;
import jp.co.metateam.library.repository.AccountRepository;
import jp.co.metateam.library.repository.RentalRepository;
import jp.co.metateam.library.repository.StockRepository;

@Service
public class RentalService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    public List<RentalManageView> findAllForView() {

        List<Rental> rentals = rentalRepository.findAll();
        List<RentalManageView> views = new ArrayList<>();

        for (Rental r : rentals) {

            RentalManageView v = new RentalManageView();
            v.setId(r.getId());
            v.setStatus(r.getStatus());
            v.setLendDate(r.getLendDate());
            v.setReturnDate(r.getReturnDate());
            v.setRentaledAt(r.getRentaledAt());

            // stockId → Stock
            Stock stock = stockRepository.findById(r.getStockId()).orElse(null);
            v.setStock(stock);

            // employeeId → Account
            Account account = accountRepository.findByEmployeeId(r.getEmployeeId()).orElse(null);
            v.setAccount(account);

            views.add(v);
        }
        return views;
    }

    /*
     * =================================================
     * ⑭ 在庫ステータス取得
     * =================================================
     */
    public boolean isStockRentable(String stockId, Model model) {
        Stock stock = stockRepository.findById(stockId).orElse(null);

        if (stock == null || stock.getStatus() == 2) {
            model.addAttribute(
                    "errStock",
                    "貸出可能な書籍ではありません");
            return false;
        }
        return true;
    }

    /*
     * =================================================
     * ⑮ 既存貸出データ取得＆期間重複チェック
     * =================================================
     */
    public boolean isRentalPeriodAvailable(RentalManageDto dto, Model model) {

        List<Integer> statusList = List.of(0, 1, 2, 3);
        List<Rental> rentalList = rentalRepository.findActiveByStockId(dto.getStockId(), statusList);

        for (Rental r : rentalList) {

            boolean overlap = !(dto.getReturnDate().isBefore(r.getLendDate())
                    || dto.getLendDate().isAfter(r.getReturnDate()));
            if (overlap) {
                return false;
            }
        }
        return true;
    }

    /*
     * =================================================
     * DB登録
     * =================================================
     */
    public void register(RentalManageDto dto) {

        Rental rental = new Rental();

        rental.setStockId(dto.getStockId());
        rental.setEmployeeId(dto.getEmployeeId());
        rental.setLendDate(dto.getLendDate());
        rental.setReturnDate(dto.getReturnDate());
        rental.setStatus(dto.getStatus());

        if (dto.getStatus() == 1) { // RENTING
            rental.setRentaledAt(LocalDateTime.now());
        }

        rentalRepository.save(rental);
    }
}