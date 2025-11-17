import { useNavigate } from "react-router-dom";
import Footer from "../components/common/Footer";
import { getAllUsers, loginUser } from "../services/authService";
import { useEffect, useState } from "react";

const HomePage = () => {
  const navigate = useNavigate();
  const [userCount, setUserCount] = useState(0);
  const [feedbacks, setFeedbacks] = useState([]);
  const [loadingFeedback, setLoadingFeedback] = useState(true);
  const [feedbackError, setFeedbackError] = useState("");

  const handleLogin = async (e) => {
    e.preventDefault();
    const email = e.target.email.value;
    const password = e.target.password.value;
    try {
      const data = await loginUser(email, password);
      console.log("✅ Login successful:", data);
      localStorage.setItem("userEmail", data.email);
      localStorage.setItem("jwt", data.jwt);
      localStorage.setItem("userId", data.id);
      navigate("/dashboard");
    } catch (err) {
      console.error("❌ Login failed:", err);
      alert("Invalid credentials. Please try again.");
    }
  };

  // Fetch user count
  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const users = await getAllUsers();
        setUserCount(users.length);
      } catch (error) {
        console.error("Error fetching users:", error);
      }
    };
    fetchUsers();
  }, []);

  // Fetch feedbacks from backend
  useEffect(() => {
    const fetchFeedbacks = async () => {
      try {
        const response = await fetch("http://localhost:8081/quote"); // ✅ Your endpoint
        if (!response.ok) throw new Error("Failed to fetch feedbacks");
        const data = await response.json(); // Expecting a list of strings
        setFeedbacks(data);
      } catch (error) {
        console.error("Error fetching feedbacks:", error);
        setFeedbackError("Could not load feedbacks.");
      } finally {
        setLoadingFeedback(false);
      }
    };
    fetchFeedbacks();
  }, []);

  return (
    <div className="container-fluid vh-100 d-flex flex-column justify-content-between">
      {/* Header */}
      <header className="text-center mt-3">
        <h2 className="fw-bold text-primary">Expense Tracker</h2>
        <p className="text-muted">Manage your income, expenses & borrowed money easily</p>
      </header>

      {/* Main body */}
      <div className="row flex-grow-1 align-items-center justify-content-center">
        {/* Left Login Box */}
        <div className="col-md-4 p-4 border rounded shadow-sm bg-white">
          <h4 className="text-center mb-3">Login</h4>
          <form onSubmit={handleLogin}>
            <div className="mb-3">
              <label className="form-label">Email</label>
              <input type="email" name="email" className="form-control" required />
            </div>
            <div className="mb-3">
              <label className="form-label">Password</label>
              <input type="password" name="password" className="form-control" required />
            </div>
            <button type="submit" className="btn btn-primary w-100">Login</button>
          </form>

          {/* Forgot Password */}
          <div className="text-center mt-2">
            <button
              className="btn btn-link p-0 text-decoration-none"
              onClick={() => navigate("/forgot-password")}
            >
              Forgot Password?
            </button>
          </div>

          {/* Sign Up */}
          <div className="text-center mt-3">
            <p>
              Don’t have an account?{" "}
              <button
                className="btn btn-link p-0 text-decoration-none"
                onClick={() => navigate("/register")}
              >
                Sign up
              </button>
            </p>
          </div>
        </div>

        {/* Right side Info Box */}
        <div className="col-md-6 d-none d-md-block">
          <div className="p-4 border rounded shadow-sm bg-light text-center">
            <h3 className="fw-bold mb-3 text-primary">Welcome to Expense Tracker</h3>
            <p className="text-muted fs-5">
              Track your financial activities, visualize reports, and stay organized — all in one place.
            </p>
            <hr />
            <h4 className="text-success">Trusted by {userCount}+ Customers</h4>

            {/* Dynamic Feedback */}
            {loadingFeedback ? (
              <p className="text-secondary">Loading feedback...</p>
            ) : feedbackError ? (
              <p className="text-danger">{feedbackError}</p>
            ) : (
              feedbacks.map((fb, index) => (
                <p key={index} className="fst-italic">{fb}</p>
              ))
            )}
          </div>
        </div>
      </div>

      {/* Footer */}
      <Footer />
    </div>
  );
};

export default HomePage;