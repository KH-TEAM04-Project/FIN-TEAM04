import React, {useState} from 'react';
import axios from 'axios';
import {Avatar, IconButton, Box, Stack, MenuItem, Menu} from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';

function ImageUpload() {
    const DELETE_URL = '/profileDelete';
    const [userData, setUserData] = useState({
        mname: "",
        profilephoto: ""
    });
    const [anchorEl, setAnchorEl] = useState(null);

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
            const formData = new FormData();
            const reader = new FileReader();
            reader.onloadend = async () => {
                const token = localStorage.getItem('accessToken');

                if (token) {
                    // Decode the token and parse the payload
                    const decodedToken = JSON.parse(atob(token.split('.')[1]));

                    formData.append('multipartFile', file);

                    axios
                        .post('/member/Profilephoto', formData, {
                            headers: {
                                Authorization: `Bearer ${token}`
                            }
                        })
                        .then((response) => {
                            const userData = response.data;
                            setUserData(userData);
                            window.location.reload();
                        })
                        .catch((error) => {
                            console.error('Image upload failed:', error);
                        });
                }
            };

            reader.readAsDataURL(file);
        };

        fileInput.click();
    };

    console.log(userData.profilephoto);

    return (
        <div>
                <>
                    <button onClick={handleUploadClick}>이미지 업로드</button>
                </>
        </div>
    );
}

export default ImageUpload;
