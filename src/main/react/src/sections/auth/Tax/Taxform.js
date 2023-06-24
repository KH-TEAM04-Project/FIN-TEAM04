import { Grid, Container, Typography } from "@mui/material";
import React, { useEffect, useState } from 'react';
import './TaxForm.css';
import axios from "axios";
import { useNavigate } from 'react-router-dom';

import Nav from '../../../layouts/dashboard';

import { AppWidgetSummary } from "../../@dashboard/app";

function TaxForm() {
  const navigate = useNavigate();

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

  const token = localStorage.getItem('accessToken');
  const sub = token ? JSON.parse(atob(token.split('.')[1])).sub : '';

  useEffect(() => {
    if (token) {
      const decodedToken = JSON.parse(atob(token.split('.')[1]));

      axios.post("/tax/taxrefund", null, {
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

  return (
    <div>
      <Nav />
      <div className={"info-box"}>
        {(
          <>
            <Container maxWidth="xl">
              <Typography variant="h4" sx={{ mb: 5 }}>
                {columnInfo.year}년도 {sub}님의 연말정산 결과
              </Typography>
              <Grid container spacing={3}>
                <Grid item xs={12} sm={6} md={4} lg={3}>
                  <AppWidgetSummary title="국민연금" total={columnInfo.npension} />
                </Grid>
                <Grid item xs={12} sm={6} md={4} lg={3}>
                  <AppWidgetSummary title="건강보험" total={columnInfo.lifeinsurance} color="info" />
                </Grid>
                <Grid item xs={12} sm={6} md={4} lg={3}>
                  <AppWidgetSummary title="보험료" total={columnInfo.insurance} color="warning" />
                </Grid>
                <Grid item xs={12} sm={6} md={4} lg={3}>
                  <AppWidgetSummary title="의료비" total={columnInfo.medi} color="error" />
                </Grid>
                <Grid item xs={12} sm={6} md={4} lg={3}>
                  <AppWidgetSummary title="교육비" total={columnInfo.edu} />
                </Grid>
                <Grid item xs={12} sm={6} md={4} lg={3}>
                  <AppWidgetSummary title="신용카드" total={columnInfo.card} color="info" />
                </Grid>
                <Grid item xs={12} sm={6} md={4} lg={3}>
                  <AppWidgetSummary title="체크카드" total={columnInfo.dcard} color="warning" />
                </Grid>
                <Grid item xs={12} sm={6} md={4} lg={3}>
                  <AppWidgetSummary title="개인연금저축/연금계좌" total={columnInfo.pension} color="error" />
                </Grid>
                <Grid item xs={12} sm={6} md={4} lg={3}>
                  <AppWidgetSummary title="현금영수증" total={columnInfo.cash} />
                </Grid>
                <Grid item xs={12} sm={6} md={4} lg={3}>
                  <AppWidgetSummary title="주택마련저축" total={columnInfo.housesaving} color="info" />
                </Grid>
                <Grid item xs={12} sm={6} md={4} lg={3}>
                  <AppWidgetSummary title="주택자금/월세액" total={columnInfo.housefunds} color="warning" />
                </Grid>
                <Grid item xs={12} sm={6} md={4} lg={3}>
                  <AppWidgetSummary title="장기집합투자장권저축/벤처기업투자신탁" total={columnInfo.invest} color="error" />
                </Grid>
                <Grid item xs={12} sm={6} md={4} lg={3}>
                  <AppWidgetSummary title="소기업/소상고인 공제부금" total={columnInfo.sbo} />
                </Grid>
                <Grid item xs={12} sm={6} md={4} lg={3}>
                  <AppWidgetSummary title="기부금" total={columnInfo.donation} color="info" />
                </Grid>
              </Grid>
              <div className={"center-container2"}>
                <button onClick={handleGoToCardPage}>나의 솔루션 보러가기</button>
                <button onClick={handleGoBack}>메인페이지</button>
              </div>
            </Container>
          </>
        )}
      </div>
    </div>
  );
}

export default TaxForm;
