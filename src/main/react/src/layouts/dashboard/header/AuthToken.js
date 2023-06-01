import axios from 'axios';

const AuthToken = async (url, method = 'get', data = null) {
  const accessToken = localStorage.getItem('accessToken');
  const response = await axios({
    method: method,
    url,
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
    data: data,
  });

  return response.data;
};

export default AuthToken;
