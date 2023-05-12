import axios from 'axios';

axios.get('http://example.com/api/some-data', {
  headers: {
    Authorization: `Bearer ${token}`, // 토큰값
  },
})
  .then((response) => {
    // 요청이 성공했을 때의 처리
    console.log(response.data);
  })
  .catch((error) => {
    // 요청이 실패했을 때의 처리
    console.error(error);
  });
