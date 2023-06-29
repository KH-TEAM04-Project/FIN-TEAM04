import {Helmet} from 'react-helmet-async';
import {filter} from 'lodash';
import {styled, alpha} from '@mui/material/styles';
// @mui

// components

import axios from 'axios';
// mock
import React, {useEffect, useState} from 'react';
import {Link, useParams} from 'react-router-dom';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import TablePagination from '@mui/material/TablePagination';
// ----------------------------------------------------------------------  // 수정 성준 추가
import Button from '@mui/material/Button';
import {OutlinedInput, InputAdornment, Typography, Modal, Box, Container} from '@mui/material';
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


function descendingComparator(a, b, orderBy) {
    if (b[orderBy] < a[orderBy]) {
        return -1;
    }
    if (b[orderBy] > a[orderBy]) {
        return 1;
    }
    return 0;
}

function applySortFilter(posts, comparator, query) {
    const stabilizedThis = posts.map((el, index) => [el, index]);
    stabilizedThis.sort((a, b) => {
        const order = comparator(a[0], b[0]);
        if (order !== 0) return order;
        return a[1] - b[1];
    });
    if (query) {
        return stabilizedThis
            .filter((el) => el[0].content && el[0].content.toLowerCase().includes(query.toLowerCase()))
            .map((el) => el[0]);
    }
    return stabilizedThis.map((el) => el[0]);
}

function getComparator(order, orderBy) {
    return order === 'desc'
        ? (a, b) => descendingComparator(a, b, orderBy)
        : (a, b) => -descendingComparator(a, b, orderBy);
}

// ----------------------------------------------------------------------


const StyledSearch = styled(OutlinedInput)(({theme}) => ({
    width: 240,
    transition: theme.transitions.create(['box-shadow', 'width'], {
        easing: theme.transitions.easing.easeInOut,
        duration: theme.transitions.duration.shorter,
    }),
    '&.Mui-focused': {
        width: 320,
        boxShadow: theme.customShadows.z8,
    },
    '& fieldset': {
        borderWidth: `1px !important`,
        borderColor: `${alpha(theme.palette.grey[500], 0.32)} !important`,
    },
}));

export default function BoardList() {

    const token = localStorage.getItem('accessToken');
    const sub = token ? JSON.parse(atob(token.split('.')[1])).sub : '';
    const [mno, setMno] = useState(token ? JSON.parse(atob(token.split('.')[1])).mno : '');

    const auth = token ? JSON.parse(atob(token.split('.')[1])).auth : '';
    console.log(auth);

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
    const [page, setPage] = React.useState(0);
    const [rowsPerPage, setRowsPerPage] = React.useState(10);

    const handleChangePage = (event: React.ChangeEvent<any>, newPage: number) => {
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
        axios.get('/board/list', {
            headers: {
                // http 헤더의 auth 부분에 accessToken 값 설정
                'Authorization': `Bearer ${token}`
            }
        })
            .then((response) => {
            setPosts(response.data);
            console.log(response.data.length);
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
    const compareFunction = (a, b) => {
        return b.bno - a.bno;
    };
    posts.sort(compareFunction);

// Search를 위한 코드
    const [order, setOrder] = useState('asc');
    const [orderBy, setOrderBy] = useState('name');
    const [filterName, setFilterName] = useState('');

    const handleFilterByName = (event) => {
        setPage(0);
        setFilterName(event.target.value);
    };

    const emptyRows = page > 0 ? Math.max(0, (1 + page) * rowsPerPage - posts.length) : 0;

    const filteredUsers = applySortFilter(posts, getComparator(order, orderBy), filterName);

    const isNotFound = !filteredUsers.length && !!filterName;

    return (
        <>
            <Helmet>
                <title> retry | Minimal UI </title>
            </Helmet>

            <StyledSearch
                sx={{mr: 160, ml: 1}}
                value={filterName}
                onChange={handleFilterByName}
                placeholder="내용을 검색하세요"
                startAdornment={
                    <InputAdornment position="start">
                        <Iconify icon="eva:search-fill" sx={{color: 'text.disabled', width: 20, height: 20}}/>
                    </InputAdornment>
                }
            />

                {auth === "ROLE_ADMIN" && (
                    <Button href='http://localhost:3000/board/regist' variant="contained"
                            startIcon={<Iconify icon="eva:plus-fill"/>}>
                        게시글 작성하기
                    </Button>
                )}



            <TableContainer component={Paper}>
                <Table sx={{minWidth: 650, overflow: 'hidden'}} aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell> </TableCell>
                            <TableCell align="right">번호</TableCell>
                            <TableCell align="right">제목</TableCell>
                            <TableCell align="right">내용</TableCell>
                            <TableCell align="right">작성자</TableCell>
                            <TableCell align="right">작성일</TableCell>
                            <TableCell align="right">조회수</TableCell>

                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {filteredUsers.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                            .map((data) => (
                                <TableRow
                                    key={data.bno}
                                    sx={{'&:last-child td, &:last-child th': {border: 0}}}
                                >
                                    <TableCell component="th" scope="row">&nbsp;</TableCell>

                                    <TableCell align="right">{data.bno}</TableCell>
                                    <TableCell align="right">
                                        <Link to={`/board/detail/${data.bno}`}>{data.title}</Link>
                                    </TableCell>
                                    <TableCell align="right">{data.content}</TableCell>
                                    <TableCell align="right">{data.writerID}</TableCell>
                                    <TableCell align="right">{data.regDate}</TableCell>
                                    <TableCell align="right">{data.hits}</TableCell>

                                </TableRow>
                            ))}
                    </TableBody>

                    {isNotFound && (
                        <TableBody>
                            <TableRow>
                                <TableCell align="center" colSpan={6} sx={{py: 3}}>
                                    <Paper
                                        sx={{
                                            textAlign: 'center',
                                        }}
                                    >
                                        <Typography variant="h6" paragraph>
                                            못찾겠어요...
                                        </Typography>

                                        <Typography variant="body2">
                                            No results found for &nbsp;
                                            <strong>&quot;{filterName}&quot;</strong>.
                                            <br/> 다시한번 검색해주세요 지발요~
                                        </Typography>
                                    </Paper>
                                </TableCell>
                            </TableRow>
                        </TableBody>
                    )}


                </Table>
            </TableContainer>
            <Container align="right">
                <Stack alignItems="center" margin-top="auto" spacing={3}>

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
