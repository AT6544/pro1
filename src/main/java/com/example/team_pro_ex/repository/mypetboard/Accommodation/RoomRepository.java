package com.example.team_pro_ex.repository.mypetboard.Accommodation;

import com.example.team_pro_ex.Entity.mypetboard.accommodation.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Room findByAccommodationSeq(Long accSeq);

}
