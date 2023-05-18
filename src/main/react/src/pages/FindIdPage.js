import React, { useState } from 'react';
import { Button, Form, Modal } from 'react-bootstrap';
import axios from 'axios';
import Swal from 'sweetalert2';

const UsernameRecoveryModal = () => {
  const [showModal, setShowModal] = useState(false);
  const [mname, setMname] = useState(''); // Changed userName to mname
  const [email, setEmail] = useState(''); // Changed userEmail to email
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

  const handleMnameChange = (e) => { // Changed handleUserNameChange to handleMnameChange
    setMname(e.target.value);
  };

  const handleEmailChange = (e) => { // Changed handleUserEmailChange to handleEmailChange
    setEmail(e.target.value);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    const requestData = {
      email, // Changed userEmail to email
      mname, // Changed userName to mname
    };

    console.log('Request Data:', requestData);

    axios
      .post('/check/findID', requestData)
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
                value={mname} // Changed userName to mname
                onChange={handleMnameChange} // Changed handleUserNameChange to handleMnameChange
              />
            </Form.Group>
            <Form.Group>
              <Form.Label>이메일</Form.Label>
              <Form.Control
                type="email"
                placeholder="가입 시 등록한 이메일을 입력하세요."
                value={email} // Changed userEmail to email
                onChange={handleEmailChange} // Changed handleUserEmailChange to handleEmailChange
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
