import { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import SignUpForm from '../sections/auth/SignUp/SignUpForm';

export default function SignUpPage() {
  const navigate = useNavigate();
  const [formValues, setFormValues] = useState({
    mname: "",
    mid: "",
    regno: "",
    email: "",
    pwd: "",
    detailaddress: "",
    address: "", // Add address field to formValues
    ph: "",
  });

  const handleFormSubmit = (event) => {
    event.preventDefault();
    console.log('Submitted form:', formValues);
    axios.post("/SignUp2", formValues)
      .then(response => {
        console.log(response.data);
        if (response.data === "success") {
          alert("회원가입이 완료되었습니다.");
          navigate('/login');
        } else {
          alert("회원가입에 실패하였습니다. 다시 시도해주세요.");
        }
      })
      .catch(error => {
        console.error(error);
        alert("회원가입 중 오류가 발생하였습니다. 다시 시도해주세요.");
      });
  };

  const handleFormChange = (event) => {
    const { name, value } = event.target;
    setFormValues((prevValues) => ({
      ...prevValues,
      [name]: value,
    }));
  };

  const handleAddressChange = (address) => {
    setFormValues((prevValues) => ({
      ...prevValues,
      address,
    }));
  };

  return (
    <div>
      <h1>꽁 머 니</h1>
      <SignUpForm
        formValues={formValues}
        onFormSubmit={handleFormSubmit}
        onFormChange={handleFormChange}
        onAddressChange={handleAddressChange}
      />
    </div>
  );
}
