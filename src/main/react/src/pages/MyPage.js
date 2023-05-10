import React, { useState } from "react";
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
    </div>
  );
}

export default MyPage;
