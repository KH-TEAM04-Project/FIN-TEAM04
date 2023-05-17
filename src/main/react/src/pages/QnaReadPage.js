import { Helmet } from 'react-helmet-async';
import React, { useEffect, useState } from 'react';
import { Link,useParams ,useNavigate} from 'react-router-dom';
import axios from 'axios';
import TableCell from '@mui/material/TableCell';
// @mui
import { styled } from '@mui/material/styles';
import { TextField, Typography, Container,Stack,Button,Box,Modal,
AppBar,Toolbar,IconButton,Menu,Avatar,Tooltip,MenuItem,Table,TableHead,TableBody,TableRow} from '@mui/material';
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
   const { qno } = useParams();
   const [posts, setPosts] = useState([]);

   const getPosts = () => {
     axios.get(`/QnaReadPage/${qno}`).then((response) => {
       setPosts([response.data]); // 배열 형태로 설정
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
   };

   useEffect(() => {
     getPosts();
   }, []);
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

  const [replys, setReplys] = useState([]);
  const [reply, setReply] = useState({
    title: "",
    RegDate: "",
    writer: "",
    content: ""
  });

  const handleChange = ({ target }) => {
    const { value, name } = target;
    setReply((prevReply) => ({
      ...prevReply,
      [name]: value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();

  // reply 객체 확인 및 초기화
    const reply = {
        rno: null,
        content: '',
        qnaQno: qno
    };

    const userReply = {
      rno: reply.rno,
      content: reply.content,
      qnaQno: reply.qnaQno
    };
    axios
      .post(`/QnaReadPage/${qno}`, userReply)
      .then((response) => {
        console.log(response.status, response.replys);
        addReply(response.replys.map(reply => ({
          rno: reply.rno || '', // 'rno'가 존재하지 않을 경우 빈 문자열로 설정
          content: reply.content,
          qnaQno: reply.qnaQno || '' // 'qnaQno'가 존재하지 않을 경우 빈 문자열로 설정
        })));
      })
      .catch((error) => {
        if (error.response) {
          console.log("댓글 에러 발생");
          console.log(userReply);
        } else if (error.request) {
          console.log("네트워크 오류");
        } else {
          console.log(error);
        }
        });
     };

  const addReply = (newReply) => {
    setReplys((prevReplys) => [...prevReplys, newReply]);
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
      <Container key={data.qno} Width="10000">
        <StyledContent2 sx={{ textAlign: 'center', alignItems: 'right' }}>
          <Typography variant="h5" paragraph  defaultValue="Normal">
            QNA 보세유
          </Typography>

          <Typography sx={{ color: 'text.secondary' }}>
        무엇이든 보세유
          </Typography>
          <div>---------------------------------------------------------------------------------------------------------------------------------------------------------------------</div>

          <TextField defaultValue={data.title} name="text" label="제목" readOnly disabled
           sx={{ my: { xs: 3, sm: 5, mr: 5 } }}>{data.title}</TextField>

          <TextField defaultValue={data.writer} color="secondary"   name="text" label="작성자" disabled
          sx={{my: {  xs: 3, sm: 5 ,mr: 1
          } }}> {data.writer} </TextField>

          <TextField defaultValue={data.regDate} color="secondary"   name="text" label="작성일" disabled
                    sx={{my: {  xs: 3, sm: 5 ,mr: 1
                    } }}> {data.regDate} </TextField>




          <TextField
          id="outlined-multiline-static"
            disabled
          multiline
          rows={10}
          value={data.content}

        ><TableCell >{data.content}</TableCell>}</TextField>

         <Stack direction="row" alignItems="center" spacing={4} sx={{my: { xs: 1, mr: 12 } }}>
      <Button variant="contained" component="label">
        재업로드  <ThumbUpOffAltRoundedIcon  sx={{ display: { xs:2, md: '1' , mr: 6 }}} />
        <input hidden accept="image/*" multiple type="file" />

      </Button>
      </Stack>

        <form onSubmit={handleSubmit}>
              <li className='comment-from'>
                <span className='ps_box'>
                  <TextField
                    fullWidth
                    type='text'
                    name='content'
                    className='int'
                    value={replys.content}
                    onChange={handleChange}
                    placeholder='댓글을 입력해주세요~'
                  />
                </span>
                <Stack direction='row-reverse' sx={{ my: { xs: 1, mr: 12 } }}>
                  <Button variant='contained' type='submit' className='btn'>
                    등록
                  </Button>
                </Stack>
              </li>
            </form>

         <Table sx={{ maxWidth: 2000, overflow: 'hidden' }} aria-label='simple table'>
              <TableHead>
                <TableCell>번호</TableCell>
                <TableCell>내용</TableCell>
                <TableCell>작성자</TableCell>
                <TableCell>수정/삭제</TableCell>
              </TableHead>

              <TableBody>
                {replys.map((reply, index) => (
                  <TableRow key={index}>
                    <TableCell>{reply.rno}</TableCell>
                    <TableCell>{reply.content}</TableCell>
                    <TableCell>작성자</TableCell>
                    <TableCell>수정/삭제</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>

      <Button fullWidth size="large" type="submit" variant="contained" onClick={handleOpen}>목록으로 돌아가기</Button>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="parent-modal-title"
        aria-describedby="parent-modal-description"
      >
        <Box sx={{ ...style, width: 1500 }}>
          <h2 id="parent-modal-title">꽁 머 니</h2>
          <p id="parent-modal-description">
            목록가즈아ㅏㅏㅏ
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
