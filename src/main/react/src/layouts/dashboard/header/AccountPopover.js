import React, {useState, useEffect} from 'react';
import {useNavigate} from 'react-router-dom';
import axios from 'axios';
import jwtDecode from 'jwt-decode';
import {alpha} from '@mui/material/styles';
import {Box, Divider, Typography, Stack, MenuItem, Avatar, IconButton, Popover} from '@mui/material';
import { AccountCircle } from '@mui/icons-material';

const MENU_OPTIONS = [
    {
        label: 'Home',
        icon: 'eva:home-fill',
    },
    {
        label: 'Mypage',
        icon: 'eva:person-fill',
        onClick: '/mypage', // 마이페이지 경로를 추가합니다.
    },
];

function AccountPopover() {
    const [userData, setUserData] = useState({
        profilephoto: ""
    });

    // 로컬 스토리지에서 토큰 값을 가져옴
    const token = localStorage.getItem('accessToken');

    useEffect(() => {
        if (token) {
            // 토큰을 디코딩하여 payload 부분을 추출하고 JSON 파싱
            const decodedToken = JSON.parse(atob(token.split('.')[1]));

            axios.post("/member/MyPageCont", null, {
                headers: {
                    // http 헤더의 auth 부분에 accessToken 값 설정
                    'Authorization': `Bearer ${token}`
                }
            })
                .then(response => {
                    // 사용자 데이터를 성공적으로 가져온 경우
                    const userData = response.data;

                    // 사용자 정보를 상태에 설정
                    setUserData(userData);
                })
                .catch(error => {
                    // API 호출 중 에러 발생한 경우
                    console.error(error);
                });
        }
    }, [token]);


    const navigate = useNavigate();
    const [open, setOpen] = useState(null);
    const [account, setAccount] = useState({
        displayName: '로그인해주세요',
        onClick: '/login',
        photoURL: '',
    });

    const handleOpen = (event) => {
        setOpen(event.currentTarget);
    };

    const handleClose = () => {
        setOpen(null);
    };

    const handleOptionClick = (option) => {
        if (option.onClick) {
            if (option.onClick === '/login') {
                navigate(option.onClick);
            } else if (option.onClick === '/mypage') {
                // 마이페이지로 이동하도록 추가합니다.
                navigate(option.onClick);
            } else {
                // Handle other option clicks here
            }
        }
        handleClose();
    };

    const handleLogout = async () => {
        const accessToken = localStorage.getItem('accessToken');
        console.log(accessToken);

        try {
            await axios
                .post('/member/logout22', null, {
                    headers: {
                        // http 헤더의 auth 부분에 accessToken 값 설정
                        Authorization: `Bearer ${accessToken}`,
                    },
                })
                .then((response) => console.log(response.data));
            localStorage.removeItem('accessToken');
            localStorage.removeItem('refreshToken');
            navigate('/slogin'); // Adjust the path according to your routing configuration
            window.location.reload(); // 페이지를 새로고침
        } catch (error) {
            console.error('Logout failed:', error);
        }
    };

    useEffect(() => {
        const accessToken = localStorage.getItem('accessToken');
        if (accessToken) {
            const decodedToken = jwtDecode(accessToken);
            setAccount((prevAccount) => ({
                ...prevAccount,
                displayName: decodedToken.sub,
            }));
        }
    }, []);

    return (
        <>
            <IconButton
                onClick={handleOpen}
                sx={{
                    p: 0,
                    ...(open && {
                        '&:before': {
                            zIndex: 1,
                            content: "''",
                            width: '100%',
                            height: '100%',
                            borderRadius: '50%',
                            position: 'absolute',
                            bgcolor: (theme) => alpha(theme.palette.grey[900], 0.8),
                        },
                    }),
                }}
            >
                 {userData.profilephoto ? (
          <img
            src={userData.profilephoto}
            alt="프로필사진"
            style={{
              width: '60px',
              height: '60px',
              borderRadius: '50%',
            }}
          />
        ) : (
          <AccountCircle
            sx={{
              width: '60px',
              height: '60px',
            }}
          />
        )}
      </IconButton>

            <Popover
                open={Boolean(open)}
                anchorEl={open}
                onClose={handleClose}
                anchorOrigin={{vertical: 'bottom', horizontal: 'right'}}
                transformOrigin={{vertical: 'top', horizontal: 'right'}}
                PaperProps={{
                    sx: {
                        p: 0,
                        mt: 1.5,
                        ml: 0.75,
                        width: 180,
                        '& .MuiMenuItem-root': {
                            typography: 'body2',
                            borderRadius: 0.75,
                        },
                    },
                }}
            >
                <Box sx={{my: 1.5, px: 2.5}}>
                    <Typography variant="subtitle2" noWrap>
                        {account.displayName}
                    </Typography>
                    <Typography variant="body2" sx={{color: 'text.secondary'}} noWrap>
                        {account.email}
                    </Typography>
                </Box>

                <Divider sx={{borderStyle: 'dashed'}}/>

                <Stack sx={{p: 1}}>
                    {MENU_OPTIONS.map((option) => (
                        <MenuItem key={option.label} onClick={() => handleOptionClick(option)}>
                            {option.label}
                        </MenuItem>
                    ))}
                </Stack>

                <Divider sx={{borderStyle: 'dashed'}}/>

                <MenuItem onClick={handleLogout} sx={{m: 1}}>
                    Logout
                </MenuItem>
            </Popover>
        </>
    );
}

export default AccountPopover;
