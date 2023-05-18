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
import AddAPhotoIcon from '@mui/icons-material/AddAPhoto';
import CollectionsIcon from '@mui/icons-material/Collections';
import axios from "axios";
// ----------------------------------------------------------------------
const style13 = {
  position: 'absolute' ,
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 600,
  bgcolor: 'background.paper',
  border: '2px solid #000',
  boxShadow: 24,
   pt: 10,
   px: 10,
   pb: 15
};


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
export default function Yaya() {
  const [data, setData] = useState({
    title: "",
    RegDate: "",
    writer: "",
    content: ""
  });

  const handleChange = ({ target }) => {
    const { value, name } = target;
    setData((prevData) => ({
        ...prevData,
        [name]: value
    }));
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
      .post("/qna/regist", userData)
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

  // const [value, setValue] = React.useState<number ||  null>(2);
  const navigate = useNavigate();

    const handleClick = () => {
      navigate('/qna/list', { replace: true });
    };

    const [open, setOpen] = useState(false);
    const [open1, setOpen1] = React.useState(false);

    const handleOpen = () => {
      setOpen(true);
    };

   const handleOpen1 = () => {
         setOpen1(true);
         };

    const handleClose = () => {
      setOpen(false);
    };

     const handleClose1 = () => {
          setOpen1(false);
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

 const [imageSrc, setImageSrc] = useState('');
 const encodeFileToBase64 = (fileBlob) => {
     const reader = new FileReader();
     reader.readAsDataURL(fileBlob);
     return new Promise((resolve) => {
       reader.onload = () => {
         setImageSrc(reader.result);
         resolve();
       };
     });
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
<div>
            <Stack direction="row" alignItems="center" spacing={4} sx={{my: { xs: 1, mr: 12 } }}>
          <Button variant="contained" component="label">
              Upload File &nbsp;<AddAPhotoIcon  sx={{ display: { xs:2, md: '1' , mr: 6 }}} />
            <input hidden accept="image/*" multiple type="file"
            onChange={(e) => {encodeFileToBase64(e.target.files[0]); }}
            />
          </Button>


            <Button variant="contained"  component="label" onClick={handleOpen1}
            type="file" >
            이미지 미리보기 &nbsp; <CollectionsIcon  sx={{ display: { xs:2, md: '1' , mr: 6 }}}/></Button>
                <Modal
                  open={open1}
                  onClose={handleClose1}
                  aria-labelledby="modal-modal-title"
                  aria-describedby="modal-modal-description"
                >
                  <Box sx={style13}>
                    <Typography id="modal-modal-title" variant="h6" component="h2">
                  &nbsp; &nbsp; &nbsp; 미리 보기 에유
                    </Typography>
                   <Container
                   style={{ width: '200%', height: '150px' }}
                   className="preview"> {imageSrc && <img src={imageSrc} alt="preview-img"
                   style={{ width: '400px', height: '150%' }}/>}
                   </Container>
                  </Box>
                </Modal>
                   </Stack>
          </div>
         
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
