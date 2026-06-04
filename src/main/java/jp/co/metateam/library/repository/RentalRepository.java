package jp.co.metateam.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jp.co.metateam.library.model.Rental;

public interface RentalRepository extends JpaRepository<Rental, Long> {

  /**
   * 貸出一覧取得（index画面用）
   */
  @Override
  List<Rental> findAll();

  /**
   * 同一在庫で、貸出中・貸出待ちのデータを取得
   * （期間重複チェック用）
   */
  @Query("""
        SELECT r
          FROM Rental r
         WHERE r.stockId = :stockId
           AND r.status IN :statusList
      """)
  List<Rental> findActiveByStockId(
      @Param("stockId") String stockId,
      @Param("statusList") List<Integer> statusList);
}
