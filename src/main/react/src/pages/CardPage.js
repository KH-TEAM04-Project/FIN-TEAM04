// CardPage.js
import React, { useEffect, useState } from 'react';
import axios from "axios";
import { useNavigate } from 'react-router-dom';

import CardForm from '../sections/auth/Card/CardForm';

function CardPage() {
  const navigate = useNavigate();

  const [columnInfo, setColumnInfo] = useState({
    mno: Number,
    lifeinsurance: Number,
    npension: Number,
    insurance: Number,
    medi: Number,
    edu: Number,
    card: Number,
    dcard: Number,
    pension: Number,
    cash: Number,
    housefunds: Number,
    housesaving: Number,
    invest: Number,
    sbo: Number,
    donation: Number,
    year: ""
  });

  const token = localStorage.getItem('accessToken');
  const sub = token ? JSON.parse(atob(token.split('.')[1])).sub : '';

  useEffect(() => {
    if (token) {
      // 토큰을 디코딩하여 payload 부분을 추출하고 JSON 파싱
      const decodedToken = JSON.parse(atob(token.split('.')[1]));

      axios.post("/tax/taxrefund", null, {
        headers: {
          // http 헤더의 auth 부분에 accessToken 값 설정
          'Authorization': `Bearer ${token}`
        }
      })
        .then(response => {
          // 사용자 데이터를 성공적으로 가져온 경우
          const columnInfo = response.data;

          // 사용자 정보를 상태에 설정
          setColumnInfo(columnInfo);
        })
        .catch(error => {
          // API 호출 중 에러 발생한 경우
          console.error(error);
        });
    }
  }, [token]);

  return (
    <div>
      <h1><strong>{sub}</strong> 님께 카드를 추천해드립니다.</h1>
      
      <CardForm columnInfo={columnInfo} />
    </div>
  );
}

export default CardPage;
