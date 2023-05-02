import { useState } from 'react';
import axios from 'axios';
import IdPwForm from '../sections/auth/IdPw/IdPwForm';


const IdPwPage = () => {
    
  const [foundId, setFoundId] = useState('');
  const [foundPassword, setFoundPassword] = useState('');

  const handleFindId = async (name, ssn) => {
    try {
      const response = await axios.get(`/api/find-id?name=${name}&ssn=${ssn}`);
      setFoundId(response.data.id);
    } catch (error) {
      console.error(error);
    }
  };

  const handleFindPassword = async (id, name, ssn) => {
    try {
      const response = await axios.get(`/api/find-password?id=${id}&name=${name}&ssn=${ssn}`);
      setFoundPassword(response.data.password);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    
    <div>
      <h1>아이디와 비밀번호 찾기</h1>
      <IdPwForm onFindId={handleFindId} onFindPassword={handleFindPassword} />
      {foundId && <p>찾은 아이디: {foundId}</p>}
      {foundPassword && <p>찾은 비밀번호: {foundPassword}</p>}
    </div>
  );
};

export default IdPwPage;
