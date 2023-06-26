import React, { useState, useEffect } from 'react';
import jwtDecode from 'jwt-decode';
import axios from 'axios';

const CombinedComponent = (props) => {
  const [tokenExpiry, setTokenExpiry] = useState(null);
  const [time, setTime] = useState(null);
  const [loggedIn, setLoggedIn] = useState(false);

  useEffect(() => {
    const refreshToken = localStorage.getItem('accessToken');
    if (refreshToken) {
      const decodedToken = jwtDecode(refreshToken);
      setTokenExpiry(decodedToken.exp);
      setLoggedIn(true);
    } else {
      setLoggedIn(false);
    }
  }, [loggedIn]);

  useEffect(() => {
    if (!loggedIn) return () => {};

    const timer = setInterval(() => {
      const currentTime = Math.floor(Date.now() / 1000);
      const timeRemaining = tokenExpiry - currentTime;
      setTime(timeRemaining);

      if (timeRemaining <= 0) {
        handleLogout();
        clearInterval(timer);
      }
    }, 1000);

    return () => clearInterval(timer);
  }, [tokenExpiry, loggedIn]);

  const handleButtonClick = async () => {
    try {
      const atk = localStorage.getItem('accessToken');
      const refreshToken = localStorage.getItem('refreshToken');
      const response = await axios.post('/member/reissue', { refreshToken }, {
          headers: {
              'Authorization': `Bearer ${atk}`
          }
      });
      localStorage.setItem('accessToken', response.data.accessToken);
      localStorage.setItem('refreshToken', response.data.refreshToken);
      
      const decodedToken = jwtDecode(response.data.accessToken); // 새로 발급된 accessToken을 사용하여 디코딩
      setTokenExpiry(decodedToken.exp); // 새로운 만료 시간을 설정

    } catch (error) {
      console.error(error);
    }
  };

  const displayTokenInfo = () => {
    if (!tokenExpiry) {
      return '로그인이 필요합니다.';
    }

    if (time <= 0) {
      return '재로그인 해주세요.';
    }

    const minutes = Math.floor(time / 60);
    const seconds = time % 60;

    return `로그인 유효 시간: ${minutes}분 ${seconds}초`;
  };

  const handleLogout = () => {
    localStorage.removeItem('accessToken');
    setLoggedIn(false);
    setTokenExpiry(null);
    setTime(null);
    
  };

  return (
    <div style={{ width: '210px' }}> 
      <div style={{ backgroundColor: 'orange' }}>
        {displayTokenInfo()}
      </div>
      {loggedIn && <button onClick={handleButtonClick}>연장하기</button>}
    </div>
  );
};

export default CombinedComponent;
