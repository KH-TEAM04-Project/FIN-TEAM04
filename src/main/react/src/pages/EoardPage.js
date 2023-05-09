import { Helmet } from 'react-helmet-async';
// @mui
// import { Grid, Button, Container, Stack, Typography } from '@mui/material';
// components
// import Iconify from '../components/iconify';

// mock
import React, { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
// ----------------------------------------------------------------------  // 수정 성준 추가
import Button from '@mui/material/Button';


import axios from 'axios';
import Label from '../components/label';
import Iconify from '../components/iconify';
import Scrollbar from '../components/scrollbar';
// ----------------------------------------------------------------------



// ----------------------------------------------------------------------

export default function QnaPage() {
  const [posts, setPosts] = useState([]);

  const getPosts = () => {
    axios.get('/EoardPage').then((response) => {
      setPosts(response.data);
    });
  };

  // 성준 추가 (게시글 삭제 관련)
  const handleDelete = (bno) => {
    axios.get(`/delete/${bno}`).then((response) => {
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


        <TableContainer component={Paper}>
          <Table sx={{ minWidth: 650 }} aria-label="simple table">
            <TableHead>
              <TableRow>
                <TableCell> </TableCell>
                <TableCell align="right">번호</TableCell>
                <TableCell align="right">제목</TableCell>
                <TableCell align="right">내용</TableCell>
                <TableCell align="right">작성자</TableCell>
                <TableCell align="right">작성일</TableCell>
                <TableCell align="right">조회수</TableCell>
                <TableCell align="right">수정/삭제</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {posts.map((data) => (
                  <TableRow
                      key={data.bno}
                      sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                  >
                    <TableCell component="th" scope="row">
                      {data.writer}
                    </TableCell>
                    <TableCell align="right">{data.bno}</TableCell>
                    <TableCell align="right">
                      <Link to={`/posts/${data.bno}`}>{data.title}</Link>
                    </TableCell>
                    <TableCell align="right">{data.content}</TableCell>
                    <TableCell align="right">{data.writer}</TableCell>
                    <TableCell align="right">{data.regDate}</TableCell>
                    <TableCell align="right">{data.hits}</TableCell>
                    <TableCell align="right">
                      <button onClick={() => console.log('수정')}>수정</button>
                      <button onClick={() => handleDelete(data.bno)}>삭제</button>
                    </TableCell>
                  </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>

      </>
  );
}
