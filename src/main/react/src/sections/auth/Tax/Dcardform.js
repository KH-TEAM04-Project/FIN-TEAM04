import {Grid, Container, Typography} from "@mui/material";
import React, {useEffect, useState} from 'react';
import './TaxForm.css';
import axios from "axios";
import {useNavigate} from 'react-router-dom';

import Nav from '../../../layouts/dashboard';

import {AppWidgetSummary} from "../../@dashboard/app";

function DcardForm() {
    const navigate = useNavigate();

    const [columnInfo, setColumInfo] = useState({
        airport: Number,
        edu: Number,
        npension: Number,
        tran: Number,
        cultural: Number,
        delivery: Number,
        stores: Number,
        giftcard: Number,
        food: Number,
        shop: Number,
        post: Number,
        medi: Number,
        cloth: Number,
        commi: Number,
        travel: Number,
        gas: Number,
        commu: Number,
        coffee: Number,
        conveni: Number,
        overseas: Number,
        foreign: Number,
        hotel: Number,
        publicfee: Number,
        allfor: Number,
    });

    const token = localStorage.getItem('accessToken');
    const sub = token ? JSON.parse(atob(token.split('.')[1])).sub : '';

    useEffect(() => {
        if (token) {
            const decodedToken = JSON.parse(atob(token.split('.')[1]));

            axios.post("/tax/Check", null, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            })
                .then(response => {
                    const columnInfo = response.data;
                    setColumInfo(columnInfo);
                })
                .catch(error => {
                    console.error(error);
                });
        }
    }, [token]);

    const handleGoToCardPage = () => {
        if (token) {
            axios.post('/tax/CheckDetail', null, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
                .then(response => {
                    console.log(response.data);
                })
                .catch(error => {
                    console.error(error);
                });
        }
        navigate('/card');
    };

    const handleGoBack = () => {
        navigate(-2);
    };

    return (
        <div style={{ marginTop: '200px' }}>
            <Nav/>
            <div className={"info-box"}>
                {(
                    <>
                        <Container maxWidth="xl">
                            <Typography variant="h4" sx={{mb: 5}}>
                                {sub}님의 항목별 체크카드 사용 결과
                            </Typography>
                            <Grid container spacing={3}>
                                <Grid item xs={12} sm={6} md={4} lg={3}>
                                    <AppWidgetSummary title="공항" total={columnInfo.airport}/>
                                </Grid>
                                <Grid item xs={12} sm={6} md={4} lg={3}>
                                    <AppWidgetSummary title="교육" total={columnInfo.edu} color="info"/>
                                </Grid>
                                <Grid item xs={12} sm={6} md={4} lg={3}>
                                    <AppWidgetSummary title="교통" total={columnInfo.tran} color="warning"/>
                                </Grid>
                                <Grid item xs={12} sm={6} md={4} lg={3}>
                                    <AppWidgetSummary title="문화생활" total={columnInfo.cultural} color="error"/>
                                </Grid>
                                <Grid item xs={12} sm={6} md={4} lg={3}>
                                    <AppWidgetSummary title="배달" total={columnInfo.delivery}/>
                                </Grid>
                                <Grid item xs={12} sm={6} md={4} lg={3}>
                                    <AppWidgetSummary title="백화점" total={columnInfo.stores} color="info"/>
                                </Grid>
                                <Grid item xs={12} sm={6} md={4} lg={3}>
                                    <AppWidgetSummary title="상품권" total={columnInfo.giftcard} color="warning"/>
                                </Grid>
                                <Grid item xs={12} sm={6} md={4} lg={3}>
                                    <AppWidgetSummary title="음식" total={columnInfo.food} color="error"/>
                                </Grid>
                                <Grid item xs={12} sm={6} md={4} lg={3}>
                                    <AppWidgetSummary title="쇼핑" total={columnInfo.shop}/>
                                </Grid>
                                <Grid item xs={12} sm={6} md={4} lg={3}>
                                    <AppWidgetSummary title="우체국" total={columnInfo.post} color="info"/>
                                </Grid>
                                <Grid item xs={12} sm={6} md={4} lg={3}>
                                    <AppWidgetSummary title="의료" total={columnInfo.medi} color="warning"/>
                                </Grid>
                                <Grid item xs={12} sm={6} md={4} lg={3}>
                                    <AppWidgetSummary title="의류" total={columnInfo.cloth} color="error"/>
                                </Grid>
                                <Grid item xs={12} sm={6} md={4} lg={3}>
                                    <AppWidgetSummary title="수수료" total={columnInfo.commi}/>
                                </Grid>
                                <Grid item xs={12} sm={6} md={4} lg={3}>
                                    <AppWidgetSummary title="여행" total={columnInfo.travel} color="info"/>
                                </Grid>
                                <Grid item xs={12} sm={6} md={4} lg={3}>
                                    <AppWidgetSummary title="주유" total={columnInfo.gas}/>
                                </Grid>
                                <Grid item xs={12} sm={6} md={4} lg={3}>
                                    <AppWidgetSummary title="통신" total={columnInfo.commu} color="info"/>
                                </Grid>
                                <Grid item xs={12} sm={6} md={4} lg={3}>
                                    <AppWidgetSummary title="커피" total={columnInfo.coffee} color="warning"/>
                                </Grid>
                                <Grid item xs={12} sm={6} md={4} lg={3}>
                                    <AppWidgetSummary title="편의점" total={columnInfo.conveni} color="error"/>
                                </Grid>
                                <Grid item xs={12} sm={6} md={4} lg={3}>
                                    <AppWidgetSummary title="해외" total={columnInfo.overseas}/>
                                </Grid>
                                <Grid item xs={12} sm={6} md={4} lg={3}>
                                    <AppWidgetSummary title="외화" total={columnInfo.foreign} color="info"/>
                                </Grid>
                                <Grid item xs={12} sm={6} md={4} lg={3}>
                                    <AppWidgetSummary title="호텔" total={columnInfo.hotel} color="warning"/>
                                </Grid>
                                <Grid item xs={12} sm={6} md={4} lg={3}>
                                    <AppWidgetSummary title="공과금" total={columnInfo.publicfee} color="error"/>
                                </Grid>
                            </Grid>
                            <div className={"center-container2"}>
                                <button onClick={handleGoToCardPage}>카드추천 보러가기</button>
                                <button onClick={handleGoBack}>메인페이지</button>
                            </div>
                        </Container>
                    </>
                )}
            </div>
        </div>
    );
}

export default DcardForm;
