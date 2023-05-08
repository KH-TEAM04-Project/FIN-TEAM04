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
import Label from '../components/label';
import Iconify from '../components/iconify';
import Scrollbar from '../components/scrollbar';
// ----------------------------------------------------------------------



// ----------------------------------------------------------------------

export default function BlogPage() {
  function Board1() {
    
    // 웹에서 서버로 요청 >> 리스트전달해줘라진짜
    const getPosts = () => {
    axios.get('/re').then((response)=> {
      setPosts(response.data);
    })
  }
  useEffect(getPosts,[]);
  
  }
  const [posts, setPosts] = useState([]);
  return (
    <>
      <Helmet>
        <title> retry | Minimal UI </title>
      </Helmet>

  
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 650 }} aria-label="simple table">
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
