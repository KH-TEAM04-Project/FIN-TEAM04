import React, { useState, useEffect } from "react";
import axios from 'axios';
import { useNavigate } from "react-router-dom";

function MyPage() {
  const [userData, setUserData] = useState({
    mname: "",
    mid: "",
    regno: "", // Resident Registration Number
    email: "",
    address: "",
    detailAddress: "",
    ph: "",
    pwd: "" // Include pwd in the initial state
  });
  const navigate = useNavigate();
  const [mno, setMno] = useState(""); // 토큰에서 추출한 sub 값 상태
  const [isEditing, setIsEditing] = useState(false); // 회원 정보 수정 여부 상태

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

  // Function to handle updating the user information
  const handleUpdateMember = (e) => {
    e.preventDefault();

    // Implement the logic to handle updating user information
    const { email, pwd, detailAddress, address, ph } = userData;

    // Create a MemberReqDTO object with the updated information
    const memberReqDTO = {
      mno, // Include the mno value
      email,
      pwd,
      detailAddress,
      address,
      ph
    };

    // Send the updated user information to the backend
    axios.post("/memberUpdate", memberReqDTO)
      .then(response => {
        // Successful update
        const updatedUserData = response.data;

        if (updatedUserData) {
          // Update the user data in the state
          setUserData(updatedUserData);
          setIsEditing(false); // Exit editing mode
          console.log("User information updated successfully");
          alert("사용자 정보가 성공적으로 업데이트되었습니다.");
        } else {
          console.log("Failed to update user information");
          alert("사용자 정보 업데이트에 실패했습니다.");
        }
      })
      .catch(error => {
        // Error occurred while updating
        console.error(error);
        alert("사용자 정보 업데이트 중 에러가 발생했습니다.");
      });
  };

  // Function to handle changes in the input fields
  const handleChange = (field, value) => {
    setUserData(prevState => ({
      ...prevState,
      [field]: value
    }));
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
          ph: "",
          pwd: "" // Include pwd in the initial state
        });
        alert("회원 정보가 삭제되었습니다.");
        document.cookie = "accessToken=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
        navigate("/login");
      })
      .catch(error => {
        // Error occurred while deleting
        console.error(error);
      });
  };

  return (
    <div>
      <h1>마이페이지</h1>
      {userData.mname && !isEditing && (
        <>
          <p>이름: {userData.mname}</p>
          <p>아이디: {userData.mid}</p>
          <p>생년월일: {getMaskedRegNo()}</p>
          <p>이메일: {userData.email}</p>
          <p>주소: {userData.address}</p>
          <p>상세주소: {userData.detailAddress}</p>
          <p>휴대폰번호: {userData.ph}</p>
        </>
      )}

      {isEditing && (
        <form>
          <div>
            <label htmlFor="email">이메일</label>
            <input
              type="text"
              id="email"
              value={userData.email}
              onChange={(e) => handleChange("email", e.target.value)}
            />
          </div>
          <div>
            <label htmlFor="pwd">패스워드</label>
            <input
              type="password"
              id="pwd"
              value={userData.pwd}
              onChange={(e) => handleChange("pwd", e.target.value)}
            />
          </div>
          <div>
            <label htmlFor="detailaddress">상세주소</label>
            <input
              type="text"
              id="detailaddress"
              value={userData.detailAddress}
              onChange={(e) => handleChange("detailAddress", e.target.value)}
            />
          </div>
          <div>
            <label htmlFor="address">주소</label>
            <input
              type="text"
              id="address"
              value={userData.address}
              onChange={(e) => handleChange("address", e.target.value)}
            />
          </div>
          <div>
            <label htmlFor="ph">휴대폰번호</label>
            <input
              type="text"
              id="ph"
              value={userData.ph}
              onChange={(e) => handleChange("ph", e.target.value)}
            />
          </div>
          <button onClick={handleUpdateMember}>회원 정보 수정</button>
        </form>
      )}

      {userData.mname && !isEditing && (
        <button onClick={() => setIsEditing(true)}>회원 정보 수정</button>
      )}

      {/* 회원 삭제 버튼 */}
      <button onClick={handleDelete}>회원 삭제</button>
    </div>
  );
}

export default MyPage;
