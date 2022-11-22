package com.example.team_pro_ex.Controller.mypetboard;

import com.example.team_pro_ex.Entity.mypetboard.accommodation.Accommodation;
import com.example.team_pro_ex.Entity.mypetboard.accommodation.Room;
import com.example.team_pro_ex.Entity.mypetboard.common.MenuImage;
import com.example.team_pro_ex.Entity.mypetboard.common.RoomImage;
import com.example.team_pro_ex.Entity.mypetboard.foodandcafe.Menu;
import com.example.team_pro_ex.Security.Member.PrincipaDetailsMember;
import com.example.team_pro_ex.Service.mypetboard.foodandcafe.FoodCafeService;
import com.example.team_pro_ex.Entity.mypetboard.common.FoodCafeImage;
import com.example.team_pro_ex.Entity.mypetboard.foodandcafe.FoodCafe;
import com.example.team_pro_ex.Service.mypetboard.foodandcafe.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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

@Controller
@RequestMapping(path = "/mypetboard/foodCafe")
public class FoodCafeController {
    private final FoodCafeService foodcafeService;

    private final MenuService menuService;

    @Autowired
    protected FoodCafeController(FoodCafeService foodcafeService, MenuService menuService) {

        this.foodcafeService = foodcafeService;
        this.menuService = menuService;
    }

    @GetMapping("/insertFoodCafe")
    public String insertFoodCafeView(FoodCafe foodcafe, Model model) {
        model.addAttribute("foodcafe", foodcafe);
        return "mypetboard/foodCafe/insertFoodCafe";
    }

