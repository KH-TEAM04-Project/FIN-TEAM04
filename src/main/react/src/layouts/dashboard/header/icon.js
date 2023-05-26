import React, { useState } from 'react';

const IconButton = () => {
  const [photoURL, setPhotoURL] = useState('/assets/images/avatars/avatar_default.jpg');
  
  const icons = [
    '/assets/images/avatars/icon_1.jpg',
    '/assets/images/avatars/icon_2.jpg',
    '/assets/images/avatars/icon_3.jpg',
  ];

  const changeIcon = () => {
    const randomIndex = Math.floor(Math.random() * icons.length);
    setPhotoURL(icons[randomIndex]);
  };

  const item = {
    label: 'icon',
    photoURL: photoURL,
    onClick: changeIcon,
  };

  const account = {
    displayName: '로그인해주세요',
    email: 'demo@minimals.cc',
    photoURL: item.photoURL,
  };

  return (
    <div>
      <img
        src={account.photoURL}
        alt={account.displayName}
        onClick={item.onClick}
        style={{ cursor: 'pointer' }}
      />
      <p>{account.displayName}</p>
      <p>{account.email}</p>
    </div>
  );
};

export default IconButton;
