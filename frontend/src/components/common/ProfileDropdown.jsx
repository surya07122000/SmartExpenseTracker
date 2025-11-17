import { useNavigate } from "react-router-dom";

const ProfileDropdown = ({ profileData, onLogout }) => {
    const navigate = useNavigate();

    return (
        <div
            className="dropdown-menu show position-absolute end-0 mt-2 p-3 shadow"
            style={{ width: "250px" }}
        >
            <h6 className="dropdown-header">Profile</h6>
            <p className="mb-1"><strong>Name:</strong> {profileData.name}</p>
            <p className="mb-1"><strong>Email:</strong> {profileData.email}</p>
            <p className="mb-2"><strong>Phone:</strong> {profileData.phoneNumber}</p>

            {/* ✅ Update Profile Button */}
            <button
                type="button"
                className="btn btn-outline-primary w-100 mb-2"
                onClick={() => navigate("/update-profile")}
            >
                Update Profile
            </button>

            {/* ✅ Logout Button */}
            <button
                type="button"
                className="btn btn-danger w-100"
                onClick={onLogout}
            >
                Logout
            </button>
        </div>
    );
};

export default ProfileDropdown;