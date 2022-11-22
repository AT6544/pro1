package com.example.team_pro_ex.Controller.mypetboard;

import com.example.team_pro_ex.Entity.mypetboard.accommodation.Accommodation;
import com.example.team_pro_ex.Entity.mypetboard.accommodation.Room;
import com.example.team_pro_ex.Entity.mypetboard.common.AccommodationImage;
import com.example.team_pro_ex.Entity.mypetboard.common.RoomImage;
import com.example.team_pro_ex.Security.Member.PrincipaDetailsMember;
import com.example.team_pro_ex.Service.mypetboard.accommodation.AccommodationService;
import com.example.team_pro_ex.Service.mypetboard.accommodation.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/*
 * * Accommodation domain CONTROLLER
 * @Param String HTML에서 받아온 데이터
 * @return String HTML 파일과 연결을 위해 사용(ViewResolber)
 * @ author(작성자) 이승현
 * @version 20220912.0.0.1
 */


@Controller
@RequestMapping(path = "/mypetboard/accommodation")
public class AccommodationController {
    private final AccommodationService accommodationService;
    private final RoomService roomService;

    /** 생성자 주입 방식으로
     * 숙소, 룸Service 주입 */
    @Autowired
    protected AccommodationController(AccommodationService accommodationService,  RoomService roomService) {

        this.accommodationService = accommodationService;
        this.roomService = roomService;
    }

    /** 숙소 등록 page 불러오기 */
    @GetMapping("/insertAccommodation")
    public String insertAccommodationView( Accommodation accommodation, Model model) {

        //model.addAttribute("ownerMember", ownerMemberService.getOwnerMember(ownerMember));

        model.addAttribute("accommodation" , accommodation);
        return "mypetboard/accommodation/insertAccommodation";
    }

