import React from 'react';
import DaumPostcode from 'react-daum-postcode';

const PopupPostCode = (props) => {
  const { onClose, onComplete } = props;

  return (
    <div>
      <DaumPostcode
        style={{
          display: "block",
          position: "absolute",
          top: "10%",
          width: "600px",
          height: "600px",
          padding: "7px",
        }}
        onComplete={onComplete}
      />
      <button type='button' onClick={onClose} className='postCode_btn'>닫기</button>
    </div>
  );
};

export default PopupPostCode;
