package com.kh.team4.controller;

import com.kh.team4.dto.QnaDTO;
import com.kh.team4.service.QnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qna")
@CrossOrigin(origins = "http://localhost:3000")
@Log4j2

public class QnaController {
    private final QnaService qnaService;

    //  게시글 작성(CREATE)
    @PostMapping("/regist")
    public ResponseEntity<String> register(@RequestHeader("Authorization") String data, @RequestBody QnaDTO qnaDTO) {
        System.out.println("게시글 작성 컨트롤러 진입");
        // "/dashboard/write" 주소로 POST 요청이 들어오면 QnaDTO를 받아서 QnaService의 register() 메서드를 호출
        String atk = data.substring(7);
        System.out.println("atk : " + atk);
        qnaService.register(qnaDTO, atk);
        System.out.println("qnaDTO:" + qnaDTO);
        // register() 메서드에서는 QnaDTO를 이용하여 게시글을 작성하고, Qna 객체를 데이터베이스에 저장.
        return new ResponseEntity<>("게시글이 작성되었습니다.", HttpStatus.OK);
        // 성공적으로 수행되면 "게시글이 작성되었습니다" 메시지와 함께 HttpStatus.OK(200) 상태 코드를 반환.
    }
    // 게시글 리스트 불러오기
    @GetMapping("/list")
    @ResponseBody
    public List<QnaDTO> qnaList() {
        System.out.println("리스트 불러오는 컨트롤러 진입");
        // DB에서 전체 게시글 데이터를 가져와서 list.html에 보여준다.
        List<QnaDTO> qnaDTOList = qnaService.findAll();
        System.out.println("qnaDTOList" + qnaDTOList);

        return qnaDTOList;
    }
    // 상세보기 페이지
    @GetMapping({"/detail/{qno}", "/update/{qno}"}) // id 값을 받아온다.
    public ResponseEntity<QnaDTO> findById(@RequestHeader("Authorization") String data, @PathVariable("qno") Long qno) {
        log.info("상세페이지/수정 컨트롤러");
        // 조회수 하나를 올리고 게시글 데이터 가져와서 나타내야 함
        String atk = data.substring(7);
        System.out.println("atk : " + atk);
        qnaService.updateHits(qno);
        return ResponseEntity.ok(qnaService.findById(qno, atk));
    }
    // 게시글 삭제(DELETE)
    @DeleteMapping("/delete/{qno}")
    public String delete(@RequestHeader("Authorization") String data, @PathVariable("qno") Long qno) {
        System.out.println("삭제 컨트롤러 진입");
        String atk = data.substring(7);
        System.out.println("atk : " + atk);
        qnaService.delete(qno, atk);
        System.out.println("서비스에서 delete 함수 호출");
        return "/delete";
    }
    // 게시글 수정
    @PutMapping("/update/{qno}")
    public ResponseEntity<Long> update(@RequestHeader("Authorization") String data, @PathVariable Long qno, @RequestBody QnaDTO qnaDTO) {
        // @PathVariable Long qno를 추가하여 qno 값을 메서드에 직접 전달하고
        // qnaDTO.setQno(qno)를 통해 qnaDTO 객체에도 qno 값을 설정.
        log.info("업데이트 컨트롤러 진입");
        String atk = data.substring(7);
        System.out.println("atk : " + atk);
        qnaDTO.setQno(qno);  // qno 값을 qnaDTO에 설정
        Long updateQno = qnaService.modify(qnaDTO, atk);
        log.info("수정 완료 qno: " + updateQno);
        return ResponseEntity.ok(updateQno);
    }
}