package com.example.team_pro_ex.repository.mypetboard.hospital;


import com.example.team_pro_ex.Entity.mypetboard.accommodation.Accommodation;
import com.example.team_pro_ex.Entity.mypetboard.hospital.Hospital;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {

    Page<Hospital> findByStoreNameContaining(String name, Pageable pageable);

    @Query("SELECT a FROM Hospital a WHERE a.storeName LIKE %?1%")
    Page<Hospital> findCategoryAndKeyword(String searchKeyword, Pageable pageable);


}

