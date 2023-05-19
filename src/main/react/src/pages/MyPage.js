import React, { useState, useEffect } from "react";
import axios from 'axios';
import MyPageForm from "../sections/auth/MyPage/MyPageForm";

function MyPage() {
  const [userData, setUserData] = useState({
    mname: "",
    mid: "",
    regno: "", // Resident Registration Number
    email: "",
    address: "",
    detailAddress: "",
    ph: ""
  });

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
      axios.post("/MyPageCont", { mno })
        .then(response => {
          // 사용자 데이터를 성공적으로 가져온 경우
          const userData = response.data;

          // 사용자 정보를 상태에 설정
          setUserData(userData);
        })
        .catch(error => {
          // API 호출 중 에러 발생한 경우
          console.error(error);
        });
    }
  }, [token]);

  // Function to display only the first six digits of the Resident Registration Number
  const getMaskedRegNo = () => {
    const { regno } = userData;
    if (regno.length >= 6) {
      return regno.slice(0, 6);
    }
    return regno;
  };

  // Function to handle saving the updated user information and changing the password
  const handleSaveAndChangePassword = () => {
    // Implement the logic to handle saving user information and changing the password
    console.log("Save and Change Password logic goes here");
  };

  // 회원 삭제 함수
  const handleDelete = () => {
    // Send the mno value to the backend
    axios.post("/memberDelete", { mno })
      .then(response => {
        // Successful deletion
        setUserData({
          mname: "",
          mid: "",
          regno: "",
          email: "",
          address: "",
          detailAddress: "",
          ph: ""
        });
        alert("회원 정보가 삭제되었습니다.");
      })
      .catch(error => {
        // Error occurred while deleting
        console.error(error);
      });
  };

  // Function to handle changes in the input fields
  const handleChange = (field, value) => {
    setUserData(prevState => ({
      ...prevState,
      [field]: value
    }));
  };

  return (
    <div>
      <h1>마이페이지</h1>
      <p>이름: {userData.mname}</p>
      <p>아이디: {userData.mid}</p>
      <p>생년월일: {getMaskedRegNo()}</p>
      <p>이메일: {userData.email}</p>
      <p>주소: {userData.address}</p>
      <p>상세주소: {userData.detailAddress}</p>
      <p>휴대폰번호: {userData.ph}</p>

      <MyPageForm onSave={handleChange} />

      {/* 수정 및 비밀번호 변경 버튼 */}
      <button onClick={handleSaveAndChangePassword}>회원 정보 수정</button>

      {/* 회원 삭제 버튼 */}
      <button onClick={handleDelete}>회원 삭제</button>
    </div>
  );
}

export default MyPage;
