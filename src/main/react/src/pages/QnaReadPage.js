import { Helmet } from 'react-helmet-async';
import React, { useState, useEffect } from 'react';
import { useParams ,useNavigate} from 'react-router-dom';
import axios from 'axios';

// @mui
import { styled } from '@mui/material/styles';
import { TextField, Typography, Container,Stack,Button,Box,Modal,
AppBar,Toolbar,IconButton,Menu,Avatar,Tooltip,MenuItem} from '@mui/material';
import { LoadingButton } from '@mui/lab';
import WbSunnyIcon  from '@mui/icons-material/WbSunny';
import MenuIcon from '@mui/icons-material/Menu';
import ThumbUpOffAltRoundedIcon from '@mui/icons-material/ThumbUpOffAltRounded';
// import { number } from 'prop-types';
// import Clock from 'react-live-clock'
// ----------------------------------------------------------------------

const StyledContent2 = styled('div')(({ theme }) => ({
  maxWidth: 1000,
  margin: 'auto',
  minHeight: '100vh',
  display: 'flex',
  justifyContent: 'left',
  flexDirection: 'column',
  padding: theme.spacing(12, 0),
}));

const style = {
  position: "absolute" ,
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 400,
  bgcolor: "background.paper",
  border: "2px solid #000",
  boxShadow: 24,
  pt: 2,
  px: 4,
  pb: 3
};
// ----------------------------------------------------------------------
const pages = ['Products', 'Pricing', 'Blog'];
const settings = ['Profile', 'Account', 'Dashboard', 'Logout'];



