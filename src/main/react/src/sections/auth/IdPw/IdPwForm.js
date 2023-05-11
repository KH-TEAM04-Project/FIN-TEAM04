import React, { useState } from 'react';
import axios from 'axios';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import { Email } from '@mui/icons-material';
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';

const IdPwForm = ({ onFindId, onFindPassword }) => {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [id, setId] = useState('');

  const handleNameChange = (event) => {
    setName(event.target.value);
  };

  const handleEmailChange = (event) => {
    setEmail(event.target.value);
  };

  const handleIdChange = (event) => {
    setId(event.target.value);
  };

  const handleFindId = (event) => {
    event.preventDefault();
    onFindId(name, email);
  };

  const handleFindPassword = (event) => {
    event.preventDefault();
    onFindPassword(id, name, email);
  };

  return (
    <Box
      sx={{
        width: '50%',
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'left',
        justifyContent: 'center',
        gap: '1rem',
      }}
    >
      <form onSubmit={handleFindId}>
      <h2 style={{ 
  fontSize: '1.5rem',
  fontWeight: 'bold',
  marginBottom: '1rem',
}}>
  아이디 찾기
</h2>
        <TextField
          fullWidth
          label="이름"
          variant="outlined"
          size="small"
          value={name}
          onChange={handleNameChange}
          margin="dense"
        />
        <TextField
          fullWidth
          label="이메일"
          variant="outlined"
          size="small"
          value={email}
          onChange={handleEmailChange}
          margin="dense"
        />
         <Stack spacing={2} direction="row">
      <Button type="submit">이메일로 인증번호 발송</Button>
      
    </Stack>
    
      </form>

      <form onSubmit={handleFindPassword}>
        <h2>비밀번호 찾기</h2>
        <TextField
          fullWidth
          label="아이디"
          variant="outlined"
          size="small"
          value={id}
          onChange={handleIdChange}
          margin="dense"
        />
        <TextField
          fullWidth
          label="이름"
          variant="outlined"
          size="small"
          value={name}
          onChange={handleNameChange}
          margin="dense"
        />
        <TextField
          fullWidth
          label="이메일"
          variant="outlined"
          size="small"
          value={email}
          onChange={handleEmailChange}
          margin="dense"
        />
        <Stack spacing={2} direction="row">
      <Button type="submit">비밀번호 찾기</Button>
      
    </Stack>
      </form>
    </Box>
  );
};

export default IdPwForm;
