import { Navigate, useRoutes } from 'react-router-dom';
// layouts
import DashboardLayout from './layouts/dashboard';
import SimpleLayout from './layouts/simple';
//
import BlogPage from './pages/BlogPage';
import UserPage from './pages/UserPage';
import LoginPage from './pages/LoginPage';
import Page404 from './pages/Page404';
import ProductsPage from './pages/ProductsPage';
import DashboardAppPage from './pages/DashboardAppPage';
<<<<<<< HEAD

=======
import BoardPage from './pages/BoardPage';
import CoardPage from './pages/CoardPage';
import DoardPage from './pages/DoardPage';
import EoardPage from './pages/EoardPage';
import SignUpPage from './pages/SignUpPage';
import EditPage from './pages/EditPage';
import RetryPage from './pages/RetryPage';
// import FetchPage from './pages/FetchPage';
import BoardReadPage from './pages/BoardReadPage';
import MyPage from './pages/MyPage';
import QnaReadPage from './pages/QnaReadPage';
>>>>>>> parent of 2234eb0 (회원삭제폼 추가)

// ----------------------------------------------------------------------

export default function Router() {
  const routes = useRoutes([
    {
      path: '/dashboard',
      element: <DashboardLayout />,
      children: [
        { element: <Navigate to="/dashboard/app" />, index: true },
        { path: 'app', element: <DashboardAppPage /> },
        { path: 'user', element: <UserPage /> },
        { path: 'products', element: <ProductsPage /> },
        { path: 'blog', element: <BlogPage /> },
      ],
    },
    {
      path: '/login',
      element: <LoginPage />,
    },
    {
<<<<<<< HEAD
      path: '/login',
      element: <LoginPage />,
=======
      path: '/MyPage',
      element: <MyPage />
    }
    ,
    {
      path: '/SignUp',
      element: <SignUpPage />,
    },

    {
      path: '/Coardpage',
      element: <CoardPage />,
    },
    {
      path: '/Doardpage',
      element: <DoardPage />,
    },
    {
      path: '/EditPage',
      element: <EditPage />,
    },
    {
      element: <BoardReadPage />,
      path: '/BoardReadPage',

    },
    {
      element: <QnaReadPage />,
      path: '/QnaReadPage:qno',
    },
    {
      element: <BoardReadPage />,
      path: '/BoardReadPage:bno',
>>>>>>> parent of 2234eb0 (회원삭제폼 추가)
    },
    {
      element: <SimpleLayout />,
      children: [
        { element: <Navigate to="/dashboard/app" />, index: true },
        { path: '404', element: <Page404 /> },
        { path: '*', element: <Navigate to="/404" /> },
      ],
    },
    {
      path: '*',
      element: <Navigate to="/404" replace />,
    },
  ]);

  return routes;
}
