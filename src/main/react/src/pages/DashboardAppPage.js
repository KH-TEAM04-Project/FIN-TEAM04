import { Helmet } from 'react-helmet-async';
import { faker } from '@faker-js/faker';
// @mui
import { useTheme } from '@mui/material/styles';
import { Grid, Container, Typography } from '@mui/material';
// components
import Iconify from '../components/iconify';
// sections
import {
  AppTasks,
  AppNewsUpdate,
  AppOrderTimeline,
  AppCurrentVisits,
  AppWebsiteVisits,
  AppTrafficBySite,
  AppWidgetSummary,
  AppCurrentSubject,
  AppConversionRates,
} from '../sections/@dashboard/app';

import ggongImage from '../img/ggong.png'; // Import the image

export default function DashboardAppPage() {
  const theme = useTheme();

  return (
    <>
      <Helmet>
        <title> 13월의 월급 | 꽁머니 </title>
      </Helmet>

      <Container maxWidth="xl" style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
        <img
          src={ggongImage}
          alt="Ggong"
          style={{
            width: '100%',
            height: 'auto',
            maxWidth: '100%',
          }}
        />
      </Container>
    </>
  );
}
