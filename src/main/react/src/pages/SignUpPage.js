import { useState } from 'react';
import SignUpForm from '../sections/auth/SignUp/SignUpForm';


export default function SignUpPage() {
  
  const [formValues, setFormValues] = useState({
    FullName: '',
    Id: '',
    email: '',
    password: '',
    confirmPassword: '',
  });

  const handleFormSubmit = (event) => {
    event.preventDefault();
    console.log('Submitted form:', formValues);
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
