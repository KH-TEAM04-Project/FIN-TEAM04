import React, { useState } from 'react';
import { Button, Form } from 'react-bootstrap';
import axios from 'axios';
import swal from 'sweetalert';

const PasswordResetModal = () => {
  const [showModal, setShowModal] = useState(false);
  const [userName, setUserName] = useState('');
  const [userEmail, setUserEmail] = useState('');
  const [errorMsg, setErrorMsg] = useState('');

  const openModal = () => {
    setShowModal(true);
  };

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
      userName,
      userEmail,
    };

    console.log(requestData);
    axios
      .get('/check/findPw', { params: requestData })
      .then((response) => {
        const res = response.data;
        if (res.check) {
          swal('발송 완료!', '입력하신 이메일로 임시비밀번호가 발송되었습니다.', 'success').then((OK) => {
            if (OK) {
              axios
                .post('/check/findPw/sendEmail', null, { params: requestData })
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

  return (
    <div className="container">
      <Button onClick={openModal}>비밀번호 찾기</Button>
      {showModal && (
        <div className="modal" style={{ display: 'block', backgroundColor: 'rgba(0, 0, 0, 0.5)' }}>
          <div className="modal-dialog">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">비밀번호 찾기</h5>
                <button type="button" className="close" onClick={closeModal}>
                  <span>&times;</span>
                </button>
              </div>
              <div className="modal-body">
                <div style={{ color: '#ac2925' }}>
                  <center>입력된 정보로 임시 비밀번호가 전송됩니다.</center>
                </div>
                <hr />
                <Form onSubmit={handleSubmit}>
                  <Form.Group>
                    <Form.Label>이메일</Form.Label>
                    <Form.Control
                      type="text"
                      placeholder="가입 시 등록한 이메일을 입력하세요."
                      value={userEmail}
                      onChange={handleUserEmailChange}
                    />
                  </Form.Group>
                  <Form.Group>
                    <Form.Label>이름</Form.Label>
                    <Form.Control
                      type="text"
                      placeholder="가입 시 등록한 이름을 입력하세요."
                      value={userName}
                      onChange={handleUserNameChange}
                    />
                  </Form.Group>
                  <Button type="submit" variant="success" block>
                    OK
                  </Button>
                </Form>
                <hr />
                <div className="text-center small mt-2" style={{ color: 'red' }}>
                  {errorMsg}
                </div>
              </div>
              <div className="modal-footer">
                <Button variant="danger" onClick={closeModal}>
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
