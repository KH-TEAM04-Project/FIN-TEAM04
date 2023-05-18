import React, { useState } from 'react';
import { Button, Form, Modal } from 'react-bootstrap';
import axios from 'axios';

const UsernameRecoveryModal = () => {
  const [showModal, setShowModal] = useState(false);
  const [userName, setUserName] = useState('');
  const [userEmail, setUserEmail] = useState('');
  const [retrievedUsername, setRetrievedUsername] = useState('');
  const [errorMsg, setErrorMsg] = useState('');

  const openModal = () => {
    setShowModal(true);
  };

  const closeModal = () => {
    setShowModal(false);
    setUserName('');
    setUserEmail('');
    setRetrievedUsername('');
    setErrorMsg('');
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

    axios
      .post('/check/findID', null, {params: requestData})
      .then((response) => {
        const res = response.data;
        if (res.success) {
          setRetrievedUsername(res.username);
          setErrorMsg('');
        } else {
          setRetrievedUsername('');
          setErrorMsg(res.message);
        }
      })
      .catch((error) => {
        console.error(error);
        setRetrievedUsername('');
        setErrorMsg('Failed to retrieve username. Please try again later.');
      });
  };

  return (
    <>
      <Button onClick={openModal}>아이디 찾기</Button>
      <Modal show={showModal} onHide={closeModal}>
        <Modal.Header closeButton>
          <Modal.Title>아이디 찾기</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form onSubmit={handleSubmit}>
            <Form.Group>
              <Form.Label>이름</Form.Label>
              <Form.Control
                type="text"
                placeholder="가입 시 등록한 이름을 입력하세요."
                value={userName}
                onChange={handleUserNameChange}
              />
            </Form.Group>
            <Form.Group>
              <Form.Label>이메일</Form.Label>
              <Form.Control
                type="email"
                placeholder="가입 시 등록한 이메일을 입력하세요."
                value={userEmail}
                onChange={handleUserEmailChange}
              />
            </Form.Group>
            <Button type="submit" variant="success" block>
              아이디 찾기
            </Button>
          </Form>
          {retrievedUsername && (
            <div className="text-center mt-3">
              <strong>아이디:</strong> {retrievedUsername}
            </div>
          )}
          {errorMsg && (
            <div className="text-center mt-3 text-danger">
              {errorMsg}
            </div>
          )}
        </Modal.Body>
        <Modal.Footer>
          <Button variant="danger" onClick={closeModal}>
            닫기
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};

export default UsernameRecoveryModal;
