import React, { useState } from 'react';
import { Button, Form, Modal } from 'react-bootstrap';
import axios from 'axios';
import Swal from 'sweetalert2';

const UsernameRecoveryModal = () => {
  const [showModal, setShowModal] = useState(false);
  const [mname, setMname] = useState('');
  const [email, setEmail] = useState('');
  const [retrievedUsername, setRetrievedUsername] = useState('');
  const [errorMsg, setErrorMsg] = useState('');

  const openModal = () => {
    setShowModal(true);
  };

  const closeModal = () => {
    setShowModal(false);
    setMname('');
    setEmail('');
    setRetrievedUsername('');
    setErrorMsg('');
  };

  const handleMnameChange = (e) => {
    setMname(e.target.value);
  };

  const handleEmailChange = (e) => {
    setEmail(e.target.value);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    const requestData = {
      email,
      mname,
    };

    axios
      .post('/auth/check/findID', requestData)
      .then((response) => {
        const res = response.data;
        if (res) {
          Swal.fire({
            icon: 'success',
            title: '아이디 찾기 성공',
            text: `아이디: ${res}`,
          });
          setRetrievedUsername(res);
          setErrorMsg('');
        } else {
          Swal.fire({
            icon: 'error',
            title: '아이디 찾기 실패',
            text: '아이디를 찾을 수 없습니다.',
          });
          setRetrievedUsername('');
          setErrorMsg('아이디를 찾을 수 없습니다.');
        }
      })
      .catch((error) => {
        console.error(error);
        Swal.fire({
          icon: 'error',
          title: '아이디 찾기 실패',
          text: '아이디를 검색하는 중에 오류가 발생했습니다. 나중에 다시 시도해주세요.',
        });
        setRetrievedUsername('');
        setErrorMsg('아이디를 검색하는 중에 오류가 발생했습니다.');
      });
  };

  return (
    <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
      <Button onClick={openModal} style={{ backgroundColor: '#007bff', color: '#fff', border: 'none', fontSize: '20px', padding: '10px 20px' }}>
        아이디 찾기
      </Button>
      <Modal show={showModal} onHide={closeModal} centered>
        <Modal.Header closeButton>
          <Modal.Title style={{ fontSize: '24px' }}>아이디 찾기</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form onSubmit={handleSubmit}>
            <Form.Group>
              <Form.Label style={{ fontSize: '18px' }}>이름</Form.Label>
              <Form.Control
                type="text"
                placeholder="가입 시 등록한 이름을 입력하세요."
                value={mname}
                onChange={handleMnameChange}
                style={{ border: '1px solid #ced4da', borderRadius: '4px', fontSize: '16px', padding: '10px' }}
              />
            </Form.Group>
            <Form.Group>
              <Form.Label style={{ fontSize: '18px' }}>이메일</Form.Label>
              <Form.Control
                type="email"
                placeholder="가입 시 등록한 이메일을 입력하세요."
                value={email}
                onChange={handleEmailChange}
                style={{ border: '1px solid #ced4da', borderRadius: '4px', fontSize: '16px', padding: '10px' }}
              />
            </Form.Group>
            <Button type="submit" variant="success" block style={{ fontSize: '18px', padding: '10px 20px' }}>
              아이디 찾기
            </Button>
          </Form>
          {retrievedUsername && (
            <div className="text-center mt-3" style={{ color: '#000', fontSize: '18px' }}>
              <strong>아이디:</strong> {retrievedUsername}
            </div>
          )}
          {errorMsg && (
            <div className="text-center mt-3 text-danger" style={{ fontSize: '18px' }}>
              {errorMsg}
            </div>
          )}
        </Modal.Body>
        <Modal.Footer>
          <Button variant="danger" onClick={closeModal} style={{ backgroundColor: '#dc3545', color: '#fff', border: 'none', fontSize: '20px', padding: '10px 20px' }}>
            닫기
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default UsernameRecoveryModal;
