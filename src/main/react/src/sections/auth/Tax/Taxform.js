import {Grid, Container, Typography} from "@mui/material";
import React, {useEffect, useState} from 'react';
import './TaxForm.css';
import axios from "axios";
import {useNavigate} from 'react-router-dom';

import Nav from '../../../layouts/dashboard';

import {AppWidgetSummary} from "../../@dashboard/app";

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
                {(
                    <>
                        <Container maxWidth="xl">
                            <Typography variant="h4" sx={{mb: 5}}>
                                {columnInfo.year}년도 {sub}님의 연말정산 결과
                            </Typography>
                            <Grid container spacing={3}>
                                <Grid item xs={10} sm={5} md={2}>
                                    <AppWidgetSummary title="국민연금" total={columnInfo.npension}/>
                                </Grid>
                                <Grid item xs={10} sm={5} md={2}>
                                    <AppWidgetSummary title="건강보험" total={columnInfo.lifeinsurance} color="info"/>
                                </Grid>
                                <Grid item xs={10} sm={5} md={2}>
                                    <AppWidgetSummary title="보험료" total={columnInfo.insurance} color="warning"/>
                                </Grid>
                                <Grid item xs={10} sm={5} md={2}>
                                    <AppWidgetSummary title="의료비" total={columnInfo.medi} color="error"/>
                                </Grid>
                                <Grid item xs={10} sm={5} md={2}>
                                    <AppWidgetSummary title="교육비" total={columnInfo.edu}/>
                                </Grid>
                                <Grid item xs={10} sm={5} md={2}>
                                    <AppWidgetSummary title="신용카드" total={columnInfo.card} color="info"/>
                                </Grid>
                                <Grid item xs={10} sm={5} md={2}>
                                    <AppWidgetSummary title="체크카드" total={columnInfo.dcard} color="warning"/>
                                </Grid>
                                <Grid item xs={10} sm={5} md={2}>
                                    <AppWidgetSummary title="개인연금저축/연금계좌" total={columnInfo.pension} color="error"/>
                                </Grid>
                                <Grid item xs={10} sm={5} md={2}>
                                    <AppWidgetSummary title="현금영수증" total={columnInfo.cash}/>
                                </Grid>
                                <Grid item xs={10} sm={5} md={2}>
                                    <AppWidgetSummary title="주택마련저축" total={columnInfo.housesaving} color="info"/>
                                </Grid>
                                <Grid item xs={10} sm={5} md={2}>
                                    <AppWidgetSummary title="주택자금/월세액" total={columnInfo.housefunds} color="warning"/>
                                </Grid>
                                <Grid item xs={10} sm={5} md={2}>
                                    <AppWidgetSummary title="장기집합투자장권저축/벤처기업투자신탁" total={columnInfo.invest}
                                                      color="error"/>
                                </Grid>
                                <Grid item xs={10} sm={5} md={2}>
                                    <AppWidgetSummary title="소기업/소상고인 공제부금" total={columnInfo.sbo}/>
                                </Grid>
                                <Grid item xs={10} sm={5} md={2}>
                                    <AppWidgetSummary title="기부금" total={columnInfo.donation} color="info"/>
                                </Grid>
                                <div className={"center-container2"}>
                                    <button>나의 솔루션 보러가기</button>
                                    <button onClick={handleGoBack}>메인페이지</button>
                                </div>
                            </Grid></Container>
                    </>
                )}
            </div>
        </div>

    );
}

export default TaxForm;
