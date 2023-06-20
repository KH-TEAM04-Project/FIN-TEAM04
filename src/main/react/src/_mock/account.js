// ----------------------------------------------------------------------
const token = localStorage.getItem('accessToken');
const sub = token ? JSON.parse(atob(token.split('.')[1])).sub : '';
console.log(sub);
const account = {
  displayName: sub ,
  email: '님 환영합니다.',
  photoURL: '/assets/images/avatars/avatar_default.jpg',
};

export default account;
