import { Helmet } from 'react-helmet-async';
import React, {useCallback, useEffect, useState } from 'react';
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



export default function Page404() {
   const { bno } = useParams();

   const [data, setData] = useState({
   bno : "",
     title: "",
     regDate: "",
     writer: "",
     content: "",
   });

   const [posts, setPosts] = useState([]);

   const getPosts = useCallback(() => {
     axios
       .get(`/EditPage/${bno}`)
       .then((response) => {
         setPosts([response.data]);
         console.log(response.data);
         console.log("yaya");
       })
       .catch((error) => {
         if (error.response) {
           console.log("이거 에러인걸?");
         } else if (error.request) {
           console.log("network error");
         } else {
           console.log(error);
         }
       });
   }, [bno]);

   const handleChange = useCallback((e) => {
     const value = e.target.value;
     setData((prevData) => ({
       ...prevData,
       [e.target.name]: value,
     }));
   }, []);

   const handleSubmit = useCallback(
     (e) => {
       e.preventDefault();
       const userData = {
         bno: data.bno,
         title: data.title,
         regDate: data.regDate,
         writer: data.writer,
         content: data.content,
       };
       axios
         .post(`/EditPage/${bno}`, userData)
         .then((response) => {
           console.log(response.status, response.data);
           console.log(response.data);
         })
         .catch((error) => {
           if (error.response) {
             console.log("이거 포스트 에러인걸?");
             console.log(userData);
             console.log(error.response.data);
           } else if (error.request) {
             console.log("network error");
           } else {
             console.log(error);
           }
         });
     },
     [data, bno]
   );

   useEffect(() => {
     if (bno) {
       getPosts();
     }
   }, [bno, getPosts]);



  const navigate = useNavigate();

  const handleClick = () => {
    navigate('/EoardPage', { replace: true });
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
        <title> 게시글보기| 꽁머니 </title>
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
<form onSubmit={handleSubmit} key={data.bno}>
      <Container  width="10000">
        <StyledContent2 sx={{ textAlign: 'center', alignItems: 'right' }}>
          <Typography variant="h5" paragraph  defaultValue="Normal">
            게시글 수정 해보세유
          </Typography>

          <Typography sx={{ color: 'text.secondary' }}>
        무엇이든 보세유
          </Typography>
          <div>---------------------------------------------------------------------------------------------------------------------------------------------------------------------</div>
           <TextField name="bno" label="게시글 번호"
                      defaultValue={data.bno} onChange={handleChange}
                      sx={{ my: { xs: 3, sm: 5, mr: 5 } }}/>

          <TextField name="title" label="제목"
            defaultValue={data.title} onChange={handleChange}
            sx={{ my: { xs: 3, sm: 5, mr: 5 } }}/>

          <TextField name="regDate" label="작성일"
            defaultValue={data.regDate} onChange={handleChange}
            sx={{my: {  xs: 3, sm: 5 ,mr: 1 } }}>
            {data.regDate}
          </TextField>

          <TextField name="writer" label="작성자"
            defaultValue={data.writer} onChange={handleChange}
            sx={{my: {  xs: 3, sm: 5 ,mr: 1 } }}>
            {data.writer}
          </TextField>

          <TextField
            id="outlined-multiline-static"
            name="content"
            defaultValue={data.content} onChange={handleChange}
            multiline
            rows={10}
            >{data.content}
          </TextField>

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
          <LoadingButton fullWidth size="large"  variant="contained" onClick={handleClick}>
       등록
      </LoadingButton>
        </Box>
      </Modal>

      </StyledContent2>
      </Container>
</form>
 ))}

    </>
  );
}
