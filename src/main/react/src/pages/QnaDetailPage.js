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
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions
} from '@mui/material';
import { LoadingButton } from '@mui/lab';
import WbSunnyIcon from '@mui/icons-material/WbSunny';
import MenuIcon from '@mui/icons-material/Menu';
import ThumbUpOffAltRoundedIcon from '@mui/icons-material/ThumbUpOffAltRounded';
import ThumbUpAltRoundedIcon from '@mui/icons-material/ThumbUpAltRounded';
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
  const [reply, setReply] = useState([]); // 댓글 목록 상태
  const [replyContent, setReplyContent] = useState(''); // 댓글 내용 상태
  const [liked, setLiked] = useState(false);
  const [likesCount, setLikesCount] = useState(0); // 좋아요 개수 표시
  const [likeData, setLikeData] = useState(0); // 초기값을 빈 객체로 설정
  const [isLiked, setIsLiked] = useState(false);
  // 사용자 인증 상태
  const isAuthenticated = !!token;
  const loggedInUserId = sub; // 로그인한 사용자의 ID를 얻어오는 코드 또는 상태

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
        console.log("성공");
      })
      .catch((error) => {
        if (error.response) {
          console.log("response 에러 발생");
        } else if (error.request) {
          console.log("network error");
        } else {
          console.log(error);
        }
      });
  };

  const getReply = () => {
    axios
      .get(`/qna/replys/${qno}`, {
         headers: {
             // http 헤더의 auth 부분에 accessToken 값 설정
             'Authorization': `Bearer ${token}`
         }
      })
      .then((response) => {
        setReply(response.data); // 댓글 목록 업데이트
        console.log(response.data);
        console.log("댓글 목록");
      })
      .catch((error) => {
        if (error.response) {
          console.log("response 에러 발생");
        } else if (error.request) {
          console.log("network error");
        } else {
          console.log(error);
        }
      });
  };

  useEffect(() => {
    getPost();
    getReply();
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

  const fetchLikesData = async () => {
    try {
      const response = await axios.get(`/qna/likes/${qno}`, {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
      const data = response.data;
      setLikeData(data); // 좋아요 데이터를 가져와서 likeData에 설정
      setIsLiked(true); // 좋아요 요청이 성공하면 좋아요 상태를 true로 설정
    } catch (error) {
      console.log('Error fetching likes data:', error);
    }
  };

  useEffect(() => {
    fetchLikesData();
  }, []);

  console.log(likeData);

  const handleLike = () => {
    if (liked) return; // 이미 좋아요한 상태라면 중복 클릭 방지

    const requestData = {
      qno, // 좋아요할 질문 번호
      mno,
    };

    axios
      .post(`/qna/addLike/${likeData.qno}`, requestData, {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      })
      .then((response) => {
        console.log('좋아요 요청 성공:', response.data);
        fetchLikesData(); // 좋아요 요청 성공 시 좋아요 데이터를 다시 가져와서 상태를 업데이트
        // 좋아요 요청 성공 시 추가 동작 구현
      })
      .catch((error) => {
        console.error('좋아요 요청 실패:', error);
        // 좋아요 요청 실패 시 추가 동작 구현
      });
      setLiked(true); // 좋아요 상태 변경
  };

  const handleUnlike = () => {
    if (!liked) return; // 좋아요하지 않은 상태라면 중복 클릭 방지

    setLiked(false);
    const requestData = {
      qno, // 좋아요 취소할 질문 번호
      mno,
    };

    axios
      .delete(`/qna/removeLike/${likeData.qno}`, {
        headers: {
          'Authorization': `Bearer ${token}`
        },
        data: requestData // 요청 본문에 데이터 추가
      })
      .then((response) => {
        console.log('좋아요 취소 요청 성공:', response.data);
        fetchLikesData(); // 좋아요 취소 요청 성공 시 좋아요 데이터를 다시 가져와서 상태를 업데이트
        // 좋아요 취소 요청 성공 시 추가 동작 구현
      })
        .catch((error) => {
          console.error('좋아요 취소 요청 실패:', error);
          // 좋아요 취소 요청 실패 시 추가 동작 구현
      });
    setLiked(false); // 좋아요 상태 변경
  };

  // 좋아요 개수 가져오기
  const fetchLikesCount = async () => {
    try {
      const response = await axios.get(`/qna/likes/${qno}`, {
        headers: {
          Authorization: `Bearer ${token}`, // 필요한 경우 헤더에 인증 토큰 추가
        },
      });
      const data = response.data;
      setLikesCount(data);
    } catch (error) {
      console.log('Error fetching likes count:', error);
    }
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
  const handleDeleteReply = (reply) => {
    console.log('reply:', reply); // 추가: reply 객체 확인
    if (!reply || typeof reply.qno === 'undefined' || typeof reply.rno === 'undefined') {
      console.error('유효하지 않은 댓글입니다.');
      return;
    }
    const { qno, rno } = reply;
    axios
      .delete(`/qna/replys/delete/${qno}/${rno}`, {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      })
      .then((response) => {
        console.log('댓글이 성공적으로 삭제되었습니다.');
        alert('댓글이 삭제되었습니다.');
        getReply();
      })
      .catch((error) => {
        console.error('댓글 삭제 실패:', error);
        alert('댓글 삭제에 실패했습니다.');
      });
  };

  // 삭제 버튼 클릭 이벤트 핸들러
  const handleDeleteButtonClick = (reply) => {
    if (!reply || typeof reply.qno === 'undefined' || typeof reply.rno === 'undefined') {
      console.error('유효하지 않은 댓글입니다.');
      return;
    }
    handleDeleteReply(reply);
    handleCloseDeleteDialog(); // 다이얼로그를 닫는 함수 호출
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
        getReply();
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

            </Menu>
            <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
              Q&A 상세보기
            </Typography>
            <Tooltip title="Light / Dark" edge="end">
              <IconButton color="inherit" onClick={handleOpen}>
                <WbSunnyIcon />
              </IconButton>
            </Tooltip>
            <Avatar alt="User Avatar" src="/avatar.png" />
          </Toolbar>
        </AppBar>
        <Container>

          <Stack sx={{ marginTop: '80px' }}>
            <Grid container alignItems="center" sx={{ mb: 2 }}>
              <Grid item xs={1}>
                <Typography variant="h5">제목:</Typography>
              </Grid>
              <Grid item xs={10}>
                <TextField
                  value={post && post.title}
                  name="text"
                  disabled
                  sx={{
                    width: '100%',
                    padding: '1px',
                    borderRadius: '5px',
                    fontSize: '24px',
                    fontWeight: 'bold',
                  }}
                />
              </Grid>
            </Grid>

            <Grid container alignItems="center" sx={{ mb: 2 }}>
              <Grid item xs={1}>
                <Typography variant="h5">작성자:</Typography>
              </Grid>
              <Grid item xs={10}>
                <TextField
                  value={post && post.writerID}
                  color="secondary"
                  name="text"
                  disabled
                  sx={{
                    width: '100%',
                    padding: '1px',
                    borderRadius: '5px',
                    fontSize: '20px',
                  }}
                />
              </Grid>
            </Grid>

            <Grid container alignItems="center" sx={{ mb: 2 }}>
              <Grid item xs={1}>
                <Typography variant="h5">작성일:</Typography>
              </Grid>
              <Grid item xs={10}>
                <TextField
                  value={post && post.regDate}
                  color="secondary"
                  name="text"
                  disabled
                  sx={{
                    width: '100%',
                    padding: '1px',
                    borderRadius: '5px',
                    fontSize: '20px',
                  }}
                />
              </Grid>
            </Grid>
            <TextField
              id="outlined-multiline-static"
              disabled
              multiline
              rows={10}
              defaultValue={post && post.content}
              sx={{ width: '100%', padding: '10px', borderRadius: '5px', marginTop: '20px' }}
            />
          </Stack>
          <Stack direction="row" spacing={2} sx={{ marginTop: '32px', marginBottom: '16px', justifyContent: 'space-between' }}>
            <Stack direction="row" spacing={2}>
              {!liked ? (
                <Button variant="outlined" onClick={handleLike}>
                  <ThumbUpOffAltRoundedIcon sx={{ marginRight: '8px' }} />
                  좋아요
                </Button>
              ) : (
                <Button variant="outlined" onClick={handleUnlike}>
                  <ThumbUpAltRoundedIcon sx={{ marginRight: '8px' }} />
                  좋아요 취소
                </Button>
              )}
              <div
                style={{
                  display: 'inline-flex',
                  alignItems: 'center',
                  padding: '4px 8px',
                  borderRadius: '4px',
                  backgroundColor: '#f5f5f5',
                }}
              >
                <Typography variant="subtitle2" color="textSecondary">
                  이 게시글을 좋아하는 사람: {likeData}명
                </Typography>
              </div>
            </Stack>
            {isAuthenticated && post && post.writerID === loggedInUserId && (
              <Button
                onClick={handleDelete}
                variant="contained"
                color="error"
                size="small"
                startIcon={<DeleteIcon />}
              >
                게시글 삭제하기
              </Button>
            )}
          </Stack>

            <Stack sx={{ marginTop: '32px' }}>
            <form onSubmit={handleSubmit}>
              <Stack direction="row" spacing={2}>
                <TextField
                  label="댓글을 작성해주세요"
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
          <Stack sx={{ marginTop: '60px' }}>
            <Typography variant="h5">댓글</Typography>
            <Table sx={{ marginTop: '20px' }}>
              <TableHead>
                <TableRow>
                  <TableCell>작성자</TableCell>
                  <TableCell>내용</TableCell>
                  <TableCell>작성일시</TableCell>
                  <TableCell>댓글 삭제</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {reply?.map((reply) => (
                  <TableRow key={reply.rno}>
                    <TableCell>{reply.writerID}</TableCell>
                    <TableCell>{reply.content}</TableCell>
                    <TableCell>{reply.regDate}</TableCell>
                    <TableCell>
                      {isAuthenticated && reply.writerID === loggedInUserId && (
                        <IconButton
                          color="error"
                          onClick={() => handleDeleteButtonClick(reply)}
                        >
                          <DeleteIcon />
                        </IconButton>
                      )}
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