import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Header from './Header';
import Footer from "./Footer";
import '../styles/Login.css';
import logo from '../assets/logo.JPG';

const Login = ({ setUserId }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [userInfo, setUserInfo] = useState(null); 
  const navigate = useNavigate(); 

  const handleLogin = async (e) => {
    e.preventDefault();
    setErrorMessage(''); // Clear previous errors

    try {
      const response = await fetch("http://localhost:8080/api/auth/signin", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({ username, password })
      });

      if (!response.ok) {
        throw new Error('Login failed. Please check your credentials.');
      }

      const data = await response.json();
      console.log("User authenticated:", data);

      // Extract token from the response body
      const token = data.token;

      // Store user information and token in localStorage
      localStorage.setItem('userInfo', JSON.stringify(data)); // Store the entire user info
      localStorage.setItem('token', token); // Store the token separately

      // Update user info state
      setUserInfo(data); 

      // Pass userId to the parent component
      if (setUserId) {
        setUserId(data.id);
      }

      // Redirect user based on role
      if (data.roles.includes("ROLE_ADMIN")) {
        navigate('/admin');
      } else if (data.roles.includes("ROLE_MODERATOR")) {
        navigate('/mod');
      } else {
        // Redirect to home page if not admin or moderator
        navigate('/');
      }

    } catch (error) {
      console.error('Error logging in:', error);
      setErrorMessage('Login failed. Please check your credentials and try again.');
    }
  };

  return (
    <div className="signin-wrapper">
      <Header />
      <main>
        <div className="signin-container">
          <img src={logo} className='logo' alt="Logo" />
          <form className="signin-form" onSubmit={handleLogin}>
            <h2>Sign in</h2>
            <p>Stay up to date with waiting</p>
            {errorMessage && <p className="error-message">{errorMessage}</p>}
            <input
              type="text"
              placeholder="Email or username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
            <input
              type="password"
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
            <a href="#" className="forgot-password">Forgot password?</a>
            <button type="submit">Login</button>
            <div className="signup-link">Don't have an account? <a href="/signup">Sign up</a></div>
          </form>
          {userInfo && (
            <div className="welcome-message">
              Welcome, {userInfo.username}!
            </div>
          )}
        </div>
      </main>
      <Footer />
    </div>
  );
};

export default Login;
