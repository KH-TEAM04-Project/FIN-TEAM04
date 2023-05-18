// component
import SvgColor from '../../../components/svg-color';

// ----------------------------------------------------------------------

const icon = (name) => <SvgColor src={`/assets/icons/navbar/${name}.svg`} 
sx={{ width: 1, height: 1 }} />;

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
  {
    title: 'login',
    path: '/login',
    icon: icon('ic_lock'),
  },



];

export default navConfig;
