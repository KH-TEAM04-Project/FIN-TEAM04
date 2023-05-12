import { Helmet } from 'react-helmet-async';
// @mui
// import { Grid, Button, Container, Stack, Typography } from '@mui/material';
// components
// import Iconify from '../components/iconify';
import axios from 'axios';
// mock
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
// ----------------------------------------------------------------------  // 수정 성준 추가
import Button from '@mui/material/Button';
import { Modal,Box,Container } from '@mui/material';
import TablePagination from '@mui/material/TablePagination';
import Stack from '@mui/material/Stack';
import Iconify from '../components/iconify';
// ----------------------------------------------------------------------
const style = {
  position: "absolute",
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

export default function QnaPage() {
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(10);

  const handleChangePage = (event:React.ChangeEvent<any>, newPage: number) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };

     const handleClose = () => {
        setOpen1(false);
      };

    const [open1, setOpen1] = React.useState(false);
      const handleOpen = () => {
        setOpen1(true);
      };

    const [posts, setPosts] = useState([]);

    const getPosts = () => {
        axios.get('/re').then((response) => {
            setPosts(response.data);
            console.log(response.data);
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

    // 성준 추가 (게시글 삭제 관련
      const handleDelete = (qno) => {
        axios.get(`/delete/${qno}`).then((response) => {
          console.log('게시글이 삭제되었습니다.');
          // 삭제 후 게시글 리스트를 다시 불러옴
          getPosts();
        });
      };

      useEffect(() => {
        getPosts();
      }, []);

  return (
    <>

      <Helmet>
        <title> retry | Minimal UI </title>
      </Helmet>
      <Button href='http://localhost:3000/DoardPage' variant="contained" startIcon={<Iconify icon="eva:plus-fill" />}>
        QnA 작성하기
      </Button>
  
    <TableContainer component={Paper} align="center">
      <Table sx={{ maxWidth: 2000 ,overflow: 'hidden' }} aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell>게시판</TableCell>
            <TableCell align="right">번호</TableCell>
            <TableCell align="right">제목</TableCell>
            <TableCell align="right">내용</TableCell>
            <TableCell align="right">작성일</TableCell>
            <TableCell align="right">조회수</TableCell>
            <TableCell align="right">작성자</TableCell>
            <TableCell align="right">수정/삭제</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
        {posts.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
              .map((data) => (
            <TableRow
              key={data.qno}
              sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
            >
              <TableCell component="th" scope="row">
              -
              </TableCell>
              <TableCell align="right">{data.qno}</TableCell>
              <TableCell align="right">
                <Link to={`/QnaReadPage/${data.qno}`}>{data.title}</Link>
              </TableCell>
              <TableCell align="right">{data.content}</TableCell>
              <TableCell align="right">{data.regDate}</TableCell>
              <TableCell align="right">조회수</TableCell>
              <TableCell align="right">{data.writer}</TableCell>
              <TableCell align="right">

                   <Link to={`/QEditPage/${data.qno}`}>
                   <Button>
                          <Iconify  icon={'eva:edit-fill'} sx={{ mr: 2 }} />
                         글 수 정
                         </Button>
                         </Link>


                 <Button color='error' onClick={handleOpen}>
                      <Iconify icon={'eva:trash-2-outline'} sx={{ mr: 2 }} />글 삭 제</Button>
                      <Modal
                        open={open1}
                        onClose={handleClose}
                        aria-labelledby="parent-modal-title"
                        aria-describedby="parent-modal-description"
                      >
                        <Box sx={{ ...style, width: 400 }}>
                          <h2 id="parent-modal-title">게시글 삭제</h2>
                          <p id="parent-modal-description">
                            진짜 글삭제 되는데요?
                          </p>
                          <Button href="http://localhost:3000/re" onClick={() => handleDelete(data.qno)}>진짜 삭제</Button>
                        </Box>
                      </Modal>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
             <Container align="right">
              <Stack alignItems="center" margin-top="auto" spacing={3} >

              <TablePagination
                     rowsPerPageOptions={[10, 25, 100]}
                     component="div"
                     count={posts.length}
                     rowsPerPage={rowsPerPage}
                     page={page}
                     onPageChange={handleChangePage}
                     onRowsPerPageChange={handleChangeRowsPerPage}
                   />
              </Stack>
              </Container>
    </>
  );
}
