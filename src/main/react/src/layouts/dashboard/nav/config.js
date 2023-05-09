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
    title: '공지사항',
    path: '/board',
    icon: icon('notice'),
  },
  {
    title: 'QNA',
    path: '/QnA',
    icon: icon('qna'),
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
  {
    title: 'Not found',
    path: '/404',
    icon: icon('ic_disabled'),
  },
  {
    title: 'EoardPage',
    path: '/EoardPage',
    icon: icon('ic_cart'),

  },
  {
    title: 'retryPage',
    path: '/re',
    icon: icon('ic_cart'),

  },
];

export default navConfig;
