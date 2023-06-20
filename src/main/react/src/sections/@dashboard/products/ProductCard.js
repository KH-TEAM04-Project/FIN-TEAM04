import React, { useState } from 'react';
import axios from 'axios';

const YourComponent = () => {
  // 로그인 유효시간이 있는 경우의 초기값 설정
  const initialTokenExpiry = new Date(); // 예시로 현재 시간을 사용
  initialTokenExpiry.setHours(initialTokenExpiry.getHours() + 1); // 현재 시간으로부터 1시간 후로 설정

  const [tokenExpiry, setTokenExpiry] = useState(initialTokenExpiry);

  const handleButtonClick = async () => {
    try {
      const response = await axios.post('/reissue'); // POST 요청 보내기
      console.log(response.data); // 응답 데이터 확인
      // 여기에서 상태 업데이트 또는 다른 동작 수행
    } catch (error) {
      console.error(error); // 에러 처리
    }
  };

  const handleLogout = () => {
    setTokenExpiry(null); // 로그아웃 시 tokenExpiry 값을 null로 업데이트
    // 로그아웃 처리 또는 다른 동작 수행
  };

  return (
    <>
      {tokenExpiry && new Date() < tokenExpiry && (
        <button onClick={handleButtonClick}>연장하기</button>
      )}
      <button onClick={handleLogout}>로그아웃</button>
    </>
  );
};

export default YourComponent;
