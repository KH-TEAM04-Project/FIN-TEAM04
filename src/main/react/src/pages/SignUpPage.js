import { useState } from 'react';
import axios from 'axios';
import SignUpForm from '../sections/auth/SignUp/SignUpForm';



export default function SignUpPage() {
  
  const [formValues, setFormValues] = useState({
    fullname: '',
    id: '',
    ssn: '',
    address: '',
    ph:'',
    email: '',
    password: '',
    confirmPassword: '',
    
  });

  const handleFormSubmit = (event) => {
    event.preventDefault();
    console.log('Submitted form:', formValues);
    axios.post('/SignUp', formValues)
    .then(response => {
      console.log(response.data);
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


