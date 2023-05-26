// ImageUpload.js
import React from 'react';
import axios from 'axios';
import { Avatar, IconButton, Box, Stack, MenuItem, Menu } from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';

// 사용하려는 백엔드 엔드포인트를 변경하세요.
const UPLOAD_URL = '/profileUpload'; 
const DELETE_URL = '/profileDelete';

const ImageUpload = ({ avatarSrc, setAvatarSrc }) => {
  const [anchorEl, setAnchorEl] = React.useState(null);

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleUploadClick = async (event) => {
    const fileInput = document.createElement('input');
    fileInput.type = 'file';
    fileInput.accept = 'image/*';
    fileInput.onchange = async (e) => {
      const file = e.target.files[0];
      const reader = new FileReader();
      reader.onloadend = async () => {
        try {
          const formData = new FormData();
          formData.append('image', file);

          const response = await axios.post(UPLOAD_URL, formData, {
            headers: {
              'Content-Type': 'multipart/form-data',
            },
          });
        
          if (response.status === 200) {
            // 백엔드 엔드포인트로부터 제공받은 이미지 URL 사용.
            setAvatarSrc(response.data.imageUrl);
          } else {
            console.error('Image upload failed:', response);
          }

        } catch (error) {
          console.error('Image upload failed:', error);
        }
      };
      reader.readAsDataURL(file);
    };

    fileInput.click();
  };

  const handleDeleteClick = async () => {
    try {
      const response = await axios.delete(DELETE_URL, {
        // 백엔드에 전달할 데이터를 함께 전송.
        data: { imageUrl: avatarSrc },
      });
      
      if (response.status === 200) {
        // 이미지 삭제에 성공하면 빈 이미지로 설정.
        setAvatarSrc(null);
      } else {
        console.error('Image deletion failed:', response);
      }

    } catch (error) {
      console.error('Image deletion failed:', error);
    }
  };

  return (
    <Box sx={{ position: 'relative', display: 'inline-block' }}>
      <Avatar
        src={avatarSrc}
        alt="Uploaded Image"
        sx={{ width: 128, height: 128, borderRadius: '50%' }}
      />
      <IconButton
        onClick={handleClick}
        sx={{ position: 'absolute', bottom: 0, right: 0 }}
      >
        <EditIcon />
      </IconButton>
      <Menu
        anchorEl={anchorEl}
        open={Boolean(anchorEl)}
        onClose={handleClose}
      >
        <Stack spacing={1} direction="column">
          <MenuItem onClick={handleUploadClick}>사진 업로드</MenuItem>
          <MenuItem onClick={handleDeleteClick}>사진 삭제</MenuItem>
        </Stack>
      </Menu>
    </Box>
  );
};

export default ImageUpload;
