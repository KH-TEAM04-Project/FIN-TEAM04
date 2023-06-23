import React, { useState } from 'react';

function ImageUpload({ setPhotoURL }) {
  const [uploadedPhoto, setUploadedPhoto] = useState(null);

  const handlePhotoUpload = (event) => {
    const file = event.target.files[0];
    const reader = new FileReader();

    reader.onloadend = () => {
      setUploadedPhoto(reader.result);
      setPhotoURL(reader.result); // 사진 URL을 AccountPopover로 전달합니다.
    };

    if (file) {
      reader.readAsDataURL(file);
    }
  };

  return (
    <input type="file" accept="image/*" onChange={handlePhotoUpload} />
  );
}

export default ImageUpload;