    /** 숙소 등록 page */
    //로컬 저장소에 저장 (DB에 값 저장)
    @PostMapping("/insertAccommodation")
    public String insertAccommodation(Accommodation accommodation,
                                      @AuthenticationPrincipal PrincipaDetailsMember userDetails ,
                                      @RequestParam(value = "accommodationimage" , required = false)MultipartFile[] accommodationimage) {
        try{

            //파일의 메타데이터 저장
            for(MultipartFile file : accommodationimage) {
                if(!file.isEmpty()){
                    /*accommodation ImgName컬럼에 uuid + originalfilename 형식으로 저장*/
                    accommodation.setCreateDate(new Date());
                    accommodation.setBookingDate(new Date());
                    accommodation.setMember(userDetails.getMember());
                    String uuid = UUID.randomUUID().toString();
                    accommodation.setImgname(uuid+"_"+file.getOriginalFilename());

                    //게시글 정보 저장
                    Long accommodationSeq = accommodationService.insertAccommodation(accommodation);
                    // 사진을 불러오기 위한 정보 저장
                    AccommodationImage entity = new AccommodationImage(null,
                            uuid,
                            file.getContentType(),
                            file.getName(),
                            file.getOriginalFilename(),
                            accommodationSeq);// 숙소 seq에 어떤 사진이 등록이 돼 있는지 확인을 위해 추가

                    accommodationService.insertAccommodationImage(entity);

                    File newFileName = new File(uuid+"_"+ entity.getOriginalFilename());
                    //서버에 사진 파일 업로드(저장) uuid+업로드한 사진 이름으로 DB에 저장된다.
                    file.transferTo(newFileName);
                }
                else {
                    return "mypetboard/accommodation/insertAccommodation";
            }

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        //"redirect:/mypetboard/accommodation/getHotelList"
        return "redirect:/mypetboard/accommodation/getAccommodationList";
    }

    /** 숙소에 업로드한 사진 처리를 위한것 */
    @GetMapping(value = "/image/{imgname}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> imageLoading(@PathVariable("imgname")String input_imgName) throws IOException {
        //ResponseEntity<Byte[]>:http 프로토콜을 통해서 byte 데이터를 전달하는 객체 , byte(소문자 = 기본타입)
        //@PathVariable: URL주소의 값을 받아옴
        String path = "C:/work/MyPet/Team_pro_ex/image/"+input_imgName;
        //데이터(이미지)를 전송 하기 위한 객체로써 java에서는 항상 데이터를 스트림 타입으로 전달
        //객체(데이터 저장) :String,int,double
        //String객체는 파일을 컴퓨터가 cpu에서 바로 읽어 들일수 있도록 하는 갳체
        InputStream fis = new FileInputStream(path);
        //Bufferd : cpu에서 데이터를 읽어 올떄 메모리와 캐시 사이에서 Cpu와의 속도 차이를 줄이기 위한 중간 저장 위치
        BufferedInputStream bis =  new BufferedInputStream(fis);
        //byte 배열로 변환
        //HTTP프로토콜은 바이트 단위(배열)로 주고 받음
        byte[] imgByteArr = bis.readAllBytes();
        fis.close();
        //HTTP 프로토콜은 바이트 배열로 데이터를 주고 받기 때문에 Stream이나 버퍼를 통해 변환
        return new ResponseEntity<byte[]>(imgByteArr , HttpStatus.OK);

    }

    /** 숙소 List 페이지 불러오기 */
    @GetMapping ("/getAccommodationList")
    public String getAccommodationList(
            @RequestParam(value= "searchKeyword" , required = false , defaultValue = "")String searchKeyword,
            @RequestParam(value = "detailCategory" , required = false, defaultValue = "")String detailCategory,
            Model model, Accommodation accommodation, @PageableDefault(size = 10 , sort = "seq", direction = Sort.Direction.ASC ) Pageable pageable) {
        /* 한 화면에 size 글 10개 , sort = "seq" 정렬(시퀀스 번호로 정렬 하겠다) , direction = Sort.Direction.ASC(오름차순으로 정렬)  */

        // 숙소 검색기능
        /* 객체 초기화 */
        Page<Accommodation> accommodation1 = null;

        /* 카테고리분류(search기능) -> 카테고리와 숙소 이름으로 검색 */
        if(detailCategory.equals("모텔")){
            System.out.println("모텔");
            accommodation1 = accommodationService.getAccommodationContainsName(searchKeyword, pageable);
        }
        else if(detailCategory.equals("호텔")){
            System.out.println("호텔");
            accommodation1 = accommodationService.getAccommodationContainsName(searchKeyword, pageable);
        }
        else if(detailCategory.equals("펜션")){
            System.out.println("펜션");
            accommodation1 = accommodationService.getAccommodationContainsName(searchKeyword, pageable);
        }
        else {
            accommodation1 = accommodationService.getAccommodationContainsName(searchKeyword, pageable);
        }
        System.out.println("[detailCategory= " + detailCategory + "]," + " [searchKeyword= " + searchKeyword + "]");


        model.addAttribute("detailCategory", detailCategory );
        model.addAttribute("searchKeyword", searchKeyword );
        model.addAttribute("accommodationList", accommodation1 );
        //페이징처리 이전 ,다음
        model.addAttribute("previous" , pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next" , pageable.next().getPageNumber());

        return "/mypetboard/accommodation/getAccommodationList";
    }

    /** 룸 사진, 정보 등록 */
    @GetMapping("/getAccommodation")
    public String getAccommodation(Accommodation accommodation , Model model, Room room) {

        /* 룸 정보 뷰에 뿌려주기 */
        model.addAttribute(accommodationService.getAccommodation(accommodation));
        model.addAttribute("room1" ,   roomService.getRoomList().get(0));
        model.addAttribute("room2" ,   roomService.getRoomList().get(1));
        model.addAttribute("room3" ,   roomService.getRoomList().get(2));
        model.addAttribute("room4" ,   roomService.getRoomList().get(3));

        /* 저장된 숙소사진을 불러와서 뷰에 뿌려주기 */
        AccommodationImage accommodationImage = accommodationService.getAccommodationImageEntity(accommodation.getSeq());
        String path = "/mypetboard/accommodation/image/"+ accommodationImage.getUuid()+"_"+ accommodationImage.getOriginalFilename();
        model.addAttribute("imgLoding", path);

        /* 저장된 사진 불러와서 뷰에 뿌려주기 */
        List<RoomImage> roomImageList = roomService.getRoomImageEntity(accommodation.getSeq());
        List<String> path1 = new ArrayList<>();

        for(RoomImage ri: roomImageList) {
            String savePath =  "/mypetboard/accommodation/image/"+ ri.getUuid()+"_"+ ri.getOriginalFilename();
            path1.add(savePath);
        }
//        model.addAttribute("rimgLoading", path1);
        model.addAttribute("rimgLoading1", path1.get(0));
        model.addAttribute("rimgLoading2", path1.get(1));
        model.addAttribute("rimgLoading3", path1.get(2));
        model.addAttribute("rimgLoading4", path1.get(3));

        return "/mypetboard/accommodation/getAccommodation";
    }
    /** 슥소 삭제 */
    @GetMapping("/deleteAccommodation")
    public String deleteAccommodation(Accommodation accommodation){
        accommodationService.deleteAccommodation(accommodation);
        return "redirect:/mypetboard/accommodation/getAccommodationList";
    }

    /** 숙소 정보 업데이트*/
    @GetMapping("/updateAccommodation")
    public String updateAccommodationView(Accommodation accommodation, Model model) {
        model.addAttribute("accommodation" , accommodation);

        return "mypetboard/accommodation/updateAccommodation";
    }

    @PostMapping("/updateAccommodation")
    public String updateAccommodation(Accommodation accommodation) {
        accommodationService.updateAccommodation(accommodation);

        return "redirect:/mypetboard/accmmodation/getAccommodationList";
    }

    //    룸 등록 Controller
    @RequestMapping("/insertRoom/{seq}")
    public String insertRoomView(@PathVariable("seq")Long seq,
                                 Model model , Room room) {
        /* 룸 등록 페이지 뷰 */
        Accommodation accommodation = accommodationService.getAccommodationRequest(seq);
        model.addAttribute("accommodation" , accommodation);
        List<RoomImage> roomImageList = roomService.getRoomImageEntity(accommodation.getSeq());
        List<String> path1 = new ArrayList<>();

        for(RoomImage ri: roomImageList) {
            String savePath =  "/mypetboard/accommodation/image/"+ ri.getUuid()+"_"+ ri.getOriginalFilename();
            path1.add(savePath);
        }

        model.addAttribute("rimgLoading", path1);

        return "/mypetboard/accommodation/insertRoom";
    }
    //룸 등록 Controller
    @PostMapping("/insertRoom/{seq}")
    public String insertRoom(@PathVariable("seq")Long seq , Model model ,
                             @RequestParam String roomName,
                             @RequestParam String roomInfo,
                             @RequestParam Integer roomPrice,
                             @RequestParam String roomType,
                             @RequestParam(value = "roomimage" , required = false)MultipartFile[] roomimage)  {
        /* 룸 등록 페이지 폼 */
//        Accommodation accommodation = accommodationService.getAccommodationRequest(seq);
//        Room room = new Room();
//        room.setRoomName(roomName);
//        room.setRoomType(roomType);
//        room.setRoomPrice(roomPrice);
//        room.setRoomInfo(roomInfo);
//        room.setAccommodation(accommodation);
//
//
//        roomService.insertRoom(room);

        try{
            Accommodation accommodation = accommodationService.getAccommodationRequest(seq);
            Room room = new Room();
            room.setRoomName(roomName);
            room.setRoomType(roomType);
            room.setRoomPrice(roomPrice);
            room.setRoomInfo(roomInfo);
            room.setAccommodation(accommodation);

            roomService.insertRoom(room);
            //게시글 정보 저장
            Long roomSeq = roomService.insertRoom(room);
            Long accSeq = accommodationService.insertAccommodation(accommodation);
            //파일의 메타데이터 저장
            for(MultipartFile file : roomimage) {
                if(!file.isEmpty()){

                    RoomImage entity = new RoomImage(null,
                            UUID.randomUUID().toString(),
                            file.getContentType(),
                            file.getName(),
                            file.getOriginalFilename(),
                            roomSeq,accSeq);//어떤 게시글에 이미지가 연결이 되어 있나

                    roomService.insertRoomImage(entity);

                    File newFileName = new File(entity.getUuid()+"_"+ entity.getOriginalFilename());
                    //서버에 이미지 파일 업로드(저장)
                    file.transferTo(newFileName);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return String.format("redirect:/mypetboard/accommodation/insertRoom/%s", seq);
    }
}