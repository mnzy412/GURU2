import { Toaster } from 'react-hot-toast';
import AuthProvider from './context/AuthProvider';
import Router from './routes/routers';
import './styles/global.css';
import "@/styles/calendar.css";

export default function App() {

  return (
    <>
      <AuthProvider>
        <Router />
      </AuthProvider>
      <Toaster />
    </>
  );
}
