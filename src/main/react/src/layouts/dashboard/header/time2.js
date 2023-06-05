import React, {useState} from 'react';
import axios from 'axios';
import { LocalGasStationRounded } from '@mui/icons-material';
import { render } from 'react-dom';

const YourComponent = () => {
    const atk = localStorage.getItem('accessToken');
    const refreshToken = localStorage.getItem('refreshToken');
    const handleButtonClick = async () => {
        try {
            const response = await axios.post('/member/reissue', { refreshToken }, {
                headers: {
                    // http 헤더의 auth 부분에 accessToken 값 설정
                    'Authorization': `Bearer ${atk}`
                }
            }).then(response => {
                localStorage.setItem('accessToken', response.data.accessToken);
                localStorage.setItem('refreshToken', response.data.refreshToken);
            }) 
            
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
