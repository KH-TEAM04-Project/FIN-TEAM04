import { Navigate, useRoutes } from 'react-router-dom';
// layouts
import DashboardLayout from './layouts/dashboard';
import SimpleLayout from './layouts/simple';
//
import BlogPage from './pages/BlogPage';
import LoginPage from './pages/LoginPage';
import Page404 from './pages/Page404';
import ProductsPage from './pages/ProductsPage';
import DashboardAppPage from './pages/DashboardAppPage';
import SignUpPage from './pages/SignUpPage';
import MyPage from './pages/MyPage';


import PasswordResetModal from './pages/FindPwPage';
import UsernameRecoveryModal from './pages/FindIdPage';

import BoardListPage from './pages/BoardListPage'
import BoardRegistPage from './pages/BoardRegistPage';
import BoardUpdatePage from './pages/BoardUpdatePage';
import BoardDetailPage from './pages/BoardDetailPage';

import QnaListPage from './pages/QnaListPage';
import QnaRegistPage from './pages/QnaRegistPage';
import QnaDetailPage from './pages/QnaDetailPage';
import QnaUpdatePage from './pages/QnaUpdatePage';


// ----------------------------------------------------------------------
export default function Router() {
  const routes = useRoutes([
    {
      path: '/',
      element: <DashboardLayout />,
      children: [

        { element: <Navigate to="/Main" />, index: true },
        { path: '/Main', element: <DashboardAppPage /> },
        { path: '/products', element: <ProductsPage /> },
        { path: '/blog', element: <BlogPage /> },
        { path: '/qna/list', element: <QnaListPage /> },
        { path: '/board/list', element: <BoardListPage/> }

      ],
    },
    {
      path: '/login',
      element: <LoginPage />,
    },
    {

      path: '/login',
      element: <LoginPage />,
    },
    {
      path: '/MyPage',
      element: <MyPage />
    }
    ,
    {

      path: '/SignUp',
      element: <SignUpPage />,
    },
    {
      path: '/IdPw',
      element: <UsernameRecoveryModal />,
    },
    {
      path: '/IdPw2',
      element: <PasswordResetModal />

    },

    {
      path: '/qna/regist',
      element: <QnaRegistPage />,
    },
    {
      path: '/board/update',
      element: <BoardUpdatePage />,
    },
     {
      element: <BoardUpdatePage />,
      path: '/board/update/:bno',
    },
      {
          path: '/qna/update',
          element: <QnaUpdatePage />,
        },
         {
          element: <QnaUpdatePage />,
          path: '/qna/update/:qno',
        },
    {

      element: <BoardDetailPage />,
      path: '/board/detail',
    },
        {

          element: <BoardRegistPage />,
          path: '/board/regist',
        },
    {
      element: <QnaDetailPage />,
      path: '/qna/detail',
    },
        {
          element: <QnaDetailPage />,
          path: '/qna/detail/:qno',
        },
    {
      element: <BoardDetailPage />,
      path: '/board/detail/:bno',
    },

    {

      element: <BoardDetailPage />,
      path: '/boardDetail',

    },
    {
      element: <QnaDetailPage />,
      path: '/qna/detail:qno',
    },
    {
      element: <BoardDetailPage />,
      path: '/board/detail:bno',
    }
    ,
    {
      path: '/board/list',
      element: <QnaListPage />
    },
    {
      path: '/board/detail',
      element: <BoardDetailPage />

    },
    {
      element: <SimpleLayout />,
      children: [
        { element: <Navigate to="/dashboard/app" />, index: true },
        { path: '404', element: <Page404 /> },
        // { path: '*', element: <Navigate to="/404" /> },
      ],
    },
    // {
    //   path: '*',
    //   element: <Navigate to="/404" replace />,
    // },
  ]);

  return routes;
}
