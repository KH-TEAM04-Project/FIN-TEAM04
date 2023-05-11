import { Helmet } from 'react-helmet-async';
// @mui
import { styled } from '@mui/material/styles';
import { Link, Container, Typography, Divider, Stack, Button } from '@mui/material';
// hooks
import useResponsive from '../hooks/useResponsive';
// components
import Logo from '../components/logo';
import Iconify from '../components/iconify';
// sections
import { SignUpPage } from '../sections/auth/SignUp';

export default function SignUpPage() {
    const mdUp = useResponsive('up', 'md');
  
<<<<<<< HEAD
    return (
      <>
        <Helmet>
          <title> Login | Minimal UI </title>
        </Helmet>
=======
  const [formValues, setFormValues] = useState({
    mname: "",
    mid: "",
    regno: "",
    email: "",
    pwd: "",
    detailaddress: "",
    address: "",
    ph: "",
    
  });

  const handleFormSubmit = (event) => {
    event.preventDefault();
    console.log('Submitted form:', formValues);
    axios.post("/SignUp2",formValues)

    .then(response => {
      console.log(response.aaa);
      // 회원가입 성공 후 처리할 로직 작성
    })
    .catch(error => {
      console.error(error);
      // 회원가입 실패 후 처리할 로직 작성
    });
  };

>>>>>>> parent of 2234eb0 (회원삭제폼 추가)
  
        <StyledRoot>
          <Logo
            sx={{
              position: 'fixed',
              top: { xs: 16, sm: 24, md: 40 },
              left: { xs: 16, sm: 24, md: 40 },
            }}
          />
  
          {mdUp && (
            <StyledSection>
              <Typography variant="h3" sx={{ px: 5, mt: 10, mb: 5 }}>
                Hi, Welcome Back
              </Typography>
              <img src="/assets/illustrations/illustration_login.png" alt="login" />
            </StyledSection>
          )}
  
          <Container maxWidth="sm">
            <StyledContent>
              <Typography variant="h4" gutterBottom>
                꽁 머 니
              </Typography>
  
              <Typography variant="body2" sx={{ mb: 5 }}>
          계정이 없으신가요 ? {''}
  
        <Link href="/SignUp" underline="hover">회원가입</Link>
            </Typography>
  
              <Stack direction="row" spacing={2}>
                <Button fullWidth size="large" color="inherit" variant="outlined">
                  <Iconify icon="eva:google-fill" color="#DF3E30" width={22} height={22} />
                </Button>
  
                <Button fullWidth size="large" color="inherit" variant="outlined">
                  <Iconify icon="eva:facebook-fill" color="#1877F2" width={22} height={22} />
                </Button>
  
                <Button fullWidth size="large" color="inherit" variant="outlined">
                  <Iconify icon="eva:twitter-fill" color="#1C9CEA" width={22} height={22} />
                </Button>
              </Stack>
  
              <Divider sx={{ my: 3 }}>
                <Typography variant="body2" sx={{ color: 'text.secondary' }}>
                  OR
                </Typography>
              </Divider>
  
              <LoginForm />
            </StyledContent>
          </Container>
        </StyledRoot>
      </>
    );
  }
  