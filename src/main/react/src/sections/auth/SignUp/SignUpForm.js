import PropTypes from 'prop-types';

export default function SignUpForm({ formValues, onFormSubmit, onFormChange }) {
  return (
    
    <form onSubmit={onFormSubmit}>
      <div>
       
        <input
          type="text"
          id="fullname"
          name="fullname"
          value={formValues.fullname}
          onChange={onFormChange}
          required
          placeholder='이름'
        />
      </div>
      <div>
        
        <input
          type="text"
          id="id"
          name="id"
          value={formValues.id}
          onChange={onFormChange}
          required
          placeholder='아이디'
        />
      </div>
      <div>
      <input
          type="text"
          id="ssn"
          name="ssn"
          value={formValues.ssn}
          onChange={onFormChange}
          required
          placeholder='주민등록번호'
        />
      </div>
      <div>
       
        <input
          type="text"
          id="address"
          name="address"
          value={formValues.address}
          onChange={onFormChange}
          required
          placeholder='주소'
        />
      </div>
      <div>
        
        <input
          type="text"
          id="ph"
          name="ph"
          value={formValues.ph}
          onChange={onFormChange}
          required
          placeholder='전화번호'
        />
      </div>
      <div>
        
        <input
          type="email"
          id="email"
          name="email"
          value={formValues.email}
          onChange={onFormChange}
          required
          placeholder='이메일'
        />
      </div>
      <div>
        
        <input
          type="password"
          id="password"
          name="password"
          value={formValues.password}
          onChange={onFormChange}
          required
          placeholder='비밀번호'
        />
      </div>
      <div>
        
        <input
          type="password"
          id="confirmPassword"
          name="confirmPassword"
          value={formValues.confirmPassword}
          onChange={onFormChange}
          required
          placeholder='비밀번호 확인'
        />
      </div>
      <button type="submit">Sign Up</button>
    </form>
  );
}

SignUpForm.propTypes = {
  formValues: PropTypes.shape({
    fullname: PropTypes.string.isRequired,
    id: PropTypes.string.isRequired,
    ssn: PropTypes.string.isRequired,
    adrress: PropTypes.string.isRequired,
    ph:PropTypes.string.isRequired,
    email: PropTypes.string.isRequired,
    password: PropTypes.string.isRequired,
    confirmPassword: PropTypes.string.isRequired,
    
  }).isRequired,
  onFormSubmit: PropTypes.func.isRequired,
  onFormChange: PropTypes.func.isRequired,
};
