import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { updateUser, getProfileData } from "../services/authService";

const UpdateProfilePage = () => {
  const navigate = useNavigate();
  const userEmail = localStorage.getItem("userEmail");
  const userId = localStorage.getItem("userId");

  const [formData, setFormData] = useState({
    id: userId,
    name: "",
    email: userEmail,
    phoneNumber: "",
    role: "",
  });

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        const data = await getProfileData(userEmail);
        setFormData(data); // Pre-fill with existing data
      } catch (error) {
        console.error("Error fetching profile:", error);
      }
    };
    fetchProfile();
  }, [userEmail]);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // ✅ Validation
    const nameRegex = /^[A-Za-z\s]+$/;
    const phoneRegex = /^\d{10}$/;

    if (!nameRegex.test(formData.name)) {
      alert("Name should contain only letters and spaces.");
      return;
    }
    if (!phoneRegex.test(formData.phoneNumber)) {
      alert("Phone number must be 10 digits.");
      return;
    }

    try {
      await updateUser(formData);
      alert("Profile updated successfully!");
      navigate("/dashboard");
    } catch (error) {
      console.error("Update failed:", error);
      alert("Failed to update profile.");
    }
  };

  return (
    <div className="d-flex justify-content-center align-items-center vh-100 bg-light">
      <div className="card shadow p-4" style={{ width: "400px" }}>
        <h3 className="text-center mb-4">Update Profile</h3>
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label">Name</label>
            <input
              type="text"
              className="form-control"
              name="name"
              value={formData.name}
              onChange={handleChange}
              required
            />
          </div>
          <div className="mb-3">
            <label className="form-label">Email (Read Only)</label>
            <input
              type="email"
              className="form-control"
              name="email"
              value={formData.email}
              readOnly
            />
          </div>
          <div className="mb-3">
            <label className="form-label">Phone Number</label>
            <input
              type="tel"
              className="form-control"
              name="phoneNumber"
              value={formData.phoneNumber}
              onChange={handleChange}
              required
            />
          </div>
          <button type="submit" className="btn btn-primary w-100">
            Update
          </button>
        </form>
      </div>
    </div>
  );
};

export default UpdateProfilePage;