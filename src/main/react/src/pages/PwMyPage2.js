import React, { useState, useEffect } from "react";
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import Swal from 'sweetalert2';

function PwMyPage2() {
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const [mno, setMno] = useState(""); // 토큰에서 추출한 sub 값 상태

    // 로컬 스토리지에서 토큰 값을 가져옴
    const token = localStorage.getItem('accessToken');

    useEffect(() => {
        if (token) {
            // 토큰을 디코딩하여 payload 부분을 추출하고 JSON 파싱
            const decodedToken = JSON.parse(atob(token.split('.')[1]));

            // payload에서 MNO 값을 추출하여 상태에 저장
            setMno(decodedToken.mno);
        }
    }, [token]);

    const handlePasswordChange = (e) => {
        setPassword(e.target.value);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            // mno 값과 비밀번호를 함께 백으로 전송하여 사용자 정보를 확인
            console.log("mno:", mno); // mno 값 콘솔에 출력
            console.log("password:", password); // 비밀번호 값 콘솔에 출력

            const response = await axios.post("/intoTaxrefund", {
                mno,
                pwd: password
            });

            // 백엔드에서 비밀번호 일치 여부에 따른 응답 처리
            const isPasswordMatch = response.data; // 받은 데이터가 true 또는 false인지 확인
            console.log(isPasswordMatch);
            if (isPasswordMatch) {
                navigate('/tax/main');
            } else {
                // 비밀번호가 일치하지 않는 경우에 대한 처리를 수행합니다.
                Swal.fire({
                    icon: 'error',
                    title: '비밀번호가 일치하지 않습니다.',
                    text: '다시 시도해주세요.'
                });
            }
        } catch (error) {
            // 에러 처리
            console.error(error);
        }
    };

    const handleGoBack = () => {
        navigate(-1);
    };

    return (
        <div>
            <h2>연말정산 페이지</h2>
            <p>연말정산 페이지를 들어가시려면 비밀번호를 입력하셔야 합니다.</p>
            <p>회원님의 개인정보보호를 위한 본인 확인 절차이오니, 로그인 시 사용하시는 비밀번호를 입력해주세요.</p>
            <form onSubmit={handleSubmit}>
                <label>
                    비밀번호:
                    <input type="password" value={password} onChange={handlePasswordChange} />
                </label>
                <button type="submit">확인</button>
                <button type="button" onClick={handleGoBack}>뒤로 가기</button>
            </form>
        </div>
    );
}

export default PwMyPage2;
