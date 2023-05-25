import { Helmet } from 'react-helmet-async';
import React, { useEffect, useState } from 'react';
import { Link, useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import TableCell from '@mui/material/TableCell';
// @mui
import { styled } from '@mui/material/styles';
import {
  TextField,
  Typography,
  Container,
  Stack,
  Button,
  Box,
  Modal,
  AppBar,
  Toolbar,
  IconButton,
  Menu,
  Avatar,
  Tooltip,
  MenuItem,
  Table,
  TableHead,
  TableBody,
  TableRow,
} from '@mui/material';
import { LoadingButton } from '@mui/lab';
import WbSunnyIcon from '@mui/icons-material/WbSunny';
import MenuIcon from '@mui/icons-material/Menu';
import ThumbUpOffAltRoundedIcon from '@mui/icons-material/ThumbUpOffAltRounded';

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
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 400,
  bgcolor: 'background.paper',
  border: '2px solid #000',
  boxShadow: 24,
  pt: 2,
  px: 4,
  pb: 3,
};

const pages = ['Products', 'Pricing', 'Blog'];
const settings = ['Profile', 'Account', 'Dashboard', 'Logout'];

export default function Yaya() {
  const token = localStorage.getItem('accessToken');
  const sub = token ? JSON.parse(atob(token.split('.')[1])).sub : '';
  const [mno, setMno] = useState(token ? JSON.parse(atob(token.split('.')[1])).mno : '');

  console.log(mno);
  useEffect(() => {
    if (token) {
      const decodedToken = JSON.parse(atob(token.split('.')[1]));
      setMno(decodedToken.mno);
      console.log(decodedToken.mno);

      const mno = decodedToken.mno;
      axios
        .post("/MyPageCont", { mno })
        .then((response) => {
          const userData = response.data;
        })
        .catch((error) => {
          console.error(error);
        });
    }
  }, [token]);

  const [data, setData] = useState({ mno });

  const { qno } = useParams();
  const [posts, setPosts] = useState([]);

  const getPosts = () => {
    axios
      .get(`/qna/detail/${qno}`)
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
  };

  useEffect(() => {
    getPosts();
  }, []);

  const navigate = useNavigate();

  const handleClick = () => {
    navigate('/qna/list', { replace: true });
  };

  const [open, setOpen] = React.useState(false);

  const handleOpen = () => {
    setOpen(true);
  };
  const handleClose = () => {
    setOpen(false);
  };

  const [anchorEl, setAnchorEl] = React.useState(null);
  const [anchorElNav, setAnchorElNav] = React.useState(null);
  const [anchorElUser, setAnchorElUser] = React.useState(null);

  const handleOpenMenu = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleCloseNavMenu = () => {
    setAnchorElNav(null);
  };

  const handleCloseMenu = () => {
    setAnchorEl(null);
  };

  const handleThemeToggle = () => {
    // TODO: Toggle theme logic
  };

  const handleMenuClose = () => {
    // TODO: Menu close logic
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

<Box
  key={reply.rno}
  sx={{
    border: '1px solid #ccc',
    borderRadius: '5px',
    padding: '10px',
    marginTop: '10px',
  }}
>
  <Typography variant="subtitle1" gutterBottom>
    {reply.writer}
  </Typography>
  <Typography variant="body2" gutterBottom>
    {reply.content}
  </Typography>
</Box>

    const userReply = {
      rno: reply.rno,
      content: reply.content,
      qno: reply.qno
    };
    axios
      .post(`/qna/detail/${qno}`, userReply)
      .then((response) => {
        console.log(response.status, response.replys);
        addReply(response.data.replys.map((reply) => ({
          rno: response.data.reply.rno || '',
          content: response.data.reply.content,
          qno: response.data.reply.qno || ''
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
        <title>Q&A Detail</title>
      </Helmet>

      <AppBar position="fixed">
        <Toolbar>
          <IconButton
            size="large"
            edge="start"
            color="inherit"
            aria-label="menu"
            sx={{ mr: 2 }}
          >
            <MenuIcon />
          </IconButton>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            Yaya
          </Typography>
          <Box>
            <Tooltip title="Toggle light/dark theme">
              <IconButton
                size="large"
                aria-label="toggle theme"
                color="inherit"
                onClick={handleThemeToggle}
              >
                <WbSunnyIcon />
              </IconButton>
            </Tooltip>
            <IconButton
              size="large"
              edge="end"
              aria-label="account of current user"
              aria-haspopup="true"
              onClick={handleOpenMenu}
              color="inherit"
            >
              <Avatar
                src="/static/images/avatar/1.jpg"
                alt="profile"
                sx={{ width: 32, height: 32 }}
              />
            </IconButton>
            <Menu
              anchorEl={anchorEl}
              open={Boolean(anchorEl)}
              onClose={handleCloseMenu}
              onClick={handleCloseMenu}
              PaperProps={{
                elevation: 0,
                sx: {
                  overflow: 'visible',
                  filter: 'drop-shadow(0px 2px 8px rgba(0,0,0,0.32))',
                  mt: 1.5,
                  '& .MuiAvatar-root': {
                    width: 32,
                    height: 32,
                    ml: -0.5,
                    mr: 1,
                  },
                  '&:before': {
                    content: '""',
                    display: 'block',
                    position: 'absolute',
                    top: 0,
                    right: 14,
                    width: 10,
                    height: 10,
                    bgcolor: 'background.paper',
                    transform: 'translateY(-50%) rotate(45deg)',
                    zIndex: 0,
                  },
                },
              }}
              transformOrigin={{ horizontal: 'right', vertical: 'top' }}
              anchorOrigin={{ horizontal: 'right', vertical: 'bottom' }}
            >
              {settings.map((setting) => (
                <MenuItem key={setting}>{setting}</MenuItem>
              ))}
            </Menu>
          </Box>
        </Toolbar>
      </AppBar>

      <StyledContent2>
        <Box sx={{ flexGrow: 1 }}>
          <Container maxWidth="lg">
            <Stack direction="row" alignItems="center" spacing={2}>
              <Typography variant="h4" component="h1" sx={{ flexGrow: 1 }}>
                Q&A Detail
              </Typography>
              <Button variant="outlined" onClick={handleClick}>
                Back
              </Button>
            </Stack>

            <Table>
              <TableHead>
                <TableRow>
                  <TableCell align="center" sx={{ fontWeight: 'bold' }}>
                    Title
                  </TableCell>
                  <TableCell align="center" sx={{ fontWeight: 'bold' }}>
                    Content
                  </TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {posts.map((post) => (
                  <TableRow key={post.qno}>
                    <TableCell align="center">{post.title}</TableCell>
                    <TableCell align="center">{post.content}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>

            <Box>
              <Typography variant="h6" component="h2" sx={{ marginTop: 4 }}>
                Reply
              </Typography>
              {replys.map((reply) => (
                <Box
                  key={reply.rno}
                  sx={{
                    border: '1px solid #ccc',
                    borderRadius: '5px',
                    padding: '10px',
                    marginTop: '10px',
                  }}
                >
                  <Typography variant="subtitle1" gutterBottom>
                    {reply.writer}
                  </Typography>
                  <Typography variant="body2" gutterBottom>
                    {reply.content}
                  </Typography>
                </Box>
              ))}
              <Box component="form" onSubmit={handleSubmit} sx={{ marginTop: 2 }}>
                <TextField
                  required
                  id="content"
                  name="content"
                  label="Reply"
                  fullWidth
                  multiline
                  rows={4}
                  value={reply.content}
                  onChange={handleChange}
                />
                <LoadingButton
                  type="submit"
                  variant="contained"
                  loading={false}
                  loadingPosition="end"
                  sx={{ mt: 2 }}
                >
                  Submit
                </LoadingButton>
              </Box>
            </Box>
          </Container>
        </Box>
      </StyledContent2>

      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          <Typography id="modal-modal-title" variant="h6" component="h2">
            추가적인 문의
          </Typography>
          <Typography id="modal-modal-description" sx={{ mt: 2 }}>
            문의하실 내용을 작성해주세요.
          </Typography>
          <TextField
            required
            fullWidth
            multiline
            rows={4}
            label="Content"
            variant="outlined"
            sx={{ mt: 2 }}
          />
          <Button variant="outlined" sx={{ mt: 2 }}>
            Submit
          </Button>
        </Box>
      </Modal>
    </>
  );
}
