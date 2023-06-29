import React, { useState, useEffect } from 'react';
import { Button, Form } from 'react-bootstrap';
import axios from 'axios';
import swal from 'sweetalert';
import { useNavigate } from 'react-router-dom';

const PasswordResetModal = () => {
  const [showModal, setShowModal] = useState(true);
  const [mname, setUserName] = useState('');
  const [email, setUserEmail] = useState('');
  const [errorMsg, setErrorMsg] = useState('');

  const navigate = useNavigate();

  useEffect(() => {
    document.body.style.overflow = 'hidden'; // Disable scrolling when the modal is open
    return () => {
      document.body.style.overflow = 'unset'; // Enable scrolling when the modal is closed
    };
  }, []);

  const closeModal = () => {
    setShowModal(false);
  };

  const handleUserNameChange = (e) => {
    setUserName(e.target.value);
  };

  const handleUserEmailChange = (e) => {
    setUserEmail(e.target.value);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    const requestData = {
      mname,
      email,
    };

    console.log(requestData);
    axios
      .get('/auth/check/findPw', { params: requestData })
      .then((response) => {
        const res = response.data;
        if (res.check) {
          swal('발송 완료!', '입력하신 이메일로 임시비밀번호가 발송되었습니다.', 'success').then((OK) => {
            if (OK) {
              axios
                .post('/auth/check/findPw/sendEmail', null, { params: requestData })
                .then(() => {
                  window.location = '/login';
                })
                .catch((error) => {
                  console.error(error);
                  setErrorMsg('비밀번호 재설정 이메일 발송에 실패했습니다.');
                });
            }
          });
          setErrorMsg('');
        } else {
          setErrorMsg('일치하는 정보가 없습니다.');
        }
      })
      .catch((error) => {
        console.error(error);
        setErrorMsg('비밀번호 재설정 이메일 발송에 실패했습니다.');
      });
  };

  const handleCancel = () => {
    navigate(-1); // Navigate back to the previous page
  };

  return (
    <div className="container">
      {showModal && (
        <div className="modal" style={{ display: 'block' }}>
          <div className="modal-dialog" style={{ maxWidth: '500px', margin: '150px auto' }}>
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">비밀번호 찾기</h5>
                <button type="button" className="close" onClick={closeModal}>
                  <span>&times;</span>
                </button>
              </div>
              <div className="modal-body">
                <div style={{ color: '#ac2925', textAlign: 'center' }}>
                  입력된 정보로 임시 비밀번호가 전송됩니다.
                </div>
                <hr />
                <Form onSubmit={handleSubmit}>
                  <Form.Group>
                    <Form.Label>이메일</Form.Label>
                    <Form.Control
                      type="text"
                      placeholder="가입 시 등록한 이메일을 입력하세요."
                      value={email}
                      onChange={handleUserEmailChange}
                      style={{ fontSize: '16px', padding: '10px' }}
                    />
                  </Form.Group>
                  <Form.Group>
                    <Form.Label>이름</Form.Label>
                    <Form.Control
                      type="text"
                      placeholder="가입 시 등록한 이름을 입력하세요."
                      value={mname}
                      onChange={handleUserNameChange}
                      style={{ fontSize: '16px', padding: '10px' }}
                    />
                  </Form.Group>
                  <Button type="submit" variant="success" block style={{ fontSize: '18px', padding: '10px 20px' }}>
                    OK
                  </Button>
                </Form>
                <hr />
                <div className="text-center small mt-2" style={{ color: 'red', fontSize: '16px' }}>
                  {errorMsg}
                </div>
              </div>
              <div className="modal-footer">
                <Button variant="danger" onClick={handleCancel} style={{ backgroundColor: '#dc3545', color: '#fff', border: 'none', fontSize: '20px', padding: '10px 20px' }}>
                  취소
                </Button>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default PasswordResetModal;
