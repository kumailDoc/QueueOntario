import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Header from './Header';
import Footer from "./Footer";
import '../styles/Login.css';
import logo from '../assets/logo.JPG';

const Login = ({ setUserId }) => {  // Receive setUserId as a prop
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [userInfo, setUserInfo] = useState(null); // To store user details on successful login
  const navigate = useNavigate(); // Initialize navigate for redirecting

  const handleLogin = async (e) => {
    e.preventDefault();
    setErrorMessage(''); // Clear previous errors

    try {
      const response = await fetch("http://localhost:8080/api/auth/signin", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        credentials: 'include', // Allows cookies to be included in the request/response
        body: JSON.stringify({ username, password })
      });

      if (!response.ok) {
        throw new Error('Login failed. Please check your credentials.');
      }

      const data = await response.json();
      console.log("User authenticated:", data);

      // Store user information in state and localStorage
      setUserInfo(data); // Store user info in state
      localStorage.setItem('userInfo', JSON.stringify(data)); // Store user info in localStorage

      // Pass userId to the parent component
      setUserId(data.id);

      // Redirect to home page after successful login
      navigate('/');

    } catch (error) {
      setErrorMessage(error.message);
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
            />
            <input 
              type="password" 
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
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