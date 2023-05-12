import {useState, useEffect} from 'react';
import {useNavigate} from 'react-router-dom';
import jwtDecode from 'jwt-decode'; // jwt-decode 모듈 추가
// @mui
import {Link, Stack, IconButton, InputAdornment, TextField, Checkbox} from '@mui/material';
import {LoadingButton} from '@mui/lab';
import axios from "axios";

// components
import Iconify from '../../../components/iconify';

// ----------------------------------------------------------------------

export default function LoginForm() {


    const navigate = useNavigate();

    const [showPassword, setShowPassword] = useState(false);

    const [id, setId] = useState("");
    const [pw, setPw] = useState("");
    const [idValid, setIdValid] = useState(false);
    const [notAllow, setNowAllow] = useState(true);

    const [pwValid, setPwValid] = useState(false);
    const handleId = (e) => {
        setId(e.target.value);
        // eslint-disable-next-line
        const regex = /^[a-zA-Z0-9]*$/;
        if (regex.test(id)) {
            setIdValid(true);
        } else {
            setIdValid(false);
        }
    }
    const handlePassword = (e) => {
        setPw(e.target.value);
        // eslint-disable-next-line
        const regex = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+])(?!.*[^a-zA-z0-9$`~!@$!%*#^?&\\(\\)\-_=+]).{8,20}$/;
        if (regex.test(e.target.value)) {
            setPwValid(true);
        } else {
            setPwValid(false)
        }

    };
    // ----------------------------------------------------------------

    const handleClick = () => {
        console.log("click login");
        console.log("ID : ", id);
        console.log("PW : ", pw);
        axios.post("/sLogin", null, {
            params: {
                mid: id,
                pwd: pw
            }
        })
            .then(response => {
                console.log(response);
                console.log("res.data.userId :: ", response.data);
                localStorage.setItem('token', response.data.accessToken);
                localStorage.setItem('token', response.data.refreshToken);
                
                console.log('acessToken', response.data.accessToken);
                console.log('refreshToken', response.data.refreshToken);

                const decoded1 = jwtDecode(response.data.accessToken);
                const decoded2 = jwtDecode(response.data.refreshToken); // 디코딩

                console.log(decoded1); // 디코딩된 토큰 출력
                console.log(decoded2);

                if (response.data.accessToken != null ) {
                    alert('환영합니다! ');
                    navigate('/')
                } else if(response.data.accessToken === null || response.data.accessToken === undefined) {
                    alert('로그인에 실패하였습니다.');
                }
            })
            .catch(error => {
                console.log(error);
            });
    };
// ----------------------------------------------------------------
    useEffect(() => {

        if (idValid && pwValid) {
            setNowAllow(false);
            return
        }
        setNowAllow(true);

    }, [idValid, pwValid]);

    return (
        <>
            <Stack spacing={3}>
                <TextField
                    name="id"
                    label="아이디"
                    type="id"
                    value={id}
                    onChange={handleId}
                    error={!idValid && id.length > 0}
                    helperText={!idValid && id.length > 0 && "올바른 아이디를 입력해주세요."}
                    required
                    fullWidth
                />

                <TextField
                    name="password"
                    label="비밀번호"
                    type={showPassword ? 'text' : 'password'}
                    value={pw}
                    onChange={handlePassword}
                    error={!pwValid && pw.length > 0}
                    helperText={!pwValid && pw.length > 0 && "영문, 숫자, 특수문자 포함 8자 이상 입력해주세요."}
                    required
                    fullWidth
                    InputProps={{
                        endAdornment: (
                            <InputAdornment position="end">
                                <IconButton onClick={() => setShowPassword(!showPassword)} edge="end">
                                    <Iconify icon={showPassword ? 'eva:eye-fill' : 'eva:eye-off-fill'}/>
                                </IconButton>
                            </InputAdornment>
                        )
                    }}
                />

            </Stack>

            <Stack direction="row" alignItems="center" justifyContent="space-between" sx={{my: 2}}>
                <Checkbox name="remember" label="Remember me">아이디저장</Checkbox>
                <Link href="/IdPw" underline="hover">아이디 비밀번호 찾기 </Link>

            </Stack>

            <LoadingButton fullWidth size="large" type="submit" variant="contained" disabled={notAllow}
                           onClick={handleClick}>
                로그인
            </LoadingButton>
        </>
    );
};
