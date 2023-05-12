import PropTypes from 'prop-types';
import Address from './Address';

export default function SignUpForm({ formValues, onFormSubmit, onFormChange }) {
  const { pwd, confirmPassword } = formValues;
  const isPasswordMatch = pwd === confirmPassword;

  // 비밀번호 제한 조건
  const passwordRegex = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
  const isPasswordValid = passwordRegex.test(pwd);

  return (
    <form onSubmit={onFormSubmit}>
      <div>
        <input type="text" id="mname" name="mname" value={formValues.mname} onChange={onFormChange} required placeholder='이름'/>
      </div>
      <div>
        <input type="text" id="mid" name="mid" value={formValues.mid} onChange={onFormChange} required placeholder='아이디'/>
      </div>
      <div>
        <input type="text" id="regno" name="regno" value={formValues.regno} onChange={onFormChange} required placeholder='주민등록번호'/>
      </div>
      <div>
        <input type="email" id="email" name="email" value={formValues.email} onChange={onFormChange} required placeholder='이메일'/>
      </div>
      <div>
        <Address /> 
        <input type="text" id="detailaddress" name="detailaddress" value={formValues.detailaddress} onChange={onFormChange} required placeholder='상세주소'/>
      </div>
      <div>
        <input type="text" id="ph" name="ph" value={formValues.ph} onChange={onFormChange} required placeholder='핸드폰 번호'/>
      </div>
      <div>
        <input type="password" id="pwd" name="pwd" value={formValues.pwd} onChange={onFormChange} required placeholder='비밀번호'/>
        {!isPasswordValid && <span style={{ color: "red" }}>비밀번호는 8자 이상, 영어/숫자/특수문자가 하나 이상 포함되어야 합니다.</span>}
      </div>
      <div>
        <input type="password" id="confirmPassword" name="confirmPassword" value={confirmPassword} onChange={onFormChange} required placeholder="비밀번호 확인"/>
        {!isPasswordMatch && <span style={{ color: "red" }}>비밀번호가 일치하지 않습니다.</span>}
      </div>
      <button type="submit">Sign Up</button>
    </form>
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
