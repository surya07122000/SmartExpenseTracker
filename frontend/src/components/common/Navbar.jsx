import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { logoutUser, getProfileData } from "../../services/authService";
import ProfileDropdown from "./ProfileDropdown";

const Navbar = () => {
    const navigate = useNavigate();
    const [showProfile, setShowProfile] = useState(false);
    let hideTimeout;
    const [profileData, setProfileData] = useState(null);
    const userEmail = localStorage.getItem("userEmail");

    useEffect(() => {
        if (userEmail) {
            const fetchProfile = async () => {
                try {
                    const data = await getProfileData(userEmail);
                    setProfileData(data);
                } catch (error) {
                    console.error("Error fetching profile:", error);
                }
            };
            fetchProfile();
        }
    }, [userEmail]);

    const handleLogout = async () => {
        await logoutUser();
        localStorage.clear();
        navigate("/");
    };

    return (
        <nav className="navbar navbar-expand-lg navbar-light bg-light px-3">
            <a className="navbar-brand" href="#">Expense Tracker</a>
            <div className="ms-auto position-relative"
                onMouseEnter={() => {
                    clearTimeout(hideTimeout); // cancel any pending hide
                    setShowProfile(true);
                }}
                onMouseLeave={() => {
                    hideTimeout = setTimeout(() => setShowProfile(false), 300); // delay hide by 300ms
                }}
                style={{ cursor: "pointer" }}
            >
                <span className="fw-bold">{userEmail}</span>
                {showProfile && profileData && (
                    <div className="position-absolute end-0 mt-2">
                        <ProfileDropdown profileData={profileData} onLogout={handleLogout} />
                    </div>
                )}
            </div>
        </nav>
    );
};

export default Navbar;