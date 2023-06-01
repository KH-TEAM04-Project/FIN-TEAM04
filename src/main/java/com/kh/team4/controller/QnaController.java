package com.kh.team4.controller;

import com.kh.team4.config.SecurityUtil;
import com.kh.team4.dto.BoardDTO;
import com.kh.team4.dto.QnaDTO;
import com.kh.team4.dto.ReplyDTO;
import com.kh.team4.repository.MemberRepository;
import com.kh.team4.service.QnaService;
import com.kh.team4.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qna")
@CrossOrigin(origins = "http://localhost:3000")
@Log4j2

public class QnaController {
    private final QnaService qnaService;
    private final ReplyService replyService;

    private final MemberRepository memberRepository;


    //  게시글 작성(CREATE) JWT 적용전
    @PostMapping("/regist")
    public ResponseEntity<String> register(@RequestBody QnaDTO qnaDTO) {
        System.out.println("게시글 작성 컨트롤러 진입");
        // "/dashboard/write" 주소로 POST 요청이 들어오면 QnaDTO를 받아서 QnaService의 register() 메서드를 호출
        qnaService.register(qnaDTO);
        System.out.println("qnaDTO:" + qnaDTO);
        // register() 메서드에서는 QnaDTO를 이용하여 게시글을 작성하고, Qna 객체를 데이터베이스에 저장.
        return new ResponseEntity<>("게시글이 작성되었습니다.", HttpStatus.OK);
        // 성공적으로 수행되면 "게시글이 작성되었습니다" 메시지와 함께 HttpStatus.OK(200) 상태 코드를 반환.
    // 게시글 작성(CREATE)
//    @PostMapping("/DoardPage")
//    public ResponseEntity<String> register(
//            @RequestBody QnaDTO qnaDTO
//    ) {
//        Long memberId = SecurityUtil.getCurrentMemberId();
//
//        // 회원 ID를 기반으로 게시글 작성 권한을 검사하여 로직을 처리
//        if (isMemberAllowedToWritePost(memberId)) {
//            qnaService.register(qnaDTO);
//            return ResponseEntity.ok("게시글이 작성되었습니다.");
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body("로그인 후에 게시글을 작성할 수 있습니다.");
//        }
//    }
//
//    private boolean isMemberAllowedToWritePost(Long memberId) {
//        // 여기에 회원의 게시글 작성 권한을 검사하는 로직을 작성합니다.
//        // 예를 들어, 회원의 등급, 권한, 상태 등을 확인하여 게시글 작성 여부를 결정할 수 있습니다.
//        // 필요한 권한 검사 로직을 구현한 후에 true 또는 false를 반환합니다.
//        // 예시로 모든 회원에게 게시글 작성 권한을 부여한다고 가정합니다.
//        return true;
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
    public ResponseEntity<QnaDTO> findById(@PathVariable("qno") Long qno) {
        log.info("상세페이지/수정 컨트롤러");
        // 조회수 하나를 올리고 게시글 데이터 가져와서 나타내야 함
        qnaService.updateHits(qno);
        return ResponseEntity.ok(qnaService.findById(qno));
    }

    // 게시글 삭제(DELETE)
    @DeleteMapping("/delete/{qno}")
    public String delete(@PathVariable("qno") Long qno) {
        System.out.println("삭제 컨트롤러 진입");
        qnaService.delete(qno);
        System.out.println("서비스에서 delete 함수 호출");
        return "/delete";
    }

    @PostMapping("/update/{qno}")
    public ResponseEntity<Long> update(@PathVariable Long qno, @RequestBody QnaDTO qnaDTO) {
        // @PathVariable Long qno를 추가하여 qno 값을 메서드에 직접 전달하고
        // qnaDTO.setQno(qno)를 통해 qnaDTO 객체에도 qno 값을 설정.
        log.info("업데이트 컨트롤러 진입");
        qnaDTO.setQno(qno);  // qno 값을 qnaDTO에 설정
        Long updateQno = qnaService.modify(qnaDTO);
        log.info("수정 완료 qno: " + updateQno);
        return ResponseEntity.ok(updateQno);
    }
}