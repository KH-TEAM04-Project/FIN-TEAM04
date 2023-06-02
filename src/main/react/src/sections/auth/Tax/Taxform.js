import React, {useEffect, useState} from 'react';
import './TaxForm.css';
import axios from "axios";
import {useNavigate} from 'react-router-dom';
import Nav from '../../../layouts/dashboard';

function TaxForm() {

    const navigate = useNavigate();

    const handleGoBack = () => {
        navigate(-2); // Go back one step in the browser history
    };

    const [columnInfo, setColumInfo] = useState({
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

    // 로컬 스토리지에서 토큰 값을 가져옴
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
                    setColumInfo(columnInfo);
                })
                .catch(error => {
                    // API 호출 중 에러 발생한 경우
                    console.error(error);
                });
        }
    }, [token]);


    return (
        <div>
            <Nav/> {/* 사이드바 컴포넌트를 추가 */}

            <div className={"info-box"}>
                <h1>{sub}님의 연말정산 결과</h1>
                {(
                    <>
                        <p className={"info-box2"}>회원번호 : {columnInfo.mno}</p>
                        <p className={"info-box2"}>해당연도 : {columnInfo.year}</p>
                        <p className={"info-box2"}>건강보험 : {columnInfo.lifeinsurance}</p>
                        <p className={"info-box2"}>국민연금 : {columnInfo.npension}</p>
                        <p className={"info-box2"}>보험료 : {columnInfo.insurance}</p>
                        <p className={"info-box2"}>의료비 : {columnInfo.medi}</p>
                        <p className={"info-box2"}>교육비 : {columnInfo.edu}</p>
                        <p className={"info-box2"}>신용카드 : {columnInfo.card}</p>
                        <p className={"info-box2"}>체크카드 : {columnInfo.dcard}</p>
                        <p className={"info-box2"}>개인연금저축/연금계좌 : {columnInfo.pension}</p>
                        <p className={"info-box2"}>현금영수증 : {columnInfo.cash}</p>
                        <p className={"info-box2"}>주택마련저축 : {columnInfo.housesaving}</p>
                        <p className={"info-box2"}>주택자금/월세액 : {columnInfo.housefunds}</p>
                        <p className={"info-box2"}>장기집합투자장권저축/벤처기업투자신탁 : {columnInfo.invest}</p>
                        <p className={"info-box2"}>소기업/소상고인 공제부금 : {columnInfo.sbo}</p>
                        <p className={"info-box2"}>기부금 : {columnInfo.donation}</p>
                        <button>나의 솔루션 보러가기</button>
                        <button onClick={handleGoBack}>메인페이지</button>
                    </>
                )}
            </div>
        </div>

    );
}

export default TaxForm;
