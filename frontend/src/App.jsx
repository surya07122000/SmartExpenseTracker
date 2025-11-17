import React from 'react';
import AppRouter from './router/AppRouter';
import { ToastContainer } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";
import ErrorBoundary from './components/common/ErrorBoundary';


const App =() => {
  return (
   <ErrorBoundary>
      <AppRouter />
      <ToastContainer position="top-right" autoClose={3000} />
   </ErrorBoundary>
  );
};

export default App;
