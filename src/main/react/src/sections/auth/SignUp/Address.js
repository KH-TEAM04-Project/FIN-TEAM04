import React, { useState } from 'react';
import styled from 'styled-components';
import PopupDom from './PopupDom';
import PopupPostCode from './PopupPostCode';

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
  margin-right: 10px; /* 주소 입력칸과 오른쪽에 공간을 주기 위한 우측 마진 추가 */
`;


const Address = () => {
  // 주소 상태 관리
  const [address, setAddress] = useState('');

  // 팝업창 상태 관리
  const [isPopupOpen, setIsPopupOpen] = useState(false);

  // 주소 입력 시
  const handleAddressChange = (event) => {
    setAddress(event.target.value);
  };

  // 팝업창 열기
  const openPostCode = () => {
    setIsPopupOpen(true);
  };

  // 팝업창 닫기
  const closePostCode = () => {
    setIsPopupOpen(false);
  };

  // 우편번호 검색 후 주소 처리 함수
  const handlePostCode = (data) => {
    let fullAddress = data.address;
    let extraAddress = '';

    if (data.addressType === 'R') {
      if (data.bname !== '') {
        extraAddress += data.bname;
      }
      if (data.buildingName !== '') {
        extraAddress += (extraAddress !== '' ? `, ${data.buildingName}` : data.buildingName);
      }
      fullAddress += (extraAddress !== '' ? ` (${extraAddress})` : '');
    }
    setAddress(fullAddress);
    closePostCode();
  };

  return(
    <div>
      <AddressWrapper>
        <AddressInput
          type="text"
          id="address"
          name="address"
          value={address}
          onChange={handleAddressChange}
          required
          placeholder='주소'
        />
        <AddressButton type='button' onClick={openPostCode}>주소 검색</AddressButton>
      </AddressWrapper>
      <div id='popupDom'>
        {isPopupOpen && (
          <PopupDom>
            <PopupPostCode onClose={closePostCode} onComplete={handlePostCode} />
          </PopupDom>
        )}
      </div>
    </div>
  );
};

export default Address;
