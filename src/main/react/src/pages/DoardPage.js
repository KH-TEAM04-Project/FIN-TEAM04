import { Helmet } from 'react-helmet-async';
import  React, {useState} from "react";
import { useNavigate } from 'react-router-dom';
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
import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import axios from "axios";
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
  const [data, setData] = useState({
    title: "",
    RegDate: "",
    writer: "",
    content: ""
  });

  const handleChange = (e) => { const value = e.target.value;
    setData({
      ...data,
      [e.target.name]: value
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const userData = {
      title: data.title,
      RegDate: data.RegDate,
      writer: data.writer,
      content: data.content
    };
    axios
      .post("http://localhost:3000/DoardPage", userData)
      .then((response) => {
        console.log(response.status, response.data);
      })
      .catch((error) => {
        if (error.response) {
          console.log("이거 에러인걸?");
          console.log(userData);
        } else if (error.request) {
          console.log("network error");
        } else {
          console.log(error);
        }
      });
  };


  const [value, setValue] = React.useState('today');

  // const [value, setValue] = React.useState<number ||  null>(2);
  const navigate = useNavigate();

  const handleClick = () => {
    navigate('/QnA', { replace: true });
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
        <title> QnA 작성| 꽁머니 </title>
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
    <form onSubmit={handleSubmit}>
      <Container width="10000">
        <StyledContent2 sx={{ textAlign: 'center', alignItems: 'right' }}>
          <Typography variant="h5" paragraph  defaultValue="Normal">
            QnA 작성하세유
          </Typography>
      
          <Typography sx={{ color: 'text.secondary' }}>
        무엇이든 물어보세유 
          </Typography>
          <div>---------------------------------------------------------------------------------------------------------------------------------------------------------------------</div>
         {/* 여기서 부터 내용 */}


         <TextField    name="title" label="제목" 
          value={data.title}
          onChange={handleChange}
          sx={{my: {  xs: 3, sm: 5 ,mr: 1} }}/>  

           
<LocalizationProvider dateAdapter={AdapterDayjs}>
            <DemoContainer components={['DatePicker', 'DatePicker']}>
            <DatePicker
                label="작성 날짜"
                value={data.date}
                defaultValue={value}
                onChange={(newValue) => setValue(newValue)}
                onChange={handleChange}
              />
            </DemoContainer>
          </LocalizationProvider>

          <TextField    name="writer" label="작성자" 
          value={data.writer}
          onChange={handleChange}
          sx={{my: {  xs: 3, sm: 5 ,mr: 1} }}/>  
        
                
            
           
        <TextField    name="content" label="내용" 
          value={data.content}
          multiline
          rows={10}
          onChange={handleChange}
          defaultValue=" 글 작성"
         />
       
         <Stack direction="row" alignItems="center" spacing={4} sx={{my: { xs: 1, mr: 12 } }}>
      <Button variant="contained" component="label">
        Upload  <ThumbUpOffAltRoundedIcon  sx={{ display: { xs:2, md: '1' , mr: 6 }}} />
        <input hidden accept="image/*" multiple type="file" />
        
      </Button>
      </Stack>
         
      <div>
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
           QnA이 작성됐습니다람쥐.
          </p>
          <LoadingButton fullWidth size="large" type="submit" variant="contained" onClick={handleClick}>
       등록
      </LoadingButton>
        </Box>
      </Modal>
    </div>
      </StyledContent2>
      </Container>
      </form>      
    </>
  );
}
