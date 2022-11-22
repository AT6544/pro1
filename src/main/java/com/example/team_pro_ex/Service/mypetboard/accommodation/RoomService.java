package com.example.team_pro_ex.Service.mypetboard.accommodation;


import com.example.team_pro_ex.Entity.mypetboard.accommodation.Room;
import com.example.team_pro_ex.Entity.mypetboard.common.RoomImage;
import com.example.team_pro_ex.Entity.mypetboard.accommodation.Accommodation;
import com.example.team_pro_ex.Entity.mypetboard.accommodation.Room;

import java.util.List;

public interface RoomService {

    List<Room> getRoomList();

    Long insertRoom( Room room);

    Room getRoom(Room room);

    void updateRoom(Room room);

    void deleteRoom(Room room);

    Long insertRoomImage(RoomImage roomImage);

    List<RoomImage>getRoomImageEntity(Long RoomSeq);




}
