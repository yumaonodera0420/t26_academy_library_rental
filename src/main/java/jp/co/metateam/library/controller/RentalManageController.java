package jp.co.metateam.library.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import jp.co.metateam.library.model.RentalManageDto;
import jp.co.metateam.library.service.AccountService;
import jp.co.metateam.library.service.RentalService;
import jp.co.metateam.library.service.StockService;
import lombok.extern.log4j.Log4j2;

/**
 * 貸出管理関連クラスß
 */
@Log4j2
@Controller
public class RentalManageController {

    @Autowired
    private RentalService rentalService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private StockService stockService;

    /**
     * 貸出一覧画面初期表示
     * @param model
     * @return
     */
    @GetMapping("/rental/index")
    public String index(Model model) {

        model.addAttribute("rentalManageList", rentalService.findAllForView());

        // 貸出管理テーブルから全件取得

        // 貸出一覧画面に渡すデータをmodelに追加

        // 貸出一覧画面に遷移
        return "/rental/index";
    }

    @GetMapping("/rental/add")
    public String add(Model model) {
        setAddScreenModel(model, new RentalManageDto());
        return "/rental/add";
    }

    /*
     * =========================
     * 貸出登録処理
     * =========================
     */
    @PostMapping("/rental/add")
    public String register(
            @Valid @ModelAttribute RentalManageDto rentalManageDto,
            BindingResult result,
            Model model) {
        /*
         * =================================================
         * 貸出ステータスの妥当性チェック
         * =================================================
         */
        if (rentalManageDto.getStatus() != null) {

            if (rentalManageDto.getStatus() == 2
                    || rentalManageDto.getStatus() == 3) {

                result.rejectValue(
                        "status",
                        null,
                        "「貸出待ち」または「貸出中」を選択してください");
            }
        }
        /*
         * =================================================
         * 貸出予定日とステータスの整合性チェック
         * =================================================
         */
        if (rentalManageDto.getLendDate() != null
                && rentalManageDto.getStatus() != null) {

            LocalDate today = LocalDate.now();

            boolean valid = (rentalManageDto.getLendDate().isAfter(today)
                    && rentalManageDto.getStatus() == 0) // WAITING
                    || (!rentalManageDto.getLendDate().isAfter(today)
                            && rentalManageDto.getStatus() == 1); // RENTING

            if (!valid) {
                result.rejectValue(
                        "status",
                        null,
                        "未来日付では「貸出待ち」、過去日付では「貸出中」を選択してください");
            }
        }
        /*
         * =================================================
         * 貸出予定日とステータスの整合性チェック
         * =================================================
         */
        if (rentalManageDto.getLendDate() != null
                && rentalManageDto.getReturnDate() != null) {

            if (!rentalManageDto.getReturnDate()
                    .isAfter(rentalManageDto.getLendDate())) {

                result.rejectValue(
                        "returnDate",
                        null,
                        "返却予定日は貸出予定日より後にしてください");
            }
        }

        if (result.hasErrors()) {
        }
        RentalManageDto dto = new RentalManageDto();

        dto.setEmployeeId(rentalManageDto.getEmployeeId());
        dto.setStockId(rentalManageDto.getStockId());
        dto.setLendDate(rentalManageDto.getLendDate());
        dto.setReturnDate(rentalManageDto.getReturnDate());
        dto.setStatus(rentalManageDto.getStatus());

        // ⑥ 在庫チェック（最後）
        if (!rentalService.isStockRentable(dto.getStockId(), model)) {

            setAddScreenModel(model, rentalManageDto);
            return "/rental/add";
        }

        // 期間重複チェック
        System.out.println("重複チェックまで到達");
        if (!rentalService.isRentalPeriodAvailable(dto, model)) {

            result.rejectValue(
                    "lendDate",
                    null,
                    "貸出期間が重複しています");
        }
        if (result.hasErrors()) {
            setAddScreenModel(model, rentalManageDto);
            return "/rental/add";
        }
        rentalService.register(dto);
        return "redirect:/rental/index";

    }

    private void setAddScreenModel(Model model, RentalManageDto rentalManageDto) {
        model.addAttribute("rentalManageDto", rentalManageDto);
        model.addAttribute("accounts", accountService.findAll());
        model.addAttribute("stockList", stockService.findAll());
        model.addAttribute("rentalStatus", List.of(0, 1, 2, 3));
    }
}