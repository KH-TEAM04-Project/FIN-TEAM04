import React, { useState, useEffect } from "react";
import axios from 'axios';
import MyPageForm from "../sections/auth/MyPage/MyPageForm";

function MyPage() {
  const [mname, setMname] = useState(""); // 이름 상태
  const [mid, setMid] = useState(""); // 아이디 상태
  const [regno, setRegno] = useState(""); // 주민등록번호 상태
  const [email, setEmail] = useState(""); // 이메일 상태
  const [pwd, setPwd] = useState(""); // 패스워드 상태
  const [detailaddress, setDetailAddress] = useState(""); // 상세주소 상태
  const [address, setAddress] = useState(""); // 주소 상태
  const [ph, setPh] = useState(""); // 휴대폰번호 상태
  const [mno, setMno] = useState(""); // 토큰에서 추출한 sub 값 상태

  // 로컬 스토리지에서 토큰 값을 가져옴
  const token = localStorage.getItem('accessToken');

  useEffect(() => {
    if (token) {
      // 토큰을 디코딩하여 payload 부분을 추출하고 JSON 파싱
      const decodedToken = JSON.parse(atob(token.split('.')[1]));

      // payload에서 MNO 값을 추출하여 상태에 저장
      setMno(decodedToken.mno);
      console.log(decodedToken.mno); // 추출한 mno 값 콘솔에 출력

      const mno = decodedToken.mno;
      // 백으로 MNO 값을 전송하여 사용자 정보를 가져옴
      axios.post("/MyPageCont", {mno} )


      .then(response => {
        // 사용자 데이터를 성공적으로 가져온 경우
        const userData = response.data;

        // 사용자 정보를 상태에 설정
        setMname(userData.mname);
        setMid(userData.mid);
        setRegno(userData.regno);
        setEmail(userData.email);
        setPwd(userData.pwd);
        setDetailAddress(userData.detailaddress);
        setAddress(userData.address);
        setPh(userData.ph);
      })
      .catch(error => {
        // API 호출 중 에러 발생한 경우
        console.error(error);
      });
    }
  }, [token]);

  // 입력된 정보를 저장하는 함수
  const handleSave = (mname, mid, regno, email, pwd, detailaddress, address, ph) => {
    setMname(mname);
    setMid(mid);
    setRegno(regno);
    setEmail(email);
    setPwd(pwd);
    setDetailAddress(detailaddress);
    setAddress(address);
    setPh(ph);
  };

  // 회원 삭제 함수
  const handleDelete = (password) => {
    if (password === pwd) {
      setMname("");
      setMid("");
      setRegno("");
      setEmail("");
      setPwd("");
      setDetailAddress("");
      setAddress("");
      setPh("");
      alert("회원 정보가 삭제되었습니다.");
    } else {
      alert("비밀번호가 일치하지 않습니다.");
    }
  };

  return (
    <div>
      <h1>마이페이지</h1>
      <p>이름: {mname}</p>
      <p>아이디: {mid}</p>
      <p>주민등록번호: {regno}</p>
      <p>이메일: {email}</p>
      <p>패스워드: {pwd}</p>
      <p>주소: {address}</p>
      <p>상세주소: {detailaddress}</p>
      <p>휴대폰번호: {ph}</p>

      <MyPageForm onSave={handleSave} />

      {/* 회원 삭제 폼 */}
      <form onSubmit={(e) => {
        e.preventDefault();
        handleDelete(e.target.password.value);
      }}>
        <div>
          <label htmlFor="password">비밀번호입력</label>
          <input
            type="password"
            id="password"
            name="password"
          />
        </div>
        <button type="submit">회원 삭제</button>
      </form>
    </div>
  );
}

export default MyPage;