import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { registerUser } from "../services/authService";

const RegisterPage = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    phoneNumber: "",
    password: "",
  });
  const [message, setMessage] = useState("");

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // ✅ Validation Rules
    const nameRegex = /^[A-Za-z\s]+$/; // Only letters and spaces
    const passwordRegex =
      /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,}$/; // Min 6 chars, 1 letter, 1 number, 1 symbol
    const phoneRegex = /^\d{10}$/; // Exactly 10 digits

    if (!nameRegex.test(formData.name)) {
      setMessage("Name should contain only letters and spaces.");
      return;
    }

    if (!phoneRegex.test(formData.phoneNumber)) {
      setMessage("Phone number must be 10 digits only.");
      return;
    }

    if (!passwordRegex.test(formData.password)) {
      setMessage(
        "Password must be at least 6 characters and include one letter, one number, and one special character."
      );
      return;
    }

    try {
      const response = await registerUser(formData);
      setMessage("Registration successful!");
      console.log("User Registered:", response);
    } catch (error) {
      console.error("Registration failed:", error);
      setMessage("Registration failed! Please try again.");
    }
  };

  return (
    <div className="d-flex justify-content-center align-items-center vh-100 bg-light">
      <div className="card shadow p-4" style={{ width: "400px" }}>
        <h3 className="text-center mb-4">Register</h3>

        {message && (
          <div
            className={`alert ${
              message.includes("successful") ? "alert-success" : "alert-danger"
            }`}
            role="alert"
          >
            {message}
          </div>
        )}

        <form onSubmit={handleSubmit}>
          {/* Name */}
          <div className="mb-3">
            <label className="form-label">Name</label>
            <input
              type="text"
              className="form-control"
              name="name"
              value={formData.name}
              onChange={handleChange}
              required
              pattern="^[A-Za-z\s]+$"
              title="Name should contain only letters and spaces."
            />
          </div>

          {/* Email */}
          <div className="mb-3">
            <label className="form-label">Email</label>
            <input
              type="email"
              className="form-control"
              name="email"
              value={formData.email}
              onChange={handleChange}
              required
            />
          </div>

          {/* Phone Number */}
          <div className="mb-3">
            <label className="form-label">Phone Number</label>
            <input
              type="tel"
              className="form-control"
              name="phoneNumber"
              value={formData.phoneNumber}
              onChange={handleChange}
              required
              pattern="\d{10}"
              title="Phone number must be 10 digits."
            />
          </div>

          {/* Password */}
          <div className="mb-3">
            <label className="form-label">Password</label>
            <input
              type="password"
              className="form-control"
              name="password"
              value={formData.password}
              onChange={handleChange}
              required
              pattern="^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,}$"
              title="Password must be at least 6 characters and include one letter, one number, and one special character."
            />
          </div>

          <button type="submit" className="btn btn-primary w-100">
            Register
          </button>

          <div className="text-center mt-3">
            <button
              type="button"
              className="btn btn-link"
              onClick={() => navigate("/")}
            >
              Proceed to Login
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default RegisterPage;