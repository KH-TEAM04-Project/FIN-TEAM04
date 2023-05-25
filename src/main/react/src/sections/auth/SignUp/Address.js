import React, { useState } from 'react';
import styled from 'styled-components';
import ReactDOM from 'react-dom';
import DaumPostcode from 'react-daum-postcode';

const AddressWrapper = styled.div`
  display: flex;
  align-items: left;
  margin-bottom: 10px;
`;

const AddressInput = styled.input`
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  width: 300px;
`;

const AddressButton = styled.button`
  padding: 8px 12px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 4px;
  margin-left: 10px;
  cursor: pointer;
  margin-right: 10px;
`;

const PopupContainer = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
`;

const PopupContent = styled.div`
  background-color: #fff;
  border: 1px solid #ccc;
  border-radius: 4px;
  padding: 20px;
`;

const Address = ({ onAddressChange }) => {
  const [address, setAddress] = useState('');
  const [isPopupOpen, setIsPopupOpen] = useState(false);

  const handleAddressChange = (event) => {
    const { value } = event.target;
    setAddress(value);
    onAddressChange(value);
  };

  const openPostCode = () => {
    setIsPopupOpen(true);
  };

  const closePostCode = () => {
    setIsPopupOpen(false);
  };

  const handlePostCode = (data) => {
    let fullAddress = data.address;
    let extraAddress = '';

    if (data.addressType === 'R') {
      if (data.bname !== '') {
        extraAddress += data.bname;
      }
      if (data.buildingName !== '') {
        extraAddress += extraAddress !== '' ? `, ${data.buildingName}` : data.buildingName;
      }
      fullAddress += extraAddress !== '' ? ` (${extraAddress})` : '';
    }
    setAddress(fullAddress);
    closePostCode();
  };

  return (
    <div>
      <AddressWrapper>
        <AddressInput
          type="text"
          id="address"
          name="address"
          value={address}
          onChange={handleAddressChange}
          required
          placeholder="주소"
        />
        <AddressButton type="button" onClick={openPostCode}>
          주소 검색
        </AddressButton>
      </AddressWrapper>
      {isPopupOpen && (
        <PopupContainer>
          <PopupContent>
            <PopupPostCode onClose={closePostCode} onComplete={handlePostCode} />
          </PopupContent>
        </PopupContainer>
      )}
    </div>
  );
};

const PopupPostCode = ({ onClose, onComplete }) => {
  return (
    <div>
      <DaumPostcode
        style={{
          display: 'block',
          position: 'absolute',
          top: '10%',
          width: '600px',
          height: '600px',
          padding: '7px',
        }}
        onComplete={onComplete}
      />
      <button type="button" onClick={onClose} className="postCode_btn">
        닫기
      </button>
    </div>
  );
};

const PopupDom = ({ children }) => {
  const el = document.getElementById('popupDom');
  return ReactDOM.createPortal(children, el);
};

export default Address;
