import React, { useState, useEffect } from 'react';
import jwtDecode from 'jwt-decode';

const TokenInfo = (props) => {
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

    if (props.onLogout) {
      props.onLogout();
    }
  };

  return (
    <div style={{ backgroundColor: 'orange' }}>
      {displayTokenInfo()}
      
    </div>
  );
};

export default TokenInfo;
