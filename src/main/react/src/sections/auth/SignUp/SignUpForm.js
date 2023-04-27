// import { useState } from 'react';
// import { Link, Stack, TextField } from '@mui/material';
// import { LoadingButton } from '@mui/lab';

// export default function SignUpForm() {
    
//   const [email, setEmail] = useState("");
//   const [pw, setPw] = useState("");
//   const [pwConfirm, setPwConfirm] = useState("");
//   const [emailValid, setemailValid] = useState(false);    
//   const [pwValid, setPwValid] =useState(false);
//   const [pwConfirmValid, setPwConfirmValid] = useState(false);
//   const [notAllow, setNowAllow] = useState(true);

//   const handleEmail = (e) => {
//     setEmail(e.target.value);
//     // eslint-disable-next-line
//     const regex =  /^(([^<>()\[\].,;:\s@"]+(\.[^<>()\[\].,;:\s@"]+)*)|(".+"))@(([^<>()[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/i;
//     if(regex.test(email)) {
//       setemailValid(true);
//     } else {
//       setemailValid(false);
//     }
//   }

//   const handlePassword = (e) => {
//     setPw(e.target.value);
//     // eslint-disable-next-line
//     const regex = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+])(?!.*[^a-zA-z0-9$`~!@$!%*#^?&\\(\\)\-_=+]).{8,20}$/;
//     if (regex.test(e.target.value)) {
//       setPwValid(true);
//     } else {
//       setPwValid(false)
//     }
//   }

//   const handlePasswordConfirm = (e) => {
//     setPwConfirm(e.target.value);
//     if (e.target.value === pw) {
//       setPwConfirmValid(true);
//     } else {
//       setPwConfirmValid(false);
//     }
//   }

//   const handleSignUp = (e) => {
//     e.preventDefault();
//     // 회원가입 로직 구현
//   }

//   const isAllValid = emailValid && pwValid && pwConfirmValid;

//   return (
    
//       <Stack spacing={3}>
//         <TextField
//           name="email"
//           label="이메일 주소"
//           type="email"
//           value={email}
//           onChange={handleEmail}
//           error={!emailValid && email.length > 0}
//           helperText={!emailValid }
//           />
//         </Stack>
//   )
// }

    
