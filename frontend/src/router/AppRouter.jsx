import { Route, Router, Routes } from "react-router-dom";
import RegisterPage from "../pages/RegisterPage";
import DashboardPage from "../pages/DashboardPage";
// import ReportsPage from "../pages/ReportsPage";
import HomePage from "../pages/HomePage";
import ForgotPassword from "../pages/ForgotPassword ";
import ReportsPage from "../pages/ReportsPage";
import UpdateProfilePage from "../pages/UpdateProfilePage";



const AppRouter = () => {
    return (
        
            <Routes>
                <Route path="/" element={<HomePage />} />
                <Route path="/register" element={<RegisterPage />} />
                <Route path="/dashboard" element={<DashboardPage />} />
                <Route path="/forgot-password" element={<ForgotPassword />} />
                <Route path="/reports" element={<ReportsPage />} />
                <Route path="/update-profile" element={<UpdateProfilePage />} />
            </Routes>
        
    );
};

export default AppRouter;