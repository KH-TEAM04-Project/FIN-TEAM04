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

      {/* 회원 정보 수정 폼 */}
      
    </div>
  );
}

export default MyPage;
