import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Header from './Header';
import Footer from './Footer';
import '../styles/Signup.css';
import logo from '../assets/logo.JPG';

const Signup = () => {
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    confirmPassword: '',
  });
  const [errors, setErrors] = useState({});
  const [successMessage, setSuccessMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate(); 

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const validate = () => {
    const newErrors = {};
    if (!formData.username) newErrors.username = 'Username is required';
    if (!formData.email) newErrors.email = 'Email is required';
    if (!formData.password) newErrors.password = 'Password is required';
    if (formData.password !== formData.confirmPassword)
      newErrors.confirmPassword = 'Passwords do not match';
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSuccessMessage('');
    setErrorMessage('');
    if (validate()) {
      try {
        const response = await fetch('http://localhost:8080/api/auth/signup', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            username: formData.username,
            email: formData.email,
            password: formData.password,
            roles: ["user"]
          })
        });

        const data = await response.json();

        if (response.ok) {
          setSuccessMessage('Registration successful! Redirecting to login...');
          setFormData({
            username: '',
            email: '',
            password: '',
            confirmPassword: '',
          });

          // Re-directs to Login page after 3 seconds
          setTimeout(() => navigate('/login'), 3000);
        } else {
          setErrorMessage(data.message || 'Registration failed.');
        }
      } catch (error) {
        setErrorMessage('An error occurred. Please try again later.');
      }
    }
  };

  return (
    <div className="signup-wrapper">
      <Header />
      <main>
        <div className="signup-container">
          <img src={logo} className="logo" alt="Logo" />
          <form className="signup-form" onSubmit={handleSubmit}>
            <h2>Sign Up</h2>
            {successMessage && <p className="success">{successMessage}</p>}
            {errorMessage && <p className="error">{errorMessage}</p>}
            <input
              type="text"
              name="username"
              placeholder="Username"
              value={formData.username}
              onChange={handleChange}
            />
            {errors.username && <span className="error">{errors.username}</span>}
            <input
              type="email"
              name="email"
              placeholder="Email"
              value={formData.email}
              onChange={handleChange}
            />
            {errors.email && <span className="error">{errors.email}</span>}
            <input
              type="password"
              name="password"
              placeholder="Password"
              value={formData.password}
              onChange={handleChange}
            />
            {errors.password && <span className="error">{errors.password}</span>}
            <input
              type="password"
              name="confirmPassword"
              placeholder="Confirm Password"
              value={formData.confirmPassword}
              onChange={handleChange}
            />
            {errors.confirmPassword && (
              <span className="error">{errors.confirmPassword}</span>
            )}
            <button type="submit">Sign Up</button>
          </form>
          <div className="login-link">
            Already have an account? <a href="/login">Login</a>
          </div>
        </div>
      </main>
      <Footer />
    </div>
  );
};

export default Signup;