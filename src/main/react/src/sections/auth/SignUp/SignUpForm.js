import PropTypes from 'prop-types';

export default function SignUpForm({ formValues, onFormSubmit, onFormChange }) {
  return (
    
    <form onSubmit={onFormSubmit}>
      <div>
       
        <input
          type="text"
          id="FullName"
          name="FullName"
          value={formValues.FullName}
          onChange={onFormChange}
          required
          placeholder='이름'
        />
      </div>
      <div>
        
        <input
          type="text"
          id="Id"
          name="Id"
          value={formValues.Id}
          onChange={onFormChange}
          required
          placeholder='아이디'
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
    FullName: PropTypes.string.isRequired,
    Id: PropTypes.string.isRequired,
    email: PropTypes.string.isRequired,
    password: PropTypes.string.isRequired,
    confirmPassword: PropTypes.string.isRequired,
  }).isRequired,
  onFormSubmit: PropTypes.func.isRequired,
  onFormChange: PropTypes.func.isRequired,
};