import { Navigate, useRoutes } from 'react-router-dom';
// layouts
import DashboardLayout from './layouts/dashboard';
import SimpleLayout from './layouts/simple';
//
import BlogPage from './pages/BlogPage';
import QnaPage from './pages/QnaPage';
import LoginPage from './pages/LoginPage';
import Page404 from './pages/Page404';
import ProductsPage from './pages/ProductsPage';
import DashboardAppPage from './pages/DashboardAppPage';
import BoardPage from './pages/BoardPage';
import CoardPage from './pages/CoardPage';
import DoardPage from './pages/DoardPage';
import SignUpPage from './pages/SignUpPage';
import EditPage from './pages/EditPage';
import RetryPage from './pages/RetryPage';
// import FetchPage from './pages/FetchPage';



// ----------------------------------------------------------------------
export default function Router() {
  const routes = useRoutes([
    {
      path: '/',
      element: <DashboardLayout />,
      children: [
        { element: <Navigate to="/" />, index: true },
        { path: '/app', element: <DashboardAppPage /> },
        { path: '/QnA', element: <QnaPage /> },
        { path: '/products', element: <ProductsPage /> },
        { path: '/blog', element: <BlogPage /> },
        { path: '/b', element: <BoardPage /> },
      
      ],
    },
    {
      path: '/login',
      element: <LoginPage />,
    },
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
      path: '/re',
      element: <RetryPage />
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
