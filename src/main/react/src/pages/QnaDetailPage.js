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
  Grid,
} from '@mui/material';
import { LoadingButton } from '@mui/lab';
import WbSunnyIcon from '@mui/icons-material/WbSunny';
import MenuIcon from '@mui/icons-material/Menu';
import ThumbUpOffAltRoundedIcon from '@mui/icons-material/ThumbUpOffAltRounded';
import styled from 'styled-components'; // styled-components 추가
import DeleteIcon from '@mui/icons-material/Delete';
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
  const [replys, setReplys] = useState([]); // 댓글 목록 상태
  const [replyContent, setReplyContent] = useState(''); // 댓글 내용 상태
  const [liked, setLiked] = useState(false);

  const getPost = () => {
    axios
      .get(`/qna/detail/${qno}`, {
         headers: {
             // http 헤더의 auth 부분에 accessToken 값 설정
             'Authorization': `Bearer ${token}`
         }
      })
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
      .get(`/qna/replys/${qno}`, {
         headers: {
             // http 헤더의 auth 부분에 accessToken 값 설정
             'Authorization': `Bearer ${token}`
         }
      })
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
      axios.put(`/qna/update/${qno}`, data, {
        headers: {
            // http 헤더의 auth 부분에 accessToken 값 설정
            'Authorization': `Bearer ${token}`
        }
      })
        .then((response) => {
          console.log("게시글이 성공적으로 수정되었습니다.");
          alert("수정이 완료되었습니다.");
          // 수정된 게시글 정보로 상태 업데이트
          setPost(response.data.qnaDTO);
        })
        .catch((error) => {
          console.error(error);
        });
  };

  const handleDelete = () => {
    axios
      .delete(`/qna/delete/${qno}`, {
        headers: {
            // http 헤더의 auth 부분에 accessToken 값 설정
            'Authorization': `Bearer ${token}`
        }
      })
      .then((response) => {
        console.log("게시글이 성공적으로 삭제되었습니다.");
        alert("게시글이 삭제되었습니다.");
        // 게시글 삭제 후, 목록 페이지로 이동하도록 처리
        navigate('/qna/list', { replace: true });
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const handleLike = () => {
    setLiked(true);

    const requestData = {
      qno: data.qno, // 좋아요할 질문 번호
      mno,
    };

    axios
      .post(`/qna/likes/{qno}`, requestData, {
         headers: {
           // http 헤더의 auth 부분에 accessToken 값 설정
           'Authorization': `Bearer ${token}`
         }
      }) // 요청 본문을 requestData로 전달
      .then((response) => {
        console.log('좋아요 요청 성공:', response.data);
        // 좋아요 요청 성공 시 추가 동작 구현
      })
      .catch((error) => {
        console.error('좋아요 요청 실패:', error);
        // 좋아요 요청 실패 시 추가 동작 구현
      });
  };

  const handleUnlike = () => {
    setLiked(false);

    const requestData = {
      qno: data.qno, // 좋아요 취소할 질문 번호
      mno,
    };

    axios
      .post(`/qna/unLikes/{qno}`, requestData, {
         headers: {
           // http 헤더의 auth 부분에 accessToken 값 설정
           'Authorization': `Bearer ${token}`
         }
      }) // 요청 본문을 requestData로 전달
      .then((response) => {
        console.log('좋아요 취소 요청 성공:', response.data);
        // 좋아요 취소 요청 성공 시 추가 동작 구현
      })
      .catch((error) => {
        console.error('좋아요 취소 요청 실패:', error);
        // 좋아요 취소 요청 실패 시 추가 동작 구현
      });
  };

  const [openDeleteDialog, setOpenDeleteDialog] = useState(false); // 삭제 다이얼로그 열림/닫힘 상태
  // 삭제 다이얼로그 열기
  const handleOpenDeleteDialog = () => {
    setOpenDeleteDialog(true);
  };
  // 삭제 다이얼로그 닫기
  const handleCloseDeleteDialog = () => {
    setOpenDeleteDialog(false);
  };
  // 댓글 삭제 함수
  const handleDeleteReply = (rno) => {
    axios
      .delete(`/qna/replys/${qno}/${rno}`, {
        headers: {
          // http 헤더의 auth 부분에 accessToken 값 설정
          'Authorization': `Bearer ${token}`
        }
      })
      .then((response) => {
        console.log('댓글이 성공적으로 삭제되었습니다.');
        alert('댓글이 삭제되었습니다.');
        getReplys();
      })
      .catch((error) => {
        console.error('댓글 삭제 실패:', error);
      });
    };

  const handleSubmit = (event) => {
    event.preventDefault();
    // replyContent 값을 서버로 전송하고, 성공적으로 작성된 댓글을 화면에 추가하는 로직을 구현해야 합니다.
    const replyData = {
      content: replyContent,
      mno,
    };

    axios
      .post(`/qna/replys/${qno}`, replyData, {
          headers: {
              // http 헤더의 auth 부분에 accessToken 값 설정
              'Authorization': `Bearer ${token}`
          }
      })
      .then((response) => {
        console.log('댓글 작성 성공:', response.data);
        alert("댓글이 작성되었습니다.");
        // 댓글 목록을 다시 불러옵니다.
        setReplyContent(''); // 작성한 댓글의 내용을 초기화
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
              <MenuItem component={Link} to="/qna/regist">
                새로 작성하기
              </MenuItem>
              <MenuItem onClick={handleDelete}>
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
            <Grid container alignItems="center" sx={{ mb: 2 }}>
              <Grid item xs={2}>
                <Typography variant="h5">제목:</Typography>
              </Grid>
              <Grid item xs={10}>
                <TextField
                  value={post && post.title}
                  name="text"
                  disabled
                  sx={{
                    width: '100%',
                    padding: '10px',
                    borderRadius: '5px',
                    fontSize: '24px',
                    fontWeight: 'bold',
                  }}
                />
              </Grid>
            </Grid>

            <Grid container alignItems="center" sx={{ mb: 2 }}>
              <Grid item xs={2}>
                <Typography variant="h6">작성자:</Typography>
              </Grid>
              <Grid item xs={10}>
                <TextField
                  value={post && post.writerID}
                  color="secondary"
                  name="text"
                  disabled
                  sx={{
                    width: '100%',
                    padding: '10px',
                    borderRadius: '5px',
                    fontSize: '20px',
                  }}
                />
              </Grid>
            </Grid>

            <Grid container alignItems="center" sx={{ mb: 2 }}>
              <Grid item xs={2}>
                <Typography variant="h6">작성일:</Typography>
              </Grid>
              <Grid item xs={10}>
                <TextField
                  value={post && post.regDate}
                  color="secondary"
                  name="text"
                  disabled
                  sx={{
                    width: '100%',
                    padding: '10px',
                    borderRadius: '5px',
                    fontSize: '20px',
                  }}
                />
              </Grid>
            </Grid>

            <Typography variant="h6">내용:</Typography>
            <TextField
              id="outlined-multiline-static"
              disabled
              multiline
              rows={10}
              defaultValue={post && post.content}
              sx={{ width: '100%', padding: '10px', borderRadius: '5px', marginTop: '20px' }}
            />
          </Stack>
          <Stack direction="row" spacing={2} sx={{ marginTop: '32px' }}>
            {!liked ? (
              <Button variant="outlined" onClick={handleLike}>
                <ThumbUpOffAltRoundedIcon sx={{ marginRight: '8px' }} /> 좋아요
              </Button>
            ) : (
              <Button variant="outlined" onClick={handleUnlike}>
                좋아요 취소
              </Button>
            )}
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
                    <TableCell>{reply.writerID}</TableCell>
                    <TableCell>{reply.content}</TableCell>
                    <TableCell>{reply.regDate}</TableCell>
                    <TableCell>
                      <IconButton color="error" onClick={() => handleOpenDeleteDialog(reply.qno, reply.rno)}>
                        <DeleteIcon />
                      </IconButton>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </Stack>
        </Container>
      </div>
    </div>
  );
};

export default QnaDetailPage;