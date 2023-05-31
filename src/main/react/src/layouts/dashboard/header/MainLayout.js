// MainLayout.js
import React from 'react';
import TokenInfo from '../time'; // 경로에 따라 수정

const MainLayout = ({ children }) => {
  return (
    <div>
      <div className="time-container">
        <TokenInfo />
      </div>
      <div className="main-content">{children}</div>
    </div>
  );
};

export default MainLayout;