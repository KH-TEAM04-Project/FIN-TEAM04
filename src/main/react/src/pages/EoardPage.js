import { Helmet } from 'react-helmet-async';
// @mui
// import { Grid, Button, Container, Stack, Typography } from '@mui/material';
// components
// import Iconify from '../components/iconify';

// mock
import React, { useEffect, useState } from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';


import axios from 'axios';

// ----------------------------------------------------------------------



// ----------------------------------------------------------------------

export default function BlogPage() {
  const [posts, setPosts] = useState([]);

  const getPosts = () => {
      axios.get('/EoradPage').then((response) => {
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
        <title> retry2 |꽁머니 </title>
      </Helmet>

  \
  
    <TableContainer component={Paper}>
      <Table sx={{ maxWidth: 650}} aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell>게시판 작성</TableCell>
            <TableCell align="right">제목</TableCell>
            <TableCell align="right">작성자&nbsp;(g)</TableCell>
            <TableCell align="right">내용&nbsp;(g)</TableCell>
            <TableCell align="right">작성일&nbsp;(g)</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {posts.map((data) => (
            <TableRow
              key={data.writer}
              sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
            >
              <TableCell component="th" scope="row">
                {data.writer}
              </TableCell>
              <TableCell align="right">{data.title}</TableCell>
              <TableCell align="right">{data.content}</TableCell>
              <TableCell align="right">{data.regDate}</TableCell>
              <TableCell align="right">{data.writer}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  
 
      
    </>
  );
}
