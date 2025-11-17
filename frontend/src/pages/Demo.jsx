import React, { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { loginUser } from '../redux/slices/authSlice';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [errors, setErrors] = useState({});
  const [steps, setSteps] = useState([]); // State for helpdesk steps
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { loading, error } = useSelector((state) => state.auth);

  // Fetch steps from API
  useEffect(() => {
    axios.get('http://localhost:8081/helpdesksteps/steps')
      .then((response) => {
        setSteps(response.data); // Assuming API returns an array of strings
      })
      .catch((err) => {
        console.error('Error fetching steps:', err);
      });
  }, []);

  const validateForm = () => {
    const newErrors = {};

    if (!email) {
      newErrors.email = 'Email is required';
    } else if (!/\S+@\S+\.\S+/.test(email)) {
      newErrors.email = 'Please enter a valid email address';
    }

    if (!password) {
      newErrors.password = 'Password is required';
    } else if (password.length < 6) {
      newErrors.password = 'Password must be at least 6 characters long';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!validateForm()) return;
    dispatch(loginUser({ email, password }))
      .unwrap()
      .then((res) => {
        localStorage.setItem("userName", res.name);
        localStorage.setItem("email", res.email);
        if (res.role === 'CUSTOMER') navigate('/customer');
        else if (res.role === 'AGENT') navigate('/agent');
        else if (res.role === 'ADMIN') navigate('/admin');
      })
      .catch((err) => console.error(err));
  };

  return (
    <div className="container vh-100 d-flex align-items-center justify-content-center">
      <div className="row w-100">
        {/* Left Column: Dynamic Helpdesk Steps */}
        <div className="col-md-6 d-flex flex-column justify-content-center p-4">
          <h3 className="mb-4">Steps to Resolve Issues</h3>
          {steps.length > 0 ? (
            <ol className="list-group list-group-numbered">
              {steps.map((step, index) => (
                <li key={index} className="list-group-item">{step}</li>
              ))}
            </ol>
          ) : (
            <p>Loading steps...</p>
          )}
        </div>

        {/* Right Column: Login Box */}
        <div className="col-md-6 d-flex align-items-center justify-content-center">
          <div className="card shadow p-4" style={{ maxWidth: '400px', width: '100%' }}>
            <h3 className="text-center mb-4">Helpdesk Login</h3>
            <form onSubmit={handleSubmit}>
              {/* Email Field */}
              <div className="mb-3">
                <label className="form-label">Email address</label>
                <input
                  type="email"
                  className={`form-control ${errors.email ? 'is-invalid' : ''}`}
                  placeholder="Enter email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                />
                {errors.email && <div className="invalid-feedback">{errors.email}</div>}
              </div>

              {/* Password Field */}
              <div className="mb-3">
                <label className="form-label">Password</label>
                <input
                  type="password"
                  className={`form-control ${errors.password ? 'is-invalid' : ''}`}
                  placeholder="Enter password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                />
                {errors.password && <div className="invalid-feedback">{errors.password}</div>}
              </div>

              {/* Submit Button */}
              <button type="submit" className="btn btn-primary w-100" disabled={loading}>
                {loading ? 'Logging in...' : 'Login'}
              </button>

              {/* Error Message */}
              {error && <div className="alert alert-danger mt-3">{error}</div>}
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
