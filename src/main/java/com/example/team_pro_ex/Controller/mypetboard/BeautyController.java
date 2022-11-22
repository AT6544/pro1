package com.example.team_pro_ex.Controller.mypetboard;

import com.example.team_pro_ex.Entity.mypetboard.accommodation.Accommodation;
import com.example.team_pro_ex.Entity.mypetboard.beauty.Beauty;
import com.example.team_pro_ex.Entity.mypetboard.common.BeautyImage;

import com.example.team_pro_ex.Security.Member.PrincipaDetailsMember;
import com.example.team_pro_ex.Service.mypetboard.Beauty.BeautyService;
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
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping(path = "/mypetboard/beauty")
public class BeautyController {
    private BeautyService beautyService;

    @Autowired
    protected BeautyController(BeautyService beautyService) {
        this.beautyService = beautyService;
    }

    @GetMapping("/insertBeauty")
    public String insertBeauty(Beauty beauty, Model model) {
        //model.addAttribute("ownerMember", ownerMemberService.getOwnerMember(ownerMember));
        model.addAttribute("beauty", beauty);
        return "mypetboard/beauty/insertBeauty";
    }

    //로컬 저장소에 저장 (DB에 값 저장)
    @PostMapping("/insertBeauty")
    public String insertBeauty(
            @RequestParam(value="beautyimage", required = false)MultipartFile[] beautyimage, @AuthenticationPrincipal PrincipaDetailsMember userDetails,
            Beauty beauty){
        try{
            //파일의 메타데이터 저장
            for(MultipartFile file : beautyimage) {
                if(!file.isEmpty()){
                    /*beauty ImgName 컬럼에 uuid + originalfilename 형식으로 저장*/
                    beauty.setMember(userDetails.getMember());
                    beauty.setCreateDate(new Date());
                    beauty.setBookingDate(new Date());
//                    beauty.setBookingDate(new Date());
                    String uuid = UUID.randomUUID().toString();
                    beauty.setImgName(uuid+"_"+file.getOriginalFilename());

                    //게시글 정보 저장
                    Long beautySeq = beautyService.insertBeauty(beauty);
                    BeautyImage entity = new BeautyImage(null,
                            uuid,
                            file.getContentType(),
                            file.getName(),
                            file.getOriginalFilename(),
                            beautySeq);//어떤 게시글에 이미지가 연결이 되어 있나
                    beautyService.insertBeautyImage(entity);

                    File newFileName = new File(uuid+"_"+ entity.getOriginalFilename());
                    //서버에 이미지 파일 업로드(저장)
                    file.transferTo(newFileName);
                }
                else {
                    return "mypetboard/beauty/insertBeauty";
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/mypetboard/beauty/getBeautyList";
    }

    //이미지 전송
    @GetMapping(value = "/image/{imgname}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> imageLoading(@PathVariable("imgname")String input_imgName) throws IOException {
        String path = "C:/work/MyPet/Team_pro_ex/image/"+input_imgName;
        InputStream fis = new FileInputStream(path);
        BufferedInputStream bis =  new BufferedInputStream(fis);
        byte[] imgByteArr = bis.readAllBytes();
        fis.close();
        return new ResponseEntity<byte[]>(imgByteArr , HttpStatus.OK);
    }

    @GetMapping ("/getBeautyList")
    public String getAccommodationList(
            @RequestParam(value= "searchKeyword" , required = false , defaultValue = "")String searchKeyword,
            @RequestParam(value = "PetSize" , required = false, defaultValue = "")String petSize,
            Model model, Accommodation accommodation, @PageableDefault(size = 10 , sort = "seq", direction = Sort.Direction.ASC ) Pageable pageable) {
        /* 한 화면에 size 글 10개 , sort = "seq" 정렬(시퀀스 번호로 정렬 하겠다) , direction = Sort.Direction.ASC(오름차순으로 정렬)  */

        /* 객체 초기화 */
        Page<Beauty> beauty = null;

        /* 카테고리분류(search기능) -> 카테고리와 숙소 이름으로 검색 */
        if(petSize.equals("소형")){
            System.out.println("소형");
            beauty = beautyService.getBeautyContainsName(searchKeyword, pageable);
        }
        else if(petSize.equals("중형")){
            System.out.println("중형");
            beauty = beautyService.getBeautyContainsName(searchKeyword, pageable);
        }
        else if(petSize.equals("대형")){
            System.out.println("대형");
            beauty = beautyService.getBeautyContainsName(searchKeyword, pageable);
        }
        else if(petSize.equals("전체")) {
            System.out.println("전체");
            beauty = beautyService.getBeautyContainsName(searchKeyword, pageable);
        }
        else {
            beauty = beautyService.getBeautyContainsName(searchKeyword, pageable);
        }
        System.out.println("[petSize= " + petSize + "]," + " [searchKeyword= " + searchKeyword + "]");

        model.addAttribute("petSize", petSize);
        model.addAttribute("searchKeyword", searchKeyword);
        model.addAttribute("BeautyList", beauty);
        //페이징처리 이전 ,다음
        model.addAttribute("previous" , pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next" , pageable.next().getPageNumber());
        return "/mypetboard/beauty/getBeautyList";
    }

    @GetMapping("/getBeauty")
    public String getBeauty(Beauty beauty, Model model) {
        /* 룸 정보 뷰에 뿌려주기 */
        model.addAttribute("beauty", beautyService.getBeauty(beauty));
        System.out.println("--------" + beautyService.getBeauty(beauty));
        /* 저장된 숙소이미지 불러와서 뷰에 뿌려주기 */
        BeautyImage beautyImage = beautyService.getBeautyImageEntity(beauty.getSeq());
        String path = "/mypetboard/beauty/image/"+ beautyImage.getUuid()+"_"+ beautyImage.getOriginalFilename();
        model.addAttribute("imgLoding", path);

        return "/mypetboard/beauty/getBeauty";
    }

    @GetMapping("/deleteBeauty")
    public String deleteBeauty(Beauty beauty){
        beautyService.deleteBeauty(beauty);
        return "redirect:/mypetboard/beauty/getBeautyList";
    }

    @GetMapping("/updateBeauty")
    public String updateBeauty(Beauty beauty, Model model) {
        model.addAttribute("beauty", beauty);
        return "mypetboard/beauty/updateBeauty";
    }

    @PostMapping("/updateBeauty")
    public String updateBeauty(Beauty beauty) {
        beautyService.updateBeauty(beauty);
        return "redirect:/mypetboard/beauty/getBeautyList";
    }
}
