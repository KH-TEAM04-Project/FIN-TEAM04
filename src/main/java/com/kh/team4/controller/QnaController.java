package com.kh.team4.controller;

import com.kh.team4.dto.BoardDTO;
import com.kh.team4.dto.QnaDTO;
import com.kh.team4.dto.ReplyDTO;
import com.kh.team4.entity.Qna;
import com.kh.team4.service.QnaService;
import com.kh.team4.service.ReplyService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:3000")
@Log4j2

public class QnaController {
    private final QnaService qnaService;

    private final ReplyService replyService;

    // 게시글 작성(CREATE) JWT 적용전
    @PostMapping("/DoardPage")
    public ResponseEntity<String> register(@RequestBody QnaDTO qnaDTO) {
        System.out.println("게시글 작성 컨트롤러 진입");
        // "/dashboard/write" 주소로 POST 요청이 들어오면 QnaDTO를 받아서 QnaService의 register() 메서드를 호출
        qnaService.register(qnaDTO);
        System.out.println("qnaDTO:" + qnaDTO);
        // register() 메서드에서는 QnaDTO를 이용하여 게시글을 작성하고, Qna 객체를 데이터베이스에 저장.
        return new ResponseEntity<>("게시글이 작성되었습니다.", HttpStatus.OK);
        // 성공적으로 수행되면 "게시글이 작성되었습니다" 메시지와 함께 HttpStatus.OK(200) 상태 코드를 반환.
    }
 /*   @PostMapping("/DoardPage")
    public ResponseEntity<QnaDTO> createQna(@RequestBody QnaDTO qnaDTO, @RequestHeader("Authorization") String token) {
        log.info("게시글작성 컨트롤러 진입");
        // JWT 검증 및 사용자 정보 추출
        String jwt = token.substring(7); // "Bearer " 접두사 제거
        Claims claims = Jwts.parser().setSigningKey("yourSecretKey").parseClaimsJws(jwt).getBody();
        String userMno = claims.getSubject(); // 사용자 아이디 추출
        log.info("사용자 아이디 추출 완료");

    // 게시글 작성
        Qna qna = Qna.builder()
            .title(qnaDTO.getTitle())
            .content(qnaDTO.getContent())
            .writer(userMno) // JWT에서 추출한 사용자 아이디를 작성자로 설정
            .secret(qnaDTO.getSecret())
            .build();

    // 작성자 정보를 포함한 응답 DTO 생성
        QnaDTO responseDTO = QnaDTO.toQnaDTO(qna);
        log.info("QnaDTO" + qnaDTO);

        return ResponseEntity.ok(responseDTO);
    }*/


    // 게시글 리스트 불러오기
    @GetMapping("/re")
    @ResponseBody
    public List<QnaDTO> qnaList() {
        System.out.println("리스트 불러오는 컨트롤러 진입");
        // DB에서 전체 게시글 데이터를 가져와서 list.html에 보여준다.
        List<QnaDTO> qnaDTOList = qnaService.findAll();
        System.out.println("qnaDTOList" + qnaDTOList);

        return qnaDTOList;
    }

    // 상세보기 페이지
    @GetMapping({"/QnaReadPage/{qno}", "/QEditPage/{qno}"})    // id 값을 받아온다.
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long qno) {
        log.info("상세보기/수정 컨트롤러 진입");
        // 만약에 페이지 요청이 없는 경우도 있을 수 있으니 @PageableDefault 사용
        // 경로상의 값을 가져올 때는 @PathVariable 라는 어노테이션을 사용한다.
        qnaService.updateHits(qno);
        QnaDTO qnaDTO = qnaService.findById(qno); // 서비스클래스의 findById 메소드 호출해서 boardDTO 객체로 가져옴.
        log.info("findById 메소드 호출" + qnaDTO);

        /* 댓글 목록 가져오기 */
        List<ReplyDTO> replyDTOList = replyService.findAll(qno);
        log.info("댓글 목록 가져오기");

        Map<String, Object> response = new HashMap<>();
        response.put("qnaDTO", qnaDTO);
        response.put("replyList", replyDTOList);


        if (qnaDTO != null) {
            log.info("게시글이 존재하는 경우");
            return ResponseEntity.ok(response); // 게시글이 존재하는 경우 200 OK 상태로 게시글 정보를 리턴
        } else {
            System.out.println("게시글이 존재하지 않은 경우");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // 게시글이 존재하지 않는 경우 404 Not Found 상태를 리턴
        }
    }

    // 게시글 삭제(DELETE)
    @GetMapping("/delete/{qno}")
    public String delete(@PathVariable("qno") Long qno) {
        System.out.println("삭제 컨트롤러 진입");
        qnaService.delete(qno);
        System.out.println("서비스에서 delete 함수 호출");
        return "/delete";
    }

    @PostMapping("/QEditPage/{qno}")
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