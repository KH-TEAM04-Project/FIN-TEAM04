import SvgColor from '../../../components/svg-color';

// ----------------------------------------------------------------------
const token = localStorage.getItem('accessToken');
const sub = token ? JSON.parse(atob(token.split('.')[1])).sub : '';
console.log(sub);

const icon = (sub) => (
  <SvgColor src={`/assets/icons/navbar/${sub}.svg`} sx={{ width: 1, height: 1 }} />
);

const navConfig = [
  {
    title: 'dashboard',
    path: '/Main',
    icon: icon('ic_analytics'),
  },
  {
    title: '게 시 판',
    path: '/board/list',
    icon: icon('ic_cart'),
  },
  {
    title: 'Q & A',
    path: '/qna/list',
    icon: icon('ic_cart'),
  },
  {
    title: 'product',
    path: '/products',
    icon: icon('ic_cart'),
  },
  {
    title: 'blog',
    path: '/blog',
    icon: icon('ic_blog'),
  },
  // 조건부 렌더링을 통해 로그인 상태에 따라 'Login' 메뉴를 표시하지 않음
  {
    title: 'login',
    path: '/login',
    icon: icon('ic_lock'),
    // isLogin 변수에 따라 메뉴 표시 여부 결정
    isLogin: !localStorage.getItem('accessToken'), // 토큰이 없는 경우에만 표시
  },
  {
    title: 'tax',
    path: '/tax',
    icon: icon('ic_tax'),
    
  },
].filter((item) => {
  // 로그인 상태에 따라 'Login' 메뉴 필터링
  return !(item.title === 'login' && !item.isLogin);
});

export default navConfig;
