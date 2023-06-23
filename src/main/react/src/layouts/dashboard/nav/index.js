import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import { useLocation, useNavigate } from 'react-router-dom';
import { styled, alpha } from '@mui/material/styles';
import { Box, Link, Drawer, Typography, Avatar } from '@mui/material';
import SvgColor from '../../../components/svg-color';
import Logo from '../../../components/logo';
import Scrollbar from '../../../components/scrollbar';
import NavSection from '../../../components/nav-section';
import account from '../../../_mock/account';
import useResponsive from '../../../hooks/useResponsive';

const NAV_WIDTH = 280;

const StyledAccount = styled('div')(({ theme }) => ({
  display: 'flex',
  alignItems: 'center',
  padding: theme.spacing(2, 2.5),
  borderRadius: Number(theme.shape.borderRadius) * 1.5,
  backgroundColor: alpha(theme.palette.grey[500], 0.12),
}));

Nav.propTypes = {
  openNav: PropTypes.bool,
  onCloseNav: PropTypes.func,
};

export default function Nav({ openNav, onCloseNav }) {
  const { pathname } = useLocation();
  const navigate = useNavigate();
  const isDesktop = useResponsive('up', 'lg');

  const [navConfig, setNavConfig] = useState([]);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [userId, setUserId] = useState(''); // 사용자 ID 추가

  useEffect(() => {
    const token = localStorage.getItem('accessToken');
    const sub = token ? JSON.parse(atob(token.split('.')[1])).sub : '';

    const icon = (sub) => (
      <SvgColor src={`/assets/icons/navbar/${sub}.svg`} sx={{ width: 1, height: 1 }} />
    );

    const initialNavConfig = [
      {
        title: '공 지 사 항',
        path: '/board/list',
        icon: icon('ic_cart'),
      },
      ...(token
        ? [
            {
              title: 'Q & A',
              path: '/qna/list',
              icon: icon('ic_cart'),
            },
          ]
        : []),
      {
        title: 'login',
        path: '/login',
        icon: icon('ic_lock'),
        isLogin: !token,
      },
      {
        title: '연 말 정 산',
        path: '/tax',
        icon: icon('ic_analytics'),
      },
    ].filter((item) => !(item.title === 'login' && !item.isLogin));

    setNavConfig(initialNavConfig);
    setIsLoggedIn(token && token.split('.').length === 3); // 토큰의 유효성에 따라 isLoggedIn 상태를 업데이트합니다.
    setUserId(sub);
  }, []);

  useEffect(() => {
    if (openNav) {
      onCloseNav();
    }
  }, [pathname, openNav, onCloseNav]);

  const renderContent = (
    <Scrollbar
      sx={{
        height: 1,
        '& .simplebar-content': { height: 1, display: 'flex', flexDirection: 'column' },
      }}
    >
      <Box sx={{ px: 2.5, py: 3, display: 'inline-flex' }}>
        <Logo />
      </Box>

      <Box sx={{ mb: 5, mx: 2.5 }}>
        <Link underline="none">
          {/* <StyledAccount>
            <Avatar src={account.photoURL} alt="photoURL" />

            <Box sx={{ ml: 2 }}>
              <Typography variant="subtitle2" sx={{ color: 'text.primary' }}>
<<<<<<< HEAD
                {isLoggedIn ? `${userId}님 환영합니다 ` : '로그인 해주세용><'}
=======
                스타일어카운트.
>>>>>>> feature/seokjun2
              </Typography>

              <Typography variant="body2" sx={{ color: 'text.secondary' }}>
                {account.role}
              </Typography>
            </Box>
          </StyledAccount> */}
        </Link>
      </Box>

      <NavSection data={navConfig} />

      <Box sx={{ flexGrow: 1 }} />
    </Scrollbar>
  );

  return (
    <Box
      component="nav"
      sx={{
        flexShrink: { lg: 0 },
        width: { lg: NAV_WIDTH },
      }}
    >
      {isDesktop ? (
        <Drawer
          open
          variant="permanent"
          PaperProps={{
            sx: {
              width: NAV_WIDTH,
              bgcolor: 'background.default',
              borderRightStyle: 'dashed',
            },
          }}
        >
          {renderContent}
        </Drawer>
      ) : (
        <Drawer
          open={openNav}
          onClose={onCloseNav}
          ModalProps={{
            keepMounted: true,
          }}
          PaperProps={{
            sx: { width: NAV_WIDTH },
          }}
        >
          {renderContent}
        </Drawer>
      )}
    </Box>
  );
}
