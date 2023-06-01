import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import jwtDecode from 'jwt-decode';
import { alpha } from '@mui/material/styles';
import {
  Box,
  Divider,
  Typography,
  Stack,
  MenuItem,
  Avatar,
  IconButton,
  Popover,
} from '@mui/material';

const MENU_OPTIONS = [
  {
    label: 'Home',
    icon: 'eva:home-fill',
  },
  {
    label: 'Mypage',
    icon: 'eva:person-fill',
   
  },
  {
    label: 'icon',
    icon: 'eva:settings-2-fill',
    onClick: <IconButton />,
  },
];

function AccountPopover() {
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
      } else {
        // Handle other option clicks here
      }
    }
    handleClose();
  };

  const handleLogout = async () => {
    const accessToken = localStorage.getItem('accessToken');
    console.log(accessToken)

    try {
      await axios.post("/logout22", { accessToken }, { withCredentials: true })
      .then(response => console.log(response.data));
      localStorage.removeItem('accessToken');
      localStorage.removeItem('refreshToken');
      navigate("/slogin"); // Adjust the path according to your routing configuration
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
        <Avatar src={account.photoURL} alt="photoURL" />
      </IconButton>

      <Popover
        open={Boolean(open)}
        anchorEl={open}
        onClose={handleClose}
        anchorOrigin={{ vertical: 'bottom', horizontal: 'right' }}
        transformOrigin={{ vertical: 'top', horizontal: 'right' }}
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
        <Box sx={{ my: 1.5, px: 2.5 }}>
          <Typography variant="subtitle2" noWrap>
            {account.displayName}
          </Typography>
          <Typography variant="body2" sx={{ color: 'text.secondary' }} noWrap>
            {account.email}
          </Typography>
        </Box>

        <Divider sx={{ borderStyle: 'dashed' }} />

        <Stack sx={{ p: 1 }}>
          {MENU_OPTIONS.map((option) => (
            <MenuItem key={option.label} onClick={() => handleOptionClick(option)}>
              {option.label}
            </MenuItem>
          ))}
        </Stack>

        <Divider sx={{ borderStyle: 'dashed' }} />

        <MenuItem onClick={handleLogout} sx={{ m: 1 }}>
          Logout
        </MenuItem>
      </Popover>
    </>
  );
}

export default AccountPopover;
