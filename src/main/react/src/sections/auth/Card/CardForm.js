import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend, Label } from 'recharts';

const ElasticsearchVisualization = () => {
  const [data, setData] = useState([]);

  useEffect(() => {
    // 엘라스틱 서치에서 자료를 가져오는 API 호출
    axios.get('https://example.com/elastic-search-api')
      .then(response => {
        // 자료를 가져오고 상태 업데이트
        setData(response.data);
      })
      .catch(error => {
        console.error('Error fetching data from Elasticsearch:', error);
      });
  }, []);

  const columnInfo = {
    data1: 443,
    data2: 156,
    data3: 789,
    // 엘라스틱 서치 데이터를 받아와서 data1, data2, data3에 넣어서 막대그래프 수치 조정.
  };

  const cardFormData = [
    { name: 'Food', value: columnInfo.data1, image: 'https://api.card-gorilla.com:8080/storage/card/2310/card_img/23676/2310card.png' },
    { name: 'Clothes', value: columnInfo.data2, image: 'https://api.card-gorilla.com:8080/storage/card/762/card_img/26507/762card.png' },
    { name: 'Leisure', value: columnInfo.data3, image: 'https://api.card-gorilla.com:8080/storage/card/2454/card_img/27870/2454card.png' },
    // image에 받아온 데이터 기반으로 URL을 불러오면 됩니다.
  ];

  return (
    <div>
      <h1>당신이 써야 할 카드 top3</h1>
      <div className="cardContainer">
        {cardFormData.map(card => (
          <div className="cardItem" key={card.name}>
            <p>카드 이름</p>
            <img className="cardImage" src={card.image} alt={card.name} />
            <p>{card.name}</p>
          </div>
        ))}
      </div>

      <h2>당신의 소비 패턴 분석</h2>
      <BarChart width={500} height={300} data={cardFormData}>
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis dataKey="name" />
        <YAxis />
        <Tooltip />
        <Legend />
        <Bar dataKey="value" fill="#8884d8">
          <Label content={({ value }) => `${value}`} position="top" />
        </Bar>
      </BarChart>

      <style>
        {`
        .cardContainer {
          display: flex;
          justify-content: space-between;
        }

        .cardItem {
          flex-basis: 30%;
          margin-bottom: 20px;
        }

        .cardImage {
          width: 50%;
          height: auto;
        }
        `}
      </style>
    </div>
  );
};

export default ElasticsearchVisualization;
