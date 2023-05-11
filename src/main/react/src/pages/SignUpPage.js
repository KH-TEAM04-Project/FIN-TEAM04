import { useState } from 'react';
import axios from 'axios';
import SignUpForm from '../sections/auth/SignUp/SignUpForm';




export default function SignUpPage() {
<<<<<<< HEAD
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
=======
>>>>>>> a92fe0addbddf82948087449d0e5b4e21940a557
  
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
    axios.post("/SignUp",formValues)

    .then(response => {
      console.log(response.aaa);
      // 회원가입 성공 후 처리할 로직 작성
    })
    .catch(error => {
      console.error(error);
      // 회원가입 실패 후 처리할 로직 작성
    });
  };

  

  const handleFormChange = (event) => {
    const { name, value } = event.target;
    setFormValues((prevValues) => ({
      ...prevValues,
      [name]: value,
    }));
  };

  return (
    <div>
      <h1>회원가입</h1>
      <SignUpForm
        formValues={formValues}
        onFormSubmit={handleFormSubmit}
        onFormChange={handleFormChange}
      />
      
    </div>
  );

  

}


