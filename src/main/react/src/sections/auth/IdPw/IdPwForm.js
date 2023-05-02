import React, { useState } from 'react';
import axios from 'axios';


const IdPwForm = ({ onFindId, onFindPassword }) => {
  const [name, setName] = useState('');
  const [ssn, setSsn] = useState('');
  const [id, setId] = useState('');

  const handleNameChange = (event) => {
    setName(event.target.value);
  };

  const handleSsnChange = (event) => {
    setSsn(event.target.value);
  };

  const handleIdChange = (event) => {
    setId(event.target.value);
  };

  const handleFindId = (event) => {
    event.preventDefault();
    onFindId(name, ssn);
  };

  const handleFindPassword = (event) => {
    event.preventDefault();
    onFindPassword(id, name, ssn);
  };

  return (
    
    <div>
      <form onSubmit={handleFindId}>
        <h2>아이디 찾기</h2>
        <label>
          이름:
          <input type="text" value={name} onChange={handleNameChange} />
        </label>
        <label>
          주민등록번호:
          <input type="text" value={ssn} onChange={handleSsnChange} />
        </label>
        <button type="submit">아이디 찾기</button>
      </form>

      <form onSubmit={handleFindPassword}>
        <h2>비밀번호 찾기</h2>
        <label>
          아이디:
          <input type="text" value={id} onChange={handleIdChange} />
        </label>
        <label>
          이름:
          <input type="text" value={name} onChange={handleNameChange} />
        </label>
        <label>
          주민등록번호:
          <input type="text" value={ssn} onChange={handleSsnChange} />
        </label>
        <button type="submit">비밀번호 찾기</button>
      </form>
    </div>
  );
};

export default IdPwForm;
