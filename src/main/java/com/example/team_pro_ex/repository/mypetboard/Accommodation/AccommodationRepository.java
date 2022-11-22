package com.example.team_pro_ex.repository.mypetboard.Accommodation;


import com.example.team_pro_ex.Entity.mypetboard.accommodation.Accommodation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    /**숙소 이름으로 검색*/
    Page<Accommodation> findByStoreNameContaining(String name, Pageable pageable);

    /**카테고리 선택 후 입력받은 숙소 이름으로 검색*/
    @Query("SELECT a FROM Accommodation a WHERE a.storeName LIKE %?1%")
    Page<Accommodation> findCategoryAndKeyword(String searchKeyword, Pageable pageable);

    Accommodation findBySeq(Long seq);
}
