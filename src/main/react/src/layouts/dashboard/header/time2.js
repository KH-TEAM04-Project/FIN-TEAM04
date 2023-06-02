import React, {useState} from 'react';
import axios from 'axios';

const YourComponent = () => {
    const [tokenExpiry, setTokenExpiry] = useState(null);
    const handleButtonClick = async () => {
        try {
            const response = await axios.post('/reissue'); // POST 요청 보내기
            console.log(response.data); // 응답 데이터 확인
            // 여기에서 상태 업데이트 또는 다른 동작 수행
        } catch (error) {
            console.error(error); // 에러 처리
        }
    };

    return (
        <button onClick={handleButtonClick}>연장하기</button>
    );
};

export default YourComponent;