export default function QnaReadPage() {
    const { qno } = useParams();
     const [posts, setPosts] = useState([]);

        const getPost = (qno) => {
            axios.get(`/QnaReadPage/${qno}`).then((response) => {
                setPosts(response.data);
                 console.log(response.data);
                 console.log("yaya");
            });
        };

       useEffect(() => {
           getPost(qno);
         }, [qno]);

  const navigate = useNavigate();

  const handleClick = () => {
    navigate('/re', { replace: true });
  };
  const [open, setOpen] = React.useState(false);
  const handleOpen = () => {
    setOpen(true);
  };
  const handleClose = () => {
    setOpen(false);
  };

  const [anchorElNav, setAnchorElNav] = React.useState(null);
  const [anchorElUser, setAnchorElUser] = React.useState(null);

  const handleOpenNavMenu = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorElNav(event.currentTarget);
  };
  const handleOpenUserMenu = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorElUser(event.currentTarget);
  };

  const handleCloseNavMenu = () => {
    setAnchorElNav(null);
  };

  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
  };

  return (
    <>
      <Helmet>
        <title> QnA보기| 꽁머니 </title>
      </Helmet>

      <AppBar position="static">
      <Container maxWidth="xl">
        <Toolbar disableGutters>
          <WbSunnyIcon  sx={{ display: { xs: 'none', md: 'flex' }, mr: 1 }} />
          <Typography
            variant="h6"
            noWrap
            component="a"
            href="/Main"
            sx={{
              mr: 2,
              display: { xs: 'none', md: 'flex' },
              fontFamily: 'monospace',
              fontWeight: 700,
              letterSpacing: '.3rem',
              color: 'inherit',
              textDecoration: 'none',
            }}
          >
            꽁 머 니
          </Typography>

          <Box sx={{ flexGrow: 1, display: { xs: 'flex', md: 'none' } }}>
            <IconButton
              size="large"
              aria-label="account of current user"
              aria-controls="menu-appbar"
              aria-haspopup="true"
              onClick={handleOpenNavMenu}
              color="inherit"
            >
              <MenuIcon />
            </IconButton>
            <Menu
              id="menu-appbar"
              anchorEl={anchorElNav}
              anchorOrigin={{
                vertical: 'bottom',
                horizontal: 'left',
              }}
              keepMounted
              transformOrigin={{
                vertical: 'top',
                horizontal: 'left',
              }}
              open={Boolean(anchorElNav)}
              onClose={handleCloseNavMenu}
              sx={{
                display: { xs: 'block', md: 'none' },
              }}
            >
              {pages.map((page) => (
                <MenuItem key={page} onClick={handleCloseNavMenu}>
                  <Typography textAlign="center">{page}</Typography>
                </MenuItem>
              ))}
            </Menu>
          </Box>
          <WbSunnyIcon  sx={{ display: { xs: 'flex', md: 'none' }, mr: 1 }} />
          <Typography
            variant="h5"
            noWrap
            component="a"
            href=""
            sx={{
              mr: 2,
              display: { xs: 'flex', md: 'none' },
              flexGrow: 1,
              fontFamily: 'monospace',
              fontWeight: 700,
              letterSpacing: '.3rem',
              color: 'inherit',
              textDecoration: 'none',
            }}
          >
           꽁 머 니
          </Typography>
          <Box sx={{ flexGrow: 1, display: { xs: 'none', md: 'flex' } }}>
            {pages.map((page) => (
              <Button
                key={page}
                onClick={handleCloseNavMenu}
                sx={{ my: 2, color: 'white', display: 'block' }}
              >
                {page}
              </Button>
            ))}
          </Box>

          <Box sx={{ flexGrow: 0 }}>
            <Tooltip title="Open settings">
              <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                <Avatar alt="Remy Sharp" src="/static/images/avatar/2.jpg" />
              </IconButton>
            </Tooltip>
            <Menu
              sx={{ mt: '45px' }}
              id="menu-appbar"
              anchorEl={anchorElUser}
              anchorOrigin={{
                vertical: 'top',
                horizontal: 'right',
              }}
              keepMounted
              transformOrigin={{
                vertical: 'top',
                horizontal: 'right',
              }}
              open={Boolean(anchorElUser)}
              onClose={handleCloseUserMenu}
            >
              {settings.map((setting) => (
                <MenuItem key={setting} onClick={handleCloseUserMenu}>
                  <Typography textAlign="center">{setting}</Typography>
                </MenuItem>
              ))}
            </Menu>
          </Box>
        </Toolbar>
      </Container>
    </AppBar>
     {posts.map((data) => (
      <Container key={data.qno} Width="10000">
        <StyledContent2 sx={{ textAlign: 'center', alignItems: 'right' }}>
          <Typography variant="h5" paragraph  defaultValue="Normal">
            게시글 보세유
          </Typography>

          <Typography sx={{ color: 'text.secondary' }}>
        무엇이든 보세유
          </Typography>
          <div>---------------------------------------------------------------------------------------------------------------------------------------------------------------------</div>

          <TextField name="text" label="제목" readOnly disabled
           sx={{ my: { xs: 3, sm: 5, mr: 5 } }}>{data.title}</TextField>

          <TextField color="secondary"   name="text" label="작성자" disabled
          sx={{my: {  xs: 3, sm: 5 ,mr: 1
          } }}> {data.writer} </TextField>






          <TextField readOnly disabled
          id="outlined-multiline-static"
          label="내용"
          multiline
          rows={10}

          defaultValue=" 글 작성"
        >{data.content}</TextField>

         <Stack direction="row" alignItems="center" spacing={4} sx={{my: { xs: 1, mr: 12 } }}>
      <Button variant="contained" component="label">
        재업로드  <ThumbUpOffAltRoundedIcon  sx={{ display: { xs:2, md: '1' , mr: 6 }}} />
        <input hidden accept="image/*" multiple type="file" />

      </Button>
      </Stack>


      <Button fullWidth size="large" type="submit" variant="contained" onClick={handleOpen}>작성하기</Button>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="parent-modal-title"
        aria-describedby="parent-modal-description"
      >
        <Box sx={{ ...style, width: 500 }}>
          <h2 id="parent-modal-title">꽁 머 니</h2>
          <p id="parent-modal-description">
            수정이 완료됐습니다람쥐.
          </p>
          <LoadingButton fullWidth size="large" type="submit" variant="contained" onClick={handleClick}>
       등록
      </LoadingButton>
        </Box>
      </Modal>

      </StyledContent2>
      </Container>
 ))}
    </>
  );
}
