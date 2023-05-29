import React, { useEffect, useState } from 'react';
import { Helmet } from 'react-helmet-async';
import { Link, useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
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
  TableCell,
} from '@mui/material';
import { LoadingButton } from '@mui/lab';
import WbSunnyIcon from '@mui/icons-material/WbSunny';
import MenuIcon from '@mui/icons-material/Menu';
import ThumbUpOffAltRoundedIcon from '@mui/icons-material/ThumbUpOffAltRounded';

const QnaDetailPage = () => {
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
  const [post, setPost] = useState(null); // 게시글 상태
  const [reply, setReply] = useState([]);
  const [replys, setReplys] = useState([]); // 댓글 목록 상태
  const [replyContent, setReplyContent] = useState(''); // 댓글 내용 상태

  const getPost = () => {
    axios
      .get(`/qna/detail/${qno}`)
      .then((response) => {
        setPost(response.data); // 게시글 업데이트
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

  const getReplys = () => {
    axios
      .get(`/qna/replys/${qno}`)
      .then((response) => {
        setReplys(response.data); // 댓글 목록 업데이트
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
    getPost();
    getReplys();
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

  const handleClickMenu = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleCloseMenu = () => {
    setAnchorEl(null);
  };

  const handleEdit = () => {
    // 게시글 수정 기능 구현
      axios.put(`/qna/update/${qno}`, data)
        .then((response) => {
          console.log("게시글이 성공적으로 수정되었습니다.");
          // 수정된 게시글 정보로 상태 업데이트
          setPost(response.data.qnaDTO);
        })
        .catch((error) => {
          console.error(error);
        });
  };

  const handleDelete = () => {
    console.log("삭제하기");
  };

  const handleLike = () => {
    console.log("좋아요");
  };

  const handleUnlike = () => {
    console.log("좋아요 취소");
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    // replyContent 값을 서버로 전송하고, 성공적으로 작성된 댓글을 화면에 추가하는 로직을 구현해야 합니다.
    const replyData = {
      content: replyContent,
    };

    axios
      .post(`/qna/replys/${qno}`, replyData)
      .then((response) => {
        console.log('댓글 작성 성공:', response.data);
        // 댓글 목록을 다시 불러옵니다.
        getReplys();
      })
      .catch((error) => {
        console.error('댓글 작성 실패:', error);
      });
  };

  return (
    <div>
      <Helmet>
        <title>게시글 상세 | Q&A</title>
      </Helmet>
      <div className="qna-detail">
        <AppBar position="fixed" sx={{ zIndex: 10 }}>
          <Toolbar>
            <IconButton
              size="large"
              edge="start"
              color="inherit"
              aria-label="menu"
              sx={{ mr: 2 }}
              onClick={handleClickMenu}
            >
              <MenuIcon />
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
              <MenuItem component={Link} to="/qna/list">
                Q&A 목록
              </MenuItem>
              <MenuItem component={Link} to="/qna/write">
                새로 작성하기
              </MenuItem>
              <MenuItem onClick={handleEdit}>
                수정하기
              </MenuItem>
              <MenuItem component={Link} to={`/qna/delete/${qno}`}>
                삭제하기
              </MenuItem>
            </Menu>
            <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
              Q&A 상세
            </Typography>
            <Tooltip title="Light / Dark" edge="end">
              <IconButton color="inherit" onClick={handleOpen}>
                <WbSunnyIcon />
              </IconButton>
            </Tooltip>
            <Modal
              open={open}
              onClose={handleClose}
              aria-labelledby="modal-modal-title"
              aria-describedby="modal-modal-description"
            >
              <Box
                sx={{
                  position: 'absolute',
                  top: '50%',
                  left: '50%',
                  transform: 'translate(-50%, -50%)',
                  width: 400,
                  bgcolor: 'background.paper',
                  border: '2px solid #000',
                  boxShadow: 24,
                  p: 4,
                }}
              >
                <Typography id="modal-modal-title" variant="h6" component="h2">
                  기능 준비 중
                </Typography>
                <Typography id="modal-modal-description" sx={{ mt: 2 }}>
                  이 기능은 준비 중에 있습니다.
                </Typography>
              </Box>
            </Modal>
            <Avatar alt="User Avatar" src="/avatar.png" />
          </Toolbar>
        </AppBar>
        <Container>

          <Stack sx={{ marginTop: '80px' }}>
            <Typography variant="h4">{post && post.title}</Typography>
            <Typography variant="subtitle1" color="textSecondary">
              {post && post.writer} | {post && post.createdTime}
            </Typography>
            <Typography variant="body1">{post && post.content}</Typography>
          </Stack>
          <Stack direction="row" spacing={2} sx={{ marginTop: '32px' }}>
            <Button variant="outlined" onClick={handleLike}>
              <ThumbUpOffAltRoundedIcon sx={{ marginRight: '8px' }} /> 좋아요
            </Button>
            <Button variant="outlined" onClick={handleUnlike}>
              좋아요 취소
            </Button>
          </Stack>
          <Stack sx={{ marginTop: '32px' }}>
            <Typography variant="h5">댓글</Typography>
            <Table sx={{ marginTop: '16px' }}>
              <TableHead>
                <TableRow>
                  <TableCell>작성자</TableCell>
                  <TableCell>내용</TableCell>
                  <TableCell>작성일시</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {replys?.map((reply) => (
                  <TableRow key={reply.rno}>
                    <TableCell>{reply.writer}</TableCell>
                    <TableCell>{reply.content}</TableCell>
                    <TableCell>{reply.regDate}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </Stack>
          <Stack sx={{ marginTop: '32px' }}>
            <Typography variant="h5">댓글 작성</Typography>
            <form onSubmit={handleSubmit}>
              <Stack direction="row" spacing={2}>
                <TextField
                  label="댓글 내용"
                  variant="outlined"
                  multiline
                  rows={4}
                  fullWidth
                  required
                  value={replyContent} // 댓글 내용 표시
                  onChange={(event) => setReplyContent(event.target.value)} // 댓글 내용 업데이트
                />
              </Stack>
              <LoadingButton
                type="submit"
                variant="contained"
                loading={false}
                loadingPosition="start"
                startIcon={<ThumbUpOffAltRoundedIcon />}
                sx={{ marginTop: '16px' }}
              >
                작성하기
              </LoadingButton>
            </form>
          </Stack>
        </Container>
      </div>
    </div>
  );
};

export default QnaDetailPage;