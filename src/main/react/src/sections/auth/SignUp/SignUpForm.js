import PropTypes from 'prop-types';
import styled from 'styled-components';
import Address from './Address';

const StyledForm = styled.form`
  display: flex;
  flex-direction: column;
  align-items: left;
  padding: 20px;
`;

const StyledTitle = styled.h1`
  font-size: 24px;
  margin-bottom: 10px;
`;

const StyledSubtitle = styled.h2`
  font-size: 18px;
  margin-bottom: 20px;
`;

const StyledInputWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin-bottom: 10px;
`;

const StyledInput = styled.input`
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  width: 300px;
`;

const StyledError = styled.span`
  color: red;
  font-size: 14px;
`;

const StyledButton = styled.button`
  padding: 6px 12px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  width: 80px; /* Adjust the width to your desired size */
`;

export default function SignUpForm({ formValues, onFormSubmit, onFormChange }) {
  const { pwd, confirmPassword } = formValues;
  const isPasswordMatch = pwd === confirmPassword;

  // 비밀번호 제한 조건
  const passwordRegex = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
  const isPasswordValid = passwordRegex.test(pwd);

  return (
    <StyledForm onSubmit={onFormSubmit}>
      
      <StyledSubtitle>회원가입</StyledSubtitle>
      <StyledInputWrapper>
        <StyledInput type="text" id="mname" name="mname" value={formValues.mname} onChange={onFormChange} required placeholder='이름'/>
      </StyledInputWrapper>
      <StyledInputWrapper>
        <StyledInput type="text" id="mid" name="mid" value={formValues.mid} onChange={onFormChange} required placeholder='아이디'/>
      </StyledInputWrapper>
      <StyledInputWrapper>
        <StyledInput type="text" id="regno" name="regno" value={formValues.regno} onChange={onFormChange} required placeholder='주민등록번호'/>
      </StyledInputWrapper>
      <StyledInputWrapper>
        <StyledInput type="email" id="email" name="email" value={formValues.email} onChange={onFormChange} required placeholder='이메일'/>
      </StyledInputWrapper>
      <Address /> 
      <StyledInputWrapper>
        
        <StyledInput type="text" id="detailaddress" name="detailaddress" value={formValues.detailaddress} onChange={onFormChange} required placeholder='상세주소'/>
      </StyledInputWrapper>
      <StyledInputWrapper>
        <StyledInput type="text" id="ph" name="ph" value={formValues.ph} onChange={onFormChange} required placeholder='핸드폰 번호'/>
      </StyledInputWrapper>
      <StyledInputWrapper>
        <StyledInput type="password" id="pwd" name="pwd" value={formValues.pwd} onChange={onFormChange} required placeholder='비밀번호'/>
        {!isPasswordValid && <span style={{ color: "red" }}>영문/숫자/특수문자포함 8자이상</span>}
        </StyledInputWrapper>
      <StyledInputWrapper>
        <StyledInput type="password" id="confirmPassword" name="confirmPassword" value={confirmPassword} onChange={onFormChange} required placeholder="비밀번호 확인"/>
        {!isPasswordMatch && <span style={{ color: "red" }}>비밀번호가 일치하지 않습니다.</span>}
        </StyledInputWrapper>
      <StyledButton type="submit">회원가입</StyledButton>
    </StyledForm>
  );
}

SignUpForm.propTypes = {
  formValues: PropTypes.shape({
    mname: PropTypes.string.isRequired,
    mid: PropTypes.string.isRequired,
    regno: PropTypes.string.isRequired,
    email: PropTypes.string.isRequired,
    pwd: PropTypes.string.isRequired,
    detailaddress: PropTypes.string.isRequired, // address 추가
    ph: PropTypes.string.isRequired,
    address: PropTypes.string.isRequired // hp 추가
  }).isRequired,
  onFormSubmit: PropTypes.func.isRequired,
  onFormChange: PropTypes.func.isRequired,
};