    @PostMapping("insertFoodCafe")
    public String insertFoodCafe(FoodCafe foodcafe, @AuthenticationPrincipal PrincipaDetailsMember userDetails, @RequestParam(value ="foodcafeimage",  required = false) MultipartFile[] foodcafeimage) {
        try {


            for (MultipartFile file : foodcafeimage) {
                foodcafe.setMember(userDetails.getMember());
                foodcafe.setCreateDate(new Date());
                foodcafe.setBookingDate(new Date());

                String uuid = UUID.randomUUID().toString();
                foodcafe.setImgname(uuid+"_"+file.getOriginalFilename());

                Long foodcafeSeq = foodcafeService.insertFoodCafe(foodcafe);

                if (!file.isEmpty()) {
                    FoodCafeImage entity = new FoodCafeImage(
                            null,
                            uuid,
                            file.getContentType(),
                            file.getName(),
                            file.getOriginalFilename(),
                            foodcafeSeq);

                    foodcafeService.insertFoodCafeImage(entity);

                    File newFileName = new File(uuid + "_" + entity.getOriginalFilename());
                    file.transferTo(newFileName);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //hotelService.insertHotel(hotel);
        return "redirect:/mypetboard/foodCafe/getFoodCafeList";
    }

    @GetMapping ("/getFoodCafeList")
    public String getFoodCafeList( @RequestParam(value= "searchKeyword" , required = false , defaultValue = "")String searchKeyword,
                                   @RequestParam(value = "detailCategory" , required = false, defaultValue = "")String detailCategory,
            FoodCafe foodcafe , Model model, @PageableDefault(size = 10 , sort = "seq", direction = Sort.Direction.ASC ) Pageable pageable) {
        /*한 화면에 size 글 10개 , sort = "seq" 정렬(시퀀스 번호로 정렬 하겠다) , direction = Sort.Direction.ASC(오름차순으로 정렬)  */
        Page<FoodCafe> foodCafes = null;

        /* 카테고리분류(search기능) -> 카테고리와 숙소 이름으로 검색 */
        if(detailCategory.equals("음식점")){
            System.out.println("음식점");
            foodCafes = foodcafeService.findCategoryAndKeyword(searchKeyword, pageable);
        }
        else if(detailCategory.equals("카페")){
            System.out.println("카페");
            foodCafes =  foodcafeService.findCategoryAndKeyword(searchKeyword, pageable);
        }
        else {
            foodCafes = foodcafeService.getFoodCafeContainsName(searchKeyword, pageable);
        }


        model.addAttribute("detailCategory", detailCategory );
        model.addAttribute("searchKeyword", searchKeyword );
        model.addAttribute("foodCafeList", foodCafes );

        model.addAttribute("previous" , pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next" , pageable.next().getPageNumber());


        return "/mypetboard/foodCafe/getFoodCafeList";
    }

    @GetMapping("/getFoodCafe")
    public String getFoodcafe(FoodCafe foodcafe , Model model) {

        model.addAttribute(foodcafeService.getFoodCafe(foodcafe));


        model.addAttribute("menu1" ,   menuService.getMenuList().get(0));
        System.out.println(menuService.getMenuList().get(0));
        model.addAttribute("menu2" ,   menuService.getMenuList().get(1));
        System.out.println(menuService.getMenuList().get(1));
        model.addAttribute("menu3" ,   menuService.getMenuList().get(2));
        model.addAttribute("menu4" ,   menuService.getMenuList().get(3));

        FoodCafeImage foodcafeImage = foodcafeService.getFoodCafeImageEntity(foodcafe.getSeq());
        String path = "/mypetboard/foodCafe/image/"+foodcafeImage.getUuid()+"_"+foodcafeImage.getOriginalFilename();
        model.addAttribute("imageview", path);


        List<MenuImage> menuImageList = menuService.getMenuImageEntity(foodcafe.getSeq());
        List<String> path1 = new ArrayList<>();

        for(MenuImage mi: menuImageList ) {
            String savePath =  "/mypetboard/foodCafe/image/"+ mi.getUuid()+"_"+ mi.getOriginalFilename();
            path1.add(savePath);
        }



        model.addAttribute("mimgLoading1", path1.get(0));
        model.addAttribute("mimgLoading2", path1.get(1));
        model.addAttribute("mimgLoading3", path1.get(2));
        model.addAttribute("mimgLoading4", path1.get(3));

        return "mypetboard/foodCafe/getFoodCafe";
    }

    @GetMapping("/image/{imageview}")
    public ResponseEntity<byte[]> imageView3(@PathVariable("imageview")String input_imgName) throws IOException {
        //ResponseEntity<Byte[]>:http 프로토콜을 통해서 byte 데이터를 전달하는 객체 , byte(소문자 = 기본타입)
        //@PathVariable: URL주소의 값을 받아옴
        String path = "C:/work/MyPet/Team_pro_ex/image/" +input_imgName;
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
    @GetMapping("/deleteFoodCafe")
    public String deleteFoodCafe(FoodCafe foodCafe){
        foodcafeService.deleteFoodCafe(foodCafe);
        return "redirect:/mypetboard/foodCafe/getFoodCafeList";
    }
    //메뉴 등록 Controller
    @RequestMapping("/insertMenu/{seq}")
    public String insertMenuView(@PathVariable("seq")Long seq, Model model ) {

        /* 룸 등록 페이지 뷰 */
        FoodCafe foodcafe = foodcafeService.getFoodCafeRequest(seq);
        model.addAttribute("foodcafe" , foodcafe);
        List<MenuImage> MenuImageList = menuService.getMenuImageEntity(foodcafe.getSeq());
        List<String> path1 = new ArrayList<>();

        for(MenuImage mi: MenuImageList) {
            String savePath =  "/mypetboard/accommodation/image/"+ mi.getUuid()+"_"+ mi.getOriginalFilename();
            path1.add(savePath);
        }

        model.addAttribute("mimgLoading", path1);

        return "/mypetboard/foodCafe/insertMenu";
    }

    @PostMapping("/insertMenu/{seq}")
    public String insertMenu(@PathVariable("seq")Long seq , Model model ,
                             @RequestParam String menuName,
                             @RequestParam String menuInfo,
                             @RequestParam Integer menuPrice,
                             @RequestParam String menuType,
                             @RequestParam(value = "menuimage" , required = false)MultipartFile[] menuimage)  {
        /* 룸 등록 페이지 폼 */


        try{
            FoodCafe foodCafe = foodcafeService.getFoodCafeRequest(seq);
            Menu menu = new Menu();
            menu.setMenuName(menuName);
            menu.setMenuPrice(menuPrice);
            menu.setMenuType(menuType);
            menu.setMenuInfo(menuInfo);
            menu.setFoodCafe(foodCafe);

            menuService.insertMenu(menu);
            //게시글 정보 저장
            Long roomSeq = menuService.insertMenu(menu);
            Long fcSeq = foodcafeService.insertFoodCafe(foodCafe);
            //파일의 메타데이터 저장
            for(MultipartFile file : menuimage) {
                if(!file.isEmpty()){

                    MenuImage entity = new MenuImage(null,
                            UUID.randomUUID().toString(),
                            file.getContentType(),
                            file.getName(),
                            file.getOriginalFilename(),
                            roomSeq,fcSeq);//어떤 게시글에 이미지가 연결이 되어 있나

                    menuService.insertMenuImage(entity);

                    File newFileName = new File(entity.getUuid()+"_"+ entity.getOriginalFilename());
                    //서버에 이미지 파일 업로드(저장)
                    file.transferTo(newFileName);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return String.format("redirect:/mypetboard/foodCafe/insertMenu/%s", seq);
    }
}
