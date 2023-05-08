import React, { useState } from 'react';
import PopupDom from './PopupDom';
import PopupPostCode from './PopupPostCode';

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

  const postCodeStyle = {
    display: "block",
    position: "absolute",
    top: "10%",
    width: "600px",
    height: "600px",
    padding: "7px",
  };

  return(
    <div>
      <input
        type="text"
        id="address"
        name="address"
        value={address}
        onChange={handleAddressChange}
        required
        placeholder='주소'
      />
      <button type='button' onClick={openPostCode}>주소 검색</button>
      <div id='popupDom'>
        {isPopupOpen && (
          <PopupDom>
            <PopupPostCode onClose={closePostCode} onComplete={handlePostCode} />
          </PopupDom>
        )}
      </div>
     
    </div>
  )
};

export default Address;
