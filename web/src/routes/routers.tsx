import { ROUTE_URLS } from '@/constants/route.ts';
import React, { Suspense, lazy } from 'react';
import { Route, Routes } from 'react-router';
import { BrowserRouter } from 'react-router-dom';

const BookSearch = lazy(() => import('@/pages/book-search'));
const BookEdit = lazy(() => import('@/pages/book-edit'));

const COMMON_ROUTER = [
    {
        path: ROUTE_URLS.bookSearch,
        element: <BookSearch />,
    },
    {
        path: ROUTE_URLS.bookEdit,
        element: <BookEdit />,
    },
];

const Router: React.FC = () => {

    return (
        <BrowserRouter>
            <Suspense fallback={<div className='bg-[#EFEBE5]'></div>}>
                <Routes>
                    {
                        COMMON_ROUTER.map(route => <Route key={route.path} {...route} />)
                    }
                </Routes>
            </Suspense>
        </BrowserRouter>
    );
};

export default Router;