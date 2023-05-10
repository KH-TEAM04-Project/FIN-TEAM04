import React, { useState } from "react";

function MyPageForm(props) {
  const [mname, setMname] = useState(""); // 이름 상태
  const [mid, setMid] = useState(""); // 아이디 상태
  const [regno, setRegno] = useState(""); // 주민등록번호 상태
  const [email, setEmail] = useState(""); // 이메일 상태
  const [pwd, setPwd] = useState(""); // 패스워드 상태
  const [detailaddress, setDetailAddress] = useState(""); // 상세주소 상태
  const [address, setAddress] = useState(""); // 주소 상태
  const [ph, setPh] = useState(""); // 휴대폰번호 상태

  // 입력된 정보를 저장하는 함수
  const handleSave = (e) => {
    e.preventDefault();
    props.onSave(mname, mid, regno, email, pwd, detailaddress, address, ph);
  };

  return (
    <form onSubmit={handleSave}>
      {/* <div>
        <label htmlFor="mname">이름</label>
        <input
          type="text"
          id="mname"
          value={mname}
          onChange={(e) => setMname(e.target.value)}
        />
      </div>
      <div>
        <label htmlFor="mid">아이디</label>
        <input
          type="text"
          id="mid"
          value={mid}
          onChange={(e) => setMid(e.target.value)}
        />
      </div>
      <div>
        <label htmlFor="regno">주민등록번호</label>
        <input
          type="text"
          id="regno"
          value={regno}
          onChange={(e) => setRegno(e.target.value)}
        />
      </div> */}
      <div>
        <label htmlFor="email">이메일</label>
        <input
          type="text"
          id="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
      </div>
      <div>
        <label htmlFor="pwd">패스워드</label>
        <input
          type="password"
          id="pwd"
          value={pwd}
          onChange={(e) => setPwd(e.target.value)}
        />
      </div>
      <div>
        <label htmlFor="detailaddress">상세주소</label>
        <input
          type="text"
          id="detailaddress"
          value={detailaddress}
          onChange={(e) => setDetailAddress(e.target.value)}
        />
      </div>
      <div>
        <label htmlFor="address">주소</label>
        <input
          type="text"
          id="address"
          value={address}
          onChange={(e) => setAddress(e.target.value)}
        />
      </div>
      <div>
        <label htmlFor="ph">휴대폰번호</label>
        <input
          type="text"
          id="ph"
          value={ph}
          onChange={(e) => setPh(e.target.value)}
        />
      </div>
      <button type="submit">저장</button>
    </form>
  );
}

export default MyPageForm;
